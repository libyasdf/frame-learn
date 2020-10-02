package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzbg.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzbg.entity.DwxtOrgChange;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzbg.services.DwxtOrgChangeService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtUnit;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtHjxj;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.vo.DwxtTreeVO;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 党组织控制层
 * @author 胡石阳
 * @Date 2018年4月9日 上午9:30:57
 */
@RestController
@RequestMapping(value = "/djgl/ddjs/dzz/dzzbg")
public class DwxtOrgChangeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DwxtOrgChangeService dwxtOrgChangeService;

	@LogAnnotation(value = "query",opName = "查询列表")
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, DwxtOrgChange dwxtOrgChange, String timeRange) {
		return dwxtOrgChangeService.getPageList(pageImpl,dwxtOrgChange,timeRange);
	}


	/**
	 * 保存党组织
	 * @param dwxtOrgChange
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存变更信息")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public JSONObject save(DwxtOrgChange dwxtOrgChange,String code) {
		JSONObject json = new JSONObject();
		try {
			dwxtOrgChange = dwxtOrgChangeService.save(dwxtOrgChange,code);
			json.put("flag", 1);
			json.put("data", dwxtOrgChange);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}

	/**
	 * 查询换届信息
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "edit",opName = "查询变更信息")
	@RequestMapping(value = "findOne", method = RequestMethod.GET)
	public JSONObject findOne(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DwxtOrgChange dwxtOrgChange = null;
		try {
			dwxtOrgChange = dwxtOrgChangeService.findOne(id);
			json.put("flag", "1");
			json.put("data", dwxtOrgChange);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
