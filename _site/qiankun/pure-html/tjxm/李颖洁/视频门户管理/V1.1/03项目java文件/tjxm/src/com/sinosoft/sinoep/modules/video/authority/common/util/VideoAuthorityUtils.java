package com.sinosoft.sinoep.modules.video.authority.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.info.authority.common.InfoFbConstants;
import com.sinosoft.sinoep.modules.info.authority.entity.ColumnModel;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoModel;
import com.sinosoft.sinoep.modules.info.authority.services.InfoFbZwqxService;
import com.sinosoft.sinoep.modules.video.authority.common.ContentFbConstants;
import com.sinosoft.sinoep.modules.video.authority.entity.VideoModel;
import com.sinosoft.sinoep.modules.video.authority.services.ContentFbZwqxService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class VideoAuthorityUtils {
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
		sBuilder.append("select t.content_id from video_fb_dept t where t.dept_id = '"+UserUtil.getCruDeptId()+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
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
				sBuilder.append("select t.content_id from video_fb_group t where t.group_id in ("+sbStr+") and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
				sBuilder.append(" union ");
			}
		}
		sBuilder.append("select t.content_id from video_fb_zwqx t where t.position_id = '"+userVo.get(UserUtil.getCruUserId()).getPositionName()+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		sBuilder.append(" union ");
		sBuilder.append("select t.content_id from video_fb_dept_zwqx t where (t.dept_id = '"+UserUtil.getCruDeptId()+"' or t.dept_id = '"+UserUtil.getCruChushiId()+"' or t.dept_id = '"+UserUtil.getCruJuId()+"' or t.dept_id = '"+UserUtil.getCruOrgId()+"') and t.position_id = '"+userVo.get(UserUtil.getCruUserId()).getPositionName()+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
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
		sBuilder.append("select t.content_id contentId,t.dept_id deptId from video_fb_dept t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<Map<String, Object>> deptList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("deptAuth", deptList);
		sBuilder.setLength(0);
		sBuilder.append("select t.content_id contentId,t.position_id positionId from video_fb_zwqx t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<Map<String, Object>> positionList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("positionAuth", positionList);
		sBuilder.setLength(0);
		sBuilder.append("select t.content_id contentId,t.dept_id deptId,t.position_id positionId from video_fb_dept_zwqx t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<Map<String, Object>> deptPositionList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("deptPositionAuth", deptPositionList);
		sBuilder.setLength(0);
		sBuilder.append("select t.content_id contentId,t.group_id groupId from video_fb_group t where t.content_id = '"+contentId+"' and t.visible = '"+InfoFbConstants.VISIBLE[1]+"'");
		List<Map<String, Object>> groupList = jdbcTemplate.queryForList(sBuilder.toString());
		authMap.put("groupAuth",groupList);
		return authMap;
	}

}
