package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DDJS_DYGL_ADMINISTRATIVE")
public class DdjsDyglAdministrativeEntity{
    private String administrativeId;
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
    private String administrativeSuperId;
    private String administrativeDutiesName;
    private String tenureUnit;
    private String personalPositionOrder;
    private String xzTenureTime;
    private String xzOutgoingTime;


    @Basic
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "administrative_Id", length = 50)
    public String getAdministrativeId() {
        return administrativeId;
    }

    public void setAdministrativeId(String administrativeId) {
        this.administrativeId = administrativeId;
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
    @Column(name = "administrative_SUPER_ID")
    public String getAdministrativeSuperId() {
        return administrativeSuperId;
    }

    public void setAdministrativeSuperId(String administrativeSuperId) {
        this.administrativeSuperId = administrativeSuperId;
    }

    @Basic
    @Column(name = "ADMINISTRATIVE_DUTIES_NAME")
    public String getAdministrativeDutiesName() {
        return administrativeDutiesName;
    }

    public void setAdministrativeDutiesName(String administrativeDutiesName) {
        this.administrativeDutiesName = administrativeDutiesName;
    }

    @Basic
    @Column(name = "TENURE_UNIT")
    public String getTenureUnit() {
        return tenureUnit;
    }

    public void setTenureUnit(String tenureUnit) {
        this.tenureUnit = tenureUnit;
    }

    @Basic
    @Column(name = "PERSONAL_POSITION_ORDER")
    public String getPersonalPositionOrder() {
        return personalPositionOrder;
    }

    public void setPersonalPositionOrder(String personalPositionOrder) {
        this.personalPositionOrder = personalPositionOrder;
    }


    @Basic
    @Column(name = "TENURE_TIME")

    public String getXzTenureTime() {
        return xzTenureTime;
    }

    public void setXzTenureTime(String xzTenureTime) {
        this.xzTenureTime = xzTenureTime;
    }


    @Basic
    @Column(name = "OUTGOING_TIME")

    public String getXzOutgoingTime() {
        return xzOutgoingTime;
    }

    public void setXzOutgoingTime(String xzOutgoingTime) {
        this.xzOutgoingTime = xzOutgoingTime;
    }

    public DdjsDyglAdministrativeEntity() {
        super();
    }

    public DdjsDyglAdministrativeEntity(String administrativeId, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String administrativeSuperId, String administrativeDutiesName, String tenureUnit, String personalPositionOrder, String xzTenureTime, String xzOutgoingTime) {
        this.administrativeId = administrativeId;
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
        this.administrativeSuperId = administrativeSuperId;
        this.administrativeDutiesName = administrativeDutiesName;
        this.tenureUnit = tenureUnit;
        this.personalPositionOrder = personalPositionOrder;
        this.xzTenureTime = xzTenureTime;
        this.xzOutgoingTime = xzOutgoingTime;
    }
}
