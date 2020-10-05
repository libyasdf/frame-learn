package com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.constant.XXKHCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.services.QuestionService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.entity.XxkhQuestionQgroup;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.services.XxkhQuestionQgroupService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces.XxkhQuestionGroupService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author 颜振兴
   *     时间：2018年8月21日
 *	XxkhQuestionGroupController
 */
@Controller
@RequestMapping("/zhbg/xxkh/qusetiongroup")
public class XxkhQuestionGroupController {
		
	private Logger log = LoggerFactory.getLogger(this.getClass());
		@Autowired
		private XxkhQuestionGroupService service;
		
		@Autowired
		private XxkhQuestionQgroupService xxkhQuestionQgroupService;
		
		@Autowired
		private QuestionService questionService;
		
		
		@LogAnnotation(opName="查询试题数量",value="query")
		@RequestMapping("/getLevelCount")
		@ResponseBody
		public JSONObject getLevelCount(XxkhQuestionGroup group) {
			JSONObject json = new JSONObject();
				json.put("flag", "1");
				XxkhQuestionGroup levelCount = service.getLevelCount(group);
				XxkhQuestionGroup getbyId = service.getbyId(group);
				Integer zongfen= (Integer.parseInt(levelCount.getSimpleCount()==null?0+"":levelCount.getSimpleCount())+Integer.parseInt(levelCount.getNormalCount()==null?0+"":levelCount.getNormalCount())+Integer.parseInt(levelCount.getHardCount()==null?0+"":levelCount.getHardCount()))*Integer.parseInt(getbyId.getEveryScore());
				XxkhQuestionGroup update = service.update(group.getId(), levelCount.getSimpleCount()==null?0+"":levelCount.getSimpleCount(), levelCount.getNormalCount()==null?0+"":levelCount.getNormalCount(), levelCount.getHardCount()==null?0+"":levelCount.getHardCount(), "",zongfen+"","0");
				json.put("data", update);
			return json;
		}
		
		@LogAnnotation(opName="查询试题数量",value="query")
		@RequestMapping("/getQuestionCount")
		@ResponseBody
		public JSONObject getQusetionCount(Question question,Integer shuliang) {
			JSONObject json = new JSONObject();
			int questionCount = 0;	
			json.put("flag", "0");
				/*if (question.getType().equals("juj")) {
					question.setType("st");
					questionCount = questionService.getQuestionJuCount(question);
				}else {*/
					questionCount = questionService.getQuestionCount(question);
//				}
			json.put("number", questionCount);
			if (shuliang<=questionCount) {
				json.put("flag", "1");
			}
			return json;
		}
		
		@LogAnnotation(opName="保存一个人工新题组",value="save")
		@RequestMapping("/save")
		@ResponseBody
		public JSONObject save(XxkhQuestionGroup group) {
			JSONObject json = new JSONObject();
					json.put("flag", "0");
			XxkhQuestionGroup save = service.save(group);
			if (save!=null && !save.getId().equals("")) {
				json.put("flag", "1");
				json.put("data",save );
			}
			return json;
		}
		
		@LogAnnotation(opName="保存一个自动新题组",value="save")
		@RequestMapping("/autosave")
		@ResponseBody
		public JSONObject autosave(XxkhQuestionGroup group) {
			JSONObject json = new JSONObject();
					json.put("flag", "0");
			XxkhQuestionGroup save = service.autosave(group);
			if (save!=null && !save.getId().equals("")) {
				json.put("flag", "1");
				json.put("data",save );
			}
			return json;
		}
		
		@LogAnnotation(opName="修改题组",value="update")
		@RequestMapping("/update")
		@ResponseBody
		public JSONObject update(XxkhQuestionGroup group) {
			JSONObject json = new JSONObject();
					json.put("flag", "0");
			XxkhQuestionGroup save = service.update(group);
			if (save!=null && !save.getId().equals("")) {
				json.put("flag", "1");
				json.put("data",save );
			}
			return json;
		}
		
		@LogAnnotation(opName="删除题组",value="delete")
		@RequestMapping("/delete")
		@ResponseBody
		public JSONObject delete(XxkhQuestionGroup group) {
			JSONObject json = new JSONObject();
					json.put("flag", "0");
			int i = service.delete(group);
			if (i>0) {
				json.put("flag", "1");
			}
			return json;
		}
		
