package com.sinosoft.sinoep.modules.zhbg.kqgl.wc.services;

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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.dao.GoOutInfoDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.entity.GoOutInfo;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 业务层的service实现
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月17日 上午10:18:54
 */
@Service
public class GoOutInfoServiceImp implements GoOutInfoService{
	
	@Autowired
	private GoOutInfoDao dao;
	 @Autowired
	 private TreeService service;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 查询草稿列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 上午10:18:19
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String creUserName, String applicantUnitName,
			String startDate, String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from GoOutInfo t ");
		querySql.append("	where t.creUserId = ? and t.subflag = ? and t.visible = '"+CommonConstants.VISIBLE[1]+"' ");
		//拼接条件
		para.add(UserUtil.getCruUserId());
		para.add(subflag);
		
		if (StringUtils.isNotBlank(creUserName)) {
			querySql.append("   and t.creUserName like ?");
			para.add("%"+creUserName+"%");
		}
		if (StringUtils.isNotBlank(applicantUnitName)) {
			querySql.append("   and t.applicantUnitName like ?");
			para.add("%"+applicantUnitName+"%");
		}
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append("   and (t.goOutStartDate <= '" + startDate + "'  and t.goOutEndDate >='" + startDate +"'");
			querySql.append("   or t.goOutStartDate <= '" + endDate + "'  and t.goOutEndDate >='" + endDate +"'");
			querySql.append("	or t.goOutStartDate >='" + startDate + "'  and t.goOutEndDate<='" + endDate + "')");
		}
		/*if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   or (t.goOutStartDate <= '" + endDate + "'  and t.goOutEndDate >='" + endDate +"')");
			querySql.append("   or t.goOutEndDate <= ?");
			para.add(endDate);
		}*/
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
        Page<GoOutInfo> page = dao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<GoOutInfo> content = page.getContent();
        for (GoOutInfo info : content) {
        	if(ConfigConsts.START_FLAG.equals(info.getSubflag())) {
        		info.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        	}
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 保存方法
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 下午2:14:51
	 * @param info
	 * @param ideaName
	 * @return
	 */
	@Override
	public GoOutInfo saveForm(GoOutInfo info) {
		info.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		info.setUpdateUserId(UserUtil.getCruUserId());
		info.setUpdateUserName(UserUtil.getCruUserName());
		if(StringUtils.isBlank(info.getId())) {
			//新增
			info.setCreTime(creTime);
			info.setCreUserId(UserUtil.getCruUserId());
			info.setCreUserName(UserUtil.getCruUserName());
			info.setCreDeptId(UserUtil.getCruDeptId());
			info.setCreDeptName(UserUtil.getCruDeptName());
			String chushiId = UserUtil.getCruChushiId();
			info.setCreChushiId(chushiId);
			info.setCreChushiName(UserUtil.getCruChushiName());
			info.setCreJuId(UserUtil.getCruJuId());
			info.setCreJuName(UserUtil.getCruJuName());
			//info.setApplicationTime(creTime);
			info.setVisible("1");
			info.setApplicantUnitId(UserUtil.getCruDeptId());
			info.setApplicantUnitName(UserUtil.getCruDeptName());
			info = dao.save(info);
		}else {
			//修改
			GoOutInfo oldinfo = dao.findOne(info.getId());
			oldinfo.setGoOutDate(info.getGoOutDate());
			oldinfo.setStartStopTime(info.getStartStopTime());
			oldinfo.setGoOutLongTime(info.getGoOutLongTime());
			oldinfo.setDestination(info.getDestination());
			oldinfo.setGoOutReason(info.getGoOutReason());
			oldinfo.setUpdateTime(creTime);
			dao.save(oldinfo);
		}
		
		return info;
	}
	
	/**
	 * 根据主键ID查询一条数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 下午3:51:57
	 * @param id
	 * @return
	 */
	@Override
	public GoOutInfo getById(String id) {
		return dao.findOne(id);
	}
	
	/**
	 * 根据id删除一条外出记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 下午4:38:59
	 * @param id
	 * @return
	 */
	@Override
	public int deleteGoOutInfo(String id) {
		String jpql = "update GoOutInfo t set t.visible = ? where t.id = ?";
		return dao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * 更新业务表的流程状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 上午9:26:24
	 * @param id
	 * @param subflag
	 * @return
	 */
	@Override
	public GoOutInfo updateSubFlag(String id, String subflag) {
		GoOutInfo info = getById(id);
		info.setSubflag(subflag);
		return dao.save(info);
		
	}
	
	/**
	 * 查询外出管理审批列表的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 下午1:35:57
	 * @param pageable
	 * @param pageImpl
	 * @param creUserName
	 * @param creDeptName
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String creUserName, String applicantUnitName,
			String startDate, String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select new com.sinosoft.sinoep.modules.zhbg.kqgl.wc.entity.GoOutInfo(t.id, t.goOutTitle, t.creUserName,t.applicantUnitName, "
			+ "t.creDeptName, t.goOutStartDate, t.goOutEndDate,t.startAmOrPm,t.endAmOrPm,t.goOutLongTime,t.destination,t.subflag)");
		querySql.append("	from GoOutInfo t, FlowRead f ");
		querySql.append("	where f.userId = ? ");
		querySql.append("	and t.id = f.recordId and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(subflag) ){
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}
		if (StringUtils.isNotBlank(creUserName)) {
			querySql.append("   and t.creUserName like ?");
			para.add("%"+creUserName+"%");
		}
		if (StringUtils.isNotBlank(applicantUnitName)) {
			querySql.append("   and t.applicantUnitName like ?");
			para.add("%"+applicantUnitName+"%");
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.goOutStartDate >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.goOutEndDate <= ?");
			para.add(endDate);
		}
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
        Page<GoOutInfo> page = dao.query(querySql.toString(), pageable,para.toArray());
		return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 根据外出的开始时间，和结束时间统计出外出时长
	 * @throws ParseException 
	 */
	@Override
	public String getGoOutTime(String startDate, String endDate, String startAmOrPm, String endAmOrPm) throws Exception {
		double cnt = 0;
		//获取从开始日期到结束日期的节假日数据
		List<Map<String, Object>> list = getHolidaySets(startDate, endDate);
		if (startDate.equals(endDate)) {
			//如果开始日期和结束日期为同一天，只需统计当天的外出天数
			cnt = cnt + getGoOutDay(list, startDate, startAmOrPm, endAmOrPm);
		} else {
			//开始日期和结束日期不为同一天，统计从开始日期到结束日期的所有外出天数
			cnt = cnt + getGoOutDays(list, startDate, endDate,startAmOrPm, endAmOrPm);
		}
		return cnt + "";
	}
	/**
	 * 获取某个时间段的请假天数
	 * @param list
	 * @param startDate
	 * @param endDate
	 * @param startAmOrPm
	 * @param endAmOrPm
	 * @return
	 * @throws ParseException 
	 */
	private double getGoOutDays(List<Map<String, Object>> list, String startDate, String endDate, String startAmOrPm,
			String endAmOrPm) throws ParseException {
		double cnt=0;
		List<String> days=DateUtil.getBetweenDates(startDate, endDate);
		for (String day : days) {
			cnt = cnt + getStartGoOutDay(list, day);
		}
		//算开始外出哪天的外出时长
		cnt = cnt + getStartGoOutDay(list, startDate, startAmOrPm);
		//统计结束日期哪天的工作时长
		cnt = cnt + getEndGoOutDay(list, endDate, endAmOrPm);
		return cnt;
	}
	/**
	 * 算出某一天的请假时长
	 * @param list
	 * @param day
	 * @return
	 * @throws ParseException 
	 */
	private double getStartGoOutDay(List<Map<String, Object>> list, String day) throws ParseException {
		double cnt=0;
		if(list.size()<=0){
			//年假设置里面没有数据
			int week=DateUtil.getWeekDay(sdf.parse(day));
			if(week!=6 && week!=7){
				cnt++;
			}
		}else{
			boolean flag=false;
			for (Map<String, Object> map : list) {
				if(day.equals(map.get("date1"))){
					//在年假日设置里找到 了数据
					flag=true;
					if("0".equals(map.get("isholiday"))){
						//是工作日
						cnt++;
					}
					break;
				}
			}
			if(!flag){
				//遍历一一遍节假日设置数据，发现没有该天的数据
				int week=DateUtil.getWeekDay(sdf.parse(day));
				if(week!=6 && week!=7){
					cnt++;
				}
			}
			
		}
		return cnt;
	}

	/**
	 * 算外出结束日期的外出时长
	 * @param list
	 * @param endDate
	 * @param endAmOrPm
	 * @return
	 * @throws ParseException 
	 */
	private double getEndGoOutDay(List<Map<String, Object>> list, String endDate, String endAmOrPm) throws ParseException {
		double cnt=0;
		if(list.size()<=0){
			//年假设置里面没有数据
			int week=DateUtil.getWeekDay(sdf.parse(endDate));
			if(week!=6 && week!=7){
				if("0".equals(endAmOrPm)){
					cnt=cnt+1;
				}else{
					cnt=cnt+0.5;
				}
			}
		}else{
			boolean flag=false;
			for (Map<String, Object> map : list) {
				if(endDate.equals(map.get("date1"))){
					//在年假日设置里找到 了数据
					flag=true;
					if("0".equals(map.get("isholiday"))){
						//是工作日
						if("0".equals(endAmOrPm)){
							cnt=cnt+1;
						}else{
							cnt=cnt+0.5;
						}
					}
					break;
				}
			}
			if(!flag){
				//遍历一一遍节假日设置数据，发现没有该天的数据
				int week=DateUtil.getWeekDay(sdf.parse(endDate));
				if(week!=6 && week!=7){
					if("0".equals(endAmOrPm)){
						cnt=cnt+1;
					}else{
						cnt=cnt+0.5;
					}
				}
			}
			
		}
		return cnt;
	}

	/**
	 * 算开始外出那天的外出时长
	 * @param list
	 * @param startDate
	 * @param startAmOrPm
	 * @return
	 * @throws ParseException 
	 */
	private double getStartGoOutDay(List<Map<String, Object>> list, String startDate, String startAmOrPm) throws ParseException {
		double cnt=0;
		if(list.size()<=0){
			//年假设置里面没有数据
			int week=DateUtil.getWeekDay(sdf.parse(startDate));
			if(week!=6 && week!=7){
				if("1".equals(startAmOrPm)){
					cnt=cnt+1;
				}else{
					cnt=cnt+0.5;
				}
			}
		}else{
			boolean flag=false;
			for (Map<String, Object> map : list) {
				if(startDate.equals(map.get("date1"))){
					//在年假日设置里找到 了数据
					flag=true;
					if("0".equals(map.get("isholiday"))){
						//是工作日
						if("1".equals(startAmOrPm)){
							cnt=cnt+1;
						}else{
							cnt=cnt+0.5;
						}
					}
					break;
				}
			}
			if(!flag){
				//遍历一一遍节假日设置数据，发现没有该天的数据
				int week=DateUtil.getWeekDay(sdf.parse(startDate));
				if(week!=6 && week!=7){
					if("1".equals(startAmOrPm)){
						cnt=cnt+1;
					}else{
						cnt=cnt+0.5;
					}
				}
			}
			
		}
		return cnt;
	}

	/**
	 * 如果外出开始日期和结束日期为同一天，算是他的外出时长
	 * @param list
	 * @param startDate
	 * @param startAmOrPm
	 * @param endAmOrPm
	 * @return
	 * @throws ParseException 
	 */
	private double getGoOutDay(List<Map<String, Object>> list, String tempDate, String startAmOrPm, String endAmOrPm) throws ParseException {
		double cnt=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(list.size()<=0){
			//年假设置里面没有数据
			int week=DateUtil.getWeekDay(sdf.parse(tempDate));
			if(week!=6 && week!=7){
				if(startAmOrPm.equals(endAmOrPm)){
					cnt=0.5;
				}else{
					cnt=1;
				}
			}
		}else{
			boolean flag=false;
			for (Map<String, Object> map : list) {
				if(tempDate.equals(map.get("date1"))){
					//在年假日设置里找到 了数据
					flag=true;
					if("0".equals(map.get("isholiday"))){
						//是工作日
						if(startAmOrPm.equals(endAmOrPm)){
							cnt=0.5;
						}else{
							cnt=1;
						}
					}
					break;
				}
			}
			if(!flag){
				//遍历一一遍节假日设置数据，发现没有该天的数据
				int week=DateUtil.getWeekDay(sdf.parse(tempDate));
				if(week!=6 && week!=7){
					if(startAmOrPm.equals(endAmOrPm)){
						cnt=0.5;
					}else{
						cnt=1;
					}
				}
			}
			
		}
		return cnt;
	}

	/**
	 * 查询某段时间内，节假日设置里是否有数据
	 */
	private List<Map<String,Object>> getHolidaySets(String startDate,String endDate){
		StringBuilder sb=new StringBuilder("select  t.now_date date1,t.is_holiday isholiday  from KQGL_HOLIDAY_SET_INFO t  ");
		sb.append(" where to_date(t.NOW_DATE,'yyyy-mm-dd')>=to_date('" + startDate + "','yyyy-mm-dd') ");
		sb.append(" and to_date(t.NOW_DATE,'yyyy-mm-dd')<=to_date('" + endDate + "','yyyy-mm-dd')");
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sb.toString());
		return list;
		
	}
	
	/**
	 *  根据查询条件获取当前用户的外出信息的记录条数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午2:34:14
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public long getCnt(String userids,String startDate, String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select count(id) cnt from kqgl_go_out_info t  where t.visible = '" + CommonConstants.VISIBLE[1] + "' ");
		if (StringUtils.isNotBlank(userids)) {
			querySql.append("   and t.cre_user_id  in  (" + CommonUtils.commomSplit(userids) +")");
		}else{
			//查询当前用户下的子部门有哪些
			String childDept = getChildDept();
			querySql.append("   and t.CRE_DEPT_ID in (" + childDept + ")");
		}
		// 拼接条件
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append("   and (t.go_out_start_date <= '" + startDate + "'  and t.go_out_end_date >='" + startDate +"'");
			querySql.append("   or t.go_out_start_date <= '" + endDate + "'  and t.go_out_end_date >='" + endDate +"'");
			querySql.append("	or t.go_out_start_date >='" + startDate + "'  and t.go_out_end_date<='" + endDate + "')");
		}
		
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = '" + subflag + "'");
		}else{
			querySql.append("   and t.subflag in ('" + ConfigConsts.APPROVAL_FLAG + "','" + ConfigConsts.SUB_FLAG + "')");
		}
		long cnt=jdbcTemplate.queryForObject(querySql.toString(), Long.class);
		return cnt;
	}
	
	/**
	 * 根据查询条件获取当前用户的外出信息的记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午3:19:44
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public List<GoOutInfo> getGoOutList(String userids,String startDate, String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select t.* from kqgl_go_out_info t  where t.visible = '" + CommonConstants.VISIBLE[1] + "' ");
		if (StringUtils.isNotBlank(userids)) {
			querySql.append("   and t.cre_user_id  in  (" + CommonUtils.commomSplit(userids) +")");
		}else{
			//查询当前用户下的子部门有哪些
			String childDept = getChildDept();
			querySql.append("   and t.CRE_DEPT_ID in (" + childDept + ")");
		}
		// 拼接条件
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append("   and (t.go_out_start_date <= '" + startDate + "'  and t.go_out_end_date >='" + startDate +"'");
			querySql.append("   or t.go_out_start_date <= '" + endDate + "'  and t.go_out_end_date >='" + endDate +"'");
			querySql.append("	or t.go_out_start_date >='" + startDate + "'  and t.go_out_end_date<='" + endDate + "')");
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = '" + subflag + "'");
		}else{
			querySql.append("   and t.subflag in ('" + ConfigConsts.APPROVAL_FLAG + "','" + ConfigConsts.SUB_FLAG + "')");
		}
		querySql.append("  order by t.cre_time desc");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(GoOutInfo.class));
	}
	
	/**
	 * 查询某个人某天的外出记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月24日 下午1:12:39
	 * @param userId
	 * @param day
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDataByUseridAndDay(String userId, String day) {
		StringBuilder waiChuSql = new StringBuilder("select t.go_out_start_date startDate,t.go_out_end_date endDate,t.start_am_or_pm startAmOrPm,t.end_am_or_pm endAmOrPm  from kqgl_go_out_info t ");
		waiChuSql.append(" where t.cre_user_id = '" +  userId + "' and t.go_out_start_date>='" + day + "' and t.go_out_end_date<='" + day + "' and t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' ");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(waiChuSql.toString());
		return list;
	}
	
	/**
	 * 获取当前用户所在部门的子部分
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月19日 下午1:41:16
	 */
	@SuppressWarnings("unused")
	private String getChildDept() {
		String dept = UserUtil.getCruDeptId();
		JSONArray depts = service.getDeptTreeByDeptId(UserUtil.getCruDeptId());
		StringBuilder sb=new StringBuilder();
		for (Object object : depts) {
			JSONObject jsonObj=JSONObject.fromObject(object);
			sb.append(jsonObj.getString("uuid")+",");
		}
		String childDept = CommonUtils.commomSplit(sb.toString());
		return childDept;
	}
}
