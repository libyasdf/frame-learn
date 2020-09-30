package com.sinosoft.sinoep.modules.mypage.workplan.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.sinosoft.sinoep.modules.mypage.workplan.entity.DayStatus;
import com.sinosoft.sinoep.modules.mypage.workplan.entity.WorkPlan;
import com.sinosoft.sinoep.modules.mypage.workplan.service.WorkPlanService;

import net.sf.json.JSONObject;

/**
 * 节假日设置controller层
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月10日 下午5:49:37
 */
@Controller
@RequestMapping("/mypage/workplan")
public class WorkPlanController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WorkPlanService service;
	
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
	public PageImpl getList(PageImpl pageImpl,String dateLog,String isFinish,String flag,String planType,String dateLogYear,String dateLogMonth,String dateLogWeek,String timeCondition,String content){
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
			pageImpl = service.getPageList(pageable,pageImpl,startDate,endDate,isFinish,flag,planType,dateLogYear,dateLogMonth,dateLogWeek,timeCondition,content);
			
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
	 * 获取日历数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月4日 下午5:02:09
	 * @param request
	 * @param year
	 * @param month
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlist1")
	public DayStatus[][] getList1(HttpServletRequest request, Integer year, Integer month) {
		DayStatus[][] dayStatus = service.getList(request,year,month);
		return dayStatus;
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
	public WorkPlan saveForm(WorkPlan info,String flag,String datePlan1){
		try {
			if(StringUtils.isNotBlank(info.getIsFinish()) && "1".equals(info.getIsFinish())){
				if(StringUtils.isNotBlank(datePlan1)){
					info.setDatePlan(datePlan1);
				}
			}
			info = service.saveForm(info,flag);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return info;
	}
	/**
	 * TODO设置计划已完成
	 * @author 马秋霞
	 * @Date 2018年5月4日 上午9:42:31
	 * @param info
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存表单")
	@ResponseBody
	@RequestMapping("upFinish")
	public JSONObject upFinish(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			List<WorkPlan> result = service.upFinish(id);
			if (result.size()>0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	/**
	 * 把某些日期设置为工作日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:25:24
	 * @param dates
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject editPlan(String datePlan,String id){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", "1");
		try {
			WorkPlan plan = service.getByDate(datePlan,id);
			jsonObj.put("flag", "1");
			jsonObj.put("data", plan);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jsonObj.put("flag", "0");
		}
		return jsonObj;
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
	 * 把某些日期设置为工作日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:25:24
	 * @param dates
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setWorkday")
	public JSONObject setWorkday(String dates){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", "1");
		try {
			service.setWorkDay(dates);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jsonObj.put("flag", "0");
		}
		return jsonObj;
	}
	
	/**
	 * 根据日期查询是否有该条数据
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@ResponseBody
	@RequestMapping("findByDatePlan")
	public WorkPlan findById( WorkPlan info){
		String datePlan = info.getDatePlan();
		String id = info.getId();
		String flag = "0";
		List<Map<String,Object>> list = null;
		WorkPlan workPlan = new WorkPlan(); 
		try {
			list = service.findByDatePlan(datePlan,id);
			if(list!=null && list.size()>0){
				
				if(list.size()>0){
					Map<String, Object> map = list.get(0);
					workPlan.setId((String) map.get("ID"));
					workPlan.setDatePlan((String) map.get("DATEPLAN"));
					workPlan.setContent((String) map.get("CONTENT"));
					workPlan.setPlanType((String) map.get("PLANTYPE"));
					workPlan.setIsFinish((String) map.get("ISFINISH"));
				}
				flag="1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return workPlan;
	}
	/**
	 * 设置休息日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 下午2:33:16
	 * @param dates
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setFreeDay")
	public JSONObject setFreeDay(String dates){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", "1");
		try {
			service.setFreeDay(dates);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jsonObj.put("flag", "0");
		}
		return jsonObj;
	}
	
	/**
	 * 恢复某些日期
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月18日 下午2:33:55
	 * @param dates
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setBack")
	public JSONObject setBack(String dates){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", "1");
		try {
			service.setBack(dates);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jsonObj.put("flag", "0");
		}
		return jsonObj;
	}

}
