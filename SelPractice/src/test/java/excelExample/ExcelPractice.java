package excelExample;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
 
public class ExcelPractice {
	@Test	
	public void getExcel() throws IOException {
	FileInputStream fi = new FileInputStream("C:\\Users\\MaliniR\\WorkBook.xlsx");
	XSSFWorkbook workbook = new XSSFWorkbook(fi);
	XSSFSheet sheet = workbook.getSheetAt(0);
	int rowcount=sheet.getPhysicalNumberOfRows();
	XSSFRow row=sheet.getRow(0);
	int columncount=row.getLastCellNum();
	Object data[][]=new Object[rowcount-1][columncount];
	
	for(int i=0;i<rowcount-1;i++) {
		row=sheet.getRow(i);
		for(int j=0;j<columncount;j++) {
			System.out.println(row.getCell(j));
		}
	}
	


	
	
}}
