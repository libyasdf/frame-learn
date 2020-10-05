package com.sinosoft.sinoep.modules.system.notice.entity;

public class NoticeSpEntity {

    private String id;
    private String title;
    private String draftTime;
    private String draftUserName;
    private String subflag;
    private String workItemId;
    private String cz;

    public NoticeSpEntity() {
    }

    public NoticeSpEntity(String id, String title, String draftTime, String draftUserName, String subflag, String workItemId,String cz) {
        this.id = id;
        this.title = title;
        this.draftTime = draftTime;
        this.draftUserName = draftUserName;
        this.subflag = subflag;
        this.workItemId = workItemId;
        this.cz = cz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDraftTime() {
        return draftTime;
    }

    public void setDraftTime(String draftTime) {
        this.draftTime = draftTime;
    }

    public String getDraftUserName() {
        return draftUserName;
    }

    public void setDraftUserName(String draftUserName) {
        this.draftUserName = draftUserName;
    }

    public String getSubflag() {
        return subflag;
    }

    public void setSubflag(String subflag) {
        this.subflag = subflag;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }
}
