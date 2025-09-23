package amazonautomate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import amazonFrameWorkAutomation.ErrorDataProvide;
import amazonFrameWorkAutomation.LoginPage;
import amazonFrameWorkAutomation.PurchasePage;
import amazonFrameWorkAutomation.RemoveProductPage;
import amazonFrameWorkAutomation.SignOut;


public class AmazonErrorTest extends BaseTest {

    boolean regionChanged = false;

    @BeforeTest
    public void setUp() throws IOException {
        initializeDriver();
        goTo();
    }

    @Test(dataProvider = "excelDataProvider", dataProviderClass = ErrorDataProvide.class)
    public void amazonTest(ArrayList<String> data) throws InterruptedException, IOException {

        try {
            String phone = data.get(1);
            String password = data.get(2);
            String expectedMessage = data.get(8);  
            List<String> products = new ArrayList<>();
            
            for (int i = 3; i <= 7; i++) {
                String product = data.get(i);
                if (product != null && !product.trim().isEmpty()) {
                    products.add(product);
                }
            }

            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(phone, password);

          
            if (loginPage.isErrorBoxDisplayed()) {
                String actualMessage = loginPage.getErrorBoxMessage();
                Assert.assertTrue(actualMessage.contains(expectedMessage),
                        "Expected error message not found in error box.");
                return; 
            } else if (loginPage.isInvalidMobileMessageDisplayed()) {
                String actualMessage = loginPage.getInvalidMobileMessage();
                Assert.assertTrue(actualMessage.contains(expectedMessage),
                        "Expected invalid mobile error not found.");
                return; 
            } else {
                Assert.fail("No error message was displayed for invalid credentials.");
            }

           
            if (!regionChanged) {
                loginPage.loginIndia();
                regionChanged = true;
            }

            PurchasePage purchasePage = new PurchasePage(driver);
            for (String product : products) {
                purchasePage.purchaseProduct(product);
            }

            RemoveProductPage removePage = new RemoveProductPage(driver);
            for (String product : products) {
                removePage.removeProduct(product);
            }

            SignOut signOut = new SignOut(driver);
            signOut.signOut();
            Thread.sleep(2000);
            driver.get("https://www.amazon.in/");
            Assert.assertTrue(true, "Test ran successfully for dataset.");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
