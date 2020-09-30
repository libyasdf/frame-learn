package com.sinosoft.sinoep.modules.zhbg.xxkh.gradesearch.services;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.constant.XXKHCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper;
import com.sinosoft.sinoep.user.util.UserUtil;

@Service
public class GradesearchServiceImpl implements GradesearchService {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @Title: TestManageService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services
	 * @Description: TODO成绩查询
	 * @author 颜振兴
	 * @date 2018年9月10日
	 * @param pageImpl
	 * @param testInfo
	 * @return
	 */
	@Override
	public List<XxkhTestInfo> getScoreInquiry(PageImpl pageImpl,XxkhTestInfo testInfo,String timeRange) {
		String ju = "";
		if(org.apache.commons.lang3.StringUtils.isNotBlank(testInfo.getCreJuId())) {
			ju = " and t.cre_ju_id = '"+testInfo.getCreJuId()+"' ";
		}
		String name = "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(testInfo.getName())) {
			name = " and t.name like '%"+testInfo.getName()+"%'";
		}
		String times = "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(timeRange)) {
			String[] strings = timeRange.split(" - ");
			String startTime =  strings[0];
			String endTime =  strings[1];
			times = " and start_time<='"+endTime+"' and end_time >= '"+startTime+"' ";
		}
		String sql = "select s.* from "+
				" (select s.*,u.sumPeople, (select count(1) from xxkh_test_info t where t.state = '2'   and t.type = '"+testInfo.getType()+"' "+name+" "+times+" "+ju+") as total   from "+
				" (select s.*,u.passPeople from " + 
				//" (select s.*,u.avgSvore from " + 
				" (select s.*,\"NVL\"(u.passPeople, '0') as submitPeople from " + 
				" (select t.*,u.maxScore,u.minScore from " + 
				" (select t.id,t.name,t.full_score,t.start_time,t.end_time from xxkh_test_info t where t.state = '2'   and t.type = '"+testInfo.getType()+"'  "+name+" "+times+" "+ju+") t " + 
				" left join (select u.test_id,max(to_number(u.paper_subjective_score)+to_number(nvl(u.paper_objective_score,0))) as maxScore,min(to_number(u.paper_subjective_score)+to_number(nvl(u.paper_objective_score,0))) as minScore from xxkh_user_paper u where u.artificial_marking_state = '1' and u.is_submit='1' group by  u.test_id) u on t.id=u.test_id) s  " + 
				" left join (select u.test_id,count(distinct u.user_id) as passPeople from xxkh_user_paper u where u.is_submit ='1'  group by  u.test_id) u on s.id =u.test_id ) s " + 
				//" left join (select u.test_id,round(avg(to_number(u.paper_subjective_score)+to_number(u.paper_objective_score)),2) avgSvore from xxkh_user_paper u where u.is_submit ='1' and u.artificial_marking_state = '1'  group by  u.test_id) u on s.id = u.test_id) s " + 
				" left join (select u.test_id,count(distinct u.user_id) as passPeople from xxkh_user_paper u,xxkh_test_info t where u.test_id = t.id and to_number(u.paper_subjective_score)+to_number(u.paper_objective_score)>= t.pass_score group by u.test_id) u on s.id = u.test_id) s "+
				" left join (select u.test_id,count(distinct u.user_id) as sumPeople from xxkh_user_paper u   group by  u.test_id) u on s.id =u.test_id ) s " ;
		
		if (pageImpl.getSortName()!=null&&!pageImpl.getSortName().equals("")) {
			if(pageImpl.getSortName().equals("fullScore")) {
				pageImpl.setSortName("full_Score");
			}
			sql+=" order by to_number(s."+pageImpl.getSortName()+") "+pageImpl.getSortOrder()+"";
		}
		sql = "select s.*,rownum rn from ("+sql+") s";
		int after = pageImpl.getPageNumber()*pageImpl.getPageSize();
		int pre = (pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1;
		sql = "select s.* from ("+sql+"  where rownum<="+after+") s where rn>="+pre;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhTestInfo.class));
	}

	@Override
	public List<XxkhUserPaper> getbyTestContent(PageImpl pageImpl, XxkhTestInfo testInfo,String isJiGe) {
		String ju = "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(testInfo.getCreJuName())) {
			if(testInfo.getCreJuName().indexOf(",")==-1) {
				ju = "  and u.candidate_ju_name = '"+testInfo.getCreJuName()+"'";
			}else {
				String[] strings = testInfo.getCreJuName().split(",");
				ju =" and (";
				for(String juName : strings) {
					ju +=" u.candidate_ju_name = '"+juName+"'  or";
				}
				ju = ju.substring(0, ju.length()-2)+")";
			}
		}
		String chushi= "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(testInfo.getTestChuShiNames())) {
			if(testInfo.getTestChuShiNames().indexOf(",")==-1) {
				chushi = "  and u.candidate_chushi_name = '"+testInfo.getTestChuShiNames()+"'";
			}else {
				String[] strings = testInfo.getTestChuShiNames().split(",");
				chushi =" and (";
				for(String chushiname : strings) {
					chushi +=" u.candidate_chushi_name = '"+chushiname+"'  or";
				}
				chushi = chushi.substring(0, chushi.length()-2)+")";
			}
		}
		String JiGe = "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(isJiGe)) {
			if(isJiGe.equals("y"))
				JiGe = " and to_number(u.paper_objective_score)+to_number(u.paper_subjective_score)>=to_number(t.pass_score) ";
			if(isJiGe.equals("n"))
				JiGe = " and to_number(u.paper_objective_score)+to_number(u.paper_subjective_score)<to_number(t.pass_score) ";
		}
		String sql = "select u.*,t.pass_score passScore,"
				+ "(select count(1) from xxkh_user_paper u where 1=1 and u.is_submit = '1'  and t.type = '"+testInfo.getType()+"' and u.test_id = '" +testInfo.getId()+"' "+ju+" "+chushi+" "+JiGe+" and u.test_id =t.id) total,"
				+"( select count(1) from xxkh_question_group  q where q.paper_id =u.paper_id and q.question_type = '5' " + 
				") as isZhuGuan , (select pi.name  paperName from xxkh_paper_info pi where  pi.id = u.paper_id) as paperName "
				+ " from "
				+ "xxkh_user_paper u,xxkh_test_info t "
				+ "where u.test_id=t.id "
				+ "and  t.state = '2' "
				+ "and u.is_submit = '1' "
				+ "and t.type = '"+testInfo.getType()+"' "
				+ "and u.test_id = '" +testInfo.getId()+"'"+ju+" "+chushi+" "+JiGe;
		if(StringUtils.isNotBlank(pageImpl.getSortName())) {
		if(pageImpl.getSortName().equals("beginTestTime")) {
			pageImpl.setSortName("begin_test_time");
		}
		if(pageImpl.getSortName().equals("paperSubjectiveScore")) {
			pageImpl.setSortName("paper_subjective_score");
		}
		if(pageImpl.getSortName().equals("paperObjectiveScore")) {
			pageImpl.setSortName("paper_objective_score");
		}
		}
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			sql += "  order by u.begin_test_time desc";
		}else{
			if (pageImpl.getSortName().equals("userName")) {
				pageImpl.setSortName("user_name");
			}
			sql += "  order by u."+pageImpl.getSortName()+" "+pageImpl.getSortOrder();
		}
		int after = pageImpl.getPageNumber()*pageImpl.getPageSize();
		int pre = (pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1;
		sql = "select * from (select s.*,rownum rn from ("+sql+") s  where rownum<="+after+")  where rn>="+pre;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhUserPaper.class));
	}
	
	/** 
	 * TODO 查询个人考试列表（带分页，查询项）
	 * @author 李颖洁  
	 * @date 2018年9月8日下午2:54:23
	 * @param pageImpl
	 * @param info
	 * @param isPass
	 * @return List<XxkhTestInfo>
	 */
	@Override
	public List<XxkhTestInfo> getPersonalTestScore(PageImpl pageImpl, XxkhTestInfo info, String isPass) {
		String querySql = "";
		String conditionSql = "";
		String totalSql = "";
		//过滤条件
		if (!StringUtils.isBlank(info.getType())){
			conditionSql += "   and d.type = '"+info.getType()+"'";
		}
		if (!StringUtils.isBlank(info.getName())){
			conditionSql += "   and d.name like '%"+info.getName()+"%'";
		}
		String Sql = "select distinct t.user_id,t.test_id"	
				+ "		from xxkh_user_paper t  where t.user_id = "+UserUtil.getCruUserId()+" and t.is_submit = "+XXKHCommonConstants.IS_SUBMIT[1]+"  group by t.user_id ,t.test_id 	";
		totalSql += "select count(distinct p.test_id) from ("+Sql+") p,xxkh_test_info d where p.test_id = d.id "+conditionSql;
		//全部列表查询
		querySql += "	select d.id,d.name,d.type,d.start_time,d.end_time,	";
		querySql += "	("+totalSql+") as total	";
		querySql += "	from ("+Sql+") p,xxkh_test_info d 	";
		querySql += "	where p.test_id = d.id "+conditionSql;
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql += "  order by d.cre_time desc";
		}else{
			
			querySql += "  order by d."+pageImpl.getSortName()+" "+pageImpl.getSortOrder();
		}
		int after = pageImpl.getPageNumber()*pageImpl.getPageSize();
		int pre = (pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1;
		String sql2 = "select m.* from (select n.*,rownum rn from ("+querySql+") n where rownum<="+after+") m  where rn>="+pre;
		log.info(sql2);
		List<XxkhTestInfo>  li = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<>(XxkhTestInfo.class));
		return li;
	}

	/** 
	 * TODO 查询个人考试试卷信息列表(不分页)
	 * @author 李颖洁  
	 * @date 2018年10月9日上午9:37:31
	 * @param info
	 * @return List<XxkhUserPaper>
	 */
	@Override
	public List<XxkhUserPaper> getPersonalTestPaper(XxkhTestInfo info) {
		String sql = "select t.user_id,t.test_id,t.paper_id,t.paper_subjective_score,t.paper_objective_score,t.begin_test_time,t.artificial_marking_state,f.pass_score,f.mark_status as markStatus,p.name as paperName,q.question_type as isZhuGuan "
				+ "	from xxkh_user_paper t "
				+ "	left join xxkh_test_info f on t.test_id = f.id "
				+ "	left join xxkh_paper_info p on t.paper_id = p.id "
				+ "	left join xxkh_question_group q on (t.paper_id = q.paper_id and q.question_type = 5) "
				+ "	where t.test_id = '"+info.getId()+"' and t.user_id ="+UserUtil.getCruUserId()+" and t.is_submit = "+XXKHCommonConstants.IS_SUBMIT[1]+"order by t.begin_test_time asc";
		log.info(sql);
		List<XxkhUserPaper> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhUserPaper.class));
		return list;
	}
}
