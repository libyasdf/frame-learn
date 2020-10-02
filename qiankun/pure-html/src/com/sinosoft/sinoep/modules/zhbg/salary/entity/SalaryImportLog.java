package com.sinosoft.sinoep.modules.zhbg.salary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "gzgl_log_info")
public class SalaryImportLog {
	/*主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	/*创建人id*/
	@Column(name = "cre_user_id")
	private String creUserId;
	/*创建人名*/
	@Column(name = "cre_user_name")
	private String creUserName;
	/*创建人部门id*/
	@Column(name = "cre_dept_id")
	private String creDeptId;
	/*创建人部门名*/
	@Column(name = "cre_dept_name")
	private String creDeptName;
	/*逻辑删除*/
	@Column(name = "visible")
	private String visible;
	/*创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	/*处室id*/
	@Column(name = "CRE_CHUSHI_ID")
	private String creChuShiId;
	/*处室名称*/
	@Column(name = "CRE_CHUSHI_NAME")
	private String creChuShiName;
	/*二级局id*/
	@Column(name = "CRE_JU_ID")
	private String creJuId;
	/*二级局名*/
	@Column(name = "CRE_JU_NAME")
	private String creJuName;
	
	/*成功录入的条数*/
	@Column(name = "SUCCESS_NUM")
	private String successNum;
	/*录入失败的条数*/
	@Column(name = "ERROR_NUM")
	private String errorNum;
	/*录入失败的原因*/
	@Column(name = "ERROR_REASON")
	private String errorReason;
	/*重复录入的数据条数*/
	@Column(name = "REPEAT_NUM")
	private String repeatNum;
	/*备注*/
	@Column(name = "REMARK")
	private String remark;
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
	public String getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(String successNum) {
		this.successNum = successNum;
	}
	public String getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(String errorNum) {
		this.errorNum = errorNum;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getRepeatNum() {
		return repeatNum;
	}
	public void setRepeatNum(String repeatNum) {
		this.repeatNum = repeatNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
