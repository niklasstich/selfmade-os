package roguelike;

public class TunnelTile extends Tile {
	static final char sym = 178;
	@Override
	char getSymbol() {
		return TunnelTile.sym;
	}
	
	@Override
	boolean isPassable() {
		return false;
	}
}
