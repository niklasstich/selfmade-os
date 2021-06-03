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
	
	//game state
	private Floor currFloor;
	private Player player;
	
	@Override
	public int execute() {
		currFloor = new Floor(Resources.returnBasicFloor());
		currFloor.renderFloor();
		while (true);
		Scheduler.markTaskAsFinished(this);
		return 0;
	}
}
