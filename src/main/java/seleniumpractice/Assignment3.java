package seleniumpractice;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Assignment3 {
	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();

		// Disable the passworord Manager
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);

		// Use a temporary profile (optional but prevents profile-stored prompts)
		options.addArguments("--incognito");
		options.addArguments("--start-maximized");

		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/loginpagePractise/");
		driver.manage().window().maximize();
		// String[] phone = { "Samsung Note 8", "Nokia Edge" };
		String[] credentials = getCredentials(driver);
		System.out.println(credentials[0]);
		System.out.println(credentials[1]);
		driver.findElement(By.id("username")).sendKeys(credentials[0]);
		driver.findElement(By.id("password")).sendKeys(credentials[1]);
		driver.findElement(By.xpath("(//span[@class='checkmark'])[2]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okayBtn"))).click();
		WebElement staticDropdown = driver.findElement(By.cssSelector("select.form-control"));
		Select dropdown = new Select(staticDropdown);
		dropdown.selectByVisibleText("Consultant");
		driver.findElement(By.id("terms")).click();
		driver.findElement(By.id("signInBtn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.card-title")));
		List<WebElement> productNames = driver.findElements(By.cssSelector("h4.card-title"));
		List<WebElement> AddToCart = driver.findElements(By.cssSelector("button.btn-info"));

		for (int i = 0; i < productNames.size(); i++) {
			String phoneName = productNames.get(i).getText().trim();
			AddToCart.get(i).click();

			// for (String mobiles : phone) {
			// if (phoneName.equalsIgnoreCase(mobiles)) {
			// AddToCart.get(i).click();
			// System.out.println(mobiles);
			// }

			// }

		}
		driver.findElement(By.cssSelector(".btn-primary")).click();

	}

	public static String[] getCredentials(WebDriver driver) {
		String fullText = driver.findElement(By.cssSelector(".text-center.text-white")).getText();
		System.out.println(fullText);
		fullText = fullText.replace("(", "").replace(")", "");
		String[] extract = fullText.split(" ");
		String UserName = extract[2].trim();
		String passWord = extract[6].trim();
		return new String[] { UserName, passWord };

	}
}
