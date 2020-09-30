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
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.entity.BusinessTrip;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.CcQueryService;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("zhbg/kqgl/kqcx/ccQuery")
public class CcQueryController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CcQueryService businessTripService;
	@Autowired
	private DataDictionaryService serviceDic;
	/**
	 * 出差查询Controller
	 * @author 郝灵涛
	 * @Date 2018年7月23日 上午11:00:59
	 * @param pageImpl
	 * @param busiTripType
	 * @param timeRange
	 * @param subflag
	 * @param iftjj 1 查询天津局（行政出差业务出差查询使用）0 查询本部门的
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询出差列表")
	@ResponseBody
	@RequestMapping("getlistBootHql")
	public PageImpl getList(PageImpl pageImpl, String busiTripType, String timeRange,String userId, String subflag,String iftjj,String isOne) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl= businessTripService.getPageListDraft(pageable, pageImpl, busiTripType,userId,startDate, endDate, subflag,iftjj,isOne);
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
	@RequestMapping("getCcData")
	public JSONObject selectDutyLog(String timeRange,String busiTripType,String userId,String subflag,String iftjj){
		String startDate = "";
		String endDate = "";
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}			
			List<BusinessTrip> selectlist = businessTripService.getsByDate(startDate, endDate, busiTripType, userId,subflag,iftjj);// 查询数据
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
  * 导出
  * TODO 
  * @author 郝灵涛
  * @Date 2018年8月1日 上午11:16:54
  * @param overTimeType
  * @param timeRange
  * @param subflag
  * @param request
  * @param response
  * 
  *  @param iftjj  1 查询天津局的（行政出差和业务出差使用）   0查询本部门的
  */
	@LogAnnotation(value = "query",opName = "导出出差列表")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportDutyLog(String timeRange,String busiTripType,String userId, String subflag,String iftjj,HttpServletRequest request, HttpServletResponse response) {
		String startDate = "";
		String endDate = "";
		if(StringUtils.isBlank(busiTripType)){
			busiTripType=request.getParameter("busiTripType");
		}
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			ExportDataToExcel exportData = new ExportDataToExcel();
			List<ExportBean> list = new ArrayList<ExportBean>();
			ExportBean exportBean = new ExportBean();
			List<BusinessTrip> overTimeList = businessTripService.getsByDate(startDate, endDate, busiTripType, userId,subflag,iftjj);// 查询数据
			
			List<DataDictionary> lcDic=serviceDic.getListByMark("kqcx_lczt", "1");
			for (BusinessTrip bustrip : overTimeList) {
					for(DataDictionary dic:lcDic){
						if(dic.getCode().equals(bustrip.getSubflag())){
							bustrip.setSubflag(dic.getName());
						}
					}
			}
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
}
