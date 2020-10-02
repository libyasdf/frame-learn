package com.sinosoft.sinoep.uias.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.api.auth.service.AuthApiService;
import com.sinosoft.sinoep.api.auth.vo.MessageAuthorization;
import com.sinosoft.sinoep.api.auth.vo.MessageSysResourceAndExt;
import com.sinosoft.sinoep.api.auth.vo.SysResourceInfo;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.uias.constant.UiasConfigConsts;
import com.sinosoft.sinoep.uias.model.SysRecourse;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软 康俊祥
 * @since 2016年6月20日
 */
@Service("recourseDubboService")
public class RecourseDubboServiceImpl implements RecourseService {

    private static Log log = LogFactory.getLog(RecourseDubboServiceImpl.class);

    /**
     * TODO 根据资源ID，获取用户在指定岗位下的拥有的子级资源
     * @author 李利广
     * @Date 2019年03月19日 13:31:35
     * @param uid
     * @param pid
     * @param deptId
     * @return java.util.List<com.sinosoft.sinoep.uias.model.SysRecourse>
     */
    @Override
    public List<SysRecourse> getRecoursesByPid(String uid, String pid,String deptId) {
        List<SysRecourse> sysRecourseList = new ArrayList<>();
        JSONObject para = new JSONObject();
        if (StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(deptId) && StringUtils.isNotBlank(pid)){
            para.put("userId",uid);
            para.put("subId",ConfigConsts.SYSTEM_ID);
            para.put("pid",pid);
            para.put("deptId",deptId);
            AuthApiService authApiService = (AuthApiService) SpringBeanUtils.getBean("authApiService");
            try {
                MessageAuthorization messageAuthorization = authApiService.getRByPid(para.toString());
                if (ConfigConsts.SUCESS_STARUS.equals(messageAuthorization.getStatus())) {
                    List<SysResourceInfo> sysResourceInfoList = messageAuthorization.getResourceInfo();
                    for (SysResourceInfo sysResourceInfo : sysResourceInfoList) {
                        sysRecourseList.add(getSysRecourse(sysResourceInfo));
                    }
                }
            }catch (Exception e) {
                log.info("通过pid获取子级资源信息异常：" + e);
            }
        }
        return sysRecourseList;
    }

    /**
     * 获取所有资源信息,任意层级
     *
     * @param sysResourceInfoList 接口返回的无顺序资源数据
     * @return
     */
    private List<SysRecourse> getChildrenAll(List<SysRecourse> sysResourceInfoList) {
        //获取所有资源MAP，key 为资源ID
        Map<String, SysRecourse> allMap = setList2Map(sysResourceInfoList);
        //获取所有父项资源Map,key为父项ID
        Map<String, SysRecourse> allPraMap = setList2PraMap(sysResourceInfoList, allMap);
        //获取顶级资源信息
        List<SysRecourse> topSysRecourseList = allPraMap.get("0").getChildrenList();
        for (SysRecourse topRec : topSysRecourseList) {
            if (allPraMap.containsKey(topRec.getId())) {
                topRec.setChildrenList(allPraMap.get(topRec.getId()).getChildrenList());
                settingChildren(topRec.getChildrenList(), allPraMap);
            }
        }
        return topSysRecourseList;
    }

    /**
     * 递归设置各层级子资源
     * 
     * @param childrenList
     * @param allPraMap
     */
    private void settingChildren(List<SysRecourse> childrenList, Map<String, SysRecourse> allPraMap) {
        if (childrenList != null && childrenList.size() > 0) {
            for (SysRecourse sysRecourse : childrenList) {
                SysRecourse pSysRecourse = allPraMap.get(sysRecourse.getId());
                if (pSysRecourse == null) {
                    continue;
                }
                List<SysRecourse> tempChildrenList = pSysRecourse.getChildrenList();
                sysRecourse.setChildrenList(tempChildrenList);
                settingChildren(tempChildrenList, allPraMap);
            }
        }

    }

