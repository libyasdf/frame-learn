package com.key.dwsurvey.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.key.dwsurvey.entity.SurveyDetail;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStyle;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.service.QuestionManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyStyleManager;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/wenjuan/surveyDesign")
public class MySurveyDesignController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private SurveyStyleManager surveyStyleManager;
	
	/**
	 * 跳转到问卷设计页面
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月20日 下午4:15:54
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping("/intoDesignPage")
	public void intoDesignPage(HttpServletRequest request,HttpServletResponse response,String surveyId,String surveyState) throws Exception{
		try {
			buildSurvey(request,response,surveyId);
			if(surveyState==null){
				request.getRequestDispatcher("/modules/wenjuan/content/diaowen-design/survey.jsp").forward(request, response);

			}else{
				request.getRequestDispatcher("/modules/wenjuan/content/diaowen-design/surveyReadOnly.jsp").forward(request, response);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void buildSurvey(HttpServletRequest request,HttpServletResponse response,String surveyId) {
		//判断是否拥有权限
		String userId = UserUtil.getCruUserId();
		if(StringUtils.isNotBlank(userId)){
			//SurveyDirectory surveyDirectory=surveyDirectoryManager.get(surveyId);
			//SurveyDirectory surveyDirectory = surveyDirectoryManager.getSurvey(surveyId);
			SurveyDirectory surveyDirectory = surveyDirectoryManager.findById(surveyId);
			if(surveyDirectory!=null){
				surveyDirectoryManager.getSurveyDetail(surveyId, surveyDirectory);
//				SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
				List<TQuestion> questions=questionManager.findDetails(surveyId, "2");
				surveyDirectory.setQuestions(questions);
				surveyDirectory.setSurveyQuNum(questions.size());
				surveyDirectoryManager.save(surveyDirectory);
				surveyDirectory.setDirType(2);
				request.setAttribute("survey", surveyDirectory);
				SurveyStyle surveyStyle=surveyStyleManager.getBySurveyId(surveyId);
				request.setAttribute("surveyStyle", surveyStyle);
				//request.setAttribute("prevHost", DiaowenProperty.STORAGE_URL_PREFIX);
				request.setAttribute("prevHost", null);
			}else{
				request.setAttribute("msg", "未登录或没有相应数据权限");
			}
		}else{
			request.setAttribute("msg", "未登录或没有相应数据权限");
		}
	}
	
	/**
	 * 保存问卷设置
	 * TODO 
	 * @author 李利广
	 * @Date 2018年10月9日 下午5:01:24
	 * @param request
	 * @param response
	 * @param surveyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ajaxSaveSet")
	public JSONObject ajaxSave(HttpServletRequest request,HttpServletResponse response,String surveyId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			String svyName=request.getParameter("svyName");
			String svyNote=request.getParameter("svyNote");
			//属性
			String effective=request.getParameter("effective");
			String effectiveIp=request.getParameter("effectiveIp");
			String rule=request.getParameter("rule");
			String ruleCode=request.getParameter("ruleCode");
			String refresh=request.getParameter("refresh");
			String mailOnly=request.getParameter("mailOnly");
			String ynEndNum=request.getParameter("ynEndNum");
			String endNum=request.getParameter("endNum");
			String ynEndTime=request.getParameter("ynEndTime");
			String endTime=request.getParameter("endTime");
			String showShareSurvey=request.getParameter("showShareSurvey");
			String showAnswerDa=request.getParameter("showAnswerDa");
			
			
			SurveyDirectory survey = surveyDirectoryManager.getSurvey(surveyId);
			SurveyDetail surveyDetail=survey.getSurveyDetail();
			//判断是否拥有权限
			String userId = UserUtil.getCruUserId();
			if(StringUtils.isNotBlank(userId) && survey!=null){
				if(userId.equals(survey.getUserId())){
					if( svyNote!=null){
						svyNote=URLDecoder.decode(svyNote,"utf-8");
						surveyDetail.setSurveyNote(svyNote);
					}
					if(svyName!=null && !"".equals(svyName)){
						svyName=URLDecoder.decode(svyName,"utf-8");
						survey.setSurveyName(svyName);
					}

					//保存属性
					if(effective!=null && !"".equals(effective)){
					    surveyDetail.setEffective(Integer.parseInt(effective));
					}
					if(effectiveIp!=null && !"".equals(effectiveIp)){
					    surveyDetail.setEffectiveIp(Integer.parseInt(effectiveIp));
					}
					if(rule!=null && !"".equals(rule)){
					    surveyDetail.setRule(Integer.parseInt(rule));
					    surveyDetail.setRuleCode(ruleCode);
					}
					if(refresh!=null && !"".equals(refresh)){
					    surveyDetail.setRefresh(Integer.parseInt(refresh));
					}
					if(mailOnly!=null && !"".equals(mailOnly)){
					    surveyDetail.setMailOnly(Integer.parseInt(mailOnly));
					}
					if(ynEndNum!=null && !"".equals(ynEndNum)){
					    surveyDetail.setYnEndNum(Integer.parseInt(ynEndNum));
					    if(endNum!=null && endNum.matches("\\d*")){
					    	surveyDetail.setEndNum(Integer.parseInt(endNum));			
					    }
					}
					if(ynEndTime!=null && !"".equals(ynEndTime)){
					    surveyDetail.setYnEndTime(Integer.parseInt(ynEndTime));
					    if(StringUtils.isNotBlank(endTime)){
					    	Date endDate = DateUtil.getTextDate(endTime, "yyyy-MM-dd HH:mm:ss");
						    surveyDetail.setEndTime(endDate);
					    }else{
					    	surveyDetail.setEndTime(null);
					    }
//					    surveyDetail.setEndTime(new Date());
					}
					if(showShareSurvey!=null && !"".equals(showShareSurvey)){
					    surveyDetail.setShowShareSurvey(Integer.parseInt(showShareSurvey));
					    survey.setIsShare(Integer.parseInt(showShareSurvey));
					}
					if(showAnswerDa!=null && !"".equals(showAnswerDa)){
					    surveyDetail.setShowAnswerDa(Integer.parseInt(showAnswerDa));
					    survey.setViewAnswer(Integer.parseInt(showAnswerDa));
					}
					surveyDirectoryManager.save(survey);
					json.put("flag", "1");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
