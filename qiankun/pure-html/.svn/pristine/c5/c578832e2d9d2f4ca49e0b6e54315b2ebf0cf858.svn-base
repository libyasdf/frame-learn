package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DDJS_DZZ_HISTORY_ORG")
public class DwxtHistoryOrg {
    private String id;
    private String dwxtOrgId;
    private String historyOrgId;
    private String creUserId;
    private String creUserName;
    private String creChushiId;
    private String creChushiName;
    private String creTime;
    private String revokeId;
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
    @Column(name = "HISTORY_ORG_ID")
    public String getHistoryOrgId() {
        return historyOrgId;
    }

    public void setHistoryOrgId(String historyOrgId) {
        this.historyOrgId = historyOrgId;
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
    @Column(name = "CRE_TIME")
    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }


    @Basic
    @Column(name = "REVOKE_ID")
    public String getRevokeId() {
        return revokeId;
    }

    public void setRevokeId(String revokeId) {
        this.revokeId = revokeId;
    }

}
