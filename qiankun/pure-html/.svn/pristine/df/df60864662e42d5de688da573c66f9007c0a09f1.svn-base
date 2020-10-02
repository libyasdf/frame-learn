package com.sinosoft.sinoep.modules.taskplan.services;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan;

/**
 * 业务层service接口
 * TODO 
 * @author 李利广
 * @Date 2018年3月15日 下午8:47:43
 */
public interface TaskPlanService{

	/**
	 * 保存方法
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:05
	 * @param plan
	 * @return
	 */
	TaskPlan saveForm(TaskPlan plan) throws Exception;

	/**
	 * 删除一条记录
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:21
	 * @param id
	 * @return
	 */
	int deletePlan(String id) throws Exception;

	/**
	 * 根据主键ID查询一条数据
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:36
	 * @param id
	 * @return
	 */
	TaskPlan getById(String id) throws Exception;

	/**
	 * 更新业务表流程状态
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:54
	 * @param id
	 * @param subflag
	 * @return
	 */
	TaskPlan updateSubFlag(String id, String subflag) throws Exception;

	/**
	 * 查询审批列表（连表查询）
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:49:13
	 * @param pageable
	 * @param userId
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String title, String startDate, String endDate,
			String subflag) throws Exception;

	/**
	 * 查询草稿箱列表
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:49:36
	 * @param pageable
	 * @param userId
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String title, String startDate,
			String endDate, String subflag) throws Exception;

}
