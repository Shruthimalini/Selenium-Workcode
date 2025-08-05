package synchronisation;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitMechanism {
	public static void main(String[] args) throws InterruptedException {
		String key = "rahulshettyacademy";
		String[] itemsToAdd = { "Tomato", "Cauliflower", "Mushroom", "Onion", "Pomegranate" };
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		driver.manage().window().maximize();
		Thread.sleep(1000);
		WaitMechanism obj = new WaitMechanism();
		obj.addItems(driver, itemsToAdd);
		driver.findElement(By.className("cart-icon")).click();
		driver.findElement(By.xpath("//button[text()='PROCEED TO CHECKOUT']")).click();
		// Thread.sleep(2000);

		driver.findElement(By.cssSelector("input.promoCode")).sendKeys(key);
		driver.findElement(By.cssSelector(".promoBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.promoInfo")));
		System.out.println(driver.findElement(By.cssSelector("span.promoInfo")).getText());
		
	}

	public void addItems(WebDriver driver, String[] itemsToAdd) {

		List<WebElement> productNames = driver.findElements(By.cssSelector("h4.product-name"));
		List<String> arrayAsList = Arrays.asList(itemsToAdd);
		List<WebElement> addToCart = driver.findElements(By.xpath("//button[text()='ADD TO CART']"));
		int count = 0;

		for (int i = 0; i < productNames.size(); i++) {

			String names = productNames.get(i).getText().split("-")[0].trim();
			if (arrayAsList.contains(names)) {
				addToCart.get(i).click();
				count++;
			}
			if (count == arrayAsList.size()) {
				break;
			}

		}
	}

}
