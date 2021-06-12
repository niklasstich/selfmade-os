package roguelike;

import graphics.Console;
import roguelike.entities.Player;

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
	
	public static void printStats(Player p) {
		Console.setCursor(0, CHARACTER_STATS_LINE);
		//craft string first for performance reasons
		StringBuilder sb = new StringBuilder("  Health: ");
		sb.append(p.getHealth());
		sb.append("/");
		sb.append(p.getMaxHealth());
		sb.append(" Stats: ");
		sb.append(p.getStrength());
		sb.append("Str/");
		sb.append(p.getIntelligence());
		sb.append("Int/");
		sb.append(p.getDefense());
		sb.append("Def");
		Console.print(sb.getString());
	}
}
