package com.sinosoft.sinoep.modules.zhbg.xxkh.gradesearch.services;

import java.util.ArrayList;
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
		//防止sql注入 王磊 20190425
		List<Object> list = new ArrayList<>();
		String ju = "";
		if(org.apache.commons.lang3.StringUtils.isNotBlank(testInfo.getCreJuId())) {
			ju = " and t.cre_ju_id = ? ";
		}
		String name = "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(testInfo.getName())) {
			name = " and t.name like ? ";
		}
		String times = "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(timeRange)) {
			times = " and start_time<= ? and end_time >= ? ";
		}
		String sql = "select s.* from "+
				" (select s.*,u.sumPeople, (select count(1) from xxkh_test_info t where t.state = '2'   and t.type = ? "+name+" "+times+" "+ju+") as total   from "+
				" (select s.*,u.passPeople from " + 
				//" (select s.*,u.avgSvore from " + 
				" (select s.*,\"NVL\"(u.passPeople, '0') as submitPeople from " + 
				" (select t.*,u.maxScore,u.minScore from " + 
				" (select t.id,t.name,t.full_score,t.start_time,t.end_time from xxkh_test_info t where t.state = '2'   and t.type = ?  "+name+" "+times+" "+ju+") t " + 
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
		//ju,name,times在上边sql用到两次，这里用循环来动态给list添加参数
		for(int i=1;i<=2;i++){
			list.add(testInfo.getType());
			if(!"".equals(ju)){
				list.add(testInfo.getCreJuId());
			}
			if(!"".equals(name)){
				list.add("%"+testInfo.getName()+"%");
			}
			if(!"".equals(times)){
				String[] strings = timeRange.split(" - ");
				String startTime =  strings[0];
				String endTime =  strings[1];
				list.add(startTime);
				list.add(endTime);
			}
		}
		sql = "select s.*,rownum rn from ("+sql+") s";
		list.add(pageImpl.getPageNumber()*pageImpl.getPageSize());
		list.add((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
		sql = "select s.* from ("+sql+"  where rownum<= ? ) s where rn>= ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhTestInfo.class),list.toArray());
	}

	@Override
	public List<XxkhUserPaper> getbyTestContent(PageImpl pageImpl, XxkhTestInfo testInfo,String isJiGe) {
		//防止sql注入 王磊 20190426
		List<Object> list = new ArrayList<>();
		String ju = "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(testInfo.getCreJuName())) {
			if(testInfo.getCreJuName().indexOf(",")==-1) {
				ju = "  and u.candidate_ju_name = ? ";
			}else {
				String[] strings = testInfo.getCreJuName().split(",");
				ju =" and (";
				for(String juName : strings) {
					ju +=" u.candidate_ju_name = ?  or";
				}
				ju = ju.substring(0, ju.length()-2)+")";
			}
		}
		String chushi= "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(testInfo.getTestChuShiNames())) {
			if(testInfo.getTestChuShiNames().indexOf(",")==-1) {
				chushi = "  and u.candidate_chushi_name = ? ";
			}else {
				String[] strings = testInfo.getTestChuShiNames().split(",");
				chushi =" and (";
				for(String chushiname : strings) {
					chushi +=" u.candidate_chushi_name = ? or";
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
				+ "(select count(1) from xxkh_user_paper u where 1=1 and u.is_submit = '1'  and t.type = ? and u.test_id = ? "+ju+" "+chushi+" "+JiGe+" and u.test_id =t.id) total,"
				+"( select count(1) from xxkh_question_group  q where q.paper_id =u.paper_id and q.question_type = '5' " + 
				") as isZhuGuan , (select pi.name  paperName from xxkh_paper_info pi where  pi.id = u.paper_id) as paperName "
				+ " from "
				+ "xxkh_user_paper u,xxkh_test_info t "
				+ "where u.test_id=t.id "
				+ "and  t.state = '2' "
				+ "and u.is_submit = '1' "
				+ "and t.type = ? "
				+ "and u.test_id = ? "+ju+" "+chushi+" "+JiGe;
		//testInfo.getId()+"'"+ju+" "+chushi+" "+JiGe;等在sql中出现两次，这里根据循环来拼接参数
		for(int i=1;i<=2;i++){
			list.add(testInfo.getType());
			list.add(testInfo.getId());
			if(!"".equals(ju)){
				if(testInfo.getCreJuName().indexOf(",")==-1) {
					list.add(testInfo.getCreJuName());
				}else{
					String[] strings = testInfo.getCreJuName().split(",");
					for(String s : strings){
						list.add(s);
					}
				}
				
			}
			
			if(!"".equals(chushi)){
				if(testInfo.getTestChuShiNames().indexOf(",")==-1) {
					list.add(testInfo.getTestChuShiNames());
				}else{
					String[] strings = testInfo.getTestChuShiNames().split(",");
					for(String s : strings){
						list.add(s);
					}
				}
				
			}
			
		}
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
		list.add(pageImpl.getPageNumber()*pageImpl.getPageSize());
		list.add((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
		sql = "select * from (select s.*,rownum rn from ("+sql+") s  where rownum<= ? )  where rn>= ? ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhUserPaper.class),list.toArray());
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
			conditionSql += "   and d.type = ? ";
		}
		if (!StringUtils.isBlank(info.getName())){
			conditionSql += "   and d.name like ? ";
		}
		String Sql = "select distinct t.user_id,t.test_id"	
				+ "		from xxkh_user_paper t  where t.user_id = '"+UserUtil.getCruUserId()+"' and t.is_submit = '"+XXKHCommonConstants.IS_SUBMIT[1]+"'  group by t.user_id ,t.test_id 	";
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
		//防止sql注入 王磊20190426
		//conditionSql 用到两次，用循环来接拼接sql
		List<Object> list = new ArrayList<>();
		for(int i=1;i<=2;i++){
			if (!StringUtils.isBlank(info.getType())){
				list.add(info.getType());
			}
			if (!StringUtils.isBlank(info.getName())){
				list.add("%"+info.getName()+"%");
			}
		}
		list.add(pageImpl.getPageNumber()*pageImpl.getPageSize());
		list.add((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
		String sql2 = "select m.* from (select n.*,rownum rn from ("+querySql+") n where rownum<= ? ) m  where rn>= ? ";
		log.info(sql2);
		List<XxkhTestInfo>  li = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<>(XxkhTestInfo.class),list.toArray());
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
				+ "	where t.test_id = ? and t.user_id ='"+UserUtil.getCruUserId()+"' and t.is_submit = '"+XXKHCommonConstants.IS_SUBMIT[1]+"'order by t.begin_test_time asc";
		log.info(sql);
		List<XxkhUserPaper> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhUserPaper.class),info.getId());
		return list;
	}
}
