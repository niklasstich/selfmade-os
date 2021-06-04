package roguelike;

public class Player {
	//position
	private int posx, posy;
	//player stats
	private int health, maxHealth;
	private int strength, defense, intelligence;
	Player(int posx, int posy) {
		this.posx = posx;
		this.posy = posy;
		maxHealth = health = Resources.defaultHealth;
		strength = Resources.defaultStr;
		defense = Resources.defaultDef;
		intelligence = Resources.defaultInt;
	}
	
	
	public int getPosx() {
		return posx;
	}
	
	public void setPosx(int posx) {
		this.posx = posx;
	}
	
	public int getPosy() {
		return posy;
	}
	
	public void setPosy(int posy) {
		this.posy = posy;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getIntelligence() {
		return intelligence;
	}
}
