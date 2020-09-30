package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 *
 * 党的建设——党费管理实体类
 * @Author: 李帅
 * @Date: 2018/9/11 14:56
 */
@Entity
@Table(name = "DDJS_DZZ_PARTYFEE")
public class DfglEntity {
    /** 主键id */
    private String id;
    /**创建人id*/
    private String creUserId;
    /**创建人名*/
    private String creUserName;
    /**创建人部门id*/
    private String creDeptId;
    /**创建人部门名*/
    private String creDeptName;
    /**创建人处室id*/
    private String creChushiId;
    /**创建人处室名*/
    private String creChushiName;
    /**创建人二级局id*/
    private String creJuId;
    /**创建人二级局名*/
    private String creJuName;
    /***逻辑删除*/
    private String visible;
    /**创建时间*/
    private String creTime;
    /**党组织名*/
    private String partyOrganizationName;
    /**党组织ID*/
    private String partyOrganizationId;
    /**党员ID*/
    private String partyId;
    /**党员姓名*/
    private String partyName;
    /**年份*/
    private String annual;
    /**月份*/
    private String monthval;
    /**核定月交纳党费数额*/
    private String approvedMonthPartyfee;
    /**当前交纳金额*/
    private String currentPaymentAmount;
    private String totalSum;
    /** 操作 */
    private String cz = "";
    /** 上级党组织名称 */
    private String superName = "";
    @Transient
    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }
    @Transient
    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }

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
    @Column(name = "PARTY_ID")
    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    @Basic
    @Column(name = "PARTY_NAME")
    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    @Basic
    @Column(name = "ANNUAL")
    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    @Basic
    @Column(name = "MONTHVAL")
    public String getMonthval() {
        return monthval;
    }

    public void setMonthval(String monthval) {
        this.monthval = monthval;
    }

    @Basic
    @Column(name = "APPROVED_MONTH_PARTYFEE")
    public String getApprovedMonthPartyfee() {
        return approvedMonthPartyfee;
    }

    public void setApprovedMonthPartyfee(String approvedMonthPartyfee) {
        this.approvedMonthPartyfee = approvedMonthPartyfee;
    }

    @Basic
    @Column(name = "CURRENT_PAYMENT_AMOUNT")
    public String getCurrentPaymentAmount() {
        return currentPaymentAmount;
    }

    public void setCurrentPaymentAmount(String currentPaymentAmount) {
        this.currentPaymentAmount = currentPaymentAmount;
    }
    public DfglEntity(){
        super();
    }

    public DfglEntity(String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String partyOrganizationName, String partyOrganizationId, String partyId, String partyName, String annual, String monthval, String approvedMonthPartyfee, String currentPaymentAmount, String totalSum, String cz, String superName) {
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
        this.partyId = partyId;
        this.partyName = partyName;
        this.annual = annual;
        this.monthval = monthval;
        this.approvedMonthPartyfee = approvedMonthPartyfee;
        this.currentPaymentAmount = currentPaymentAmount;
        this.totalSum = totalSum;
        this.cz = cz;
        this.superName = superName;
    }
}
