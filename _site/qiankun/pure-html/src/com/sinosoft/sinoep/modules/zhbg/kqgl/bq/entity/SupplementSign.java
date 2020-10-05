package com.sinosoft.sinoep.modules.zhbg.kqgl.bq.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 补签表实体类
 * @author 李颖洁  
 * @date 2019年4月25日  下午9:12:41
 */
@Entity
@Table(name = "KQGL_SUPPLEMENT_SIGN_INFO")
public class SupplementSign {
	
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
	@Column(name = "supplement_sign_title")
	private String supplementSignTitle;
	
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
	
	/** 补签类型 0：补签入 1：补签出 2：补全天*/
	@Column(name = "supplement_sign_type")
	private String supplementSignType;
	
	/** 补签日期 */
	@Column(name = "supplement_sign_date")
	private String supplementSignDate;
	
	/** 补签开始时间 */
	@Column(name = "supplement_sign_start_time")
	private String supplementSignStartTime;
	
	/** 补签结束时间*/
	@Column(name = "supplement_sign_end_time")
	private String supplementSignEndTime;
	
	/** 补签事由 */
	@Column(name = "supplement_sign_reason")
	private String supplementSignReason;
	
	/** 操作 */
	@Transient
	private String cz = "";
	
	@Transient
	private String yibanid;
	
	@Transient
	private String supplementSignTypeName;
	
	@Transient
	private String supplementSignTime;
	
	public SupplementSign() {
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
	public SupplementSign(String yibanid,String id, String leaveTitle, String subflag, String creUserName,String applicantUnitName, String supplementSignType, String supplementSignStartTime, String supplementSignEndTime){
		super();
		this.yibanid = yibanid;
		this.id = id;
		this.leaveTitle = leaveTitle;
		this.subflag = subflag;
		this.creUserName = creUserName;
		this.applicantUnitName = applicantUnitName;
		this.supplementSignType = supplementSignType;
		this.supplementSignStartTime = supplementSignStartTime;
		this.supplementSignEndTime = supplementSignEndTime;
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

	public String getSupplementSignTitle() {
		return supplementSignTitle;
	}

	public void setSupplementSignTitle(String supplementSignTitle) {
		this.supplementSignTitle = supplementSignTitle;
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

	public String getSupplementSignType() {
		return supplementSignType;
	}

	public void setSupplementSignType(String supplementSignType) {
		this.supplementSignType = supplementSignType;
	}

	public String getSupplementSignDate() {
		return supplementSignDate;
	}

	public void setSupplementSignDate(String supplementSignDate) {
		this.supplementSignDate = supplementSignDate;
	}

	public String getSupplementSignStartTime() {
		return supplementSignStartTime;
	}

	public void setSupplementSignStartTime(String supplementSignStartTime) {
		this.supplementSignStartTime = supplementSignStartTime;
	}

	public String getSupplementSignEndTime() {
		return supplementSignEndTime;
	}

	public void setSupplementSignEndTime(String supplementSignEndTime) {
		this.supplementSignEndTime = supplementSignEndTime;
	}

	public String getSupplementSignReason() {
		return supplementSignReason;
	}

	public void setSupplementSignReason(String supplementSignReason) {
		this.supplementSignReason = supplementSignReason;
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

	public String getSupplementSignTypeName() {
		return supplementSignTypeName;
	}

	public void setSupplementSignTypeName(String supplementSignTypeName) {
		this.supplementSignTypeName = supplementSignTypeName;
	}

	public String getSupplementSignTime() {
		return supplementSignTime;
	}

	public void setSupplementSignTime(String supplementSignTime) {
		this.supplementSignTime = supplementSignTime;
	}
	
	

}
