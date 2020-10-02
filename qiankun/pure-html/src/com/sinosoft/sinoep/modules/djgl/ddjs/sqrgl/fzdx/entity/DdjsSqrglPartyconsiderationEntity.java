package com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.fzdx.entity;/**
 * Created by s on 2018/9/15.
 */

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description :TODO
 * @Author: 李帅
 * @Date: 2018/9/15 13:55
 */
@Entity
@Table(name = "DDJS_SQRGL_PARTYCONSIDERATION")
public class DdjsSqrglPartyconsiderationEntity {
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
    /**人员基本情况id*/
    private String superId;
    /** 预计发展日期*/
    private String expectedDevelopmentTime;
    /**实际发展日期*/
    private String actualDevelopmentTime;
    /**入党介绍人*/
    private String introducer;
    /**党组织审议时间*/
    private String partyconsiderationTime;
    /**机关党委审批时间*/
    private String organApprovalTime;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "ID", length = 50)
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
    @Column(name = "SUPER_ID")
    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    @Basic
    @Column(name = "EXPECTED_DEVELOPMENT_TIME")
    public String getExpectedDevelopmentTime() {
        return expectedDevelopmentTime;
    }

    public void setExpectedDevelopmentTime(String expectedDevelopmentTime) {
        this.expectedDevelopmentTime = expectedDevelopmentTime;
    }

    @Basic
    @Column(name = "ACTUAL_DEVELOPMENT_TIME")
    public String getActualDevelopmentTime() {
        return actualDevelopmentTime;
    }

    public void setActualDevelopmentTime(String actualDevelopmentTime) {
        this.actualDevelopmentTime = actualDevelopmentTime;
    }

    @Basic
    @Column(name = "INTRODUCER")
    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    @Basic
    @Column(name = "PARTYCONSIDERATION_TIME")
    public String getPartyconsiderationTime() {
        return partyconsiderationTime;
    }

    public void setPartyconsiderationTime(String partyconsiderationTime) {
        this.partyconsiderationTime = partyconsiderationTime;
    }

    @Basic
    @Column(name = "ORGAN_APPROVAL_TIME")
    public String getOrganApprovalTime() {
        return organApprovalTime;
    }

    public void setOrganApprovalTime(String organApprovalTime) {
        this.organApprovalTime = organApprovalTime;
    }
    public DdjsSqrglPartyconsiderationEntity(){super();}
    public DdjsSqrglPartyconsiderationEntity(String organApprovalTime, String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String superId, String expectedDevelopmentTime, String actualDevelopmentTime, String introducer, String partyconsiderationTime) {
        this.organApprovalTime = organApprovalTime;
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
        this.superId = superId;
        this.expectedDevelopmentTime = expectedDevelopmentTime;
        this.actualDevelopmentTime = actualDevelopmentTime;
        this.introducer = introducer;
        this.partyconsiderationTime = partyconsiderationTime;
    }
}
