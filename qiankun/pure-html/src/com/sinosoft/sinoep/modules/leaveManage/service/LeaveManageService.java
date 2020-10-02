/**
 * Copyright 2018 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.modules.leaveManage.service;

import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.modules.leaveManage.model.LeaveManage;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author 中科软科技  liangxiuhua
 * @since 2018年1月4日
 */
public interface LeaveManageService {
    

    /**
     * <B>方法名称：获取列表页</B><BR>
     * <B>概要说明：</B><BR>
     * 
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
    public Page list(String creUser, int pageNum, int showCount, String beginTime, String endTime, String title,
            String leaveType, String status, String orgid, String sysid)
            throws Exception;

    
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
    public String saveOrUpdate(LeaveManage leave, String workitemid, String type, String idea, String id,
            String userid, String orgid, String sysid) throws Exception;

    /**
     * 
     * <B>方法名称：通过id获取请假信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id:请假id
     * @param orgid：组织id
     * @param sysid:系统id
     * @return
     * @throws Exception LeaveManagement
     */
    public LeaveManage view(String id, String orgid, String sysid) throws Exception;
    
    /**
     * 
     * <B>方法名称：通过id获取请假信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id:请假id
     * @return
     * @throws Exception LeaveManagement
     */
    public LeaveManage view(String id);
    
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
    public String deleteDoc(String id, String userid, String orgid, String sysid) throws Exception;
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
    public String updateSubFlag(String id, String subflag);
   }
