package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.util.ArrayList;
import java.util.List;
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
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.dao.KqglLeaveInfoDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.entity.KqglLeaveInfo;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 请假ServiceImpl TODO
 * 
 * @author 冯超
 * @Date 2018年4月11日 上午10:24:57
 */
@Service
public class QjQueryServiceImpl implements QjQueryService {

	@Autowired
	private KqglLeaveInfoDao kqglLeaveInfoDao;
	@Autowired
	private TreeService service;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DataDictionaryService serviceDic;

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
			String userId,String subflag) throws Exception {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from KqglLeaveInfo t ");
		querySql.append("	where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 查询子部门的请假数
//		String childDept = getChildDept();
//		querySql.append("   and t.creDeptId in (" + childDept + ")");
		// 起始时间
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append(" and t.leaveStartDate<='"+endDate+"' and t.leaveEndDate>='"+startDate+"'");
			//querySql.append(" and (((t.leaveStartDate >= '" + startDate + "' and t.leaveStartDate <='" + endDate + "')");
			//querySql.append(" or (t.leaveEndDate >= '" + startDate + "' and t.leaveEndDate <='" + endDate + "')");
			//querySql.append(" or (t.leaveStartDate <= '" + startDate + "' and t.leaveEndDate >='" + endDate + "'))");
			//querySql.append(" or ((substr(t.leaveStartTime,0,10) >= '" + startDate + "' and substr(t.leaveStartTime,0,10) <='" + endDate + "')");
			//querySql.append(" or (substr(t.leaveEndTime,0,10)>= '" + startDate + "' and substr(t.leaveEndDate,0,10) <='" + endDate + "')");
			//querySql.append(" or (substr(t.leaveStartTime,0,10) <= '" + startDate + "' and substr(t.leaveEndTime,0,10) >='" + endDate + "')))");
		}

		// 申请人
		if (StringUtils.isNotBlank(userId)) {
			querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(userId) + ")");
		}else{
			userId = getUserIds(UserUtil.getCruDeptId());
			querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(userId) +")");
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag= ?");
			para.add(subflag);
		}else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "' "
					+ " or t.subflag='"+ConfigConsts.NO_APPROVAL_FLAG+"')");
		}
		// 是否倒休
//		if (StringUtils.isNotBlank(isLeaveInLieu)) {
//			querySql.append("   and t.isLeaveInLieu = ?");
//			para.add(isLeaveInLieu);
//		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.applicationTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<KqglLeaveInfo> page = kqglLeaveInfoDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<KqglLeaveInfo> content = page.getContent();
		List<DataDictionary> dicts=serviceDic.getListByMark("leaveType", "1");
		List<DataDictionary> lcDic=serviceDic.getListByMark("kqcx_lczt", "1");
		for (KqglLeaveInfo kqglLeaveInfo : content) {
				for(DataDictionary dic:dicts){
					if(dic.getCode().equals(kqglLeaveInfo.getLeaveType())){
						kqglLeaveInfo.setLeaveType(dic.getName());
					}
				}
				for(DataDictionary lcdic:lcDic){
					if(lcdic.getCode().equals(kqglLeaveInfo.getSubflag())){
						kqglLeaveInfo.setSubflag(lcdic.getName());
					}
				}
			
//			// 判断请假时间类型 0：按日期 1：按时间
//			if (StringUtils.isNotBlank(kqglLeaveInfo.getLeaveTimeType())
//					&& kqglLeaveInfo.getLeaveTimeType().equals(EmConstants.QJTIMETYPE[1])) {
//				kqglLeaveInfo.setLeaveLongTime(kqglLeaveInfo.getLeaveLongTime() + "(h)");
//				kqglLeaveInfo.setLeaveStartDate(kqglLeaveInfo.getLeaveStartTime());
//				kqglLeaveInfo.setLeaveEndDate(kqglLeaveInfo.getLeaveEndTime());
//			} else {
//				String leaLongTime = kqglLeaveInfo.getLeaveLongTime();
//				if (StringUtils.isNotBlank(leaLongTime)) {
//					kqglLeaveInfo.setLeaveLongTime(kqglLeaveInfo.getLeaveLongTime() + "(d)");
//				}
//			}
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 获取当前部门和子部门的人员ids
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月8日 下午12:18:23
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
	
//	private String getChildDept() {
//		JSONArray depts = service.getDeptTreeByDeptId(UserUtil.getCruDeptId());
//		StringBuilder sb = new StringBuilder();
//		for (Object object : depts) {
//			JSONObject jsonObj = JSONObject.fromObject(object);
//			sb.append(jsonObj.getString("uuid") + ",");
//		}
//		String childDept = CommonUtils.commomSplit(sb.toString());
//		return childDept;
//	}
	
	public List<KqglLeaveInfo> getsByDate(String startDate, String endDate, String userId,String subflag) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select * ");
		querySql.append("	from kqgl_leave_info t");
		querySql.append("	where ");
		querySql.append(" t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
//		String childDept = getChildDept();
//		querySql.append("   and t.cre_dept_id in (" + childDept + ")");
		// 起始时间
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append(" and t.leave_start_date<='"+endDate+"' and t.leave_end_date>='"+startDate+"'");
//			querySql.append(" and (((t.leave_start_date >= '" + startDate + "' and t.leave_start_date <='" + endDate + "')");
//			querySql.append(" or (t.leave_end_date >= '" + startDate + "' and t.leave_end_date <='" + endDate + "')");
//			querySql.append(" or (t.leave_start_date <= '" + startDate + "' and t.leave_end_date >='" + endDate + "'))");
//			
//			querySql.append(" or ((substr(t.leave_start_time,0,10) >= '" + startDate + "' and substr(t.leave_start_time,0,10) <='" + endDate + "')");
//			querySql.append(" or (substr(t.leave_end_time,0,10)>= '" + startDate + "' and substr(t.leave_end_time,0,10) <='" + endDate + "')");
//			querySql.append(" or (substr(t.leave_start_time,0,10) <= '" + startDate + "' and substr(t.leave_end_time,0,10) >='" + endDate + "')))");
		}
		// 申请人
		if (StringUtils.isNotBlank(userId)) {
			querySql.append("   and t.cre_user_id in (" + CommonUtils.commomSplit(userId) + ")");
		}
		else{
			userId = getUserIds(UserUtil.getCruDeptId());
			querySql.append("   and t.cre_user_id in (" + CommonUtils.commomSplit(userId) +")");
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag= "+subflag+"");
		}else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "' "
					+ " or t.subflag='"+ConfigConsts.NO_APPROVAL_FLAG+"')");
		}
		// 是否倒休
//		if (StringUtils.isNotBlank(isLeaveInLieu)) {
//			querySql.append("   and t.is_leave_in_lieu = "+isLeaveInLieu+"");
//		}
		querySql.append("  order by t.application_time desc ");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(KqglLeaveInfo.class));
}
}
