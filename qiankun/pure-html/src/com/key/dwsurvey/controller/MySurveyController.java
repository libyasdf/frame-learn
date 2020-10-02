package com.key.dwsurvey.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sinoep.modules.info.authority.common.util.InfoAuthorityUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.key.authority.common.util.AuthorityUtils;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.DiaowenProperty;
import com.key.common.utils.JspToHtml;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStyle;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.service.QuestionManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyStyleManager;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;

import net.sf.json.JSONObject;

/**
 * 收集入口 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://www.dwsurvey.net
 *
 */

@Controller
@RequestMapping("/wenjuan/surveyDirectory")
public class MySurveyController{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SurveyDirectoryManager directoryManager;
	
	@Autowired
	private AccountManager accountManager;
	
	@Autowired
	private QuestionManager questionManager;
	
	@Autowired
	private SurveyStyleManager surveyStyleManager;
	
	/**
	 * 保存问卷目录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月19日 上午9:58:22
	 * @param surveyName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public SurveyDirectory save(SurveyDirectory survey) throws Exception {
	    try{
	    	survey.setDirType(2);
	    	if(survey.getSurveyName()==null || "".equals(survey.getSurveyName().trim())){
	    		survey.setSurveyName("请输入问卷标题");
	    	}
	 	   directoryManager.save(survey);
	 	    //surveyId = survey.getId();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return survey;
	}
	
	/**
	 * 草稿列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月20日 下午6:57:45
	 * @param pageImpl
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlist")
	@LogAnnotation(value = "getList",opName = "查询草稿列表")
	public PageImpl getList(PageImpl pageImpl,String type,Integer surveyState,String timeRange,String surveyName){
		String startDate="";
		String endDate="";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
				pageImpl = directoryManager.getPageListDraft(pageable,pageImpl,type,surveyState,startDate,endDate,surveyName);
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}

	/**
	 * 局级草稿列表
	 * TODO
	 * @author 马秋霞
	 * @Date 2018年9月20日 下午6:57:45
	 * @param pageImpl
	 * @param creUserName
	 * @param applicantUnitName
	 * @param goOutTime
	 * @param subflag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getGenalsList")
	@LogAnnotation(value = "getGenalsList",opName = "查询局级草稿列表")
	public PageImpl getGenalsList(PageImpl pageImpl,String type,Integer surveyState,String timeRange,String surveyName){
		String startDate="";
		String endDate="";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = directoryManager.getGenalsListDraft(pageable,pageImpl,type,surveyState,startDate,endDate,surveyName);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}

	
	/**
	 * 
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月25日 下午10:18:17
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	@LogAnnotation(value = "delete",opName = "删除数据")
	public JSONObject delete(String id) {
		JSONObject json = new JSONObject();
		 try {
			directoryManager.deleteById(id);
				json.put("flag", "1");
		} catch (Exception e) {
			json.put("flag", "0");
		}

		return json;
	}

    @ResponseBody
    @RequestMapping("findSurveyId")
    @LogAnnotation(value = "findSurveyId",opName = "根据id查询数据")
    public JSONObject findSurveyId(String id) {
        JSONObject json = new JSONObject();
        try {
            SurveyDirectory surveyDirectory = directoryManager.findById(id);
			String subflag =surveyDirectory.getSubflag();
            json.put("flag", "1");
            json.put("subflag",subflag);
        } catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
           /* json.put("flag", "0");*/
        }

        return json;
    }

	/**
	 * 发布调查问卷
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月27日 上午10:34:50
	 * @param surveyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("publish")
	@LogAnnotation(value = "update",opName = "发布问卷")
	public JSONObject devSurvey(String surveyId, String tabId, HttpServletRequest request){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		SurveyDirectory survey = directoryManager.findById(surveyId);
		Date createDate=survey.getCreateDate();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd/");
		try{
			//String url="/survey!answerSurvey.action?surveyId="+surveyId;
			String url = "/wenjuan/surveyDirectory/answerSurvey?surveyId=" + surveyId;
			String filePath = "/wjHtml/"+dateFormat.format(createDate);
			String fileName = surveyId+".html";
			new JspToHtml().postJspToHtml(url, filePath, fileName);
			survey.setHtmlPath(filePath+fileName);

			List<TQuestion> questions = questionManager.find(surveyId, "2");
			survey.setSurveyQuNum(questions.size());
			directoryManager.save(survey);
			json.put("flag", "1");
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 问卷的动态访问方式
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月27日 下午2:04:09
	 * @param mv
	 * @param surveyId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("answerSurvey")
	public ModelAndView answerSurvey(ModelAndView mv, String surveyId) throws Exception {
		SurveyDirectory survey=directoryManager.getSurvey(surveyId);
		if (survey == null){
			survey = directoryManager.getSurvey(surveyId);
		}
		survey.setQuestions(questionManager.findDetails(surveyId, "2"));
		mv.addObject("survey", survey);
		SurveyStyle surveyStyle=surveyStyleManager.getBySurveyId(surveyId);
		mv.addObject("surveyStyle", surveyStyle);
		mv.addObject("prevHost", DiaowenProperty.STORAGE_URL_PREFIX);
		mv.setViewName("modules/wenjuan/content/diaowen-design/answer-survey");
		return mv;
	}
	
	/**
	 * 查询当前用户收到的调查问卷
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月27日 下午9:25:38
	 * @param pageImpl
	 * @param type
	 * @param timeRange
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSurveyList")
	public PageImpl getSurveyList(PageImpl pageImpl,String surveyName, String type,String timeRange){
		pageImpl.setFlag("0");
		String startDate="";
		String endDate="";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			pageImpl = directoryManager.getSurveyList(pageImpl,surveyName, type,startDate,endDate);
			pageImpl.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 判断当前用户是否有权限填写问卷
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月28日 上午10:31:09
	 * @param surveyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getIsAuth")
	public JSONObject getIsAuth(String surveyId){
		JSONObject json = new JSONObject();
		json.put("isAuth", false);
		//获取当前用户权限内的问卷ID
		try {
			List<String> authList = InfoAuthorityUtils.getAuthList();
			if (!authList.isEmpty()) {
				if (authList.contains(surveyId)) {
					json.put("isAuth", true);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 判断当前用户是否已经提交过
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月28日 上午11:14:59
	 * @param surveyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getIsComplete")
	public JSONObject getIsComplete(String surveyId){
		JSONObject json = new JSONObject();
		json.put("isCom", false);
		try {
			boolean res = directoryManager.getIsComplete(surveyId);
			json.put("isCom", res);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 停止收集
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月27日 下午5:02:26
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("stopCollect")
	public JSONObject stopCollect(String id) {
		JSONObject json = new JSONObject();
		 try {
			directoryManager.stopCollect(id);
				json.put("flag", "1");
		} catch (Exception e) {
			json.put("flag", "0");
			log.error(e.getMessage(),e);
		}

		return json;
	}
	
	/**
	 * 重新收集
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月27日 下午5:07:00
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("startCollect")
	public JSONObject startCollect(String id) {
		JSONObject json = new JSONObject();
		 try {
			directoryManager.startCollect(id);
			json.put("flag", "1");
		} catch (Exception e) {
			json.put("flag", "0");
			log.error(e.getMessage(),e);
		}

		return json;
	}
	
	/**
	 * ajax删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("ajaxDelete")
	public JSONObject ajaxDelete(String delQuId) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try{
			questionManager.delete(delQuId);	
			json.put("flag", "1");
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
