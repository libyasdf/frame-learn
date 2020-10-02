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
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.QjQueryService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.entity.KqglLeaveInfo;

import net.sf.json.JSONObject;

/**
 * 请假查询Controller
 * @author 郝灵涛
 * @Date 2018年7月23日 上午9:49:24
 */
@Controller
@RequestMapping("zhbg/kqgl/kqcx/qjQuery")
public class QjQueryController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private QjQueryService qjQueryService;
	
	@Autowired
	private DataDictionaryService serviceDic;
	
	/**
	 * 请假查查列表
	 * @author 郝灵涛
	 * @Date 2018年7月23日 上午9:50:44
	 * @param pageImpl
	 * @param timeRange
	 * @param leaveType
	 * @param creUserName
	 * @param applicantUnitName
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询请假列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String userId,String subflag,String isOne) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl= qjQueryService.getPageListDraft(pageable, pageImpl, startDate, endDate, userId,subflag,isOne);
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
     * @Date 2018年8月1日 上午10:53:46
     * @param timeRange
     * @param userId
     * @param isLeaveInLieu
     * @param subflag
     * @return
     */
	@ResponseBody
	@RequestMapping("getQjData")
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
			List<KqglLeaveInfo> selectlist = qjQueryService.getsByDate(startDate, endDate, userId,subflag);// 查询数据
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
	@LogAnnotation(value = "query",opName = "导出请假列表")
	@RequestMapping(value = "exportQjData", method = RequestMethod.GET)
	public void exportDutyLog(String timeRange, String userId,String subflag, HttpServletRequest request,
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
			List<KqglLeaveInfo> leaveInfoList = qjQueryService.getsByDate(startDate, endDate, userId,
					subflag);// 查询数据
			List<DataDictionary> dicts=serviceDic.getListByMark("leaveType", "1");
			List<DataDictionary> lcDic=serviceDic.getListByMark("kqcx_lczt", "1");
			for (KqglLeaveInfo leaveInfo : leaveInfoList) {
//				if (StringUtils.isNotBlank(leaveInfo.getSubflag())) {
//					leaveInfo.setSubflag(EmConstants.SUBFLAG[Integer.parseInt(leaveInfo.getSubflag())]);
//				}
				for(DataDictionary dic:dicts){
					if(dic.getCode().equals(leaveInfo.getLeaveType())){
						leaveInfo.setLeaveType(dic.getName());
					}
				}
				for(DataDictionary lcdic:lcDic){
					if(lcdic.getCode().equals(leaveInfo.getSubflag())){
						leaveInfo.setSubflag(lcdic.getName());
					}
				}
//				if (StringUtils.isNotBlank(leaveInfo.getIsLeaveInLieu())) {
//					leaveInfo.setIsLeaveInLieu(
//							EmConstants.Is_LeaveInLieu[Integer.parseInt(leaveInfo.getIsLeaveInLieu())]);
//				}
				// 设置请假日期
				String leaveDate = "";
//				String qjTimeType = leaveInfo.getLeaveTimeType();
//				if (StringUtils.isNotBlank(qjTimeType) && "1".equals(qjTimeType)) {
//					if (StringUtils.isNotBlank(leaveInfo.getLeaveStartTime())) {
//						leaveDate = "" + leaveInfo.getLeaveStartTime();
//					}
//					if (StringUtils.isNotBlank(leaveInfo.getLeaveEndTime())) {
//						leaveDate = leaveDate + " - " + leaveInfo.getLeaveEndTime();
//					}
//					if(StringUtils.isNotBlank(leaveInfo.getLeaveLongTime())){
//						leaveInfo.setLeaveLongTime(leaveInfo.getLeaveLongTime() + " /h");	
//					}
//				} else {
					if (StringUtils.isNotBlank(leaveInfo.getLeaveStartDate())) {
						leaveDate = "" + leaveInfo.getLeaveStartDate();
						if (StringUtils.isNotBlank(leaveInfo.getStartAmOrPm())) {
							if ("1".equals(leaveInfo.getStartAmOrPm())) {
								leaveDate = leaveDate + "上午";
							}
                            if("0".equals(leaveInfo.getStartAmOrPm())){
								leaveDate = leaveDate + "下午";
							}
						}
					}
					if (StringUtils.isNotBlank(leaveInfo.getLeaveEndDate())) {
						leaveDate = leaveDate + " - " + leaveInfo.getLeaveEndDate();
						if (StringUtils.isNotBlank(leaveInfo.getEndAmOrPm())) {
							if ("1".equals(leaveInfo.getEndAmOrPm())) {
								leaveDate = leaveDate + " 上午";
							} 
							if("0".equals(leaveInfo.getEndAmOrPm())){
								leaveDate = leaveDate + " 下午";
							}
						}
					}
					if(StringUtils.isNotBlank(leaveInfo.getLeaveLongTime())){
						leaveInfo.setLeaveLongTime(leaveInfo.getLeaveLongTime());
					}
				//}
				leaveInfo.setLeaveDate(leaveDate);
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
