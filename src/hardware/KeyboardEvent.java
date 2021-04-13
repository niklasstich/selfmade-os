package hardware;

public class KeyboardEvent {
	//MODIFIER
	final boolean ALT, SHIFT, CONTROL;
	//TOGGLES
	final boolean CAPSLOCK, SCROLLLOCK, NUMLOCK;
	final int KEYCODE;
	
	KeyboardEvent(boolean alt, boolean shift, boolean control, boolean capslock, boolean scrolllock,
	              boolean numlock, int keycode) {
		ALT = alt;
		SHIFT = shift;
		CONTROL = control;
		CAPSLOCK = capslock;
		SCROLLLOCK = scrolllock;
		NUMLOCK = numlock;
		KEYCODE = keycode;
	}
}
