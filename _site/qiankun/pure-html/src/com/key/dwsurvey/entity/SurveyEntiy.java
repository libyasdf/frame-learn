package com.key.dwsurvey.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * TODO 问卷调查实体类
 * @author 杜建松
 * @Date 2018年8月17日 下午4:12:52
 */
@Entity
@Table(name="SURVEY_ENTIY")
public class SurveyEntiy {

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

	/** 状态（0草稿；1流程中；2撤销;5发布;6未通过） */
	@Column(name="SUBFLAG")
	private String subflag;

	/** 标题 */
	@Column(name="TITLE")
	private String title;

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

	/** 操作 */
	@Transient
	private String cz;

	@Column(name="WENJUAN_DEPTID")
	private String wenjuanDeptId;

	@Column(name="WENJUANTITLE")
	private String wenjuanTitle;

	@Column(name="SURVEYID")
	private String surveyId;


	public SurveyEntiy() {
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

	
	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}


	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}

	public String getWenjuanDeptId() {
		return wenjuanDeptId;
	}

	public void setWenjuanDeptId(String wenjuanDeptId) {
		this.wenjuanDeptId = wenjuanDeptId;
	}

	public String getWenjuanTitle() {
		return wenjuanTitle;
	}

	public void setWenjuanTitle(String wenjuanTitle) {
		this.wenjuanTitle = wenjuanTitle;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
}