package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * TODO 党组织管理 党员民主评议实体类
 * @Author: 李帅
 * @Date: 2018/9/10 14:30
 */
@Entity
@Table(name = "DDJS_DZZ_PARTYREVIEW")
public class DymzpyEntity {
    /** 主键ID*/
    private String id;
    /**创建人id*/
    private String creUserId;
    /**创建人名*/
    private String creUserName;
    /**
     创建人部门id*/
    private String creDeptId;
    /**创建人部门名*/
    private String creDeptName;
    /**创建人处室id*/
    private String creChushiId;
    /**创建人处室名*/
    private String creChushiName;
    /**创建人二级局id*/
    private String creJuId;
    /**创建人二级局名*/
    private String creJuName;
    /**逻辑删除*/
    private String visible;
    /**创建时间*/
    private String creTime;
    /**党员姓名*/
    private String partyName;
    /**党员ID*/
    private String partyId;
    /**开始时间*/
    private String startTime;
    /**结束时间*/
    private String endTime;
    /**评议结果*/
    private String reviewResults;
    /**评议原因*/
    private String reviewReason;
    /**处理情况*/
    private String handleSituation;
    /**处理原因*/
    private String handleReason;
    /**处理原因类型*/
    private String handleReasonType;
    /**上级党组织意见*/
    private String partyOrganizationOpinion;
    /**备注*/
    private String memo;
    /**主表ID*/
    private String reviewid;
    private String cz;

    /**
     * 党员排序 拼到时间字段里 解决分页时间一样 顺序错误
     */
    private Integer dyOrder;

    @Transient
    public Integer getDyOrder() {
        return dyOrder;
    }

    public void setDyOrder(Integer dyOrder) {
        this.dyOrder = dyOrder;
    }

    /** 操作 */
    @Transient
    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }
    /** 主键id */
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
    @Column(name = "PARTY_NAME")
    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    @Basic
    @Column(name = "PARTY_ID")
    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    @Basic
    @Column(name = "START_TIME")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "END_TIME")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "REVIEW_RESULTS")
    public String getReviewResults() {
        return reviewResults;
    }

    public void setReviewResults(String reviewResults) {
        this.reviewResults = reviewResults;
    }

    @Basic
    @Column(name = "REVIEW_REASON")
    public String getReviewReason() {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason;
    }

    @Basic
    @Column(name = "HANDLE_SITUATION")
    public String getHandleSituation() {
        return handleSituation;
    }

    public void setHandleSituation(String handleSituation) {
        this.handleSituation = handleSituation;
    }

    @Basic
    @Column(name = "HANDLE_REASON")
    public String getHandleReason() {
        return handleReason;
    }

    public void setHandleReason(String handleReason) {
        this.handleReason = handleReason;
    }
    @Basic
    @Column(name = "HANDLE_REASON_TYPE")
    public String getHandleReasonType() {
        return handleReasonType;
    }

    public void setHandleReasonType(String handleReasonType) {
        this.handleReasonType = handleReasonType;
    }



    @Basic
    @Column(name = "PARTY_ORGANIZATION_OPINION")
    public String getPartyOrganizationOpinion() {
        return partyOrganizationOpinion;
    }

    public void setPartyOrganizationOpinion(String partyOrganizationOpinion) {
        this.partyOrganizationOpinion = partyOrganizationOpinion;
    }

    @Basic
    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    @Basic
    @Column(name = "REVIEWID")

    public String getReviewid() {
        return reviewid;
    }

    public void setReviewid(String reviewid) {
        this.reviewid = reviewid;
    }
    public DymzpyEntity(){super();}

    public DymzpyEntity(String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String partyName, String partyId, String startTime, String endTime, String reviewResults, String reviewReason, String handleSituation, String handleReason, String handleReasonType, String partyOrganizationOpinion, String memo, String reviewid, String cz,Integer dyOrder) {
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
        this.partyName = partyName;
        this.partyId = partyId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reviewResults = reviewResults;
        this.reviewReason = reviewReason;
        this.handleSituation = handleSituation;
        this.handleReason = handleReason;
        this.handleReasonType = handleReasonType;
        this.partyOrganizationOpinion = partyOrganizationOpinion;
        this.memo = memo;
        this.reviewid = reviewid;
        this.cz = cz;
        this.dyOrder = dyOrder;
    }
}
