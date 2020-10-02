package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.controller;

import com.sinosoft.sinoep.handlerInterceptor.OpType;

import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;

import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.Util.ExcelUtil;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services.AttendenceQueryService;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


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
	@LogAnnotation(opType = OpType.QUERY,opName = "查询列表")
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


}
