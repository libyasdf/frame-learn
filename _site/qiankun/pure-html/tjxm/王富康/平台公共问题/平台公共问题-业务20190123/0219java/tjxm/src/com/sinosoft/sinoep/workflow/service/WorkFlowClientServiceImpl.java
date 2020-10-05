package com.sinosoft.sinoep.workflow.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.ep.webform.tool.Utiles;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.consultManage.model.ConsultManage;
import com.sinosoft.sinoep.modules.consultManage.service.ConsultManageService;
import com.sinosoft.sinoep.modules.leaveManage.model.LeaveManage;
import com.sinosoft.sinoep.modules.leaveManage.service.LeaveManageService;
import com.sinosoft.sinoep.workflow.constant.WorkFlowConfigConsts;
import com.sinosoft.sinoep.workflow.dao.ModifyFieldDao;
import com.sinosoft.sinoep.workflow.model.FlowClientData;
import com.sinosoft.sinoep.workflow.util.ParaBeanForXml;
import com.sinosoft.sinoep.workflow.util.WorkFlowUtil;

import workflow.spring.OutControlProcessService;
import workflow.spring.ProcessControlService;
import workflow.spring.ProcessRelaDataService;
import workflow.vo.FlowWriteVO;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：工作流操作类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月5日
 */
@Service
public class WorkFlowClientServiceImpl implements WorkFlowClientService {

    // 取工作流相关数据的服务 本地服务
    ProcessRelaDataService processRelaDataService = (ProcessRelaDataService) SpringBeanUtils.getBean(
            "processRelaDataService");

    //工作流过程控制服务 本地服务
    ProcessControlService processControlService = (ProcessControlService) SpringBeanUtils.getBean(
            "processControlService");

    //提交工作流服务 本地服务
    OutControlProcessService outControlProcessService = (OutControlProcessService) SpringBeanUtils.getBean(
            "outControlProcessService");

