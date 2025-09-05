package amazonautomate;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Amazon {

	WebDriver driver;
	WebDriverWait wait;
	Scanner sc;
	Actions act;

	@BeforeTest
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		act = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		sc = new Scanner(System.in);

		driver.get("https://www.amazon.com/");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

		try {
			WebElement continueBtn = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
			continueBtn.click();
			System.out.println("Handled 'Continue shopping' popup.");
		} catch (TimeoutException e) {
			System.out.println("No 'Continue shopping' popup appeared.");
		}
	}

	@Test(dataProvider = "excelDataProvider", dataProviderClass = DataProvide.class)
	public void amazonTest(ArrayList<String> data) throws InterruptedException, IOException {
		String phone = data.get(1);
		String password = data.get(2);
		String product1 = data.get(3);
		String product2 = data.get(4);

		signInToIndia(driver, wait);
		//signInToAmazon(driver, wait, sc, phone, password);
		purchaseProduct(driver, wait, product1);
		purchaseProduct(driver, wait, product2);
		removeProduct(driver,wait,product1);

		System.out.println("Successfully signed in");
	}

	private void signInToAmazon(WebDriver driver, WebDriverWait wait, Scanner sc, String phone, String password)
			throws InterruptedException {

		WebElement accountList = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='nav-link-accountList']")));
		act.moveToElement(accountList).build().perform();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sign in']"))).click();
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(phone);
		Thread.sleep(1000);

		WebElement signIn = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a-button-text.a-declarative")));
		signIn.click();

		WebElement indiaOption = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'India ')]")));
		indiaOption.click();

		driver.findElement(By.className("a-button-input")).click();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
		driver.findElement(By.id("signInSubmit")).click();

	}

	private void signInToIndia(WebDriver driver, WebDriverWait wait) throws InterruptedException {
		try {
			WebElement toaster = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".glow-toaster")));
			WebElement dismissBtn = driver
					.findElement(By.xpath("//span[contains(@class,'glow-toaster-button-dismiss')]"));
			dismissBtn.click();
			System.out.println("Toaster dismissed.");
		} catch (TimeoutException e) {
			System.out.println("No toaster popup appeared.");
		}

		WebElement changeCountry = driver.findElement(By.id("icp-nav-flyout"));
		act.moveToElement(changeCountry).perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Change country/region.']")))
				.click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.className("a-dropdown-prompt"))).click();

		WebElement indiaOption1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//a[contains(@class,'a-dropdown-link') and contains(text(),'India')]")));
		Thread.sleep(1000);
		indiaOption1.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.a-button-input[type='submit']")))
				.click();

		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}
	}

	private static void purchaseProduct(WebDriver driver, WebDriverWait wait, String productName)
			throws InterruptedException, IOException {

		WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
		searchBox.clear();
		searchBox.sendKeys(productName);
		driver.findElement(By.id("nav-search-submit-button")).click();

		List<WebElement> productTitles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.cssSelector("div.s-main-slot div[data-component-type='s-search-result'] a h2")));

		Optional<WebElement> matchingProduct = productTitles.stream()
				.filter(el -> el.getText().toLowerCase().contains(productName.toLowerCase())).findFirst();

		if (matchingProduct.isPresent()) {
			System.out.println("Found matching product: " + matchingProduct.get().getText());

			WebElement productLink = matchingProduct.get();
			Set<String> oldTabs = driver.getWindowHandles();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productLink);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", productLink);

			wait.until(driver1 -> driver1.getWindowHandles().size() > oldTabs.size());
			Set<String> allHandles = driver.getWindowHandles();

			for (String handle : allHandles) {
				if (!oldTabs.contains(handle)) {
					driver.switchTo().window(handle);
					break;
				}
			}

			WebElement addToCartButton = driver.findElement(By.xpath(
					"(//input[@id='add-to-cart-button' and @type='submit' and contains(@class,'a-button-input')])[2]"));
			// WebElement addToCartButton =
			// driver.findElement(By.xpath("//input[@id='add-to-cart-button']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
			// ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
			// addToCartButton);
			addToCartButton.click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(
					"//div[contains(@class,'attach-cart-info-container')]//a[contains(@class,'attach-close-button')]"))
					.click();

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String timestamp = String.valueOf(System.currentTimeMillis());
			File dest = new File("C:\\Users\\MaliniR\\Pictures\\Screenshots\\amazon_cart_" + timestamp + ".png");
			FileUtils.copyFile(src, dest);

		} else {
			System.out.println("No match found for: " + productName);
		}
	}

	private static void removeProduct(WebDriver driver, WebDriverWait wait, String productName) {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='add-to-cart-button']"))).click();
		//WebElement ActiveCart=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.a-cardui.sc-card-style.sc-list.sc-java-remote-feature.celwidget.sc-grid-view.sc-grid-full-width.sc-card-spacing-top-none")));
       // List<WebElement> items=ActiveCart.findElements(By.xpath(".//div[contains(@class, 'sc-list-item')]//span[contains(@class, 'a-truncate-cut')]"));
      //  for (WebElement item : items) {
          //  if (item.getText().equalsIgnoreCase(productName)) {
             //   System.out.println("Found and will remove: " + productName);
         //       WebElement parent = item.findElement(By.xpath("./ancestor::div[contains(@class,'sc-list-item')]"));
             //   WebElement deleteButton = parent.findElement(By.xpath(".//input[@value='Delete']"));
           //     deleteButton.click();
             //   break;
            }
       // }
 //   }
	}
