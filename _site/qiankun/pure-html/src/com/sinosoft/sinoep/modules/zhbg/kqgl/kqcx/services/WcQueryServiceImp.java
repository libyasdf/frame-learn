package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.text.SimpleDateFormat;
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
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.dao.GoOutInfoDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.entity.GoOutInfo;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 业务层的service实现
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月17日 上午10:18:54
 */
@Service
public class WcQueryServiceImp implements WcQueryService{
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private GoOutInfoDao dao;
	
	@Autowired
	private TreeService service;
	
	/**
	 * 查询草稿列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 上午10:18:19
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String userId, String deptId,
			String startDate, String endDate, String subflag) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from GoOutInfo t ");
		querySql.append("	where  t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		if (StringUtils.isNotBlank(subflag)) {
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}else{
			querySql.append("   and (t.subflag = '" + ConfigConsts.APPROVAL_FLAG + "' or t.subflag = '" + ConfigConsts.SUB_FLAG + "')");
		}
		if (StringUtils.isNotBlank(userId)) {
			String inString = CommonUtils.solveSqlInjectionOfIn(userId, para);
			querySql.append("   and t.creUserId in (" + inString + ")");
			//para.add(userId);
		}
		if (StringUtils.isNotBlank(deptId)) {
			querySql.append("   and t.creDeptId = ?");
			para.add(deptId);
		}else{
			//查询当前用户下的子部门有哪些
			String childDept = getChildDept();
			String inString2 = CommonUtils.solveSqlInjectionOfIn(childDept, para);
			querySql.append("   and t.creDeptId in (" + inString2 + ")");
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.goOutDate >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.goOutDate <= ?");
			para.add(endDate);
		}
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.goOutDate desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
        Page<GoOutInfo> page = dao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<GoOutInfo> content = page.getContent();
        for (GoOutInfo info : content) {
        	if(ConfigConsts.START_FLAG.equals(info.getSubflag())) {
        		info.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        	}
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 获取当前用户所在部门的子部分
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月19日 下午1:41:16
	 */
	@SuppressWarnings("unused")
	private String getChildDept() {
		String dept = UserUtil.getCruDeptId();
		JSONArray depts = service.getDeptTreeByDeptId(UserUtil.getCruDeptId());
		StringBuilder sb=new StringBuilder();
		for (Object object : depts) {
			JSONObject jsonObj=JSONObject.fromObject(object);
			sb.append(jsonObj.getString("uuid")+",");
		}
		String childDept = CommonUtils.commomSplit(sb.toString());
		return childDept;
	}
	
	

}
