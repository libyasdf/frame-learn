package com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity;

public class RelationVo {

    /** 门类id*/
    private String cateId;

    /** 数据字典里立卷单位名称*/
    private String ljdwName;

    /** 数据字典对应的mark值*/
    private String ljdwMark;

    /** 录入人name*/
    private String lrrNames;

    /** 录入人id*/
    private String lrrIds;

    /** 立卷单位管理员name*/
    private String ljdwAdminNames;

    /** 立卷单位管理员id*/
    private String ljdwAdminIds;

    /** 录入人所在部门id*/
    private String lrrDeptId;

    /** 立卷单位管理员所在部门id*/
    private String ljdwAdminDeptId;

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getLjdwName() {
        return ljdwName;
    }

    public void setLjdwName(String ljdwName) {
        this.ljdwName = ljdwName;
    }

    public String getLjdwMark() {
        return ljdwMark;
    }

    public void setLjdwMark(String ljdwMark) {
        this.ljdwMark = ljdwMark;
    }

    public String getLrrNames() {
        return lrrNames;
    }

    public void setLrrNames(String lrrNames) {
        this.lrrNames = lrrNames;
    }

    public String getLrrIds() {
        return lrrIds;
    }

    public void setLrrIds(String lrrIds) {
        this.lrrIds = lrrIds;
    }

    public String getLjdwAdminNames() {
        return ljdwAdminNames;
    }

    public void setLjdwAdminNames(String ljdwAdminNames) {
        this.ljdwAdminNames = ljdwAdminNames;
    }

    public String getLjdwAdminIds() {
        return ljdwAdminIds;
    }

    public void setLjdwAdminIds(String ljdwAdminIds) {
        this.ljdwAdminIds = ljdwAdminIds;
    }

    public String getLrrDeptId() {
        return lrrDeptId;
    }

    public void setLrrDeptId(String lrrDeptId) {
        this.lrrDeptId = lrrDeptId;
    }

    public String getLjdwAdminDeptId() {
        return ljdwAdminDeptId;
    }

    public void setLjdwAdminDeptId(String ljdwAdminDeptId) {
        this.ljdwAdminDeptId = ljdwAdminDeptId;
    }
}
