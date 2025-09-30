package configDrivenAutomation;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.DataProvider;

public class KeywordDataProvider {

    @DataProvider(name = "keywordExcelData")
    public Object[][] getExcelData() throws IOException {
        ExcelDrivenData excelReader = new ExcelDrivenData();
        List<String> testCaseIds = excelReader.getAllTestCaseIds("C:\\Users\\MaliniR\\Documents\\KeywordSteps.xlsx");

        Object[][] data = new Object[testCaseIds.size()][1];
        for (int i = 0; i < testCaseIds.size(); i++) {
            data[i][0] = testCaseIds.get(i);
        }
        return data;
    }
}
