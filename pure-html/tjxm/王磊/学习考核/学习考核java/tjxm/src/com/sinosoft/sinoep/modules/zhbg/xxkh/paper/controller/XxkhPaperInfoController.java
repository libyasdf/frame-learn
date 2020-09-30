package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.controller;

import org.springframework.util.StringUtils;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services.XxkhPaperInfoService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/zhbg/xxkh/xxkhPaperInfo")
public class XxkhPaperInfoController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private XxkhPaperInfoService service;
	
	@LogAnnotation(value = "saveOrUpdate", opName = "保存/修改一张试卷")
	@RequestMapping("save")
	@ResponseBody
	public JSONObject save(XxkhPaperInfo paperInfo) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		XxkhPaperInfo info=null;
		if(paperInfo.getId()==null||paperInfo.getId().equals("")) {
	    info = service.save(paperInfo);
		}else {
		info = service.update(paperInfo);
		}
		if (!StringUtils.isEmpty(info)) {
			json.put("flag", "1");
			json.put("data", info);
		}
		return json;
	}

	@LogAnnotation(value = "update", opName = "修改试卷分数")
	@RequestMapping("updataFenShu")
	@ResponseBody
	public JSONObject updataFenShu(XxkhPaperInfo paperInfo) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		XxkhPaperInfo info = service.updateFenShu(paperInfo);
		if (!StringUtils.isEmpty(info)) {
			json.put("flag", "1");
			json.put("data", info);
		}
		return json;
	}
	
	@LogAnnotation(value = "delete", opName = "删除试卷")
	@RequestMapping("delete")
	@ResponseBody
	public JSONObject delete(XxkhPaperInfo paperInfo) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		int info = service.delete(paperInfo);
		if (info>0) {
			json.put("flag", "1");
		}else {
			json.put("flag", "0");
		}
		return json;
	}
	
	@LogAnnotation(value = "isCanDelete", opName = "是否可以删除试卷")
	@RequestMapping("isCanDelete")
	@ResponseBody
	public JSONObject isCanDelete(XxkhPaperInfo paperInfo) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		List<XxkhTestInfo> canDelete = service.isCanDelete(paperInfo);
		if (canDelete.size()>0) {
			json.put("flag", "1");
		}else {
			json.put("flag", "0");
		}
		return json;
	}
	
	@LogAnnotation(value = "query", opName = "查询试卷列表")
	@RequestMapping("list")
	@ResponseBody
	public JSONObject list(XxkhPaperInfo paperInfo, PageImpl pageImpl) {
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		paperInfo.setCreJuId(UserUtil.getCruJuId());
		Page<XxkhPaperInfo> list = service.list(paperInfo, pageImpl);
		List<XxkhPaperInfo> content = list.getContent();
		for (XxkhPaperInfo info : content) {
			info.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE + ","
					+ CommonConstants.OPTION_VIEW);
			info.setGroup(null);
		}
		JSONObject data = new JSONObject();
		data.put("total", list.getTotalElements());
		data.put("rows", content);
		json.put("data", data);
		return json;
	}

	@LogAnnotation(value = "query", opName = "查询一张人工组卷试卷的基本信息")
	@RequestMapping("getById")
	@ResponseBody
	public JSONObject getById(XxkhPaperInfo paperInfo) {
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		XxkhPaperInfo one = service.getOne(paperInfo);
		json.put("data", one);
		return json;
	}
	
	@LogAnnotation(value = "query", opName = "查询一张自动组卷试卷的基本信息")
	@RequestMapping("getAutoById")
	@ResponseBody
	public JSONObject getAutoById(XxkhPaperInfo paperInfo) {
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		XxkhPaperInfo one = service.getAutoOne(paperInfo);
		json.put("data", one);
		return json;
	}

	@LogAnnotation(value = "update", opName = "是否能编辑试卷")
	@RequestMapping("isCanUpdate")
	@ResponseBody
	public JSONObject isCanUpdate(XxkhPaperInfo paperInfo) {
		JSONObject json = new JSONObject();
		int canUpdate = service.isCanUpdate(paperInfo.getId());
		if (canUpdate==2) {
			json.put("flag", 2);
		}else if(canUpdate==1) {
			json.put("flag", 1);
		}else {
			json.put("flag", 0);
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 考试后获取试卷信息，拼接了这个人这场考试这张试卷的主观题得分，客观题得分，人工评卷状态的字段
	 * @Date 2018/9/17 20:13
	 * @Param [paperInfo, testId, userId]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "query", opName = "考试后获取试卷信息，拼接了这个人这场考试这张试卷的主观题得分，客观题得分，人工评卷状态的字段")
	@RequestMapping("getTestResult")
	@ResponseBody
	public JSONObject getTestResult(XxkhPaperInfo paperInfo, String testId, String userId) {
		JSONObject json = new JSONObject();
		json.put("flag", "1");
		XxkhPaperInfo one = service.getTestResult(paperInfo, testId, userId);
		json.put("data", one);
		return json;
	}
}
