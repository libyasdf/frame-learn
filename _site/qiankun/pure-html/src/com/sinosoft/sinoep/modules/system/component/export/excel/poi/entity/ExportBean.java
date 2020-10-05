package com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 导出excel的实体类
 * @author 邴秀慧
 * @Date 2016-12-14 上午08:48:00
 */
public class ExportBean {
	private String sheetTitle;//导出excel时sheet名
	private String[] header;//list结果导出excel时 导出的字段名称
	private short sheetColWidth = 20;//sheet的列宽
	private short textFontSize = 11;//文本的字体大小
	private short titleFontSize = 14;//表头字体大小
	private String fontName = "宋体";//字体 例如：黑体
	private String[] getMethod;//导入的字段的get方法(依据反射原理)
	private short titleRowHight = 600;//表头行高
	//List<Object> datasets = new ArrayList<Object>();
	List<? extends Object> exportList = new ArrayList<Object>();
	public String getSheetTitle() {
		return sheetTitle;
	}
	public void setSheetTitle(String sheetTitle) {
		this.sheetTitle = sheetTitle;
	}
	public String[] getHeader() {
		return header;
	}
	public void setHeader(String[] header) {
		this.header = header;
	}
	public short getSheetColWidth() {
		return sheetColWidth;
	}
	public void setSheetColWidth(short sheetColWidth) {
		this.sheetColWidth = sheetColWidth;
	}
	public short getTextFontSize() {
		return textFontSize;
	}
	public void setTextFontSize(short textFontSize) {
		this.textFontSize = textFontSize;
	}
	public short getTitleFontSize() {
		return titleFontSize;
	}
	public void setTitleFontSize(short titleFontSize) {
		this.titleFontSize = titleFontSize;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public String[] getGetMethod() {
		return getMethod;
	}
	public void setGetMethod(String[] getMethod) {
		this.getMethod = getMethod;
	}
	public short getTitleRowHight() {
		return titleRowHight;
	}
	public void setTitleRowHight(short titleRowHight) {
		this.titleRowHight = titleRowHight;
	}
	public List<? extends Object> getExportList() {
		return exportList;
	}
	public void setExportList(List<? extends Object> exportList) {
		this.exportList = exportList;
	}
	
	
}
