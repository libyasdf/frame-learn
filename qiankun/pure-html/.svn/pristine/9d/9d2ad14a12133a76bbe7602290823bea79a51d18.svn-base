package com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.comeLateLeaveEarly.entity.ComeLateLeaveEarlyInfo;

/**
 * 迟到早退service
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月15日 上午9:13:44
 */
public interface ComeLateLeaveEarlyService {
	/**
	 * 迟到早退保存
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午2:31:22
	 * @param comeLateLeaveEarlyInfo
	 * @return
	 */
	ComeLateLeaveEarlyInfo saveForm(ComeLateLeaveEarlyInfo comeLateLeaveEarlyInfo);

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
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String cdztUserId,String subflag) throws Exception;

	/**
	 * 根据id查询迟到早退信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月16日 下午3:05:03
	 * @param id
	 * @return
	 */
	ComeLateLeaveEarlyInfo getById(String id);

	/**
	 * 根据id删除请假数据 TODO
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:40:40
	 * @param id
	 * @return
	 */
	int delete(String id);

	/**
	 * 更新状态 TODO
	 * @author 冯超
	 * @Date 2018年4月18日 上午8:46:31
	 * @param id
	 * @param subflag
	 * @return
	 */
	ComeLateLeaveEarlyInfo updateSubFlag(String id, String subflag);
	
	/**
	 * 
	 * 查询某些用户某段时间审批统计的迟到早退数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午10:01:08
	 * @param startDate
	 * @param endDate
	 * @param commomSplit
	 * @return
	 */
	List<Map<String, Object>> findUserData(String startDate, String endDate, String commomSplit);
}
