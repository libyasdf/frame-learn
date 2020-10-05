package com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.entity.MeetingServiceNotice;

import net.sf.json.JSONObject;

public interface MeetingServiceNoticeService {
	
	/**
	 * 保存方法
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:05:14
	 * @param meetingServiceNotice
	 * @return
	 * @throws Exception
	 */
	MeetingServiceNotice saveForm(MeetingServiceNotice meetingServiceNotice) throws Exception;

	/**
	 * 修改
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:05:31
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int delete(String id) throws Exception;

	/**
	 * 获取数据
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:07:11
	 * @param id
	 * @return
	 * @throws Exception
	 */
	MeetingServiceNotice getById(String id) throws Exception;

	/**
	 * 更新业务表流程状态
	 * TODO 
	 * @author 冯超
	 * @Date 2018年3月15日 下午8:48:54
	 * @param id
	 * @param subflag
	 * @return
	 */
	MeetingServiceNotice updateSubFlag(String id, String subflag) throws Exception;

	/**
	 * 查询草稿箱列表
	 * TODO 
	 * @author 冯超
	 * @Date 2018年3月15日 下午8:49:36
	 * @param pageable
	 * @param userId
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl,String meetingRoom,String meetingName,String startDate,
			String endDate,String meetingServices,String subflag) throws Exception;

	List<MeetingServiceNotice> getByMeetingApplyId(MeetingServiceNotice meetingServiceNotice);

	List<MeetingServiceNotice> saveForm(List<MeetingServiceNotice> meetingServiceNotices) throws Exception;

	PageImpl getPageBackList(Pageable pageable, PageImpl pageImpl, String title, String startDate, String endDate,
			String subflag,String meetingRoom,String meetingName,String meetingServices) throws Exception;

	PageImpl getAllList(Pageable pageable, PageImpl pageImpl, String meetingroomApplyId, String status);

	List<MeetingServiceNotice> fankuiForm(List<MeetingServiceNotice> meetingServiceNotices) throws Exception;

	JSONObject queryMessageId(NotityMessage notityMessage);
	
	/**
	 * TODO 根据会议申请id查询会务服务负责人ids
	 * @author 李颖洁  
	 * @date 2020年1月1日上午3:46:35
	 * @param applyId 会议申请id
	 * @return String
	 */
	String getResponseUserIds(String applyId);
}
