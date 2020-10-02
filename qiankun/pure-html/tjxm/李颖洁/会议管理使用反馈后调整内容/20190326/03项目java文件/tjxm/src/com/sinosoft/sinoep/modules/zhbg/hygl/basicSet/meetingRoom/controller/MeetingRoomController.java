package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.entity.MeetingRoomInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.service.MeetingRoomInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 会议室管理
 * TODO 
 * @author 冯超
 * @Date 2018年7月19日 下午6:17:17
 */
 
@Controller
@RequestMapping("zhbg/hygl/basicSet/meetingRoom")
public class MeetingRoomController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MeetingRoomInfoService meetingRoomInfoService;
	
	/**
	 * 
	 * TODO 修改会议室
	 * @author 冯超
	 * @Date 2018年5月7日 下午1:59:55
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改会议室信息")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		MeetingRoomInfo meetingRoomInfo = null;
		try {
			meetingRoomInfo = meetingRoomInfoService.getById(id);
			json.put("flag", "1");
			json.put("data", meetingRoomInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改会议室信息")
	@ResponseBody
	@RequestMapping("getByDoorNum")
	public JSONObject getByDoorNum( String doorNumber){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		List<MeetingRoomInfo> mtInfoList = null;
		try {
			mtInfoList = meetingRoomInfoService.getByDoorNum(doorNumber);
			json.put("flag", "1");
			if(mtInfoList!=null && mtInfoList.size()>0 && mtInfoList.get(0).getDoorNumber()!=null){
				json.put("data", "1");
			}else{
				json.put("data", "0");
			}
		} catch (Exception e) {
			json.put("data", "-1");
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 
	 * TODO 保存会议室
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:14:18
	 * @param application
	 * @return
	 */
	@LogAnnotation(opType = OpType.SAVE,opName = "保存会议室信息")
	@ResponseBody
	@RequestMapping("saveForm")
	public MeetingRoomInfo saveForm(MeetingRoomInfo meetingRoomInfo){
		try {
			meetingRoomInfo = meetingRoomInfoService.saveForm(meetingRoomInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return meetingRoomInfo;
	}
	
	/**
	 * 
	 * TODO 获取会议室列表(分页)
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:33:40
	 * @param pageImpl
	 * @param name
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "获取会议室列表")
	@ResponseBody
	@RequestMapping("getPageList")
	public PageImpl getPageList(PageImpl pageImpl,String name){
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = meetingRoomInfoService.getPageList(pageable,pageImpl,name);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
	/**
	 * 
	 * TODO 获取会议室列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:27:00
	 * @param application
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "查询会议室信息列表")
	@RequestMapping("getList")
	@ResponseBody
	public JSONObject getList(MeetingRoomInfo meetingRoomInfo) {
		JSONObject json = new JSONObject();
		try {
			List<MeetingRoomInfo> list = meetingRoomInfoService.getList(meetingRoomInfo);
			json.put("flag", "1");
			JSONObject data = new JSONObject();
			data.put("total", list.size());
			JSONArray array = new JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
					"updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
			array = JSONArray.fromObject(list, jsonConfig);
			data.put("rows", array);
			json.put("data", data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.put("flag", 0);
		}
		log.info(json.toString());
		return json;
	}
	
	/**
	 * 
	 * TODO 删除会议室
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:54:10
	 * @param id
	 * @return
	 */
	@LogAnnotation(opType = OpType.DELETE,opName = "删除会议室信息")
	@ResponseBody
	@RequestMapping("delete")
	public JSONObject delete( String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int result = meetingRoomInfoService.delete(id);
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
	 * 
	 * TODO 会议室排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:16:19
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sort", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject sort(String[] id){
		JSONObject json = new JSONObject();
		json.put("flag","0");
		try {
			meetingRoomInfoService.sort(id);
			json.put("flag","1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 判断服务单位是否重复
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月3日 下午2:24:44
	 * @param serviceDeptInfo
	 * @return
	 */
	@RequestMapping(value = "check", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject check(MeetingRoomInfo meetingRoomInfo){
		JSONObject json = new JSONObject();
		boolean valid = meetingRoomInfoService.checkSdi(meetingRoomInfo);
		json.put("valid",valid);
		return json;
	}
	
}
