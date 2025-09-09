package datadrivenautomation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


	public class ResultToPrint {

	    public void writeResult(ArrayList<String> testDataRow, String resultMessage) throws IOException {
	        FileInputStream file = new FileInputStream("C:\\Users\\MaliniR\\Documents\\Test.xlsx");
	        XSSFWorkbook workbook = new XSSFWorkbook(file);
	        file.close();

	        XSSFSheet sheet = workbook.getSheet("demo");
	        if (sheet == null) {
	            workbook.close();
	            throw new RuntimeException("Sheet 'demo' not found");
	        }

	        int resultColIndex = -1;
	        Row headerRow = sheet.getRow(0);
	        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
	            if (headerRow.getCell(i).getStringCellValue().equalsIgnoreCase("Result")) {
	                resultColIndex = i;
	                break;
	            }
	        }

	        if (resultColIndex == -1) {
	            workbook.close();
	            throw new RuntimeException("Result column not found!");
	        }

	        
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            Row row = sheet.getRow(i);
	            if (row == null) continue;

	            boolean match = true;
	            for (int j = 0; j < testDataRow.size(); j++) {
	                Cell cell = row.getCell(j);
	                String cellValue = (cell == null) ? "" : cell.toString().trim();
	                String expectedValue = testDataRow.get(j).trim();

	                if (!cellValue.equalsIgnoreCase(expectedValue)) {
	                    match = false;
	                    break;
	                }
	            }

	            if (match) {
	                Cell resultCell = row.getCell(resultColIndex);
	                if (resultCell == null) {
	                    resultCell = row.createCell(resultColIndex);
	                }
	                resultCell.setCellValue(resultMessage);
	                break;
	            }
	        }

	        FileOutputStream out = new FileOutputStream("C:\\Users\\MaliniR\\Documents\\Test.xlsx");
	        workbook.write(out);
	        out.close();
	        workbook.close();

	        System.out.println("✅ Result written: " + resultMessage);
	    }
	}
