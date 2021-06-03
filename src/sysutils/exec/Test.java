package sysutils.exec;

import graphics.Console;
import sysutils.Scheduler;

public class Test extends Executable{
	static {
		ExecutableStore.addExecutableFactory(new ExecutableFactory() {
			@Override
			public Executable createExecutable() {
				return new Test();
			}
			
			@Override
			public String getName() {
				return "TEST";
			}
		});
	}
	@Override
	public int execute() {
		String test = "\n123\n4567\n89";
		test = test.removeNewlines();
		Console.println(test);
		Scheduler.markTaskAsFinished(this);
		return 0;
	}
}
