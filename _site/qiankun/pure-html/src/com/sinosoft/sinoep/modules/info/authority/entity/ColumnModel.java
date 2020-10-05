package com.sinosoft.sinoep.modules.info.authority.entity;

import java.util.List;

public class ColumnModel {

    private String columnId;

    private List<ColumnFbDept> deptList;

    private List<ColumnFbZwqx> zwqxList;

    private List<ColumnFbGroup> groupList;

    private List<ColumnFbDeptZwqxList> deptZwqxListList;

    private List<ColumnFbDeptZwqx> deptZwqxList;

    public List<ColumnFbDept> getDeptList() {
        return deptList;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public void setDeptList(List<ColumnFbDept> deptList) {
        this.deptList = deptList;
    }

    public List<ColumnFbZwqx> getZwqxList() {
        return zwqxList;
    }

    public void setZwqxList(List<ColumnFbZwqx> zwqxList) {
        this.zwqxList = zwqxList;
    }

    public List<ColumnFbGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<ColumnFbGroup> groupList) {
        this.groupList = groupList;
    }

    public List<ColumnFbDeptZwqxList> getDeptZwqxListList() {
        return deptZwqxListList;
    }

    public void setDeptZwqxListList(List<ColumnFbDeptZwqxList> deptZwqxListList) {
        this.deptZwqxListList = deptZwqxListList;
    }

    public List<ColumnFbDeptZwqx> getDeptZwqxList() {
        return deptZwqxList;
    }

    public void setDeptZwqxList(List<ColumnFbDeptZwqx> deptZwqxList) {
        this.deptZwqxList = deptZwqxList;
    }
}
