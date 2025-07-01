import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class Locators2 {

	public static void main(String[] args) throws InterruptedException {
		
		String name="malini";
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Lenovo\\Documents\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com//locatorspractice//");
		driver.findElement(By.cssSelector("input#inputUsername")).sendKeys(name);    
	    driver.findElement(By.xpath("//input[@name='inputPassword']")).sendKeys("rahulshettyacademy");	     
	    driver.findElement(By.xpath("//input[@name='chkboxOne']")).click();
	    driver.findElement(By.className("signIn")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    Thread.sleep(2000);
	    System.out.println( driver.findElement(By.tagName("p")).getText());
        Assert.assertEquals(driver.findElement(By.tagName("p")).getText(),"You are successfully logged in.");
        Assert.assertEquals(driver.findElement(By.cssSelector("div[class='login-container'] h2")).getText(),"Hello"+name+",");
        driver.findElement(By.className("logout-btn")).click();
        driver.findElement(By.xpath("//button[text()='Log Out']" )).click();
        driver.close();
	}
	
	}

