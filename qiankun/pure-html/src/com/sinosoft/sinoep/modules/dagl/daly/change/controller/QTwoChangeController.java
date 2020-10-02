package com.sinosoft.sinoep.modules.dagl.daly.change.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.daly.change.entity.QTwoChange;
import com.sinosoft.sinoep.modules.dagl.daly.change.services.QTwoChangeService;
import com.sinosoft.sinoep.modules.dagl.xtpz.categorymanage.services.CategoryManageService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.services.QuestionService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.entity.XxkhQuestionQgroup;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.services.XxkhQuestionQgroupService;
import net.sf.json.JSONObject;

/**
 * Q2变更控制类
 * TODO 
 * @author 王磊
 * @Date 2018年11月24日 上午10:26:55
 */
@Controller
@RequestMapping("dagl/qtwo")
public class QTwoChangeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QTwoChangeService qTwoChangeService;
	
	@Autowired
	private CategoryManageService categoryManageService;

	/**
	 * 查询变更列表
	 * @author 王磊
	 * @Date 2018年11月24日 上午10:34:41
	 * @param pageImpl
	 * @param question
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询更新列表")
	@ResponseBody
	@RequestMapping("getList")
	public PageImpl getList(PageImpl pageImpl, String timeRange,String changeNo,String folderNo) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		PageImpl pageList = qTwoChangeService.getPageList(pageable, pageImpl, timeRange, changeNo, folderNo);
		return pageList;
	}
	
	
	/**
	 * 修改记录并保存
	 * @author 王磊
	 * @Date 2018年11月24日 上午10:35:55
	 * @param question
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存主管人、主管单位变更记录")
	@RequestMapping("saveChange")
	@ResponseBody
	public JSONObject saveChange(@RequestBody QTwoChange qTwoChange) {
		JSONObject json = new JSONObject();
		try {
			qTwoChangeService.saveForm(qTwoChange);
			int updateForm = qTwoChangeService.updateForm(qTwoChange);
			log.info("修改条数"+updateForm);
			json.put("flag", 1);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}
	
	/**
	 * 根据变更pid获得一次变更记录
	 * @author 王磊
	 * @Date 2018年11月24日 上午10:36:42
	 * @param pid
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "根据pid获得本次变更记录")
	@ResponseBody
	@RequestMapping(value = "findByPid",produces = "application/json;charset=utf-8")
	public JSONObject findByPid (String pid) {
		JSONObject json = new JSONObject();
		QTwoChange qTwoChange = new QTwoChange();
		try {
			qTwoChange = qTwoChangeService.findByPid(pid);
			json.put("flag", "1");
			json.put("data",qTwoChange);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	@LogAnnotation(value = "query",opName = "通过表名和id查数据")
	@ResponseBody
	@RequestMapping(value="findListByIds",produces = "application/json;charset=utf-8")
	public String findListByIds(String ids,String tName) {
		List<Map<String,Object>> findListByIds = qTwoChangeService.findListByIds(ids, tName);
		String jsonString = com.alibaba.fastjson.JSONObject.toJSONString(findListByIds, SerializerFeature.WriteMapNullValue);
		return jsonString;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 查询变更数量生成流水号
	 * @Date 2019/1/31 17:04
	 * @Param [pid]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "query",opName = "查询变更数量生成流水号")
	@ResponseBody
	@RequestMapping("findChangeConut")
	public JSONObject findChangeConut (String pid) {
		JSONObject json = new JSONObject();
		try {
			String changeNo = qTwoChangeService.findChangeConut();
			json.put("flag", "1");
			json.put("changeNo",changeNo);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 查询q2的授权立卷单位，Q2变更用
	 * @author 王磊
	 * @Date 2019年4月23日 下午8:41:18
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询q2的授权立卷单位")
	@ResponseBody
	@RequestMapping(value="getQtwoLicenceUnit",produces = "application/json;charset=utf-8")
	public JSONObject getQtwoLicenceUnit () {
		JSONObject json = new JSONObject();
		try {
			json.put("data", categoryManageService.getLiJuanDanWeiForQtow());
			json.put("flag", "1");
		} catch (Exception e) {
			log.info("查询q2的授权立卷单位异常：");
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
	}
}
