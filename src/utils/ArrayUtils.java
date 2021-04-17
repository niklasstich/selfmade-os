package utils;

public class ArrayUtils {
	public static char[] cleanBuffer(char[] buf) {
		for (int i = 0; i < buf.length; i++) {
			buf[i] = '\u0000';
		}
		return buf;
	}
	
	//gives a sub array of a, which contains low to high inclusive
	public static String[] subArray(String[] a, int low, int high) {
		int c = high - low + 1;
		String[] retval = new String[c];
		int j = low;
		for (int i = 0; i < c; i++) {
			retval[i] = a[j];
			j++;
		}
		return retval;
	}
}
