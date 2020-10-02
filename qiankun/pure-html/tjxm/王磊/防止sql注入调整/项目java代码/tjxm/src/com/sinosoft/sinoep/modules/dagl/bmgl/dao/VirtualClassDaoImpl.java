package com.sinosoft.sinoep.modules.dagl.bmgl.dao;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.sinoep.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.VirtualClass;
import com.sinosoft.sinoep.user.util.UserUtil;

@Repository
public class VirtualClassDaoImpl implements VirtualClassRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<VirtualClass> findTree(String isAdmin,String dwylk,String[] codes) {
		List<Object> para = new ArrayList<>();
		String newCodes =  StringUtils.join(codes, ",");
		String result = "";
		if(!StringUtils.isBlank(isAdmin)) {
			result = " and isAdmin = ?";
			para.add(isAdmin);
			if(!StringUtils.isBlank(dwylk)) {
				String codesInString = CommonUtils.solveSqlInjectionOfIn(newCodes,para);
				if("danweiguidang".equals(dwylk)) {
					result += " and CATEGORY_CODE in ("+codesInString+") ";
				} else {
				result+= " and cre_user_id ='"+UserUtil.getCruUserId()+"' and CATEGORY_CODE in ("+codesInString+") ";
				}
			}
		}
		String sql = "select * from dagl_virtual_class where 1=1 " +result;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(VirtualClass.class),para.toArray());
	}
}
