package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services.DyxxService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 党员管理-党员信息-控制层
 * author 冯建海
 */
@Controller
@RequestMapping("dyjl")
public class DyxxController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DyxxService dyxxService;

	@LogAnnotation(value = "query",opName = "党员信息列表查询")
	@RequestMapping(value = "list")
	@ResponseBody
	public String list(){
		String pageImpl = "";
		/*try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}*/
		return pageImpl;
	}
	/**
	 * @Author 冯建海
	 * @Description 修改党员信息
	 * @Date 2018/8/21 17:00
	 **/
	@LogAnnotation(value = "update",opName = "修改党员信息")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject reportDuty(String id, String month) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		if (StringUtils.isNotBlank(id)) {
			try {
				//shangBaoService.reportDuty(id, month);
				json.put("flag", "1");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
		}
		return json;
	}
	/**
	 * @Author 冯建海
	 * @Description 新增党员信息
	 * @Date 2018/8/21 17:00
	 **/
	@LogAnnotation(value = "save",opName = "新增党员信息")
	@RequestMapping("save")
	@ResponseBody
	public JSONObject save(ShangBao sb) {
		JSONObject json = new JSONObject();
		try {
			//sb = shangBaoService.save(sb);
			json.put("flag", 1);
			json.put("data", sb);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}

}
