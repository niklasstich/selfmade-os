package roguelike;

class Resources {
	static final int MAX_PLAYFIELD_HEIGHT = 20;
	static final int MAX_PLAYFIELD_WIDTH = 80;
	
	//default player stats
	static final int defaultHealth = 20;
	static final int defaultStr = 10;
	static final int defaultDef = 10;
	static final int defaultInt = 10;
	
	static String returnBasicFloor() {
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
		return retval;
	}
}
