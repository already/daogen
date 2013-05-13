package com.kifanle.daogen.util;

public class CharUtil {
	/**
	 * 将字符转换成大写，如a->A
	 * @param c
	 * @return
	 */
	public static char toUpperCase(char c) {
		if (c < 97 || c > 122)
			return c;
		else
			return (char) (c - 32);
	}

	/**
	 * 将字符转换成小写，如A->a
	 * @param c
	 * @return
	 */
	public static char toLowerCase(char c) {
		if (c < 65 || c > 90)
			return c;
		else
			return (char) (c + 32);
	}
	
	public static boolean isLowerCase(char c){
		return (c>='a'&&c<='z');
	}
	
	public static boolean isUpperCase(char c){
		return (c>='A'&&c<='Z');
	}
	
	public static String getLowerCaseNames(String names){
		StringBuilder sb = new StringBuilder();
		char[] cc = names.toCharArray();
		sb.append(toLowerCase(cc[0]));
		for(int i=1;i<cc.length;i++){
			if(isLowerCase(cc[i-1])&&isUpperCase(cc[i])){
				sb.append("_"+toLowerCase(cc[i]));
			}else{
				sb.append(toLowerCase(cc[i]));
			}
		};
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(isLowerCase('C'));
		String s ="assAsddAddd";
		String s1 ="UserInfo";
		System.out.println(getLowerCaseNames(s1));
		String s2 ="hserInfo";
		System.out.println(getLowerCaseNames(s2));
	}
}
