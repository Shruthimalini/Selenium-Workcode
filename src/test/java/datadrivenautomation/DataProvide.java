package datadrivenautomation;

import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.util.ArrayList;

public class DataProvide {

	@DataProvider(name = "excelDataProvider")
	public Object[][] getExcelData() throws IOException {
		ExcelDataDriven dataReader = new ExcelDataDriven();

		String[] testCases = { "Login", "Purchase" };

		ArrayList<ArrayList<String>> combinedData = new ArrayList<>();

		for (String testCase : testCases) {
			ArrayList<ArrayList<String>> allRows = dataReader.getAllData(testCase);
			combinedData.addAll(allRows);
		}

		Object[][] data = new Object[combinedData.size()][1];

		for (int i = 0; i < combinedData.size(); i++) {
			data[i][0] = combinedData.get(i);
		}
		return data;
	}
}