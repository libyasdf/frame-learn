package com.sinosoft.sinoep.modules.zhbg.kqgl.wc.controller;

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
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;
import com.sinosoft.sinoep.modules.zhbg.common.util.KqglUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.entity.GoOutInfo;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.services.GoOutInfoService;
import net.sf.json.JSONObject;

/**
 * 外出的controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月17日 上午9:36:32
 */
@Controller
@RequestMapping("/zhbg/kqgl/wc")
public class GoOutInfoController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GoOutInfoService service;
	
	@Autowired
	private KqglUtil kqglUtil;
	
	/**
	 *HQL语句查询，获取外出的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 上午10:14:26
	 * @param pageImpl
	 * @param creUserName
	 * @param creDeptName
	 * @param goOutTime
	 * @param subflag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql")
	@LogAnnotation(value = "query",opName = "查询草稿列表")
	public PageImpl getList(PageImpl pageImpl,String creUserName,String applicantUnitName,String goOutTime, String subflag){
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(goOutTime)) {
				startDate = goOutTime.substring(0, (goOutTime.length() + 1) / 2 - 1).trim();
				endDate = goOutTime.substring((goOutTime.length() + 1) / 2, goOutTime.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			if (ConfigConsts.START_FLAG.equals(subflag)) {
				pageImpl = service.getPageListDraft(pageable,pageImpl,creUserName,applicantUnitName,startDate,endDate,subflag);
			}else{
				 pageImpl = service.getPageList(pageable,pageImpl,creUserName,applicantUnitName,startDate,endDate,subflag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 下午2:12:24
	 * @param plan
	 * @param ideaName
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存表单")
	@ResponseBody
	@RequestMapping("saveForm")
	public GoOutInfo saveForm(GoOutInfo info){
		try {
			info = service.saveForm(info);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return info;
	}
	
	/**
	 * 根据id获取外出记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月18日 下午3:00:38
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "edit",opName = "修改页面")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		GoOutInfo info = null;
		try {
			info = service.getById(id);
			json.put("flag", "1");
			json.put("data", info);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 根据id删除一条外出记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 下午4:36:19
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	@LogAnnotation(value = "delete",opName = "删除数据")
	public JSONObject deletePlan( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = service.deleteGoOutInfo(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 更新业务表的流程状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 上午9:26:24
	 * @param id
	 * @param subflag
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "修改流程状态")
	@ResponseBody
	@RequestMapping("updateFlag")
	public JSONObject updateFlag(String id,String subflag){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			service.updateSubFlag(id, subflag);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
        return json;
	}
	
	/**
	 * 根据外出的开始时间和结束时间统计出日期
	 * @param startDate
	 * @param endDate
	 * @param startAmOrPm
	 * @param endAmOrPm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getGoOutTime")
	public String getGoOutTime(String startDate,String endDate,String startAmOrPm,String endAmOrPm){
		String data="";
		try {
			data=kqglUtil.getGoOutTime(startDate, endDate, startAmOrPm, endAmOrPm);
			//data= service.getGoOutTime(startDate,endDate,startAmOrPm,endAmOrPm);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return data;
	}
	
	/**
	 * 根据条件查询数据库中外出查询的记录条数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午2:13:13
	 * @param timeRange
	 * @param state
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCnt")
	public JSONObject selectDutyLog(String userids,String goOutTime,String subflag){
		JSONObject json = new JSONObject();
		json.put("cnt", 0);
		try {
				String startDate = "";
				String endDate = "";
				if (StringUtils.isNotBlank(goOutTime)) {
					startDate = goOutTime.substring(0, (goOutTime.length() + 1) / 2 - 1).trim();
					endDate = goOutTime.substring((goOutTime.length() + 1) / 2, goOutTime.length()).trim();
				}
				long cnt = service.getCnt(userids,startDate,endDate,subflag);
				json.put("cnt", cnt);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		
		return json;
	}
	
	/**
	 * 导出
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午2:59:36
	 * @param timeRange
	 * @param state
	 * @param request
	 * @param response
	 */
	@LogAnnotation(value = "import",opName = "导出excel")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportDutyLog(String userids,String goOutTime,String subflag,HttpServletRequest request, HttpServletResponse response) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(goOutTime)) {
				startDate = goOutTime.substring(0, (goOutTime.length() + 1) / 2 - 1).trim();
				endDate = goOutTime.substring((goOutTime.length() + 1) / 2, goOutTime.length()).trim();
			}
			ExportDataToExcel exportData = new ExportDataToExcel();
			List<ExportBean> list = new ArrayList<ExportBean>();
			ExportBean exportBean = new ExportBean();
			List<GoOutInfo> goOutList = service.getGoOutList(userids,startDate,endDate, subflag);// 查询数据
			exportBean.setExportList(goOutList);
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
