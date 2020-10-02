package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
public interface AttendenceQueryService {
	
	/**
	 * 考勤查询
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月27日 下午8:08:23
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param cardNumber
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String cardNumber,String userId);

}
