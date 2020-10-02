package com.sinosoft.sinoep.modules.mypage.worklog.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.worklog.dao.WorkLogDao;
import com.sinosoft.sinoep.modules.mypage.worklog.entity.WorkLog;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WorkLogServiceImp implements WorkLogService{
	@Autowired
	WorkLogDao dao;
	@Autowired
	JdbcTemplate JdbcTemplate;
	 @Autowired
	 private TreeService service;

	/**
	 * 获取分页数据
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String type,String flag,String logType,String dateLogYear,String dateLogMonth,String dateLogWeek,String timeCondition,String content) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from WorkLog t ");
		querySql.append("	where t.creUserId = ? and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		para.add(UserUtil.getCruUserId());
		
		
		
		if (StringUtils.isNotBlank(type)) {
			querySql.append("   and t.type = ?");
			para.add(type);
		}
		if (StringUtils.isNotBlank(logType)) {
			querySql.append("   and t.logType in (" + CommonUtils.commomSplit(logType) +")");
		}
		if (StringUtils.isNotBlank(content)) {
			querySql.append("   and t.content  like '%" + content +"%'");
			//para.add("%" +  +"%");
		}
		if("0".equals(timeCondition)){
			//按年
			if (StringUtils.isNotBlank(dateLogYear)) {
				querySql.append(" and substring(t.dateLog,0,4) = ?");
				para.add(dateLogYear);
			}
		}else if("1".equals(timeCondition)){
			//按月
			if (StringUtils.isNotBlank(dateLogMonth)) {
				querySql.append(" and substring(t.dateLog,0,7) = ?");
				para.add(dateLogMonth);
			}
		}else {
			//按周或自定义
			if (StringUtils.isNotBlank(startDate)) {
				querySql.append("   and t.dateLog >= ?");
				para.add(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				querySql.append("   and t.dateLog <= ?");
				para.add(endDate);
			}
		}
		//拼接排序语句
		if("1".equals(flag)){
			//表示首页上的工作日志列表
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.dateLog desc) ");
			}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
			}
		}else{
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.dateLog desc,t.creTime desc) ");
			}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
			}
		}
		
        Page<WorkLog> page = dao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<WorkLog> content1 = page.getContent();
        for (WorkLog info : content1) {
        		info.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}

	/**
	 * 
	 * TODO 保存
	 * @author 马秋霞
	 * @Date 2018年5月4日 上午9:44:29
	 * @param info
	 * @return
	 */
	@Override
	public WorkLog saveForm(WorkLog info,String flag) {
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		if(StringUtils.isBlank(info.getId())){
			info.setCreTime(creTime);
			info.setCreUserId(UserUtil.getCruUserId());
			info.setCreUserName(UserUtil.getCruUserName());
			info.setCreDeptId(UserUtil.getCruDeptId());
			info.setCreDeptName(UserUtil.getCruDeptName());
			info.setUpdateTime(creTime);
			info.setUpdateUserId(UserUtil.getCruUserId());
			info.setUpdateUserName(UserUtil.getCruUserName());
			info.setVisible("1");
			if("1".equals(flag)){
				//上报
				info.setState("1");
				info.setReportTime(creTime);
				info.setReportUserId(UserUtil.getCruUserId());
				info.setReportUserName(UserUtil.getCruDeptName());
			}else{
				//保存
				info.setState("0");
			}
			
			info=dao.save(info);
		}else{
			WorkLog log=dao.findOne(info.getId());
			log.setUpdateTime(creTime);
			if("1".equals(flag)){
				//上报
				log.setState("1");
				log.setReportTime(creTime);
				log.setReportUserId(UserUtil.getCruUserId());
				log.setReportUserName(UserUtil.getCruDeptName());
			}
			log.setUpdateUserId(UserUtil.getCruUserId());
			log.setUpdateUserName(UserUtil.getCruUserName());
			log.setDateLog(info.getDateLog());
			log.setType(info.getType());
			log.setContent(info.getContent());
			log.setLogType(info.getLogType());
			info=dao.save(log);
		}
		return info;
	}
	
	/**
	 * 根据id查询一条记录
	 */
	@Override
	public WorkLog getById(String id) {
		return dao.findOne(id);
	}
	
	/**
	 * 删除一条记录
	 */
	@Override
	public int delete(String id) {
		String jpql = "update WorkLog t set t.visible = ? where t.id = ?";
		return dao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * 上报
	 */
	@Override
	public int shangBao(String id) {
		String jpql = "update WorkLog t set t.type='1',t.state = ? where t.id = ?";
		return dao.update(jpql, "1", id);
	}
	
	/**
	 * 根据日期查询是否有该条数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月8日 下午3:24:43
	 * @param dateLog
	 * @return
	 */
	@Override
	public List findByDateLog(String dateLog,String id) {
		
		StringBuilder sb=new StringBuilder("SELECT t.id,t.date_log dateLog,t.content,t.log_type logType FROM work_log  t ");
		sb.append(" where t.visible='1' and t.CRE_USER_ID='" + UserUtil.getCruUserId() +"' and t.date_log ='" + dateLog + "'");
		if(!StringUtils.isBlank(id)){
			sb.append(" and id!='" +id+ "'");
		}
		List<Map<String,Object>> list=JdbcTemplate.queryForList(sb.toString());
		return list;
	}
	
	/**
	 * 获取首页上工作日志的前10条数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月8日 下午5:00:25
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getPageList() {
		StringBuilder sb=new StringBuilder("  select rownum,temp.* from(  ");
		sb.append("  SELECT t.id,t.date_log dateLog,t.content,t.state,t.log_type logTyp  FROM work_log  t ");
		sb.append("  WHERE t.cre_user_id='" +UserUtil.getCruUserId()+ "' and t.visible='" + CommonConstants.VISIBLE[1] + "'  ORDER BY t.date_log   DESC,t.cre_time desc  ");
		sb.append(" ) temp where rownum<6 ");
		//List<Map<String,Object>> list=JdbcTemplate.queryForList(sb.toString());t.visible = '"+CommonConstants.VISIBLE[1]+"'"
		List<Map<String,Object>> list=JdbcTemplate.queryForList(sb.toString());
		 for (Map map : list) {
			 map.put("cz", "CommonConstants.OPTION_DELETE");
		}
		return list;
	}

	/**
	 * 获取领导查询工作日志的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月15日 下午2:52:44
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 */
	@Override
	public PageImpl getLeaderShowList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String type,String persionId,String logType,String dateLogYear,String dateLogMonth,String dateLogWeek,String timeCondition,String content) {
		ArrayList<String> list=new ArrayList();
		//获取当前用户所在部门的所有子部门
		JSONArray depts=getDeptTree(UserUtil.getCruDeptId());
		for (Object object : depts) {
			JSONObject jsonObj=JSONObject.fromObject(object);
			list.add(jsonObj.getString("uuid"));
		}
		String deptStr = StringUtils.join(list, ",");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from WorkLog t ");
		querySql.append("	where t.creDeptId in (" + deptStr +") and t.state='1' and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if (StringUtils.isNotBlank(type)) {
			querySql.append("   and t.type = ?");
			para.add(type);
		}
		if (StringUtils.isNotBlank(persionId)) {
			querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(persionId) + ")");
		}
		if (StringUtils.isNotBlank(logType)) {
			querySql.append("   and t.logType in (" + CommonUtils.commomSplit(logType) +")");
		}
		if (StringUtils.isNotBlank(content)) {
			querySql.append("   and t.content  like '%" + content +"%'");
		}
		if("0".equals(timeCondition)){
			//按年
			if (StringUtils.isNotBlank(dateLogYear)) {
				querySql.append(" and substring(t.dateLog,0,4) = ?");
				para.add(dateLogYear);
			}
		}else if("1".equals(timeCondition)){
			//按月
			if (StringUtils.isNotBlank(dateLogMonth)) {
				querySql.append(" and substring(t.dateLog,0,7) = ?");
				para.add(dateLogMonth);
			}
		}else {
			//按周或自定义
			if (StringUtils.isNotBlank(startDate)) {
				querySql.append("   and t.dateLog >= ?");
				para.add(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				querySql.append("   and t.dateLog <= ?");
				para.add(endDate);
			}
		}
		//拼接排序语句
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.dateLog desc,t.creTime desc) ");
			}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
			}
        Page<WorkLog> page = dao.query(querySql.toString(), pageable,para.toArray());
        
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
     * 获取该部门下的所有子部门
     * @param deptId 部门id
     * @param level 级别，从1开始，即查询deptId下几级的部门
     * @return
     */

    public JSONArray getDeptTree(String deptId) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(deptId)) {
        	 tree = service.getDeptTreeByDeptId(deptId);
        }
        return tree;
    }

	@Override
	public JSONObject getWeekDate(String date) throws ParseException {
		return DateUtil.getByDate(date);
	}
}
