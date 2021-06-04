package roguelike;

class FloorTile extends Tile {
	private Item item;
	@Override
	char getSymbol() {
		if (item!=null) {
			return item.getSymbol();
		}
		return '.';
	}
	
	@Override
	boolean isPassable() {
		return true;
	}
	
}
