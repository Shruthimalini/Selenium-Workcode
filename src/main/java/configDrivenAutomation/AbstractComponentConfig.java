package configDrivenAutomation;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponentConfig {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    public AbstractComponentConfig(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
    }

    protected By getBy(String locatorType, String locatorValue) {
        switch (locatorType.toLowerCase()) {
            case "id": return By.id(locatorValue);
            case "xpath": return By.xpath(locatorValue);
            case "css": return By.cssSelector(locatorValue);
            case "classname": return By.className(locatorValue);
            case "name": return By.name(locatorValue);
            case "tagname": return By.tagName(locatorValue);
            case "linktext": return By.linkText(locatorValue);
            case "partiallinktext": return By.partialLinkText(locatorValue);
            default: throw new IllegalArgumentException("Invalid locator type: " + locatorType);
        }
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void hoverOverElement(WebElement element) {
        actions.moveToElement(element).perform();
    }

    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForStaleness(WebElement element) {
        wait.until(ExpectedConditions.stalenessOf(element));
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchToNewWindow() {
        String current = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(current)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public void acceptAlertIfPresent() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (TimeoutException e) {
            System.out.println("No alert found.");
        }
    }

    public void clickListByText(String locatorType, String locatorValue, String text) {
        try {
            List<WebElement> items = driver.findElements(getBy(locatorType, locatorValue));
            for (WebElement item : items) {
                if (item.getText().toLowerCase().contains(text.toLowerCase())) {
                    scrollToElement(item);
                    item.click();
                    System.out.println("Clicked product: " + text);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error clicking list by text: " + e.getMessage());
        }
    }
}
