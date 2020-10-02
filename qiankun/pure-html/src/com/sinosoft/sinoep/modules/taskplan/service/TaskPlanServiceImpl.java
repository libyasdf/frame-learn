package com.sinosoft.sinoep.modules.taskplan.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan;
import com.sinosoft.sinoep.user.util.UserUtil;

import workflow.spring.ProcessRelaDataService;

/**
 * 原DEMO中使用service
 * TODO 
 * @author 李利广
 * @Date 2018年3月15日 下午8:45:03
 */
@Service
public class TaskPlanServiceImpl implements TaskPlanService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    ProcessRelaDataService  processRelaDataService;
	
	@Override
	public Page getPageList(Integer pageNum, Integer showCount, String title, String startTime,String endTime, String subflag) {
		StringBuilder querySql = new StringBuilder();
		StringBuilder countSql = new StringBuilder();
		StringBuilder publicSql = new StringBuilder();
		StringBuilder paramSql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		List<Object> paratem = new ArrayList<>();
		//拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(title)) {
			paramSql.append("   and t.title like ?");
			paratem.add("%"+title+"%");
		}
		if (StringUtils.isNotBlank(subflag) ){
			paramSql.append("   and t.subflag = ?");
			paratem.add(subflag);
		}else{
			paramSql.append("   and t.subflag != ?");
			paratem.add(ConfigConsts.START_FLAG);
		}
		if (StringUtils.isNotBlank(startTime)) {
			paramSql.append("   and t.cre_time >= ?");
			paratem.add(startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			paramSql.append("   and t.cre_time <= ?");
			paratem.add(endTime);
		}
		para.addAll(paratem);
		para.addAll(paratem);
		
		publicSql.append("	select f.id yibanid,t.* ");
		publicSql.append("	from task_plan t, epcloud.flow_read f ");
		publicSql.append("	where f.userid = ? ");
		publicSql.append("	and t.id = f.recordid ");
		publicSql.append(paramSql);
		publicSql.append("union");
		publicSql.append("	select '' yibanid,t.* ");
		publicSql.append("	from task_plan t ");
		publicSql.append("	where t.subflag = '"+ConfigConsts.START_FLAG+"' ");
		publicSql.append(paramSql);
		
		countSql.append("	select count(1) from ( ");
		countSql.append(publicSql + ")");
		Integer totalCount = jdbcTemplate.queryForObject(countSql.toString(), para.toArray(), Integer.class);
		
		
		//拼接查询语句
		querySql.append("	select * from ( ");
		querySql.append("	select rownum rn, a.* from ( ");
		querySql.append(publicSql);
		querySql.append(") a");
		querySql.append("	where rownum <= ? ");
		querySql.append("    order by a.cre_time desc) ");
		querySql.append("	where rn >= ? ");
		
		para.add(pageNum*showCount);//本页结束
		para.add((pageNum-1)*showCount+1);//本页开始
		
		List<TaskPlan> query = jdbcTemplate.query(querySql.toString(),para.toArray(), new RowMapper<TaskPlan>(){
			@Override
			public TaskPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
				TaskPlan plan = new TaskPlan();
				plan.setId(rs.getString("id"));
				plan.setTitle(rs.getString("title"));
				plan.setSubflag(rs.getString("subflag"));
				plan.setWorkitemid(rs.getString("yibanid"));
				plan.setWorkFlowId(rs.getString("filetype"));
				plan.setZhubDeptNm(rs.getString("zhub_dept_nm"));
				plan.setZhubPersonNm(rs.getString("zhub_person_nm"));
				return plan;
			}
			
		});
		Page page = new Page(pageNum, showCount, totalCount, query);
		return page;
	}
	
}
