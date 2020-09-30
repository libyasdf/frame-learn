package com.sinosoft.sinoep.modules.system.config.bbkzversion.controller;


import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.Help;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.ReturnClass;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.tool.Helper;
import com.sinosoft.sinoep.modules.system.config.bbkzversion.service.BbkzVersionServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Controller 类
 * @author 子火
 * @Date 2019年4月10日09:38:20
 */
@Controller
@RequestMapping("/bbkzVersionController")
public class BbkzVersionController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BbkzVersionServiceI bbkzVersionServiceI;

	@LogAnnotation(opType =OpType.QUERY ,opName = "查询BBKZ_VERSION表")
	@RequestMapping(value="getVersion")
	@ResponseBody
	public ReturnClass getVersion() {
		ReturnClass res=null;
		try {
			res=bbkzVersionServiceI.getVersion();
		}catch (Exception e){
			log.error(Helper.exceptionToString(e));
			res=Help.returnClassT(500,"获取版本的getVersion方法异常", Helper.exceptionToString(e));
		}
		return res;
	}
	
	

	
}
