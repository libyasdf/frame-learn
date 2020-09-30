package com.sinosoft.sinoep.modules.system.config.honor.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.system.config.honor.entity.HonorDetails;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.config.honor.entity.Honor;
import com.sinosoft.sinoep.modules.system.config.honor.services.HonorService;
import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/honor")
public class HonorController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	HonorService honorService;

	@LogAnnotation(opType = OpType.QUERY, opName = "查询荣誉墙列表")
	@RequestMapping("getPageList")
	public PageImpl getPageList(PageImpl pageImpl,Honor honor){
		return honorService.getPageList(pageImpl,honor);
	}

	@LogAnnotation(opType = OpType.QUERY, opName = "查询荣誉墙")
	@RequestMapping("getlist")
	public List<Honor> getlist(Honor honor){
		return honorService.getList(honor);
	}

	@LogAnnotation(opType = OpType.QUERY, opName = "查询并保存")
	@RequestMapping("isCreate")
	public Honor isCreate(String year){
		Honor honor = honorService.findByVisibleAndYear(year);
		if(null == honor){ //不存在 可以添加
			return honorService.saveHonor(year);
		}else{
			return null;
		}
	}

	@LogAnnotation(opType = OpType.QUERY, opName = "查询详情")
	@RequestMapping("getListDetails")
	public List<HonorDetails> getListDetails(String honorId){
		List<HonorDetails> list = new ArrayList<HonorDetails>();
		if(StringUtils.isNotBlank(honorId)){
			return honorService.findByHonor_Id(honorId);
		}
		return list;
	}

	@LogAnnotation(opType = OpType.QUERY, opName = "查询详情")
	@RequestMapping("findByYear")
	public List<HonorDetails> findByYear(String year){
		Honor honor = honorService.findByVisibleAndYear(year);
		if(null != honor){
			List<HonorDetails> list = honorService.findByHonor_Id(honor.getId());
			for(HonorDetails honorDetails : list){
				if(StringUtils.isNotBlank(honorDetails.getHonorStory())){
					String[] arr = honorDetails.getHonorStory().split("\n");
					StringBuffer sb = new StringBuffer();
					for(int i=0; i<arr.length; i++){
						sb.append("<p class=&quot;honor-con&quot;>"+ arr[i] +"</p>");
					}
					honorDetails.setHonorStory(sb.toString());
				}
			}
			return list;
		}
		return null;
	}

	@LogAnnotation(opType = OpType.UPDATE,opName = "编辑列表")
	@RequestMapping("editHonorDetails")
	public JSONObject editHonorDetails(@RequestBody HonorDetails honorDetails) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			honorService.editHonorDetails(honorDetails);
			json.put("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 删除
	 * TODO
	 * @author 马秋霞
	 * @Date 2018年5月31日 下午1:30:56
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.DELETE,opName = "删除")
	@RequestMapping("delete")
	public JSONObject delete(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = honorService.delete(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

	@LogAnnotation(opType = OpType.DELETE,opName = "删除荣誉人")
	@RequestMapping("deleteDetails")
	public JSONObject deleteDetails(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = honorService.deleteDetails(id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

	@LogAnnotation(opType = OpType.UPDATE,opName = "修改序号")
	@RequestMapping(value = "/changeNumber")
	public JSONObject changeNumber(String id,Integer number,String exchangeId,Integer exchangeNumber) {
		JSONObject json = new JSONObject();
		try {
			honorService.changeNumber(id, number,exchangeId,exchangeNumber);
			json.put("flag", 1);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		return json;
	}

	@LogAnnotation(opType = OpType.UPDATE,opName = "更新发布状态")
	@RequestMapping("updatePublish")
	public JSONObject updatePublish(String publish,String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = honorService.updatePublish(publish,id);
			if (result != 0) {
				json.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}


}
