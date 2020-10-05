package com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * TODO 出差实体类
 * 
 * @author 张建明
 * @Date 2018年4月10日 下午2:13:14
 */

@Entity
@Table(name = "KQGL_BUSINESS_TRIP_INFO")
public class BusinessTrip {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "FILETYPE")
	private String workFlowId;

	@Column(name = "SUBFLAG")
	private String subflag;

	/*@Column(name = "BUSINESS_TRIP_TITLE")
	private String businessTripTitle;*/

	@Column(name = "APPLICATION_UNIT_ID")
	private String applicationUnitId;

	@Column(name = "APPLICATION_UNIT_NAME")
	private String applicationUnitName;

	@Column(name = "APPLICATION_TIME")
	private String applicationTime;

	@Column(name = "START_TIME")
	private String startTime;

	@Column(name = "END_TIME")
	private String endTime;
	
	@Column(name = "DESTINATION")
	private String destination;

	@Column(name = "VEHICLE")
	private String vehicle;

	@Column(name = "IS_HAVE_RECEPTION_FEES")
	private String isHaveReceptionFees;

	@Column(name = "RECEPTION_FEES")
	private String receptionFees;

	@Column(name = "LONG_TIME")
	private String longTime;

	@Column(name = "BUSINESS_TRIP_REASON")
	private String reason;

	@Column(name = "CRE_TIME")
	private String creTime;

	@Column(name = "CRE_USER_ID")
	private String creUserId;

	@Column(name = "CRE_USER_NAME")
	private String creUserName;

	@Column(name = "CRE_DEPT_ID")
	private String creDeptId;

	@Column(name = "CRE_DEPT_NAME")
	private String creDeptName;

	@Column(name = "CRE_CHUSHI_ID")
	private String creChuShiId;

	@Column(name = "CRE_CHUSHI_NAME")
	private String creChuShiName;

	@Column(name = "CRE_JU_ID")
	private String creJuId;

	@Column(name = "CRE_JU_NAME")
	private String creJuName;

	@Column(name = "UPDATE_USER_ID")
	private String updateUserId;

	@Column(name = "UPDATE_USER_NAME")
	private String updateUserName;

	@Column(name = "UPDATE_TIME")
	private String updateTime;

	@Column(name = "VISIBLE")
	private String visible;

	@Column(name = "IDEA")
	private String idea;
	
	@Column(name = "BUSI_TRIP_TYPE")
	private String busiTripType;
	
	//同行人员
	@Column(name = "trip_colleague")
	private String tripColleague;
	
	@Column(name = "trip_colleague_ids")
	private String tripColleagueIds;

	@Transient
	private String workitemid;

	@Transient
	private String cz = "";

	@Transient
	private String yibanid;

	public BusinessTrip() {
		super();
	}
   /**
    * 为hql连接创造构造函数
    * @param id
    * @param title
    * @param workFlowId
    * @param subflag
    * @param applicationUnitId
    * @param applicationUnitName
    * @param applicationTime
    * @param startTime
    * @param endTime
    * @param destination
    * @param vehicle
    * @param isHaveReceptionFees
    * @param receptionFees
    * @param longTime
    * @param reason
    * @param creTime
    * @param creUserId
    * @param creUserName
    * @param creDeptId
    * @param creDeptName
    * @param creChuShiId
    * @param creChuShiName
    * @param creJuId
    * @param creJuName
    * @param updateUserId
    * @param updateUserName
    * @param updateTime
    * @param visible
    * @param idea
    * @param busiTripType
    * @param tripColleague
    * @param tripColleagueIds
    * @param workitemid
    * @param cz
    * @param yibanid
    */

	public BusinessTrip(String id, String title, String workFlowId, String subflag, String applicationUnitId,
			String applicationUnitName, String applicationTime, String startTime, String endTime, String destination,
			String vehicle, String isHaveReceptionFees, String receptionFees, String longTime, String reason,
			String creTime, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String creChuShiId, String creChuShiName, String creJuId, String creJuName, String updateUserId,
			String updateUserName, String updateTime, String visible, String idea, String busiTripType,
			String tripColleague, String tripColleagueIds, String workitemid, String cz, String yibanid) {
		super();
		this.id = id;
		this.title = title;
		this.workFlowId = workFlowId;
		this.subflag = subflag;
		this.applicationUnitId = applicationUnitId;
		this.applicationUnitName = applicationUnitName;
		this.applicationTime = applicationTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.destination = destination;
		this.vehicle = vehicle;
		this.isHaveReceptionFees = isHaveReceptionFees;
		this.receptionFees = receptionFees;
		this.longTime = longTime;
		this.reason = reason;
		this.creTime = creTime;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.creChuShiId = creChuShiId;
		this.creChuShiName = creChuShiName;
		this.creJuId = creJuId;
		this.creJuName = creJuName;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.visible = visible;
		this.idea = idea;
		this.busiTripType = busiTripType;
		this.tripColleague = tripColleague;
		this.tripColleagueIds = tripColleagueIds;
		this.workitemid = workitemid;
		this.cz = cz;
		this.yibanid = yibanid;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

//	public String getBusinessTripTitle() {
//		return businessTripTitle;
//	}
//
//	public void setBusinessTripTitle(String businessTripTitle) {
//		this.businessTripTitle = businessTripTitle;
//	}

	public String getApplicationUnitId() {
		return applicationUnitId;
	}

	public String getTripColleagueIds() {
		return tripColleagueIds;
	}
	
	public void setTripColleagueIds(String tripColleagueIds) {
		this.tripColleagueIds = tripColleagueIds;
	}
	
	public void setApplicationUnitId(String applicationUnitId) {
		this.applicationUnitId = applicationUnitId;
	}

	public String getApplicationUnitName() {
		return applicationUnitName;
	}

	public void setApplicationUnitName(String applicationUnitName) {
		this.applicationUnitName = applicationUnitName;
	}

	public String getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getIsHaveReceptionFees() {
		return isHaveReceptionFees;
	}

	public void setIsHaveReceptionFees(String isHaveReceptionFees) {
		this.isHaveReceptionFees = isHaveReceptionFees;
	}

	public String getReceptionFees() {
		return receptionFees;
	}

	public void setReceptionFees(String receptionFees) {
		this.receptionFees = receptionFees;
	}

	public String getLongTime() {
		return longTime;
	}

	public void setLongTime(String longTime) {
		this.longTime = longTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreUserName() {
		return creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	public String getCreDeptId() {
		return creDeptId;
	}

	public void setCreDeptId(String creDeptId) {
		this.creDeptId = creDeptId;
	}

	public String getCreDeptName() {
		return creDeptName;
	}

	public void setCreDeptName(String creDeptName) {
		this.creDeptName = creDeptName;
	}

	public String getCreChuShiId() {
		return creChuShiId;
	}

	public void setCreChuShiId(String creChuShiId) {
		this.creChuShiId = creChuShiId;
	}

	public String getCreChuShiName() {
		return creChuShiName;
	}

	public void setCreChuShiName(String creChuShiName) {
		this.creChuShiName = creChuShiName;
	}

	public String getCreJuId() {
		return creJuId;
	}

	public void setCreJuId(String creJuId) {
		this.creJuId = creJuId;
	}

	public String getCreJuName() {
		return creJuName;
	}

	public void setCreJuName(String creJuName) {
		this.creJuName = creJuName;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getBusiTripType() {
		return busiTripType;
	}

	public void setBusiTripType(String busiTripType) {
		this.busiTripType = busiTripType;
	}

	public String getWorkitemid() {
		return workitemid;
	}

	public void setWorkitemid(String workitemid) {
		this.workitemid = workitemid;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getYibanid() {
		return yibanid;
	}

	public void setYibanid(String yibanid) {
		this.yibanid = yibanid;
	}

	public String getTripColleague() {
		return tripColleague;
	}

	public void setTripColleague(String tripColleague) {
		this.tripColleague = tripColleague;
	}
	
}
