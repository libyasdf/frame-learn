package com.sinosoft.sinoep.workflow.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.workflow.constant.WorkFlowConfigConsts;
import com.sinosoft.sinoep.workflow.model.FlowClientData;

import org.apache.commons.lang.StringUtils;
import workflow.spring.ProcessRelaDataService;
import workflow.vo.FlowReadVO;
import workflow.vo.FlowWriteVO;

public class WorkFlowUtil {
	
	private static ProcessRelaDataService processRelaDataService = (ProcessRelaDataService) SpringBeanUtils.getBean(
            "processRelaDataService");

    /**
     * 
     * <B>方法名称：提交流程时封装bean数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:55:23
     * @param data
     * @return ParaBeanForXml
     */
    public static ParaBeanForXml beanSetUp(FlowClientData data) {
        ParaBeanForXml bean = new ParaBeanForXml();
        bean.setFlag(data.getFlag());
        bean.setAttr(data.getAttr());
        bean.setAttr1(data.getAttr1());
        bean.setWorkitemid(data.getWorkitemid());
        bean.setFiletypeid(data.getFiletypeid());
        bean.setWorkflowid(data.getFiletypeid());
        bean.setDeptid(data.getDeptid());
        bean.setUserid(data.getUserid());
        bean.setStartwfleveId(data.getWfleveid());
        bean.setWfleveid(data.getWfleveid());
        bean.setWfoperateid(data.getWfoperateid());
        bean.setSubflowlist(data.getSubflowlist());
        bean.setSubflowdeptlist(data.getSubflowdeptlist());
        bean.setSelectuserlist(data.getSelectuserlist());
        bean.setSelectdeptlist(data.getSelectdeptlist());
        bean.setTitle(data.getTitle());
        bean.setRecordid(data.getRecordid());
        bean.setSysid(data.getSysid());
        bean.setSubflag("0");
        bean.setIdea(data.getIdea());// 工作流提交正式意见
        return bean;
    }

    /**
     * 
     * <B>方法名称：提交流程时封装bean数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:44:30
     * @param data：工作流参数
     * @return ParaBeanForXml
     */
    public static ParaBeanForXml beanSetUpForWorkFlow(FlowClientData data) {
        ParaBeanForXml bean = new ParaBeanForXml();
        bean.setFlag(data.getFlag());
        bean.setWorkitemid(data.getWorkitemid());
        bean.setFiletypeid(data.getFiletypeid());
        bean.setDeptid(data.getDeptid());
        bean.setUserid(data.getUserid());
        bean.setStartwfleveId(data.getWfleveid());
        bean.setWfleveid(data.getWfleveid());
        bean.setWfoperateid(data.getWfoperateid());
        bean.setSubflowlist(data.getSubflowlist());
        bean.setSubflowdeptlist(data.getSubflowdeptlist());
        bean.setSelectuserlist(data.getSelectuserlist());
        bean.setSelectdeptlist(data.getSelectdeptlist());
        bean.setTitle(data.getTitle());
        bean.setRecordid(data.getRecordid());
        bean.setSubflag(data.getSubflag());
        bean.setIdea(data.getIdea());// 工作流提交正式意见
        return bean;
    }

    /**
     * 
     * <B>方法名称：根据流程type获取业务表名和where语句</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：goulijun
     * @cretetime:2017年7月26日 上午10:23:35
     * @param type
     * @param recordid
     * @return Map<String,String>
     */
    public static Map<String, String> getTablename2Where(String type, String recordid) {
        String tablename = "", where = "", filedname = "";
        Map<String, String> returnmap = new HashMap<String, String>();
        //根据工作流流程分类判断业务表以及主键id值，
        //例如：线上收文流程流程分类为RECEIPT（需要在对应的配置文件中写清相关流程分类标识），对应的业务表名为“Archives_In_Outer”,主键id名为“id”
//        if (type.equals(GlobalNames.RECEIPT)) {// 线上收文
//            tablename = "ArchivesInOuter";
//            where = " id = '" + recordid + "' ";
//        }
        returnmap.put("tablename", tablename);
        returnmap.put("where", where);
        returnmap.put("filedname", filedname);
        return returnmap;
    }

    /**
     * 
     * <B>方法名称：设置response属性</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月10日 上午10:42:01
     * @param response
     * @param json：json对象
     * @return void
     * @throws IOException void
     */
    public static void setResponse(HttpServletResponse response, JSONObject json) throws IOException {
        //让浏览器用utf8来解析返回的数据  
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.getWriter().print(json);
    }

    /**
     * 
     * <B>方法名称：设置response属性</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月10日 上午10:42:01
     * @param response
     * @param json：json对象
     * @return void
     * @throws IOException void
     */
    public static void setArrayResponse(HttpServletResponse response, JSONArray json) throws IOException {
        //让浏览器用utf8来解析返回的数据  
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.getWriter().print(json);
    }

    /**
     * 
     * <B>方法名称：转换字符串为Boolean类型</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param str
     * @return
     */
    public static Boolean getBoolean(String str) {
        return Boolean.valueOf(str).booleanValue();
    }
    
	/**
	 * TODO 保存临时意见
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:40:42
	 * @param workItemId	待办工作项ID
	 * @param idea
	 * @return
	 */
    public static Boolean saveTempIdea(String workItemId, String idea) {
    	String type = ConfigConsts.SERVICE_TYPE;
        boolean res = false;
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用R
            res = processRelaDataService.saveIdeaTemp(workItemId, "idea", idea);
        }else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/saveIdeaTemp";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "workitemid=" + workItemId
                    + "&fieldName=idea&idea=" + idea));
        }
        return res;
    }

    /**
     * TODO 根据workItemId获取一条已办数据
     * @author 李利广
     * @Date 2019年01月15日 10:56:16
     * @param workItemId
     * @return workflow.vo.FlowReadVO
     */
    public static FlowReadVO getFlowReadVo(String workItemId){
        String data = "";
        FlowReadVO vo = null;
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            data = processRelaDataService.getReadVoById(workItemId);
        }else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            data = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                    + "/processdata/getReadVoById", "workitemid = " + workItemId);
        }
        if (StringUtils.isNotBlank(data)){
            vo = JSONObject.parseObject(data,FlowReadVO.class);
        }
        return vo;
    }

    /**
     * TODO 根据workItemId获取一条待办数据
     * @author 李利广
     * @Date 2019年01月15日 10:58:09
     * @param workItemId
     * @return workflow.vo.FlowWriteVO
     */
    public static FlowWriteVO getWriteVoById(String workItemId) {
        String res = "";
        FlowWriteVO vo = null;
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            res = processRelaDataService.getWriteVoById(workItemId);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            res = HttpRequestUtil.sendGet(WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL
                    + "/processdata/getWriteVoById", "workitemid=" + workItemId);
        }
        if (StringUtils.isNotBlank(res)){
            vo = JSONObject.parseObject(res,FlowWriteVO.class);
        }
        return vo;
    }

}