		@LogAnnotation(opName="自动组题",value="save")
		@RequestMapping("/autoZujuan")
		@ResponseBody
		public JSONObject autoZujuan(String startZujuan,String type,String deptType) {
			if (type.indexOf(XXKHCommonConstants.XXKH_TYPE[0])>-1) {
				type=XXKHCommonConstants.XXKH_TYPE_ST[0];
			}else if (type.indexOf(XXKHCommonConstants.XXKH_TYPE[1])>-1) {
				type=XXKHCommonConstants.XXKH_TYPE_ST[1];
			}else if (type.indexOf(XXKHCommonConstants.XXKH_TYPE[2])>-1) {
				type=XXKHCommonConstants.XXKH_TYPE_ST[2];
			}
			if(!deptType.equals("")) {
				type=deptType;
			}
			JSONObject json = new JSONObject();
			JSONArray leixings = JSONArray.fromObject(startZujuan); 
			if(leixings.size()>0) {
				for (int i = 0; i < leixings.size(); i++) {
					JSONObject object = leixings.getJSONObject(i);
					for(int j=0;j<=4;j++) {
						common(object, XXKHCommonConstants.QUESTION_TYPE[j], "1",type);
						common(object, XXKHCommonConstants.QUESTION_TYPE[j], "2",type);
						common(object, XXKHCommonConstants.QUESTION_TYPE[j], "3",type);
					}
//					common(object, XXKHCommonConstants.QUESTION_TYPE[0], "1",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[0], "2",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[0], "3",type);
//					
//					common(object, XXKHCommonConstants.QUESTION_TYPE[1], "1",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[1], "2",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[1], "3",type);
//					
//					common(object, XXKHCommonConstants.QUESTION_TYPE[2], "1",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[2], "2",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[2], "3",type);
//					
//					common(object, XXKHCommonConstants.QUESTION_TYPE[3], "1",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[3], "2",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[3], "3",type);
//					
//					common(object, XXKHCommonConstants.QUESTION_TYPE[4], "1",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[4], "2",type);
//					common(object, XXKHCommonConstants.QUESTION_TYPE[4], "3",type);
			
				}
			}
			
			return json;
		}
		
		
		public boolean common(JSONObject object,String leixing,String difficultyLevel,String type) {
			String questionType ="";
			if (leixing.equals(XXKHCommonConstants.QUESTION_TYPE[0])) questionType="1";
			if (leixing.equals(XXKHCommonConstants.QUESTION_TYPE[1])) questionType="2";
			if (leixing.equals(XXKHCommonConstants.QUESTION_TYPE[2])) questionType="3";
			if (leixing.equals(XXKHCommonConstants.QUESTION_TYPE[3])) questionType="4";
			if (leixing.equals(XXKHCommonConstants.QUESTION_TYPE[4])) questionType="5";
			
			String nandujibie ="";
			if(difficultyLevel.equals("1")) nandujibie=XXKHCommonConstants.DIFFICULTY_LEVEL[0];
			if(difficultyLevel.equals("2")) nandujibie=XXKHCommonConstants.DIFFICULTY_LEVEL[1];
			if(difficultyLevel.equals("3")) nandujibie=XXKHCommonConstants.DIFFICULTY_LEVEL[2];
			if (object.toString().equals("null")  || object.getJSONObject(leixing).size()<1) {
				return true;
			}
			if (((String)object.getJSONObject(leixing).get(nandujibie)).equals("0")) {
				return true;
			}
			if(!object.toString().equals("null") && object.getJSONObject(leixing).size()>0) {
				Question question = new Question();
				question.setDifficultyLevel(difficultyLevel);
				question.setQuestionType(questionType);
				question.setType(type);
				String groupId = (String)object.getJSONObject(leixing).get("groupId");
				String jiandan = (String)object.getJSONObject(leixing).get("jiandan");
				String yiban = (String)object.getJSONObject(leixing).get("yiban");
				String kunnan = (String)object.getJSONObject(leixing).get("kunnan");
				String everyScore = (String)object.getJSONObject(leixing).get("everyScore");
				Integer fullScore = (Integer.parseInt(jiandan)+Integer.parseInt(yiban)+Integer.parseInt(kunnan))*Integer.parseInt(everyScore);
				service.update(groupId,jiandan,yiban,kunnan,everyScore,fullScore+"","1");
				//随机在题库中选择指定数量的题
				List<Question> List = questionService.radomList(question, (String)object.getJSONObject(leixing).get(nandujibie));
				for(Question q:List) {
				XxkhQuestionQgroup qgroup = new XxkhQuestionQgroup();
				qgroup.setQuestionId(q.getId());
				qgroup.setQuestionGroupId(groupId);
				XxkhQuestionQgroup autoSave = xxkhQuestionQgroupService.autoSave(qgroup);
					if(autoSave.getId().equals("")) {
						log.debug("组卷异常");
						return false;
					}
				}
			}
			return true;
		}
}
