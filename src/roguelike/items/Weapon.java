package roguelike.items;

import roguelike.entities.Player;

public abstract class Weapon extends Item {
	int hitChance, blockChance, parryChance;
	int minDamage, maxDamage;
	
	public Weapon(String name, int hitChance, int blockChance, int parryChance, int minDamage, int maxDamage) {
		super(name);
		this.hitChance = hitChance;
		this.blockChance = blockChance;
		this.parryChance = parryChance;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}
	
	//execute special modifications, such as in(/de)creasing stats or healing the player
	abstract public void onEquip(Player p);
	
	abstract public void onUnequip(Player p);
	
	@Override
	public char getSymbol() {
		return 234;
	}
	
	public int getHitChance() {
		return hitChance;
	}
	
	public int getBlockChance() {
		return blockChance;
	}
	
	public int getParryChance() {
		return parryChance;
	}
	
	public int getMinDamage() {
		return minDamage;
	}
	
	public int getMaxDamage() {
		return maxDamage;
	}
}
