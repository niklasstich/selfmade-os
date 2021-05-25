package sysutils.exec;


import hardware.Serial;

//forces a page fault by accessing first or last page
class FaultMyPage extends Executable {
	static {
		ExecutableStore.addExecutableFactory(new ExecutableFactory() {
			@Override
			Executable createExecutable() {
				return new FaultMyPage();
			}
			
			@Override
			String getName() {
				return "faultme";
			}
		});
	}
	@Override
	public int execute() {
		//for last page
		int addr = 0x0;
		if(args.length>0 && args[0].equals("-l")){
			addr = 0xFFFFFFFF;
			Serial.print('l');
		}
		Object o = MAGIC.cast2Obj(addr);
		o = o._r_next;
		return 0;
	}
}