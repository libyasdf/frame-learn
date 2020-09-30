package com.sinosoft.sinoep.modules.system.config.bbkzversion.service;


import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.ReturnClass;

/**
 * @author 子火
 * @Date 2019年4月10日09:38:20
 */
public interface BbkzVersionServiceI {
	/**
	 * 获取版本号
	 * @author 子火
	 * @Date 2019年4月10日09:39:59
	 * @throws  Exception 接口若扔出了异常要有此
	 * @return  返回状态码类
	 */
	public ReturnClass getVersion() throws Exception;
}
