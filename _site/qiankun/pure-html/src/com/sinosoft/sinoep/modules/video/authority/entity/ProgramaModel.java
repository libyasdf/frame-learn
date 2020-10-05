package com.sinosoft.sinoep.modules.video.authority.entity;

import java.util.List;

public class ProgramaModel {

    private String columnId;

    private List<ProgramaFbDept> deptList;

    private List<ProgramaFbZwqx> zwqxList;

    private List<ProgramaFbDeptZwqxList> deptZwqxListList;

    private List<ProgramaFbDeptZwqx> deptZwqxList;
    
    private List<ProgramaFbGroup> groupList;

    public List<ProgramaFbDept> getDeptList() {
        return deptList;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public void setDeptList(List<ProgramaFbDept> deptList) {
        this.deptList = deptList;
    }

    public List<ProgramaFbZwqx> getZwqxList() {
        return zwqxList;
    }

    public void setZwqxList(List<ProgramaFbZwqx> zwqxList) {
        this.zwqxList = zwqxList;
    }

    public List<ProgramaFbDeptZwqxList> getDeptZwqxListList() {
        return deptZwqxListList;
    }

    public void setDeptZwqxListList(List<ProgramaFbDeptZwqxList> deptZwqxListList) {
        this.deptZwqxListList = deptZwqxListList;
    }

    public List<ProgramaFbDeptZwqx> getDeptZwqxList() {
        return deptZwqxList;
    }

    public void setDeptZwqxList(List<ProgramaFbDeptZwqx> deptZwqxList) {
        this.deptZwqxList = deptZwqxList;
    }

	public List<ProgramaFbGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<ProgramaFbGroup> groupList) {
		this.groupList = groupList;
	}
    
    
}
