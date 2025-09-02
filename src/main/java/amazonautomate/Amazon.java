package amazonautomate;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Amazon {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		Scanner sc = new Scanner(System.in);

		driver.get("https://www.amazon.in/?ref_=icp_country_from_us");
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

		signInToAmazon(driver, wait, sc);
		purchaseInAmazon(driver, wait);

		System.out.println("Successfully signed in");
	}

	private static void signInToAmazon(WebDriver driver, WebDriverWait wait, Scanner sc) {
		Actions act = new Actions(driver);
		// SignIn With OTP
		WebElement accountList = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='nav-link-accountList']")));
		act.moveToElement(accountList).build().perform();

		driver.findElement(By.xpath("//span[text()='Sign in']")).click();
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("7339585788");

		WebElement signIn = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a-button-text.a-declarative")));
		signIn.click();
		WebElement indiaOption = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'India ')]")));
		indiaOption.click();

		driver.findElement(By.className("a-button-input")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[contains(@class,'a-button-inner')])[2]")))
				.click();

		System.out.println("Please enter the OTP manually in the browser, then press Enter here to continue...");
		sc.nextLine();
		driver.findElement(By.id("cvf-submit-otp-button")).click();

		// Sign out
		WebElement accountMenu = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-link-accountList")));
		act.moveToElement(accountMenu).perform();

		WebElement signOutBtn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sign Out']")));
		signOutBtn.click();
		// SignIn with password
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
				.sendKeys("7339585788");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".a-button-text.a-declarative")))
				.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'India ')]"))).click();
		driver.findElement(By.className("a-button-input")).click();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Mali29@Amazon");
		driver.findElement(By.id("signInSubmit")).click();

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
	}

	private static void purchaseInAmazon(WebDriver driver, WebDriverWait wait) throws InterruptedException {
		WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
	    searchBox.sendKeys("Apple iPhone 14");
		driver.findElement(By.id("nav-search-submit-button")).click();
		 List<WebElement> productTitles = wait.until(ExpectedConditions
		            .presenceOfAllElementsLocatedBy(By.cssSelector("div.s-main-slot div[data-component-type='s-search-result'] h2 a")));
		 Optional<WebElement> matchingProduct = productTitles.stream()
				    .filter(el -> el.getText().trim().equalsIgnoreCase("Apple iPhone 14 (256 GB) - Midnight"))
				    .findFirst();

				if (matchingProduct.isPresent()) {
				    System.out.println("Found matching product: " + matchingProduct.get().getText());
				    Thread.sleep(2000);
				    matchingProduct.get().click();
				} else {
				    System.out.println("No exact match found");
				}
				for (String handle : driver.getWindowHandles()) {
				    driver.switchTo().window(handle);
				}
		
		
		

	}
}
