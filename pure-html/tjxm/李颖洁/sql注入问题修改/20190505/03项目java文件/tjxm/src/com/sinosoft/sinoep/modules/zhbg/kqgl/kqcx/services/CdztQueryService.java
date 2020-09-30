package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.entity.ComeLateLeaveEarlyInfo;

/**
 * 迟到早退service
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月15日 上午9:13:44
 */
public interface CdztQueryService {
	
	/**
	 * 查询迟到早退列表
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午4:32:26
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param cdztUserId
	 * @param subflag
	 * @return
	 * @throws Exception
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String cdztUserId,String subflag,String isOne) throws Exception;
	/**
	 * 根据时间 userId等查询
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月18日 上午11:32:01
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @param subflag
	 * @return
	 */
	List<ComeLateLeaveEarlyInfo> getsByDate(String startDate, String endDate, String userId,String subflag);
	
}
