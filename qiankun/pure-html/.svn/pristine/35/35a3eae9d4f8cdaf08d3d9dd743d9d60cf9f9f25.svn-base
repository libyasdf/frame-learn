package com.sinosoft.sinoep.uias.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.api.auth.model.FlowRolesInfo;
import com.sinosoft.sinoep.api.auth.model.SysRoleSystVO;
import com.sinosoft.sinoep.api.auth.vo.MessageFlowRoles;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.jedis.services.RedisClusterService;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;
import com.sinosoft.sinoep.user.service.UserInfoService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.api.auth.vo.MessageSysResourceAndExt;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.JSONUtils;
import com.sinosoft.sinoep.uias.constant.UiasConfigConsts;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：获取资源访问权限</B><BR>
 *
 * @author 中科软 康俊祥
 * @since 2016年6月20日
 */
public class RecourseUtils {

    private static Logger log = Logger.getLogger(RecourseUtils.class);

    private static RedisClusterService redisService = (RedisClusterService)SpringBeanUtils.getBean("com_sinosoft_sinoep_common_jedis_services_RedisClusterService");

    /**
     * TODO 获取用户在指定岗位下的业务角色信息
     * @author 李利广
     * @Date 2019年03月14日 20:50:32
     * @param userId
     * @param deptId
     * @return List<FlowRolesInfo> 查询失败或无信息返回空集合
     */
    public static List<FlowRolesInfo> getFlowRoleByDept(String userId,String deptId){
        JSONObject para = new JSONObject();
        List<FlowRolesInfo> rolesInfo = new ArrayList();
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(deptId)){
            Object obj = redisService.get(CommonConstants.FLOW_ROLE+deptId+"_"+userId);
            if(obj != null){
                JSONArray array = JSONArray.fromObject(obj);
                for(int i = 0;i < array.size();i++){
                    rolesInfo.add((FlowRolesInfo)JSONObject.toBean(array.getJSONObject(i),FlowRolesInfo.class));
                }
            }
            if(rolesInfo.size() <= 0){
                para.put("subId",ConfigConsts.SYSTEM_ID);
                para.put("userId",userId);
                para.put("deptId",deptId);
                try{
                    String sysFlowRolesJson = HttpRequestUtil.sendPost(ConfigConsts.UIAS_SERVICE_ROOT_URL
                            + "/resource/getFlowRoles","para=" + para.toString());
                    log.info("flowRolesJson:" + sysFlowRolesJson);
                    if(StringUtils.isNotBlank(sysFlowRolesJson)){
                        MessageFlowRoles flowRoles = JSON.parseObject(sysFlowRolesJson,MessageFlowRoles.class);
                        if (UiasConfigConsts.FLAG_STATUS[1].equals(flowRoles.getStatus())){
                            rolesInfo = flowRoles.getRolesInfo();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    log.error(e.getMessage(),e);
                }
                if(rolesInfo.size() > 0){
                    redisService.set(CommonConstants.FLOW_ROLE+deptId+"_"+userId,JSONArray.fromObject(rolesInfo).toString());
                    //设置redis超时时间（1800秒 = 30分钟）
                    redisService.expire(CommonConstants.FLOW_ROLE+deptId+"_"+userId,CommonConstants.EXPIRE_REDIS);
                }
            }
        }
        return rolesInfo;
    }

    /**
     * TODO 获取用户在指定岗位下的业务角色ID(已逗号分隔)
     * @author 李利广
     * @Date 2019年03月14日 20:52:21
     * @param userId
     * @param deptId
     * @return java.lang.String 无数据则返回空字符串
     */
    public static String getFlowRoleIdByDept(String userId,String deptId){
        String str = "";
        StringBuilder stringBuilder = new StringBuilder();
        List<FlowRolesInfo> rolesInfo = getFlowRoleByDept(userId,deptId);
        if (!rolesInfo.isEmpty()){
            for (FlowRolesInfo role:rolesInfo) {
                stringBuilder.append(role.getRolesId()).append(",");
            }
        }
        if (stringBuilder.length() > 0){
            str = stringBuilder.substring(0,stringBuilder.length()-1);
        }
        return str;
    }

    /**
     * TODO 获取用户在指定岗位下的业务角色编号(已逗号分隔)
     * @author 李利广
     * @Date 2019年03月14日 20:52:21
     * @param userId
     * @param deptId
     * @return java.lang.String 无数据则返回空字符串
     */
    public static String getFlowRoleNoByDept(String userId,String deptId){
        StringBuilder stringBuilder = new StringBuilder();
        String str = "";
        List<FlowRolesInfo> rolesInfo = getFlowRoleByDept(userId,deptId);
        if (!rolesInfo.isEmpty()){
            for (FlowRolesInfo role:rolesInfo) {
                stringBuilder.append(role.getRolesNO()).append(",");
            }
        }
        if (stringBuilder.length() > 0){
            str = stringBuilder.substring(0,stringBuilder.length()-1);
        }
        return str;
    }

    /**
     * TODO 获取用户在指定岗位下的系统角色
     * @author 李利广
     * @Date 2019年03月14日 21:09:25
     * @param userId
     * @param deptId
     * @return List<SysRoleSystVO>
     */
    public static List<SysRoleSystVO> getSysRoleByDept(String userId,String deptId){
        JSONObject para = new JSONObject();
        List<SysRoleSystVO> roleList = new ArrayList<>();
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(deptId)){
            Object obj = redisService.get(CommonConstants.SYS_ROLE+deptId+"_"+userId);
            if(obj != null){
                JSONArray array = JSONArray.fromObject(obj);
                for(int i = 0;i < array.size();i++){
                    roleList.add((SysRoleSystVO)JSONObject.toBean(array.getJSONObject(i),SysRoleSystVO.class));
                }
            }
            if(roleList.size() <= 0){
                para.put("subId",ConfigConsts.SYSTEM_ID);
                para.put("userId",userId);
                para.put("deptId",deptId);
                try{
                    String sysFlowRolesJson = HttpRequestUtil.sendPost(ConfigConsts.UIAS_SERVICE_ROOT_URL
                            + "/resource/getFlowRlsy","para=" + para.toString());
                    log.info("sysRolesJson:" + sysFlowRolesJson);
                    if(StringUtils.isNotBlank(sysFlowRolesJson)){
                        JSONObject json = JSONUtils.getJsonObject(sysFlowRolesJson);
                        if (UiasConfigConsts.FLAG_STATUS[1].equals(json.getString(UiasConfigConsts.STATUS))){
                            String array = json.getString("SysRoleSystvo");
                            roleList = JSONUtils.getList(array,SysRoleSystVO.class);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    log.error(e.getMessage(),e);
                }
                if(roleList.size() > 0){
                    redisService.set(CommonConstants.SYS_ROLE+deptId+"_"+userId,JSONArray.fromObject(roleList).toString());
                    //设置redis超时时间（1800秒 = 30分钟）
                    redisService.expire(CommonConstants.SYS_ROLE+deptId+"_"+userId,CommonConstants.EXPIRE_REDIS);
                }
            }
        }
        return roleList;
    }

    /**
     * TODO 获取用户在指定岗位下的系统角色ID串（以逗号分隔）
     * @author 李利广
     * @Date 2019年03月14日 21:19:16
     * @param userId
     * @param deptId
     * @return java.lang.String 无信息则返回空串
     */
    public static String getSysRoleIdByDept(String userId,String deptId){
        StringBuilder stringBuilder = new StringBuilder();
        String str = "";
        List<SysRoleSystVO> roleList = getSysRoleByDept(userId,deptId);
        if (!roleList.isEmpty()){
            for (SysRoleSystVO vo:roleList) {
                stringBuilder.append(vo.getRlsyId()).append(",");
            }
        }
        if (stringBuilder.length() > 0){
            str = stringBuilder.substring(0,stringBuilder.length()-1);
        }
        return str;
    }

    /**
     * TODO 获取用户在指定岗位下的系统角色编号串（以逗号分隔）
     * @author 李利广
     * @Date 2019年03月14日 21:19:16
     * @param userId
     * @param deptId
     * @return java.lang.String 无信息则返回空串
     */
    public static String getSysRoleNosByDept(String userId,String deptId){
        String str = "";
        StringBuilder stringBuilder = new StringBuilder();
        List<SysRoleSystVO> roleList = getSysRoleByDept(userId,deptId);
        if (!roleList.isEmpty()){
            for (SysRoleSystVO vo:roleList) {
                stringBuilder.append(vo.getRlsyno()).append(",");
            }
        }
        if (stringBuilder.length() > 0){
            str = stringBuilder.substring(0,stringBuilder.length()-1);
        }
        return str;
    }

    /**
     * <B>方法名称：根据资源ID获取资源信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月12日 下午3:17:25
     * @param rid 资源ID
     * @return MessageSysResourceAndExt 资源实体对象
     */
    public static MessageSysResourceAndExt getResourceInfo(String rid) {
        String resJson = HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/resource/findByRid",
                "rid=" + rid);
        return JSON.parseObject(resJson, MessageSysResourceAndExt.class);
    }

    /**
     * TODO 判断用户在指定岗位下是否有指定URL资源的权限
     * @author 李利广
     * @Date 2019年03月15日 16:18:38
     * @param url
     * @return java.lang.Boolean
     */
    public static Boolean hasPrivilegeByUrl(HttpServletRequest request,String url){
        log.info("开始校验资源权限");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(UserConfigConsts.USER_ID);
        String deptId = (String) session.getAttribute(UserConfigConsts.USER_DEPT_ID);
        log.info("userId==" + userId);
        log.info("deptId==" + deptId);
        log.info("url==" + url);
        Boolean hasAuth = false;
        JSONObject para = new JSONObject();
        String par = "";
        para.put("userId",userId);
        para.put("deptId",deptId);
        para.put("Url",url);
        para.put("subId",ConfigConsts.SYSTEM_ID);
        try{
            par = URLEncoder.encode(para.toString(),"utf-8");
        }catch (Exception e){}
        String flagJson = HttpRequestUtil.sendPostNoEncode(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/resource/getAuthByUrl",
                "para=" + par);
        JSONObject objJson = JSONObject.fromObject(flagJson);
        String authority = objJson.getString("authority");
        //有数据
        if (UiasConfigConsts.FLAG_STATUS[1].equals(objJson.getString(UiasConfigConsts.STATUS))) {
            // 有权限
            if (UiasConfigConsts.HAS_AUTH[1].equals(authority)) {
                hasAuth = true;
            }else if (UiasConfigConsts.HAS_AUTH[0].equals(authority)) {
                // 无权限
                hasAuth = false;
            }else if (UiasConfigConsts.HAS_AUTH[2].equals(authority)) {
                // 无资源
                hasAuth = true;
            }
        }else if (UiasConfigConsts.FLAG_STATUS[0].equals(objJson.getString(UiasConfigConsts.STATUS))) {
            //无数据
            hasAuth = false;
        }
        log.info("资源权限校验结束！是否有权限：" + hasAuth);
        return hasAuth;
    }

    /**
     * TODO 判断用户在指定岗位下是否有指定资源元素的权限
     * @param content
     * @param resId
     * @return
     */
    public static Boolean hasPrivilegeByContent(HttpServletRequest request, String content, String resId){
        log.info("开始校验资源元素权限");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(UserConfigConsts.USER_ID);
        String deptId = (String) session.getAttribute(UserConfigConsts.USER_DEPT_ID);
        log.info("userId==" + userId);
        log.info("deptId==" + deptId);
        log.info("content==" + content);
        log.info("resId==" + resId);
        Boolean hasAuth = false;
        JSONObject para = new JSONObject();
        para.put("userId",userId);
        para.put("deptId",deptId);
        para.put("elementContent",content);
        para.put("subId",ConfigConsts.SYSTEM_ID);
        para.put("resourceId",resId);
        String flagJson = HttpRequestUtil.sendPost(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/resource/getAuthorityByElement",
                "para=" + para.toString());
        JSONObject objJson = JSONObject.fromObject(flagJson);
        String authority = objJson.getString("authority");
        //有数据
        if (UiasConfigConsts.FLAG_STATUS[1].equals(objJson.getString(UiasConfigConsts.STATUS))) {
            // 无权限
            if (UiasConfigConsts.HAS_AUTH[0].equals(authority)) {
                hasAuth = false;
            }else if (UiasConfigConsts.HAS_AUTH[1].equals(authority)) {
                // 有权限
                hasAuth = true;
            }else if (UiasConfigConsts.HAS_AUTH[2].equals(authority)) {
                // 无资源
                hasAuth = true;
            }
        }else if (UiasConfigConsts.FLAG_STATUS[0].equals(objJson.getString(UiasConfigConsts.STATUS))) {
            //无数据
            hasAuth = false;
        }
        log.info("资源元素权限校验结束！是否有权限：" + hasAuth);
        return hasAuth;
    }

    /**
     * TODO 根据资源id查询模块数据
     * @param ids json格式：{"id":"123,123,132"}
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getResourceListByIds(String ids){
        List<Map<String,Object>> list = new ArrayList<>();
        if (StringUtils.isNotBlank(ids)){
            UserInfoService userInfoService = (UserInfoService) SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
            JSONObject json = JSONObject.fromObject(ids);
            String idStr = json.getString("id");
            String[] resIds = idStr.split(",");
            for(int i = 0;i<resIds.length;i++){
                Map<String,Object> map = new HashMap<>();
                String resSql = "select tree_id from sys_resource where id = '" + resIds[i] + "' and sub_id = '"+ConfigConsts.SYSTEM_ID+"' and status = '1'";
                JSONObject jsonObject = userInfoService.getDateBySql(resSql);
                List<Map<String,Object>> resourceMap = jsonObject.getJSONArray("data");
                //如果资源存在，则查询其父资源信息
                if (resourceMap.size() > 0){
                    Map<String,Object> reMap = resourceMap.get(0);
                    String treeId = ((String) reMap.get("tree_id")).substring(0,4);
                    String sql = "select ID,RESOURCE_NAME,FLAG,CONTENT from sys_resource" +
                            " where tree_id like '" + treeId + "%'" +
                            " and sub_id = '"+ConfigConsts.SYSTEM_ID+"' and status = '1'" +
                            " start with id = '" + resIds[i] + "'" +
                            " connect by prior super_id = id" +
                            " order by tree_id";
                    List<Map<String,Object>> resourceList = userInfoService.getDateBySql(sql).getJSONArray("data");
                    if(resourceList.size() > 0){
                        String parent = "";
                        if(resourceList.size()>1){
                            for(int j = 0;j<resourceList.size()-1;j++){
                                parent += resourceList.get(j).get("resource_name") +  "/";
                            }
                        }else{
                            parent = resourceList.get(0).get("resource_name") + "/";
                        }
                        Map<String,Object> lastResource = resourceList.get(resourceList.size()-1);
                        Map<String,Object> firstResource = resourceList.get(0);
                        map.put("flag","1");
                        map.put("id",resIds[i]);
                        map.put("name",lastResource.get("resource_name"));
                        map.put("type","null".equals(firstResource.get("flag").toString())?"":firstResource.get("flag"));
                        map.put("url",lastResource.get("content"));
                        map.put("parent",parent.substring(0,parent.length()-1));
                    }else{
                        map.put("id",resIds[i]);
                        map.put("flag","-1");
                        map.put("msg","获取数据失败");
                    }
                    list.add(map);
                }else{
                    //如果资源不存在
                    map.put("id",resIds[i]);
                    map.put("flag","0");
                    map.put("msg","获取数据失败");
                    list.add(map);
                }
            }
        }
        return list;
    }

}
