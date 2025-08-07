package datadrivenauomation;
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
	        public void writeResult(String testcaseName,String resultMessage) throws IOException {
			FileInputStream file = new FileInputStream("C:\\Users\\MaliniR\\Documents\\Test.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			int sheets = workbook.getNumberOfSheets();
			for (int i = 0; i < sheets; i++) {
				if (workbook.getSheetName(i).equals("demo")) {
					XSSFSheet sheet = workbook.getSheetAt(i);
					Iterator<Row> rows = sheet.iterator();
					Row firstrow = rows.next();
					 int testcaseColIndex = 0;
			            int resultColIndex = 0;

			            
			            Iterator<Cell> cells = firstrow.cellIterator();
			            int colIndex = 0;
			            while (cells.hasNext()) {
			                Cell cell = cells.next();
			                String header = cell.getStringCellValue();
			                if (header.equalsIgnoreCase("Testcases")) {
			                    testcaseColIndex = colIndex;
			                }
			                if (header.equalsIgnoreCase("Result")) {
			                    resultColIndex = colIndex;
			                }
			                colIndex++;
			            }

			            if (testcaseColIndex == -1 || resultColIndex == -1) {
			                System.out.println("Either Testcases or Result column not found!");
			                workbook.close();
			                file.close();
			                return;
			            }
			            while (rows.hasNext()) {
			                Row r = rows.next();
			                Cell testcaseCell = r.getCell(testcaseColIndex);
			                if (testcaseCell != null && testcaseCell.getStringCellValue().equalsIgnoreCase(testcaseName)) {
			                    Cell resultCell = r.getCell(resultColIndex);
			                   if (resultCell == null) {
			                       resultCell = r.createCell(resultColIndex);
			                    resultCell.setCellValue(resultMessage);
			                    break;
			                    }
			                    
			                }
			            }

			            file.close();

			            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\MaliniR\\Documents\\Test.xlsx");
			            workbook.write(outputStream);
			            outputStream.close();
			            workbook.close();

			            System.out.println("Message written to Excel for testcase: " + testcaseName);
			            break; 
			        }
			    }
	        }}