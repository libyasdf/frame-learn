package com.sinosoft.sinoep.modules.zhbg.hygl.meetingSign.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 会议签到实体类
 * TODO 
 * @author 郝灵涛
 * @Date 2018年7月13日 上午11:21:48
 */
@Entity
@Table(name = "HYGL_MEETING_SIGN")
public class MeetingSignInfo {
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
	/*更新时间*/
	@Column(name = "update_time")
	private String updateTime;
	/*更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	/*更新人姓名*/
	@Column(name = "update_user_name")
	private String updateUserName;	
	/*逻辑删除*/
	@Column(name = "visible")
	private String visible;
	/* 会议名称 */
	@Column(name = "MEETING_NAME")
	private String meetingName;
	/* 会议室 */
	@Column(name = "MEETINGROOM")
	private String meetingRoom;
	/* 签到机id */
	@Column(name = "SIGN_MACHINE_ID")
	private String signMachineId;
	
	/* 参会人员cardid */
	@Column(name = "CARD_ID")
	private String cardId;
	/* 参会人员cardid */
	@Column(name = "CARD_HOLDER")
	private String cardHolder;
	/*签到时间*/
	@Column(name = "SIGN_IN_TIME")
	private String signInTime;
	/*签到状态*/
	@Column(name = "STATE")
	private String state;
	/*会议Id*/
	@Column(name = "MEETING_APPLY_ID")
	private String meetingApplyId;
	/*备注*/
	@Column(name = "remark")
	private String remark;
	
	public MeetingSignInfo() {
		super();
	}

	public MeetingSignInfo(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String creTime, String updateTime, String updateUserId, String updateUserName, String visible,
			String meetingName, String meetingRoom, String signMachineId, String cardId, String cardHolder,
			String signInTime, String state, String meetingApplyId, String remark) {
		super();
		this.id = id;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.creTime = creTime;
		this.updateTime = updateTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.visible = visible;
		this.meetingName = meetingName;
		this.meetingRoom = meetingRoom;
		this.signMachineId = signMachineId;
		this.cardId = cardId;
		this.cardHolder = cardHolder;
		this.signInTime = signInTime;
		this.state = state;
		this.meetingApplyId = meetingApplyId;
		this.remark = remark;
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
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
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

	public String getSignInTime() {
		return signInTime;
	}
	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getMeetingApplyId() {
		return meetingApplyId;
	}
	public void setMeetingApplyId(String meetingApplyId) {
		this.meetingApplyId = meetingApplyId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getSignMachineId() {
		return signMachineId;
	}

	public void setSignMachineId(String signMachineId) {
		this.signMachineId = signMachineId;
	}
	

}
