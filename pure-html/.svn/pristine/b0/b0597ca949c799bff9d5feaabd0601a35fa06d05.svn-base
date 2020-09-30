package com.sinosoft.sinoep.modules.system.flowSend.services;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.flowSend.common.FlowSendConstants;
import com.sinosoft.sinoep.modules.system.flowSend.dao.SysFlowSendDao;
import com.sinosoft.sinoep.modules.system.flowSend.entity.SysFlowSend;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO 待阅业务实现类
 * @author 李利广
 * @Date 2018年8月16日 下午8:38:16
 */
@Service
public class SysFlowSendServiceImpl implements SysFlowSendService {
	
	@Autowired
	private SysFlowSendDao sysFlowSendDao;

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * TODO 查询待阅列表（分页）
	 * @author 李利广
	 * @Date 2018年8月16日 下午8:07:11
	 * @param pageImpl
	 * @param pageImpl
	 * @param flowSend
	 * @param startTime	yyyy-MM-dd HH:mm:ss
	 * @param endTime	yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	@Override
	public PageImpl getFlowSendList(PageImpl pageImpl, SysFlowSend flowSend, String startTime, String endTime) {
		PageImpl page = new PageImpl();
		page.setFlag("0");
		try {
			Integer pageNumber = pageImpl.getPageNumber();
			Integer pageSize = pageImpl.getPageSize();
			if (pageNumber == null) {
				pageNumber = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			Pageable pageable = new PageRequest(pageNumber-1, pageSize);
			List listzwf=new ArrayList();
			StringBuilder sBuilder = new StringBuilder("from SysFlowSend t where t.receiveUserId = '"+UserUtil.getCruUserId()+"'");
//			sBuilder.append(" and t.receiveDeptId = '"+UserUtil.getCruDeptId()+"'");
			if (StringUtils.isNotBlank(startTime)) {
				sBuilder.append(" and t.sendTime >= ? ");
				listzwf.add(startTime);
			}
			if (StringUtils.isNotBlank(endTime)) {
				sBuilder.append(" and t.sendTime >= ? ");
				listzwf.add(endTime);
			}
			if (StringUtils.isNotBlank(flowSend.getWorkflowId())) {
				sBuilder.append(" and t.workflowId =? ");
				listzwf.add(flowSend.getWorkflowId());
			}
			if (StringUtils.isNotBlank(flowSend.getStatus())) {
				sBuilder.append(" and t.status =?");
				listzwf.add(flowSend.getStatus());
			}
			if (StringUtils.isNotBlank(flowSend.getTitle())) {
				sBuilder.append(" and t.title like ?");
				listzwf.add("%"+flowSend.getTitle().trim()+"%");
			}
			if (StringUtils.isNotBlank(flowSend.getSysId())) {
				sBuilder.append(" and t.sysId = ? ");
				listzwf.add(flowSend.getSysId());
			}
			sBuilder.append(" order by t.sendTime desc");
			Page<SysFlowSend> query = sysFlowSendDao.query(sBuilder.toString(), pageable,listzwf.toArray());
			page.setFlag("1");
			page.getData().setRows(query.getContent());
			page.getData().setTotal((int)query.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * TODO 根据userid发送一条待阅给指定的用户
	 * 必填参数：receiveUserId（接收人ID）,sendUrl（待阅URL）
	 * 			workflowName（待阅的类型名，例如【请假申请】）,title（标题）,recordId（业务ID）,
	 * 		如果有流程，还需增加参数workflowId（流程ID）
	 * @author 李利广
	 * @Date 2018年8月16日 下午7:40:41
	 * @param flowSend	必填参数：receiveUserId,workflowName,title,recordId,sendUrl
	 * @return JSONObject {"flag":"1","data":flowSend,"msg":"异常信息"}
	 */
	@Override
	public JSONObject sendFlowSend(SysFlowSend flowSend) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//检查必填参数是否为空
			if(checkParam(flowSend)){
				Map<String, SysFlowUserVo> receiveUser = UserUtil.getUserVo(flowSend.getReceiveUserId());
				if (!receiveUser.isEmpty()) {
					SysFlowUserVo user = receiveUser.get(flowSend.getReceiveUserId());
					flowSend.setOrgId(UserUtil.getCruOrgId());
					flowSend.setSysId(ConfigConsts.SYSTEM_ID);
					flowSend.setReceiveUserName(user.getUserNameFull());
					/*Dept dept = userInfoService.getDeptById(user.getUserDeptId());
					flowSend.setReceiveDeptName(dept.getDeptname());*/
					/*flowSend.setReceiveRoleNo(user.getUserRoleNO());*/
					flowSend.setSendUserId(UserUtil.getCruUserId());
					flowSend.setSendUserName(UserUtil.getCruUserName());
					flowSend.setSendDeptId(UserUtil.getCruDeptId());
					flowSend.setSendDeptName(UserUtil.getCruDeptName());
					flowSend.setSendChuShiId(UserUtil.getCruChushiId());
					flowSend.setSendChuShiName(UserUtil.getCruChushiName());
					flowSend.setSendJuId(UserUtil.getCruJuId());
					flowSend.setSendJuName(UserUtil.getCruJuName());
					flowSend.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					flowSend.setStatus(FlowSendConstants.STATUS[0]);
					if (StringUtils.isBlank(flowSend.getIsIdea())) {
						flowSend.setIsIdea(FlowSendConstants.IS_IDEA[0]);
					}
					flowSend = sysFlowSendDao.save(flowSend);
					json.put("flag", "1");
					json.put("data", flowSend);
				}else{
					json.put("msg", "指定接收人不存在");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "发送待阅异常");
		}
		return json;
	}

	/**
	 * TODO 检查必填参数是否为空
	 * @author 李利广
	 * @Date 2019年01月25日 09:32:05
	 * @param flowSend 必填参数：receiveUserId,workflowName,title,recordId,sendUrl
	 * @return boolean
	 */
	private boolean checkParam(SysFlowSend flowSend){
		if (flowSend != null){
			if(StringUtils.isNotBlank(flowSend.getReceiveUserId()) && StringUtils.isNotBlank(flowSend.getWorkflowName())
					&& StringUtils.isNotBlank(flowSend.getTitle()) && StringUtils.isNotBlank(flowSend.getRecordId())
					&& StringUtils.isNotBlank(flowSend.getSendUrl())){
				return true;
			}
		}
		return false;
	}

	/**
	 * TODO 阅毕方法；将待阅置为已阅状态，同时修改阅毕时间，如果需要填写意见，则保存意见信息
	 * @author 李利广
	 * @Date 2018年8月16日 下午7:42:58
	 * @param send
	 * @return
	 */
	@Override
	public JSONObject readDone(SysFlowSend send) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			String id = send.getId();
			if (StringUtils.isNotBlank(id)) {
				SysFlowSend oldSend = sysFlowSendDao.findOne(id);
				oldSend.setStatus(FlowSendConstants.STATUS[1]);
				oldSend.setReadTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
				if (FlowSendConstants.IS_IDEA[1].equals(oldSend.getIsIdea())) {
					oldSend.setIdea(send.getIdea());
				}
				oldSend = sysFlowSendDao.save(oldSend);
				json.put("flag", "1");
				json.put("data", oldSend);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "阅毕异常");
		}
		return json;
	}

}
