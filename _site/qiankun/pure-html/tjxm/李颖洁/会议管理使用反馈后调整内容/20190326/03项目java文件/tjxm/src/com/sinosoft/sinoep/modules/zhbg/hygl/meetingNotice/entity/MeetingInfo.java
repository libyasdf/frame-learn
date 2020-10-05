package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.entity;

/**
 * TODO 会议信息，保存服务反馈和通知反馈
 * @author 李颖洁  
 * @date 2019年3月20日  下午8:45:58
 */
public class MeetingInfo {
	
	/** 会议id*/
	private String id;
	
	/** 会议主办单位名称*/
	private String hostDeptName;
	
	/** 会议主办单位id*/
	private String hostDeptId;
	
	/** 会议申请id*/
	private String applyTitle;
	
	/** 会议类型*/
	private String meetingType;
	
	/** 会议申请的会议室名称，以逗号隔开*/
	private String meetingRoomName;
	
	/** 会议申请的会议室id，以逗号隔开*/
	private String meetingRoomId;
	
	/** 会议起止日期时间：yyyy-MM-dd HH:mm:ss - yyyy-MM-dd HH:mm:ss*/
	private String meetingTime;
	
	/** 会议申请的会务服务名称*/
	private String meetingServiceName;
	
	/** 会议申请的会务服务反馈的数量*/
	private String remark;
	
	/** 会议申请的会务服务的总数量*/
	private String status;
	
	/** 会议通知id*/
	private String noticeId;
	
	/** 会议通知已反馈的数量*/
	private String attendFeedbackNum;
	
	/** 会议通知需反馈的总数量*/
	private String attendNum;
	
	/** 会议通知是否需要反馈：0：不反馈*/
	private String notFeedback;
	
	/** 查询结果的总数*/
	private String total;
	
	/** 会议申请人id*/
	private String creUserId;
	
	/** 会议通知的反馈类型*/
	private String fankuiType;
	
	public MeetingInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MeetingInfo(String id, String hostDeptName, String hostDeptId, String applyTitle, String meetingType,
			String meetingRoomName, String meetingRoomId, String meetingTime, String meetingServiceName, String remark,
			String status, String noticeId, String attendFeedbackNum, String attendNum, String notFeedback,
			String total, String creUserId,String fankuiType) {
		super();
		this.id = id;
		this.hostDeptName = hostDeptName;
		this.hostDeptId = hostDeptId;
		this.applyTitle = applyTitle;
		this.meetingType = meetingType;
		this.meetingRoomName = meetingRoomName;
		this.meetingRoomId = meetingRoomId;
		this.meetingTime = meetingTime;
		this.meetingServiceName = meetingServiceName;
		this.remark = remark;
		this.status = status;
		this.noticeId = noticeId;
		this.attendFeedbackNum = attendFeedbackNum;
		this.attendNum = attendNum;
		this.notFeedback = notFeedback;
		this.total = total;
		this.creUserId = creUserId;
		this.fankuiType = fankuiType;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getHostDeptName() {
		return hostDeptName;
	}
	
	public void setHostDeptName(String hostDeptName) {
		this.hostDeptName = hostDeptName;
	}
	
	public String getHostDeptId() {
		return hostDeptId;
	}
	
	public void setHostDeptId(String hostDeptId) {
		this.hostDeptId = hostDeptId;
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
	
	public String getMeetingRoomName() {
		return meetingRoomName;
	}
	
	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
	}
	
	public String getMeetingRoomId() {
		return meetingRoomId;
	}
	
	public void setMeetingRoomId(String meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}
	
	public String getMeetingTime() {
		return meetingTime;
	}
	
	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}
	
	public String getMeetingServiceName() {
		return meetingServiceName;
	}
	
	public void setMeetingServiceName(String meetingServiceName) {
		this.meetingServiceName = meetingServiceName;
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
	
	public String getNoticeId() {
		return noticeId;
	}
	
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	
	public String getAttendFeedbackNum() {
		return attendFeedbackNum;
	}
	
	public void setAttendFeedbackNum(String attendFeedbackNum) {
		this.attendFeedbackNum = attendFeedbackNum;
	}
	
	public String getAttendNum() {
		return attendNum;
	}
	
	public void setAttendNum(String attendNum) {
		this.attendNum = attendNum;
	}
	
	public String getNotFeedback() {
		return notFeedback;
	}
	
	public void setNotFeedback(String notFeedback) {
		this.notFeedback = notFeedback;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public String getFankuiType() {
		return fankuiType;
	}

	public void setFankuiType(String fankuiType) {
		this.fankuiType = fankuiType;
	}
	
	
	
}
