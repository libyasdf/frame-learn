package com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.controller;

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
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;
import com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.entity.AbsenteeismInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.service.AbsenteeismInfoService;
import net.sf.json.JSONObject;

/**
 * 请假控制层 TODO
 * 
 * @author 冯超
 * @Date 2018年4月10日 下午4:41:33
 */
@Controller
@RequestMapping("zhbg/kqgl/absenteeism")
public class AbsenteeismController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AbsenteeismInfoService absenteeismInfoService;

	/**
	 * 保存表单 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月11日 上午10:59:59
	 * @param kqglLeaveInfo
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存旷工录入")
	@ResponseBody
	@RequestMapping("saveForm")
	public AbsenteeismInfo saveForm(AbsenteeismInfo absenteeismInfo) {
		try {
			absenteeismInfo = absenteeismInfoService.saveForm(absenteeismInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return absenteeismInfo;
	}

	/**
	 * 请假列表 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月11日 下午2:01:41
	 * @param pageImpl
	 * @param timeRange
	 * @param leaveType
	 * @param creUserName
	 * @param applicantUnitName
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询旷工列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String userId) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
				pageImpl= absenteeismInfoService.getPageListDraft(pageable, pageImpl, startDate, endDate, userId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pageImpl;
	}

	/**
	 * 修改 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:36:41
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "修改旷工信息")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		AbsenteeismInfo absenteeismInfo = null;
		try {
			absenteeismInfo = absenteeismInfoService.getById(id);
			json.put("flag", "1");
			json.put("data", absenteeismInfo);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 删除 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:38:03
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除旷工信息")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete(String id) {
		JSONObject json = new JSONObject();
		int result = absenteeismInfoService.delete(id);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * Excel导出前查看是否有数据
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月17日 上午11:07:14
	 * @param timeRange
	 * @param leaveType
	 * @param isLeaveInLieu
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "导出旷工信息")
	@ResponseBody
	@RequestMapping("getCdztData")
	public JSONObject selectDutyLog(String timeRange,String userId){
		String startDate = "";
		String endDate = "";
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}			
			List<AbsenteeismInfo> selectlist = absenteeismInfoService.getsByDate(startDate, endDate, userId);// 查询数据
			if(selectlist.size()>0){
				json.put("flag", "0"); //0代表查到了数据
			}else{
				json.put("flag", "1"); //1代表没有查到数据
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 
	 *  导出Excel
	 * @author 郝灵涛
	 * @Date 2018年6月29日 上午10:12:51
	 * @param timeRange
	 * @param state
	 * @param request
	 * @param response
	 */
	@LogAnnotation(value = "query",opName = "导出旷工信息")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public void exportDutyLog(String absenteeismTime,String userId, HttpServletRequest request,HttpServletResponse response) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(absenteeismTime)) {
				startDate = absenteeismTime.substring(0, (absenteeismTime.length() + 1) / 2 - 1).trim();
				endDate = absenteeismTime.substring((absenteeismTime.length() + 1) / 2, absenteeismTime.length()).trim();
			}
			ExportDataToExcel exportData = new ExportDataToExcel();
			List<ExportBean> list = new ArrayList<ExportBean>();
			ExportBean exportBean = new ExportBean();
			List<AbsenteeismInfo> dutyLoglist = absenteeismInfoService.getsByDate(startDate, endDate, userId);// 查询数据
			exportBean.setExportList(dutyLoglist);
			exportBean.setGetMethod(EmConstants.GET_METHOD);
			exportBean.setHeader(EmConstants.HEADER);
			exportBean.setSheetTitle(EmConstants.SHEET_TITLE[0]);
			exportBean.setSheetColWidth(EmConstants.SHEET_COL_WIDTH);// 列宽默认20，可以自己指定
			exportBean.setFontName(EmConstants.FONT_NAME);// 默认宋体
			exportBean.setTextFontSize(EmConstants.TEXT_FONT_SIZE);// 文本字体大小默认11
			exportBean.setTitleFontSize(EmConstants.TITLE_FONT_SIZE);// 表头字体大小默认14
			exportBean.setTitleRowHight(EmConstants.TITLE_ROW_HEIGHT);// 表头行高默认600
			list.add(exportBean);
			exportData.excelProject(response, list, EmConstants.FILE_NAME[0]);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}

}
