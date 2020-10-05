
package com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 会务服务通知表
 * TODO 
 * @author 张建明
 * @Date 2018年8月29日 上午11:22:18
 */

@Entity
@Table(name = "HYGL_MEETING_SERVICE_TASK")
public class MeetingServiceNotice {

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
	
	/** 会务服务ID*/
	@Column(name = "MEETING_SERVICE_ID")
	private String meetingServiceId;
	
	/** 会务服务NAME*/
	@Column(name = "MEETING_SERVICE_NAME")
	private String meetingServiceName;
	
	/** 联系人id*/
	@Column(name = "CONTACT_ID")
	private String contactId;
	
	/** 会议申请ID*/
	@Column(name = "MEETINGROOM_APPLY_ID")
	private String meetingroomApplyId;
	
	/**能否按时提供服务 0:可以 1：其他*/
	@Column(name = "IS_OR_NOT")
	private String isOrNot;
	
	/** 备注*/
	@Column(name = "remark")
	private String remark;
	
	/** 状态*/
	@Column(name = "STATUS")
	private String status;
	
	@Transient
	private String applyTitle = "";
	@Transient
	private String meetingroomName = "";
	@Transient
	private String meetingPlace = "";
	@Transient
	private String meetingStartDate = "";
	@Transient
	private String meetingStartTime = "";
	@Transient
	private String meetingEndDate = "";
	@Transient
	private String meetingEndTime = "";
	@Transient
	private String hostDeptName = "";
	
	@Transient
	private String responsibleUserName = "";
	@Transient
	private String responsibleUserTelephone = "";
	@Transient
	private String serviceDeptId = "";
	@Transient
	private String cz = "";
	
	@Transient
	private String yibanid;
	
	@Transient
	private String meetingTime;
	
	public MeetingServiceNotice() {
		super();
	}
	
	public MeetingServiceNotice(String id, String meetingroomApplyId,String meetingServiceName,
			String isOrNot, String remark, String responsibleUserName, 
			String responsibleUserTelephone, String serviceDeptId) {
		super();
		this.id = id;
		this.meetingroomApplyId = meetingroomApplyId;
		this.meetingServiceName = meetingServiceName;
		this.isOrNot = isOrNot;
		this.remark = remark;
		this.responsibleUserName = responsibleUserName;
		this.responsibleUserTelephone = responsibleUserTelephone;
		this.serviceDeptId = serviceDeptId;
		
	}

	public MeetingServiceNotice(String meetingroomApplyId,String hostDeptName,String applyTitle,String meetingroomName,
			String meetingPlace,String meetingTime,String meetingServiceName,String remark,String status) {
		super();
		this.meetingroomApplyId = meetingroomApplyId;
		this.hostDeptName = hostDeptName;
		this.applyTitle = applyTitle;
		this.meetingroomName = meetingroomName;
		this.meetingPlace = meetingPlace;
		this.meetingTime = meetingTime;
		this.meetingServiceName = meetingServiceName;
		this.remark = remark;
		this.status = status;
	}

	public MeetingServiceNotice(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime,
			String updateUserId, String updateUserName, String updateTime, String meetingServiceId,
			String meetingServiceName, String contactId, String meetingroomApplyId, String isOrNot, String remark,
			String status, String cz, String yibanid) {
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
		this.meetingServiceId = meetingServiceId;
		this.meetingServiceName = meetingServiceName;
		this.contactId = contactId;
		this.meetingroomApplyId = meetingroomApplyId;
		this.isOrNot = isOrNot;
		this.remark = remark;
		this.status = status;
		this.cz = cz;
		this.yibanid = yibanid;
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

	public String getMeetingServiceId() {
		return meetingServiceId;
	}

	public void setMeetingServiceId(String meetingServiceId) {
		this.meetingServiceId = meetingServiceId;
	}

	public String getMeetingServiceName() {
		return meetingServiceName;
	}

	public void setMeetingServiceName(String meetingServiceName) {
		this.meetingServiceName = meetingServiceName;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getMeetingroomApplyId() {
		return meetingroomApplyId;
	}

	public void setMeetingroomApplyId(String meetingroomApplyId) {
		this.meetingroomApplyId = meetingroomApplyId;
	}

	public String getIsOrNot() {
		return isOrNot;
	}

	public void setIsOrNot(String isOrNot) {
		this.isOrNot = isOrNot;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	public String getApplyTitle() {
		return applyTitle;
	}
	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
	}
	public String getMeetingroomName() {
		return meetingroomName;
	}
	public void setMeetingroomName(String meetingroomName) {
		this.meetingroomName = meetingroomName;
	}
	public String getMeetingPlace() {
		return meetingPlace;
	}
	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}
	public String getMeetingStartDate() {
		return meetingStartDate;
	}
	public void setMeetingStartDate(String meetingStartDate) {
		this.meetingStartDate = meetingStartDate;
	}
	public String getMeetingStartTime() {
		return meetingStartTime;
	}
	public void setMeetingStartTime(String meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}
	public String getMeetingEndDate() {
		return meetingEndDate;
	}
	public void setMeetingEndDate(String meetingEndDate) {
		this.meetingEndDate = meetingEndDate;
	}
	public String getMeetingEndTime() {
		return meetingEndTime;
	}
	public void setMeetingEndTime(String meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}
	public String getHostDeptName() {
		return hostDeptName;
	}
	public void setHostDeptName(String hostDeptName) {
		this.hostDeptName = hostDeptName;
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
	public String getServiceDeptId() {
		return serviceDeptId;
	}
	public void setServiceDeptId(String serviceDeptId) {
		this.serviceDeptId = serviceDeptId;
	}

	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}
	
	

}
