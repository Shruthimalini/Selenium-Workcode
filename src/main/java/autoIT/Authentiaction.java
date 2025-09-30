package autoIT;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Authentiaction {
	
	public static void main(String[] args) {
		
		WebDriver driver=new ChromeDriver();
		driver.get("http://admin:admin@the-internet.herokuapp.com/");
		driver.manage().window().maximize();
		driver.findElement(By.linkText("Basic Auth")).click();
		
	
		
	}

}
