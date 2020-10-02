package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.service.AbsenteeismInfoService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.service.ComeLateLeaveEarlyService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.common.KqglConstant;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.service.KqglLeaveInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.util.MyUserUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.dao.KqglAttendenceHandleDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
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

	@Autowired
	private KqglLeaveInfoService kqglLeaveInfoService;

	@Autowired
	private AbsenteeismInfoService absenteeismInfoService;

	@Autowired
	private ComeLateLeaveEarlyService comeLateLeaveEarlyService;
	 @Autowired
		private UserInfoService userInfoService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	MyUserUtil myUserUtil;

	@SuppressWarnings("unchecked")
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String cardNumber,String userId,String isAll) {
	/*	String roleNo = UserUtil.getCruUserRoleNo();
		if(roleNo.contains(KqglConstant.KQMANGE_ROLE_ID) && "1".equals(isAll)) {
			//当前用户为考勤管理员，查询所有数据
			List<UserInfo> list = userInfoService.getAllUserByDeptId("441");
			for (UserInfo userInfo : list) {
				userId=userId+userInfo.getUid()+",";
			}
			userId=userId.substring(0, userId.length()-1);
		}else if((StringUtils.isBlank(UserUtil.getCruChushiId()) && StringUtils.isBlank(userId)) || (roleNo.contains(KqglConstant.KQMANGE_ROLE_ID) && StringUtils.isBlank(userId))){//是局长，列表什么都不显示
			List<KqglAttendenceHandle> content = new ArrayList<KqglAttendenceHandle>();
			pageImpl.setFlag("1");
			pageImpl.getData().setRows(content);
			pageImpl.getData().setTotal(0);
			return pageImpl;
		} */
		if(StringUtils.isBlank(userId)) {
			List<KqglAttendenceHandle> content = new ArrayList<KqglAttendenceHandle>();
			pageImpl.setFlag("1");
			pageImpl.getData().setRows(content);
			pageImpl.getData().setTotal(0);
			return pageImpl;
		}
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		JSONObject json = new JSONObject();
		querySql.append("	from KqglAttendenceHandle t ");
		querySql.append("	where  1=1 ");
		
		StringBuilder cardSb = new StringBuilder();
		int index = 0;
		if(StringUtils.isNotBlank(userId)){
			getCardNoms(userId,json);
			setCardNomIntoSql(querySql, json, cardSb, index,para);			
		}/*else{
			 json = getCardNoms();
			 setCardNomIntoSql(querySql, json, cardSb, index);	
			 //querySql.append("   and t.cardNumber in (" + CommonUtils.commomSplit(json.getString("cardNum")) + ")");
		}*/
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
			querySql.append("  order by t.importDate desc,t.cardNumber desc ) ");
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

	@Override
	public String getUserIds(String treeIds) {
		String userids = "";
		String[] treeId = treeIds.split(",");
		for(int i=0; i<treeId.length; i++){
			JSONArray deptUserList = service.getDeptUserTreeByDeptId(treeId[i], 20);
			StringBuilder sb = new StringBuilder();
			for (Object object : deptUserList) {
				JSONObject jsonObj = JSONObject.fromObject(object);
				String type = jsonObj.getString("type");
				if(type.equals("user")){
					sb.append(jsonObj.getString("uuid") + ",");
				}
			}
			userids += sb.toString();
		}
		return userids.substring(0, userids.length()-1);
	}

	@Override
	public List<Map<String,SysFlowUserVo>> getDeptIds(String treeIds) {
		List<Map<String,SysFlowUserVo>> list = new ArrayList<Map<String,SysFlowUserVo>>();
		String[] treeId = treeIds.split(",");
		for(int i=0; i<treeId.length; i++){
			JSONArray deptUserList = service.getDeptUserTreeByDeptId(treeId[i], 1);
			String juName = "";
			Map<String,SysFlowUserVo> map = new HashMap<String,SysFlowUserVo>();
			for (Object object : deptUserList) {
				JSONObject jsonObj = JSONObject.fromObject(object);
				if(jsonObj.getString("uuid").equals(treeId[i])){
					juName = jsonObj.getString("name");
					break;
				}
			}
			for (Object object : deptUserList) {
				JSONObject jsonObj = JSONObject.fromObject(object);
				String type = jsonObj.getString("type");
				if(type.equals("dept") && !jsonObj.getString("uuid").equals(treeId[i])){
					SysFlowUserVo sysFlowUserVo = new SysFlowUserVo();
					sysFlowUserVo.setUserJuName(juName);
					sysFlowUserVo.setUserChushiName(jsonObj.getString("name"));
					map.put(jsonObj.getString("uuid"),sysFlowUserVo);
				}
			}
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getAllList(String timeRange, String dataType, String treeIds, String activeId) {
		//返回的数据
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		List<Map<String,Object>> kqList = new ArrayList<Map<String,Object>>();

		String absenteeismType = ""; //0旷工，1迟到，2早退

		if(StringUtils.isNotBlank(treeIds)){
			String urdpIds = "";
			if(activeId.equals("3")){
				//请假数据
				absenteeismType = "0";
				kqList = kqglLeaveInfoService.groupByLeaveReason(timeRange,dataType,treeIds);
			}else{
				String state = "";
				if (activeId.equals("1")) {
					state = "0"; //迟到
					absenteeismType = "1";
				}else{
					state = "1"; //早退
					absenteeismType = "2";
				}
				kqList = comeLateLeaveEarlyService.groupByCdztReason(timeRange,dataType,state,treeIds);
			}

			List<Map<String,Object>> tsList = absenteeismInfoService.findByParams(timeRange,dataType,absenteeismType,treeIds);

			if(dataType.equals("user")){
				//tree获取ids
				urdpIds = getUserIds(treeIds);

				Map<String, SysFlowUserVo> userVoMap = UserUtil.getUserVo(urdpIds);
				String[] userIds = urdpIds.split(",");
				Set<Map.Entry<String, SysFlowUserVo>> entries = userVoMap.entrySet();
				for(int i=0; i<userIds.length; i++){
					for (Map.Entry<String, SysFlowUserVo> entry : entries) {
						if(entry.getKey().equals(userIds[i])){
							Map map = new HashMap();
							map.put("BUMEN",entry.getValue().getUserJuName()); //部门
							map.put("CHUSHI",entry.getValue().getUserChushiName());//处室
							map.put("NAME",entry.getValue().getUserName()); //用户名
							for(Map<String,Object> kqMap : kqList){
								if(kqMap.get("CRE_USER_ID").toString().equals(entry.getKey())){
									for(Object key : kqMap.keySet()){
										if(!key.equals("CRE_USER_ID")){
											String value = kqMap.get(key) == null? "": kqMap.get(key).toString();
											map.put(key,value);
										}
									}
									break;
								}
							}
							for(Map<String,Object> tsMap : tsList){
								if(tsMap.get("ABSENTEEISM_USER_ID").toString().equals(entry.getKey())){
									String value = tsMap.get("SC") == null? "": tsMap.get("SC").toString();
									map.put("TS",value);
									break;
								}
							}
							list.add(map);
							break;
						}
					}
				}
			}else{
				List<Map<String,SysFlowUserVo>> deptList = getDeptIds(treeIds);
				for(Map<String,SysFlowUserVo> deptVoMap : deptList){
					Set<Map.Entry<String, SysFlowUserVo>> entries = deptVoMap.entrySet();
					for (Map.Entry<String, SysFlowUserVo> entry : entries) {
						Map map = new HashMap();
						map.put("BUMEN",entry.getValue().getUserJuName()); //部门
						map.put("CHUSHI",entry.getValue().getUserChushiName());//处室
						for(Map<String,Object> kqMap : kqList){
							if(kqMap.get("CRE_CHUSHI_ID").toString().equals(entry.getKey())){
								for(Object key : kqMap.keySet()){
									if(!key.equals("CRE_CHUSHI_ID")){
										String value = kqMap.get(key) == null? "": kqMap.get(key).toString();
										map.put(key,value);
									}
								}
								break;
							}
						}
						for(Map<String,Object> tsMap : tsList){
							if(tsMap.get("ABS_APPLICANT_CHUSHI_ID").toString().equals(entry.getKey())){
								String value = tsMap.get("SC") == null? "": tsMap.get("SC").toString();
								map.put("TS",value);
								break;
							}
						}
						list.add(map);
					}
				}

			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getList(String timeRange, String dataType, String treeIds, String activeId) {

		List<Map<String,Object>> kqList = new ArrayList<Map<String,Object>>();

		String absenteeismType = ""; //0旷工，1迟到，2早退

		if(StringUtils.isNotBlank(treeIds)){

			if(activeId.equals("3")){
				//请假数据
				absenteeismType = "0";
				kqList = kqglLeaveInfoService.groupByLeaveReason(timeRange,dataType,treeIds);
			}else{
				String state = "";
				if (activeId.equals("1")) {
					state = "0"; //迟到
					absenteeismType = "1";
				}else{
					state = "1"; //早退
					absenteeismType = "2";
				}
				kqList = comeLateLeaveEarlyService.groupByCdztReason(timeRange,dataType,state,treeIds);
			}

			List<Map<String,Object>> tsList = absenteeismInfoService.findByParams(timeRange,dataType,absenteeismType,treeIds);
			String key = "";
			String key1 = "";
			if(dataType.equals("user")){
				key = "CRE_USER_ID";
				key1 = "ABSENTEEISM_USER_ID";
			}else{
				key = "CRE_CHUSHI_ID";
				key1 = "CRE_CHUSHI_ID";
			}

			List<Map<String,Object>> list =  new ArrayList<Map<String,Object>>();
			list.addAll(kqList);
			for(Map<String,Object> tsMap : tsList){
				if(list.size() > 0){
					int i = 1;
					for(Map<String,Object> kqMap:list){
						if(kqMap.get(key).toString().equals(tsMap.get(key1))){
							String value = tsMap.get("SC") == null? "": tsMap.get("SC").toString();
							kqMap.put("SC",value);
							break;
						}
						if(list.size() == i){
							kqList.add(tsMap);
						}
						i++;
					}
				}else{
					kqList = tsList;
				}

			}
		}

		Collections.sort(kqList, new Comparator<Map<String,Object>>() {
			public int compare(Map<String,Object> o1, Map<String,Object> o2) {
				return o1.get("BUMEN").toString().compareTo(o2.get("BUMEN").toString());
			}
		});

		return kqList;
	}

	private void setCardNomIntoSql(StringBuilder querySql, JSONObject json, StringBuilder cardSb, int index,List<Object> param) {
		String[] cardNums = json.getString("cardNum").split(",");
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(cardNums));
		if(list.size()>0){
			querySql.append(" and ( ");
			while(list.size()>0){
				cardSb.setLength(0);
				if(index==0){
					querySql.append(" cardNumber in (");
				}else{
					querySql.append(" or cardNumber in (");
				}
				for(int i=0;i<900&&list.size()>0;i++){
					index = 1;
					String tempCardid =list.remove(0);
					cardSb.append(tempCardid+",");
				}
				querySql.append(CommonUtils.solveSqlInjectionOfIn(cardSb.substring(0, cardSb.length()-1),param) + " )");
			}
			querySql.append(" ) ");
		}
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
		StringBuilder sql = new StringBuilder(" select sud.userid,to_char(sud.deptname) deptname,sf1.username,sf2.card_number cardnumber from uias.sys_flow_user sf1 right outer join uias.sys_flow_user_ext sf2 on sf1.userid=sf2.userid and sf1.status='1' left outer join ( ");
		sql.append("  select t.userid,wm_concat(to_char(t1.deptname))deptname,count(*) from uias.sys_user_dprb t left outer join uias.sys_flow_dept t1 on t.deptid=t1.deptid where  t.status='1' group by t.userid ");
		sql.append("   )sud on sf1.userid=sud.userid where sf2.card_number is not null ");
		String[] userids = userId.split(",");
		List<String> userlist = new ArrayList(Arrays.asList(userids));
		useridIntoSql1(userlist,sql);
		JSONObject jsonObj = userInfoService.getDateBySql(sql.toString());
		System.out.println(sql.toString());
		List<Map<String,Object>> data = (List<Map<String,Object>>)jsonObj.get("data");
		for (Map<String, Object> tempMap : data) {
			Object cardNumberObj = tempMap.get("cardnumber");
			if(cardNumberObj!=null && (!"null".equals(cardNumberObj))) {
				String cardNumber = cardNumberObj.toString();
				cardNoms.append(cardNumber+",");
				SysFlowUserVo userVo = new SysFlowUserVo();
				Object username = tempMap.get("username");
				Object deptname = tempMap.get("deptname");
				userVo.setUserNameFull(username.toString());
				userVo.setUserDeptName(deptname.toString());
				userInfo.put(cardNumber, userVo);
			}
		}
	/*	Map<String, SysFlowUserVo> map = myUserUtil.getUserVo(userId);
		//Map<String, SysFlowUserVo> map = UserUtil.getUserVo(userId);
		String[] userids = userId.split(",");
		for (String userid : userids) {
			SysFlowUserVo userVo = map.get(userid);
			if(userVo!=null){
				if(StringUtils.isNotBlank(userVo.getCardNumber())){
					cardNoms.append(userVo.getCardNumber()+",");
					userInfo.put(userVo.getCardNumber(), userVo);
				}
			}
			
			
		}*/
		if(cardNoms.length()>0){
			json.put("cardNum", cardNoms.substring(0,cardNoms.length()-1));
		}else{
			json.put("cardNum", "");
		}
		
		json.put("userInfo", userInfo);
	}
	private void useridIntoSql1(List<String> useridList, StringBuilder sb) {
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
		if(useridList.size()>0){
			sb.append("  and (");
			while(useridList.size()>0){
				useridSb.setLength(0);
				if(index == 0){
					sb.append(" sud.userid in (");
				}else{
					sb.append(" or sud.userid in (");
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
		//判断是否考勤管理员 查看全部数据 add by hlt
		String tempid="";
		if(UserUtil.getCruUserRoleNo().contains("D141")){
			tempid=UserUtil.getCruOrgId();
		}else{
			tempid=UserUtil.getCruChushiId();
			if(!StringUtils.isNotBlank(UserUtil.getCruChushiId())){
				tempid=UserUtil.getCruDeptId();
			}
		}
		//String tempid=UserUtil.getCruChushiId();
		
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
		String[] userids1 = useridt.split(",");
		//long cnt = userids1.length/900+(userids1.length%900>1?1:0);
		
		Map<String, SysFlowUserVo> map = myUserUtil.getUserVo(useridt);
		//Map<String, SysFlowUserVo> map = UserUtil.getUserVo(useridt);
		for (String userid : userIdList) {
			SysFlowUserVo userVo = map.get(userid);
			//userVo.setUserDeptName(deptidAndDeptnameMap.get(useidAndDeptidMap.get(userid)));
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
	
	/**
	 * 根据打卡查询打卡信息的条数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年4月28日 下午3:23:33
	 * @param startDate
	 * @param endDate
	 * @param cardNumber
	 * @param userId
	 * @param isAll
	 * @return
	 */
	@Override
	public long getCnt(String startDate, String endDate, String cardNumber, String userId, String isAll) {
		if(StringUtils.isBlank(userId)) {
			return 0;
		}
		StringBuilder querySql = new StringBuilder(" select count(1) from kqgl_attendence_handle t where 1=1 ");
		StringBuilder cardSb = new StringBuilder();
		int index = 0;
		List<Object> para = new ArrayList<>();
		JSONObject json = new JSONObject();
		if(StringUtils.isNotBlank(userId)){
			getCardNoms(userId,json);
			setCardNomIntoSql1(querySql, json, cardSb, index,para);			
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.import_date >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.import_date <= ?");
			para.add(endDate);
		}
		Long cnt = jdbcTemplate.queryForObject(querySql.toString(), para.toArray(), Long.class);
		
		return cnt;
	}
	
	/**
	 * 导出
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午5:55:42
	 * @param overTimeType
	 * @param timeRange
	 * @param subflag
	 * @param request
	 * @param response
	 */
	@Override
	public List<KqglAttendenceHandle> getExportList(String startDate, String endDate, String cardNumber, String userId,
			String isAll) {
		List<KqglAttendenceHandle> list = new ArrayList<KqglAttendenceHandle>();
		if(StringUtils.isBlank(userId)) {
			return list;
		}
		StringBuilder querySql = new StringBuilder(" select card_number cardNumber,qr_time qrTime,qc_time qcTime,import_date importDate from kqgl_attendence_handle t where 1=1 ");
		StringBuilder cardSb = new StringBuilder();
		int index = 0;
		List<Object> para = new ArrayList<>();
		JSONObject json = new JSONObject();
		if(StringUtils.isNotBlank(userId)){
			getCardNoms(userId,json);
			setCardNomIntoSql1(querySql, json, cardSb, index,para);			
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.import_date >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.import_date <= ?");
			para.add(endDate);
		}
		querySql.append(" order by t.import_date desc,card_number desc ");
		 
		list=jdbcTemplate.query(querySql.toString(), para.toArray(), new RowMapper<KqglAttendenceHandle>() {
			@Override
			public KqglAttendenceHandle mapRow(ResultSet result, int arg1) throws SQLException {
				KqglAttendenceHandle obj = new KqglAttendenceHandle();
				obj.setCardNumber(result.getString("cardNumber"));
				obj.setQcTime(result.getString("qcTime"));
				obj.setQrTime(result.getString("qrTime"));
				obj.setImportDate(result.getString("importDate"));
				return obj;
			}
		});
		for (KqglAttendenceHandle kqglAttendenceHandle : list) {
			Map<String,SysFlowUserVo > userMap = (Map<String,SysFlowUserVo >)json.get("userInfo");
			Object userJson = userMap.get(kqglAttendenceHandle.getCardNumber());
			JSONObject jsonObj = JSONObject.fromObject(userJson);
			kqglAttendenceHandle.setName(jsonObj.getString("userNameFull"));
			kqglAttendenceHandle.setDeptName(jsonObj.getString("userDeptName"));
		}
		return list;
	}


	private void setCardNomIntoSql1(StringBuilder querySql, JSONObject json, StringBuilder cardSb, int index,List<Object> param) {
		String[] cardNums = json.getString("cardNum").split(",");
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(cardNums));
		if(list.size()>0){
			querySql.append(" and ( ");
			while(list.size()>0){
				cardSb.setLength(0);
				if(index==0){
					querySql.append(" card_number in (");
				}else{
					querySql.append(" or card_number in (");
				}
				for(int i=0;i<900&&list.size()>0;i++){
					index = 1;
					String tempCardid =list.remove(0);
					cardSb.append(tempCardid+",");
				}
				querySql.append(CommonUtils.solveSqlInjectionOfIn(cardSb.substring(0, cardSb.length()-1),param) + " )");
			}
			querySql.append(" ) ");
		}
	}
	
}
