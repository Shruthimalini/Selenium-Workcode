import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Locators3 {
	
public static void main(String[] args)  {
	
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Lenovo\\Documents\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
	//Traverse-parent to child and child to parent and sibling to sibling
		driver.get("https://rahulshettyacademy.com//AutomationPractice//");
		//parent to child 
		//driver.findElement(By.xpath("//header/div/buttton[1]")).click();
		//sibling to sibling
		System.out.println(driver.findElement(By.xpath("//header/div/button[1]/following-sibling::button[1]")).getText());
		//child to parent
		//System.out.println(driver.findElement(By.xpath("//header/div/button[1]/parent::div/button[2]")).getText());	
}
}