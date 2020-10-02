package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;

public class XxkhQuestionGroupDaoImpl implements XxkhQuestionGroupRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<XxkhQuestionGroup> getList(String id) {
		String sql ="select t.id,create_type,t.sequence,every_score,t.full_score,t.QUESTION_TYPE,l.question_count as question_count from XXKH_QUESTION_GROUP t left join(" +
					"select q.question_group_id,count(q.question_id) question_count  from xxkh_question_qgroup q  group by q.question_group_id) l " +
					"on t.id = l.question_group_id "
					+ "where t.paper_id ='"+id+"' order by t.question_type asc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhQuestionGroup.class));
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 只获取简答题的试题组
	 * @Date 2018/9/18 19:47
	 * @Param [id, questionType]
	 * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup>
	 **/
	@Override
	public List<XxkhQuestionGroup> getSortAnswerList(String id, String questionType) {
		String sql ="select t.id,create_type,t.sequence,every_score,t.full_score,t.QUESTION_TYPE,l.question_count as question_count from XXKH_QUESTION_GROUP t left join(" +
				"select q.question_group_id,count(q.question_id) question_count  from xxkh_question_qgroup q  group by q.question_group_id) l " +
				"on t.id = l.question_group_id "
				+ "where t.paper_id ='"+id+"' " ;
				if(StringUtils.isNotBlank(questionType)){
					sql+=" and t.question_type = '"+questionType+"' ";
				}
				sql += "	order by t.question_type asc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhQuestionGroup.class));
	}

	@Override
	public XxkhQuestionGroup getLevelCount(String id) {
		String sql =" SELECT " + 
					"      WM_CONCAT(simpleCount)simpleCount," + 
					"      WM_CONCAT(normalCount)normalCount," + 
					"      WM_CONCAT(hardCount)hardCount\r\n" + 
					"      FROM(" + 
					"SELECT " + 
					"      DECODE(a.difficulty_level,'1',a.s,NULL)simpleCount," + 
					"      DECODE(a.difficulty_level,'2',a.s,NULL)normalCount," + 
					"      DECODE(a.difficulty_level,'3',a.s,NULL)hardCount" + 
					"      FROM (" + 
					"select t.difficulty_level,count(t.difficulty_level) as s from xxkh_question_qgroup q,xxkh_question_info t  where q.question_id=t.id and q.question_group_id = '"+id+"' group by t.difficulty_level" + 
					") a)";
		return jdbcTemplate.queryForObject(sql,new RowMapper<XxkhQuestionGroup>() {
			@Override
			public XxkhQuestionGroup mapRow(ResultSet rs, int arg1) throws SQLException {
				XxkhQuestionGroup group = new XxkhQuestionGroup();
				group.setSimpleCount(rs.getString("simpleCount"));
				group.setNormalCount(rs.getString("normalCount"));
				group.setHardCount(rs.getString("hardCount"));
				return group;
			}
		});
	}

	@Override
	public XxkhQuestionGroup getEverScore(String tizuId, String questionId) {
		String sql="select t.* from xxkh_question_group t,( " + 
					"select * from xxkh_question_qgroup q where q.question_id ='"+questionId+"' and q.question_group_id = '"+tizuId+"') a" + 
					" where t.id = a.question_group_id";
		return jdbcTemplate.queryForObject(sql, new RowMapper<XxkhQuestionGroup>() {

			@Override
			public XxkhQuestionGroup mapRow(ResultSet rs, int arg1) throws SQLException {
				XxkhQuestionGroup group = new XxkhQuestionGroup();
				group.setEveryScore(rs.getString("everyScore"));
				return group;
			}
		});
	}

}
