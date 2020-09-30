package com.sinosoft.sinoep.modules.mypage.workplan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 工作计划实体类
 * TODO 
 * @author 张建明
 * @Date 2018年10月10日 下午5:51:45
 */
@Entity
@Table(name = "SYS_WORK_PLAN")
public class WorkPlan {
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
	/*日期*/
	@Column(name = "date_plan")
	private String datePlan;
	/*计划是否转日志（0表示未进行转化；1表示已进行转化）*/
	@Column(name = "is_change")
	private String isChange;
	/*类型*/
	@Column(name = "plan_type")
	private String planType;
	/*内容*/
	@Column(name = "content")
	private String content;
	/*状态0表示未完成，1表示已完成*/
	@Column(name = "is_finish")
	private String isFinish;
	/*提醒日期*/
	@Column(name = "remind_time")
	private String remindTime;
	
	/*是否已提醒0：未提醒，1：已提醒*/
	@Column(name = "is_remind")
	private String isRemind;
	
	/*更新时间*/
	@Column(name = "update_time")
	private String updateTime;
	/*更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	/*更新人姓名*/
	@Column(name = "update_user_name")
	private String updateUserName;

	/*操作*/
	@Transient
	private String cz = "";
	@Transient
	private String logTypeLable;
	
	public String getLogTypeLable() {
		if("0".equals(planType)){
			return "日常";
		}else if("1".equals(planType)){
			return "业务";
		}else if("2".equals(planType)){
			return "党务";
		}else{
			return "其他";
		}
	}
	public void setLogTypeLable(String logTypeLable) {
		this.logTypeLable = logTypeLable;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
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
	
	public String getDatePlan() {
		return datePlan;
	}
	public void setDatePlan(String datePlan) {
		this.datePlan = datePlan;
	}
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	

	public String getIsChange() {
		return isChange;
	}
	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
	public String getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
	public String getIsRemind() {
		return isRemind;
	}
	public void setIsRemind(String isRemind) {
		this.isRemind = isRemind;
	}
	
	
	

}
