package com.sinosoft.sinoep.modules.invocation.responsibility.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "COLUMN_FB_DEPT", schema = "TJXM", catalog = "")
public class ColumnFbDeptEntity {
    private String id;
    private String columnId;
    private String deptId;
    private String deptName;
    private String creUserId;
    private String creUserName;
    private String creTime;
    private String visible;

    @Id
    @Column(name = "ID")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnFbDeptEntity that = (ColumnFbDeptEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(columnId, that.columnId) &&
                Objects.equals(deptId, that.deptId) &&
                Objects.equals(deptName, that.deptName) &&
                Objects.equals(creUserId, that.creUserId) &&
                Objects.equals(creUserName, that.creUserName) &&
                Objects.equals(creTime, that.creTime) &&
                Objects.equals(visible, that.visible);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, columnId, deptId, deptName, creUserId, creUserName, creTime, visible);
    }
}
