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

import com.key.common.CheckType;
import com.key.common.QuType;
import com.key.dwsurvey.entity.QuCheckbox;
import com.key.dwsurvey.entity.QuestionLogic;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.service.QuCheckboxManager;
import com.key.dwsurvey.service.QuestionManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wenjuan/quCheck")
public class QuCheckboxController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private QuCheckboxManager quCheckboxManager;
	
	/**
	 * 保存复选
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月26日 上午9:42:51
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception quCheck
	 */
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
		//{id:'null',quItems:[{id:'null',title:'null'},{id:'null',title:'null'}]}
		StringBuffer strBuf=new StringBuffer();
		strBuf.append("{id:'").append(entity.getId());
		strBuf.append("',orderById:");
		strBuf.append(entity.getOrderById());
		strBuf.append(",quItems:[");
		List<QuCheckbox> quCheckboxs=entity.getQuCheckboxs();
		for (QuCheckbox quCheckbox : quCheckboxs) {
			strBuf.append("{id:'").append(quCheckbox.getId());
			strBuf.append("',title:'").append(quCheckbox.getOrderById()).append("'},");
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
		String hv=request.getParameter("hv");
		String randOrder=request.getParameter("randOrder");
		String cellCount=request.getParameter("cellCount");
		String contactsAttr=request.getParameter("contactsAttr");
		String contactsField=request.getParameter("contactsField");
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
		entity.setQuType(QuType.CHECKBOX);
		isRequired=(isRequired==null || "".equals(isRequired))?"0":isRequired;
		hv=(hv==null || "".equals(hv))?"0":hv;
		randOrder=(randOrder==null || "".equals(randOrder))?"0":randOrder;
		cellCount=(cellCount==null || "".equals(cellCount))?"0":cellCount;
		contactsAttr=(contactsAttr==null || "".equals(contactsAttr))?"0":contactsAttr;
		entity.setContactsAttr(Integer.parseInt(contactsAttr));
		entity.setContactsField(contactsField);
		entity.setIsRequired(Integer.parseInt(isRequired));
		entity.setHv(Integer.parseInt(hv));
		entity.setRandOrder(Integer.parseInt(randOrder));
		entity.setCellCount(Integer.parseInt(cellCount));
		Map<String, Object> optionNameMap=WebUtils.getParametersStartingWith(request, "optionValue_");
		List<QuCheckbox> quCheckboxs=new ArrayList<QuCheckbox>();
		for (String key : optionNameMap.keySet()) {
			String optionId=request.getParameter("optionId_"+key);
			String isNote=request.getParameter("isNote_"+key);
			String checkType=request.getParameter("checkType_"+key);
			String isRequiredFill=request.getParameter("isRequiredFill_"+key);
			
			Object optionName=optionNameMap.get(key);
			String optionNameValue=(optionName!=null)?optionName.toString():"";
			QuCheckbox quCheckbox=new QuCheckbox();
			if("".equals(optionId)){
				optionId=null;
			}
			quCheckbox.setId(optionId);
//			quRadio.setOptionTitle(key);
			optionNameValue=URLDecoder.decode(optionNameValue,"utf-8");
			quCheckbox.setOptionName(optionNameValue);
			quCheckbox.setOrderById(Integer.parseInt(key));
			isNote=(isNote==null || "".equals(isNote))?"0":isNote;
			checkType=(checkType==null || "".equals(checkType))?"NO":checkType;
			isRequiredFill=(isRequiredFill==null || "".equals(isRequiredFill))?"0":isRequiredFill;
			quCheckbox.setIsNote(Integer.parseInt(isNote));
			quCheckbox.setCheckType(CheckType.valueOf(checkType));
			quCheckbox.setIsRequiredFill(Integer.parseInt(isRequiredFill));
			quCheckboxs.add(quCheckbox);
		}
		entity.setQuCheckboxs(quCheckboxs);
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
	
	/**
	 * 删除选项
	 * @return
	 */
	@ResponseBody
	@RequestMapping("ajaxDelete")
	public JSONObject ajaxDelete(String quItemId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try{
			quCheckboxManager.ajaxDelete(quItemId);
			json.put("flag", "1");
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
