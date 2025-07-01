import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import org.openqa.selenium.*;
public class Locators {

	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Lenovo\\Documents\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com//locatorspractice//");
		driver.findElement(By.id("inputUsername")).sendKeys("malini");
        driver.findElement(By.name("inputPassword")).sendKeys("hello123");
        driver.findElement(By.className("signInBtn")).click();
        System.out.println(driver.findElement(By.cssSelector("p.error")).getText());
       driver.findElement(By.linkText("Forgot your password?")).click();
        driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("malini");
        driver.findElement(By.cssSelector("input[placeholder='Email']")).sendKeys("shrumali29@gmail.com");   
        driver.findElement(By.xpath("//input[@type='text'][2]")).clear();
       driver.findElement(By.cssSelector("input[placeholder='Email']:nth-child(3)")).sendKeys("john@gmail.com"); 
       //driver.findElement(By.xpath("//input[@placeholder='Phone Number']")).sendKeys("7339585788");
       driver.findElement(By.xpath("//form/input[3]")).sendKeys("7339585788");
       driver.findElement(By.className("reset-pwd-btn")).click();
      System.out.println( driver.findElement(By.className("infoMsg")).getText());
     // System.out.println(driver.findElement(By.cssSelector("form p")).getText());
      driver.findElement(By.className("go-to-login-btn")).click();
      driver.findElement(By.cssSelector("input#inputUsername")).sendKeys("malini");
     driver.findElement(By.xpath("//input[@name='inputPassword']")).sendKeys("rahulshettyacademy");
      driver.findElement(By.xpath("//input[@name='chkboxOne']")).click();
      driver.findElement(By.xpath("//button[@type='submit']")).click();
      //Dynamic changing attribute Value
     //driver.findElement(By.cssSelector("//input[type*='pass']")).sendKeys("rahulshettyacademy");
      driver.findElement(By.xpath("//button[contains(@class,'submit']")).click();
	}} 