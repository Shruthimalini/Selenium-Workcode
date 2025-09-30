package configDriven;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import configDrivenAutomation.ExcelDrivenData;


public class BaseTestConfig {



    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> threadWait = new ThreadLocal<>();

    private ExcelDrivenData excelReader = new ExcelDrivenData();

    /**
     * Returns current driver instance
     */
    public WebDriver getDriver() {
        return threadDriver.get();
    }

    /**
     * Returns current wait instance
     */
    public WebDriverWait getWait() {
        return threadWait.get();
    }

    /**
     * Initializes WebDriver and opens URL from configuration sheet
     */
    public void initializeDriver() throws IOException {
        // Excel path for test data/configuration
        String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData.xlsx";

        // Read configuration (Browser + URL)
        Map<String, String> config = excelReader.getConfiguration(excelPath);
        String browser = config.get("Browser");
        String url = config.get("URL");

        if (browser == null || browser.trim().isEmpty()) {
            throw new IOException("Browser not defined in configuration sheet.");
        }

        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                driver = new ChromeDriver(options);
                break;

            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\Users\\MaliniR\\Downloads\\edgedriver_win64\\msedgedriver.exe");
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        threadDriver.set(driver);
        threadWait.set(new WebDriverWait(driver, Duration.ofSeconds(15)));

        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();

        // Open URL from configuration
        if (url != null && !url.trim().isEmpty()) {
            getDriver().get(url);
        } else {
            throw new IOException("URL not defined in configuration sheet.");
        }
    }

    /**
     * Close browser and clean up
     */
    public void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            threadDriver.remove();
            threadWait.remove();
        }
    }
}


    