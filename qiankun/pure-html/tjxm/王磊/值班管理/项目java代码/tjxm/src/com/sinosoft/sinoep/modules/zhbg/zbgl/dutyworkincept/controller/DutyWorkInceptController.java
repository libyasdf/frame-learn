package com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DutyWork;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.entity.DutyWorkIncept;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.services.DutyWorkInceptService;
import net.sf.json.JSONObject;

/**TODO 值班通知接收控制层
 * @author 李颖洁  
 * @date 2018年7月16日  下午3:53:48
 */
@Controller
@RequestMapping("zhbg/zbgl/dutyworkincept/")
public class DutyWorkInceptController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DutyWorkInceptService dutyWorkInceptService;
	
	@Autowired
	private NotifyController notifyController;
	
	/**
	 * TODO 获取本部门接收的通知数据列表
	 * @author 李颖洁  
	 * @date 2018年7月18日下午6:58:48
	 * @param pageImpl
	 * @param timeRange 有效期
	 * @param isRead 是否已读
	 * @param isSecurity 是否是安保通知
	 * @param deptId 部门ID
	 * @return PageImpl
	 */
	@LogAnnotation(value = "query",opName = "查询本部门值班通知列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getPageList(PageImpl pageImpl, String timeRange, String isRead,String isReport,String isSecurity) {
		String startTime = "";
		String endTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = dutyWorkInceptService.getPageList(pageable, pageImpl, startTime, endTime,isRead,isReport,isSecurity);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * TODO 读取本部门的通知信息和改变读取状态
	 * @author 李颖洁  
	 * @date 2018年7月19日上午10:16:05
	 * @param id 值班通知信息ID
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "查看本部门值班通知信息")
	@ResponseBody
	@RequestMapping("read")
	public JSONObject readNotice(String id,String isRead) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DutyWork dutyWork = null;
		try {
			dutyWork = dutyWorkInceptService.getById(id,isRead);
			if(dutyWork.getStartTime()==null){
				dutyWork.setYxq("");
			}else{
				dutyWork.setYxq(dutyWork.getStartTime()+"-"+dutyWork.getEndTime());
			}
			json.put("flag", "1");
			json.put("data", dutyWork);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * TODO 上报值班表
	 * @author 李颖洁  
	 * @date 2018年7月30日下午6:49:35
	 * @param id
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "修改上报值班表状态")
	@ResponseBody
	@RequestMapping(value="report")
	public JSONObject reportDutyDetail(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			dutyWorkInceptService.updateState(id);
			json.put("flag", "1");
			json.put("msg", "上报值班表成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "上报值班表失败！");
		}
		return json;
	} 
	
	/**
	 * TODO 查询消息id
	 * @author 李颖洁  
	 * @date 2018年8月3日上午9:00:07
	 * @param notityMessage
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "更改消息状态和URL")
	@ResponseBody
	@RequestMapping(value="updateMessage")
	public JSONObject updateMessage(NotityMessage notityMessage){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			JSONObject js = dutyWorkInceptService.queryMessageId(notityMessage);
			Long id = Long.parseLong(js.getJSONArray("data").getJSONObject(0).get("id").toString());
			notifyController.edit(id);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "");
		}
		return json;
	} 
	
	/**
	 * TODO 根据id获取接收表信息
	 * @author 李颖洁  
	 * @date 2018年8月3日上午9:54:56
	 * @param id
	 * @return JSONObject
	 */
    @LogAnnotation(value = "query",opName = "根据id获取一条接收表信息")
    @RequestMapping(value = "getIncept")
    @ResponseBody
    public JSONObject getDutyWorkInceptById(String id) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
        	DutyWorkIncept in = dutyWorkInceptService.getInceptById(id);
            json.put("flag","1");
            json.put("data",in);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return json;
    }
	
}
