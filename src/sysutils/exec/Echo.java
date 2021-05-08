package sysutils.exec;

import graphics.Console;

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
	public int execute(String[] args) {
		for (int i = 0; i < args.length; i++) {
			Console.print(args[i]);
			if(i!=args.length-1) Console.print(" ");
		}
		Console.println();
		return 0;
	}
	
}
