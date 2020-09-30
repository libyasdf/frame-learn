package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
/**
 * TODO 党组织管理 民主评议实体类
 * @Author: 李帅
 * @Date: 2018/9/9 11:06
 */
@Entity
@Table(name = "DDJS_DZZ_REVIEW")
public class MzpyEntity {
    private String id;
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
    private String partyOrganizationName;
    private String partyOrganizationId;
    private String startTime;
    private String endTime;
    private String placeval;
    private String themeval;
    private String peopleNumber;
    private String actualNumber;
    private String host;
    private String primaryCoverage;
    private String situation;
    private String attendance;
    private String cz;
    /** 操作 */
    @Transient
    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }
    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "PARTY_ORGANIZATION_NAME")
    public String getPartyOrganizationName() {
        return partyOrganizationName;
    }

    public void setPartyOrganizationName(String partyOrganizationName) {
        this.partyOrganizationName = partyOrganizationName;
    }

    @Basic
    @Column(name = "PARTY_ORGANIZATION_ID")
    public String getPartyOrganizationId() {
        return partyOrganizationId;
    }

    public void setPartyOrganizationId(String partyOrganizationId) {
        this.partyOrganizationId = partyOrganizationId;
    }

    @Basic
    @Column(name = "START_TIME")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "END_TIME")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "PLACEVAL")
    public String getPlaceval() {
        return placeval;
    }

    public void setPlaceval(String placeval) {
        this.placeval = placeval;
    }

    @Basic
    @Column(name = "THEMEVAL")
    public String getThemeval() {
        return themeval;
    }

    public void setThemeval(String themeval) {
        this.themeval = themeval;
    }

    @Basic
    @Column(name = "PEOPLE_NUMBER")
    public String getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(String peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    @Basic
    @Column(name = "ACTUAL_NUMBER")
    public String getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(String actualNumber) {
        this.actualNumber = actualNumber;
    }

    @Basic
    @Column(name = "HOST")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Basic
    @Column(name = "PRIMARY_COVERAGE")
    public String getPrimaryCoverage() {
        return primaryCoverage;
    }

    public void setPrimaryCoverage(String primaryCoverage) {
        this.primaryCoverage = primaryCoverage;
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
    @Column(name = "ATTENDANCE")
    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
    public MzpyEntity(){super();}

    public MzpyEntity(String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String partyOrganizationName, String partyOrganizationId, String startTime, String endTime, String placeval, String themeval, String peopleNumber, String actualNumber, String host, String primaryCoverage, String situation, String attendance,String cz) {
        this.id = id;
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creDeptId = creDeptId;
        this.creDeptName = creDeptName;
        this.creChushiId = creChushiId;
        this.creChushiName = creChushiName;
        this.creJuId = creJuId;
        this.creJuName = creJuName;
        this.visible = visible;
        this.creTime = creTime;
        this.partyOrganizationName = partyOrganizationName;
        this.partyOrganizationId = partyOrganizationId;
            this.startTime = startTime;
        this.endTime = endTime;
        this.placeval = placeval;
        this.themeval = themeval;
        this.peopleNumber = peopleNumber;
        this.actualNumber = actualNumber;
        this.host = host;
        this.primaryCoverage = primaryCoverage;
        this.situation = situation;
        this.attendance = attendance;
        this.cz =cz;
    }
}
