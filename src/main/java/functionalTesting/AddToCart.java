package functionalTesting;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

public class AddToCart {
	public static void main(String[] args)  {
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		driver.manage().window().maximize();
		List<WebElement> productNames = driver.findElements(By.cssSelector("h4.product-name"));
		List<WebElement> addToCart = driver.findElements(By.xpath("//button[text()='ADD TO CART']"));
		for (int i = 0; i < productNames.size(); i++) {

			String name = productNames.get(i).getText();
			if (name.contains("Cucumber")) {
				addToCart.get(i).click();
			}
		}

	}
}
