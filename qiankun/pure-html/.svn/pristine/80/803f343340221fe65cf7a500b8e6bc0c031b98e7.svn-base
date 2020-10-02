package com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.entity.Seat;
import com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.entity.UserSeat;
import com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.entity.UserSeatData;
import com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.services.SeatService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.constant.ZBGLCommonConstants;

import net.sf.json.JSONObject;



/**
 * 排座controller
 * TODO 
 * @author 马秋霞
 * @Date 2018年9月12日 下午3:01:26
 */
@Controller
@RequestMapping("/zhbg/hygl/arrangeSeat")
public class SeatController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SeatService service;
	
	
	/**
	 * 排班页面的数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 上午10:30:24
	 * @param meetingNoticeId
	 * @return
	 */
	 @LogAnnotation(opType = OpType.QUERY,opName = "排座页面的数据")
	@ResponseBody
	@RequestMapping("getList")
	public List<List<Seat>> getList(String meetingNoticeId,String attendDeptId){
		List<List<Seat>> list = new ArrayList<List<Seat>>();
		try {
			list = service.getList(meetingNoticeId,attendDeptId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	
	/**
	 * 待办中座位图的数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 上午10:30:24
	 * @param meetingNoticeId
	 * @return
	 */
	 @LogAnnotation(opType = OpType.QUERY,opName = "获取代办中座位图的数据")
	@ResponseBody
	@RequestMapping("getList1")
	public List<List<Seat>> getList1(String meetingNoticeId,String attendDeptId,String userid){
		List<List<Seat>> list = new ArrayList<List<Seat>>();
		try {
			list = service.getList1(meetingNoticeId,attendDeptId,userid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	/**
	 * 重置所有(某些)座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 上午10:31:12
	 * @param meetingNoticeId
	 * @return
	 */
	 @LogAnnotation(opType = OpType.DELETE,opName = "重置所有(某些)座位")
	@RequestMapping("reset")
	@ResponseBody
	public JSONObject reset(String flag,String meetingNoticeId,String seatListJson){
		JSONObject jsonObj = new JSONObject();
		String flg = "1";
		try {
			service.deleteByNoticeId(flag,meetingNoticeId,seatListJson);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flg="0";
		}
		jsonObj.put("flag", flg);
		return jsonObj;
	}
	
	/**
	 * 设置预留座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 下午2:26:55
	 * @param seatList
	 * @return
	 */
	 @LogAnnotation(opType = OpType.SAVE,opName = "设置预留座位")
	@RequestMapping("reserve")
	@ResponseBody
	public JSONObject reserve(String meetingNoticeId,String seatListJson,Integer state){
		JSONObject jsonObj = new JSONObject();
		String flag = "1";
		try {
			service.saveAll(meetingNoticeId,seatListJson,state);
		} catch (Exception e) {
			flag="0";
			e.printStackTrace();
		}
		jsonObj.put("flag", flag);
		return jsonObj;
	}
	
	/**
	 * 取消某些座位的预留
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 下午2:26:55
	 * @param seatList
	 * @return
	 */
	 @LogAnnotation(opType = OpType.DELETE,opName = "取消某些座位的预留")
	@RequestMapping("cancelReserve")
	@ResponseBody
	public JSONObject cancelReserve(String meetingNoticeId,String seatListJson){
		JSONObject jsonObj = new JSONObject();
		String flag = "1";
		try {
			service.cancelReserve(meetingNoticeId,seatListJson);
		} catch (Exception e) {
			flag="0";
			e.printStackTrace();
		}
		jsonObj.put("flag", flag);
		return jsonObj;
	}
	
	/**
	 * 自动排座
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 下午5:24:33
	 * @param meetingNoticeId
	 * @param seatList
	 * @return
	 */
	 @LogAnnotation(opType = OpType.SAVE,opName = "自动排座")
	@RequestMapping("autoArrangeSeat")
	@ResponseBody
	public JSONObject autoArrangeSeat(String meetingNoticeId,String fankuiType){
		JSONObject jsonObj = new JSONObject();
		String flag = "1";
		try {
			service.autoArrangeSeat(meetingNoticeId,fankuiType);
		} catch (Exception e) {
			flag="0";
			e.printStackTrace();
		}
		jsonObj.put("flag", flag);
		return jsonObj;
	}
	

	/**
	 * 获取可手动排座的参会人员信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月9日 下午3:17:56
	 * @param meetingNoticeId
	 * @param isJu 0表示未被选中，即不查询部门领导信息；"1"表示被选中
	 * @param isChuZhang
	 * @return
	 */
	 @LogAnnotation(opType = OpType.QUERY,opName = "获取可手动排座的参会人员信息")
	@RequestMapping("getAllAttendees")
	@ResponseBody
	public List<UserSeat> getAllAttendees(String meetingNoticeId,String isJu,String isChuZhang){
		return service.getAllAttendees(meetingNoticeId,isJu,isChuZhang);
	}
	
	/**
	 * 手动排座保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月10日 下午2:25:30
	 * @param seatData
	 * @return
	 */
	 @LogAnnotation(opType = OpType.SAVE,opName = "手动排座保存")
	@RequestMapping("manualSeatSave")
	@ResponseBody
	public JSONObject manualSeatSave(@RequestBody UserSeatData data ){
		JSONObject jsonObj = new JSONObject();
		String flag = "1";
		List<Seat> list = new ArrayList<Seat>();;
		try {
			list=service.manualSeatSave(data.getMeetingNoticeId(),JSON.toJSONString(data.getSeatDataJson()),data.getFankuiType());
		} catch (Exception e) {
			flag="0";
			e.printStackTrace();
		}
		jsonObj.put("flag", flag);
		jsonObj.put("data", list);
		return jsonObj;
	}
	
	/**
	 * 发布
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月18日 下午5:34:51
	 * @param meetingNoticeId
	 * @param seatDataJson
	 * @return
	 */
	 @LogAnnotation(opType = OpType.UPDATE,opName = "发布")
	@RequestMapping("publish")
	@ResponseBody
	public JSONObject publish(String meetingNoticeId,String fankuiType ){
		JSONObject jsonObj = new JSONObject();
		String flag = "1";
		
		try {
			service.publish(meetingNoticeId,fankuiType);
		} catch (Exception e) {
			flag="0";
			e.printStackTrace();
		}
		
		jsonObj.put("flag", flag);
		
		return jsonObj;
	}
	
	/**
	 * 座位票列表
	 */
	 @LogAnnotation(opType = OpType.QUERY,opName = "座位票列表")
	@ResponseBody
	@RequestMapping("ticketList")
	public JSONObject ticketList(String meetingNoticeId,String attendDeptType,String attentionItem,String attendDeptId,String userid){
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj = service.ticketList(meetingNoticeId,attendDeptType,attentionItem,attendDeptId,userid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jsonObj;
	}
	 
	 	/**
		 * TODO 根据角色删除待办
		 * @author 李颖洁  
		 * @date 2019年4月3日下午3:42:19
		 * @param inceptId
		 * @param receiveDeptId
		 * @return JSONObject
		 */
		@LogAnnotation(opType = OpType.DELETE,opName = "删除收发人员的座位图待办")
	    @RequestMapping("deleteWaitNoflow")
	    @ResponseBody
	    public JSONObject deleteWaitNoflow(String sourceId,String receiveDeptId,String isJu){
	        JSONObject json = new JSONObject();
	        json.put("flag","0");
	        int num =service.deleteWaitNoflow(sourceId,receiveDeptId,isJu);
	        if(num>0){
	        	json.put("flag", "1");
	        	json.put("msg", "删除待办成功！");
	        }
	        return json;
	    }
}
