package com.sinosoft.sinoep.modules.info.authority.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "COLUMN_FB_DEPT_ZWQX", schema = "TJXM")
public class ColumnFbDeptZwqx {
    private String id;
    private String columnId;
    private String deptId;
    private String deptName;
    private String positionId;
    private String positionName;
    private String creUserId;
    private String creUserName;
    private String creTime;
    private String visible;
    private String deptZwqxListId;

    private String line;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "COLUMN_ID")
    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    @Basic
    @Column(name = "DEPT_ID")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Basic
    @Column(name = "DEPT_NAME")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Basic
    @Column(name = "POSITION_ID")
    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @Basic
    @Column(name = "POSITION_NAME")
    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Basic
    @Column(name = "CRE_USER_ID")
    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    @Basic
    @Column(name = "CRE_USER_NAME")
    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    @Basic
    @Column(name = "CRE_TIME")
    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    @Basic
    @Column(name = "VISIBLE")
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "DEPT_ZWQX_LIST_ID")
    public String getDeptZwqxListId() {
        return deptZwqxListId;
    }

    public void setDeptZwqxListId(String deptZwqxListId) {
        this.deptZwqxListId = deptZwqxListId;
    }

    @Transient
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public ColumnFbDeptZwqx() {
    }

    public ColumnFbDeptZwqx(String id, String columnId, String deptId, String deptName, String positionId, String positionName, String creUserId, String creUserName, String creTime, String visible, String deptZwqxListId, String line) {
        this.id = id;
        this.columnId = columnId;
        this.deptId = deptId;
        this.deptName = deptName;
        this.positionId = positionId;
        this.positionName = positionName;
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creTime = creTime;
        this.visible = visible;
        this.deptZwqxListId = deptZwqxListId;
        this.line = line;
    }
}
