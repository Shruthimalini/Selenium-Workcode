package seleniumpractice;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Assignment4 {
	public static void main(String[] args) {
		WebDriver driver=new ChromeDriver();
		driver.get("https://the-internet.herokuapp.com/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//a[text()='Multiple Windows']")).click();
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		Set<String> windows=driver.getWindowHandles();
		Iterator<String> id=windows.iterator();
		String parentID=id.next();
		String childID=id.next();
		driver.switchTo().window(childID);
		System.out.println(driver.findElement(By.xpath("//div[@class='example']")).getText());
		driver.switchTo().window(parentID);
		System.out.println(driver.findElement(By.xpath("//div/h3")).getText());
		
		
		
		
	

}
}