package graphics;

public class VideoMemory extends STRUCT {
	static final int VIDEO_MEMORY_LENGTH = 2000;
	static final int VIDEO_MEMORY_STARTPOS = 0xB8000;
	static final int VIDEO_MEMORY_ENDPOS = 0xB8000 + VIDEO_MEMORY_LENGTH-1;
	@SJC(count = 2000)
	VideoChar[] pos;
}


