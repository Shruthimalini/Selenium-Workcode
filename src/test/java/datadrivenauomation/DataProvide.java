package datadrivenauomation;

import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.util.ArrayList;

public class DataProvide {

	@DataProvider(name = "excelDataProvider")
	public Object[][] getExcelData() throws IOException {
		ExcelDataDriven dataReader = new ExcelDataDriven();

		String[] testCases = { "Login", "Purchase" };

		Object[][] data = new Object[testCases.length][1];

		for (int i = 0; i < testCases.length; i++) {
			ArrayList<String> testData = dataReader.getData(testCases[i]);
			data[i][0] = testData;
		}

		return data;
	}

}
