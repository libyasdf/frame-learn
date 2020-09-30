package com.sinosoft.sinoep.modules.dagl.bygl.entity;

/**
 * @Author 王富康
 * @Description //TODO 查询部门下是否含有某个业务角色编号的人员 Vo
 * @Date 2019/1/7 16:16
 * @Param
 * @return
 **/
public class UserDeptVo {

    /* 部门ID */
    private String deptid;

    /* 部门名称 */
    private String deptname;

    /* 部门人员ID */
    private String userid;

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }






}

