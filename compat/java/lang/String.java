package java.lang;

import utils.ArrayUtils;

public class String {
	private char[] value;
	private int count;
	public String(char[] value) {
		this.value = value;
		this.count = value.length;
	}
	public static String compactString(char[] value) {
		int realLen = value.length - 1;
		while (true) {
			if (value[realLen] == 0) realLen--; //last char is null byte, so cut it off
			else break;
		}
		char[] compactValue = new char[realLen+1];
		for (int i = 0; i <= realLen; i++) {
			compactValue[i] = value[i];
		}
		return new String(compactValue);
	}
	@SJC.Inline
	public int length() {
		return count;
	}
	@SJC.Inline
	public char charAt(int i) {
		return value[i];
	}
	@SJC.Inline
	public char[] toCharArray() {
		return value;
	}
	@SJC.Inline
	public String reverse() {
		char[] rev = new char[count];
		for (int i=0,j=count-1;i<=j;i++,j--) {
			rev[i] = value[j];
			rev[j] = value[i];
		}
		return new String(rev);
	}
	@SJC.Inline
	public String concat(String s) {
		char[] buf = new char[s.count+this.count];
		//copy over this string first
		int index = 0;
		for(int i = 0; i<this.count; i++) {
			buf[index++] = this.value[i];
		}
		//copy over s after
		for (int i = 0; i<s.count; i++) {
			buf[index++] = (char) s.value[i];
		}
		return new String(buf);
	}
	
	public boolean equals(String s) {
		if (this.length()!=s.length()) return false;
		for (int i = 0; i < s.length(); i++) {
			if (this.charAt(i)!=s.charAt(i)) return false;
		}
		return true;
	}
	
	//splits a string into up to 128 substrings according to splitList
	public String[] split(char delimiter) {
		String[] splitStrings = new String[128];
		int sIn = 0;
		char[] currBuffer = new char[1024];
		int cIn = 0;
		for (int i = 0; i < this.length(); i++) {
			char c = this.charAt(i);
			if (c==delimiter && cIn > 0) {
				splitStrings[sIn] = String.compactString(currBuffer);
				sIn++;
				currBuffer = ArrayUtils.cleanBuffer(currBuffer);
				cIn = 0;
			} else {
				currBuffer[cIn] = c;
				cIn++;
			}
		}
		//gotta put the remainder of the buffer in a string after being done going over every char
		splitStrings[sIn] = String.compactString(currBuffer);
		sIn++;
		String[] retval = new String[sIn];
		for (int i = 0; i < sIn; i++) {
			retval[i] = splitStrings[i];
		}
		return retval;
	}
	
	
}