package com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.controller;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.repeatformvalidator.SameUrlData;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.zbgl.constant.ZBGLCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.services.DutyTableService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DutyWork;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.services.DutyWorkService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.entity.DutyWorkIncept;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.services.DutyWorkInceptService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * TODO 值班工作通知控制层
 * @author 李颖洁  
 * @date 2018年7月10日  下午5:36:23
 */
@Controller
@RequestMapping("zhbg/zbgl/dutywork")
public class DutyWorkController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DutyWorkService dutyWorkService;
	
	@Autowired
	private DutyWorkInceptService dutyWorkInceptService;
	
	@Autowired
	private SysWaitNoflowService sysWatiNoflowService;
	
	@Autowired
	private DutyTableService dutyTableService;
	
	/**
	 * TODO 新增值班通知保存
	 * @author 李颖洁  
	 * @date 2018年7月17日下午8:50:02
	 * @param dutyWork
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "保存值班通知")
	@SameUrlData
	@ResponseBody
	@RequestMapping(value="save",method=RequestMethod.POST)
	public JSONObject saveNotice(DutyWork dutyWork,String deptIds,String deptNames){
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("flag", "0");
		try {
			dutyWork = dutyWorkService.saveNotice(dutyWork);
			List<DutyWorkIncept> inceptList = dutyWorkInceptService.sendNotice(dutyWork, deptIds, deptNames);
			if(dutyWork.getId()!=null){
				json.put("flag", "1");
				data.put("data", dutyWork);
				data.put("rows", inceptList);
				json.put("data", data);
				json.put("msg", "保存数据成功！");
			}else{
				json.put("msg", "保存数据失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "保存数据失败！");
		}
		return json;
	} 
	
	/**
	 * TODO 新增值班通知发布
	 * @author 李颖洁  
	 * @date 2018年7月17日下午8:50:57
	 * @param id
	 * @param deptId
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "修改值班通知发布状态")
	@SameUrlData
	@ResponseBody
	@RequestMapping(value="send")
	public JSONObject sendNotice(String id,String deptId){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if(id!=null){
				int row = dutyWorkService.updateState(id);
				dutyWorkInceptService.updateVisible(id,deptId);
				json.put("flag", "1");
				json.put("data", row);
				json.put("msg", "发布通知成功！");
			}else{
				json.put("msg", "发布通知失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "发布通知失败！");
		}
		return json;
	} 
	
	/**
	 * TODO 修改值班通知数据，数据回显
	 * @author 李颖洁  
	 * @date 2018年7月17日下午8:51:32
	 * @param id 通知数据ID
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "获取值班通知信息")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject editNotice(String id) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DutyWork dutyWork = null;
		try {
			dutyWork = dutyWorkService.getById(id);
			if(dutyWork.getStartTime()==null){
				dutyWork.setYxq("");
			}else{
				dutyWork.setYxq(dutyWork.getStartTime()+" - "+dutyWork.getEndTime());
			}
			/*if("readOnly".equals(operation)){
				if(StringUtils.isNotBlank(dutyWork.getNoticeText()) && dutyWork.getNoticeText().contains("\n")){
					dutyWork.setNoticeText(dutyWork.getNoticeText().replaceAll("\n","<br/>"));
				}
				if(StringUtils.isNotBlank(dutyWork.getRemark()) && dutyWork.getRemark().contains("\n")){
					dutyWork.setRemark(dutyWork.getRemark().replaceAll("\n","<br/>"));
				}
			}*/
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
	 * TODO 上报表值班通知数据回显和日期列表回显
	 * @author 李颖洁  
	 * @date 2018年7月17日下午8:51:32
	 * @param id 通知数据ID
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "查看上报表")
	@ResponseBody
	@RequestMapping("queryNotice")
	public JSONObject queryNotice(String id,String inceptId) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		JSONArray array1 = new JSONArray();
		json.put("flag", "0");
		DutyWork dutyWork = null;
		try {
			dutyWork = dutyWorkService.getById(id);
			if(StringUtils.isNotBlank(dutyWork.getStartTime())){
				dutyWork.setYxq(dutyWork.getStartTime()+" - "+dutyWork.getEndTime());
				List<JSONObject>list = dutyWorkService.getDateList(dutyWork.getStartTime(),dutyWork.getEndTime());
				array1 = JSONArray.fromObject(list, jsonConfig);
			}
			DutyTable dut = new DutyTable();
			dut.setZbNoticeId(inceptId);
			List<DutyTable> ld = dutyTableService.getList("",dut);
			DutyWorkIncept dutyWorkIncept = dutyWorkInceptService.getInceptById(inceptId);
			JSONArray array2 = JSONArray.fromObject(ld, jsonConfig);
			json.put("flag", "1");
			data.put("data1",dutyWork);
			data.put("rows1", array1);
			data.put("rows2", array2);
			data.put("data2", dutyWorkIncept);
			json.put("data", data);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * TODO 删除通知数据
	 * @author 李颖洁  
	 * @date 2018年7月17日下午8:52:14
	 * @param id 通知数据的ID
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "删除数据")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject deletePlan(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = dutyWorkService.delete(id);
			//dutyWorkInceptService.deleteBatch(id);
			if (result != 0) {
				json.put("flag", "1");
				json.put("msg", "删除数据成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * TODO 获取通知数据列表
	 * @author 李颖洁  
	 * @date 2018年7月17日下午8:52:58
	 * @param pageImpl
	 * @param timeRange 有效期
	 * @param state 是否发布
	 * @param isSecurity 是否是安保通知
	 * @return PageImpl
	 */
	@LogAnnotation(value = "query",opName = "查询通知数据列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String state,String isSecurity) {
		String startTime = "";
		String endTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = dutyWorkService.getPageList(pageable, pageImpl, startTime, endTime,state,isSecurity);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * TODO 获取部门的上报情况列表
	 * @author 李颖洁  
	 * @date 2018年7月26日下午9:52:58
	 * @param zbNoticeId
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "查询部门上报情况")
	@RequestMapping(value = "getInceptList", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getList(PageImpl pageImpl,String zbNoticeId, String state){
        JSONObject json = new JSONObject();
        try {
        	List<DutyWorkIncept> list = dutyWorkInceptService.getList(pageImpl,zbNoticeId, state);
            json.put("flag", "1");
            JSONObject data = new JSONObject();
            data.put("total", list.size());
            JSONArray array = new JSONArray();
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
            jsonConfig.setExcludes(new String[] { "visible", "creTime","creUserId", "creUserName","creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
            array = JSONArray.fromObject(list, jsonConfig);
            data.put("rows", array);
            json.put("data", data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.put("flag", 0);
        }
        return json;
    }
	
	/**
	 * TODO 获取未发布前的部门
	 * @author 李颖洁  
	 * @date 2018年7月26日下午9:52:58
	 * @param zbNoticeId
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "查询未发布前保存部门列表")
	@RequestMapping(value = "getDeptList", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getDeptList(String zbNoticeId){
		JSONObject json = new JSONObject();
		try {
			List<DutyWorkIncept> list = dutyWorkInceptService.getDeptList(zbNoticeId);
			json.put("flag", "1");
			JSONObject data = new JSONObject();
			data.put("total", list.size());
			JSONArray array = new JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "visible", "creTime","creUserId", "creUserName","creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
			array = JSONArray.fromObject(list, jsonConfig);
			data.put("rows", array);
			json.put("data", data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * TODO 插入不走流程待办
	 * @author 李颖洁  
	 * @date 2018年8月1日下午3:57:58
	 * @param id 通知表id
	 * @param deptName 接收处室名称
	 * @param deptId 接收处室id
	 * @param userId 接收人id
	 * @param inceptId 接收表id
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "发送安保值班通知待办事项和消息")
	@RequestMapping("sendWaitNoflow")
	@ResponseBody
	public JSONObject sendWaitNoflow(String id,String deptName,String deptId,String userId,String inceptId,String subject,String content,String messageURL,String daibanURL,String opName){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		if(StringUtils.isNotBlank(UserUtil.getCruDeptId())){
			SysWaitNoflow waitNoflw = new SysWaitNoflow();
			//李颖洁20190329改动，原来传递的deptid 是接收人的处室id，导致接收不到待办，改为按照角色发送待办
			//waitNoflw.setReceiveDeptName(deptName);//接收人部门
			waitNoflw.setReceiveDeptId(deptId);//接收人部门id 必填
			//waitNoflw.setReceiveUserId(userId);//接受人id
			//waitNoflw.setReceiveUserName("");//接受人name
			waitNoflw.setRolesNo(ZBGLCommonConstants.ROLE_NO[0]);//接受业务角色
			waitNoflw.setOpName(opName);//操作类型
			waitNoflw.setDaibanUrl(daibanURL);//待办url  必填
			waitNoflw.setTitle(subject);//待办显示标题
			waitNoflw.setTableId("");//业务表id
			waitNoflw.setTableName("zbgl_duty_notice");//业务表名
			waitNoflw.setSourceId(inceptId);//业务id
			waitNoflw.setAttr1(id);//预留字段1
			waitNoflw.setAttr2("");//预留字段2
			waitNoflw.setAttr3("");//预留字段3
			sysWatiNoflowService.saveWaitNoflow(waitNoflw,true,subject,content,messageURL);
			SysWaitNoflow waitNoflw1 = new SysWaitNoflow();
			waitNoflw1.setReceiveDeptName(deptName);//接收人部门
			//李颖洁20190329改动，原来传递的deptid 是接收人的处室id，导致接收不到待办，改为按照角色发送待办
			waitNoflw1.setReceiveDeptId(deptId);//接收人部门id 必填
			//waitNoflw.setReceiveUserId(userId);//接受人id
			//waitNoflw.setReceiveUserName("");//接受人name
			waitNoflw1.setRolesNo(ZBGLCommonConstants.ROLE_NO[1]);//接受业务角色
			waitNoflw1.setOpName(opName);//操作类型
			waitNoflw1.setDaibanUrl(daibanURL);//待办url  必填
			waitNoflw1.setTitle(subject);//待办显示标题
			waitNoflw1.setTableId("");//业务表id
			waitNoflw1.setTableName("zbgl_duty_notice");//业务表名
			waitNoflw1.setSourceId(inceptId);//业务id
			waitNoflw1.setAttr1(id);//预留字段1
			waitNoflw1.setAttr2("");//预留字段2
			waitNoflw1.setAttr3("");//预留字段3
			sysWatiNoflowService.saveWaitNoflow(waitNoflw1,true,subject,content,messageURL);
			json.put("flag","1");
			
		}
		return json;
	}
	
	/**
	 * TODO 根据部门和资源删除多条记录，上报后删除待办
	 * @author 李颖洁  
	 * @date 2018年8月1日下午2:26:22
	 * @param inceptId 接收表id
	 * @param receiveDeptId 接收部门的id
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "删除不走流程待办")
    @RequestMapping("deleteWaitNoflow")
    @ResponseBody
    public JSONObject deleteWaitNoflow(String inceptId,String receiveDeptId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
      //李颖洁20190329改动，按照角色发送待办，改为按照角色删除待办
        int num =sysWatiNoflowService.deleteWaitNoflow("",inceptId,receiveDeptId, "", ZBGLCommonConstants.ROLE_NO[0]);
        int num1 =sysWatiNoflowService.deleteWaitNoflow("",inceptId,receiveDeptId, "", ZBGLCommonConstants.ROLE_NO[1]);
        if (num !=0 && num1!=0){
            json.put("flag","1");
        }
        return json;
    }
	
	/**
	 * TODO 判断本部门是否有上报员
	 * @author 李颖洁  
	 * @date 2018年7月24日下午2:26:30
	 * @param deptIds
	 */
	@LogAnnotation(value = "query",opName = "查询部门是否有上报员")
	@ResponseBody
	@RequestMapping("hasReportUser")
	public JSONObject hasReportUserByDeptId(String deptIds,String deptNames) {
		JSONObject json = new JSONObject();
		try {
			json = dutyWorkService.hasReportUserByDeptId(deptIds,deptNames);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("flag", "0");
		}
		return json;
	}
	
	
}
