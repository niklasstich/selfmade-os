package roguelike;

public class PathTile extends Tile {
	static final char sym = 176;
	@Override
	char getSymbol() {
		return PathTile.sym;
	}
	
	@Override
	boolean isPassable() {
		return true;
	}
}
