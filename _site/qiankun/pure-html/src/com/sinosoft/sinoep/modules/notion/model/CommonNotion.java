/**
 * Copyright 2017 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.modules.notion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：流程常用意见</B><BR>
 * 
 * @author 中科软科技 goulijun
 * @since 2017年5月23日
 */
@Entity
@Table(name = "common_notion")
public class CommonNotion implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // Fields
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", length = 32)
    private String id;
    private String notion;//意见内容
    private String userid;//创建用户id
    private String username;//创建用户姓名
    private String creatime;//创建时间
    private String sysid;//系统id
    private String orgid;//组织机构id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotion() {
        return notion;
    }

    public void setNotion(String notion) {
        this.notion = notion;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatime() {
        return creatime;
    }

    public void setCreatime(String creatime) {
        this.creatime = creatime;
    }

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

}
