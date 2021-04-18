package hardware.pci;

public class PCIDevice {
	final int devID, vendorID, status, command;
	final int baseClass, subClass, devInterface, revision, BIST, header, latency, CLG;
	
	public PCIDevice(int devID, int vendorID, int status, int command, int baseClass, int subClass, int devInterface, int revision, int bist, int header, int latency, int clg) {
		this.devID = devID;
		this.vendorID = vendorID;
		this.status = status;
		this.command = command;
		this.baseClass = baseClass;
		this.subClass = subClass;
		this.devInterface = devInterface;
		this.revision = revision;
		this.BIST = bist;
		this.header = header;
		this.latency = latency;
		this.CLG = clg;
	}
}
