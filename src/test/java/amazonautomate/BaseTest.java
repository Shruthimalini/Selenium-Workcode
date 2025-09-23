package amazonautomate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseTest {

	protected WebDriver driver;
	protected WebDriverWait wait;
	
    
	public void initializeDriver() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\GlobalData.properties");
		prop.load(fis);

		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			driver = new ChromeDriver(options);
			

		} else if (browserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "C:\\Users\\MaliniR\\Downloads\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();

		}
		else {
            throw new RuntimeException("Browser not supported: " + browserName);
        }

		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
	}
	public void goTo() throws IOException {
		driver.get("https://www.amazon.com/");
		try {
			WebElement continueBtn = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
			continueBtn.click();
			System.out.println("Handled 'Continue shopping' popup.");
		} catch (TimeoutException e) {
			System.out.println("No 'Continue shopping' popup appeared.");
		}
	}
	public String captureScreenshot(String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destPath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timestamp + ".png";
        File destFile = new File(destPath);
        try {
            FileUtils.copyFile(src, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destPath;
    }
   
}

