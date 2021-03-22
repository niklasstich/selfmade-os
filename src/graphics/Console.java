package graphics;

//Basic console output
public class Console {
	private String test = "mhm\n";
	
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
			VideoController.HandleChar(m.charAt(i), color);
		}
	}
	public static void ClearConsole() {
		VideoController.ClearVideoMemory();
	}
	
	private static void PrintInternal(char ascii, int cl) {
	
	}
	
	public static void DisableCursor() {
		VideoController.DisableCursor();
	}
	
	public void SetTest(String s){
		test = s;
	}
	public void Test() {
		Print(test);
	}
}
