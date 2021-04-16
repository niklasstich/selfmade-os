package graphics;

import utils.TypeConv;

//Basic console output
public class Console {
	private final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	private int color = ConsoleColors.DEFAULT_CONSOLE_COLOR;
	private boolean cursor = true;
	
	public void clearConsole() {
		VideoController.clearVideoMemory();
		if(cursor)VideoController.updateCursor();
	}
	
	public void disableCursor() {
		VideoController.disableCursor();
	}
	
	//dynamische Methoden
	
	public void setColor(int fg, int bg, boolean blinking) {
		//enforce sane defaults if args are out of bounds
		if (fg < ConsoleColors.FG_BLACK || fg > ConsoleColors.FG_WHITE) {
			fg = ConsoleColors.FG_WHITE;
		}
		if (bg < ConsoleColors.BG_BLACK || bg > ConsoleColors.BG_LIGHTGREY) {
			bg = ConsoleColors.BG_BLACK;
		}
		color = fg | bg;
		if (blinking) color |= ConsoleColors.BLINKING;
	}
	
	public void setDefaultColor() {
		setColor(ConsoleColors.FG_WHITE, ConsoleColors.BG_BLACK, false);
	}
	
	public void setCursor(int newX, int newY) {
		//TODO: what shall do with out of bounds args
		VideoController.setPos(newX ,newY);
		if (cursor) VideoController.updateCursor();
	}
	
	public void print(char c) {
		VideoController.handleChar(c, color);
		if (cursor) VideoController.updateCursor();
	}
	
	public void print(int x) {
		print((long)x);
	}
	
	public void print(long x) {
		print(TypeConv.longToString(x));
		if (cursor) VideoController.updateCursor();
	}
	
	public void print(String str) {
		if (str==null) return;
		for(int i=0;i<str.length();i++) {
			print(str.charAt(i));
		}
		if (cursor) VideoController.updateCursor();
	}
	
	//https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
	//1 byte
	public void printHex(byte b) {
		char[] hexChars = new char[2];
		int v = b & 0xFF;
		hexChars[0] = HEX_ARRAY[v>>>4];
		hexChars[1] = HEX_ARRAY[v&0x0F];
		print("0x".concat(new String(hexChars)));
		if (cursor) VideoController.updateCursor();
	}
	
	//2 bytes
	public void printHex(short s) {
		printHex(TypeConv.toBytes(s));
	}
	
	//4 bytes
	public void printHex(int x) {
		printHex(TypeConv.toBytes(x));
	}
	
	public void printHex(long x) {
		printHex(TypeConv.toBytes(x));
	}
	
	public void printHex(byte[] b) {
		char[] hexChars = new char[b.length*2];
		for (int i = 0; i<b.length; i++) {
			int v = b[i] & 0xFF;
			hexChars[i*2] = HEX_ARRAY[v>>>4];
			hexChars[i*2+1] = HEX_ARRAY[v&0x0F];
		}
		print("0x".concat(new String(hexChars)));
		if (cursor) VideoController.updateCursor();
	}
	
	public void println() {
		print("\n");
		if (cursor) VideoController.updateCursor();
	}
	
	//vorgegebene Methoden
	public void println(char c) {
		print(c);
		println();
		if (cursor) VideoController.updateCursor();
	}
	
	public void println(int i) {
		print(i);
		println();
		if (cursor) VideoController.updateCursor();
	}
	
	public void println(long l) {
		print(l);
		println();
		if (cursor) VideoController.updateCursor();
	}
	
	public void println(String str) {
		print(str);
		println();
		if (cursor) VideoController.updateCursor();
	}
	
	//static debug
	public static void directPrintInt(int value, int base, int len, int x, int y, int color) {
		return;
	}
	
	public static void directPrintChar(char c, int x, int y, int cl) {
		VideoController.writeCharDirectly(c, x, y, cl);
	}
	
	public static void debug(String msg) {
		for (int i = 0; i<msg.length(); i++) {
			VideoController.writeCharDebug(msg.charAt(i), ConsoleColors.DEFAULT_CONSOLE_COLOR);
		}
	}
	
	public static void debugHex(byte b) {
		char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[2];
		int v = b & 0xFF;
		hexChars[0] = HEX_ARRAY[v>>>4];
		hexChars[1] = HEX_ARRAY[v&0x0F];
		debug("0x".concat(new String(hexChars)));
		VideoController.updateCursor();
	}
	
	//USE WITH GREAT CARE
	public static void resetConsole() {
		VideoController.clearVideoMemory();
	}
}
