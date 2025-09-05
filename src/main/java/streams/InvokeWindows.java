package streams;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InvokeWindows {
	public static void main(String[] args) throws IOException {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/angularpractice/");
		driver.manage().window().maximize();
		String parentWindow = driver.getWindowHandle();
		// driver.switchTo().newWindow(WindowType.TAB);
		driver.switchTo().newWindow(WindowType.WINDOW);
		driver.get("https://rahulshettyacademy.com/");
		String newTab = driver.getWindowHandle();
		List<WebElement> courseLinks = wait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.cssSelector("a[href*='https://courses.rahulshettyacademy.com/p']")));
		String courseName = courseLinks.get(1).getText();
		driver.switchTo().window(parentWindow);
		WebElement name = driver.findElement(By.cssSelector("[name='name']"));
		name.sendKeys(courseName);
		File src = name.getScreenshotAs(OutputType.FILE);
		String timestamp = String.valueOf(System.currentTimeMillis());
		File dest = new File("C:\\Users\\MaliniR\\Pictures\\Screenshots\\webelement" + timestamp + ".png");
		FileUtils.copyFile(src, dest);

	}
}