package com.sinosoft.sinoep.sendFLowWorkflow.common;

public class Constant {

    public final static String RULE_TYPE_YEAR = "00";//年报
    public final static String RULE_TYPE_MONTH = "01";//月报
    public final static String RULE_TYPE_TIME = "02";//时间段
    public final static String RULE_TYPE_PHASE = "03";//半年报，季度报
    public final static String RULE_TYPE_XUN = "04";//旬报

    public static final String INFO_STATUS_NOT_REPORT = "00";// 流程状态情况表，未上报
    public static final String INFO_STATUS_REPORT = "01";// 流程状态情况表，已上报
    public static final String INFO_STATUS_BACK = "02";// 流程状态情况表，退回


    public static final String WAIT_DO_TYPE_REPORT = "01";//已上报
    public static final String WAIT_DO_TYPE_NOT_REPORT = "02";//未上报
    public static final String WAIT_DO_TYPE_BACK = "03"; // 退回状态

    public static final String DEPTLEVEL_ERJIJU = "402";//部门级别：二级局
    public static final String DEPTLEVEL_CHUSHI = "93248";//部门级别:处室

    public static final String DEPTTYPE_JU = "0";//查询类别局级
    public static final String DEPTTYPE_ERJIJU = "1";//查询类别二级局
    public static final String DEPTTYPE_CHUSHI = "2";//查询类别处室

}
