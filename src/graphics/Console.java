package graphics;

//Basic console output
public class Console {
	//Prints to console
	public static void Print(String m) {
		Print(m, ConsoleColors.DEFAULT_CONSOLE_COLOR);
	}
	//TODO: Ask if there is a way to ensure only a proper ConsoleColor is inserted
	public static void Print(String m, int color) {
		//If color is invalid, just use the default color
		if (color < 0 || color > 0xFF) {
			color = ConsoleColors.DEFAULT_CONSOLE_COLOR;
		}
		for(int i=0; i<m.length(); i++){
			VideoController.WriteChar(m.charAt(i), color);
		}
	}
	public static void ClearConsole() {
		VideoController.ClearVideoMemory();
	}
}
