package com.sinosoft.sinoep.modules.system.flowSend.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.flowSend.entity.SysFlowSend;

import net.sf.json.JSONObject;

/**
 * TODO 待阅业务接口
 * @author 李利广
 * @Date 2018年8月16日 下午7:45:07
 */
public interface SysFlowSendService {
	
	/**
	 * TODO 查询待阅列表（分页）
	 * @author 李利广
	 * @Date 2018年8月16日 下午8:07:11
	 * @param pageImpl
	 * @param flowSend
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	PageImpl getFlowSendList(PageImpl pageImpl,SysFlowSend flowSend,String startTime,String endTime);

	/**
	 * TODO 根据userid发送一条待阅给指定的用户
	 * 必填参数：receiveUserId（接收人ID）,receiveDeptId（接收人所在单位ID）,sendUrl（待阅URL）
	 * 			workflowName（待阅的类型名，例如【请假申请】）,title（标题）,recordId（业务ID）,
	 * 		如果有流程，还需增加参数workflowId（流程ID）
	 * @author 李利广
	 * @Date 2018年8月16日 下午7:40:41
	 * @param flowSend	必填参数：receiveUserId,receiveDeptId,workflowName,title,recordId,sendUrl
	 * @return JSONObject {"flag":"1","data":flowSend,"msg":"异常信息"}
	 */
	JSONObject sendFlowSend(SysFlowSend flowSend);
	
	/**
	 * TODO 阅毕方法；将待阅置为已阅状态，同时修改阅毕时间，如果需要填写意见，则保存意见信息
	 * @author 李利广
	 * @Date 2018年8月16日 下午7:42:58
	 * @param send
	 * @return
	 */
	JSONObject readDone(SysFlowSend send); 

}
