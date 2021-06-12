package roguelike.entities;

import roguelike.Coordinate;

public abstract class Enemy extends Entity {
	float hitChance, dodgeChance;
	int minDamage, maxDamage;
	
	public Enemy(Coordinate coord, int health, int maxHealth, float hitChance, float dodgeChance, int minDamage, int maxDamage) {
		super(coord, health, maxHealth);
		this.hitChance = hitChance;
		this.dodgeChance = dodgeChance;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}
}
