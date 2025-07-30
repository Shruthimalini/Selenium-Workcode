package seleniumpractice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Assignment2 {
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/angularpractice/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		driver.findElement(By.name("name")).sendKeys("Malini R");
		driver.findElement(By.name("email")).sendKeys("shrumali29@gmail.com");
		driver.findElement(By.cssSelector("input[id='exampleInputPassword1']")).sendKeys("malinir");
		driver.findElement(By.id("exampleCheck1")).click();
		WebElement options = driver.findElement(By.id("exampleFormControlSelect1"));
		Select dropdown = new Select(options);
		dropdown.selectByVisibleText("Female");
		driver.findElement(By.id("inlineRadio1")).click();
		driver.findElement(By.name("bday")).sendKeys("29-05-2001");
		driver.findElement(By.className("btn")).click();

	}

}
