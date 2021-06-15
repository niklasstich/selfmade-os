package roguelike.tiles;

import roguelike.items.Item;
import roguelike.tiles.Tile;

public class FloorTile extends Tile {
	private Item item;
	
	@Override
	public char getSymbol() {
		if (item!=null) {
			return item.getSymbol();
		}
		return '.';
	}
	
	@Override
	public boolean isPassable() {
		return true;
	}
	
}
