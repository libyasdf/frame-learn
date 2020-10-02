package com.sinosoft.sinoep.modules.zhbg.kqgl.qj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * TODO 请假实体类
 * @author 冯超
 * @Date 2018年4月10日 下午1:51:21
 */

@Entity
@Table(name = "KQGL_LEAVE_INFO")
public class KqglLeaveInfo {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/** 创建人ID（也是申请人ID字段） */
	@Column(name = "cre_user_id")
	private String creUserId;
	
	/** 创建人名称（也是申请人名称字段） */
	@Column(name = "cre_user_name")
	private String creUserName;
	
	/** 创建部门ID */
	@Column(name = "cre_dept_id")
	private String creDeptId;
	
	/** 创建部门名 */
	@Column(name = "cre_dept_name")
	private String creDeptName;
	
	/** 创建人处室ID */
	@Column(name = "cre_chushi_id")
	private String creChushiId;
	
	/** 创建人处室名*/
	@Column(name = "cre_chushi_name")
	private String creChushiName;
	
	/** 创建人二级局ID */
	@Column(name = "cre_ju_id")
	private String creJuId;
	
	/** 创建人二级局名 */
	@Column(name = "cre_ju_name")
	private String creJuName;
	
	/** 逻辑删除 */
	@Column(name = "visible")
	private String visible;
	
	/** 创建时间 */
	@Column(name = "cre_time")
	private String creTime;
	
	/** 流程状态 */
	@Column(name = "subflag")
	private String subflag;
	
	/** 带班标题 */
	@Column(name = "title")
	private String title;
	
	/** 最后更新人ID */
	@Column(name = "update_user_id")
	private String updateUserId;
	
	/** 最后更新人名 */
	@Column(name = "update_user_name")
	private String updateUserName;
	
	/** 最后更新时间 */
	@Column(name = "update_time")
	private String updateTime;
	
	/** 标题 */
	@Column(name = "leave_title")
	private String leaveTitle;
	
	/** 申请人单位ID */
	@Column(name = "applicant_unit_id")
	private String applicantUnitId;
	
	/** 申请人单位名称 */
	@Column(name = "applicant_unit_name")
	private String applicantUnitName;
	
	/** 申请时间 */
	@Column(name = "application_time")
	private String applicationTime;
	
	/** 请假开始日期 */
	@Column(name = "leave_start_date")
	private String leaveStartDate;
	
	/** 请假开始上午 */
	@Column(name = "start_am_or_pm")
	private String startAmOrPm;
	
	/** 请假结束日期 */
	@Column(name = "leave_end_date")
	private String leaveEndDate;
	
	/** 请假结束上下午*/
	@Column(name = "end_am_or_pm")
	private String endAmOrPm;
	
	/** 请假类型包括：事假、病假、年休假、探亲假、婚假、丧假、哺乳假、产假、陪产假 */
	@Column(name = "leave_type")
	private String leaveType;
	
	/** 是否倒休 */
//	@Column(name = "is_leave_in_lieu")
//	private String isLeaveInLieu;
	
	/** 请假时长 */
	@Column(name = "leave_long_time")
	private String leaveLongTime;
	
	/** 请假事由*/
	@Column(name = "leave_reason")
	private String leaveReason;
	
	/** 请假事由 是否是下拉框选项)*/
	@Column(name = "is_choose")
	private String isChoose;
	
//	/** 请假开始时间 */
//	@Column(name = "leave_start_time")
//	private String leaveStartTime;
//	
//	/** 请假结束时间*/
//	@Column(name = "leave_end_time")
//	private String leaveEndTime;
//	
//	/** 请假时间类型   0：按日期请假  1：按时间请假 */
//	@Column(name = "leave_time_type")
//	private String leaveTimeType;
	
	/** 操作 */
	@Transient
	private String cz = "";
	
	@Transient
	private String yibanid;
	
	/** 请假开始时间：leaveStartDate+start_am_or_pm */
	@Transient
	private String startDate;
	
	/** 请假结束时间：leaveEndDate+end_am_or_pm */
	@Transient
	private String endDate;
	
	@Transient
	private String isLeaveInLieuName;
	
	@Transient
	private String leaveDate;
	
	@Transient
	private String talLeaveLongTimeH;
	
	@Transient
	private String talLeaveLongTimeD;
	
	public KqglLeaveInfo() {
		super();
	}
	
