package sysutils.exec;

import graphics.Console;
import hardware.Serial;
import sysutils.Scheduler;

class Echo extends Executable{
	static {
		ExecutableStore.addExecutableFactory(new ExecutableFactory() {
			@Override
			Executable createExecutable() {
				return new Echo();
			}
			
			@Override
			String getName() {
				return "echo";
			}
		});
	}
	@Override
	public int execute() {
		for (int i = 0; i < args.length; i++) {
			Console.print(args[i]);
			if(i!=args.length-1) Console.print(" ");
		}
		Console.println();
		Scheduler.markTaskAsFinished(this);
		Serial.print("bye!");
		return 0;
	}
	
}
