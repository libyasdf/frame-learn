package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DDJS_DYGL_REWARD")
public class DdjsDyglRewardEntity {
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
    private String superId;
    private String partyMember;
    private String partyMemberId;
    private String partyOrganization;
    private String partyOrganizationId;

    private String rewardTime;
    private String rewardUnit;
    private String partyBasicSituation;
    private String rewardContent;
    private String rewardReason;
    private String approvalOrganLevel;
    private String mainDeeds;
    private String rewardRevokeTime;
    private String remarks;

    @Basic
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
    @Column(name = "SUPER_ID")
    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    @Basic
    @Column(name = "PARTY_MEMBER")
    public String getPartyMember() {
        return partyMember;
    }

    public void setPartyMember(String partyMember) {
        this.partyMember = partyMember;
    }

    @Basic
    @Column(name = "PARTY_MEMBER_ID")
    public String getPartyMemberId() {
        return partyMemberId;
    }

    public void setPartyMemberId(String partyMemberId) {
        this.partyMemberId = partyMemberId;
    }

    @Basic
    @Column(name = "PARTY_ORGANIZATION")
    public String getPartyOrganization() {
        return partyOrganization;
    }

    public void setPartyOrganization(String partyOrganization) {
        this.partyOrganization = partyOrganization;
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
    @Column(name = "REWARD_TIME")
    public String getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(String rewardTime) {
        this.rewardTime = rewardTime;
    }

    @Basic
    @Column(name = "REWARD_UNIT")
    public String getRewardUnit() {
        return rewardUnit;
    }

    public void setRewardUnit(String rewardUnit) {
        this.rewardUnit = rewardUnit;
    }

    @Basic
    @Column(name = "PARTY_BASIC_SITUATION")
    public String getPartyBasicSituation() {
        return partyBasicSituation;
    }

    public void setPartyBasicSituation(String partyBasicSituation) {
        this.partyBasicSituation = partyBasicSituation;
    }

    @Basic
    @Column(name = "REWARD_CONTENT")
    public String getRewardContent() {
        return rewardContent;
    }

    public void setRewardContent(String rewardContent) {
        this.rewardContent = rewardContent;
    }

    @Basic
    @Column(name = "REWARD_REASON")
    public String getRewardReason() {
        return rewardReason;
    }

    public void setRewardReason(String rewardReason) {
        this.rewardReason = rewardReason;
    }

    @Basic
    @Column(name = "APPROVAL_ORGAN_LEVEL")
    public String getApprovalOrganLevel() {
        return approvalOrganLevel;
    }

    public void setApprovalOrganLevel(String approvalOrganLevel) {
        this.approvalOrganLevel = approvalOrganLevel;
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
    @Column(name = "REWARD_REVOKE_TIME")
    public String getRewardRevokeTime() {
        return rewardRevokeTime;
    }

    public void setRewardRevokeTime(String rewardRevokeTime) {
        this.rewardRevokeTime = rewardRevokeTime;
    }

    @Basic
    @Column(name = "REMARKS")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public DdjsDyglRewardEntity(String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String superId, String partyMember, String partyMemberId, String partyOrganization, String partyOrganizationId, String rewardTime, String rewardUnit, String partyBasicSituation, String rewardContent, String rewardReason, String approvalOrganLevel, String mainDeeds, String rewardRevokeTime, String remarks) {
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
        this.superId = superId;
        this.partyMember = partyMember;
        this.partyMemberId = partyMemberId;
        this.partyOrganization = partyOrganization;
        this.partyOrganizationId = partyOrganizationId;
        this.rewardTime = rewardTime;
        this.rewardUnit = rewardUnit;
        this.partyBasicSituation = partyBasicSituation;
        this.rewardContent = rewardContent;
        this.rewardReason = rewardReason;
        this.approvalOrganLevel = approvalOrganLevel;
        this.mainDeeds = mainDeeds;
        this.rewardRevokeTime = rewardRevokeTime;
        this.remarks = remarks;
    }

    public DdjsDyglRewardEntity() {
        super();
    }
}
