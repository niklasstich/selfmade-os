package roguelike.entities;

import roguelike.Coordinate;
import roguelike.Resources;

public abstract class Entity {
	//position
	protected Coordinate coord;
	//player stats
	protected int health;
	protected int maxHealth;
	protected int strength;
	protected int defense;
	protected int intelligence;
	protected Coordinate lastCoord;
	
	public Entity(Coordinate coord) {
		this.coord = coord;
		strength = Resources.defaultStr;
		defense = Resources.defaultDef;
		intelligence = Resources.defaultInt;
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
	
	public Coordinate getCoord() {
		return coord;
	}
	
	public void setPos(Coordinate coord) {
		lastCoord = this.coord;
		this.coord = coord;
	}
	
	public Coordinate getLastCoord() {
		return lastCoord;
	}
	
	//see Resources for direction constants
	public void move(Coordinate newCoord) {
		lastCoord = coord;
		coord = newCoord;
	}
	
	public abstract char getSymbol();
}
