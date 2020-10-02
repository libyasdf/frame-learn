package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzbg.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DDJS_DZZ_ORG_CHANGE")
public class DwxtOrgChange {
    private String id;
    private String originalUpId;
    private String originalUpName;
    private String targetId;
    private String targetName;
    private String originalId;
    private String originalName;
    private String changeId;
    private String changeName;
    private String updateUserId;
    private String updateUserName;
    private String updateTime;
    private String changeDate;
    private String reason;

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
    @Column(name = "ORIGINAL_UP_ID")
    public String getOriginalUpId() {
        return originalUpId;
    }

    public void setOriginalUpId(String originalUpId) {
        this.originalUpId = originalUpId;
    }



    @Basic
    @Column(name = "ORIGINAL_UP_NAME")
    public String getOriginalUpName() {
        return originalUpName;
    }

    public void setOriginalUpName(String originalUpName) {
        this.originalUpName = originalUpName;
    }

    @Basic
    @Column(name = "TARGET_ID")
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Basic
    @Column(name = "TARGET_NAME")
    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Basic
    @Column(name = "ORIGINAL_ID")
    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    @Basic
    @Column(name = "ORIGINAL_NAME")
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Basic
    @Column(name = "CHANGE_ID")
    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    @Basic
    @Column(name = "CHANGE_NAME")
    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
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
    @Column(name = "CHANGE_DATE")
    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    @Basic
    @Column(name = "REASON")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
