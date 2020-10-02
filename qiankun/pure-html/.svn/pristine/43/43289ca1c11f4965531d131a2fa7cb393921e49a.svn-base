package com.sinosoft.sinoep.modules.system.component.template.services;

import javax.servlet.http.HttpServletResponse;

import com.sinosoft.sinoep.modules.system.component.template.dao.TemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO 
 * @author 王富康
 * @Date 2018年07月10日 下午18:18
 */
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateDao templateDao;
	
	@Override
	public TemplateDao getRepository() {
		return this.templateDao;
	}


	/**
	 * TODO 根据id下载附件
	 * @author 王富康
	 * @Date 2018年07月10日 下午18:18
	 * @param response
	 * @param templateId
	 */
	@Override
	public void download(HttpServletResponse response, String templateId) {
		templateDao.download(response,templateId);
	}


}
