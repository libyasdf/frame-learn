package com.sinosoft.sinoep.workflow.subflag.services;

import com.sinosoft.sinoep.workflow.subflag.entity.SysFlowSubflag;

/**
 * TODO 流程状态业务接口
 * @author 李利广
 * @Date 2019年01月14日 16:29:27
 */
public interface SysFlowSubflagService {

    /**
     * TODO 保存、修改一条流程状态
     * @author 李利广
     * @Date 2019年01月14日 16:30:20
     * @param subflag
     * @return com.sinosoft.sinoep.workflow.subflag.entity.SysFlowSubflag
     */
    SysFlowSubflag saveSubflag(SysFlowSubflag subflag) throws Exception;

    /**
     * TODO 根据流程ID及业务ID获取一条数据
     * @author 李利广
     * @Date 2019年01月14日 16:42:18
     * @param subflag
     * @return com.sinosoft.sinoep.workflow.subflag.entity.SysFlowSubflag
     */
    SysFlowSubflag getByFlowId(SysFlowSubflag subflag) throws Exception;
}
