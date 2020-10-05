package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * 会议通知类
 * 
 * @author 郝灵涛
 * @Date 2018年7月9日 下午6:18:16
 */
@Entity
@Table(name = "HYGL_MEETING_NOTICE")
public class MeetingNoticeInfo {

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

	/** 创建时间 */
	@Column(name = "cre_time")
	private String creTime;

	/** 最后更新人ID */
	@Column(name = "update_user_id")
	private String updateUserId;

	/** 最后更新人名 */
	@Column(name = "update_user_name")
	private String updateUserName;

	/** 最后更新时间 */
	@Column(name = "update_time")
	private String updateTime;
	
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
	/** treeId*/
	@Column(name = "tree_id")
	private String treeId;
	/** 逻辑删除 */
	@Column(name = "VISIBLE")
	private String visible;

	/** 参会部门 处*/
	@Column(name = "ATTEND_DEPT")
	private String attendDept;
	
	/** 参会部门局 */
	@Column(name = "ATTEND_DEPT_JU")
	private String attendDeptJu;
	
	/** 参会单位Id*/
	@Column(name = "ATTEND_DEPT_JU_ID")
	private String attendDeptJuId;
	
	/** 是否反馈   1：是  0：不是*/
	@Column(name = "IS_FANKUI")
	private String isFankui;
	
	/** 反馈类别  1：参会人数  0:参会人名*/
	@Column(name = "FANKUI_TYPE")
	private String fankuiType;

	/** 反馈日期 */
	@Column(name = "RESPONSE_TIME")
	private String responseTime;

	/** 实际反馈日期 */
	@Column(name = "ACTUAL_FANKUI_TIME")
	private String actualFankuiTime;
	
	/** 是否已排位 （0：未排位，1：已排位，2：无需排位）*/
	@Column(name = "IS_PAIWEI")
	private String isPaiwei;
	
	/** 人数 */
	@Column(name = "ATTEND_NUMBERS")
	private String attendNumbers;

	/** 人名 */
	@Column(name = "ATTEND_NAMES")
	private String attendNames;

	/** 会议名称 */
	@Transient
	private String meetingName;

	/** 通知标题*/
	@Column(name = "notice_name")
	private String noticeName;
	
	/** 参会单位处Id*/
	@Column(name = "ATTEND_DEPT_ID")
	private String attendDeptId;
	
	/** 会议室id */
	@Transient
	private String meetingroomId;
	
	/** 会议室 */
	@Transient
	private String meetingRoom;
	
	/** 会议时间 */
	@Transient
	private String meetingTime;

	/** 会议开始时间 */
	@Transient
	private String startDate;

	/** 会议结束时间 */
	@Transient
	private String endDate;
	/** 会议开始时间上下午 */
	@Transient
	private String startTime;

	/** 会议结束时间上下午 */
	@Transient
	private String endTime;

	/** 状态 0:草稿 1：已发布  */
	@Column(name = "status")
	private String status;

	/** 会议内容 */
	@Column(name = "CONTENTS")
	private String contents;
	
	/** 会议地点 */
	@Transient
	private String meetingPlace;

	/** 备注 */
	@Column(name = "REMARK")
	private String remark;

	/** 申请ID */
	@Column(name = "APPLY_ID")
	private String applyId;

	/** 流程状态 */
	@Column(name = "SUBFLAG")
	private String subflag;
	
	//排座发布状态
	@Column(name = "publish_state")
	private String publishState;//0表示未发布，1表示已发布
	
	//会议的注意事项
	@Column(name = "attention_item")
	private String attentionItem;//会议的注意事项
	
	/** 提醒时间 */
	@Column(name = "remind_time")
	private String remindTime;

	/**附件*/
	@Column(name = "affix_file")
	private String affixFile;
	
	/** 操作 */
	@Transient
	private String cz = "";
	
	/** 主办单位 */
	@Transient
	private String hostDeptName;
	
	/** 未反馈的局id */
	@Transient
	private String notFanKuiJuId;
	
	/** 未反馈的处id */
	@Transient
	private String notFanKuiChuId;
	
	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	@Transient
	private Integer shouldAttendDeptNum;
	
	@Transient
	private Integer actualAttendDeptNum;
	
	/** 排好的座位数 */
	@Transient
	private Integer seatNum;
	
	
	public Integer getSeatNum() {
		return seatNum;
	}

