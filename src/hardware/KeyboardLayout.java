package hardware;

public abstract class KeyboardLayout {
	public abstract int translatePhysToLogicalKey(int physKey, boolean shift, boolean caps, boolean alt);
	
}
