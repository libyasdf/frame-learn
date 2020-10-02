package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * TODO 出勤导入表
 * @author 冯超
 * @Date 2018年5月30日 上午10:12:42
 */
@Entity
@Table(name = "kqgl_attendance")
public class KqAttendance {
	
	/*主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/*创建人id*/
	@Column(name = "cre_user_id")
	private String creUserId;
	
	/*创建人名*/
	@Column(name = "cre_user_name")
	private String creUserName;
	
	/*创建人部门id*/
	@Column(name = "cre_dept_id")
	private String creDeptId;
	
	/*创建人部门名*/
	@Column(name = "cre_dept_name")
	private String creDeptName;
	
	/*处室id*/
	@Column(name = "cre_chushi_id")
	private String creChuShiId;
	
	/*处室名称*/
	@Column(name = "cre_chushi_name")
	private String creChuShiName;
	
	/*二级局id*/
	@Column(name = "cre_ju_id")
	private String creJuId;
	
	/*二级局名*/
	@Column(name = "cre_ju_name")
	private String creJuName;
	
	/*逻辑删除*/
	@Column(name = "visible")
	private String visible;
	
	/*创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	
	/*导入ID*/
	@Column(name = "import_id")
	private String importId;
	
	/*日期*/
	@Column(name = "import_date")
	private String importDate;
	
	/*闸机号*/
	@Column(name = "gate_number")
	private String gateNumber;
	
	/*刷卡时间*/
	@Column(name = "care_time")
	private String careTime;
	
	/*卡号*/
	@Column(name = "card_number")
	private String cardNumber;
	
	/* 最后更新时间 */
	@Column(name = "update_time")
	private String updateTime;

	/* 最后更新人ID */
	@Column(name = "update_user_id")
	private String updateUserId;

	/* 最后更新人名 */
	@Column(name = "update_user_name")
	private String updateUserName;
	
	/* 闸机名字 */
	@Transient
	private String gateName;
	
	/* 改考勤记录的人名 */
	@Transient
	private String name;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreUserName() {
		return creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	public String getCreDeptId() {
		return creDeptId;
	}

	public void setCreDeptId(String creDeptId) {
		this.creDeptId = creDeptId;
	}

	public String getCreDeptName() {
		return creDeptName;
	}

	public void setCreDeptName(String creDeptName) {
		this.creDeptName = creDeptName;
	}

	public String getCreChuShiId() {
		return creChuShiId;
	}

	public void setCreChuShiId(String creChuShiId) {
		this.creChuShiId = creChuShiId;
	}

	public String getCreChuShiName() {
		return creChuShiName;
	}

	public void setCreChuShiName(String creChuShiName) {
		this.creChuShiName = creChuShiName;
	}

	public String getCreJuId() {
		return creJuId;
	}

	public void setCreJuId(String creJuId) {
		this.creJuId = creJuId;
	}

	public String getCreJuName() {
		return creJuName;
	}

	public void setCreJuName(String creJuName) {
		this.creJuName = creJuName;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}


	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public String getGateNumber() {
		return gateNumber;
	}

	public void setGateNumber(String gateNumber) {
		this.gateNumber = gateNumber;
	}

	public String getCareTime() {
		return careTime;
	}

	public void setCareTime(String careTime) {
		this.careTime = careTime;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getGateName() {
		return gateName;
	}

	public void setGateName(String gateName) {
		this.gateName = gateName;
	}


}
