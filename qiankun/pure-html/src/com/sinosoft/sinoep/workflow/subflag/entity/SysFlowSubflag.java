package com.sinosoft.sinoep.workflow.subflag.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * TODO 流程状态表
 * @author 李利广
 * @Date 2019年01月14日 16:19:29
 */
@Entity
@Table(name = "sys_flow_subflag")
public class SysFlowSubflag {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "fileTypeId")
    private String fileTypeId;

    @Column(name = "workFlowId")
    private String workFlowId;

    @Column(name = "subflag")
    private String subflag;

    @Column(name = "recordId")
    private String recordId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getSubflag() {
        return subflag;
    }

    public void setSubflag(String subflag) {
        this.subflag = subflag;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
