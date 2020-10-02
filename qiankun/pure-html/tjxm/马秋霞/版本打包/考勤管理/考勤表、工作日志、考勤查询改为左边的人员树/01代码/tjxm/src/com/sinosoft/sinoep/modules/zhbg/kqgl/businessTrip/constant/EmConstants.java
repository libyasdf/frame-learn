package com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.constant;

/**
 * 
 * 常量类 TODO 
 * @author 张建明
 * @Date 2018年7月9日 下午6:59:02
 */
public class EmConstants {
	//科长角色编号
	public static final String[] KZ_ROLE_NO={"D001","D002"};
	//处长角色编号
	public static final String[] CZ_ROLE_NO={"C001","C002"};
	//局长角色编号
	public static final String[] JZ_ROLE_NO={"B001","B002"};
	
	//导出ExcelStyle start 
	public static final short SHEET_COL_WIDTH = 20;	  //sheet的行宽
	public static final short TITLE_ROW_HEIGHT = 600;  //表头行高
	public static final String FONT_NAME = "宋体";	  //字体
	public static final short TITLE_FONT_SIZE = 14;   //表头的字体大小
	public static final short TEXT_FONT_SIZE = 11;	  //文本的字体大小	
	/**
	 * 导出的excel表名
	 */
	public static final String[] FILE_NAME = {"出差记录.xls"};

	/**
	 * 导出excel时 sheet名
	 */
	public static final String[] SHEET_TITLE ={"出差记录"};
	
	/**
	 * 查询结果导出excel时 导出的字段名称
	 */
	public static final String[] HEADER = {"序号","申请人",
			"单位","同行人员","出差开始时间","出差结束时间","出差类型","出差时长(d)","目的地","有无接待费用","接待费用(元)","交通工具","流程状态"};
	
	/**
	 * 导入的字段的get方法(依据反射原理)
	 */
	public static final String[] GET_METHOD = {"getCreUserName",
			"getApplicationUnitName","getTripColleague","getStartTime","getEndTime","getBusiTripType",
			"getLongTime","getDestination","getIsHaveReceptionFees","getReceptionFees","getVehicle","getSubflag"};
	//导出ExcelStyle start 
		
	
	
}
