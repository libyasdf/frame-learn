package com.sinosoft.sinoep.modules.dagl.wdgl.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.pdf.BarcodePDF417;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglConfig;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import net.sf.json.JSONArray;
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
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglReceiveFile;
import com.sinosoft.sinoep.modules.dagl.wdgl.services.DaglReceiveFileService;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

/**
 * TODO 文电管理控制层（收文）
 * @author 李颖洁  
 * @date 2018年11月10日  下午6:04:09
 */
@Controller
@RequestMapping("/dagl/wdgl/receiveFile")
public class DaglReceiveFileController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DaglReceiveFileService fileManageService;

	@Autowired
	private TreeService service;
	
	/**
	 * TODO 保存文件信息（收文）
	 * @author 李颖洁  
	 * @date 2018年11月12日上午9:35:14
	 * @param receiveFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "保存文件信息")
	@ResponseBody
	@RequestMapping(value="save",method=RequestMethod.POST)
	public JSONObject saveReceiveFile(DaglReceiveFile receiveFile){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			receiveFile = fileManageService.editReceiveFile(receiveFile);
			json.put("flag", "1");
			json.put("data", receiveFile);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "保存数据失败！");
		}
		return json;
	} 
	
	/**
	 * TODO 修改文件信息（收文）
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:10:00
	 * @param receiveFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "修改文件信息")
	@ResponseBody
	@RequestMapping(value="edit")
	public JSONObject editReceiveFile(DaglReceiveFile receiveFile ){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//根据id 获取文件信息
			receiveFile = fileManageService.getOne(receiveFile);
			json.put("flag", "1");
			json.put("data", receiveFile);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	} 
	
	/**
	 * TODO 删除文件信息（单个）（收文）
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:25:09
	 * @param receiveFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "删除单个数据")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject deleteOne(DaglReceiveFile receiveFile){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//删除一个文件
			int result = fileManageService.deleteOne(receiveFile);
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
	 * TODO 删除文件信息（批量）（收文）
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:25:09
	 * @param receiveFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "delete",opName = "批量删除数据")
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
	 * TODO 获取文件信息列表，带分页（收文）
	 * @author 李颖洁  
	 * @date 2018年11月12日下午2:07:06
	 * @param pageImpl
	 * @param receiveFile
	 * @return PageImpl
	 */
	@LogAnnotation(value = "query",opName = "查询文件数据列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getFileList(PageImpl pageImpl,DaglReceiveFile receiveFile) {
		pageImpl.setFlag("0");
		try {
			
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			receiveFile.setCreChushiId(UserUtil.getCruChushiId());
			Page<DaglReceiveFile> page = fileManageService.list(receiveFile, pageImpl,pageable);
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
	public List<DaglReceiveFile> getDestoryList(String year) {
		List<DaglReceiveFile> list = new ArrayList<>();
		list = fileManageService.getDestoryList(year);
		return list;
	}

	/**
	 * TODO 获取销毁数据
	 * @author 张浩磊
	 * @date 2018年11月12日下午2:07:06
	 */
	@LogAnnotation(value = "query",opName = "查询文件打印列表")
	@ResponseBody
	@RequestMapping("getPrintList")
	public List<DaglReceiveFile> getPrintList(DaglReceiveFile daglReceiveFile,String ids) {
		List<DaglReceiveFile> list = new ArrayList<>();
		daglReceiveFile.setCreChushiId(UserUtil.getCruChushiId());
		list = fileManageService.getDaglReceiveFileList(daglReceiveFile,ids);
		return list;
	}
	
    /**
     * TODO 导入数据（收文）
     * @author 李颖洁  
     * @date 2018年11月16日下午2:09:35
     * @param request
     * @param fileToUpload
     * @return JSONObject
     */
	@LogAnnotation(value = "save",opName = "导入数据（收文）")
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
	 * TODO 归档文件操作（收文）
	 * @author 李颖洁  
	 * @date 2018年11月12日上午9:35:14
	 * @param receiveFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "文件归档")
	@ResponseBody
	@RequestMapping(value="/tidyRecode")
	public JSONObject tidyRecode(ContrastingRelations relations,@RequestParam("data")String data){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			List<DaglReceiveFile> listData = JSON.parseArray(data,DaglReceiveFile.class);
			String ids = "";
			for (DaglReceiveFile daglReceiveFile : listData) {
				ids += "'"+daglReceiveFile.getId()+"',";
			}
			ids = ids.substring(0,ids.length()-1);
			if(ids.length()>0){
				int flag = fileManageService.tidyRecode(relations,ids);
				if(flag==0){
					json.put("msg", "推送失败！");
				}else{
					//fileManageService.tidyRecode(relations,ids);已经在事务中更新了文件状态，去掉多余代码 王磊20190409
					json.put("flag", "1");
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
	 * TODO 扫码枪配置（收文）
	 * @author 张浩磊
	 * @date 2018年11月12日上午9:35:14
	 * @param receiveFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "save",opName = "保存文件信息")
	@ResponseBody
	@RequestMapping(value="saveConfig",method=RequestMethod.POST)
	public JSONObject saveConfig(DaglConfig daglConfig){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			daglConfig = fileManageService.saveConfig(daglConfig);
			json.put("flag", "1");
			json.put("data", daglConfig);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "保存数据失败！");
		}
		return json;
	}

	/**
	 * TODO 获取扫码枪配置信息
	 * @author 李颖洁
	 * @date 2018年11月12日上午11:10:00
	 * @param receiveFile
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "获取扫码枪配置信息")
	@ResponseBody
	@RequestMapping(value="editConfig")
	public JSONObject editConfig(HttpServletRequest request){
		DaglConfig daglConfig = new DaglConfig();
		daglConfig.setId(getIpAddr(request));
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//根据id 获取文件信息
			daglConfig = fileManageService.getConfig(daglConfig);
			json.put("flag", "1");
			json.put("data", daglConfig);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		System.out.println("x-forwarded-for ip: " + ip);
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if( ip.indexOf(",")!=-1 ){
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println("Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println("WL-Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			System.out.println("HTTP_CLIENT_IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
			System.out.println("X-Real-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			System.out.println("getRemoteAddr ip: " + ip);
		}
		System.out.println("获取客户端ip: " + ip);
		return ip;
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
	 * @param receiveFile 
	 * @param updateType 
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "根据勾选的数据批量修改数据")
	@ResponseBody
	@RequestMapping(value="batchUpdateQueryData")
	public JSONObject batchUpdateQueryData(DaglReceiveFile receiveFile,String updateType){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			//根据id 获取文件信息
			List<DaglReceiveFile> list = fileManageService.getList(receiveFile);
			if(list.size()>0){
				String ids = "";
				for (DaglReceiveFile file : list) {
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

	/**
	 * TODO 根据查询条件批量修改数据
	 * @author 张浩磊
	 * @date 2018年12月18日下午4:55:39
	 */
	@LogAnnotation(value = "query",opName = "根据所在处室获取科室信息")
	@ResponseBody
	@RequestMapping(value="getKeshiList")
	public JSONArray getKeshiList(String deptId){
		JSONArray json = service.getDeptTreeByDeptId(deptId,1);
		for(int i = 0;i<json.size();i++){
			if(json.getJSONObject(i).get("uuid").equals(deptId)){
				json.remove(i);
			}
		}
		return json;
	}

	/**
	 * TODO 生成二维码
	 * @author 张浩磊
	 * @date 2018年12月18日下午4:55:39
	 */
	@LogAnnotation(value = "query",opName = "根据所在处室获取科室信息")
	@ResponseBody
	@RequestMapping(value="getCode")
	public void getCode(String content,HttpServletResponse response) {
		try {
			BarcodePDF417 pdf = new BarcodePDF417();
			pdf.setText(content.getBytes("GBK"));
			Image pdfImg = pdf.createAwtImage(Color.black, Color.white);
			BufferedImage img = new BufferedImage((int)pdfImg.getWidth(null), (int)pdfImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();

			g.drawImage(pdfImg, 0, 0, Color.white, null);
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			ImageIO.write(img, "PNG", os);
		}catch (Exception e){
			log.info(e.getMessage());
		}
	}
}
