package practicalProblems;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;



public class Scroll {
	public static void main(String[] args) throws InterruptedException {
		int sum=0;
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scroll(0,500)");
		Thread.sleep(1000);
		js.executeScript("document.querySelector(\".tableFixHead\").scrollTop=5000");
		List<WebElement> values = driver.findElements(By.cssSelector(".tableFixHead td:nth-child(4)"));
		for (int i = 0; i < values.size(); i++) {
          // System.out.println(Integer.parseInt(values.get(i).getText()));
		   sum=sum+Integer.parseInt(values.get(i).getText());
		}
		System.out.println(sum);
        String arr=driver.findElement(By.className("totalAmount")).getText();
        System.out.println(arr);
       int total= Integer.parseInt(arr.split(":")[1].trim());
       System.out.println(total);
       Assert.assertEquals(total,sum);
        
	}
}