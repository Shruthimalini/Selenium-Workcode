package amazonautomate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import amazonFrameWorkAutomation.ErrorDataProvide;
import amazonFrameWorkAutomation.LoginPage;
import amazonFrameWorkAutomation.PurchasePage;
import amazonFrameWorkAutomation.RemoveProductPage;
import amazonFrameWorkAutomation.SignOut;

public class AmazonErrorTest extends BaseTest {

    private ThreadLocal<Boolean> regionChanged = ThreadLocal.withInitial(() -> false);

    @BeforeMethod
    public void setUp() throws IOException {
        initializeDriver();
        goTo();
    }

   @Test(
            dataProvider = "excelDataProvider",
            dataProviderClass = ErrorDataProvide.class,
            retryAnalyzer = amazonautomate.Retry.class
        )
    public void amazonTest(ArrayList<String> data) throws InterruptedException, IOException {
        try {
            
            System.out.println("DEBUG: Received data row = " + data + " | size = " + data.size());

            
            
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

            LoginPage loginPage = new LoginPage(getDriver());

            if (!regionChanged.get()) {
                loginPage.loginIndia();
                regionChanged.set(true);
            }
            loginPage.enterPhoneNumber(phone);

        
         if (loginPage.isInvalidMobileMessageDisplayed()) {
             String actualMessage = loginPage.getInvalidMobileMessage();
             System.out.println(actualMessage);
             Assert.assertTrue(actualMessage.contains(expectedMessage),
                 "Expected invalid mobile error not found. Actual: " + actualMessage);
             return;
         }

         loginPage.enterPassword(password);

        
         if (loginPage.isErrorBoxDisplayed()) {
             String actualMessage = loginPage.getErrorBoxMessage();
             System.out.println(actualMessage);
             Assert.assertTrue(actualMessage.contains(expectedMessage),
                 "Expected error message not found after password. Actual: " + actualMessage);
             return;
         }


            
            PurchasePage purchasePage = new PurchasePage(getDriver());
            for (String product : products) {
                purchasePage.purchaseProduct(product);
            }

            RemoveProductPage removePage = new RemoveProductPage(getDriver());
            for (String product : products) {
                removePage.removeProduct(product);
            }

            SignOut signOut = new SignOut(getDriver());
            signOut.signOut();

            
            Thread.sleep(2000);
            getDriver().get("https://www.amazon.in/");

            Assert.assertTrue(true, "Test ran successfully for dataset.");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        quitDriver();
    }
}
