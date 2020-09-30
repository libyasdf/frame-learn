package com.sinosoft.sinoep.modules.system.config.orgdepmapping.util;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;

public class OtherFlowDepUtil {
	
	private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");
	
	/**
	 * 根据体系id查询sort最大的值
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午3:59:00
	 * @return
	 */
	public static Integer getSort(String orgId){
		StringBuilder sb = new StringBuilder();
		sb.append("select max(t.sort) sort from sys_other_flow_dept t where ");
		sb.append("t.peak_deptid='"+ orgId +"'");
		Integer sort = jdbcTemplate.queryForObject(sb.toString(),Integer.class);
		return (sort == null) ? 0 : (sort+1);
	}

}
