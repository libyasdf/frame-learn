package com.sinosoft.sinoep.modules.system.config.holidayset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 节假日设置的实体类
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月10日 下午5:51:45
 */
@Entity
@Table(name = "KQGL_HOLIDAY_SET_INFO")
public class HolidaySet {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "YEAR_MONTH")
	private String yearMonth;

	@Column(name = "NOW_DATE")
	private String nowDate;

	@Column(name = "CRE_TIME")
	private String creTime;

	@Column(name = "CRE_USER_ID")
	private String creUserId;

	@Column(name = "CRE_USER_NAME")
	private String creUserName;

	@Column(name = "CRE_DEPT_ID")
	private String creDeptId;

	@Column(name = "CRE_DEPT_NAME")
	private String creDeptName;

	@Column(name = "CRE_CHUSHI_ID")
	private String creChuShiId;

	@Column(name = "CRE_CHUSHI_NAME")
	private String creChuShiName;

	@Column(name = "CRE_JU_ID")
	private String creJuId;

	@Column(name = "CRE_JU_NAME")
	private String creJuName;

	@Column(name = "UPDATE_USER_ID")
	private String updateUserId;

	@Column(name = "UPDATE_USER_NAME")
	private String updateUserName;

	@Column(name = "UPDATE_TIME")
	private String updateTime;

	@Column(name = "VISIBLE")
	private String visible;

	@Column(name = "IS_HOLIDAY")
	private String isHoliday;
	
	

	public HolidaySet() {
		super();
	}

	/**
	 * 为hql连表查询，创建有参构造
	 * 
	 * @param id
	 * @param title
	 * @param subflag
	 * @param businessTripTitle
	 * @param applicationUnitId
	 * @param applicationUnitName
	 * @param applicationTime
	 * @param startTime
	 * @param endTime
	 * @param destination
	 * @param vehicle
	 * @param isHaveReceptionFees
	 * @param receptionFees
	 * @param longTime
	 * @param reason
	 * @param creTime
	 * @param creUserId
	 * @param creUserName
	 * @param creDeptId
	 * @param creDeptName
	 * @param creChuShiId
	 * @param creChuShiName
	 * @param creJuId
	 * @param creJuName
	 * @param updateUserId
	 * @param updateUserName
	 * @param updateTime
	 * @param visible
	 * @param workitemid
	 * @param cz
	 * @param yibanid
	 */
	public HolidaySet(String id,String yearMonth, String nowDate, String creTime, String creUserId, String creUserName, String creDeptId,
			String creDeptName, String creChuShiId, String creChuShiName, String creJuId, String creJuName,
			String updateUserId, String updateUserName, String updateTime, String visible, String isHoliday) 
	{
		super();
		this.id = id;
		this.yearMonth = yearMonth;
		this.nowDate = nowDate;
		this.creTime = creTime;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.creChuShiId = creChuShiId;
		this.creChuShiName = creChuShiName;
		this.creJuId = creJuId;
		this.creJuName = creJuName;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.visible = visible;
		this.isHoliday = isHoliday;
	}
	// t.id, t.title, t.creUserId, t.subflag, t.startTime,
	// t.applicationUnitName,"
	// + "t.longTime, t.destination"


	public HolidaySet(String id, String isHoliday) {
		super();
		this.id = id;
		this.isHoliday = isHoliday;
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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
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

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getIsHoliday() {
		return isHoliday;
	}

	public void setIsHoliday(String isHoliday) {
		this.isHoliday = isHoliday;
	}

}
