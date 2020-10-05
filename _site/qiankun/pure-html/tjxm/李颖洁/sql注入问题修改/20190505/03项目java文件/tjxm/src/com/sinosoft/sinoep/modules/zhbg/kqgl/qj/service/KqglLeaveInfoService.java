package com.sinosoft.sinoep.modules.zhbg.kqgl.qj.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.qj.entity.KqglLeaveInfo;

/**
 * 请假Service TODO
 * @author 冯超
 * @Date 2018年4月11日 上午10:07:29
 */
public interface KqglLeaveInfoService {
	/**
	 * 
	 * TODO 保存
	 * @author 冯超
	 * @Date 2018年4月11日 上午10:16:42
	 * @param kqglLeaveInfo
	 * @return
	 */
	KqglLeaveInfo saveForm(KqglLeaveInfo kqglLeaveInfo);

	/**
	 * 查询审批列表 TODO
	 * @author 冯超
	 * @Date 2018年4月11日 下午4:02:01
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param leaveType
	 * @param creUserName
	 * @param applicantUnitName
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String leaveType,
			String creUserName, String applicantUnitName, String isLeaveInLieu, String subflag);

	/**
	 * 查询草稿 TODO
	 * @author 冯超
	 * @Date 2018年4月11日 下午4:03:16
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param leaveType
	 * @param creUserName
	 * @param applicantUnitName
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String leaveType,
			String isLeaveInLieu, String subflag) throws Exception;

	/**
	 * 根据id获取请假数据 TODO
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:39:53
	 * @param id
	 * @return
	 */
	KqglLeaveInfo getById(String id);

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
	KqglLeaveInfo updateSubFlag(String id, String subflag);
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
	List<KqglLeaveInfo> getsByDate(String startDate,String endDate, String leaveType, String isLeaveInLieu);
	
	/**
	 * 查询某个人某天是否有请假数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月24日 下午12:50:06
	 * @param userId
	 * @param day
	 * @return
	 */
	List<Map<String,Object>> getDataByUseridAndDay(String userId,String day);
	/**
	 * 根据季度的开始时间和结束时间获取已调休的天数和小时数
	 * @author 郝灵涛
	 * @Date 2018年7月30日 上午11:11:20
	 * @param beginQuarters
	 * @param endQuerters
	 * @return
	 * @throws Exception
	 */
	List<KqglLeaveInfo> getTalQjDaysAndHours(String beginQuarters,String endQuerters) throws Exception;
	
	/**
	 * 查询某段时间某些用户的请假数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午9:25:43
	 * @param startDate
	 * @param endDate
	 * @param commomSplit
	 * @return
	 */
	List<Map<String, Object>> findData(String startDate, String endDate, String commomSplit);


	List<Map<String, Object>> groupByLeaveReason(String timeRange,String dataType,String treeIds);
}
