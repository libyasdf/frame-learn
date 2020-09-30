package com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.entity.XxkhLearnTime;

/**
 * 学习时长service实现类
 * TODO 
 * @author 王磊
 * @Date 2018年8月24日 下午3:00:37
 */
public interface XxkhLearnTimeService {
	
	XxkhLearnTime saveLearningRecord(XxkhLearnTime learnTime);
	/**
	 * 根据选取资料类型查看具体学习资料时长
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月8日 上午10:17:08
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param nodeIds
	 * @return
	 */
	PageImpl getLearnTimeById(Pageable pageable, PageImpl pageImpl,String timeRange,String nodeIds);
	Page<XxkhLearnTime> learningTimeTablelist(XxkhLearnTime dt,PageImpl pageImpl) throws Exception;
	
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl,String startTime,String endTime) throws Exception;
	/**
	 * 获取个人学习时长
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月8日 上午10:16:06
	 * @param startTime
	 * @param endTime
	 * @param nodeIds
	 * @return
	 */
	List<XxkhLearnTime> getLearnTotalTime(String startTime,String endTime,String nodeIds);
	/**
	 * 获取查询统计中学习时长
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月8日 上午10:16:06
	 * @param startTime
	 * @param endTime
	 * @param nodeIds
	 * @return
	 */
	PageImpl getLearnDeptTotalTime(PageImpl pageImpl,Pageable pageable,String deptIds,String chushiIds,String positions,String startTime,String endTime,String nodeIds);
	/**
	 * 根据选取资料类型查看具体学习资料时长
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月8日 上午10:16:06
	 * @param startTime
	 * @param endTime
	 * @param nodeIds
	 * @return
	 */
	PageImpl getDeptLearnTimeById(Pageable pageable, PageImpl pageImpl,String timeRange,String userId,String nodeIds);
	
}
