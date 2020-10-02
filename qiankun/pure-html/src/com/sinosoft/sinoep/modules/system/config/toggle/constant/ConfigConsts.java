package com.sinosoft.sinoep.modules.system.config.toggle.constant;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：开关模块</B><BR>
 * <B>中文类名：开关相关常量</B><BR>
 * <B>概要说明：开关设置常量放在此类下</B><BR>
 * 
 * @author 王富康
 * @since 2018年5月8日
 */
public class ConfigConsts {

	/** 
	 * 逻辑删除：该开关数据的状态，比如删除，暂停使用，锁定等，默认值为0。
	 *  各取值的定义为： 0：正常使用 1：开关被锁定(不可编辑) 2：暂停使用 3：开关被删除
	 */
	public static String TOGGLE_VISIBLE[] = {"0","1","2","3"};
	
	/** 开关状态，是开着，还是关着。0，开关打开，1开关关闭*/
	public static int TOGGLE_ISOPEN[] = {0,1};
	
	/**
	 * 二维码的相关属性
	 */
	public static  int QRCOLOR = 0xFF000000; // 默认是黑色
	public static  int BGWHITE = 0xFFFFFFFF; // 背景颜色
	
	/**
	 * 非流程待办发送消息开关
	 */
	public static final String NOFLOW_SEND_MSG_TOGGLE = "sendWaitdoNoFlowMessage";
	
}
