package com.sinosoft.sinoep.user.util;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.user.entity.UserParam;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.servlet.HttpContext;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.JSONUtils;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.uias.util.RecourseUtils;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;
import com.sinosoft.sinoep.user.entity.SessionUser;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.service.UserInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TODO 
 * @author 周俊林
 * @Date 2018年3月13日 下午3:39:51
 */
public class UserUtil {
	
	private static Log log = LogFactory.getLog(UserUtil.class);
	
	/**
	 * TODO 获取当前用户ID
	 * @return 
	 */
	public static String getCruUserId() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_ID);
	}
	
	/**
	 * TODO 获取当前用户NAME
	 * @return 
	 */
	public static String getCruUserName() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_NAME);
	}
	
	/**
	 * TODO 获取当前用户部门ID
	 * @return 
	 */
	public static String getCruDeptId() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_DEPT_ID);
	}
	
	/**
	 * TODO 获取当前用户部门NAME
	 * @return 
	 */
	public static String getCruDeptName() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_DEPT_NAME);
	}
	
	/**
	 * TODO 获取当前用户处室ID
	 * @return 
	 */
	public static String getCruChushiId() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_CHUSHI_ID);
	}
	
	/**
	 * TODO 获取当前用户处室Name
	 * @return 
	 */
	public static String getCruChushiName() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_CHUSHI_NAME);
	}
	
	/**
	 * TODO 获取当前用户二级局ID
	 * @return 
	 */
	public static String getCruJuId() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_JU_ID);
	}
	
	/**
	 * TODO 获取当前用户二级局name
	 * @return 
	 */
	public static String getCruJuName() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_JU_NAME);
	}

	/**
	 * TODO 获取当前用户局ID
	 * @return
	 */
	public static String getCruOrgId() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_ORG_ID);
	}

	/**
	 * TODO 获取当前用户局name
	 * @return
	 */
	public static String getCruOrgName() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_ORG_NAME);
	}

	/**
	 * TODO 获取当前用户具有的系统角色ids
	 * @return 
	 */
	public static String getCruUserSysRoleIds() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_SYSROLE_ID);
	}

	/**
	 * TODO 获取当前用户具有的系统角色编号
	 * @return
	 */
	public static String getCruUserSysRoleNos() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_SYSROLE_NO);
	}
	
	/**
	 * TODO 获取当前用户具有的业务角色ids
	 * @return 
	 */
	public static String getCruUserRole() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.USER_ROLE);
	}
	
	/**
	 * TODO 获取当前用户具有的业务角色编号
	 * @return 
	 */
	public static String getCruUserRoleNo() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.ROLE_NO);
	}
	
	/**
	 * TODO 获取当前用户父部门ID
	 * @return 
	 */
	public static String getSuperDeptId() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.SUPER_DEPT_ID);
	}
	
	/**
	 * TODO 获取当前用户父部门NAME
	 * @return 
	 */
	public static String getSuperDeptName() {
		return (String)HttpContext.getSession().getAttribute(UserConfigConsts.SUPER_DEPT_NAME);
	}

	/**
	 * 获取SessionUser对象
	 * @return
	 */
	public static SessionUser getSessionUser() {
		SessionUser su = new SessionUser();
		su.setUserId(getCruUserId());
		su.setUserName(getCruUserName());
		su.setUserDeptId(getCruDeptId());
		su.setUserDeptName(getCruDeptName());
		su.setSuperDeptId(getSuperDeptId());
		su.setSuperDeptName(getSuperDeptName());
		su.setUserChushiId(getCruChushiId());
		su.setUserChushiName(getCruChushiName());
		su.setUserOrgId(getCruOrgId());
		su.setUserOrgName(getCruOrgName());
		su.setUserJuId(getCruJuId());
		su.setUserJuName(getCruJuName());
		su.setUserSysroleId(getCruUserSysRoleIds());
		su.setUserRole(getCruUserRole());
		su.setUserRoleNo(getCruUserRoleNo());
		su.setUserSysroleNo(getCruUserSysRoleNos());
		return su;
	}

	/**
	 * 获取SessionUser并将其转换为json
	 * @return
	 */
	public static JSONObject getSessionUserJson() {
		return JSONObject.fromObject(getSessionUser());
	}
	
	/**
	 * TODO 根据用户ID获取用户详细信息
	 * @author 李利广
	 * @Date 2018年5月22日 下午7:32:46
	 * @param userIds 多个以逗号分隔
	 * @return Map<String, SysFlowUserVo>
	 */
	public static Map<String, SysFlowUserVo> getUserVo(String userIds){
		String userList = "";
		UserInfoService userInfoService = (UserInfoService)SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
		Map<String, SysFlowUserVo> map = new HashMap<>();
		try {
			if(StringUtils.isNotBlank(userIds)){
				//处理userId，防止参数过长导致异常
				List<String[]> userIdList = subUser(userIds);
				for(int n = 0;n < userIdList.size(); n++){
					String[] ids = userIdList.get(n);
					String userIdStr = StringUtils.join(ids,",");
					userList = HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/user/getUserInfoAllById","uid="+userIdStr);
					if (StringUtils.isNotBlank(userList)) {
						JSONArray list = JSONArray.fromObject(userList);
						if (map.isEmpty()){
							map = JSONUtils.array2Obj(list,SysFlowUserVo.class);
						}else{
							Map<String, SysFlowUserVo> map1 = JSONUtils.array2Obj(list,SysFlowUserVo.class);
							map.putAll(map1);
						}
						for(int i = 0; i < list.size(); i++) {
							JSONObject json = list.getJSONObject(i);
							SysFlowUserVo userVo = map.get(json.getString("userid"));
							/**
							 * 设置用户的部门信息
							 */
							setDeptInfo(userVo,userInfoService);
							/**
							 * 设置用户的角色信息（需要再部门信息之后）
							 */
							//系统角色ID
							userVo.setUserSysroleId(RecourseUtils.getSysRoleIdByDept(userVo.getUserId(),userVo.getUserDeptId()));
							//业务角色ID
							userVo.setUserRole(RecourseUtils.getFlowRoleIdByDept(userVo.getUserId(),userVo.getUserDeptId()));
							//系统角色编号
							userVo.setUserSysroleNo(RecourseUtils.getSysRoleNosByDept(userVo.getUserId(),userVo.getUserDeptId()));
							//业务角色编号
							userVo.setUserRoleNO(RecourseUtils.getFlowRoleNoByDept(userVo.getUserId(),userVo.getUserDeptId()));
							/**
							 * 设置用户的职务信息
							 */
							setPositionInfo(userVo,userInfoService);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * TODO 处理过长的userId
	 * @author 李利广
	 * @Date 2019年03月26日 22:03:11
	 * @param userIds
	 * @return java.util.List<java.lang.String[]>
	 */
	private static List<String[]> subUser(String userIds){
		userIds = userIds.replaceAll(",", ";");
		List<String[]> userIdList = new ArrayList<>();
		String[] userIdArray = userIds.split(";");
		List<String> userList = Arrays.asList(userIdArray);
		while (userList.size() > 200){
			List<String> userList1 = new ArrayList<>();
			userList1 = userList.subList(0,200);
			String[] array = new String[200];
			userList1.toArray(array);
			userIdList.add(array);
			userList = userList.subList(200,userList.size());
		}
		String[] array = new String[userList.size()];
		userList.toArray(array);
		userIdList.add(array);
		return userIdList;
	}

	/**
	 * TODO 设置用户的部门信息
	 * @author 李利广
	 * @Date 2019年03月19日 20:30:50
	 * @param userVo
	 * @param userInfoService
	 * @return void
	 */
	private static void setDeptInfo(SysFlowUserVo userVo,UserInfoService userInfoService){
		String deptId = userInfoService.getDeptId(userVo.getUserId());
		//存放部门ID串的集合
		List<String> userDeptId = new ArrayList<>();
		//存放部门名称串的集合
		List<String> userDeptName = new ArrayList<>();
		//存放父部门ID串的集合
		List<String> superDeptId = new ArrayList<>();
		//存放父部门名称串的集合
		List<String> superDeptName = new ArrayList<>();
		//存放处室ID串的集合
		List<String> userChushiId = new ArrayList<>();
		//存放处室名称串的集合
		List<String> userChushiName = new ArrayList<>();
		//存放局ID串的集合
		List<String> userJuId = new ArrayList<>();
		//存放局名称串的集合
		List<String> userJuName = new ArrayList<>();
		String[] deptIdArray = deptId.split(",");
		for(int j = 0; j< deptIdArray.length;j++) {
			List<Dept> deptList = userInfoService.getAllSuperDeptById(deptIdArray[j]);
			if(deptList.size() > 0) {
				if(!userDeptId.contains(deptList.get(0).getDeptid())){
					userDeptId.add(deptList.get(0).getDeptid());
				}
				if(!userDeptName.contains(deptList.get(0).getDeptname())) {
					userDeptName.add(deptList.get(0).getDeptname());
				}
			}
			if(deptList.size() > 1) {
				if(!superDeptId.contains(deptList.get(1).getDeptid())) {
					superDeptId.add(deptList.get(1).getDeptid());
				}
				if(!superDeptName.contains(deptList.get(1).getDeptname())) {
					superDeptName.add(deptList.get(1).getDeptname());
				}
			}
			for (int k = 0; k < deptList.size(); k++) {
				Dept dept = deptList.get(k);
				//二级局
				if (dept.getTreeId().length() == 8) {
					if(!userJuId.contains(dept.getDeptid())) {
						userJuId.add(dept.getDeptid());
					}
					if(!userJuName.contains(dept.getDeptname())) {
						userJuName.add(dept.getDeptname());
					}
				} else if (dept.getTreeId().length() == 12) {
					//处室
					if(!userChushiId.contains(dept.getDeptid())) {
						userChushiId.add(dept.getDeptid());
					}
					if(!userChushiName.contains(dept.getDeptname())) {
						userChushiName.add(dept.getDeptname());
					}
				}
			}
		}
		//所在部门ID
		if(userDeptId.size() > 0) {
			userVo.setUserDeptId(CommonUtils.arrayToString(userDeptId));
		}
		//所在部门name
		if(userDeptName.size() > 0) {
			userVo.setUserDeptName(CommonUtils.arrayToString(userDeptName));
		}
		//父部门ID
		if(superDeptId.size() > 0) {
			userVo.setSuperDeptId(CommonUtils.arrayToString(superDeptId));
		}
		//父部门name
		if(superDeptName.size() > 0) {
			userVo.setSuperDeptName(CommonUtils.arrayToString(superDeptName));
		}
		if(userChushiId.size() > 0) {
			userVo.setUserChushiId(CommonUtils.arrayToString(userChushiId));
		}
		if(userChushiName.size() > 0) {
			userVo.setUserChushiName(CommonUtils.arrayToString(userChushiName));
		}
		if(userJuId.size() > 0) {
			userVo.setUserJuId(CommonUtils.arrayToString(userJuId));
		}
		if(userJuName.size() > 0) {
			userVo.setUserJuName(CommonUtils.arrayToString(userJuName));
		}
	}

	/**
	 * TODO 设置用户职务信息
	 * @author 李利广
	 * @Date 2019年03月19日 20:40:39
	 * @param userVo
	 * @param userInfoService
	 * @return void
	 */
	private static void setPositionInfo(SysFlowUserVo userVo,UserInfoService userInfoService){
		/** 职务权限 名称 */
		if (StringUtils.isNotBlank(userVo.getPositionName())){
			userVo.setPositionNameMn(userInfoService.getNameByCode("1",userVo.getPositionName()));
		}else{
			userVo.setPositionNameMn("");
		}
		/** 职务 名称 */
		if (StringUtils.isNotBlank(userVo.getPositionRealName())){
			userVo.setPositionRealNameMn(userInfoService.getNameByCode("5",userVo.getPositionRealName()));
		}else{
			userVo.setPositionRealNameMn("");
		}
		/** 职级 名称 */
		if (StringUtils.isNotBlank(userVo.getPositionLevel())){
			userVo.setPositionLevelMn(userInfoService.getNameByCode("2",userVo.getPositionLevel()));
		}else{
			userVo.setPositionLevelMn("");
		}
	}
	
	/**
     * 把用户的信息存储在session中
     * @param request
     * @param deptId
     * @return
     */
	public static void setSession(HttpServletRequest request,String deptId) {
		log.info("开始设置session中的用户数据! deptId:" + deptId);
		HttpSession session = request.getSession();
		UserInfoService userInfoService = (UserInfoService)SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
		String userId = request.getAttribute("userid").toString();
		UserInfo userInfo = userInfoService.getUserBase(userId, null);
		session.setAttribute(UserConfigConsts.USER_ID, userId);
		session.setAttribute(UserConfigConsts.USER_NAME, userInfo.getUserFullName());
		/**
		 * 如果deptId为空，则判断session中是否已经有deptId了，
		 * 如果有，直接用session中的deptId，如果没有再根据userId获取deptId
		 */
		if(StringUtils.isBlank(deptId)) {
			deptId = userInfoService.getDeptId(userId);
		}
		if(!deptId.contains(",")){
			List<Dept> deptList = userInfoService.getAllSuperDeptById(deptId);
			if (deptList.size() > 0) {
				session.setAttribute(UserConfigConsts.USER_DEPT_ID, deptList.get(0).getDeptid().toString());
				session.setAttribute(UserConfigConsts.USER_DEPT_NAME, deptList.get(0).getDeptname());
			}
			if (deptList.size() > 1) {
				session.setAttribute(UserConfigConsts.SUPER_DEPT_ID, deptList.get(1).getDeptid().toString());
				session.setAttribute(UserConfigConsts.SUPER_DEPT_NAME, deptList.get(1).getDeptname());
			}
			for (int i = 0, length = deptList.size(); i < length; i++) {
				Dept dept = deptList.get(i);
				if (dept.getTreeId().length() == 4) {
					session.setAttribute(UserConfigConsts.USER_ORG_ID, dept.getDeptid().toString());
					session.setAttribute(UserConfigConsts.USER_ORG_NAME, dept.getDeptname());
				} else if (dept.getTreeId().length() == 8) {
					session.setAttribute(UserConfigConsts.USER_JU_ID, dept.getDeptid().toString());
					session.setAttribute(UserConfigConsts.USER_JU_NAME, dept.getDeptname());
				} else if (dept.getTreeId().length() == 12) {
					session.setAttribute(UserConfigConsts.USER_CHUSHI_ID, dept.getDeptid().toString());
					session.setAttribute(UserConfigConsts.USER_CHUSHI_NAME, dept.getDeptname());
				}
			}
			//业务角色ID
			session.setAttribute(UserConfigConsts.USER_ROLE, RecourseUtils.getFlowRoleIdByDept(userId,deptId));
			//业务角色编号
			session.setAttribute(UserConfigConsts.ROLE_NO, RecourseUtils.getFlowRoleNoByDept(userId,deptId));
			//系统角色ID
			session.setAttribute(UserConfigConsts.USER_SYSROLE_ID, RecourseUtils.getSysRoleIdByDept(userId,deptId));
			//系统角色编号
			session.setAttribute(UserConfigConsts.USER_SYSROLE_NO, RecourseUtils.getSysRoleNosByDept(userId,deptId));
		}
		//存放标志位，证明已经在session中存储过值
		session.setAttribute(UserConfigConsts.SSO_FLAG, UserConfigConsts.SSO_FLAG);
		log.info("设置session结束!");
	}
	
	/**
	 * TODO 根据类型查询字典表，获取人员职务、职级、涉密等级
	 * @author 李利广
	 * @Date 2018年7月4日 上午8:58:20
	 * @param type	1：职务权限；2：职级；3：涉密等级；4：部门类型；5：职务名称；
	 * @return
	 */
	public static Map<String, String> getPositionAndMarkByType(String type){
		Map<String, String> map = new HashMap<>();
		UserInfoService userInfoService = (UserInfoService)SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
		String sql = "select t.id,t.name,t.value,t.parentId from sys_dictionary t where t.remarks = '" + type + "' and t.status = '1' order by t.orderno";
		JSONObject data = userInfoService.getDateBySql(sql);
		if (!data.isEmpty() && "1".equals(data.getString("flag"))) {
			JSONArray res = data.getJSONArray("data");
			for (int i=0; i<res.size() ; i++) {
				JSONObject msg = res.getJSONObject(i);
				map.put(msg.getString("value"), msg.getString("name"));
			}
		}
		return map;
	}

	/**
	 * 获取用户所在群组
	 * @param userId
	 * @return
	 */
	public static JSONArray getUserGroup(String userId){
		JSONArray res = new JSONArray();
		UserInfoService userService = (UserInfoService)SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
		StringBuilder sBuilder = new StringBuilder("select t.* from sys_group t,sys_user_grup u where t.grup_id = u.grup_id and t.status = '1'");
		sBuilder.append(" and u.userid = '"+userId+"' and t.sub_id = '"+ConfigConsts.SYSTEM_ID+"'");
		JSONObject json = userService.getDateBySql(sBuilder.toString());
		if (!json.isEmpty() && "1".equals(json.getString("flag"))) {
			res = json.getJSONArray("data");
		}
		return res;
	}

	/**
	 * TODO 更新用户信息
	 * @author 李利广
	 * @Date 2019年03月05日 17:12:56
	 * @param userInfo
	 * @return net.sf.json.JSONObject
	 */
	public static JSONObject updateUserInfo(Map<UserParam,String> userInfo){
		UserInfoService userService = (UserInfoService)SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
		return userService.updateUserInfo(userInfo);
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
	public static JSONObject removeUser(String userId,String deptId,String moveDeptId){
		UserInfoService userService = (UserInfoService)SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
		return userService.removeUser(userId,deptId,moveDeptId);
	}

	/**
	 * TODO 设置一人多岗
	 * @author 李利广
	 * @Date 2019年03月05日 17:30:54
	 * @param userId 用户ID
	 * @param moveDeptId 要设置岗位的deptId
	 * @return net.sf.json.JSONObject
	 */
	public static JSONObject setUserMultipleDept(String userId,String moveDeptId){
		UserInfoService userService = (UserInfoService)SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
		return userService.setUserMultipleDept(userId,moveDeptId);
	}
	
}
