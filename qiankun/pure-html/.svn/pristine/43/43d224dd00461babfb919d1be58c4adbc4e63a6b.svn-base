package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;

import java.util.List;
import java.util.Map;

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

	String getUserIds(String treeIds);

	List<Map<String,SysFlowUserVo>> getDeptIds(String treeIds);

	List<Map<String,Object>> getAllList(String timeRange,String dataType,String treeIds,String activeId);

	List<Map<String,Object>> getList(String timeRange,String dataType,String treeIds,String activeId);
}
