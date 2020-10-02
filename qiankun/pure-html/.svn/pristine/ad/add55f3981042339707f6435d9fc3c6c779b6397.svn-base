package com.sinosoft.sinoep.modules.mypage.diningmenu.controller;

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
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.diningmenu.constant.EmConstants;
import com.sinosoft.sinoep.modules.mypage.diningmenu.entity.DiningMenu;
import com.sinosoft.sinoep.modules.mypage.diningmenu.service.DiningMenuService;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;

import net.sf.json.JSONObject;

/**
 * 模板Controller层 TODO
 * 
 * @author 张建明
 * @Date 2018年3月15日 下午8:28:54
 */
@Controller
@RequestMapping("diningMenu")
public class DiningMenuController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DiningMenuService diningMenuServices;

	/**
	 * 
	 * TODO 查询菜谱列表
	 * 
	 * @author 张建明
	 * @Date 2018年5月8日 下午3:02:55
	 * @param pageImpl
	 * @param timeRange
	 * @param state
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql")
	public PageImpl getList(PageImpl pageImpl, String timeRange, String state) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = diningMenuServices.getPageList(pageable, pageImpl, startDate, endDate, state);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return pageImpl;
	}

	/**
	 * 
	 * TODO 保存食谱
	 * 
	 * @author 张建明
	 * @Date 2018年5月8日 下午3:03:19
	 * @param ideaName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveForm")
	public JSONObject saveForm(DiningMenu diningMenu, String ideaName) {
		JSONObject json = new JSONObject();
		try {
			diningMenu = diningMenuServices.saveForm(diningMenu, ideaName);
			json.put("flag", "1");
			json.put("data", diningMenu);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * 
	 * TODO 删除菜谱
	 * 
	 * @author 张建明
	 * @Date 2018年5月8日 下午3:03:34
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteDiningMenu")
	public JSONObject deleteDiningMenu(String id) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = diningMenuServices.deleteDiningMenu(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 
	 * TODO 打开只读、修改页面时，查询数据进行渲染
	 * 
	 * @author 张建明
	 * @Date 2018年5月8日 下午3:04:03
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DiningMenu diningMenu = null;
		try {
			diningMenu = diningMenuServices.getById(id);
			json.put("flag", "1");
			json.put("data", diningMenu);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 
	 * TODO 根据日期获取当天菜谱
	 * 
	 * @author 张建明
	 * @Date 2018年5月8日 下午3:04:18
	 * @param date
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getByDate")
	public JSONObject getByDate(String date) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DiningMenu diningMenu = null;
		try {
			diningMenu = diningMenuServices.getByDate(date);
			if (diningMenu != null) {
				json.put("flag", "1");
				json.put("data", diningMenu);
			} else {
				json.put("data", "暂无食谱数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 
	 * TODO 根据日期获取当天菜谱是否存在
	 * 
	 * @author 张建明
	 * @Date 2018年5月8日 下午3:04:18
	 * @param date
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isExist")
	public JSONObject isExist(String date, String id) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			List<DiningMenu> listDm = diningMenuServices.isExist(date, id);
			if (listDm.size() > 0) {
				json.put("flag", "0");//代办存在
				// json.put("data", diningMenu);
			} else {
				json.put("flag", "1");//不存在
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 导出excel
	 * @author 赵海龙
	 * @Date 2018年5月22日 下午2:50:47
	 * @param timeRange
	 * @param state
	 * @param request
	 * @param response
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "导出excel")
	@RequestMapping(value = "/exportDiningMenu", method = RequestMethod.GET)
	public void exportDutyLog(String timeRange,String state, HttpServletRequest request, HttpServletResponse response) {
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
			List<DiningMenu> dutyLoglist = diningMenuServices.getsByDate(startDate,endDate, state);// 查询数据
			for (DiningMenu emDutyLog : dutyLoglist) {
				// 将 代码（0，1）替换成 食谱状态（未交班，已交班）
				emDutyLog.setState(EmConstants.STATUS_NAME[Integer.parseInt(emDutyLog.getState())]);
			}
			exportBean.setExportList(dutyLoglist);
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
	 * 
	 * TODO 导出excel前查询表中是否有数据
	 * @author 赵海龙
	 * @Date 2018年5月23日 下午2:06:28
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectDiningMenu")
	public JSONObject selectDutyLog(String timeRange,String state){
		String startDate = "";
		String endDate = "";
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}			
			List<DiningMenu> selectlist = diningMenuServices.getsByDate(startDate,endDate, state);// 查询数据
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

}
