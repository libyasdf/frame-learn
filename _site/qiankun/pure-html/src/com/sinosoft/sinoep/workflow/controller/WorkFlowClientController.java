package com.sinosoft.sinoep.workflow.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.workflow.model.FlowClientData;
import com.sinosoft.sinoep.workflow.service.WorkFlowClientService;
import com.sinosoft.sinoep.workflow.util.WorkFlowUtil;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：工作流操作控制类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Controller
@RequestMapping(value = "flowService")
public class WorkFlowClientController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WorkFlowClientService workFlowClientService;

    /**
     * 
     * <B>方法名称：提交工作流</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:55:07
     * @param request
     * @param response
     * @return JSONObject
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping(value = "toFlow", produces = "application/json; charset=utf-8")
    public JSONObject subToFlow(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String userid = UserUtil.getCruUserId();
        String deptid = UserUtil.getCruDeptId();
        String jsonPar = request.getParameter("jsonPar") == null ? "" : request.getParameter("jsonPar");
        jsonPar = java.net.URLDecoder.decode(jsonPar, "utf-8");
        String remindtype = request.getParameter("remindtype");
        FlowClientData data = new FlowClientData(request);
        data.setDeptid(deptid);
        String extendattr = request.getParameter("extendattr");
        return workFlowClientService.submitToFlow(userid, jsonPar, remindtype, data, extendattr);
    }

    /**
     * 
     * <B>方法名称：点击待办和已办调用的服务</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:56:15
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "getFlowData", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getFlowData(HttpServletRequest request, HttpServletResponse response) {
        String userid = UserUtil.getCruUserId();
        FlowClientData formClientData = new FlowClientData(request);
        return workFlowClientService.getFlowData(userid, formClientData);
    }

    /**
     * 
     * <B>方法名称：办结</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月8日 上午11:19:13
     * @param workitemid：工作项id
     * @param subflag：状态
     * @param recordid：业务主键id
     * @param idea 意见信息
     * @param response
     * @return void
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "finishFlow")
    public void finishFlow(String workitemid, String subflag, String recordid,String flag,String idea,
            HttpServletResponse response) throws IOException {
        JSONObject json = workFlowClientService.finishFlow(workitemid, subflag, flag,recordid,idea);
        WorkFlowUtil.setResponse(response, json);
    }

    /**
     * 
     * <B>方法名称：收回</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午10:56:23
     * @param workitemid：已办工作项ID
     * @param isBackIdea：1是收回意见，0是不收回
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value = "takeBack")
    public void takeBack(String workitemid, String isBackIdea, HttpServletResponse response)
            throws IOException {
        String type = ConfigConsts.SERVICE_TYPE;
        JSONObject json = workFlowClientService.cancelFlow(workitemid, isBackIdea, type);
        WorkFlowUtil.setResponse(response, json);
    }

    /**
     * 
     * <B>方法名称：撤办</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午10:57:24
     * @param workitemid：已办工作项ID
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value = "removeFlow")
    public void removeFlow(String workitemid, HttpServletResponse response) throws IOException {
        String type = ConfigConsts.SERVICE_TYPE;
        JSONObject json = workFlowClientService.removeFlow(workitemid, type);
        WorkFlowUtil.setResponse(response, json);
    }

    /**
     * 
     * <B>方法名称：恢复撤办</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午10:58:25
     * @param workitemid：工作项ID
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value = "resumeFlow")
    public void resumeFlow(String workitemid, HttpServletResponse response) throws IOException {
        String type = ConfigConsts.SERVICE_TYPE;
        JSONObject json = workFlowClientService.resumeFlow(workitemid, type);
        WorkFlowUtil.setResponse(response, json);
    }

    /**
     * 
     * <B>方法名称：增加会签</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午10:58:25
     * @param workitemid：工作项ID
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value = "getsignatureFlow")
    public void getsignatureFlow(String workitemid, HttpServletResponse response) throws IOException {
        String type = ConfigConsts.SERVICE_TYPE;
        JSONObject json = workFlowClientService.getsignatureFlow(workitemid, type);
        WorkFlowUtil.setResponse(response, json);
    }

    /**
     * 
     * <B>方法名称：删除会签</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午10:58:25
     * @param workitemid：工作项ID
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value = "delsignatureFlow")
    public void delsignatureFlow(String workitemid, HttpServletResponse response) throws IOException {
        String type = ConfigConsts.SERVICE_TYPE;
        JSONObject json = workFlowClientService.delsignatureFlow(workitemid, type);
        WorkFlowUtil.setResponse(response, json);
    }

    /**
     * <B>方法名称：跳节点</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午10:58:59
     * @param request
     * @param response
     * @return void
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/jumpToWfleve")
    public void jumpToWfleve(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject param = new JSONObject();
        param.put("workitemid", request.getParameter("workitemid"));
        param.put("wfleveid", request.getParameter("wfleveid"));
        param.put("paticipant", request.getParameter("rolesId"));
        String type = ConfigConsts.SERVICE_TYPE;
        JSONObject json = workFlowClientService.jumpToWfleve(param, type);
        WorkFlowUtil.setResponse(response, json);
    }

    /**
     * 
     * <B>方法名称：公文管理办结流程</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午10:59:21
     * @param workitemid：工作项id
     * @param subflag：状态
     * @param recordid：记录id
     * @param response
     * @return void
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "completeFlow")
    public void completeFlow(String workitemid, String subflag, String recordid,
            HttpServletResponse response, String idea,String flag) throws IOException {
        JSONObject json = workFlowClientService.completeFlow(workitemid, subflag, recordid, idea,flag);
        WorkFlowUtil.setResponse(response, json);
    }

    /**
     * 
     * <B>方法名称：签发信息</B><BR>
     * <B>概要说明：此方法是节点事件处理的一个例子，主要作用是经过签发节点之后，下一个流程节点配置此节点事件后，可以获取到上一个节点的签发人，签发时间等信息</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月10日 上午9:39:30
     * @param request
     * @return void
     * @throws IOException
     */
    @SuppressWarnings("unused")
	@ResponseBody
    @RequestMapping(value = "signInfo", produces = "application/json; charset=utf-8")
    public void test1(HttpServletRequest request) throws IOException {
        //工作流返回的相关数据
        JSONObject contextJson = JSONObject.parseObject(request.getParameter("context"));
        String result = workFlowClientService.saveSignInfo(contextJson);
    }
    
    /**
     * TODO 取待办配置数据
     * @author 李利广
     * @Date 2018年4月8日 下午3:26:28
     * @param workItemId 待办工作项ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getDaiBanData")
    public JSONObject getDaiBanData(String workItemId){
    	String type = ConfigConsts.SERVICE_TYPE;
    	return workFlowClientService.getDaiBanData(workItemId, type);
    }
    
    /**
     * TODO 保存临时意见
     * @author 李利广
     * @Date 2018年4月8日 下午7:14:47
     * @param workItemId
     * @param idea
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveTempIdea")
    public JSONObject saveTempIdea(String workItemId, String idea,String ideaName){
    	String type = ConfigConsts.SERVICE_TYPE;
    	JSONObject json = new JSONObject();
    	json.put("res", workFlowClientService.saveTempIdea(workItemId, type,idea,ideaName));
    	return json;
    }
    
    /**
     * TODO 获取临时意见
     * @author 李利广
     * @Date 2018年4月8日 下午6:59:18
     * @param workItemId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getTempIdea")
    public JSONObject getTempIdea(String workItemId){
    	String type = ConfigConsts.SERVICE_TYPE;
    	JSONObject json = new JSONObject();
    	json.put("tempIdea", workFlowClientService.getTempIdea(workItemId, type));
    	return json;
    }
    
    /**
     * 获取启动节点信息
     * TODO 
     * @author 李利广
     * @Date 2018年10月31日 下午2:22:47
     * @param fileTypeId
     * @param workflowId
     * @param deptId
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getStartWflevel")
    public JSONObject getStartWflevel(String fileTypeId,String workflowId,String deptId,String userId){
    	JSONObject json = new JSONObject();
    	json.put("flag", "0");
    	try {
			JSONArray jsonArray = workFlowClientService.getStartWflevel(fileTypeId,workflowId,deptId,userId);
			json.put("flag", "1");
			json.put("data", jsonArray);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
    	return json;
    }

    /**
     * 根据流程ID与业务ID删除流程信息（包括：待办、已办、流程记录、意见信息）
     * @auther 李利广
     * @Date 2019年1月7日 下午2:22:47
     * @param workflowId
     * @param recordId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "removeFlowInfo")
    public JSONObject removeFlowInfo(String workflowId,String recordId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
            json = workFlowClientService.deleteFlowInfo(workflowId,recordId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            json.put("msg","删除流程信息异常");
        }
        return json;
    }
}
