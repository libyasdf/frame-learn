package com.sinosoft.sinoep.modules.dagl.constant;


/**
 * 档案管理常量类
 * @author 王磊
 * @Date 2018年11月23日
 */
public class DAGLCommonConstants {
	
	/** 档案录入人业务角色编号 */
    public static final String DALRR = "D701";
	
    /** 档案馆管理理员业务角色编号 */
    public static final String ADMIN = "D702";
    
    /** 立卷单位管理员业务角色编号 */
    public static final String LJDWGLY = "D705";
    
    /** 文电管理文件状态保存值：01：待归档、02：已推送至档案管理员 、03：已退回待处置、04：已归档、05：已推送至档案录入人*/
    //原来：/** 文电管理文件状态保存值：01：待归档、02：已归档 、03：不归档*/
    //根据用户需求，调整文件状态数据字典 王磊 20190409
    public static final String WEN_DIAN_STATUS[] = {"01","02","03","04","05"};
    
    /** 文电管理文件类型：需归档、留存、销毁 */
    public static final String WEN_DIAN_TYPE[] = {"01","02","03"};

    /**  档案移交状态 0:待移交，1：已移交待汇总，2：已归档，3：已退回待处置*/
    //原来：public static final String ARCHIVE_ENTITY_STATUS[] = {"已归档","已移交待审核","已移交"};
    //根据用户需求，调整各个状态名称 王磊 20190409
    public static final String ARCHIVE_ENTITY_STATUS[] = {"待移交","已移交待汇总","已归档","已退回待处置"};
    /**  档案组卷状态*/
    public static final String ARCHIVE_FLAG[] ={"已组卷","未组卷"};
    
    /** 文电管理文件状态：待归档、已归档 */
    public static final String WEN_DIAN_STATUS_NAME[] = {"待归档","已归档","不归档"};

    /** 编研状态：0待分发，1已分发，2已汇总 */
    public static final String STUDYING_STATUS[] = {"0","1","2"};

    /** 编研状态：0未确认，1已确认 */
    public static final String STUDYING_SUB_STATUS[] = {"0","1"};

    /** 编研状态：0未反馈，1已反馈 */
    public static final String IS_BACK[] = {"0","1"};

    /** 发布状态：0草稿、1已发布 (判断是否有上报员用)*/
    public static final String STATE[] = {"0","1"};

    /** 删除状态：0已删除、1未删除 */
    public static final String VISIBLE[] = {"0","1"};

    /** 文件位置：01：文件管理；02：中转库；03：已经归档到了单位预立库 */
    public static final String FILE_SITE[] = {"01","02","03"};
}
