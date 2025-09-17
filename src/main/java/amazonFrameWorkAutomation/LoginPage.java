package amazonFrameWorkAutomation;

import amazonFrameWorkAutomation.AbstractComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class LoginPage extends AbstractComponent {

    @FindBy(css = "div[id='nav-link-accountList']")
    private WebElement accountList;

    @FindBy(xpath = "//span[text()='Sign in']")
    private WebElement signInButton;

    @FindBy(id = "ap_email_login")
    private WebElement emailField;

    @FindBy(css = ".a-button-text.a-declarative")
    private WebElement regionSignIn;

    @FindBy(xpath = "//a[contains(text(),'India ')]")
    private WebElement indiaOption;

    @FindBy(className = "a-button-input")
    private WebElement continueButton;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(id = "signInSubmit")
    private WebElement signInSubmit;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String phone, String password) {
        hoverOverElement(accountList);
        waitForClickability(signInButton).click();
        waitForVisibility(By.id("ap_email_login"));
        emailField.sendKeys(phone);

        try {
            regionSignIn.click();
            indiaOption.click();
        } catch (Exception e) {
            System.out.println("Region switch not needed.");
        }

        continueButton.click();
        passwordField.sendKeys(password);
        signInSubmit.click();
    }
}
