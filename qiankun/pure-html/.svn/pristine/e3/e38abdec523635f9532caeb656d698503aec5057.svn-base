package com.sinosoft.sinoep.modules.info.authority.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * TODO 信息发布范围-部门+职务权限 表
 * @author 李利广
 * @Date 2018年9月15日 下午5:43:23
 */
@Entity
@Table(name="INFO_FB_DEPT_ZWQX")
public class InfoFbDeptZwqx  {

	@Id
	@Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="CONTENT_ID")
	private String contentId;

	@Column(name="CRE_TIME")
	private String creTime;

	@Column(name="CRE_USER_ID")
	private String creUserId;

	@Column(name="CRE_USER_NAME")
	private String creUserName;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="DEPT_NAME")
	private String deptName;

	@Column(name="DEPT_ZWQX_LIST_ID")
	private String deptZwqxListId;

	@Column(name="POSITION_ID")
	private String positionId;

	@Column(name="POSITION_NAME")
	private String positionName;

	@Column(name="visible")
	private String visible;
	
	@Transient
	private String line;

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public InfoFbDeptZwqx() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
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

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptZwqxListId() {
		return this.deptZwqxListId;
	}

	public void setDeptZwqxListId(String deptZwqxListId) {
		this.deptZwqxListId = deptZwqxListId;
	}

	public String getPositionId() {
		return this.positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getVisible() {
		return this.visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

}