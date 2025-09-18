package miscellaneous;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLinks {
	public static void main(String[] args) throws IOException {
	WebDriver driver = new ChromeDriver();
	driver.get("https://www.amazon.com/");
	driver.manage().window().maximize();

	List<WebElement> links = driver.findElements(By.tagName("a"));
	System.out.println("Total links: " + links.size());

	for (WebElement link : links) {
		String url = link.getAttribute("href");

		if (url == null || url.isEmpty()) {
			continue;
		}

		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responseCode = conn.getResponseCode();
			if (responseCode >= 400) {
				System.out.println(url + " is BROKEN (Status: " + responseCode + ")");
				
			} else {
				System.out.println(url + " is OK (Status: " + responseCode + ")");
			}
		} catch (Exception e) {
			System.out.println(url + " is invalid or cannot be checked");
		}
	}

	driver.quit();
}
}


