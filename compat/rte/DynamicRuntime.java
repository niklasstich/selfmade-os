package rte;

import graphics.Console;
import kernel.BIOS;

public class DynamicRuntime {
	private static SEmptyObject firstEObj = null;
	
	public static void initializeEmptyObjects() {
		//get memsegs from bios and evaluate if it is free
		int conIndex = 0;
		do {
			BIOS.writeMemMapToBuff(conIndex);
			conIndex = BIOS.regs.EBX;
			
			//if segment is free, allocate empty object
			int memSegType = MAGIC.rMem32(BIOS.MEM_READ_BUF+16);
			if(memSegType==1) {
				int baseAdd = (int) MAGIC.rMem64(BIOS.MEM_READ_BUF);
				int len = (int) MAGIC.rMem64(BIOS.MEM_READ_BUF+8);
				if (baseAdd>BIOS.BIOS_ADDR_MAX) {
					
					int maxAddress = baseAdd+len-1;
					//get imageBase first object and go through the next pointer until we find no more objects
					Object obj = MAGIC.cast2Obj(MAGIC.imageBase+16);
					while (obj._r_next!=null) {
						obj = obj._r_next;
					}
					
					int nextFreeAddress = MAGIC.cast2Ref(obj)+obj._r_scalarSize;
					//null initialization
					for(int i = nextFreeAddress;i<=maxAddress;i++) {
						MAGIC.wMem8(i, (byte) 0);
					}
					
					Object eObj = MAGIC.cast2Obj(nextFreeAddress);
					nextFreeAddress+=12;
					//set properties correctly
					MAGIC.assign(eObj._r_relocEntries, 3);
					MAGIC.assign(eObj._r_type, MAGIC.clssDesc("SEmptyObject"));
					MAGIC.assign(eObj._r_scalarSize, maxAddress-nextFreeAddress+1);
					
					//correct previous objects next pointer
					MAGIC.assign(obj._r_next, (Object) eObj);
					
					if (firstEObj==null) {
						firstEObj = (SEmptyObject) eObj;
					} else {
						SEmptyObject temp = firstEObj;
						while (temp.nextEmptyObject!=null) {
							temp = temp.nextEmptyObject;
						}
						MAGIC.assign(temp.nextEmptyObject, (SEmptyObject)eObj);
					}
				}
			}
		} while(conIndex!=0);
	}
	
