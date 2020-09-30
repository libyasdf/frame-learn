package com.sinosoft.sinoep.modules.video.background.entity;

public class VideoSpEntity {

    private String id;
    private String title;
    private String draftTime;
    private String columnName;
    private String draftUserName;
    private String subflag;
    private String columnId;
    private String workItemId;
    //判断当前登录人是发布人还是审核人  0：发布人  1：审批人
    private String isFbOrSpUser; 

    public VideoSpEntity() {
    }

    public VideoSpEntity(String id, String title, String draftTime, String columnName, String draftUserName, String subflag, String columnId, String workItemId) {
        this.id = id;
        this.title = title;
        this.draftTime = draftTime;
        this.columnName = columnName;
        this.draftUserName = draftUserName;
        this.subflag = subflag;
        this.columnId = columnId;
        this.workItemId = workItemId;
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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
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

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

	public String getIsFbOrSpUser() {
		return isFbOrSpUser;
	}

	public void setIsFbOrSpUser(String isFbOrSpUser) {
		this.isFbOrSpUser = isFbOrSpUser;
	}
    
    
}
