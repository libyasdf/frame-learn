package com.sinosoft.sinoep.modules.zhbg.kqgl.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONObject;

@Component
public class MyUserUtil {
	
	@Autowired
	UserInfoService userInfoService;
	/**
	 * 根据用户id查询用户信息
	 * @param userid
	 * @return
	 */
	public Map<String, SysFlowUserVo> getUserVo(String userid){
		List<Map<String, Object>> data = getUserListByUserId(userid);
		Map<String, SysFlowUserVo> map = ChangeToMap(data);
		return map;
		
	}

	private Map<String, SysFlowUserVo> ChangeToMap(List<Map<String, Object>> data) {
		Map<String, SysFlowUserVo> map = new HashMap<String, SysFlowUserVo>();
		for (Map<String, Object> tempmap : data) {
			SysFlowUserVo user = new SysFlowUserVo();
			String userid = tempmap.get("userid").toString();
			user.setUserId(userid);
			user.setUserNameFull(tempmap.get("usernamefull").toString());
			user.setUserDeptName(tempmap.get("deptname").toString());
			String cardNum = tempmap.get("cardnumber").toString();
			if(!"null".equals(cardNum)){
				user.setCardNumber(cardNum);
			}
			
			map.put(userid, user);
		}
		return map;
	}

	private List<Map<String, Object>> getUserListByUserId(String userid) {
		String[] userids1 = userid.split(",");
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(userids1));
		//List<String> list = Arrays.asList(userids1);
		//int cnt = userids1.length/900+(userids1.length%900>1?1:0);
		StringBuilder sql = new StringBuilder();
		sql.append(" select t.*,t2.CARD_NUMBER cardnumber from( ");
		sql.append(" select userid,usernamefull,(select to_char(wm_concat(sfd.deptname)) from sys_user_dprb sud,sys_flow_dept sfd where sud.userid = sfu.userid and sud.deptid=sfd.deptid and sud.status='1')deptname ");
		sql.append("  from sys_flow_user sfu   where status!='0' ");
		StringBuilder useridSb = new StringBuilder();
		int index = 0;
		if(list.size()>0){
			sql.append(" and  (");
			while(list.size()>0){
				useridSb.setLength(0);
				if(index == 0){
					sql.append(" userid in (");
				}else{
					sql.append(" or userid in (");
				}
				for(int i=0;i<900&&list.size()>0;i++){
					index=1;
					String tempUserid = list.remove(0);
					useridSb.append( tempUserid + ",");
				}
				sql.append(CommonUtils.commomSplit(useridSb.substring(0, useridSb.length()-1)) + " )");
			}
			sql.append(" )");
		}
	
		sql.append(" ) t left outer join sys_flow_user_ext t2 on t.userid=t2.userid    where t.deptname is not null ");
		
		JSONObject jsonObj = userInfoService.getDateBySql(sql.toString());
		List<Map<String,Object>> data = (List<Map<String,Object>>)jsonObj.get("data");
		return data;
	}
	
	/**
	 * TODO 判断字符串是否是日期格式
	 * @author 李颖洁  
	 * @date 2019年4月25日下午9:03:37
	 * @param value 字符串
	 * @param dateFormat 日期格式
	 * @return boolean
	 */
	public static boolean isDateString (String value,String dateFormat){
		if(StringUtils.isBlank(value)){
			return false;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date date = sdf.parse(value);
			if(value.equals(sdf.format(date))){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
