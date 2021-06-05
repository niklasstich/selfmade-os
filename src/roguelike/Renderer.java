package roguelike;

import graphics.Console;
import graphics.ConsoleColors;

public class Renderer {
	private Floor floor;
	private Player player;
	Renderer(Floor floor, Player player) {
		this.floor = floor;
		this.player = player;
	}
	void renderFloor() {
		Tile[][] tiles = floor.getFloorTiles();
		Console.clearConsole();
		Console.setCursor(0,0);
		for (int row = 0; row < Resources.MAX_PLAYFIELD_HEIGHT; row++) {
			for (int column = 0; column < Resources.MAX_PLAYFIELD_WIDTH; column++) {
				Console.print(tiles[row][column].getSymbol());
			}
		}
	}
	
	void renderPlayer() {
		//overwrite player old position with proper symbol
		Coordinate lastCoord = player.getLastCoord();
		if(lastCoord!=null) {
			Tile t = floor.getFloorTiles()[lastCoord.getPosy()][lastCoord.getPosx()];
			Console.directPrintChar(t.getSymbol(), lastCoord.getPosx(), lastCoord.getPosy(), ConsoleColors.DEFAULT_CONSOLE_COLOR);
		}
		//write new player pos
		Coordinate currentCoord = player.getCoord();
		Console.directPrintChar(Player.getSymbol(), currentCoord.getPosx(), currentCoord.getPosy(), ConsoleColors.DEFAULT_CONSOLE_COLOR);
	}
}
