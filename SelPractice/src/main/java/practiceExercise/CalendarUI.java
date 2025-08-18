package practiceExercise;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class CalendarUI {
	public static void main(String[] args) {
		String month="5";
		String Year="2027";
		String date="2";
		String[] expectedDate= {month,date,Year};
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		driver.manage().window().maximize();
		driver.findElement(By.cssSelector("button.react-date-picker__calendar-button")).click();
		driver.findElement(By.cssSelector("button.react-calendar__navigation__label")).click();
		driver.findElement(By.cssSelector("button.react-calendar__navigation__label")).click();
		driver.findElement(By.xpath("//button[text()='"+Year+"']")).click();
		driver.findElements(By.cssSelector(".react-calendar__tile")).get(Integer.parseInt(month)-1).click();
	    driver.findElement(By.xpath("//button[not(contains(@class,'react-calendar__month-view__days__day--neighboringMonth'))]//abbr[text()='" + date + "']")).click();
	    List<WebElement> selectedDate=driver.findElements(By.cssSelector(".react-date-picker__inputGroup__input"));
	   for(int i=0;i<selectedDate.size();i++)  {
		   System.out.println(selectedDate.get(i).getAttribute("value"));
		   Assert.assertEquals(selectedDate.get(i).getAttribute("value"),expectedDate[i]);
	    	
	    	
	    }
	    
	    driver.close(); 
		
		

}
}