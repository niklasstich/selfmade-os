package hardware;


import graphics.Console;

public class Keyboard {
	private static ByteRingBuffer buffer;
	static {
		buffer = new ByteRingBuffer();
	}
	//interface to interrupt
	public static void storeKeycode() {
		byte b = MAGIC.rIOs8(0x60);
		if (b >= ((byte)0xE2)) {
			return;
		}
		buffer.writeByte(b);
		Console c = new Console();
		c.printHex(b);
		c.println();
	}
	
	
	//interface to loop
	//TODO: TBD P4b
}
