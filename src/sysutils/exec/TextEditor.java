package sysutils.exec;


//VIM - Vi IMpaired
public class TextEditor extends Executable {
	//region constructor
	static {
		ExecutableStore.addExecutableFactory(new ExecutableFactory() {
			@Override
			Executable createExecutable() {
				return new TextEditor();
			}
			
			@Override
			String getName() {
				return "vim";
			}
		});
	}
	//endregion
	
	//region editor state
	private final int MAX_FILE_BUFFER = 8*1024*80;
	private final int MODE_COMMAND = 0;
	private final int MODE_INSERT = 1;
	
	private int editorMode = 0;
	private char[] fileBuffer = new char[MAX_FILE_BUFFER];
	private int writePointer = 0;
	private int consoleLinePointer = 0;
	
	//endregion
	//executable stuff
	@Override
	public final boolean acceptsKeyboardInputs = true;
	
	@Override
	public int execute() {
		return 0;
	}
}
