package com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.vo;

/**
 * @Author 王富康
 * @Description //TODO 用户部门关联
 * @Date 2018/8/9 15:16
 * @Param
 * @return
 **/
public class userDprbVo {

    private String id;//id
    private String deptid;//部门id
    private String userid;//用户id
    private String user_code;//
    private String user_name;//用户姓名
    private String order_no;//序号
    private String note;//
    private String auter;
    private String statue;//状态
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAuter() {
        return auter;
    }

    public void setAuter(String auter) {
        this.auter = auter;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
