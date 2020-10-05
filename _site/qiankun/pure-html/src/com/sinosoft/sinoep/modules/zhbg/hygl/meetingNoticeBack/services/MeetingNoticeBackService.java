package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.CountNoticeBack;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNoticeBack.entity.MeetingNoticeBack;

public interface MeetingNoticeBackService {

	MeetingNoticeBack updateSubFlag(String id, String subflag);

	int delete(String id);

	MeetingNoticeBack getById(String id);

	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String meetingName, String status);

	MeetingNoticeBack saveForm(MeetingNoticeBack meetingNoticeBack);

	MeetingNoticeBack getById1(String id);

	PageImpl getPageListDraft1(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String meetingName, String status);

	PageImpl getBackedPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate);

	PageImpl getunBackedPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String meetingName, String status, String id);
	
	/**
	 * 根据会议通知id获取该会议通知对应的参会反馈信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月13日 上午10:02:11
	 * @param meetingNoticeId
	 * @return
	 */
	Map<String,List<MeetingNoticeBack>> findByNoticeId(String meetingNoticeId,String isJu,String isChuZhang,Map<String,String> attendDeptMap);

	CountNoticeBack getBackCount(String id);

	/**
	 * TODO 根据会议申请id查询参会人员ids
	 * @author 李颖洁  
	 * @date 2020年1月1日上午3:45:33
	 * @param applyId 会议申请id
	 * @return String
	 */
	String getAllAttendPersonIds(String applyId);
}
