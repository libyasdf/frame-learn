package com.sinosoft.sinoep.modules.zhbg.xxkh.grade.services;

import java.util.ArrayList;
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
			QName = " and ti.name like ? ";
		}
		String QMarkStatus = "";
		if (StringUtils.isNotBlank(xxkhTestInfo.getMarkStatus())) {
			QMarkStatus = " and ti.mark_status = ?";
		}
		//参数list 王磊20190425
		List<Object> list = new ArrayList<>();
		String QJu="  and ti.cre_ju_id = '"+ UserUtil.getCruJuId()+"' ";
		String sql = "select distinct ti.*, " + 
				"       (select count(1)  " + 
				"          from (select distinct ti.* " + 
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
				"                 where ss.test_id = ti.id  and ti.state = '2' and ti.type = ? "+QName+" "+QMarkStatus+"  "+QJu+")) as total " + 
				"  from (select distinct * " + 
				"          from (select  p.id " + 
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
				"   and ti.type = ? "+QName+" "+QMarkStatus+"  "+QJu+" order by "+sortName+"  "+pageImpl.getSortOrder()+"";
		//防止sql注入调整 王磊 20190425
		list.add(xxkhTestInfo.getType());
		if(!"".equals(QName) && !"".equals(QMarkStatus)){
			//两者都不为空
			list.add("%"+xxkhTestInfo.getName()+"%");
			list.add(xxkhTestInfo.getMarkStatus());
			list.add(xxkhTestInfo.getType());
			list.add("%"+xxkhTestInfo.getName()+"%");
			list.add(xxkhTestInfo.getMarkStatus());
			
		}else if(!"".equals(QName) && "".equals(QMarkStatus)){
			//QName不为空
			list.add("%"+xxkhTestInfo.getName()+"%");
			list.add(xxkhTestInfo.getType());
			list.add("%"+xxkhTestInfo.getName()+"%");
		}else if("".equals(QName) && !"".equals(QMarkStatus)){
			//QMarkStatus不为空
			list.add(xxkhTestInfo.getMarkStatus());
			list.add(xxkhTestInfo.getType());
			list.add(xxkhTestInfo.getMarkStatus());
		}else{
			//两者都为空
			list.add(xxkhTestInfo.getType());
		}
		list.add(pageImpl.getPageNumber()*pageImpl.getPageSize());
		list.add((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
		sql = "select s.* from (select s.*,rownum rn from ("+sql+") s where rownum<= ? ) s where rn>= ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhTestInfo.class),list.toArray());
	}
}
