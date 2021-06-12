package roguelike;

import graphics.Console;
import graphics.ConsoleColors;
import roguelike.entities.Enemy;
import roguelike.entities.EnemyCollection;
import roguelike.entities.Entity;
import roguelike.entities.Player;
import roguelike.tiles.Tile;

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
	
	void renderEntity(Entity e) {
		Coordinate lastCoord = e.getLastCoord();
		if(lastCoord!=null) {
			Tile t = floor.getFloorTiles()[lastCoord.getPosy()][lastCoord.getPosx()];
			//TODO: possible bug? what if e1 moves away from tile, e2 moves on it, and e2 is rendered before e1?
			Console.directPrintChar(t.getSymbol(), lastCoord.getPosx(), lastCoord.getPosy(), ConsoleColors.DEFAULT_CONSOLE_COLOR);
		}
		Coordinate currentCoord = e.getCoord();
		Console.directPrintChar(e.getSymbol(), currentCoord.getPosx(), currentCoord.getPosy(), ConsoleColors.DEFAULT_CONSOLE_COLOR);
	}
	
	void renderPlayer() {
		renderEntity(player);
	}
	
	public void renderEnemies(EnemyCollection enemies) {
		for (Enemy e : enemies.getEnemies()) {
			renderEntity(e);
		}
	}
}
