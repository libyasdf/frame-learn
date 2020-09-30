package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author 颜振兴
 * 时间：2018年7月18日
 *	DataDictionarys
 */
@Entity
@Table(name = "xxkh_tree")
public class DataDictionarys {

	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", length = 50)
	private String id;
	
	/** 字典类型的父ID */
	@Column(name = "pId", length = 50)
	private String pId;
	
	/** 创建人id */
	@Column(name = "cre_user_id", length = 50)
	private String creUserId;
	
	/** 创建人名 */
	@Column(name = "cre_user_name", length = 50)
	private String creUserName;
	/** 创建人部门id */
	@Column(name = "cre_dept_id", length = 50)
	private String creDeptId;
	
	/** 创建人部门名 */
	@Column(name = "cre_dept_name", length = 50)
	private String creDeptName;
	/** 名称 */
	@Column(name = "name", length = 50)
	private String name;

	/** 字典项的code码 */
	@Column(name = "code", length = 50)
	private String code;
	
//	/** 字典类型唯一标识 */
//	@Column(name = "mark", length = 50)
//	private String mark;
	
	/** 是否启用 0：禁用；1：启用 */
	@Column(name = "flag", length = 1)
	private String flag;
	
	/** 逻辑删除 0：删除；1：可见 */
	@Column(name = "visible", length = 1)
	private String visible;
	
	/** 顺序号 */
	@Column(name = "sort")
	private int sort;
	
	/** 类型 0：字典类型；1：字典项 */
	@Column(name = "type", length = 1)
	private String type;
	
	/** 创建时间 */
	@Column(name = "cre_time", length = 30)
	private String creTime;
	
	/** 最后更新时间 */
	@Column(name = "update_time", length = 30)
	private String updateTime;
	
	
	/** 最后更新人id */
	@Column(name = "update_user_id", length = 50)
	private String updateUserId;
	
	/** 最后更新人名 */
	@Column(name = "update_user_name", length = 50)
	private String updateUserName;
	
	/** 树类别 */
	@Column(name = "TREE_TYPE", length = 50)
	private String treeType;
	
	
	/** 备注 */
	@Column(name = "REMARK", length = 50)
	private String remark;
	/** 创建人处室ID、 */
	@Column(name = "CRE_CHUSHI_ID", length = 50)
	private String creChuShiId;
	/** 创建人处室名、 */
	@Column(name = "CRE_CHUSHI_NAME", length = 50)
	private String creChuShiName;
	/** 创建人二级局ID、 */
	@Column(name = "CRE_JU_ID", length = 50)
	private String creJuId;
	/** 创建人二级局名、 */
	@Column(name = "CRE_JU_NAME", length = 50)
	private String creJuName;
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

	
	public String getTreeType() {
		return treeType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}



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
