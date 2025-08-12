package practiceExercise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CalendarUI {
	public static void main(String[] args) {
		String month="05";
		String Year="2027";
		String date="	2";
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		driver.manage().window().maximize();
		driver.findElement(By.cssSelector("button.react-date-picker__calendar-button")).click();
		driver.findElement(By.cssSelector("button.react-calendar__navigation__label")).click();
		driver.findElement(By.cssSelector("button.react-calendar__navigation__label")).click();
		driver.findElement(By.xpath("//button[text()='"+Year+"']")).click();
		driver.findElements(By.cssSelector(".react-calendar__tile")).get(Integer.parseInt(month)-1).click();
	    driver.findElement(By.xpath("//button[not(contains(@class,'react-calendar__month-view__days__day--neighboringMonth'))]//abbr[text()='" + date + "']")).click();
		
		

}
}