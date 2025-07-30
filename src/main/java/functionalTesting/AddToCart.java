package functionalTesting;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

public class AddToCart {
	public static void main(String[] args) {
		WebDriver driver=new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		driver.manage().window().maximize();
		List<WebElement> productsName= driver.findElements(By.cssSelector("h4.product-name"));
		{
			for(int i=0;i<productsName.size();i++) {
				String name=productsName.get(i).getText();
				if(name.contains("Cucumber"))
				{
					driver.findElements(By.xpath("//button[text()='ADD TO CART']")).get(i).click();
					break;
				}	
				
			}
		}
		
	}

}
