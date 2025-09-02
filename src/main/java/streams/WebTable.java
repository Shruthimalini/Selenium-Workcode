package streams;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class WebTable {
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("https://filamentgroup.github.io/tablesaw/demo/sort.html");
		driver.manage().window().maximize();

		List<WebElement> initialTitles = driver.findElements(By.xpath("//td[@class='title']//a"));
		List<String> initialTitleNames = initialTitles.stream().map(WebElement::getText).collect(Collectors.toList());
		initialTitleNames.forEach(System.out::println);
		driver.findElement(By.xpath("//button[text()='Movie Title']")).click();

		
		List<WebElement> sortedTitles = driver.findElements(By.xpath("//td[@class='title']//a"));
		List<String> sortedTitleNames = sortedTitles.stream().map(WebElement::getText).collect(Collectors.toList());

		
		Assert.assertFalse(sortedTitleNames.equals(initialTitleNames));

		
		List<String> ranks = initialTitles.stream().filter(el -> el.getText().contains("Frozen")).map(el -> getRank(el))
				.collect(Collectors.toList());


		ranks.forEach(rank -> System.out.println("Rank of Frozen: " + rank));

		driver.quit();
	}

	private static String getRank(WebElement titleElement) {
		WebElement row = titleElement.findElement(By.xpath("./ancestor::tr"));
		WebElement rankCell = row.findElement(By.xpath("./td[2]"));
		return rankCell.getText();
	}
}
