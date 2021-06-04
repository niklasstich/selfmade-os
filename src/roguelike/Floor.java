package roguelike;

import graphics.Console;
import hardware.Serial;
import rte.DynamicRuntime;
import rte.SClassDesc;

class Floor {
	private Tile[][] floorTiles;
	private Enemy[] enemies;
	
	//creates a clean floor
	Floor() {
		floorTiles = new Tile[Resources.MAX_PLAYFIELD_HEIGHT][Resources.MAX_PLAYFIELD_WIDTH];
		
	}
	
	Floor(String layout) {
		layout = layout.removeNewlines();
		if(layout.length()!=Resources.MAX_PLAYFIELD_WIDTH*Resources.MAX_PLAYFIELD_HEIGHT)
			MAGIC.inline(0xCC);
		floorTiles = new Tile[Resources.MAX_PLAYFIELD_HEIGHT][Resources.MAX_PLAYFIELD_WIDTH];
		parseFloor(layout);
	}
	
	private void parseFloor(String layout) {
		for (int row = 0; row < Resources.MAX_PLAYFIELD_HEIGHT; row++) {
			for (int column = 0; column < Resources.MAX_PLAYFIELD_WIDTH; column++) {
				int strPos = row*Resources.MAX_PLAYFIELD_WIDTH+column;
				Tile tile = null;
				switch (layout.charAt(strPos)) {
					case '.': tile = new FloorTile(); break;
					case TunnelTile.sym: tile = new TunnelTile(); break;
					case '#': tile = new WallTile(); break;
					case ' ': tile = new BlockedTile(); break;
				}
				floorTiles[row][column] = tile;
			}
		}
	}
	
	void renderFloor() {
		Console.clearConsole();
		Console.setCursor(0,0);
		for (int row = 0; row < Resources.MAX_PLAYFIELD_HEIGHT; row++) {
			for (int column = 0; column < Resources.MAX_PLAYFIELD_WIDTH; column++) {
				Console.print(floorTiles[row][column].getSymbol());
			}
		}
	}
	
	public Coordinate getValidSpawn() {
		//get a random number
		return null;
	}
	
	private class CoordinateList {
		Coordinate[] coords = new Coordinate[Resources.MAX_PLAYFIELD_WIDTH*Resources.MAX_PLAYFIELD_HEIGHT];
		int count = 0;
		void add(Coordinate c) {
			coords[count] = c;
			count++;
		}
	}
	
	private CoordinateList getValidPoints() {
		CoordinateList list = new CoordinateList();
		for (int row = 0; row < Resources.MAX_PLAYFIELD_HEIGHT; row++) {
			for (int column = 0; column < Resources.MAX_PLAYFIELD_WIDTH; column++) {
				Tile tile = floorTiles[row][column];
				if(tile.isPassable() && !DynamicRuntime.isInstance(tile, (SClassDesc) MAGIC.clssDesc("TunnelTile"), false)) {
					list.add(new Coordinate(column, row));
				}
			}
		}
	}
	
}
