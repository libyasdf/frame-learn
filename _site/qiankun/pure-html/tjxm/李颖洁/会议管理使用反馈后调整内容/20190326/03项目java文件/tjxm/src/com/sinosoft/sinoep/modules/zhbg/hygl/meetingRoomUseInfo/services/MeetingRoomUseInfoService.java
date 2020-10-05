package com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.services;

import java.util.List;

import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.entity.MeetingRoomUseInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingRoomUseInfo.entity.MeetingRoomUseState;

import net.sf.json.JSONObject;

/**
 * 会议室的使用情况
 * TODO 
 * @author 马秋霞
 * @Date 2019年3月5日 下午1:51:27
 */
public interface MeetingRoomUseInfoService {
	
	/**
	 * 获取某天所有会议室的使用情况
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月5日 下午2:59:55
	 * @param date
	 * @return
	 */
	List<MeetingRoomUseState> getMeetingRoomUseList(String date);
	
	/**
	 * 保存所有的会议室申请数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月8日 下午3:49:46
	 * @param data
	 * @return
	 */
	List<MeetingRoomUseInfo> saveAll(String data);
	
	/**
	 * 根据id删除一条记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月9日 上午9:54:50
	 * @param id
	 */
	void deletById(String id);

	JSONObject checkMeetingRoom(String data);
	
	/**
	 * 根据会议申请id获取所有的会议室设置信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年3月11日 上午9:41:52
	 * @param applyId
	 */
	List<MeetingRoomUseInfo> getByApplyId(String applyId);

}
