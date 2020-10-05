package com.sinosoft.sinoep.modules.zhbg.xxkh.grade.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.user.util.UserUtil;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	


	@Override
	public List<XxkhTestInfo> getGradeList(PageImpl pageImpl, XxkhTestInfo xxkhTestInfo) {
		String sortName = "ti.start_time";
		if (StringUtils.isNotBlank(pageImpl.getSortName())) {
			if (pageImpl.getSortName().equals("markStatus")) {
				pageImpl.setSortName("ti.mark_status");
				sortName = pageImpl.getSortName();
			}
			if (pageImpl.getSortName().equals("answerTime")){
				pageImpl.setSortName("ti.answer_time");
				sortName = " to_number("+pageImpl.getSortName()+")";
			}
		}
		String QName = "";
		if (StringUtils.isNotBlank(xxkhTestInfo.getName())) {
			QName = " and ti.name like '%"+xxkhTestInfo.getName()+"%'";
		}
		String QMarkStatus = "";
		if (StringUtils.isNotBlank(xxkhTestInfo.getMarkStatus())) {
			QMarkStatus = " and ti.mark_status = '" +xxkhTestInfo.getMarkStatus()+"'";
		}
		String QJu="  and ti.cre_ju_id = '"+ UserUtil.getCruJuId()+"' ";
		String sql = "select ti.*, " + 
				"       (select count(1)  " + 
				"          from (select ti.* " + 
				"                  from (select * " + 
				"                          from (select p.id " + 
				"                                  from xxkh_paper_info p, xxkh_question_group q " + 
				"                                 where p.id = q.paper_id " + 
				"                                   and p.visible = '1' " + 
				"                                   and q.question_type = '5' " + 
				"                                   and p.state = '1' " + 
				"                                 group by p.id) s, " + 
				"                               xxkh_test_paper tp " + 
				"                         where s.id = tp.paper_id) ss, " + 
				"                       xxkh_test_info ti " + 
				"                 where ss.test_id = ti.id  and ti.state = '2' and ti.type = '"+xxkhTestInfo.getType()+"'  "+QName+" "+QMarkStatus+"  "+QJu+")) as total " + 
				"  from (select * " + 
				"          from (select p.id " + 
				"                  from xxkh_paper_info p, xxkh_question_group q " + 
				"                 where p.id = q.paper_id " + 
				"                   and p.visible = '1' " + 
				"                   and q.question_type = '5' " + 
				"                   and p.state = '1' " + 
				"                 group by p.id) s, " + 
				"               xxkh_test_paper tp " + 
				"         where s.id = tp.paper_id) ss, " + 
				"       xxkh_test_info ti " + 
				" where ss.test_id = ti.id and ti.state = '2'"+
				"   and ti.type = '"+xxkhTestInfo.getType()+"'  "+QName+" "+QMarkStatus+"  "+QJu+" order by "+sortName+"  "+pageImpl.getSortOrder()+"";
		
		int after = pageImpl.getPageNumber()*pageImpl.getPageSize();
		int pre = (pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1;
		sql = "select s.* from (select s.*,rownum rn from ("+sql+") s where rownum<="+after+") s where rn>="+pre;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhTestInfo.class));
	}
}
