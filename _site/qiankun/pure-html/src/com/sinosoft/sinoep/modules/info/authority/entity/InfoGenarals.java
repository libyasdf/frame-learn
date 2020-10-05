package com.sinosoft.sinoep.modules.info.authority.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * TODO 全局普发关联表
 * @author 杜建松
 * @Date 2019年1月8日 下午4:12:52
 */
@Entity
@Table(name="INFO_GENARALS")
public class InfoGenarals {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    /** 创建时间 */
    @Column(name="CRE_TIME")
    private String creTime;

    /** 创建人ID */
    @Column(name="CRE_USER_ID")
    private String creUserId;

    /** 创建人name */
    @Column(name="CRE_USER_NAME")
    private String creUserName;

    @Column(name = "VISIBLE")
    private String visible;

    //全局id
    @Column(name = "SYS_ORG_ID")
    private String sysOrgId;

    //是否全局发送
    @Column(name = "GENARALS")
    private String genarals;


    @Column(name = "CONTENT_ID")
    private String contentId;

    public InfoGenarals() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getSysOrgId() {
        return sysOrgId;
    }

    public void setSysOrgId(String sysOrgId) {
        this.sysOrgId = sysOrgId;
    }

    public String getGenarals() {
        return genarals;
    }

    public void setGenarals(String genarals) {
        this.genarals = genarals;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
