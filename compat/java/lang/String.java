package java.lang;
public class String {
	private char[] value;
	private int count;
	public String(char[] value) {
		this.value = value;
		this.count = value.length;
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
}