package miscellaneous;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Proxy;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ProxyExample {
	  public static void main(String[] args) throws InterruptedException {
		  ChromeOptions options = new ChromeOptions();
		  
		  String proxyAddress = "47.90.205.231:33333";
		  Proxy proxy = new Proxy();
	        proxy.setHttpProxy(proxyAddress);
	        options.setCapability("proxy", proxy);
	        WebDriver driver = new ChromeDriver(options);
	        driver.get("https://www.whatismyipaddress.com/");
	        Thread.sleep(5000);
	        System.out.println("Page Title: " + driver.getTitle());

	       
}}