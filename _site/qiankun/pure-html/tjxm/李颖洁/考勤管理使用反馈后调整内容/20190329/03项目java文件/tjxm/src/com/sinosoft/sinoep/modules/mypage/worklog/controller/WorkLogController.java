package com.sinosoft.sinoep.modules.mypage.worklog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.worklog.entity.WorkLog;
import com.sinosoft.sinoep.modules.mypage.worklog.services.WorkLogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 工作日志的controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月3日 下午6:21:06
 */
@Controller
@RequestMapping("/mypage/worklog")
public class WorkLogController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WorkLogService service;
	
	/**
	 * 获取工作日志的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月3日 下午6:37:03
	 * @param pageImpl
	 * @param creUserName
	 * @param applicantUnitName
	 * @param goOutTime
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "个人日志列表")
	@ResponseBody
	@RequestMapping("getlistBootHql")
	public PageImpl getList(PageImpl pageImpl,String dateLog,String type,String flag,String logType,String dateLogYear,String dateLogMonth,String dateLogWeek,String timeCondition,String content){
		String startDate = "";
		String endDate = "";
		try {
			if("3".equals(timeCondition)){
				//自定义
				if (StringUtils.isNotBlank(dateLog)) {
					startDate = dateLog.substring(0, (dateLog.length() + 1) / 2 - 1).trim();
					endDate = dateLog.substring((dateLog.length() + 1) / 2, dateLog.length()).trim();
				}
			}else if("2".equals(timeCondition)){
				//按周
				if (StringUtils.isNotBlank(dateLogWeek)) {
					startDate = dateLogWeek.substring(0, (dateLogWeek.length() + 1) / 2 - 1).trim();
					endDate = dateLogWeek.substring((dateLogWeek.length() + 1) / 2, dateLogWeek.length()).trim();
				}
			}
			
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = service.getPageList(pageable,pageImpl,startDate,endDate,type,flag,logType,dateLogYear,dateLogMonth,dateLogWeek,timeCondition,content);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 获取领导查询的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月3日 下午6:37:03
	 * @param pageImpl
	 * @param creUserName
	 * @param applicantUnitName
	 * @param goOutTime
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "领导查询列表")
	@ResponseBody
	@RequestMapping("getLeaderShowlist")
	public PageImpl getLeaderShowlist(PageImpl pageImpl,String dateLog,String type,String persionId,String logType,String dateLogYear,String dateLogMonth,String dateLogWeek,String timeCondition,String content){
		String startDate = "";
		String endDate = "";
		try {
			if("3".equals(timeCondition)){
				//自定义
				if (StringUtils.isNotBlank(dateLog)) {
					startDate = dateLog.substring(0, (dateLog.length() + 1) / 2 - 1).trim();
					endDate = dateLog.substring((dateLog.length() + 1) / 2, dateLog.length()).trim();
				}
			}else if("2".equals(timeCondition)){
				//按周
				if (StringUtils.isNotBlank(dateLogWeek)) {
					startDate = dateLogWeek.substring(0, (dateLogWeek.length() + 1) / 2 - 1).trim();
					endDate = dateLogWeek.substring((dateLogWeek.length() + 1) / 2, dateLogWeek.length()).trim();
				}
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = service.getLeaderShowList(pageable,pageImpl,startDate,endDate,type,persionId,logType,dateLogYear,dateLogMonth,dateLogWeek,timeCondition,content);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 获取首页上工作日志的前6条数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月8日 下午4:59:17
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getPageList")
	public List getPageList(){
		List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
		list=service.getPageList();
		return list;
	}
	
	@ResponseBody
	@RequestMapping("getPageList1")
	public JSONObject getPageList1(){
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
		try {
			//list=service.getPageList();
			JSONObject data = new JSONObject();
			data.put("total",list.size());
			JSONArray array = new JSONArray();
			array = JSONArray.fromObject(list);
			data.put("rows", array);
			json.put("data",data);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	/**
	 * TODO 保存
	 * @author 马秋霞
	 * @Date 2018年5月4日 上午9:42:31
	 * @param info
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存表单")
	@ResponseBody
	@RequestMapping("saveForm")
	public WorkLog saveForm(WorkLog info,String flag){
		try {
			info = service.saveForm(info,flag);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return info;
	}
		
	/**
	 * TODO 打开只读、修改页面时，查询数据进行渲染
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType =OpType.UPDATE,opName = "修改页面")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		WorkLog workLog = null;
		try {
			workLog = service.getById(id);
			json.put("flag", "1");
			json.put("data", workLog);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 根据日期查询是否有该条数据
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findByDateLog")
	public String findById( String dateLog,String id){
		String flag = "0";
		List list = null;
		try {
			list = service.findByDateLog(dateLog,id);
			if(list!=null && list.size()>0){
				flag="1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return flag;
	}
	
	
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除数据")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = service.delete(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 打开只读、修改页面时，查询数据进行渲染
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "修改状态")
	@ResponseBody
	@RequestMapping("shangBao")
	public JSONObject shangBao( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = service.shangBao(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 获取当前周的开始到结束时间
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月2日 下午4:32:31
	 * @param date
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getWeekDate")
	public JSONObject getWeekDate(String date){
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj= service.getWeekDate(date);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return jsonObj;
	}

	
}
