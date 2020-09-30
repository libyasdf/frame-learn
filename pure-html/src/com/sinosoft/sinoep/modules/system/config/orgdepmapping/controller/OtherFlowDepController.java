package com.sinosoft.sinoep.modules.system.config.orgdepmapping.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.modules.mypage.worklog.entity.WorkLog;
import com.sinosoft.sinoep.modules.system.config.orgdepmapping.entity.OtherFlowDep;
import com.sinosoft.sinoep.modules.system.config.orgdepmapping.services.OtherFlowDepService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;



@Controller
@RequestMapping("/system/config/orgdepmapping")
public class OtherFlowDepController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	OtherFlowDepService service;
	
	/**
	 * 获取其他部门的列表数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午2:02:53
	 * @param orgId
	 * @param departmentName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getList")
	public JSONObject getList(String orgId,String departmentName,String sortName,String sortOrder){
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<OtherFlowDep>list=new ArrayList<OtherFlowDep>();
		try {
			list=service.getList(orgId,departmentName,sortName,sortOrder);
			JSONObject data = new JSONObject();
			data.put("total",list.size());
			
			JSONArray array = new JSONArray();
			array = JSONArray.fromObject(list);
			
			data.put("rows", array);
			json.put("data",data);
		} catch (Exception e) {
			json.put("flag", "0");
		}
		return json;
	}
	
	
	/**
	 * 保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午2:03:26
	 * @param info
	 * @param flag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveForm")
	public OtherFlowDep saveForm(OtherFlowDep info){
		try {
			info = service.saveForm(info);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return info;
	}
	
	
	/**
	 * TODO 打开只读、修改页面时，查询数据进行渲染
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		OtherFlowDep otherDep = null;
		try {
			otherDep = service.getById(id);
			json.put("flag", "1");
			json.put("data", otherDep);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = service.delete(id);
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
	 * 排序
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午5:21:17
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sort", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject sort(String[] id){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			service.sort(id);
			json.put("flag","1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 绑定其他部门的id
	 */
	@RequestMapping(value = "binding", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject binding(String id,String glDeptId,String glDeptName){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = service.binding(id,glDeptId,glDeptName);
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
	 * 绑定其他部门的id
	 */
	@RequestMapping(value = "unbinding", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject unbinding(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = service.unbinding(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
