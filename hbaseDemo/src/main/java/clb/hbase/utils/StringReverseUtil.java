package clb.hbase.utils;

public class StringReverseUtil {

	/**
	 * 对字符串进行反转
	 */
	public static String reverse(String s) {
		char ch[] = s.toCharArray();
		int start = 0;
		int end = ch.length - 1;
		char temp;
		while (start < end) {
			temp = ch[start];
			ch[start] = ch[end];
			ch[end] = temp;
			start++;
			end--;
		}
		String s1 = String.copyValueOf(ch);
		return s1;
	}

	public static void main(String[] args) {

		String str = "customer1000611";
		System.out.println(str);
		System.out.println(reverse(str));
	}

}
