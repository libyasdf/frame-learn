package com.sinosoft.sinoep.modules.system.component.export.excel.poi.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * POI工具类 功能点： 1、实现excel的sheet复制，复制的内容包括单元的内容、样式、注释
setMForeColor修改HSSFColor.YELLOW的色值，setMBorderColor修改PINK的色值
 * @author Administrator
 */
public final class PoiUtil {

    /**
     * 功能：拷贝sheet 实际调用 copySheet(targetSheet, sourceSheet, targetWork,
     * sourceWork, true)
     *
     * @param targetSheet
     * @param sourceSheet
     * @param targetWork
     * @param sourceWork
     */
    public static void copySheet(HSSFSheet targetSheet, HSSFSheet sourceSheet, HSSFWorkbook targetWork,
                                 HSSFWorkbook sourceWork, int index) throws Exception {
        if (targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null) {
            throw new IllegalArgumentException(
                    "调用PoiUtil.copySheet()方法时，targetSheet、sourceSheet、targetWork、sourceWork都不能为空，故抛出该异常！");
        }
        copySheet(targetSheet, sourceSheet, targetWork, sourceWork, true, index);
    }

    /**
     * 功能：拷贝sheet
     *
     * @param targetSheet
     * @param sourceSheet
     * @param targetWork
     * @param sourceWork
     * @param copyStyle
     *            boolean 是否拷贝样式
     */
    public static void copySheet(HSSFSheet targetSheet, HSSFSheet sourceSheet, HSSFWorkbook targetWork,
            HSSFWorkbook sourceWork, boolean copyStyle, int index) throws Exception {

        if (targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null) {
            throw new IllegalArgumentException(
                    "调用PoiUtil.copySheet()方法时，targetSheet、sourceSheet、targetWork、sourceWork都不能为空，故抛出该异常！");
        }

        // 复制源表中的行
        int maxColumnNum = 0;

        Map styleMap = (copyStyle) ? new HashMap() : null;

        HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch(); // 用于复制注释
        for (int i = sourceSheet.getFirstRowNum(); i <= index; i++) {
            HSSFRow sourceRow = sourceSheet.getRow(i);
            HSSFRow targetRow = targetSheet.createRow(i);

            if (sourceRow != null) {
                copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch, styleMap);
                if (sourceRow.getLastCellNum() > maxColumnNum) {
                    maxColumnNum = sourceRow.getLastCellNum();
                }
            }
        }

        // 复制源表中的合并单元格
        mergerRegion(targetSheet, sourceSheet);

