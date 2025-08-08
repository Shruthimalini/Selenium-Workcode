package actionclass;

import java.time.Duration;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

public class MouseOver {
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");
	    driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		try {
		WebElement continueBtn = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
		continueBtn.click();
		System.out.println("Handled 'Continue shopping' popup.");}
		catch (TimeoutException e) {
            System.out.println("No 'Continue shopping' popup appeared.");
        }
		
		
		// driver.findElement(By.cssSelector("button[type='submit']")).click();
		WebElement search = driver.findElement(By.xpath("//input[@type='text']"));
		WebElement accountList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='nav-link-accountList']")));
		Actions act = new Actions(driver);
		act.moveToElement(accountList).contextClick().build().perform();
		act.moveToElement(search).click().keyDown(Keys.SHIFT).sendKeys("hello").doubleClick().build().perform();

	}

}
