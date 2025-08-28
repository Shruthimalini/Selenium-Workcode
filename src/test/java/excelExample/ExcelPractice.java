package excelExample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class ExcelPractice {
	DataFormatter format = new DataFormatter();

	@Test(dataProvider = "driveTest")
	public void testCaseData(String greetings, String communication, String id) {
		System.out.println(greetings + " " + communication + " " + id);
	}

	@DataProvider(name = "driveTest")
	public Object[][] getData() throws IOException {
		FileInputStream file = new FileInputStream("E:\\Users\\Lenovo\\Documents\\Dataread.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		XSSFRow row = sheet.getRow(0);
		int columncount = row.getLastCellNum();
		Object data[][] = new Object[rowCount - 1][columncount];
		for (int i = 1; i < rowCount; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j < columncount; j++) {
				XSSFCell cells = row.getCell(j);
				data[i-1][j] = format.formatCellValue(cells);
			}

		}
		return data;

	}
}