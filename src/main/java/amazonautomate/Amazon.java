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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Amazon {

	WebDriver driver;
	WebDriverWait wait;
	Scanner sc;
	Actions act;
	boolean regionChanged = false;
	
	@BeforeSuite
    public void beforeSuite() {
        System.out.println("====== Test Suite Started ======");
        
    }

	@BeforeTest
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		act = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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
		 try {
		String phone = data.get(1);
		String password = data.get(2);
		List<String> products = data.subList(3, 8);
		

		if (!regionChanged) {
	        signInToIndia(driver, wait);
	        regionChanged = true;}
		
		signInToAmazon(driver, wait, sc, phone, password);
		for (String product : products) {
		    purchaseProduct(driver, wait, product);
		}
		for (String product : products) {
			removeProduct(driver, wait, product);
		}
	
        signOut();
        driver.get("https://www.amazon.in/"); 
        Thread.sleep(2000);	
        signIn(driver, wait,  phone,password);
        Assert.assertTrue(true, "Test ran successfully for dataset.");

		    } catch (Exception e) {
		        e.printStackTrace();
		        Assert.fail("Test failed due to exception: " + e.getMessage());
		    }
		}
	
	private void signInToAmazon(WebDriver driver, WebDriverWait wait, Scanner sc, String phone, String password)
			throws InterruptedException {

		WebElement accountList = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='nav-link-accountList']")));
		act.moveToElement(accountList).build().perform();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sign in']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_email_login"))).sendKeys(phone);
		Thread.sleep(1000);
		 try {
		WebElement signIn = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a-button-text.a-declarative")));
		signIn.click();

		WebElement indiaOption = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'India ')]")));
		indiaOption.click();
	 } catch (TimeoutException e) {
		 		 System.out.println("India region selection not required. Continuing sign-in.");
	    }
		driver.findElement(By.className("a-button-input")).click();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
		try {
	    WebElement errorBox=driver.findElement(By.id("auth-error-message-box"));
	    if(errorBox.isDisplayed()) {
	    	String errorMessage = errorBox.getText();
	    	System.out.println("Login failed with error: " + errorMessage);
            return; 
        }
    } catch (NoSuchElementException e) {
        System.out.println("No error box displayed after entering mobile.");
    }
	        
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
		 System.out.println("Switched to Amazon India.");
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
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
			addToCartButton.click();
			Thread.sleep(3000);
			try {
			    WebElement closeBtn = driver.findElement(By.xpath("//div[contains(@class,'attach-cart-info-container')]//a[contains(@class,'attach-close-button')]"));
			    closeBtn.click();
			} catch (NoSuchElementException e) {
			    System.out.println("Side cart not shown, proceeding without closing.");
			}
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String timestamp = String.valueOf(System.currentTimeMillis());
			File dest = new File(System.getProperty("user.home") + "/amazon_cart_" + timestamp + ".png");
			FileUtils.copyFile(src, dest);

		} else {
			System.out.println("No match found for: " + productName);
		}
	}

	private static void removeProduct(WebDriver driver, WebDriverWait wait, String productName) {
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-cart-count-container"))).click();
	    WebElement cartForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("activeCartViewForm")));
	    List<WebElement> cartItems = cartForm.findElements(
	        By.xpath(".//div[contains(@class,'sc-list-item')]")
	    );
	    boolean itemRemoved = false;
	    for (WebElement item : cartItems) {
	        try {
	            WebElement titleElement = item.findElement(By.cssSelector("span.a-truncate-cut"));
	            String title = titleElement.getText().trim();

	            if (title.toLowerCase().contains(productName.toLowerCase())) {
	                System.out.println("Found and will remove: " + title);
	                WebElement deleteButton = item.findElement(By.xpath(".//button[@data-a-selector='decrement']"));
	                deleteButton.click();
	                wait.until(ExpectedConditions.stalenessOf(item));
	                itemRemoved = true;
	                break;
	            }
	        } catch (NoSuchElementException e) {
	            continue;
	        }
	    }
	}

	
	private void signOut() {
	    WebElement accountList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-link-accountList")));
	    act.moveToElement(accountList).perform();

	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sign Out']"))).click();
	    System.out.println("Signed out successfully.");
	    Set<String> allWindows = driver.getWindowHandles();
	    }
		
	private static void signIn(WebDriver driver, WebDriverWait wait,String phone,String password ) {
		driver.findElement(By.xpath("//input[@id='ap_email_login']")).sendKeys(phone);
		driver.findElement(By.cssSelector(".a-button-input")).click();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
		driver.findElement(By.id("signInSubmit")).click();	
	}
	/*@AfterClass
	public void tearDown() {
	    for (String window : driver.getWindowHandles()) {
	        driver.switchTo().window(window);
	        driver.close();
	    }
	
	}*/@AfterSuite
    public void afterSuite() {
        System.out.println("====== Test Suite Finished ======");
        
    }
	
}