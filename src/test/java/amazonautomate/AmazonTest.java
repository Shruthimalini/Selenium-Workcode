package amazonautomate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import amazonFrameWorkAutomation.DataProvideAmazon;
import amazonFrameWorkAutomation.LoginPage;
import amazonFrameWorkAutomation.PurchasePage;
import amazonFrameWorkAutomation.RemoveProductPage;
import amazonFrameWorkAutomation.SignOut;

public class AmazonTest extends BaseTest {
	boolean regionChanged = false;

	@BeforeTest
	public void setUp() throws IOException {
		initializeDriver();
		goTo();

	}

	@Test(dataProvider = "jsonData", dataProviderClass = DataProvideAmazon.class)
	public void amazonTest(HashMap<String, String> data) throws InterruptedException, IOException {

		try {
			String phone = data.get("PhoneNumber");
			String password = data.get("Password");

			List<String> products = new ArrayList<>();
			for (int i = 1; i <= 5; i++) {
				String product = data.get("Product " + i);
				if (product != null && !product.trim().isEmpty()) {
					products.add(product);
				}
			}

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
