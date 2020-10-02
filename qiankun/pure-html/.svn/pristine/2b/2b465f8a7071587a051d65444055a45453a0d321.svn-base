/**
 * Copyright 2018 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.modules.leaveManage.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HqlHelper;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.modules.leaveManage.dao.LeaveManageDao;
import com.sinosoft.sinoep.modules.leaveManage.model.LeaveManage;
import com.sinosoft.sinoep.workflow.service.WorkFlowClientService;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author 中科软科技  liangxiuhua
 * @since 2018年1月4日
 */
@Service
public class LeaveManageServiceImp implements LeaveManageService{
    
    @Autowired
    LeaveManageDao leaveManageDao;
    @Autowired
    WorkFlowClientService workFlowClientService;
    

    /**
     * <B>方法名称：获取列表页</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @author：lilili
     * @cretetime:2017年5月4日 下午4:20:50
     * @param id：请假id
     * @param pageNum：当前页
     * @param showCount：显示数量
     * @param beginTime：结束时间
     * @param endTime：开始时间
     * @param title：标题
     * @param status：办理状态
     * @param creUser：创建人
     * @param creDept：创建部门
     * @param orgId：组织id
     * @param sysId:系统id
     * @return
     * @throws Exception
     */
    @Override
    public Page list(String username, int pageNum, int showCount, String beginTime, String endTime, String title,
            String leaveType, String status, String orgid, String sysid)
            throws Exception {
        HqlHelper helper = new HqlHelper(LeaveManage.class);
        if (StringUtils.isNotBlank(title)) {
            helper.addCondition(" title like ? ", "%" + title + "%");
        }
        if (StringUtils.isNotBlank(beginTime)) {
            helper.addCondition(" startDate >= ? ", beginTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            helper.addCondition(" endDate <= ? ", endTime);
        }
        helper.addOrder("creTime", false);//按cre_time 降序排列
        return helper.buildPageBean(pageNum, showCount, leaveManageDao);
    }
    
    
    @Override
    public LeaveManage view(String id) {
        String hql = "from LeaveManage where id = ? ";
        return (LeaveManage) leaveManageDao.getUnique(hql, id);
    }

    
    /**
     * 
     * <B>方法名称：保存或更新请假信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param leave：请假实体
     * @param workitemid：流程id
     * @param type：类型
     * @param idea：意见
     * @param id：请假id
     * @param userid：用户id
     * @param orgid：组织id
     * @param sysid:系统id
     * @param vo:用户部门相关树结构实体信息类
     * @return
     * @throws Exception String
     */
    @Override
    public String saveOrUpdate(LeaveManage leave, String workitemid, String type, String idea, String id,
            String userid, String orgid, String sysid) throws Exception {
        if (StringUtils.isBlank(leave.getId())) {
            leave.setSubFlag(ConfigConsts.START_FLAG);//设置为起草
            leave.setSysId(sysid);
            leave.setOrgId(orgid);
            leaveManageDao.add(leave);
            id = leave.getId();
            return id;
        }
        else {
            LeaveManage leaveManage = view(id, orgid, sysid);
            leaveManage.setTitle(leave.getTitle());
            leaveManage.setTel(leave.getTel());
            leaveManage.setAddress(leave.getAddress());
            leaveManage.setLeaveType(leave.getLeaveType());
            leaveManage.setEndDate(leave.getEndDate());
            leaveManage.setStartDate(leave.getStartDate());
            leaveManage.setRemark(leave.getRemark());
            leaveManage.setLeaveDay(leave.getLeaveDay());
            leaveManageDao.update(leaveManage);
            return id;
        }
    }
    /**
     * 
     * <B>方法名称：通过id获取请假信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id:请假id
     * @param orgid：组织id
     * @param sysid:系统id
     * @return
     * @throws Exception LeaveManage
     */
    @Override
    public LeaveManage view(String id, String orgid, String sysid) throws Exception {
        String hql = " from LeaveManage where id = ? and sysId = ? and orgId = ? ";
        return (LeaveManage) leaveManageDao.getUnique(hql, id, sysid, orgid);
    }
    
    /**
     * 
     * <B>方法名称：根据id删除草稿中的请假信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id:请假id
     * @param userid:用户id
     * @param orgid：组织id
     * @param sysid:系统id
     * @return
     * @throws Exception String
     */
    @Override
    public String deleteDoc(String id, String userid, String orgid, String sysid) throws Exception {
        String result = "fail";
        LeaveManage entity = view(id, orgid, sysid);
        if (entity != null) {
            leaveManageDao.delete(entity);
            result = "success";
        }
        return result;
    }
    /**
     * 
     * <B>方法名称：修改业务状态</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id:请假id
     * @param subflag：状态
     * @param userid：用户id
     * @param orgid：组织id
     * @param sysid:系统id
     * @return
     * @throws Exception String
     */
    @Override
    public String updateSubFlag(String id, String subflag) {
        LeaveManage entity = view(id);
        entity.setSubFlag(subflag);
        leaveManageDao.update(entity);
        return "success";
    }
    
}
