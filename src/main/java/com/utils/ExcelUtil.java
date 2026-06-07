package com.orangehrm.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ExcelUtil - Apache POI utility for data-driven testing.
 * Reads test data from .xlsx files in src/test/resources/testdata/
 */
public class ExcelUtil {

    private static final Logger log = LogManager.getLogger(ExcelUtil.class);

    private ExcelUtil() {}

    /**
     * Read all rows from a given sheet as a list of maps (header -> cell value).
     *
     * @param filePath  Absolute or relative path to the .xlsx file
     * @param sheetName Sheet name to read
     * @return          List of row data maps
     */
    public static List<Map<String, String>> readSheetData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                log.error("Sheet '{}' not found in file: {}", sheetName, filePath);
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                log.warn("Header row is empty in sheet: {}", sheetName);
                return data;
            }

            int colCount = headerRow.getLastCellNum();
            List<String> headers = new ArrayList<>();
            for (int i = 0; i < colCount; i++) {
                Cell cell = headerRow.getCell(i);
                headers.add(cell != null ? getCellValue(cell) : "Column_" + i);
            }

            for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                if (row == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int colIdx = 0; colIdx < colCount; colIdx++) {
                    Cell cell = row.getCell(colIdx);
                    rowData.put(headers.get(colIdx), cell != null ? getCellValue(cell) : "");
                }
                data.add(rowData);
            }

            log.info("Read {} rows from sheet '{}' in '{}'", data.size(), sheetName, filePath);

        } catch (IOException e) {
            log.error("Error reading Excel file '{}': {}", filePath, e.getMessage());
            throw new RuntimeException("Failed to read Excel: " + e.getMessage());
        }

        return data;
    }

    /**
     * Read a single column of data by column name from a sheet.
     */
    public static List<String> readColumnData(String filePath, String sheetName, String columnName) {
        List<String> columnData = new ArrayList<>();
        for (Map<String, String> row : readSheetData(filePath, sheetName)) {
            if (row.containsKey(columnName)) {
                columnData.add(row.get(columnName));
            }
        }
        return columnData;
    }

    /**
     * Return sheet data as a 2D Object array for TestNG @DataProvider.
     */
    public static Object[][] getDataAsObjectArray(String filePath, String sheetName) {
        List<Map<String, String>> rows = readSheetData(filePath, sheetName);
        Object[][] result = new Object[rows.size()][1];
        for (int i = 0; i < rows.size(); i++) {
            result[i][0] = rows.get(i);
        }
        return result;
    }

    private static String getCellValue(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }
}
