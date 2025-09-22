package amazonFrameWorkAutomation;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PurchasePage extends AbstractComponent {

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;

    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;

    public PurchasePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void purchaseProduct(String productName) throws InterruptedException {
        waitForVisibility(searchBox).clear();
        searchBox.sendKeys(productName);
        searchButton.click();

        List<WebElement> productTitles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("div.s-main-slot div[data-component-type='s-search-result'] a h2")));

        for (WebElement product : productTitles) {
            if (product.getText().toLowerCase().contains(productName.toLowerCase())) {
                scrollToElement(product);
                
                Set<String> oldTabs = driver.getWindowHandles();

                product.click();

               
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(d -> d.getWindowHandles().size() > oldTabs.size());

                
                Set<String> allHandles = driver.getWindowHandles();
                for (String handle : allHandles) {
                    if (!oldTabs.contains(handle)) {
                        driver.switchTo().window(handle);
                        break;
                    }
                }
                break;
            }
        }
        WebElement addToCartButton = driver.findElement(By.xpath(
				"(//input[@id='add-to-cart-button' and @type='submit' and contains(@class,'a-button-input')])[2]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
		waitForVisibility(addToCartButton).click();
		Thread.sleep(3000);

        try {
            WebElement closeSideCart = driver.findElement(By.xpath("//a[contains(@class,'attach-close-button')]"));
            closeSideCart.click();
        } catch (Exception e) {
            System.out.println("No side cart appeared.");
        }
        captureScreenshot("productAdded");
    }
}
