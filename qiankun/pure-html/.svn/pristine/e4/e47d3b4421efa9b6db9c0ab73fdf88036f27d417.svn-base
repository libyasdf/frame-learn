package com.sinosoft.sinoep.modules.system.component.affix.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.system.component.affix.services.AffixService;
import com.sinosoft.sinoep.modules.system.component.affix.util.UploadUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * TODO 文件上传
 * @author 周俊林
 * @Date 2018年3月14日 上午9:25:20
 */
@Controller
@RequestMapping("/system/component/affix")
public class AffixController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AffixService affixService;
	
	/**
	 * TODO 上传附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:34:14
	 * @param request
	 * @param tableName
	 * @param tableId
	 * @param recordId
	 * @return
	 */
	@ResponseBody
	@LogAnnotation(opType = OpType.SAVE,opName = "上传附件")
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public JSONObject upload(HttpServletRequest request, String tableName, String tableId,String recordId,String fileListId,String defaultName) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray files = new JSONArray();
		json.put("flag", "0");
		json.put("msg", "上传失败");
		json.put("data", null);
		try {
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 取得上传的所有文件
					List<MultipartFile> fileList = multiRequest.getFiles(iter.next());
					if(fileList.size() != 0){
						//判断文件后缀是否有篡改，如果有篡改，则返回失败
						if(!UploadUtil.isFileFalsify(fileList)){
							for (MultipartFile file : fileList) {
								// 上传
								String uuid = affixService.fileUpload(file, tableName, tableId,recordId,fileListId,defaultName);
								if (StringUtils.isNotBlank(uuid)) {
									JSONObject jfile = new JSONObject();
									jfile.put("name", file.getOriginalFilename());
									jfile.put("id", uuid);
									files.add(jfile);
								}
							}
						}else{
							json.put("msg", "文件类型与后缀名不符！");
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if (files.size() > 0) {
			json.put("msg", "成功上传" + files.size() + "个文件！");
			json.put("flag", "1");
			data.put("files", files);
			json.put("data", data);
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * TODO 删除附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:33:20
	 * @param affixId
	 * @return
	 */
	@LogAnnotation(opType = OpType.DELETE,opName = "删除附件")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public JSONObject delete(String affixId) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			if (StringUtils.isNotBlank(affixId)) {
				affixService.delete(affixId);
				json.put("flag", "1");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * TODO 根据id下载附件（单附件下载）
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:39:59
	 * @param response
	 * @param affixId
	 */
	@ResponseBody
	@LogAnnotation(opType = OpType.QUERY,opName = "下载附件")
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletResponse response, String affixId) {
		if (StringUtils.isNotBlank(affixId)) {
			affixService.download(response,affixId);
		}
	}
	
	/**
	 * TODO 查询附件
	 * @author 周俊林
	 * @Date 2018年3月19日 下午9:13:56
	 * @param tableName
	 * @param tableId
	 * @return
	 */
	@ResponseBody
	@LogAnnotation(opType = OpType.QUERY,opName = "查询附件列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONObject affixList(String tableName, String tableId) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("flag", "0");
		json.put("msg", "");
		json.put("data", null);
		if (StringUtils.isNotBlank(tableName) && StringUtils.isNotBlank(tableId)) {
			try {
				JSONArray files = affixService.affixList(tableName, tableId);
				data.put("files", files);
				json.put("data", data);
				json.put("msg", "");
				json.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		return json;
	}
	
	/**
	 * TODO 保存业务ID到附件表
	 * @author 李利广
	 * @Date 2018年3月28日 上午10:39:59
	 * @param affixIds
	 * @param docId
	 */
	@ResponseBody
	@LogAnnotation(opType = OpType.UPDATE,opName = "保存业务ID到附件表")
	@RequestMapping(value = "/saveIdToAffix")
	public JSONObject saveIdToAffix( String affixIds,String docId) {
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		try {
			Integer count = affixService.saveIdToAffix(affixIds,docId);
			if (count == 0) {
				json.put("flag", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 上传附件到服务器
	 * @author 冯超
	 * @Date 2018年5月14日 下午2:27:23
	 * @param request
	 * @param tableName
	 * @param tableId
	 * @param recordId
	 * @param fileListId
	 * @return
	 */
	@ResponseBody
	@LogAnnotation(opType = OpType.SAVE,opName = "上传附件到服务器")
	@RequestMapping(value = "/fileuploadToServer", method = RequestMethod.POST)
	public JSONObject fileuploadToServer(HttpServletRequest request, String tableName, String tableId,String recordId,String fileListId) {
		return uploadToServerPath( request,  tableName,  tableId, recordId, fileListId, "upload");
	}

	/**
	 *
	 * TODO 上传附件到服务器指定路径
	 * @author 冯超
	 * @Date 2018年5月14日 下午2:27:23
	 * @param request
	 * @param tableName
	 * @param tableId
	 * @param recordId
	 * @param fileListId
	 * @param path 指定保存服务器的路径（路径格式：从项目根路径开始。例如：upload/**）
	 * @return
	 */
	@ResponseBody
	@LogAnnotation(opType = OpType.SAVE,opName = "上传附件到服务器指定路径")
	@RequestMapping(value = "/uploadToServerPath", method = RequestMethod.POST)
	public JSONObject uploadToServerPath(HttpServletRequest request, String tableName, String tableId,String recordId,String fileListId, String path) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray files = new JSONArray();
		json.put("flag", "0");
		json.put("msg", "上传失败");
		json.put("data", null);
		try{
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 取得上传的所有文件
					List<MultipartFile> fileList = multiRequest.getFiles(iter.next());
					if(fileList.size() != 0){
						//判断文件后缀是否有篡改，如果有篡改，则返回失败
						if(!UploadUtil.isFileFalsify(fileList)){
							for (MultipartFile file : fileList) {
								// 上传
								if(path==null){
									path = request.getRealPath("/")+"upload"+File.separator;
								}else{
									path = request.getRealPath("/") +"upload"+File.separator+path + File.separator;
								}
								String id = affixService.fileuploadToServer(request, file, tableName, tableId, recordId, fileListId, path);
								if (StringUtils.isNotBlank(id)) {
									JSONObject jfile = new JSONObject();
									jfile.put("name", file.getOriginalFilename());
									jfile.put("id", id);
									String savePath = affixService.getSavePath(id);
									jfile.put("path",savePath);
									files.add(jfile);
								}
							}
						}else{
							json.put("msg", "文件类型与后缀名不符！");
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if (files.size() > 0) {
			json.put("flag", "1");
			json.put("msg", "成功上传" + files.size() + "个文件！");
			data.put("files", files);
			json.put("data", data);
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * 
	 * TODO 根据id在服务器下载附件
	 * @author 冯超
	 * @Date 2018年5月14日 下午4:59:50
	 * @param response
	 * @param affixId
	 */
	@ResponseBody
	@LogAnnotation(opType = OpType.QUERY,opName = "根据id从服务器下载附件")
	@RequestMapping(value = "/downloadInServer", method = RequestMethod.GET)
	public void downloadInServer(HttpServletResponse response, String affixId) {
		if (StringUtils.isNotBlank(affixId)) {
			affixService.downloadInServer(response,affixId);
		}
	}
	
	/**
	 * 
	 * TODO 删除affix表和上传服务器上的文件
	 * @author 冯超
	 * @Date 2018年5月14日 下午6:03:56
	 * @param affixId
	 * @return
	 */
	@ResponseBody
	@LogAnnotation(opType = OpType.DELETE,opName = "删除affix表和上传服务器上的文件")
	@RequestMapping(value = "/deleteInServer", method = RequestMethod.GET)
	public JSONObject deleteInServer(String affixId) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotBlank(affixId)) {
				String path = affixService.getSavePath(affixId);
				if(deleteFile(path)){
					affixService.delete(affixId);
					json.put("flag", "1");
				}
			}
		} catch (Exception e){
			log.error(e.getMessage(),e);
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月14日 下午7:41:53
	 * @param fileName
	 * @return
	 */
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.info("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				log.info("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			log.info("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 下载pdf附件
	 * @param response
	 * @param affixId
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "下载pdf附件")
	@RequestMapping(value = "downloadPDF",method = RequestMethod.GET)
	@ResponseBody
	public void downloadPDF(HttpServletResponse response, String affixId){
		if(StringUtils.isNotBlank(affixId)){
			affixService.downloadPDF(response,affixId);
		}
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 修改附件的回显div（档案编研列表附件删除某个子表信息时需要更改回显的div）
	 * @Date 2018/12/27 16:55
	 * @Param []
	 * @return void
	 **/
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改附件的回显div")
	@RequestMapping(value = "updateAffixFileListId",method = RequestMethod.GET)
	@ResponseBody
	public void updateAffixFileListId(String tableId){
		if(StringUtils.isNotBlank(tableId)){
			affixService.updateAffixFileListId(tableId);
		}
	}
}
