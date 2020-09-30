package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.controller;

import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.Util.ExcelUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.AttendenceQueryService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.entity.OverTime;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * TODO 考勤统计controller和查询打卡记录
 * @author 李颖洁  
 * @date 2019年4月26日  上午10:26:54
 */
@Controller
@RequestMapping("zhbg/kqgl/kqcx/attendence")
public class AttendenceQueryController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AttendenceQueryService attendenceQueryService;

	@Autowired
	private DataDictionaryService dataDictionaryService;

	@ResponseBody
	@RequestMapping("getlist")
	@LogAnnotation(opType = OpType.QUERY,opName = "查询考勤统计列表")
	public List<Map<String,Object>> getList(String timeRange,String dataType,String treeIds,String activeId) {
		return attendenceQueryService.getList(timeRange,dataType,treeIds,activeId);
	}

	@RequestMapping("export")
	@LogAnnotation(opType = OpType.QUERY,opName = "考勤统计")
	public void export(String timeRange,String dataType,String treeIds,String activeId,HttpServletResponse response) throws Exception {

		//获取数据
		List<Map<String,Object>> list = attendenceQueryService.getList(timeRange,dataType,treeIds,activeId);
		String mark = "";
		if(activeId.equals("1")){
			mark = "lateCause";
		}else if(activeId.equals("2")){
			mark = "leaveEarlyCause";
		}else{
			mark = "leaveReason";
		}

		List<DataDictionary> dicts = dataDictionaryService.getListByMark(mark,"1");
		List<String> titleList = new ArrayList<String>();
		titleList.add("序号");
		titleList.add("部门名称");
		titleList.add("处室");

		//excel标题
		if(dataType.equals("user")){
			titleList.add("姓名");
		}

		for (DataDictionary dict : dicts) {
			titleList.add(dict.getName());
		}

		String content[][] = new String[list.size()][titleList.size()+1];

		for(int i=0; i<list.size();i++){
			int s = 3;
			content[i][0] = String.valueOf(i+1);
			content[i][1] = list.get(i).get("BUMEN").toString();
			content[i][2] = list.get(i).get("CHUSHI") == null ? "":list.get(i).get("CHUSHI").toString();
			if(dataType.equals("user")){
				content[i][3] = list.get(i).get("NAME").toString();
				s = 4;
			}
			Set<Map.Entry<String, Object>> entries = list.get(i).entrySet(); //每一行 MAP
			for (int j=0; j<dicts.size(); j++) { //列
				int k = 1;
				for (Map.Entry<String, Object> entry : entries) { //查看每个MAP 的KEY
					if(entry.getKey().equals(dicts.get(j).getCode())){ //如果有KEY 和 其中一列相同 就赋值VALUE并跳出
						String value = entry.getValue() == null ? "" : entry.getValue().toString();
						value = value.equals("0")  ? "" : value;
						content[i][j+s] = value;
						break;
					}
					if(dicts.size() == k){
						content[i][j+s] = "";
					}
					k++;
				}
			}
			content[i][titleList.size()] = list.get(i).get("SC") == null ? "":list.get(i).get("SC").toString();
		}

		if(StringUtils.isBlank(timeRange)){
			timeRange = "";
		}
		//excel文件名
		String fileName = timeRange + "考勤统计.xls";

		//sheet名
		String sheetName = "考勤统计";

		if(activeId.equals("1")){
			titleList.add("无故迟到");
		}else if(activeId.equals("2")){
			titleList.add("无故早退");
		}else{
			titleList.add("旷工");
		}

		String[] title = new String[titleList.size()];
		titleList.toArray(title);
		//创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

		//响应到客户端
		try {
			this.setResponseHeader(response, fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//发送响应流方法
	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes("gb2312"),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/x-msdownload;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping("getPageList")
	@LogAnnotation(value = "query",opName = "查询打卡记录列表")
	public PageImpl getPageList(PageImpl pageImpl, String timeRange,String cardNumber,String userId,String isAll) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = attendenceQueryService.getPageList(pageable, pageImpl, startDate,endDate ,cardNumber,userId,isAll);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return pageImpl;
	}
	
	@ResponseBody
	@RequestMapping("getCnt")
	@LogAnnotation(value = "query",opName = "查询打卡记录列表")
	public JSONObject getPageList( String timeRange,String cardNumber,String userId,String isAll) {
		JSONObject json = new JSONObject();
		json.put("cnt", 0);
		try {
				String startDate = "";
				String endDate = "";
				if (StringUtils.isNotBlank(timeRange)) {
					startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
					endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				}
				long cnt = attendenceQueryService.getCnt(startDate,endDate ,cardNumber,userId,isAll);
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
	 * @Date 2018年6月29日 下午5:55:42
	 * @param overTimeType
	 * @param timeRange
	 * @param subflag
	 * @param request
	 * @param response
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "导出excel")
	@RequestMapping(value = "/export1", method = RequestMethod.POST)
	public void export1(String timeRange,String cardNumber,String userId,String isAll,HttpServletRequest request, HttpServletResponse response) {
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
			List<KqglAttendenceHandle> dataList = attendenceQueryService.getExportList(startDate,endDate ,cardNumber,userId,isAll);// 查询数据
			exportBean.setExportList(dataList);
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
