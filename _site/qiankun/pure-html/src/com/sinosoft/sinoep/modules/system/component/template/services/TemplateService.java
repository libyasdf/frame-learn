package com.sinosoft.sinoep.modules.system.component.template.services;

import com.sinosoft.sinoep.modules.system.component.template.dao.TemplateDao;
import javax.servlet.http.HttpServletResponse;

public interface TemplateService {

	/**
	 * TODO 
	 * @author 王富康
	 * @Date 2018年07月10日 下午18:18
	 * @return
	 */
	public TemplateDao getRepository();


	/**
	 * TODO 根据id下载附件（单附件下载）
	 * @author 王富康
	 * @Date 2018年07月10日 下午18:18
	 * @param response
	 * @param templateId
	 */
	public void download(HttpServletResponse response, String templateId);

}
