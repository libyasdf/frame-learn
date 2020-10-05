/**
 * Copyright 2017 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.workflow.model;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：工作流实体类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 卢跃文
 * @since 2017年3月29日
 */
public class WorkFlowInfo {
    /**
     * 流程ID
     */
    private String workFlowId;
    /**
     * 流程名称
     */
    private String workFlowName;
    /**
     * 流程管理人
     */
    private String userId;
    /**
     * 部门ID
     */
    private String deptId;
    /**
     * 流程类型名称
     */
    private String fileTypeName;
    /**
     * 流程类型ID
     */
    private String fileTypeId;
    /**
     * 待办路径
     */
    private String waitUrl;
    /**
     * 已办路径
     */
    private String hadDoneUrl;
    /**
     * 分类ID
     */
    private String sortid;
    /**
     * 表单ID
     */
    private String formid;

    /**
     * <B>取得：</B>workFlowId<BR>
     * 
     * @return String
     */
    public String getWorkFlowId() {
        return workFlowId;
    }

    /**
     * <B>设定：</B>workFlowId<BR>
     * 
     * @param workFlowId
     */
    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    /**
     * <B>取得：</B>workFlowName<BR>
     * 
     * @return String
     */
    public String getWorkFlowName() {
        return workFlowName;
    }

    /**
     * <B>设定：</B>workFlowName<BR>
     * 
     * @param workFlowName
     */
    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }

    /**
     * <B>取得：</B>userId<BR>
     * 
     * @return String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * <B>设定：</B>userId<BR>
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * <B>取得：</B>deptId<BR>
     * 
     * @return String
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * <B>设定：</B>deptId<BR>
     * 
     * @param deptId
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * <B>取得：</B>fileTypeName<BR>
     * 
     * @return String
     */
    public String getFileTypeName() {
        return fileTypeName;
    }

    /**
     * <B>设定：</B>fileTypeName<BR>
     * 
     * @param fileTypeName
     */
    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    /**
     * <B>取得：</B>fileTypeId<BR>
     * 
     * @return String
     */
    public String getFileTypeId() {
        return fileTypeId;
    }

    /**
     * <B>设定：</B>fileTypeId<BR>
     * 
     * @param fileTypeId
     */
    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    /**
     * <B>取得：</B>waitUrl<BR>
     * 
     * @return String
     */
    public String getWaitUrl() {
        return waitUrl;
    }

    /**
     * <B>设定：</B>waitUrl<BR>
     * 
     * @param waitUrl
     */
    public void setWaitUrl(String waitUrl) {
        this.waitUrl = waitUrl;
    }

    /**
     * <B>取得：</B>hadDoneUrl<BR>
     * 
     * @return String
     */
    public String getHadDoneUrl() {
        return hadDoneUrl;
    }

    /**
     * <B>设定：</B>hadDoneUrl<BR>
     * 
     * @param hadDoneUrl
     */
    public void setHadDoneUrl(String hadDoneUrl) {
        this.hadDoneUrl = hadDoneUrl;
    }

    /**
     * <B>取得：</B>sortid<BR>
     * 
     * @return String
     */
    public String getSortid() {
        return sortid;
    }

    /**
     * <B>设定：</B>sortid<BR>
     * 
     * @param sortid
     */
    public void setSortid(String sortid) {
        this.sortid = sortid;
    }

    /**
     * <B>取得：</B>formid<BR>
     * 
     * @return String
     */
    public String getFormid() {
        return formid;
    }

    /**
     * <B>设定：</B>formid<BR>
     * 
     * @param formid
     */
    public void setFormid(String formid) {
        this.formid = formid;
    }

}
