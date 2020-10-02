package com.sinosoft.sinoep.modules.info.authority.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.info.authority.common.InfoFbConstants;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

public class InfoAuthorityUtils {
	
	private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");
	
	/**
	 * 获取当前用户权限内的数据ID
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月18日 下午2:57:02
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAuthList() throws Exception{
		Map<String, SysFlowUserVo> userVo = UserUtil.getUserVo(UserUtil.getCruUserId());
		StringBuilder sBuilder = new StringBuilder("");
		sBuilder.append("select t.content_id from info_fb_dept t where t.dept_id = '"+UserUtil.getCruDeptId()+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		sBuilder.append(" union ");
		JSONArray res = UserUtil.getUserGroup(UserUtil.getCruUserId());
		StringBuilder sb = new StringBuilder();
		String sbStr = "";
		if (!res.isEmpty()){
			for (int i=0;i<res.size();i++) {
				JSONObject group = res.getJSONObject(i);
				sb.append("'"+ group.getString("grup_id") + "',");
			}
			if (sb.length() > 0){
				sbStr = sb.substring(0,sb.length()-1);
				sBuilder.append("select t.content_id from info_fb_group t where t.group_id in ("+sbStr+") and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
				sBuilder.append(" union ");
			}
		}
		sBuilder.append("select t.content_id from INFO_FB_USERGROUP t where t.SYS_USER_ID ='"+UserUtil.getCruUserId()+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		sBuilder.append(" union ");
		sBuilder.append("select t.content_id from INFO_GENARALS t where t.SYS_ORG_ID ='"+UserUtil.getCruOrgId()+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"' and t.GENARALS = 1");
		sBuilder.append(" union ");
		sBuilder.append("select t.content_id from info_fb_zwqx t where (t.dept_id = '"+UserUtil.getCruDeptId()+"' or t.dept_id = '"+UserUtil.getCruChushiId()+"' or t.dept_id = '"+UserUtil.getCruJuId()+"' or t.dept_id = '"+UserUtil.getCruOrgId()+"') and t.position_id = '"+userVo.get(UserUtil.getCruUserId()).getPositionName()+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		sBuilder.append(" union ");
		sBuilder.append("select t.content_id from info_fb_dept_zwqx t where (t.dept_id = '"+UserUtil.getCruDeptId()+"' or t.dept_id = '"+UserUtil.getCruChushiId()+"' or t.dept_id = '"+UserUtil.getCruJuId()+"' or t.dept_id = '"+UserUtil.getCruOrgId()+"') and t.position_id = '"+userVo.get(UserUtil.getCruUserId()).getPositionName()+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<String> authList = jdbcTemplate.queryForList(sBuilder.toString(), String.class);
		return authList;
	}
	
	/**
	 * 根据信息ID查询信息的权限范围
	 * TODO 
	 * @author 李利广
	 * @Date 2018年10月22日 上午10:42:26
	 * @param contentId
	 * @return
	 */
	public static Map<String,List<Map<String, Object>>> getAuthListByContentId(String contentId){
		Map<String,List<Map<String, Object>>> authMap = new HashMap<>();
		StringBuilder sBuilder = new StringBuilder("");
		sBuilder.append("select t.content_id contentId,t.dept_id deptId from info_fb_dept t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<Map<String, Object>> deptList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("deptAuth", deptList);
		sBuilder.setLength(0);
		sBuilder.append("select t.content_id contentId,t.dept_id deptId,t.position_id positionId from info_fb_zwqx t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<Map<String, Object>> positionList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("positionAuth", positionList);
		sBuilder.setLength(0);
		sBuilder.append("select t.content_id contentId,t.dept_id deptId,t.position_id positionId from info_fb_dept_zwqx t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<Map<String, Object>> deptPositionList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("deptPositionAuth", deptPositionList);
		sBuilder.setLength(0);
		sBuilder.append("select t.content_id contentId,t.group_id groupId from info_fb_group t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<Map<String, Object>> groupList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("groupAuth",groupList);
		sBuilder.setLength(0);
		//通知人群
		sBuilder.append("select t.SYS_USER_NAME sysUserName,t.SYS_USER_ID sysUserId,t.SYS_DEPT_ID sysDeptId from INFO_FB_USERGROUP t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<Map<String, Object>> userGroupList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("userGroupAuth",userGroupList);
		sBuilder.setLength(0);
		//全局普发
		sBuilder.append("select t.content_id contentId,t.SYS_ORG_ID sysOrgId,t.GENARALS genarals from INFO_GENARALS t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"' and t.genarals =1");
		List<Map<String, Object>> generalsList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("genaralsAuth",generalsList);
		return authMap;
	}

}
