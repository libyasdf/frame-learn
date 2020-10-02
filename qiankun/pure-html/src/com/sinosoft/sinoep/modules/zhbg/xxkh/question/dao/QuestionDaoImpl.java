package com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao;

import java.util.ArrayList;
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
		//防止sql注入 王磊20190426
		List<Object> list = new ArrayList<>();
		String type="";
		if(question.getType()!=null&&!question.getType().equals("")) {
			type =" and type = ? ";
			/*if(question.getType().equals(XXKHCommonConstants.JUSJ)) {
				type =" and type like '%"+XXKHCommonConstants.TREE_TYPE_STATUS[2]+"%'";
			}*/
		}
		list.add(question.getDifficultyLevel());
		list.add(question.getQuestionType());
		if(!"".equals(type)){
			list.add(question.getType());
		}
		list.add(num);
		String sql ="select * from (select * from xxkh_question_info where difficulty_level = ? and question_type= ? "+type+" and visible = '1' and state = '1'  order by dbms_random.random) where rownum<= ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class),list.toArray());
	}

	@Override
	public List<Question> getList(String id) {
		//防止sql注入 王磊20190426
		String sql = "select i.* from " + 
					 "(select * from xxkh_question_qgroup q where q.question_group_id = ?) t,xxkh_question_info i " + 
					 "where t.question_id = i.id order by i.id asc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class),id);
	}

	@Override
	public List<Option> getOptionList(String id) {
		//防止sql注入 王磊20190426
		String sql = "select * from XXKH_OPTION t where t.question_id = ? and t.visible = '1' order by t.sequence asc";
	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Option.class),id);
	}

	@Override
	public int getQuestionJuCount(String difficultyLevel, String questionType, String type) {
		//防止sql注入 王磊20190426
		List<Object> list = new ArrayList<>();
		String sql  = " select count(1) from xxkh_question_info  where difficulty_level = ? and question_type = ? and visible = '1' and state = '1' and type like ? ";
		list.add(difficultyLevel);
		list.add(questionType);
		list.add("%"+type+"%");
		return jdbcTemplate.queryForObject(sql, Integer.class,list.toArray());
	}
}
