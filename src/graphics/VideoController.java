package graphics;

//Handles low level access to Video components
public class VideoController {
	
	//define constants for ASCII control sequences
	private static final int ASCII_NULL = 0x0;
	private static final int ASCII_BELL = 0x7;
	private static final int ASCII_BACKSPACE = 0x8;
	private static final int ASCII_HORIZONTAL_TAB = 0x9;
	private static final int ASCII_LINE_FEED = 0xA;
	private static final int ASCII_VERTICAL_TAB = 0xB;
	private static final int ASCII_FORM_FEED = 0xC;
	private static final int ASCII_CARRIAGE_RETURN = 0xD;
	private static final int ASCII_EOF = 0x1A;
	private static final int ASCII_ESCAPE = 0x1B;
	
	private static final int ASCII_SPACE = 0x20;
	
	private static final int TAB_SIZE = 4;
	
	private static int videoMemoryPosition = 0;
	private static final VideoMemory vidMem = (VideoMemory) MAGIC.cast2Struct(VideoMemory.VIDEO_MEMORY_STARTPOS);
	//Writes a VideoChar to the graphics output
	public static void HandleChar(int ascii, int color) {
		if (videoMemoryPosition < 0 || videoMemoryPosition >= 2000) videoMemoryPosition = 0;
		//TODO: support ascii_bell? that should be handled on the Console level
		switch (ascii){
			case ASCII_NULL: //nothing to do
			case ASCII_VERTICAL_TAB: //not supported
			case ASCII_BELL: //not supported
			case ASCII_EOF: //not supported
			case ASCII_ESCAPE: //no escape sequences on console
			{
				return;
			}
			case ASCII_BACKSPACE: {
				videoMemoryPosition--;
				return;
			}
			case ASCII_HORIZONTAL_TAB: {
				//go to the next tab position, every TAB_SIZE positions
				videoMemoryPosition += TAB_SIZE - (videoMemoryPosition % TAB_SIZE);
				return;
			}
			case ASCII_LINE_FEED: {
				//newline + carriage return
				CarriageReturn();
				NewLine();
				return;
			}//not supported
			case ASCII_FORM_FEED: {
				ClearVideoMemory();
				return;
			}
			case ASCII_CARRIAGE_RETURN: {
				CarriageReturn();
				return;
			}
			default: {
				if (ascii >= 0x20 && ascii < 0x7F) {
					WriteCharToMemory(ascii, color);
					return;
				}
			}
		}
	}
	//inline for performance?
	@SJC.Inline
	private static void WriteCharToMemory(int as, int cl) {
		assert vidMem != null;
		vidMem.pos[videoMemoryPosition].ascii = (byte)as;
		vidMem.pos[videoMemoryPosition++].color = (byte)cl;
	}
	
	public static void ClearVideoMemory() {
		videoMemoryPosition = 0;
		assert vidMem != null;
		while(videoMemoryPosition<VideoMemory.VIDEO_MEMORY_LENGTH) {
			vidMem.pos[videoMemoryPosition].ascii = (byte)ASCII_SPACE;
			vidMem.pos[videoMemoryPosition++].color = (byte)ConsoleColors.DEFAULT_CONSOLE_COLOR;
		}
		videoMemoryPosition = 0;
	}
	
	public static void CarriageReturn() {
		videoMemoryPosition -= videoMemoryPosition % VideoMemory.VIDEO_MEMORY_COLUMNS;
	}
	
	public static void NewLine() {
		videoMemoryPosition +=VideoMemory.VIDEO_MEMORY_COLUMNS;
	}
	
	protected static void UpdateCursor() {
		//current pos is videoMemoryPosition+1
		MAGIC.wIOs8(0x3D4, (byte)0x0F);
		MAGIC.wIOs8(0x3D5, (byte)(videoMemoryPosition&0xFF));
		MAGIC.wIOs8(0x3D4, (byte)0x0E);
		MAGIC.wIOs8(0x3D5, (byte)((videoMemoryPosition>>8)&0xFF));
	}
	
	protected static void DisableCursor() {
		MAGIC.wIOs8(0x3D4, (byte)0x0A);
		MAGIC.wIOs8(0x3D5, (byte)0x20);
	}
}
