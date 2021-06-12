package roguelike.entities;

import roguelike.Coordinate;
import roguelike.Resources;
import roguelike.items.ItemCollection;

public class Player extends Entity {
	protected ItemCollection items;
	protected int strength;
	protected int defense;
	protected int intelligence;
	
	
	public Player(Coordinate coord) {
		super(coord, Resources.defaultPlayerHealth, Resources.defaultPlayerHealth);
		strength = Resources.defaultStr;
		defense = Resources.defaultDef;
		intelligence = Resources.defaultInt;
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
}
