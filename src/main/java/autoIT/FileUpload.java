package autoIT;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FileUpload {
    public static void main(String[] args) throws InterruptedException, IOException {

        String dest = System.getProperty("user.dir");

        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", dest); // set your folder
        options.setExperimentalOption("prefs", chromePrefs);

       
        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("https://www.ilovepdf.com/pdf_to_jpg");
        driver.manage().window().maximize();

        driver.findElement(By.id("pickfiles")).click();
        Thread.sleep(2000);
        Runtime.getRuntime().exec("C:\\Users\\MaliniR\\Downloads\\autoit-v3\\install\\upload_file.exe");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("processTask"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("pickfiles"))).click(); 

       
        File downloaded = new File(dest + "/ilovepdf_pages-to-jpg.zip");
        int timeout = 30; 

        while (!downloaded.exists() && timeout > 0) {
            Thread.sleep(1000);
            timeout--;
        }

        if (downloaded.exists()) {
            System.out.println("✅ File downloaded: " + downloaded.getAbsolutePath());
            Assert.assertTrue(true);
            if (downloaded.delete()) {
                System.out.println("🗑️ File deleted");
            }
        } else {
            System.out.println("❌ Download failed or wrong path");
            Assert.fail("File not found");
        }

        
    }
}
