package com.sinosoft.sinoep.modules.consultManage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "OA_CONSULT_MANAGE")
public class ConsultManage {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", length = 32)
	private String id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 公文类型
	 */
	private String doctype;
	/**
	 * 文件原号
	 */
	private String outCode;
	/**
	 * 网络号
	 */
	private String netCode;
	/**
	 * 办阅标识(01.办件 02.阅件)
	 */
	private String readingMark;
	/**
	 * 流水号
	 */
	private String serialNumber;
	/**
	 * 来文单位
	 */
	private String receivedUnit;
	/**
	 * 来文单位ID
	 */
	private String receivedUnitId;
	/**
	 * 文件归类
	 */
	private String fileClass;
	/**
	 * 登记人
	 */
	private String registrant;
	/**
	 * 登记人ID
	 */
	private String registrantId;
	/**
	 * 紧急程度
	 */
	private String needGrade;
	/**
	 * 清退类型(01.待清退 02.不清退)
	 */
	private String returnType;
	/**
	 * 归档类型
	 */
	private String pigeonholeType;
	/**
	 * 文种
	 */
	private String archiveType;
	/**
	 * 查询级别
	 */
	private String queryLevel;
	/**
	 * 文件密级
	 */
	private String secGrade;
	/**
	 * 创建日期
	 */
	private String createDate;
	/**
	 * 成文日期
	 */
	private String writtenDate;
	/**
	 * 收文日期
	 */
	private String receiptDate;
	/**
	 * 转办单位
	 */
	private String transferUnit;
	/**
	 * 转办单位ID
	 */
	private String transferUnitId;
	/**
	 * 转办日期
	 */
	private String transferDate;
	/**
	 * 办理期限
	 */
	private String handlelimit;
	/**
	 * 返回时间
	 */
	private String returnTime;
	/**
	 * 份数
	 */
	private String copies;
	/**
	 * 页数
	 */
	private String pages;
	/**
	 * 处室
	 */
	private String office;
	/**
	 * 处室流水号
	 */
	private String officeNumber;

	/**
	 * 批示领导id
	 */
	private String instructionId;
	/**
	 * 批示领导名称
	 */
	private String instructionName;
	/**
	 * 主办单位id
	 */
	private String sponsorId;
	/**
	 * 主办单位名称
	 */
	private String sponsorName;

	/**
	 * 备注
	 */
	private String note;
	/**
	 * 组织体系ID
	 */
	private String orgid;
	/**
	 * 系统ID
	 */
	private String sysid;
	/**
	 * 状态(0.撤办1.流转中2.挂起3.归档4.办结5.拟稿)
	 */
	private String subflag;
	/**
	 * 流程ID
	 */
	private String workflowid;
	
	/**
	 * 意见
	 */
	private String idea;

	@Transient
	private String attr1;
	@Transient
	private String attr2;
	@Transient
	private String attr3;
	@Transient
	private String attr4;
	@Transient
	private String attr5;

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

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	public String getReadingMark() {
		return readingMark;
	}

	public void setReadingMark(String readingMark) {
		this.readingMark = readingMark;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getReceivedUnit() {
		return receivedUnit;
	}

	public void setReceivedUnit(String receivedUnit) {
		this.receivedUnit = receivedUnit;
	}

	public String getReceivedUnitId() {
		return receivedUnitId;
	}

	public void setReceivedUnitId(String receivedUnitId) {
		this.receivedUnitId = receivedUnitId;
	}

	public String getFileClass() {
		return fileClass;
	}

	public void setFileClass(String fileClass) {
		this.fileClass = fileClass;
	}

	public String getRegistrant() {
		return registrant;
	}

	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}

	public String getRegistrantId() {
		return registrantId;
	}

	public void setRegistrantId(String registrantId) {
		this.registrantId = registrantId;
	}

	public String getNeedGrade() {
		return needGrade;
	}

	public void setNeedGrade(String needGrade) {
		this.needGrade = needGrade;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getPigeonholeType() {
		return pigeonholeType;
	}

	public void setPigeonholeType(String pigeonholeType) {
		this.pigeonholeType = pigeonholeType;
	}

	public String getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(String archiveType) {
		this.archiveType = archiveType;
	}

	public String getQueryLevel() {
		return queryLevel;
	}

	public void setQueryLevel(String queryLevel) {
		this.queryLevel = queryLevel;
	}

	public String getSecGrade() {
		return secGrade;
	}

	public void setSecGrade(String secGrade) {
		this.secGrade = secGrade;
	}

	public String getWrittenDate() {
		return writtenDate;
	}

	public void setWrittenDate(String writtenDate) {
		this.writtenDate = writtenDate;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getTransferUnit() {
		return transferUnit;
	}

	public void setTransferUnit(String transferUnit) {
		this.transferUnit = transferUnit;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getHandlelimit() {
		return handlelimit;
	}

	public void setHandlelimit(String handlelimit) {
		this.handlelimit = handlelimit;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getCopies() {
		return copies;
	}

	public void setCopies(String copies) {
		this.copies = copies;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getOfficeNumber() {
		return officeNumber;
	}

	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTransferUnitId() {
		return transferUnitId;
	}

	public void setTransferUnitId(String transferUnitId) {
		this.transferUnitId = transferUnitId;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
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

	public String getAttr3() {
		return attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getWorkflowid() {
		return workflowid;
	}

	public void setWorkflowid(String workflowid) {
		this.workflowid = workflowid;
	}

	public String getInstructionId() {
		return instructionId;
	}

	public void setInstructionId(String instructionId) {
		this.instructionId = instructionId;
	}

	public String getInstructionName() {
		return instructionName;
	}

	public void setInstructionName(String instructionName) {
		this.instructionName = instructionName;
	}

	public String getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getNetCode() {
		return netCode;
	}

	public void setNetCode(String netCode) {
		this.netCode = netCode;
	}

	public String getAttr4() {
		return attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	public String getAttr5() {
		return attr5;
	}

	public void setAttr5(String attr5) {
		this.attr5 = attr5;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

}
