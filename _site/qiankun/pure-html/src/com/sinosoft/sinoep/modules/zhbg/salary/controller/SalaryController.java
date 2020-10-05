package com.sinosoft.sinoep.modules.zhbg.salary.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.zhbg.salary.entity.Salary;
import com.sinosoft.sinoep.modules.zhbg.salary.services.SalaryService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/zhbg/salary")
public class SalaryController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SalaryService service;
	
	/**
	 * 获取分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月28日 下午2:25:30
	 * @param pageImpl
	 * @param dateLog
	 * @param type
	 * @param flag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql")
	public PageImpl getlistBootHql(PageImpl pageImpl,String name,String yearMonth,String idCarNo,String flag,String personId){
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = service.getlistBootHql(pageable,pageImpl,name,yearMonth, idCarNo,flag,personId);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月29日 上午9:30:59
	 * @param request
	 * @param file
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "工资导入excel")
	@RequestMapping(value = "/importInfo",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject importInfo(HttpServletRequest request, @RequestParam("file") MultipartFile file){
		String filePath = request.getSession().getServletContext().getRealPath("/")+"upload"+file.getOriginalFilename();
		String msg = "导入失败！";
		try {
			msg = service.importInfo(filePath,file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("msg",msg);
		return json;
	}
	
	/**
	 * 根据用户id获取用户的详细信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 上午10:02:39
	 * @param userIds
	 * @return
	 */
	@RequestMapping(value = "/getUserVo",method = RequestMethod.POST)
	@ResponseBody
	public static SysFlowUserVo getUserVo(String userId){
		Map<String, SysFlowUserVo> map=UserUtil.getUserVo(userId);
		SysFlowUserVo user=map.get(userId);
		return user;
	}
	
	/**
	 * 删除
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 下午1:30:56
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = service.delete(id);
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
	 * 修改
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 下午2:27:10
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		Salary salary = null;
		try {
			salary = service.getById(id);
			json.put("flag", "1");
			json.put("data", salary);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 修改
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 下午2:47:41
	 * @param info
	 * @param flag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveForm")
	public Salary saveForm(Salary info){
		try {
			info = service.saveForm(info);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return info;
	}

}
