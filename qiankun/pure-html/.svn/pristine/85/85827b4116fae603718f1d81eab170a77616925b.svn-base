package com.key.dwsurvey.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStats;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyStatsManager;
import com.sinosoft.sinoep.user.entity.SessionUser;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 分析报告的controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年9月28日 下午8:36:57
 */
@Controller
@RequestMapping("/wenjuan/surveyReport")
public class SurveyReportController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SurveyStatsManager surveyStatsManager;
	@Autowired
	private SurveyDirectoryManager directoryManager;
	
	private SurveyStats surveyStats = new SurveyStats();
	
	@RequestMapping("/defaultReport")
	public String defaultReport(HttpServletRequest request,HttpServletResponse response,String surveyId) {
		// 得到频数分析数据
		try {
			SessionUser user = UserUtil.getSessionUser();
			if(user!=null){
				//SurveyDirectory directory=directoryManager.getSurveyByUser(surveyId, user.getUserId());
				SurveyDirectory directory = directoryManager.findById(surveyId);
				directoryManager.getSurveyDetail(surveyId, directory);
				if(directory!=null){
					List<TQuestion> questions = surveyStatsManager.findFrequency(directory);
					surveyStats.setQuestions(questions);
				}
				request.setAttribute("surveyId", surveyId);
				request.setAttribute("directory", directory);
				request.setAttribute("surveyStats", surveyStats);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return "forward:/modules/wenjuan/content/diaowen-da/default-report.jsp";
	}
	
	
	//取得某一题的统计数据
	@RequestMapping("/chartData")
	public String chartData(HttpServletResponse response,String quId) throws Exception {
		SessionUser user =  UserUtil.getSessionUser();
		//取柱状图数据
		if(user!=null){
			TQuestion question=new TQuestion();
			question.setId(quId);
			surveyStatsManager.questionDateCross(question);
			response.getWriter().write(question.getStatJson());
		}
		return null;
	}
}
