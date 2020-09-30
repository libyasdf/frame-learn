package com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.absenteeism.entity.AbsenteeismInfo;

/**
 * 旷工service
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月14日 下午2:15:04
 */
public interface AbsenteeismInfoService {
	/**
	 * 保存
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月14日 上午11:41:20
	 * @param absenteeismInfo
	 * @return
	 */
	AbsenteeismInfo saveForm(AbsenteeismInfo absenteeismInfo);
	/**
	 * 获取旷工列表
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月14日 下午1:04:08
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param leaveType
	 * @param isLeaveInLieu
	 * @param subflag
	 * @return
	 * @throws Exception
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,String userId) throws Exception;

	/**
	 * 根据id获取旷工信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月14日 下午2:09:09
	 * @param id
	 * @return
	 */
	AbsenteeismInfo getById(String id);

	/**
	 * 根据id删除旷工信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月14日 下午2:13:19
	 * @param id
	 * @return
	 */
	int delete(String id);
	/**
	 * 根据时间 用户等查询
	 * @author 郝灵涛
	 * @Date 2018年6月29日 上午9:55:38
	 * @param startDate
	 * @param endDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	List<AbsenteeismInfo> getsByDate(String startDate,String endDate, String userId);
	
	/**
	 * 查询某些用户某个月的旷工数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午10:36:57
	 * @param startDate
	 * @param endDate
	 * @param commomSplit
	 * @return
	 */
	List<Map<String, Object>> findUserData(String startDate, String endDate, String userids);
	
}
