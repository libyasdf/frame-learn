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
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.dao.BusinessTripDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.entity.BusinessTrip;
import com.sinosoft.sinoep.user.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 出差业务层service实现类 TODO
 * 
 * @author 张建明
 * @Date 2018年4月12日 上午10:38:41
 */
@Service
public class CcQueryServiceImpl implements CcQueryService {
	
	@Autowired
	private BusinessTripDao businessTripDao;
	
	@Autowired
	private TreeService service;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataDictionaryService serviceDic;

	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String busiTripType, String userId,
			String startDate, String endDate, String subflag,String iftjj,String isOne) throws Exception {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from BusinessTrip t ");
		querySql.append("	where  t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		//出差查询看的的是本部门信息  
		//行政出差和业务出差查询的是天津局的所有人员
//		String childDept = getChildDept();
//		if(StringUtils.isNotBlank(iftjj) && "0".equals(iftjj)){
//			querySql.append("   and t.creDeptId in (" + childDept + ")");
//		}
		
		if (StringUtils.isNotBlank(busiTripType)) {
			querySql.append("  and t.busiTripType = ?");
			para.add(busiTripType);
		}
		if (StringUtils.isNotBlank(userId)) {
			String[] userids = userId.split(",");
			//querySql.append(" and (");
			//querySql.append("   and (t.creUserId in (" + CommonUtils.commomSplit(userId) + ") ");
			@SuppressWarnings("unchecked")
			ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(userId.split(",")));
			useridIntoSql(useridList, querySql,para);
			querySql.deleteCharAt(querySql.length()-1);
			for(int i=0;i<userids.length;i++){
				querySql.append(" or t.tripColleagueIds like ? ");
				para.add("%"+userids[0]+"%");
			}
			querySql.append(")");
		}
		if(StringUtils.isBlank(userId) && iftjj.equals("0") && "1".equals(isOne)){
			userId = getUserIds(UserUtil.getCruDeptId());
			@SuppressWarnings("unchecked")
			ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(userId.split(",")));
			useridIntoSql(useridList, querySql,para);
			
			//querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(userId) +")");
		}
		if(StringUtils.isBlank(userId) && iftjj.equals("0") && "0".equals(isOne)){
			querySql.append("   and t.creUserId is null");
		}
		if(StringUtils.isBlank(userId) && iftjj.equals("1") && "0".equals(isOne)){
			querySql.append("   and t.creUserId is null");
		}
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append("  and t.endTime>= ? and t.startTime<= ?");
			para.add(startDate);
			para.add(endDate);
		}
		if(StringUtils.isNotBlank(subflag)){
			querySql.append(" and t.subflag=? ");
			para.add(subflag);
		}else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "'"
					+ " or t.subflag='"+ConfigConsts.NO_APPROVAL_FLAG+"')");
		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.applicationTime desc ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<BusinessTrip> page = businessTripDao.query(querySql.toString(), pageable, para.toArray());
		List<BusinessTrip> content=page.getContent();
		List<DataDictionary> lcDic=serviceDic.getListByMark("kqcx_lczt", "1");
		for(BusinessTrip bus:content){
			for(DataDictionary lcdic:lcDic){
				if(lcdic.getCode().equals(bus.getSubflag())){
					bus.setSubflag(lcdic.getName());
				}
			}
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
				//sb.append(CommonUtils.commomSplit(useridSb.substring(0, useridSb.length()-1)) + " )");
				sb.append(inString + " )");
			}
			sb.append(" )");
		}
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
	public List<BusinessTrip> getsByDate(String startDate, String endDate, String busiTripType, String userId, String subflag,String iftjj) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select t.CRE_USER_NAME,t.APPLICATION_UNIT_NAME,t.TRIP_COLLEAGUE,"
				+ "t.START_TIME,t.END_TIME,(case t.BUSI_TRIP_TYPE when '0' then '业务出差' when '1' then '行政出差' else '其他' end) BUSI_TRIP_TYPE ,t.LONG_TIME,"
				+ "t.DESTINATION,(case t.IS_HAVE_RECEPTION_FEES when '0' then '无' when '1' then '有' else ' ' end) IS_HAVE_RECEPTION_FEES,t.RECEPTION_FEES,t.VEHICLE,t.SUBFLAG");
		querySql.append("	from KQGL_BUSINESS_TRIP_INFO t");
		querySql.append("	where ");
		querySql.append(" t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		//出差查询看的的是本部门信息  
		//行政出差和业务出差查询的是天津局的所有人员
//		String childDept = getChildDept();
//		if(StringUtils.isNotBlank(iftjj) && "0".equals(iftjj)){
//			querySql.append("   and t.cre_dept_id in (" + childDept + ")");
//		}
		
//		if (StringUtils.isNotBlank(startDate)) {
//			querySql.append("   and (t.start_date >= '" + startDate + "' or t.start_time >='"+startDate+"')" );
//		}
//		if (StringUtils.isNotBlank(endDate)) {
//			querySql.append("   and (t.end_date <= '"+ endDate +"' or t.end_time <='"+endDate+"')");
//		}
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append("  and t.end_time>= ? and t.start_time<= ?");
			para.add(startDate);
			para.add(endDate);
		}
		if(StringUtils.isNotBlank(busiTripType)){
			querySql.append(" and t.BUSI_TRIP_TYPE= ?");
			para.add(busiTripType);
		}
		String inString = "";
		if(StringUtils.isNotBlank(userId)){
			String[] userids = userId.split(",");
			querySql.append("	and (");
			//处理userId，防止参数过长导致异常
			List<String[]> userIdList = subUser(userId);
			for(int n = 0;n < userIdList.size(); n++){
				String[] ids = userIdList.get(n);
				String userIdStr = StringUtils.join(ids,",");
				inString = CommonUtils.solveSqlInjectionOfIn(userIdStr, para);
				if(n==0){
					querySql.append("   t.cre_user_id in (" + inString + ") ");
				}else{
					querySql.append("   or t.cre_user_id in (" + inString + ") ");
				}
			}
			for(int i=0;i<userids.length;i++){
				querySql.append(" or instr(t.trip_colleague_ids,?)>0 ");
				para.add(userids[i]);
			}
			querySql.append(")");
			//querySql.append("   and t.cre_user_id in (" + CommonUtils.commomSplit(userId) + ")");
		}
		
		if(StringUtils.isBlank(userId) && iftjj.equals("0")){
			userId = getUserIds(UserUtil.getCruDeptId());
			inString = CommonUtils.solveSqlInjectionOfIn(userId, para);
			querySql.append("   and t.cre_user_id in (" + inString +")");
		}
		if(StringUtils.isNotBlank(subflag)){
			querySql.append(" and t.subflag= ?");
			para.add(subflag);
		}else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "'"
					+ " or t.subflag='"+ConfigConsts.NO_APPROVAL_FLAG+"')");
		}
		
		querySql.append("  order by t.application_time desc ");
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(BusinessTrip.class),para.toArray());
	}

	/**
	 * TODO 处理过长的userId
	 * @author 李颖洁  
	 * @date 2019年5月21日下午6:17:58
	 * @param userIds
	 * @return List<String[]>
	 */
	private static List<String[]> subUser(String userIds){
		userIds = userIds.replaceAll(",", ";");
		List<String[]> userIdList = new ArrayList<>();
		String[] userIdArray = userIds.split(";");
		List<String> userList = Arrays.asList(userIdArray);
		while (userList.size() > 1000){
			List<String> userList1 = new ArrayList<>();
			userList1 = userList.subList(0,1000);
			String[] array = new String[1000];
			userList1.toArray(array);
			userIdList.add(array);
			userList = userList.subList(1000,userList.size());
		}
		String[] array = new String[userList.size()];
		userList.toArray(array);
		userIdList.add(array);
		return userIdList;
	}
}
