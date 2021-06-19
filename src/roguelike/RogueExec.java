package roguelike;

import graphics.Console;
import hardware.Random;
import hardware.Serial;
import hardware.keyboard.KeyboardEvent;
import roguelike.entities.Enemy;
import roguelike.entities.Player;
import roguelike.entities.Zombie;
import roguelike.tiles.FloorTile;
import roguelike.tiles.Tile;
import rte.DynamicRuntime;
import rte.SClassDesc;
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
		messages = new MessageStatPrinter();
	}
	public boolean firstRun = true;
	//game state
	private Floor currFloor;
	private Player player;
	
	//game dependencies
	private Renderer renderer;
	private MessageStatPrinter messages;
	
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
			
			//TODO: REMOVE THIS DEBUG
			//player.setGodMode(true);
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
					messages.clearMessage();
				}
			}
			if(messages.hasMessages()) {
				messages.printNextMessage();
			} else {
				messages.clearMessage();
				switch (kev.KEYCODE) {
					case Key.h: movement(Resources.DIR_LEFT); break;
					case Key.j: movement(Resources.DIR_DOWN); break;
					case Key.k: movement(Resources.DIR_UP); break;
					case Key.l: movement(Resources.DIR_RIGHT); break;
					case Key.Q: confirmQuit = true; messages.queueMessage("Press Q/q again to quit."); break;
					case Key.d: messages.queueMessage("This is a test!");
					case Key.Z: spawnZombie();
				}
			}
			if(messages.hasMessages()) {
				messages.printNextMessage();
			}
			if(player.isDead()) {
				return 0;
			}
			renderer.renderPlayer();
			renderer.renderEnemies(currFloor.getEnemies());
		}
		if(rerenderStats) {
			messages.printStats(player);
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
			rerenderStats = true;
			//TODO: handle fight
			int fightOutcome = Combat.doMeleeCombat(player, enemy, messages);
			if(fightOutcome == Combat.PLAYER_DIED) {
				player.setDead();
				deathScreen();
				return;
			}
			if(fightOutcome == Combat.ENEMY_DIED) {
				//remove the enemy from the enemy list, and rerender tile it was on
				currFloor.killEnemy(enemy);
				Tile t = currFloor.getTileAtCoordinate(newCoord);
				renderer.renderTile(t, newCoord);
				messages.queueMessage("Enemy died!");
			}
			return;
		} else {
		}
		player.move(newCoord);
	}
	
	private void spawnZombie() {
		//generate new zombie
		Zombie z = new Zombie(currFloor.getValidEnemySpawn(player.getCoord()));
		//insert it into the floor
		if(currFloor.insertEnemy(z)) Serial.print("success adding zombie!\n");
	}
	
	private void debugItems() {
		//find random tile, plant a claymore there
		Tile t = currFloor.getFloorTiles()[Random.rand(0, Resources.MAX_PLAYFIELD_HEIGHT-1)][Random.rand(0, Resources.MAX_PLAYFIELD_WIDTH-1)];
		if(DynamicRuntime.isInstance(t, (SClassDesc) MAGIC.clssDesc("FloorTile"), false)) {
			FloorTile ft = (FloorTile) t;
			
		}
	}
	
	private void deathScreen() {
		//TODO: make this nice like the original rogue death screen
		Console.clearConsole();
		Console.print("YOU DIED. RIP.\n");
		Scheduler.markTaskAsFinished(this);
	}
}
