package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity.DataTable;

import java.util.List;

@Repository
public class DataTableDaoImpl implements DataTableRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<DataTable> getlist(DataTable table) {
		String sql="select d.* from xxkh_zi_liao d where d.mark =  (select t.mark from xxkh_tree t where t.id ='"+table.getId()+"') and visible='1'"; 
				
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataTable.class));
	}
	@Override
	public List<DataTable> getlistDiGui(DataTable table) {
		String sql="select d.* from xxkh_zi_liao d where d.mark in  (select t.mark from xxkh_tree t start with t.id ='"+table.getId()+"' connect by prior t.id = t.pid) and visible='1'"; 
				
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataTable.class));
	}
	@Override
	public List<DataTable> gettypelist(DataTable table) {
		String sql="select d.* from xxkh_zi_liao d where d.type='"+table.getType()+"'  and visible='1'"; 
				
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataTable.class));
	}
	@Override
	public DataTable getById(DataTable table) {
		String sql="select d.* from xxkh_zi_liao d where d.id='"+table.getId()+"'"; 
		return jdbcTemplate.queryForObject(sql, DataTable.class);
	}





}
