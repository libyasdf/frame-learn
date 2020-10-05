/**
 * Copyright 2017 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.user.controller;

import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.api.dept.vo.MessageDept;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.CookieUtil;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.system.online.common.OnlineUtils;
import com.sinosoft.sinoep.modules.system.online.entity.OnlineUserInfo;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 张战国
 * @since 2017年2月23日
 */
@Controller
@RequestMapping("user")
public class UserController {

    private Log log = LogFactory.getLog(UserController.class);

    @Resource
    UserInfoService userInfoService;

    @ResponseBody
    @RequestMapping("/view")
    public UserInfo view(String type) {
        String userId = UserUtil.getCruUserId();
        UserInfo userInfo = userInfoService.getUserBase(userId, type);
        return userInfo;
    }

    @ResponseBody
    @RequestMapping("/info")
    public Map<String, Object> userinfo() {
        Map<String, Object> map = new HashMap<>(2);
        String deptId = userInfoService.getDeptId(UserUtil.getCruUserId());
        map.put("userId", UserUtil.getCruUserId());
        map.put("deptId", deptId);
        return map;
    }

    /**
     * 
     * <B>方法名称：确定用户的部门个数，如果只有一个的话，直接将唯一的部门信息存储到cookie中</B><BR>
     * <B>概要说明：</B><BR>
     */
    @ResponseBody
    @RequestMapping("/checkDeptsSize")
    public JSONObject checkDeptsSize() {
        JSONObject json = new JSONObject();
        json.put("flag", false);
    	String userId = UserUtil.getCruUserId();
        try {
            MessageDept depts = userInfoService.getAllDeptByUserId(userId);
            if (depts.getDeptInfo().size() > 1 && StringUtils.isBlank(UserUtil.getCruDeptId())) {
            	json.put("flag", true);
            }
        } catch (Exception e) {
            log.error("确定用户的部门个数信息异常：" + e);
        }
        return json;
    }

    /**
     * 
     * <B>方法名称：存储cookie</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param cookieName
     * @param subcode
     * @param resp
     * @throws IOException
     */
    private void setCookie(HttpServletResponse resp, String cookieName, String subcode) throws IOException {
        CookieUtil.setCookie(resp, cookieName, subcode, 60 * 60);
    }

