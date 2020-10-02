package com.sinosoft.sinoep.modules.system.component.template.controller;

import com.sinosoft.sinoep.modules.system.component.template.services.TemplateService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;


/**
 * TODO 模板下载
 * @author 王富康
 * @Date 2018年07月10日 下午18:18
 */
@Controller
@RequestMapping("/system/component/template")
public class TemplateController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TemplateService templateService;
	
	/**
	 * TODO 根据id下载附件（单附件下载）
	 * @author 王富康
	 * @Date 2018年07月10日 下午18:18
	 * @param response
	 * @param templateId
	 */
	@ResponseBody
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletResponse response, String templateId) {
		if (StringUtils.isNotBlank(templateId)) {
			templateService.download(response,templateId);
		}
	}
}
