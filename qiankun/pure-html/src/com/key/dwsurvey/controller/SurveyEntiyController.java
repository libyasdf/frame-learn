package com.key.dwsurvey.controller;

import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyEntiy;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyEntiyService;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.constant.GlobalNames;
import com.sinosoft.sinoep.common.servlet.HttpContext;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.info.authority.common.util.InfoAuthorityUtils;
import com.sinosoft.sinoep.modules.system.notice.common.SysNoticeConstants;
import com.sinosoft.sinoep.modules.system.notice.common.util.NoticeUtils;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeBack;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerifyUser;
import com.sinosoft.sinoep.modules.system.notice.services.SysNoticeBackService;
import com.sinosoft.sinoep.modules.system.notice.services.SysNoticeGrupService;
import com.sinosoft.sinoep.modules.system.notice.services.SysNoticeService;
import com.sinosoft.sinoep.modules.system.notice.services.SysNoticeVerifyService;
import com.sinosoft.sinoep.user.entity.DeptAllInfo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO 通知公告控制类
 * @author 李利广
 * @Date 2018年9月5日 下午4:08:31
 */
@Controller
@RequestMapping("survey/surveyDesign")
public class SurveyEntiyController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SurveyEntiyService surveyEntiyService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SysWaitNoflowService waitNoflowService;

	@Autowired
	private SurveyDirectoryManager directoryManager;

	@Autowired
	private SysNoticeGrupService sysNoticeGrupService;
	

	
	/**
	 * TODO 保存调查问卷
	 * @author 杜建松
	 * @Date 2018年9月5日 下午4:17:37
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存调查问卷")
	@ResponseBody
	@RequestMapping("save")
	public JSONObject saveSurveyEntiy(SurveyEntiy surveyEntiy){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			surveyEntiy = surveyEntiyService.saveSurvey(surveyEntiy);
			json.put("flag", "1");
			json.put("msg", "问卷调查保存成功");
			json.put("data", surveyEntiy);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "问卷调查保存失败！");
		}
		return json;
	}

	
	/**
	 * 发送消息提醒
	 * TODO 
	 * @author 李利广
	 * @Date 2018年10月22日 上午10:38:06
	 * @param
	 */
	private void sendMsg(final String surveyId,HttpServletRequest request,String title){
		try {
			String orgid = UserUtil.getCruOrgId();
			SurveyDirectory survey = directoryManager.findById(surveyId);
			String htmlPath = survey.getHtmlPath();
			//创建一个单线程的线程池
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.execute(new Thread(){
				@Override
				public void run() {
					HttpServletRequest req = request;
					HttpSession session = req.getSession(false);
					/*HttpContext.setRequest(req);*/
					HttpContext.setContext(new HttpContext(new HashMap<>()));
					HttpContext.setSession(session);
					//获取权限
					Map<String,List<Map<String, Object>>> authMap = InfoAuthorityUtils.getAuthListByContentId(surveyId);
					//获取拥有权限的userid
					JSONArray jsonArray = NoticeUtils.getUser(authMap,orgid);
					//给拥有权限的人员发送消息
					if (!jsonArray.isEmpty()) {
						List<String> userIdList = new ArrayList<>();
						for(int i = 0;i < jsonArray.size();i++){
							JSONObject json = jsonArray.getJSONObject(i);
							String userId = json.getString("userid");
							if(!userIdList.contains(userId)){
								SysWaitNoflow waitNoflowEntity  = new SysWaitNoflow();
								waitNoflowEntity.setReceiveUserId(userId);
								waitNoflowEntity.setReceiveUserName(json.getString("username"));
								String deptId = userInfoService.getDeptId(userId);
								waitNoflowEntity.setReceiveDeptId(json.getString("deptid"));
								waitNoflowEntity.setReceiveDeptName("");
								waitNoflowEntity.setRolesNo("");
								waitNoflowEntity.setTableName("t_survey_answer");
								waitNoflowEntity.setTableId("id");
								waitNoflowEntity.setSourceId(surveyId);
								waitNoflowEntity.setOpName("调查问卷:"+title);
								waitNoflowEntity.setDaibanUrl(htmlPath+"?id="+surveyId+"&oper=EDIT");
								waitNoflowEntity.setTitle("调查问卷:"+title);
								waitNoflowEntity = waitNoflowService.saveWaitNoflow(waitNoflowEntity,"调查问卷:"+title,"调查问卷:"+title,htmlPath+"?id="+surveyId+"&oper=EDIT");
								userIdList.add(userId);
							}
						}
					}
				}
			});
			executor.shutdown();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	/**
	 * TODO 删除调查问卷
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:45:51
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除调查问卷")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delSurvey(String surveyId){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			Boolean boo = surveyEntiyService.delSurvey(surveyId);
			if (boo) {
				res.put("flag", "1");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "删除失败！");
		}
		return res;
	}
	
	/**
	 * TODO 获取一条数据
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:45:51
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询一条调查问卷信息")
	@ResponseBody
	@RequestMapping("view")
	public JSONObject getView(String surveyId){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			SurveyEntiy surveyEntiy = surveyEntiyService.getView(surveyId);
			res.put("flag", "1");
			res.put("data", surveyEntiy);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "读取调查问卷数据失败！");
		}
		return res;
	}

	/**
	 * TODO 获取一条数据
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:45:51
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询一条调查问卷信息")
	@ResponseBody
	@RequestMapping("getIdView")
	public JSONObject getIdView(String id){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			SurveyEntiy surveyEntiy = surveyEntiyService.getIdView(id);
			res.put("flag", "1");
			res.put("data", surveyEntiy);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "读取调查问卷数据失败！");
		}
		return res;
	}

	/**
	 * TODO 更新流程状态
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:40:55
	 * @param
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "更新流程状态")
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id,String subflag,HttpServletRequest request){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try{
			SurveyEntiy surveyEntiy = surveyEntiyService.updateFlag(id,subflag);
			json.put("flag",subflag);
			if(surveyEntiy.getSubflag().equals("5")){
				//发送消息通知
				sendMsg(surveyEntiy.getSurveyId(),request,surveyEntiy.getTitle());
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return json;
	}

	/**
	 * TODO 查询调查问卷群组列表
	 * @author 李利广
	 * @Date 2018年9月10日 上午9:56:11
	 * @param page
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询调查问卷群组列表")
	@ResponseBody
	@RequestMapping("getSurveyGrupList")
	public PageImpl getSurveyGrupList(PageImpl page,String sysGrupName){
		page.setFlag("0");
		try {
			page = sysNoticeGrupService.getSurveyGrupList(page, sysGrupName);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			page.setMsg("获取部门调查问卷群组列表异常！");
		}
		return page;
	}
}
