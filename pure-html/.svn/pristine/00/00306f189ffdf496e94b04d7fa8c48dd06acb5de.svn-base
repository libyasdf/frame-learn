package com.sinosoft.sinoep.workflow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.api.user.vo.MessageUser;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.*;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.workflow.constant.WorkFlowConfigConsts;
import com.sinosoft.sinoep.workflow.model.FlowClientData;
import com.sinosoft.sinoep.workflow.model.WorkFlowVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import workflow.spring.FlowDataService;
import workflow.spring.ProcessControlService;
import workflow.spring.ProcessRelaDataService;
import workflow.vo.FlowWorkflowVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：工作流信息类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    /**
     * 取工作流相关数据的服务 本地服务
     */
    ProcessRelaDataService processRelaDataService = (ProcessRelaDataService) SpringBeanUtils.getBean("processRelaDataService");

    @Autowired
    UserInfoService userInfoService;
    
    /**getFlowReadList
     * 获取流程配置相关信息服务接口
     */
    @Autowired
    FlowDataService flowDataService;
    
    /**
     * 控制流转过程服务接口
     */
    @Resource
    ProcessControlService processControlService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 
     * <B>方法名称：获取待办数据</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService# getWaitDo(java.lang.String, java.lang.String, int,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public PageImpl getWaitDoList(PageImpl pageImpl,String workFlowId,String fileTypeId, String endTime, String beginTime, String title, String attr, String attr1,String recordId) {
        List<WorkFlowVO> listw = null;
        int totalResult = 0;
        String userid = UserUtil.getCruUserId();
        JSONObject json = new JSONObject();
        json.put("userId", userid);
        json.put("sysId", ConfigConsts.SYSTEM_ID);
        json.put("start", String.valueOf((pageImpl.getPageNumber() - 1) * pageImpl.getPageSize()));
        json.put("rowNum", pageImpl.getPageSize());
        json.put("endTime", endTime);
        json.put("beginTime", beginTime);
        json.put("title", title);
        JSONObject jsonP = new JSONObject();
        jsonP.put("userId", userid);
        jsonP.put("endTime", endTime);
        jsonP.put("beginTime", beginTime);
        jsonP.put("title", title);
        jsonP.put("type", "1");
        jsonP.put("sysId", ConfigConsts.SYSTEM_ID);
        String condition = "";
        if (StringUtils.isNotBlank(workFlowId)) {
        	workFlowId = CommonUtils.commomSplit(workFlowId);
            condition = " and workflowid in (" + workFlowId + ")";
        }
        if (StringUtils.isNotBlank(fileTypeId)) {
        	workFlowId = CommonUtils.commomSplit(fileTypeId);
            condition = " and filetypeid in (" + fileTypeId + ")";
        }
        if (StringUtils.isNotBlank(recordId)) {
        	recordId = CommonUtils.commomSplit(recordId);
            condition = " and recordid in (" + recordId + ")";
        }
        if (StringUtils.isNotBlank(attr)) {
            condition = " and attr = '" + attr + "' ";
        }
        if (StringUtils.isNotBlank(attr1)) {
            if (StringUtils.isNotBlank(condition)) {
                condition += " and attr1 = '" + attr1 + "' ";
            }
            else {
                condition = " and attr1 = '" + attr1 + "' ";
            }
        }
        json.put("condition", condition);
        jsonP.put("condition", condition);
        try {
			if (StringUtils.isNotBlank(userid)) {
			    if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
			        String waitdoStr = processRelaDataService.getWaitDo(json.toString());
			        totalResult = Integer.parseInt(processRelaDataService.getWRCount(jsonP.toString()));
			        listw = analyJsonToWorkFlow(waitdoStr);
			    } else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
			        String waitdoStr = HttpRequestUtil.sendGetNoEncode(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
			                + "/processdata/getWaitDo", "param=" + URLEncoder.encode(json.toString(), "utf-8"));
			        totalResult = Integer.valueOf(HttpRequestUtil.sendGetNoEncode(
			                WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getWRCount", "param="
			                        + URLEncoder.encode(jsonP.toString(), "utf-8")));
			        listw = analyJsonToWorkFlow(waitdoStr);
			    }
			}
			pageImpl.setFlag("1");
		} catch (Exception e) {
			e.printStackTrace();
			pageImpl.setFlag("0");
		}
        pageImpl.getData().setRows(listw);
        pageImpl.getData().setTotal(totalResult);
        return pageImpl;
    }

    /**
     * 
     * <B>方法名称：获取已办数据</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getYiBanData(java.lang.String, java.lang.String)
     */
    @Override
    public JSONObject getYiBanData(String workitemid, String type) {
        JSONObject res = new JSONObject();
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            String readStr = processRelaDataService.getReadVoById(workitemid);
            if (readStr != null && !"".equals(readStr) && !"null".equals(readStr)) {
                JSONObject readJson = JSON.parseObject(readStr);
                JSONArray buttonArray = new JSONArray();

                if (processControlService.isDisAddHq(workitemid)) {
                    JSONObject button = getButton(ConfigConsts.INCREASE_SIGN_BTN, "增加会签", "", "");
                    buttonArray.add(button);
                }
                if (processControlService.isDisDelHq(workitemid)) {
                    JSONObject button = getButton(ConfigConsts.DELETE_SIGN_BTN, "删除会签", "", "");
                    buttonArray.add(button);
                }
                if (StringUtils.isNotBlank(readJson.getString("userid")) && readJson.getString("userid").equals(readJson.getString("draftuser"))
                        && readJson.getString("stattag").equals("1")) {
                    JSONObject button = getButton(ConfigConsts.REMOVE_BTN, "撤办", "", "");
                    buttonArray.add(button);
                }
                if (StringUtils.isNotBlank(readJson.getString("userid")) && readJson.getString("userid").equals(readJson.getString("draftuser"))
                        && readJson.getString("stattag").equals("0")) {
                    JSONObject button = getButton(ConfigConsts.RECOVERY_BTN, "重新启用", "", "");
                    buttonArray.add(button);
                }
                if (processControlService.canCancel(workitemid) && readJson.getString("stattag").equals("1")) {
                    JSONObject button = getButton(ConfigConsts.TASK_BACK_BTN, "收回", "", "");
                    buttonArray.add(button);
                }
                res.put("buttonVec", buttonArray);
            }
        }else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getReadVoById";
            String readStr = HttpRequestUtil.sendGet(url, "workitemid=" + workitemid);
            if (readStr != null && !"".equals(readStr) && !"null".equals(readStr)) {
                JSONObject readJson = JSONObject.parseObject(readStr);
                JSONArray buttonArray = new JSONArray();
                url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/canCancel";
                if (HttpRequestUtil.sendPost(url, "workitemid=" + workitemid).equals("true")) {
                	JSONObject button = getButton(ConfigConsts.TASK_BACK_BTN, "收回", "", "");
                    buttonArray.add(button);
                }
                url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/isDisAddHq";
                if (HttpRequestUtil.sendGet(url, "workitemid=" + workitemid).equals("true")) {
                	JSONObject button = getButton(ConfigConsts.INCREASE_SIGN_BTN, "增加会签", "", "");
                    buttonArray.add(button);
                }
                url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/isDisDelHq";
                if (HttpRequestUtil.sendGet(url, "workitemid=" + workitemid).equals("true")) {
                	JSONObject button = getButton(ConfigConsts.DELETE_SIGN_BTN, "删除会签", "", "");
                    buttonArray.add(button);
                }
                if (StringUtils.isNotBlank(readJson.getString("userid")) && readJson.getString("userid").equals(readJson.getString("draftuser"))
                        && readJson.getString("stattag").equals("1")) {
                	JSONObject button = getButton(ConfigConsts.REMOVE_BTN, "撤办", "", "");
                    buttonArray.add(button);
                }
                if (StringUtils.isNotBlank(readJson.getString("userid")) && readJson.getString("userid").equals(readJson.getString("draftuser"))
                        && readJson.getString("stattag").equals("0")) {
                	JSONObject button = getButton(ConfigConsts.RECOVERY_BTN, "重新启用", "", "");
                    buttonArray.add(button);
                }
                res.put("buttonVec", buttonArray);
            }
        }
        return res;
    }

    /**
     * 
     * <B>方法名称：封装按钮数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:45:24
     * @param id：按钮主键
     * @param name：按钮名称
     * @param note：备注
     * @param method：方法
     * @return JSONObject
     */
    private JSONObject getButton(String id, String name, String note, String method) {
        JSONObject button = new JSONObject();
        button.put("num", id);
        button.put("buttonname", name);
        button.put("buttonnote", note);
        button.put("method", method);
        return button;
    }

    /**
     * 
     * <B>方法名称：获取强制会签列表服务层</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @throws UnsupportedEncodingException
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getForceHqList(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Page getForceHqList(String userid, String pageNum, String endTime, String beginTime, String title,
            String type) throws UnsupportedEncodingException {
        Page page = new Page();
        List<WorkFlowVO> listw = null;
        int totalResult = 0;
        JSONObject json = new JSONObject();
        String str = String.valueOf((Integer.parseInt(pageNum) - 1) * 10);
        json.put("start", str);
        json.put("rowNum", "10");
        json.put("endTime", endTime);
        json.put("beginTime", beginTime);
        json.put("title", title);
        json.put("sysid", ConfigConsts.SYSTEM_ID);
        json.put("condition", " and USERID=DRAFTUSER");
        JSONObject jsonP = new JSONObject();
        jsonP.put("endTime", endTime);
        jsonP.put("beginTime", beginTime);
        jsonP.put("title", title);
        jsonP.put("type", "2");
        jsonP.put("sysid", ConfigConsts.SYSTEM_ID);
        jsonP.put("condition", " and USERID=DRAFTUSER");
        if (StringUtils.isNotBlank(userid)) {
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                String haddoStr = processRelaDataService.getHadDone(json.toString());
                totalResult = Integer.parseInt(processRelaDataService.getWRCount(jsonP.toString()));
                listw = analyJsonToWorkFlow(haddoStr);
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                String haddoStr = HttpRequestUtil.sendGetNoEncode(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/processdata/getHadDone", "param=" + URLEncoder.encode(json.toString(), "utf-8"));
                totalResult = Integer.valueOf(HttpRequestUtil.sendGetNoEncode(
                        WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                                + "/processdata/getWRCount", "param=" + URLEncoder.encode(jsonP.toString(), "utf-8")));
                listw = analyJsonToWorkFlow(haddoStr);
            }
        }
        page.setRecordList(listw);
        page.setCurrentPage(Integer.parseInt(pageNum));
        page.setShowCount(10);
        page.setTotalResult(totalResult);
        return page;
    }

    /**
     * 
     * <B>方法名称：解析json到工作流</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:50:58
     * @param json
     * @return List<WorkFlowVO>
     */
    public List<WorkFlowVO> analyJsonToWorkFlow(String json) {
        List<WorkFlowVO> listw = null;
        if (StringUtils.isNotBlank(json)) {
            JSONArray jarry = JSONArray.parseArray(json);
            Iterator<Object> it = jarry.iterator();
            listw = new ArrayList<WorkFlowVO>();
            while (it.hasNext()) {
                JSONObject ob = (JSONObject) it.next();
                WorkFlowVO workFlow = new WorkFlowVO();
                workFlow.setTitle(ob.getString("title"));
                workFlow.setCreateTime(ob.getString("createtime"));
                workFlow.setFileTypeName(ob.getString("filetypename"));
                workFlow.setRecordid(ob.getString("recordid"));
                workFlow.setSys_id(ob.getString("sysId"));
                workFlow.setUserid(ob.getString("userid"));
                workFlow.setReadTime(ob.getString("readtime"));
                workFlow.setWorkflowid(ob.getString("workflowid"));
                workFlow.setWorkitemid(ob.getString("id"));
                workFlow.setWorkFlowName(ob.getString("workflowname"));
                workFlow.setFileTypeId(ob.getString("filetypeid"));
                workFlow.setDeptid(ob.getString("deptid"));
                workFlow.setFormid(ob.getString("formurl"));
                workFlow.setStattag(ob.getString("stattag"));
                // 如果是待办，就给前一接收人和接受时间赋值
                if (!ob.containsKey("handdoneurl")) {
                    workFlow.setUsername(ob.getString("draftUserName"));
                    workFlow.setReceiveTime(ob.getString("receivetime"));
                    workFlow.setPreUserName(ob.getString("preUserName"));
                }
                else {
                    String draftUserName = "";
                    MessageUser messagecreUser = userInfoService.getUserInfoById(ob.getString("draftuser"));
                    if (messagecreUser.getUserInfo().size() > 0) {
                        UserInfo userInfo = messagecreUser.getUserInfo().get(0);
                        draftUserName = userInfo.getUserFullName();
                    }
                    workFlow.setUsername(draftUserName);
                    workFlow.setReceiveTime("");
                    workFlow.setPreUserName("");
                }
                boolean boo = ob.containsKey("waitdourl");
                if (boo) {
                    workFlow.setUrl(ob.getString("waitdourl"));
                }
                else {
                    workFlow.setUrl(ob.getString("handdoneurl"));
                }
                listw.add(workFlow);
            }
        }
        return listw;

    }

    /**
     * 
     * <B>方法名称：查询跳节点列表</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getJumpNodeList(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public Page getJumpNodeList(String userid, String pageNum, String type) throws UnsupportedEncodingException {
        Page page = new Page();
        List<WorkFlowVO> listw = null;
        int totalResult = 0;
        JSONObject json = new JSONObject();
        json.put("userId", userid);
        json.put("sysId", ConfigConsts.SYSTEM_ID);
        json.put("start", String.valueOf((Integer.parseInt(pageNum) - 1) * 10));
        json.put("rowNum", "10");
        json.put("condition", " ");
        JSONObject jsonP = new JSONObject();
        jsonP.put("userId", userid);
        jsonP.put("type", "1");
        jsonP.put("sysId", ConfigConsts.SYSTEM_ID);
        jsonP.put("sysid", ConfigConsts.SYSTEM_ID);
        jsonP.put("condition", " ");
        if (StringUtils.isNotBlank(userid)) {
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                String waitdoStr = processRelaDataService.getWaitDo(json.toString());
                totalResult = Integer.parseInt(processRelaDataService.getWRCount(jsonP.toString()));
                listw = analyJsonToWorkFlow(waitdoStr);
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                String waitdoStr = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/processdata/getWaitDo", "param=" + json.toString());
                totalResult = Integer.valueOf(HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/processdata/getWRCount", "param=" + jsonP.toString()));
                listw = analyJsonToWorkFlow(waitdoStr);
            }
        }
        page.setRecordList(listw);
        page.setCurrentPage(Integer.parseInt(pageNum));
        page.setShowCount(10);
        page.setTotalResult(totalResult);
        return page;
    }

    /**
     * TODO 无用方法
     * <B>方法名称：getTjdept</B><BR>
     * <B>概要说明：根据部门获取统计数据</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getTjdept(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public Page getTjdept(String start, String end, String year, String deptid, Integer pageNum, Integer size) {
        String where = "";
        if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end) && StringUtils.isNotBlank(deptid)) {
            if (StringUtils.isNotBlank(year)) {
                where += " and ((signtime>  " + start + " and signtime<" + end + ") or (signtime like '" + year
                        + "%'))";
            }
            else {
                where += " and signtime>" + start + " and signtime<" + end + "";
            }
            where += " and signdeptid in(" + deptid + ") ";
        }
        else {
            if (StringUtils.isNotBlank(year)) {
                where += " and signtime like '" + year + "%'";
            }
        }
        String from = " (select signdeptid,signname from(select t.*, row_number() over(partition by signdeptid order by signtime desc ) rn from flow_wflog t ) where rn=1 "
                + where
                + " order by signdeptid ) wf1,";
        from += "(select signdeptid, count(distinct recordid) as recordnum from flow_wflog "
                + "where 1=1 " + where
                + "group by signdeptid order by signdeptid) wf2 ";
        from += "left join (select  signdeptid ,count(distinct fw.recordid) ybnum from flow_wflog fw ,flow_read f "
                + "where fw.recordid = f.recordid and f.stattag='4' " + where
                + "group by signdeptid) wf3 on wf2.signdeptid =  wf3.signdeptid ";
        String sql = "select wf1.signdeptid,signname,recordnum,nvl(ybnum,0) as ybnum,(recordnum-nvl(ybnum,0)) as dbnum,rownum rn from"
                + from
                + "where  wf1.signdeptid = wf2.signdeptid";
        String sqlData = "select * from (" + sql + ") where rn>" + ((pageNum - 1) * size) + " and rn<=" + ((pageNum - 1)
                * size + size);
        String sqlcount = "select count(*) as num from "
                + from
                + "where wf1.signdeptid = wf2.signdeptid";
        List<Map<String, Object>> listdata = null;
        //调用工作流服务，查询统计数据
        listdata = processRelaDataService.getDataBySql(sqlData);
        List<Map<String, Object>> listCount = null;
        listCount = processRelaDataService.getDataBySql(sqlcount);
        BigDecimal count = null;
        if (listCount != null && listCount.size() > 0) {
            Map<String, Object> mapCount = listCount.get(0);
            count = (BigDecimal) mapCount.get("num");
        }
        Page page = new Page(pageNum, size, count.intValue(), listdata);
        return page;
    }

    /**
     * TODO 无用方法
     * <B>方法名称：getTjUser</B><BR>
     * <B>概要说明：根据用户获取统计数据</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getTjdept(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public Page getTjUser(String start, String end, String year, String userid, Integer pageNum, Integer size) {
        String where = "";
        if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end) && StringUtils.isNotBlank(userid)) {
            if (StringUtils.isNotBlank(year)) {
                where += " and ((signtime>" + start + " and signtime<" + end + ") or (signtime like '" + year + "%'))";
            }
            else {
                where += " and signtime>" + start + " and signtime<" + end + "";
            }
            where += " and signuserid in(" + userid + ") ";
        }
        else {
            if (StringUtils.isNotBlank(year)) {
                where += " and signtime like '" + year + "%'";
            }
        }
        String from = "(select fl.signuserid as signuserid, count(distinct recordid) as recordnum ,count(recordid) as jdnum from flow_wflog fl where 1=1 "
                + where
                + "group by fl.signuserid order by fl.signuserid) wf4,";
        from += "(select signuserid ,Max(num) as maxnum from("
                + "select wfl.signuserid as signuserid ,trunc(to_date(wfl.signtime,'yyyy-MM-dd HH24:mi:ss'),'dd') as d, count(distinct wfl.recordid) as num"
                + " from  flow_wflog wfl"
                + " where 1=1 " + where
                + " group by wfl.signuserid,trunc(to_date(wfl.signtime,'yyyy-MM-dd HH24:mi:ss'),'dd')"
                + ") group by signuserid  order by signuserid) wf3,";
        from += " (select signuserid,trunc(sum(dealtime),1)as dealtime ,trunc(min(dealtime),1) as mindealtime from("
                + "select f1.signuserid as signuserid ,f2.signtime,"
                + "(TO_NUMBER(nvl(to_date(f1.sendtime ,'yyyy-MM-dd HH24:mi:ss'),(select sysdate from dual)) - to_date(f2.receivetime,'yyyy-MM-dd HH24:mi:ss'))*24) as dealtime"
                + " from (select * from(select t.*, row_number() over(partition by recordid order by receivetime desc ) rn from flow_wflog t ) where rn=1) f1,"
                + " (select * from flow_wflog where superlogid ='0' ) f2 "
                + " where f2.recordid = f1.recordid and  f1.signuserid is not null"
                + ")"
                + " where 1=1 " + where
                + " group by signuserid order by signuserid) wf2,";
        from += " (select signuserid,signname from(select t.*, row_number() over(partition by signuserid order by signtime desc ) rn from flow_wflog t ) where rn=1 "
                + where
                + " order by signuserid ) wf1";
        String sql = "select wf1.*,wf4.recordnum,wf4.jdnum,wf3.maxnum,wf2.dealtime,wf2.mindealtime,rownum rn from "
                + from
                + " where wf1.signuserid = wf2.signuserid and wf1.signuserid = wf3.signuserid and wf1.signuserid = wf4.signuserid";
        String sqlData = "select * from (" + sql + ") where rn>" + ((pageNum - 1) * size) + " and rn<=" + ((pageNum - 1)
                * size + size);
        String sqlcount = "select count(*) as num from "
                + from
                + " where wf1.signuserid = wf2.signuserid and wf1.signuserid = wf3.signuserid and wf1.signuserid = wf4.signuserid";
        List<Map<String, Object>> listdata = null;
        //调用工作流服务，查询统计数据
        listdata = processRelaDataService.getDataBySql(sqlData);
        List<Map<String, Object>> listCount = new ArrayList<Map<String, Object>>();
        listCount = processRelaDataService.getDataBySql(sqlcount);
        BigDecimal count = null;
        if (listCount != null && listCount.size() > 0) {
            Map<String, Object> mapCount = listCount.get(0);
            count = (BigDecimal) mapCount.get("num");
        }
        Page page;
        if (count != null && count.intValue() > 0) {
            page = new Page(pageNum, size, count.intValue(), listdata);
        }
        else {
            page = new Page(pageNum, size, 0, listdata);
        }
        return page;
    }

    /**
     * TODO 无用方法
     * <B>方法名称：从工作流日志表中获取历年</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getYear()
     */
    @Override
    public List<Map<String, Object>> getYear() {
        String sql = "select distinct extract(year from to_date(signtime,'yyyy-MM-dd HH24:mi:ss')) year from flow_wflog where signtime is not null";
        //调用工作流服务，查询统计数据
        List<Map<String, Object>> list = processRelaDataService.getDataBySql(sql);
        return list;
    }

    /**
     * <B>方法名称：列表筛选</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#list(com.sinosoft.sinoep.common.util.Page)
     */
    @SuppressWarnings("unchecked")
	@Override
    public Page list(Page page) {
        List<WorkFlowVO> list = page.getRecordList();
        if (list != null) {
            for (int i = 0; list.size() > i; i++) {
                WorkFlowVO w = list.get(i);
                if (StringUtils.isNotBlank(w.getUserid())) {
                    MessageUser m = userInfoService.getUserInfoById(w.getUserid());
                    if ("1".equals(m.getStatus())) {
                        String userinfo = JSONObject.toJSONString(m.getUserInfo().get(0));
                        JSONObject json = JSONObject.parseObject(userinfo);
                        String username = json.getString("userFullName");
                        w.setUsername(username);
                        list.set(i, w);
                    }
                }
            }
        }
        page.setRecordList(list);
        return page;
    }

    /**
     * 
     * <B>方法名称：获取流程列表</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getworkflow(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public JSONObject getworkflow(String userId, String type, String deptid) throws UnsupportedEncodingException {
        JSONObject resultJson = new JSONObject();
        String json = "";
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        if (StringUtils.isNotBlank(userId)) {
            JSONObject param = new JSONObject();
            param.put("sysId", ConfigConsts.SYSTEM_ID);
            param.put("deptId", deptid);
            param.put("userId", userId);
            param.put("flag", "0");
            param.put("mark", "");
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                json = processRelaDataService.getCanStartWorkflowSort(param.toString());
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                json = HttpRequestUtil.sendGet(
                        WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getCanStartWorkflowBySort",
                        "param=" + param.toString());
            }
            JSONArray jarry = JSONArray.parseArray(json);
            resultJson.put("flows", jarry);
            resultJson.put("deptId", deptid);
            resultJson.put("userId", userId);
        }
        return resultJson;
    }

    /**
     * 
     * <B>方法名称：获取拟稿状态下的按钮</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getStartWfButton(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public JSONArray getStartWfButton(String filetypeid, String workflowid, String userid) {
        String deptId = UserUtil.getCruDeptId();
        String res = "";
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            res = flowDataService.getStartWfButton(filetypeid, workflowid, deptId, userid);
        } else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            res = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/flowData/getStartWfButton",
                    "fileTypeId=" + filetypeid + "&workflowId=" + workflowid + "&deptId=" + deptId + "&userId="
                            + userid);
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonb = JSONObject.parseObject(res);
        //{'88':'88','999':'99'}
        Set<String> jsonSet = jsonb.keySet();
        for (String str : jsonSet) {
            JSONObject jsob = new JSONObject();
            JSONObject btnJson = jsonb.getJSONObject(str);
            jsob.put("num", btnJson.get("num"));
            jsonArray.add(jsob);
        }
        return jsonArray;
    }

    /**
     * 
     * <B>方法名称：获取图形化监控数据</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getFlowStatus(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String getFlowStatus(String sysId, String fileTypeId, String workflowId, String recordId, String type) {
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        String res = "";
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processRelaDataService.getFlowStatus(sysId, fileTypeId, workflowId, recordId);
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getFlowStatus";
            res = HttpRequestUtil.sendGet(url, "fileTypeId=" + fileTypeId + "&recordId=" + recordId + "&sysId="
                    + sysId + "&workflowId=" + workflowId);
        }
        return res;
    }

    /**
     * 
     * <B>方法名称：获取文字式流程记录</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getFlowCourse(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public String getFlowCourse(String fileTypeId, String recordId, String type) {
        String res = "";
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processRelaDataService.getFlowcourse(fileTypeId, recordId);

        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getFlowcourse";
            res = HttpRequestUtil.sendGet(url, "fileTypeId=" + fileTypeId + "&recordId=" + recordId);

        }
        return res;

    }

    /**
     * 
     * <B>方法名称：获取正式意见</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getFormalIdea(com.sinosoft.sinoep.workflow.model.FlowClientData,
     *      java.lang.String)
     */
    @Override
    public String getFormalIdea(FlowClientData data, String type) {
        String res = "";
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processRelaDataService.getIdea(data.getFiletypeid(), "", data.getRecordid(), data.getDeptid(),
                    data.getUserid(), "2", "", "");
        }else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getIdea";
            res = HttpRequestUtil.sendGet(url, "fileTypeId=" + data.getFiletypeid() + "&workflowid=" + "&recordId="
                    + data.getRecordid() + "&deptId=" + data.getDeptid() + "&userId="
                    + data.getUserid() + "&isVisibleType=2&order=&con=");
        }
        return res;
    }

    /**
     * 
     * <B>方法名称：获取指定节点的参与者</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getJumpWfAppointWrite(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public JSONArray getJumpWfAppointWrite(String wfleveId, String type) {
        String res = "";
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        JSONArray arr = new JSONArray();
        JSONArray dept = null;
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processControlService.getJumpWfAppointWrite(wfleveId, "");
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/getJumpWfAppointWrite";
            res = HttpRequestUtil.sendGet(url, "wfleveId=" + wfleveId + "&write=");
        }
        JSONArray array = JSONArray.parseArray(res);
        JSONArray brray = JSONArray.parseArray(res);
        for (int i = 0; i < array.size(); i++) {
            boolean flag = false;
            JSONObject ajson = array.getJSONObject(i);
            dept = new JSONArray();
            int j = 0;
            while (j < brray.size()) {
                JSONObject bjson = brray.getJSONObject(j);
                if (ajson.get("deptId").equals(bjson.get("deptId"))) {
                    dept.add(bjson);
                    brray.remove(brray.get(j));
                    flag = true;
                    j = 0;
                }
                else {
                    j = j + 1;
                }
            }
            if (flag) {
                JSONObject json = new JSONObject();
                json.put("deptName", ajson.get("deptname"));
                json.put("res", dept);
                arr.add(json);
            }
        }
        return arr;
    }

    /**
     * 
     * <B>方法名称：获取退回意见</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService#getBackIdea(com.sinosoft.sinoep.workflow.model.FlowClientData)
     */
    @Override
    public String getBackIdea(FlowClientData data) throws IOException {
        String res = "";
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processRelaDataService.getIdeaByField(data.getFiletypeid(), data.getRecordid(), data.getDeptid(),
                    data.getUserid(), WorkFlowConfigConsts.BACK_IDEA_FIELDNAME, "2", "", "");
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/getIdea";
            res = HttpRequestUtil.sendGet(url, "fileTypeId=" + data.getFiletypeid() + "&workflowid=" + data
                    .getWorkflowid() + "&recordId=" + data.getRecordid() + "&deptId=" + data.getDeptid() + "&userId="
                    + data.getUserid() + "&isVisibleType=2&order=&con=");

        }
        return res;
    }

    /**
     * <B>方法名称：获取已经办理的流程节点</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService# getJumpWflevePassed(java.lang.String)
     */
    @Override
    public JSONObject getJumpWflevePassed(String workitemid, String type) {
        String res = "";
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用
            res = processControlService.getJumpWflevePassed(workitemid);
        }
        else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/getJumpWflevePassed";
            res = HttpRequestUtil.sendGet(url, "workitemid=" + workitemid);
        }
        JSONArray arr = JSONArray.parseArray(res);
        for (int i = 0; i < arr.size() - 1; i++) {
            for (int j = arr.size() - 1; j > i; j--) {
                if (arr.get(j).equals(arr.get(i))) {
                    arr.remove(j);
                }
            }
        }
        json.put("res", arr);
        return json;
    }

    /**
     * TODO 无用方法
     * <B>方法名称：获取所有流转中的流程</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.WorkFlowService# getSubflagFlow(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public Page getSubflagFlow(String title, String start, String end, Integer pageNum, Integer size) {
        String where = "where 1 = 1 ";
        if (StringUtils.isNotBlank(title)) {
            where += " and title like '%" + title + "%'";
        }
        if (StringUtils.isNotBlank(start) && StringUtils.isBlank(end)) {
            where += " and receivetime >= ' " + start + "'";
        }
        else if (StringUtils.isBlank(start) && StringUtils.isNotBlank(end)) {
            where += " and receivetime <= '" + end + "'";
        }
        else if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
            where += " and receivetime >=  '" + start + "'";
            where += " and receivetime <=  '" + end + "'";
        }
        String sqlData = "select * from (select rownum rownum_, row_.* from "
                + "(select * from flow_write t " + where + " order by t.receivetime desc) row_ where rownum <= "
                + ((pageNum - 1) * size + size) + ") where rownum_ > " + ((pageNum - 1) * size);
        String sqlcount = "select count(*) as num from flow_write " + where;
        List<Map<String, Object>> listdata = null;
        //调用工作流服务，查询统计数据
        listdata = processRelaDataService.getDataBySql(sqlData);
        List<Map<String, Object>> listCount = null;
        listCount = processRelaDataService.getDataBySql(sqlcount);
        BigDecimal count = null;
        if (listCount != null && listCount.size() > 0) {
            Map<String, Object> mapCount = listCount.get(0);
            count = (BigDecimal) mapCount.get("num");
        }
        Page page = new Page();
        if (count != null) {
            page = new Page(pageNum, size, count.intValue(), listdata);
        }
        else {
            page = new Page(pageNum, size, 0, listdata);
        }
        return page;
    }

    /**
     * 查询已办数据（接口）
     * TODO 
     * @author 李利广
     * @Date 2018年4月28日 下午2:39:46
     * @param userid
     * @param workFlowId
     * @param pageImpl
     * @param endTime
     * @param beginTime
     * @param title
     * @return
     */
	@Override
	public PageImpl getHanDone(String userid, String workFlowId,String fileTypeId, PageImpl pageImpl, String endTime, String beginTime,
			String title,String attr,String attr1,String stattag) {
		List<WorkFlowVO> listw = null;
		title = StringUtils.deleteWhitespace(title);
        int totalResult = 0;
        JSONObject json = new JSONObject();
        json.put("userId", userid);
        json.put("endTime", endTime);
        json.put("beginTime", beginTime);
        json.put("title", title);
        json.put("stateTag", stattag);
        json.put("start", ((pageImpl.getPageNumber()) - 1) * pageImpl.getPageSize());
        json.put("rowNum", pageImpl.getPageSize());
        json.put("sysId", ConfigConsts.SYSTEM_ID);
        
        JSONObject jsonP = new JSONObject();
        jsonP.put("userId", userid);
        jsonP.put("endTime", endTime);
        jsonP.put("beginTime", beginTime);
        jsonP.put("title", title);
        jsonP.put("sysId", ConfigConsts.SYSTEM_ID);
        jsonP.put("stateTag", stattag);
        jsonP.put("type", "2");//2代表查询已办个数，1代表查询待办个数
        String condition = "";
        if (StringUtils.isNotBlank(workFlowId)) {
        	workFlowId = CommonUtils.commomSplit(workFlowId);
            condition = " and workflowid in (" + workFlowId + ")";
        }
        if (StringUtils.isNotBlank(fileTypeId)) {
        	fileTypeId = CommonUtils.commomSplit(fileTypeId);
            condition = " and filetypeid in (" + fileTypeId + ")";
        }
        
        if (StringUtils.isNotBlank(attr)) {
        	if (StringUtils.isNotBlank(condition)) {
        		condition += " and attr = '" + attr + "' ";
        	}else {
        		condition = " and attr = '" + attr + "' ";
        	}
        }
        if (StringUtils.isNotBlank(attr1)) {
            if (StringUtils.isNotBlank(condition)) {
                condition += " and attr1 = '" + attr1 + "' ";
            }else {
                condition = " and attr1 = '" + attr1 + "' ";
            }
        }
        json.put("condition", condition);
        jsonP.put("condition", condition);
        
        String haddoStr = "";
        try {
			if (StringUtils.isNotBlank(userid)) {
			    if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
			    	haddoStr = processRelaDataService.getHadDone(json.toString());
			        totalResult = Integer.parseInt(processRelaDataService.getWRCount(jsonP.toString()));
			        listw = analyJsonToWorkFlow(haddoStr);
			    }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
			        haddoStr = HttpRequestUtil.sendGetNoEncode(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
			                + "/processdata/getHadDone", "param=" + URLEncoder.encode(json.toString(), "utf-8"));
			        totalResult = Integer.valueOf(HttpRequestUtil.sendGetNoEncode(
			                WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
			                        + "/processdata/getWRCount", "param=" + URLEncoder.encode(jsonP.toString(), "utf-8")));
			        listw = analyJsonToWorkFlow(haddoStr);
			    }
			}
			pageImpl.setFlag("1");
		} catch (Exception e) {
			e.printStackTrace();
			pageImpl.setFlag("0");
		}
        getWfleve(listw);//添加当前办理节点
        pageImpl.getData().setTotal(totalResult);
        pageImpl.getData().setRows(listw);
		return pageImpl;
	}

    /**
     * 查询已办列表
     * TODO
     * @author 李利广
     * @Date 2018年4月28日 下午2:39:46
     * @param userid
     * @param workFlowId
     * @param pageImpl
     * @param endTime
     * @param beginTime
     * @param title
     * @return
     */
    @Override
    public PageImpl getFlowReadList(String userid, String workFlowId,String fileTypeId, PageImpl pageImpl, String endTime, String beginTime,
                               String title,String attr,String attr1,String stattag) {
        List<WorkFlowVO> listw = null;
        StringBuilder querySql = new StringBuilder();
        StringBuilder sql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        Integer totalCount = 0;
        try {
            if (StringUtils.isNotBlank(userid)){
                List listzwf=new ArrayList(16);
                title = StringUtils.deleteWhitespace(title);
                String condSql = getContion(workFlowId, fileTypeId,title, attr, attr1, stattag,endTime,  beginTime);
                querySql.append("select (case t.stattag");
                //当已办表中状态为办结（4）时，使用业务中的状态值
                querySql.append("           when '").append(ConfigConsts.END_FLAG).append("' then sf.subflag");
                //流程已办表中办结状态时0，因此当已办表中状态为0时，使用业务中的状态值
                querySql.append("           when '").append(ConfigConsts.START_FLAG).append("' then sf.subflag");
                querySql.append("           else t.stattag");
                querySql.append("        end) stattag,");
                querySql.append(" t.id workitemid,t.title,t.filetypeid,t.workflowid,t.workflowname,t.filetypename,t.sys_id,t.recordid,t.createtime,t.formurl formid,t.readtime,t.draftuser userid,t.deptid,t.handdoneurl url");
                querySql.append("   from epcloud.flow_read t, sys_flow_subflag sf");
                querySql.append(" where t.userid =? ");
                listzwf.add(userid);
                querySql.append("       and t.deptid =? ");
                listzwf.add(UserUtil.getCruDeptId());
                querySql.append(condSql);
                //分页查询数据
                sql.append("select * from ");
                sql.append("  (select rownum rn, a.* from (");
                sql.append(querySql);
                sql.append("  order by t.readtime desc");
                sql.append("  ) a");
                sql.append("    where rownum <= ").append(pageImpl.getPageNumber()*pageImpl.getPageSize());
                sql.append("  ) ");
                sql.append(" where rn >= ").append((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
                listw = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<>(WorkFlowVO.class),listzwf.toArray());
                for (WorkFlowVO vo:listw) {
                    String draftUserName = "";
                    MessageUser messagecreUser = userInfoService.getUserInfoById(vo.getUserid());
                    if (messagecreUser.getUserInfo().size() > 0) {
                        UserInfo userInfo = messagecreUser.getUserInfo().get(0);
                        draftUserName = userInfo.getUserFullName();
                    }
                    vo.setUsername(draftUserName);
                }
                //查询总数据个数
                countSql.append("	select count(1) from ( ");
                countSql.append(querySql + ")");
                totalCount = jdbcTemplate.queryForObject(countSql.toString(), Integer.class,listzwf.toArray());
                pageImpl.setFlag("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pageImpl.setFlag("0");
        }
        getWfleve(listw);//添加当前办理节点
        pageImpl.getData().setTotal(totalCount);
        pageImpl.getData().setRows(listw);
        return pageImpl;
    }
	
	/**
	 * 已办列表获取已办数据的当前办理节点
	 * TODO 
	 * @author 李利广
	 * @Date 2018年11月8日 上午10:05:22
	 * @param listw
	 * @return
	 */
	private List<WorkFlowVO> getWfleve(List<WorkFlowVO> listw){
		for (WorkFlowVO vo : listw) {
			String workflowid = vo.getWorkflowid();
			String recordid = vo.getRecordid();
			String sql = "select t.wfleveid,t.wflevename from flow_write t where t.recordid = '"+recordid+"' and t.workflowid = '"+workflowid+"'";
			List<Map<String, Object>> data = getDataBySql(sql);
			if (!data.isEmpty()) {
				vo.setWflevelId(data.get(0).get("wfleveid").toString());
				vo.setWflevelName(data.get(0).get("wflevename").toString());
			}
		}
		return listw;
	}

	/**
	 * TODO 自定义sql查询工作流数据（epcloud库）
	 * @author 武帅
	 * @Date 2018年7月21日 下午2:57:03
	 * @param sql
	 * @return
	 */
    @Override
    public List<Map<String, Object>> getDataBySql(String sql) {
        List<Map<String, Object>>  data = new ArrayList<Map<String, Object>>();
        try {
        	data = processRelaDataService.getDataBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
	    return data;
    }
    
    /**
     * TODO 取当前用户维护的流程
     * @author 李利广
     * @Date 2018年7月23日 下午8:53:28
     * @return
     */
    @Override
    public List<FlowWorkflowVO> getManageFlowByUser(){
    	List<FlowWorkflowVO> workflowList = new ArrayList<>();
    	String userId = UserUtil.getCruUserId();
    	String orgId = UserUtil.getCruOrgId();
    	String res = "";
    	try {
			if (StringUtils.isNotBlank(userId)) {
			    if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
			    	res = processRelaDataService.getManageFlowByUser(userId,orgId,ConfigConsts.SYSTEM_ID);
			    }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
			        res = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
	                        + "/processdata/getManageFlowByUser", "userId="+userId+"&orgId="+orgId+"&sysId="+ConfigConsts.SYSTEM_ID);
			    }
			}
			if(StringUtils.isNotBlank(res) && !"[]".equals(res)){
				workflowList = JSONUtils.getList(res, FlowWorkflowVO.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return workflowList;
    }

    /**
     * TODO 获取当前节点配置的按钮
     * @author 李利广
     * @Date 2019年01月11日 16:22:41
     * @param workItemId
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONArray getButtons(String workItemId){
        String res = "";
        JSONArray buttons = new JSONArray();
        if (StringUtils.isNotBlank(workItemId)){
            try {
                if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                    res = processRelaDataService.getButton(workItemId);
                }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                    res = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                            + "/processdata/getButton", "workitemid=" + workItemId);
                }
                if (StringUtils.isNotBlank(res)){
                    buttons = JSONArray.parseArray(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return buttons;
    }

    private String getContion(String workFlowId,String fileTypeId,String title, String attr,String attr1,String stattag,String endTime, String beginTime){
        StringBuilder cond = new StringBuilder();
        if (StringUtils.isNotBlank(workFlowId)){
            workFlowId = CommonUtils.commomSplit(workFlowId);
            cond.append(" and t.workFlowId in (").append(workFlowId).append(")");
        }
        if (StringUtils.isNotBlank(fileTypeId)){
            fileTypeId = CommonUtils.commomSplit(fileTypeId);
            cond.append(" and t.fileTypeId in (").append(fileTypeId).append(")");
        }
        if (StringUtils.isNotBlank(title)){
            cond.append(" and t.title like '%").append(title).append("%'");
        }
        if (StringUtils.isNotBlank(attr)){
            cond.append(" and t.attr = '").append(attr).append("'");
        }
        if (StringUtils.isNotBlank(attr1)){
            cond.append(" and t.attr1 = '").append(attr1).append("'");
        }
        if (StringUtils.isNotBlank(endTime)){
            cond.append(" and t.readtime <= '").append(endTime).append("'");
        }
        if (StringUtils.isNotBlank(beginTime)){
            cond.append(" and t.readtime >= '").append(beginTime).append("'");
        }
        cond.append(" and t.filetypeid = sf.filetypeid");
        cond.append(" and t.workflowid = sf.workflowid");
        cond.append(" and t.recordid = sf.recordid");
        if (StringUtils.isNotBlank(stattag)){
            cond.append(" and sf.subflag = '").append(stattag).append("'");
        }
        return cond.toString();
    }

    /**
     * TODO 根据操作ID，获取操作配置数据
     * @author 李利广
     * @Date 2019年03月27日 17:25:38
     * @param wfOperateId
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject getOprateInfo(String wfOperateId){
        JSONObject json = new JSONObject();
        StringBuilder sql = new StringBuilder("select t.wfoperateid,t.deptlevel,f.event_path");
        sql.append("    from flow_wfoperate t,flow_event f");
        sql.append("    where t.deptlevel = f.event_id and t.wfoperateid = '"+wfOperateId+"'");
        List<Map<String, Object>> data = getDataBySql(sql.toString());
        if (data.size() > 0){
            Map<String, Object> info = data.get(0);
            json.put("wfOprateId",info.get("wfoperateid").toString());
            json.put("deptLevel",info.get("deptlevel").toString());
            String path = info.get("event_path").toString();
            if (StringUtils.isNotBlank(path)){
                path = path.substring(path.indexOf("/yycx"),path.length()) + "/execution";
                json.put("eventPath",path);
            }else{
                json.put("eventPath","");
            }
        }
        return json;
    }

    /**
     * TODO 获取所有的流程类型
     * @author 李利广
     * @Date 2019年04月29日 21:14:14
     * @param workFlowIds 流程ID
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Override
    public List<Map<String,Object>> getFlowWorkFlow(String workFlowIds){
        StringBuilder sql = new StringBuilder("select workflowid,workflowname from flow_workflow where sys_id ='97206' and veision ='1'");
        if(StringUtils.isNotBlank(workFlowIds)){
            workFlowIds = CommonUtils.commomSplit(workFlowIds);
            sql.append(" and workflowId in ("+workFlowIds+")");
        }
        return this.getDataBySql(sql.toString());
    }

}
