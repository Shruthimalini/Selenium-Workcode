package seleniumpractice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Assignment5 {
	public static void main(String[] args) throws IOException {
		FileInputStream file = new FileInputStream("C:\\Users\\MaliniR\\Documents\\DemoData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		int worksheets = workbook.getNumberOfSheets();
		for (int i = 0; i < worksheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("demo")) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				Row firstrow = rows.next();
				Iterator<Cell> cells = firstrow.cellIterator();
				int k = 0;
				int columnindex = 0;
				while (cells.hasNext()) {
					Cell value = cells.next();
					if (value.getStringCellValue().equalsIgnoreCase("Testcase")) {
						columnindex = k;

					}
					k++;
				}
				System.out.println(columnindex);
				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(columnindex).getStringCellValue().equalsIgnoreCase("Purchase")) {
						Iterator<Cell> data = r.cellIterator();
						while (data.hasNext()) {
							System.out.println(data.next().getStringCellValue());

						}

					}
				}

			}
		}
	}
}
