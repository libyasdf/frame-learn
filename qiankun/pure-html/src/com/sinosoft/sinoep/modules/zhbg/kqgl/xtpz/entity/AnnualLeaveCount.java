package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * 员工年假的实体类
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月20日 下午3:53:41
 */
@Entity
@Table(name = "annualLeaveCount")
public class AnnualLeaveCount {
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
	/*创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	/*最后更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	/*最后更新人名*/
	@Column(name = "update_user_name")
	private String updateUserName;
	/*最后更新时间*/
	@Column(name = "update_time")
	private String updateTime;
	/*年限*/
	@Column(name = "work_time")
	private String workTime;
	/*年假天数*/
	@Column(name = "annual_ leave_days")
	private String annualLeaveDays;
	/*操作*/
	@Transient
	private String cz = "";
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
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getAnnualLeaveDays() {
		return annualLeaveDays;
	}
	public void setAnnualLeaveDays(String annualLeaveDays) {
		this.annualLeaveDays = annualLeaveDays;
	}
	

}
