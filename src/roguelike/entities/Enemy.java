package roguelike.entities;

import roguelike.Coordinate;

public abstract class Enemy extends Entity {
	//0-100
	int hitChance, dodgeChance;
	int minDamage, maxDamage;
	
	public Enemy(Coordinate coord, int health, int maxHealth, int hitChance, int dodgeChance, int minDamage, int maxDamage) {
		super(coord, health, maxHealth);
		this.hitChance = hitChance;
		this.dodgeChance = dodgeChance;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}
	
	public int getHitChance() {
		return hitChance;
	}
	
	public int getDodgeChance() {
		return dodgeChance;
	}
	
	public int getMinDamage() {
		return minDamage;
	}
	
	public int getMaxDamage() {
		return maxDamage;
	}
	
	
}
