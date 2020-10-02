package com.sinosoft.sinoep.modules.system.notice.common;

/**
 * TODO 通知通告常量类
 * @author 李利广
 * @Date 2018年8月17日 下午2:51:17
 */
public class SysNoticeConstants {
	
	/**
	 * 通知通告状态：0草稿；1流程中；2撤销；5已发布；6未通过
	 */
	public static final String[] FLAG = {"0","1","2","5","6"};
	
	/**
	 * 删除状态：0删除；1可用
	 */
	public static final String[] VISIBLE = {"0","1"};
	
	/**
	 * 是否置顶：0不置顶；1置顶
	 */
	public static final String[] IS_TOP = {"0","1"};
	
	/**
	 * 是否需要反馈：0否；1是
	 */
	public static final String[] IS_BACK = {"0","1"};
	
	/**
	 * 发送类型（0部门；1人员；2业务角色）
	 */
	public static final String[] TYPE = {"0","1","2"};

	/**
	 * 发送是否为局级（01局级；02部门）
	 */
	public static final String[] IS_GENARLAS = {"01","02"};

}
