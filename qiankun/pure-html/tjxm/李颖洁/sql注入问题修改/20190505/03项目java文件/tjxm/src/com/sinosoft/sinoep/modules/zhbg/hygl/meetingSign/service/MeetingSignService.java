package com.sinosoft.sinoep.modules.zhbg.hygl.meetingSign.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingSign.entity.MeetingSignInfo;

public interface MeetingSignService {

	List<MeetingSignInfo> saveForm(List<MeetingSignInfo> meetingSignInfos) throws Exception;

	MeetingSignInfo saveForm(MeetingSignInfo meetingSignInfo) throws Exception;

	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			String meetingName, String status);

}
