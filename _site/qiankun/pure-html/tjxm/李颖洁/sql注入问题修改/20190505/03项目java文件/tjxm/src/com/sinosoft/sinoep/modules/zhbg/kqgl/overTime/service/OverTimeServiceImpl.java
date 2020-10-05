package com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.service;

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
import com.sinosoft.sinoep.modules.system.config.holidayset.service.HolidaySetService;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.dao.OverTimeDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.entity.OverTime;
import com.sinosoft.sinoep.modules.zhbg.kqgl.util.MyUserUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service.KqglAttendenceHandleService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 加班业务层service实现类 TODO
 * 
 * @author 张建明
 * @Date 2018年4月12日 上午10:38:41
 */
@Service
public class OverTimeServiceImpl implements OverTimeService {

	@Autowired
	private OverTimeDao overTimeDao;
	
	@Autowired
	private TreeService service;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	HolidaySetService holidaySetService;
	
	@Autowired
	KqglAttendenceHandleService kqglAttendenceHandleService;

	/**
	 * 保存加班申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:38:24
	 * @param overTime
	 * @param ideaName
	 * @return
	 */
	@Override
	public OverTime saveForm(OverTime overTime, String ideaName) {
		if (StringUtils.isBlank(overTime.getId())) {
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//overTime.setOverTimeTitle("测试加班流程标题");
			overTime.setCreTime(creTime);
			overTime.setCreUserId(UserUtil.getCruUserId());
			overTime.setCreUserName(UserUtil.getCruUserName());
			overTime.setApplicationUnitId(UserUtil.getCruDeptId());
			overTime.setCreChuShiId(UserUtil.getCruChushiId());
			overTime.setCreChuShiName(UserUtil.getCruChushiName());
			overTime.setCreDeptId(UserUtil.getCruDeptId());
			overTime.setCreDeptName(UserUtil.getCruDeptName());
			overTime.setCreJuId(UserUtil.getCruJuId());
			overTime.setVisible(CommonConstants.VISIBLE[1]);
			overTime.setCreJuName(UserUtil.getCruJuName());
			overTime.setUpdateUserId(UserUtil.getCruUserId());
			overTime.setUpdateUserName(UserUtil.getCruUserName());
			overTime = overTimeDao.save(overTime);
		} else {
			String updTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			OverTime oldoverTime = overTimeDao.findOne(overTime.getId());
			oldoverTime.setIdea(overTime.getIdea());
			//oldoverTime.setOverTimeTitle(overTime.getOverTimeTitle());
			oldoverTime.setOverTimeType(overTime.getOverTimeType());
			oldoverTime.setOverTimeDate(overTime.getOverTimeDate());
			oldoverTime.setStartStopTime(overTime.getStartStopTime());
			oldoverTime.setLongTimeh(overTime.getLongTimeh());
			oldoverTime.setLongTimed(overTime.getLongTimed());
			oldoverTime.setReason(overTime.getReason());
			oldoverTime.setUpdateUserId(UserUtil.getCruUserId());
			oldoverTime.setUpdateUserName(UserUtil.getCruUserName());
			oldoverTime.setUpdateTime(updTime);
			oldoverTime.setOverTimeDate(overTime.getOverTimeDate());
			overTimeDao.save(oldoverTime);
		}
		return overTime;
	}

