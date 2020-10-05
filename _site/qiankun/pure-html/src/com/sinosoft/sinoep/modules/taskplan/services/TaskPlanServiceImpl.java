package com.sinosoft.sinoep.modules.taskplan.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.taskplan.dao.TaskPlanDao;
import com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 业务层service实现类
 * TODO 
 * @author 李利广
 * @Date 2018年3月15日 下午8:51:44
 */
@Service
public class TaskPlanServiceImpl implements TaskPlanService {
	
	@Autowired
	private TaskPlanDao taskPlanDao;
	
	/**
	 * 保存方法
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:05
	 * @param plan
	 * @param ideaName
	 * @return
	 * @throws Exception
	 */
	@Override
	public TaskPlan saveForm(TaskPlan plan) throws Exception {
		plan.setVisible(CommonConstants.VISIBLE[1]);
		if (StringUtils.isBlank(plan.getId())) {
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			plan.setCreTime(creTime);
			plan.setCreUserId(UserUtil.getCruUserId());
			plan.setCreUserName(UserUtil.getCruUserName());
			plan = taskPlanDao.save(plan);
		}else{
			TaskPlan oldPlan = taskPlanDao.findOne(plan.getId());
			oldPlan.setIdea(plan.getIdea());
			oldPlan.setNote(plan.getNote());
			oldPlan.setTitle(plan.getTitle());
			oldPlan.setXiebDept(plan.getXiebDept());
			oldPlan.setXiebPerson(plan.getXiebPerson());
			oldPlan.setZhubDept(plan.getZhubDept());
			oldPlan.setZhubDeptNm(plan.getZhubDeptNm());
			oldPlan.setZhubPerson(plan.getZhubPerson());
			oldPlan.setZhubPersonNm(plan.getZhubPersonNm());
			oldPlan.setWeek(plan.getWeek());
			oldPlan.setSex(plan.getSex());
			oldPlan.setArea(plan.getArea());
			taskPlanDao.save(oldPlan);
		}
		return plan;
	}
	
	/**
	 * 删除一条记录
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:21
	 * @param id
	 * @return
	 */
	@Override
	public int deletePlan(String id)  throws Exception {
		String jpql = "update TaskPlan t set t.visible = ? where t.id = ?";
		return taskPlanDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}

	/**
	 * 根据主键ID查询一条数据
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:36
	 * @param id
	 * @return
	 */
	@Override
	public TaskPlan getById(String id) throws Exception {
		return taskPlanDao.findOne(id);
	}

	/**
	 * 更新业务表流程状态
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:54
	 * @param id
	 * @param subflag
	 * @return
	 */
	@Override
	public TaskPlan updateSubFlag(String id, String subflag) throws Exception {
		TaskPlan plan = getById(id);
		plan.setSubflag(subflag);
		return taskPlanDao.save(plan);
	}

	/**
	 * 查询审批列表
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月14日 下午5:08:38
	 * @param pageable
	 * @param userId
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String title, String startDate,
			String endDate, String subflag) throws Exception{
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("select new com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan(f.id as yibanid,t.id, t.title, t.workFlowId, t.subflag, t.note, t.zhubDept,"
			+ "t.zhubPerson, t.zhubDeptNm, t.zhubPersonNm, t.xiebDept, t.xiebPerson,"
			+ "t.creTime, t.creUserId, t.creUserName, t.idea)");
		querySql.append("	from TaskPlan t, FlowRead f ");
		querySql.append("	where f.userId = ? ");
		querySql.append("	and t.id = f.recordId and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(title)) {
			querySql.append("   and t.title like ?");
			para.add("%"+title+"%");
		}
		if (StringUtils.isNotBlank(subflag) ){
			querySql.append("   and t.subflag = ?");
			para.add(subflag);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.creTime >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.creTime <= ?");
			para.add(endDate);
		}
		
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
        Page<TaskPlan> page = taskPlanDao.query(querySql.toString(), pageable,para.toArray());
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(page.getContent());
		pageImpl.getData().setTotal((int)page.getTotalElements());
		return pageImpl;
	}

	/**
	 * 查询草稿列表
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月14日 下午5:11:01
	 * @param pageable
	 * @param userId
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String title,
			String startDate, String endDate, String subflag) throws Exception {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("	from TaskPlan t ");
		querySql.append("	where t.creUserId = ? and t.subflag = ? and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		
		//拼接条件
		para.add(UserUtil.getCruUserId());
		para.add(subflag);
		if (StringUtils.isNotBlank(title)) {
			querySql.append("   and t.title like ?");
			para.add("%"+title+"%");
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.creTime >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.creTime <= ?");
			para.add(endDate);
		}
		
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
        Page<TaskPlan> page = taskPlanDao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<TaskPlan> content = page.getContent();
        for (TaskPlan taskPlan : content) {
        	if (ConfigConsts.START_FLAG.equals(taskPlan.getSubflag())) {
        		taskPlan.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
			}
		}
        pageImpl.setFlag("1");
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int)page.getTotalElements());
        return pageImpl;
	}
	
}
