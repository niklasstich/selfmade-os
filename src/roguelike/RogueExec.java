package roguelike;

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
	public int execute() {
		return 0;
	}
}
