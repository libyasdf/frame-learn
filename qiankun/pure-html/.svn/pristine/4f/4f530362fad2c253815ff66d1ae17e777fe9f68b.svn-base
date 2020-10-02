package com.sinosoft.sinoep.modules.taskplan.controller;

import com.sinosoft.sinoep.api.auth.service.AuthApiService;
import com.sinosoft.sinoep.api.auth.vo.MessageAuthorization;
import com.sinosoft.sinoep.api.auth.vo.MessageFlowRoles;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.common.jedis.services.RedisClusterService;
import com.sinosoft.sinoep.common.util.*;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.taskplan.service.ImportTaskPlanService;
import com.sinosoft.sinoep.uias.model.SysRecourse;
import com.sinosoft.sinoep.uias.service.RecourseDubboServiceImpl;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.entity.UserParam;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan;
import com.sinosoft.sinoep.modules.taskplan.service.TaskPlanService;

import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板Controller层
 * TODO 
 * @author 李利广
 * @Date 2018年3月15日 下午8:28:54
 */
@Controller
@RequestMapping("taskplan")
public class TaskPlanController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskPlanService taskPlanService;
	
	@Autowired
	private com.sinosoft.sinoep.modules.taskplan.services.TaskPlanService taskPlanServices;

	@Autowired
	private ImportTaskPlanService importTaskPlanService;

	@Autowired
	private SysWaitNoflowService waitNoflwService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private RecourseDubboServiceImpl recourseService;

    @Autowired
    private RedisClusterService redisService;

	/**
	 * TODO 原系统DEMO列表查询方法
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:53:12
	 * @param
	 * @param pageNum
	 * @param showCount
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param subflag
	 * @return
	 */

	@LogAnnotation(opType = OpType.QUERY,opName = "查询草稿列表")
	@ResponseBody
	@RequestMapping("getlist1")
	public Page getList(Integer pageNum,Integer showCount,String title,String startTime,String endTime,String subflag){
		return taskPlanService.getPageList(pageNum,showCount,title,startTime,endTime,subflag);
	}
	
	/**
	 * TODO HQL语句查询
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:52:56
	 * @param
	 * @param pageImpl
	 * @param title
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "HQL语句查询")
	@ResponseBody
	@RequestMapping("getlist")
	public PageImpl getList(PageImpl pageImpl,String title,String timeRange,String subflag){
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			if (ConfigConsts.START_FLAG.equals(subflag)) {
				pageImpl = taskPlanServices.getPageListDraft(pageable,pageImpl,title,startDate,endDate,subflag);
			}else{
				pageImpl = taskPlanServices.getPageList(pageable,pageImpl,title,startDate,endDate,subflag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * TODO 保存表单
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:52:40
	 * @param
	 * @param plan
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存表单")
	@ResponseBody
	@RequestMapping("saveForm")
	public JSONObject saveForm(TaskPlan plan){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			plan = taskPlanServices.saveForm(plan);
			json.put("flag", "1");
			json.put("data", plan);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "");
		}
		return json;
	}
	
	/**
	 * TODO 删除数据
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:53:53
	 * @param
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deletePlan")
	public JSONObject deletePlan( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = taskPlanServices.deletePlan(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 打开只读、修改页面时，查询数据进行渲染
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:54:07
	 * @param
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改页面")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		TaskPlan plan = null;
		try {
			plan = taskPlanServices.getById(id);
			json.put("flag", "1");
			json.put("data", plan);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 更新业务表流程状态
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:54:17
	 * @param id
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改流程状态")
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id,String subflag){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			taskPlanServices.updateSubFlag(id, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
        return json;
	}

	/**
	 * 导入excel
	 * @param request
	 * @param file
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "导入excel")
	@RequestMapping(value = "/importInfo",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject importInfo(HttpServletRequest request, @RequestParam("file") MultipartFile file){
		String filePath = request.getSession().getServletContext().getRealPath("/")+"upload"+file.getOriginalFilename();
		String msg = "导入失败！";
		try {
			msg = importTaskPlanService.importInfo(filePath,file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("msg",msg);
		return json;
	}

	/**
	 * 插入不走流程待办
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "插入不走流程待办")
	@RequestMapping("/sendWaitNoflow")
	@ResponseBody
	public JSONObject sendWaitNoflow(){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		if(StringUtils.isNotBlank(UserUtil.getCruDeptId())){
			SysWaitNoflow waitNoflw = new SysWaitNoflow();
			waitNoflw.setReceiveDeptName("资金交易处");//接收人部门
			waitNoflw.setReceiveDeptId("93255");//接收人部门id 必填
			waitNoflw.setReceiveUserId("");//接受人id
			waitNoflw.setReceiveUserName("");//接受人name
			waitNoflw.setRolesNo("56");//接受业务角色
			waitNoflw.setOpName("[不走流程待办 ]");//操作类型
			waitNoflw.setDaibanUrl("/modules/zhbg/dcdb/task/taskEditForm.html?id=4028cf81638ffd3a01638ffe7df10000&resId=");//待办url  必填
			waitNoflw.setTitle("测试111");//待办显示标题
			waitNoflw.setTableId("id");//业务表id
			waitNoflw.setTableName("task_plan");//业务表名
			waitNoflw.setSourceId("");//业务id
			waitNoflw.setAttr1("");//预留字段1
			waitNoflw.setAttr2("");//预留字段2
			waitNoflw.setAttr3("");//预留字段3
			waitNoflwService.saveWaitNoflow(waitNoflw,"测试subject字段值333","测试content字段值333","测试messageURL字段值111");
			json.put("flag","1");
		}
		return json;
	}
	@LogAnnotation(opType = OpType.DELETE,opName = "删除不走流程待办")
	@RequestMapping("/deleteWaitNoflow")
	@ResponseBody
	public JSONObject deleteWaitNoflow(String id){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		int num =waitNoflwService.deleteWaitNoflow("4028cf81638c2c7e01638c3214ec0001","","","","");
		if (num !=0){
			json.put("flag","1");
		}
		return json;
	}

	@LogAnnotation(opType = OpType.QUERY,opName = "应用程序")
	@ResponseBody
	@RequestMapping("/Yycx/exception")
	public String taskPlanYycx(){
		String deptUsers= "";
		String deptId = "93255";
		List<UserInfo> users = userInfoService.getUserByDeptIdAndRolesNo(deptId,"'16'");
		for (UserInfo userEntity : users){
			deptUsers+=deptId+"*"+userEntity.getUid()+",";
		}
		if(StringUtils.isNotBlank(deptUsers)){
			deptUsers = deptUsers.substring(0,deptUsers.length()-1);
		}
		log.info("工作流调用OA时间方法放回结果："+deptUsers);
		return deptUsers;
	}

	/**
	 * TODO 测试获取用户在指定岗位下的权限（业务角色、系统角色、资源）
	 * @author 李利广
	 * @Date 2019年03月14日 20:25:48
	 * @param userId
	 * @param deptId
	 * @return net.sf.json.JSONObject
	 */
	@ResponseBody
	@RequestMapping("test")
	public JSONObject test(String userId,String deptId){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try{
			AuthApiService authApiService = (AuthApiService) SpringBeanUtils.getBean("authApiService");
			JSONObject flowRole = new JSONObject();
			flowRole.put("subId","97206");
			flowRole.put("userId",UserUtil.getCruUserId());
			flowRole.put("deptId",deptId);
			//业务角色
			MessageFlowRoles flowRoles = authApiService.getFlowRoles(flowRole.toString());
			json.put("flowRoles",flowRoles);
			//系统角色
			com.alibaba.fastjson.JSONObject sysRoles = authApiService.getFlowRlsy(flowRole.toString());
			json.put("sysRoles",sysRoles);
			//资源
			MessageAuthorization auth = authApiService.getFlowRlsyResId(flowRole.toString());
			json.put("auth",auth);
			List<UserInfo> list = userInfoService.getUserByDeptIdAndRolesNo("1027814","C001");
			json.put("list",list);

		}catch (Exception e){
			log.error(e.getMessage(),e);
			json.put("flag","-1");
		}
		return json;
	}

	/**
	 * TODO 测试用户在指定岗位下是否拥有资源/资源元素的权限
	 * @author 李利广
	 * @Date 2019年03月14日 20:26:25
	 * @param url
	 * @param ele
	 * @return net.sf.json.JSONObject
	 */
	@ResponseBody
	@RequestMapping("test1")
	public JSONObject test1(String url,String ele,String resourceId){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try{
			AuthApiService authApiService = (AuthApiService) SpringBeanUtils.getBean("authApiService");
			JSONObject auth = new JSONObject();
			auth.put("userId",UserUtil.getCruUserId());
			auth.put("deptId",UserUtil.getCruDeptId());
			auth.put("subId","97206");
			auth.put("Url",url);
			com.alibaba.fastjson.JSONObject result = authApiService.getAuthByUrl(auth.toString());
			json.put("urlAuth",result);


			auth.remove("Url");
			auth.put("elementContent",ele);
			auth.put("resourceId",resourceId);
			com.alibaba.fastjson.JSONObject res = authApiService.getAuthorityByElement(auth.toString());
			json.put("eleAuth",res);

		}catch (Exception e){
			log.error(e.getMessage(),e);
			json.put("flag","-1");
		}
		return json;
	}

	/** 测试修改用户基本信息接口
	 * TODO
	 * @author 李利广
	 * @Date 2019年03月05日 21:00:00
	 * @param name
	 * @param value
	 * @return net.sf.json.JSONObject
	 */
	@ResponseBody
	@RequestMapping("testUser")
	public JSONObject testUser(String name,String value){
		Map<UserParam,String> userInfo = new HashMap<>();
		userInfo.put(UserParam.USER_ID,UserUtil.getCruUserId());
		userInfo.put(UserParam.USER_ID,UserUtil.getCruUserId());
		return UserUtil.updateUserInfo(userInfo);
	}

	/** 测试人员迁移接口
	 * TODO
	 * @author 李利广
	 * @Date 2019年03月05日 21:00:26
	 * @param movedeptId
	 * @return net.sf.json.JSONObject
	 */
	@ResponseBody
	@RequestMapping("saveUserRemove")
	public JSONObject saveUserRemove(String movedeptId){
		return UserUtil.removeUser(UserUtil.getCruUserId(),UserUtil.getCruDeptId(),movedeptId);
	}

	/**
	 * TODO 测试设置一人多岗的接口
	 * @author 李利广
	 * @Date 2019年03月05日 21:00:38
	 * @param movedeptId
	 * @return net.sf.json.JSONObject
	 */
	@ResponseBody
	@RequestMapping("saveUserMultiplePosts")
	public JSONObject saveUserMultiplePosts(String movedeptId){
		List<UserInfo> userList = userInfoService.getUserByDeptId("441");
		userList = userInfoService.getAllUserByDeptId(UserUtil.getCruDeptId());
		StringBuffer sb = new StringBuffer();
		for (UserInfo userInfo:userList) {
			sb.append(userInfo.getUid()).append(",");
		}
		Map<String, SysFlowUserVo> vos = UserUtil.getUserVo(sb.toString());
		return JSONObject.fromObject(vos);
//		return UserUtil.setUserMultipleDept(UserUtil.getCruUserId(),movedeptId);
	}

	/**
	 * TODO 测试根据pid获取子（孙）资源接口
	 * @param userId
	 * @param deptId
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("testResByPid")
	public JSONObject testResByPid(String userId,String deptId,String pid){
		JSONObject json = new JSONObject();
		//获取子级资源
		List<SysRecourse> list = recourseService.getRecoursesByPid(userId,pid,deptId);
		json.put("child",list);
		List<SysRecourse> list1 = recourseService.getResourceByRid(userId,pid,deptId);
		json.put("children",list1);
		return json;
	}

	/**
	 * TODO 测试session中的角色数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("testSession")
	public JSONObject testSession(){
		JSONObject json = new JSONObject();
		json.put("deptId",UserUtil.getCruDeptId());
		json.put("deptName",UserUtil.getCruDeptName());
		json.put("flowRoleId",UserUtil.getCruUserRole());
		json.put("flowRoleNo",UserUtil.getCruUserRoleNo());
		json.put("sysRoleId",UserUtil.getCruUserSysRoleIds());
		json.put("sysRoleNo",UserUtil.getCruUserSysRoleNos());
		json.put("session",UserUtil.getSessionUser());
		return json;
	}

	/**
	 * TODO 测试加密解密
	 * @param encrypt 待加密数据
	 * @param decrypt 待解密密文
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "测试加密解密")
	@ResponseBody
	@RequestMapping("testAes")
	public JSONObject testAes(String encrypt,String decrypt){
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(encrypt)){
			String jiaMi = AesUtil.cbcEncrypt(encrypt);
			json.put("待加密数据",encrypt);
			json.put("加密后",jiaMi);
		}
		if (StringUtils.isNotBlank(decrypt)){
			String jieMi = AesUtil.cbcDecrypt(decrypt);
			json.put("待解密密文：",decrypt);
			json.put("解密后",jieMi);
		}
		log.info("============testAes============");
		JSONObject jsonObject = userInfoService.getAllDeptBySuperId("441");
		return json;
	}

	@RequestMapping("scanRedis")
	public void scanRedis(){
	    int size = redisService.getJedisCluster().getClusterNodes().size();
        Object auditTab = redisService.get("SYS_AUDIT_LOG_SETTING:${" + ConfigConsts.SYSTEM_ID + "}");
        if(auditTab != null) {
            String auditTable = auditTab.toString();
            JSONArray tableArray = JSONArray.fromObject(auditTable);
            if(tableArray.size() > 0) {
                for (int i = 0; i < tableArray.size(); i++) {
                    JSONObject jsonObject = tableArray.getJSONObject(i);
                    if ("tjxm".equals(jsonObject.getString("sourceName").toLowerCase())) {
                        JSONArray tableList = jsonObject.getJSONArray("tables");
                        if (tableList.size() > 0) {
                            for(int j = 0;j < tableList.size(); j++){
                                JSONObject tableInfo = tableList.getJSONObject(j);
                                String tableId = tableInfo.getString("tableId");
                                String key = "SYS_AUDIT_LOG_SETTING:${" + tableId + "}";
                                if(redisService.getJedisCluster().exists(key)){
                                    log.info("主键字段为空的tableInfo：" + tableInfo.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

	public static void main(String[] args){
        Object[] passArray = buildPwd();
        if(passArray.length > 0){
            for(int i=0;i<passArray.length;i++){
                String aes = CryptoUtil.encryptStr(passArray[i].toString());
                System.out.println(passArray[i] + " " + aes);
            }
        }
    }

    private static Object[] buildPwd(){
	    List<String> passArray = new ArrayList<>();
        Integer[] numbs = {0,1,2,3,4,5,6,7,8,9};
        String[] strs = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String[] teShu = {"@","$","%","_","+","&"};

        for(int i = 0;i < 72;i++){
            StringBuilder password = new StringBuilder();
            int hua = (int)(Math.random()*10);
            for(int j = 0;j < 10;j++){
                if(j == hua){
                    password.append("_");
                }else{
                    int arrRand = (int)(Math.floor(Math.random()*3));
                    int numRand = (int)(Math.random()*10);
                    int strRand = (int)(Math.floor(Math.random()*26));
                    int teShuRand = (int)(Math.floor(Math.random()*6));
                    if(arrRand == 0){
                        Integer a = numbs[numRand];
                        password.append(a);
                    }else if(arrRand == 1){
                        if(get(strRand)){
                            String b = strs[strRand];
                            password.append(b);
                        }else{
                            String b = strs[strRand];
                            password.append(b.toUpperCase());
                        }
                    }else if(arrRand == 1){
                        if(get(teShuRand)){
                            String b = teShu[teShuRand];
                            password.append(b);
                        }else{
                            String b = teShu[teShuRand];
                            password.append(b.toUpperCase());
                        }
                    }else{
                        String b = strs[strRand];
                        password.append(b);
                    }
                }
            }
//            System.out.println(password.toString());
            passArray.add(password.toString());
            password.setLength(0);
        }
        return passArray.toArray();
    }

    private static boolean get(int num){
	    if(num != 0){
	        if(num%2 != 1){
                return true;
            }else{
	            return false;
            }
        }else{
            return true;
        }

    }

}
