package com.sinosoft.sinoep.workflow.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.sinoep.workflow.model.FlowClientData;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：工作流操作类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
public interface WorkFlowClientService {

    /**
     * 
     * <B>方法名称：提交工作流方法</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月9日 上午10:24:46
     * @param userid：用户id
     * @param jsonPar：json字符串
     * @param remindtype：提醒类型
     * @param data：工作流参数数据包装类
     * @param extendattr:流程扩展参数
     * @return JSONObject
     * @throws UnsupportedEncodingException
     */
    public JSONObject submitToFlow(String userid, String jsonPar, String remindtype, FlowClientData data, String extendattr)
            throws UnsupportedEncodingException;

    /**
     * 
     * <B>方法名称：保存临时意见</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:40:58
     * @param workItemId：工作项ID
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @param idea：意见
     * @param ideaName：意见域名称
     * @return Boolean
     */
    public Boolean saveTempIdea(String workItemId, String type, String idea,String ideaName);

    /**
     * 
     * <B>方法名称：签收工作项</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:06
     * @param workitemid:工作项ID
     * @param type:调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return boolean
     */
    public boolean signItem(String workitemid, String type);

    /**
     * 
     * <B>方法名称：取临时意见</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:09
     * @param workitemid：工作项ID
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return String
     */
    public String getTempIdea(String workitemid, String type);

    /**
     * 
     * <B>方法名称：取正式意见</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:12
     * @param data：工作流参数数据包装类
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONArray
     */
    public JSONArray getFormalIdea(FlowClientData data, String type);

    /**
     * 
     * <B>方法名称：取待办配置</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:16
     * @param workitemid：待办工作项ID
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     */
    public JSONObject getDaiBanData(String workitemid, String type);

    /**
     * 
     * <B>方法名称：收回</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:26
     * @param workitemid：已办工作项ID
     * @param isBackIdea：1是收回意见，0是不收回
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     */
    public JSONObject cancelFlow(String workitemid, String isBackIdea, String type) throws IOException;

    /**
     * 
     * <B>方法名称：点击待办和已办调用的服务</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月9日 上午10:26:43
     * @param userid：用户id
     * @param formClientData：工作流参数数据包装类
     * @return String
     */
    public String getFlowData(String userid, FlowClientData formClientData);

    /**
     * 
     * <B>方法名称：撤办</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:35
     * @param workitemid：工作项id
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     */
    public JSONObject removeFlow(String workitemid, String type);

    /**
     * 
     * <B>方法名称：增加会签</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:40
     * @param workitemid：工作项id
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     */
    public JSONObject getsignatureFlow(String workitemid, String type);

    /**
     * 
     * <B>方法名称：删除会签</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:40
     * @param workitemid：工作项id
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     */
    public JSONObject delsignatureFlow(String workitemid, String type);

    /**
     * 
     * <B>方法名称：恢复撤办</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:40
     * @param workitemid：工作项id
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     */
    public JSONObject resumeFlow(String workitemid, String type);

    /**
     * 
     * <B>方法名称：跳节点</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:44
     * @param params：json对象（存放request请求的参数）
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     */
    public JSONObject jumpToWfleve(JSONObject params, String type) throws IOException;

    /**
     * 
     * <B>方法名称：办结流程</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:46
     * @param workitemid：工作项id
     * @param subflag：状态
     * @param recordid：业务主键id
     * @return void
     */
    public JSONObject completeFlow(String workitemid, String subflag, String recordid, String idea,String flag);

    /**
     * 
     * <B>方法名称：办结</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月8日 上午10:46:15
     * @param workitemid:工作项id
     * @param subflag：状态
     * @param flag 流程终止方式
     * @param recordid：业务主键id
     * @return JSONObject
     */
    public JSONObject finishFlow(String workitemid, String subflag,String flag, String recordid,String idea) throws IOException;

    /**
     * 
     * <B>方法名称：保存签发时间及签发人信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月10日 上午10:07:01
     * @param contextJson：工作流返回的相关数据
     * @return String
     * @throws IOException
     */
    public String saveSignInfo(JSONObject contextJson) throws IOException;

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
	public JSONArray getStartWflevel(String fileTypeId, String workflowId, String deptId, String userId) throws Exception;

    /**
     * 根据流程ID与业务ID删除流程信息（包括：待办、已办、流程记录、意见信息）
     * @auther 李利广
     * @Date 2019年1月7日 下午2:22:47
     * @param workflowId
     * @param recordId
     * @return
     */
    JSONObject deleteFlowInfo(String workflowId,String recordId) throws Exception;
}
