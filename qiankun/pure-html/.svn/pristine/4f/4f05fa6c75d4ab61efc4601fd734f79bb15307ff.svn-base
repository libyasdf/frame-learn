package com.sinosoft.sinoep.modules.dagl.wdgl.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglReceiveFile;
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglSendFile;
import com.sinosoft.sinoep.modules.dagl.wdgl.services.DaglSendFileService;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

/**
 * TODO 文电管理控制层(发文)
 * @author 李颖洁  
 * @date 2018年11月10日  下午6:04:09
 */
@Controller
@RequestMapping("/dagl/wdgl/sendFile")
public class DaglSendFileController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DaglSendFileService fileManageService;
	
	/**
	 * TODO 保存文件信息(发文)
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:42:17
	 * @param sendFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "保存文件信息")
	@ResponseBody
	@RequestMapping(value="save",method=RequestMethod.POST)
	public JSONObject saveSendFile(DaglSendFile sendFile){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			sendFile = fileManageService.editSendFile(sendFile);
			json.put("flag", "1");
			json.put("data", sendFile);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "保存数据失败！");
		}
		return json;
	} 
	
	/**
	 * TODO 修改文件信息(发文)
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:10:00
	 * @param SendFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "edit",opName = "修改文件信息")
	@ResponseBody
	@RequestMapping(value="edit")
	public JSONObject editSendFile(DaglSendFile sendFile ){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//根据id 获取文件信息
			sendFile = fileManageService.getOne(sendFile);
			json.put("flag", "1");
			json.put("data", sendFile);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	} 
	
	/**
	 * TODO 删除文件信息（单个）(发文)
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:25:09
	 * @param sendFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "删除单个数据")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject deleteOne(DaglSendFile sendFile){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//删除一个文件
			int result = fileManageService.deleteOne(sendFile);
			if(result>0){
				json.put("flag", "1");
				json.put("msg", "删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * TODO 删除文件信息（批量）(发文)
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:25:09
	 * @param sendFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "batchDelete",opName = "批量删除数据")
	@ResponseBody
	@RequestMapping("batchDelete")
	public JSONObject deletePlan(String ids){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//删除多个文件
			int result = fileManageService.batchDelete(ids);
			if(result>0){
				json.put("flag", "1");
				json.put("msg", "删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * TODO 获取文件信息列表，带分页(发文)
	 * @author 李颖洁  
	 * @date 2018年11月12日下午2:07:06
	 * @param pageImpl
	 * @param sendFile
	 * @return PageImpl
	 */
	@LogAnnotation(value = "query",opName = "查询文件数据列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getFileList(PageImpl pageImpl,DaglSendFile sendFile) {
		pageImpl.setFlag("0");
		try {
			
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			sendFile.setCreChushiId(UserUtil.getCruChushiId());
			Page<DaglSendFile> page = fileManageService.list(sendFile, pageImpl,pageable);
			pageImpl.setFlag("1");
			pageImpl.getData().setRows(page.getContent());
			pageImpl.getData().setTotal((int)page.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}

	/**
	 * TODO 获取打印数据
	 * @author 张浩磊
	 * @date 2018年11月12日下午2:07:06
	 */
	@LogAnnotation(value = "query",opName = "查询文件打印列表")
	@ResponseBody
	@RequestMapping("getDestoryList")
	public List<DaglSendFile> getDestoryList(String year) {
		List<DaglSendFile> list = new ArrayList<>();
		list = fileManageService.getDestoryList(year);
		return list;
	}

	/**
	 * TODO 获取打印数据
	 * @author 张浩磊
	 * @date 2018年11月12日下午2:07:06
	 */
	@LogAnnotation(value = "query",opName = "查询文件打印列表")
	@ResponseBody
	@RequestMapping("getPrintList")
	public List<DaglSendFile> getPrintList(DaglSendFile sendFile,String ids) {
		List<DaglSendFile> list = new ArrayList<>();
        sendFile.setCreChushiId(UserUtil.getCruChushiId());
		list = fileManageService.getDaglSendFileList(sendFile,ids);
		return list;
	}
	
    /**
     * TODO 导入数据(发文)
     * @author 李颖洁  
     * @date 2018年11月16日下午2:09:35
     * @param request
     * @param fileToUpload
     * @return JSONObject
     */
    @RequestMapping(value = "/importInfo",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importInfo(HttpServletRequest request, @RequestParam("file") MultipartFile fileToUpload){
        String filePath = request.getSession().getServletContext().getRealPath("/")+"upload"+fileToUpload.getOriginalFilename();
        JSONObject json = new JSONObject();
        String msg = "导入成功！";
        try {
            msg = fileManageService.importInfo(filePath,fileToUpload);
        } catch (Exception e) {
            msg = "导入失败！";
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }

        json.put("msg",msg);
        return json;
    }

    /**
	 * TODO 归档文件操作(发文)
	 * @author 李颖洁  
	 * @date 2018年11月12日上午9:35:14
	 * @param sendFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "文件归档")
	@ResponseBody
	@RequestMapping(value="/tidyRecode")
	public JSONObject tidyRecode(ContrastingRelations relations,@RequestParam("data")String data){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			List<DaglSendFile> listData = JSON.parseArray(data,DaglSendFile.class);
			String ids = "";
			for (DaglSendFile daglSendFile : listData) {
				ids += "'"+daglSendFile.getId()+"',";
			}
			ids = ids.substring(0,ids.length()-1);
			if(ids.length()>0){
				int flag = fileManageService.tidyRecode(relations,ids);
				if(flag>0){
					int num = fileManageService.updateState(ids,DAGLCommonConstants.WEN_DIAN_STATUS[1]);
					if(num>0){
						json.put("flag", "1");
					}else{
						json.put("msg", "归档失败！");
					}
					
				}else{
					json.put("msg", "归档失败！");
				}
			}else{
				json.put("msg", "请选择数据！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "归档失败！");
		}
		return json;
	} 
	
	/**
	 * TODO 根据勾选的数据批量修改数据
	 * @author 李颖洁  
	 * @date 2018年12月18日下午4:55:39
	 * @param ids 
	 * @param updateType 
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "根据勾选的数据批量修改数据")
	@ResponseBody
	@RequestMapping(value="batchUpdateSelectData")
	public JSONObject batchUpdateSelectData(String ids,String updateType){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//根据id 获取文件信息
			int num  = fileManageService.batchUpdate(ids,updateType);
			json.put("flag", "1");
			json.put("data", num);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 根据查询条件批量修改数据
	 * @author 李颖洁  
	 * @date 2018年12月18日下午4:55:39
	 * @param sendFile 
	 * @param updateType 
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "根据勾选的数据批量修改数据")
	@ResponseBody
	@RequestMapping(value="batchUpdateQueryData")
	public JSONObject batchUpdateQueryData(DaglSendFile sendFile,String updateType){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//根据id 获取文件信息
			List<DaglSendFile> list = fileManageService.getList(sendFile);
			if(list.size()>0){
				String ids = "";
				for (DaglSendFile file : list) {
					ids += file.getId()+",";
				}      
				ids = ids.substring(0,ids.length()-1);
				int num  = fileManageService.batchUpdate(ids,updateType);
				json.put("flag", "1");
				json.put("data", num);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
}
