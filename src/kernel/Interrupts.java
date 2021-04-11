package kernel;

import graphics.Console;
import graphics.ConsoleColors;

@SuppressWarnings("StatementWithEmptyBody")
public class Interrupts {
	//segment start
	private static final int IDT_START = 0x07E00;
	private final static int MASTER = 0x20, SLAVE = 0xA0;
	
	public static void prepareInterrupts() {
		int tableLimit = 8*48-1;
		writeIDTR();
		
		createTableEntry(0, getHandlerAddress(MAGIC.mthdOff("Interrupts", "divHandler")));
		createTableEntry(1, getHandlerAddress(MAGIC.mthdOff("Interrupts", "debugHandler")));
		createTableEntry(2, getHandlerAddress(MAGIC.mthdOff("Interrupts", "nmiHandler")));
		createTableEntry(3, getHandlerAddress(MAGIC.mthdOff("Interrupts", "breakpointHandler")));
		createTableEntry(4, getHandlerAddress(MAGIC.mthdOff("Interrupts", "intoHandler")));
		createTableEntry(5, getHandlerAddress(MAGIC.mthdOff("Interrupts", "indexOutOfRangeHandler")));
		createTableEntry(6, getHandlerAddress(MAGIC.mthdOff("Interrupts", "invalidOpcodeHandler")));
		createTableEntry(7, getHandlerAddress(MAGIC.mthdOff("Interrupts", "noopHandler")));
		createTableEntry(8, getHandlerAddress(MAGIC.mthdOff("Interrupts", "doubleFaultHandler")));
		for (int j = 9; j < 13; j++) {
			createTableEntry(j, getHandlerAddress(MAGIC.mthdOff("Interrupts", "noopHandler")));
		}
		createTableEntry(13, getHandlerAddress(MAGIC.mthdOff("Interrupts", "gpeHandler")));
		createTableEntry(14, getHandlerAddress(MAGIC.mthdOff("Interrupts", "pageFaultHandler")));
		for (int j = 15; j < 32; j++) {
			createTableEntry(j, getHandlerAddress(MAGIC.mthdOff("Interrupts", "noopHandler")));
		}
		createTableEntry(32, getHandlerAddress(MAGIC.mthdOff("Interrupts", "timerHandler")));
		createTableEntry(33, getHandlerAddress(MAGIC.mthdOff("Interrupts", "keyboardHandler")));
		for (int j = 34; j < 48; j++) {
			createTableEntry(j, getHandlerAddress(MAGIC.mthdOff("Interrupts", "noopHandler")));
		}
		
		initPic();
	}
	//region IDT
	public static int getHandlerAddress(int handlerOffset) {
		return MAGIC.rMem32(MAGIC.cast2Ref(MAGIC.clssDesc("Interrupts")) + handlerOffset) + MAGIC.getCodeOff();
	}
	
	protected static void writeIDTR() {
		//8 bytes per handler, 48 handlers, first byte is free :)
		int tableLimit = 8*48-1;
		long tmp=(((long) IDT_START)<<16)|(long)tableLimit;
		//load
		MAGIC.inline(0x0F, 0x01, 0x5D);
		MAGIC.inlineOffset(1, tmp);
	}
	
	private static void createTableEntry(int i, int handler) {
		//low 16 bit offset
		MAGIC.wMem16(Interrupts.IDT_START+i*8, (short) (handler&0x0000FFFF));
		//segment address
		MAGIC.wMem16(Interrupts.IDT_START+i*8+2, (short) 8);
		//options
		MAGIC.wMem16(Interrupts.IDT_START+i*8+4, (short) 0x8E00);
		//high 16 bit offset
		MAGIC.wMem16(Interrupts.IDT_START+i*8+6, (short) ((handler&0xFFFF0000)>>16));
		
	}
	//endregion
	
	//region PIC
	public static void initPic() {
		programmChip(MASTER, 0x20, 0x04); //init offset and slave config of master
		programmChip(SLAVE, 0x28, 0x02); //init offset and slave config of slave
	}
	private static void programmChip(int port, int offset, int icw3) {
		MAGIC.wIOs8(port++, (byte)0x11); // ICW1
		MAGIC.wIOs8(port, (byte)offset); // ICW2
		MAGIC.wIOs8(port, (byte)icw3); // ICW3
		MAGIC.wIOs8(port, (byte)0x01); // ICW4
	}
	
	
	//endregion
	
	@SJC.Interrupt
	private static void noopHandler() {
		return;
	}
	
	//region CPU Interrupts
		//hex 00 Divide Error
		@SJC.Interrupt
		private static void divHandler() {
			Console.debug("div by 0");
			while (true);
		}
		//hex 01 Debug Exception
		@SJC.Interrupt
		private static void debugHandler() {
			Console.debug("debug");
		}
		//hex 02 Non maskable interrupt
		@SJC.Interrupt
		private static void nmiHandler() {
			Console.debug("non maskable interrupt");
			while (true);
		}
		//hex 03 Breakpoint
		@SJC.Interrupt
		private static void breakpointHandler() {
			Console.debug("breakpoint");
			while (true);
		}
		//hex 04 INTO (overflow)
		@SJC.Interrupt
		private static void intoHandler() {
			Console.debug("INTO");
			while (true);
		}
		//hex 05 index out of range
		@SJC.Interrupt
		private static void indexOutOfRangeHandler() {
			Console.debug("i out of range");
			while (true);
		}
		//hex 06 invalid opcode
		@SJC.Interrupt
		private static void invalidOpcodeHandler() {
			Console.debug("invalid opcode");
			while (true);
		}
		//hex 07 reserved
		//hex 08 double fault (handler exception)
		@SJC.Interrupt
		private static void doubleFaultHandler() {
			Console.debug("double fault");
			while (true);
		}
		//hex 09-0C reserved
		//hex 0D general protection error (memory protection)
		@SJC.Interrupt
		private static void gpeHandler() {
			Console.debug("memory protection");
			while (true);
		}
		//hex 0E page fault
		@SJC.Interrupt
		private static void pageFaultHandler() {
			Console.debug("page fault");
			while (true);
		}
		//endregion
	
	//region HARDWARE INTERRUPTS
	//hex 0F-1F reserved
	//hex 20 - IRQ0 Timer
	@SJC.Interrupt
	private static void timerHandler() {
		//TODO: respond to PIC, handle timer in time class
		Time.increaseSystemTime(55);
		MAGIC.wIOs8(MASTER, (byte)0x20);
	}
	//hex 21 - IRQ1 Keyboard
	@SJC.Interrupt
	private static void keyboardHandler() {
		//TODO: P4 implement keyboard class
		Console.debug("keyboard");
		MAGIC.wIOs8(MASTER, (byte)0x20);
	}
	//hex 22-2F - IRQ2-IRQ15 other devices
	//endregion
}
