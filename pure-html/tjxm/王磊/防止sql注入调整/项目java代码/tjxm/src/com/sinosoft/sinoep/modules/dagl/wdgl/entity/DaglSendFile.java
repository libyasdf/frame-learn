package com.sinosoft.sinoep.modules.dagl.wdgl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 发文实体类
 * @author 李颖洁  
 * @date 2018年11月10日  下午5:36:33
 */
@Entity
@Table(name = "DAGL_SEND_FILE")
public class DaglSendFile {
	
	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", length = 50)
	private String id;
	
	/** 创建人姓名 */
	@Column(name = "CRE_USER_NAME", length = 50)
	private String creUserName;
	
	/** 创建人id */
	@Column(name = "CRE_USER_ID", length = 50)
	private String creUserId;
	
	/** 创建人部门名称 */
	@Column(name = "CRE_DEPT_NAME", length = 50)
	private String creDeptName;
	
	/** 创建人部门id */
	@Column(name = "CRE_DEPT_ID", length = 50)
	private String creDeptId;
	
	/** 创建人二级局ID */
	@Column(name = "CRE_JU_ID", length = 50)
	private String creJuId;
	
	/** 创建人处室ID */
	@Column(name="CRE_CHUSHI_ID", length = 50)
	private String creChushiId;
	
	/** 创建人处室名 */
	@Column(name="CRE_CHUSHI_NAME", length = 50)
	private String creChushiName;
	
	/** 创建人二级局名 */
	@Column(name = "CRE_JU_NAME", length = 50)
	private String creJuName;
	
	/** 创建时间 */
	@Column(name = "CRE_TIME", length = 30)
	private String creTime;
	
	/** 逻辑删除 0：已“删除；1：未删除 */
	@Column(name = "VISIBLE", length = 1)
	private String visible;
	
	/** 文件标题 */
	@Column(name = "TITLE", length = 255)
	private String title;
	
	/** 文号 */
	@Column(name = "FILE_NUM", length = 100)
	private String fileNum;
	
	/** 页数 */
	@Column(name = "PAGE_NUM", length = 30)
	private Integer pageNum;
	
	/** 来文单位 */
	@Column(name = "FILE_DEPT", length = 200)
	private String fileDept;
	
	/** 密级 */
	@Column(name = "SECURITY_CLASS", length = 4)
	private String securityClass;
	
	/** 文件形成时间 */
	@Column(name = "CREATED_DATE", length = 50)
	private String createdDate;
	
	/** 状态 0：待归档；1：已归档； */
	@Column(name = "STATE", length = 4)
	private String state;
	
	/** 备注*/
	@Column(name = "NOTE", length = 500)
	private String note;

	/** 文件类型 0：需归档；1：留存；2：销毁 */
	@Column(name = "TYPE", length = 4)
	private String type;
	
	/** 收文编号 */
	@Column(name = "SERIAL_NUM", length = 50)
	private String serialNum;
	
	/** 收文日期 */
	@Column(name = "FILE_TIME", length = 30)
	private String fileTime;
	
	/** 份数 */
	@Column(name = "QUANTITY", length = 30)
	private Integer quantity;
	
	/** 条码编号 */
	@Column(name = "BARCODE", length = 30)
	private String barcode;
	
	/** 文种 */
	@Column(name = "FILE_TYPE", length = 10)
	private String fileType;
	
	/** 主送单位 */
	@Column(name = "ZHUS_UNIT", length = 100)
	private String zhusUnit;
	
	/** 紧急程度 */
	@Column(name = "URGENCY_DEGREE", length = 10)
	private String urgencyDegree;
	
	/** 经办单位 */
	@Column(name = "HANDLE_UNIT", length = 50)
	private String handleUnit;
	
	/** 经手人 */
	@Column(name = "RECEIPIENT", length = 50)
	private String receipient;
	
	/** 成文日期范围 */
	@Transient
	private String timeRange;
	
	public DaglSendFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param title
	 * @param fileNum
	 * @param pageNum
	 * @param fileDept
	 * @param securityClass
	 * @param createdDate
	 * @param state
	 * @param note
	 * @param type
	 * @param serialNum
	 * @param fileTime
	 * @param quantity
	 * @param barcode
	 * @param fileType
	 * @param zhusUnit
	 * @param urgencyDegree
	 * @param handleUnit
	 * @param receipient
	 */
	public DaglSendFile(String id, String title, String fileNum, Integer pageNum, String fileDept, String securityClass,
			String createdDate, String state, String note, String type, String serialNum, String fileTime,
			Integer quantity, String barcode, String fileType, String zhusUnit, String urgencyDegree, String handleUnit,
			String receipient) {
		super();
		this.id = id;
		this.title = title;
		this.fileNum = fileNum;
		this.pageNum = pageNum;
		this.fileDept = fileDept;
		this.securityClass = securityClass;
		this.createdDate = createdDate;
		this.state = state;
		this.note = note;
		this.type = type;
		this.serialNum = serialNum;
		this.fileTime = fileTime;
		this.quantity = quantity;
		this.barcode = barcode;
		this.fileType = fileType;
		this.zhusUnit = zhusUnit;
		this.urgencyDegree = urgencyDegree;
		this.handleUnit = handleUnit;
		this.receipient = receipient;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreUserName() {
		return creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	public String getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreDeptName() {
		return creDeptName;
	}

	public void setCreDeptName(String creDeptName) {
		this.creDeptName = creDeptName;
	}

	public String getCreDeptId() {
		return creDeptId;
	}

	public void setCreDeptId(String creDeptId) {
		this.creDeptId = creDeptId;
	}

	public String getCreJuId() {
		return creJuId;
	}

	public void setCreJuId(String creJuId) {
		this.creJuId = creJuId;
	}

	public String getCreChushiId() {
		return creChushiId;
	}

	public void setCreChushiId(String creChushiId) {
		this.creChushiId = creChushiId;
	}

	public String getCreChushiName() {
		return creChushiName;
	}

	public void setCreChushiName(String creChushiName) {
		this.creChushiName = creChushiName;
	}

	public String getCreJuName() {
		return creJuName;
	}

	public void setCreJuName(String creJuName) {
		this.creJuName = creJuName;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getFileDept() {
		return fileDept;
	}

	public void setFileDept(String fileDept) {
		this.fileDept = fileDept;
	}

	public String getSecurityClass() {
		return securityClass;
	}

	public void setSecurityClass(String securityClass) {
		this.securityClass = securityClass;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getFileTime() {
		return fileTime;
	}

	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getZhusUnit() {
		return zhusUnit;
	}

	public void setZhusUnit(String zhusUnit) {
		this.zhusUnit = zhusUnit;
	}

	public String getUrgencyDegree() {
		return urgencyDegree;
	}

	public void setUrgencyDegree(String urgencyDegree) {
		this.urgencyDegree = urgencyDegree;
	}

	public String getHandleUnit() {
		return handleUnit;
	}

	public void setHandleUnit(String handleUnit) {
		this.handleUnit = handleUnit;
	}

	public String getReceipient() {
		return receipient;
	}

	public void setReceipient(String receipient) {
		this.receipient = receipient;
	}

	public String getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}
	
	
	
	
}
