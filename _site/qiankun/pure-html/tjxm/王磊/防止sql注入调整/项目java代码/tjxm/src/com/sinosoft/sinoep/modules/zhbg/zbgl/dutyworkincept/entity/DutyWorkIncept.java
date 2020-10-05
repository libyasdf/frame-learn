package com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 值班通知接收实体类
 * @author 李颖洁  
 * @date 2018年7月10日  下午4:39:14
 */
@Entity
@Table(name="zbgl_duty_notice_incept")
public class DutyWorkIncept {

	/*主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/*值班通知_主键id*/
	@Column(name="ZB_NOTICE_ID")
	private String zbNoticeId;
	
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
	
	/*逻辑删除*/
	@Column(name = "VISIBLE")
	private String visible;
	
	/*创建时间*/
	@Column(name = "CRE_TIME")
	private String creTime;
	
	/*值班部门id*/
	@Column(name = "ZB_DEPT_ID")
	private String zbDeptId;
	
	/*值班部门名*/
	@Column(name = "ZB_DEPT_NAME")
	private String zbDeptName;
	
	/*是否已读*/
	@Column(name = "IS_READ")
	private String isRead;
	
	/*读取人id*/
	@Column(name = "READ_USER_ID")
	private String readUserId;
	
	/*读取人姓名*/
	@Column(name = "READ_USER_NAME")
	private String readUserName;
	
	/*读取时间*/
	@Column(name = "READ_TIME")
	private String readTime;
	
	/*是否上报:0：未上报1：已上报*/
	@Column(name = "IS_REPORT")
	private String isReport;

	/**
	 * 无参构造方法
	 */
	public DutyWorkIncept() {
		super();
	}
	
	/**
	 * 有参构造方法
	 * @param id
	 * @param zbNoticeId
	 * @param zbDeptId
	 * @param zbDeptName
	 * @param isRead
	 * @param readUserId
	 * @param readUserName
	 * @param readTime
	 * @param isReport
	 */
	public DutyWorkIncept(String id, String zbNoticeId, String zbDeptId, String zbDeptName, String isRead,
			String readUserId, String readUserName, String readTime, String isReport) {
		super();
		this.id = id;
		this.zbNoticeId = zbNoticeId;
		this.zbDeptId = zbDeptId;
		this.zbDeptName = zbDeptName;
		this.isRead = isRead;
		this.readUserId = readUserId;
		this.readUserName = readUserName;
		this.readTime = readTime;
		this.isReport = isReport;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZbNoticeId() {
		return zbNoticeId;
	}

	public void setZbNoticeId(String zbNoticeId) {
		this.zbNoticeId = zbNoticeId;
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



	public String getZbDeptId() {
		return zbDeptId;
	}

	public void setZbDeptId(String zbDeptId) {
		this.zbDeptId = zbDeptId;
	}

	public String getZbDeptName() {
		return zbDeptName;
	}

	public void setZbDeptName(String zbDeptName) {
		this.zbDeptName = zbDeptName;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getReadUserId() {
		return readUserId;
	}

	public void setReadUserId(String readUserId) {
		this.readUserId = readUserId;
	}

	public String getReadUserName() {
		return readUserName;
	}

	public void setReadUserName(String readUserName) {
		this.readUserName = readUserName;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}
	
	
	
	
	
}
