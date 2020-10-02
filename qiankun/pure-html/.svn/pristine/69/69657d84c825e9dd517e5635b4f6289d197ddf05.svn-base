package com.sinosoft.sinoep.modules.zhbg.kqgl.qj.service;

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
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.dao.KqglLeaveInfoDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.entity.KqglLeaveInfo;
import com.sinosoft.sinoep.user.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 请假ServiceImpl TODO
 * @author 冯超
 * @Date 2018年4月11日 上午10:24:57
 */
@Service
public class KqglLeaveInfoServiceImpl implements KqglLeaveInfoService {

	@Autowired
	private KqglLeaveInfoDao kqglLeaveInfoDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DataDictionaryService service;
	/**
	 * 保存 TODO
	 * @author 冯超
	 * @Date 2018年4月11日 下午3:48:34
	 * @param kqglLeaveInfo
	 * @return
	 */
	@Override
	public KqglLeaveInfo saveForm(KqglLeaveInfo kqglLeaveInfo) {
		kqglLeaveInfo.setVisible(CommonConstants.VISIBLE[1]);
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 创建人
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		//按时间请假 手动输入请节哀时长时 出入的数据格式一致
//		if(StringUtils.isNotBlank(kqglLeaveInfo.getLeaveLongTime())){
//		  kqglLeaveInfo.setLeaveLongTime(kqglUtil.getNumFormate(kqglLeaveInfo.getLeaveLongTime(),1));
//		}
		if (StringUtils.isBlank(kqglLeaveInfo.getId())) {
			// 创建人部门
			String creDeptId = UserUtil.getCruDeptId();
			String creDeptName = UserUtil.getCruDeptName();
			// 创建人处室
			String creChushiId = UserUtil.getCruChushiId();
			String creChushiName = UserUtil.getCruChushiName();
			// 创建人二级局
			String creJuId = UserUtil.getCruJuId();
			String creJuName = UserUtil.getCruJuName();

			kqglLeaveInfo.setCreTime(creTime);
			kqglLeaveInfo.setCreUserId(userId);
			kqglLeaveInfo.setCreUserName(userName);
			kqglLeaveInfo.setCreDeptId(creDeptId);
			kqglLeaveInfo.setCreDeptName(creDeptName);
			kqglLeaveInfo.setCreChushiId(creChushiId);
			kqglLeaveInfo.setCreChushiName(creChushiName);
			kqglLeaveInfo.setCreJuId(creJuId);
			kqglLeaveInfo.setCreJuName(creJuName);
			kqglLeaveInfo.setApplicantUnitId(creDeptId);
			kqglLeaveInfo.setApplicantUnitName(creDeptName);
			//kqglLeaveInfo.setApplicationTime(creTime);
			// 更新最后修改记录
			kqglLeaveInfo.setUpdateTime(creTime);
			kqglLeaveInfo.setUpdateUserId(userId);
			kqglLeaveInfo.setUpdateUserName(userName);
			kqglLeaveInfo = kqglLeaveInfoDao.save(kqglLeaveInfo);
		} else {
			KqglLeaveInfo oldKqglLeaveInfo = kqglLeaveInfoDao.findOne(kqglLeaveInfo.getId());
			//oldKqglLeaveInfo.setLeaveTitle(kqglLeaveInfo.getLeaveTitle());
			oldKqglLeaveInfo.setLeaveStartDate(kqglLeaveInfo.getLeaveStartDate());
			oldKqglLeaveInfo.setStartAmOrPm(kqglLeaveInfo.getStartAmOrPm());
			oldKqglLeaveInfo.setLeaveEndDate(kqglLeaveInfo.getLeaveEndDate());
			oldKqglLeaveInfo.setEndAmOrPm(kqglLeaveInfo.getEndAmOrPm());
			oldKqglLeaveInfo.setLeaveType(kqglLeaveInfo.getLeaveType());
			oldKqglLeaveInfo.setLeaveLongTime(kqglLeaveInfo.getLeaveLongTime());
			//oldKqglLeaveInfo.setIsLeaveInLieu(kqglLeaveInfo.getIsLeaveInLieu());
			oldKqglLeaveInfo.setLeaveReason(kqglLeaveInfo.getLeaveReason());
			// 更新最后修改记录
			kqglLeaveInfo.setUpdateTime(creTime);
			kqglLeaveInfo.setUpdateUserId(userId);
			kqglLeaveInfo.setUpdateUserName(userName);
			kqglLeaveInfoDao.save(oldKqglLeaveInfo);
		}
		return kqglLeaveInfo;
	}

