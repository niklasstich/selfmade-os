package roguelike;

import hardware.Random;
import hardware.Serial;
import roguelike.entities.Enemy;
import roguelike.entities.EnemyCollection;
import roguelike.tiles.*;
import rte.DynamicRuntime;
import rte.SClassDesc;

class Floor {
	private final Tile[][] floorTiles;
	private EnemyCollection enemies;
	
	//creates a clean floor
	Floor() {
		floorTiles = new Tile[Resources.MAX_PLAYFIELD_HEIGHT][Resources.MAX_PLAYFIELD_WIDTH];
	}
	
	Floor(String layout) {
		layout = layout.removeNewlines();
		if(layout.length()!=Resources.MAX_PLAYFIELD_WIDTH*Resources.MAX_PLAYFIELD_HEIGHT) {
			Serial.print(layout.length());
			MAGIC.inline(0xCC);
		}
		floorTiles = new Tile[Resources.MAX_PLAYFIELD_HEIGHT][Resources.MAX_PLAYFIELD_WIDTH];
		enemies = new EnemyCollection();
		parseFloor(layout);
	}
	
	Tile[][] getFloorTiles() {
		return floorTiles;
	}
	
	//parses a string layout into a valid floor
	private void parseFloor(String layout) {
		for (int row = 0; row < Resources.MAX_PLAYFIELD_HEIGHT; row++) {
			for (int column = 0; column < Resources.MAX_PLAYFIELD_WIDTH; column++) {
				int strPos = row*Resources.MAX_PLAYFIELD_WIDTH+column;
				Tile tile = null;
				switch (layout.charAt(strPos)) { //TODO: remove magic numbers
					case '.': tile = new FloorTile(); break;
					case 178: tile = new PathTile(); break;
					case '#': tile = new WallTile(); break;
					case ' ': tile = new BlockedTile(); break;
				}
				floorTiles[row][column] = tile;
			}
		}
	}
	
	//returns a valid spawn position for player
	public Coordinate getValidSpawn() {
		//get list of valid coordinates, pick one at random
		CoordinateList cl = getValidSpawnPoints();
		return cl.coordinateAt(Random.unsignedRand()%cl.count);
	}
	
	//returns a valid spawn position for an enemy, except for the position the player is currently in
	public Coordinate getValidEnemySpawn(Coordinate playerPos) {
		CoordinateList cl = getValidSpawnPoints();
		Coordinate c = null;
		while(c==null||c==playerPos){
			c = cl.coordinateAt(Random.unsignedRand()%cl.count);
		}
		return c;
	}
	
	//returns whether or not tile at coord is passable
	public boolean isCoordinatePassable(Coordinate coord) {
		return floorTiles[coord.getPosy()][coord.getPosx()] != null
				&& floorTiles[coord.getPosy()][coord.getPosx()].isPassable();
	}
	
	//returns enemy if there is one at coord, otherwise null
	public Enemy getEnemyAtCoordinate(Coordinate coord) {
		for(Enemy e : enemies.getEnemies()) {
			if (e==null) continue;
			if (e.getCoord().equals(coord)) return e;
		}
		return null;
	}
	
	//adds enemy to floor and returns true if successful, returns false if there is already an enemy at that coordinate
	//TODO: rethink if this is the correct approach
	public boolean insertEnemy(Enemy enemy) {
		if(getEnemyAtCoordinate(enemy.getCoord())!=null) return false;
		return enemies.append(enemy);
	}
	
	public EnemyCollection getEnemies() {
		return enemies;
	}
	
	public void killEnemy(Enemy e) {
		enemies.delete(e);
	}
	
	public Tile getTileAtCoordinate(Coordinate coord) {
		return floorTiles[coord.getPosy()][coord.getPosx()];
	}
	
	//container class for coordinates
	private static class CoordinateList {
		Coordinate[] coords = new Coordinate[Resources.MAX_PLAYFIELD_WIDTH*Resources.MAX_PLAYFIELD_HEIGHT];
		private int count = 0;
		void add(Coordinate c) {
			coords[count] = c;
			count++;
		}
		Coordinate coordinateAt(int index) {
			return coords[index];
		}
		
		boolean contains(Coordinate coord) {
			for (int i = 0; i < count; i++) {
				if(coords[i].getPosx() == coord.getPosx() && coords[i].getPosy() == coord.getPosy())
					return true;
			}
			return false;
		}
	}
	
	//returns a list of all valid spawn points
	private CoordinateList getValidSpawnPoints() {
		CoordinateList list = new CoordinateList();
		for (int row = 0; row < Resources.MAX_PLAYFIELD_HEIGHT; row++) {
			for (int column = 0; column < Resources.MAX_PLAYFIELD_WIDTH; column++) {
				Tile tile = floorTiles[row][column];
				if(tile.isPassable() && !DynamicRuntime.isInstance(tile, (SClassDesc) MAGIC.clssDesc("PathTile"), false)) {
					list.add(new Coordinate(column, row));
				}
			}
		}
		return list;
	}
	
}
