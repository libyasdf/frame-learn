package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 会议通知反馈实体类
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月12日 上午11:37:22
 */
@Entity
@Table(name = "hygl_notice_back")
public class MeetingNoticeBack {
	/*主键*/
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
	
	/** 逻辑删除*/
	@Column(name = "visible")
	private String visible;
	
	/** treeId*/
	@Transient
	private String treeId;
	
	
	/** 处室id */
	@Column(name = "CRE_CHUSHI_ID")
	private String creChuShiId;
	
	/** 处室名称 */
	@Column(name = "CRE_CHUSHI_NAME")
	private String creChuShiName;
	
	/** 二级局id */
	@Column(name = "CRE_JU_ID")
	private String creJuId;
	
	/** 二级局名 */
	@Column(name = "CRE_JU_NAME")
	private String creJuName;
	
	/** 创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	
	/** 更新时间*/
	@Column(name = "update_time")
	private String updateTime;
	
	/** 更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	
	/** 更新人姓名*/
	@Column(name = "update_user_name")
	private String updateUserName;
	
	/** 通知id*/
	@Column(name = "notice_id")
	private String noticeId;
	
	/** 会议申请id*/
	@Column(name = "meeting_apply_id")
	private String meetingApplyId;
	
	/** 参会人员人数*/
	@Column(name = "attend_person_num")
	private String attendPersonNum;
	
	/** 参会人员id*/
	@Column(name = "attend_person_id")
	private String attendPersonId;
	
	/** 参会人员姓名*/
	@Column(name = "attend_person_name")
	private String attendPersonName;
	
	/** 主要领导人（处长参会人员）姓名*/
	@Column(name = "attend_person_chu_name")
	private String attendPersonChuName;
	
	/** 主要领导人（处长参会人员）id*/
	
	@Column(name = "attend_person_chu_id")
	private String attendPersonChuId;
	
	
	/** 参会部门id*/
	@Column(name = "attend_dept_id")
	private String attendDeptId;
	
	/** 参会部门*/
	@Column(name = "attend_dept")
	private String attendDept;
	
	/** 参会部门类型 0：局，1：处*/
	@Column(name = "attend_dept_type")
	private String attendDeptType;
	
	/** 通知备注*/
	@Transient
	private String noticeRemark;
	
	/** 状态0：未反馈，1已反馈，2无需反馈*/
	@Column(name = "state")
	private String state;

	/** 应参会人数*/
	@Column(name = "SHOULD_ATTEND_NUM")
	private String shouldAttendNum;
	
	/** 实参会人数*/
	@Column(name = "REAL_ATTEND_NUM")
	private String realAttendNum;
	
	/** 分管领导人id*/
	@Column(name = "ATTEND_PERSON_LEAVE_ID")
	private String attendPersonLeaveId;
	
	/** 分管领导人姓名*/
	@Column(name = "ATTEND_PERSON_LEAVE_NAME")
	private String attendPersonLeaveName;
	
	/** 不能参会人员姓名*/
	@Column(name = "NOT_ATTEND_PERSON_ID")
	private String notAttendPersonId;
	
	/** 分管领导人姓名*/
	@Column(name = "NOT_ATTEND_PERSON_NAME")
	private String notAttendPersonName;
	
	/** 分管领导人姓名*/
	@Column(name = "NOT_ATTEND_PERSON_CHU_ID")
	private String notAttendPersonChuId;
	
	/** 分管领导人姓名*/
	@Column(name = "NOT_ATTEND_PERSON_CHU_NAME")
	private String notAttendPersonChuName;
	
	/** 分管领导人姓名*/
	@Column(name = "NOT_ATTEND_PERSON_LEAVE_ID")
	private String notAttendPersonLeaveId;
	
	/** 分管领导人姓名*/
	@Column(name = "NOT_ATTEND_PERSON_LEAVE_NAME")
	private String notAttendPersonLeaveName;
	
	/** 会议室*/
	@Transient
	private String meetingRoom;
	
	/** 会议室地点*/
	@Transient
	private String meetingRoomPlace;
	
	/** 会议名称*/
	@Transient
	private String meetingName;
	
	/** 会议开始时间上下午*/
	@Transient
	private String meetingBeginTime;
	
	/** 会议结束 时间上下午*/
	@Transient
	private String meetingEndTime;
	
	/** 会议开始时间*/
	@Transient
	private String meetingStartDate;
	
	/** 会议结束 时间*/
	@Transient
	private String meetingEndDate;
	
