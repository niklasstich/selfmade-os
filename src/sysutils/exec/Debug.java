package sysutils.exec;

class Debug extends Executable {
	static {
		ExecutableStore.addExecutableFactory(new ExecutableFactory() {
			@Override
			Executable createExecutable() {
				return new Debug();
			}
			
			@Override
			String getName() {
				return "debughalt";
			}
		});
	}
	@Override
	public int execute(String[] args) {
		MAGIC.inline(0xCC);
		return -1;
	}
	
}
