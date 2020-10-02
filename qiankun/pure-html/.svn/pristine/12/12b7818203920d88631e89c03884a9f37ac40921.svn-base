package com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingApply.entity.MeetingApplyInfo;

/**
 * 会议申请dao
 * TODO 
 * @author 冯超
 * @Date 2018年8月21日 上午10:59:37
 */
public interface MeetingApplyDao extends BaseRepository<MeetingApplyInfo, String> {
	
	//@Query("select u from MeetingApplyInfo u where u.visible='1' and u.subflag in ('1','5') and u.meetingEndDate>=?1 and u.meetingStartDate<=?2")
	@Query("select u from MeetingApplyInfo u where u.visible='1' and u.subflag in ('1','5') ")
	public List<MeetingApplyInfo> getMeetingApplyInfoByDate(String startDate, String endDate);

}
