
package com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 会议室申请列表
 * TODO 
 * @author 冯超
 * @Date 2018年8月21日 上午10:42:30
 */

@Entity
@Table(name = "hygl_meeting_apply")
public class MeetingApplyInfo {

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
	
	/** 待办标题*/
	@Column(name = "title")
	private String title;
	
	/** 流程状态*/
	@Column(name = "subflag")
	private String subflag;
	
	/** 申请会议标题*/
	@Column(name = "apply_title")
	private String applyTitle;
	
	/** 1：一类会议 2：二类会议 3：三类会议*/
	@Column(name = "meeting_type")
	private String meetingType;
	
	/** 主办单位ID*/
	@Column(name = "host_dept_id")
	private String hostDeptId;
	
	/** 主办单位NAME*/
	@Column(name = "host_dept_name")
	private String hostDeptName;
	
	/** 召集人*/
	@Column(name = "convenor")
	private String convenor;
	

	
	/** 会议室地点*/
	@Column(name = "meeting_place")
	private String meetingPlace;
	
	/** 会议起止时间*/
	@Column(name = "meeting_time")
	private String meetingTime;
		
	/** 会务服务*/
	@Column(name = "meeting_services")
	private String meetingServices;
	
	/** 会务服务id*/
	@Column(name = "meeting_services_id")
	private String meetingServicesId;
	
	/** 0:为起草通知 1已起草会议通知*/
	@Column(name = "notice_flag")
	private String noticeFlag;
	
	
	/** 将开始日期和上下午合并2018-08-23 08:00*/
	@Column(name = "meeting_start")
	private String meetingStart;
	
	/** 将结束日期和上下午合并*/
	@Column(name = "meeting_end")
	private String meetingEnd;
	
	/** 备注*/
	@Column(name = "remark")
	private String remark;
	
	/*@Transient
	private String workitemid;*/
	
	@Transient
	private String cz = "";
	
	@Transient
	private String yibanid;
	
	@Transient
	private String meetingRoomName; //会议室
	
	@Transient
	private String meetingRoomInfo;//所设置的所有会议室信息
	
	public String getMeetingRoomInfo() {
		return meetingRoomInfo;
	}

	public void setMeetingRoomInfo(String meetingRoomInfo) {
		this.meetingRoomInfo = meetingRoomInfo;
	}

	public MeetingApplyInfo() {
		super();
	}
	
	public MeetingApplyInfo(String yibanid,String id, String applyTitle, String subflag ) {
		super();
		this.yibanid = yibanid;
		this.id = id;
		this.applyTitle = applyTitle;
		this.subflag = subflag;
	}

	public String getMeetingRoomName() {
		return meetingRoomName;
	}

	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}

	public String getApplyTitle() {
		return applyTitle;
	}

	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public String getHostDeptId() {
		return hostDeptId;
	}

	public void setHostDeptId(String hostDeptId) {
		this.hostDeptId = hostDeptId;
	}

	public String getHostDeptName() {
		return hostDeptName;
	}

	public void setHostDeptName(String hostDeptName) {
		this.hostDeptName = hostDeptName;
	}

	public String getConvenor() {
		return convenor;
	}

	public void setConvenor(String convenor) {
		this.convenor = convenor;
	}

	
	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	

	

	

	public String getMeetingServices() {
		return meetingServices;
	}

	public void setMeetingServices(String meetingServices) {
		this.meetingServices = meetingServices;
	}
	
	public String getNoticeFlag() {
		return noticeFlag;
	}

	public void setNoticeFlag(String noticeFlag) {
		this.noticeFlag = noticeFlag;
	}

	public String getMeetingStart() {
		return meetingStart;
	}

	public void setMeetingStart(String meetingStart) {
		this.meetingStart = meetingStart;
	}

	public String getMeetingEnd() {
		return meetingEnd;
	}

	public void setMeetingEnd(String meetingEnd) {
		this.meetingEnd = meetingEnd;
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

	public String getYibanid() {
		return yibanid;
	}

	public void setYibanid(String yibanid) {
		this.yibanid = yibanid;
	}

	public String getMeetingServicesId() {
		return meetingServicesId;
	}

	public void setMeetingServicesId(String meetingServicesId) {
		this.meetingServicesId = meetingServicesId;
	}

	public String getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}
	
	

}
