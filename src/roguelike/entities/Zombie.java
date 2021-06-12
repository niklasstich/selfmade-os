package roguelike.entities;

import roguelike.Coordinate;
import roguelike.Resources;

public class Zombie extends Enemy {
	public Zombie(Coordinate coord) {
		super(coord, Resources.ZOMBIE_HEALTH, Resources.ZOMBIE_HEALTH, Resources.ZOMBIE_HITCHANCE,
				Resources.ZOMBIE_DODGECHANCE, Resources.ZOMBIE_MINDMG, Resources.ZOMBIE_MAXDMG);
	}
	
	@Override
	public char getSymbol() {
		return 'Z';
	}
}
