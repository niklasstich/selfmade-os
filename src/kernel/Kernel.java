package kernel;

import graphics.Console;
import graphics.ConsoleColors;

public class Kernel {
	private static final int GDTBASE = 0x10000;
	public static void main() {
		MAGIC.doStaticInit();
		Console c = new Console();
		c.clearConsole();
		Interrupts.prepareInterrupts();
		//set interrupt flag ERST WENN PICs
		MAGIC.inline(0xFB);
		
		while(true);
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
		c.println("rueckwaerts".reverse());
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
	
	public static void biosFun() {
		BIOS.enterGraphicsMode();
		for (int i = 0xA0000; i < 0xA0000+64000; i++) {
			MAGIC.wMem8(i, (byte) (i&0x11));
		}
		Time.sleep(2000);
		BIOS.enterTextMode();
	}
}
