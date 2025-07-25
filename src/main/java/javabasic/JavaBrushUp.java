package javabasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaBrushUp {
	public static void main(String[] args) {
		
      int[] b= {1,2,3,4,5,6,7,8,9};
      
      for(int i=0;i<b.length;i++){  
    	  
    	  if(b[i]%2==0)
    	  {
    		  System.out.println(b[i]);
    		  
    	  }
    	  else {
    		  System.out.println(b[i]+" is not even");
    	  }
      }
      ArrayList<String> arr=new ArrayList<String>();
      arr.add("Selenium");
      arr.add("JUnit");
      arr.add("TestNG");
      arr.add("WebDriver");
      arr.add("ChromeDriver");
      System.out.println(arr.get(1));
      
      for(int j=0;j<arr.size();j++) {
    	  
    	  System.out.println(arr.get(j)); 
      }
      for(String item:arr) {
    	  System.out.println(item); 
      }
      System.out.println(arr.contains("TestNG")); 
      //Array to ArrayList
      String[] name= {"Selenium","Automation","WebDriver","TestNG"};
      List<String> namearray=Arrays.asList(name);
      namearray.contains("Selenium");
}}