    /**
     * 
     * <B>方法名称：获取当前用户的所有部门</B><BR>
     * <B>概要说明：</B><BR>
     * 
     */
    @ResponseBody
    @RequestMapping("/getDepts")
    public JSONObject getDepts() {
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
            String userId = UserUtil.getCruUserId();
            MessageDept deptInfo = userInfoService.getAllDeptByUserId(userId);
            List<Dept> deptList = deptInfo.getDeptInfo();
            for (Dept dept:deptList){
                String deptName = userInfoService.getDeptName(dept.getDeptid());
                String[] deptNames = deptName.split("/");
                List<String> temp = new ArrayList<>();
                for(int i = deptNames.length-1 ;i >= 0;i--){
                    temp.add(deptNames[i]);
                }
                String deptAllName = CommonUtils.arrayToString(temp,"/");
                dept.setDeptname(deptAllName);
            }
            json.put("flag","1");
            json.put("depts", deptList);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("获取当前用户的所有部门信息异常：" + e);
        }
        return json;
    }

    /**
     * <B>方法名称：</B><BR>
     * <B>概要说明：cookie里面填充userid和sysid</B><BR>
     *
     */
    @ResponseBody
    @RequestMapping("/userRolesInfo")
    public JSONObject getNowUserInfo(String infoName) {
    	JSONObject json = new JSONObject();
    	String info = "";
    	if (UserConfigConsts.COOKIE_ROLE_NO.equals(infoName)) {
			info = UserUtil.getCruUserRoleNo();
		}else if (UserConfigConsts.COOKIE_SYS_ROLE_IDS.equals(infoName)) {
			info = UserUtil.getCruUserSysRoleIds();
		}else if (UserConfigConsts.COOKIE_SYS_ROLE_NOS.equals(infoName)) {
			info = UserUtil.getCruUserSysRoleNos();
		}else if(UserConfigConsts.COOKIE_USER_ID.equals(infoName)){
    	    info = UserUtil.getCruUserId();
        }else if(UserConfigConsts.COOKIE_USER_NAME.equals(infoName)){
            info = UserUtil.getCruUserName();
        }else if(UserConfigConsts.COOKIE_DEPT_ID.equals(infoName)){
            info = UserUtil.getCruDeptId();
        }else if(UserConfigConsts.COOKIE_DEPT_NAME.equals(infoName)){
            info = UserUtil.getCruDeptName();
        }else if(UserConfigConsts.COOKIE_DEPT_NM.equals(infoName)){
            info = userInfoService.getDeptFullName(UserUtil.getCruDeptId());
        }else if(UserConfigConsts.COOKIE_ORGID.equals(infoName)){
            info = UserUtil.getCruOrgId();
        }else if(UserConfigConsts.COOKIE_ORG_NAME.equals(infoName)){
            info = UserUtil.getCruOrgName();
        }else if(UserConfigConsts.COOKIE_CHU_ID.equals(infoName)){
            info = UserUtil.getCruChushiId();
        }else if(UserConfigConsts.COOKIE_CHU_NAME.equals(infoName)){
            info = UserUtil.getCruChushiName();
        }else if(UserConfigConsts.COOKIE_JU_ID.equals(infoName)){
            info = UserUtil.getCruJuId();
        }else if(UserConfigConsts.COOKIE_JU_NAME.equals(infoName)){
            info = UserUtil.getCruJuName();
        }else if(UserConfigConsts.COOKIE_SYSID.equals(infoName)){
            info = ConfigConsts.SYSTEM_ID;
        }
    	json.put("info", info);
        return json;
    }
    
    /**
     * <B>方法名称：</B><BR>
     * <B>概要说明：cookie里面填充用户信息</B><BR>
     * 
     * @param response
     */
    @ResponseBody
    @RequestMapping("/setUserInfoToCookie")
    public JSONObject setUserInfoToCookie(HttpServletResponse response) {
    	JSONObject json = new JSONObject();
    	json.put("flag", false);
    	try {
			String cruChuShiId = UserUtil.getCruChushiId();
			String cruChuShiName = UserUtil.getCruChushiName();
			String cruJuId = UserUtil.getCruJuId();
			String cruJuName = UserUtil.getCruJuName();
			String orgName = UserUtil.getCruOrgName();
			String deptName = UserUtil.getCruDeptName();
			String deptId = UserUtil.getCruDeptId();
			String deptNm = userInfoService.getDeptFullName(deptId);
			String userName = UserUtil.getCruUserName();
			setCookie(response, "sysid", ConfigConsts.SYSTEM_ID);
			setCookie(response, "userid", UserUtil.getCruUserId());
            setCookie(response, "orgid", UserUtil.getCruOrgId());
            setCookie(response, "deptid", deptId);
            setCookie(response, "deptname", StringUtils.isBlank(deptName)?null:URLEncoder.encode(deptName, "UTF-8"));
            setCookie(response, "deptnm", StringUtils.isBlank(deptNm)?null:URLEncoder.encode(deptNm, "UTF-8"));
            setCookie(response, "usernm", StringUtils.isBlank(userName)?null:URLEncoder.encode(userName, "UTF-8"));
            setCookie(response, "orgName", StringUtils.isBlank(orgName)?null:URLEncoder.encode(orgName, "UTF-8"));
			setCookie(response, "chuShiId", cruChuShiId);
			setCookie(response, "chuShiName", StringUtils.isBlank(cruChuShiName)?null:URLEncoder.encode(cruChuShiName, "UTF-8"));
			setCookie(response, "juId", cruJuId);
			setCookie(response, "juName", StringUtils.isBlank(cruJuName)?null:URLEncoder.encode(cruJuName, "UTF-8"));
			json.put("flag", true);
		} catch (Exception e) {
			log.error("将人员信息存到COOKIE中异常：" + e);
		}
    	return json;
    }
    
    /**
     * 一人多岗存储用户的session信息（所在部门、二级局、处室信息）
     * @param request
     * @param deptId
     * @return
     */
    @ResponseBody
    @RequestMapping("/setSession")
    public JSONObject setSession(HttpServletRequest request,String deptId) {
    	 JSONObject json = new JSONObject();
    	try {
			UserUtil.setSession(request,deptId);
			json.put("result", "success");
		} catch (Exception e) {
			json.put("result", "error");
			e.printStackTrace();
		}
        return json;
    }
    
    /**
     * TODO 获取人员头像
     * @author 李利广
     * @Date 2018年6月21日 下午5:34:29
     * @param request
     * @param userId
     * @return
     */
    @SuppressWarnings("deprecation")
	@ResponseBody
    @RequestMapping("/getUserImg")
    public JSONObject getUserImgContent(HttpServletRequest request,String userId){
    	JSONObject res = new JSONObject();
    	res.put("flag", false);
    	String path = "";
    	String imgStr = "";
    	if(StringUtils.isBlank(userId)){
    		userId = UserUtil.getCruUserId();
    	}
    	//获取图片
    	String jsonStr = HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/userEx/getImgContentUserid",
				"userid=" + userId);
    	JSONObject json = JSONObject.fromObject(jsonStr);
    	if ("1".equals(json.getString("status"))) {
    		imgStr = json.getString("img");
		}
    	//对字节数组字符串进行Base64解码并生成图片 
        if (!StringUtils.isBlank(imgStr)){
        	BASE64Decoder decoder = new BASE64Decoder();
            //新生成的图片
            String imgFilePath = request.getRealPath("/")+"static"+File.separator+"images"+File.separator+"userImg";
            File file = new File(imgFilePath);
            if (!file.exists()) {
    			file.mkdirs();
    		}
    		byte[] byteBase=Base64.decodeBase64(imgStr);
            StringBuilder btbuffer=new StringBuilder();
            for(int i=0;i<byteBase.length&&i<50;i++){
                btbuffer.append(Integer.toHexString(((int)byteBase[i])& 0xFF));
            }
            String byteStr=btbuffer.toString();
            String strType=byteStr.substring(0,6>imgStr.length()?imgStr.length():6);
            String strT=".png";
            if("ffd8ff".equals(strType)){
                strT=".jpg";
            }
            try(OutputStream out = new FileOutputStream(imgFilePath + File.separator + userId + strT)){
                //Base64解码 
                byte[] b = decoder.decodeBuffer(imgStr);
                for(int i=0;i<b.length;++i){
                    //调整异常数据
                    if(b[i]<0){
                        b[i]+=256;
                    }
                }
                //生成jpeg图片
                out.write(b);
                out.flush();
                res.put("flag", true);
                path = "/static/images/userImg/" + userId + strT;
            }catch (Exception e){
            	e.printStackTrace();
    			log.error(e.getMessage(),e);
            }
        }
        res.put("path",path);
        return res;
    }
    
	@ResponseBody
    @RequestMapping("/getUserSize")
    public JSONObject getUserSize(HttpServletRequest request){
    	JSONObject res = new JSONObject();
    	List<OnlineUserInfo> users = new ArrayList<>();
    	res.put("flag", "0");
		try {
            HttpSession session = request.getSession();
            ServletContext application = session.getServletContext();
			users = OnlineUtils.getUserSize(application);
			res.put("flag", "1");
			res.put("data", users);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		res.put("userSize", users.size());
    	return res;
    }
    
    /**
     * 去除session集合中相同用户的session
     * TODO 
     * @author 李利广
     * @Date 2018年10月10日 下午3:18:35
     * @param sessions
     * @return
     */
    private HashSet<HttpSession> clearSessions(HashSet<HttpSession> sessions){
    	HashSet<HttpSession> sessionList = new HashSet<>();
        //用户ID集合，用来存放session中获取到的不重复的userid
    	List<String> userIds = new ArrayList<>();
    	for (HttpSession session : sessions) {
			String userId = (String)session.getAttribute(UserConfigConsts.USER_ID);
			if (!userIds.contains(userId)) {
				/**
				 * 如果集合中不存在该userid，将userid放入集合中,并且将该session放入session集合中
				 * 如果集合中已经存在该userid，不做任何处理
				 */
				userIds.add(userId);
				sessionList.add(session);
			}
		}
    	return sessionList;
    }
    
    /**
     * TODO 查询人员职务职级、涉密等级下拉框
     * @author 李利广
     * @Date 2018年8月14日 下午7:02:02
     * @param type	1：职务权限；2：职级；3：涉密等级；4：部门类型；5：职务名称；
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserLevel")
    public JSONObject getUserLevel(String type){
    	JSONObject data = new JSONObject();
    	data.put("flag", "0");
		try {
			UserInfoService userInfoService = (UserInfoService)SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
//			String sql = "select * from sys_user_position t where t.occupation_type = '" + type + "' order by t.order_no";
			String sql = "select t.id,t.name occupation_name,t.value occupation_level,t.parentId from sys_dictionary t where t.remarks = '" + type + "' and t.status = '1' order by t.orderno";
			data = userInfoService.getDateBySql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		log.info(data);
    	return data;
    }

    /**
     * 根据部门id,职务或职级获取人员信息
     * @param deptId
     * @param zw
     * @param zj
     * @return   {'id':'id1','name':'name1','type':'1','asyn':false,'level':3,'deptId':441,'zw':'01','url':'/user/getUserInfoByDeptIdAndZwZj'}
     */
    @RequestMapping("getUserInfoByDeptIdAndZwZj")
    @ResponseBody
    public JSONArray getUserInfoByDeptIdAndZwZj(String deptId,String zw,String zj,String zwjb){
        JSONObject json = userInfoService.getUserInfoByDeptIdAndZwZj(deptId,zw,zj,zwjb);
        JSONArray array = json.getJSONArray("data");
        for(int n = 0;n<array.size();n++){
            JSONObject object = array.getJSONObject(n);
            if("dept".equals(object.getString("type"))) {
                object.put("name", object.get("deptname"));
                object.put("uuid", object.get("deptid"));
                object.put("uupid", object.get("super_id"));
            }else{
                object.put("uuid", object.get("userid"));
                object.put("uupid", object.get("deptid"));
                object.put("name", object.get("username"));
            }
        }
        return array;
    }
    
    /**
     * TODO 清除浏览器所有cookie
     * @author 李利广
     * @Date 2018年9月11日 下午9:41:40
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("clearAllCookie")
    @ResponseBody
    public JSONObject clearAllCookie(HttpServletRequest request,HttpServletResponse response){
    	JSONObject json = new JSONObject();
    	json.put("flag", false);
    	try {
			CookieUtil.clearCookie(request, response);
			json.put("flag", true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
        return json;
    }

    @RequestMapping(value = "getUserTreeOnlySelectByUserNameFull",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getUserTreeOnlySelectByUserNameFull(String userNamefull){
        JSONObject json = userInfoService.getUserTreeOnlySelectByUserNameFull(userNamefull);
        JSONArray array = json.getJSONArray("data");
        for(int n = 0;n<array.size();n++){
            JSONObject object = array.getJSONObject(n);
            if("dept".equals(object.getString("type"))) {
                object.put("uuid", object.get("deptid"));
                object.put("uupid", object.get("super_id"));
                object.put("name", object.get("deptname"));
            }else{
                object.put("uuid", object.get("userid"));
                object.put("uupid", object.get("deptid"));
                object.put("name", object.get("usernamefull"));
            }
        }
        return array.toString();
    }

    /**
     * 根据用户id和部门id获取该用户拥有的业务角色编号信息（处理一人多岗）
     * @param userId
     * @param deptId
     * @return
     */
    /*@RequestMapping("getUserRolesNoByDeptId")
    @ResponseBody
    public JSONObject getUserRolesNoByDeptId(String userId,String deptId){
        return userInfoService.getUserRolesNoByDeptId(userId,deptId);
    }*/

    /**
     * 获取群组树
     * @param subId 系统id 如:97206,85585
     * @return
     */
    @RequestMapping("getGroupTree")
    @ResponseBody
    public String getGroupTree(String subId){
        JSONObject json = userInfoService.getGroup(subId);
        JSONArray array = json.getJSONArray("data");
        return array.toString();
    }

    /**
     * 获取群组下用户
     * @param groupId 群组id
     * @return
     */
    @RequestMapping("getGroupUser")
    @ResponseBody
    public JSONObject getGroupUser(String groupId){
        return userInfoService.getGroupUser(groupId);
    }

}
