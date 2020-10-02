package com.sinosoft.sinoep.modules.zhbg.kqgl.bq.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.bq.entity.SupplementSign;

/**
 * 补签Service TODO
 * @author 冯超
 * @Date 2018年4月11日 上午10:07:29
 */
public interface SupplementSignService {
	/**
	 * 
	 * TODO 保存
	 * @author 冯超
	 * @Date 2018年4月11日 上午10:16:42
	 * @param kqglLeaveInfo
	 * @return
	 */
	SupplementSign saveForm(SupplementSign supplementSign);

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
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String supplementSignType,
			String creUserName, String applicantUnitName, String subflag);

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
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String supplementSignType,
			 String subflag);

	/**
	 * 根据id获取数据 TODO
	 * @author 冯超
	 * @Date 2018年4月12日 下午2:39:53
	 * @param id
	 * @return
	 */
	SupplementSign getById(String id);

	/**
	 * 根据id删除数据 TODO
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
	SupplementSign updateSubFlag(String id,String userId,String supplementSignDate,String supplementSignTime,String supplementSignType, String subflag);
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
	List<SupplementSign> getsByDate(String startDate,String endDate, String supplementSignType);


}
