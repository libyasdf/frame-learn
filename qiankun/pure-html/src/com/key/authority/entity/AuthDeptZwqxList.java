package com.key.authority.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * 信息发布范围-部门+职务权限详情表
 * TODO 
 * @author 李利广
 * @Date 2018年9月15日 下午5:46:26
 */
@Entity
@Table(name="T_AUTH_DEPT_ZWQX_LIST")
public class AuthDeptZwqxList  {

	@Id
	@Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="DWSURVEY_ID")
	private String surveyId;

	@Column(name="CRE_TIME")
	private String creTime;

	@Column(name="CRE_USER_ID")
	private String creUserId;

	@Column(name="CRE_USER_NAME")
	private String creUserName;

	@Column(name="visible")
	private String visible;
	
	@Transient
	private String line;

	public AuthDeptZwqxList() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
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

	public String getVisible() {
		return this.visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}