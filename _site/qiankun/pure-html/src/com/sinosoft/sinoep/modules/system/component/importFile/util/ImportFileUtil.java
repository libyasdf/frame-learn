package com.sinosoft.sinoep.modules.system.component.importFile.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportFileUtil {

    /**
     * 获取workbook工作空间
     * @param
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static Workbook getWorkBook(InputStream is,String filePath) throws IOException, InvalidFormatException {
        Workbook workbook =null;
        if(isExcel2003(filePath)){
            workbook = new HSSFWorkbook(is);
        }else{
            workbook = new XSSFWorkbook(is);
        }
    return workbook;
    }

    // 验证是否是excel2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //验证是否是excel2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
    /**
     * 根据当前row的i列获取单元格值
     * @param row
     * @param i
     * @return
     */
    public static String getRowCellValue(Row row,Short i){
        Cell cell = row.getCell(i);
        return getCellTypes(cell);
    }

    /**
     * 获取sheet的num
     * @param workbook
     * @return
     */
    public static int getSheetNumByWorkBook(Workbook workbook){
        int sheetNum = workbook.getNumberOfSheets();
        return sheetNum;
    }

    /**
     * 数据类型转换
     * @param cell
     * @return
     */
    public static String getCellTypes(Cell cell){
        String cellValue = null;
        if (null != cell) {
            // 以下是判断数据的类型
            switch (cell.getCellType()){
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    // 处理日期格式、时间格式
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date d = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = formater.format(d);
                    }
                    else{
                        cellValue = cell.getNumericCellValue() + "";
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    // cellValue = cell.getCellFormula() + "";
                    try {
                        DecimalFormat df = new DecimalFormat("0.0000");
                        cellValue = String.valueOf(df.format(cell.getNumericCellValue()));

                    } catch (IllegalStateException e) {
                        cellValue = String.valueOf(cell.getRichStringCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    cellValue = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
                    break;
            }
        }else{
        	cellValue = "";
        }
        return cellValue.replaceAll("//s","");
      }
}
