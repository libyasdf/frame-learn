package com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.entity.XxkhQuestionQgroup;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.services.XxkhQuestionQgroupService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.services.DataDictionarysService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/zhbg/xxkh/questionandgroup")
public class XxkhQuestionQgroupController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private XxkhQuestionQgroupService service;
	
	@Autowired
	private DataDictionarysService dataDictionarysService;
	
	@RequestMapping("list")
	@ResponseBody
	public PageImpl list(PageImpl pageImpl,XxkhQuestionQgroup xxkhQuestionQgroup) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
		PageImpl list = service.list(pageImpl, xxkhQuestionQgroup, pageable);
		List<Question> rows = (List<Question>) list.getData().getRows();
		for(Question question:rows) {
			question.setCz(CommonConstants.OPTION_DELETE);
			List<DataDictionarys> pname = dataDictionarysService.getPname(question.getNodeId());
			StringBuffer name = new StringBuffer();
			for(DataDictionarys dictionarys:pname) {
				name.append(dictionarys.getName()+"-");
			}
			question.setNodeId(name.toString().substring(0, name.length()-1));
		}
		list.setFlag("1");
		return list;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public JSONObject save(XxkhQuestionQgroup xxkhQuestionQgroup) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		XxkhQuestionQgroup questionQgroup = service.save(xxkhQuestionQgroup);
		if (questionQgroup!=null&&!questionQgroup.equals("")) {
			json.put("flag", "1");
			json.put("data", questionQgroup);
		}
		return json;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public JSONObject delete(XxkhQuestionQgroup xxkhQuestionQgroup) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		int i = service.delete(xxkhQuestionQgroup);
		if (i>0) {
			json.put("flag", "1");
		}
		return json;
	}
	
	@RequestMapping("deleteti")
	@ResponseBody
	public JSONObject deleteti(XxkhQuestionQgroup xxkhQuestionQgroup) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		int i = service.deleteti(xxkhQuestionQgroup);
		if (i>0) {
			json.put("flag", "1");
		}
		return json;
	}
}
