package roguelike;

class Resources {
	static final int MAX_PLAYFIELD_HEIGHT = 20;
	static final int MAX_PLAYFIELD_WIDTH = 80;
	
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
