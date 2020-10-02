package com.sinosoft.sinoep.user.entity;

public class DeptAllInfo {

    private String deptId;//部门id
    private String treeId;//树节点id
    private String deptname;//部门name
    private String abbreviation;
    private String superId;//上级id
    private Long orderNo;//序号
    private String deptNumber;//部门排序
    private String deptPhone;//部门电话
    private String status;//状态
    private String orgId;//局id
    private String orgName;//局name
    private String juId;//二级局id
    private String juName;//二级局name
    private String chushiId;//处室id
    private String chushiName;//处室name

    public DeptAllInfo() {
        super();
    }

    public DeptAllInfo(String deptId, String treeId, String deptname, String abbreviation, String superId, Long orderNo, String deptNumber, String deptPhone, String status, String orgId, String orgName, String juId, String juName, String chushiId, String chushiName) {
        this.deptId = deptId;
        this.treeId = treeId;
        this.deptname = deptname;
        this.abbreviation = abbreviation;
        this.superId = superId;
        this.orderNo = orderNo;
        this.deptNumber = deptNumber;
        this.deptPhone = deptPhone;
        this.status = status;
        this.orgId = orgId;
        this.orgName = orgName;
        this.juId = juId;
        this.juName = juName;
        this.chushiId = chushiId;
        this.chushiName = chushiName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getDeptNumber() {
        return deptNumber;
    }

    public void setDeptNumber(String deptNumber) {
        this.deptNumber = deptNumber;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getJuId() {
        return juId;
    }

    public void setJuId(String juId) {
        this.juId = juId;
    }

    public String getJuName() {
        return juName;
    }

    public void setJuName(String juName) {
        this.juName = juName;
    }

    public String getChushiId() {
        return chushiId;
    }

    public void setChushiId(String chushiId) {
        this.chushiId = chushiId;
    }

    public String getChushiName() {
        return chushiName;
    }

    public void setChushiName(String chushiName) {
        this.chushiName = chushiName;
    }
}
