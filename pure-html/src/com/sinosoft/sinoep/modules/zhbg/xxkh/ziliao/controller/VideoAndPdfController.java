package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity.VideoAndPdf;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.services.VideoAndPdfService;
import net.sf.json.JSONObject;

/**
 * 资料对应文件的控制层
 * @author 王磊
 * @Date 2018年8月21日 下午5:43:13
 */
@Controller
@RequestMapping("zhbg/xxkh/videoAndPdf")
public class VideoAndPdfController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VideoAndPdfService videoAndPdfService;
	
	/**
	 * 保存资料对应的文件
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:29:57
	 * @param videoAndPdf
	 * @return
	 */
	//@SameUrlData
	@LogAnnotation(value = "save",opName = "保存资料对应的文件")
	@RequestMapping("saveFile")
	@ResponseBody
	public JSONObject saveFile(@RequestBody VideoAndPdf videoAndPdf) {
		JSONObject json = new JSONObject();
		try {
			json.put("flag", 1);
			json.put("file", videoAndPdfService.saveForm(videoAndPdf));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}
	
	/**
	 * 根据文件id删除文件
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:33:15
	 * @param questionInfo
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "根据id删除文件")
	@ResponseBody
	@RequestMapping("deleteById")
	public JSONObject deleteById(String fileId) {
		JSONObject json = new JSONObject();
		try {
			int n = videoAndPdfService.delete(fileId);
			json.put("flag", ""+n+"");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * 根据资料id查询所有文件
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:40:34
	 * @param infoId
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "根据infoId查询文件")
	@ResponseBody
	@RequestMapping("getByInfoId")
	public JSONObject getByIdAndType(String infoId) {
		JSONObject json = new JSONObject();
		try {
			json.put("fileList", videoAndPdfService.getByInfoId(infoId));
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
}
