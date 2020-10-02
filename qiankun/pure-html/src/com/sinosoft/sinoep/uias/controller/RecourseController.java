package com.sinosoft.sinoep.uias.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.sinosoft.sinoep.uias.constant.UiasConfigConsts;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.api.auth.vo.MessageSysResourceAndExt;
import com.sinosoft.sinoep.api.auth.vo.SysResourceInfo;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.uias.model.SysRecourse;
import com.sinosoft.sinoep.uias.service.RecourseService;

import net.sf.json.JSONObject;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软 康俊祥
 * @since 2016年6月20日
 */
@Controller
@RequestMapping("recourse")
public class RecourseController {

    @Resource(name = "recourseRestService")
    RecourseService recourseRestService;

    @Resource(name = "recourseDubboService")
    RecourseService recourseDubboService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * <B>方法名称：加载当前用户具有的资源</B><BR>
     * <B>概要说明：后台管理</B><BR>
     * @return
     */
    @ResponseBody
    @RequestMapping("top")
    public JSONObject myList() {
        JSONObject json = new JSONObject();
        String userId = UserUtil.getCruUserId();
        String deptId = UserUtil.getCruDeptId();
        List<SysRecourse> data = new ArrayList<>();
        try {
            data = recourseDubboService.getFlowRlsyResId(userId,deptId);
            json.put("flag", "1");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            json.put("flag", "0");
        }
        json.put("data", JSONArray.fromObject(data));
        return json;
    }

    /**
     *  左侧导航树
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping("left")
    public JSONObject leftList(String pid) {
        JSONObject json = new JSONObject();
        List<SysRecourse> data = new ArrayList<>();
        try {
            if (StringUtils.isNotBlank(pid)) {
                data = recourseDubboService.getResourceByRid(UserUtil.getCruUserId(),pid,UserUtil.getCruDeptId());
            }
            json.put("flag", "1");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            json.put("flag", "0");
        }
        json.put("data", JSONArray.fromObject(data));
        log.info(json.toString());
        return json;
    }

    /**
     * <B>方法名称：getResourceInfo</B><BR>
     * <B>概要说明：获取资源url</B><BR>
     * 
     * @param rid 资源id
     * @return
     */
    @ResponseBody
    @RequestMapping("getresourceinfo")
    public Map<String, String> getResourceInfo(String rid) {
        Map<String, String> map = new HashMap<>(12);
        if (StringUtils.isNotBlank(rid)) {
            MessageSysResourceAndExt m = null;
            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                m = recourseRestService.getResourceInfo(rid);
            }
            else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                m = recourseDubboService.getResourceInfo(rid);
            }
            if (m != null) {
                if (UiasConfigConsts.FLAG_STATUS[1].equals(m.getStatus())) {
                    SysResourceInfo s = m.getSysResourceInfo();
                    map.put("status", "ok");
                    map.put("url", s.getResourceContent());
                    return map;
                }
            }
        }
        map.put("status", "false");
        map.put("url", "");
        return map;
    }

    /**
     * 权限验证不通过的返回值
     * @return
     */
    @ResponseBody
    @RequestMapping("noaccess")
    public JSONObject noAccess() {
        JSONObject json = new JSONObject();
        json.put("msg", "您没有操作权限！");
        return json;
    }

    /**
     * 根据业务角色编号给用户授权
     * @param userId 被授权人ID
     * @param deptId 被授权人所在单位ID
     * @param roleNo 授权业务角色编号
     * @param type  类型：是否需要审核（1需要审核；0不需要审核）
     * @return json(msg:异常信息；status:状态码；AuthorizationId:待审核ID)
     */
    @ResponseBody
    @RequestMapping("accreditByRoleNo")
    public JSONObject accreditByRoleNo(String userId, String deptId, String roleNo, String type){
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(type)){
            type = "1";
        }
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            json = recourseRestService.accreditByRoleNo(userId,deptId,roleNo,type);
        }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            json = recourseDubboService.accreditByRoleNo(userId,deptId,roleNo,type);
        }
        return json;
    }
    /**
     * 根据业务角色编号取消给用户授权
     * @param userId 被授权人ID
     * @param deptId 被授权人所在单位ID
     * @param roleNo 授权业务角色编号
     * @param type  类型：是否需要审核（1需要审核；0不需要审核）
     * @return json(msg:异常信息；status:状态码；AuthorizationId:待审核ID)
     */
    @ResponseBody
    @RequestMapping("cancelAccreditByRoleNo")
    public JSONObject cancelAccreditByRoleNo(String userId, String deptId, String roleNo, String type){
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(type)){
            type = "1";
        }
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            json = recourseRestService.cancelAccreditByRoleNo(userId,deptId,roleNo,type);
        }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            json = recourseDubboService.cancelAccreditByRoleNo(userId,deptId,roleNo,type);
        }
        return json;
    }

    /**
     * （标签页调用方法）根据资源ID获取其下的所有子资源（包括不在导航树中显示的资源）
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping("getRecoursesByPid")
    public JSONObject getRecByPid(String pid){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        List<SysRecourse> list = new ArrayList<>();
        try{
            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                list = recourseRestService.getRecoursesByPid(UserUtil.getCruUserId(),pid,UserUtil.getCruDeptId());
            }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                list = recourseDubboService.getRecoursesByPid(UserUtil.getCruUserId(),pid,UserUtil.getCruDeptId());
            }
            json.put("flag","1");
            json.put("data",list);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * TODO 授权审核（通过/不通过）
     * @author 李利广
     * @Date 2019年02月19日 15:19:16
     * @param authIds   待审核数据ID（由授权/取消授权接口返回），多个由逗号分隔
     * @param status    审核通过/不通过（1通过；-1不通过）
     * @return net.sf.json.JSONObject
     */
    @ResponseBody
    @RequestMapping("addAudits")
    public JSONObject addAudits(String authIds,String status){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                json = recourseRestService.addAudits(authIds,status);
            }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                json = recourseDubboService.addAudits(authIds,status);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            json.put("flag","-1");
        }
        return json;
    }

    /**
     * 根据资源id查询模块数据（多个资源id用“,”隔开）
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("resourceList")
    public List<Map<String,Object>> getResourceListByIds(String ids) {
        List<Map<String,Object>> list = recourseRestService.getResourceListByIds(ids);
        return list;
    }

    /**
     * 根据资源id获取资源信息
     * @param id
     * @return 资源对象
     */
    @ResponseBody
    @RequestMapping("getResourceInfo")
    public SysResourceInfo getResource(String id) {
        SysResourceInfo sysResourceInfo = new SysResourceInfo();
        if (StringUtils.isNotBlank(id)) {
            MessageSysResourceAndExt m = null;
            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//restful
                m = recourseRestService.getResourceInfo(id);
            }
            else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//dubbo
                m = recourseDubboService.getResourceInfo(id);
            }
            if (m != null) {
                if ("1".equals(m.getStatus())) {//获取信息成功
                    sysResourceInfo = m.getSysResourceInfo();
                }
            }
        }
        return  sysResourceInfo;
    }


}
