package com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.service;

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
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.dao.BusinessTripDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.entity.BusinessTrip;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 出差业务层service实现类 TODO
 * 
 * @author 张建明
 * @Date 2018年4月12日 上午10:38:41
 */
@Service
public class BusinessTripServiceImpl implements BusinessTripService {

	@Autowired
	private BusinessTripDao businessTripDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 保存出差申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:38:24
	 * @param chuChai
	 * @param ideaName
	 * @return
	 * @throws ParseException 
	 */
	@Override
	public BusinessTrip saveForm(BusinessTrip chuChai, String ideaName){
		chuChai.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		if (StringUtils.isBlank(chuChai.getId())) {
			//创建人部门
			String creDeptId = UserUtil.getCruDeptId();
			String creDeptName = UserUtil.getCruDeptName();
			// 创建人处室
			String creChushiId = UserUtil.getCruChushiId();
			String creChushiName = UserUtil.getCruChushiName();
			// 创建人二级局
			String creJuId = UserUtil.getCruJuId();
			String creJuName = UserUtil.getCruJuName();
			chuChai.setCreTime(creTime);
			chuChai.setCreUserId(userId);
			chuChai.setCreUserName(userName);
			chuChai.setCreDeptId(UserUtil.getCruDeptId());
			chuChai.setCreDeptName(UserUtil.getCruDeptName());
			chuChai.setCreChuShiId(creChushiId);
			chuChai.setCreChuShiName(creChushiName);
			chuChai.setCreJuId(creJuId);
			chuChai.setCreJuName(creJuName);
			chuChai.setApplicationUnitId(creDeptId);
			chuChai.setApplicationUnitName(creDeptName);
			//前台出过来的时间范围
			String timeRange=chuChai.getStartTime();
			if(StringUtils.isNotBlank(timeRange)){
				String startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				String endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				chuChai.setStartTime(startDate);
				chuChai.setEndTime(endDate);
			}
			// 更新最后修改记录
			chuChai.setUpdateUserId(UserUtil.getCruUserId());
			chuChai.setUpdateUserName(UserUtil.getCruUserName());
			chuChai.setUpdateTime(creTime);
			chuChai = businessTripDao.save(chuChai);
		} else {
			BusinessTrip oldchuChai = businessTripDao.findOne(chuChai.getId());
			//oldchuChai.setBusinessTripTitle(chuChai.getBusinessTripTitle());
			//设置出差日期
			String timeRange=chuChai.getStartTime();
			if(StringUtils.isNotBlank(timeRange)){
				String startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				String endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				oldchuChai.setStartTime(startDate);
				oldchuChai.setEndTime(endDate);
			}
			oldchuChai.setTripColleagueIds(chuChai.getTripColleagueIds());
			oldchuChai.setLongTime(chuChai.getLongTime());
			oldchuChai.setDestination(chuChai.getDestination());
			oldchuChai.setVehicle(chuChai.getVehicle());
			oldchuChai.setBusiTripType(chuChai.getBusiTripType());
			oldchuChai.setIsHaveReceptionFees(chuChai.getIsHaveReceptionFees());
			oldchuChai.setReceptionFees(chuChai.getReceptionFees());
			oldchuChai.setReason(chuChai.getReason());
			oldchuChai.setTripColleague(chuChai.getTripColleague());
			oldchuChai.setUpdateTime(creTime);
			oldchuChai.setUpdateUserId(userId);
			oldchuChai.setUpdateUserName(userName);
			businessTripDao.save(oldchuChai);
		}
		return chuChai;
	}

