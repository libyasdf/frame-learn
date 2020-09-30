package com.sinosoft.sinoep.modules.system.config.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO 数据字典
 * @author 周俊林
 * @Date 2018年4月9日 上午9:15:33
 */
@Entity
@Table(name = "sys_data_dictionary")
public class DataDictionary {

	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", length = 50)
	private String id;
	
	/** 字典类型的父ID */
	@Column(name = "pId", length = 50)
	private String pId;
	
	/** 名称 */
	@Column(name = "name", length = 50)
	private String name;

	/** 字典项的code码 */
	@Column(name = "code", length = 50)
	private String code;
	
	/** 字典类型唯一标识 */
	@Column(name = "mark", length = 50)
	private String mark;
	
	/** 是否启用 0：禁用；1：启用 */
	@Column(name = "flag", length = 1)
	private String flag;
	
	/** 逻辑删除 0：删除；1：可见 */
	@Column(name = "visible", length = 1)
	private String visible;
	
	/** 顺序号 */
	@Column(name = "sort")
	private int sort;
	
	/** 备注 */
	@Column(name = "remark", length = 2000)
	private String remark;
	
	/** 类型 0：字典类型；1：字典项 */
	@Column(name = "type", length = 1)
	private String type;
	
	/** 创建时间 */
	@Column(name = "cre_time", length = 30)
	private String creTime;
	
	/** 最后更新时间 */
	@Column(name = "update_time", length = 30)
	private String updateTime;
	
	/** 创建人id */
	@Column(name = "cre_user_id", length = 50)
	private String creUserId;
	
	/** 创建人名 */
	@Column(name = "cre_user_name", length = 50)
	private String creUserName;
	
	/** 最后更新人id */
	@Column(name = "update_user_id", length = 50)
	private String updateUserId;
	
	/** 最后更新人名 */
	@Column(name = "update_user_name", length = 50)
	private String updateUserName;
	
	/** 创建人部门id */
	@Column(name = "cre_dept_id", length = 50)
	private String creDeptId;
	
	/** 创建人部门名 */
	@Column(name = "cre_dept_name", length = 50)
	private String creDeptName;
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}