	/** 会议内容*/
	@Transient
	private String meetingContent;
	
	/** 是否反馈*/
	@Transient
	private String isFeedback;
	
	/** 反馈类型*/
	@Transient
	private String feedbackType;
	
	/** 反馈部门id*/
	@Column(name = "feedback_dept_id")
	private String feedbackDeptId;
	
	/** 反馈部门*/
	@Column(name = "feedback_dept_name")
	private String feedbackDeptName;
	
	/** 反馈时间*/
	@Transient
	private String feedbackTime;	
	
	/** 反馈人id*/
	@Column(name = "feedback_person_id")
	private String feedbackPersonId;
	
	/** 反馈人姓名*/
	@Column(name = "feedback_person_name")
	private String feedbackPersonName;
	
	/** 实际反馈时间*/
	@Column(name = "REAL_FEEDBACK_TIME")
	private String realFeedbackTime;
	
	/** 反馈备注*/
	@Column(name = "remark")
	private String remark;
	
	
	/** 排好座位的座位数*/
	@Transient
	private Integer seatNum;
	
	/** 操作*/
	@Transient
	private String cz;
	
	/** 会议起止时间*/
	@Transient
	private String meetingTime;
	
	/** 会议主办单位*/
	@Transient
	private String hostDept;
	
	//排座发布状态
	@Transient
	private String publishState;//0表示未发布，1表示已发布
	
	//反馈的人员是第二部分人（部门领导、处长）还是第三部部分人（副处长、处员、科员）
	@Transient
	private String flag; 
	
	@Transient
	private String attendChuDept;//通知的处的参会部门
	@Transient
	private String attendJuDept;//通知的处的参会部门
	
	
	@Transient
	private String meetingroomId;//会议室id
	
	
	//会议的注意事项
	@Transient
	private String attentionItem;//会议的注意事项
	
	@Transient
	private String attendCount;
	
	@Transient
	private String shouldAttendCount;
	
	@Transient
	private String realAttendCount;
	