	/**
	 * 删除出差申请草稿 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:38:07
	 * @param id
	 * @return
	 */
	@Override
	public int deleteBusinessTrip(String id) {
		int result = 0;
		String jpql = "update BusinessTrip t set t.visible = ? where t.id = ?";
		try {
			result = businessTripDao.update(jpql, CommonConstants.VISIBLE[0], id);
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
	public BusinessTrip getById(String id) {
		return businessTripDao.findOne(id);
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
			BusinessTrip chuChai = getById(id);
			chuChai.setSubflag(subflag);
			businessTripDao.save(chuChai);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "0";
		}
		return flag;
	}

	/**
	 * 查询出差申请列表 TODO
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
				"select new BusinessTrip(f.id as yibanid,t.id, t.businessTripTitle,  t.subflag,t.applicationUnitName, t.startTime,t.endTime, "
						+ " t.destination,t.longTime,t.creUserId,t.creUserName,t.busiTripType" + ")");
		querySql.append("	from BusinessTrip t, FlowRead f");
		querySql.append("	where f.userId = ? ");
		querySql.append("	and t.id = f.recordId and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(title)) {
			querySql.append("   and t.businessTripTitle like ?");
			para.add("%" + title + "%");
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.creTime >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.creTime <= ?");
			para.add(endDate);
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + " ");
		}

		Page<BusinessTrip> page = businessTripDao.query(querySql.toString(), pageable, para.toArray());
		List<BusinessTrip> content = page.getContent();
		for (BusinessTrip chuChai : content) {
			if (ConfigConsts.START_FLAG.equals(chuChai.getSubflag())) {
				chuChai.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
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
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String busiTripType, String startDate,
			String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();

		querySql.append("	from BusinessTrip t ");
		querySql.append(
				"	where t.creUserId = ? and t.subflag = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");

		// 拼接条件
		para.add(UserUtil.getCruUserId());
		//para.add(UserUtil.getCruDeptId());
		para.add(subflag);
		if (StringUtils.isNotBlank(busiTripType)) {
			querySql.append("   and t.busiTripType like ?");
			para.add("%" + busiTripType + "%");
		}
		
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				querySql.append("   and (t.startTime <= '" + startDate + "'  and t.endTime >='" + startDate +"'");
				querySql.append("   or t.startTime <= '" + endDate + "'  and t.endTime >='" + endDate +"'");
				querySql.append("	or t.startTime >='" + startDate + "'  and t.endTime<='" + endDate + "')");
			}
		}
		
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<BusinessTrip> page = businessTripDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<BusinessTrip> content = page.getContent();
		for (BusinessTrip BusinessTrip : content) {
			if (ConfigConsts.START_FLAG.equals(BusinessTrip.getSubflag())) {
				BusinessTrip.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
			}
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
		
		
	}
	/**
	 * 
	 * 根据条件查询出差列表TODO 
	 * @author 张建明
	 * @Date 2018年7月9日 下午6:55:32
	 * @param overTimeType
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public List<BusinessTrip> getList(String busiTripType, String startDate, String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		String creUserId=UserUtil.getCruUserId();
		querySql.append("select t.BUSINESS_TRIP_TITLE,t.CRE_USER_NAME,t.APPLICATION_UNIT_NAME,"
				+ "t.START_TIME,t.END_TIME,"
				+ "(case t.BUSI_TRIP_TYPE when '0' then '业务出差' when '1' then '行政出差' else '其他' end) BUSI_TRIP_TYPE ,"
				+ "t.LONG_TIME,t.DESTINATION,"
				+ "(case t.IS_HAVE_RECEPTION_FEES when '0' then '无' when '1' then '有' else ' ' end) IS_HAVE_RECEPTION_FEES,"
				+ "t.RECEPTION_FEES,t.VEHICLE,"
				+ "(CASE t.SUBFLAG WHEN '0' THEN '草稿' WHEN '1' THEN '流程中' WHEN '5' THEN '审批通过' WHEN '6' THEN '审批未通过' ELSE '其他' END) SUBFLAG");
		querySql.append("	from KQGL_BUSINESS_TRIP_INFO t");
		querySql.append("	where ");
		querySql.append(" t.cre_user_id='"+creUserId+"' and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and (t.start_date >= '" + startDate + "' or t.start_time >='"+startDate+"')" );
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and (t.end_date <= '"+ endDate +"' or t.end_time <='"+endDate+"')");
		}
		if(StringUtils.isNotBlank(busiTripType)){
			querySql.append(" and t.busitrip_type='"+busiTripType+"'");
		}
		if(StringUtils.isNotBlank(subflag)){
			querySql.append(" and t.subflag='"+subflag+"'");
		}
		querySql.append("  order by t.application_time desc ");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(BusinessTrip.class));
	}
	
	/**
	 * 查询某用户该天的流程结束的出差记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月24日 下午1:02:28
	 * @param userId
	 * @param day
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDataByUseridAndDay(String userId, String day) {
		StringBuilder chuChaiSql = new StringBuilder("select t.id from KQGL_BUSINESS_TRIP_INFO t  ");
		chuChaiSql.append(" where t.cre_user_id='" + userId + "' and  t.START_TIME<='" + day + "' and t.END_TIME>='" + day + "'  and t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' ");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(chuChaiSql.toString());
		return list;
	}

	@Override
	public PageImpl getBusinessTripRecordList(Pageable pageable, PageImpl pageImpl,String busiTripType, String startDate, String endDate) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from BusinessTrip t ");
		querySql.append(
				"	where (t.creUserId = ? or t.tripColleagueIds like '%"+UserUtil.getCruUserId()+"%') and t.subflag='"+ConfigConsts.APPROVAL_FLAG+"' and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(busiTripType)) {
			querySql.append("   and t.busiTripType =?");
			para.add(busiTripType);
		}
		
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			//querySql.append(" and (t.startTime<='"+endDate+"' and t.endTime>= '"+startDate+"')");
				querySql.append("   and (t.startTime <= '" + startDate + "'  and t.endTime >='" + startDate +"'");
				querySql.append("   or t.startTime <= '" + endDate + "'  and t.endTime >='" + endDate +"'");
				querySql.append("	or t.startTime >='" + startDate + "'  and t.endTime<='" + endDate + "')");
		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<BusinessTrip> page = businessTripDao.query(querySql.toString(), pageable, para.toArray());
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}
	
	/**
	 * //查询某段时间某些人做为同行人的同行人id
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月31日 上午11:47:23
	 * @param startDate
	 * @param endDate
	 * @param users
	 * @return
	 */
	@Override
	public List<Map<String,Object>> findTripColleagueByUserId(String startDate, String endDate, String[] userids) {
		StringBuilder sb = new StringBuilder(" select t.trip_colleague_ids userids,t.cre_user_id creUserid,t.start_time startTime,t.end_time endTime,t.LONG_TIME longTime from KQGL_BUSINESS_TRIP_INFO t ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append("   where t.subflag = '5' and (start_time <= '" + startDate + "'  and end_time >='" + startDate +"'");
			sb.append("   or start_time <= '" + endDate + "'  and end_time >='" + endDate + "'");
			sb.append("	or start_time >='" + startDate + "'  and end_time<='" + endDate + "')");
		}
		if(userids.length==1){
			sb.append(" and instr(t.trip_colleague_ids,"+ userids[0] +")>0 ");
		}else{
			for(int i=0;i<userids.length;i++){
				if(i==0){
					sb.append(" and (instr(t.trip_colleague_ids,"+ userids[0] +")>0 ");
				}else{
					sb.append(" or instr(t.trip_colleague_ids,"+  userids[i] +")>0 ");
				}
			}
			sb.append(" )");
		}
	
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
		return list;
	}

	/**
	 *查询某些用户某段时间的审批通过的出差数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午10:48:16
	 * @param startDate
	 * @param endDate
	 * @param userids
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findUserData(String startDate, String endDate, String userids) {
		StringBuilder sb = new StringBuilder("select t.cre_user_id userid,t.start_time startTime,t.end_time endTime,t.long_time longTime from KQGL_BUSINESS_TRIP_INFO t where t.visible='1' and t.subflag='5' and t.cre_user_id in(" + userids + ") ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			/*sb.append("   and (t.start_time <= '" + startDate + "'  and t.end_time >='" + startDate +"'");
			sb.append("   or t.start_time <= '" + endDate + "'  and t.end_time >='" + endDate +"'");
			sb.append("	or t.start_time >='" + startDate + "'  and t.end_time<='" + endDate + "')");*/
			
			sb.append(" and to_date(t.start_time,'yyyy-mm-dd') <= to_date('" + endDate + "','yyyy-mm-dd') and to_date(t.end_time,'yyyy-mm-dd') >= to_date('" + startDate + "','yyyy-mm-dd')");
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
		return list;
	}
	


}