    /**
     * 将list转换为MAP 方便资源信息获取
     * 
     * @param sysResourceInfoList
     * @return
     */
    private Map<String, SysRecourse> setList2Map(List<SysRecourse> sysResourceInfoList) {
        Map<String, SysRecourse> resultMap = new LinkedHashMap<>();
        if (sysResourceInfoList != null && !sysResourceInfoList.isEmpty()) {
			for (SysRecourse rec : sysResourceInfoList) {
				resultMap.put(rec.getId(), rec);
			} 
		}
		return resultMap;
    }

    /**
     * 将父项资源进行整合
     * 
     * @param sysResourceInfoList
     * @param allMap
     * @return
     */
    private Map<String, SysRecourse> setList2PraMap(List<SysRecourse> sysResourceInfoList,
            Map<String, SysRecourse> allMap) {
        Map<String, SysRecourse> resultPraMap = new LinkedHashMap<>();
        for (SysRecourse rec : sysResourceInfoList) {
            rec = settingSysRecourse(rec);
            if (resultPraMap.containsKey(rec.getPid())) {
                resultPraMap.get(rec.getPid()).getChildrenList().add(rec);
            }else {
                if ("0".equals(rec.getPid())) {
                    SysRecourse temp = new SysRecourse();
                    temp.setId("0");
                    temp.setPid("");
                    temp.setName("top");
                    temp.getChildrenList().add(rec);
                    resultPraMap.put("0", temp);
                }else {
                    SysRecourse pSysRecourse = allMap.get(rec.getPid());
                    SysRecourse temp = settingSysRecourse(pSysRecourse);
                    temp.getChildrenList().add(rec);
                    resultPraMap.put(rec.getPid(), temp);
                }
            }
        }
        return resultPraMap;
    }

    /**
     * 重新实例化对象，防止逻辑混乱
     * 
     * @param sysRecourse
     * @return
     */
    private SysRecourse settingSysRecourse(SysRecourse sysRecourse) {
        SysRecourse recourse = new SysRecourse();
        if (sysRecourse != null) {
			recourse.setId(sysRecourse.getId());
			recourse.setPid(sysRecourse.getPid());
			recourse.setName(sysRecourse.getName());
			recourse.setUrl(sysRecourse.getUrl());
			recourse.setStyle(sysRecourse.getStyle());
			recourse.setMemo(sysRecourse.getMemo());
			recourse.setDiscription(sysRecourse.getDiscription());
			recourse.setIsNavigation(sysRecourse.getIsNavigation());
			recourse.setIsTab(sysRecourse.getIsTab());
			recourse.setTreeId(sysRecourse.getTreeId());
		}
		return recourse;
    }

    /**
     * 属性转换
     * 
     * @param sysResourceInfo
     * @return
     */
    private SysRecourse getSysRecourse(SysResourceInfo sysResourceInfo) {
        SysRecourse recourse = new SysRecourse();
        recourse.setId(sysResourceInfo.getResourceId());
        recourse.setPid(sysResourceInfo.getResourcePid());
        recourse.setName(sysResourceInfo.getResourceName());
        recourse.setUrl(sysResourceInfo.getResourceContent());
        recourse.setStyle(sysResourceInfo.getStyle());
        recourse.setIsNavigation(sysResourceInfo.getIsNavigation());
        recourse.setIsTab(sysResourceInfo.getIsTab());
        recourse.setMemo(sysResourceInfo.getMemo());
        recourse.setTreeId(sysResourceInfo.getTreeId());
        return recourse;
    }

    /**
     * TODO 根据资源ID获取资源信息
     * @author 李利广
     * @Date 2019年03月15日 09:45:39
     * @param rid
     * @return com.sinosoft.sinoep.api.auth.vo.MessageSysResourceAndExt
     */
    @Override
    public MessageSysResourceAndExt getResourceInfo(String rid) {
        AuthApiService authApiService = (AuthApiService) SpringBeanUtils.getBean("authApiService");
        try {
            MessageSysResourceAndExt messageSysResourceAndExt = authApiService.findByRid(rid);
            return messageSysResourceAndExt;
        }catch (Exception e) {
            log.error("根据资源id获取资源详细信息异常：" + e);
        }
        return null;
    }

