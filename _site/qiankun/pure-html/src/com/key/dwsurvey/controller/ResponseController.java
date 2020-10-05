package com.key.dwsurvey.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.key.common.QuType;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.ipaddr.IPService;
import com.key.common.utils.CookieUtils;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.AnCheckbox;
import com.key.dwsurvey.entity.AnRadio;
import com.key.dwsurvey.entity.SurveyAnswer;
import com.key.dwsurvey.entity.SurveyDetail;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.service.SurveyAnswerManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 答卷的controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年9月28日 下午8:36:00
 */
@Controller
@RequestMapping("/wenjuan/response")
public class ResponseController {
	
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;
	
	@Autowired
	private SurveyDirectoryManager directoryManager;
	
	@Autowired
	private IPService ipService;

	@Autowired
	private SysWaitNoflowService service;
	
	@Autowired
	private AccountManager accountManager;
	
	private ImageCaptchaService imageCaptchaService=new DefaultManageableImageCaptchaService();
	
	/**
	 * 保存问卷答案
	 * TODO 
	 * @author 李利广
	 * @Date 2018年11月7日 上午10:59:40
	 * @param request
	 * @param response
	 * @param model
	 * @param surveyId
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request,HttpServletResponse response,Model model,String surveyId,String sid) throws Exception {
		request.setCharacterEncoding("utf-8");
		try {
			String ipAddr = ipService.getIp(request);
			long ipNum = surveyAnswerManager.getCountByIp(surveyId, ipAddr);
			SurveyDirectory directory = directoryManager.getSurvey(surveyId);
			SurveyDetail surveyDetail = directory.getSurveyDetail();
			String userId = UserUtil.getCruUserId();
			SurveyAnswer entity = new SurveyAnswer();
			if (StringUtils.isNotBlank(userId)) {
				entity.setUserId(userId);
			}
			Cookie cookie = CookieUtils.getCookie(request, surveyId);
			Integer effectiveIp = surveyDetail.getEffectiveIp();
			Integer effective = surveyDetail.getEffective();
			if ((effective != null && effective > 1 && cookie != null) || (effectiveIp != null && effectiveIp == 1 && ipNum > 0)) {
				model.addAttribute("surveyId", surveyId);
				return "redirect:/wenjuan/response/answerError";
			}
			Map<String, Map<String, Object>> quMaps = buildSaveSurveyMap(request);
			String addr = ipService.getCountry(ipAddr);
			String city = ipService.getCurCityByCountry(addr);
			entity.setUserName(UserUtil.getCruUserName());
			entity.setDeptId(UserUtil.getCruDeptId());
			entity.setDeptName(UserUtil.getCruDeptName());
			entity.setChuShiId(UserUtil.getCruChushiId());
			entity.setChuShiName(UserUtil.getCruChushiName());
			entity.setJuId(UserUtil.getCruJuId());
			entity.setJuName(UserUtil.getCruJuName());
			entity.setIpAddr(ipAddr);
			entity.setAddr(addr);
			entity.setCity(city);
			entity.setSurveyId(surveyId);
			entity.setDataSource(0);
			surveyAnswerManager.saveAnswer(entity, quMaps);
			int effe = surveyDetail.getEffectiveTime();
			CookieUtils.addCookie(response, surveyId, (ipNum + 1) + "", effe * 60, "/");
			//删除非流程待办
			service.deleteWaitNoflow("",surveyId,"",UserUtil.getCruUserId(),"");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("surveyId", surveyId);
			return "redirect:/wenjuan/response/answerFailure";
		}
		model.addAttribute("sid", sid);
		return "redirect:/wenjuan/response/answerSuccess";
	}
	
	@RequestMapping("/answerSuccess")
	public String answerSuccess(HttpServletRequest request,HttpServletResponse response,String sid) throws Exception {
		SurveyDirectory directory = directoryManager.getSurveyBySid(sid);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("viewAnswer", directory.getViewAnswer());
		request.setAttribute("sid", directory.getSid());
		//request.getRequestDispatcher("/modules/wenjuan/conten/diaowen-answer/response-success.jsp").forward(request, response);
		//return ANSWER_SUCCESS;
		return "forward:/modules/wenjuan/content/diaowen-answer/response-success.jsp";
	}
	
	@RequestMapping("/answerFailure")
	public String answerFailure(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception {
		//SurveyDirectory directory = directoryManager.get(surveyId);
		SurveyDirectory directory =directoryManager.findById(surveyId);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("htmlPath", directory.getHtmlPath());
		return "forward:/modules/wenjuan/content/diaowen-answer/response-failure.jsp";
	}
	
	public Map<String, Map<String, Object>> buildSaveSurveyMap(HttpServletRequest request) throws Exception {
		Map<String, Map<String, Object>> quMaps = new HashMap<String, Map<String, Object>>();
		Map<String, Object> yesnoMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.YESNO + "_");//是非
		quMaps.put("yesnoMaps", yesnoMaps);
		Map<String, Object> radioMaps = WebUtils.getParametersStartingWith(request, "qu_"+QuType.RADIO + "_");//单选
		Map<String, Object> checkboxMaps = WebUtils.getParametersStartingWith(request, "qu_"+QuType.CHECKBOX + "_");//多选
		Map<String, Object> fillblankMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.FILLBLANK + "_");//填空
		Set<Entry<String,Object>> entrySet = fillblankMaps.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			String value = new String(entry.getValue().toString().getBytes("iso8859-1"),"utf-8");
			fillblankMaps.put(key, value);
		}
		quMaps.put("fillblankMaps", fillblankMaps);
		Map<String, Object> dfillblankMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.MULTIFILLBLANK + "_");//多项填空
		for (String key : dfillblankMaps.keySet()) {
			String dfillValue = dfillblankMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, dfillValue);
			Set<Entry<String,Object>> mapEntrySet = map.entrySet();
			for (Entry<String, Object> entry : mapEntrySet) {
				String key1 = entry.getKey();
				String value = new String(entry.getValue().toString().getBytes("iso8859-1"),"utf-8");
				map.put(key1, value);
			}
			dfillblankMaps.put(key, map);
		}
		quMaps.put("multifillblankMaps", dfillblankMaps);
		Map<String, Object> answerMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.ANSWER + "_");//多行填空
		Set<Entry<String,Object>> answerEntrySet = answerMaps.entrySet();
		for (Entry<String, Object> entry : answerEntrySet) {
			String key = entry.getKey();
			String value = new String(entry.getValue().toString().getBytes("iso8859-1"),"utf-8");
			answerMaps.put(key, value);
		}
		quMaps.put("answerMaps", answerMaps);
		Map<String, Object> compRadioMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.COMPRADIO + "_");//复合单选
		for (String key : compRadioMaps.keySet()) {
			String enId = key;
			String quItemId = compRadioMaps.get(key).toString();
			String otherText = Struts2Utils.getParameter("text_qu_" + QuType.COMPRADIO + "_" + enId + "_" + quItemId);
			AnRadio anRadio = new AnRadio();
			anRadio.setQuId(enId);
			anRadio.setQuItemId(quItemId);
			anRadio.setOtherText(otherText);
			compRadioMaps.put(key, anRadio);
		}
		quMaps.put("compRadioMaps", compRadioMaps);
		Map<String, Object> compCheckboxMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.COMPCHECKBOX + "_");//复合多选
		for (String key : compCheckboxMaps.keySet()) {
			String dfillValue = compCheckboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, "tag_" + dfillValue);
			for (String key2 : map.keySet()) {
				String quItemId = map.get(key2).toString();
				String otherText = Struts2Utils.getParameter("text_" + dfillValue + quItemId);
				AnCheckbox anCheckbox = new AnCheckbox();
				anCheckbox.setQuItemId(quItemId);
				anCheckbox.setOtherText(otherText);
				map.put(key2, anCheckbox);
			}
			compCheckboxMaps.put(key, map);
		}
		quMaps.put("compCheckboxMaps", compCheckboxMaps);
		Map<String, Object> enumMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.ENUMQU + "_");//枚举
		quMaps.put("enumMaps", enumMaps);
		Map<String, Object> scoreMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.SCORE + "_");//分数
		for (String key : scoreMaps.keySet()) {
			String tag = scoreMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, tag);
			scoreMaps.put(key, map);
		}
		quMaps.put("scoreMaps", scoreMaps);
		Map<String, Object> quOrderMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.ORDERQU + "_");//排序
		for (String key : quOrderMaps.keySet()) {
			String tag = quOrderMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, tag);
			quOrderMaps.put(key, map);
		}
		quMaps.put("quOrderMaps", quOrderMaps);
		Map<String, Object> chenRadioMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.CHENRADIO + "_");
		for (String key : chenRadioMaps.keySet()) {
			String tag = chenRadioMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, tag);
			chenRadioMaps.put(key, map);
		}
		quMaps.put("chenRadioMaps", chenRadioMaps);
		Map<String, Object> chenCheckboxMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.CHENCHECKBOX + "_");
		for (String key : chenCheckboxMaps.keySet()) {
			String tag = chenCheckboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenCheckboxMaps.put(key, map);
		}
		quMaps.put("chenCheckboxMaps", chenCheckboxMaps);
		Map<String, Object> chenScoreMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.CHENSCORE + "_");
		for (String key : chenScoreMaps.keySet()) {
			String tag = chenScoreMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenScoreMaps.put(key, map);
		}
		quMaps.put("chenScoreMaps", chenScoreMaps);
		Map<String, Object> chenFbkMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.CHENFBK + "_");
		for (String key : chenFbkMaps.keySet()) {
			String tag = chenFbkMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenFbkMaps.put(key, map);
		}
		quMaps.put("chenFbkMaps", chenFbkMaps);
		for (String key:radioMaps.keySet()) {
			String enId = key;
			String quItemId = radioMaps.get(key).toString();
			String otherText = Struts2Utils.getParameter("text_qu_" + QuType.RADIO + "_" + enId + "_" + quItemId);
			AnRadio anRadio = new AnRadio();
			anRadio.setQuId(enId);
			anRadio.setQuItemId(quItemId);
			anRadio.setOtherText(otherText);
			radioMaps.put(key, anRadio);
		}
		quMaps.put("compRadioMaps", radioMaps);
		for (String key : checkboxMaps.keySet()) {
			String dfillValue = checkboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, dfillValue);
			for (String key2 : map.keySet()) {
				String quItemId = map.get(key2).toString();
				String otherText = Struts2Utils.getParameter("text_" + dfillValue + quItemId);
				AnCheckbox anCheckbox = new AnCheckbox();
				anCheckbox.setQuItemId(quItemId);
				anCheckbox.setOtherText(otherText);
				map.put(key2, anCheckbox);
			}
			checkboxMaps.put(key, map);
		}
		quMaps.put("compCheckboxMaps", checkboxMaps);
		Map<String, Object> chenCompChenRadioMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.COMPCHENRADIO + "_");
		for (String key : chenCompChenRadioMaps.keySet()) {
			String tag = chenCompChenRadioMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenCompChenRadioMaps.put(key, map);
		}
		quMaps.put("compChenRadioMaps", chenCompChenRadioMaps);
		return quMaps;
	}
	
	@RequestMapping("/answerError")
	public String answerError(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception {
		//SurveyDirectory directory = directoryManager.get(surveyId);
		SurveyDirectory directory = directoryManager.findById(surveyId);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("sId", directory.getSid());
		String ipAddr = ipService.getIp(request);
		request.setAttribute("ip", ipAddr);
		request.getRequestDispatcher("/modules/wenjuan/content/diaowen-answer/response-input-error.jsp").forward(request, response);
		//return ANSWER_ERROR;
		return "";
	}
}
