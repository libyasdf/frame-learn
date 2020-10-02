package com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.entity;

import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglUserbasicinfoEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * 申请人管理-申请人员情况-实体类
 * author 李帅
 */
@Entity
@Table(name = "DDJS_SQRGL_PARTYBASICINFO")
public class DdjsSqrglPartybasicinfoEntity {
    /** 主键id */
    private String partybasicinfoId;
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
    /**序号*/
    private String serialNumber;
    /**入党申请时间*/
    private String applicationTime;
    /**培养联系人*/
    private String trainingContacts;
    /**发展类型*/
    private String developmentType;
    /**政治面貌*/
    private String politicalOutlook;
    /**确定为积极分子时间*/
    private String determineActivistTime;
    /**确定为发展对象时间*/
    private String developmentObjectTime;
    /**奖惩情况*/
    private String rewardsAndPunishments;
    /**现时表现*/
    private String presentPerformance;
    /**备注*/
    private String memo;
    /** 操作 */
    private String cz ="";


    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "PARTYBASICINFO_ID", length = 50)
    public String getPartybasicinfoId() {
        return partybasicinfoId;
    }

    public void setPartybasicinfoId(String partybasicinfoId) {
        this.partybasicinfoId = partybasicinfoId;
    }

    @Transient
    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
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
    @Column(name = "SERIAL_NUMBER")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }



    @Basic
    @Column(name = "DEVELOPMENT_OBJECT_TIME")
    public String getDevelopmentObjectTime() {
        return developmentObjectTime;
    }

    public void setDevelopmentObjectTime(String developmentObjectTime) {
        this.developmentObjectTime = developmentObjectTime;
    }
    @Basic
    @Column(name = "APPLICATION_TIME")
    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    @Basic
    @Column(name = "TRAINING_CONTACTS")
    public String getTrainingContacts() {
        return trainingContacts;
    }

    public void setTrainingContacts(String trainingContacts) {
        this.trainingContacts = trainingContacts;
    }

    @Basic
    @Column(name = "DEVELOPMENT_TYPE")
    public String getDevelopmentType() {
        return developmentType;
    }

    public void setDevelopmentType(String developmentType) {
        this.developmentType = developmentType;
    }

    @Basic
    @Column(name = "POLITICAL_OUTLOOK")
    public String getPoliticalOutlook() {
        return politicalOutlook;
    }

    public void setPoliticalOutlook(String politicalOutlook) {
        this.politicalOutlook = politicalOutlook;
    }

    @Basic
    @Column(name = "DETERMINE_ACTIVIST_TIME")
    public String getDetermineActivistTime() {
        return determineActivistTime;
    }

    public void setDetermineActivistTime(String determineActivistTime) {
        this.determineActivistTime = determineActivistTime;
    }

    @Basic
    @Column(name = "REWARDS_AND_PUNISHMENTS")
    public String getRewardsAndPunishments() {
        return rewardsAndPunishments;
    }

    public void setRewardsAndPunishments(String rewardsAndPunishments) {
        this.rewardsAndPunishments = rewardsAndPunishments;
    }

    @Basic
    @Column(name = "PRESENT_PERFORMANCE")
    public String getPresentPerformance() {
        return presentPerformance;
    }

    public void setPresentPerformance(String presentPerformance) {
        this.presentPerformance = presentPerformance;
    }

    @Basic
    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public DdjsSqrglPartybasicinfoEntity(){super();}

    public DdjsSqrglPartybasicinfoEntity(String partybasicinfoId, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String superId, String serialNumber, String applicationTime, String trainingContacts, String developmentType, String politicalOutlook, String determineActivistTime, String developmentObjectTime, String rewardsAndPunishments, String presentPerformance, String memo, String cz) {
        this.partybasicinfoId = partybasicinfoId;
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
        this.serialNumber = serialNumber;
        this.applicationTime = applicationTime;
        this.trainingContacts = trainingContacts;
        this.developmentType = developmentType;
        this.politicalOutlook = politicalOutlook;
        this.determineActivistTime = determineActivistTime;
        this.developmentObjectTime = developmentObjectTime;
        this.rewardsAndPunishments = rewardsAndPunishments;
        this.presentPerformance = presentPerformance;
        this.memo = memo;
        this.cz = cz;
    }
}
