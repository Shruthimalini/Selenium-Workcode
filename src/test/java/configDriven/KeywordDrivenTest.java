package configDriven;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import configDrivenAutomation.ConfigExcelDrivenData;
import configDrivenAutomation.KeywordExecutor;

public class KeywordDrivenTest extends BaseTestConfig {

    ConfigExcelDrivenData excelReader;
    KeywordExecutor executor;
    String stepsExcelPath;

    @BeforeClass
    public void setUp() {
        try {
            initializeDriver();
            goTo();
            excelReader = new ConfigExcelDrivenData();
            executor = new KeywordExecutor(getDriver());

            stepsExcelPath = "C:\\Users\\MaliniR\\Documents\\AmazonKeyData.xlsx"; 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DataProvider for all sheet names (POM names)
    @DataProvider(name = "sheetNameProvider")
    public Object[][] sheetNameProvider() throws IOException {
        List<String> sheetNames = excelReader.getSheetNames(stepsExcelPath);
        Object[][] data = new Object[sheetNames.size()][1];
        for (int i = 0; i < sheetNames.size(); i++) {
            data[i][0] = sheetNames.get(i);
        }
        return data;
    }

    // DataProvider for test data rows
    @DataProvider(name = "testDataProvider")
    public Object[][] testDataProvider() throws IOException {
        List<Map<String, String>> testDataList = excelReader.getAllTestData(stepsExcelPath, "Users");
        Object[][] data = new Object[testDataList.size()][1];
        for (int i = 0; i < testDataList.size(); i++) {
            data[i][0] = testDataList.get(i);
        }
        return data;
    }

    // Test method combining sheet names and test data
    @Test(dataProvider = "sheetNameProvider")
    public void runKeywordTest(String sheetName) {
        try {
            System.out.println("\n--- Executing sheet (POM): " + sheetName + " ---");

            List<Map<String, String>> testDataList = excelReader.getAllTestData(stepsExcelPath, "Users");

            for (Map<String, String> testData : testDataList) {
                executor.execute(stepsExcelPath, sheetName, testData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
}