	public static Object newInstance(int scS, int rlE, SClassDesc type) {
		/*
		int objPtr = MAGIC.imageBase;
		//offset für ptr zum nächsten Objekt
		objPtr+=16;
		//cast ptr to obj
		Object ob = MAGIC.cast2Obj(objPtr);
		//TODO: this case should never happen?
		if (ob == null) {
			while(true);
		}
		while(ob._r_next != null) {
			//traverse objects
			ob = ob._r_next;
		}
		//return to pointer and add scalar size after last object
		objPtr = MAGIC.cast2Ref(ob)+ob._r_scalarSize;
		//align to 4
		if(objPtr % 4 != 0){
			objPtr += 4 - (objPtr % 4);
		}
		for(int i = objPtr; i<objPtr+scS+rlE*4+4;i++) {
			MAGIC.wMem8(i, (byte)0); //initialize with 0
		}
		objPtr += rlE*4;//offset object pointer to make space for the relocs
		Object newOb = MAGIC.cast2Obj(objPtr);//we now have the correct address for the new object in objPtr
		MAGIC.assign(ob._r_next, newOb);
		MAGIC.assign(newOb._r_relocEntries, rlE);
		MAGIC.assign(newOb._r_scalarSize, scS);
		MAGIC.assign(newOb._r_type, type);
		return newOb;*/
		
		//find emptyobject that can fit new instance
		SEmptyObject eObj = firstEObj;
		int newObjLen = ((scS+3)&~3) + 4*rlE+16;
		while(true) {
			if ((eObj._r_scalarSize-8)>= newObjLen){
				break;
			}
			if(eObj.nextEmptyObject == null) {
				MAGIC.inline(0xCC);
			}
			eObj = eObj.nextEmptyObject;
		}
		
		//calculate new address and subtract object size from eObj scalarsize
		int newObjAddr = MAGIC.cast2Ref(eObj)+eObj._r_scalarSize-((scS+3)&~3);
		MAGIC.assign(eObj._r_scalarSize, eObj._r_scalarSize-newObjLen);
		
		//assign the actual object
		Object newOb = MAGIC.cast2Obj(newObjAddr);
		MAGIC.assign(newOb._r_relocEntries, rlE);
		MAGIC.assign(newOb._r_scalarSize, scS);
		MAGIC.assign(newOb._r_type, type);
		
		//correct next pointers
		MAGIC.assign(newOb._r_next, eObj._r_next);
		MAGIC.assign(eObj._r_next, newOb);
		return newOb;
	}
	//creates a new array object
	public static SArray newArray(int length, int arrDim, int entrySize,
	                              int stdType, Object unitType) {
		int scS, rlE;
		SArray me;
		
		if (stdType==0 && unitType._r_type!=MAGIC.clssDesc("SClassDesc"))
			MAGIC.inline(0xCC); //check type of unitType, we don't support interface arrays
		scS=MAGIC.getInstScalarSize("SArray");
		rlE=MAGIC.getInstRelocEntries("SArray");
		if (arrDim>1 || entrySize<0) rlE+=length;
		else scS+=length*entrySize;
		me=(SArray)newInstance(scS, rlE, (SClassDesc) MAGIC.clssDesc("SArray"));
		MAGIC.assign(me.length, length);
		MAGIC.assign(me._r_dim, arrDim);
		MAGIC.assign(me._r_stdType, stdType);
		MAGIC.assign(me._r_unitType, unitType);
		return me;
	}
	//create a new multi level array
	//aus dem Handbuch kopiert :/
	public static void newMultArray(SArray[] parent, int curLevel,
	                                int destLevel, int length, int arrDim, int entrySize, int stdType,
	                                Object unitType) {
		int i; //temporäre Variable
		if (curLevel+1<destLevel) { //es folgt noch mehr als eine Dimension
			curLevel++; //aktuelle Dimension erhöhen
			for (i=0; i<parent.length; i++) //jedes Element mit Array befüllen
			newMultArray((SArray[])((Object)parent[i]), curLevel, destLevel,
			length, arrDim, entrySize, stdType, unitType);
		}
		else { //letzte anzulegende Dimension
			destLevel=arrDim-curLevel; //Zieldimension eines Elementes
			for (i=0; i<parent.length; i++) //jedes Element mit Zieltyp befüllen
			parent[i]=newArray(length, destLevel, entrySize, stdType, unitType);
		}
	}
	//aus dem Handbuch kopiert :/
	public static boolean isInstance(Object o, SClassDesc dest,
	                                 boolean asCast) {
		SClassDesc check; //temporäre Variable
		if (o==null) { //Prüfung auf null
			if (asCast) return true; //null darf immer konvertiert werden
			return false; //null ist keine Instanz
		}
		check=o._r_type; //für weitere Vergleiche Objekttyp ermitteln
		while (check!=null) { //suche passende Klasse
			if (check==dest) return true; //passende Klasse gefunden
			check=check.parent; //Elternklasse versuchen
		}
		if (asCast) MAGIC.inline(0xCC); //Konvertierungsfehler
		return false; //Objekt passt nicht zu Klasse
	}
	public static SIntfMap isImplementation(Object o, SIntfDesc dest,
	                                        boolean asCast) {
		SIntfMap check;
		
		if (o==null) return null;
		check=o._r_type.implementations;
		while (check!=null) {
			if (check.owner==dest) return check;
			check=check.next;
		}
		if (asCast) MAGIC.inline(0xCC);
		return null;
	}
	public static boolean isArray(SArray o, int stdType, SClassDesc clssType, int arrDim, boolean asCast) {
		SClassDesc clss;
		
		//in fact o is of type "Object", _r_type has to be checked below - but this check is faster than "instanceof" and conversion
		if (o==null) {
			if (asCast) return true; //null matches all
			return false; //null is not an instance
		}
		if (o._r_type!=MAGIC.clssDesc("SArray")) { //will never match independently of arrDim
			if (asCast) MAGIC.inline(0xCC);
			return false;
		}
		if (clssType==MAGIC.clssDesc("SArray")) { //special test for arrays
			if (o._r_unitType==MAGIC.clssDesc("SArray")) arrDim--; //an array of SArrays, make next test to ">=" instead of ">"
			if (o._r_dim>arrDim) return true; //at least one level has to be left to have an object of type SArray
			if (asCast) MAGIC.inline(0xCC);
			return false;
		}
		//no specials, check arrDim and check for standard type
		if (o._r_stdType!=stdType || o._r_dim<arrDim) { //check standard types and array dimension
			if (asCast) MAGIC.inline(0xCC);
			return false;
		}
		if (stdType!=0) {
			if (o._r_dim==arrDim) return true; //array of standard-type matching
			if (asCast) MAGIC.inline(0xCC);
			return false;
		}
		//array of objects, make deep-check for class type (PicOS does not support interface arrays)
		if (o._r_unitType._r_type!=MAGIC.clssDesc("SClassDesc")) MAGIC.inline(0xCC);
		clss= (SClassDesc) o._r_unitType;
		while (clss!=null) {
			if (clss==clssType) return true;
			clss=clss.parent;
		}
		if (asCast) MAGIC.inline(0xCC);
		return false;
	}
	
	public static void checkArrayStore(SArray dest, SArray newEntry) {
		if (dest._r_dim>1) isArray(newEntry, dest._r_stdType, (SClassDesc) dest._r_unitType, dest._r_dim-1, true);
		else if (dest._r_unitType==null) MAGIC.inline(0xCC);
		else isInstance(newEntry, (SClassDesc) dest._r_unitType, true);
	}
}