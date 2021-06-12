package roguelike.tiles;

public class PathTile extends Tile {
	static final char sym = 176;
	@Override
	public char getSymbol() {
		return PathTile.sym;
	}
	
	@Override
	public boolean isPassable() {
		return true;
	}
}
