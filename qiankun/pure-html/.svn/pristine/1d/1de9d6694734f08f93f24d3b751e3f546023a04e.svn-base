package com.sinosoft.sinoep.modules.video.authority.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.modules.video.authority.entity.VideoModel;
import com.sinosoft.sinoep.modules.video.authority.entity.ProgramaModel;
import com.sinosoft.sinoep.modules.video.authority.services.ContentFbZwqxService;
import net.sf.json.JSONObject;

/**
 * TODO 信息发布职务权限控制类
 * @author 郝灵涛
 * @Date 2018年9月15日 下午5:41:32
 */
@Controller
@RequestMapping("/video/authority/contentFbZwqx")
public class ContentFbZwqxController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
		private ContentFbZwqxService service;

	/**
	 * TODO 保存职务权限
	 * @author 郝灵涛
	 * @Date 2018年9月15日 下午5:42:35
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public JSONObject saveInfo(@RequestBody VideoModel model){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			model = service.saveInfoZwqx(model);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		json.put("data", model);
		return json;
	}
	
	/**
	 * 查询一条信息的权限，用于数据回显
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月16日 下午4:36:10
	 * @param contentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAuthority")
	public JSONObject getAuthority(String contentId,String columnId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		json.put("from", "0");//权限来自于内容的权限，还是栏目的权限；0表示内容的权限；1表示栏目的权限
		try {
			VideoModel model = service.getAuthority(contentId);
			json.put("type","content");//区分权限是从内容中获取还是栏目中获取的 content:内容，column:栏目
			json.put("data", model);
			if(model.getDeptList().size() <= 0 && model.getZwqxList().size()<=0 && model.getDeptZwqxList().size() <=0 && model.getGroupList().size()<=0 && StringUtils.isNotBlank(columnId)){
				ProgramaModel model1 = service.getColumnAuthority(columnId);
				json.put("type","column");
				json.put("data", model1);
				json.put("from", "0");
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
	public JSONObject saveColumn(@RequestBody ProgramaModel model){
		JSONObject json = new JSONObject();
		json.put("flag","1");
		try{
			model = service.saveColumnFbfw(model);
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
			ProgramaModel model = service.getColumnAuthority(columnId);
			json.put("data", model);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
