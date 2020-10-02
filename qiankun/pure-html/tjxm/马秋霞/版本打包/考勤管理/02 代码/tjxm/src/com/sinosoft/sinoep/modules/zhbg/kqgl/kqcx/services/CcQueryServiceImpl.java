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
			String startDate, String endDate, String subflag,String iftjj) throws Exception {
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
			querySql.append("   and (t.creUserId in (" + CommonUtils.commomSplit(userId) + ") ");
			for(int i=0;i<userids.length;i++){
				querySql.append(" or instr(t.tripColleagueIds,"+ userids[i] +")>0 ");
			}
			querySql.append(")");
		}
		if(StringUtils.isBlank(userId) && iftjj.equals("0")){
			userId = getUserIds(UserUtil.getCruDeptId());
			querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(userId) +")");
		}
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			querySql.append("  and t.endTime>='"+startDate+"' and t.startTime<='"+endDate+"'");
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
			querySql.append("  and t.end_time>='"+startDate+"' and t.start_time<='"+endDate+"'");
		}
		if(StringUtils.isNotBlank(busiTripType)){
			querySql.append(" and t.BUSI_TRIP_TYPE='"+busiTripType+"'");
		}
		if(StringUtils.isNotBlank(userId)){
			querySql.append("   and t.cre_user_id in (" + CommonUtils.commomSplit(userId) + ")");
		}
		
		if(StringUtils.isBlank(userId) && StringUtils.isNotBlank(iftjj)){
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
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(BusinessTrip.class));
	}

}
