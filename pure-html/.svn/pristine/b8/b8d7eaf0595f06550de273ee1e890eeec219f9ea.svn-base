package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.PersonalStats;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service.SectionStatsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("zhbg/kqgl/statistics/sectionlStats")
public class SectionStatsController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SectionStatsService service;
	
	/**
	 * 处长和科长可以看到的一些人员的统计情况的数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月29日 下午3:03:34
	 * @param orgId
	 * @param timeRange
	 *@param flg表示来自于哪，为0表示来自领导查询统计，1表示来自查询统计的只读
	 *@param isAll表示是否选中所有的人，1表示选中所有的人，0表示不是
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getList")
	@LogAnnotation(value = "query",opName = "领导查询统计查询列表")
	public PageImpl getList(PageImpl pageImpl,String orgId,String timeRange,String userids,String deptid,String deptname,String flg,String isAll,Integer total,String from){
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<PersonalStats>list=new ArrayList<PersonalStats>();
		try {
			long beginTime=System.currentTimeMillis();
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl=service.getList(pageable,pageImpl,timeRange,userids,deptid,deptname,flg,isAll,total,from);
			long endTime=System.currentTimeMillis();
			System.out.println("\n"+"领导查询统计所需要时间："+(endTime-beginTime)/1000+"s");
			/*JSONObject data = new JSONObject();
			data.put("total",list.size());
			JSONArray array = new JSONArray();
			array = JSONArray.fromObject(list);
			data.put("rows", array);
			json.put("data",data);*/
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			json.put("flag", "0");
		}
		return pageImpl;
	}
}
