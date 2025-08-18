package javabasic;

import java.util.Arrays;

public class DataTypes {
	public static void main(String[] args) {
		int a = 10;
		String str = "Malini";
		char c = 'A';
		double d = 5.99;
		boolean mycard = true;
		System.out.println(a);
		System.out.println(str);
		System.out.println(c);
		System.out.println(d);
		System.out.println(mycard);

		int[] arr = new int[3];
		arr[0] = 1;
		arr[1] = 4;
		arr[2] = 8;
		System.out.println(Arrays.toString(arr));

		int[] b = { 1, 4, 6, 5, 8, 9 };
		System.out.println(b[3]);
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}

		String[] strarr = { "Webdriver", "TestNG", "Selenium" };
		for (int i = 0; i < strarr.length; i++) {
			System.out.println(strarr[i]);
		}
		for (String value : strarr) {

			System.out.println(value);
		}

	}

}
