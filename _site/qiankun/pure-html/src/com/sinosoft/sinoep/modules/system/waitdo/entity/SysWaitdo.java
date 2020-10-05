package com.sinosoft.sinoep.modules.system.waitdo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TODO 流程待办和不走流程待办视图实体类
 * @author 李利广
 * @Date 2018年5月24日 下午8:19:32
 */
@Entity
@Table(name = "sys_waitdo")
public class SysWaitdo {
	
	@Id
	@Column(name = "ID")
	private String workitemid;
	
	/** 接收人ID */
	@Column(name = "receive_user_id")
	private String receiveUserId;
	
	/** 接收人name */
	@Transient
	private String receiveUserName;
	
	/** 接收人部门ID */
	@Column(name = "receive_dept_id")
	private String receiveDeptId;
	
	/** 接收人部门name */
	@Transient
	private String receiveDeptName;
	
	/** 接收业务角色ID */
	@Column(name = "receive_role_no")
	private String receiveRoleNo;
	
	/** 接收时间 */
	@Column(name = "receive_time")
	private String receiveTime;
	
	/** 待办名称 */
	@Column(name = "op_name")
	private String workFlowName;
	
	/** 流程ID */
	@Column(name = "workflowid")
	private String workflowid;
	
	/** 流程类型ID */
	@Column(name = "filetypeid")
	private String fileTypeId;
	
	/** 待办标题 */
	@Column(name = "title")
	private String title;
	
	/** 待办URL */
	@Column(name = "waitdo_url")
	private String url;
	
	/** 业务ID */
	@Column(name = "source_id")
	private String recordid;
	
	/** 前一节点用户ID */
	@Column(name = "pre_user_id")
	private String preUserId;
	
	/** 前一节点用户name */
	@Column(name = "pre_user_name")
	private String preUserName;
	
	/** 待办类型（1流程；2不走流程） */
	@Column(name = "waitdo_type")
	private String waitdoType;
	
	/** 创建 时间  */
	@Column(name = "createtime")
	private String createTime;
	
	/** 创建人 */
	@Column(name = "draftusername")
	private String userName;

	@Column(name = "draftuser")
	private String draftuser;
	
	/** 备用字段1 */
	@Column(name = "attr")
	private String attr;
	
	/** 备用字段2 */
	@Column(name = "attr1")
	private String attr1;
	
	/** 备用字段3 */
	@Column(name = "attr2")
	private String attr2;
	
	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWorkitemid() {
		return workitemid;
	}

	public void setWorkitemid(String workitemid) {
		this.workitemid = workitemid;
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public String getReceiveDeptId() {
		return receiveDeptId;
	}

	public void setReceiveDeptId(String receiveDeptId) {
		this.receiveDeptId = receiveDeptId;
	}

	public String getReceiveDeptName() {
		return receiveDeptName;
	}

	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}

	public String getReceiveRoleNo() {
		return receiveRoleNo;
	}

	public void setReceiveRoleNo(String receiveRoleNo) {
		this.receiveRoleNo = receiveRoleNo;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRecordid() {
		return recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}

	public String getPreUserId() {
		return preUserId;
	}

	public void setPreUserId(String preUserId) {
		this.preUserId = preUserId;
	}

	public String getPreUserName() {
		return preUserName;
	}

	public void setPreUserName(String preUserName) {
		this.preUserName = preUserName;
	}

	public String getWaitdoType() {
		return waitdoType;
	}

	public void setWaitdoType(String waitdoType) {
		this.waitdoType = waitdoType;
	}

	public String getWorkflowid() {
		return workflowid;
	}

	public void setWorkflowid(String workflowid) {
		this.workflowid = workflowid;
	}

	public String getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public String getDraftuser() {
		return draftuser;
	}

	public void setDraftuser(String draftuser) {
		this.draftuser = draftuser;
	}
}
