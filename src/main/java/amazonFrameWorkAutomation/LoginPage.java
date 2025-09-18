package amazonFrameWorkAutomation;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
	private WebElement indiaOption0;

	@FindBy(className = "a-button-input")
	private WebElement continueButton;

	@FindBy(xpath = "//input[@type='password']")
	private WebElement passwordField;

	@FindBy(id = "signInSubmit")
	private WebElement signInSubmit;

	@FindBy(css = ".glow-toaster")
	private WebElement toasterPopup;

	@FindBy(xpath = "//span[contains(@class,'glow-toaster-button-dismiss')]")
	private WebElement toasterDismissBtn;

	@FindBy(id = "icp-nav-flyout")
	private WebElement changeCountryMenu;

	@FindBy(xpath = "//div[text()='Change country/region.']")
	private WebElement changeCountryOption;

	@FindBy(className = "a-dropdown-prompt")
	private WebElement dropdownPrompt;

	@FindBy(xpath = "//a[contains(@class,'a-dropdown-link') and contains(text(),'India')]")
	private WebElement indiaOption1;

	@FindBy(css = "input.a-button-input[type='submit']")
	private WebElement goToWebsiteBtn;

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void login(String phone, String password) {
		hoverOverElement(accountList);
		waitForClickability(signInButton).click();
		waitForVisibility(emailField).sendKeys(phone);

		try {
			waitForClickability(regionSignIn).click();
			waitForClickability(indiaOption0).click();
		} catch (TimeoutException e) {
			System.out.println("Region switch not needed.");
		}

		continueButton.click();
		waitForVisibility(passwordField).sendKeys(password);
		signInSubmit.click();
	}

	public void loginIndia() {

		dismissToasterIfVisible();

		hoverOverElement(changeCountryMenu);
		waitForVisibility(changeCountryOption).click();
		waitForClickability(dropdownPrompt).click();
		waitForClickability(indiaOption1).click();

		waitForClickability(goToWebsiteBtn).click();

		switchToNewWindow();

		System.out.println("Switched to Amazon India.");
	}

	private void dismissToasterIfVisible() {
		try {
			waitForVisibility(toasterPopup);
			toasterDismissBtn.click();
			System.out.println("Toaster dismissed.");
		} catch (TimeoutException e) {
			System.out.println("No toaster popup appeared.");
		}
	}

}
