package com.sinosoft.sinoep.modules.system.online.entity;

/**
 * TODO 人员信息封装类
 * @author 李利广
 * @Date 2019年06月19日 10:53:13
 */
public class OnlineUserInfo {

    /**
     * 登录票据,可以同userId一起用来关联查询SysOnlineTime表中的数据
     */
    private String ticket;

    private String userId;

    private String userName;

    /*private String deptId;

    private String deptName;

    private String chuId;

    private String chuName;

    private String juId;

    private String juName;*/

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getChuId() {
        return chuId;
    }

    public void setChuId(String chuId) {
        this.chuId = chuId;
    }

    public String getChuName() {
        return chuName;
    }

    public void setChuName(String chuName) {
        this.chuName = chuName;
    }

    public String getJuId() {
        return juId;
    }

    public void setJuId(String juId) {
        this.juId = juId;
    }

    public String getJuName() {
        return juName;
    }

    public void setJuName(String juName) {
        this.juName = juName;
    }*/
}
