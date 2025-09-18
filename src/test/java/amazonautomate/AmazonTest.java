package amazonautomate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import amazonFrameWorkAutomation.LoginPage;
import amazonFrameWorkAutomation.PurchasePage;
import amazonFrameWorkAutomation.RemoveProductPage;
import amazonFrameWorkAutomation.SignOut;

public class AmazonTest extends BaseTest{
    boolean regionChanged = false;

    @BeforeTest
    public void setUp() throws IOException {
    	initializeDriver();
    	goTo();
    	
	}
    
    @Test(dataProvider = "excelDataProvider", dataProviderClass = DataProvide.class)
    public void amazonTest(ArrayList<String> data) throws InterruptedException, IOException {
        try {
            String phone = data.get(1);
            String password = data.get(2);
            List<String> products = data.subList(3, 8); 

            LoginPage loginPage = new LoginPage(driver);
             
            if (!regionChanged) {
                loginPage.loginIndia();
                regionChanged = true;
            }

            
            loginPage.login(phone, password);

            
            PurchasePage purchasePage = new PurchasePage(driver);
            for (String product : products) {
                if (!product.isEmpty()) {
                    purchasePage.purchaseProduct(product);
                }
            }

           
            RemoveProductPage removePage = new RemoveProductPage(driver);
            for (String product : products) {
                if (!product.isEmpty()) {
                    removePage.removeProduct(product);
                }
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
