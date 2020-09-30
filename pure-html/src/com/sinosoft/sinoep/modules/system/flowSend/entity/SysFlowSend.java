package com.sinosoft.sinoep.modules.system.flowSend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 待阅表实体类
 * @author 李利广
 * @Date 2018年8月16日 下午7:21:40
 */
@Entity
@Table(name = "sys_flow_send")
public class SysFlowSend {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/** 发送人ID */
	@Column(name = "send_user_id")
	private String sendUserId;
	
	/** 发送人name */
	@Column(name = "send_user_name")
	private String sendUserName;
	
	/** 发送人部门ID */
	@Column(name = "send_dept_id")
	private String sendDeptId;
	
	/** 发送人部门name */
	@Column(name = "send_dept_name")
	private String sendDeptName;
	
	/** 发送人处室ID */
	@Column(name = "send_chushi_id")
	private String sendChuShiId;
	
	/** 发送人处室name */
	@Column(name = "send_chushi_name")
	private String sendChuShiName;
	
	/** 发送人二级局ID */
	@Column(name = "send_ju_id")
	private String sendJuId;
	
	/** 发送人二级局name */
	@Column(name = "send_ju_name")
	private String sendJuName;
	
	/** 发送时间 */
	@Column(name = "send_time")
	private String sendTime;
	
	/** 状态（0待阅；1已阅） */
	@Column(name = "status")
	private String status;
	
	/** 接收人ID */
	@Column(name = "receive_user_id")
	private String receiveUserId;
	
	/** 接收人name */
	@Column(name = "receive_user_name")
	private String receiveUserName;
	
	/** 接收人部门ID */
	@Column(name = "receive_dept_id")
	private String receiveDeptId;
	
	/** 接收人部门name */
	@Column(name = "receive_dept_name")
	private String receiveDeptName;
	
	/** 接收人业务角色编号 */
	@Column(name = "receive_role_no")
	private String receiveRoleNo;
	
	/** 流程ID（非流程可空） */
	@Column(name = "workflow_id")
	private String workflowId;
	
	/** 流程名称(非流程就传非流程的op_name) */
	@Column(name = "workflow_name")
	private String workflowName;
	
	/** 待阅标题 */
	@Column(name = "title")
	private String title;
	
	/** 业务数据ID */
	@Column(name = "record_id")
	private String recordId;
	
	/** 待阅URL */
	@Column(name = "send_url")
	private String sendUrl;
	
	/** 是否需要意见（0不需要；1需要） */
	@Column(name = "is_idea")
	private String isIdea;
	
	/** 意见 */
	@Column(name = "idea")
	private String idea;
	
	/** 阅毕时间 */
	@Column(name = "read_time")
	private String readTime;
	
	/** 系统ID */
	@Column(name = "sys_id")
	private String sysId;
	
	/** 组织体系ID */
	@Column(name = "org_id")
	private String orgId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getSendDeptId() {
		return sendDeptId;
	}

	public void setSendDeptId(String sendDeptId) {
		this.sendDeptId = sendDeptId;
	}

	public String getSendDeptName() {
		return sendDeptName;
	}

	public void setSendDeptName(String sendDeptName) {
		this.sendDeptName = sendDeptName;
	}

	public String getSendChuShiId() {
		return sendChuShiId;
	}

	public void setSendChuShiId(String sendChuShiId) {
		this.sendChuShiId = sendChuShiId;
	}

	public String getSendChuShiName() {
		return sendChuShiName;
	}

	public void setSendChuShiName(String sendChuShiName) {
		this.sendChuShiName = sendChuShiName;
	}

	public String getSendJuId() {
		return sendJuId;
	}

	public void setSendJuId(String sendJuId) {
		this.sendJuId = sendJuId;
	}

	public String getSendJuName() {
		return sendJuName;
	}

	public void setSendJuName(String sendJuName) {
		this.sendJuName = sendJuName;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public String getIsIdea() {
		return isIdea;
	}

	public void setIsIdea(String isIdea) {
		this.isIdea = isIdea;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
