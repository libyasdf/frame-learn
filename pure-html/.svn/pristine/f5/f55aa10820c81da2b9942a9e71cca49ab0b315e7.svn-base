package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqLog;

/**
 * 
 * TODO 考勤日志service
 * @author 冯超
 * @Date 2018年5月30日 上午10:06:15
 */
public interface KqLogService {
	
	/**
	 * 
	 * TODO 获取日志列表
	 * @author 冯超
	 * @Date 2018年5月31日 上午9:20:41
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate) throws Exception;
	
	/**
	 * 
	 * TODO 根据id获取数据
	 * @author 冯超
	 * @Date 2018年5月31日 上午10:05:56
	 * @param id
	 * @return
	 * @throws Exception
	 */
	KqLog getById(String id) throws Exception;

}
