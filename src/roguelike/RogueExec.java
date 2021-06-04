package roguelike;

import sysutils.Scheduler;
import sysutils.exec.Executable;
import sysutils.exec.ExecutableFactory;
import sysutils.exec.ExecutableStore;

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
	@Override
	public final boolean acceptsKeyboardInputs = true;
	//game state
	private Floor currFloor;
	private Player player;
	
	@Override
	public int execute() {
		//get a new floor if there is no floor currently
		//TODO: generate the floor randomly
		if(currFloor == null) {
			currFloor = new Floor(Resources.returnBasicFloor());
			currFloor.renderFloor();
		}
		//if player isn't initialized, create new object with random coords on a valid Tile
		//Coordinate spawn = currFloor.getValidSpawn();
		Scheduler.markTaskAsFinished(this);
		return 0;
	}
}
