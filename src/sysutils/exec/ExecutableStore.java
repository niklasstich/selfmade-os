package sysutils.exec;

import graphics.Console;
import utils.ASCIIControlSequences;

//executablestore is itself an executable, because it can list all executables to the console
public class ExecutableStore extends Executable {
	static {
		addExecutable(new ExecutableStore());
	}
	
	private static Executable[] executables;
	private static int insertionIndex;
	
	static {
		executables = new Executable[1024];
	}
	
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
		Console.print(ASCIIControlSequences.LINE_FEED);
		for (int i = 0; i < insertionIndex; i++) {
			Console.print(executables[i].getName().concat(" "));
		}
		return 0;
	}
	
	@Override
	String getName() {
		return "lsexec";
	}
}
