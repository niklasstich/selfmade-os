package roguelike;

public class Resources {
	static final int MAX_PLAYFIELD_HEIGHT = 20;
	static final int MAX_PLAYFIELD_WIDTH = 80;
	
	public static final int MAX_ENEMY_COUNT_PER_FLOOR = 10;
	
	//default player stats
	public static final int defaultPlayerHealth = 20;
	public static final int defaultStr = 10;
	public static final int defaultDef = 10;
	public static final int defaultInt = 10;
	static final int defaultPlayerItemCapacity = 20;
	
	//zombie
	public static final int ZOMBIE_HEALTH = 10;
	public static final int ZOMBIE_MINDMG = 4;
	public static final int ZOMBIE_MAXDMG = 5;
	public static final int ZOMBIE_HITCHANCE = 65;
	public static final int ZOMBIE_DODGECHANCE = 30;
	
	//claymore
	public static final int CLAYMORE_HITCHANCE = 75;
	public static final int CLAYMORE_BLOCKCHANCE = 0;
	public static final int CLAYMORE_PARRYCHANCE = 15;
	public static final int CLAYMORE_MINDMG = 3;
	public static final int CLAYMORE_MAXDMG = 5;
	public static final String CLAYMORE_NAME = "Iron Claymore";
	
	//directions
	static final int DIR_UP = 1;
	static final int DIR_RIGHT = 2;
	static final int DIR_DOWN = 3;
	static final int DIR_LEFT = 4;
	
	//basic floor for testing
	static String returnBasicFloor() {
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
