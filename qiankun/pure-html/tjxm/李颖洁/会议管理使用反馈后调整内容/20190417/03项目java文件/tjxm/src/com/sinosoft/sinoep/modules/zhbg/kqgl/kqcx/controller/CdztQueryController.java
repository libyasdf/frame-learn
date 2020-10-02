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
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.entity.ComeLateLeaveEarlyInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.CdztQueryService;
import net.sf.json.JSONObject;

/**
 * 迟到早退查询Controller
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月18日 上午9:53:49
 */
@Controller
@RequestMapping("zhbg/kqgl/cdztQuery")
public class CdztQueryController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CdztQueryService cdztQueryService;
	@Autowired
	private DataDictionaryService serviceDic;

	/**
	 * 获取迟到早退列表
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午4:07:12
	 * @param pageImpl
	 * @param timeRange
	 * @param cdztUserId
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询迟到早退列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String cdztUserId,String subflag,String isOne) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
				pageImpl= cdztQueryService.getPageListDraft(pageable, pageImpl, startDate, endDate,cdztUserId,subflag,isOne);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return pageImpl;
	}

	 /**
	  * 导数据前查看是否有数据
	  * TODO 
	  * @author 郝灵涛
	  * @Date 2018年8月18日 上午11:34:11
	  * @param timeRange
	  * @param userId
	  * @param isLeaveInLieu
	  * @param subflag
	  * @return
	  */
	@ResponseBody
	@RequestMapping("getCdztData")
	public JSONObject selectDutyLog(String timeRange,String userId,String subflag){
		String startDate = "";
		String endDate = "";
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}			
			List<ComeLateLeaveEarlyInfo> selectlist = cdztQueryService.getsByDate(startDate, endDate, userId,subflag);// 查询数据
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
	 * 导出Excel
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月1日 上午10:54:03
	 * @param timeRange
	 * @param userId
	 * @param isLeaveInLieu
	 * @param subflag
	 * @param request
	 * @param response
	 */
	@LogAnnotation(value = "query",opName = "迟到早退导出")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public void exportDutyLog(String cdztDate, String cdztUserId,String subflag, HttpServletRequest request,
			HttpServletResponse response) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(cdztDate)) {
				startDate = cdztDate.substring(0, (cdztDate.length() + 1) / 2 - 1).trim();
				endDate = cdztDate.substring((cdztDate.length() + 1) / 2, cdztDate.length()).trim();
			}
			ExportDataToExcel exportData = new ExportDataToExcel();
			List<ExportBean> list = new ArrayList<ExportBean>();
			ExportBean exportBean = new ExportBean();
			List<ComeLateLeaveEarlyInfo> leaveInfoList = cdztQueryService.getsByDate(startDate, endDate, cdztUserId,subflag);// 查询数据
			List<DataDictionary> lcDic=serviceDic.getListByMark("kqcx_lczt", "1");
			for (ComeLateLeaveEarlyInfo leaveInfo : leaveInfoList) {
				if("0".equals(leaveInfo.getState())){
					leaveInfo.setState("迟到");
				}
				if("1".equals(leaveInfo.getState())){
					leaveInfo.setState("早退");
				}
				for(DataDictionary lcdic:lcDic){
					if(lcdic.getCode().equals(leaveInfo.getSubflag())){
						leaveInfo.setSubflag(lcdic.getName());
					}
				}
			}
			exportBean.setExportList(leaveInfoList);
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
