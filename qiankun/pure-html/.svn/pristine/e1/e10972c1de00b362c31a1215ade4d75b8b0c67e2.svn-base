package com.sinosoft.sinoep.modules.system.config.holidayset.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.modules.system.config.holidayset.entity.DayStatus;
import com.sinosoft.sinoep.modules.system.config.holidayset.service.HolidaySetService;
import net.sf.json.JSONObject;

/**
 * 节假日设置controller层
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月10日 下午5:49:37
 */
@Controller
@RequestMapping("/zhbg/kqgl/holidaySet")
public class HolidaySetController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private HolidaySetService service;
	
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

	/**
	 * 
	 * TODO 根据借阅日期获取应还日期方法
	 * @author 王磊
	 * @Date 2019年2月27日 下午6:53:21
	 * @param startDate 借阅日期
	 * @param num 借几个工作日
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getDateByStartDateAndDays")
	public JSONObject getDateByStartDateAndDays(String startDate, int num){
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("endDate", service.getDateByStartDateAndDays(startDate, num));
			jsonObj.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jsonObj.put("endDate", "查询应还日期异常！");
			jsonObj.put("flag", "0");
		}
		return jsonObj;
	}
}
