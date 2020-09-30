package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "DDJS_SHYK_ANNUALPLAN_WORDTABL")
public class NdjhWordtablEntity {
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
    /**季度*/
    private String quarter;
    /**月份*/
    private String monthval;
    /**形式*/
    private String modeval;
    /**主要内容*/
    private String primaryCoverage;
    /**attr1*/
    private String attr1;
    /**attr2*/
    private String attr2;
    /**主表id*/
    private String ndjhId;
    /**排序*/
    private BigDecimal sort;

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
    @Column(name = "CRE_JU_ID")
    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
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
    @Column(name = "QUARTER")
    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    @Basic
    @Column(name = "MONTHVAL")
    public String getMonthval() {
        return monthval;
    }

    public void setMonthval(String monthval) {
        this.monthval = monthval;
    }

    @Basic
    @Column(name = "MODEVAL")
    public String getModeval() {
        return modeval;
    }

    public void setModeval(String modeval) {
        this.modeval = modeval;
    }

    @Basic
    @Column(name = "PRIMARY_COVERAGE")
    public String getPrimaryCoverage() {
        return primaryCoverage;
    }

    public void setPrimaryCoverage(String primaryCoverage) {
        this.primaryCoverage = primaryCoverage;
    }

    @Basic
    @Column(name = "ATTR1")
    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    @Basic
    @Column(name = "ATTR2")
    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    @Basic
    @Column(name = "NDJH_ID")
    public String getNdjhId() {
        return ndjhId;
    }

    public void setNdjhId(String ndjhId) {
        this.ndjhId = ndjhId;
    }
    @Basic
    @Column(name = "SORT")
    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public NdjhWordtablEntity(String attr2, String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String quarter, String monthval, String modeval, String primaryCoverage, String attr1, String ndjhId,BigDecimal sort) {
        this.attr2 = attr2;
        this.id = id;
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creDeptId = creDeptId;
        this.creDeptName = creDeptName;
        this.creChushiId = creChushiId;
        this.creChushiName = creChushiName;
        this.creJuId = creJuId;
        this.creJuName = creJuName;
        this.visible = visible;
        this.creTime = creTime;
        this.quarter = quarter;
        this.monthval = monthval;
        this.modeval = modeval;
        this.primaryCoverage = primaryCoverage;
        this.attr1 = attr1;
        this.ndjhId = ndjhId;
        this.sort =sort;
    }
    public NdjhWordtablEntity(){
        super();
    }
}
