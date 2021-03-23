package kernel;

import graphics.Console;
import graphics.ConsoleColors;

public class Kernel {
	public static void main() {
		testConsole();
	}

	public static void testConsole() {
		Console c = new Console();
		c.clearConsole();
		c.println("hallo :)");
		c.setColor(ConsoleColors.FG_BLUE, ConsoleColors.BG_GREEN, false);
		c.println();
		c.println('a');
		c.setCursor(5, 4);
		c.println("ich bin versetzt :o");
		c.print(12345);
		c.print(-1234);
		c.print(-678L);
		c.println();
		c.setColor(ConsoleColors.FG_WHITE, ConsoleColors.BG_BLACK, false);
		c.println("lueckwaerts".reverse());
		c.println(-12839L);
		c.println(4321);
		c.println();
		c.printHex((byte)0xF2);
		c.println();
		c.printHex((short)0x4F0F); //???
		c.println();
		c.printHex((int)0xFFFF2442);
		c.println();
		c.printHex((long)0x0123456789ABCDEFL);
		while(true);
	}
}
