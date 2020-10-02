package com.sinosoft.sinoep.modules.zhbg.xxkh.question.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.services.QuestionService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.entity.XxkhQuestionQgroup;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.services.XxkhQuestionQgroupService;
import net.sf.json.JSONObject;

/**
 * 试题控制层
 * TODO 
 * @author 王磊
 * @Date 2018年7月25日 下午4:41:16
 */
@Controller
@RequestMapping("zhbg/xxkh/question")
public class QuestionController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private XxkhQuestionQgroupService xxkhQuestionQgroupService;

	/**
	 * 试题列表
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月25日 下午5:33:18
	 * @param pageImpl
	 * @param question
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询试题列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, Question question) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		PageImpl pageList = questionService.getPageList(pageable, pageImpl, question);
		return pageList;
	}
	
	/**
	 * 
	 * @Title: QuestionController.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.question.controller
	 * @Description: TODO回显试题列表 题的选中状态
	 * @author 颜振兴
	 * @date 2018年8月16日
	 * @param pageImpl
	 * @param question
	 * @param tizhuid
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询试题列表")
	@ResponseBody
	@RequestMapping("getTreeList")
	public PageImpl getTreeList(PageImpl pageImpl, Question question,String tizhuid) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		PageImpl pageList = questionService.getPageList(pageable, pageImpl, question);
		List<Question> rows = (List<Question>) pageList.getData().getRows();
		for(Question ti:rows) {
		XxkhQuestionQgroup check = xxkhQuestionQgroupService.isCheck(tizhuid, ti.getId());
			if (check!=null&&!check.equals("")) {
				ti.setIsCheck("OK");
			}else {
				ti.setIsCheck("");
			}
		}
		return pageList;
	}
	
	/**
	 * 保存试题
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月26日 下午3:23:57
	 * @param question
	 * @return
	 */
	//@SameUrlData
	@LogAnnotation(value = "save",opName = "保存试题")
	@RequestMapping("saveQuestion")
	@ResponseBody
	public JSONObject saveQuestion(@RequestBody Question question) {
		JSONObject json = new JSONObject();
		try {
			question = questionService.saveForm(question);
			json.put("flag", 1);
			json.put("questionId", question.getId());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}
	
	/**
	 * 根据试题id获得一个试题
	 * TODO 
	 * @author 王磊
	 * @Date 2018年7月30日 下午7:09:51
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "根据id获得试题")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(Question questionInfo) {
		JSONObject json = new JSONObject();
		questionInfo = questionService.getById(questionInfo);
		try {
			json.put("flag", "1");
			json.put("data",questionInfo);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * 根据id逻辑删除试题及其对应的选项
	 * @author 王磊
	 * @Date 2018年7月31日 下午4:13:46
	 * @param questionInfo
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "根据id删除试题")
	@ResponseBody
	@RequestMapping("deleteById")
	public JSONObject deleteById(Question questionInfo) {
		JSONObject json = new JSONObject();
		try {
			int n = questionService.delete(questionInfo);
			json.put("flag", ""+n+"");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * 根据id修改试题状态为提交
	 * 提交后不可以编辑试题
	 * @author 王磊
	 * @Date 2018年8月23日 下午7:27:01
	 * @param questionInfo
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "提交试题")
	@ResponseBody
	@RequestMapping("commitById")
	public JSONObject commitById(Question questionInfo) {
		JSONObject json = new JSONObject();
		try {
			int n = questionService.commitQuestionById(questionInfo.getId());
			json.put("flag", ""+n+"");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 *列表批量提交试题
	 * @author 王磊
	 * @Date 2018年9月20日 下午4:27:17
	 * @param questionIds
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "列表批量提交试题")
	@ResponseBody
	@RequestMapping("commitSelected")
	public JSONObject commitSelected(String questionIds) {
		JSONObject json = new JSONObject();
		try {
			int n = questionService.commitSelected(questionIds);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}

}
