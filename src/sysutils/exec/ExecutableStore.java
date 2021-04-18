package sysutils.exec;

import graphics.Console;
import graphics.ConsoleColors;
import utils.ASCIIControlSequences;

//executablestore is itself an executable, because it can list all executables to the console
public class ExecutableStore extends Executable {
	static {
		//first initialize the array, otherwise we shit the bed
		executables = new Executable[1024];
		addExecutable(new ExecutableStore());
	}
	
	private static Executable[] executables;
	private static int insertionIndex;
	
	
	public static void addExecutable(Executable ex) {
		executables[insertionIndex++] = ex;
	}
	
	public static Executable fetchExecutable(String name) {
		for (int i = 0; i < insertionIndex; i++) {
			if(executables[i].getName().equals(name)) {
				return executables[i];
			}
		}
		return null;
	}
	
	@Override
	public int execute(String[] args) {
		Console.setColor(ConsoleColors.FG_GREEN, ConsoleColors.BG_BLACK, false);
		for (int i = 0; i < insertionIndex; i++) {
			Console.print(executables[i].getName().concat(" "));
		}
		Console.print('\n');
		Console.setDefaultColor();
		return 0;
	}
	
	@Override
	String getName() {
		return "lsexec";
	}
}
