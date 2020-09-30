package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * 会议室信息
 * TODO 
 * @author 冯超
 * @Date 2018年7月19日 上午11:39:16
 */
@Entity
@Table(name = "HYGL_MEETINGROOM")
public class MeetingRoomInfo {
	
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
	
	/** 会议室编号*/
	@Column(name = "meetingroom_no")
	private String meetingRoomNo;
	
	/** 会议室名称*/
	@Column(name = "meetingroom_name")
	private String meetingRoomName;
	
	/** 会议室地点*/
	@Column(name = "location")
	private String location;
	
	/** 可容纳人数*/
	@Column(name = "content")
	private String content;
	
	/** 门牌号*/
	@Column(name = "door_number")
	private String doorNumber;
	
	/** 会议室状态*/
	@Column(name = "meetingroom_state")
	private String meetingroomState;
	
	/** 布局*/
	@Column(name = "layout")
	private String layout;
	
	/** 设备情况*/
	@Column(name = "equipment")
	private String equipment;
	
	/** 管理部门ID*/
	@Column(name = "manage_dept_id")
	private String manageDeptId;
	
	/** 管理部门NAME*/
	@Column(name = "manage_dept_name")
	private String manageDeptName;
	
	/** 管理员*/
	@Column(name = "manage_user_name")
	private String manageUserName;
	
	/** 联系方式*/
	@Column(name = "phone")
	private String phone;
	
	/** 网络方式*/
	@Column(name = "network")
	private String network;
	
	/** 会议室平面图*/
	@Column(name = "meetingroom_plan")
	private String meetingroomPlan;
	
	/** 顺序号 */
	@Column(name = "sort")
	private int sort;
	
	/** 备注*/
	@Column(name = "REMARK")
	private String remark;
	
	/** 操作 */
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

	public String getMeetingRoomNo() {
		return meetingRoomNo;
	}

	public void setMeetingRoomNo(String meetingRoomNo) {
		this.meetingRoomNo = meetingRoomNo;
	}

	public String getMeetingRoomName() {
		return meetingRoomName;
	}

	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}

	public String getMeetingroomState() {
		return meetingroomState;
	}

	public void setMeetingroomState(String meetingroomState) {
		this.meetingroomState = meetingroomState;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getManageDeptId() {
		return manageDeptId;
	}

	public void setManageDeptId(String manageDeptId) {
		this.manageDeptId = manageDeptId;
	}

	public String getManageDeptName() {
		return manageDeptName;
	}

	public void setManageDeptName(String manageDeptName) {
		this.manageDeptName = manageDeptName;
	}

	public String getManageUserName() {
		return manageUserName;
	}

	public void setManageUserName(String manageUserName) {
		this.manageUserName = manageUserName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getMeetingroomPlan() {
		return meetingroomPlan;
	}

	public void setMeetingroomPlan(String meetingroomPlan) {
		this.meetingroomPlan = meetingroomPlan;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
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
