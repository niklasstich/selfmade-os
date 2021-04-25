package kernel;

import graphics.Console;
import graphics.ConsoleColors;
import graphics.VideoController;
import graphics.VideoMemory;

public class ErrorScreen {
	//9 registers with 4 bytes
	private static final int EIP_OFFSET = 9*4;
	private static final int STACK_BEGINNING = 0x9BFFC;
	public static void BreakpointScreen(int ebp) {
		FillScreen();
		Console.print("Breakpoint reached. Stackframe:\n");
		//load previous ebp and eip
		int eip = MAGIC.rMem32(ebp+EIP_OFFSET);
		ebp = MAGIC.rMem32(ebp);
		do {
			//print stackframe
			Console.print("ebp:"); Console.printHex(ebp);
			Console.print(", eip:"); Console.printHex(eip);
			Console.print('\n');
			ebp = MAGIC.rMem32(ebp);
			eip = MAGIC.rMem32(ebp + 4);
			
		} while (ebp <= STACK_BEGINNING && ebp > 0);
	}
	
	//fills the entire screen with a red color
	private static void FillScreen() {
		Console.setColor(ConsoleColors.FG_BLACK, ConsoleColors.BG_RED, false);
		for (int i = 0; i < VideoMemory.VIDEO_MEMORY_COLUMNS * VideoMemory.VIDEO_MEMORY_ROWS; i++) {
			Console.print(' ');
		}
		Console.setCursor(0,0);
	}
}