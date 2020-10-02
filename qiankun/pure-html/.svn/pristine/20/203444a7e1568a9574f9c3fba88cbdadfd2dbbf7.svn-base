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
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.dao.SupplementSignDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.entity.SupplementSign;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 补签ServiceImpl TODO
 * 
 * @author 冯超
 * @Date 2018年4月11日 上午10:24:57
 */
@Service
public class BqQueryServiceImpl implements BqQueryService {

	@Autowired
	private SupplementSignDao supplementSignDao;
	@Autowired
	private TreeService service;
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String supplementSignType, String userId,String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from SupplementSign t ");
		querySql.append("	where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 查询子部门的请假数
//		String childDept = getChildDept();
//		querySql.append("   and t.creDeptId in (" + childDept + ")");
		// 起始时间
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append(" and t.supplementSignEndTime>='"+startDate+"' and t.supplementSignStartTime<='"+endDate+"'");
			/*querySql.append("   and (t.c <= '" + startDate + "'  and t.supplementSignEndTime >='" + startDate +"'");
			querySql.append("   or t.supplementSignStartTime <= '" + endDate + "'  and t.supplementSignEndTime >='" + endDate +"'");
			querySql.append("	or t.supplementSignStartTime >='" + startDate + "'  and t.supplementSignEndTime<='" + endDate + "')");*/
		}
		
		// 是否倒休
		if (StringUtils.isNotBlank(supplementSignType)) {
			querySql.append("   and t.supplementSignType = ?");
			para.add(supplementSignType);
		}
		if (StringUtils.isNotBlank(userId)) {
			querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(userId) + ")");
		}else{
			userId = getUserIds(UserUtil.getCruDeptId());
			querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(userId) +")");
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "'"
					+ " or t.subflag='"+ConfigConsts.NO_APPROVAL_FLAG+"')");
		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.applicationTime desc) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<SupplementSign> page = supplementSignDao.query(querySql.toString(), pageable, para.toArray());
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	// 获取当前用户的子部门
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
	@Override
	public List<SupplementSign> getsByDate(String startDate, String endDate, String supplementSignType,String userId,String subflag) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select * ");
		querySql.append("	from kqgl_supplement_sign_info t");
		querySql.append("	where ");
		querySql.append("  t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		//String childDept = getChildDept();
		//querySql.append("   and t.cre_dept_id in (" + childDept + ")");
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.supplement_sign_start_time >= '" + startDate + "'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.supplement_sign_end_time <= '"+ endDate +"'");
		}
		if(StringUtils.isNotBlank(supplementSignType)){
			querySql.append(" and t.supplement_sign_type='"+supplementSignType+"'");
		}
		if(StringUtils.isNotBlank(userId)){
			querySql.append("   and t.cre_user_id in (" + CommonUtils.commomSplit(userId) + ")");
		}else{
			userId = getUserIds(UserUtil.getCruDeptId());
			querySql.append("   and t.cre_user_id in (" + CommonUtils.commomSplit(userId) +")");
		}
		if(StringUtils.isNotBlank(subflag)){
			querySql.append(" and t.subflag='"+subflag+"'");
		}else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "'"
					+ " or t.subflag='"+ConfigConsts.NO_APPROVAL_FLAG+"')");
		}
		querySql.append("  order by t.application_time desc ");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(SupplementSign.class));
	}
}
