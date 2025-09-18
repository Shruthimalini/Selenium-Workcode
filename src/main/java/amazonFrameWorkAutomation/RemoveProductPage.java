package amazonFrameWorkAutomation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RemoveProductPage extends AbstractComponent {

    @FindBy(id = "nav-cart-count-container")
    private WebElement cartIcon;

    @FindBy(id = "activeCartViewForm")
    private WebElement cartForm;

    public RemoveProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void removeProduct(String productName) {
    	 waitForClickability(cartIcon).click();
    	 waitForVisibility(cartForm);	
        List<WebElement> cartItems = cartForm.findElements(By.xpath(".//div[contains(@class,'sc-list-item')]"));

        for (WebElement item : cartItems) {
            try {
                WebElement title = item.findElement(By.cssSelector("span.a-truncate-cut"));
                if (title.getText().toLowerCase().contains(productName.toLowerCase())) {
                    WebElement deleteButton = item.findElement(By.xpath(".//button[@data-a-selector='decrement']"));
                    deleteButton.click();
                    waitForStaleness(item);
                    break;
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }
    }

   
   
}



