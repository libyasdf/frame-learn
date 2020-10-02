package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity.MeetingDeptAndServices;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity.MeetingServiceInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.service.MeetingServiceService;
import com.sinosoft.sinoep.uias.util.RecourseUtils;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 会务服务项目
 * TODO 
 * @author 冯超
 * @Date 2018年7月19日 下午6:17:17
 */
 
@Controller
@RequestMapping("zhbg/hygl/basicSet/meetingService")
public class MeetingServiceController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MeetingServiceService meetingServiceService;
	/**
	 * 
	 * TODO 修改会务服务项目
	 * @author 冯超
	 * @Date 2018年5月7日 下午1:59:55
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "修改会务服务项目信息")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		MeetingServiceInfo meetingServiceInfo = null;
		try {
			meetingServiceInfo = meetingServiceService.getById(id);
			json.put("flag", "1");
			json.put("data", meetingServiceInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 保存会务服务项目
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:14:18
	 * @param application
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存会务服务项目信息")
	@ResponseBody
	@RequestMapping("saveForm")
	public JSONObject saveForm(@RequestBody MeetingDeptAndServices meetingDeptAndServices){
		JSONObject json = new JSONObject();
		try {
			meetingDeptAndServices = meetingServiceService.saveForm(meetingDeptAndServices);
			json.put("flag", "1");
			json.put("data", meetingDeptAndServices);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 获取会务服务单位列表(分页)
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:33:40
	 * @param pageImpl
	 * @param name
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询会务服务项目信息列表")
	@ResponseBody
	@RequestMapping("getPageList")
	public PageImpl getPageList(PageImpl pageImpl,String name){
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = meetingServiceService.getPageList(pageable,pageImpl,name);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 获取会务服务项目列表不分页
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:27:00
	 * @param application
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询会务服务项目信息列表")
	@RequestMapping("getList")
	@ResponseBody
	public JSONObject getList(MeetingServiceInfo meetingServiceInfo) {
		JSONObject json = new JSONObject();
		try {
			List<MeetingServiceInfo> list = meetingServiceService.getList(meetingServiceInfo);
			json.put("flag", "1");
			JSONObject data = new JSONObject();
			data.put("total", list.size());
			JSONArray array = new JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
					"updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
			array = JSONArray.fromObject(list, jsonConfig);
			data.put("rows", array);
			json.put("data", list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * 获取所有的会务服务（会议申请加载会务服务项）
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月22日 上午10:11:02
	 * @param meetingServiceInfo
	 * @return
	 */
	@RequestMapping("getAllList")
	@ResponseBody
	public JSONObject getAllList(MeetingServiceInfo meetingServiceInfo) {
		JSONObject json = new JSONObject();
		try {
			List<MeetingServiceInfo> list = meetingServiceService.getAllList(meetingServiceInfo);
			json.put("flag", "1");
			json.put("data", list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * 根据会务负责人获其会务服务（查询加载会务服务项）
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月22日 上午10:11:02
	 * @param meetingServiceInfo
	 * @return
	 */
	@RequestMapping("getListByUser")
	@ResponseBody
	public JSONObject getListByUser(MeetingServiceInfo meetingServiceInfo) {
		JSONObject json = new JSONObject();
		try {
			List<MeetingServiceInfo> list = meetingServiceService.getListByUser(meetingServiceInfo);
			json.put("flag", "1");
			json.put("data", list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * 根据用户id获取业务角色信息 判断是不是会务服务人员
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月20日 下午3:48:14
	 * @param userId
	 * @return
	 * flag  0:不是  1：是
	 */
	@ResponseBody
	@RequestMapping("getRoleNoByUserId")
	public String getRoleNoByUserId(String responsibleUserIdd,String responsibleDeptId){
		String roleNo=RecourseUtils.getFlowRoleNoByDept(responsibleUserIdd,responsibleDeptId);
		String flag="0";
		if(roleNo.indexOf(EmConstants.HWFW_ROLE_NO)!=-1){
			flag="1";
		}
		return flag;
	}
	
	/**
	 * 删除会务服务单位
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:54:10
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "查询会务服务项目信息列表")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = meetingServiceService.delete(id);
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
	 * 批量删除
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月2日 上午10:25:28
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "查询会务服务项目信息列表")
	@ResponseBody
	@RequestMapping("deleteIds")
	public JSONObject deleteIds( String ids){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = meetingServiceService.deleteIds(ids);
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
	 * 
	 * TODO 会务服务单位排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:16:19
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sort", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject sort(String[] id){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			meetingServiceService.sort(id);
			json.put("flag","1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	
}
