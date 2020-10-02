package com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.constant;

/**
 * 迟到早退常量类
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月17日 上午11:40:06
 */
public class EmConstants {
	//导出ExcelStyle start 
	public static final short SHEET_COL_WIDTH = 20;	  //sheet的行宽
	public static final short TITLE_ROW_HEIGHT = 600;  //表头行高
	public static final String FONT_NAME = "宋体";	  //字体
	public static final short TITLE_FONT_SIZE = 14;   //表头的字体大小
	public static final short TEXT_FONT_SIZE = 11;	  //文本的字体大小	
	/**
	 * 导出的excel表名
	 */
	public static final String[] FILE_NAME = {"旷工列表.xls"};

	/**
	 * 导出excel时 sheet名
	 */
	public static final String[] SHEET_TITLE ={"旷工列表"};
	
	/**
	 * 查询结果导出excel时 导出的字段名称
	 */
	public static final String[] HEADER = {"序号","录入人","录入单位","录入时间","姓名","单位","日期","旷工原因"};
	/**
	 * 导入的字段的get方法(依据反射原理)
	 */
	public static final String[] GET_METHOD = {"getCreUserName","getApplicantUnitName","getApplicationTime","getAbsenteeismUserName","getAbsApplicantUnitName","getAbsenteeismDate","getAbsenteeismReason"};
	//导出ExcelStyle start 
		
	
	
}
