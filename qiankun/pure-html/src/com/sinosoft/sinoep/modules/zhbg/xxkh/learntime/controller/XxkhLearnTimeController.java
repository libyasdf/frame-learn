package com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.api.dept.service.DeptInfoService;
import com.sinosoft.sinoep.api.dept.vo.DeptUserTreeVo;
import com.sinosoft.sinoep.api.dept.vo.MessageDeptUserTree;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.entity.XxkhLearnTime;
import com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.services.XxkhLearnTimeService;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author 颜振兴
 * 时间：2018年7月27日
 *	XxkhLearnTimeController
 */
@Controller
@RequestMapping("zhbg/xxkh/learntime")
public class XxkhLearnTimeController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private XxkhLearnTimeService service;
	
	/**
	 * 根据学习开始、结束时间保存学习记录
	 * @author 王磊
	 * @Date 2018年8月25日 上午11:42:58
	 * @param learnTime
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存一条学习记录")
	@RequestMapping("saveLearningRecord")
	@ResponseBody
	public JSONObject saveLearningRecord(@RequestBody XxkhLearnTime learnTime) {
		JSONObject json = new JSONObject();
		try {
			service.saveLearningRecord(learnTime);
			json.put("flag", 1);
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info("保存学习时间异常！"+learnTime.getStartTime()+"至"+learnTime.getOverTime());
			json.put("flag", 0);
		}
		return json;
	}
	
	/**
	 * 获取学习时长列表
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月4日 下午2:14:11
	 * @param dt
	 * @param pageImpl
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询学习时长列表")
	@RequestMapping("/learningTimeList_bak")
	@ResponseBody
	public JSONObject learningTimeList(XxkhLearnTime dt,String timeRange,PageImpl pageImpl)  {
		Page<XxkhLearnTime> list = null;
		dt.setCreJuId(UserUtil.getCruJuId());
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		String startTime = "";
		String overTime = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				overTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				dt.setStartTime(startTime);
				dt.setOverTime(overTime);
			}
			json.put("flag", "1");
			list=service.learningTimeTablelist(dt,pageImpl);
			//list = service.list(dt, pageImpl);
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject data = new JSONObject();
		data.put("total", list.getTotalElements());
//		for(XxkhLearnTime dTable:list.getContent()) {
//			dTable.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
//			
//		}
		data.put("rows", list.getContent());
		json.put("data", data);
		return json;
	} 

	/**
	 * 
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月6日 下午2:03:48
	 * @param pageImpl
	 * @param timeRange
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询学习时长列表")
	@ResponseBody
	@RequestMapping("learningTimeList")
	public JSONObject getList11(String timeRange,String treeType,String nodeIds) {
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		String startTime = "";
		String endTime = "";
		List<XxkhLearnTime> list=new ArrayList<XxkhLearnTime>();
		if(StringUtils.isNotBlank(nodeIds)){
			try {
				if (StringUtils.isNotBlank(timeRange)) {
					startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim()+" 00:00:00";
					endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim()+" 23:59:59";
				}
				 list= service.getLearnTotalTime(startTime, endTime,nodeIds);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		JSONArray array = new JSONArray();
		array = JSONArray.fromObject(list);
		JSONObject data = new JSONObject();
		//if(list!=null && list.size()>0){
			data.put("total", list.size());
			data.put("rows", array);
		//}
		json.put("data", data);
		return json;
	}
	
	/**
	 * 根据id查询学习时长明细
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月6日 上午10:19:43
	 * @param id
	 * @param pageImpl
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询单条学习时长信息")
	@RequestMapping("/getLearnTimeById")
	@ResponseBody
	public PageImpl getLearnTimeById(PageImpl pageImpl,String timeRange,String nodeIds) {
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = service.getLearnTimeById(pageable,pageImpl,timeRange,nodeIds);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pageImpl;
	}
	
	/**
	 * 查询下的学习时长
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月6日 下午2:03:48
	 * @param pageImpl
	 * @param timeRange
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询部门学习时长列表")
	@ResponseBody
	@RequestMapping("deptLearningTimeList")
	public PageImpl deptLearningTimeList(PageImpl pageImpl,String deptIds,String chushiIds,String positions,String timeRange,String treeType,String nodeIds) {
		String startTime = "";
		String endTime = "";
		if(StringUtils.isNotBlank(nodeIds)){
			try {
				if (StringUtils.isNotBlank(timeRange)) {
					startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim()+" 00:00:00";
					endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim()+" 23:59:59";
				}
				Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
				pageImpl= service.getLearnDeptTotalTime(pageImpl,pageable,deptIds,chushiIds,positions,startTime,endTime,nodeIds);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}else{
			pageImpl.setFlag("1");
			pageImpl.getData().setRows(new ArrayList<XxkhLearnTime>());
			pageImpl.getData().setTotal(0);
		}
		return pageImpl;
	}
	
	@LogAnnotation(value = "query",opName = "查询单条学习时长信息")
	@RequestMapping("/getDeptLearnTimeById")
	@ResponseBody
	public PageImpl getDeptLearnTimeById(PageImpl pageImpl,String timeRange,String userId,String nodeIds) {
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = service.getDeptLearnTimeById(pageable,pageImpl,timeRange,userId,nodeIds);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pageImpl;
	}
	
	/**
	 * 
	 * TODO 根据部门id查询部门总人数
	 * @author 王磊
	 * @Date 2019年3月1日 上午10:38:18
	 * @param pageImpl
	 * @param timeRange
	 * @param userId
	 * @param nodeIds
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "根据部门id查询部门总人数")
	@RequestMapping("/getDeptUserCount")
	@ResponseBody
	public JSONObject getDeptUserCount(PageImpl pageImpl,String deptId) {
		JSONObject json = new JSONObject();
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        MessageDeptUserTree deptAndUser = deptInfoService.getDeptAndUserBydeptId(deptId);
        List<DeptUserTreeVo> list = deptAndUser.getDeptUserTree();
        List<String> userIdList = new ArrayList<String>();//总人数
        //遍历部门人员list
        for(DeptUserTreeVo d : list){
        	//只查找人员且根据人员id进行过滤，防止一人多岗算做两人
        	if(StringUtils.isNotBlank(d.getType()) && "user".equals(d.getType()) && !userIdList.contains(d.getUuid())){
        		userIdList.add(d.getUuid());
        		//log.info(d.name);
        	}
        }
        json.put("data", userIdList.size());
		return json;
	}
	
}
