package com.sinosoft.sinoep.modules.system.notice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sinosoft.sinoep.common.constant.GlobalNames;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerifyUser;
import com.sinosoft.sinoep.modules.system.notice.services.SysNoticeVerifyService;
import com.sinosoft.sinoep.user.entity.DeptAllInfo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.info.authority.common.util.InfoAuthorityUtils;
import com.sinosoft.sinoep.modules.system.notice.common.SysNoticeConstants;
import com.sinosoft.sinoep.modules.system.notice.common.util.NoticeUtils;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeBack;
import com.sinosoft.sinoep.modules.system.notice.services.SysNoticeBackService;
import com.sinosoft.sinoep.modules.system.notice.services.SysNoticeService;

import net.sf.json.JSONObject;

/**
 * TODO 通知公告控制类
 * @author 李利广
 * @Date 2018年9月5日 下午4:08:31
 */
@Controller
@RequestMapping("system/notice")
public class SysNoticeController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysNoticeService noticeService;
	
	@Autowired
	private SysNoticeBackService backService;

	@Autowired
	private SysNoticeVerifyService sysNoticeVerifyService;

	@Autowired
	private NotifyController notifyController;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SysWaitNoflowService waitNoflowService;
	
	/**
	 * TODO 查询当前用户收到的通知公告列表（分页）
	 * @author 李利广
	 * @Date 2018年9月10日 上午9:56:11
	 * @param page
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询当前用户收到的通知公告列表（分页）")
	@ResponseBody
	@RequestMapping("getNoticeList")
	public PageImpl getNoticeList(PageImpl page, SysNotice notice,String timeRange){
		page.setFlag("0");
		String startTime = "";
		String endTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			page = noticeService.getNoticeList(page, notice,startTime,endTime);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			page.setMsg("获取通知通告列表异常！");
		}
		return page;
	}
	
	/**
	 * TODO 查询通知公告维护列表(部门级)（当前用户查看自己维护的数据）
	 * @author 李利广
	 * @Date 2018年9月10日 上午10:24:48
	 * @param page
	 * @param notice
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询通知公告维护列表（当前用户查看自己维护的数据）")
	@ResponseBody
	@RequestMapping("getAllNoticeList")
	public PageImpl getAllNoticeList(PageImpl page, SysNotice notice,String timeRange){
		page.setFlag("0");
		String startTime = "";
		String endTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			page = noticeService.getAllNoticeList(page, notice, startTime, endTime);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			page.setMsg("获取通知通告列表异常！");
		}
		return page;
	}

	/**
	 * TODO 查询通知公告维护列表(局级)（当前用户查看自己维护的数据）
	 * @author 李利广
	 * @Date 2018年9月10日 上午10:24:48
	 * @param page
	 * @param notice
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询通知公告维护列表（当前用户查看自己维护的数据）")
	@ResponseBody
	@RequestMapping("getAllNoticeList1")
	public PageImpl getAllNoticeList1(PageImpl page, SysNotice notice,String timeRange){
		page.setFlag("0");
		String startTime = "";
		String endTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			page = noticeService.getAllNoticeList1(page, notice, startTime, endTime);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			page.setMsg("获取通知通告列表异常！");
		}
		return page;
	}

	/**
	 * TODO 查询通知公告管理列表(局级)（当前用户查看自己维护的数据）
	 * @author 李利广
	 * @Date 2018年9月10日 上午10:24:48
	 * @param page
	 * @param notice
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询通知公告管理列表")
	@ResponseBody
	@RequestMapping("getAllNoticeList2")
	public PageImpl getAllNoticeList2(PageImpl page, SysNotice notice,String timeRange){
		page.setFlag("0");
		String startTime = "";
		String endTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			page = noticeService.getAllNoticeList1(page, notice, startTime, endTime);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			page.setMsg("获取通知通告列表异常！");
		}
		return page;
	}
	
	/**
	 * TODO 保存通知公告
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:17:37
	 * @param notice
	 * @param timeRange
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存通知公告")
	@ResponseBody
	@RequestMapping("save")
	public JSONObject saveNotice(SysNotice notice,String timeRange){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		String startTime = "";
		String endTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			notice.setStartTime(startTime);
			notice.setEndTime(endTime);
			notice = noticeService.saveNotice(notice);
			json.put("flag", "1");
			json.put("msg", "通知公告保存成功");
			json.put("data", notice);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "通知公告保存失败！");
		}
		return json;
	}
	
	/**
	 * TODO 发布通知公告
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:40:55
	 * @param noticeId
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "发布通知公告")
	@ResponseBody
	@RequestMapping("publish")
	public JSONObject publishNotice(String noticeId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(noticeId)) {
				SysNotice notice = noticeService.updateFlag(noticeId, SysNoticeConstants.FLAG[1]);
				json.put("date", notice);
				json.put("flag", "1");
				json.put("msg", "状态更新成功");
				//发送消息通知
				sendMsg(notice.getId());
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "通知公告发布失败！");
		}
		return json;
	}
	
	/**
	 * 发送消息提醒
	 * TODO 
	 * @author 李利广
	 * @Date 2018年10月22日 上午10:38:06
	 * @param noticeId
	 */
	private void sendMsg(final String noticeId){
		try {
			String orgid = UserUtil.getCruOrgId();
			//创建一个单线程的线程池
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.execute(new Thread(){
				@Override
				public void run() {
					//获取权限
					Map<String,List<Map<String, Object>>> authMap = InfoAuthorityUtils.getAuthListByContentId(noticeId);
					//获取拥有权限的userid
					JSONArray jsonArray = NoticeUtils.getUser(authMap,orgid);
					//给拥有权限的人员发送消息
					if (!jsonArray.isEmpty()) {
						List<String> userIdList = new ArrayList<>();
						for(int i = 0;i < jsonArray.size();i++){
							JSONObject json = jsonArray.getJSONObject(i);
							String userId = json.getString("userid");
							if(!userIdList.contains(userId)){
								NotityMessage msg = new NotityMessage();
								msg.setAccepterId(userId);
								msg.setContent("您收到一条通知公告信息，请及时查看！");
								msg.setMsgUrl("/modules/system/notice/noticeBackForm.html?id=" + noticeId + "&oper=VIEW");
								msg.setSenderId(ConfigConsts.SYSTEM_ID);
								msg.setStatus(GlobalNames.SYS_NOTIFY_STATUS_W);
								msg.setType("3");
								msg.setSubject("通知公告提醒");
								notifyController.save(msg);
								userIdList.add(userId);
							}
						}
					}
				}
			});
			executor.shutdown();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * TODO 撤销通知公告
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:44:17
	 * @param noticeId
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "撤销通知公告")
	@ResponseBody
	@RequestMapping("cancel")
	public JSONObject cancelNotice(String noticeId){
		JSONObject json = new JSONObject();
		try {
			SysNotice notice = noticeService.updateFlag(noticeId, SysNoticeConstants.FLAG[2]);
			json.put("date", notice);
			json.put("flag", "1");
			json.put("msg", "状态更新成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "撤销失败！");
		}
		return json;
	}
	
	/**
	 * TODO 删除通知公告
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:45:51
	 * @param noticeId
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除通知公告")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delNotice(String noticeId){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			Boolean boo = noticeService.delNotice(noticeId);
			if (boo) {
				res.put("flag", "1");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "删除失败！");
		}
		return res;
	}
	
	/**
	 * TODO 获取一条数据
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:45:51
	 * @param noticeId
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询一条通知公告信息")
	@ResponseBody
	@RequestMapping("view")
	public JSONObject getView(String noticeId){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			SysNotice notice = noticeService.getView(noticeId);
			res.put("flag", "1");
			res.put("data", notice);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "读取通知公告数据失败！");
		}
		return res;
	}
	
	/**
	 * TODO 通知公告反馈
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:16:51
	 * @param back
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存通知公告反馈信息")
	@ResponseBody
	@RequestMapping("saveBack")
	public JSONObject saveBack(SysNoticeBack back){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			back = backService.saveBack(back);
			res.put("flag", "1");
			res.put("data", back);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "反馈信息保存失败");
		}
		return res;
	}
	
	/**
	 * TODO 获取通知公告当前用户的反馈信息
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:21:53
	 * @param noticeId
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "获取通知公告当前用户的反馈信息")
	@ResponseBody
	@RequestMapping("getBack")
	public JSONObject getBack(String noticeId){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			SysNoticeBack back = backService.getBack(noticeId);
			res.put("flag", "1");
			res.put("data", back);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "读取反馈信息失败");
		}
		return res;
	}
	
	/**
	 * TODO 获取通知公告所有的反馈信息
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:21:53
	 * @param noticeId
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "获取通知公告所有的反馈信息")
	@ResponseBody
	@RequestMapping("getAllBack")
	public PageImpl getAllBack(PageImpl page,String noticeId){
		page.setFlag("0");
		try {
			page = backService.getAllBack(page,noticeId);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return page;
	}

	/**
	 * TODO 统计未反馈处室
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:21:53
	 * @param noticeId
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "统计未反馈处室")
	@ResponseBody
	@RequestMapping("getAllNoBackChu")
	public PageImpl getAllNoBackChu(PageImpl page,String noticeId){
		page.setFlag("0");
		try {
			page = backService.getAllNoBackChu(page,noticeId);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return page;
	}

	/**
	 * TODO 统计未反馈人员
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:21:53
	 * @param noticeId
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "统计未反馈人员")
	@ResponseBody
	@RequestMapping("getAllNoBackUser")
	public PageImpl getAllNoBackUser(PageImpl page,String noticeId){
		page.setFlag("0");
		try {
			page = backService.getAllNoBackUser(page,noticeId);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return page;
	}
	
	/**
	 * TODO 置顶/取消置顶
	 * @author 李利广
	 * @Date 2018年9月10日 上午11:26:46
	 * @param ids
	 * @param upDown true:置顶；false:取消置顶
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "置顶/取消置顶")
	@ResponseBody
	@RequestMapping("setTop")
	public JSONObject setTop(String ids,Boolean upDown){
		JSONObject res = new JSONObject();
		res.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(ids)){
				Boolean setTop = noticeService.setTop(ids, upDown);
				if (setTop) {
					res.put("flag", "1");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("msg", "操作失败！");
		}
		return res;
	}

	/**
	 * 置顶排序
	 * @param ids
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "置顶排序")
	@ResponseBody
	@RequestMapping("sort")
	public JSONObject sort(String[] ids){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try{
			boolean res = noticeService.sort(ids);
			if (res){
				json.put("flag","1");
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
			json.put("msg", "操作失败！");
		}
		return json;
	}
	
	/**
	 * 查询个人门户最新发布消息
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月29日 下午7:18:26
	 * @param page
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询通知公告最新发布消息")
	@ResponseBody
	@RequestMapping("getInfoNoticeList")
	public PageImpl getInfoNoticeList(PageImpl page){
		page.setFlag("0");
		try {
			page = noticeService.getInfoNoticeList(page);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return page;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 查询公文发布最新发布消息
	 * @Date 2019/10/10 9:24
	 * @Param [page]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	@LogAnnotation(value = "query",opName = "查询公文发布最新发布消息")
	@ResponseBody
	@RequestMapping("getInfoNoticeDocumentsList")
	public PageImpl getInfoNoticeDocumentsList(PageImpl page){
		page.setFlag("0");
		try {
			page = noticeService.getInfoNoticeDocumentsList(page);
			page.setFlag("1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return page;
	}

	@LogAnnotation(value = "query",opName = "获取排序列表，根据columnId")
	@RequestMapping(value = "getZhiDingList")
	@ResponseBody
	public PageImpl getZhiDingList(PageImpl pageImpl){
		try{
				List<SysNotice> list = noticeService.getZhiDingList();
				pageImpl.setFlag("1");
				pageImpl.getData().setRows(list);
				pageImpl.getData().setTotal(list.size());
		}catch (Exception e){
			e.printStackTrace();
		}
		return pageImpl;
	}

	/*
  * @param entity
  * @return
  */
	@LogAnnotation(value = "query",opName = "查询是否有发送，发布，审批通过，审批不通过和是否有发布范围的权限")
	@RequestMapping("queryNotice")
	@ResponseBody
	public JSONObject queryNotice(){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try{
			json = noticeService.queryNotice();
			json.put("flag","1");
		}catch (Exception e){
			e.printStackTrace();
			json.put("flag","-1");
		}
		return json;
	}

	@LogAnnotation(value = "update",opName = "通知通告发送")
	@RequestMapping("sendFlow")
	@ResponseBody
	public JSONObject sendFlow(String noticeId){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try{
			if (StringUtils.isNotBlank(noticeId)) {
				SysNotice notice = noticeService.updateFlag(noticeId, SysNoticeConstants.FLAG[3]);
				String userNoticeId = UserUtil.getCruUserId();
				SysNoticeVerifyUser sysNoticeVerifyUser = sysNoticeVerifyService.findByFbUserIdAndVisible(userNoticeId,"1");
				SysWaitNoflow waitNoflowEntity  = new SysWaitNoflow();
				waitNoflowEntity.setReceiveUserId(sysNoticeVerifyUser.getShUserId());
				waitNoflowEntity.setReceiveUserName(sysNoticeVerifyUser.getShUserName());
				DeptAllInfo deptAllInfo = userInfoService.getDeptInfoByDeptId(sysNoticeVerifyUser.getShUserDeptId());
				String deptname = deptAllInfo.getDeptname();
				waitNoflowEntity.setReceiveDeptId(sysNoticeVerifyUser.getShUserDeptId());
				waitNoflowEntity.setReceiveDeptName(deptname);
				waitNoflowEntity.setRolesNo("");
				waitNoflowEntity.setTableName("sys_notice");
				waitNoflowEntity.setTableId("id");
				waitNoflowEntity.setSourceId(noticeId);
				waitNoflowEntity.setOpName("通知公告");
				waitNoflowEntity.setDaibanUrl("/modules/system/notice/noticeEditForm.html?id="+notice.getId()+"&oper=EDIT");
				waitNoflowEntity.setTitle(notice.getTitle());
				waitNoflowEntity = waitNoflowService.saveWaitNoflow(waitNoflowEntity,"通知公告",notice.getTitle(),"/modules/system/notice/noticeEditForm.html?id="+notice.getId()+"&oper=EDIT");
				json.put("flag","1");
				json.put("data",notice);
				json.put("waitNoflowEntity",waitNoflowEntity);
			}
		}catch (Exception e){
			e.printStackTrace();
			json.put("flag","-1");
		}
		return json;
	}

	@LogAnnotation(value = "update",opName = "审批不通过")
	@RequestMapping("noPass")
	@ResponseBody
	public JSONObject noPass(String noticeId,String workItemId){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try{
			if (StringUtils.isNotBlank(noticeId)) {
				SysNotice notice = noticeService.updateFlag(noticeId, SysNoticeConstants.FLAG[4]);
				SysWaitNoflow waitNoflowEntity  = new SysWaitNoflow();
				if(StringUtils.isNotBlank(workItemId)){
					SysWaitNoflow oldFlowEntity = waitNoflowService.getSysWaitNoflow(workItemId);
					if(oldFlowEntity != null){
						waitNoflowEntity.setReceiveUserId(oldFlowEntity.getDraftUserId());
						waitNoflowEntity.setReceiveUserName(oldFlowEntity.getDraftUserName());
						waitNoflowEntity.setReceiveDeptName(oldFlowEntity.getDraftDeptName());
						waitNoflowEntity.setReceiveDeptId(oldFlowEntity.getDraftDeptId());
					}
				}
				waitNoflowEntity.setRolesNo("");
				waitNoflowEntity.setTableName("SYS_NOTICE");
				waitNoflowEntity.setTableId("id");
				waitNoflowEntity.setSourceId(notice.getId());
				waitNoflowEntity.setOpName("通知公告");
				waitNoflowEntity.setDaibanUrl("/modules/system/notice/noticeEditForm.html?id="+notice.getId()+"&oper=EDIT");
				waitNoflowEntity.setTitle(notice.getTitle());
				waitNoflowEntity = waitNoflowService.saveWaitNoflow(waitNoflowEntity,"通知公告","entity.getTitle()","/modules/system/notice/noticeEditForm.html?id="+notice.getId()+"&oper=EDIT");
				json.put("flag","1");
				json.put("data",notice);
				json.put("waitNoflowEntity",waitNoflowEntity);
			}
		}catch (Exception e){
			e.printStackTrace();
			json.put("flag","-1");
		}
		return json;
	}

	@LogAnnotation(value = "query",opName = "获取通知公告审核列表")
	@RequestMapping("getSpList")
	@ResponseBody
	public PageImpl getSpList(PageImpl pageImpl,String subflag,String title){
		Pageable pageable1 = new PageRequest(pageImpl.getPageNumber()-1,pageImpl.getPageSize());
		return noticeService.getSpList(pageable1,pageImpl,subflag,title);
	}

	/**
	 * TODO 更新流程状态
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:40:55
	 * @param
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "更新流程状态")
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id,String subflag){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try{
			SysNotice sysNotice = noticeService.updateFlag(id,subflag);
			json.put("flag",subflag);
			if(sysNotice.getSubflag().equals("5")){
				//发送消息通知
				sendMsg(sysNotice.getId());
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return json;
	}
}
