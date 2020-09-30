package com.sinosoft.sinoep.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 已办表实体类
 * @author 李利广
 * @Date 2018年3月15日 下午9:18:34
 */
@Entity
@Table(name = "FLOW_READ",schema = "epcloud")
public class FlowRead {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/**
	 * 标题
	 */
	@Column(name = "TITLE")
	private String title;
	
	/**
	 * 流程类型ID
	 */
	@Column(name = "FILETYPEID")
	private String fileTypeId;
	
	/**
	 * 流程类型名
	 */
	@Column(name = "FILETYPENAME")
	private String fileTypeName;
	
	/**
	 * 流程ID
	 */
	@Column(name = "WORKFLOWID")
	private String workFlowId;
	
	/**
	 * 流程名
	 */
	@Column(name = "WORKFLOWNAME")
	private String workFlowName;
	
	/**
	 * 流转ID(默认与DOCID业务数据ID一致)
	 */
	@Column(name = "RECORDID")
	private String recordId;
	
	/**
	 * 启动时间(进入流转的时间)
	 */
	@Column(name = "CREATETIME")
	private String createTime;
	
	/**
	 * 办理人ID
	 */
	@Column(name = "USERID")
	private String userId;
	
	/**
	 * 办理人所在部门ID
	 */
	@Column(name = "DEPTID")
	private String deptId;
	
	/**
	 * 办理人所在组织体系ID
	 */
	@Column(name = "organiseid")
	private String oraniseId;
	
	/**
	 * 办理时间
	 */
	@Column(name = "readtime")
	private String readTime;
	
	/**
	 *办理标识
	 */
	@Column(name = "readflag")
	private String readFlag;
	
	/**
	 * 全局流转唯一ID(不会是子流程而改变)
	 */
	@Column(name = "uniqueid")
	private String uniqueId;
	
	/**
	 * 业务属性值得集合，格式：“变量名=变量值,变量名=变量值”
	 */
	@Column(name = "para")
	private String para;
	
	/**
	 * 拟稿人所在部门ID
	 */
	@Column(name = "draftdept")
	private String draftDept;
	
	/**
	 * 拟稿人ID
	 */
	@Column(name = "draftuser")
	private String draftUser;
	
	/**
	 * 状态标识
	 */
	@Column(name = "STATTAG")
	private String statTag;
	
	/**
	 * 已办URL
	 */
	@Column(name = "handdoneurl")
	private String handDoneUrl;
	
	/**
	 * 业务记录主键ID
	 */
	@Column(name = "docid")
	private String docId;
	
	/**
	 * 表单ID
	 */
	@Column(name = "formUrl")
	private String formUrl;
	
	/**
	 * 流程分类ID(一般与uniqueId一致)
	 */
	@Column(name = "sortId")
	private String sortId;
	
	/**
	 * 父流程类型ID
	 */
	@Column(name = "superFileType")
	private String superFileType;
	
	/**
	 * 日志ID
	 */
	@Column(name = "logId")
	private String logId;
	
	/**
	 * 备用字段1
	 */
	@Column(name = "backup")
	private String backup;
	
	/**
	 * 备用字段2
	 */
	@Column(name = "backup1")
	private String backup1;
	
	/**
	 * 业务扩展属性
	 */
	@Column(name = "attr")
	private String attr;
	
	/**
	 * 业务扩展属性
	 */
	@Column(name = "attr1")
	private String attr1;
	
	/**
	 * 业务扩展属性
	 */
	@Column(name = "attr2")
	private String attr2;
	
	/**
	 * 父流程ID
	 */
	@Column(name = "superWorkFlow")
	private String superWorkFlow;
	
	/**
	 * 系统ID
	 */
	@Column(name = "SYS_ID")
	private String sysId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	public String getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getOraniseId() {
		return oraniseId;
	}

	public void setOraniseId(String oraniseId) {
		this.oraniseId = oraniseId;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getDraftDept() {
		return draftDept;
	}

	public void setDraftDept(String draftDept) {
		this.draftDept = draftDept;
	}

	public String getDraftUser() {
		return draftUser;
	}

	public void setDraftUser(String draftUser) {
		this.draftUser = draftUser;
	}

	public String getStatTag() {
		return statTag;
	}

	public void setStatTag(String statTag) {
		this.statTag = statTag;
	}

	public String getHandDoneUrl() {
		return handDoneUrl;
	}

	public void setHandDoneUrl(String handDoneUrl) {
		this.handDoneUrl = handDoneUrl;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getSuperFileType() {
		return superFileType;
	}

	public void setSuperFileType(String superFileType) {
		this.superFileType = superFileType;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getBackup() {
		return backup;
	}

	public void setBackup(String backup) {
		this.backup = backup;
	}

	public String getBackup1() {
		return backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}

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

	public String getSuperWorkFlow() {
		return superWorkFlow;
	}

	public void setSuperWorkFlow(String superWorkFlow) {
		this.superWorkFlow = superWorkFlow;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

}
