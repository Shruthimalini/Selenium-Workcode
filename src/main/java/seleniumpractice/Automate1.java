package seleniumpractice;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Automate1 {
	public static void main(String[] args) throws InterruptedException {
	WebDriver driver=new ChromeDriver();
	driver.get("https://rahulshettyacademy.com//dropdownsPractise//");
	driver.manage().window().maximize();
	Thread.sleep(1000);
	//Select Country
	driver.findElement(By.id("autosuggest")).sendKeys("ind");
	  Thread.sleep(1000);
	  List<WebElement> options=driver.findElements(By.className("ui-menu-item"));
	  
	  for(WebElement option:options)
	  {
		  if(option.getText().equals("India"))
		  {
			  option.click();
			   break;
		  }}
	  //Select Trip
	  driver.findElement(By.id("ctl00_mainContent_rbtnl_Trip_1")).click();
	  //Select From and To Place
		  driver.findElement(By.name("ctl00_mainContent_ddl_originStation1_CTXT")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@id='glsctl00_mainContent_ddl_originStation1_CTNR'] //a[@value='MAA']")).click();
		    Thread.sleep(1000);
	        driver.findElement(By.xpath("//div[@id='glsctl00_mainContent_ddl_destinationStation1_CTNR'] //a[@value='BLR']")).click();
		//Select a date	
	        driver.findElement(By.cssSelector(".ui-state-default.ui-state-active")).click();
	        driver.findElement(By.className("ui-datepicker-trigger")).click();
	        driver.findElement(By.cssSelector(".ui-state-default.ui-state-active")).click();
	        //Select no of passenger
	        driver.findElement(By.id("divpaxinfo")).click();
			Thread.sleep(2000);
				
					for(int i=0;i<4;i++) {
				driver.findElement(By.id("hrefIncAdt")).click();
			}
			
			driver.findElement(By.id("btnclosepaxoption")).click();
			System.out.println(driver.findElement(By.id("divpaxinfo")).getText());
			//Select Currency
			WebElement staticDropdown=driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency"));	
			Select dropdown=new Select(staticDropdown);
			dropdown.selectByVisibleText("INR");
			System.out.println(dropdown.getFirstSelectedOption().getText());
			//Select Checkbox
          driver.findElement(By.name("ctl00$mainContent$chk_SeniorCitizenDiscount")).click();
			  System.out.println(driver.findElement(By.name("ctl00$mainContent$chk_SeniorCitizenDiscount")).isSelected());
			driver.findElement(By.name("ctl00$mainContent$btn_FindFlights")).click();
			  
	
	}
}
