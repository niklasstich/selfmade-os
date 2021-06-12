package roguelike;

public class Coordinate {
	private final int posx, posy;
	Coordinate(int posx, int posy) {
		this.posx = posx;
		this.posy = posy;
	}
	
	public int getPosx() {
		return posx;
	}
	
	public int getPosy() {
		return posy;
	}
	
	public boolean equals(Coordinate c) {
		if(this.getPosx()!=c.getPosx()) return false;
		return this.getPosy() == c.getPosy();
	}
}
