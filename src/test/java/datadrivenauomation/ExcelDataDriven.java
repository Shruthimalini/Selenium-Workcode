package datadrivenauomation;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataDriven {

	public ArrayList<String> getData(String testcaseName) throws IOException {
		FileInputStream file = new FileInputStream("C:\\Users\\MaliniR\\Documents\\Test.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		ArrayList<String> data = new ArrayList<>();
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equals("demo")) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				Row firstrow = rows.next();
				Iterator<Cell> cells = firstrow.cellIterator();
				int k = 0;
				int columnindex = 0;
				while (cells.hasNext()) {
					Cell values = cells.next();
					if (values.getStringCellValue().equalsIgnoreCase("Testcase")) {
						columnindex = k;
					}
					k++;
				}

				System.out.println(columnindex);
				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(columnindex).getStringCellValue().equalsIgnoreCase(testcaseName)) {
						Iterator<Cell> cellvalues = r.cellIterator();
						while (cellvalues.hasNext()) {
							Cell c = cellvalues.next();
							if (c.getCellType() == CellType.STRING) {
								data.add(c.getStringCellValue());
							} else {
								data.add(NumberToTextConverter.toText(c.getNumericCellValue()));

							}

						}
						break;

					}
				}

			}

		}
		return data;
	}

	public static void main(String[] args) {

	}
}
