package configDrivenAutomation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConfigExcelDrivenData {

    /**
     * Returns list of all sheet names in a workbook.
     */
    public List<String> getSheetNames(String excelPath) throws IOException {
        List<String> sheetNames = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(excelPath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetNames.add(workbook.getSheetName(i));
            }
        }
        return sheetNames;
    }

    /**
     * Reads a single sheet (POM or TestData) and returns data as a list of maps.
     */
    public List<Map<String, String>> readSheet(String excelPath, String sheetName) throws IOException {
        List<Map<String, String>> sheetData = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(excelPath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IOException("Sheet not found: " + sheetName);

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return sheetData;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    Cell headerCell = headerRow.getCell(j);
                    if (headerCell == null) continue;

                    String header = headerCell.getStringCellValue().trim();
                    String value = "";

                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue().trim();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    value = new DataFormatter().formatCellValue(cell);
                                } else {
                                    value = String.valueOf((long) cell.getNumericCellValue());
                                }
                                break;
                            case BOOLEAN:
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                value = cell.getCellFormula();
                                break;
                            default:
                                value = "";
                        }
                    }
                    rowData.put(header, value);
                }
                sheetData.add(rowData);
            }
        }
        return sheetData;
    }

    /**
     * Gets all test steps for a given sheet (POM).
     */
    public List<Map<String, String>> getAllSteps(String excelPath, String sheetName) throws IOException {
        return readSheet(excelPath, sheetName);
    }

    /**
     * Gets all test data for a given sheet (used for DataProvider).
     */
    public List<Map<String, String>> getAllTestData(String excelPath, String sheetName) throws IOException {
        return readSheet(excelPath, sheetName);
    }
}

