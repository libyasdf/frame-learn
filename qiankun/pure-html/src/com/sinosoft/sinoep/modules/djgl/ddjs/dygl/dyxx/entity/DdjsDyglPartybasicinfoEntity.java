package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DDJS_DYGL_PARTYBASICINFO")
public class DdjsDyglPartybasicinfoEntity{
    private String partybasicinfoId;
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
    private String partybasicinfoSuperId;
    private String locationpartygroup;
    private String orderNumber;
    private String joinPartyTime;
    private String joinPartyIntroducer;
    private String introducerTime;
    private String introducerSituation;
    private String developmentType;
    private String partyPaymentBase;
    private String dyRemarks;

    @Basic
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "partybasicinfo_Id", length = 50)
    public String getPartybasicinfoId() {
        return partybasicinfoId;
    }

    public void setPartybasicinfoId(String partybasicinfoId) {
        this.partybasicinfoId = partybasicinfoId;
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
    @Column(name = "partybasicinfo_SUPER_ID")
    public String getPartybasicinfoSuperId() {
        return partybasicinfoSuperId;
    }

    public void setPartybasicinfoSuperId(String partybasicinfoSuperId) {
        this.partybasicinfoSuperId = partybasicinfoSuperId;
    }

    @Basic
    @Column(name = "LOCATIONPARTYGROUP")
    public String getLocationpartygroup() {
        return locationpartygroup;
    }

    public void setLocationpartygroup(String locationpartygroup) {
        this.locationpartygroup = locationpartygroup;
    }

    @Basic
    @Column(name = "ORDER_NUMBER")
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Basic
    @Column(name = "JOIN_PARTY_TIME")
    public String getJoinPartyTime() {
        return joinPartyTime;
    }

    public void setJoinPartyTime(String joinPartyTime) {
        this.joinPartyTime = joinPartyTime;
    }

    @Basic
    @Column(name = "JOIN_PARTY_INTRODUCER")
    public String getJoinPartyIntroducer() {
        return joinPartyIntroducer;
    }

    public void setJoinPartyIntroducer(String joinPartyIntroducer) {
        this.joinPartyIntroducer = joinPartyIntroducer;
    }

    @Basic
    @Column(name = "INTRODUCER_TIME")
    public String getIntroducerTime() {
        return introducerTime;
    }

    public void setIntroducerTime(String introducerTime) {
        this.introducerTime = introducerTime;
    }

    @Basic
    @Column(name = "INTRODUCER_SITUATION")
    public String getIntroducerSituation() {
        return introducerSituation;
    }

    public void setIntroducerSituation(String introducerSituation) {
        this.introducerSituation = introducerSituation;
    }

    @Basic
    @Column(name = "DEVELOPMENT_TYPE")
    public String getDevelopmentType() {
        return developmentType;
    }

    public void setDevelopmentType(String developmentType) {
        this.developmentType = developmentType;
    }

    @Basic
    @Column(name = "PARTY_PAYMENT_BASE")
    public String getPartyPaymentBase() {
        return partyPaymentBase;
    }

    public void setPartyPaymentBase(String partyPaymentBase) {
        this.partyPaymentBase = partyPaymentBase;
    }

    @Basic
    @Column(name = "REMARKS")

    public String getDyRemarks() {
        return dyRemarks;
    }

    public void setDyRemarks(String dyRemarks) {
        this.dyRemarks = dyRemarks;
    }


    public DdjsDyglPartybasicinfoEntity(String partybasicinfoId, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String partybasicinfoSuperId, String locationpartygroup, String orderNumber, String joinPartyTime, String joinPartyIntroducer, String introducerTime, String introducerSituation, String developmentType, String partyPaymentBase, String dyRemarks) {
        this.partybasicinfoId = partybasicinfoId;
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
        this.partybasicinfoSuperId = partybasicinfoSuperId;
        this.locationpartygroup = locationpartygroup;
        this.orderNumber = orderNumber;
        this.joinPartyTime = joinPartyTime;
        this.joinPartyIntroducer = joinPartyIntroducer;
        this.introducerTime = introducerTime;
        this.introducerSituation = introducerSituation;
        this.developmentType = developmentType;
        this.partyPaymentBase = partyPaymentBase;
        this.dyRemarks = dyRemarks;
    }

    public DdjsDyglPartybasicinfoEntity() {
        super();
    }
}
