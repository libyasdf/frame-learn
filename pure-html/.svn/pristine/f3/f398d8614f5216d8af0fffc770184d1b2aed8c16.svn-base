package com.sinosoft.sinoep.modules.system.notice.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * TODO 通知通告通知人群子表
 * @author 杜建松
 * @Date 2019年1月8日 下午4:12:52
 */
@Entity
@Table(name="SYS_NOTICE_USER")
public class SysNoticeUser {

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

    //通知人群名称
    @Column(name = "SYS_GRUP_NAME")
    private String sysGrupName;

    //通知人名称
    @Column(name = "SYS_USER_NAME")
    private String sysUserName;

    //通知人id
    @Column(name = "SYS_USER_ID")
    private String sysUserId;

    //外键id
    @Column(name = "GRUP_ID")
    private String grupId;

    @Column(name = "SYS_DEPT_ID")
    private String sysDeptId;

    @Column(name = "SYS_DEPT_NAME")
    private String sysDeptName;

    @Column(name = "SYS_CHUSHI_ID")
    private String sysChuShiId;

    @Column(name = "SYS_DEPTUSER_NAME")
    private String sysDeptUserName;

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

    public String getSysGrupName() {
        return sysGrupName;
    }

    public void setSysGrupName(String sysGrupName) {
        this.sysGrupName = sysGrupName;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getGrupId() {
        return grupId;
    }

    public void setGrupId(String grupId) {
        this.grupId = grupId;
    }

    public String getSysDeptId() {
        return sysDeptId;
    }

    public void setSysDeptId(String sysDeptId) {
        this.sysDeptId = sysDeptId;
    }

    public String getSysDeptName() {
        return sysDeptName;
    }

    public void setSysDeptName(String sysDeptName) {
        this.sysDeptName = sysDeptName;
    }

    public String getSysChuShiId() {
        return sysChuShiId;
    }

    public void setSysChuShiId(String sysChuShiId) {
        this.sysChuShiId = sysChuShiId;
    }

    public String getSysDeptUserName() {
        return sysDeptUserName;
    }

    public void setSysDeptUserName(String sysDeptUserName) {
        this.sysDeptUserName = sysDeptUserName;
    }
}
