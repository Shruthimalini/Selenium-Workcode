package amazonautomate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest {

    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> threadWait = new ThreadLocal<>();

    
    public WebDriver getDriver() {
        return threadDriver.get();
    }

   
    public WebDriverWait getWait() {
        return threadWait.get();
    }

   
    public void initializeDriver() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\GlobalData.properties");
        prop.load(fis);

        String browser = prop.getProperty("browser");
        WebDriver driver;

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser in properties file: " + browser);
        }

        threadDriver.set(driver);
        threadWait.set(new WebDriverWait(driver, Duration.ofSeconds(15)));

        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();

    }

    public void goTo() {
        getDriver().get("https://www.amazon.com/");
        try {
            WebElement continueBtn = getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']"))
            );
            continueBtn.click();
            System.out.println("Handled 'Continue shopping' popup.");
        } catch (TimeoutException e) {
            System.out.println("'Continue shopping' popup appeared.");
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
