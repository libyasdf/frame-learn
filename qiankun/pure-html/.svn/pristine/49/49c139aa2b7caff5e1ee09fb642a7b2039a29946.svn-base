package com.sinosoft.sinoep.modules.dagl.daly.urge.services;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.daly.urge.entity.BorrowUrge;

/**
 * 档案借阅催还service
 * @author 王磊
 * @Date 2019年2月11日 上午10:26:53
 */
public interface BorrowUrgeService {
	
	/**
	 * 根据借阅id查询催还记录列表
	 * @author 王磊
	 * @Date 2019年2月11日 上午10:38:07
	 * @param pageable
	 * @param pageImpl
	 * @param borrowId
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, BorrowUrge borrowUrge);
	
	/**
	 * 添加一条催还记录
	 * @author 王磊
	 * @Date 2019年2月16日 下午3:07:53
	 * @param borrowUserId 借阅人id
	 * @param borrowUserName 借阅人name
	 * @param borrowId 借阅id或者借阅子表数据id，这里得根据设计来确定这个字段含义
	 * @return
	 */
	boolean addUrgeInfo(BorrowUrge borrowUrge);
}
