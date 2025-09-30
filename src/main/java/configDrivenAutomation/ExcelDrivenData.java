package configDrivenAutomation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDrivenData {

    /**
     * Reads all sheets in an Excel file.
     * Returns a map with sheetName as key and sheet data as List of Maps.
     */
    public Map<String, List<Map<String, String>>> readAllSheets(String excelPath) throws IOException {
        Map<String, List<Map<String, String>>> sheetDataMap = new HashMap<>();

        try (FileInputStream file = new FileInputStream(excelPath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
                XSSFSheet sheet = workbook.getSheetAt(s);
                String sheetName = sheet.getSheetName();

                Row headerRow = sheet.getRow(0);
                if (headerRow == null) continue;

                List<Map<String, String>> sheetData = new ArrayList<>();

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < row.getLastCellNum(); j++) {
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
                                    value = String.valueOf((long) cell.getNumericCellValue());
                                    break;
                                case BOOLEAN:
                                    value = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                default:
                                    value = "";
                            }
                        }
                        rowData.put(header, value);
                    }
                    sheetData.add(rowData);
                }
                sheetDataMap.put(sheetName, sheetData);
            }
        }

        return sheetDataMap;
    }

    /**
     * Reads configuration (Browser, URL) from the first sheet containing them.
     */
    public Map<String, String> getConfiguration(String excelPath) throws IOException {
        Map<String, List<Map<String, String>>> allSheets = readAllSheets(excelPath);

        for (List<Map<String, String>> sheetData : allSheets.values()) {
            for (Map<String, String> row : sheetData) {
                if (row.containsKey("Browser") && row.containsKey("URL")) {
                    Map<String, String> config = new HashMap<>();
                    config.put("Browser", row.get("Browser"));
                    config.put("URL", row.get("URL"));
                    return config;
                }
            }
        }
        throw new IOException("No configuration found in any sheet!");
    }

    /**
     * Returns all unique TestCaseIDs from all sheets.
     */
    public List<String> getAllTestCaseIds(String excelPath) throws IOException {
        Map<String, List<Map<String, String>>> allSheets = readAllSheets(excelPath);
        Set<String> testCaseIds = new LinkedHashSet<>();

        for (List<Map<String, String>> sheetData : allSheets.values()) {
            for (Map<String, String> row : sheetData) {
                String testCaseId = row.get("TestCaseID");
                if (testCaseId != null && !testCaseId.trim().isEmpty()) {
                    testCaseIds.add(testCaseId.trim());
                }
            }
        }
        return new ArrayList<>(testCaseIds);
    }

    /**
     * Returns all steps for a given TestCaseID and sheet.
     */
    public List<Map<String, String>> getStepsForTestCase(String excelPath, String sheetName, String testCaseId)
            throws IOException {
        Map<String, List<Map<String, String>>> allSheets = readAllSheets(excelPath);
        List<Map<String, String>> sheetData = allSheets.get(sheetName);

        if (sheetData == null) throw new IOException("Sheet '" + sheetName + "' not found!");

        List<Map<String, String>> stepsForTestCase = new ArrayList<>();
        for (Map<String, String> row : sheetData) {
            if (testCaseId.equalsIgnoreCase(row.get("TestCaseID"))) {
                stepsForTestCase.add(row);
            }
        }
        return stepsForTestCase;
    }

    /**
     * Reads test data for a given TestCaseID from a sheet.
     */
    public Map<String, String> getTestDataForTestCase(String excelPath, String sheetName, String testCaseId)
            throws IOException {
        try (FileInputStream file = new FileInputStream(excelPath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IOException("Sheet '" + sheetName + "' not found!");

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return Collections.emptyMap();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell testCaseCell = row.getCell(0);
                if (testCaseCell != null && testCaseCell.getStringCellValue().equalsIgnoreCase(testCaseId)) {
                    Map<String, String> testDataMap = new HashMap<>();
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell headerCell = headerRow.getCell(j);
                        Cell dataCell = row.getCell(j);

                        String key = headerCell != null ? headerCell.getStringCellValue().trim() : "";
                        String value = "";

                        if (dataCell != null) {
                            switch (dataCell.getCellType()) {
                                case STRING:
                                    value = dataCell.getStringCellValue().trim();
                                    break;
                                case NUMERIC:
                                    value = String.valueOf((long) dataCell.getNumericCellValue());
                                    break;
                                case BOOLEAN:
                                    value = String.valueOf(dataCell.getBooleanCellValue());
                                    break;
                                default:
                                    value = "";
                            }
                        }
                        testDataMap.put(key, value);
                    }
                    return testDataMap;
                }
            }
        }
        return Collections.emptyMap();
    }
}
