package sysutils.exec;

import sysutils.Scheduler;

class Debug extends Executable {
	static {
		ExecutableStore.addExecutableFactory(new ExecutableFactory() {
			@Override
			Executable createExecutable() {
				return new Debug();
			}
			
			@Override
			String getName() {
				return "debughalt";
			}
		});
	}
	@Override
	public int execute() {
		MAGIC.inline(0xCC);
		Scheduler.markTaskAsFinished(this);
		return -1;
	}
	
}
