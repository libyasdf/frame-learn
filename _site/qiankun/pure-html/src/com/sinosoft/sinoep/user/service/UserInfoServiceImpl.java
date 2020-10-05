package com.sinosoft.sinoep.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.jedis.services.RedisClusterService;
import com.sinosoft.sinoep.modules.system.component.affix.services.AffixService;
import com.sinosoft.sinoep.user.entity.UserParam;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.api.dept.DeptInfoService;
import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.api.dept.vo.DeptUserTreeVo;
import com.sinosoft.sinoep.api.dept.vo.MessageDept;
import com.sinosoft.sinoep.api.dept.vo.MessageSql;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.api.user.service.UserService;
import com.sinosoft.sinoep.api.user.vo.MessageUser;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.exception.SysException;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.JSONUtils;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.user.entity.DeptAllInfo;
import com.workflow.external.IClientWorkflowOrg;
import com.workflow.vo.OrgTreeNode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import workflow.service.DeptService;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>.
 *
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 张战国
 * @since 2017年2月24日
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private Log log = LogFactory.getLog(UserInfoServiceImpl.class);

    @Autowired
    DeptInfoService deptInfoService;
    @Autowired
    DeptService deptService;
    @Autowired
    IClientWorkflowOrg clientWorkflowOrg;

    @Autowired
    com.sinosoft.sinoep.api.dept.service.DeptInfoService  deptInfoServiceNew;

    @Autowired
    private UserService userService;

    @Autowired
    private AffixService affixService;

    @Autowired
    private RedisClusterService redisService;

    /**
     * <B>方法名称：getUserBase</B><BR>
     * <B>概要说明：根据用户id获取用户基础信息</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService# getUserBase(java.lang.Long)
     */
    @Override
    public UserInfo getUserBase(String uid, String type) {
        UserInfo userInfo = null;
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        Object info = redisService.get(CommonConstants.USER_INFO_REDIS + uid);
        if(info != null){
            userInfo = (UserInfo) JSONObject.toBean(JSONObject.fromObject(info), UserInfo.class);
        }else{
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
                MessageUser messageUser = getUserInfoById(uid);
                List<UserInfo> userInfoList = messageUser.getUserInfo();
                if (userInfoList.size() > 0) {
                    userInfo = userInfoList.get(0);
                }
            }else if (type.equals(ConfigConsts.REST_TYPE)) {//rest服务
                try {
                    //发送http请求获取用户信息
                    String result = new String(HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL
                            + "/user/getUserInfoById", "uid=" + uid)
                            .getBytes(), "utf-8");
                    if (StringUtils.isNotBlank(result)) {
                        JSONObject jsob = JSONObject.fromObject(result);
                        if ("1".equals(jsob.getString("status"))) {
                            JSONArray jsonArray = jsob.getJSONArray("userInfo");
                            JSONObject jsonobject = null;
                            if (jsonArray.size() > 0) {
                                jsonobject = jsonArray.getJSONObject(0);
                                userInfo = (UserInfo) JSONObject.toBean(jsonobject, UserInfo.class);
                            }
                        }
                        else {
                            log.error("获取用户信息异常：" + jsob.getString("msg"));
                        }
                    }
                }
                catch (Exception e) {
                    log.error("获取用户信息异常：" + e);
                }
            }
            if(userInfo != null){
                redisService.set(CommonConstants.USER_INFO_REDIS + uid,JSONObject.fromObject(userInfo).toString());
                //设置redis超时时间（1800秒 = 30分钟）
                redisService.expire(CommonConstants.USER_INFO_REDIS + uid,CommonConstants.EXPIRE_REDIS);
            }
        }
        return userInfo;

    }

    /**
     * 
     * <B>方法名称：getPrisonCompanyIdByDeptId</B><BR>
     * <B>概要说明：根据部门id获取最近实体信息</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getPrisonCompanyIdByDeptId(java.lang.String)
     */
    @Override
    public Dept getPrisonCompanyIdByDeptId(String deptid) {
        JSONObject json = new JSONObject();
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            json = deptInfoService.getIdepDeptByDeptid(deptid);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            json = json.getJSONObject(HttpRequestUtil.sendGet(
                    ConfigConsts.UIAS_SERVICE_ROOT_URL + "/dept/getIdepDeptByDeptid", "deptid=" + deptid));
        }
        if ("true".equals(json.getString("status"))) {
            return JSON.parseObject(json.getString("dept"), Dept.class);
        }
        else {
            return null;
        }
    }

    /**
     * 
     * <B>方法名称：getDeptById</B><BR>
     * <B>概要说明：获取部门信</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getDeptById(java.lang.String)
     */
    @Override
    public Dept getDeptById(String deptID) {
        MessageDept mdept = new MessageDept();
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            mdept = deptInfoServiceNew.getFlowDept(deptID);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            String mdeptJson = HttpRequestUtil.sendGet(
                    ConfigConsts.UIAS_SERVICE_ROOT_URL + "/dept/getFlowDept", "deptId=" + deptID);
            mdept = JSON.parseObject(mdeptJson, MessageDept.class);
        }
        if (!"1".equals(mdept.getStatus())) {
            return null;
        }
        List<Dept> listDept = mdept.getDeptInfo();
        return listDept.get(0);
    }

    /**
     * 
     * <B>方法名称：getDeptlistByDeptid</B><BR>
     * <B>概要说明：根据部门id获取该部门下的所有的组织部门</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getDeptlistByDeptid(java.lang.String, java.lang.String)
     */
    @SuppressWarnings({ "unchecked", "static-access" })
	@Override
    public List<Dept> getDeptlistByDeptid(String type, String deptid) {
        List<Dept> list = new ArrayList<Dept>();
        JSONObject dept = new JSONObject();
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            dept = deptInfoService.getDeptListIszhc(deptid, type);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            dept = dept.getJSONObject(HttpRequestUtil.sendGet(
                    ConfigConsts.UIAS_SERVICE_ROOT_URL + "/dept/getDeptListIszhc", "deptid=" + deptid + "&iszhc="
                            + type));
        }
        JSONObject jsonobject;
        Map<String, String> map = dept;
        if ("true".equals(map.get("status"))) {
            JSONArray arrraylist = JSONArray.fromObject(map.get("dept"));
            for (int i = 0; i < arrraylist.size(); i++) {
                jsonobject = arrraylist.getJSONObject(i);
                Dept t = (Dept) jsonobject.toBean(jsonobject, Dept.class);
                if (t.getDeptid().equals(Long.valueOf(deptid))) {
                    t.setOrderNo(0l);
                }
                list.add(t);
            }
            return list;
        }
        else {
            return null;
        }

    }

    /**
     * 
     * <B>方法名称：getUserFullName</B><BR>
     * <B>概要说明：得到用户名称</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getUserFullName(java.lang.String)
     */
    @Override
    public String getUserFullName(String userId) {
        String userFullName = "";
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            userFullName = deptService.getUserFullName(userId);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            userFullName = HttpRequestUtil.sendGet(
                    ConfigConsts.USER_SERVICE_ROOT_URL + "/dept/getUserFullName", "userId=" + userId);
        }
        return userFullName;
    }

    /**
     * 
     * <B>方法名称：getDeptOrUserTree</B><BR>
     * <B>概要说明：获得部门或人员树</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getDeptOrUserTree(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String getDeptOrUserTree(String type, String nodeType, String orgId, String deptId,
            String superId, String status) throws SysException {
        //获取用户部门实体类信息
        Dept dept = getDeptById(deptId);
        // if ("0".equals(deptId)) {
        superId = deptId;
        //  }
        String result = "";
        // 判断是否查询子资源
        if (StringUtils.isNotBlank(superId)) {
            JSONObject json = new JSONObject();
            json.put("orgId", orgId);
            json.put("type", type);
            json.put("subId", ConfigConsts.SYSTEM_ID);
            json.put("nodeType", nodeType);
            json.put("superId", superId);
            json.put("status", status);
            try {
                if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
                    result = clientWorkflowOrg.getOrgTree(json.toString());
                }
                else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
                    result = HttpRequestUtil.sendGet(
                            ConfigConsts.USER_SERVICE_ROOT_URL + "/tree/getOrgTree", "param=" + json.toString());
                }

            }
            catch (Exception e) {
                throw new SysException(ConfigConsts.ERROR_STATUS, "获取部门树异常", e);
            }
        }
        else {
            // 直接显示当前部门资源数据
            OrgTreeNode orgt = new OrgTreeNode();
            orgt.setId(deptId);
            orgt.setIsBranch("true");
            orgt.setIsLoad("false");
            orgt.setIsSelect("1");
            orgt.setName(dept.getDeptname());
            orgt.setNodeType("dept");
            List<OrgTreeNode> list = new ArrayList<OrgTreeNode>();
            list.add(orgt);
            JSONArray array = JSONArray.fromObject(list);
            result = array.toString();
        }
        return result;
    }

    /**
     * 
     * <B>方法名称：getDeptFullName</B><BR>
     * <B>概要说明：得到部门名称</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getDeptFullName(java.lang.String)
     */
    @Override
    public String getDeptFullName(String deptId) {
        String deptFullName = "";
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            deptFullName = deptService.getDeptFullName(deptId);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            deptFullName = HttpRequestUtil.sendGet(
                    ConfigConsts.USER_SERVICE_ROOT_URL + "/dept/getDeptFullName", "deptId=" + deptId);
        }
        return deptFullName;
    }

    /**
     * 
     * <B>方法名称：getOrgId</B><BR>
     * <B>概要说明：获得组织体系id</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getOrgId(java.lang.String)
     */
    @Override
    public String getOrgId(String deptId) {
        String orgId = "";
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            orgId = deptService.getOrgId(deptId);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            orgId = HttpRequestUtil.sendGet(
                    ConfigConsts.USER_SERVICE_ROOT_URL + "/dept/getOrgId", "deptId=" + deptId);
        }
        return orgId;
    }

    /**
     * 
     * <B>方法名称：getDeptId</B><BR>
     * <B>概要说明：根据用户ID得到部门ID</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService# getDept(java.lang.String)
     */
    @Override
    public String getDeptId(String userId) {
        String deptId = "";
        Object id = redisService.get(CommonConstants.USER_DEPTID_REDIS + userId);
        if(id != null && !id.equals("")){
            deptId = String.valueOf(id);
        }else{
            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
                deptId = deptService.getDept(userId);
            }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
                deptId = HttpRequestUtil.sendGet(
                        ConfigConsts.USER_SERVICE_ROOT_URL + "/dept/getDept", "userId=" + userId);
            }
            if(StringUtils.isNotBlank(deptId)){
                redisService.set(CommonConstants.USER_DEPTID_REDIS + userId,deptId);
                //设置redis超时时间（1800秒 = 30分钟）
                redisService.expire(CommonConstants.USER_DEPTID_REDIS + userId,CommonConstants.EXPIRE_REDIS);
            }
        }
        return deptId;
    }

    /**
     * 
     * <B>方法名称：getAllDeptByUserId</B><BR>
     * <B>概要说明：根据用户ID得用户到所有的部门</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getAllDeptByUserId(java.lang.String)
     */
    @Override
    public MessageDept getAllDeptByUserId(String userId) {
        MessageDept mdept = new MessageDept();
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            mdept = deptInfoService.getAllDeptByUserId(userId);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            String mdeptJson = HttpRequestUtil.sendGet(
                    ConfigConsts.UIAS_SERVICE_ROOT_URL + "/dept/getAllDeptByUserId", "userId=" + userId);
            mdept = JSON.parseObject(mdeptJson, MessageDept.class);
        }
        return mdept;
    }

    /**
     * 
     * <B>方法名称：getDeptName</B><BR>
     * <B>概要说明：得到部门全称</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getDeptName(java.lang.String)
     */
    @Override
    public String getDeptName(String deptId) {
        String deptName = "";
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            deptName = deptService.getDeptName(deptId);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            deptName = HttpRequestUtil.sendGet(
                    ConfigConsts.USER_SERVICE_ROOT_URL + "/dept/getDeptName", "deptId=" + deptId);
        }
        JSONArray jArray = JSONArray.fromObject(deptName);
        return jArray.getJSONObject(0).getString("name");
    }

    /**
     * 
     * <B>方法名称：getUserInfoById</B><BR>
     * <B>概要说明：根据用户id获得用户</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getUserInfoById(java.lang.String)
     */
    @Override
    public MessageUser getUserInfoById(String userId) {
        MessageUser messageUser = new MessageUser();
        UserService userService = (UserService) SpringBeanUtils.getBean("userService");
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            messageUser = userService.getUserInfoById(userId);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            String messageUserJson = HttpRequestUtil.sendGet(
                    ConfigConsts.UIAS_SERVICE_ROOT_URL + "/user/getUserInfoById", "uid=" + userId);
            messageUser = JSON.parseObject(messageUserJson, MessageUser.class);
        }
        return messageUser;
    }

    /**
     * 
     * <B>方法名称：getUserInfoByName</B><BR>
     * <B>概要说明：根据用户名获得用户信息</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getUserInfoByName(java.lang.String)
     */
    @Override
    public MessageUser getUserInfoByName(String userName) {
        MessageUser messageUser = new MessageUser();
        UserService userService = (UserService) SpringBeanUtils.getBean("userService");
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            messageUser = userService.getUserInfoByName(userName);

        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            String messageUserJson = HttpRequestUtil.sendGet(
                    ConfigConsts.UIAS_SERVICE_ROOT_URL + "/user/getUserInfoByName", "uName=" + userName);
            messageUser = JSON.parseObject(messageUserJson, MessageUser.class);
        }
        return messageUser;
    }

    /**
     * 
     * <B>方法名称：getDeptByNmAndOrgId</B><BR>
     * <B>概要说明：通过部门名称和部门组织体系id获取部门列表</B><BR>
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getDeptByNmAndOrgId(java.lang.String, java.lang.String)
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
	@Override
    public List<Dept> getDeptByNmAndOrgId(String deptname, String orgid) {
        List<Dept> list = new ArrayList<Dept>();
        JSONArray dept = new JSONArray();
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
            list = deptInfoService.getDeptByNmAndOrgId(deptname, orgid);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
            dept = JSONArray.fromObject(HttpRequestUtil.sendGet(
                    ConfigConsts.UIAS_SERVICE_ROOT_URL + "/dept/getDeptByNmAndOrgId", "deptname=" + deptname
                            + "&orgid="
                            + orgid));
            list = JSONArray.toList(dept, Dept.class);
        }
        return list;

    }

    /**
     * 
     * <B>方法名称：getDepartmentStaffByName</B><BR>
     * <B>概要说明：根据名字查询部门和用户</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService# getDepartmentStaffByName(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public String getDepartmentStaffByName(String type, String qName, String userid,String deptId) {
        JSONObject result = null;
        JSONArray resultArray = null;
        try {
            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
                result =deptInfoServiceNew.getDeptAndUserTreeByName(type, qName, userid,deptId);
                resultArray = result.getJSONArray("deptUserArry");
            }
            else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
                String resultNew = HttpRequestUtil.sendGet(
                        ConfigConsts.UIAS_SERVICE_ROOT_URL + "/dept/getDeptAndUserTreeByName", "type=" + type
                                + "&qname="
                                + qName + "&userId=" + userid + "&deptId=" + deptId);
                result = JSONObject.fromObject(resultNew);
                resultArray = result.getJSONArray("deptUserArry");
            }
        }
        catch (Exception e) {
            log.error("获取全部部门人员数据异常", e);
        }
        return resultArray.toString();
    }

    /**
     * 
     * <B>方法名称：getDepartmentStaffTree</B><BR>
     * <B>概要说明：获取全部部门人员数据</B><BR>
     * 
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getDepartmentStaffTree(java.lang.String)
     */
    @Override
    public String getDepartmentStaffTree(String type) {
        String resultJson = "";
        try {
            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {//Dubbo服务
                List<DeptUserTreeVo> result = deptInfoService.getDeptAndUserTree(type);
                resultJson = JSONArray.fromObject(result).toString();
            }
            else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {//rest服务
                resultJson = HttpRequestUtil.sendGet(
                        ConfigConsts.UIAS_SERVICE_ROOT_URL + "/dept/getDeptAndUserTree", "type=" + type);
            }
        }
        catch (Exception e) {
            log.error("获取全部部门人员数据异常", e);
        }
        return resultJson;
    }



    /**
     * 根据deptid获取其所有父部门的信息
     * @param deptId
     * @return
     */
    @Override
    public List<Dept> getAllSuperDeptById(String deptId) {
        List<Dept> deptList = new ArrayList<>();
        Object list = redisService.get(CommonConstants.ALL_SUPER_DEPT + deptId);
        if(list != null){
            JSONArray array = JSONArray.fromObject(list);
            for(int i = 0;i < array.size();i++){
                deptList.add((Dept)JSONObject.toBean(array.getJSONObject(i),Dept.class));
            }
        }
        if(deptList.size() <= 0){
            Dept dept = getDeptById(deptId);
            deptList = getSuperDept(dept,deptList);
            if(deptList.size() > 0){
                redisService.set(CommonConstants.ALL_SUPER_DEPT + deptId,JSONArray.fromObject(deptList).toString());
                //设置redis超时时间（1800秒 = 30分钟）
                redisService.expire(CommonConstants.ALL_SUPER_DEPT + deptId,CommonConstants.EXPIRE_REDIS);
            }
        }
        return deptList;
    }

    private List<Dept> getSuperDept(Dept dept, List<Dept> deptList) {
        if (dept != null) {
            deptList.add(dept);
            if (!"0".equals(dept.getSuperId())) {
                Dept superDept = getDeptById(dept.getSuperId().toString());
                getSuperDept(superDept,deptList);
            }
        }
        return  deptList;
    }

    @Override
    public List<UserInfo> getUserByDeptId(String deptId) {
        String resultJson = "";
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        JSONArray jsonArray = null;
        try{
            if(StringUtils.isNotBlank(deptId)){
                if(ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)){
                    jsonArray = deptInfoService.getUserByDeptid(deptId,"1");
                    if(jsonArray.size()>0){
                        for(int i=0;i<jsonArray.size();i++){
                            UserInfo user = new UserInfo();
                            JSONObject job = jsonArray.getJSONObject(i);
                            user.setUid(job.getString("userid"));
                            user.setUserName((String)job.get("usernemefull"));
                            user.setStatus((String)job.get("status"));
                            userInfoList.add(user);
                        }
                    }
                }else if(ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)){
                    resultJson = HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL+"/dept/getUserByDeptId","deptId="+deptId+"&status=1");
                    List<JSONObject> list = JSON.parseArray(resultJson,JSONObject.class);
                    userInfoList = objectToUserInfo(list);
                }
            }
        }catch (Exception e){
            log.error("根据部门id获取人员信息异常",e);
        }
        return userInfoList;
    }

    @Override
    public List<UserInfo> getAllUserByDeptId(String deptId) {
        List<UserInfo> list = new ArrayList<UserInfo>();
        try{
            String deptIds = "";
            String[] deptIdArray = deptId.split(",");
            for (String s:deptIdArray){
                deptIds +="'"+s+"',";
            }
            deptId = deptIds.substring(0,deptIds.length()-1);
            StringBuilder sql = new StringBuilder();
            sql.append("select *" +
                    "  from (select rownum as row_num, t.deptname, t.deptid, t.order_no" +
                    "  from sys_flow_dept t" +
                    "  where t.status = '1'" );
            if(StringUtils.isNotEmpty(deptId)){
               sql.append(" start with t.deptid in(").append(deptId).append(") ");
            }else{
                sql.append(" start with t.deptid in('') ");
            }
            sql.append(" connect by prior t.deptid = t.super_id" +
                    " order siblings by t.order_no) a," +
                    " (select * from sys_user_dprb t where t.status = '1') ud," +
                    " (select * from sys_flow_user u where u.status = '1') u" +
                    " where a.deptid = ud.deptid" +
                    " and ud.userid(+) = u.userid" +
                    " order by a.row_num, ud.order_no");
            JSONObject json = this.getDateBySql(sql.toString());
            List<JSONObject> userList = json.getJSONArray("data");
            for(JSONObject obj:userList){
                UserInfo user = new UserInfo();
                user.setUid(obj.getString("userid"));
                user.setUserName((String)obj.get("username"));
                list.add(user);
            }
        }catch (Exception e){
            log.error("获取部门下所有人员信息错误",e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<UserInfo> getUserByDeptIdAndRolesNo1(String deptId, String rolesNo) {
        String deptIds = "";
        JSONObject deptJSON = this.getAllDeptBySuperId(deptId);
        JSONArray jsonArray = deptJSON.getJSONArray("data");
        List<Dept> list = JSONArray.toList(jsonArray,Dept.class);
        for(Dept item :list){
            deptIds +=item.getDeptid()+",";
        }
        return this.getUserByDeptIdAndRolesNo(deptIds,rolesNo);
    }

    @Override
    public List<UserInfo> getUserByDeptIdAndRolesNo(String deptId, String rolesNo) {
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        try {
            if(StringUtils.isNotBlank(rolesNo) && StringUtils.isNotBlank(deptId)){
                if(!rolesNo.contains("'")){
                    String roles = "";
                    String[] rolesNoArray = rolesNo.split(",");
                    for(String s :rolesNoArray){
                        roles += "'"+s+"',";
                    }
                    rolesNo = roles.substring(0,roles.length()-1);
                }
                String deptIds = "";
                String[] deptIdArray = deptId.split(",");
                for (String s:deptIdArray){
                    deptIds +="'"+s+"',";
                }
                deptId = deptIds.substring(0,deptIds.length()-1);
                String sql ="select  u.userid, nvl(USERNAME, ' ') username, nvl(USER_NM, ' ') usernickname, nvl(USERNAMEFULL, ' ') userfullname, nvl(USER_TITLE, ' ') usertitle," +
                        "       nvl(USER_SEX, ' ') usergender, nvl(ADDRESS, ' ') address,nvl(COMPANY, ' ') company, nvl(QQ, ' ') qq, nvl(UNIONID, ' ') unionid," +
                        "       nvl(BIRTHDATE, ' ') birthdate, nvl(DEGREE, ' ') degree, nvl(PHONE, ' ') phone, nvl(USER_EMAIL, ' ') email,nvl(CA_G_CODE, ' ') cagcode," +
                        "       nvl(IDENTITY, ' ') idcard, nvl(CO_REGISTRATION_CODE, ' ') coregistrationcode,nvl(USER_STATUS, ' ') usertype,nvl(IMAGEPATH, ' ') imagepath," +
                        "       nvl(u.STATUS, ' ') status, nvl(u.NOTE, ' ') note,nvl(MTIME, ' ') mtime,nvl(PHONEIMEI, ' ') phoneimei, nvl(AUTHENTICATION, ' ') authentication," +
                        "       nvl(SENSITIVE_MARK, ' ') sensitivemark,nvl(POSITION_NAME, ' ') positionname,nvl(POSITION_LEVEL, ' ') positionlevel,nvl(POLICE_NUMBER, ' ') policenumber,nvl(CARD_NUMBER, ' ') cardnumber" +
                        "  from sys_user_dprb     ud," +
                        "       sys_flow_user     u," +
                        "       sys_user_frole    s," +
                        "       sys_flow_role     r," +
                        "       SYS_FLOW_USER_EXT ue" +
                        " where u.status = '1'" +
                        "   and ud.status = '1'" +
                        "   and s.status = '1'" +
                        "   and r.status = '1'"+
                        "   and u.userid = ue.userid" +
                        "   and u.userid = ud.userid" +
                        "   and ud.id = s.u_dept_id" +
                        "   and r.roleid = s.roleid" +
                        "   and r.role_no in ("+rolesNo+")" +
                        "   and ud.deptid in ("+deptId+")" +
                        " order by ud.order_no";
                JSONObject json = this.getDateBySql(sql);
                userInfoList = this.objectToUserInfo2(json.getJSONArray("data"));
            }
        }catch (Exception e){
            log.error("获取部门下业务角色的人员信息异常",e);
        }
        return userInfoList;
    }

    /**
     * 人员信息装换
     * @param list
     * @return
     */
    public List<UserInfo> objectToUserInfo(List<JSONObject> list){
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        for (JSONObject obj :list){
            UserInfo user = new UserInfo();
            user.setUid(obj.getString("Uid"));
            user.setUserName((String)obj.get("userName"));
            user.setUserNickName((String)obj.get("userNickName"));
            user.setUserFullName((String)obj.get("userFullName"));
            user.setUserTitle((String)obj.get("userTitle"));
            user.setUserGender((String)obj.get("userGender"));
            user.setAddress((String)obj.get("address"));
            user.setCompany((String)obj.get("company"));
            user.setQq((String)obj.get("qq"));
            user.setUnionid((String)obj.get("unionid"));
            user.setBirthdate((String)obj.get("birthdate"));
            user.setDegree((String)obj.get("degree"));
            user.setPhone((String)obj.get("phone"));
            user.setEmail((String)obj.get("email"));
            user.setCaGCode((String)obj.get("caGCode"));
            user.setIdcard((String)obj.get("idcard"));
            user.setCoRegistrationCode((String)obj.get("coRegistrationCode"));
            user.setUserType((String)obj.get("userType"));
            user.setImagepath((String)obj.get("imagepath"));
            user.setStatus((String)obj.get("status"));
            user.setNote((String)obj.get("note"));
            user.setMtime((String)obj.get("mtime"));
            user.setPhoneIMEI((String)obj.get("phoneIMEI"));
            user.setSubstatus((String)obj.get("substatus"));
            user.setAuthentication((String)obj.get("authentication"));
            user.setSensitiveMark((String)obj.get("sensitiveMark"));
            user.setPositionName((String)obj.get("positionName"));
            user.setPositionLevel((String)obj.get("positionLevel"));
            user.setPoliceNumber((String)obj.get("policeNumber"));
            user.setCardNumber((String)obj.get("cardNumber"));
            userInfoList.add(user);
        }
        return userInfoList;
    }

    public List<UserInfo> objectToUserInfo2(List<JSONObject> list){
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        for (JSONObject obj :list){
            UserInfo user = new UserInfo();
            user.setUid(obj.getString("userid"));
            user.setUserName((String)obj.get("username"));
            user.setUserNickName((String)obj.get("usernickname"));
            user.setUserFullName((String)obj.get("userfullname"));
            user.setUserTitle((String)obj.get("usertitle"));
            user.setUserGender((String)obj.get("usergender"));
            user.setAddress((String)obj.get("address"));
            user.setCompany((String)obj.get("company"));
            user.setQq((String)obj.get("qq"));
            user.setUnionid((String)obj.get("unionid"));
            user.setBirthdate((String)obj.get("birthdate"));
            user.setDegree((String)obj.get("degree"));
            user.setPhone((String)obj.get("phone"));
            user.setEmail((String)obj.get("email"));
            user.setCaGCode((String)obj.get("cagcode"));
            user.setIdcard((String)obj.get("idcard"));
            user.setCoRegistrationCode((String)obj.get("coregistrationcode"));
            user.setUserType((String)obj.get("usertype"));
            user.setImagepath((String)obj.get("imagepath"));
            user.setStatus((String)obj.get("status"));
            user.setNote((String)obj.get("note"));
            user.setMtime((String)obj.get("mtime"));
            user.setPhoneIMEI((String)obj.get("phoneimei"));
            user.setSubstatus((String)obj.get("substatus"));
            user.setAuthentication((String)obj.get("authentication"));
            user.setSensitiveMark((String)obj.get("sensitivemark"));
            user.setPositionName((String)obj.get("positionname"));
            user.setPositionLevel((String)obj.get("positionlevel"));
            user.setPoliceNumber((String)obj.get("policenumber"));
            user.setCardNumber((String)obj.get("cardnumber"));
            userInfoList.add(user);
        }
        return userInfoList;
    }

    /**
     * 根据部门id获取部门信息和局、二级局、处室信息
     * @param deptId
     * @return
     */
    @Override
    public DeptAllInfo getDeptInfoByDeptId(String deptId) {
        List<Dept> deptList = this.getAllSuperDeptById(deptId);
        DeptAllInfo entity = new DeptAllInfo();
        for (int n=0;n<deptList.size();n++){
            Dept dept = deptList.get(n);
            if (n==0){
                entity.setDeptId(dept.getDeptid());
                entity.setDeptname(dept.getDeptname());
                entity.setAbbreviation(dept.getAbbreviation());
                entity.setTreeId(dept.getTreeId());
                entity.setSuperId(dept.getSuperId());
                entity.setOrderNo(dept.getOrderNo());
                entity.setDeptNumber(dept.getDeptNumber());
                entity.setDeptPhone(dept.getDeptPhone());
                entity.setStatus(dept.getStatus());
            }
            if (dept.getTreeId().length() == 4){
                entity.setOrgId(dept.getDeptid());
                entity.setOrgName(dept.getDeptname());
            }else if (dept.getTreeId().length() == 8){
                entity.setJuId(dept.getDeptid());
                entity.setJuName(dept.getDeptname());
            }else if (dept.getTreeId().length() == 12){
                entity.setChushiId(dept.getDeptid());
                entity.setChushiName(dept.getDeptname());
            }
        }
        return entity;
    }
    
    /**
     * TODO 根据自定义sql查询用户信息（uias数据库）
     * @author 李利广
     * @Date 2018年7月3日 下午5:02:08
     * @param sql
     * @return
     */
    @Override
    public JSONObject getDateBySql(String sql){
    	JSONObject json = new JSONObject();
		MessageSql data = new MessageSql();
        try {
            if(ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)){
                data = deptInfoServiceNew.getDataBySql(sql);
            }else {	//rest服务
                String result = HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL+"/dept/getDataBySql","sql=" + sql);
                data = JSONUtils.getJsonAsObject(result, MessageSql.class);
            }
            json.put("flag", data.getMsg());
            json.put("msg", data.getStatus());
            json.put("data", data.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", "-1");
            json.put("msg", "接口响应异常！");
        }
    	return json;
    }

    @Override
    public JSONObject getAllDeptBySuperId(String superId) {
        JSONObject json = new JSONObject();
        MessageDept data = new MessageDept();
        try{
            if(ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)){
                data = deptInfoServiceNew.getAllDeptBySuperId(superId);
            }else {	//rest服务
                String result = HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL+"/dept/getAllDeptBySuperId","superId=" + superId);
                data = JSONUtils.getJsonAsObject(result, MessageDept.class);
            }
            json.put("flag", data.getStatus());
            json.put("msg", data.getMsg());
            json.put("data", data.getDeptInfo());
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag","-1");
            json.put("msg","接口响应异常");
        }
        return json;
    }

    /**
     * 根据部门id获取部门下所有职务权限（职级或职务级别）的部门人员树
     * @param deptId
     * @param zw 职务
     * @param zj 职级
     * @param zwjb 职务级别
     * @return
     */
    @Override
    public JSONObject getUserInfoByDeptIdAndZwZj(String deptId, String zw, String zj,String zwjb) {
        JSONObject json = new JSONObject();
        String deptIds = "";
        if(StringUtils.isNotEmpty(deptId)) {
            JSONObject deptJson = this.getAllDeptBySuperId(deptId);
            if("1".equals(deptJson.getString("flag"))){
                JSONArray jsonArray = deptJson.getJSONArray("data");
                List<Dept> list = JSONArray.toList(jsonArray, Dept.class);
                for (Dept item : list) {
                    deptIds += "'" + item.getDeptid() + "',";
                }
                deptIds = deptIds.substring(0, deptIds.length() - 1);
            }
        }
        if(StringUtils.isNotBlank(zw)){
            String[] zwArray = zw.split(",");
            String zws = "";
            for(String s:zwArray){
                zws += "'"+s+"',";
            }
            zw = zws.substring(0,zws.length()-1);
        }
        if(StringUtils.isNotBlank(zj)){
            String[] zjArray = zj.split(",");
            String zjs = "";
            for(String s:zjArray){
                zjs += "'"+s+"',";
            }
            zj = zjs.substring(0,zjs.length()-1);
        }
        if(StringUtils.isNotBlank(zwjb)){
            String[] zwjbArray = zwjb.split(",");
            String zwjbs = "";
            for(String s:zwjbArray){
                zwjbs += "'"+s+"',";
            }
            zwjb = zwjbs.substring(0,zwjbs.length()-1);
        }
        StringBuilder sql = new StringBuilder("");
        StringBuilder deptSql = new StringBuilder();
        sql.append("select u.userid,u.username, u.deptid,u.deptname,p.name, p.value ,'user' type" +
                "  from (select s.name,s.value,s.remarks from sys_dictionary s) p," +
                "       (select c.userid, c.username, c.position_name,c.position_level,c.POSITION_REAL_LEVEL,a.deptid, a.deptname" +
                "          from (select rownum as row_num, t.deptname, t.deptid, t.order_no" +
                "                  from sys_flow_dept t" +
                "                 where t.status = '1'" +
                "                 start with t.deptid = '441'" +
                "                connect by prior t.deptid = t.super_id" +
                "                 order siblings by t.order_no) a," +
                "               (select * from sys_user_dprb t where t.status = '1') b," +
                "               (select * from sys_flow_user t where t.status = '1') c" +
                "         where a.deptid = b.deptid" +
                "           and b.userid(+) = c.userid" +
                "         order by a.row_num, b.order_no) u" +
                " where 1=1" );
        deptSql.append("select t.deptid, t.deptname,"+
                "t.order_no,"+
                "t.super_id,"+
                "t.tree_id,"+
                "'dept' type from sys_flow_dept t"+
                " where t.status = '1' "+
                " start with t.deptid in( "+
                " select distinct(u.deptid) deptid from (select s.name,s.value,s.remarks from sys_dictionary s) p,"+
                "       (select c.userid, c.username, c.position_name,c.position_level,c.POSITION_REAL_LEVEL, a.deptid, a.deptname" +
                "          from (select rownum as row_num, t.deptname, t.deptid, t.order_no,t.tree_id,t.super_id " +
                "                  from sys_flow_dept t" +
                "                 where t.status = '1'" +
                "                 start with t.deptid = '441'" +
                "                connect by prior t.deptid = t.super_id" +
                "                 order siblings by t.order_no) a," +
                "               (select * from sys_user_dprb t where t.status = '1') b," +
                "               (select * from sys_flow_user t where t.status = '1') c" +
                "         where a.deptid = b.deptid" +
                "           and b.userid(+) = c.userid" +
                "         order by a.row_num, b.order_no) u" +
                " where 1=1 " );
        if(StringUtils.isNotEmpty(deptIds)){
            sql.append(" and u.deptid in (").append(deptIds).append(")");
            deptSql.append(" and u.deptid in (").append(deptIds).append(")");
        }
        if(StringUtils.isNotEmpty(zw)){//职务
            sql.append(" and p.value = u.position_name ");
            sql.append(" and p.remarks = '1'");
            sql.append(" and p.value in (").append(zw).append(")");
            deptSql.append(" and p.value = u.position_name ");
            deptSql.append(" and p.remarks = '1'");
            deptSql.append(" and p.value in (").append(zw).append(")");
        }else if(StringUtils.isNotEmpty(zj)){//职级
            sql.append(" and p.value = u.position_level ");
            sql.append(" and p.remarks = '2'");
            sql.append(" and p.value in (").append(zj).append(")");
            deptSql.append(" and p.value = u.position_level ");
            deptSql.append(" and p.remarks = '2'");
            deptSql.append(" and p.value in (").append(zj).append(")");
        }else if(StringUtils.isNotEmpty(zwjb)){
            sql.append(" and p.value = u.POSITION_REAL_LEVEL ");
            sql.append(" and p.remarks = '5'");
            sql.append(" and p.value in (").append(zwjb).append(")");
            deptSql.append(" and p.value = u.POSITION_REAL_LEVEL ");
            deptSql.append(" and p.remarks = '5'");
            deptSql.append(" and p.value in (").append(zwjb).append(")");
        }
        deptSql.append(" ) connect by prior t.super_id = t.deptid " +
                " union （select t.deptid,t.deptname,t.order_no,t.super_id,t.tree_id,'dept' type from sys_flow_dept t where t.status ='1' and t.deptid ='441'）");
        json = this.getDateBySql(sql.toString());
        JSONArray jsonArray = json.getJSONArray("data");
        JSONObject deptjson = new JSONObject();
        deptjson = this.getDateBySql(deptSql.toString());
        jsonArray.addAll(deptjson.getJSONArray("data"));
        json.put("data",jsonArray);
        return json;
    }

    /**
     * 根据用户id获取排好序的部门
     * @param userIds
     * @return
     */
    @Override
    public JSONObject getDeptByUserIds(String userIds){
        JSONObject json = new JSONObject();
        if(StringUtils.isNotEmpty(userIds)){
            String[] userIdsArry = userIds.split(",");
            String userIds1 = "";
            for(String userId :userIdsArry){
                userIds1  +="'"+userId+"',";
            }
            userIds1 = userIds1.substring(0,userIds1.length()-1);
            StringBuilder sql = new StringBuilder("select * from (select distinct(a.deptid),a.deptName,a.order_no,a.super_id,a.tree_id,a.type from (select a.* ,'dept' type" +
                    "          from (select rownum as row_num, t.deptname, t.deptid, t.order_no,t.super_id,t.tree_id" +
                    "                  from sys_flow_dept t" +
                    "                 where t.status = '1'" +
                    "                 start with t.deptid = '441'" +
                    "                connect by prior t.deptid = t.super_id" +
                    "                 order siblings by t.order_no) a," +
                    "               (select * from sys_user_dprb t where t.status = '1') b," +
                    "               (select * from sys_flow_user t where t.status = '1') c" +
                    "         where a.deptid = b.deptid" +
                    "           and b.userid(+) = c.userid");
            sql.append(" and c.userid in(").append(userIds1).append(") order by a.row_num ) a) ");
            sql.append(" union ");
            sql.append(" (select t.deptid,t.deptname,t.order_no,t.super_id,t.tree_id,'dept' type from sys_flow_dept t " +
                    "where t.status ='1' and t.deptid='441')");
            System.out.println("根据用户id获取排好序的部门="+sql);
            json = this.getDateBySql(sql.toString());
        }
        return json;
    }

    @Override
    public JSONObject getUsersByUserNameFull(String userNameFull) {
        String sql = "select s.userid,s.usernamefull,d.deptid,'user' type from sys_flow_user s ,sys_user_dprb d where s.status = '1' and d.status = '1' and s.userid = d.userid ";
        if(StringUtils.isNotEmpty(userNameFull)){
            sql+=" and s.usernamefull = '"+userNameFull+"'";
        }
        return this.getDateBySql(sql);
    }

    @Override
    public JSONObject getUserTreeOnlySelectByUserNameFull(String userNameFull) {
        JSONObject json = new JSONObject();
        JSONObject userJson = new JSONObject();
        JSONObject deptJson = new JSONObject();
        String userIds = "";
        if(StringUtils.isNotEmpty(userNameFull)){
            userJson = this.getUsersByUserNameFull(userNameFull);
            List<Map<String,Object>> userList = (List<Map<String,Object>>)userJson.get("data");
            for(Map<String,Object> map:userList){
                userIds +=map.get("userid")+",";
            }
            userIds = userIds.substring(0,userIds.length()>=1?(userIds.length()-1):0);
            deptJson = this.getAllDeptByUserids(userIds);
            JSONArray jsonArray = userJson.getJSONArray("data");
            if(deptJson.size() > 0){
                jsonArray.addAll(deptJson.getJSONArray("data"));
            }
            json.put("data",jsonArray);
        }
        return json;
    }

    @Override
    public JSONObject getAllDeptByUserids(String userIds) {
        JSONObject json = new JSONObject();
        if(StringUtils.isNotEmpty(userIds)) {
            String[] userIdsArry = userIds.split(",");
            String userIds1 = "";
            for (String userId : userIdsArry) {
                userIds1 += "'" + userId + "',";
            }
            userIds1 = userIds1.substring(0, userIds1.length() - 1);
            StringBuilder sql = new StringBuilder("select t.deptid, t.deptname, t.order_no, t.super_id, t.tree_id, 'dept' type" +
                    "  from sys_flow_dept t" +
                    " where t.status = '1'" +
                    " start with t.deptid in (select distinct(a.deptid) from (select a.* ,'dept' type" +
                    "          from (select rownum as row_num, t.deptname, t.deptid, t.order_no,t.super_id,t.tree_id" +
                    "                  from sys_flow_dept t" +
                    "                 where t.status = '1'" +
                    "                 start with t.deptid = '441'" +
                    "                connect by prior t.deptid = t.super_id" +
                    "                 order siblings by t.order_no) a," +
                    "               (select * from sys_user_dprb t where t.status = '1') b," +
                    "               (select * from sys_flow_user t where t.status = '1') c" +
                    "         where a.deptid = b.deptid" +
                    "           and b.userid(+) = c.userid");
            sql.append(" and c.userid in(").append(userIds1).append(") order by a.row_num ) a) connect by prior t.super_id = t.deptid");
            sql.append(" union ");
            sql.append(" (select t.deptid,t.deptname,t.order_no,t.super_id,t.tree_id,'dept' type from sys_flow_dept t " +
                    "where t.status ='1' and t.deptid='441')");
            System.out.println("根据用户ids获取排好序的部门及以上部门=" + sql);
            json = this.getDateBySql(sql.toString());
        }
        return json;
    }

    @Override
    public JSONObject getGroup(String subId) {
        JSONObject json = new JSONObject();
        if(StringUtils.isNotBlank(subId)){
            String[] subIdArray = subId.split(",");
            String subId1 ="";
            for (String s:subIdArray){
                subId1 +="'"+s+"',";
            }
            subId1 = subId1.substring(0,subId1.length()-1);
            String sql = "select * from (select k.id uuid, k.name name, to_char(k.sub_id) uupid,k.order_no orderNo ,'key' type" +
                    "  from sys_grup_key k" +
                    " where k.sub_id in ("+subId1+")" +
                    " union" +
                    " select r.sub_id uuid, r.sub_name name, '0' uupid, 0 orderNo ,'key' type" +
                    "  from sys_sub_rd_info r" +
                    " where r.status = '1'" +
                    "   and r.sub_id in ("+subId1+")" +
                    " union" +
                    " select g.grup_id uuid,g.grup_name name,to_char(g.grup_type) uupid,g.order_no orderNo ,'value' type" +
                    "  from sys_group g" +
                    " where g.status = '1'" +
                    "   and g.grup_type in" +
                    "       (select k.id from sys_grup_key k where k.sub_id in ("+subId1+"))" +
                    ") order by orderNo";
            try {
                json = this.getDateBySql(sql);
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
    }

    @Override
    public JSONObject getGroupUser(String groupId) {
        JSONObject json = new JSONObject();
        if(StringUtils.isNotBlank(groupId)){
            String sql = "select g.id,g.grup_id,u.userid ,u.username from sys_user_grup g,sys_flow_user u where g.status = '1' and u.status = '1' and g.userid = u.userid ";
            sql += " and g.grup_id = '"+groupId+"'";
            json = this.getDateBySql(sql);
        }
        return json;
    }

    /**
     * 通过类型和编号获取对应名称（职务权限：1，职级：2，职务：5）
     * @param remarks
     * @param value
     * @return
     */
    @Override
    public String getNameByCode(String remarks,String value){
        String name = "";
        StringBuilder sql = new StringBuilder("select name from sys_dictionary where 1=1 ");
        if (StringUtils.isNotBlank(remarks) && StringUtils.isNotBlank(value)){
            sql.append(" and remarks = '").append(remarks).append("'");
            sql.append(" and value = '").append(value).append("'");
        }
        log.info("获取职务职级名称sql=" + sql.toString());
        JSONObject json = this.getDateBySql(sql.toString());
        JSONArray jsonArray = new JSONArray();
        if ("1".equals(json.getString("flag"))){
            jsonArray.addAll(json.getJSONArray("data"));
            List<Map<String,Object>> list = (List)jsonArray;
            if (list.size() > 0){
                name = (String)list.get(0).get("name");
            }
        }
        return name;
    }

    /**
     * TODO 更新用户信息
     * @author 李利广
     * @Date 2019年03月05日 16:30:54
     * @param userInfo
     * @return net.sf.json.JSONObject
     */
    @Override
    public JSONObject updateUserInfo(Map<UserParam,String> userInfo){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
            if (userInfo != null && !userInfo.isEmpty()){
                JSONObject para = new JSONObject();
                para.put("type","1");
                para.put("deptId", UserUtil.getCruDeptId());
                para.put("author",UserUtil.getCruUserId());
                Set<UserParam> keySet = userInfo.keySet();
                //是否包含userId
                if (keySet.contains(UserParam.USER_ID)){
                    JSONObject user = new JSONObject();
                    for (UserParam param:keySet) {
                        String value = userInfo.get(param);
                        String name = param.getName();
                        if (name.equals(UserParam.IMAGE.getName())){
                            //根据附件ID，查询附件内容（base64编码）
                            String imgStr = affixService.getAffixContent(value);
                            para.put(UserParam.IMAGE.getName(),imgStr);
                        }else{
                            user.put(name,value);
                        }
                    }
                    para.put("userinfo",user);
                    json = userService.saveUser(para.toString());
                }else{
                    json.put("msg","缺少参数userId");
                }
            }else{
                json.put("msg","用户信息为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag","-1");
            json.put("msg","更新用户信息异常");
        }
        return json;
    }

    /**
     * TODO 迁移用户
     * @author 李利广
     * @Date 2019年03月05日 17:18:26
     * @param userId 用户ID
     * @param deptId 用户原deptId
     * @param moveDeptId 要迁移到的deptId
     * @return net.sf.json.JSONObject
     */
    @Override
    public JSONObject removeUser(String userId,String deptId,String moveDeptId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
            if(StringUtils.isNotBlank(moveDeptId)){
                if (StringUtils.isBlank(userId)){
                    userId = UserUtil.getCruUserId();
                }
                if (StringUtils.isBlank(deptId)){
                    deptId = UserUtil.getCruDeptId();
                }
                JSONObject userJSON = new JSONObject();
                userJSON.put("type","1");
                userJSON.put("deptId",deptId);
                userJSON.put("author",UserUtil.getCruUserId());
                userJSON.put("userId",userId);
                userJSON.put("movedeptId",moveDeptId);
                json = userService.saveUserRemove(userJSON.toString());
            }else{
                json.put("msg","迁移部门不能为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag","-1");
            json.put("msg","更新用户信息异常");
        }
        return json;
    }

    /**
     * TODO 设置一人多岗
     * @author 李利广
     * @Date 2019年03月05日 17:30:54
     * @param userId 用户ID
     * @param moveDeptId 要设置岗位的deptId
     * @return net.sf.json.JSONObject
     */
    @Override
    public JSONObject setUserMultipleDept(String userId,String moveDeptId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
            if (StringUtils.isNotBlank(moveDeptId)){
                JSONObject userJSON = new JSONObject();
                if (StringUtils.isBlank(userId)){
                    userId = UserUtil.getCruUserId();
                }
                userJSON.put("type","1");
                userJSON.put("movedeptId",moveDeptId);
                userJSON.put("author",UserUtil.getCruUserId());
                userJSON.put("userId",userId);
                json = userService.saveUserMultiplePosts(userJSON.toString());
            }else{
                json.put("msg","请选择要增加岗位的部门");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag","-1");
            json.put("msg","更新用户信息异常");
        }
        return json;
    }

}
