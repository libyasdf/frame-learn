package com.sinosoft.sinoep.modules.system.config.honor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 工资的实体类
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月28日 上午11:33:56
 */
@Entity
@Table(name = "SYS_HONOR")
public class Honor {
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
	/*逻辑删除*/
	@Column(name = "visible")
	private String visible;
	/*创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	/*处室id*/
	@Column(name = "CRE_CHUSHI_ID")
	private String creChushiId;
	/*处室名称*/
	@Column(name = "CRE_CHUSHI_NAME")
	private String creChushiName;
	/*二级局id*/
	@Column(name = "CRE_JU_ID")
	private String creJuId;
	/*二级局名*/
	@Column(name = "CRE_JU_NAME")
	private String creJuName;
	/*年度*/
	@Column(name = "year")
	private String year;
	
	/*更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	/*更新人姓名*/
	@Column(name = "update_user_name")
	private String updateUserName;
	
	@Column(name = "update_time")
	private String updateTime;

	/* 是否发布*/
	@Column(name = "is_publish")
	private String isPublish;

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

}
