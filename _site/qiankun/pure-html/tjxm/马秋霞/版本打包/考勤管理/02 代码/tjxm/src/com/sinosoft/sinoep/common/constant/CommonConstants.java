package com.sinosoft.sinoep.common.constant;

import com.sinosoft.sinoep.common.util.ConfigProperties;

/**
 * 公共常量类
 * TODO 
 * @author 李利广
 * @Date 2018年3月15日 下午8:26:54
 */
public class CommonConstants {
	
	/** 列表操作列（添加） */
	public static final String OPTION_ADD = "add";
	
	/** 列表操作列（修改） */
	public static final String OPTION_UPDATE = "update";
	
	/** 列表操作列（删除） */
	public static final String OPTION_DELETE = "delete";
	
	/** 列表操作列（查看） */
	public static final String OPTION_VIEW = "view";

	/** 逻辑删除：0删除、1可用 */
	public static final String VISIBLE[] = {"0","1"};

	/** 常用模块id */
	public static final String OFTENMODEL_ID = ConfigProperties.getPropertyValue("OFTENMODEL_ID");
	
	/** 开关状态：0为关闭，1为打开 */
	public static final String ISOPEN[] = {"0","1"};
	
	/** 签入时间 */
	public static final String QR_TIME = "08:30:00";
	
	/** 签出时间 */
	public static final String QC_TIME = "17:30:00";
	
	
}
