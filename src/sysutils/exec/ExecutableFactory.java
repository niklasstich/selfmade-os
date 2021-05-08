package sysutils.exec;

public abstract class ExecutableFactory {
	abstract Executable createExecutable();
	abstract String getName();
}
