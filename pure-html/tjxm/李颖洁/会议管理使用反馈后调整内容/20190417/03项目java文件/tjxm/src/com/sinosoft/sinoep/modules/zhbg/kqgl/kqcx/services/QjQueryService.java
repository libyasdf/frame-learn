package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.entity.KqglLeaveInfo;
/**
 * 请假查询Service
 * @author 郝灵涛
 * @Date 2018年7月23日 上午10:59:08
 */
public interface QjQueryService {
/**
 * 请假查询
 * @author 郝灵涛
 * @Date 2018年7月23日 上午10:45:46
 * @param pageable
 * @param pageImpl
 * @param startDate
 * @param endDate
 * @param userIds
 * @param isLeaveInLieu
 * @param subflag
 * @return
 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String userIds, String subflag,String isOne) throws Exception;
	/**
	 * 根据时间 请假类型等查询
	 * @author 郝灵涛
	 * @Date 2018年6月29日 上午9:55:38
	 * @param startDate
	 * @param endDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	List<KqglLeaveInfo> getsByDate(String startDate, String endDate, String userId,String subflag);
	
}
