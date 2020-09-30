package com.sinosoft.sinoep.message.services;

import java.util.List;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.message.model.NotityMessage;

import net.sf.json.JSONObject;

/**
 * TODO 消息客户端接口
 * @author 李利广
 * @Date 2018年8月23日 上午11:51:28
 */
public interface MessageService {
	
	/**
	 * TODO 查询某用户某一时间段内的所有消息，日期可空
	 * @author 李利广
	 * @Date 2018年8月23日 上午11:54:31
	 * @param startDate	yyyy-MM-dd
	 * @param endDate yyyy-MM-dd
	 * @param userId
	 * @return
	 */
	List<NotityMessage> getMsgByDate(String startDate, String endDate, String userId);
	
	/**
	 * TODO 分页查询某用户一段时间内的所有消息
	 * @author 李利广
	 * @Date 2018年8月23日 上午11:57:37
	 * @param page
	 * @param startDate
	 * @param endDate
	 * @param content
	 * @param userId
	 * @return
	 */
	PageImpl getMsgByDateAndContent(PageImpl page,String startDate, String endDate, String content, String userId);
	
	/**
	 * TODO 将某用户一段时间内的未读消息置为已读状态
	 * @author 李利广
	 * @Date 2018年8月23日 下午12:00:56
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 */
	Boolean updateStatusByDate(String startDate, String endDate, String userId);
	
	/**
	 * TODO 查询当天该用户所有的消息
	 * @author 李利广
	 * @Date 2018年8月23日 下午12:03:16
	 * @param userId
	 * @return
	 */
	List<NotityMessage> getTodayMsg(String userId);
	
	JSONObject historyCount(String startDate, String endDate, String userId);

	/**
	 * 
	 * @param impl
	 * @param status
	 * @param userId
	 * @return
	 * @author 颜振兴
	 * PageImpl
	 * TODO
	 */
	PageImpl getIsRead(PageImpl impl,String status,String userId);
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 总未读消息数
	 */
	JSONObject sumTotal(String userId);
}
