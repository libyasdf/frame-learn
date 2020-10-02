package com.sinosoft.sinoep.modules.zhbg.kqgl.wc.services;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.wc.entity.GoOutInfo;

/*
 * 业务层的service实现
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月17日 上午10:18:54
 */
public interface GoOutInfoService {
	
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
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String creUserName, String applicantUnitName,
			String startDate, String endDate, String subflag);
	
	/**
	 * 保存方法
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 下午2:14:20
	 * @param info
	 * @param ideaName
	 * @return
	 */
	GoOutInfo saveForm(GoOutInfo info);
	
	/**
	 * 根据主键ID查询一条数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 下午3:51:29
	 * @param id
	 * @return
	 */
	GoOutInfo getById(String id);
	
	/**
	 * 根据id删除一条外出记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月17日 下午4:37:00
	 * @param id
	 * @return
	 */
	int deleteGoOutInfo(String id);

	/**
	 * 更新业务表的流程状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 上午9:26:24
	 * @param id
	 * @param subflag
	 * @return
	 */
	GoOutInfo updateSubFlag(String id, String subflag);
	
	/**
	 * 获取外出审批状态的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 下午1:33:03
	 * @param pageable
	 * @param pageImpl
	 * @param creUserName
	 * @param creDeptName
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String creUserName, String applicantUnitName, String startDate,
			String endDate, String subflag);
	
	/**
	 * 根据外出的开始时间和结束时间统计出外出时长
	 * @param startDate
	 * @param endDate
	 * @param startAmOrPm
	 * @param endAmOrPm
	 * @return
	 * @throws Exception 
	 */
	String getGoOutTime(String startDate, String endDate, String startAmOrPm, String endAmOrPm) throws Exception;
	
	/**
	 * 根据查询条件获取当前用户的外出信息的记录条数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午2:33:33
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	long getCnt(String userids,String startDate, String endDate, String subflag);

	/**
	 * 根据查询条件获取当前用户的外出信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午3:16:59
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	List<GoOutInfo> getGoOutList(String userids,String startDate, String endDate, String subflag);
	
	/**
	 * 查询某个人某天的外出记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月24日 下午1:12:07
	 * @param userId
	 * @param day
	 * @return
	 */
	List<Map<String,Object>> getDataByUseridAndDay(String userId,String day);
	
	

}
