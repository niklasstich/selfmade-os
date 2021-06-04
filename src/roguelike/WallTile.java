package roguelike;

public class WallTile extends Tile {
	
	@Override
	char getSymbol() {
		return '#';
	}
	
	@Override
	boolean isPassable() {
		return false;
	}
}
