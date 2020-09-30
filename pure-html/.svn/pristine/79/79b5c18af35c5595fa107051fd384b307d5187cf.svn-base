package com.sinosoft.sinoep.modules.info.authority.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * TODO 信息发布范围-群组表
 * @author 李利广
 * @Date 2018年9月15日 下午5:43:05
 */
@Entity
@Table(name="INFO_FB_GROUP")
public class InfoFbGroup {

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

	@Column(name="GROUP_ID")
	private String groupId;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name="VISIBLE")
	private String visible;

	public InfoFbGroup() {
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

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String deptId) {
		this.groupId = deptId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getVisible() {
		return this.visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

}