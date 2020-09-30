package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * 年休假规则设置的实体类
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月20日 下午3:53:41
 */
@Entity
@Table(name = "kqgl_annual_leave_regular")
public class LeaveRegular {
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
	/*创建人处室id*/
	@Column(name = "cre_chushi_id")
	private String creChushiId;
	/*创建人处室名*/
	@Column(name = "cre_chushi_name")
	private String creChushiName;
	/*创建人二级局id*/
	@Column(name = "cre_ju_id")
	private String creJuId;
	/*创建人二级局名*/
	@Column(name = "cre_ju_name")
	private String creJuName;
	/*逻辑删除*/
	@Column(name = "visible")
	private String visible;
	/*创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	/*最后更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	/*最后更新人名*/
	@Column(name = "update_user_name")
	private String updateUserName;
	/*最后更新时间*/
	@Column(name = "update_time")
	private String updateTime;
	/*年限*/
	@Column(name = "age_limit")
	private String ageLimit;
	/*天数*/
	@Column(name = "day_number")
	private String dayNumber;
	/*操作*/
	@Transient
	private String cz = "";
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public String getId() {
		return id;
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
	public String getAgeLimit() {
		return ageLimit;
	}
	public void setAgeLimit(String ageLimit) {
		this.ageLimit = ageLimit;
	}
	public String getDayNumber() {
		if(dayNumber==null){
			return "";
		}
		return dayNumber;
	}
	public void setDayNumber(String dayNumber) {
		this.dayNumber = dayNumber;
	}
	

}
