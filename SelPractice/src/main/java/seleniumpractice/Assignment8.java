package seleniumpractice;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Assignment8 {
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
	    driver.get("https://www.nseindia.com/");
		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scroll(0,600)");
		Thread.sleep(3000);
		WebElement classdriver=driver.findElement(By.cssSelector(".market_snapshort_table"));
		List<WebElement> tableRow=classdriver.findElements(By.tagName("tr"));
		int row=tableRow.size();
		System.out.println(row);
		List<WebElement> tableColumn=tableRow.get(1).findElements(By.tagName("td"));
		int column=tableColumn.size();
		System.out.println(column);
		for (WebElement cell : tableColumn) {
            System.out.println(cell.getText());
        }
		
		 
	}

}
