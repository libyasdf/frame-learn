package com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyDetailInfo;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyInfo;

public interface MeetingApplyService {
	
	/**
	 * 保存方法
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 上午11:05:14
	 * @param meetingApplyInfo
	 * @return
	 * @throws Exception
	 */
	MeetingApplyInfo saveForm(MeetingApplyInfo meetingApplyInfo) throws Exception;

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
	MeetingApplyInfo getById(String id) throws Exception;

	/**
	 * 更新业务表流程状态
	 * TODO 
	 * @author 冯超
	 * @Date 2018年3月15日 下午8:48:54
	 * @param id
	 * @param subflag
	 * @return
	 */
	MeetingApplyInfo updateSubFlag(String id, String subflag) throws Exception;

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
	 * @param serviceDeptId 
	 * @param serviceDeptName 
	 * @param meetingRoom 
	 * @return
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String title, String startDate,
			String endDate, String subflag,String noticeFlag, String meetingRoom, String serviceDeptName, String serviceDeptId) throws Exception;
	
	/**
	 * 获取一周内会议室的预约情况
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月23日 下午5:28:49
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<MeetingApplyDetailInfo>  getMeetingApplyDetail(String startDate, String endDate)throws ParseException;

	Map<String,Object> getCount(String flag,String startDate, String endDate, String subflag) throws Exception;

	PageImpl getNoticeList(Pageable pageable, PageImpl pageImpl, String title, String startDate, String endDate,
			String subflag, String noticeFlag, String meetingName, String serviceDeptName, String serviceDeptId);

	List<MeetingApplyInfo> getPageListToSign(String startDate, String endDate);

	void save(MeetingApplyInfo meetingApplyInfo);

}
