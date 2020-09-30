package com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * 旷工管理实体类
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月14日 上午11:23:38
 */
@Entity
@Table(name = "KQGL_ABSENTEEISM_INFO")
public class AbsenteeismInfo {
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
	
	/** 录入时间 */
	@Column(name = "application_time")
	private String applicationTime;
	
	/** 旷工人id */
	@Column(name = "absenteeism_user_id")
	private String absenteeismUserId;
	
	/** 旷工人姓名 */
	@Column(name = "absenteeism_user_name")
	private String absenteeismUserName;
	
	/** 旷工人单位id */
	@Column(name = "abs_applicant_unit_id")
	private String absApplicantUnitId;
	
	/** 旷工人单位姓名 */
	@Column(name = "abs_applicant_unit_name")
	private String absApplicantUnitName;
	
	/**旷工日期日期 */
	@Column(name = "absenteeism_date")
	private String absenteeismDate;
	
	/**旷工原因*/
	@Column(name = "absenteeism_reason")
	private String absenteeismReason;
	
	@Transient
	private String cz="";

	public AbsenteeismInfo() {
		super();
	}

	public AbsenteeismInfo(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime,
			String updateUserId, String updateUserName, String updateTime, String applicantUnitId,
			String applicantUnitName, String applicationTime, String absenteeismUserId, String absenteeismUserName,
			String absenteeismDate, String absenteeismReason) {
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
		this.absenteeismUserId = absenteeismUserId;
		this.absenteeismUserName = absenteeismUserName;
		this.absenteeismDate = absenteeismDate;
		this.absenteeismReason = absenteeismReason;
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

	public String getAbsenteeismUserId() {
		return absenteeismUserId;
	}

	public void setAbsenteeismUserId(String absenteeismUserId) {
		this.absenteeismUserId = absenteeismUserId;
	}

	public String getAbsenteeismUserName() {
		return absenteeismUserName;
	}

	public void setAbsenteeismUserName(String absenteeismUserName) {
		this.absenteeismUserName = absenteeismUserName;
	}

	public String getAbsenteeismDate() {
		return absenteeismDate;
	}

	public void setAbsenteeismDate(String absenteeismDate) {
		this.absenteeismDate = absenteeismDate;
	}

	public String getAbsenteeismReason() {
		return absenteeismReason;
	}

	public void setAbsenteeismReason(String absenteeismReason) {
		this.absenteeismReason = absenteeismReason;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getAbsApplicantUnitId() {
		return absApplicantUnitId;
	}

	public void setAbsApplicantUnitId(String absApplicantUnitId) {
		this.absApplicantUnitId = absApplicantUnitId;
	}

	public String getAbsApplicantUnitName() {
		return absApplicantUnitName;
	}

	public void setAbsApplicantUnitName(String absApplicantUnitName) {
		this.absApplicantUnitName = absApplicantUnitName;
	}

}
