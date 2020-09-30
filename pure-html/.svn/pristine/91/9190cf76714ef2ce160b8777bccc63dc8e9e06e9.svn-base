package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.entity.ServiceDeptInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.service.ServiceDeptService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 会务服务单位
 * TODO 
 * @author 冯超
 * @Date 2018年7月19日 下午6:17:17
 */
 
@Controller
@RequestMapping("zhbg/hygl/basicSet/serviceDept")
public class ServiceDeptController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ServiceDeptService serviceDeptService;
	
	/**
	 * 
	 * TODO 修改会务服务单位
	 * @author 冯超
	 * @Date 2018年5月7日 下午1:59:55
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改会务服务单位信息")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		ServiceDeptInfo serviceDeptInfo = null;
		try {
			serviceDeptInfo = serviceDeptService.getById(id);
			json.put("flag", "1");
			json.put("data", serviceDeptInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 保存会务服务单位
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:14:18
	 * @param application
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存会务服务单位信息")
	@ResponseBody
	@RequestMapping("saveForm")
	public ServiceDeptInfo saveForm(ServiceDeptInfo serviceDeptInfo){
		try {
			serviceDeptInfo = serviceDeptService.saveForm(serviceDeptInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return serviceDeptInfo;
	}
	
	/**
	 * 
	 * TODO 获取会务服务单位列表(分页)
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:33:40
	 * @param pageImpl
	 * @param name
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询会务服务单位信息列表")
	@ResponseBody
	@RequestMapping("getPageList")
	public PageImpl getPageList(PageImpl pageImpl,String name){
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = serviceDeptService.getPageList(pageable,pageImpl,name);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 
	 * TODO 获取会务服务单位列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:27:00
	 * @param application
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询会务服务单位信息列表")
	@RequestMapping("getList")
	@ResponseBody
	public JSONObject getList(ServiceDeptInfo serviceDeptInfo) {
		JSONObject json = new JSONObject();
		try {
			List<ServiceDeptInfo> list = serviceDeptService.getList(serviceDeptInfo);
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
			json.put("data", data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * 
	 * TODO 删除会务服务单位
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:54:10
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.DELETE,opName = "删除会务服务单位信息")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = serviceDeptService.delete(id);
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
			serviceDeptService.sort(id);
			json.put("flag","1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 判断服务单位是否重复
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月3日 下午2:24:44
	 * @param serviceDeptInfo
	 * @return
	 */
	@RequestMapping(value = "check", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject check(ServiceDeptInfo serviceDeptInfo){
		JSONObject json = new JSONObject();
		boolean valid = serviceDeptService.checkSdi(serviceDeptInfo);
		json.put("valid",valid);
		return json;
	}
	
}
