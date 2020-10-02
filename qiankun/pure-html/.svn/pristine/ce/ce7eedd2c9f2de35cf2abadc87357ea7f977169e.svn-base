package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.dao.KqglAttendenceHandleDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 考勤查询
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月27日 下午8:08:53
 */
@Service
public class AttendenceQueryServiceImp implements AttendenceQueryService {
	@Autowired
	private KqglAttendenceHandleDao kqglAttendenceHandleDao;
	 @Autowired
	 private TreeService service;

	@SuppressWarnings("unchecked")
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String cardNumber,String userId) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		JSONObject json = new JSONObject();
		querySql.append("	from KqglAttendenceHandle t ");
		querySql.append("	where  1=1 ");
		
		/*if (StringUtils.isNotBlank(cardNumber)) {
			querySql.append("   and t.cardNumber = ?");
			para.add(cardNumber);
		}else{
			//该部门人员卡号
			 json = getCardNoms();
			querySql.append("   and t.cardNumber in (" + CommonUtils.commomSplit(json.getString("cardNum")) + ")");
		}*/
		if(StringUtils.isNotBlank(userId)){
			getCardNoms(userId,json);
			querySql.append("   and t.cardNumber in (" + CommonUtils.commomSplit(json.getString("cardNum")) + ")");
		}else{
			 json = getCardNoms();
			 querySql.append("   and t.cardNumber in (" + CommonUtils.commomSplit(json.getString("cardNum")) + ")");
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.importDate >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.importDate <= ?");
			para.add(endDate);
		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.importDate desc) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<KqglAttendenceHandle> page = kqglAttendenceHandleDao.query(querySql.toString(), pageable, para.toArray());
		List<KqglAttendenceHandle> content = page.getContent();
		for (KqglAttendenceHandle kqglAttendenceHandle : content) {
			Map<String,SysFlowUserVo > userMap = (Map<String,SysFlowUserVo >)json.get("userInfo");
			Object userJson = userMap.get(kqglAttendenceHandle.getCardNumber());
			JSONObject jsonObj = JSONObject.fromObject(userJson);
			kqglAttendenceHandle.setName(jsonObj.getString("userNameFull"));
			kqglAttendenceHandle.setDeptName(jsonObj.getString("userDeptName"));
		}
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int) page.getTotalElements());
		return pageImpl;
	}
	
	/**
	 * 根据某些用户id获取该用户的一些卡号，及用户信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月29日 下午6:38:41
	 * @param userId
	 */
	private void getCardNoms(String userId,JSONObject json) {
		Map<String,SysFlowUserVo> userInfo = new HashMap<String,SysFlowUserVo>();//key为卡号，value为用户
		StringBuilder cardNoms = new StringBuilder();//所有的卡号
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(userId);
		String[] userids = userId.split(",");
		for (String userid : userids) {
			SysFlowUserVo userVo = map.get(userid);
			if(StringUtils.isNotBlank(userVo.getCardNumber())){
				cardNoms.append(userVo.getCardNumber()+",");
				userInfo.put(userVo.getCardNumber(), userVo);
			}
			
		}
		if(cardNoms.length()>0){
			json.put("cardNum", cardNoms.substring(0,cardNoms.length()-1));
		}else{
			json.put("cardNum", "");
		}
		
		json.put("userInfo", userInfo);
	}

	/**
	 * 获取当前用户部门及子部门的人员的卡号
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月28日 上午11:13:20
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JSONObject getCardNoms() {
		List<String> userIdList = new ArrayList();
		StringBuilder sb = new StringBuilder();
		StringBuilder cardNoms = new StringBuilder();
		JSONObject returnJson = new JSONObject(); //用于返回的数据
		Map<String,String> useidAndDeptidMap = new HashMap<String,String> ();//用于存放人员和部门id的map
		Map<String,String> deptidAndDeptnameMap = new HashMap<String,String> ();//用于部门id和部门名称的map
		Map<String,SysFlowUserVo> userInfo = new HashMap<String,SysFlowUserVo>();
		String tempid=UserUtil.getCruChushiId();
		if(!StringUtils.isNotBlank(UserUtil.getCruChushiId())){
			tempid=UserUtil.getCruDeptId();
		}
		JSONArray jsonArry = service.getDeptAndUserBydeptId(tempid);
		for (Object object : jsonArry) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			if (!StringUtils.isNotBlank(jsonObj.getString("deptlevel"))) {
				//该json数据为人员
				sb.append(jsonObj.getString("uuid") + ",");
				userIdList.add(jsonObj.getString("uuid"));
				useidAndDeptidMap.put(jsonObj.getString("uuid"),jsonObj.getString("uupid") );
			}else{
				//该条数据为部门数据
				deptidAndDeptnameMap.put(jsonObj.getString("uuid"), jsonObj.getString("name"));
			}
		}
		String userids = sb.toString();
		String useridt = userids.substring(0, userids.length()-1);
		Map<String, SysFlowUserVo> map = UserUtil.getUserVo(useridt);
		for (String userid : userIdList) {
			SysFlowUserVo userVo = map.get(userid);
			userVo.setUserDeptName(deptidAndDeptnameMap.get(useidAndDeptidMap.get(userid)));
			//userVo.setSuperDeptName(superDeptName);
			String cardNo = map.get(userid).getCardNumber();
			if(StringUtils.isNotBlank(cardNo)){
				cardNoms.append(cardNo+",");
				userInfo.put(cardNo, userVo);
			}
			
		}
		if(cardNoms.length()>0){
			returnJson.put("cardNum", cardNoms.substring(0,cardNoms.length()-1));
		}else{
			returnJson.put("cardNum", "");
		}
		
		returnJson.put("userInfo", userInfo);
		return returnJson;
	}


	
	
}
