package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.entity.SupplementSign;
public interface BqQueryService {
	/**
	 * 请假查询Service
	 * @author 郝灵涛
	 * @Date 2018年7月23日 上午10:58:11
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param supplementSignType
	 * @param subflag
	 * @return
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String supplementSignType,
			 String userId,String subflag);
	/**
	 * 根据时间补签类型查询
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年6月29日 下午2:15:39
	 * @param startDate
	 * @param endDate
	 * @param supplementSignType
	 * @return
	 */
	List<SupplementSign> getsByDate(String startDate,String endDate, String supplementSignType,String userId,String subflag);

}
