package seleniumpractice;

import java.time.Duration;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Amazon {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");
		 driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			WebElement continueBtn = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
			continueBtn.click();
			System.out.println("Handled 'Continue shopping' popup.");
		} catch (TimeoutException e) {
			System.out.println("No 'Continue shopping' popup appeared.");
		}

		Actions act = new Actions(driver);
		WebElement accountList = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='nav-link-accountList']")));
		act.moveToElement(accountList).build().perform();
		driver.findElement(By.xpath("//span[text()='Sign in']")).click();
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("7339585788");
		WebElement signIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a-button-text.a-declarative")));
		signIn.click();
		WebElement indiaOption = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'India ')]")));
		indiaOption.click();
		driver.findElement(By.className("a-button-input")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[contains(@class,'a-button-inner')])[2]"))).click();
		
		System.out.println("Please enter the OTP manually in the browser, then press Enter here to continue...");
		sc.nextLine();
		driver.findElement(By.id("cvf-submit-otp-button")).click();

        
	}
}