    /**
     * TODO 根据资源ID，获取用户在指定岗位下的拥有的子孙资源
     * @author 李利广
     * @Date 2019年03月19日 13:36:28
     * @param userId
     * @param pid
     * @param deptId
     * @return java.util.List<com.sinosoft.sinoep.uias.model.SysRecourse>
     */
    @Override
    public List<SysRecourse> getResourceByRid(String userId, String pid,String deptId){
        List<SysRecourse> list = new ArrayList<>();
        JSONObject para = new JSONObject();
        if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(deptId) && StringUtils.isNotBlank(pid)){
            para.put("userId",userId);
            para.put("subId",ConfigConsts.SYSTEM_ID);
            para.put("pid",pid);
            para.put("deptId",deptId);
            try{
                AuthApiService authApiService = (AuthApiService) SpringBeanUtils.getBean("authApiService");
                com.alibaba.fastjson.JSONObject result = authApiService.getOffspringByRid(para.toString());
                JSONObject res = JSONObject.fromObject(result);
                String status = res.getString("status");
                if (ConfigConsts.SUCESS_STARUS.equals(status)) {
                    // 获取子孙资源成功
                    JSONArray array = res.getJSONArray("resourceInfo");
                    if (array != null && array.size() > 0) {
                        list = getSysRecourseList(array, list);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return list;
    }

    /**
     * 递归获取左侧导航树
     * @param array
     * @param list
     * @return
     */
    private List<SysRecourse> getSysRecourseList(JSONArray array, List<SysRecourse> list) {
        for (int i = 0, length = array.size(); i < length; i++) {
            SysRecourse sr = new SysRecourse();
            JSONObject json = array.getJSONObject(i);
            if(json.getString("isNavigation").equals("1")) {
            	sr.setId(json.getString("resourceId"));
                sr.setPid(json.getString("resourcePid"));
                sr.setName(json.getString("resourceName"));
                sr.setUrl(json.getString("resourceContent"));
                sr.setDiscription(json.getString("discription"));
                sr.setMemo(json.getString("memo"));
                sr.setStyle(json.getString("style"));
                sr.setTreeId(json.getString("treeId"));
                JSONArray childrenArray = json.getJSONArray("resourceInfo");
                if (childrenArray != null && childrenArray.size() > 0) {
                    sr.setChildrenList(getSysRecourseList(childrenArray,sr.getChildrenList()));
                }
                list.add(sr);	
            }
        }
        return list;
    }

    /**
     * 根据业务角色编号给用户授权
     * @param userId 被授权人ID
     * @param deptId 被授权人所在单位ID
     * @param roleNo 授权业务角色编号
     * @param type  类型：是否需要审核（1需要审核；0不需要审核）
     * @return json(msg:异常信息；status:状态码；AuthorizationId:待审核ID)
     */
    @Override
    public JSONObject accreditByRoleNo(String userId,String deptId,String roleNo,String type){
        JSONObject jsonObject = new JSONObject();
        AuthApiService authApiService = (AuthApiService) SpringBeanUtils.getBean("authApiService");
        JSONObject json = new JSONObject();
        json.put("rolesNO",roleNo);
        json.put("uid",userId);
        json.put("deptId",deptId);
        json.put("type","1");
        json.put("subId",ConfigConsts.SYSTEM_ID);
        json.put("auther", UserUtil.getCruUserId());
        try{
            Map map = authApiService.saveFroleUserByRolesNO(json.toString());
            jsonObject = JSONObject.fromObject(map);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            jsonObject.put("status","-1");
        }
        return jsonObject;
    }

    /**
     * 根据业务角色编号取消给用户授权
     * @param userId 被授权人ID
     * @param deptId 被授权人所在单位ID
     * @param roleNo 授权业务角色编号
     * @param type  类型：是否需要审核（1需要审核；0不需要审核）
     * @return json(msg:异常信息；status:状态码；AuthorizationId:待审核ID)
     */
    @Override
    public JSONObject cancelAccreditByRoleNo(String userId, String deptId, String roleNo, String type){
        JSONObject jsonObject = new JSONObject();
        AuthApiService authApiService = (AuthApiService) SpringBeanUtils.getBean("authApiService");
        JSONObject json = new JSONObject();
        json.put("rolesNO",roleNo);
        json.put("uid",userId);
        json.put("deptId",deptId);
        json.put("type","1");
        json.put("subId",ConfigConsts.SYSTEM_ID);
        json.put("auther", UserUtil.getCruUserId());
        try{
            Map map = authApiService.deleteFTypeUserByRolesNO(json.toString());
            jsonObject = JSONObject.fromObject(map);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            jsonObject.put("status","-1");
        }
        return jsonObject;
    }

    /**
     * TODO 授权审核（通过/不通过）
     * @author 李利广
     * @Date 2019年02月19日 15:19:16
     * @param authIds   待审核数据ID（由授权/取消授权接口返回），多个由逗号分隔
     * @param status    审核通过/不通过（1通过；-1不通过）
     * @return net.sf.json.JSONObject（status：状态码；msg：状态描述）
     *          status 0是接口无数据;1是审核成功，-1审核操作异常，2审核无效,3参数为空
     */
    @Override
    public JSONObject addAudits(String authIds,String status){
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(authIds) && StringUtils.isNotBlank(status)){
            AuthApiService authApiService = (AuthApiService) SpringBeanUtils.getBean("authApiService");
            JSONObject json = new JSONObject();
            json.put("auditsId",authIds);
            json.put("status",status);
            try{
                Map map = authApiService.addAudits(json.toString());
                jsonObject = JSONObject.fromObject(map);
                if (UiasConfigConsts.FLAG_STATUS[1].equals(jsonObject.getString(UiasConfigConsts.STATUS))){
                    if (!"2".equals(jsonObject.getString("auditsStatus"))){
                        jsonObject.put("status","1");
                        jsonObject.put("msg","审核成功");
                    }else{
                        jsonObject.put("status","2");
                        jsonObject.put("msg","审核无效");
                    }
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
                jsonObject.put("status","-1");
                jsonObject.put("msg","审核操作异常");
            }
        }else{
            jsonObject.put("status","3");
            jsonObject.put("msg","参数不能为空");
        }
        return jsonObject;
    }

    @Override
    public List<Map<String, Object>> getResourceListByIds(String ids) {
        return null;
    }

    /**
     * TODO 获取用户在指定岗位下拥有的所有资源
     * @author 李利广
     * @Date 2019年03月15日 14:40:32
     * @param userId
     * @param deptId
     * @return java.util.List<com.sinosoft.sinoep.uias.model.SysRecourse>
     */
    @Override
    public List<SysRecourse> getFlowRlsyResId(String userId,String deptId){
        JSONObject para = new JSONObject();
        List<SysRecourse> resultList = new ArrayList<>();
        if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(deptId)){
            para.put("userId",userId);
            para.put("deptId",deptId);
            para.put("subId",ConfigConsts.SYSTEM_ID);
            try{
                AuthApiService authApiService = (AuthApiService) SpringBeanUtils.getBean("authApiService");
                MessageAuthorization auth = authApiService.getFlowRlsyResId(para.toString());
                if (UiasConfigConsts.FLAG_STATUS[1].equals(auth.getStatus())){
                    resultList = new ArrayList<>();
                    for (SysResourceInfo sysResourceInfo : auth.getResourceInfo()) {
                        //将不再资源树中展示的资源过滤
                        if ("1".equals(sysResourceInfo.getIsNavigation())) {
                            resultList.add(getSysRecourse(sysResourceInfo));
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return getChildrenAll(resultList);
    }

}
