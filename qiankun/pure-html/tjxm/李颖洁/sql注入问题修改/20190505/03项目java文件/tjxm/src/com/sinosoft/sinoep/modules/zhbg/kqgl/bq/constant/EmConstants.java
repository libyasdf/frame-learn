package com.sinosoft.sinoep.modules.zhbg.kqgl.bq.constant;

/**
 * 
 * TODO 常量类
 * @author 赵海龙
 * @Date 2018年5月22日 下午2:48:59
 */
public class EmConstants {
	public static final String[] SUPPLEMENTSIGN_TYPE = {"补签入","补签出","补全天"};
	public static final String[] SUBFLAG = {"草稿","审批中","","已撤办","","审批通过","审批未通过 "};
	//导出ExcelStyle start 
	public static final short SHEET_COL_WIDTH = 20;	  //sheet的行宽
	public static final short TITLE_ROW_HEIGHT = 600;  //表头行高
	public static final String FONT_NAME = "宋体";	  //字体
	public static final short TITLE_FONT_SIZE = 14;   //表头的字体大小
	public static final short TEXT_FONT_SIZE = 11;	  //文本的字体大小	
	/**
	 * 导出的excel表名
	 */
	public static final String[] FILE_NAME = {"补签列表.xls"};

	/**
	 * 导出excel时 sheet名
	 */
	public static final String[] SHEET_TITLE ={"补签列表"};
	
	/**
	 * 查询结果导出excel时 导出的字段名称
	 */
	public static final String[] HEADER = {"序号","申请人","单位","补签时间","补签类型","流程状态"};
	
	/**
	 * 导入的字段的get方法(依据反射原理)
	 */
	public static final String[] GET_METHOD = {"getCreUserName","getApplicantUnitName","getSupplementSignTime","getSupplementSignType","getSubflag"};
	//导出ExcelStyle start 
		
	
	
}