        // 设置目标sheet的列宽
        for (int i = 0; i <= maxColumnNum; i++) {
            targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i) + 250);
        }

    }

    /**
     * 功能：拷贝row
     *
     * @param targetRow
     * @param sourceRow
     * @param styleMap
     * @param targetWork
     * @param sourceWork
     * @param targetPatriarch
     */
    public static void copyRow(HSSFRow targetRow, HSSFRow sourceRow, HSSFWorkbook targetWork, HSSFWorkbook sourceWork,
            HSSFPatriarch targetPatriarch, Map styleMap) throws Exception {
        if (targetRow == null || sourceRow == null || targetWork == null || sourceWork == null
                || targetPatriarch == null) {
            throw new IllegalArgumentException(
                    "调用PoiUtil.copyRow()方法时，targetRow、sourceRow、targetWork、sourceWork、targetPatriarch都不能为空，故抛出该异常！");
        }

        // 设置行高
        targetRow.setHeight(sourceRow.getHeight());

        for (int i = sourceRow.getFirstCellNum(); i <= sourceRow.getLastCellNum(); i++) {
            HSSFCell sourceCell = sourceRow.getCell(i);
            HSSFCell targetCell = targetRow.getCell(i);

            if (sourceCell != null) {
                if (targetCell == null) {
                    targetCell = targetRow.createCell(i);
                }

                // 拷贝单元格，包括内容和样式
                copyCell(targetCell, sourceCell, targetWork, sourceWork, styleMap);

                // 拷贝单元格注释
                copyComment(targetCell, sourceCell, targetPatriarch);
            }
        }
    }

    /**
     * 功能：拷贝cell，依据styleMap是否为空判断是否拷贝单元格样式
     *
     * @param targetCell
     *            不能为空
     * @param sourceCell
     *            不能为空
     * @param targetWork
     *            不能为空
     * @param sourceWork
     *            不能为空
     * @param styleMap
     *            可以为空
     */
    public static void copyCell(HSSFCell targetCell, HSSFCell sourceCell, HSSFWorkbook targetWork,
            HSSFWorkbook sourceWork, Map styleMap) {
        if (targetCell == null || sourceCell == null || targetWork == null || sourceWork == null) {
            throw new IllegalArgumentException(
                    "调用PoiUtil.copyCell()方法时，targetCell、sourceCell、targetWork、sourceWork都不能为空，故抛出该异常！");
        }

        // 处理单元格样式
        if (styleMap != null) {
            if (targetWork == sourceWork) {
                targetCell.setCellStyle(sourceCell.getCellStyle());
            } else {
                String stHashCode = "" + sourceCell.getCellStyle().hashCode();
                HSSFCellStyle targetCellStyle = (HSSFCellStyle) styleMap.get(stHashCode);
                if (targetCellStyle == null) {
                    targetCellStyle = targetWork.createCellStyle();
                    targetCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
                    styleMap.put(stHashCode, targetCellStyle);
                }

                targetCell.setCellStyle(targetCellStyle);
            }
        }

        // 处理单元格内容
        switch (sourceCell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            targetCell.setCellValue(sourceCell.getRichStringCellValue());
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            targetCell.setCellValue(sourceCell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            targetCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            targetCell.setCellValue(sourceCell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_ERROR:
            targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
            break;
        case HSSFCell.CELL_TYPE_FORMULA:
            targetCell.setCellFormula(sourceCell.getCellFormula());
            break;
        default:
            break;
        }
    }

    /**
     * 功能：拷贝comment
     *
     * @param targetCell
     * @param sourceCell
     * @param targetPatriarch
     */
    public static void copyComment(HSSFCell targetCell, HSSFCell sourceCell, HSSFPatriarch targetPatriarch)
            throws Exception {
        if (targetCell == null || sourceCell == null || targetPatriarch == null) {
            throw new IllegalArgumentException(
                    "调用PoiUtil.copyCommentr()方法时，targetCell、sourceCell、targetPatriarch都不能为空，故抛出该异常！");
        }

        // 处理单元格注释
        HSSFComment comment = sourceCell.getCellComment();
        if (comment != null) {
            HSSFComment newComment = targetPatriarch.createComment(new HSSFClientAnchor());
            newComment.setAuthor(comment.getAuthor());
            newComment.setColumn(comment.getColumn());
            newComment.setFillColor(comment.getFillColor());
            newComment.setHorizontalAlignment(comment.getHorizontalAlignment());
            newComment.setLineStyle(comment.getLineStyle());
            newComment.setLineStyleColor(comment.getLineStyleColor());
            newComment.setLineWidth(comment.getLineWidth());
            newComment.setMarginBottom(comment.getMarginBottom());
            newComment.setMarginLeft(comment.getMarginLeft());
            newComment.setMarginTop(comment.getMarginTop());
            newComment.setMarginRight(comment.getMarginRight());
            newComment.setNoFill(comment.isNoFill());
            newComment.setRow(comment.getRow());
            newComment.setShapeType(comment.getShapeType());
            newComment.setString(comment.getString());
            newComment.setVerticalAlignment(comment.getVerticalAlignment());
            newComment.setVisible(comment.isVisible());
            targetCell.setCellComment(newComment);
        }
    }

    /**
     * 功能：复制原有sheet的合并单元格到新创建的sheet
     *
     * @param sourceSheet
     */
    public static void mergerRegion(HSSFSheet targetSheet, HSSFSheet sourceSheet) throws Exception {
        if (targetSheet == null || sourceSheet == null) {
            throw new IllegalArgumentException("调用PoiUtil.mergerRegion()方法时，targetSheet或者sourceSheet不能为空，故抛出该异常！");
        }

        for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            CellRangeAddress oldRange = sourceSheet.getMergedRegion(i);
            CellRangeAddress newRange = new CellRangeAddress(oldRange.getFirstRow(), oldRange.getLastRow(),
                    oldRange.getFirstColumn(), oldRange.getLastColumn());
            targetSheet.addMergedRegion(newRange);
        }
    }

    /**
     * 功能：重新定义HSSFColor.PINK的色值
     *
     * @param workbook
     * @return
     */
    public static HSSFColor setMBorderColor(HSSFWorkbook workbook) {
        HSSFPalette palette = workbook.getCustomPalette();
        HSSFColor hssfColor = null;
        byte[] rgb = { (byte) 0, (byte) 128, (byte) 192 };
        try {
            hssfColor = palette.findColor(rgb[0], rgb[1], rgb[2]);
            if (hssfColor == null) {
                palette.setColorAtIndex(HSSFColor.PINK.index, rgb[0], rgb[1], rgb[2]);
                hssfColor = palette.getColor(HSSFColor.PINK.index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hssfColor;
    }

    /*
     * 合并单元格
     *
     * @param sheet 要合并单元格的excel 的sheet
     *
     * @param cellLine 要合并的列
     *
     * @param startRow 要合并列的开始行
     *
     * @param endRow 要合并列的结束行
     */
    public static void addMergedRegion(HSSFSheet sheet, int cellLine, int startRow, int endRow, HSSFWorkbook workBook) {

        HSSFCellStyle style = workBook.createCellStyle(); // 样式对象

        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
        style.setAlignment(HorizontalAlignment.CENTER);// 水平
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框

        /*style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);*/

        //style.setBorderBottom((short) 1);
        //style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
        style.setWrapText(true);
        // 获取第一行的数据,以便后面进行比较
        //String s_will = sheet.getRow(startRow).getCell(cellLine).getStringCellValue();
        String s_will = getCellValue(sheet.getRow(startRow).getCell(cellLine));

        int count = 0;
        for (int i = startRow + 1; i <= endRow; i++) {
            String s_current = getCellValue(sheet.getRow(i).getCell(cellLine));
            startRow = i;
            if (s_will.equals(s_current)) {
                count++;
            } else {
                s_will=s_current;
                if (count != 0) {
                    sheet.addMergedRegion(new CellRangeAddress(startRow - count-1, startRow-1, cellLine, cellLine)); // 合并单元格
                    HSSFRow row = sheet.getRow(startRow - count); // 获取合并单元格的第一行
                    //String cellValueTemp = row.getCell(cellLine).getStringCellValue(); // 获得第n个单元格的值
                    String cellValueTemp = getCellValue(row.getCell(cellLine)); // 获得第n个单元格的值
                    HSSFCell cell = row.createCell(cellLine); //重新创建单元格
                    cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                    cell.setCellStyle(style);
                    count = 0;
                }

            }
            // 由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
            if (i == endRow && count > 0) {
                sheet.addMergedRegion(new CellRangeAddress(endRow - count, endRow, cellLine, cellLine));
                String cellValueTemp = getCellValue(sheet.getRow(startRow - count).getCell(cellLine));
                HSSFRow row = sheet.getRow(startRow - count);
                HSSFCell cell = row.createCell(cellLine);
                cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                cell.setCellStyle(style); // 样式
            }
        }
    }


    /**
     *
     *
     * @Title: getCellValue
     *
     * @Description: 对Excel的各个单元格的格式进行判断并转换
     *
     * @param cell @return 设定文件
     *
     * @author af
     *
     * @return String 返回类型
     *
     * @throws
     */
    @SuppressWarnings("unused")
    private static String getCellValue(HSSFCell cell) {
        String cellValue = ""; // 返回的单元格内容
        DecimalFormat df = new DecimalFormat("#");// 用来格式化除零外的数字
        if (cell == null) { // 如果此单元格没有内容，返回空字符串
            return "";
        }
        switch (cell.getCellType()) { // 判断单元格类型
        case HSSFCell.CELL_TYPE_STRING: // 字符串型
            cellValue = cell.getRichStringCellValue().getString().trim();// 获取去除空格后的单元格内容
            break;
        case HSSFCell.CELL_TYPE_NUMERIC: // 数字型
            cellValue = df.format(cell.getNumericCellValue()).toString();// 获取单元格内的数字并转为String
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔型
            cellValue = String.valueOf(cell.getBooleanCellValue()).trim();// 获取去除空格后的单元格的值
            break;
        case HSSFCell.CELL_TYPE_FORMULA: // 公式
            cellValue = cell.getCellFormula(); // 返回单元格的公式；如（SUM(C4:E4)）
            break;
        default: // 其他类型统一返回空字符串
            cellValue = "";
        }
        return cellValue;
    }

}