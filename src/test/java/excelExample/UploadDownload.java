package excelExample;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class UploadDownload {
	
	public static void main(String[] args) throws IOException {
		String filepath = "C:\\Users\\MaliniR\\Documents\\download.xlsx";
		String fruitname = "Apple";
		//String columnName="price";
		
		
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("downloadButton"))).click();

		int col = getColumnNumber(filepath,"price");
		int row = getRowNumber(filepath,"Apple" );
		updateCell(filepath, row, col, "350");

		WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
		upload.sendKeys(filepath);

		By toast = By.cssSelector(".Toastify__toast-body div:nth-child(2)");

		wait.until(ExpectedConditions.visibilityOfElementLocated(toast));
		String toastText = driver.findElement(toast).getText();
		System.out.println(toastText);
		Assert.assertEquals("Updated Excel Data Successfully.", toastText);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(toast));

		String pricecolumn = driver.findElement(By.xpath("//div[text()='Price']")).getAttribute("data-column-id");
		String price = driver.findElement(By.xpath("//div[text()='" + fruitname
				+ "']/parent::div/parent::div/div[@id='cell-" + pricecolumn + "-undefined']")).getText();
         System.out.println(price);
		Assert.assertEquals("350", price);

		driver.quit();
	}

	
	private static int getColumnNumber(String filepath, String columnName) throws IOException {
		FileInputStream file = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		Row headerRow = rows.next(); 
		Iterator<Cell> cells = headerRow.cellIterator();

		int colIndexPrice = 0;
		while (cells.hasNext()) {
			Cell cell = cells.next();
			if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
				return colIndexPrice;
				
			}
			colIndexPrice++;
		}
		workbook.close();
		throw new RuntimeException("Column '" + columnName + "' not found!");
	}

	private static int getRowNumber(String filepath, String fruitName) throws IOException {
		FileInputStream file = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);

	
		Iterator<Row> rows = sheet.iterator();
		Row headerRow = rows.next();
		Iterator<Cell> headerCells = headerRow.cellIterator();

		int colIndexApple = 0;
		int fruitNameColIndex = 0;
		while (headerCells.hasNext()) {
			Cell cell = headerCells.next();
			if (cell.getStringCellValue().equalsIgnoreCase("fruit_name")) {
				fruitNameColIndex = colIndexApple;
				break;
			}
			colIndexApple++;
		}
		if (fruitNameColIndex == -1) {
			workbook.close();
			throw new RuntimeException("Fruit-Name column not found!");
		}

		
		int rowIndex = 1; 
		while (rows.hasNext()) {
			Row row = rows.next();
			Cell cell = row.getCell(fruitNameColIndex);
			if (cell != null && cell.getStringCellValue().equalsIgnoreCase(fruitName)) {
				workbook.close();
				return rowIndex;
			}
			rowIndex++;
		}
		workbook.close();
		throw new RuntimeException("Fruit '" + fruitName + "' not found!");
	}
	private static void updateCell(String filename, int row, int column, String newValue) throws IOException {
	    FileInputStream fis = new FileInputStream(filename);
	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
	    XSSFSheet sheet = workbook.getSheetAt(0);
	    fis.close();
	    Row targetRow = sheet.getRow(row);
	    if (targetRow == null) {
	        targetRow = sheet.createRow(row);
	    }
	    Cell targetCell = targetRow.getCell(column);
	    if (targetCell == null) {
	        targetCell = targetRow.createCell(column);
	    }

	    targetCell.setCellValue(newValue);

	    FileOutputStream fos = new FileOutputStream(filename);
	    workbook.write(fos);
	    fos.close();
	    workbook.close();
	}
	    

}