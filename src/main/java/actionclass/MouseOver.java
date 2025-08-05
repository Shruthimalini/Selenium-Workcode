package actionclass;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;

public class MouseOver {
	public static void main(String[] args) {
		WebDriver driver=new ChromeDriver();
		driver.get("https://www.amazon.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		//driver.findElement(By.cssSelector("button[type='submit']")).click();
		WebElement search=driver.findElement(By.xpath("//input[@type='text']"));
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(By.cssSelector("div[id='nav-link-accountList']"))).contextClick().build().perform();
		act.moveToElement(search).click().keyDown(Keys.SHIFT).sendKeys("hello").doubleClick().build().perform();
		
		
		
	}

}
