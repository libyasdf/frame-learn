package com.sinosoft.sinoep.modules.consultManage.service;

import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.modules.consultManage.model.ConsultManage;

public interface ConsultManageService {

    /**
     * 保存修改
     * 
     * @param entity
     * @param id
     * @param fuj
     * @param delj
     * @param orgid
     * @param sysid
     * @return
     */
    public String saveOrUpdate(ConsultManage entity, String workitemid, String idea, String id,
            String userid, String orgid, String sysid);

    /**
     * 获取具体的表单信息
     * 
     * @param id
     * @return
     */
    public ConsultManage view(String id);

    /**
     * 获取草稿数量
     * 
     * @return
     */
    public Long getCount(String subflag, String userid, String orgid, String sysid, String flowType);

    /**
     * 
     * <B>方法名称：根据ID删除草稿中的发文</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param id
     *            发文ID
     * @return
     */
    public String deleteDoc(String id, String userid, String orgid, String sysid);

    /**
     * 流程提交成功修改业务表的状态
     * 
     * @param id
     * @param subFlag
     * @return
     */
    public String updateSubFlag(String id, String subFlag);

    /**
     * 分页获取数据
     * 
     * @return
     */
    public Page pageList(String userid, int pageNum, int showCount, String title, String endTime, String beginTime);
}
