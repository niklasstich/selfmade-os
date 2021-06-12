package roguelike.entities;

import roguelike.Coordinate;
import roguelike.Resources;
import roguelike.entities.Entity;
import roguelike.items.ItemCollection;

public class Player extends Entity {
	protected ItemCollection items;
	
	
	public Player(Coordinate coord) {
		super(coord);
		maxHealth = health = Resources.defaultPlayerHealth;
	}
	
	@Override
	public char getSymbol() {
		return '@';
	}
	
}
