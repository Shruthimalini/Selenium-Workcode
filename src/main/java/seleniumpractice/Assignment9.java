package seleniumpractice;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Assignment9 {

	public static void main(String[] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.goibibo.com/flights/");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions act = new Actions(driver);

        try { 
        	WebElement close=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".logSprite"))); 
        	close.click(); 
        	System.out.println("Handled popup.");} 
        catch(TimeoutException e) { 
        		System.out.println("No popup appeared."); }
        

	

        	try { WebElement fromInput = driver.findElement(By.xpath("//p[text()='Enter city or airport']"));
        	wait.until(ExpectedConditions.elementToBeClickable(fromInput)).click();
        	driver.findElement(By.xpath("//input[@type='text']")).sendKeys("Che");
    
		List<WebElement> suggestions =
		 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
		 By.xpath("//ul[@id='autoSuggest-list']/li")));

		for (WebElement suggestion : suggestions) {
		 String text = suggestion.getText();
		 System.out.println("Start: " + text);

		 act.moveToElement(suggestion).pause(Duration.ofMillis(300)).perform();

		if (text.contains("Chengdu")) {
		 suggestion.click();
		 System.out.println("Clicked on Chengdu.");
		break;
		 }
		 }}
	catch(TimeoutException e)
	{
		System.out.println("Element not clickable or suggestions not found.");
	}


}}