	public String getAttentionItem() {
		return attentionItem;
	}

	public void setAttentionItem(String attentionItem) {
		this.attentionItem = attentionItem;
	}

	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}

	public MeetingNoticeInfo() {
		super();
	}
	public MeetingNoticeInfo(String attentionItem,String meetingroomId,String meetingName,String publishState,String meetingRoom,String startTime, String endTime,String startDate,String endDate,
			String id,String applyId, String attendDept, String isFankui,String fankuiType, String responseTime,
			String actualFankuiTime,String attendDeptId,String attendDeptJu, String attendDeptJuId,String noticeName, String status, String contents,  String remark,String treeId,String meetingPlace) {
		super();
		this.attentionItem=attentionItem;
		this.meetingroomId=meetingroomId;
		this.meetingName = meetingName;
		this.publishState=publishState;
		this.meetingRoom = meetingRoom;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.id = id;
		this.applyId = applyId;
		this.attendDept = attendDept;
		this.isFankui = isFankui;
		this.fankuiType = fankuiType;
		this.responseTime = responseTime;
		this.actualFankuiTime = actualFankuiTime;
		this.attendDeptId = attendDeptId;
		this.attendDeptJu = attendDeptJu;
		this.attendDeptJuId = attendDeptJuId;
		this.noticeName = noticeName;
		this.status = status;
		this.contents = contents;
		this.remark = remark;
		this.treeId = treeId;
		this.meetingPlace = meetingPlace;
	}

	
	public MeetingNoticeInfo(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String creTime, String updateUserId, String updateUserName, String updateTime, String creChuShiId,
			String creChuShiName, String creJuId, String creJuName, String visible, String attendDept, String isFankui,
			String fankuiType, String responseTime, String actualFankuiTime, String attendNumbers, String attendNames,
			String meetingName, String attendDeptId, String meetingRoom, String startTime, String endTime,
			String status, String contents, String meetingPlace, String remark, String applyId, String subflag,
			String affixFile, String cz,String treeId) {
		super();
		this.id = id;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.creTime = creTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.creChuShiId = creChuShiId;
		this.creChuShiName = creChuShiName;
		this.creJuId = creJuId;
		this.creJuName = creJuName;
		this.visible = visible;
		this.attendDept = attendDept;
		this.isFankui = isFankui;
		this.fankuiType = fankuiType;
		this.responseTime = responseTime;
		this.actualFankuiTime = actualFankuiTime;
		this.attendNumbers = attendNumbers;
		this.attendNames = attendNames;
		this.meetingName = meetingName;
		this.attendDeptId = attendDeptId;
		this.meetingRoom = meetingRoom;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.contents = contents;
		this.meetingPlace = meetingPlace;
		this.remark = remark;
		this.applyId = applyId;
		this.subflag = subflag;
		this.affixFile = affixFile;
		this.cz = cz;
		this.treeId = treeId;
		
	}

	public MeetingNoticeInfo(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String creTime, String updateUserId, String updateUserName, String updateTime, String creChuShiId,
			String creChuShiName, String creJuId, String creJuName, String treeId, String visible, String attendDept,
			String attendDeptJu, String attendDeptJuId, String isFankui, String fankuiType, String responseTime,
			String actualFankuiTime, String isPaiwei, String attendNumbers, String attendNames, String meetingName,
			String noticeName, String attendDeptId, String meetingroomId, String meetingRoom, String meetingTime,
			String startDate, String endDate, String startTime, String endTime, String status, String contents,
			String meetingPlace, String remark, String applyId, String subflag, String publishState,
			String attentionItem, String affixFile, String cz, String hostDeptName, Integer shouldAttendDeptNum,
			Integer actualAttendDeptNum, Integer seatNum) {
		super();
		this.id = id;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.creTime = creTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.creChuShiId = creChuShiId;
		this.creChuShiName = creChuShiName;
		this.creJuId = creJuId;
		this.creJuName = creJuName;
		this.treeId = treeId;
		this.visible = visible;
		this.attendDept = attendDept;
		this.attendDeptJu = attendDeptJu;
		this.attendDeptJuId = attendDeptJuId;
		this.isFankui = isFankui;
		this.fankuiType = fankuiType;
		this.responseTime = responseTime;
		this.actualFankuiTime = actualFankuiTime;
		this.isPaiwei = isPaiwei;
		this.attendNumbers = attendNumbers;
		this.attendNames = attendNames;
		this.meetingName = meetingName;
		this.noticeName = noticeName;
		this.attendDeptId = attendDeptId;
		this.meetingroomId = meetingroomId;
		this.meetingRoom = meetingRoom;
		this.meetingTime = meetingTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.contents = contents;
		this.meetingPlace = meetingPlace;
		this.remark = remark;
		this.applyId = applyId;
		this.subflag = subflag;
		this.publishState = publishState;
		this.attentionItem = attentionItem;
		this.affixFile = affixFile;
		this.cz = cz;
		this.hostDeptName = hostDeptName;
		this.shouldAttendDeptNum = shouldAttendDeptNum;
		this.actualAttendDeptNum = actualAttendDeptNum;
		this.seatNum = seatNum;
	}

	public Integer getShouldAttendDeptNum() {
		int length=0;
		if(StringUtils.isNotBlank(attendDeptId) && StringUtils.isNotBlank(attendDeptJuId)){
			length=attendDeptId.split(",").length+attendDeptJuId.split(",").length;
		}else if(StringUtils.isNotBlank(attendDeptId)){
			length=attendDeptId.split(",").length;
		}else if(StringUtils.isNotBlank(attendDeptJuId)){
			length=attendDeptJuId.split(",").length;
		}
		return length;
	}

	public void setShouldAttendDeptNum(Integer shouldAttendDeptNum) {
		this.shouldAttendDeptNum = shouldAttendDeptNum;
	}

	public Integer getActualAttendDeptNum() {
		return actualAttendDeptNum;
	}

	public void setActualAttendDeptNum(Integer actualAttendDeptNum) {
		this.actualAttendDeptNum = actualAttendDeptNum;
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
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

	
	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
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

	public String getAttendDept() {
		return attendDept;
	}

	public void setAttendDept(String attendDept) {
		this.attendDept = attendDept;
	}

	public String getAttendDeptId() {
		return attendDeptId;
	}

	public void setAttendDeptId(String attendDeptId) {
		this.attendDeptId = attendDeptId;
	}
	
	public String getIsFankui() {
		return isFankui;
	}

	public void setIsFankui(String isFankui) {
		this.isFankui = isFankui;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getActualFankuiTime() {
		return actualFankuiTime;
	}

	public void setActualFankuiTime(String actualFankuiTime) {
		this.actualFankuiTime = actualFankuiTime;
	}

	public String getAttendNumbers() {
		return attendNumbers;
	}

	public void setAttendNumbers(String attendNumbers) {
		this.attendNumbers = attendNumbers;
	}

	public String getAttendNames() {
		return attendNames;
	}

	public void setAttendNames(String attendNames) {
		this.attendNames = attendNames;
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(String meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getFankuiType() {
		return fankuiType;
	}

	public void setFankuiType(String fankuiType) {
		this.fankuiType = fankuiType;
	}


	public String getAffixFile() {
		return affixFile;
	}


	public void setAffixFile(String affixFile) {
		this.affixFile = affixFile;
	}

	public String getAttendDeptJu() {
		return attendDeptJu;
	}

	public void setAttendDeptJu(String attendDeptJu) {
		this.attendDeptJu = attendDeptJu;
	}

	public String getAttendDeptJuId() {
		return attendDeptJuId;
	}

	public void setAttendDeptJuId(String attendDeptJuId) {
		this.attendDeptJuId = attendDeptJuId;
	}

	public String getIsPaiwei() {
		return isPaiwei;
	}

	public void setIsPaiwei(String isPaiwei) {
		this.isPaiwei = isPaiwei;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getHostDeptName() {
		return hostDeptName;
	}

	public void setHostDeptName(String hostDeptName) {
		this.hostDeptName = hostDeptName;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	public String getNotFanKuiJuId() {
		return notFanKuiJuId;
	}

	public void setNotFanKuiJuId(String notFanKuiJuId) {
		this.notFanKuiJuId = notFanKuiJuId;
	}

	public String getNotFanKuiChuId() {
		return notFanKuiChuId;
	}

	public void setNotFanKuiChuId(String notFanKuiChuId) {
		this.notFanKuiChuId = notFanKuiChuId;
	}
	
	
	

}