	/**
	 * 删除加班申请草稿 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:38:07
	 * @param id
	 * @return
	 */
	@Override
	public int deleteOverTime(String id) {
		int result = 0;
		String jpql = "update OverTime t set t.visible = ? where t.id = ?";
		try {
			result = overTimeDao.update(jpql, CommonConstants.VISIBLE[0], id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据主键ID查询一条数据 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:38:00
	 * @param id
	 * @return
	 */
	@Override
	public OverTime getById(String id) {
		return overTimeDao.findOne(id);
	}

	/**
	 * 更新业务表流程状态 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:37:49
	 * @param id
	 * @param subflag
	 * @return
	 */
	@Override
	public String updateSubFlag(String id, String subflag) {
		String flag = "1";
		try {
			OverTime overTime = getById(id);
			overTime.setSubflag(subflag);
			overTimeDao.save(overTime);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "0";
		}
		return flag;
	}

	/**
	 * 查询加班申请列表 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:37:22
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String title, String startDate, String endDate,
			String subflag) {

		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();

		querySql.append(
				"select new OverTime(f.id as yibanid,t.id, t.overTimeTitle,  t.subflag,t.applicationUnitName, t.startTime,t.endTime, "
						+ " t.overTimeType,t.overTimeDate,t.longTime,t.creUserId,t.creUserName" + ")");
		querySql.append("	from OverTime t, FlowRead f");
		querySql.append("	where f.userId = ? ");
		querySql.append("	and t.id = f.recordId and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(title)) {
			querySql.append("   and t.overTimeTitle like ?");
			para.add("%" + title + "%");
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.overTimeDate >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.overTimeDate <= ?");
			para.add(endDate);
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.overTimeDate  ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + " ");
		}

		Page<OverTime> page = overTimeDao.query(querySql.toString(), pageable, para.toArray());
		List<OverTime> content = page.getContent();
		for (OverTime overTime : content) {
			if (ConfigConsts.START_FLAG.equals(overTime.getSubflag())) {
				overTime.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
			}
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 查询草稿列表 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:37:07
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String overTimeType, String startDate,
			String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();

		querySql.append("	from OverTime t ");
		querySql.append(
				"	where t.creUserId = ? and t.subflag = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "' ");

		// 拼接条件
		para.add(UserUtil.getCruUserId());
		para.add(subflag);
		
		if (StringUtils.isNotBlank(overTimeType)) {
			querySql.append("   and t.overTimeType = ?");
			para.add(overTimeType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.overTimeDate >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.overTimeDate <= ?");
			para.add(endDate);
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.overTimeDate desc) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<OverTime> page = overTimeDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<OverTime> content = page.getContent();
		for (OverTime overTime : content) {
			if (ConfigConsts.START_FLAG.equals(overTime.getSubflag())) {
				overTime.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
			}
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}
	
	/**
	 * 根据查询条件查询当前用户的加班列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午6:01:14
	 * @param overTimeType
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public List<OverTime> getList(String userids,String overTimeType, String startDate, String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select t.cre_user_name,t.applicant_unit_name applicationUnitName,t.START_STOP_TIME startStopTime,t.over_time_type,t.over_time_date,t.over_time_long_timeh longTimeh,t.over_time_long_timed longTimed,t.subflag");
		querySql.append("   from KQGL_OVER_TIME_INFO t  where t.visible = '" + CommonConstants.VISIBLE[1] + "' ");
		// 拼接条件
		String inString = "";
		if (StringUtils.isNotBlank(userids)) {
			inString = CommonUtils.solveSqlInjectionOfIn(userids, para);
			querySql.append("   and t.cre_user_id  in  (" + inString +")");
		}else{
			//查询当前用户下的子部门人员有哪些
			userids = getUserIds(UserUtil.getCruDeptId());
			inString = CommonUtils.solveSqlInjectionOfIn(userids, para);
			querySql.append("   and t.cre_user_id in (" + inString +")");
		}
		if (StringUtils.isNotBlank(overTimeType)) {
			querySql.append("   and t.OVER_TIME_TYPE >= ?");
			para.add(overTimeType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.OVER_TIME_DATE >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.OVER_TIME_DATE <= ?");
			para.add(endDate);
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}else{
			querySql.append("   and (t.subflag != '" + ConfigConsts.START_FLAG + "' and t.subflag != '" + ConfigConsts.REMOVE_FLAG + "')");
		}
		querySql.append("  order by t.OVER_TIME_DATE desc");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(OverTime.class),para.toArray());
	}
	
	
	/**
	 * 根据加班日期获取加班的开始时间和结束数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 上午9:38:39 
	 * @param overTimeDate
	 * @return
	 */
	@Override
	public JSONObject getStartAndEndTime(String day) {
		JSONObject jsonObj = new JSONObject();
		//查询当前用户该天的考勤信息
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(UserUtil.getCruUserId());
		String cardNo = map.get(UserUtil.getCruUserId()).getCardNumber();
		//该用户该天的考勤信息
		KqglAttendenceHandle kqInfo = getKqglAttendenceHandle(day,cardNo);
		if(kqInfo!=null){
			//有该用户的考勤信息
			jsonObj.put("startTime", kqInfo.getImportDate() + " 05:30:00");
			jsonObj.put("endTime", kqInfo.getQcTime());
		}else{
			jsonObj.put("startTime",  "");
			jsonObj.put("endTime", "");
		}
		return jsonObj;
	}
	
	//查询该用户该天的考勤数据
	public KqglAttendenceHandle getKqglAttendenceHandle(String importDate,String cardNumber) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select * ");
		querySql.append("	from kqgl_attendence_handle t");
		querySql.append("	where 1=1 and t.card_number = ?");
		para.add(cardNumber);
		//日期
		if (StringUtils.isNotBlank(importDate)) {
			querySql.append("   and t.import_date = ?");
			para.add(importDate);
		}
		List<KqglAttendenceHandle> list = jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(KqglAttendenceHandle.class),para.toArray());
		KqglAttendenceHandle kqglAttendenceHandle = null;
		if(list.size()>0){
			kqglAttendenceHandle = list.get(0);
		}
		return kqglAttendenceHandle;
	}
	
	/**
	 * 获取某一天加班的开始结束日期以及是否是节假日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 下午4:33:57
	 * @param overTimeDate
	 * @return
	 * @throws ParseException 
	 */
	@Override
	public JSONObject getStartEndTimeAndIsHoliday(String day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("startTime", "");
		jsonObj.put("endTime", "");
		//查询当前用户的考勤信息
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(UserUtil.getCruUserId());
		String cardNo = map.get(UserUtil.getCruUserId()).getCardNumber();
		KqglAttendenceHandle kqInfo = getKqglAttendenceHandle(day,cardNo);
		//设置该天是否是工作日
		String isHoliday = holidaySetService.isHoliday(day);
		if("".equals(isHoliday)){
			int week=DateUtil.getWeekDay(sdf.parse(day));
			if(week!=6 && week!=7){
				//工作日
				jsonObj.put("isHoliday", "0");
				if(kqInfo != null){
					jsonObj.put("startTime", kqInfo.getImportDate() + " 05:30:00");
					jsonObj.put("endTime", kqInfo.getQcTime());
				}
			}else{
				//休息日
				jsonObj.put("isHoliday", "1");
				if(kqInfo != null){
					jsonObj.put("startTime", kqInfo.getQrTime());
					jsonObj.put("endTime", kqInfo.getQcTime());
				}
			}
		}else{
			jsonObj.put("isHoliday", isHoliday);
			if("1".equals(isHoliday)){
				//休息日
				if(kqInfo != null){
					jsonObj.put("startTime", kqInfo.getQrTime());
					jsonObj.put("endTime", kqInfo.getQcTime());
				}
			}else{
				//工作日
				if(kqInfo != null){
					jsonObj.put("startTime", kqInfo.getImportDate() + " 05:30:00");
					jsonObj.put("endTime", kqInfo.getQcTime());
				}
			}
		}
		return jsonObj;
	}

	@Override
	public List<OverTime> getTalOverTimeDaysAndHours(String beginQuarters, String endQuerters) throws Exception {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select sum(t.over_time_long_timeh) talLongTimeH,sum(t.over_time_long_timed) talLongTimeD ");
		querySql.append("   from KQGL_OVER_TIME_INFO t  where t.visible = '" + CommonConstants.VISIBLE[1] + "' and t.cre_user_id = '" + UserUtil.getCruUserId() +"'");
		// 拼接条件
		if (StringUtils.isNotBlank(beginQuarters)) {
			querySql.append("   and t.over_time_start_time >= ?");
			para.add(beginQuarters);
		}
		if (StringUtils.isNotBlank(endQuerters)) {
			querySql.append("   and t.over_time_end_time <= ?");
			para.add(endQuerters);
		}
		querySql.append("  order by t.cre_time desc");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(OverTime.class),para.toArray());
	}
	
