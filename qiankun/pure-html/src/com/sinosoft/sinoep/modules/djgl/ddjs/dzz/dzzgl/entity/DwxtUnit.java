package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DDJS_DZZ_UNIT")
public class DwxtUnit {
    private String id;
    private String dwxtOrgId;
    private String unitId;
    private String unitName;
    private String unitBelong;
    private String unitAttr;
    private String trade;
    private String grassrootOrg;
    private String orgUnitCode;
    private String youngWorkersNumber;
    private String workersNumber;
    private String skillWorkers;
    private String majorWorkers;
    private Integer unitOrder;
    private String creUserId;
    private String creUserName;
    private String creDeptId;
    private String creDeptName;
    private String creChushiId;
    private String creChushiName;
    private String creJuId;
    private String creJuName;
    private String visible;
    private String creTime;
    private String updateUserId;
    private String updateUserName;
    private String updateTime;


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

    @Basic
    @Column(name = "UNIT_ID")
    private String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Basic
    @Column(name = "UNIT_NAME")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Basic
    @Column(name = "UNIT_BELONG")
    public String getUnitBelong() {
        return unitBelong;
    }

    public void setUnitBelong(String unitBelong) {
        this.unitBelong = unitBelong;
    }

    @Basic
    @Column(name = "UNIT_ATTR")
    public String getUnitAttr() {
        return unitAttr;
    }

    public void setUnitAttr(String unitAttr) {
        this.unitAttr = unitAttr;
    }

    @Basic
    @Column(name = "TRADE")
    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    @Basic
    @Column(name = "GRASSROOT_ORG")
    public String getGrassrootOrg() {
        return grassrootOrg;
    }

    public void setGrassrootOrg(String grassrootOrg) {
        this.grassrootOrg = grassrootOrg;
    }

    @Basic
    @Column(name = "ORG_UNIT_CODE")
    public String getOrgUnitCode() {
        return orgUnitCode;
    }

    public void setOrgUnitCode(String orgUnitCode) {
        this.orgUnitCode = orgUnitCode;
    }

    @Basic
    @Column(name = "YOUNG_WORKERS_NUMBER")
    public String getYoungWorkersNumber() {
        return youngWorkersNumber;
    }

    public void setYoungWorkersNumber(String youngWorkersNumber) {
        this.youngWorkersNumber = youngWorkersNumber;
    }

    @Basic
    @Column(name = "WORKERS_NUMBER")
    public String getWorkersNumber() {
        return workersNumber;
    }

    public void setWorkersNumber(String workersNumber) {
        this.workersNumber = workersNumber;
    }

    @Basic
    @Column(name = "SKILL_WORKERS")
    public String getSkillWorkers() {
        return skillWorkers;
    }

    public void setSkillWorkers(String skillWorkers) {
        this.skillWorkers = skillWorkers;
    }

    @Basic
    @Column(name = "MAJOR_WORKERS")
    public String getMajorWorkers() {
        return majorWorkers;
    }

    public void setMajorWorkers(String majorWorkers) {
        this.majorWorkers = majorWorkers;
    }

    @Basic
    @Column(name = "UNIT_ORDER")
    public Integer getUnitOrder() {
        return unitOrder;
    }

    public void setUnitOrder(Integer unitOrder) {
        this.unitOrder = unitOrder;
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
    @Column(name = "VISIBLE")
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
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
}
