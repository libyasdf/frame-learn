package com.sinosoft.sinoep.workflow.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.workflow.model.FlowClientData;

import workflow.vo.FlowWorkflowVO;

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
public interface WorkFlowService {

    /**
     * 
     * <B>方法名称：获取待办信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月9日 上午10:55:14
     * @param userid：用户id
     * @param pageNum：当前页
     * @param showCount：每页条数
     * @param endTime：结束时间
     * @param beginTime：开始时间
     * @param title：标题
     * @param attr：业务属性1
     * @param attr1：业务属性2（存放缓急、密级等业务属性。出待办列表可以按此业务属性排序）
     * @param sysid：系统id
     * @param orgid：组织id
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return Page
     * @throws UnsupportedEncodingException
     */
    public PageImpl getWaitDoList( PageImpl pageImpl,String workFlowId,String fileTypeId, String endTime, String beginTime, String title, String attr, String attr1,String recordId);

    /**
     * 查询指定流程类型的已办数据(接口)
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
	public PageImpl getHanDone(String userid, String workFlowId,String fileTypeId, PageImpl pageImpl, String endTime, String beginTime,
			String title,String attr,String attr1,String stattag);

    /**
     * 查询指定流程类型的已办数据
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
    public PageImpl getFlowReadList(String userid, String workFlowId,String fileTypeId, PageImpl pageImpl, String endTime, String beginTime,
                               String title,String attr,String attr1,String stattag);
	
    /**
     * 
     * <B>方法名称：获取已办数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午1:41:02
     * @param workitemid:工作项ID
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONObject
     */
    public JSONObject getYiBanData(String workitemid, String type);

    /**
     * 
     * <B>方法名称：获取强制会签列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:49:55
     * @param userid：用户id
     * @param pageNum：当前页
     * @param endTime：结束时间
     * @param beginTime：开始时间
     * @param title：标题
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return Page
     * @throws UnsupportedEncodingException
     */
    public Page getForceHqList(String userid, String pageNum, String endTime, String beginTime, String title,
            String type) throws UnsupportedEncodingException;

    /**
     * 
     * <B>方法名称：查询跳节点列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:50:01
     * @param userid：用户id
     * @param pageNum：当前页
     * @param type:调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return Page
     * @throws UnsupportedEncodingException
     */
    public Page getJumpNodeList(String userid, String pageNum, String type) throws UnsupportedEncodingException;

    /**
     * 
     * <B>方法名称：获取根据部门统计数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:50:12
     * @param start：开始时间
     * @param end：结束时间
     * @param year：年份
     * @param deptid：部门id
     * @param pageNum：当前页
     * @param size：每页条数
     * @return Page
     */
    public Page getTjdept(String start, String end, String year, String deptid, Integer pageNum, Integer size);

    /**
     * 
     * <B>方法名称：获取根据用户统计数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:50:20
     * @param start：开始时间
     * @param end：结束时间
     * @param year：年份
     * @param userid：用户id
     * @param pageNum：当前页
     * @param size：每页条数
     * @return Page
     */
    public Page getTjUser(String start, String end, String year, String userid, Integer pageNum, Integer size);

    /**
     * 
     * <B>方法名称：从工作流日志表中获取历年</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:50:28
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> getYear();

    /**
     * 
     * <B>方法名称：列表筛选</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午4:34:15
     * @param page：分页列表数据
     * @return Page
     */
    public Page list(Page page);

    /**
     * 
     * <B>方法名称：获取流程列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月9日 上午9:11:50
     * @param userId：用户id
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @param deptid：部门id
     * @return JSONObject
     */
    public JSONObject getworkflow(String userId, String type, String deptid) throws UnsupportedEncodingException;

    /**
     * 
     * <B>方法名称：获取拟稿状态下的按钮</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月9日 上午11:02:50
     * @param filetypeid：流程ID
     * @param workflowid：流程唯一ID
     * @param userid：用户id
     * @return JSONArray
     */
    public JSONArray getStartWfButton(String filetypeid, String workflowid, String userid);

    /**
     * 
     * <B>方法名称：获取图形化监控数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午5:16:42
     * @param sysId：系统id
     * @param fileTypeId：流程ID
     * @param workflowId：流程唯一ID
     * @param recordId：业务记录id
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return String
     */
    public String getFlowStatus(String sysId, String fileTypeId, String workflowId, String recordId, String type);

    /**
     * 
     * <B>方法名称：获取文字式流程记录</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午5:21:01
     * @param fileTypeId：流程ID
     * @param recordId：业务记录id
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return String
     */
    public String getFlowCourse(String fileTypeId, String recordId, String type);

    /**
     * 
     * <B>方法名称：获取正式意见</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午5:22:29
     * @param data：工作流参数数据包装类
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return String
     */
    public String getFormalIdea(FlowClientData data, String type);

    /**
     * 
     * <B>方法名称：获取指定节点的参与者</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午5:24:21
     * @param wfleveId：跳到的目的节点
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return JSONArray
     */
    public JSONArray getJumpWfAppointWrite(String wfleveId, String type);

    /**
     * 
     * <B>方法名称：获取退回意见</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月8日 下午2:20:27
     * @param data：工作流参数数据包装类
     * @return String
     */
    public String getBackIdea(FlowClientData data) throws IOException;

    /**
     * 
     * <B>方法名称：获取已经办理的流程节点</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月9日 下午7:54:50
     * @param workitemid：工作项id
     * @return JSONObject
     */
    public JSONObject getJumpWflevePassed(String workitemid, String type);

    /**
     * 
     * <B>方法名称：获取流转中的数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月12日 下午6:46:05
     * @param title：标婷
     * @param start：开始时间
     * @param end：结束时间
     * @param pageNum：当前页
     * @param showCount：每页条数
     * @return Page
     */
    public Page getSubflagFlow(String title, String start, String end, Integer pageNum, Integer showCount);

    /**
     *epcloud数据库自定义sql查询
     * @param sql
     * @return
     */
    public List<Map<String,Object>> getDataBySql(String sql);
    
    /**
     * TODO 取当前用户维护的流程
     * @author 李利广
     * @Date 2018年7月23日 下午8:53:28
     * @return
     */
    public List<FlowWorkflowVO> getManageFlowByUser();

    /**
     * TODO 获取当前节点配置的按钮
     * @author 李利广
     * @Date 2019年01月11日 16:22:41
     * @param workItemId
     * @return com.alibaba.fastjson.JSONObject
     */
    public JSONArray getButtons(String workItemId);

    /**
     * TODO 根据操作ID，获取操作配置数据
     * @author 李利广
     * @Date 2019年03月27日 17:25:38
     * @param wfOperateId
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject getOprateInfo(String wfOperateId);

    /**
     * TODO 获取所有的流程类型
     * @author 李利广
     * @Date 2019年04月29日 21:14:14
     * @param workFlowIds 流程ID
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> getFlowWorkFlow(String workFlowIds);

}
