package roguelike.entities;

import roguelike.Coordinate;

public abstract class Enemy extends Entity {
	float hitChance;
	int minDamage, maxDamage;
	
	public Enemy(Coordinate coord) {
		super(coord);
	}
}
