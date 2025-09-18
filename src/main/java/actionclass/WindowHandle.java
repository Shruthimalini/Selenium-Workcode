package actionclass;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WindowHandle {
	public static void main(String[] args) {
		WebDriver driver=new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/loginpagePractise/");
		driver.manage().window().maximize();
		driver.findElement(By.cssSelector("a[class='blinkingText']")).click();
		Set<String> windows=driver.getWindowHandles();
		Iterator<String> id=windows.iterator();
		String parentID=id.next();
		String childId=id.next();
		driver.switchTo().window(childId);
		String UserName=getUserName(driver);
		driver.switchTo().window(parentID);
		driver.findElement(By.id("username")).sendKeys(UserName);
		
		
	}
	public static String getUserName(WebDriver driver) {
		String fullText=driver.findElement(By.xpath("//p[@class='im-para red']")).getText();
		System.out.println(fullText);
		String[] extract=fullText.split(" ");
		String email=extract[4].trim();
		return email;
			
	}
}