package com.sinosoft.sinoep.modules.system.notice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 通知通告实体类
 * @author 李利广
 * @Date 2018年8月17日 下午4:12:52
 */
@Entity
@Table(name="SYS_NOTICE")
public class SysNotice {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	/** 内容 */
	@Column(name="CONTENT")
	private String content;

	/** 创建人处室ID */
	@Column(name="CRE_CHUSHI_ID")
	private String creChushiId;

	/** 创建人处室name */
	@Column(name="CRE_CHUSHI_NAME")
	private String creChushiName;

	/** 创建人部门ID */
	@Column(name="CRE_DEPT_ID")
	private String creDeptId;

	/** 创建人部门name */
	@Column(name="CRE_DEPT_NAME")
	private String creDeptName;

	/** 创建人局ID */
	@Column(name="CRE_JU_ID")
	private String creJuId;

	/** 创建人局name */
	@Column(name="CRE_JU_NAME")
	private String creJuName;

	/** 创建时间 */
	@Column(name="CRE_TIME")
	private String creTime;

	/** 创建人ID */
	@Column(name="CRE_USER_ID")
	private String creUserId;

	/** 创建人name */
	@Column(name="CRE_USER_NAME")
	private String creUserName;

	/** 通知结束时间 */
	@Column(name="END_TIME")
	private String endTime;

	/** 状态（0草稿；1流程中；2撤销;5发布;6未通过） */
	@Column(name="SUBFLAG")
	private String subflag;
	
	/** 是否需要反馈（0不需要；1需要） */
	@Column(name="IS_BACK")
	private String isBack;

	/** 是否置顶（0不置顶，默认；1置顶） */
	@Column(name="IS_TOP")
	private String isTop;

	/** 排序号 */
	@Column(name="ORDER_NO")
	private String orderNo;

	/** 通知发布时间 */
	@Column(name="START_TIME")
	private String startTime;

	/** 标题 */
	@Column(name="TITLE")
	private String title;

	/** 通知类型 */
	@Column(name="NOTICE_TYPE")
	private String noticeType;
	
	/** 是否已选择发布范围（0未选，1已选） */
	@Column(name="STATUS")
	private String status;

	/** 最近修改时间 */
	@Column(name="UPDATE_TIME")
	private String updateTime;

	/** 最近修改人ID */
	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	/** 最近修改人name */
	@Column(name="UPDATE_USER_NAME")
	private String updateUserName;

	/** 删除状态（0删除；1可用） */
	@Column(name="VISIBLE")
	private String visible;
	
	/** 发布时间 */
	@Column(name="PUBLISH_TIME")
	private String publishTime;
	
	/** 通知来源 */
	@Column(name="source")
	private String source;
	
	/** 操作 */
	@Transient
	private String cz;

	@Column(name="NOTICE_DEPTID")
	private String noticeDeptId;

	@Column(name="NOTICETITLE")
	private String noticeTitle;

	public SysNotice() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreChushiId() {
		return this.creChushiId;
	}

	public void setCreChushiId(String creChushiId) {
		this.creChushiId = creChushiId;
	}

	public String getCreChushiName() {
		return this.creChushiName;
	}

	public void setCreChushiName(String creChushiName) {
		this.creChushiName = creChushiName;
	}

	public String getCreDeptId() {
		return this.creDeptId;
	}

	public void setCreDeptId(String creDeptId) {
		this.creDeptId = creDeptId;
	}

	public String getCreDeptName() {
		return this.creDeptName;
	}

	public void setCreDeptName(String creDeptName) {
		this.creDeptName = creDeptName;
	}

	public String getCreJuId() {
		return this.creJuId;
	}

	public void setCreJuId(String creJuId) {
		this.creJuId = creJuId;
	}

	public String getCreJuName() {
		return this.creJuName;
	}

	public void setCreJuName(String creJuName) {
		this.creJuName = creJuName;
	}

	public String getCreTime() {
		return this.creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getCreUserId() {
		return this.creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreUserName() {
		return this.creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsTop() {
		return this.isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	
	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNoticeDeptId() {
		return noticeDeptId;
	}

	public void setNoticeDeptId(String noticeDeptId) {
		this.noticeDeptId = noticeDeptId;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}
}