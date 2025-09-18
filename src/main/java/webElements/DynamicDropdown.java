package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DynamicDropdown {
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com//dropdownsPractise//");
		driver.manage().window().maximize();
		Thread.sleep(1000);
		driver.findElement(By.name("ctl00_mainContent_ddl_originStation1_CTXT")).click();
		Thread.sleep(1000);
		// driver.findElement(By.xpath("//div[@id='glsctl00_mainContent_ddl_originStation1_CTNR']
		// //a[@value='MAA']")).click();
		driver.findElement(By.xpath("//a[@value='MAA']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//a[@value='BLR'])[2]")).click();
		// driver.findElement(By.xpath("//div[@id='glsctl00_mainContent_ddl_destinationStation1_CTNR']
		// //a[@value='BLR']")).click();

	}

}
