package com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.entity.XxkhLearnTime;
import com.sinosoft.sinoep.user.util.UserUtil;

public class XxkhLearnTimeDaoImpl implements XxkhLearnTimeRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<XxkhLearnTime> findList(XxkhLearnTime learnTime) {
		String cruUserId = UserUtil.getCruUserId();
		String sql = "select d.type,sum(x.minutes) as minutes  from xxkh_learn_time x,xxkh_zi_liao d where x.zl_id=d.id and x.cre_user_id = '"
				+ cruUserId + "' group by d.type";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhLearnTime.class));
	}

	@Override
	public List<XxkhLearnTime> findBuMenList(XxkhLearnTime learnTime) {
		if (learnTime.getCreDeptName() != null && !learnTime.getCreDeptName().equals("")) {

		}
		if (learnTime.getInfoId() != null && !learnTime.getInfoId().equals("")) {

		}
		if (learnTime.getOverTime() != null && !learnTime.getOverTime().equals("")) {

		}
		String sql = "select t.cre_dept_name,s.*,t.counts "
				+ "from (select t.cre_dept_id,t.type,sum(t.minutes) as times "
				+ "from  (select x.*,d.type    from xxkh_learn_time x,xxkh_zi_liao d " + "where x.zl_id = d.id) t"
				+ " group by t.cre_dept_id,t.type) s,(" + "select  cre_dept_name,cre_dept_id,count(1) as counts  "
				+ "from xxkh_learn_time group by cre_dept_id,cre_dept_name) t where s.cre_dept_id = t.cre_dept_id \r\n"
				+ "";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhLearnTime.class));

	}

	@Override
	public List<XxkhLearnTime> findDeptUserList(XxkhLearnTime learnTime) {
		String sql = " select  t.cre_user_name,sum(t.minutes) as minutes " + " from" + " (select x.*,d.type "
				+ " from xxkh_learn_time x,xxkh_zi_liao d " + " where x.cre_dept_id ='" + learnTime.getCreDeptId()
				+ "' and x.zl_id = d.id) t" + " where t.type = '" + learnTime.getInfoId() + "'  group by t.cre_user_name";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhLearnTime.class));
	}
}
