package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DDJS_DZZ_HJXJ", schema = "TJXM", catalog = "")
public class DwxtHjxj {
    private String id;
    private String dwxtOrgId;
//    private String orgName;
//    private String orgId;
    private String sessionNumber;
    private Integer limitYear;
    private String changeTime;
    private String modality;
    private Integer shouldNumber;
    private Integer actualNumber;
    private String situation;
    private String creUserId;
    private String creUserName;
    private String creDeptId;
    private String creDeptName;
    private String creChushiId;
    private String creChushiName;
    private String creJuId;
    private String creJuName;
    private String creTime;
    private String updateUserId;
    private String updateUserName;
    private String updateTime;
    private String visible;
    private Integer hjxjOrder;
    private Boolean isMaxHj;
    private String delHj;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DWXT_ORG_ID")
    public String getDwxtOrgId() {
        return dwxtOrgId;
    }

    public void setDwxtOrgId(String dwxtOrgId) {
        this.dwxtOrgId = dwxtOrgId;
    }

//    @Basic
//    @Column(name = "ORG_NAME")
//    public String getOrgName() {
//        return orgName;
//    }
//
//    public void setOrgName(String orgName) {
//        this.orgName = orgName;
//    }
//
//    @Basic
//    @Column(name = "ORG_ID")
//    public String getOrgId() {
//        return orgId;
//    }
//
//    public void setOrgId(String orgId) {
//        this.orgId = orgId;
//    }

    @Basic
    @Column(name = "SESSION_NUMBER")
    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    @Basic
    @Column(name = "LIMIT_YEAR")
    public Integer getLimitYear() {
        return limitYear;
    }

    public void setLimitYear(Integer limitYear) {
        this.limitYear = limitYear;
    }

    @Basic
    @Column(name = "CHANGE_TIME")
    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    @Basic
    @Column(name = "MODALITY")
    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    @Basic
    @Column(name = "SHOULD_NUMBER")
    public Integer getShouldNumber() {
        return shouldNumber;
    }

    public void setShouldNumber(Integer shouldNumber) {
        this.shouldNumber = shouldNumber;
    }

    @Basic
    @Column(name = "ACTUAL_NUMBER")
    public Integer getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(Integer actualNumber) {
        this.actualNumber = actualNumber;
    }

    @Basic
    @Column(name = "SITUATION")
    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    @Basic
    @Column(name = "CRE_USER_ID")
    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    @Basic
    @Column(name = "CRE_USER_NAME")
    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    @Basic
    @Column(name = "CRE_DEPT_ID")
    public String getCreDeptId() {
        return creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    @Basic
    @Column(name = "CRE_DEPT_NAME")
    public String getCreDeptName() {
        return creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_ID")
    public String getCreChushiId() {
        return creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_NAME")
    public String getCreChushiName() {
        return creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    @Basic
    @Column(name = "CRE_JU_ID")
    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
    }

    @Basic
    @Column(name = "CRE_JU_NAME")
    public String getCreJuName() {
        return creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
    }

    @Basic
    @Column(name = "CRE_TIME")
    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    @Basic
    @Column(name = "UPDATE_USER_ID")
    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Basic
    @Column(name = "UPDATE_USER_NAME")
    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    @Basic
    @Column(name = "UPDATE_TIME")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "VISIBLE")
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "HJXJ_ORDER")
    public Integer getHjxjOrder() {
        return hjxjOrder;
    }

    public void setHjxjOrder(Integer hjxjOrder) {
        this.hjxjOrder = hjxjOrder;
    }

    @Transient
    public Boolean getMaxHj() {
        return isMaxHj;
    }

    public void setMaxHj(Boolean maxHj) {
        isMaxHj = maxHj;
    }
    @Transient

    public String getDelHj() {
        return delHj;
    }

    public void setDelHj(String delHj) {
        this.delHj = delHj;
    }
}
