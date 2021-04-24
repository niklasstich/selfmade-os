package sysutils.exec;

class Debug extends Executable {
	static {
		ExecutableStore.addExecutable(new Debug());
	}
	@Override
	public int execute(String[] args) {
		MAGIC.inline(0xCC);
		return -1;
	}
	
	@Override
	String getName() {
		return "debughalt";
	}
}
