package com.sinosoft.sinoep.modules.system.notice.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * TODO 通知通告通知人群
 * @author 杜建松
 * @Date 2019年1月8日 下午4:12:52
 */
@Entity
@Table(name="SYS_NOTICE_GRUP")
public class SysNoticeGrup {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;


    /** 创建人处室ID */
    @Column(name="CRE_CHUSHI_ID")
    private String creChushiId;

    /** 创建人处室name */
    @Column(name="CRE_CHUSHI_NAME")
    private String creChushiName;

    /** 创建人部门ID */
    @Column(name="CRE_DEPT_ID")
    private String creDeptId;

    /** 创建人部门name */
    @Column(name="CRE_DEPT_NAME")
    private String creDeptName;

    /** 创建人局ID */
    @Column(name="CRE_JU_ID")
    private String creJuId;

    /** 创建人局name */
    @Column(name="CRE_JU_NAME")
    private String creJuName;

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

    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    @Column(name = "UPDATE_TIME")
    private String updateTime;

    //通知人群名称
    @Column(name = "SYS_GRUP_NAME")
    private String sysGrupName;

    @Column(name = "TYPE_CODE")
    private String typeCode;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @Column(name = "OFFICE_SCOPE_ID")
    private String officeScopeId;

    @Column(name = "OFFICE_SCOPE_NAME")
    private String officeScopeName;

    @Column(name = "IS_GENARALS")
    private String isGenarals;

    /** 操作 */
    @Transient
    private String cz;

    @Transient
    private List<SysNoticeUser> sysNoticeUserList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreChushiId() {
        return creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    public String getCreChushiName() {
        return creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    public String getCreDeptId() {
        return creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    public String getCreDeptName() {
        return creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
    }

    public String getCreJuName() {
        return creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
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

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSysGrupName() {
        return sysGrupName;
    }

    public void setSysGrupName(String sysGrupName) {
        this.sysGrupName = sysGrupName;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public List<SysNoticeUser> getSysNoticeUserList() {
        return sysNoticeUserList;
    }

    public void setSysNoticeUserList(List<SysNoticeUser> sysNoticeUserList) {
        this.sysNoticeUserList = sysNoticeUserList;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOfficeScopeId() {
        return officeScopeId;
    }

    public void setOfficeScopeId(String officeScopeId) {
        this.officeScopeId = officeScopeId;
    }

    public String getOfficeScopeName() {
        return officeScopeName;
    }

    public void setOfficeScopeName(String officeScopeName) {
        this.officeScopeName = officeScopeName;
    }

    public String getIsGenarals() {
        return isGenarals;
    }

    public void setIsGenarals(String isGenarals) {
        this.isGenarals = isGenarals;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
