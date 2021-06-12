package roguelike.entities;

import roguelike.Coordinate;

public class Zombie extends Enemy {
	public Zombie(Coordinate coord) {
		super(coord);
		this.hitChance = 0.25F;
		this.minDamage = 1;
		this.maxDamage = 3;
	}
	
	@Override
	public char getSymbol() {
		return 'Z';
	}
}
