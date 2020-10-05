package com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sinosoft.sinoep.modules.zhbg.xxkh.constant.XXKHCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;

public class QuestionDaoImpl implements QuestionRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Question> radomList(Question question,String num) {
		String type="";
		if(question.getType()!=null&&!question.getType().equals("")) {
			type =" and type = '"+question.getType()+"'";
			/*if(question.getType().equals(XXKHCommonConstants.JUSJ)) {
				type =" and type like '%"+XXKHCommonConstants.TREE_TYPE_STATUS[2]+"%'";
			}*/
		}
		String sql ="select * from (select * from xxkh_question_info where difficulty_level = '"+question.getDifficultyLevel()+"' and question_type='"+question.getQuestionType()+"' "+type+" and visible = '1' and state = '1'  order by dbms_random.random) where rownum<='"+num+"'";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class));
	}

	@Override
	public List<Question> getList(String id) {
		String sql = "select i.* from " + 
					 "(select * from xxkh_question_qgroup q where q.question_group_id ='"+id+"') t,xxkh_question_info i " + 
					 "where t.question_id = i.id order by t.cre_time asc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class));
	}

	@Override
	public List<Option> getOptionList(String id) {
		String sql = "select * from XXKH_OPTION t where t.question_id = '"+id+"' and t.visible = '1' order by t.sequence asc";
	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Option.class));
	}

	@Override
	public int getQuestionJuCount(String difficultyLevel, String questionType, String type) {
		String sql  = " select count(1) from xxkh_question_info  where difficulty_level = '"+difficultyLevel+"' and question_type ='"+questionType+"' and visible = '1' and state = '1' and type like '%"+type+"%'";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
