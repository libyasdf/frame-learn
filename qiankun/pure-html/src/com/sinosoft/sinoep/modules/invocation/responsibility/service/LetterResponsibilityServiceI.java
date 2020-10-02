package com.sinosoft.sinoep.modules.invocation.responsibility.service;



public interface LetterResponsibilityServiceI {
 
	public String testService(String str) throws Exception;
	/**
	 * 根据部门id生成人员的详细信息.
	 * @auto 子火
	 * @Date 2018年12月7日11:02:07
	 */
	public String departmentsCrew(String str) throws Exception;
}
