package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service.KqAttendanceService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/kqgl/xtpz/attendance")
public class KqAttendanceController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KqAttendanceService kqAttendanceService;
	
	/**
	 * 
	 * TODO 导入出勤信息
	 * @author 冯超
	 * @Date 2018年5月30日 上午11:13:57
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/importInfo",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject importInfo(HttpServletRequest request, @RequestParam("file") MultipartFile file){
		//String filePath = request.getSession().getServletContext().getRealPath("/")+"upload"+file.getOriginalFilename();
		JSONObject json = new JSONObject();
		String msg = "导入成功！";
		try {
			//msg = kqAttendanceService.importInfo(filePath,file);
			msg = kqAttendanceService.importCSV(file);
		} catch (Exception e) {
			 msg = "导入失败！";
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		
		json.put("msg",msg);
		return json;
	}
	
	/**
	 * 获取考勤记录
	 * TODO 
	 * @author 冯超
	 * @Date 2018年7月26日 下午5:43:21
	 * @param pageImpl
	 * @param importDate
	 * @param cardNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlist")
	public PageImpl getList(PageImpl pageImpl, String importDate, String cardNumber,String name) {
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
			pageImpl = kqAttendanceService.getPageList(pageable, pageImpl, importDate ,cardNumber,name);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return pageImpl;
	}
	
	
}
