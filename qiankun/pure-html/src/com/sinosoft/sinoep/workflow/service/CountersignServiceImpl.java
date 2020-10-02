package com.sinosoft.sinoep.workflow.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.api.user.vo.MessageUser;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.workflow.constant.WorkFlowConfigConsts;
import com.sinosoft.sinoep.workflow.model.WorkFlowVO;
import com.sinosoft.sinoep.workflow.util.WorkFlowUtil;
import com.sinosoft.util.tool.JStringToolkit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import workflow.spring.ProcessControlService;

/**
 * 
 * <B>系统名称：会签操作业务模块</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Service
public class CountersignServiceImpl implements CountersignService {

    ProcessControlService processControlService = (ProcessControlService) SpringBeanUtils
            .getBean("processControlService");

    @Autowired
    UserInfoService userInfoService;

    /**
     * 
     * <B>方法名称：添加删除会签分支</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.CountersignService#addAndDelHq(java.lang.String, java.lang.String)
     */
    @SuppressWarnings({ "static-access", "rawtypes" })
    @Override
    public String addAndDelHq(String workitemid, String type) {
        // 取得发起会签人信息
        // 发起会签人信息String[] 0-发起会签人所在的日志ID 1-发起会签人用户名和发起节点名称 2-发起会签时间
        HashMap<String, String> wfMap = new HashMap<String, String>();
        JSONObject result = new JSONObject();
        JSONArray json = new JSONArray();
        JSONArray jsonde = new JSONArray();
        JSONArray jsonaddhq = new JSONArray();
        JSONArray jsondelhq = new JSONArray();
        JSONObject jsonObject = null;
        JSONObject jsonObject_del = null;
        // 得到发起会签人的信息
        if (workitemid != null && !workitemid.equals("")) {
            String jso = "";
            String jsode = "";
            // 取得发起会签人信息
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                jso = processControlService.getForceStartExamineUser(workitemid,"0");
                jsode = processControlService.getDelStartExamineUser(workitemid);
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                jso = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/proceecontrol/getForceStartExamineUser", "workitemid=" + workitemid);
                jsode = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/proceecontrol/getDelStartExamineUser", "workitemid=" + workitemid);
            }
            json = json.fromObject(jso);
            jsonde = json.fromObject(jsode);
            String json_c = json.getString(0);
            String json_c_del = jsonde.getString(0);
            jsonObject = JSONObject.fromObject(json_c);
            jsonObject_del = JSONObject.fromObject(json_c_del);
            for (Iterator iter = jsonObject.keys(); iter.hasNext();) { // 先遍历整个json对象
                String key = (String) iter.next();
                String value = jsonObject.getString(key);
                wfMap.put(key, value);
            }
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                String jsonadd = processControlService.getForceAddHq(workitemid, jsonObject.getString("id"));// 可添加会签分支
                jsonaddhq = jsonaddhq.fromObject(jsonadd);
                String jsondel = processControlService.getForceDelHq(workitemid, jsonObject_del.getString("id"));// 可删除会签分支
                jsondelhq = jsondelhq.fromObject(jsondel);
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                String jsonadd = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/proceecontrol/getForceAddHq", "workitemid=" + workitemid + "&nodId=" + jsonObject.getString(
                                "id"));// 可添加会签分支
                jsonaddhq = jsonaddhq.fromObject(jsonadd);
                String jsondel = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/proceecontrol/getForceDelHq", "workitemid=" + workitemid + "&nodId=" + jsonObject_del
                                .getString("id"));// 可删除会签分支
                jsondelhq = jsondelhq.fromObject(jsondel);
            }
            for (Iterator iter = jsonObject_del.keys(); iter.hasNext();) {
                String key = (String) iter.next();
                String value = jsonObject_del.getString(key);
                jsonObject.put(key, value);
            }
            result.put("json", json);
            result.put("jsonaddhq", jsonaddhq);
            result.put("jsondelhq", jsondelhq);
        }
        return result.toString();
    }

    /**
     * 
     * <B>方法名称：检查增删会签按钮显示方法方法(isDisAddHq/isDisDelHq)</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.CountersignService#addAndDelHq_list(java.lang.String, java.lang.String)
     */
    @Override
    public boolean addAndDelHq_list(String workitemid, String type) {
        boolean isDisAddHq_ = false;
        boolean isDisDelHq_ = false;
        boolean falg = false;
        if (StringUtils.isNotBlank(workitemid)) {
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                isDisAddHq_ = processControlService.isDisAddHq(workitemid);// 功能:判断是否显示能增加会签分支
                isDisDelHq_ = processControlService.isDisDelHq(workitemid);// 功能:判断是否可以调整（增加和删除）会签分支
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                isDisAddHq_ = WorkFlowUtil.getBoolean(HttpRequestUtil.sendGet(
                        WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                                + "/proceecontrol/isDisHq", "workitemid=" + workitemid));
                isDisDelHq_ = WorkFlowUtil.getBoolean(HttpRequestUtil.sendGet(
                        WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                                + "/proceecontrol/isDisDelHq", "workitemid=" + workitemid));
            }
            if (isDisAddHq_ || isDisDelHq_) {
                falg = true;
            }
        }
        return falg;
    }

    /**
     * 
     * <B>方法名称：删除会签分支/和增加会签分支</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.CountersignService#addAndDelHq_do(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public String addAndDelHq_do(String workitemid, String delHq, String addHq, String type) {
        ProcessControlService processControlService = (ProcessControlService) SpringBeanUtils
                .getBean("processControlService");
        // 执行结果信息
        String res = "";
        Vector<String> delVec = new Vector<String>();
        Vector<String> addVec = new Vector<String>();
        JSONObject result = new JSONObject();
        if (!delHq.equals("")) {
            if (!delHq.equals("")) {
                String[] delArr = JStringToolkit.split(delHq, ",");
                if (delArr != null) {
                    for (int i = 0; i < delArr.length; i++) {
                        delVec.add(delArr[i]);
                    }
                }
            }
        }
        // 要增加的会签分支
        if (!addHq.equals("")) {
            if (!addHq.equals("")) {
                String[] addArr = JStringToolkit.split(addHq, ",");
                if (addArr != null) {
                    for (int i = 0; i < addArr.length; i++) {
                        addVec.add(addArr[i]);
                    }
                }
            }
        }
        if (addVec.size() > 0) {
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                res = processControlService.forceAddHq(workitemid, addHq);
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                res = HttpRequestUtil.sendPost(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/proceecontrol/forceAddHq",
                        "workitemid=" + workitemid + "&addStr=" + addHq);
            }
        }
        if (delVec.size() > 0) {
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                if (res.equals("")) {
                    res = processControlService.forceDelHq(workitemid, delHq);
                }
                else {
                    res = res + ";" + processControlService.forceDelHq(workitemid, delHq);
                }
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                if (res.equals("")) {
                    res = HttpRequestUtil.sendPost(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                            + "/proceecontrol/forceDelHq", "workitemid=" + workitemid + "&delStr=" + delHq);
                }
                else {
                    res = res + ";" + HttpRequestUtil.sendPost(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                            + "/proceecontrol/forceDelHq", "workitemid=" + workitemid + "&delStr=" + delHq);
                }

            }
        }
        result.put("res", res);
        return result.toString();
    }

    /**
     * 
     * <B>方法名称：查询强制增删会签分支</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.CountersignService#forceAddHq(java.lang.String, java.lang.String)
     */
    @SuppressWarnings({ "static-access", "rawtypes" })
    @Override
    public String forceAddHq(String workitemid, String type) {
        // 发起会签人信息String[] 0-发起会签人所在的日志ID 1-发起会签人用户名和发起节点名称 2-发起会签时间
        HashMap<String, String> wfMap = new HashMap<String, String>();
        JSONObject result = new JSONObject();
        JSONArray json = new JSONArray();
        JSONArray jsondel = new JSONArray();
        JSONArray jsonaddhq = new JSONArray();
        JSONObject jsonObject = null;
        JSONObject jsonObjectde = null;
        // 取得发起会签人信息
        if (workitemid != null && !workitemid.equals("")) {
            String jso = "";
            String jsod = "";
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                jso = processControlService.getForceStartExamineUser(workitemid,"0");
                jsod = processControlService.getForceStartExamineUser(workitemid,"0");
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                jso = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/proceecontrol/getForceStartExamineUser",
                        "workitemid=" + workitemid);
                jsod = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/proceecontrol/getForceStartExamineUser",
                        "workitemid=" + workitemid);
            }
            json = json.fromObject(jso);
            jsondel = jsondel.fromObject(jsod);
            String json_c = json.getString(0);
            String json_d = jsondel.getString(0);
            jsonObject = JSONObject.fromObject(json_c);
            jsonObjectde = JSONObject.fromObject(json_d);
            for (Iterator iter = jsonObject.keys(); iter.hasNext();) { // 先遍历整个json对象
                String key = (String) iter.next();
                String value = jsonObject.getString(key);
                wfMap.put(key, value);
            }
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                String jsonadd = processControlService.getForceAddHq(workitemid, jsonObject.getString("id"));// 可添加会签分支
                jsonaddhq = jsonaddhq.fromObject(jsonadd);
                String jsondell = processControlService.getForceDelHq(workitemid, jsonObjectde.getString("id"));// 可添加会签分支
                jsondel = jsondel.fromObject(jsondell);
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                String jsonadd = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/proceecontrol/getForceAddHq", "workitemid=" + workitemid + "&nodId=" + jsonObject.getString(
                                "id"));// 可添加会签分支
                jsonaddhq = jsonaddhq.fromObject(jsonadd);
                String jsondell = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                        + "/proceecontrol/getForceDelHq", "workitemid=" + workitemid + "&nodId=" + jsonObjectde
                                .getString("id"));// 可添加会签分支
                jsondel = jsondel.fromObject(jsondell);
            }
            result.put("json", json);
            result.put("jsonaddhq", jsonaddhq);// 增加会签分支
            result.put("jsondel", jsondel);// 删除会签分支
        }
        return result.toString();
    }

    /**
     * 
     * <B>方法名称：检查增删会签按钮显示方法</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.CountersignService#isDisForceAddHq(java.lang.String, java.lang.String)
     */
    @Override
    public boolean isDisForceAddHq(String workitemid, String type) {
        boolean isDisForceAddHq = false;
        boolean isDisForceDelHq_ = false;
        boolean falg = false;
        if (StringUtils.isNotBlank(workitemid)) {
            ProcessControlService processControlService = (ProcessControlService) SpringBeanUtils
                    .getBean("processControlService");
            if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                isDisForceAddHq = processControlService.isDisForceAddHq(workitemid);// 功能:判断是否显示能增加会签分支
                isDisForceDelHq_ = processControlService.isDisForceDelHq(workitemid);// 功能:判断是否可以调整（增加和删除）会签分支
            }
            else if (type.equals(ConfigConsts.REST_TYPE)) {
                isDisForceAddHq = WorkFlowUtil.getBoolean(HttpRequestUtil
                        .sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/isDisForceAddHq",
                                "workitemid="
                                        + workitemid));// 功能:判断是否显示能增加会签分支
                isDisForceDelHq_ = WorkFlowUtil.getBoolean(HttpRequestUtil
                        .sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/proceecontrol/isDisForceDelHq",
                                "workitemid="
                                        + workitemid));// 功能:判断是否可以调整（增加和删除）会签分支
            }
            if (isDisForceAddHq || isDisForceDelHq_) {
                falg = true;
            }
        }
        return falg;
    }

    /**
     * 
     * <B>方法名称：强制删除会签分支/和增加会签分支</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.CountersignService#forceaddHq_do(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public String forceaddHq_do(String workitemid, String addHq, String delHq, String type) {
        // 执行结果信息
        String res = "";
        JSONObject result = new JSONObject();
        if (StringUtils.isEmpty(type)) {
            type = ConfigConsts.SERVICE_TYPE;
        }
        if (!addHq.equals("")) {
            Vector<String> addVec = new Vector<String>();
            if (!addHq.equals("")) {
                String[] addArr = JStringToolkit.split(addHq, ",");
                if (addArr != null) {
                    for (int i = 0; i < addArr.length; i++) {
                        addVec.add(addArr[i]);
                    }
                }
            }
            if (addVec.size() > 0) {
                if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                    res = processControlService.forceAddHq(workitemid, addHq);
                }
                else if (type.equals(ConfigConsts.REST_TYPE)) {
                    res = HttpRequestUtil.sendPost(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                            + "/proceecontrol/forceAddHq", "workitemid=" + workitemid + "&addStr=" + addHq);
                }
            }
        }
        if (!delHq.equals("")) {
            Vector<String> delVec = new Vector<String>();
            if (!delHq.equals("")) {
                String[] delArr = JStringToolkit.split(delHq, ",");
                if (delArr != null) {
                    for (int i = 0; i < delArr.length; i++) {
                        delVec.add(delArr[i]);
                    }
                }
            }
            if (delVec.size() > 0) {
                if (type.equals(ConfigConsts.DUBBO_TYPE)) {
                    res = res + ";" + processControlService.forceDelHq(workitemid, delHq);
                }
                else if (type.equals(ConfigConsts.REST_TYPE)) {
                    res = res + ";" + HttpRequestUtil.sendPost(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                            + "/proceecontrol/forceDelHq", "workitemid=" + workitemid + "&delStr=" + delHq);
                }
            }
        }
        result.put("res", res);
        return result.toString();
    }

    /**
     * <B>方法名称：列表筛选</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.CountersignService#list(com.sinosoft.sinoep.common.util.Page)
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
                        JSONObject json = JSONObject.fromObject(m.getUserInfo().get(0));
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

}
