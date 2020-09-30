package com.sinosoft.sinoep.uias.model;

/**
 * 系统角色实体类
 * TODO 
 * @author 李利广
 * @Date 2018年6月11日 下午2:41:47
 */
public class SysRole {
	
	/** 系统角色ID */
	private String rlsyId;
	
	/** 系统角色名称 */
	private String rlsyNm;
	
	/** 系统角色编号 */
	private String rlsyno;
	
	/** 是否默认角色（1默认、0非默认） */
	private String sysdef;

	public String getRlsyId() {
		return rlsyId;
	}

	public void setRlsyId(String rlsyId) {
		this.rlsyId = rlsyId;
	}

	public String getRlsyNm() {
		return rlsyNm;
	}

	public void setRlsyNm(String rlsyNm) {
		this.rlsyNm = rlsyNm;
	}

	public String getRlsyno() {
		return rlsyno;
	}

	public void setRlsyno(String rlsyno) {
		this.rlsyno = rlsyno;
	}

	public String getSysdef() {
		return sysdef;
	}

	public void setSysdef(String sysdef) {
		this.sysdef = sysdef;
	}

}