	public String getMeetingroomId() {
		return meetingroomId;
	}
	public void setMeetingroomId(String meetingroomId) {
		this.meetingroomId = meetingroomId;
	}
	public String getPublishState() {
		return publishState;
	}
	public void setPublishState(String publishState) {
		this.publishState = publishState;
	}
	public Integer getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}
	public MeetingNoticeBack() {
		super();
	}
	//反馈列表查询构造器
	public MeetingNoticeBack(String attentionItem,String meetingroomId,String publishState,String noticeId,String attendDeptType,
			String feedbackType,String meetingName, String meetingRoom,String meetingTime, String id, 
			String state) {
		super();
		this.attentionItem=attentionItem;
		this.meetingroomId=meetingroomId;
		this.publishState=publishState;
		this.noticeId=noticeId;
		this.attendDeptType=attendDeptType;
		this.feedbackType=feedbackType;
		this.meetingName = meetingName;
		this.meetingRoom = meetingRoom;
		this.meetingTime = meetingTime;
		this.id = id;
		this.state = state;
	}
	//查看反馈详情：列表查询构造器
	public MeetingNoticeBack(String meetingName, String attendDeptId, String attendDept, String attendPersonName,
			String attendPersonId, String attendPersonChuName, String attendPersonChuId, String attendPersonNum, String remark,
			String id, String state, String attendPersonLeaveId,String attendPersonLeaveName,
			String shouldAttendNum, String realAttendNum, String notAttendPersonId, String notAttendPersonName,
			String notAttendPersonChuId, String notAttendPersonChuName,
			String notAttendPersonLeaveId, String notAttendPersonLeaveName) {
		super();
		this.meetingName = meetingName;
		this.attendDeptId = attendDeptId;
		this.attendDept = attendDept;
		this.attendPersonName = attendPersonName;
		this.attendPersonId = attendPersonId;
		this.attendPersonChuName = attendPersonChuName;
		this.attendPersonChuId = attendPersonChuId;
		this.attendPersonNum = attendPersonNum;
		this.remark = remark;
		this.id = id;
		this.state = state;
		this.attendPersonLeaveId = attendPersonLeaveId;
		this.attendPersonLeaveName = attendPersonLeaveName;
		this.shouldAttendNum = shouldAttendNum;
		this.realAttendNum = realAttendNum;
		this.notAttendPersonId = notAttendPersonId;
		this.notAttendPersonName = notAttendPersonName;
		this.notAttendPersonChuId = notAttendPersonChuId;
		this.notAttendPersonChuName = notAttendPersonChuName;
		this.notAttendPersonLeaveId = notAttendPersonLeaveId;
		this.notAttendPersonLeaveName = notAttendPersonLeaveName;
	}
	public String getAttendChuDept() {
		return attendChuDept;
	}
	public void setAttendChuDept(String attendChuDept) {
		this.attendChuDept = attendChuDept;
	}
	public String getAttendJuDept() {
		return attendJuDept;
	}
	public void setAttendJuDept(String attendJuDept) {
		this.attendJuDept = attendJuDept;
	}
	//编辑页面构造器
	public MeetingNoticeBack(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String visible, String creTime,
			String updateTime, String updateUserId, String updateUserName, String attendPersonNum,
			String attendPersonId, String attendDeptId, String attendPersonName, String attendDept, String remark,
			String state, String noticeId, String meetingRoom, String meetingName,
			String meetingBeginTime, String meetingEndTime, String meetingContent, String isFeedback,
			String feedbackType, String feedbackDeptId, String feedbackDeptName, String feedbackTime,
			String feedbackPersonId, String feedbackPersonName, String meetingApplyId, String cz) {
		super();
		this.id = id;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.visible = visible;
		this.creTime = creTime;
		this.updateTime = updateTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.attendPersonNum = attendPersonNum;
		this.attendPersonId = attendPersonId;
		this.attendDeptId = attendDeptId;
		this.attendPersonName = attendPersonName;
		this.attendDept = attendDept;
		this.remark = remark;
		this.state = state;
		this.noticeId = noticeId;
		this.meetingRoom = meetingRoom;
		this.meetingName = meetingName;
		this.meetingBeginTime = meetingBeginTime;
		this.meetingEndTime = meetingEndTime;
		this.meetingContent = meetingContent;
		this.isFeedback = isFeedback;
		this.feedbackType = feedbackType;
		this.feedbackDeptId = feedbackDeptId;
		this.feedbackDeptName = feedbackDeptName;
		this.feedbackTime = feedbackTime;
		this.feedbackPersonId = feedbackPersonId;
		this.feedbackPersonName = feedbackPersonName;
		this.meetingApplyId = meetingApplyId;
		this.cz = cz;
	}

	public MeetingNoticeBack(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String visible, String treeId, String creChuShiId, String creChuShiName, String creJuId, String creJuName,
			String creTime, String updateTime, String updateUserId, String updateUserName, String noticeId,
			String meetingApplyId, String attendPersonNum, String attendPersonId, String attendPersonName,
			String attendPersonChuName, String attendPersonChuId, String attendDeptId, String attendDept,
			String attendDeptType, String noticeRemark, String state, String shouldAttendNum, String realAttendNum,
			String attendPersonLeaveId, String attendPersonLeaveName, String notAttendPersonId,
			String notAttendPersonName, String notAttendPersonChuId, String notAttendPersonChuName,
			String notAttendPersonLeaveId, String notAttendPersonLeaveName, String meetingRoom, String meetingRoomPlace,
			String meetingName, String meetingBeginTime, String meetingEndTime, String meetingStartDate,
			String meetingEndDate, String meetingContent, String isFeedback, String feedbackType, String feedbackDeptId,
			String feedbackDeptName, String feedbackTime, String feedbackPersonId, String feedbackPersonName,
			String realFeedbackTime, String remark, Integer seatNum, String cz, String publishState, String flag,
			String meetingroomId, String attentionItem) {
		super();
		this.id = id;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.visible = visible;
		this.treeId = treeId;
		this.creChuShiId = creChuShiId;
		this.creChuShiName = creChuShiName;
		this.creJuId = creJuId;
		this.creJuName = creJuName;
		this.creTime = creTime;
		this.updateTime = updateTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.noticeId = noticeId;
		this.meetingApplyId = meetingApplyId;
		this.attendPersonNum = attendPersonNum;
		this.attendPersonId = attendPersonId;
		this.attendPersonName = attendPersonName;
		this.attendPersonChuName = attendPersonChuName;
		this.attendPersonChuId = attendPersonChuId;
		this.attendDeptId = attendDeptId;
		this.attendDept = attendDept;
		this.attendDeptType = attendDeptType;
		this.noticeRemark = noticeRemark;
		this.state = state;
		this.shouldAttendNum = shouldAttendNum;
		this.realAttendNum = realAttendNum;
		this.attendPersonLeaveId = attendPersonLeaveId;
		this.attendPersonLeaveName = attendPersonLeaveName;
		this.notAttendPersonId = notAttendPersonId;
		this.notAttendPersonName = notAttendPersonName;
		this.notAttendPersonChuId = notAttendPersonChuId;
		this.notAttendPersonChuName = notAttendPersonChuName;
		this.notAttendPersonLeaveId = notAttendPersonLeaveId;
		this.notAttendPersonLeaveName = notAttendPersonLeaveName;
		this.meetingRoom = meetingRoom;
		this.meetingRoomPlace = meetingRoomPlace;
		this.meetingName = meetingName;
		this.meetingBeginTime = meetingBeginTime;
		this.meetingEndTime = meetingEndTime;
		this.meetingStartDate = meetingStartDate;
		this.meetingEndDate = meetingEndDate;
		this.meetingContent = meetingContent;
		this.isFeedback = isFeedback;
		this.feedbackType = feedbackType;
		this.feedbackDeptId = feedbackDeptId;
		this.feedbackDeptName = feedbackDeptName;
		this.feedbackTime = feedbackTime;
		this.feedbackPersonId = feedbackPersonId;
		this.feedbackPersonName = feedbackPersonName;
		this.realFeedbackTime = realFeedbackTime;
		this.remark = remark;
		this.seatNum = seatNum;
		this.cz = cz;
		this.publishState = publishState;
		this.flag = flag;
		this.meetingroomId = meetingroomId;
		this.attentionItem = attentionItem;
	}
	public String getAttentionItem() {
		return attentionItem;
	}
	public void setAttentionItem(String attentionItem) {
		this.attentionItem = attentionItem;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public String getNoticeRemark() {
		return noticeRemark;
	}
	public void setNoticeRemark(String noticeRemark) {
		this.noticeRemark = noticeRemark;
	}
	public String getRealFeedbackTime() {
		return realFeedbackTime;
	}
	public void setRealFeedbackTime(String realFeedbackTime) {
		this.realFeedbackTime = realFeedbackTime;
	}
	
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	
	public String getIsFeedback() {
		return isFeedback;
	}
	public void setIsFeedback(String isFeedback) {
		this.isFeedback = isFeedback;
	}
	public String getFeedbackType() {
		return feedbackType;
	}
	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
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
	public String getAttendPersonId() {
		return attendPersonId;
	}
	public void setAttendPersonId(String attendPersonId) {
		this.attendPersonId = attendPersonId;
	}
	public String getAttendPersonNum() {
		return attendPersonNum;
	}
	public void setAttendPersonNum(String attendPersonNum) {
		this.attendPersonNum = attendPersonNum;
	}
	public String getMeetingRoomPlace() {
		return meetingRoomPlace;
	}
	public void setMeetingRoomPlace(String meetingRoomPlace) {
		this.meetingRoomPlace = meetingRoomPlace;
	}
	public String getAttendDeptId() {
		return attendDeptId;
	}
	public void setAttendDeptId(String attendDeptId) {
		this.attendDeptId = attendDeptId;
	}
	public String getAttendPersonName() {
		return attendPersonName;
	}
	public void setAttendPersonName(String attendPersonName) {
		this.attendPersonName = attendPersonName;
	}
	public String getAttendDept() {
		return attendDept;
	}
	public void setAttendDept(String attendDept) {
		this.attendDept = attendDept;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public String getMeetingRoom() {
		return meetingRoom;
	}
	public void setMeetingRoom(String meetingRoom) {
		this.meetingRoom = meetingRoom;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	public String getMeetingBeginTime() {
		return meetingBeginTime;
	}
	public void setMeetingBeginTime(String meetingBeginTime) {
		this.meetingBeginTime = meetingBeginTime;
	}
	public String getMeetingEndTime() {
		return meetingEndTime;
	}
	public void setMeetingEndTime(String meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}
	public String getMeetingContent() {
		return meetingContent;
	}
	public void setMeetingContent(String meetingContent) {
		this.meetingContent = meetingContent;
	}
	public String getFeedbackDeptId() {
		return feedbackDeptId;
	}
	public void setFeedbackDeptId(String feedbackDeptId) {
		this.feedbackDeptId = feedbackDeptId;
	}
	public String getFeedbackDeptName() {
		return feedbackDeptName;
	}
	public void setFeedbackDeptName(String feedbackDeptName) {
		this.feedbackDeptName = feedbackDeptName;
	}
	public String getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	public String getFeedbackPersonId() {
		return feedbackPersonId;
	}
	public void setFeedbackPersonId(String feedbackPersonId) {
		this.feedbackPersonId = feedbackPersonId;
	}
	public String getFeedbackPersonName() {
		return feedbackPersonName;
	}
	public void setFeedbackPersonName(String feedbackPersonName) {
		this.feedbackPersonName = feedbackPersonName;
	}
	public String getMeetingApplyId() {
		return meetingApplyId;
	}
	public void setMeetingApplyId(String meetingApplyId) {
		this.meetingApplyId = meetingApplyId;
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
	public String getMeetingStartDate() {
		return meetingStartDate;
	}
	public void setMeetingStartDate(String meetingStartDate) {
		this.meetingStartDate = meetingStartDate;
	}
	public String getMeetingEndDate() {
		return meetingEndDate;
	}
	public void setMeetingEndDate(String meetingEndDate) {
		this.meetingEndDate = meetingEndDate;
	}
	public String getAttendDeptType() {
		return attendDeptType;
	}
	public void setAttendDeptType(String attendDeptType) {
		this.attendDeptType = attendDeptType;
	}
	public String getAttendPersonChuName() {
		return attendPersonChuName;
	}
	public void setAttendPersonChuName(String attendPersonChuName) {
		this.attendPersonChuName = attendPersonChuName;
	}
	public String getAttendPersonChuId() {
		return attendPersonChuId;
	}
	public void setAttendPersonChuId(String attendPersonChuId) {
		this.attendPersonChuId = attendPersonChuId;
	}
	public String getShouldAttendNum() {
		return shouldAttendNum;
	}
	public void setShouldAttendNum(String shouldAttendNum) {
		this.shouldAttendNum = shouldAttendNum;
	}
	public String getRealAttendNum() {
		return realAttendNum;
	}
	public void setRealAttendNum(String realAttendNum) {
		this.realAttendNum = realAttendNum;
	}
	public String getAttendPersonLeaveId() {
		return attendPersonLeaveId;
	}
	public void setAttendPersonLeaveId(String attendPersonLeaveID) {
		this.attendPersonLeaveId = attendPersonLeaveID;
	}
	public String getAttendPersonLeaveName() {
		return attendPersonLeaveName;
	}
	public void setAttendPersonLeaveName(String attendPersonLeaveName) {
		this.attendPersonLeaveName = attendPersonLeaveName;
	}
	public String getNotAttendPersonId() {
		return notAttendPersonId;
	}
	public void setNotAttendPersonId(String notAttendPersonId) {
		this.notAttendPersonId = notAttendPersonId;
	}
	public String getNotAttendPersonName() {
		return notAttendPersonName;
	}
	public void setNotAttendPersonName(String notAttendPersonName) {
		this.notAttendPersonName = notAttendPersonName;
	}
	public String getNotAttendPersonChuId() {
		return notAttendPersonChuId;
	}
	public void setNotAttendPersonChuId(String notAttendPersonChuId) {
		this.notAttendPersonChuId = notAttendPersonChuId;
	}
	public String getNotAttendPersonChuName() {
		return notAttendPersonChuName;
	}
	public void setNotAttendPersonChuName(String notAttendPersonChuName) {
		this.notAttendPersonChuName = notAttendPersonChuName;
	}
	public String getNotAttendPersonLeaveId() {
		return notAttendPersonLeaveId;
	}
	public void setNotAttendPersonLeaveId(String notAttendPersonLeaveId) {
		this.notAttendPersonLeaveId = notAttendPersonLeaveId;
	}
	public String getNotAttendPersonLeaveName() {
		return notAttendPersonLeaveName;
	}
	public void setNotAttendPersonLeaveName(String notAttendPersonLeaveName) {
		this.notAttendPersonLeaveName = notAttendPersonLeaveName;
	}
	public String getMeetingTime() {
		return meetingTime;
	}
	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}
	public String getHostDept() {
		return hostDept;
	}
	public void setHostDept(String hostDept) {
		this.hostDept = hostDept;
	}
	public String getAttendCount() {
		return attendCount;
	}
	public void setAttendCount(String attendCount) {
		this.attendCount = attendCount;
	}
	public String getShouldAttendCount() {
		return shouldAttendCount;
	}
	public void setShouldAttendCount(String shouldAttendCount) {
		this.shouldAttendCount = shouldAttendCount;
	}
	public String getRealAttendCount() {
		return realAttendCount;
	}
	public void setRealAttendCount(String realAttendCount) {
		this.realAttendCount = realAttendCount;
	}
	
	
}
