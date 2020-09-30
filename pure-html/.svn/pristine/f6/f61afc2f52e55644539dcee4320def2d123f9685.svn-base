package com.key.dwsurvey.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.key.common.base.service.AccountManager;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.service.SurveyAnswerManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 统计结果导出控制类
 * TODO 
 * @author 李利广
 * @Date 2018年11月7日 上午9:21:42
 */
@Controller
@RequestMapping("/wenjuan/mySurveyAnswer")
public class MySurveyAnswerController {
	
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;
	
	@Autowired
	private SurveyDirectoryManager directoryManager;
	
	@Autowired
	private AccountManager accountManager;
	
	@RequestMapping("/exportXLS")
	public String exportXLS(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception{
		try{
			String savePath = request.getSession().getServletContext().getRealPath("/");
			String userId = UserUtil.getCruUserId();
	    	if(StringUtils.isNotBlank(userId)){
	    		//SurveyDirectory survey=directoryManager.getSurveyByUser(surveyId, user.getId());
	    		SurveyDirectory survey = directoryManager.getSurvey(surveyId);
	    		if(survey != null){
	    			savePath = surveyAnswerManager.exportXLS(surveyId,savePath);
	    			response.sendRedirect(request.getContextPath() + "/" + savePath);
	    		}
	    	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
