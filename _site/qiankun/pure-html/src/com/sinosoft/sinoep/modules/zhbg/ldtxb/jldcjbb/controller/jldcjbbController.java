package com.sinosoft.sinoep.modules.zhbg.ldtxb.jldcjbb.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.ldtxb.jldcjbb.entity.Jldcjbb;
import com.sinosoft.sinoep.modules.zhbg.ldtxb.jldcjbb.services.JldcjbbService;

import net.sf.json.JSONObject;

/**
 * 模板Controller层
 * TODO 
 * @author 李利广
 * @Date 2018年3月15日 下午8:28:54
 */
@Controller
@RequestMapping("/zhbg/ldtxb/jldcjbb")
public class jldcjbbController {
	
	@Autowired
	private JldcjbbService jldcjbbService;
	
	@Autowired
	private com.sinosoft.sinoep.modules.taskplan.services.TaskPlanService taskPlanServices;
	
	/**
	 * TODO 原系统DEMO列表查询方法
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:53:12
	 * @param request
	 * @param pageNum
	 * @param showCount
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param subflag
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping("getlist")
	public Page getList(Integer pageNum,Integer showCount,String title,String startTime,String endTime,String subflag){
		return taskPlanService.getPageList(pageNum,showCount,title,startTime,endTime,subflag);
	}*/
	
	/**
	 * TODO HQL语句查询
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:52:56
	 * @param request
	 * @param pageImpl
	 * @param title
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql")
	public PageImpl getList(PageImpl pageImpl,String name,String timeRange){
		String startDate = "";
		String endDate = "";
		if (StringUtils.isNotBlank(timeRange)) {
			startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
		}
		Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
		return jldcjbbService.getPageList(pageable,pageImpl,name,startDate,endDate);
	}
	
	/**
	 * TODO 保存表单
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:52:40
	 * @param request
	 * @param plan
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveForm")
	public Jldcjbb saveForm(Jldcjbb jldcjbb){
		try {
			jldcjbb = jldcjbbService.saveForm(jldcjbb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jldcjbb;
	}
	
	/**
	 * TODO 删除数据
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:53:53
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject deletePlan( String id){
		JSONObject json = new JSONObject();
		int result = jldcjbbService.delete(id);
		if (result != 0) {
			json.put("flag", "1");
		}else{
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * TODO 打开只读、修改页面时，查询数据进行渲染
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:54:07
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public Jldcjbb edit( String id){
		Jldcjbb jldcjbb = null;
		try {
			jldcjbb = jldcjbbService.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jldcjbb;
	}
	
	/**
	 * TODO 更新业务表流程状态
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:54:17
	 * @param id
	 * @param subflag
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id,String subflag){
		JSONObject json = new JSONObject();
		String flag = taskPlanServices.updateSubFlag(id, subflag);
        json.put("flag", flag);
        return json;
	}
*/
}
