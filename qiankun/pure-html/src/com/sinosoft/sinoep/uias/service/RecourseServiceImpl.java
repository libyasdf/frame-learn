package com.sinosoft.sinoep.uias.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.api.auth.vo.MessageAuthorization;
import com.sinosoft.sinoep.api.auth.vo.SysResourceInfo;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.JSONUtils;
import com.sinosoft.sinoep.uias.constant.UiasConfigConsts;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.api.auth.vo.MessageSysResourceAndExt;
import com.sinosoft.sinoep.uias.model.SysRecourse;
import com.sinosoft.sinoep.uias.util.RecourseUtils;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软 康俊祥
 * @since 2016年6月20日
 */
@Service("recourseRestService")
public class RecourseServiceImpl implements RecourseService {

    private static Log log = LogFactory.getLog(RecourseServiceImpl.class);

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
            try {
                String result = HttpRequestUtil.sendPost(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/resource/getOffspringByRid",
                        "para=" + para.toString());
                MessageAuthorization messageAuthorization = JSONUtils.getJsonAsObject(result,MessageAuthorization.class);
                if (ConfigConsts.SUCESS_STARUS.equals(messageAuthorization.getStatus())) {
                    List<SysResourceInfo> sysResourceInfoList = messageAuthorization.getResourceInfo();
                    for (SysResourceInfo sysResourceInfo : sysResourceInfoList) {
                        sysRecourseList.add(getSysRecourse(sysResourceInfo));
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return sysRecourseList;
    }

    /**
     * 属性转换
     * @param sysResourceInfo
     * @return
     */
    private SysRecourse getSysRecourse(SysResourceInfo sysResourceInfo) {
        SysRecourse recourse = new SysRecourse();
        recourse.setId(sysResourceInfo.getResourceId());
        recourse.setIsNavigation(sysResourceInfo.getIsNavigation());
        recourse.setIsTab(sysResourceInfo.getIsTab());
        recourse.setPid(sysResourceInfo.getResourcePid());
        recourse.setName(sysResourceInfo.getResourceName());
        recourse.setUrl(sysResourceInfo.getResourceContent());
        recourse.setStyle(sysResourceInfo.getStyle());
        recourse.setMemo(sysResourceInfo.getMemo());
        recourse.setTreeId(sysResourceInfo.getTreeId());
        return recourse;
    }

    /**
     * 获取资源信息
     */
    @Override
    public MessageSysResourceAndExt getResourceInfo(String rid) {
        return RecourseUtils.getResourceInfo(rid);
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
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(deptId) && StringUtils.isNotBlank(pid)){
            para.put("userId",userId);
            para.put("subId",ConfigConsts.SYSTEM_ID);
            para.put("pid",pid);
            para.put("deptId",deptId);
            try{
                String result = HttpRequestUtil.sendPost(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/resource/getOffspringByRid",
                        "para=" + para.toString());
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
                sr.setName(json.getString("resourceName"));
                sr.setUrl(json.getString("resourceContent"));
                sr.setPid(json.getString("resourcePid"));
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
     * @return
     */
    @Override
    public JSONObject accreditByRoleNo(String userId, String deptId, String roleNo, String type){
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        json.put("rolesNo",roleNo);
        json.put("uid",userId);
        json.put("deptId",deptId);
        json.put("type","1");
        json.put("subId",ConfigConsts.SYSTEM_ID);
        json.put("auther", UserUtil.getCruUserId());
        try{
            String str = HttpRequestUtil.sendPost(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/resource/saveFroleUserByrolesNO","json=" + json.toString());
            jsonObject = JSONObject.fromObject(str);
        }catch (Exception e){
            e.printStackTrace();
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
     * @return
     */
    @Override
    public JSONObject cancelAccreditByRoleNo(String userId, String deptId, String roleNo, String type){
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        json.put("rolesNo",roleNo);
        json.put("uid",userId);
        json.put("deptId",deptId);
        json.put("type","1");
        json.put("subId",ConfigConsts.SYSTEM_ID);
        json.put("auther", UserUtil.getCruUserId());
        try{
            String str = HttpRequestUtil.sendPost(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/resource/deleteFTypeUserByrolesNO","json=" + json.toString());
            jsonObject = JSONObject.fromObject(str);
        }catch (Exception e){
            e.printStackTrace();
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
        JSONObject jsonObj = new JSONObject();
        if (StringUtils.isNotBlank(authIds) && StringUtils.isNotBlank(status)){
            JSONObject json = new JSONObject();
            json.put("auditsId",authIds);
            json.put("status",status);
            try{
                String str = HttpRequestUtil.sendPost(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/resource/addAudits","para=" + json.toString());
                jsonObj = JSONObject.fromObject(str);
                if (UiasConfigConsts.FLAG_STATUS[1].equals(jsonObj.getString(UiasConfigConsts.STATUS))){
                    if ("2".equals(jsonObj.getString("auditsStatus"))){
                        jsonObj.put("status","2");
                        jsonObj.put("msg","审核无效");
                    }else{
                        jsonObj.put("status","1");
                        jsonObj.put("msg","审核成功");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                jsonObj.put("status","-1");
                jsonObj.put("msg","审核操作异常");
            }
        }else{
            jsonObj.put("status","3");
            jsonObj.put("msg","参数不能为空");
        }
        return jsonObj;
    }

    @Override
    public List<Map<String, Object>> getResourceListByIds(String ids) {
        return RecourseUtils.getResourceListByIds(ids);
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
        return new ArrayList<>();
    }

}
