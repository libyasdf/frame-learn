package com.sinosoft.sinoep.modules.invocation.responsibility.service.impl;


import com.sinosoft.sinoep.modules.invocation.responsibility.service.LetterResponsibilityServiceI;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.tool.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class LetterResponsibilityServiceImpl implements LetterResponsibilityServiceI {

	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public String testService(String str) throws Exception {
        System.out.println("xxxxx");
		return "xx----------";
	}
	@Override
	public String departmentsCrew(String str) throws Exception{

		String sql="SELECT * FROM EPCLOUD.FLOW_WRITE";
		List<Map<String, Object>> list=jdbc.queryForList(sql);
		System.out.println(Helper.listToStringJSON(list));
		return "";
	}
 
	
}