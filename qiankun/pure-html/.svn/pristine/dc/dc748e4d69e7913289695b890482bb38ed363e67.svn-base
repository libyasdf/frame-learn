package com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 值班通知实体类
 * @author 李颖洁  
 * @date 2018年7月10日  下午4:27:14
 */
@Entity
@Table(name="zbgl_duty_notice")
public class DutyWork {

	/*主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/*创建人id*/
	@Column(name = "CRE_USER_ID")
	private String creUserId;
	
	/*创建人名*/
	@Column(name = "CRE_USER_NAME")
	private String creUserName;
	
	/*创建人部门id*/
	@Column(name = "CRE_DEPT_ID")
	private String creDeptId;
	
	/*创建人部门名*/
	@Column(name = "CRE_DEPT_NAME")
	private String creDeptName;
	
	/*处室id*/
	@Column(name = "CRE_CHUSHI_ID")
	private String creChuShiId;
	
	/*处室名称*/
	@Column(name = "CRE_CHUSHI_NAME")
	private String creChuShiName;
	
	/*二级局id*/
	@Column(name = "CRE_JU_ID")
	private String creJuId;
	
	/*二级局名*/
	@Column(name = "CRE_JU_NAME")
	private String creJuName;
	
	/*逻辑删除0:删除 1：可见*/
	@Column(name = "VISIBLE")
	private String visible = "1";
	
	/*创建时间*/
	@Column(name = "CRE_TIME")
	private String creTime;
	
	/*值班标题*/
	@Column(name = "TITLE")
	private String title;
	
	/*有效期开始时间*/
	@Column(name = "START_TIME")
	private String startTime;
	
	/*有效期结束时间*/
	@Column(name = "END_TIME")
	private String endTime;
	
	/*通知具体内容*/
	@Column(name = "NOTICE_TEXT")
	private String noticeText;
	
	/*备注*/
	@Column(name = "REMARK")
	private String remark;
	
	/*是否安保值班:0:不是，1：是*/
	@Column(name = "IS_SECURITY")
	private String isSecurity;
	
	/*状态:是否发布0：草稿1：发布*/
	@Column(name = "STATE")
	private String state;
	
	/*最后更新人id*/
	@Column(name = "UPDATE_USER_ID")
	private String updateUserId;
	
	/*最后更新人名*/
	@Column(name = "UPDATE_USER_NAME")
	private String updateUserName;
	
	/*最后更新时间*/
	@Column(name = "UPDATE_TIME")
	private String updateTime;
	
	/*有效期*/
	@Transient
	private String yxq ;
	
	/*操作（列表中显示的操作）*/
	@Transient
	private String cz;
	
	/*是否已读（部门通知接收列表中的已读状态）*/
	@Transient
	private String isRead;
	
	/*是否已上报（部门通知接收列表中的上报状态）*/
	@Transient
	private String isReport;
	
	/*通知接收ID（部门通知接收列表中的ID）*/
	@Transient
	private String inceptId;
	
	/**
	 * 无参构造方法
	 */
	public DutyWork() {
		super();
	}
	
	/**
	 * 有参构造方法（封装列表信息）
	 * @param id
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param state
	 */
	public DutyWork(String id, String title, String startTime, String endTime, String state) {
		super();
		this.id = id;
		this.title = title;
		this.state = state;
		this.startTime = startTime;
		this.endTime = endTime;
		if(startTime==null&&endTime==null){
			yxq = "";
		}else{
			yxq = startTime+"-"+endTime;
		}
		if("0".equals(state)){
			cz = "update,delete";
		}else{
			cz = "";
		}
	}
	
	/**
	 * 有参构造方法（封装部门接收通知列表信息）
	 * @param id 通知ID
	 * @param title 通知标题
	 * @param startTime 有效期开始时间
	 * @param endTime 有效期结束时间
	 * @param isRead 通知是否已读
	 * @param noticeText 通知内容
	 * @param remark 通知备注
	 * @param inceptId 本部门接收此通知的通知接收表的ID
	 */
	public DutyWork(String id, String title, String noticeText,String remark,String startTime, String endTime, String isRead,String isReport,String inceptId) {
		super();
		this.id = id;
		this.title = title;
		this.noticeText = noticeText;
		this.remark = remark;
		this.isRead = isRead;
		this.isReport = isReport;
		this.inceptId = inceptId;
		this.startTime = startTime;
		this.endTime = endTime;
		if(startTime==null&&endTime==null){
			yxq = "";
		}else{
			yxq = startTime+"-"+endTime;
		}
	}

	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	public String getInceptId() {
		return inceptId;
	}

	public void setInceptId(String inceptId) {
		this.inceptId = inceptId;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getCz() {
		return cz;
	}
	
	public void setCz(String cz) {
		this.cz = cz;
	}
	
	public String getYxq() {
		return this.yxq;
	}
	
	public void setYxq(String yxq) {
		this.yxq = yxq;
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

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getNoticeText() {
		return noticeText;
	}

	public void setNoticeText(String noticeText) {
		this.noticeText = noticeText;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsSecurity() {
		return isSecurity;
	}

	public void setIsSecurity(String isSecurity) {
		this.isSecurity = isSecurity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
	
	
}
