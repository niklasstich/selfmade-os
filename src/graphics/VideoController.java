package graphics;

public class VideoController {
	private static int videoMemoryPosition = 0;
	private static final VideoMemory vidMem = (VideoMemory) MAGIC.cast2Struct(VideoMemory.VIDEO_MEMORY_STARTPOS);
	//Writes a VideoChar to the graphics output
	public static void WriteChar(int ascii, int color) {
		if (videoMemoryPosition < 0 || videoMemoryPosition >= 2000) videoMemoryPosition = 0;
		assert vidMem != null;
		vidMem.pos[videoMemoryPosition].ascii = (byte)ascii;
		vidMem.pos[videoMemoryPosition++].color = (byte)color;
	}
	
	public static void ClearVideoMemory() {
		videoMemoryPosition = 0;
		assert vidMem != null;
		while(videoMemoryPosition<VideoMemory.VIDEO_MEMORY_LENGTH) {
			vidMem.pos[videoMemoryPosition].ascii = (byte)0x20;
			vidMem.pos[videoMemoryPosition++].color = (byte)ConsoleColors.DEFAULT_CONSOLE_COLOR;
		}
		videoMemoryPosition = 0;
	}
}
