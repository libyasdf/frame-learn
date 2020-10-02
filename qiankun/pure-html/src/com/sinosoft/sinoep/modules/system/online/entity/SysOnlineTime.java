package com.sinosoft.sinoep.modules.system.online.entity;

import javax.persistence.*;
import javax.persistence.IdClass;

/**
 * TODO 在线时长统计表实体
 * @author 李利广
 * @Date 2019年06月19日 10:51:48
 */
@Entity
@Table(name="SYS_ONLINE_TIME")
@IdClass(com.sinosoft.sinoep.modules.system.online.entity.IdClass.class)
public class SysOnlineTime {

    @Id
    @Column(name = "USERID")
    private String userId;

    @Id
    @Column(name = "TICKET")
    private String ticket;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "LOGIN_TIME")
    private String loginTime;

    @Column(name = "LOGOUT_TIME")
    private String logoutTime;

    @Column(name = "ONLINE_TIME")
    private String onlineTime;

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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }
}
