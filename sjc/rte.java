package java.lang;
import rte.SClassDesc;
public class Object {
	public final SClassDesc _r_type=null;
	public final Object _r_next=null;
	public final int _r_relocEntries=0, _r_scalarSize=0;
}
package java.lang;
public class String {
	private char[] value;
	private int count;
	@SJC.Inline
	public int length() {
		return count;
	}
	@SJC.Inline
	public char charAt(int i) {
		return value[i];
	}
}
package rte;
public class SArray {
	public final int length=0, _r_dim=0, _r_stdType=0;
	public final Object _r_unitType=null;
}
package rte;
public class SClassDesc {
	public SClassDesc parent;
	public SIntfMap implementations;
}
package rte;
public class SIntfDesc {
}
package rte;
public class SIntfMap {
	public SIntfDesc owner;
	public SIntfMap next;
}
package rte;
public class SMthdBlock {
}
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
		objPtr += ob._r_scalarSize + ob._r_relocEntries*4;
		while(ob._r_next != null) {
			//traverse objects while updating pointer
			ob = ob._r_next;
			objPtr += ob._r_scalarSize + ob._r_relocEntries*4;
		}
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
	public static SArray newArray(int length, int arrDim, int entrySize,
	                              int stdType, Object unitType) { while(true); }
	public static void newMultArray(SArray[] parent, int curLevel,
	                                int destLevel, int length, int arrDim, int entrySize, int stdType,
	                                Object unitType) { while(true); }
	public static boolean isInstance(Object o, SClassDesc dest,
	                                 boolean asCast) { while(true); }
	public static SIntfMap isImplementation(Object o, SIntfDesc dest,
	                                        boolean asCast) { while(true); }
	public static boolean isArray(SArray o, int stdType,
	                              Object unitType, int arrDim, boolean asCast) { while(true); }
	public static void checkArrayStore(Object dest,
	                                   SArray newEntry) { while(true); }
}
