package com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * 迟到早退实体类
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月14日 下午8:46:32
 */
@Entity
@Table(name = "KQGL_COMELATE_LEAVEEARLY_INFO")
public class ComeLateLeaveEarlyInfo {
	
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
	
	/** 最后更新人ID */
	@Column(name = "update_user_id")
	private String updateUserId;
	
	/** 最后更新人名 */
	@Column(name = "update_user_name")
	private String updateUserName;
	
	/** 最后更新时间 */
	@Column(name = "update_time")
	private String updateTime;
	
	/** 申请人单位ID */
	@Column(name = "applicant_unit_id")
	private String applicantUnitId;
	
	/** 申请人单位名称 */
	@Column(name = "applicant_unit_name")
	private String applicantUnitName;
	
	/** 申请时间 */
	@Column(name = "application_time")
	private String applicationTime;
	
	/** 迟到早退姓名id */
	@Column(name = "cdzt_user_id")
	private String cdztUserId;
	
	/**迟到早退姓名 */
	@Column(name = "cdzt_user_name")
	private String cdztUserName;
	
	/** 状态 */
	@Column(name = "state")
	private String state;
	
	/**迟到早退日期*/
	@Column(name = "cdzt_date")
	private String cdztDate;
	
	/** 迟到早退原因 */
	@Column(name = "cdzt_reason")
	private String cdztReason;
	
	/**流程 */
	@Column(name = "subflag")
	private String subflag;
	
	/**标题 */
	@Column(name = "CDZT_TITLE")
	private String cdztTitle;
	
	/**原因类型（0：因私；1：因公） */
	@Column(name = "REASON_TYPE")
	private String reasonType;
	
	/**原因是否是下拉选（1：是） */
	@Column(name = "is_choose")
	private String isChoose;
	
	/**申请人处室ID*/
	@Column(name = "APPLICANT_CHUSHI_ID")
	private String applicantChushiId;
	
	/**申请人处室名称*/
	@Column(name = "APPLICANT_CHUSHI_NAME")
	private String applicantChushiName;
	
	/** 操作 */
	@Transient
	private String cz = "";
	
	public ComeLateLeaveEarlyInfo() {
		super();
	}

	public ComeLateLeaveEarlyInfo(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime,
			String updateUserId, String updateUserName, String updateTime, String applicantUnitId,
			String applicantUnitName, String applicationTime, String cdztUserId, String cdztUserName, String state,
			String cdztDate, String cdztReason, String subflag, String cdztTitle, String reasonType, String isChoose,
			String applicantChushiId, String applicantChushiName, String cz) {
		super();
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
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.applicantUnitId = applicantUnitId;
		this.applicantUnitName = applicantUnitName;
		this.applicationTime = applicationTime;
		this.cdztUserId = cdztUserId;
		this.cdztUserName = cdztUserName;
		this.state = state;
		this.cdztDate = cdztDate;
		this.cdztReason = cdztReason;
		this.subflag = subflag;
		this.cdztTitle = cdztTitle;
		this.reasonType = reasonType;
		this.isChoose = isChoose;
		this.applicantChushiId = applicantChushiId;
		this.applicantChushiName = applicantChushiName;
		this.cz = cz;
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

	public String getCdztUserId() {
		return cdztUserId;
	}

	public void setCdztUserId(String cdztUserId) {
		this.cdztUserId = cdztUserId;
	}

	public String getCdztUserName() {
		return cdztUserName;
	}

	public void setCdztUserName(String cdztUserName) {
		this.cdztUserName = cdztUserName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCdztDate() {
		return cdztDate;
	}

	public void setCdztDate(String cdztDate) {
		this.cdztDate = cdztDate;
	}

	public String getCdztReason() {
		return cdztReason;
	}

	public void setCdztReason(String cdztReason) {
		this.cdztReason = cdztReason;
	}
	
	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getCdztTitle() {
		return cdztTitle;
	}

	public void setCdztTitle(String cdztTitle) {
		this.cdztTitle = cdztTitle;
	}

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	public String getIsChoose() {
		return isChoose;
	}

	public void setIsChoose(String isChoose) {
		this.isChoose = isChoose;
	}

	public String getApplicantChushiId() {
		return applicantChushiId;
	}

	public void setApplicantChushiId(String applicantChushiId) {
		this.applicantChushiId = applicantChushiId;
	}

	public String getApplicantChushiName() {
		return applicantChushiName;
	}

	public void setApplicantChushiName(String applicantChushiName) {
		this.applicantChushiName = applicantChushiName;
	}
	
}
