package com.sinosoft.sinoep.modules.system.notice.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 通知通告反馈信息实体类
 * @author 李利广
 * @Date 2018年8月17日 下午4:13:08
 */
@Entity
@Table(name="SYS_NOTICE_BACK")
public class SysNoticeBack  {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	/** 反馈人处室ID */
	@Column(name="BACK_CHUSHI_ID")
	private String backChushiId;

	/** 反馈人处室name */
	@Column(name="BACK_CHUSHI_NAME")
	private String backChushiName;

	/** 反馈内容 */
	@Column(name="BACK_CONTENT")
	private String backContent;

	/** 反馈人部门ID */
	@Column(name="BACK_DEPT_ID")
	private String backDeptId;

	/** 反馈人部门name */
	@Column(name="BACK_DEPT_NAME")
	private String backDeptName;

	/** 反馈人二级局ID */
	@Column(name="BACK_JU_ID")
	private String backJuId;

	/** 反馈人二级局name */
	@Column(name="BACK_JU_NAME")
	private String backJuName;

	/** 反馈时间 */
	@Column(name="BACK_TIME")
	private String backTime;

	/** 反馈人ID */
	@Column(name="BACK_USER_ID")
	private String backUserId;

	/** 反馈人name */
	@Column(name="BACK_USER_NAME")
	private String backUserName;

	/** 通知通告主键ID */
	@Column(name="NOTICE_ID")
	private String noticeId;

	@Transient
	private Boolean hasAffix;

	public SysNoticeBack() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBackChushiId() {
		return this.backChushiId;
	}

	public void setBackChushiId(String backChushiId) {
		this.backChushiId = backChushiId;
	}

	public String getBackChushiName() {
		return this.backChushiName;
	}

	public void setBackChushiName(String backChushiName) {
		this.backChushiName = backChushiName;
	}

	public String getBackContent() {
		return this.backContent;
	}

	public void setBackContent(String backContent) {
		this.backContent = backContent;
	}

	public String getBackDeptId() {
		return this.backDeptId;
	}

	public void setBackDeptId(String backDeptId) {
		this.backDeptId = backDeptId;
	}

	public String getBackDeptName() {
		return this.backDeptName;
	}

	public void setBackDeptName(String backDeptName) {
		this.backDeptName = backDeptName;
	}

	public String getBackJuId() {
		return this.backJuId;
	}

	public void setBackJuId(String backJuId) {
		this.backJuId = backJuId;
	}

	public String getBackJuName() {
		return this.backJuName;
	}

	public void setBackJuName(String backJuName) {
		this.backJuName = backJuName;
	}

	public String getBackTime() {
		return this.backTime;
	}

	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	public String getBackUserId() {
		return this.backUserId;
	}

	public void setBackUserId(String backUserId) {
		this.backUserId = backUserId;
	}

	public String getBackUserName() {
		return this.backUserName;
	}

	public void setBackUserName(String backUserName) {
		this.backUserName = backUserName;
	}

	public String getNoticeId() {
		return this.noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public Boolean getHasAffix() {
		return this.hasAffix;
	}

	public void setHasAffix(Boolean hasAffix) {
		this.hasAffix = hasAffix;
	}

}