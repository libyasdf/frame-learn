package com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.constant;

/**
 * @Author 王富康
 * @Description //TODO 常量类
 * @Date 2018/7/18 14:31
 * @Param
 * @return
 **/
public class EmConstants {
    public static final String[] SUPPLEMENTSIGN_TYPE = {"补签入","补签出","补全天"};
    public static final String[] SUBFLAG = {"草稿","审批中","","已撤办",""," 审批通过","审批未通过 "};
    //导出ExcelStyle start
    public static final short SHEET_COL_WIDTH = 20;	  //sheet的行宽
    public static final short TITLE_ROW_HEIGHT = 600;  //表头行高
    public static final String FONT_NAME = "宋体";	  //字体
    public static final short TITLE_FONT_SIZE = 14;   //表头的字体大小
    public static final short TEXT_FONT_SIZE = 11;	  //文本的字体大小
    /**
     * 导出的excel表名
     */
    public static final String[] FILE_NAME = {"值班表.xls"};

    /**
     * 导出excel时 sheet名
     */
    public static final String[] SHEET_TITLE ={"值班详情列表"};

    /**
     * 查询结果导出excel时 导出的字段名称
     */
    public static final String[] HEADER = {"序号","日期","星期","值班班次","值班员","手机号","带班领导","手机号","普网电话","公网电话"};

    /**
     * 导入的字段的get方法(依据反射原理)
     */
    public static final String[] GET_METHOD = {"getWatchDate","getWeekday","getClasses","getWatchName","getPhone","getShiftLeaderName","getShiftLeaderPhone","getCommonPhone","getPrivatePhone"};
    //导出ExcelStyle start



}
