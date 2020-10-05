package com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity;

/**
 * TODO 封装部门信息（判断部门是否有上报员角色的人） 
 * @author 李颖洁  
 * @date 2018年7月26日  上午9:34:55
 */
public class DeptVo {

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
