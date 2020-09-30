package com.sinosoft.sinoep.modules.info.authority.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.info.authority.entity.ColumnModel;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoGenarals;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.modules.info.authority.entity.InfoModel;
import com.sinosoft.sinoep.modules.info.authority.services.InfoFbZwqxService;

import net.sf.json.JSONObject;

import java.util.List;

/**
 * TODO 信息发布职务权限控制类
 * @author 李利广
 * @Date 2018年9月15日 下午5:41:32
 */
@Controller
@RequestMapping("/infoFbZwqx")
public class InfoFbZwqxController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InfoFbZwqxService infoService;

	/**
	 * TODO 保存职务权限
	 * @author 李利广
	 * @Date 2018年9月15日 下午5:42:35
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public JSONObject saveInfo(@RequestBody InfoModel model){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			model = infoService.saveInfoZwqx(model);
			infoService.saveGenarals(model.getContentId(),"0");
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		json.put("data", model);
		return json;
	}

	/**
	 * TODO 保存职务权限
	 * @author 李利广
	 * @Date 2018年9月15日 下午5:42:35
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveGenarals")
	public JSONObject saveGenarals(String contentId,String genarals){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			InfoGenarals infoGenarals = infoService.saveGenarals(contentId,genarals);
			json.put("flag", "1");
			json.put("data", infoGenarals);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 查询一条信息的权限，用于数据回显
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午4:36:10
	 * @param contentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAuthority")
	public JSONObject getAuthority(String contentId,String columnId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			InfoModel model = infoService.getAuthority(contentId);
			json.put("type","content");//区分权限是从内容中获取还是栏目中获取的 content:内容，column:栏目
			json.put("data", model);
			if(model.getDeptList().size() <= 0 && model.getZwqxList().size()<=0 && model.getDeptZwqxList().size() <=0 && model.getUserGroupList().size()<=0 && StringUtils.isNotBlank(columnId)){
				InfoModel model1 = infoService.getAuthority(columnId);
				json.put("type","column");
				json.put("data", model1);
			}
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 查询一条信息的权限，用于数据回显
	 * TODO
	 * @author 李利广
	 * @Date 2018年9月16日 下午4:36:10
	 * @param contentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGenarals")
	public JSONObject getGenarals(String contentId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			List<InfoGenarals> infoGenarals = infoService.getGenarals(contentId);
			if(infoGenarals.size()>0){
				if(infoGenarals.get(0).getGenarals().equals("1")){
					json.put("type","1");
				}else{
					json.put("type","0");
				}
			}else{
				json.put("type","0");
			}
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 保存栏目权限
	 * @param model
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存栏目默认权限")
	@RequestMapping("saveColumn")
	@ResponseBody
	public JSONObject saveColumn(@RequestBody ColumnModel model){
		JSONObject json = new JSONObject();
		json.put("flag","1");
		try{
			model = infoService.saveColumnFbfw(model);
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 查询一条信息的权限，用于数据回显
	 * @param columnId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getColumnAuthority")
	public JSONObject getColumnAuthority(String columnId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			ColumnModel model = infoService.getColumnAuthority(columnId);
			json.put("data", model);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
