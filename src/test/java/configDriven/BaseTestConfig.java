package configDriven;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import configDrivenAutomation.BrowserReader;

public class BaseTestConfig {

    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> threadWait = new ThreadLocal<>();
    protected BrowserReader configData;
    private String baseUrl;  

    public WebDriver getDriver() {
        return threadDriver.get();
    }

    public WebDriverWait getWait() {
        return threadWait.get();
    }

    public void initializeDriver() throws IOException {
    	configData = new BrowserReader();

        Map<String, String> config =configData .getConfiguration(
                "C:\\Users\\MaliniR\\Documents\\ConfigData.xlsx",
                "Configuration"
        );

        String browserName = config.get("Browser");
        baseUrl = config.get("URL");

        System.out.println("Browser from Excel: " + browserName);
        System.out.println("URL from Excel: " + baseUrl);

        WebDriver driver;

        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Unsupported browser: " + browserName);
        }

        threadDriver.set(driver);
        threadWait.set(new WebDriverWait(driver, Duration.ofSeconds(15)));

       
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void goTo() {
        getDriver().get(baseUrl);
        getDriver().manage().deleteAllCookies();
        try {
            WebElement continueBtn = getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']"))
            );
            continueBtn.click();
            System.out.println("Handled 'Continue shopping' popup.");
        } catch (TimeoutException e) {
            System.out.println("'Continue shopping' popup not appeared.");
        }
    }

    public String captureScreenshot(String testName) {
        WebDriver driver = getDriver();
        if (driver == null) {
            System.out.println("Driver is null. Screenshot cannot be captured.");
            return null;
        }

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destDir = System.getProperty("user.dir") + "\\screenshots\\";
        String destPath = destDir + testName + "_" + timestamp + ".png";

        try {
            File dir = new File(destDir);
            if (!dir.exists()) dir.mkdirs();
            FileUtils.copyFile(src, new File(destPath));
            System.out.println("Screenshot saved at: " + destPath);
            return destPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            threadDriver.remove();
            threadWait.remove();
        }
    }
}
