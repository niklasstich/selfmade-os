package hardware;


public class Time {
	private static long systemTime = 0;
	
	public static long getSystemTime() {
		return systemTime;
	}
	public static void increaseSystemTime(long ms) {
		systemTime += ms;
		if(systemTime<0)
			systemTime = 0;
	}
	public static void sleep(long ms) {
		long now = systemTime;
		//noinspection StatementWithEmptyBody
		while (systemTime < now+ms);
		
	}
}
