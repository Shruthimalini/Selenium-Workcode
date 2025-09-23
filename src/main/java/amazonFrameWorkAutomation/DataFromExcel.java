package amazonFrameWorkAutomation;
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

public class DataFromExcel {
	
	public ArrayList<ArrayList<String>> getAllData(String testcaseName) throws IOException {
	FileInputStream file = new FileInputStream("C:\\Users\\MaliniR\\Documents\\AmazonErrorData.xlsx");
    XSSFWorkbook workbook = new XSSFWorkbook(file);
    ArrayList<ArrayList<String>> allData = new ArrayList<>();

    int sheets = workbook.getNumberOfSheets();
    for (int i = 0; i < sheets; i++) {
        if (workbook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            Iterator<Row> rows = sheet.iterator();

           
            Row firstrow = rows.next();
            Iterator<Cell> cells = firstrow.cellIterator();
            int k = 0;
            int columnIndex = 0;
            while (cells.hasNext()) {
                Cell cell = cells.next();
                if (cell.getStringCellValue().equalsIgnoreCase("Testcase")) {
                    columnIndex = k;
                }
                k++;
            }

            
            while (rows.hasNext()) {
                Row row = rows.next();
                Cell testCaseCell = row.getCell(columnIndex);

                if (testCaseCell != null && testCaseCell.getStringCellValue().equalsIgnoreCase(testcaseName)) {
                    ArrayList<String> rowData = new ArrayList<>();
                    Iterator<Cell> cellValues = row.cellIterator();
                    while (cellValues.hasNext()) {
                        Cell c = cellValues.next();
                        if (c.getCellType() == CellType.STRING) {
                            rowData.add(c.getStringCellValue());
                        } else {
                            rowData.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                        }
                    }
                    allData.add(rowData); 
                }
            }
        }
    }

    workbook.close();
    return allData;
}

public static void main(String[] args) {

}
}

