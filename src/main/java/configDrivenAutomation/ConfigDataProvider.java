package configDrivenAutomation;

import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ConfigDataProvider {
	ConfigExcelDrivenData excelReader = new ConfigExcelDrivenData();

	@DataProvider(name = "userDataProvider")
	public Object[][] getUserData() throws IOException {
		String excelPath = "C:\\Users\\MaliniR\\Documents\\AmazonKeyData.xlsx"; 
		String sheetName = "Users"; 

		List<Map<String, String>> allUsersData = excelReader.getAllTestData(excelPath, sheetName);

		Object[][] data = new Object[allUsersData.size()][1];
		for (int i = 0; i < allUsersData.size(); i++) {
			data[i][0] = allUsersData.get(i); 
		}
		return data;
	}
}
