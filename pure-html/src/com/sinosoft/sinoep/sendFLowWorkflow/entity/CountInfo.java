package com.sinosoft.sinoep.sendFLowWorkflow.entity;

public class CountInfo {

    //部门id
    private String deptid;
    //部门name
    private String deptName;
    //总共
    private int sum;
    //未上报
    private int noSub;
    //已上报
    private int sub;
    //退回
    private int back;

    public CountInfo() {
    }

    public CountInfo(String deptid, String deptName, int sum, int noSub, int sub, int back) {
        this.deptid = deptid;
        this.deptName = deptName;
        this.sum = sum;
        this.noSub = noSub;
        this.sub = sub;
        this.back = back;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getNoSub() {
        return noSub;
    }

    public void setNoSub(int noSub) {
        this.noSub = noSub;
    }

    public int getSub() {
        return sub;
    }

    public void setSub(int sub) {
        this.sub = sub;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }
}
