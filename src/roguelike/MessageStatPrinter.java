package roguelike;

import graphics.Console;

class MessageStatPrinter {
	static final int MESSAGEBAR_LINE = 21;
	static final int CHARACTER_STATS_LINE = 23;
	
	static void printMessage(String msg) {
		Console.setCursor(0, MESSAGEBAR_LINE);
		Console.print(msg);
	}
	
	static void clearMessage() {
		Console.setCursor(0, MESSAGEBAR_LINE);
		for (int i = 0; i < 80; i++) {
			Console.print(' ');
		}
	}
	
	public static void printStats() {
		Console.setCursor(0, CHARACTER_STATS_LINE);
		
	}
}
