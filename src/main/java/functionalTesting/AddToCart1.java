package functionalTesting;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddToCart1 {
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		driver.manage().window().maximize();
		Thread.sleep(1000);
		String[] itemsToAdd = { "Tomato", "Cauliflower", "Mushroom", "Onion", "Pomegranate" };
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