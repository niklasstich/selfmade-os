package roguelike;

import graphics.Console;
import hardware.Serial;

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
				Serial.print(floorTiles[row][column].getSymbol());
			}
		}
	}
}
