package com.sinosoft.sinoep.modules.system.config.utillink.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * TODO 常用工具实体类
 * @author 冯超
 * @Date 2018年5月10日 下午6:51:48
 */
@Entity
@Table(name = "sys_util_link")
public class UtilLink {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	/** 创建人ID（也是申请人ID字段） */
	@Column(name = "cre_user_id")
	private String creUserId;

	/** 创建人名称（也是申请人名称字段） */
	@Column(name = "cre_user_name")
	private String creUserName;

	/** 逻辑删除 */
	@Column(name = "visible")
	private String visible;

	/** 创建时间 */
	@Column(name = "cre_time")
	private String creTime;

	/** 常用工具下载链接 */
	@Column(name = "path")
	private String path;

	/** 常用工具名称 */
	@Column(name = "name")
	private String name;

	/** 常用工具下载链接 */
	@Column(name = "url")
	private String url;

	/** 顺序号 */
	@Column(name = "sort")
	private int sort;

	/** 备注 */
	@Column(name = "remark")
	private String remark;

	/** 最后更新时间 */
	@Column(name = "update_time")
	private String updateTime;

	/** 最后更新人ID */
	@Column(name = "update_user_id")
	private String updateUserId;

	/** 最后更新人名 */
	@Column(name = "update_user_name")
	private String updateUserName;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
