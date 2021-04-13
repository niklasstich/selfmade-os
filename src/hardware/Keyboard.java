package hardware;


import graphics.Console;

public class Keyboard {
	private static ByteRingBuffer buffer;
	//modifier pressed?
	private static boolean SHIFT, CTRL, ALT;
	//toggles
	private static boolean CAPSLOCK, SCROLLLOCK, NUMLOCK;
	static {
		buffer = new ByteRingBuffer();
	}
	//interface to interrupt
	public static void storeKeycode() {
		byte b = MAGIC.rIOs8(0x60);
		if ((b&0xFF) >= (0xE2)) {
			return;
		}
		buffer.writeByte(b);
		Console.debugHex(b);
	}
	
	//interface to loop
	//TODO: TBD P4b
	
	//processes the input buffer and tokenizes it into the output buffer for getNextKeyboardEvent()
	public static void processInputBuffer() {
		while(buffer.canRead()) {
			byte b = buffer.readByte();
			if ((b&0xFF)==0xE0) { //sequence, read one more byte
				b = buffer.readByte();
				switch (b&0xFF) {
				
				}
			} else if ((b&0xFF)==0xE1) { //sequence, read two more bytes
			
			} else {
			
			}
		}
	}
	
	//returns the next KeyboardEvent
	public static KeyboardEvent getNextKeyboardEvent() {
	
	}
	
	//returns whether or not there is a new KeyboardEvent available
	public static boolean eventAvailable() {
	
	}
	
	
}
