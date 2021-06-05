package roguelike;

import graphics.Console;
import hardware.Serial;
import hardware.keyboard.KeyboardEvent;
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
	void init() {
		Console.disableCursor();
		firstRun = false;
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
				case Key.h: player.move(Resources.DIR_LEFT, currFloor); break;
				case Key.j: player.move(Resources.DIR_DOWN, currFloor); break;
				case Key.k: player.move(Resources.DIR_UP, currFloor); break;
				case Key.l: player.move(Resources.DIR_RIGHT, currFloor); break;
				case Key.Q: confirmQuit = true; MessageStatPrinter.printMessage("Press Q/q again to quit."); break;
				case Key.d: MessageStatPrinter.printMessage("This is a test!");
			}
			renderer.renderPlayer();
			MessageStatPrinter.printStats();
		}
		return 0;
	}
}
