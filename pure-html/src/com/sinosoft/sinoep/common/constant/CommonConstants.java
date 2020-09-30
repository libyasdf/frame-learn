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

    /** 列表操作列（进展） */
    public static final String OPTION_COMPLETE = "complete";

    /** 列表操作列（反馈） */
    public static final String OPTION_BACKFUN = "backfun";

	/** 列表操作列（删除） */
	public static final String OPTION_DELETE = "delete";
	
	/** 列表操作列（查看） */
	public static final String OPTION_VIEW = "view";
	
	/** 列表操作列（发布） */
	public static final String OPTION_PUBLISH = "publish";
	
	/** 列表操作列（撤销） */
	public static final String OPTION_CANCEL = "cancel";
	
	/** 查看反馈信息 */
	public static final String OPTION_SEE_BACK = "seeBack";

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

	public static final String ZILIAO_TYPE[] = {"fz","zzll","bm"};

	public static final String ZILIAO_TYPE_NAME[] ={"法制","政治理论","保密"};

	public static final String OPTION_TASK = "task";

	public static final String OPTION_WITH = "withdra";

	public static final String PUBLISH[] = {"0","1"};

	public static final String HANDLESTATE[] ={"0","1","2","3","4","5"};

	public static final String CYCLE[] = {"","每周","每两周","每月","每季度","每半年","每年"};

	/** 会议室座位的总排数 */
    public static final int HYGL_MEETINGROM_ROWNUM = 14;
    
    /**会议室座位的总列数*/
    public static final int  HYGL_MEETINGROM_COLNUM = 27;

    /** 登录类型（0：系统登录；1：客户端登录） */
    public static final String LOGIN_TYPE[] ={"0","1"};

    /** redis存放用户信息超时时间 */
	public static final Integer EXPIRE_REDIS = 1800;

    /** 用户基本信息存放redis前缀 */
	public static final String USER_INFO_REDIS = "USER_INFO_";

    /** 用户全部信息存放redis前缀 */
    public static final String USER_INFO_ALL_REDIS = "USER_INFO_ALL_";

    /** 用户部门ID存放redis前缀 */
    public static final String USER_DEPTID_REDIS = "USER_DEPTID_";

    /** redis存放某部门所有父辈部门集合的前缀 */
    public static final String ALL_SUPER_DEPT = "ALL_SUPER_DEPT_";

    /** redis存放用户业务角色信息的前缀 */
    public static final String FLOW_ROLE = "FLOW_ROLE_";

    /** redis存放用户系统角色信息的前缀 */
    public static final String SYS_ROLE = "SYS_ROLE_";
}
