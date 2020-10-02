package com.sinosoft.sinoep.modules.system.config.orgdepmapping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * 实体类 TODO
 * 
 * @author 马秋霞
 * @Date 2018年5月3日 下午5:59:42
 */
@Entity
@Table(name = "sys_other_flow_dept")
public class OtherFlowDep {
	/* 主键 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	/* 创建人id */
	@Column(name = "cre_user_id")
	private String creUserId;
	/* 创建人名 */
	@Column(name = "cre_user_name")
	private String creUserName;
	/* 创建人部门id */
	@Column(name = "cre_dept_id")
	private String creDeptId;
	/* 创建人部门名 */
	@Column(name = "cre_dept_name")
	private String creDeptName;
	/* 逻辑删除 */
	@Column(name = "visible")
	private String visible;
	/* 处室id */
	@Column(name = "CRE_CHUSHI_ID")
	private String creChuShiId;
	/* 处室名称 */
	@Column(name = "CRE_CHUSHI_NAME")
	private String creChuShiName;
	/* 二级局id */
	@Column(name = "CRE_JU_ID")
	private String creJuId;
	/* 二级局名 */
	@Column(name = "CRE_JU_NAME")
	private String creJuName;

	/* 创建时间 */
	@Column(name = "cre_time")
	private String creTime;
	/* 组织体系ID */
	@Column(name = "peak_deptid")
	private String peakDeptid;
	/* 部门id */
	@Column(name = "dept_id")
	private String deptId;
	/* 部门名称 */
	@Column(name = "dept_name")
	private String deptName;
	/* 关联部门id */
	@Column(name = "GL_DEPT_ID")
	private String glDeptId;
	
	/* 关联部门名称 */
	@Column(name = "GL_DEPT_NAME")
	private String glDeptName;
	/* 关联部门树的id */
	@Column(name = "GL_TREE_ID")
	private String glTreeId;
	/* 排序 */
	@Column(name = "sort")
	private Integer sort;
	/* 备注 */
	@Column(name = "remark")
	private String remark;
	/* 更新时间 */
	@Column(name = "update_time")
	private String updateTime;
	/* 更新人id */
	@Column(name = "update_user_id")
	private String updateUserId;
	/* 更新人姓名 */
	@Column(name = "update_user_name")
	private String updateUserName;
	/* 操作 */
	@Transient
	private String cz = "";

	public String getCz() {
		return cz;
	}
	
	public String getGlDeptName() {
		return glDeptName;
	}

	public void setGlDeptName(String glDeptName) {
		this.glDeptName = glDeptName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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

	public String getPeakDeptid() {
		return peakDeptid;
	}

	public void setPeakDeptid(String peakDeptid) {
		this.peakDeptid = peakDeptid;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
	}



	public Integer getSort() {
		return sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getGlDeptId() {
		return glDeptId;
	}

	public void setGlDeptId(String glDeptId) {
		this.glDeptId = glDeptId;
	}

	public String getGlTreeId() {
		return glTreeId;
	}

	public void setGlTreeId(String glTreeId) {
		this.glTreeId = glTreeId;
	}

}
