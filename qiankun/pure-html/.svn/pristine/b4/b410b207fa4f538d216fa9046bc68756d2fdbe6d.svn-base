package com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.services;

import java.util.List;
import com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.entity.Seat;
import com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.entity.UserSeat;
import net.sf.json.JSONObject;

/**
 * 排座service
 * TODO 
 * @author 马秋霞
 * @Date 2018年9月12日 下午3:01:46
 */
public interface SeatService {
	
	/**
	 * 获取某次会议的所有座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月12日 下午3:22:56
	 * @param meetingApplyId
	 * @return
	 */
	List<List<Seat>> getList(String meetingNoticeId,String attendDeptId);
	
	/**
	 * 重置所有座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 上午10:31:12
	 * @param meetingNoticeId
	 * @return
	 */
	void deleteByNoticeId(String flag,String meetingNoticeId,String seatListJson);
	
	/**
	 * 保存所有的预留座位
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 下午2:28:29
	 * @param seatList
	 */
	void saveAll(String meetingNoticeId,String seatListJson,Integer state);
	
	/**
	 * 取消某些座位的预留
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 下午2:26:55
	 * @param seatList
	 * @return
	 */
	void cancelReserve(String meetingNoticeId,String seatListJson);
	
	/**
	 * 自动排座
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 下午5:25:39
	 * @param meetingNoticeId
	 */
	void autoArrangeSeat(String meetingNoticeId,String fankuiType);
	
	/**
	 * 获取此次会议通知的所有参会人员
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月30日 上午10:32:54
	 * @param meetingNoticeId
	 * @return
	 */
	List<UserSeat> getAllAttendees(String meetingNoticeId,String isJu,String isChuZhang);
	
	/**
	 * 手动排座保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月10日 下午2:26:43
	 * @param seatDataJson
	 */
	List<Seat> manualSeatSave(String meetingNoticeId,String seatDataJson,String fankuiType);
	
	/**
	 * 发布
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年10月18日 下午5:35:42
	 * @param meetingNoticeId
	 * @return
	 */
	void publish(String meetingNoticeId,String fankuiType);
	
	/**
	 * 座位票列表
	 */
	JSONObject ticketList(String meetingNoticeId,String attendDeptType,String attentionItem,String attendDeptId,String userid);
	
	/**
	 * 待办中座位图的数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月14日 上午10:30:24
	 * @param meetingNoticeId
	 * @return
	 */
	List<List<Seat>> getList1(String meetingNoticeId, String attendDeptId, String userid);

	/** 
	 * TODO 删除收发人员的座位图待办
	 * @author 李颖洁  
	 * @date 2019年4月3日下午3:48:24
	 * @param sourceId
	 * @param receiveDeptId
	 * @return int
	 */
	int deleteWaitNoflow(String sourceId, String receiveDeptId,String isJu);

}
