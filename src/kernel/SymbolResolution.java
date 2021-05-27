package kernel;

import rte.SClassDesc;
import rte.SMthdBlock;
import rte.SPackage;

public class SymbolResolution {
	public static SMthdBlock findMethodBlock(int addr){
		return searchPackageRecursive(addr, SPackage.root);
	}
	
	private static SMthdBlock searchPackageRecursive(int addr, SPackage pack) {
		SMthdBlock retval = null;
		if(pack.subPacks!=null) {
			retval = searchPackageRecursive(addr, pack.subPacks);
		}
		if (retval != null) return retval;
		//go through our own classes
		retval = searchClass(addr, pack.units);
		if (retval != null) return retval;
		pack = pack.nextPack;
		if(pack.nextPack != null)
			return searchPackageRecursive(addr, pack.nextPack);
		return null;
	}
	
	private static SMthdBlock searchClass(int addr, SClassDesc cl) {
		SMthdBlock retval = null;
		while(cl!=null) {
			//check methods of this class
			if(cl.mthds != null)
				retval = checkMethods(addr, cl.mthds);
			if (retval!=null) return retval;
			cl = cl.nextUnit;
		}
		return null;
	}
	private static SMthdBlock checkMethods(int addr, SMthdBlock mthd) {
		while(mthd != null) {
			int startAddr = MAGIC.cast2Ref(mthd);
			int endAddr = startAddr+mthd._r_scalarSize;
			if(startAddr <= addr && addr <= endAddr)
				return mthd;
			mthd = mthd.nextMthd;
		}
		return null;
	}
}
