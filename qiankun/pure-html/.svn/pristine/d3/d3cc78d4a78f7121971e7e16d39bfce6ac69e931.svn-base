package com.sinosoft.sinoep.workflow.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.ep.webform.ext.FormRunnerService;
import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.api.user.vo.MessageUser;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CookieUtil;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.workflow.model.FlowClientData;
import com.sinosoft.sinoep.workflow.model.WorkFlowVO;
import com.sinosoft.sinoep.workflow.service.WorkFlowService;
import com.sinosoft.sinoep.workflow.util.WorkFlowUtil;

import workflow.spring.FlowDataService;
import workflow.vo.FlowWorkflowVO;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：工作流信息类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：获取工作流待办，已办，节点等信息</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Controller
@RequestMapping("workflow")
public class WorkFlowController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    WorkFlowService workFlowService;
    @Autowired
    FlowDataService flowDataService;

    /**
     * 查询待办列表
     * TODO 
     * @author 李利广
     * @Date 2018年3月22日 下午2:59:45
     * @param request
     * @param pageImpl
     * @param timeRange
     * @param title
     * @return
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping("daiblist")
    public PageImpl getWaitDo(HttpServletRequest request,String workFlowId,String fileTypeId, PageImpl pageImpl,String timeRange,String title,String recordId) throws UnsupportedEncodingException {
    	String attr = request.getParameter("attr");
        String attr1 = request.getParameter("attr1");
    	String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
        return workFlowService.getWaitDoList(pageImpl,workFlowId,fileTypeId, endDate, startDate, title,attr,attr1,recordId);
    }

    /**
     * 
     * <B>方法名称：获取指定流程类型的已办列表(接口)</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：李利广
     * @cretetime:2018年1月4日 下午6:37:13
     * @param request
     * @return Page
     * @throws UnsupportedEncodingException
     */
    @LogAnnotation(value = "query",opName = "查询已办列表")
    @ResponseBody
    @RequestMapping("getHadDone")
    public PageImpl getHadDoneByFlow(HttpServletRequest request,PageImpl pageImpl,String timeRange,String title,String workFlowId,String fileTypeId,String stattag) throws UnsupportedEncodingException {
        String userid = UserUtil.getCruUserId();
        String attr = request.getParameter("attr");
        String attr1 = request.getParameter("attr1");
        String beginTime = "";
		String endTime = "";
		if (StringUtils.isNotBlank(timeRange)) {
			beginTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			if(!beginTime.equals("")) {
				beginTime = beginTime + " 00:00:00";
			}
			if(!endTime.equals("")) {
				endTime = endTime + " 23:59:59";
			}
		}
        return workFlowService.getHanDone(userid,workFlowId,fileTypeId, pageImpl, endTime, beginTime, title,attr,attr1,stattag);
    }

    /**
     * TODO 获取指定流程类型的已办列表
     * @author 李利广
     * @Date 2019年01月15日 15:08:49
     * @param request
     * @param pageImpl
     * @param timeRange
     * @param title
     * @param workFlowId
     * @param fileTypeId
     * @param stattag
     * @return com.sinosoft.sinoep.common.util.PageImpl
     */
    @LogAnnotation(value = "query",opName = "查询已办列表")
    @ResponseBody
    @RequestMapping("getFlowReadList")
    public PageImpl getFlowReadList(HttpServletRequest request,PageImpl pageImpl,String timeRange,String title,String workFlowId,String fileTypeId,String stattag) throws UnsupportedEncodingException {
        String userid = UserUtil.getCruUserId();
        String attr = request.getParameter("attr");
        String attr1 = request.getParameter("attr1");
        String beginTime = "";
        String endTime = "";
        if (StringUtils.isNotBlank(timeRange)) {
            beginTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
            endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
            if(!beginTime.equals("")) {
                beginTime = beginTime + " 00:00:00";
            }
            if(!endTime.equals("")) {
                endTime = endTime + " 23:59:59";
            }
        }
        return workFlowService.getFlowReadList(userid,workFlowId,fileTypeId, pageImpl, endTime, beginTime, title,attr,attr1,stattag);
    }

    /**
     * 
     * <B>方法名称：获取已办数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:02
     * @param workitemid:工作项ID
     * @return JSONObject
     */
    @ResponseBody
    @RequestMapping("getYiBanData")
    public JSONObject getYiBanData(String workitemid) {
        String type = ConfigConsts.SERVICE_TYPE;
        return workFlowService.getYiBanData(workitemid, type);
    }

    /**
     * 
     * <B>方法名称：获取跳转节点列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:37:24
     * @param request
     * @return Page
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping("/getJumpNodeList")
    public Page getJumpNodeList(HttpServletRequest request) throws UnsupportedEncodingException {
        String userid = UserUtil.getCruUserId();
        String pageNum = request.getParameter("pageNum");
        String type = ConfigConsts.SERVICE_TYPE;
        return workFlowService.getJumpNodeList(userid, pageNum, type);
    }

    /**
     * 
     * <B>方法名称：获取流程列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:37:36
     * @param request
     * @return JSONObject
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping(value = "getworkflow", produces = "application/json;charset=UTF-8")
    public JSONObject getworkflow(HttpServletRequest request) throws UnsupportedEncodingException {
        String userId = UserUtil.getCruUserId();
        String type = ConfigConsts.SERVICE_TYPE;
        String deptid = UserUtil.getCruDeptId();
        return workFlowService.getworkflow(userId, type, deptid);
    }

    /**
     * 
     * <B>方法名称：获取流程信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:38:26
     * @param fileTypeId：文件类型id
     * @param workflowId：工作项id
     * @param response
     * @return void
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/getWorkflowVO")
    public void getWorkflowVO(String fileTypeId, String workflowId, HttpServletResponse response) throws IOException {
        String res = flowDataService.getWorkflowVO(fileTypeId, workflowId);
        WorkFlowUtil.setResponse(response, JSONObject.parseObject(res));
    }

    /**
     * 
     * <B>方法名称：统计部门</B><BR>
     * <B>概要说明：通过部门统计工作流信息</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:39:03
     * @param request
     * @param response
     * @param start 开始时间
     * @param end 结束时间
     * @param year 历年
     * @param deptname 部门名字
     * @param pageNum 当前页
     * @param size 每页条数
     * @return Page
     */
    @ResponseBody
    @RequestMapping("/tjdept")
    public Page tjdept(HttpServletRequest request, HttpServletResponse response, String start, String end,
            String year, String deptname, Integer pageNum, Integer size) {
        //根据部门名称获取部门id
        List<Dept> listDept = null;
        if (StringUtils.isNotBlank(deptname)) {
            listDept = userInfoService.getDeptByNmAndOrgId(deptname, null);
        }
        String deptid = "";
        if (listDept != null && listDept.size() > 0) {//
            for (Dept dept : listDept) {
                deptid += dept.getDeptid() + ",";
            }
            if (!"".equals(deptid)) {
                deptid = deptid.substring(0, (deptid.length() - 1));
            }
        }
        return workFlowService.getTjdept(start, end, year, deptid, pageNum, size);
    }

    /**
     * 
     * <B>方法名称：统计用户</B><BR>
     * <B>概要说明：通过用户统计工作流信息</B><BR>
     * 
     * @param request
     * @param response
     * @param start 开始时间
     * @param end 结束时间
     * @param year 历年
     * @param username 用户名字
     * @param pageNum 当前页
     * @param size 每页条数
     * @return Page
     */
    @ResponseBody
    @RequestMapping("/tjuser")
    public Page tjuser(HttpServletRequest request, HttpServletResponse response, String start, String end,
            String year, String username, Integer pageNum, Integer size) {
        String userid = "";
        //根据用户名获取用户id
        if (StringUtils.isNotBlank(username)) {
            MessageUser messageUser = userInfoService.getUserInfoByName(username);
            List<UserInfo> listUser = messageUser.getUserInfo();
            if (listUser != null && listUser.size() > 0) {
                for (UserInfo userInfo : listUser) {
                    userid += userInfo.getUid() + ",";
                }
                if (StringUtils.isNotBlank(userid)) {
                    userid = userid.substring(0, (userid.length() - 1));
                }
            }
        }
        return workFlowService.getTjUser(start, end, year, userid, pageNum, size);
    }

    /**
     * 
     * <B>方法名称：获取年份</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:43:18
     * @return List<Map<String,Object>>
     */
    @ResponseBody
    @RequestMapping("/getyear")
    public List<Map<String, Object>> getYear() {
        return workFlowService.getYear();
    }

    /**
     * 
     * <B>方法名称：获取拟稿状态下的按钮</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:56:29
     * @param filetypeid：文件类型
     * @param workflowid：工作项id
     * @param request
     * @return JSONArray
     */
    @ResponseBody
    @RequestMapping(value = "getStartWfButton", produces = "application/json;charset=UTF-8")
    public JSONArray getStartWfButton(String filetypeid, String workflowid, HttpServletRequest request) {
        String userid = UserUtil.getCruUserId();
        return workFlowService.getStartWfButton(filetypeid, workflowid, userid);
    }

    /**
     * 
     * <B>方法名称：获取表单URL</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:58:58
     * @param formid：表单id
     * @return String
     */
    @ResponseBody
    @RequestMapping("/getformurl")
    public String getFormUrl(String formid) {
        FormRunnerService formRunnerService = (FormRunnerService) SpringBeanUtils.getBean("formRunnerService");
        JSONObject Json = new JSONObject();
        Json.put("subId", ConfigConsts.SYSTEM_ID);
        Json.put("formId", formid);
        return formRunnerService.getFormUrl(Json.toString());
    }

    /**
     * 
     * <B>方法名称：获取图形化监控数据</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:03:07
     * @param sysId：系统id
     * @param fileTypeId：文件类型id
     * @param workflowId：待办工作项id
     * @param recordId：业务记录id
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value = "getFlowStatus")
    public void getFlowStatus(String sysId, String fileTypeId, String workflowId, String recordId,
            HttpServletResponse response) throws IOException {
        String type = ConfigConsts.SERVICE_TYPE;
        String res = workFlowService.getFlowStatus(sysId, fileTypeId, workflowId, recordId, type);
        WorkFlowUtil.setResponse(response, JSONObject.parseObject(res));
    }

    /**
     * 
     * <B>方法名称：获取文字式流程记录</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:04:25
     * @param fileTypeId:文件类型id
     * @param recordId：业务主键id
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value = "getflowcourse")
    public void getFlowCourse(String fileTypeId, String recordId, HttpServletResponse response)
            throws IOException {
        String type = ConfigConsts.SERVICE_TYPE;
        String res = workFlowService.getFlowCourse(fileTypeId, recordId, type);
        WorkFlowUtil.setArrayResponse(response, JSONArray.parseArray(res));
    }

    /**
     * 
     * <B>方法名称：获取正式意见</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午11:02:55
     * @param request
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value = "getformalidea")
    public void getFormalIdea(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        FlowClientData data = new FlowClientData(request);
        String type = ConfigConsts.SERVICE_TYPE;
        String res = workFlowService.getFormalIdea(data, type);
        WorkFlowUtil.setArrayResponse(response, JSONArray.parseArray(res));
    }

    /**
     * 
     * <B>方法名称：获取指定节点的参与者</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午11:03:30
     * @param request
     * @param response
     * @return void
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/getJumpWfAppointWrite")
    public void getJumpWfAppointWrite(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String wfleveId = request.getParameter("wfleveId");
        String type = ConfigConsts.SERVICE_TYPE;
        JSONArray json = workFlowService.getJumpWfAppointWrite(wfleveId, type);
        WorkFlowUtil.setArrayResponse(response, json);
    }

    /**
     * 
     * <B>方法名称：获取退回意见</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月8日 下午2:19:45
     * @param request
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value = "getBackIdea")
    public void getBackIdea(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FlowClientData data = new FlowClientData(request);
        String res = workFlowService.getBackIdea(data);
        WorkFlowUtil.setArrayResponse(response, JSONArray.parseArray(res));
    }

    /**
     * 
     * <B>方法名称：获取已经办理的流程节点</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "getJumpWflevePassed")
    public void getJumpWflevePassed(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String workitemid = request.getParameter("workitemid");
        String type = ConfigConsts.SERVICE_TYPE;
        JSONObject json = workFlowService.getJumpWflevePassed(workitemid, type);
        WorkFlowUtil.setResponse(response, json);
    }

    /**
     * 
     * <B>方法名称：获取所有流转中的流程</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月12日 下午5:57:20
     * @param pageNum：当前页
     * @param size：每页条数
     * @return void
     */
    @ResponseBody
    @RequestMapping("/getSubflagFlow")
    public Page getSubflagFlow(String title, String start, String end, Integer pageNum, Integer size) {
        return workFlowService.getSubflagFlow(title, start, end, pageNum, size);
    }
    
    /**
     * 收回重办，完成后，跳转到待办页面，待办的URL
     * @param request
     * @param workFlowId
     * @param fileTypeId
     * @param pageImpl
     * @param timeRange
     * @param title
     * @param recordId
     * @return
     */
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping("/getFlowWritUrl")
    public JSONObject getFlowWritUrl(HttpServletRequest request,String workFlowId,String fileTypeId, PageImpl pageImpl,String timeRange,String title,String recordId) {
    	JSONObject json = new JSONObject();
    	pageImpl.setPageNumber(1);
    	pageImpl.setPageSize(1);
    	PageImpl pageList = workFlowService.getWaitDoList(pageImpl,workFlowId,fileTypeId, "", "", title,"","",recordId);
    	 List<WorkFlowVO> listw = (List<WorkFlowVO>)pageList.getData().getRows();
    	if(listw.size() > 0) {
    		String url = listw.get(0).getUrl();
    		String workitemId = listw.get(0).getWorkitemid();
    		json.put("url", url);
    		json.put("workitemId",workitemId);
    	}
        return json;
    }

    /**
	 * TODO 查询流程类型
	 * @author 武帅
	 * @Date 2018年7月21日 下午2:57:03
     * @param workFlowIds 流程ID
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/getFlowWorkFlow")
    public List<Map<String,Object>> getFlowWorkFlow(String workFlowIds){
        return workFlowService.getFlowWorkFlow(workFlowIds);
    }
    
    /**
     * TODO 取当前用户维护的流程
     * @author 李利广
     * @Date 2018年7月23日 下午8:53:28
     * @return
     */
    @ResponseBody
    @RequestMapping("/getManageFlowByUser")
    public List<FlowWorkflowVO> getManageFlowByUser(){
    	return workFlowService.getManageFlowByUser();
    }

    /**
     * TODO 根据操作ID，获取操作配置数据
     * @author 李利广
     * @Date 2019年03月27日 17:25:38
     * @param wfOperateId
     * @return com.alibaba.fastjson.JSONObject
     */
    @ResponseBody
    @RequestMapping("/getOprateInfo")
    public JSONObject getOprateInfo(String wfOperateId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            JSONObject info = workFlowService.getOprateInfo(wfOperateId);
            json.put("flag","1");
            json.put("data",info);
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }
}
