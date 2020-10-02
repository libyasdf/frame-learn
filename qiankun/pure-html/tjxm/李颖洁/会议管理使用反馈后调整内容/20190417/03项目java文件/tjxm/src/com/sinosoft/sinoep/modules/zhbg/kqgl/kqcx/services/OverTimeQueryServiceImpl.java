package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;



import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.dao.OverTimeDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.entity.OverTime;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.user.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 加班查询service
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月19日 下午3:04:37
 */
@Service
public class OverTimeQueryServiceImpl implements OverTimeQueryService {

	@Autowired
	private OverTimeDao overTimeDao;
	@Autowired
	 private TreeService service;

	
	/**
	 * 加班查询分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月19日 下午3:04:59
	 * @param pageable
	 * @param pageImpl
	 * @param userId
	 * @param deptId
	 * @param overTimeType
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String userId,String deptId,String overTimeType, String startDate,
			String endDate, String subflag,String isOne) {
		
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
	

		querySql.append("	from OverTime t ");
		querySql.append("	where  t.visible = '" + CommonConstants.VISIBLE[1] + "'");

		// 拼接条件
		/*if (StringUtils.isBlank(userId)) {
			userId = getUserIds(UserUtil.getCruDeptId());
			
		}
		if(StringUtils.isNotBlank(userId)) {
			ArrayList<String> useridList = new ArrayList<String>(Arrays.asList(userId.split(",")));
			useridIntoSql(useridList, querySql);
		}*/
		if (StringUtils.isNotBlank(userId)) {
			querySql.append("   and t.creUserId in ("+CommonUtils.commomSplit(userId)+")");
		}else if("1".equals(isOne)&&StringUtils.isBlank(userId)){
			userId = getUserIds(UserUtil.getCruDeptId());
			querySql.append("   and t.creUserId in (" + CommonUtils.commomSplit(userId) +")");
		}else if("0".equals(isOne)&&StringUtils.isBlank(userId)){
			querySql.append("   and t.creUserId is null");
		}
		
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}else{
			querySql.append("   and (t.subflag != '" + ConfigConsts.START_FLAG + "' and t.subflag != '" + ConfigConsts.REMOVE_FLAG + "')");
		}
		/*if (StringUtils.isNotBlank(deptId)) {
			querySql.append("   and t.creDeptId = ?");
			para.add(deptId);
		}else{
			//查询当前用户下的子部门有哪些
			String childDept = getChildDept();
			querySql.append("   and t.creDeptId in (" + childDept + ")");
		}*/
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
	private void useridIntoSql(ArrayList<String> useridList, StringBuilder sb) {
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
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
				sb.append(CommonUtils.commomSplit(useridSb.substring(0, useridSb.length()-1)) + " )");
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
