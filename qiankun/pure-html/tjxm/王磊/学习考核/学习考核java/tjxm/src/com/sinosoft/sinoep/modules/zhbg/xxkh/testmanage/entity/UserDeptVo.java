package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity;

/**
 * TODO 封装部门信息（判断部门是否有上报员角色的人） 
 * @author 李颖洁  
 * @date 2018年9月19日  下午8:19:49
 */
public class UserDeptVo {

	/* 部门ID */
	private String deptid;
	
	/* 部门名称 */
	private String deptname;
	
	/* 部门人员ID */
	private String userid;

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	

	
	
	
}
