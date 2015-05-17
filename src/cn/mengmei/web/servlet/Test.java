package cn.mengmei.web.servlet;

public class Test {

	public static void main(String[] args) {
		String s = "a";
		String str = "abcba";
		if(str.contains(s)){
			str = str.replace(s, "c");
		}
		System.out.println(str);
	}

}
