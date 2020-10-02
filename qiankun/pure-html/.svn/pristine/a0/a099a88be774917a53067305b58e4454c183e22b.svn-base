package com.sinosoft.sinoep.modules.video.authority.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Programa_FB_DEPT_ZWQX_LIST", schema = "TJXM")
public class ProgramaFbDeptZwqxList {
    private String id;
    private String columnId;
    private String creUserId;
    private String creUserName;
    private String creTime;
    private String visible;
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

    @Transient
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public ProgramaFbDeptZwqxList() {
    }

    public ProgramaFbDeptZwqxList(String id, String columnId, String creUserId, String creUserName, String creTime, String visible, String line) {
        this.id = id;
        this.columnId = columnId;
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creTime = creTime;
        this.visible = visible;
        this.line = line;
    }

}
