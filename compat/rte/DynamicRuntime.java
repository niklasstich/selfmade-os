package rte;
public class DynamicRuntime {
	public static Object newInstance(int scS, int rlE, SClassDesc type) {
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
			MAGIC.wMem32(i, 0); //initialize with 0
		}
		objPtr += rlE*4;//offset object pointer to make space for the relocs
		Object newOb = MAGIC.cast2Obj(objPtr);//we now have the correct address for the new object in objPtr
		MAGIC.assign(ob._r_next, newOb);
		MAGIC.assign(newOb._r_relocEntries, rlE);
		MAGIC.assign(newOb._r_scalarSize, scS);
		MAGIC.assign(newOb._r_type, type);
		return newOb;
	}
	//creates a new array object
	public static SArray newArray(int length, int arrDim, int entrySize,
	                              int stdType, Object unitType) {
		int scalarSize = MAGIC.getInstScalarSize("SArray");
		int relocCount = MAGIC.getInstScalarSize("SArray");
		//if the array is multidimensional or holds relocs, we add the length of it to the reloc count
		if (arrDim>1 || entrySize<0) relocCount+=length;
		else scalarSize+=length*entrySize; //otherwise, its elements are scalars, so we add to that count
		SArray arr = (SArray)newInstance(scalarSize, relocCount, (SClassDesc) MAGIC.clssDesc("SArray"));
		//overwrite proper attributes
		MAGIC.assign(arr.length, length);
		MAGIC.assign(arr._r_unitType, unitType);
		MAGIC.assign(arr._r_stdType, stdType);
		MAGIC.assign(arr._r_dim, arrDim);
		return arr;
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
		if (asCast) while(true); //Konvertierungsfehler
		return false; //Objekt passt nicht zu Klasse
	}
	public static SIntfMap isImplementation(Object o, SIntfDesc dest,
	                                        boolean asCast) { while(true); }
	public static boolean isArray(SArray o, int stdType,
	                              Object unitType, int arrDim, boolean asCast) { while(true); }
	public static void checkArrayStore(Object dest,
	                                   SArray newEntry) { while(true); }
}