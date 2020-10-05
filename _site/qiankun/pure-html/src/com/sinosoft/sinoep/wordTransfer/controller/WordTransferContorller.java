package com.sinosoft.sinoep.wordTransfer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.sinosoft.sinoep.wordTransfer.services.WordTransferService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/wordTransfer")
public class WordTransferContorller {

	@Autowired
	private WordTransferService wordTransferService;

	@RequestMapping(value="/AffixFile/{affixId}/pdf",method = RequestMethod.GET)
	public void getAffixFilePdf(@PathVariable("affixId")String affixId,HttpServletResponse response) throws IOException{
		WebUtils.responsePdf(response, wordTransferService.wordTransAffixFilePdf(affixId));
	}

	/**
	 * 放回pdf文件路径
	 * @param affixId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/AffixFilePath/{affixId}/pdf",method = RequestMethod.GET)
	public String getAffixFilePath(@PathVariable("affixId")String affixId) throws IOException{
		String filePath = wordTransferService.wordTransAffixFilePathPdf(affixId);
		return filePath;
	}
	/**
	 * ajax获取返回pdf文件路径
	 * @param affixId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="filePath/{affixId}/pdf",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getFilePath(@PathVariable("affixId")String affixId) throws IOException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag","0");
		if(StringUtils.isNotEmpty(affixId)) {
			String filePath = wordTransferService.wordTransAffixFilePathPdf(affixId);
			if (StringUtils.isNotEmpty(filePath)) {
				System.out.println(filePath);
				jsonObject.put("filePath", filePath);
				jsonObject.put("flag", "1");
			}
		}
		return jsonObject;
	}
}
