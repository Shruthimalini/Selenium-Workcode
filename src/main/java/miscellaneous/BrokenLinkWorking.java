package miscellaneous;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

public class BrokenLinkWorking {
    public static void main(String[] args) throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.manage().window().maximize();

        List<WebElement> links = driver.findElements(By.cssSelector("li.gf-li a"));
        System.out.println("Total links found: " + links.size());

        SoftAssert softAssert = new SoftAssert();

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            
            if (url == null || url.isEmpty()) {
                continue;
            }

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();

            System.out.println(link.getText() + " → " + url + " → Status Code: " + respCode);

            softAssert.assertTrue(respCode < 400, 
                "Broken Link: " + link.getText() + " → " + url + " (Status Code: " + respCode + ")");
        }

        driver.quit();

    
        softAssert.assertAll();
    }
}
