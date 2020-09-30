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
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.dao.ComeLateLeaveEarlyDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.entity.ComeLateLeaveEarlyInfo;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 迟到早退ServiceImpl
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月14日 下午8:39:59
 */
@Service
public class CdztQueryServiceImpl implements CdztQueryService {

	@Autowired
    private ComeLateLeaveEarlyDao comeLateLeaveEarlyDao;
	@Autowired
	private TreeService service;
	@Autowired
	private DataDictionaryService serviceDic;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 获取迟到早退列表
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月17日 下午3:33:35
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param cdztUserId
	 * @param subflag
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String cdztUserId,String subflag) throws Exception {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from ComeLateLeaveEarlyInfo t ");
		querySql.append(
				"	where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		//查询当前用户下的子部门有哪些
//		String childDept = getChildDept();
//		querySql.append("   and t.creDeptId in (" + childDept + ")");
		// 起始时间
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append(" and t.cdztDate between '"+startDate+"' and '"+endDate+"'");
		}
		if (StringUtils.isNotBlank(cdztUserId)) {
			querySql.append("   and t.creUserId in ("+CommonUtils.commomSplit(cdztUserId)+")");
		}else{
			cdztUserId = getUserIds(UserUtil.getCruDeptId());
			querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(cdztUserId) +")");
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}
		else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "' "
					+ " or t.subflag='"+ConfigConsts.NO_APPROVAL_FLAG+"')");
		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.applicationTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<ComeLateLeaveEarlyInfo> page = comeLateLeaveEarlyDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<ComeLateLeaveEarlyInfo> content = page.getContent();
		List<DataDictionary> lcDic=serviceDic.getListByMark("kqcx_lczt", "1");
		for (ComeLateLeaveEarlyInfo comeLateLeaveEarlyInfo : content) {
			for(DataDictionary lcdic:lcDic){
				if(lcdic.getCode().equals(comeLateLeaveEarlyInfo.getSubflag())){
					comeLateLeaveEarlyInfo.setSubflag(lcdic.getName());
				}
			}
			//if (ConfigConsts.START_FLAG.equals(comeLateLeaveEarlyInfo.getSubflag())) {
			//	comeLateLeaveEarlyInfo.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
			//}
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 查询迟到早退数据
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月18日 上午11:33:20
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	public List<ComeLateLeaveEarlyInfo> getsByDate(String startDate, String endDate, String cdztUserId,String subflag) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select * ");
		querySql.append("	from KQGL_COMELATE_LEAVEEARLY_INFO t");
		querySql.append("	where ");
		querySql.append(" t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
//		String childDept = getChildDept();
//		querySql.append("   and t.cre_dept_id in (" + childDept + ")");
		// 起始时间
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append(" and t.cdzt_date between '"+startDate+"' and '"+endDate+"'");
		}
		// 申请人
		if (StringUtils.isNotBlank(cdztUserId)) {
			querySql.append("   and t.cre_user_id in (" + CommonUtils.commomSplit(cdztUserId) + ")");
		}else{
			cdztUserId = getUserIds(UserUtil.getCruDeptId());
			querySql.append("   and t.cre_user_id in (" + CommonUtils.commomSplit(cdztUserId) +")");
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag= "+subflag+"");
		}
		else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "' "
					+ " or t.subflag='"+ConfigConsts.NO_APPROVAL_FLAG+"')");
		}
		querySql.append("  order by t.application_time desc ");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(ComeLateLeaveEarlyInfo.class));
}   
	/**
	 * 获取当前部门及子部门的所有人员的ids
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月8日 下午12:23:07
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
