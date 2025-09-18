package amazonFrameWorkAutomation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory; 

public class SignOut extends AbstractComponent{

    @FindBy(id = "nav-link-accountList")
    private WebElement accountList;

    @FindBy(xpath = "//span[text()='Sign Out']")
    private WebElement signOutOption;

    public SignOut(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void signOut() {
        hoverOverElement(accountList);
        waitForClickability(signOutOption).click();
        System.out.println("Signed out successfully.");
    }
}


