package roguelike.tiles;

import roguelike.tiles.Tile;

public class WallTile extends Tile {
	
	@Override
	public char getSymbol() {
		return '#';
	}
	
	@Override
	public boolean isPassable() {
		return false;
	}
}
