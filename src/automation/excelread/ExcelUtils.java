package automation.excelread;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import automation.baseclass.TestBase;

public class ExcelUtils extends TestBase {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    public ExcelUtils() throws IOException {

        String excelPath = System.getProperty("user.dir") + "/src/ExcelData/TestData.xlsx";

        FileInputStream fis = new FileInputStream(excelPath);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet("Ins");
    }

    public static int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public static int getColCount() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    public static String getCellDataString(int rowNum, int colNum) {
        return sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
    }

    public static double getCellDataNumber(int rowNum, int colNum) {
        return sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
    }

    public static void closeWorkbook() throws IOException {
        workbook.close();
    }
}