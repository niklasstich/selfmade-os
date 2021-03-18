package graphics;

//Defines for colors used on the console
@SuppressWarnings("unused")
class ConsoleColors {
	//BIOS uses one byte for coloring console output
	//Bit 7 is used for blinking output
	//Bits 6-4 make up the background color
	//Bit 3 determines whether the color should be light (except for grey, yellow and white)
	//Bits 2-0 define the foreground color
	
	//Foreground colors
	static final int FG_BLACK =                0x00;
	static final int FG_BLUE =                 0x01;
	static final int FG_GREEN =                0x02;
	static final int FG_CYAN =                 0x03;
	static final int FG_RED =                  0x04;
	static final int FG_MAGENTA =              0x05;
	static final int FG_BROWN =                0x06;
	static final int FG_LIGHTGREY =            0x07;
	
	//Light foreground colors
	private static final int LIGHT_OFFSET =    0x08;
	static final int FG_DARKGREY =             FG_BLACK | LIGHT_OFFSET;
	static final int FG_LIGHTBLUE =            FG_BLUE | LIGHT_OFFSET;
	static final int FG_LIGHTGREEN =           FG_GREEN | LIGHT_OFFSET;
	static final int FG_LIGHTCYAN =            FG_CYAN | LIGHT_OFFSET;
	static final int FG_LIGHTRED =             FG_RED | LIGHT_OFFSET;
	static final int FG_LIGHTMAGENTA =         FG_MAGENTA | LIGHT_OFFSET;
	static final int FG_YELLOW =               0x0E;
	static final int FG_WHITE =                0x0F;
	//Background colors
	private static final int BG_OFFSET =       4;
	static final int BG_BLACK =                FG_BLACK << BG_OFFSET;
	static final int BG_BLUE =                 FG_BLUE << BG_OFFSET;
	static final int BG_GREEN =                FG_GREEN << BG_OFFSET;
	static final int BG_CYAN =                 FG_CYAN << BG_OFFSET;
	static final int BG_RED =                  FG_RED << BG_OFFSET;
	static final int BG_MAGENTA =              FG_MAGENTA << BG_OFFSET;
	static final int BG_BROWN =                FG_BROWN << BG_OFFSET;
	static final int BG_LIGHTGREY =            FG_LIGHTGREY << BG_OFFSET;
	//Blinking output
	static final int BLINKING =                0x80;
	static final int DEFAULT_CONSOLE_COLOR = ConsoleColors.BG_BLACK | ConsoleColors.FG_WHITE;
}