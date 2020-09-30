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

import edu.emory.mathcs.backport.java.util.Arrays;
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
			String cdztUserId,String subflag,String isOne) throws Exception {
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
			querySql.append(" and t.cdztDate between ? and ?");
			para.add(startDate);
			para.add(endDate);
		}
		if (StringUtils.isNotBlank(cdztUserId)) {
			@SuppressWarnings("unchecked")
			ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(cdztUserId.split(",")));
			useridIntoSql(useridList, querySql,para);
			
			//querySql.append("   and t.creUserId in ("+CommonUtils.commomSplit(cdztUserId)+")");
		}else if("1".equals(isOne)&&StringUtils.isBlank(cdztUserId)){
			cdztUserId = getUserIds(UserUtil.getCruDeptId());
			@SuppressWarnings("unchecked")
			ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(cdztUserId.split(",")));
			useridIntoSql(useridList, querySql,para);
			
			//querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(cdztUserId) +")");
		}else if("0".equals(isOne)&&StringUtils.isBlank(cdztUserId)){
			querySql.append("   and t.creUserId is null");
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
	
	private void useridIntoSql(ArrayList<String> useridList, StringBuilder sb,List<Object> para) {
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
		String inString = "";
		if(useridList.size()>0){
			sb.append(" and  (");
			while(useridList.size()>0){
				useridSb.setLength(0);
				if(index == 0){
					sb.append(" t.creUserId in (");
				}else{
					sb.append(" or t.creUserId in (");
				}
				for(int i=0;i<900&&useridList.size()>0;i++){
					index=1;
					String tempUserid = useridList.remove(0);
					useridSb.append( tempUserid + ",");
				}
				inString = CommonUtils.solveSqlInjectionOfIn(useridSb.toString(), para);
				sb.append(inString+ " )");
			}
			sb.append(" )");
		}
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
		List<Object> para = new ArrayList<>();
		querySql.append("select * ");
		querySql.append("	from KQGL_COMELATE_LEAVEEARLY_INFO t");
		querySql.append("	where ");
		querySql.append(" t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
//		String childDept = getChildDept();
//		querySql.append("   and t.cre_dept_id in (" + childDept + ")");
		// 起始时间
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append(" and t.cdzt_date between ? and ?");
			para.add(startDate);
			para.add(endDate);
		}
		String inString = "";
		// 申请人
		if (StringUtils.isNotBlank(cdztUserId)) {
			inString = CommonUtils.solveSqlInjectionOfIn(cdztUserId, para);
			querySql.append("   and t.cre_user_id in (" + inString + ")");
		}else{
			cdztUserId = getUserIds(UserUtil.getCruDeptId());
			inString = CommonUtils.solveSqlInjectionOfIn(cdztUserId, para);
			querySql.append("   and t.cre_user_id in (" + inString +")");
		}
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag= ?");
			para.add(subflag);
		}
		else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "' "
					+ " or t.subflag='"+ConfigConsts.NO_APPROVAL_FLAG+"')");
		}
		querySql.append("  order by t.application_time desc ");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(ComeLateLeaveEarlyInfo.class),para.toArray());
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
