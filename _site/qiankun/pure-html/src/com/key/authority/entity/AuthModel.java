package com.key.authority.entity;

import java.util.List;

/**
 * 权限实体封装类
 * TODO 
 * @author 李利广
 * @Date 2018年9月16日 上午10:11:42
 */
public class AuthModel {

	private String surveyId;

	private List<AuthDept> deptList;
	
	private List<AuthZwqx> zwqxList;
	
	private List<AuthDeptZwqxList> deptZwqxList;
	
	private List<AuthDeptZwqx> deptZwqxListList;

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public List<AuthDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<AuthDept> deptList) {
		this.deptList = deptList;
	}

	public List<AuthZwqx> getZwqxList() {
		return zwqxList;
	}

	public void setZwqxList(List<AuthZwqx> zwqxList) {
		this.zwqxList = zwqxList;
	}

	public List<AuthDeptZwqxList> getDeptZwqxList() {
		return deptZwqxList;
	}

	public void setDeptZwqxList(List<AuthDeptZwqxList> deptZwqxList) {
		this.deptZwqxList = deptZwqxList;
	}

	public List<AuthDeptZwqx> getDeptZwqxListList() {
		return deptZwqxListList;
	}

	public void setDeptZwqxListList(List<AuthDeptZwqx> deptZwqxListList) {
		this.deptZwqxListList = deptZwqxListList;
	}

}
