package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DDJS_DZZ_ORG")
public class DwxtOrg {
    private String id;
    private String orgName;
    private String orgFullName;
    private String orgId;
    private String orgType;
    private String contactMan;
    private String contactNumber;
    private String belongAddress;
    private String creDate;
    private String fileNumber;
    private String postalAddress;
    private String postalCode;
    private String reason;
    private String superId;
    private Integer orgOrder;
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
    private String revokeId;
    private String partyBranchId;
    private String associativeUnitId;
    private String associativeUnitName;
    private String associativeUserId;
    private String associativeUserName;
    private String historyOrgId;
    private String revokeDate;
    private String revokeCode;
    private String revokeReason;
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
    @Column(name = "ORG_NAME")
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Basic
    @Column(name = "ORG_FULL_NAME")
    public String getOrgFullName() {
        return orgFullName;
    }

    public void setOrgFullName(String orgFullName) {
        this.orgFullName = orgFullName;
    }

    @Basic
    @Column(name = "ORG_ID")
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "ORG_TYPE")
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Basic
    @Column(name = "CONTACT_MAN")
    public String getContactMan() {
        return contactMan;
    }

    public void setContactMan(String contactMan) {
        this.contactMan = contactMan;
    }

    @Basic
    @Column(name = "CONTACT_NUMBER")
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Basic
    @Column(name = "BELONG_ADDRESS")
    public String getBelongAddress() {
        return belongAddress;
    }

    public void setBelongAddress(String belongAddress) {
        this.belongAddress = belongAddress;
    }

    @Basic
    @Column(name = "CRE_DATE")
    public String getCreDate() {
        return creDate;
    }

    public void setCreDate(String creDate) {
        this.creDate = creDate;
    }

    @Basic
    @Column(name = "FILE_NUMBER")
    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    @Basic
    @Column(name = "POSTAL_ADDRESS")
    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Basic
    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "REASON")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
    @Column(name = "ORG_ORDER")
    public Integer getOrgOrder() {
        return orgOrder;
    }

    public void setOrgOrder(Integer orgOrder) {
        this.orgOrder = orgOrder;
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

    @Basic
    @Column(name = "REVOKE_ID")
    public String getRevokeId() {
        return revokeId;
    }

    public void setRevokeId(String revokeId) {
        this.revokeId = revokeId;
    }

    @Basic
    @Column(name = "PARTY_BRANCH_ID")
    public String getPartyBranchId() {
        return partyBranchId;
    }

    public void setPartyBranchId(String partyBranchId) {
        this.partyBranchId = partyBranchId;
    }

    @Basic
    @Column(name = "ASSOCIATIVE_UNIT_ID")
    public String getAssociativeUnitId() {
        return associativeUnitId;
    }

    public void setAssociativeUnitId(String associativeUnitId) {
        this.associativeUnitId = associativeUnitId;
    }

    @Basic
    @Column(name = "ASSOCIATIVE_UNIT_NAME")
    public String getAssociativeUnitName() {
        return associativeUnitName;
    }

    public void setAssociativeUnitName(String associativeUnitName) {
        this.associativeUnitName = associativeUnitName;
    }

    @Basic
    @Column(name = "ASSOCIATIVE_USER_ID")
    public String getAssociativeUserId() {
        return associativeUserId;
    }

    public void setAssociativeUserId(String associativeUserId) {
        this.associativeUserId = associativeUserId;
    }

    @Basic
    @Column(name = "ASSOCIATIVE_USER_NAME")
    public String getAssociativeUserName() {
        return associativeUserName;
    }

    public void setAssociativeUserName(String associativeUserName) {
        this.associativeUserName = associativeUserName;
    }

    @Transient
    public String getHistoryOrgId() {
        return historyOrgId;
    }

    public void setHistoryOrgId(String historyOrgId) {
        this.historyOrgId = historyOrgId;
    }

    @Transient
    public String getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(String revokeDate) {
        this.revokeDate = revokeDate;
    }

    @Transient
    public String getRevokeCode() {
        return revokeCode;
    }

    public void setRevokeCode(String revokeCode) {
        this.revokeCode = revokeCode;
    }

    @Transient
    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }
}
