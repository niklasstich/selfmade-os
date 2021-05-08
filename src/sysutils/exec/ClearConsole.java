package sysutils.exec;

import graphics.Console;

public class ClearConsole extends Executable{
	static {
		ExecutableStore.addExecutableFactory(new ExecutableFactory() {
			@Override
			Executable createExecutable() {
				return new ClearConsole();
			}
			
			@Override
			String getName() {
				return "clear";
			}
		});
	}
	@Override
	public int execute(String[] args) {
		Console.clearConsole();
		return 0;
	}
	
}
