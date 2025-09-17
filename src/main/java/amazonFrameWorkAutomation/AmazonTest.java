package amazonFrameWorkAutomation;


import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import amazonFrameWorkAutomation.LoginPage;
import utils.DataProvide;

import java.util.ArrayList;

public class AmazonTest {

    WebDriver driver;

    @BeforeTest
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");
    }

    @Test(dataProvider = "excelDataProvider", dataProviderClass = DataProvide.class)
    public void testAmazonLogin(ArrayList<String> data) throws Exception {
        String phone = data.get(1);
        String password = data.get(2);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginToAmazon(phone, password);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }



		
	

}
