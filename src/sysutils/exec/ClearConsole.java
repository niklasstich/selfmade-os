package sysutils.exec;

import graphics.Console;

public class ClearConsole extends Executable{
	static {
		ExecutableStore.addExecutable(new ClearConsole());
	}
	@Override
	public int execute(String[] args) {
		Console.clearConsole();
		return 0;
	}
	
	@Override
	String getName() {
		return "clear";
	}
}
