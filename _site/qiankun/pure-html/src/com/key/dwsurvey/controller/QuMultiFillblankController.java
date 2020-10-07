package com.key.dwsurvey.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.key.common.QuType;
import com.key.common.plugs.page.Page;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.AnDFillblank;
import com.key.dwsurvey.entity.QuMultiFillblank;
import com.key.dwsurvey.entity.QuestionLogic;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.service.AnDFillblankManager;
import com.key.dwsurvey.service.QuMultiFillblankManager;
import com.key.dwsurvey.service.QuestionManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wenjuan/quMultiFillblank")
public class QuMultiFillblankController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private QuMultiFillblankManager quMultiFillblankManager;
	@Autowired
	private AnDFillblankManager anDFillblankManager;
	
	@RequestMapping("/ajaxSave")
	public String ajaxSave(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			TQuestion entity=ajaxBuildSaveOption(request);
			questionManager.save(entity);
			String resultJson=buildResultJson(entity);
			response.getWriter().write(resultJson);
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("error");
		}
		return null;
	}
	
	public static String buildResultJson(TQuestion entity){
		StringBuffer strBuf=new StringBuffer();
		strBuf.append("{id:'").append(entity.getId());
		strBuf.append("',orderById:");
		strBuf.append(entity.getOrderById());
		strBuf.append(",quItems:[");
		List<QuMultiFillblank> quMultiFillblanks=entity.getQuMultiFillblanks();
		for (QuMultiFillblank quMultiFillblank : quMultiFillblanks) {
			strBuf.append("{id:'").append(quMultiFillblank.getId());
			strBuf.append("',title:'").append(quMultiFillblank.getOrderById()).append("'},");
		}
		int strLen=strBuf.length();
		if(strBuf.lastIndexOf(",")==(strLen-1)){
			strBuf.replace(strLen-1, strLen, "");
		}
		strBuf.append("]");
		
		strBuf.append(",quLogics:[");
		List<QuestionLogic> questionLogics=entity.getQuestionLogics();
		if(questionLogics!=null){
			for (QuestionLogic questionLogic : questionLogics) {
				strBuf.append("{id:'").append(questionLogic.getId());
				strBuf.append("',title:'").append(questionLogic.getTitle()).append("'},");
			}
		}
		strLen=strBuf.length();
		if(strBuf.lastIndexOf(",")==(strLen-1)){
			strBuf.replace(strLen-1, strLen, "");
		}
		strBuf.append("]}");
		return strBuf.toString();
	}
	private TQuestion ajaxBuildSaveOption(HttpServletRequest request) throws UnsupportedEncodingException {
		String quId=request.getParameter("quId");
		String belongId=request.getParameter("belongId");
		String quTitle=request.getParameter("quTitle");
		String orderById=request.getParameter("orderById");
		String tag=request.getParameter("tag");
		String isRequired=request.getParameter("isRequired");
		//hv 1水平显示 2垂直显示
		String hv=request.getParameter("hv");
		//randOrder 选项随机排列
		String randOrder=request.getParameter("randOrder");
		String cellCount=request.getParameter("cellCount");
		String paramInt01=request.getParameter("paramInt01");//最小分
//		String paramInt02=request.getParameter("paramInt02");//最大分
		if("".equals(quId)){
			quId=null;
		}
		//TQuestion entity=questionManager.getModel(quId);
		TQuestion entity=questionManager.findById(quId);
		entity.setBelongId(belongId);
		if(quTitle!=null){
			quTitle=URLDecoder.decode(quTitle,"utf-8");
			entity.setQuTitle(quTitle);
		}
		entity.setOrderById(Integer.parseInt(orderById));
		entity.setTag(Integer.parseInt(tag));
		entity.setQuType(QuType.MULTIFILLBLANK);
		//参数
		isRequired=(isRequired==null || "".equals(isRequired))?"0":isRequired;
		hv=(hv==null || "".equals(hv))?"0":hv;
		randOrder=(randOrder==null || "".equals(randOrder))?"0":randOrder;
		cellCount=(cellCount==null || "".equals(cellCount))?"0":cellCount;
		paramInt01=(paramInt01==null || "".equals(paramInt01))?"0":paramInt01;
		entity.setIsRequired(Integer.parseInt(isRequired));
		entity.setHv(Integer.parseInt(hv));
		entity.setRandOrder(Integer.parseInt(randOrder));
		entity.setCellCount(Integer.parseInt(cellCount));
		entity.setParamInt01(Integer.parseInt(paramInt01));
		entity.setParamInt02(10);
		Map<String, Object> optionNameMap=WebUtils.getParametersStartingWith(request, "optionValue_");
		List<QuMultiFillblank> quMFillblanks=new ArrayList<QuMultiFillblank>();
		for (String key : optionNameMap.keySet()) {
			String optionId=request.getParameter("optionId_"+key);
			Object optionName=optionNameMap.get(key);
			String optionNameValue=(optionName!=null)?optionName.toString():"";
			QuMultiFillblank quMultiFillblank=new QuMultiFillblank();
			if("".equals(optionId)){
				optionId=null;
			}
			quMultiFillblank.setId(optionId);
//			quRadio.setOptionTitle(key);
			optionNameValue=URLDecoder.decode(optionNameValue,"utf-8");
			quMultiFillblank.setOptionName(optionNameValue);
			quMultiFillblank.setOrderById(Integer.parseInt(key));
			quMFillblanks.add(quMultiFillblank);
		}
		entity.setQuMultiFillblanks(quMFillblanks);
		
		//逻辑选项设置
		Map<String, Object> quLogicIdMap=WebUtils.getParametersStartingWith(request, "quLogicId_");
		List<QuestionLogic> quLogics=new ArrayList<QuestionLogic>();
		for (String key : quLogicIdMap.keySet()) {
			String cgQuItemId=request.getParameter("cgQuItemId_"+key);
			String skQuId=request.getParameter("skQuId_"+key);
			String visibility=request.getParameter("visibility_"+key);
			String logicType=request.getParameter("logicType_"+key);
			Object quLogicId=quLogicIdMap.get(key);
			String quLogicIdValue=(quLogicId!=null)?quLogicId.toString():null;
			
			QuestionLogic quLogic=new QuestionLogic();
			quLogic.setId(quLogicIdValue);
			quLogic.setCgQuItemId(cgQuItemId);
			quLogic.setSkQuId(skQuId);
			quLogic.setVisibility(Integer.parseInt(visibility));
			quLogic.setTitle(key);
			quLogic.setLogicType(logicType);
			quLogics.add(quLogic);
		}
		entity.setQuestionLogics(quLogics);
		
		return entity;
	}
	
	
	private Page<AnDFillblank> anPage = new Page<AnDFillblank>();
	//取上传题结果
	@RequestMapping("/answers")
	public String answers() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		String quItemId = request.getParameter("quItemId");
		String surveyId = request.getParameter("surveyId");
		anPage.setPageSize(1000);
		anPage = anDFillblankManager.findPage(anPage, quItemId);
		request.setAttribute("surveyId",surveyId);
		return "forward:/modules/wenjuan/content/diaowen-da/dfillblank.jsp";
	}
	
	/**
	 * 删除选项
	 * @return
	 */
	@ResponseBody
	@RequestMapping("ajaxDelete")
	public JSONObject ajaxDelete(String quItemId) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try{
			quMultiFillblankManager.ajaxDelete(quItemId);
			json.put("flag", "1");
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return json;
	}
}