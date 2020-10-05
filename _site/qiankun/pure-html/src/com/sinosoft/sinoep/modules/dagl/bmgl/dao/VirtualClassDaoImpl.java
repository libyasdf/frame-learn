package com.sinosoft.sinoep.modules.dagl.bmgl.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.sinosoft.sinoep.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.VirtualClass;
import com.sinosoft.sinoep.user.util.UserUtil;

@Repository
public class VirtualClassDaoImpl implements VirtualClassRepository {

	private Logger log = LoggerFactory.getLogger(this.getClass());

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
				//单位预立库和单位归档库
				/*String codesInString = CommonUtils.solveSqlInjectionOfIn(newCodes,para);
				if("danweiguidang".equals(dwylk)) {
					result += " and CATEGORY_CODE in ("+codesInString+") ";
				} else {
				result+= " and cre_user_id ='"+UserUtil.getCruUserId()+"' and CATEGORY_CODE in ("+codesInString+") ";
				}*/
				Calendar c = Calendar.getInstance();
				int thisYear = c.get(Calendar.YEAR);
				c.setTime(new Date());
				c.add(Calendar.YEAR, -1);
				int lastYear = c.get(Calendar.YEAR);
				//档案管理员
				if("danweiguidang".equals(dwylk)) {
					//单位归档库
					String codesInString = CommonUtils.solveSqlInjectionOfIn(newCodes,para);
					result += " and t.CATEGORY_CODE in ("+codesInString+") ";
				}else {
					String codesInStringOne = CommonUtils.solveSqlInjectionOfIn(newCodes,para);
					String codesInStringTwo = CommonUtils.solveSqlInjectionOfIn(newCodes,para);
					String codesInStringThree = CommonUtils.solveSqlInjectionOfIn(newCodes,para);
					result +="and t.CATEGORY_CODE in ("+codesInStringOne+")\n" +
							"   and (t.column_name != 'filing_year' or\n" +
							"       (t.column_name = 'filing_year' and\n" +
							"       t.column_value >\n" +
							"       (select max(to_number(column_value)) - 3\n" +
							"            from dagl_virtual_class t\n" +
							"           where isAdmin = '1'\n" +
							"             and t.CATEGORY_CODE in ("+codesInStringTwo+")\n" +
							"             and t.column_name = 'filing_year')))\n" +
							"   and t.id not in\n" +
							"       (select id\n" +
							"          from dagl_virtual_class s\n" +
							"         start with s.pid in\n" +
							"                    (select id\n" +
							"                       from dagl_virtual_class s\n" +
							"                      where s.column_value <=\n" +
							"                            (select max(to_number(column_value)) - 3\n" +
							"                               from dagl_virtual_class t\n" +
							"                              where isAdmin = '1'\n" +
							"                                and t.CATEGORY_CODE in\n" +
							"                                    ("+codesInStringThree+")\n" +
							"                                and t.column_name = 'filing_year')\n" +
							"                        and s.column_name = 'filing_year')\n" +
							"        connect by prior s.id = s.pid)";
				}
			}else{
				//档案管理员
				String codesInString = CommonUtils.solveSqlInjectionOfIn(newCodes,para);
				result += " and t.CATEGORY_CODE in ("+codesInString+") ";
			}
		}
		String sql = "select * from dagl_virtual_class t where 1=1 " +result+"";
		//排序
		sql +=" order by case when column_name = 'leibiehao' then column_value end asc \n" +
				"		,case when column_name = 'filing_year' then column_value end desc"+
				"		,case when column_name = 'retention' then column_value end asc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(VirtualClass.class),para.toArray());
	}
}
