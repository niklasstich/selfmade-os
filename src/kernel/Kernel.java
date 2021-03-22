package kernel;

import graphics.Console;
import graphics.ConsoleColors;

public class Kernel {
	public static void main() {
		Console.ClearConsole();
		Console.Print("Hello World", ConsoleColors.BG_BLUE | ConsoleColors.FG_LIGHTGREEN);
		Console.Print("\nHello World, aber in der naechsten Zeile\n");
		Console.Print("Mich siehst du nie!");
		Console.Print("\rHier gibts nichts zu sehen, was solls hier zu sehen geben?\n");
		Console c = new Console();
		c.Test();
		c.SetTest("hmmmmmmmm\n");
		c.Test();
		Console d = new Console();
		d.Test();
		while(true);
	}
}
