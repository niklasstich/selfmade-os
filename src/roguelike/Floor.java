package roguelike;

import hardware.Random;
import hardware.Serial;
import rte.DynamicRuntime;
import rte.SClassDesc;

class Floor {
	private final Tile[][] floorTiles;
	private Enemy[] enemies;
	
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
		parseFloor(layout);
	}
	
	Tile[][] getFloorTiles() {
		return floorTiles;
	}
	
	private void parseFloor(String layout) {
		for (int row = 0; row < Resources.MAX_PLAYFIELD_HEIGHT; row++) {
			for (int column = 0; column < Resources.MAX_PLAYFIELD_WIDTH; column++) {
				int strPos = row*Resources.MAX_PLAYFIELD_WIDTH+column;
				Tile tile = null;
				switch (layout.charAt(strPos)) {
					case '.': tile = new FloorTile(); break;
					case 178: tile = new PathTile(); break;
					case '#': tile = new WallTile(); break;
					case ' ': tile = new BlockedTile(); break;
				}
				floorTiles[row][column] = tile;
			}
		}
	}
	
	public Coordinate getValidSpawn() {
		//get list of valid coordinates, pick one at random
		CoordinateList cl = getValidSpawnPoints();
		return cl.coordinateAt(Random.unsignedRand()%cl.count);
	}
	
	public boolean isCoordinatePassable(Coordinate coord) {
		return floorTiles[coord.getPosy()][coord.getPosx()] != null
				&& floorTiles[coord.getPosy()][coord.getPosx()].isPassable();
	}
	
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
