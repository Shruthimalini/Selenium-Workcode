package javabasic;

public class JavaBrushUp3 {
	public static void main(String[] args) {

		// String Literal
		String s = "Selenium";
		String s1 = "WebDriver";

		String str = new String("Malini");
		String str1 = new String("TestNG");
		String str2 = new String("Selenium is open source Automation Tool");

		String[] splitString = str2.split(" ");
		System.out.println(splitString[4]);
		System.out.println(splitString[5].trim());

	}

}