package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 年度计划主表实体类
 * @Author: 李帅
 * @Date: 2018/8/30 9:34
 */
@Entity
@Table(name = "DDJS_SHYK_ANNUALPLAN")
public class NdjhEntity {
    /** 主键id */
    private String id;
    /** 创建人id */
    private String creUserId;
    /** 创建人姓名 */
    private String creUserName;
    /** 创建人部门ID */
    private String creDeptId;
    /** 创建人部门名*/
    private String creDeptName;
    /** 创建人处室ID */
    private String creChushiId;
    /** 创建人处室名*/
    private String creChushiName;
    /** 创建人二级局ID*/
    private String creJuId;
    /** 创建人二级局名 */
    private String creJuName;
    /** 逻辑删除*/
    private String visible;
    /** 创建时间 */
    private String creTime;
    /**流程状态*/
    private String subflag;
    /**待办标题*/
    private String title;
    /**党组织名*/
    private String partyOrganizationName;
    /**党组织ID*/
    private String partyOrganizationId;
    /**年度*/
    private String annual;
    /**上报时间*/
    private String reportingTime;
    /** 操作 */
    private String cz ="";
    /** 上级党组织名称 */
    private String superName = "";
    @Transient
    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }
    List<NdjhWordtablEntity> list = new ArrayList<NdjhWordtablEntity>();
    /** 主表对应的字表*/
    @Transient
    public List<NdjhWordtablEntity> getList() {
        return list;
    }
    public void setList(List<NdjhWordtablEntity> list) {
        this.list = list;
    }


    @Transient
    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "CRE_DEPT_ID")
    public String getCreDeptId() {
        return creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    @Basic
    @Column(name = "CRE_DEPT_NAME")
    public String getCreDeptName() {
        return creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_ID")
    public String getCreChushiId() {
        return creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_NAME")
    public String getCreChushiName() {
        return creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    @Basic
    @Column(name = "CRE_JU_NAME")
    public String getCreJuName() {
        return creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
    }

    @Basic
    @Column(name = "CRE_JU_ID")
    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
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
    @Column(name = "CRE_TIME")
    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    @Basic
    @Column(name = "SUBFLAG")
    public String getSubflag() {
        return subflag;
    }

    public void setSubflag(String subflag) {
        this.subflag = subflag;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "PARTY_ORGANIZATION_NAME")
    public String getPartyOrganizationName() {
        return partyOrganizationName;
    }

    public void setPartyOrganizationName(String partyOrganizationName) {
        this.partyOrganizationName = partyOrganizationName;
    }

    @Basic
    @Column(name = "PARTY_ORGANIZATION_ID")
    public String getPartyOrganizationId() {
        return partyOrganizationId;
    }

    public void setPartyOrganizationId(String partyOrganizationId) {
        this.partyOrganizationId = partyOrganizationId;
    }

    @Basic
    @Column(name = "ANNUAL")
    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    @Basic
    @Column(name = "REPORTING_TIME")
    public String getReportingTime() {
        return reportingTime;
    }

    public void setReportingTime(String reportingTime) {
        this.reportingTime = reportingTime;
    }





    public NdjhEntity(String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuName, String creJuId, String visible, String creTime, String subflag, String title, String partyOrganizationName, String partyOrganizationId, String annual, String reportingTime) {
        this.id = id;
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creDeptId = creDeptId;
        this.creDeptName = creDeptName;
        this.creChushiId = creChushiId;
        this.creChushiName = creChushiName;
        this.creJuName = creJuName;
        this.creJuId = creJuId;
        this.visible = visible;
        this.creTime = creTime;
        this.subflag = subflag;
        this.title = title;
        this.partyOrganizationName = partyOrganizationName;
        this.partyOrganizationId = partyOrganizationId;
        this.annual = annual;
        this.reportingTime = reportingTime;
    }
    public NdjhEntity(){
        super();
    }
}
