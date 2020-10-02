package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 考勤日志
 * TODO 
 * @author 冯超
 * @Date 2018年5月30日 上午9:30:29
 */
@Entity
@Table(name = "kqgl_log_info")
public class KqLog {
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
	/*处室id*/
	@Column(name = "cre_chushi_id")
	private String creChuShiId;
	/*处室名称*/
	@Column(name = "cre_chushi_name")
	private String creChuShiName;
	/*二级局id*/
	@Column(name = "cre_ju_id")
	private String creJuId;
	/*二级局名*/
	@Column(name = "cre_ju_name")
	private String creJuName;
	/*逻辑删除*/
	@Column(name = "visible")
	private String visible;
	/*创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	/*成功录入的条数*/
	@Column(name = "success_num")
	private String successNum;
	/*录入失败的条数*/
	@Column(name = "error_num")
	private String errorNum;
	/*录入失败的原因*/
	@Column(name = "error_reason")
	private String errorReason;
	/*重复录入的数据条数*/
	@Column(name = "repeat_num")
	private String repeatNum;
	/*备注*/
	@Column(name = "remark")
	private String remark;
	
	/*操作*/
	@Transient
	private String cz = "";

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

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}
	
	
}
