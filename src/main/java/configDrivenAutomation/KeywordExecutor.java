package configDrivenAutomation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class KeywordExecutor {

    WebDriver driver;
    AbstractComponentConfig component;
    ExcelDrivenData elementReader;

    public KeywordExecutor(WebDriver driver) {
        this.driver = driver;
        this.component = new AbstractComponentConfig(driver);
        this.elementReader = new ExcelDrivenData();
    }

    public void execute(String stepsExcelPath, String stepsSheet, String testDataExcelPath,
                        String testDataSheet, String testCaseId) throws IOException {

        List<Map<String, String>> steps = elementReader.getStepsForTestCase(stepsExcelPath, stepsSheet, testCaseId);
        Map<String, String> testData = elementReader.getTestDataForTestCase(testDataExcelPath, testDataSheet, testCaseId);

        for (Map<String, String> step : steps) {
            String action = step.get("Action").toLowerCase();
            String elementType = step.get("ElementType").toLowerCase();
            String locatorType = step.get("LocatorType");
            String locatorValue = step.get("LocatorValue");
            String testDataKey = step.get("TestData");

            WebElement element = findElement(locatorType, locatorValue);
            String data = (testDataKey != null && !testDataKey.isEmpty()) ? testData.get(testDataKey) : "";

            performAction(action, elementType, element, data);
        }
    }

    private WebElement findElement(String locatorType, String locatorValue) {
        switch (locatorType.toLowerCase()) {
            case "id": return driver.findElement(By.id(locatorValue));
            case "xpath": return driver.findElement(By.xpath(locatorValue));
            case "css": return driver.findElement(By.cssSelector(locatorValue));
            case "classname": return driver.findElement(By.className(locatorValue));
            default: throw new RuntimeException("Invalid locator type: " + locatorType);
        }
    }

    private void performAction(String action, String elementType, WebElement element, String data) {
        switch (action) {
            case "hover": component.hoverOverElement(element); break;
            case "click": element.click(); break;
            case "entertext": if (elementType.equals("textbox") || elementType.equals("input")) element.sendKeys(data); break;
            case "dismiss": element.click(); break;
            case "switchwindow": component.switchToNewWindow(); break;
            default: System.out.println("Unknown action: " + action);
        }
    }
}
