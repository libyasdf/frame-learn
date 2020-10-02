package com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.constant;

/**
 * 
 * TODO 常量类
 * @author 赵海龙
 * @Date 2018年5月22日 下午2:48:59
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
	public static final String[] FILE_NAME = {"加班记录.xls"};

	/**
	 * 导出excel时 sheet名
	 */
	public static final String[] SHEET_TITLE ={"加班记录"};
	
	/**
	 * 查询结果导出excel时 导出的字段名称
	 */
	public static final String[] HEADER = {"序号","单位","申请人","加班日期","加班时间","加班类型","加班时长","流程状态"};
	
	/**
	 * 导入的字段的get方法(依据反射原理)
	 */
																						  
	public static final String[] GET_METHOD = {"getApplicationUnitName","getCreUserName","getOverTimeDate","getStartStopTime","getOverTimeTypeName","getLongTime","getSubflagName"};
	//导出ExcelStyle start 
		
	
	
}
