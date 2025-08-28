package seleniumpractice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChildContent {
	  public static void main(String[] args) throws InterruptedException {
		  ChromeOptions options = new ChromeOptions();
	        options.addArguments("--disable-notifications");

	        WebDriver driver = new ChromeDriver(options);
	        driver.get("https://www.irctc.co.in/nget/train-search");
	        driver.manage().window().maximize();
	        WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(15));
	        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-primary")));
	        okButton.click();

	        
	        WebElement flights = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("FLIGHTS")));
	        flights.click();
	        
	        Set<String> windows = driver.getWindowHandles();
	        Iterator<String> it = windows.iterator();
	        String parentId = it.next();
	        String childId = it.next();
	        
	    
	        driver.switchTo().window(childId);
	        

	        
	       
	        System.out.println("Child window title: " + driver.getTitle());

	      
	        driver.switchTo().window(parentId);
	        System.out.println("Back to parent window: " + driver.getTitle());

	        driver.quit();
	    }
	}

	   