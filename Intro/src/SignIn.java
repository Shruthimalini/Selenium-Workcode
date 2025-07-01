import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SignIn {
	

public static void main(String[] args) throws InterruptedException {
		
		String name="malini";
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Lenovo\\Documents\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		String Password=getPassword(driver);
		driver.get("https://rahulshettyacademy.com//locatorspractice//");
		driver.findElement(By.cssSelector("input#inputUsername")).sendKeys(name);
		driver.findElement(By.xpath("//input[@name='inputPassword']")).sendKeys(Password);
	    driver.findElement(By.xpath("//input[@name='chkboxOne']")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	   // driver.findElement(By.className("signIn")).click();

} public static String getPassword(WebDriver driver) throws InterruptedException {
	 
	driver.get("https://rahulshettyacademy.com//locatorspractice//");
	driver.findElement(By.linkText("Forgot your password?")).click();
	Thread.sleep(5000);
	driver.findElement(By.className("reset-pwd-btn")).click();
	String passwordMessage=driver.findElement(By.className("infoMsg")).getText();
	String password = passwordMessage.split("'")[1]; 
	return password;
}}