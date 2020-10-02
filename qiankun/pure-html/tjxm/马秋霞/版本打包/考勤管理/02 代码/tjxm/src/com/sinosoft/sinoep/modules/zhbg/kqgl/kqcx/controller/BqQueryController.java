package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.controller;

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
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.entity.SupplementSign;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.BqQueryService;
import net.sf.json.JSONObject;
/**
 * 补签查询Controller
 * @author 郝灵涛
 * @Date 2018年7月23日 上午9:55:02
 */
@Controller
@RequestMapping("zhbg/kqgl/kqcx/bqQuery")
public class BqQueryController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BqQueryService bqQueryService;
/**
 * 补签查询Controller
 * @author 郝灵涛
 * @Date 2018年7月23日 上午10:54:21
 * @param pageImpl
 * @param timeRange
 * @param supplementSignType
 * @param creUserName
 * @param applicantUnitName
 * @param subflag
 * @return
 */
	@LogAnnotation(value = "query",opName = "查询补签列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String supplementSignType, String userId,
			String subflag) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim()+" 00:00:00";
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim()+" 23:59:59";
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl=bqQueryService.getPageListDraft(pageable, pageImpl, startDate, endDate, supplementSignType,userId,subflag);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pageImpl;
	}
	/**
	 * 导出excel前查询表中是否有数据
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年6月29日 上午9:28:10
	 * @param timeRange
	 * @param state
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getBqData")
	public JSONObject selectDutyLog(String timeRange, String supplementSignType,String userId,String subflag) {
		String startDate = "";
		String endDate = "";
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			List<SupplementSign> selectlist = bqQueryService.getsByDate(startDate, endDate, supplementSignType,userId,subflag);// 查询数据
			if (selectlist.size() > 0) {
				json.put("flag", "0"); // 0代表查到了数据
			} else {
				json.put("flag", "1"); // 1代表没有查到数据
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 导出Excel
	 * 
	 * @author 郝灵涛
	 * @Date 2018年6月29日 上午10:12:51
	 * @param timeRange
	 * @param state
	 * @param request
	 * @param response
	 */
	@LogAnnotation(value = "query",opName = "导出补签列表")
	@RequestMapping(value = "exportBqData", method = RequestMethod.GET)
	public void exportDutyLog(String timeRange, String supplementSignType,String userId,String subflag,HttpServletRequest request,
			HttpServletResponse response) {
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
			List<SupplementSign> dutyLoglist = bqQueryService.getsByDate(startDate, endDate, supplementSignType,userId,subflag);// 查询数据
			for (SupplementSign emDutyLog : dutyLoglist) {
				// 审批状态： 1：审批中 5： 审批通过 6：审批未通过 3：已撤办 0：草稿
				if (StringUtils.isNotBlank(emDutyLog.getSubflag())) {
					emDutyLog.setSubflag(EmConstants.SUBFLAG[Integer.parseInt(emDutyLog.getSubflag())]);
				}
				// 补签类型 0：补签入 1：补签出 2：补全天
				if (StringUtils.isNotBlank(emDutyLog.getSupplementSignType())) {
					emDutyLog.setSupplementSignType(
							EmConstants.SUPPLEMENTSIGN_TYPE[Integer.parseInt(emDutyLog.getSupplementSignType())]);
				}
				
				String signType = emDutyLog.getSupplementSignType();
				// supplementSignTime 补签时间
				switch (signType) {
				case "补签入":
					emDutyLog.setSupplementSignTime(emDutyLog.getSupplementSignStartTime());
					break;
				case "补签出":
					emDutyLog.setSupplementSignTime(emDutyLog.getSupplementSignEndTime());
					break;
				case "补全天":
					emDutyLog.setSupplementSignTime(
							emDutyLog.getSupplementSignStartTime() + " " + emDutyLog.getSupplementSignEndTime());
					break;
				}
			}
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