    @Autowired
    ConsultManageService consultManageService;
    @Autowired
    ModifyFieldDao modifyFieldDao;
    @Autowired
    LeaveManageService leaveManageService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 
     * <B>方法名称：提交工作流方法</B><BR>
     * <B>概要说明：</B><BR>
     * 
     */
    @Override
    public JSONObject submitToFlow(String userid, String jsonPar, String remindtype, FlowClientData data, String extendattr)
            throws UnsupportedEncodingException {
        String resJson = "{}";
        if (StringUtils.isNotBlank(userid)) {
            String workitemid = "", idea = "";
            /* 获取传递的参数 */
            data.setSysid(ConfigConsts.SYSTEM_ID);
            workitemid = data.getWorkitemid();
            idea = data.getIdea();
            /* 提交流程时封装bean数据 */
            ParaBeanForXml bean = WorkFlowUtil.beanSetUp(data);
            /*bean.setIsforcepop("1");
            bean.setIsforceselectop("1");*/
            if (StringUtils.isNotBlank(extendattr)) {
				bean.setExtendattr(extendattr);
			}
				/* 短信提醒：2,邮箱提醒：3，OA提醒：1 */
            /*if (StringUtils.isNotEmpty(remindtype)) {*/
                //bean.setRemindtype("1");//1 2 3
            /*}*/
            String json = JSONObject.toJSONString(bean);
            if (StringUtils.isBlank(jsonPar)) {
                jsonPar = json;
            }
            JSONObject jsonP = JSONObject.parseObject(jsonPar);

            //为了防止下一节点只有一个人员时，流程自动发送走了，传递两个参数。
            jsonP.put("isforcepop","1");
            jsonP.put("isforceselectop","1");
            jsonPar = JSONObject.toJSONString(jsonP);
            if (StringUtils.isBlank(workitemid)) {
                workitemid = jsonP.getString("workitemid");
            }
            if (StringUtils.isBlank(idea)) {
                idea = jsonP.getString("idea");
            }
            if (StringUtils.isBlank(workitemid)) {
                //起草结点点击提交按钮时执行
                if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                    resJson = outControlProcessService.doWorkFlowBean(jsonPar.toString());
                }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                    resJson = HttpRequestUtil.sendPost(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                            + "/outcontrol/doWorkFlowBean", "para=" + jsonPar.toString());
                }
            }else {
                //非起草结点点击提交时执行
                /* 获取要提交的待办信息 */
                String res = getWriteVoById(workitemid);
                /* 在提交选择路由、选择人员时，都会先将意见保存到临时意见表中 */
                //saveTempIdea(workitemid, ConfigConsts.SERVICE_TYPE, idea,"idea");
                if (StringUtils.isNotBlank(res)) {
                    JSONObject resjson = JSONObject.parseObject(res);
                    /* 当前待办的状态标识stattag：1-在办 0-撤办，在办时才允许提交 */
                    if ("1".equals(resjson.getString("stattag"))) {
                        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                            resJson = outControlProcessService.doWorkFlowBean(jsonPar.toString());
                        }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                            resJson = HttpRequestUtil.sendPost(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                                    + "/outcontrol/doWorkFlowBean", "para=" + jsonPar.toString());
                        }
                    }else {
                        resJson = "{'result':'failed'}";
                    }
                }else {
                    resJson = "{'result':'failed'}";
                }
            }
        }
        return JSONObject.parseObject(resJson);
    }

    /**
     * 
     * <B>方法名称：根据代办表ID查询代办信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午11:33:34
     * @param workitemid：工作项id
     * @return String
     */
    private String getWriteVoById(String workitemid) {
        String res = "";
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            res = processRelaDataService.getWriteVoById(workitemid);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            res = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                    + "/processdata/getWriteVoById", "workitemid=" + workitemid);
        }
        return res;
    }

    /**
     * 
     * <B>方法名称：保存临时意见</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#
     * saveTempIdea(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public Boolean saveTempIdea(String workItemId, String type, String idea,String ideaName) {
        boolean res = false;
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用R
            res = processRelaDataService.saveIdeaTemp(workItemId, ideaName, idea);
        }else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/saveIdeaTemp";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "workitemid=" + workItemId
                    + "&fieldName="+ideaName+"&idea=" + idea));
        }
        return res;
    }

    /**
     * 
     * <B>方法名称：签收工作项</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#signItem(java.lang.String, java.lang.String)
     */
    @Override
    public boolean signItem(String workitemid, String type) {
        boolean res = false;
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processControlService.setSignFlag(workitemid);
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/setSignFlag";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "workitemid=" + workitemid));
        }
        return res;
    }

    /**
     * 
     * <B>方法名称：取临时意见</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#getTempIdea(java.lang.String, java.lang.String)
     */
    @Override
    public String getTempIdea(String workitemid, String type) {
        String res = "";
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processRelaDataService.getIdeaTemp(workitemid);
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getIdeaTemp";
            res = HttpRequestUtil.sendGet(url, "workitemid=" + workitemid);
        }
        return res;
    }

    /**
     * 
     * <B>方法名称：取正式意见</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:46:39
     * @param data：工作流参数
     * @param type:调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONArray
     */
    @Override
    public JSONArray getFormalIdea(FlowClientData data, String type) {
        String res = "";
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processRelaDataService.getIdea(data.getFiletypeid(), data.getRecordid(), data.getDeptid(),
                    data.getUserid(), "", "2", "", "");

        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getIdea";
            res = HttpRequestUtil.sendGet(url,
                    "fileTypeId=" + data.getFiletypeid() + "&workflowid=" + data.getWorkflowid() + "&recordId="
                            + data.getRecordid() + "&deptId=" + data.getDeptid() + "&userId=" + data.getUserid()
                            + "&isVisibleType=2&order=&con=");

        }
        return JSONArray.parseArray(res);
    }

    /**
     * 
     * <B>方法名称：取待办配置</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:47:03
     * @param workitemid：待办工作项ID
     * @param type:调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     */
    @Override
    public JSONObject getDaiBanData(String workitemid, String type) {
        String res = "";
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processRelaDataService.getWfleveConfig(workitemid);
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getWfleveConfig";
            res = HttpRequestUtil.sendGet(url, "workitemid=" + workitemid);
        }
        return JSONObject.parseObject(res);
    }

    /**
     * 
     * <B>方法名称：收回</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:48:26
     * @param workitemid：已办工作项ID
     * @param isBackIdea：1是收回意见，0是不收回
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     * @throws IOException
     */
    @Override
    public JSONObject cancelFlow(String workitemid, String isBackIdea, String type) throws IOException {
        boolean res = false;
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processControlService.cancelFlow(workitemid, isBackIdea);
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/cancelFlow";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "workitemid=" + workitemid + "&isBackIdea="
                    + isBackIdea));
        }
        json.put("res", res);
        return json;
    }

    /**
     * 
     * <B>方法名称：点击待办和已办调用的服务</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#getFlowData(java.lang.String,
     *      com.sinosoft.sinoep.workflow.model.FlowClientData)
     */
    @SuppressWarnings("unused")
	@Override
    public String getFlowData(String userid, FlowClientData formClientData) {
        JSONObject json = new JSONObject();
        json.put("result", "0");
        // 获取必要参数
        formClientData.setSysid(ConfigConsts.SYSTEM_ID);
        String oper = formClientData.getOper();
        // 判断必传参数
        if (Utiles.isNullStr(oper)) {
            json.put("messages", "请传入表单参数oper:NEW-新建 VIEW-查看 EDIT-编辑");
        }else if ((formClientData.getOper().equals("VIEW") || formClientData.getOper().equals("UPDATE"))
                && (Utiles.isNullStr(formClientData.getRecordid()))) {
            json.put("messages", "请传入表单参数pkValue");
        }else {
            JSONObject flowDataJson = new JSONObject();
            String workitemid = formClientData.getWorkitemid();//
            // 如里是编辑状态且工作项id不为空，则取出节点配置的表单权限角色
            if (oper.equals("EDIT") && !Utiles.isNullStr(workitemid)) {
                String data = "";
                if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                    data = processRelaDataService.getWfleveConfig(workitemid);
                }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                    data = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                            + "/processdata/getWfleveConfig", "workitemid =" + workitemid);
                }
                flowDataJson = JSONObject.parseObject(data);
            }
            // 调用工作流服务，取按钮、意见域等
            if (oper.equals("NEW") && (!Utiles.isNullStr(formClientData.getFiletypeid())
                    || !Utiles.isNullStr(formClientData.getWorkflowid()))) {
                // 新建时取启动节点的意见域和按钮
                String data = "";
                if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                    data = processRelaDataService.getWfleveConfig(workitemid);
                }
                else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                    data = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                            + "/processdata/getWfleveConfig", "workitemid =" + workitemid);
                }
                flowDataJson = JSONObject.parseObject(data);
                json.put("flowData", flowDataJson);
            }else if (!Utiles.isNullStr(workitemid)) {
                if (oper.equals("EDIT")) {
                    // 待办列表打开待办表单页面
                    json.put("flowData", flowDataJson);
                    // 取临时意见，展示到id="idea"的控件上
                    String idea = "";
                    if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                        idea = processRelaDataService.getIdeaTemp(workitemid);
                    }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                        idea = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                                + "/processdata/getIdeaTemp", "workitemid = " + workitemid);
                    }
                    json.put("tempIdea", idea);
                    // 签收待办工作项
                    if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                        processControlService.setSignFlag(workitemid);
                    }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                        String boo = HttpRequestUtil.sendPost(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                                + "/proceecontrol/setSignFlag", "workitemid=" + workitemid);
                    }
                }else if (oper.equals("VIEW")) {
                    // 已办列表打开已办页面
                    String data = "";
                    if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                        data = processRelaDataService.getReadVoById(workitemid);
                    }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                        data = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                                + "/processdata/getReadVoById", "workitemid = " + workitemid);
                    }
                    json.put("flowData", data);
                }
                // 取意见
                String ideaFinally = "";
                if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                    ideaFinally = processRelaDataService.getIdea(formClientData.getWorkflowid(), formClientData
                            .getWorkflowid(), formClientData.getRecordid(), formClientData.getDeptid(), userid, "2", "",
                            "");
                }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                    ideaFinally = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                            + "/processdata/getIdea", "fileTypeId =" + formClientData.getWorkflowid() + "&workflowid="
                                    + formClientData.getWorkflowid() + "&recordId=" + formClientData.getRecordid()
                                    + "&deptId=" + formClientData.getDeptid() + "&userId=" + userid + "&isVisibleType=2"
                                    + "&order=&con=");
                }
                json.put("formalIdea", ideaFinally);
            }
        }
        return json.toString();
    }

    /**
     * 
     * <B>方法名称：撤办</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#removeFlow(java.lang.String, java.lang.String)
     */
    @Override
    public JSONObject removeFlow(String workitemid, String type) {
        boolean res = false;
        String result = "";
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
        	result = processControlService.suspendFlow(workitemid);
        	net.sf.json.JSONObject jsonResult = net.sf.json.JSONObject.fromObject(result);
        	json.put("res", jsonResult.get("flag"));
        }else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/suspendFlow";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "workitemid=" + workitemid));
            json.put("res", res);
        }
        
        return json;
    }

    /**
     * 
     * <B>方法名称：恢复撤办</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#resumeFlow(java.lang.String, java.lang.String)
     */
    @Override
    public JSONObject resumeFlow(String workitemid, String type) {
        boolean res = false;
        String result = "";
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        JSONObject json = new JSONObject();
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
        	result = processControlService.resumeFlow(workitemid);
        	net.sf.json.JSONObject jsonResult = net.sf.json.JSONObject.fromObject(result);
        	json.put("res", jsonResult.get("flag"));
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/resumeFlow";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "workitemid=" + workitemid));
            json.put("res", res);
        }
        
        return json;
    }

    /**
     * 
     * <B>方法名称：跳节点</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#
     * jumpToWfleve(net.sf.json.JSONObject,
     *      java.lang.String)
     */
    @Override
    public JSONObject jumpToWfleve(JSONObject param, String type) throws IOException {
        boolean res = false;
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        JSONObject json = new JSONObject();
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processControlService.jumpToWfleve(param.toString());
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/jumpToWfleve";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "para=" + param.toString()));
        }
        json.put("res", res);
        return json;
    }

    /**
     * 
     * <B>方法名称：公文管理办结流程</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#
     * completeFlow(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public JSONObject completeFlow(String workitemid, String subflag, String recordid, String type, String idea) {
        String res = "";
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        JSONObject json = new JSONObject();
        String[] arr = workitemid.split(",");
        String[] arrr = recordid.split(",");
        for (int i = 0; i < arr.length; i++) {
            json.put("workitemid", arr[i]);
            json.put("flag", "0");
            subflag = StringUtils.isBlank(subflag) ? ConfigConsts.END_FLAG : subflag;
            json.put("subflag", subflag);
            json.put("idea", idea);
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                // 本地服务调用
                res = processControlService.cleanUp(json.toString());
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/cleanUp";
                res = HttpRequestUtil.sendPost(url, "para=" + json.toString());
            }
            if (StringUtils.isNotBlank(res) && res.equals("ok")) {
                ConsultManage consult = consultManageService.view(arrr[i]);
                LeaveManage leave = leaveManageService.view(arrr[i]);
                if (consult != null) {
                    consultManageService.updateSubFlag(arrr[i], ConfigConsts.END_FLAG);
                    json.put("result", "success");
                }
                if (leave != null) {
                    String sub = leave.getSubFlag();
                    if (sub == "6") {
                        leaveManageService.updateSubFlag(arrr[i], ConfigConsts.APPROVAL_FLAG);
                        json.put("result", "success");
                    }
                    else {
                        leaveManageService.updateSubFlag(arrr[i], ConfigConsts.NO_APPROVAL_FLAG);
                        json.put("result", "success");
                    }
                }
                else {
                    json.put("result", "success");
                }
            }
            json.put("res", res);
        }
        return json;
    }

    /**
     * 
     * <B>方法名称：办结</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService# finishFlow(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public JSONObject finishFlow(String workitemid, String subflag,String flag, String recordid,String idea)
            throws IOException {
        String res = "";
        JSONObject json = new JSONObject();
        json.put("workitemid", workitemid);
        json.put("flag", flag);
        subflag = StringUtils.isBlank(subflag) ? ConfigConsts.END_FLAG : subflag;
        json.put("subflag", subflag);
        json.put("idea", idea);
        //先查询待办数据,然后再结束流程
        FlowWriteVO writeVo = WorkFlowUtil.getWriteVoById(workitemid);
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processControlService.cleanUp(json.toString());
        }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/cleanUp";
            res = HttpRequestUtil.sendPost(url, "para=" + json.toString());
        }
        json.put("recordid",recordid);
        json.put("workflowid",writeVo.getWorkflowid());
        json.put("filetypeid",writeVo.getFiletypeid());
        json.put("res", res);
        return json;
    }

    /**
     * <B>方法名称：保存签发时间及签发人信息</B><BR>
     * <B>概要说明：根据流程返回的数据获取到业务记录id和公文分类标识，根据公文分类标识判断公文类型，根据业务记录id获取公文实体类，
     * 根据流程返回的数据在实体保存相应的信息，依业务需求而定</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#
     * saveSignInfo(net.sf.json.JSONObject)
     */
    @SuppressWarnings("unused")
	@Override
    public String saveSignInfo(JSONObject contextJson) throws IOException {
        String result = "0";
        //获取流程经过签发节点时的签发时间
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String signTime = sf.format(new Date(System.currentTimeMillis()));
        //获取当前的业务记录id
        String recordid = contextJson.getString("recordid");
        //获取当前的流程分类标识
        String attr1 = contextJson.getString("attr1");
        //根据分类标识判断公文类型
        //if (StringUtils.isNotBlank(attr1) && attr1.equals(GlobalNames.DISPATCH)) {
        //根据业务记录id获取业务实体
        //ArchivesOut model = (ArchivesOut) outDao.getById(recordid);
        //获取用户id
        //String userid = contextJson.getString("userid");
        //MessageUser user = userService.getUserInfoById(userid);
        //根据用户id获取签发人名字
        //String username = (user.getUserInfo().get(0)).getUserFullName();
        //设置实体字段值
        //model.setQianfaPersonId(userid);//签发人id
        //model.setQianfaPersonName(username);//签发人姓名
        //model.setDateIssue(signTime);//签发时间
        //保存或刷新保存实体类
        //outDao.saveOrUpdate(model);
        //result = "1";
        //}
        return result;
    }

    /**
     * <B>方法名称：</B><BR>
     * <B>概要说明：增加会签</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#getsignatureFlow(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public JSONObject getsignatureFlow(String workitemid, String type) {
        boolean res = false;
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processControlService.isDisAddHq(workitemid);
        }else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/isDisAddHq";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "workitemid=" + workitemid));
        }
        json.put("res", res);
        return json;
    }

    /**
     * <B>方法名称：</B><BR>
     * <B>概要说明：删除会签</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowClientService#delsignatureFlow(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public JSONObject delsignatureFlow(String workitemid, String type) {
        JSONObject json = new JSONObject();
        Boolean res = false;
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processControlService.isDisDelHq(workitemid);
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/isDisDelHq";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "workitemid=" + workitemid));
        }
        json.put("res", res);
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
    @Override
	public JSONArray getStartWflevel(String fileTypeId, String workflowId, String deptId, String userId) throws Exception{
    	JSONArray array = new JSONArray();
    	String res = "";
    	if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processRelaDataService.getStartWfleve(fileTypeId, workflowId, deptId, userId);
        }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
        	// http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getStartWfleve";
            res = HttpRequestUtil.sendGet(url, "fileTypeId=" + fileTypeId + "&workflowId=" + workflowId + "&deptId=" + deptId + "&userId=" + userId);
        }
    	if (StringUtils.isNotBlank(res)) {
			array = JSONArray.parseArray(res);
		}
		return array;
	}

    /**
     * 根据流程ID与业务ID删除流程信息（包括：待办、已办、流程记录、意见信息）
     * @auther 李利广
     * @Date 2019年1月7日 下午2:22:47
     * @param workflowId
     * @param recordId
     * @return
     */
    @Override
    public JSONObject deleteFlowInfo(String workflowId,String recordId) throws Exception{
        JSONObject json = new JSONObject();
        json.put("flag","0");
        if (StringUtils.isNotBlank(recordId) && StringUtils.isNotBlank(workflowId)) {
            //删除待办
            StringBuilder waitSql = new StringBuilder("delete from epcloud.flow_write t where t.recordid = '").append(recordId).append("'");
            waitSql.append(" and t.workflowid = '" + workflowId + "'");
            jdbcTemplate.execute(waitSql.toString());
            //删除已办
            StringBuilder readSql = new StringBuilder("delete from epcloud.flow_read t where t.recordid = '").append(recordId).append("'");
            readSql.append(" and t.workflowid = '" + workflowId + "'");
            jdbcTemplate.execute(readSql.toString());
            //删除流程记录
            StringBuilder infoSql = new StringBuilder("delete from epcloud.flow_wflog t where t.recordid = '").append(recordId).append("'");
            infoSql.append(" and t.workflowid = '" + workflowId + "'");
            jdbcTemplate.execute(infoSql.toString());
            //删除正式意见
            StringBuilder ideaSql = new StringBuilder("delete from epcloud.flow_idea t where t.recordid = '").append(recordId).append("'");
            ideaSql.append(" and t.filetypeid = '" + workflowId + "'");
            jdbcTemplate.execute(ideaSql.toString());
            //删除临时意见
            StringBuilder tempIdeaSql = new StringBuilder("delete from epcloud.flow_ideasign_temp t where t.recordid = '").append(recordId).append("'");
            tempIdeaSql.append(" and t.filetypeid = '" + workflowId + "'");
            jdbcTemplate.execute(tempIdeaSql.toString());
            json.put("flag", "1");
            json.put("msg","执行成功");
        }else{
            json.put("msg","缺少参数");
        }
        return json;
    }

}
