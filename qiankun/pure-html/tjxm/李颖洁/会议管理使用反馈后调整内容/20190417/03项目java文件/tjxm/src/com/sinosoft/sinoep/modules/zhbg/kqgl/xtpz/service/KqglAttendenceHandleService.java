package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqglAttendenceHandle;

/**
 * 
 * TODO 考勤数据处理视图
 * @author 冯超
 * @Date 2018年6月1日 上午9:31:47
 */
public interface KqglAttendenceHandleService {
	
	/**
	 * 
	 * TODO 获取列表
	 * @author 冯超
	 * @Date 2018年6月1日 下午12:48:04
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param cardNumber
	 * @return
	 * @throws Exception
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String timeRange, String cardNumber) throws Exception;
	
	/**
	 * 
	 * TODO 获取数据
	 * @author 冯超
	 * @Date 2018年6月1日 下午12:48:39
	 * @param id
	 * @return
	 * @throws Exception
	 */
	KqglAttendenceHandle getById(String id) throws Exception;
	
	/**
	 * 
	 * TODO 根据日期和卡号获取数据
	 * @author 冯超
	 * @Date 2018年6月1日 下午2:07:59
	 * @param importDate
	 * @param cardNumber
	 * @return
	 */
	KqglAttendenceHandle getKqglAttendenceHandle(String importDate,String cardNumber);
	
	/**
	 * 根据考勤卡号查询某段时间内的考勤数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月24日 下午5:38:16
	 * @param startDate
	 * @param endDate
	 * @param cardNum
	 * @return
	 */
	List<KqglAttendenceHandle> findByCondition(String startDate, String endDate, String cardNum);
	
	/**
	 * 查询某段时间某些卡号的考勤数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午11:26:26
	 * @param startDate
	 * @param endDate
	 * @param cardNum
	 * @return
	 */
	List<KqglAttendenceHandle> getKqInfo(String startDate, String endDate, String cardNum);

}
