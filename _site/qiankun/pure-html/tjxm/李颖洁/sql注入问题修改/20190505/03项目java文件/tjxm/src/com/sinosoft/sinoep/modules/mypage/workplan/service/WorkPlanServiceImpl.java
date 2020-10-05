package com.sinosoft.sinoep.modules.mypage.workplan.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.modules.mypage.workplan.dao.WorkPlanDao;
import com.sinosoft.sinoep.modules.mypage.workplan.entity.DayStatus;
import com.sinosoft.sinoep.modules.mypage.workplan.entity.WorkPlan;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.util.MyUserUtil;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *节假日设置的service层
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月10日 下午5:53:26
 */
@Service
public class WorkPlanServiceImpl implements WorkPlanService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	WorkPlanDao dao;
	@Autowired
	private TreeService service;
	/**
	 * 获取分页数据
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String isFinish,String flag,String planType,String dateLogYear,String dateLogMonth,String dateLogWeek,String timeCondition,String content) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from WorkPlan t ");
		querySql.append("	where t.creUserId = ? and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(content)) {
			querySql.append("   and t.content  like ? ");
			para.add("%" + content + "%");
		}
		if (StringUtils.isNotBlank(isFinish)) {
			querySql.append("   and t.isFinish = ?");
			para.add(isFinish);
		}
		if (StringUtils.isNotBlank(planType)) {
			querySql.append("   and t.planType in (" + CommonUtils.solveSqlInjectionOfIn(planType,para) +")");
		}
		if("0".equals(timeCondition)){
			//按年
			if (StringUtils.isNotBlank(dateLogYear)) {
				querySql.append(" and substring(t.datePlan,0,4) = ?");
				para.add(dateLogYear);
			}
		}else if("1".equals(timeCondition)){
			//按月
			if (StringUtils.isNotBlank(dateLogMonth)) {
				querySql.append(" and substring(t.datePlan,0,7) = ?");
				para.add(dateLogMonth);
			}
		}else {
			//按周或自定义
			if (StringUtils.isNotBlank(startDate)) {
				querySql.append("   and t.datePlan >= ?");
				para.add(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				querySql.append("   and t.datePlan <= ?");
				para.add(endDate);
			}
		}
		//拼接排序语句
		if("1".equals(flag)){
			//表示首页上的工作日志列表
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.datePlan desc) ");
			}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+" ");
			}
		}else{
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.datePlan desc,t.updateTime desc ");
			}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+" ");
			}
		}
		
        Page<WorkPlan> page = dao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<WorkPlan> content1 = page.getContent();
        for (WorkPlan info : content1) {
        	if("0".equals(info.getIsFinish())){
        		info.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE+","+CommonConstants.OPTION_PUBLISH);
        	}else{
        		info.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        	}
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		querySql.append("	from WorkPlan t ");
		querySql.append("	where t.creDeptId in (" + deptStr +") and t.isFinish='1' and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if (StringUtils.isNotBlank(type)) {
			querySql.append("   and t.type = ? ");
			para.add(type);
		}
		if (StringUtils.isNotBlank(persionId)) {
			querySql.append("   and t.creUserId in (" + CommonUtils.solveSqlInjectionOfIn(persionId,para) + ")");
		}
		if (StringUtils.isNotBlank(logType)) {
			querySql.append("   and t.planType in (" + CommonUtils.solveSqlInjectionOfIn(persionId,para) +")");
		}else{
			querySql.append("   and t.planType ='' ");
		}
		if (StringUtils.isNotBlank(content)) {
			querySql.append("   and t.content  like ? ");
			para.add("%" + content +"%");
		}
		if("0".equals(timeCondition)){
			//按年
			if (StringUtils.isNotBlank(dateLogYear)) {
				querySql.append(" and substring(t.datePlan,0,4) = ?");
				para.add(dateLogYear);
			}
		}else if("1".equals(timeCondition)){
			//按月
			if (StringUtils.isNotBlank(dateLogMonth)) {
				querySql.append(" and substring(t.datePlan,0,7) = ?");
				para.add(dateLogMonth);
			}
		}else {
			//按周或自定义
			if (StringUtils.isNotBlank(startDate)) {
				querySql.append("   and t.datePlan >= ?");
				para.add(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				querySql.append("   and t.datePlan <= ?");
				para.add(endDate);
			}
		}
		//拼接排序语句
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.datePlan desc,t.creTime desc) ");
			}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
			}
        Page<WorkPlan> page = dao.query(querySql.toString(), pageable,para.toArray());
        
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	/**
	 * 获取日历数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:28:12
	 * @param request
	 * @param year
	 * @param month
	 * @return
	 */
	@Override
	public DayStatus[][] getList(HttpServletRequest request, Integer year, Integer month) {
		//获取计划和日志里某年某月的数据
		List<Map<String,Object>> list = getHolidaySetInfo(year, month!=12?(month+1):1);
		Calendar ca = new GregorianCalendar(year, month, 1);
		   int month_count; //一个月的天数
		    DayStatus[][] arr = new DayStatus[6][7];
		   
	        int before = ca.get(Calendar.DAY_OF_WEEK)-1; //得到月历前的空白天数
			if(before == 0){
				before = 7;
			}
	        if(ca.get(Calendar.MONTH)==Calendar.DECEMBER){
	            ca.add(Calendar.YEAR,1);
	        }
	        ca.roll(Calendar.MONTH,1); //滚动一个月后
		       /* int s=ca.get(Calendar.MONTH);
		        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        System.out.println(sdf.format(ca.getTime()));*/
	        int next_before = ca.get(Calendar.DAY_OF_WEEK)-1;
			if(next_before == 0){
				next_before = 7;
			}
	        int after;
	        if (next_before == 1) {
	            after = 7;
	        } else {
	            after = next_before - 1;
	        } //得到当前时间

	        /* 获取月天数 ,考虑闰年的2月 */
	        if (before <= after) {
	            int temp = after - before + 1;
	            if (temp < 7) {
	                month_count = temp + 28;
	            } else {
	                month_count = 28;
	            }
	        } else {
	            int temp = 8 - before + after;
	            if (temp < 7) {
	                month_count = temp + 28;
	            } else {
	                month_count = 28;
	            }
	        }

	        //         System.out.println ("月天数="+month_count); // 检查月天数
	        //         开始赋值
	        
	        int temp_day = 9 - before; //第二行 起始的日期数

	        //         System.out.println ("before="+before); // test
	        //         System.out.println ("after="+after);
	        //        第一行的处理
	        //获取某个月的最后几天
	        List<Integer> beforeDays= getLastDaysByDate(year, month,before);
	        for (int k = 0; k < before - 1; k++) {
	        		DayStatus dayStatus=new DayStatus(request,year, (month-1), beforeDays.get(k));
	        		dayStatus.setValidate(false);
	        		setIsWorkDay(dayStatus,list);
	        		arr[0][k] = dayStatus;
	        } //为前面的几个赋值空格

	        for (int m = 1; m < 9 - before; m++) {
	        	DayStatus dayStatus=new DayStatus(request,year, month, m);
	        	//设置该天是否有计划或者日志
	        	setIsWorkDay(dayStatus,list);
	        	arr[0][before - 2 + m] = dayStatus;
	        }

	        /* 检查第一行 完成 无误 */
	        //获取某个月的前几天
	       // List<Integer> lastDays=getBeforeDaysByDate(year, month,before);
	        Integer day;
	        //        接下来的处理
	        int cnt=0;
	        for (int i = 1; i < 6; i++) {
	            for (int j = 0; j < 7; j++) {
	                if (temp_day <= month_count) {
	                	DayStatus dayStatus=new DayStatus(request,year,month,temp_day);
	                	setIsWorkDay(dayStatus,list);
	                	arr[i][j] = dayStatus;
	                    temp_day++;
	                } else {
	                	 day= getBeforeDaysByDate(year, month,cnt);
	                	DayStatus dayStatus=new DayStatus(request,year,(month+1),day);
	                	dayStatus.setValidate(false);
	                	setIsWorkDay(dayStatus,list);
	                		arr[i][j] = dayStatus;
	                		cnt++;
	                }
	            }
	        }

	        /*
	         * System.out.println ("检查全部数组"); for(int t=0;t <6;t++){ for(int i=0;i
	         * <7;i++){ DayStatus d=arr[t][i]; System.out.println (d.getDay()); } }
	         */
	        return arr; //返回 日历数组
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
	public WorkPlan saveForm(WorkPlan info,String flag) {
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
			if("1".equals(info.getIsFinish())){
				//已完成
				info.setIsFinish("1");
				info.setIsRemind("1");
				
			}else{
				//未完成保存
				info.setIsFinish("0");
				info.setIsRemind("0");
			}
			
			info=dao.save(info);
		}else{
			WorkPlan plan=dao.findOne(info.getId());
			plan.setUpdateTime(creTime);
			if("1".equals(info.getIsFinish())){
				//已完成
				plan.setIsFinish("1");
				info.setIsRemind("1");
				
			}else{
				//未完成保存
				info.setIsFinish("0");
				plan.setRemindTime(info.getRemindTime());
				plan.setIsRemind("0");
			}
			plan.setIsChange(info.getIsChange());
			plan.setUpdateUserId(UserUtil.getCruUserId());
			plan.setUpdateUserName(UserUtil.getCruUserName());
			plan.setDatePlan(info.getDatePlan());
			plan.setContent(info.getContent());
			plan.setPlanType(info.getPlanType());
			
			info=dao.save(plan);
		}
		return info;
	}
	
	/**
	 * 删除一条记录
	 */
	@Override
	public int delete(String id) {
		String jpql = "update WorkPlan t set t.visible = ? where t.id = ?";
		return dao.update(jpql, CommonConstants.VISIBLE[0], id);
	}


	/**
	 * 获取某个月第几天
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月9日 下午4:49:24
	 * @param year
	 * @param month
	 * @param before
	 * @return
	 */
	private Integer getBeforeDaysByDate(Integer year, Integer month, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, day);
		String day1 = sdf.format(cal.getTime());
		return  Integer.valueOf(day1.substring(day1.lastIndexOf("-")+1));
	}




	/**
	 * 根据日志信息设置该天是否有工作计划或者日志
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月4日 下午5:35:17
	 * @param dayStatus
	 * @param list
	 */
	private void setIsWorkDay(DayStatus dayStatus, List<Map<String, Object>> list) {
		//获取该天的年月日
		String fullDay = dayStatus.getFullDay();
		if(list.size()>0){
			for (Map<String, Object> map : list) {
				if(fullDay.equals(map.get("DATEPLAN"))){
					dayStatus.setHasData("1");
					if("0".equals(map.get("ISFINISH"))){
						//设置了工作日日
						dayStatus.setIsFinish("0");
					}else if("1".equals(map.get("ISFINISH"))){
						dayStatus.setIsFinish("1");
					}else if("2".equals(map.get("ISFINISH"))){
						dayStatus.setIsFinish("2");
					}
					break;
				}
			}
		}
		
	}

	/**
	 * 根据年份和月份获取日志和计划信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月4日 下午5:32:49
	 * @param year
	 * @param month
	 * @return
	 */
	private List<Map<String, Object>> getHolidaySetInfo(Integer year, Integer month) {
		ArrayList<String>yearMonths=new ArrayList<String>();
		//设置当月的年月
		String yearMonth;
		String endMonth;
		if(month<10){
			 yearMonth = year+"-0"+month+"-01";
		}else{
			yearMonth = year+"-"+month+"-01";
		}
		if(month<10){
			endMonth = year+"-0"+month+"-31";
		}else{
			endMonth = year+"-"+month+"-31";
		}
		yearMonths.add(yearMonth);
		//设置上个月的年月
		Integer previousMonth=month-1;
		String previousYearMonth;
		if(previousMonth<10 && previousMonth>0){
			previousYearMonth = year+"-0"+previousMonth+"-01";
		}else if(previousMonth==0){
			previousYearMonth = (year-1)+"-"+12+"-01";
		}else{
			previousYearMonth = year+"-"+previousMonth+"-01";
		}
		yearMonths.add(previousYearMonth);
		//设置下个月的年月
		Integer nextMonth=month+1;
		String nextYearMonth;
		if(month==12){
			nextYearMonth = (year+1)+"-01"+"-31";
		}else if(nextMonth<10){
			nextYearMonth = year+"-0"+nextMonth+"-31";
		}else{
			nextYearMonth = year+"-"+nextMonth+"-31";
		}
		yearMonths.add(nextYearMonth);
		List<String> params = new ArrayList<String>();
		params.add(previousYearMonth);
		params.add(nextYearMonth);
		//String condition=StringUtils.join(yearMonths, ",");
		StringBuilder sb = new StringBuilder("select a.date_plan datePlan ,CASE WHEN sum(a.is_finish) = 0 THEN '0' WHEN count(a.is_finish) = 1 THEN '1'  WHEN count(a.is_finish) > 1 THEN '2' END isFinish from ");
		sb.append("  (select t.date_plan ,t.is_finish from SYS_WORK_PLAN t where t.date_plan >= ? and t.date_plan < =? " );
		sb.append("  and t.visible = '"+CommonConstants.VISIBLE[1]+"'  and t.cre_user_id = "+"'"+UserUtil.getCruUserId()+"'");
		sb.append("  group by t.is_finish,t.date_plan) a");
		sb.append("  group by a.date_plan");
		//StringBuilder sb = new StringBuilder("select t.date_plan datePlan,t.is_finish isFinish from SYS_WORK_PLAN t where t.date_plan >= " +previousYearMonth+ " and t.date_plan < = " +nextYearMonth);
		//sb.append(" and t.visible = '"+CommonConstants.VISIBLE[1]+"' and t.cre_user_id = "+"'"+UserUtil.getCruUserId()+"'");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString(),params.toArray());
		return list;
	}
	
	/**
	 * 获取某个月的最后几天
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月9日 下午3:18:34
	 * @param year
	 * @param month
	 * @param before
	 * @return
	 */
	public  List<Integer> getLastDaysByDate(Integer year, Integer month, int before) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR,year);
			  cal.set(Calendar.MONTH, month);
			  cal.set(Calendar.DAY_OF_MONTH, 1);
			  cal.add(Calendar.DAY_OF_MONTH, -1);
		for(int i=0;i<before-1;i++){
			  Date lastDate = cal.getTime();
			  String temp = sdf.format(lastDate);
			 list.add(Integer.valueOf(temp.substring(temp.lastIndexOf("-")+1)));
			 cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		Collections.sort(list, new Comparator<Integer>(){
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
			
		});
		return list;
	}

	/**
	 * 把一些天设置为工作日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:28:35
	 * @param dates
	 */
	@Override
	public void setWorkDay(String dates) {
		//在节假日设置数据库中删除这些日期的数据
		List<Object> param = new ArrayList<>();
		String dateStr = CommonUtils.solveSqlInjectionOfIn(dates,param);
		jdbcTemplate.update("delete from KQGL_HOLIDAY_SET_INFO  t where t.now_date in (" +  dateStr + ")",param.toArray());
		//保存
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<WorkPlan> list=new ArrayList<WorkPlan>();
		String time = sdf.format(new Date());
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		String deptId = UserUtil.getCruDeptId();
		String deptName = UserUtil.getCruDeptName();
		String[] tempDates = dates.split(",");
		for(int i = 0;i<tempDates.length;i++){
			WorkPlan holidaySet=new WorkPlan();
			holidaySet.setCreUserId(userId);
			holidaySet.setCreDeptName(userName);
			holidaySet.setCreDeptId(deptId);
			holidaySet.setCreDeptName(deptName);
			holidaySet.setUpdateUserId(userId);
			holidaySet.setUpdateUserName(userName);
			holidaySet.setUpdateTime(userName);
			holidaySet.setVisible("1");
			holidaySet.setCreTime(time);
			holidaySet.setIsFinish("0");
			list.add(holidaySet);
		}
		dao.save(list);
	}

	/**
	 * 设置某些日期为休息日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 下午2:34:53
	 * @param dates
	 */
	@Override
	public void setFreeDay(String dates) {
		//在节假日设置数据库中删除这些日期的数据
		List<Object> param = new ArrayList<>();
		String dateStr = CommonUtils.solveSqlInjectionOfIn(dates,param);
		jdbcTemplate.update("delete from KQGL_HOLIDAY_SET_INFO  t where t.now_date in (" + dateStr + ")",param.toArray());
		//保存
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<WorkPlan> list = new ArrayList<WorkPlan>();
		String time = sdf.format(new Date());
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		String deptId = UserUtil.getCruDeptId();
		String deptName = UserUtil.getCruDeptName();
		String[] tempDates = dates.split(",");
		for(int i = 0;i<tempDates.length;i++){
			WorkPlan holidaySet=new WorkPlan();
			holidaySet.setCreUserId(userId);
			holidaySet.setCreDeptName(userName);
			holidaySet.setCreDeptId(deptId);
			holidaySet.setCreDeptName(deptName);
			holidaySet.setUpdateUserId(userId);
			holidaySet.setUpdateUserName(userName);
			holidaySet.setUpdateTime(userName);
			holidaySet.setVisible("1");
			holidaySet.setCreTime(time);
			holidaySet.setIsFinish("1");
			list.add(holidaySet);
		}
		dao.save(list);
		
	}


	/**
	 * 恢复某些日期
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月18日 下午2:37:48
	 * @param dates
	 */
	@Override
	public void setBack(String dates) {
		List<Object> param = new ArrayList<>();
		String dateStr = CommonUtils.solveSqlInjectionOfIn(dates,param);
		jdbcTemplate.update("delete from KQGL_HOLIDAY_SET_INFO  t where t.now_date in (" + dateStr + ")",param.toArray());
		
	}
	
	/**
	 * 查询某段时间内，节假日设置里是否有数据,map中key表示日期，value表示是否节假日
	 */
	@Override
	public Map<Object, Object> getHolidaySets(String startDate, String endDate) {
		List<String> params = new ArrayList<String>();
		
		StringBuilder sb=new StringBuilder("select  t.now_date date1,t.is_holiday isholiday  from KQGL_HOLIDAY_SET_INFO t  ");
		
		if(MyUserUtil.isDateString(startDate, "yyyy-mm-dd") && MyUserUtil.isDateString(endDate, "yyyy-mm-dd")) {
			sb.append(" where to_date(t.NOW_DATE,'yyyy-mm-dd')>=to_date(?,'yyyy-mm-dd') ");
			sb.append(" and to_date(t.NOW_DATE,'yyyy-mm-dd')<=to_date(?,'yyyy-mm-dd')");
			params.add(startDate);
			params.add(endDate);
		}
		
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sb.toString(),params.toArray());
		Map<Object,Object> result=new HashMap<Object,Object>();
		for (Map<String, Object> map : list) {
			result.put((String) map.get("date1"), map.get("isholiday"));
		}
		return result;
	}

	/**
	 * 查询某天日期是否是节假日(1表示是休息日，0表示是工作日,空表示没有改天数据）
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 上午9:44:13
	 * @param day
	 * @return
	 */
	@Override
	public String isHoliday(String day) {
		String isHoliday;
		try {
			StringBuilder sb = new StringBuilder("select t.is_holiday isholiday  from KQGL_HOLIDAY_SET_INFO t  ");
			sb.append("where to_date(t.NOW_DATE,'yyyy-mm-dd')=to_date(?,'yyyy-mm-dd')");
			isHoliday = jdbcTemplate.queryForObject(sb.toString(), String.class,day);
		} catch (Exception e) {
			//没有该天信息
			isHoliday = "";
		}
		return isHoliday;
	}
	/**
	 * 根据日期查询是否有该条数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月8日 下午3:24:43
	 * @param dateLog
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findByDatePlan(String dateplan,String id) {
		List<String> param = new ArrayList<String>();
		
		StringBuilder sb=new StringBuilder("SELECT t.id,t.date_plan datePlan,t.content,t.plan_type planType FROM sys_work_plan  t ");
		sb.append(" where t.visible='1' and t.CRE_USER_ID='" + UserUtil.getCruUserId() +"' and t.date_plan =? ");
		param.add(dateplan);
		/*if(!StringUtils.isBlank(id)){
			sb.append(" and id='" +id+ "'");
		}*/
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sb.toString(),param.toArray());
		return list;
	}

	@Override
	public WorkPlan getByDate(String dates,String id) {
		List<String> param = new ArrayList<String>();
		StringBuilder sb=new StringBuilder("SELECT t.id,t.date_plan datePlan,t.content,t.plan_type planType, t.is_finish isFinish ,t.remind_time remindtime FROM sys_work_plan  t ");
		sb.append(" where t.visible='1' and t.CRE_USER_ID='" + UserUtil.getCruUserId()+"'");
		if(!StringUtils.isBlank(dates)){
			sb.append(" and t.date_plan =? ");
			param.add(dates);
		}
		if(!StringUtils.isBlank(id)){
			sb.append(" and t.id= ? ");
			param.add(id);
		}
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sb.toString(),param.toArray());
		WorkPlan workPlan = new WorkPlan(); 
		if(list.size()>0){
			Map<String, Object> map = list.get(0);
			workPlan.setId((String) map.get("ID"));
			workPlan.setDatePlan((String) map.get("DATEPLAN"));
			workPlan.setContent((String) map.get("CONTENT"));
			workPlan.setPlanType((String) map.get("PLANTYPE"));
			workPlan.setIsFinish((String) map.get("ISFINISH"));
			workPlan.setRemindTime((String) map.get("REMINDTIME"));
		}
		return workPlan;
		
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
	public List<WorkPlan> upFinish(String id) {
		List<WorkPlan> list = new ArrayList<WorkPlan>();
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		WorkPlan plan=dao.findOne(id);
		plan.setIsChange("1");
		
		WorkPlan target = new WorkPlan();
		BeanUtils.copyProperties(plan, target);
		target.setId("");
		target.setIsFinish("1");
		list.add(plan);
		list.add(target);
		return dao.save(list);
	
		
		 
	}
	
	/**
	 * 获取要发送消息提醒的工作计划并发送消息
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param persionId
	 * @param logType
	 * @param dateLogYear
	 * @param dateLogMonth
	 * @param dateLogWeek
	 * @param timeCondition
	 * @param content
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	//@Scheduled(cron="*/5 * * * * ?")//每小时执行0 0 8 * * 
	@Scheduled(cron="0 0 * * * ?")
	public void sendMsgList() {
		ArrayList<WorkPlan> list=new ArrayList<WorkPlan>();
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());
		String sql ="SELECT \n" +
				"       t.ID,\n" +
				"       t.cre_user_id,\n" +
				"       t.cre_user_name,\n" +
				"       t.cre_dept_id,\n" +
				"       t.cre_dept_name,\n" +
				"       t.visible,\n" +
				"       t.cre_time,\n" +
				"       t.date_plan,\n" +
				"       t.plan_type,\n" +
				"       t.content,\n" +
				"       t.is_finish,\n" +
				"       t.IS_CHANGE,\n" +
				"       t.remind_time,\n" +
				"       t.is_remind,\n" +
				"       t.update_time,\n" +
				"       t.update_user_id,\n" +
				"       t.update_user_name\n" +
				"  FROM SYS_WORK_PLAN t\n" +
				" WHERE t.is_finish='0' and t.is_remind<>'1' and t.visible = '"+CommonConstants.VISIBLE[1]+"' and t.remind_time is not null and t.IS_CHANGE='0' and substr( t.remind_time, 0, 13 ) = '"+creTime+"'\n" +
				"";
		List<WorkPlan> listWorkPlan = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(WorkPlan.class));
		if(listWorkPlan.size()>0){
			for(WorkPlan workPlan:listWorkPlan){
				//保存业务表
				workPlan.setIsRemind("1");
				list.add(workPlan);
	           
	            //发送提醒
	            String msgUrl = "/modules/mypage/workPlan/workPlanEditForm.html?id="+workPlan.getId()+"&datePlan="+workPlan.getDatePlan()+"&resId="+ConfigConsts.SYSTEM_ID;
				if(ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)){
		            NotityMessage message = new NotityMessage();
		            message.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
		            message.setSubject("工作计划消息提醒");//标题
		            message.setContent(workPlan.getDatePlan()+"日"+workPlan.getContent()+"的工作计划");//内容
		            message.setSendTime(new Date());//发送时间
		            message.setAcceptTime(new Date());//接收时间
		            message.setAccepterId(workPlan.getCreUserId());//接收人id
		            message.setStatus("0");//状态
		            message.setType("3");//类型  1手机    2邮箱   3栈内
		            //message.setMsgUrl(msgUrl);//消息链接
		            NotityService notityService =  (NotityService) SpringBeanUtils.getBean("notityService");
		            notityService.add(message);
		       }else{
		            String param = "sendType=3&sendContent=工作计划&uids="+workPlan.getCreUserId()+"&subId=" +ConfigConsts.SYSTEM_ID+
		                    "&msgUrl="+msgUrl+"&title=工作计划消息提醒&appSecret=af2fff3bda2043a991018689848793b4";
		            String resJson = HttpRequestUtil.sendGet(ConfigConsts.MESSAGE_SERVICE_ROOT_URL+"/sendMsgsByUid",param);
		       }
			}
			 dao.save(list);
			//List<WorkPlan> newListWorkPlan = getWorkPlans(listWorkPlan);      
			//WorkPlan workPlan = listWorkPlan.get(0);
			/*for(WorkPlan workPlan:newListWorkPlan){
				String msgUrl = "/modules/mypage/workPlan/workPlanEditForm.html?id="+workPlan.getId()+"&datePlan="+workPlan.getDatePlan()+"&resId="+ConfigConsts.SYSTEM_ID;
				if(ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)){
		            NotityMessage message = new NotityMessage();
		            message.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
		            message.setSubject("工作计划消息提醒");//标题
		            message.setContent(workPlan.getDatePlan()+"日"+workPlan.getContent()+"的工作计划");//内容
		            message.setSendTime(new Date());//发送时间
		            message.setAcceptTime(new Date());//接收时间
		            message.setAccepterId(workPlan.getCreUserId());//接收人id
		            message.setStatus("0");//状态
		            message.setType("3");//类型  1手机    2邮箱   3栈内
		            message.setMsgUrl(msgUrl);//消息链接
		            NotityService notityService =  (NotityService) SpringBeanUtils.getBean("notityService");
		            notityService.add(message);
		       }else{
		            String param = "sendType=3&sendContent=工作计划&uids="+workPlan.getCreUserId()+"&subId=" +ConfigConsts.SYSTEM_ID+
		                    "&msgUrl="+msgUrl+"&title=工作计划消息提醒&appSecret=af2fff3bda2043a991018689848793b4";
		            String resJson = HttpRequestUtil.sendGet(ConfigConsts.MESSAGE_SERVICE_ROOT_URL+"/sendMsgsByUid",param);
		       }
				
			}*/
			
		}
	}
	
	public static List<WorkPlan> getWorkPlans(List<WorkPlan> list) {
		for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   { 
		    for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   { 
		      if  (list.get(j).getCreUserId().equals(list.get(i).getCreUserId())&&list.get(j).getDatePlan().equals(list.get(i).getDatePlan()))   { 
		    	  list.get(i).setContent(list.get(i).getContent()+"和"+list.get(j).getContent());
		    	  list.remove(j); 
		      } 
		    } 
		  } 
        // 创建一个新的list对象
        /*List<WorkPlan> newList = new ArrayList<WorkPlan>();
        boolean flag = false;
        for (WorkPlan oldSD : list) {
            for (WorkPlan newSD : newList) {
                if (newSD.getCreUserId().equals(oldSD.getCreUserId())&&newSD.getDatePlan().equals(oldSD.getDatePlan())) {
                	
                    flag = true;
                }else {
                    flag =false;
                    oldSD.setContent(oldSD.getContent()+"和"+newSD.getContent());
                }
            }
            if (!flag) {
            	
                newList.add(oldSD);
            }
        }*/
 
        return list;
    }
}
