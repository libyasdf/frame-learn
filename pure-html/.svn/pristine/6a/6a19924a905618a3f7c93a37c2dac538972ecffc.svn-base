package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dnbz.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * @Author: 李帅
 * @Date: 2018/9/9 10:56
 */
@Entity
@Table(name = "DDJS_DZZ_COMMENDATION")
public class DnbzEntity {
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
    private String grantTime;
    private String grantUnit;
    private String typeval;
    private String mainDeeds;
    private String basicSituation;
    private String unitOpinion;
    private String approvalOpinion;
    private String memo;
    private String cz = "";
    private String partyOrganizationName;
    private String partyOrganizationId;

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
    @Column(name = "GRANT_TIME")
    public String getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(String grantTime) {
        this.grantTime = grantTime;
    }

    @Basic
    @Column(name = "GRANT_UNIT")
    public String getGrantUnit() {
        return grantUnit;
    }

    public void setGrantUnit(String grantUnit) {
        this.grantUnit = grantUnit;
    }

    @Basic
    @Column(name = "TYPEVAL")
    public String getTypeval() {
        return typeval;
    }

    public void setTypeval(String typeval) {
        this.typeval = typeval;
    }

    @Basic
    @Column(name = "MAIN_DEEDS")
    public String getMainDeeds() {
        return mainDeeds;
    }

    public void setMainDeeds(String mainDeeds) {
        this.mainDeeds = mainDeeds;
    }

    @Basic
    @Column(name = "BASIC_SITUATION")
    public String getBasicSituation() {
        return basicSituation;
    }

    public void setBasicSituation(String basicSituation) {
        this.basicSituation = basicSituation;
    }

    @Basic
    @Column(name = "UNIT_OPINION")
    public String getUnitOpinion() {
        return unitOpinion;
    }

    public void setUnitOpinion(String unitOpinion) {
        this.unitOpinion = unitOpinion;
    }

    @Basic
    @Column(name = "APPROVAL_OPINION")
    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

    @Basic
    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    @Basic
    @Column(name = "party_organization_name")
    public String getPartyOrganizationName() {
        return partyOrganizationName;
    }

    public void setPartyOrganizationName(String partyOrganizationName) {
        this.partyOrganizationName = partyOrganizationName;
    }
    @Basic
    @Column(name = "party_organization_id")
    public String getPartyOrganizationId() {
        return partyOrganizationId;
    }

    public void setPartyOrganizationId(String partyOrganizationId) {
        this.partyOrganizationId = partyOrganizationId;
    }

    public DnbzEntity(){super();};

    public DnbzEntity(String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String grantTime, String grantUnit, String typeval, String mainDeeds, String basicSituation, String unitOpinion, String approvalOpinion, String memo, String cz, String partyOrganizationName, String partyOrganizationId) {
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
        this.grantTime = grantTime;
        this.grantUnit = grantUnit;
        this.typeval = typeval;
        this.mainDeeds = mainDeeds;
        this.basicSituation = basicSituation;
        this.unitOpinion = unitOpinion;
        this.approvalOpinion = approvalOpinion;
        this.memo = memo;
        this.cz = cz;
        this.partyOrganizationName = partyOrganizationName;
        this.partyOrganizationId = partyOrganizationId;
    }
}
