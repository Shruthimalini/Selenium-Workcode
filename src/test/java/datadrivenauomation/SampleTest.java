package datadrivenauomation;

import org.testng.annotations.*;
import org.testng.Assert;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

public class SampleTest {

	WebDriver driver;
	WebDriverWait wait;
	ResultToPrint resultWriter;

	@BeforeClass
	public void setup() {
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--incognito");
		options.addArguments("--start-maximized");

		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		resultWriter = new ResultToPrint();
	}

	@Test(dataProvider = "excelDataProvider", dataProviderClass = DataProvide.class)
	public void dataDrivenTest(ArrayList<String> testData) throws IOException {
		String testcaseName = testData.get(0);

		switch (testcaseName.toLowerCase()) {
		case "login":
			runLoginTest(testcaseName, testData);
			break;

		case "purchase":
			runPurchaseTest(testcaseName, testData);
			break;

		default:
			System.out.println("Unknown test case: " + testcaseName);
			break;
		}
	}

	private void runLoginTest(String testcaseName, ArrayList<String> testData) throws IOException {
		String username = testData.get(1);
		String password = testData.get(2);

		driver.get("https://rahulshettyacademy.com/loginpagePractise/");

		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("(//span[@class='checkmark'])[2]")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okayBtn"))).click();
		new Select(driver.findElement(By.cssSelector("select.form-control"))).selectByVisibleText("Consultant");
		driver.findElement(By.id("terms")).click();
		driver.findElement(By.id("signInBtn")).click();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.card-title")));
			resultWriter.writeResult(testcaseName, "Login successful");
		} catch (TimeoutException e) {
			String errorMsg = driver.findElement(By.cssSelector(".alert-danger")).getText();
			resultWriter.writeResult(testcaseName, "❌ " + errorMsg);
			Assert.fail("Login failed: " + errorMsg);
		}
	}

	private void runPurchaseTest(String testcaseName, ArrayList<String> testData) throws IOException {
		// Use login data from Excel if needed (Optional: depends on your use case)
		ArrayList<String> loginData = new ExcelDataDriven().getData("Login");
		String username = loginData.get(1);
		String password = loginData.get(2);

		driver.get("https://rahulshettyacademy.com/loginpagePractise/");

		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("(//span[@class='checkmark'])[2]")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okayBtn"))).click();
		new Select(driver.findElement(By.cssSelector("select.form-control"))).selectByVisibleText("Consultant");
		driver.findElement(By.id("terms")).click();
		driver.findElement(By.id("signInBtn")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.card-title")));

		List<WebElement> productNames = driver.findElements(By.cssSelector("h4.card-title"));
		List<WebElement> addToCartButtons = driver.findElements(By.cssSelector("button.btn-info"));

		for (int i = 0; i < productNames.size(); i++) {
			String actualProduct = productNames.get(i).getText().trim();

			for (int j = 1; j < testData.size(); j++) { 
				String expectedProduct = testData.get(j).trim();
				if (!expectedProduct.isEmpty() && actualProduct.equalsIgnoreCase(expectedProduct)) {
					addToCartButtons.get(i).click();
					System.out.println("Added to cart: " + expectedProduct);
					break;
				}
			}
		}

		resultWriter.writeResult(testcaseName, "✅ Products added to cart");
	}

	@AfterClass
	public void tearDown() {
		//driver.quit();
	}
}