	/**
	 * 查询请假审批列表 TODO
	 * @author 冯超
	 * @Date 2018年4月11日 下午4:01:26
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param leaveType
	 * @param creUserName
	 * @param applicantUnitName
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String leaveType, String creUserName, String applicantUnitName, String isLeaveInLieu, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append(
				"select new com.sinosoft.sinoep.modules.zhbg.kqgl.qj.entity.KqglLeaveInfo(f.id as yibanid,t.id, t.subflag, t.creUserName, t.leaveType, t.leaveLongTime,");
		querySql.append("t.leaveStartDate, t.startAmOrPm, t.leaveEndDate, t.endAmOrPm)");
		querySql.append("	from KqglLeaveInfo t, FlowRead f ");
		querySql.append("	where f.userId = ? ");
		querySql.append("	and t.id = f.recordId and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.leaveStartDate >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.leaveEndDate <= ?");
			para.add(endDate);
		}
		// 请假类型
		if (StringUtils.isNotBlank(leaveType)) {
			querySql.append("   and t.leaveType = ?");
			para.add(leaveType);
		}
		// 是否倒休
		if (StringUtils.isNotBlank(isLeaveInLieu)) {
			querySql.append("   and t.isLeaveInLieu = ?");
			para.add(isLeaveInLieu);
		}
		// 申请人
		if (StringUtils.isNotBlank(creUserName)) {
			querySql.append("   and t.creUserName ?");
			para.add("%" + creUserName + "%");
		}
		// 单位
		if (StringUtils.isNotBlank(applicantUnitName)) {
			querySql.append("   and t.applicantUnitName ?");
			para.add("%" + applicantUnitName + "%");
		}

		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<KqglLeaveInfo> page = kqglLeaveInfoDao.query(querySql.toString(), pageable, para.toArray());

		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 请假草稿列表 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 上午10:16:59
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param leaveType
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 * @throws Exception 
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String leaveType, String isLeaveInLieu, String subflag) throws Exception {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from KqglLeaveInfo t ");
		querySql.append(
				"	where t.creUserId = ? and t.subflag = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		para.add(UserUtil.getCruUserId());
		//para.add(UserUtil.getCruDeptId());
		para.add(subflag);
		// 起始时间
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append(" and ((t.leaveStartDate >= '" + startDate + "' and t.leaveStartDate <='" + endDate + "')");
			querySql.append(" or (t.leaveEndDate >= '" + startDate + "' and t.leaveEndDate <='" + endDate + "')");
			querySql.append(" or (t.leaveStartDate <= '" + startDate + "' and t.leaveEndDate >='" + endDate + "'))");
			
//			querySql.append(" or ((substr(t.leaveStartTime,0,10) >= '" + startDate + "' and substr(t.leaveStartTime,0,10) <='" + endDate + "')");
//			querySql.append(" or (substr(t.leaveEndTime,0,10)>= '" + startDate + "' and substr(t.leaveEndDate,0,10) <='" + endDate + "')");
//			querySql.append(" or (substr(t.leaveStartTime,0,10) <= '" + startDate + "' and substr(t.leaveEndTime,0,10) >='" + endDate + "')))");
//			querySql.append("   and (t.leaveStartDate >= ? or t.leaveStartTime>=?)");
//			para.add(startDate);
//			para.add(startDate);
		}
		/*if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and (t.leaveEndDate <= ? or t.leaveEndTime<=?)");
			para.add(endDate);
			para.add(endDate);
		}*/
		// 请假类型
		if (StringUtils.isNotBlank(leaveType)) {
			querySql.append("   and t.leaveType = ?");
			para.add(leaveType);
		}
		// 是否倒休
//		if (StringUtils.isNotBlank(isLeaveInLieu)) {
//			querySql.append("   and t.isLeaveInLieu = ?");
//			para.add(isLeaveInLieu);
//		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<KqglLeaveInfo> page = kqglLeaveInfoDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<KqglLeaveInfo> content = page.getContent();
		//引用字典
		List<DataDictionary> dicts=service.getListByMark("leaveType", "1");
		for (KqglLeaveInfo kqglLeaveInfo : content) {
			for(DataDictionary dic:dicts){
				if(dic.getCode().equals(kqglLeaveInfo.getLeaveType())){
					kqglLeaveInfo.setLeaveType(dic.getName());
				}
			}
			//判断请假时间类型 0：按日期   1：按时间
//			if(StringUtils.isNotBlank(kqglLeaveInfo.getLeaveTimeType()) && kqglLeaveInfo.getLeaveTimeType().equals(EmConstants.QJTIMETYPE[1])){
//				kqglLeaveInfo.setLeaveLongTime(kqglLeaveInfo.getLeaveLongTime()+"/h");
//				kqglLeaveInfo.setLeaveStartDate(kqglLeaveInfo.getLeaveStartTime());
//				kqglLeaveInfo.setLeaveEndDate(kqglLeaveInfo.getLeaveEndTime());
//			}else{
//				String leaLongTime=kqglLeaveInfo.getLeaveLongTime();
//				if(StringUtils.isNotBlank(leaLongTime)){
//					kqglLeaveInfo.setLeaveLongTime(kqglLeaveInfo.getLeaveLongTime()+"/d");
//				}
//			}
			if (ConfigConsts.START_FLAG.equals(kqglLeaveInfo.getSubflag())) {
				kqglLeaveInfo.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
			}
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 根据id获取请假信息 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 下午12:00:58
	 * @param id
	 * @return
	 */
	@Override
	public KqglLeaveInfo getById(String id) {
		KqglLeaveInfo kqglLeaveInfo = kqglLeaveInfoDao.findOne(id);
		// 请假开始时间 2018-04-05 上午
		if (!"".equals(kqglLeaveInfo.getLeaveStartDate()) && kqglLeaveInfo.getLeaveStartDate() != null) {
			String startDate = kqglLeaveInfo.getLeaveStartDate();
			if ("1".equals(kqglLeaveInfo.getStartAmOrPm())) {
				startDate = startDate + " 上午";
			} else if ("0".equals(kqglLeaveInfo.getStartAmOrPm())) {
				startDate = startDate + " 下午";
			}
			kqglLeaveInfo.setStartDate(startDate);
		}
		if (!"".equals(kqglLeaveInfo.getLeaveEndDate()) && kqglLeaveInfo.getLeaveEndDate() != null) {
			String endDate = kqglLeaveInfo.getLeaveEndDate();
			if ("1".equals(kqglLeaveInfo.getEndAmOrPm())) {
				endDate = endDate + " 上午";
			} else if ("0".equals(kqglLeaveInfo.getEndAmOrPm())) {
				endDate = endDate + " 下午";
			}
			kqglLeaveInfo.setEndDate(endDate);
		}
		// 是否倒休
//		if (!"".equals(kqglLeaveInfo.getIsLeaveInLieu()) && kqglLeaveInfo.getIsLeaveInLieu() != null) {
//			if ("1".equals(kqglLeaveInfo.getIsLeaveInLieu())) {
//				kqglLeaveInfo.setIsLeaveInLieuName("是");
//			} else if ("0".equals(kqglLeaveInfo.getIsLeaveInLieu())) {
//				kqglLeaveInfo.setIsLeaveInLieuName("否");
//			}
//		}
		return kqglLeaveInfo;
	}

	/**
	 * 删除请假数据 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:43:11
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		int result = 0;
		String jpql = "update KqglLeaveInfo t set t.visible = ? where t.id = ?";
		try {
			result = kqglLeaveInfoDao.update(jpql, CommonConstants.VISIBLE[0], id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新状态 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月18日 上午8:50:56
	 * @param id
	 * @param subflag
	 * @return
	 */
	@Override
	public KqglLeaveInfo updateSubFlag(String id, String subflag) {
		KqglLeaveInfo kqglLeaveInfo = getById(id);
		kqglLeaveInfo.setSubflag(subflag);
		return kqglLeaveInfoDao.save(kqglLeaveInfo);
	}

	@Override
	public List<KqglLeaveInfo> getsByDate(String startDate, String endDate, String leaveType, String isLeaveInLieu) {
			StringBuilder querySql = new StringBuilder();
			String creUserId=UserUtil.getCruUserId();
			querySql.append("select * ");
			querySql.append("	from kqgl_leave_info t");
			querySql.append("	where ");
			querySql.append(" t.cre_user_id='"+creUserId+"' and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
			// 拼接条件
			if (StringUtils.isNotBlank(startDate)) {
				querySql.append("   and (t.leave_start_date >= '" + startDate + "' or t.leave_start_time >='"+startDate+"')" );
			}
			if (StringUtils.isNotBlank(endDate)) {
				querySql.append("   and (t.leave_end_date <= '"+ endDate +"' or t.leave_end_time <='"+endDate+"')");
			}
			if(StringUtils.isNotBlank(leaveType)){
				querySql.append(" and t.leave_type='"+leaveType+"'");
			}
			if(StringUtils.isNotBlank(isLeaveInLieu)){
				querySql.append(" and t.is_leave_in_lieu='"+isLeaveInLieu+"'");
			}
			querySql.append("  order by t.application_time desc ");
			return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(KqglLeaveInfo.class));
	}
	
	/**
	 *  查询某个人某天是否有请假数据(流程结束的）
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月24日 下午12:55:41
	 * @param userId
	 * @param day
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDataByUseridAndDay(String userId, String day) {
		StringBuilder qjSql=new StringBuilder("select t.leave_start_date startDate,t.leave_end_date endDate,t.start_am_or_pm startAmOrPm,t.end_am_or_pm endAmOrPm from KQGL_LEAVE_INFO t  ");
		qjSql.append("  where  t.cre_user_id='" + userId + "' and t.leave_start_date <='" + day + "' and leave_end_date >='" + day + "' and t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' ");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(qjSql.toString());
		return list;
	}

	@Override
	public List<KqglLeaveInfo> getTalQjDaysAndHours(String beginQuarters, String endQuerters) throws Exception {
		String creUserId=UserUtil.getCruUserId();
		String sql="";
		if(StringUtils.isNotBlank(beginQuarters)&& StringUtils.isNotBlank(endQuerters)){
			sql="select (SELECT sum(t.leave_long_time) "
					+ "FROM  kqgl_leave_info t "
					+ "where t.leave_time_type='0' "
					+ "and t.cre_user_id='"+creUserId+"' "
					+ "and t.visible = '" + CommonConstants.VISIBLE[1] + "' "
					+ " and t.is_leave_in_lieu='1' "
					+ "and (t.leave_start_date >= '" + beginQuarters + "' or t.leave_start_time >='"+beginQuarters+"') "
					+ "and (t.leave_end_date <= '"+ endQuerters +"' or t.leave_end_time <='"+endQuerters+"') "
					+ ") talLeaveLongTimeD,"
					+ "(SELECT sum(a.leave_long_time) FROM "
					+ " kqgl_leave_info a "
					+ "where a.leave_time_type='1' "
					+ "and a.cre_user_id='"+creUserId+"' "
					+ "and a.visible = '" + CommonConstants.VISIBLE[1] + "' "
					+ "and a.is_leave_in_lieu='1' "
					+ "and (a.leave_start_date >= '" + beginQuarters + "' or a.leave_start_time >='"+beginQuarters+"') "
					+ "and (a.leave_end_date <= '"+ endQuerters +"' or a.leave_end_time <='"+endQuerters+"') "
					+ ") talLeaveLongTimeH from dual";
		}
		return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(KqglLeaveInfo.class));
	}

	@Override
	public List<Map<String, Object>> findData(String startDate, String endDate, String userids) {
		ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(userids.split(",")));
		StringBuilder sb = new StringBuilder("select t.cre_user_id userId,t.leave_start_date startDate,t.leave_end_date endDate,t.start_am_or_pm startAmOrPm,t.end_am_or_pm endAmOrPm,t.leave_type type,t.leave_long_time leaveLongTime ");
		sb.append(" from KQGL_LEAVE_INFO t where t.visible='1' and t.subflag='5'  ");
		useridIntoSql(useridList, sb);
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			sb.append(" and to_date(t.leave_start_date,'yyyy-mm-dd') <= to_date('" + endDate + "','yyyy-mm-dd') and to_date(t.leave_end_date,'yyyy-mm-dd') >= to_date('" + startDate + "','yyyy-mm-dd')");
		}
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
		return list;
	}

	private void useridIntoSql(ArrayList<String> useridList, StringBuilder sb) {
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
		if(useridList.size()>0){
			sb.append(" and  (");
			while(useridList.size()>0){
				useridSb.setLength(0);
				if(index == 0){
					sb.append(" cre_user_id in (");
				}else{
					sb.append(" or cre_user_id in (");
				}
				for(int i=0;i<900&&useridList.size()>0;i++){
					index=1;
					String tempUserid = useridList.remove(0);
					useridSb.append( tempUserid + ",");
				}
				sb.append(CommonUtils.commomSplit(useridSb.substring(0, useridSb.length()-1)) + " )");
			}
			sb.append(" )");
		}
	}

}
