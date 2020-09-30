package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * TODO 三会一课 党日活动实体类
 * @Author: 李帅
 * @Date: 2018/8/31 10:21
 */
@Entity
@Table(name = "DDJS_SHYK_PARTYDAY")
public class DrhdEntity {
    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    private String id;


    /** 创建人id */
    @Column(name = "CRE_USER_ID", length = 50)
    private String creUserId;

    /** 创建人姓名 */
    @Column(name = "CRE_USER_NAME", length = 50)
    private String creUserName;

    /** 创建人部门ID */
    @Column(name = "CRE_DEPT_ID", length = 50)
    private String creDeptId;

    /** 创建人部门名*/
    @Column(name = "CRE_DEPT_NAME", length = 50)
    private String creDeptName;

    /** 创建人处室ID */
    @Column(name = "CRE_CHUSHI_ID", length = 50)
    private String creChushiId;

    /** 创建人处室名*/
    @Column(name = "CRE_CHUSHI_NAME", length = 50)
    private String creChushiName;

    /** 创建人二级局ID*/
    @Column(name = "CRE_JU_ID", length = 50)
    private String creJuId;

    /** 创建人二级局名 */
    @Column(name = "CRE_JU_NAME", length = 50)
    private String creJuName;

    /** 逻辑删除*/
    @Column(name = "VISIBLE", length = 50)
    private String visible;

    /** 创建时间 */
    @Column(name = "CRE_TIME", length = 50)
    private String creTime;
    /**主持人*/
    @Column(name = "compere")
    private String compere;
    /**记录人*/
    @Column(name = "note_taker")
    private String noteTaker;
    /**应到人数*/
    @Column(name = "number_of_people")
    private String numberOfPeople;
    /**实到人数*/
    @Column(name = "actual_number")
    private String actualNumber;
    /**主要内容*/
    @Column(name = "primary_coverage")
    private String primaryCoverage;
    /**参加人员*/
    @Column(name = "attendants")
    private String attendants;
    /**请假人及原因*/
    @Column(name = "leave_and_reasons")
    private String leaveAndReasons;
    /**列席人*/
    @Column(name = "seats")
    private String seats;
    /**会议情况*/
    @Column(name = "meeting_situation")
    private String meetingSituation;
    /**党组织名*/
    @Column(name = "party_organization_name")
    private String partyOrganizationName;
    /**党组织ID*/
    @Column(name = "party_organization_id")
    private String partyOrganizationId;
    /**时间*/
    @Column(name = "meeting_time")
    private String meetingTime;
    /**地点*/
    @Column(name = "meeting_place")
    private String meetingPlace;
    /** 表ID*/
    @Column(name = "table_id")
    private String tableId;
    /**表名*/
    @Column(name = "table_name")
    private String tableName;
    /** 操作 */
    @Transient
    private String cz = "";
    /** 上级党组织名称 */
    @Transient
    private String superName = "";

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }
    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCreUserId() {
        return this.creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    public String getCreUserName() {
        return this.creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    public String getCreDeptId() {
        return this.creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    public String getCreDeptName() {
        return this.creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    public String getCreChushiId() {
        return this.creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    public String getCreChushiName() {
        return this.creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    public String getCreJuId() {
        return this.creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
    }

    public String getCreJuName() {
        return this.creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
    }

    public String getVisible() {
        return this.visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getCreTime() {
        return this.creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    public String getCompere() {
        return compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public String getNoteTaker() {
        return noteTaker;
    }

    public void setNoteTaker(String noteTaker) {
        this.noteTaker = noteTaker;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(String actualNumber) {
        this.actualNumber = actualNumber;
    }

    public String getPrimaryCoverage() {
        return primaryCoverage;
    }

    public void setPrimaryCoverage(String primaryCoverage) {
        this.primaryCoverage = primaryCoverage;
    }

    public String getAttendants() {
        return attendants;
    }

    public void setAttendants(String attendants) {
        this.attendants = attendants;
    }

    public String getLeaveAndReasons() {
        return leaveAndReasons;
    }

    public void setLeaveAndReasons(String leaveAndReasons) {
        this.leaveAndReasons = leaveAndReasons;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getMeetingSituation() {
        return meetingSituation;
    }

    public void setMeetingSituation(String meetingSituation) {
        this.meetingSituation = meetingSituation;
    }

    public String getPartyOrganizationName() {
        return partyOrganizationName;
    }

    public void setPartyOrganizationName(String partyOrganizationName) {
        this.partyOrganizationName = partyOrganizationName;
    }

    public String getPartyOrganizationId() {
        return partyOrganizationId;
    }

    public void setPartyOrganizationId(String partyOrganizationId) {
        this.partyOrganizationId = partyOrganizationId;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DrhdEntity(){ super();}


    public DrhdEntity(String creUserId, String creUserName, String creDeptId, String creDeptName, String visible, String creTime, String compere, String noteTaker, String numberOfPeople, String actualNumber, String primaryCoverage, String attendants, String leaveAndReasons, String seats, String meetingSituation, String partyOrganizationName, String partyOrganizationId, String meetingTime, String meetingPlace,String tableId,String tableName) {
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creDeptId = creDeptId;
        this.creDeptName = creDeptName;
        this.visible = visible;
        this.creTime = creTime;
        this.compere = compere;
        this.noteTaker = noteTaker;
        this.numberOfPeople = numberOfPeople;
        this.actualNumber = actualNumber;
        this.primaryCoverage = primaryCoverage;
        this.attendants = attendants;
        this.leaveAndReasons = leaveAndReasons;
        this.seats = seats;
        this.meetingSituation = meetingSituation;
        this.partyOrganizationName = partyOrganizationName;
        this.partyOrganizationId = partyOrganizationId;
        this.meetingTime = meetingTime;
        this.meetingPlace = meetingPlace;
        this.tableId = tableId;
        this.tableName = tableName;
    }
}
