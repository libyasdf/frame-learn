package com.sinosoft.sinoep.modules.zhbg.ldtxb.jldtxb.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "LDTXB_JLDTXB_INFO")
public class Jldtxb {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "CRE_USER_ID")
    private String creUserId;

    @Column(name = "CRE_USER_NAME")
    private String creUserName;

    @Column(name = "CRE_DEPT_ID")
    private String creDeptId;

    @Column(name = "CRE_DEPT_NAME")
    private String creDeptName;

    @Column(name = "CRE_CHUSHI_ID")
    private String creChushiId;

    @Column(name = "CRE_CHUSHI_NAME")
    private String creChushiName;

    @Column(name = "CRE_JU_ID")
    private String creJuId;

    @Column(name = "CRE_JU_NAME")
    private String creJuName;

    @Column(name = "VISIBLE")
    private String visible;

    @Column(name = "CRE_TIME")
    private String creTime;

    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    @Column(name = "UPDATE_TIME")
    private String updateTime;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PARTY_GOVERNMENT")
    private String partyGovernment;

    @Column(name = "PRIVATE_NETWORK")
    private String privateNetwork;

    @Column(name = "UNIVERSAL_NETWORK")
    private String universalNetwork;

    @Column(name = "PHONE")
    private String phone;

    @Transient
    private String cz = "";

    public Jldtxb() {

    }

    public Jldtxb(String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String updateUserId, String updateUserName, String updateTime, String name,String partyGovernment, String privateNetwork, String universalNetwork, String phone, String cz) {
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
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateTime = updateTime;
        this.name = name;
        this.partyGovernment = partyGovernment;
        this.privateNetwork = privateNetwork;
        this.universalNetwork = universalNetwork;
        this.phone = phone;
        this.cz = cz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyGovernment() {
        return partyGovernment;
    }

    public void setPartyGovernment(String partyGovernment) {
        this.partyGovernment = partyGovernment;
    }

    public String getPrivateNetwork() {
        return privateNetwork;
    }

    public void setPrivateNetwork(String privateNetwork) {
        this.privateNetwork = privateNetwork;
    }

    public String getUniversalNetwork() {
        return universalNetwork;
    }

    public void setUniversalNetwork(String universalNetwork) {
        this.universalNetwork = universalNetwork;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }
}
