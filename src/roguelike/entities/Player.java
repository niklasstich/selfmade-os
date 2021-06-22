package roguelike.entities;

import hardware.Serial;
import roguelike.Coordinate;
import roguelike.Resources;
import roguelike.items.Claymore;
import roguelike.items.ItemCollection;
import roguelike.items.Weapon;

public class Player extends Entity {
	protected ItemCollection items;
	protected Weapon weapon;
	protected boolean dead, won;
	protected boolean godMode;
	protected int strength;
	protected int defense;
	protected int intelligence;
	
	
	public Player(Coordinate coord) {
		super(coord, Resources.defaultPlayerHealth, Resources.defaultPlayerHealth);
		strength = Resources.defaultStr;
		defense = Resources.defaultDef;
		intelligence = Resources.defaultInt;
		//player starts with a claymore
		weapon = new Claymore();
	}
	
	@Override
	public char getSymbol() {
		return '@';
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
	
	public Weapon getEquippedWeapon() {
		return weapon;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead() {
		dead = true;
	}
	
	public boolean hasWon() {
		return won;
	}
	
	public void setWon() {
		won = true;
	}
	
	public boolean isGodMode() {
		return godMode;
	}
	
	public void toggleGodmode() {
		this.godMode = !this.godMode;
		Serial.print("God mode set to: ");
		Serial.println(godMode);
	}
	
	@Override
	public void setHealth(int health) {
		if(!godMode) this.health = health;
	}
	
	public int getAttackPower() {
		//attack power is strength / 5
		return getStrength()/5;
	}
	
	public int getDefensePower() {
		//defense power is defense / 5
		return getDefense()/5;
	}
}