	/**
	 * 关联表查询
	 * @param yibanid
	 * @param id
	 * @param leaveTitle
	 * @param subflag
	 * @param creUserName
	 * @param leaveType
	 * @param isLeaveInLieu
	 * @param leaveLongTime
	 */
	public KqglLeaveInfo(String yibanid,String id,String leaveTitle,String subflag, String creUserName, String leaveType,
			String leaveLongTime, String leaveStartDate, String startAmOrPm, String leaveEndDate, String endAmOrPm){
		super();
		this.yibanid = yibanid;
		this.id = id;
		this.leaveTitle = leaveTitle;
		this.subflag = subflag;
		this.creUserName = creUserName;
		this.leaveType = leaveType;
		//this.isLeaveInLieu = isLeaveInLieu;
		this.leaveLongTime = leaveLongTime;
		this.leaveStartDate = leaveStartDate;
		this.startAmOrPm = startAmOrPm;
		this.leaveEndDate = leaveEndDate;
		this.endAmOrPm = endAmOrPm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCreChushiId() {
		return creChushiId;
	}

	public void setCreChushiId(String creChushiId) {
		this.creChushiId = creChushiId;
	}

	public String getCreChushiName() {
		return creChushiName;
	}

	public void setCreChushiName(String creChushiName) {
		this.creChushiName = creChushiName;
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

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getLeaveTitle() {
		return leaveTitle;
	}

	public void setLeaveTitle(String leaveTitle) {
		this.leaveTitle = leaveTitle;
	}

	public String getApplicantUnitId() {
		return applicantUnitId;
	}

	public void setApplicantUnitId(String applicantUnitId) {
		this.applicantUnitId = applicantUnitId;
	}

	public String getApplicantUnitName() {
		return applicantUnitName;
	}

	public void setApplicantUnitName(String applicantUnitName) {
		this.applicantUnitName = applicantUnitName;
	}

	public String getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(String leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public String getStartAmOrPm() {
		return startAmOrPm;
	}

	public void setStartAmOrPm(String startAmOrPm) {
		this.startAmOrPm = startAmOrPm;
	}

	public String getLeaveEndDate() {
		return leaveEndDate;
	}

	public void setLeaveEndDate(String leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}

	public String getEndAmOrPm() {
		return endAmOrPm;
	}

	public void setEndAmOrPm(String endAmOrPm) {
		this.endAmOrPm = endAmOrPm;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

//	public String getIsLeaveInLieu() {
//		return isLeaveInLieu;
//	}
//
//	public void setIsLeaveInLieu(String isLeaveInLieu) {
//		this.isLeaveInLieu = isLeaveInLieu;
//	}

	public String getLeaveLongTime() {
		return leaveLongTime;
	}

	public void setLeaveLongTime(String leaveLongTime) {
		this.leaveLongTime = leaveLongTime;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getYibanid() {
		return yibanid;
	}

	public void setYibanid(String yibanid) {
		this.yibanid = yibanid;
	}

	public String getIsLeaveInLieuName() {
		return isLeaveInLieuName;
	}

	public void setIsLeaveInLieuName(String isLeaveInLieuName) {
		this.isLeaveInLieuName = isLeaveInLieuName;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

//	public String getLeaveStartTime() {
//		return leaveStartTime;
//	}
//
//	public void setLeaveStartTime(String leaveStartTime) {
//		this.leaveStartTime = leaveStartTime;
//	}
//
//	public String getLeaveEndTime() {
//		return leaveEndTime;
//	}
//
//	public void setLeaveEndTime(String leaveEndTime) {
//		this.leaveEndTime = leaveEndTime;
//	}
//
//	public String getLeaveTimeType() {
//		return leaveTimeType;
//	}
//
//	public void setLeaveTimeType(String leaveTimeType) {
//		this.leaveTimeType = leaveTimeType;
//	}

	public String getTalLeaveLongTimeH() {
		return talLeaveLongTimeH;
	}

	public void setTalLeaveLongTimeH(String talLeaveLongTimeH) {
		this.talLeaveLongTimeH = talLeaveLongTimeH;
	}

	public String getTalLeaveLongTimeD() {
		return talLeaveLongTimeD;
	}

	public void setTalLeaveLongTimeD(String talLeaveLongTimeD) {
		this.talLeaveLongTimeD = talLeaveLongTimeD;
	}

	public String getIsChoose() {
		return isChoose;
	}

	public void setIsChoose(String isChoose) {
		this.isChoose = isChoose;
	}


	
	
	
	
}
