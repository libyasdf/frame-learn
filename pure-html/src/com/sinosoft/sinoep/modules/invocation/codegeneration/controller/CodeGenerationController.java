package com.sinosoft.sinoep.modules.invocation.codegeneration.controller;


import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.invocation.codegeneration.service.CodeGenerationServiceI;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.Help;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.ReturnClass;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.tool.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller 类
 * @author 子火
 * @Date 2019年4月10日09:38:20
 */
@Controller
@RequestMapping("/codeGenerationController")
public class CodeGenerationController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CodeGenerationServiceI bbkzVersionServiceI;


	/**
	 * 代码生成
	 * @author 子火
	 * @Date 2019年5月9日10:22:22
	 * @param tabN 表名
	 * @param packageN 骆驼命名法
	 * @param packagen 全小写
	 * @param packAgeN 首大写
	 * @return ReturnClass
	 * @throws  Exception
	 */
	@LogAnnotation(opType =OpType.QUERY ,opName = "code生成")
	@RequestMapping(value="/autoCode")
	@ResponseBody
	public ReturnClass autoCode(String tabN,String packageN,String packagen,String packAgeN,HttpServletRequest request) {
		ReturnClass res=null;
		//数据库的表名都是大写,所以注意传进的表名要大写
		try {
			res=bbkzVersionServiceI.autoCode(tabN,packageN,packagen,packAgeN,request);
		}catch (Exception e){
			log.error(Helper.exceptionToString(e));
			res=Help.returnClassT(500,"autoCode方法异常", Helper.exceptionToString(e));
		}
		return res;
	}


	
}
