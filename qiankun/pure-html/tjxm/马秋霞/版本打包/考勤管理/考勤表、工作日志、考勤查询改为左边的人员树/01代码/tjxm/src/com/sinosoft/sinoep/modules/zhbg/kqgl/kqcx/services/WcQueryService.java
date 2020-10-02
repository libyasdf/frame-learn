package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;

/*
 * 业务层的service实现
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月17日 上午10:18:54
 */
public interface WcQueryService {
	
	/**
	 * 查询草稿列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 上午10:18:19
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String userId, String deptId,
			String startDate, String endDate, String subflag);
	
}
