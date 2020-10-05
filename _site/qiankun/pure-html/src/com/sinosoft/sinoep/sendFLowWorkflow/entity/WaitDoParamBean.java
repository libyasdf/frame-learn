package com.sinosoft.sinoep.sendFLowWorkflow.entity;

public class WaitDoParamBean {
    /**
     * 实例ID
     */
    private String objectId;

    /**
     * 当前用户单位ID
     */
    private String cruUnitId;

    /**
     * 当前业务ID
     */
    private String businessId;

    /**
     * 文件审核状态 01: 已审核，02:未审核
     */
    private String shStatus;

    /**
     * 点击待办单位的ID
     */
    private String unitId;
    private String unitName;
    private String templateId;
    private String waitDoId;
    private String nodeId;//文件每个单位所对应的节点
    private String ruleType;
    private String nowNodeId;//当前文件待办所在节点
    private String isSb; // 是否只有上报，不为空并且为"true"
    private String isDeleteWaitDo; // 1：删除待办，0或空：不删除
    private String deptlevel; //单位级别
    private String deptType; //查询单位类型
    private String sortName;//业务表排序字段
    private String orderBy; //排序类型：desc asc


    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCruUnitId() {
        return cruUnitId;
    }

    public void setCruUnitId(String cruUnitId) {
        this.cruUnitId = cruUnitId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getShStatus() {
        return shStatus;
    }

    public void setShStatus(String shStatus) {
        this.shStatus = shStatus;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getWaitDoId() {
        return waitDoId;
    }

    public void setWaitDoId(String waitDoId) {
        this.waitDoId = waitDoId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getNowNodeId() {
        return nowNodeId;
    }

    public void setNowNodeId(String nowNodeId) {
        this.nowNodeId = nowNodeId;
    }

    public String getIsSb() {
        return isSb;
    }

    public void setIsSb(String isSb) {
        this.isSb = isSb;
    }

    public String getIsDeleteWaitDo() {
        return isDeleteWaitDo;
    }

    public void setIsDeleteWaitDo(String isDeleteWaitDo) {
        this.isDeleteWaitDo = isDeleteWaitDo;
    }

    public String getDeptlevel() {
        return deptlevel;
    }

    public void setDeptlevel(String deptlevel) {
        this.deptlevel = deptlevel;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
