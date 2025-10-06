package configDrivenAutomation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import amazonFrameWorkAutomation.AbstractComponent;

public class KeywordExecutor extends AbstractComponentConfig {

    ConfigExcelDrivenData elementReader;

    public KeywordExecutor(WebDriver driver) {
        super(driver);
        this.elementReader = new ConfigExcelDrivenData();
    }

    public void execute(String stepsExcelPath, String stepsSheet, Map<String, String> testData) throws IOException {
        List<Map<String, String>> steps = elementReader.getAllSteps(stepsExcelPath, stepsSheet);

        for (Map<String, String> step : steps) {
            String action = step.get("Action");
            if (action == null || action.isEmpty()) {
                System.out.println("Skipping step due to empty Action");
                continue;
            }

            action = action.toLowerCase();
            String elementType = step.get("ElementType") != null ? step.get("ElementType").toLowerCase() : "";
            String locatorType = step.get("LocatorType");
            String locatorValue = step.get("LocatorValue");
            String testDataKey = step.get("TestData");

            WebElement element = null;
            if (locatorType != null && locatorValue != null && !locatorType.isEmpty() && !locatorValue.isEmpty()) {
                element = findElement(locatorType, locatorValue);
            }

            String data = (testDataKey != null && !testDataKey.isEmpty()) ? testData.get(testDataKey) : "";
            performAction(action, elementType, element, data, locatorType, locatorValue);
        }
    }

    private WebElement findElement(String locatorType, String locatorValue) {
        return driver.findElement(getBy(locatorType, locatorValue));
    }

    private void performAction(String action, String elementType, WebElement element, String data,
                               String locatorType, String locatorValue) {
        switch (action) {
            case "hover":
                if (element != null) hoverOverElement(element);
                break;

            case "click":
                if (element != null) element.click();
                break;

            case "entertext":
                if (element != null && (elementType.equalsIgnoreCase("textbox") || elementType.equalsIgnoreCase("input")))
                    element.sendKeys(data);
                break;

            case "dismiss":
                if (element != null) waitForVisibility(element);
                break;

            case "clicklistbytext":
                if (locatorType != null && locatorValue != null && !data.isEmpty()) {
                    clickListByText(locatorType, locatorValue, data);
                }
                break;

            case "switchwindow":
                switchToNewWindow();
                break;

            default:
                System.out.println("Unknown action: " + action);
        }
    }
}
