import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
public class DynamicDropdown {
public static void main(String[] args) throws InterruptedException  {
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Lenovo\\Documents\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://rahulshettyacademy.com//dropdownsPractise//");
		WebElement drop=driver.findElement(By.id("ctl00_mainContent_ddl_originStation1"));
		Select dynamic=new Select(drop);
		dynamic.selectByVisibleText("Chennai (MAA)");
		
		//Thread.sleep(2000);
		//driver.findElement(By.xpath("//option[@value='MAA']")).click();
		//driver.findElement(By.id("ctl00_mainContent_ddl_destinationStation1")).click();
		//driver.findElement(By.xpath("//option[@value='BLR']")).click();
		
		
		
		
		}
}        //*[@id="ctl00_mainContent_ddl_originStation1"]