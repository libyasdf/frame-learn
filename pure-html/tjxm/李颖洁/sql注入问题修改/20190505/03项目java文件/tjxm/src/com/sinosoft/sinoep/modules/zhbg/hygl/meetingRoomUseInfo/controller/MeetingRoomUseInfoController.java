package com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.entity.MeetingRoomUseInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.entity.MeetingRoomUseState;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.services.MeetingRoomUseInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会议室的使用情况
 * TODO 
 * @author 马秋霞
 * @Date 2019年3月5日 下午1:53:40
 */
@Controller
@RequestMapping("zhbg/hygl/meetingRoomUseInfo")
public class MeetingRoomUseInfoController {
	
	 @Autowired
	 private MeetingRoomUseInfoService service;
	 
	/**
	 * 获取某天可用会议室的使用情况
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月5日 下午2:56:14
	 * @param pageImpl
	 * @param superId
	 * @return
	 */
    @LogAnnotation(opType = OpType.QUERY,opName = "获取某天可用会议室的使用情况")
    @RequestMapping(value = "getMeetingRoomUseList")
    @ResponseBody
    public JSONObject getMeetingRoomUseList(PageImpl pageImpl,String date){
    	JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<MeetingRoomUseState>list=new ArrayList<MeetingRoomUseState>();
		try {
			  list = service.getMeetingRoomUseList(date);
			JSONObject data = new JSONObject();
			data.put("total",list.size());
			JSONArray array = new JSONArray();
			array = JSONArray.fromObject(list);
			data.put("rows", array);
			json.put("data",data);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		return json;
    }
    
    /**
     * 保存会议室申请数据
     * TODO 
     * @author 马秋霞
     * @Date 2019年3月8日 下午3:45:59
     * @param pageImpl
     * @param date
     * @return
     */
    @LogAnnotation(opType = OpType.SAVE,opName = "保存会议室申请")
    @RequestMapping(value = "saveAll")
    @ResponseBody
    public JSONObject saveAll(String data){
    	JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<MeetingRoomUseInfo> list=new ArrayList<MeetingRoomUseInfo>();
		try {
			list = service.saveAll(data);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		json.put("data", list);
		return json;
    }
    
    /**
     * 根据id删除一条记录
     * TODO 
     * @author 马秋霞
     * @Date 2019年3月8日 下午6:17:21
     * @param id
     * @return
     */
    @LogAnnotation(opType = OpType.DELETE,opName = "根据id删除一条会议室申请信息")
    @RequestMapping(value = "deletById")
    @ResponseBody
    public JSONObject deletById(String id){
    	JSONObject json = new JSONObject();
		json.put("flag", "1");
		try {
			service.deletById(id);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		
		return json;
    }
    
    /**
     * 检查是否有会议室被占用
     * TODO 
     * @author 马秋霞
     * @Date 2019年3月9日 上午10:55:22
     * @param id
     * @return
     */
    @LogAnnotation(opType = OpType.QUERY,opName = "检查是否有会议室被占用")
    @RequestMapping(value = "checkMeetingRoom")
    @ResponseBody
    public JSONObject checkMeetingRoom(String data){
    	JSONObject json = new JSONObject();
		try {
			json = service.checkMeetingRoom(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
    }
    
    /**
     * 根据会议申请id获取所有的会议室设置信息
     * TODO 
     * @author 马秋霞
     * @Date 2019年3月11日 上午9:35:54
     * @param applyId
     * @return
     */
    @LogAnnotation(opType = OpType.QUERY,opName = "根据会议申请id获取所有的会议室设置信息")
    @RequestMapping(value = "getByApplyId")
    @ResponseBody
    public JSONObject getByApplyId(String applyId){
    	JSONObject json = new JSONObject();
		json.put("flag", "1");
		List<MeetingRoomUseInfo> list = new ArrayList<MeetingRoomUseInfo>();
		try {
			list = service.getByApplyId(applyId);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		json.put("data", list);
		return json;
    }
}
