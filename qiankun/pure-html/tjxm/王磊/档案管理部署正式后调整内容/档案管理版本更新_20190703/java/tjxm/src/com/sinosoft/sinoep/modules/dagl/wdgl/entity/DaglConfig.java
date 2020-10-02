package com.sinosoft.sinoep.modules.dagl.wdgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * TODO 扫码枪配置实体类
 * @author 张浩磊
 * @date 2018年11月10日  下午5:36:33
 */
@Entity
@Table(name = "DAGL_CONFIG")
public class DaglConfig {

	/** 主键id */
	@Id
	@Column(name = "id", length = 50)
	private String id;

	/** 创建人姓名 */
	@Column(name = "COM_ID", length = 50)
	private String comId;

	/**
	 * 无参构造
	 */
	public DaglConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}
}
