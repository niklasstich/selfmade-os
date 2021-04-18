package hardware.pci;

public class PCIController {
	public static int generateAddress(byte type, byte register, byte function, byte device, byte busNum) {
		return ((0x80<<24) | (busNum&0xFF << 16) | (device&0x1F << 11) | (function&0x07 << 8) | (register&0x3F << 2) | (type&0x03));
	}
	
	public static PCIDevice getFunctionRegisters(byte bus, byte dev, byte func) {
		int reg1, reg2, reg3, reg4;
	}
}
