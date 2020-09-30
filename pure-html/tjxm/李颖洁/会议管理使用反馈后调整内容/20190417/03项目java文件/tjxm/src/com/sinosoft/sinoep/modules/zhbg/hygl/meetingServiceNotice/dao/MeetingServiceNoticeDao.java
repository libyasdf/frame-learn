package com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingServiceNotice.entity.MeetingServiceNotice;

/**
 * 会务服务通知dao
 * TODO 
 * @author 张建明
 * @Date 2018年8月29日 上午11:38:04
 */
public interface MeetingServiceNoticeDao extends BaseRepository<MeetingServiceNotice, String> {
	
	@Query("select u from MeetingServiceNotice u where u.visible='1' and u.status in ('1','5')")
	public List<MeetingServiceNotice> getMeetingApplyInfoByDate(String startDate, String endDate);

}
