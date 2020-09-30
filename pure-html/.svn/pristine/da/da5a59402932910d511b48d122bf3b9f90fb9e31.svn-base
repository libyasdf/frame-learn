package com.sinosoft.sinoep.modules.system.component.export.excel.poi.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * TODO excel导出公共类·
 * @author 邴秀慧
 * @Date 2016-12-21 下午03:08:54
 */
public class ExportUtil {
    
	/**
	 * TODO 自定义样式
	 * @author 邴秀慧
	 * @Date 2016-12-16 下午03:57:27
	 * @param workbook
	 * @param exportBean
	 * @return
	 */
	public HSSFCellStyle setStyle(HSSFWorkbook workbook,ExportBean exportBean){
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setWrapText(true);//自动换行
		// 设置这些样式
		/*style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中*/

		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);//左右居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);//上下居中
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		if(exportBean.getTextFontSize() != 0){
			font.setFontHeightInPoints(exportBean.getTextFontSize());
		}
		if(exportBean.getFontName() != null && !"".equals(exportBean.getFontName())){
			font.setFontName(exportBean.getFontName());
		}
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}
     
	/**
	 * TODO 表头的样式
	 * @author 邴秀慧
	 * @Date 2016-12-16 下午03:57:41
	 * @param workbook
	 * @param exportBean
	 * @return
	 */
	public HSSFCellStyle getTitleStyle(HSSFWorkbook workbook,ExportBean exportBean) { 
		HSSFCellStyle style = workbook.createCellStyle();   
		HSSFFont font = workbook.createFont();
		if(exportBean.getFontName() != null && !"".equals(exportBean.getFontName())){
			font.setFontName(exportBean.getFontName());
		}
		if(exportBean.getTitleFontSize() != 0){
			font.setFontHeightInPoints(exportBean.getTitleFontSize());// 设置字体大小
		}
		font.setBold(true);
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		style.setAlignment(HorizontalAlignment.CENTER);//左右居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);//上下居中
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		// 设置这些样式
		/*style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中*/
        style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setWrapText(true);   
		style.setFont(font);   
		return style;   
	}
    
	/**
	 * TODO 自定义sheet
	 * @Date 2016-11-29 下午04:43:21
	 * @param workbook
	 * @param title
	 * @return
	 */
	@SuppressWarnings("unused")
	public HSSFSheet setSheet(HSSFWorkbook workbook, String title){
		HSSFSheet sheet = workbook.createSheet(title);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档

		//导出Excel 去掉序号列 的注释
		/*HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
		        0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");*/
		return sheet;
	}
}
