package com.sinosoft.sinoep.modules.dagl.bmgl.entity;

/**
 * 
 * TODO 用户存储档案的公共信息：recid，立卷单位名称，题名，录入人id，录入人name，档号
 * @author 王磊
 * @Date 2019年4月16日 上午11:35:09
 */
public class DangAnEntity {

	/**
	 * 档案在业务表中主键
	 */
	private String recid;
	
	/**
	 * 档案在业务表中立卷单位名称
	 */
	private String liJuanDanWei;
	
	/**
	 * 档案在业务表中题名
	 */
	private String maintitle;
	
	/**
	 * 档案在业务表中录入人id
	 */
	private String creUserId;
	
	/**
	 * 档案在业务表中录入人name
	 */
	private String creUserName;
	
	/**
	 * 档案在业务表中录入人档号
	 */
	private String dangHao;
	
	/**
	 * 档案所在的业务表表名
	 */
	private String tableName;
	
	/**
	 * 页面操作
	 */
	private String cz;

	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}

	public String getLiJuanDanWei() {
		return liJuanDanWei;
	}

	public void setLiJuanDanWei(String liJuanDanWei) {
		this.liJuanDanWei = liJuanDanWei;
	}

	public String getMaintitle() {
		return maintitle;
	}

	public void setMaintitle(String maintitle) {
		this.maintitle = maintitle;
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

	public String getDangHao() {
		return dangHao;
	}

	public void setDangHao(String dangHao) {
		this.dangHao = dangHao;
	}
}
