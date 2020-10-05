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
import com.key.dwsurvey.entity.QuScore;
import com.key.dwsurvey.entity.QuestionLogic;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.service.QuScoreManager;
import com.key.dwsurvey.service.QuestionManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wenjuan/quScore")
public class QuScoreController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private QuScoreManager quScoreManager;
	
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
		List<QuScore> quScores=entity.getQuScores();
		for (QuScore quScore : quScores) {
			strBuf.append("{id:'").append(quScore.getId());
			strBuf.append("',title:'").append(quScore.getOrderById()).append("'},");
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
		//isRequired 是否必选
		String isRequired=request.getParameter("isRequired");
		//hv 1水平显示 2垂直显示
		String hv=request.getParameter("hv");
		String randOrder=request.getParameter("randOrder");
		String cellCount=request.getParameter("cellCount");
		
//		String paramInt01=request.getParameter("paramInt01");//最小分
		String paramInt02=request.getParameter("paramInt02");//最大分
		
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
		entity.setQuType(QuType.SCORE);
		isRequired=(isRequired==null || "".equals(isRequired))?"0":isRequired;
		hv=(hv==null || "".equals(hv))?"0":hv;
		randOrder=(randOrder==null || "".equals(randOrder))?"0":randOrder;
		cellCount=(cellCount==null || "".equals(cellCount))?"0":cellCount;
		paramInt02=(paramInt02==null || "".equals(paramInt02))?"0":paramInt02;
		entity.setIsRequired(Integer.parseInt(isRequired));
		entity.setHv(Integer.parseInt(hv));
		entity.setRandOrder(Integer.parseInt(randOrder));
		entity.setCellCount(Integer.parseInt(cellCount));
		entity.setParamInt01(1);
		entity.setParamInt02(Integer.parseInt(paramInt02));
		//quOption
		Map<String, Object> optionNameMap=WebUtils.getParametersStartingWith(request, "optionValue_");
		List<QuScore> quScores=new ArrayList<QuScore>();
		for (String key : optionNameMap.keySet()) {
			String optionId=request.getParameter("optionId_"+key);
			Object optionName=optionNameMap.get(key);
			String optionNameValue=(optionName!=null)?optionName.toString():"";
			QuScore quScore=new QuScore();
			if("".equals(optionId)){
				optionId=null;
			}
			quScore.setId(optionId);
			optionNameValue=URLDecoder.decode(optionNameValue,"utf-8");
			quScore.setOptionName(optionNameValue);
			quScore.setOrderById(Integer.parseInt(key));
			quScores.add(quScore);
		}
		entity.setQuScores(quScores);
		
		Map<String, Object> quLogicIdMap=WebUtils.getParametersStartingWith(request, "quLogicId_");
		List<QuestionLogic> quLogics=new ArrayList<QuestionLogic>();
		for (String key : quLogicIdMap.keySet()) {
			String cgQuItemId=request.getParameter("cgQuItemId_"+key);
			String skQuId=request.getParameter("skQuId_"+key);
			String visibility=request.getParameter("visibility_"+key);
			String geLe=request.getParameter("geLe_"+key);
			String scoreNum=request.getParameter("scoreNum_"+key);
			String logicType=request.getParameter("logicType_"+key);
			
			Object quLogicId=quLogicIdMap.get(key);
			String quLogicIdValue=(quLogicId!=null)?quLogicId.toString():null;
			
			QuestionLogic quLogic=new QuestionLogic();
			if(geLe==null || "".equals(geLe)){
			    geLe="le";
			}
			if(scoreNum==null || "".equals(scoreNum)){
			    scoreNum="2";
			}
			quLogic.setId(quLogicIdValue);
			quLogic.setCgQuItemId(cgQuItemId);
			quLogic.setSkQuId(skQuId);
			quLogic.setVisibility(Integer.parseInt(visibility));
			quLogic.setTitle(key);
			quLogic.setLogicType(logicType);
			quLogic.setGeLe(geLe);
			quLogic.setScoreNum(Integer.parseInt(scoreNum));
			quLogics.add(quLogic);
		}
		entity.setQuestionLogics(quLogics);
		
		return entity;
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
			quScoreManager.ajaxDelete(quItemId);
			json.put("flag", "1");
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
