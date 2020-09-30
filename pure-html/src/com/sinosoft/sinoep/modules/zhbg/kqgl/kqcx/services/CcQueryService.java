package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.entity.BusinessTrip;

/**
 * 出差查询Service
 * TODO 
 * @author 郝灵涛
 * @Date 2018年7月23日 上午11:03:29
 */
public interface CcQueryService {
	/**
	 * 出差查询列表
	 * @author 郝灵涛
	 * @Date 2018年7月23日 上午11:03:46
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String busiTripType,String userId,String startDate, String endDate,
			String subflag,String iftjj,String isOne) throws Exception;
/**
	 * 根据时间出差类型查询
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年6月29日 下午2:15:39
	 * @param startDate
	 * @param endDate
	 * @param supplementSignType
	 * iftjj  1:查询天津局的 0：本部门下的
	 * @return
	 */
	List<BusinessTrip> getsByDate(String startDate,String endDate, String busiTripType,String userId,String subflag,String iftjj);

}
