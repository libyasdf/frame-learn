package com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.entity.OverTime;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.service.OverTimeService;
import net.sf.json.JSONObject;

/**
 * 加班Controller层 TODO
 * 
 * @author 张建明
 * @Date 2018年4月12日 上午10:42:09
 */
@Controller
@RequestMapping("/zhbg/kqgl/overTime")
public class OverTimeController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OverTimeService overTimeService;

	/**
	 * hql查询加班申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:34:56
	 * @param pageImpl
	 * @param title
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql")
	@LogAnnotation(opType = OpType.QUERY,opName = "查询草稿列表")
	public PageImpl getList(PageImpl pageImpl, String overTimeType, String timeRange, String subflag) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			if (ConfigConsts.START_FLAG.equals(subflag)) {
				pageImpl = overTimeService.getPageListDraft(pageable, pageImpl, overTimeType, startDate, endDate, subflag);
			} else {
				pageImpl = overTimeService.getPageList(pageable, pageImpl, overTimeType, startDate, endDate, subflag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}

	/**
	 * 保存加班申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:35:42
	 * @param overTime
	 * @param ideaName
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存表单")
	@ResponseBody
	@RequestMapping("saveForm")
	public OverTime saveForm(OverTime overTime, String ideaName) {
		try {
			overTime = overTimeService.saveForm(overTime, ideaName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return overTime;
	}

	/**
	 * 删除加班申请草稿 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:35:58
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteoverTime")
	@LogAnnotation(opType = OpType.DELETE,opName = "删除数据")
	public JSONObject deleteoverTime(String id) {
		JSONObject json = new JSONObject();
		int result = overTimeService.deleteOverTime(id);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 加班申请打开只读、修改页面时，查询数据进行渲染 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:36:28
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改页面")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		OverTime overTime = null;
		try {
			overTime = overTimeService.getById(id);
			json.put("flag", "1");
			json.put("data", overTime);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * TODO 更新加班业务表流程状态
	 * 
	 * @author 张建明
	 * @Date 2018年3月15日 下午8:54:17
	 * @param id
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改流程状态")
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id, String subflag) {
		JSONObject json = new JSONObject();
		String flag = overTimeService.updateSubFlag(id, subflag);
		json.put("flag", flag);
		return json;
	}
	
	/**
	 * 导出
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午5:55:42
	 * @param overTimeType
	 * @param timeRange
	 * @param subflag
	 * @param request
	 * @param response
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "导出excel")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportDutyLog(String userId,String overTimeType, String timeRange, String subflag,HttpServletRequest request, HttpServletResponse response) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			ExportDataToExcel exportData = new ExportDataToExcel();
			List<ExportBean> list = new ArrayList<ExportBean>();
			ExportBean exportBean = new ExportBean();
			List<OverTime> overTimeList = overTimeService.getList(userId,overTimeType,startDate,endDate, subflag);// 查询数据
			exportBean.setExportList(overTimeList);
			exportBean.setGetMethod(EmConstants.GET_METHOD);
			exportBean.setHeader(EmConstants.HEADER);
			exportBean.setSheetTitle(EmConstants.SHEET_TITLE[0]);
			exportBean.setSheetColWidth(EmConstants.SHEET_COL_WIDTH);//列宽默认20，可以自己指定
			exportBean.setFontName(EmConstants.FONT_NAME);//默认宋体
			exportBean.setTextFontSize(EmConstants.TEXT_FONT_SIZE);//文本字体大小默认11
			exportBean.setTitleFontSize(EmConstants.TITLE_FONT_SIZE);//表头字体大小默认14
			exportBean.setTitleRowHight(EmConstants.TITLE_ROW_HEIGHT);//表头行高默认600
			list.add(exportBean);
			exportData.excelProject(response, list, EmConstants.FILE_NAME[0]);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}		
	}
	
	/**
	 * 根据加班日期获取加班的开始时间和结束数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 上午9:38:39
	 * @param overTimeDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getStartAndEndTime")
	private JSONObject getStartAndEndTime(String overTimeDate){
		JSONObject jsonObj = overTimeService.getStartAndEndTime(overTimeDate);
		return jsonObj;
	}
	
	/**
	 * 获取某一天加班的开始结束日期以及是否是节假日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 下午4:33:57
	 * @param overTimeDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getStartEndTimeAndIsHoliday")
	private JSONObject getStartEndTimeAndIsHoliday(String overTimeDate){
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj = overTimeService.getStartEndTimeAndIsHoliday(overTimeDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	/**
	 * 根据条件查询数据库中加班查询的记录条数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午2:13:13
	 * @param timeRange
	 * @param state
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCnt")
	public JSONObject getCnt(String userId,String timeRange,String subflag){
		JSONObject json = new JSONObject();
		json.put("cnt", 0);
		try {
				String startDate = "";
				String endDate = "";
				if (StringUtils.isNotBlank(timeRange)) {
					startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
					endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				}
				long cnt = overTimeService.getCnt(userId,startDate,endDate,subflag);
				json.put("cnt", cnt);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
