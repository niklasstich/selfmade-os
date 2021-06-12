package roguelike;

import graphics.Console;
import hardware.Serial;
import hardware.keyboard.KeyboardEvent;
import roguelike.entities.Enemy;
import roguelike.entities.Player;
import roguelike.entities.Zombie;
import sysutils.Scheduler;
import sysutils.exec.Executable;
import sysutils.exec.ExecutableFactory;
import sysutils.exec.ExecutableStore;

import hardware.keyboard.Key;

public class RogueExec extends Executable {
	static {
		ExecutableStore.addExecutableFactory(new ExecutableFactory() {
			
			@Override
			public Executable createExecutable() {
				return new RogueExec();
			}
			
			@Override
			public String getName() {
				return "rogue";
			}
		});
	}
	RogueExec() {
		acceptsKeyboardInputs = true;
	}
	public boolean firstRun = true;
	//game state
	private Floor currFloor;
	private Player player;
	private Renderer renderer;
	
	//confirm quit
	private boolean confirmQuit = false;
	//save time not rerendering stats every time
	private boolean rerenderStats = true;
	void init() {
		Console.disableCursor();
		firstRun = false;
		//TODO: remove this debug
		
	}
	
	void cleanup() {
		Console.enableCursor();
		Console.clearConsole();
	}
	
	@Override
	public int execute() {
		//get a new floor if there is no floor currently
		//TODO: generate the floor randomly
		if(currFloor == null) {
			currFloor = new Floor(Resources.returnBasicFloor());
		}
		//if player isn't initialized, create new object with random coords on a valid Tile
		if(player==null) {
			Coordinate spawn = currFloor.getValidSpawn();
			player = new Player(spawn);
		}
		//initialize renderer
		if(renderer==null) {
			renderer = new Renderer(currFloor, player);
			renderer.renderFloor();
			renderer.renderPlayer();
		}
		//initialize everything we need to initialize if its the first execution
		if(firstRun) {
			//display stats
			//initialize empty message?
			init();
		}
		while (buffer.canRead()) {
			KeyboardEvent kev = buffer.readEvent();
			if(confirmQuit) {
				if(kev.KEYCODE==Key.q||kev.KEYCODE==Key.Q) {
					cleanup();
					Scheduler.markTaskAsFinished(this);
					return 0;
				} else {
					confirmQuit = false;
					MessageStatPrinter.clearMessage();
				}
			}
			switch (kev.KEYCODE) {
				case Key.h: movement(Resources.DIR_LEFT); break;
				case Key.j: movement(Resources.DIR_DOWN); break;
				case Key.k: movement(Resources.DIR_UP); break;
				case Key.l: movement(Resources.DIR_RIGHT); break;
				case Key.Q: confirmQuit = true; MessageStatPrinter.printMessage("Press Q/q again to quit."); break;
				case Key.d: MessageStatPrinter.printMessage("This is a test!");
				case Key.Z: spawnZombie();
			}
			renderer.renderPlayer();
			renderer.renderEnemies(currFloor.getEnemies());
		}
		if(rerenderStats) {
			MessageStatPrinter.printStats(player);
			rerenderStats = false;
		}
		return 0;
	}
	
	//check if movement is obstructed by tile or enemy, action accordingly
	private void movement(int direction) {
		Coordinate coord, newCoord;
		coord = player.getCoord();
		switch (direction) {
			case Resources.DIR_UP: newCoord = new Coordinate(coord.getPosx(), coord.getPosy()-1); break;
			case Resources.DIR_DOWN: newCoord = new Coordinate(coord.getPosx(), coord.getPosy()+1); break;
			case Resources.DIR_LEFT: newCoord = new Coordinate(coord.getPosx()-1, coord.getPosy()); break;
			case Resources.DIR_RIGHT: newCoord = new Coordinate(coord.getPosx()+1, coord.getPosy()); break;
			default: return;
		}
		//check if passable
		if(!currFloor.isCoordinatePassable(newCoord)) return;
		//check if enemy on tile
		Enemy enemy = currFloor.getEnemyAtCoordinate(newCoord);
		if(enemy!=null) {
			//TODO: handle fight
			Serial.print("FIGHT!\n");
			return;
		} else {
			Serial.print("no fight :(\n");
		}
		player.move(newCoord);
	}
	
	private void spawnZombie() {
		//generate new zombie
		Zombie z = new Zombie(currFloor.getValidEnemySpawn(player.getCoord()));
		//insert it into the floor
		if(currFloor.insertEnemy(z)) Serial.print("success adding zombie!\n");
	}
}