	/**
	 * 获取当前用户所在部门的子部分
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月19日 下午1:41:16
	 */
	@SuppressWarnings("unused")
	private String getChildDept() {
		JSONArray depts = service.getDeptTreeByDeptId(UserUtil.getCruDeptId());
		StringBuilder sb=new StringBuilder();
		for (Object object : depts) {
			JSONObject jsonObj=JSONObject.fromObject(object);
			sb.append(jsonObj.getString("uuid")+",");
		}
		String childDept = CommonUtils.commomSplit(sb.toString());
		return childDept;
	}

	@Override
	public long getCnt(String userids, String startDate, String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select count(t.id) ");
		querySql.append("   from KQGL_OVER_TIME_INFO t  where t.visible = '" + CommonConstants.VISIBLE[1] + "' ");
		String inString = "";
		// 拼接条件
		if (StringUtils.isNotBlank(userids)) {
			inString = CommonUtils.solveSqlInjectionOfIn(userids, para);
			querySql.append("   and t.cre_user_id  in  (" + inString +")");
		}else{
			//查询当前用户下的子部门有哪些
			userids = getUserIds(UserUtil.getCruDeptId());
			inString = CommonUtils.solveSqlInjectionOfIn(userids, para);
			querySql.append("   and t.cre_user_id in (" + inString +")");
		}
		
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.OVER_TIME_DATE >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.OVER_TIME_DATE <= ?");
			para.add(endDate);
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}else{
			querySql.append("   and (t.subflag != '" + ConfigConsts.START_FLAG + "' and t.subflag != '" + ConfigConsts.REMOVE_FLAG + "')");
		}
		querySql.append("  order by t.OVER_TIME_DATE desc");
		long cnt=jdbcTemplate.queryForObject(querySql.toString(), Long.class,para.toArray());
		return cnt;
	}
	
	/**
	 * 查询某些用户某段时间审批通过的加班数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午11:05:09
	 * @param startDate
	 * @param endDate
	 * @param commomSplit
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findUserData(String startDate, String endDate, String userids) {
		ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(userids.split(",")));
		List<Object> para = new ArrayList<>();
		StringBuilder sb = new StringBuilder("select t.cre_user_id userid,t.over_time_date overTime,t.over_time_type type,t.START_STOP_TIME startStopTime,t.over_time_long_timeh hours,t.over_time_long_timed days from KQGL_OVER_TIME_INFO t where t.visible='1' and t.subflag='5'   ");
		useridIntoSql(useridList, sb,para);
		boolean flg1 = MyUserUtil.isDateString(startDate, "yyyy-mm-dd");
		boolean flg2 = MyUserUtil.isDateString(endDate, "yyyy-mm-dd");
		if(StringUtils.isNotBlank(startDate)&&flg1){
			sb.append(" and  to_date(t.over_time_date,'yyyy-mm-dd') >= to_date(?,'yyyy-mm-dd') ");
			para.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)&&flg2){
			sb.append("  and  to_date(t.over_time_date,'yyyy-mm-dd') <= to_date(?,'yyyy-mm-dd') ");
			para.add(endDate);
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString(),para.toArray());
		return list;
	}
	private void useridIntoSql(ArrayList<String> useridList, StringBuilder sb,List<Object> para) {
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
		String inString = "";
		if(useridList.size()>0){
			sb.append(" and  (");
			while(useridList.size()>0){
				useridSb.setLength(0);
				if(index == 0){
					sb.append(" t.cre_user_id in (");
				}else{
					sb.append(" or t.cre_user_id in (");
				}
				for(int i=0;i<900&&useridList.size()>0;i++){
					index=1;
					String tempUserid = useridList.remove(0);
					useridSb.append( tempUserid + ",");
				}
				inString = CommonUtils.solveSqlInjectionOfIn(useridSb.toString(), para);
				sb.append(inString + " )");
			}
			sb.append(" )");
		}
	}
	/**
	 * 获取当前部门及子部门的人员id
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月30日 上午10:26:22
	 * @param cruDeptId
	 * @return
	 */
	private String getUserIds(String cruDeptId) {
		StringBuilder sb = new StringBuilder();
		JSONArray jsonArry = service.getDeptAndUserBydeptId(cruDeptId);
		for (Object object : jsonArry) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			if (!StringUtils.isNotBlank(jsonObj.getString("deptlevel"))) {
				//该json数据为人员
				sb.append(jsonObj.getString("uuid") + ",");
				
			}
		}
		String userids = sb.toString();
		return userids.substring(0, userids.length()-1);
	}
}
