package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * 会务服务信息
 * TODO 
 * @author 冯超
 * @Date 2018年7月19日 上午11:39:16
 */
@Entity
@Table(name = "HYGL_MEETING_SERVICE")
public class MeetingServiceInfo {
	
	/** 主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/** 创建人id*/
	@Column(name = "cre_user_id")
	private String creUserId;
	
	/** 创建人名*/
	@Column(name = "cre_user_name")
	private String creUserName;
	
	/** 创建人部门id*/
	@Column(name = "cre_dept_id")
	private String creDeptId;
	
	/** 创建人部门名*/
	@Column(name = "cre_dept_name")
	private String creDeptName;
	
	/** 创建人处室ID*/
	@Column(name = "cre_chushi_id")
	private String creChushiId;
	
	/** 创建人处室名*/
	@Column(name = "cre_chushi_name")
	private String creChushiName;
	
	/**创建人二级局ID*/
	@Column(name = "cre_ju_id")
	private String creJuId;
	
	/** 创建人二级局名*/
	@Column(name = "cre_ju_name")
	private String creJuName;
	
	/** 逻辑删除*/
	@Column(name = "visible")
	private String visible;
	
	/** 创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	
	/** 最后更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	
	/** 最后更新人名*/
	@Column(name = "update_user_name")
	private String updateUserName;
	
	/** 最后更新时间*/
	@Column(name = "update_time")
	private String updateTime;
	
	/** 服务单位表id*/
	@Column(name = "service_dept_id")
	private String serviceDeptId;
	
	/** 服务项目*/
	@Column(name = "meeting_service")
	private String meetingService;
	
	/** 负责人id */
	@Column(name = "responsible_user_id")
	private String responsibleUserId;
	
	/** 负责人name */
	@Column(name = "responsible_user_name")
	private String responsibleUserName;
	
	/** 负责人电话 */
	@Column(name = "responsible_user_telephone")
	private String responsibleUserTelephone;
	
	/** 备注*/
	@Column(name = "REMARK")
	private String remark;
	
	/** 负责人父部门id*/
	@Column(name = "responsible_dept_id")
	private String responsibleDeptId;
	
	/** 操作 */
	@Transient
	private String cz = "";
	
	/** 页面展示用主键ID*/
	@Transient
	private String meetingServiceId;
	
	/** 负责人部门id*/
	@Transient
	private String responsibleUserDeptId;
	

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

	public String getServiceDeptId() {
		return serviceDeptId;
	}

	public void setServiceDeptId(String serviceDeptId) {
		this.serviceDeptId = serviceDeptId;
	}

	public String getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(String meetingService) {
		this.meetingService = meetingService;
	}

	public String getResponsibleUserId() {
		return responsibleUserId;
	}

	public void setResponsibleUserId(String responsibleUserId) {
		this.responsibleUserId = responsibleUserId;
	}

	public String getResponsibleUserName() {
		return responsibleUserName;
	}

	public void setResponsibleUserName(String responsibleUserName) {
		this.responsibleUserName = responsibleUserName;
	}

	public String getResponsibleUserTelephone() {
		return responsibleUserTelephone;
	}

	public void setResponsibleUserTelephone(String responsibleUserTelephone) {
		this.responsibleUserTelephone = responsibleUserTelephone;
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

	public String getMeetingServiceId() {
		return meetingServiceId;
	}

	public void setMeetingServiceId(String meetingServiceId) {
		this.meetingServiceId = meetingServiceId;
	}

	public String getResponsibleUserDeptId() {
		return responsibleUserDeptId;
	}

	public void setResponsibleUserDeptId(String responsibleUserDeptId) {
		this.responsibleUserDeptId = responsibleUserDeptId;
	}

	public String getResponsibleDeptId() {
		return responsibleDeptId;
	}

	public void setResponsibleDeptId(String responsibleDeptId) {
		this.responsibleDeptId = responsibleDeptId;
	}

	
}
