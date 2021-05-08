package sysutils.exec;

import graphics.Console;
import graphics.ConsoleColors;

//executablestore is itself an executable, because it can list all executables to the console
public class ExecutableStore extends Executable {
	static {
		//first initialize the array, otherwise we shit the bed
		execFactories = new ExecutableFactory[1024];
		addExecutableFactory(new ExecutableFactory() {
			@Override
			Executable createExecutable() {
				return new ExecutableStore();
			}
			
			@Override
			String getName() {
				return "lsexec";
			}
		});
	}
	
	private static ExecutableFactory[] execFactories;
	private static int insertionIndex;
	
	
	public static void addExecutableFactory(ExecutableFactory ex) {
		execFactories[insertionIndex++] = ex;
	}
	
	public static Executable fetchExecutable(String name) {
		for (int i = 0; i < insertionIndex; i++) {
			if(execFactories[i].getName().equals(name)) {
				return execFactories[i].createExecutable();
			}
		}
		return null;
	}
	
	@Override
	public int execute(String[] args) {
		Console.setColor(ConsoleColors.FG_GREEN, ConsoleColors.BG_BLACK, false);
		for (int i = 0; i < insertionIndex; i++) {
			Console.print(execFactories[i].getName().concat(" "));
		}
		Console.print('\n');
		Console.setDefaultColor();
		return 0;
	}
	
}
