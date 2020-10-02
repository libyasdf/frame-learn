package com.sinosoft.sinoep.modules.system.notice.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.modules.system.notice.entity.InfoFBUserGroup;
import com.sinosoft.sinoep.modules.system.notice.entity.InfoNoticeVo;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import com.sinosoft.sinoep.user.service.UserInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NoticeUtils {
	
	private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");
	
	/**
	 * 生成排序号
	 * @param notice
	 * @return
	 */
	public static Integer getSort(SysNotice notice){
		StringBuilder sb = new StringBuilder();
		sb.append("select max(t.order_no+0) from sys_notice t where t.notice_type = '"+notice.getNoticeType()+"'");
		Integer sort = jdbcTemplate.queryForObject(sb.toString(),Integer.class);
		return (sort == null) ? 0 : (sort+1);
	}

	/**
	 * 根据权限范围，查询所有接收人userId
	 * TODO
	 * @author 李利广
	 * @Date 2018年10月22日 下午2:09:27
	 * @param authMap
	 * @return
	 */
	public static JSONArray getUser(Map<String, List<Map<String, Object>>> authMap) {
		return getUser(authMap,UserUtil.getCruOrgId());
	}

	/**
	 * 根据权限范围，查询所有接收人userId
	 * TODO 
	 * @author 李利广
	 * @Date 2018年10月22日 下午2:09:27
	 * @param authMap
	 * @return
	 */
	public static JSONArray getUser(Map<String, List<Map<String, Object>>> authMap,String orgId) {
		JSONArray jsonArray = new JSONArray();
//		Set<String> userSet = new HashSet<>();	//使用hashset自动去除重复的userid
		if(!authMap.isEmpty()){
			UserInfoService userInfoService = (UserInfoService)SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
			StringBuilder sBuilder = new StringBuilder();
			StringBuilder sBuilder2 = new StringBuilder();
			//根据全局普发获取人员
			List<Map<String, Object>> genaralsAuth = authMap.get("genaralsAuth");
			if(!genaralsAuth.isEmpty()){
				String sysOrgId ="";
				for (Map<String, Object> map : genaralsAuth) {
					sysOrgId = (String)map.get("sysOrgId");
				}
				sBuilder.append("select s.userid,s.deptid,s.usdr_name username");
				sBuilder.append("  from sys_user_dprb s");
				sBuilder.append(" where s.status = '1'");
				sBuilder.append("   and s.deptid in (select t.deptid");
				sBuilder.append("                      from sys_flow_dept t");
				sBuilder.append("                     where t.status = '1'");
				sBuilder.append("                     start with t.deptid = '"+sysOrgId.trim()+"'");
				sBuilder.append("                    connect by prior deptid = super_id)");
			}
			//根据部门查询人员
			List<Map<String, Object>> deptAuth = authMap.get("deptAuth");
			if (!deptAuth.isEmpty()) {
			    StringBuilder deptIds = new StringBuilder();
				for (Map<String, Object> map : deptAuth) {
					String deptId = (String)map.get("DEPTID");
					deptIds.append("'" + deptId +"',");
				}
                String deptStr = deptIds.substring(0,deptIds.length()-1);
				sBuilder.append("select s.userid,s.deptid,s.usdr_name username");
				sBuilder.append("  from sys_user_dprb s");
				sBuilder.append(" where s.status = '1'");
				sBuilder.append("   and s.deptid in (select t.deptid");
				sBuilder.append("                      from sys_flow_dept t");
				sBuilder.append("                     where t.status = '1'");
				sBuilder.append("                     start with t.deptid in ("+deptStr+")");
				sBuilder.append("                    connect by prior deptid = super_id)");
			}

			//根据群组查询人员
			List<Map<String, Object>> groupAuth = authMap.get("groupAuth");
			if (!groupAuth.isEmpty()) {
                StringBuilder groupIds = new StringBuilder();
				for (Map<String, Object> map : groupAuth) {
					String groupId = (String)map.get("GROUPID");
					groupIds.append("'" + groupId + "',");
				}
                if (sBuilder.length() > 0){
                    sBuilder.append(" union ");
                }
                String groupStr = groupIds.substring(0,groupIds.length()-1);
                sBuilder.append("select t.userid,t.deptid,u.username from sys_user_grup t,sys_flow_user u where t.grup_id in ("+groupStr+") and t.status = '1' and u.status = '1' and u.userid = t.userid");
			}

			//根据人群查询人员
			List<Map<String, Object>> userGroupAuth = authMap.get("userGroupAuth");
			List<InfoFBUserGroup> sysUserGroupList = new ArrayList<InfoFBUserGroup>();
			if (!userGroupAuth.isEmpty()) {
				StringBuilder groupIds = new StringBuilder();
				for (Map<String, Object> map : userGroupAuth) {
					InfoFBUserGroup sysFBUser = new InfoFBUserGroup();
					String groupId = (String)map.get("sysUserId");
					String sysUserName = (String)map.get("sysUserName");
					String sysDeptId = (String)map.get("sysDeptId");
					Map<String, SysFlowUserVo> getUserVo = UserUtil.getUserVo(groupId);
					SysFlowUserVo sysFlowUserVo =getUserVo.get(groupId);
					String creChushiId = sysFlowUserVo.getUserChushiId();
					//处室id
					String[] chushiIdStr = creChushiId.split(",");
					//部门id
					String deptIds = sysFlowUserVo.getUserDeptId();
					//二级局id
					String juIds = sysFlowUserVo.getUserJuId();

					String[] deptIdStr = deptIds.split(",");
					//判断部门id是否在deptIdStr中
					String deptIdss = "";
					if(sysDeptId !=null){
						if(deptIds.indexOf(sysDeptId)!=-1){
							for(int i=0;i<deptIdStr.length;i++){
								if(deptIdss.equals("")){
									if(sysDeptId.equals(deptIdStr[i])){
										deptIdss = deptIdStr[i];
										if(i<chushiIdStr.length){
											//临时存入处室id
											sysFBUser.setCreUserName(chushiIdStr[i]);
											sysFBUser.setSysUserId(groupId);
											sysFBUser.setSysUserName(sysUserName);
											sysUserGroupList.add(sysFBUser);
										}else{
											//临时存入处室id
											sysFBUser.setCreUserName(chushiIdStr[0]);
											sysFBUser.setSysUserId(groupId);
											sysFBUser.setSysUserName(sysUserName);
											sysUserGroupList.add(sysFBUser);
										}
									}
								}
							}
						}
						//判断处室id是否在cushiIdStr中
						if(creChushiId.indexOf(sysDeptId) !=-1){
							//临时存入处室id
							sysFBUser.setCreUserName(sysDeptId);
							sysFBUser.setSysUserId(groupId);
							sysFBUser.setSysUserName(sysUserName);
							sysUserGroupList.add(sysFBUser);
						}
						//判断二级局id是否在juIds中
						if(juIds.indexOf(sysDeptId)!=-1){
							//临时存入处室id
							sysFBUser.setCreUserName(sysDeptId);
							sysFBUser.setSysUserId(groupId);
							sysFBUser.setSysUserName(sysUserName);
							sysUserGroupList.add(sysFBUser);
						}

					}else{
						sysFBUser.setCreUserName(chushiIdStr[0]);
						sysFBUser.setSysUserId(groupId);
						sysFBUser.setSysUserName(sysUserName);
						sysUserGroupList.add(sysFBUser);
					}
				}
			}
			
			//根据职务权限查询人员
			List<Map<String, Object>> positionList = authMap.get("positionAuth");
			if (!positionList.isEmpty()) {
                StringBuilder positionIds = new StringBuilder();
				for (Map<String, Object> map : positionList) {
					String positionId = (String)map.get("POSITIONID");
					positionIds.append("'" + positionId + "',");
				}
                if (sBuilder.length() > 0){
                    sBuilder.append(" union ");
                }
                String positionStr = positionIds.substring(0,positionIds.length()-1);
				sBuilder.append("select t.userid, s.deptid,t.username");
				sBuilder.append("  from sys_flow_user t, sys_user_dprb s");
				sBuilder.append(" where t.position_name in ("+positionStr+")");
				sBuilder.append("   and t.status = '1'");
				sBuilder.append("   and s.status = '1'");
				sBuilder.append("   and t.userid = s.userid");
//                sBuilder.append("select t.userid from sys_flow_user t where t.position_name in ("+positionStr+") and t.status = '1'");
            }
			
			//根据部门+职务权限查询人员
			List<Map<String, Object>> deptPositionList = authMap.get("deptPositionAuth");
			if (!deptPositionList.isEmpty()) {
                StringBuilder deptPosIds = new StringBuilder();
				for (Map<String, Object> map : deptPositionList) {
					String deptId = (String)map.get("DEPTID");
					String positionId = (String)map.get("POSITIONID");
					deptPosIds.append("(t.position_name = '"+positionId+"' and");
					deptPosIds.append("       s.deptid in (select t.deptid");
					deptPosIds.append("                        from sys_flow_dept t");
					deptPosIds.append("                       where t.status = '1'");
					deptPosIds.append("                       start with t.deptid = '"+deptId+"'");
					deptPosIds.append("                      connect by prior deptid = super_id)) or");
				}
                String deptPosStr = deptPosIds.substring(0, deptPosIds.length()-2);
                if (sBuilder.length() > 0){
                    sBuilder.append(" union ");
                }
                sBuilder.append("select t.userid,s.deptid,t.username from sys_flow_user t,sys_user_dprb s where t.status = '1' and s.status = '1' and t.userid = s.userid ");
                sBuilder.append(" and (" + deptPosStr + ")");
            }

            if (sBuilder.length() > 0){
				StringBuilder sb = new StringBuilder("select ss.* from (");
				sb.append(sBuilder);
				sb.append(") ss, sys_flow_dept dd where dd.peak_deptid = '"+orgId+"' and dd.deptid = ss.deptid");
                JSONObject deptUser = userInfoService.getDateBySql(sb.toString());
                if ("1".equals(deptUser.get("flag"))) {
                    jsonArray = deptUser.getJSONArray("data");
                    /*if (!jsonArray.isEmpty()) {
                        for (int i=0;i<jsonArray.size();i++) {
                            String userId = jsonArray.getJSONObject(i).getString("userid");
                            userSet.add(userId);
                        }
                    }*/
                }
            }
            //人群查询
			if(sysUserGroupList.size()>0){
				for(int i=0;i<sysUserGroupList.size();i++){
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("userid",sysUserGroupList.get(i).getSysUserId());
					jsonObject.put("deptid",sysUserGroupList.get(i).getCreUserName());
					jsonObject.put("username",sysUserGroupList.get(i).getSysUserName());
					jsonArray.add(jsonObject);
				}
			}
		}
		return jsonArray;
	}


}
