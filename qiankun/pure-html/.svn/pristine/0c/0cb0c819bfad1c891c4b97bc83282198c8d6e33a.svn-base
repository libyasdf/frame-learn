package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;

public class XxkhPaperInfoDaoImpl implements XxkhPaperInfoRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<String> getLevelCount(String id) {
		String sql = "select t.difficulty_level  from xxkh_question_group g,xxkh_question_qgroup q,xxkh_question_info t  where g.id =q.question_group_id and t.id =q.question_id    and g.id = '"+id+"'";
		return jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				return arg0.getString(1);
			}
			
		});
	}

	@Override
	public List<XxkhTestInfo> isCanDelete(String paperID) {
		String sql = "select * " +
				"  from xxkh_test_info i " +
				" where i.id in " +
				"       (select t.test_id as id " +
				"          from xxkh_test_paper t " +
				"         where t.paper_id = '"+paperID+"') " +
				"   and i.visible = '1' ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhTestInfo.class));
	}

	@Override
	public List<XxkhTestInfo> isCanUpdata(String paperID,int i) {
		String state = "";
		if(i==1) {
			 state = " and (i.state = '2' or i.state = '1')";
		}
		if (i==0) {
			 state = " and i.state = '0'";
		}
		String sql = "select * " +
				"  from xxkh_test_info i " +
				" where i.id in " +
				"       (select t.test_id as id " +
				"          from xxkh_test_paper t " +
				"         where t.paper_id = '"+paperID+"') " +
				"   and i.visible = '1' " +state;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhTestInfo.class));
	}


	/**
	 * @Author 王富康
	 * @Description //TODO 考试后获取试卷信息，拼接了这个人这场考试这张试卷的主观题得分，客观题得分，人工评卷状态的字段
	 * @Date 2018/9/17 20:13
	 * @Param [paperInfo, testId, userId]
	 * @return net.sf.json.JSONObject
	 **/
	public XxkhPaperInfo getTestResult(XxkhPaperInfo paperInfo, String testId, String userId){

		String sql =	"	SELECT t.*, \n" +
						"   	    s.paper_subjective_score, \n" +
						"    	   s.paper_objective_score, \n" +
						"    	   s.artificial_marking_state \n" +
						" 	 FROM xxkh_paper_info t, xxkh_user_paper s \n" +
						" WHERE t.id = '"+paperInfo.getId()+"' \n" +
						"   and t.id = s.paper_id \n" +
						"   and s.test_id = '"+testId+"' \n" +
						"   and s.user_id = '"+userId+"'";
		List<XxkhPaperInfo> xxkhPaperInfoLists= jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhPaperInfo.class));
		return xxkhPaperInfoLists.get(0);
	}

}
