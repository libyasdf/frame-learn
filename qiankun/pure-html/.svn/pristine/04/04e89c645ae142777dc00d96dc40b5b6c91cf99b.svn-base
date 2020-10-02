package com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 座位entry
 * TODO 
 * @author 马秋霞
 * @Date 2018年9月12日 下午2:52:22
 */
@Entity
@Table(name = "hygl_seat")
public class Seat {
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
	/*创建人处室id*/
	@Column(name = "cre_chushi_id")
	private String creChushiId;
	/*创建人处室名*/
	@Column(name = "cre_chushi_name")
	private String creChushiName;
	/*创建人二级局id*/
	@Column(name = "cre_ju_id")
	private String creJuId;
	/*创建人二级局名*/
	@Column(name = "cre_ju_name")
	private String creJuName;
	/*行坐标*/
	@Column(name = "row1")
	private int row;
	/*纵坐标*/
	@Column(name = "col")
	private int col;//列
	//座位的状态:0表示未占用，2表示座位被占用，4预留座位;3表是个人的座位图（不会存于数据库，在给每个参会人员发座位图时用）
	@Column(name = "state")
	private int state;
	//座位的拥有者姓名
	@Column(name = "owner")
	private String owner;
	//座位的拥有者的id
	@Column(name = "ownerid")
	private String ownerid;
	/** 参会部门*/
	@Column(name = "attend_dept")
	private String attendDeptName;
	/** 参会部门id*/
	@Column(name = "attend_dept_id")
	private String attendDeptId;
	//会议通知的id
	@Column(name = "noticeId")
	private String noticeId;
	//是否自动排座
	@Column(name = "auto_arrange")
	private String autoArrange;//0表示自动排座，1表示手动排座，2表示预留
	//反馈类型
	@Column(name = "fankui_type")
	private String fankuiType;//0表示按人名进行排座，1表示按人数进行排座(暂时没用)
	//发布状态
	@Column(name = "publish_state")
	private String publishState;//0表示未发布，1表示已发布
	
	/** 会议标题*/
	@Transient
	private String noticeName;
	/** 会议室 */
	@Transient
	private String meetingRoom;
	/** 会议开始时间 */
	@Transient
	private String startDate;
	/** 会议开始时间上下午 */
	@Transient
	private String startTime;
	/** 该单位排的第几个座位 */
	@Column(name = "seat_num")
	private String seatNum;

	public String getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getNoticeName() {
		return noticeName;
	}
	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}
	public String getMeetingRoom() {
		return meetingRoom;
	}
	public void setMeetingRoom(String meetingRoom) {
		this.meetingRoom = meetingRoom;
	}
	public String getPublishState() {
		return publishState;
	}
	public void setPublishState(String publishState) {
		this.publishState = publishState;
	}
	public String getAttendDeptName() {
		return attendDeptName;
	}
	public void setAttendDeptName(String attendDeptName) {
		this.attendDeptName = attendDeptName;
	}
	public String getAttendDeptId() {
		return attendDeptId;
	}
	public void setAttendDeptId(String attendDeptId) {
		this.attendDeptId = attendDeptId;
	}
	public String getFankuiType() {
		return fankuiType;
	}
	public void setFankuiType(String fankuiType) {
		this.fankuiType = fankuiType;
	}
	public String getAutoArrange() {
		return autoArrange;
	}
	public void setAutoArrange(String autoArrange) {
		this.autoArrange = autoArrange;
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
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
   
   
}
