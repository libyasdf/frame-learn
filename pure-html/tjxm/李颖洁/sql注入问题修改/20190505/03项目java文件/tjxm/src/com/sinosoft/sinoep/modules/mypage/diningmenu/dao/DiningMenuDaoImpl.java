package com.sinosoft.sinoep.modules.mypage.diningmenu.dao;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.mypage.diningmenu.entity.DiningMenu;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DiningMenuDaoImpl implements DiningMenuRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 根据日期查询菜谱
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<DiningMenu> getAllChildTypes(String date) {
		String sql = "select * from dining_menu t where t.visible = '" + CommonConstants.VISIBLE[1] + "' "
				+ " and t.date_menu = ? and t.state ='1'";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DiningMenu.class),date);
	}

	/**
	 * 根据id查询菜谱
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<DiningMenu> getAllDiningMenus(String date,String id) {
		List<Object> para = new ArrayList<>();
		String sql = "select * from dining_menu t where t.visible = '" + CommonConstants.VISIBLE[1] + "' ";
		if(!StringUtils.isBlank(id)){
			sql+= " and t.id != ?";
			para.add(id);
		}
		if(!StringUtils.isBlank(date)){
			sql+= " and t.date_menu = ?";
			para.add(date);
		}
				
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DiningMenu.class),para.toArray());
	}

	/**
	 * 根据日期查询菜谱
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<DiningMenu> getAllDiningMenu(String date) {
		String sql = "select * from dining_menu t where t.visible = '" + CommonConstants.VISIBLE[1] + "' "
				+ " and t.date_menu = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DiningMenu.class),date);
	}
	/**
	 * 根据日期、状态查询菜谱
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<DiningMenu> getDiningMenus(String startDate,String endDate,String state) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select * ");
		querySql.append("	from dining_menu t");
		querySql.append("	where ");
		querySql.append("   t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		if (StringUtils.isNotBlank(state)) {
			querySql.append("   and t.state = ?");
			para.add(state);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.date_menu >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.date_menu <= ?");
			para.add(endDate);
		}
			querySql.append("  order by t.date_menu desc ");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(DiningMenu.class),para.toArray());
	}
}
