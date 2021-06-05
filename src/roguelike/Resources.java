package roguelike;

class Resources {
	static final int MAX_PLAYFIELD_HEIGHT = 20;
	static final int MAX_PLAYFIELD_WIDTH = 80;
	
	//default player stats
	static final int defaultHealth = 20;
	static final int defaultStr = 10;
	static final int defaultDef = 10;
	static final int defaultInt = 10;
	
	//directions
	static final int DIR_UP = 1;
	static final int DIR_RIGHT = 2;
	static final int DIR_DOWN = 3;
	static final int DIR_LEFT = 4;
	
	//basic floor for testing
	static String returnBasicFloor() {
		/*
		String retval = "";
		retval = retval.concat("                                                                                ");
		retval = retval.concat("################################################################################");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("#                                                                              #");
		retval = retval.concat("################################################################################");
		retval = retval.concat("                                                                                ");
		return retval;*/
		char[] val = new char[1600];
		int index = 0;
		for (;index<80;index++) {
			val[index] = 32;
		}
		for(;index<160;index++) {
			val[index] = 35;
		}
		for(;index<1520;index++) {
			if(index%80==0||index%80==79) {
				val[index] = 35;
			} else if (index == 1422) {
				val[index] = 178;
			} else {
				val[index] = 46;
			}
		}
		for(;index<1600;index++) {
			val[index] = 35;
		}
		return new String(val);
	}
}
