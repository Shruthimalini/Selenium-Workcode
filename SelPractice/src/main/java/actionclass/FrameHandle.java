package actionclass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class FrameHandle {
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.get("https://jqueryui.com/droppable/");
	    driver.manage().window().maximize();
	    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[class=demo-frame]")));
	   System.out.println(driver.findElements(By.tagName("iframe")).size());
	    driver.findElement(By.id("draggable")).click();
	    WebElement source=driver.findElement(By.id("draggable"));
	    WebElement target=driver.findElement(By.id("droppable"));
	    Actions a=new Actions(driver);
	    a.dragAndDrop(source, target).build().perform();
	    driver.switchTo().hashCode();

}
}