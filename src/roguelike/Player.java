package roguelike;

import graphics.Console;
import graphics.ConsoleColors;

class Player {
	//position
	private Coordinate coord, lastCoord;
	//player stats
	private int health, maxHealth;
	private int strength, defense, intelligence;
	Player(Coordinate coord) {
		this.coord = coord;
		maxHealth = health = Resources.defaultHealth;
		strength = Resources.defaultStr;
		defense = Resources.defaultDef;
		intelligence = Resources.defaultInt;
	}
	
	static char getSymbol() {
		return '@';
	}
	
	
	void setPos(Coordinate coord) {
		lastCoord = this.coord;
		this.coord = coord;
	}
	
	int getHealth() {
		return health;
	}
	
	int getMaxHealth() {
		return maxHealth;
	}
	
	int getStrength() {
		return strength;
	}
	
	int getDefense() {
		return defense;
	}
	
	int getIntelligence() {
		return intelligence;
	}
	
	Coordinate getCoord() {
		return coord;
	}
	
	Coordinate getLastCoord() {
		return lastCoord;
	}
	
	//see Resources for direction constants
	void move(int direction, Floor floor) {
		//TODO: collision
		//first, check if we can move
		//get new coord
		Coordinate newCoord;
		switch (direction) {
			case Resources.DIR_UP: newCoord = new Coordinate(coord.getPosx(), coord.getPosy()-1); break;
			case Resources.DIR_DOWN: newCoord = new Coordinate(coord.getPosx(), coord.getPosy()+1); break;
			case Resources.DIR_LEFT: newCoord = new Coordinate(coord.getPosx()-1, coord.getPosy()); break;
			case Resources.DIR_RIGHT: newCoord = new Coordinate(coord.getPosx()+1, coord.getPosy()); break;
			default: return;
		}
		//ask floor if coordinate is a valid tile
		if(!floor.isCoordinatePassable(newCoord)) return;
		//if so, apply
		lastCoord = coord;
		coord = newCoord;
	}
}
