package com.sinosoft.sinoep.workflow.dao;

import java.util.List;
import java.util.Vector;

import com.sinosoft.sinoep.common.base.BaseDao;
import com.sinosoft.sinoep.common.util.HqlHelper;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.workflow.model.FlowSccredit;
import com.sinosoft.util.exception.DAOException;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：代办授权</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */

public interface FlowSccreditDAO extends BaseDao<FlowSccredit> {

    /**
     * 
     * <B>方法名称：添加待办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午9:44:29
     * @param vec
     * @throws DAOException void
     */
    @SuppressWarnings("rawtypes")
	public void addBatch(Vector vec) throws DAOException;

    /**
     * 
     * <B>方法名称：判断待办授权是否存在</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午9:44:34
     * @param fileType
     * @param userID
     * @param startDate
     * @param endDate
     * @param deptId
     * @return
     * @throws DAOException boolean
     */
    public boolean existSccrRecord(String fileType, String userID, String startDate, String endDate, String deptId)
            throws DAOException;

    /**
     * 
     * <B>方法名称：根据授权事项id，用户id查询代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午9:44:37
     * @param fileTypes
     * @param userID
     * @return
     * @throws DAOException boolean
     */
    public boolean checkAuto(String fileTypes, String userID) throws DAOException;

    /**
     * 
     * <B>方法名称：获取代办授权列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午9:44:40
     * @param pageNum
     * @param pageSize
     * @param flowSccredit
     * @param hql
     * @return
     * @throws DAOException Page
     */
    public Page queryFlowSccredit(int pageNum, int pageSize, FlowSccredit flowSccredit, HqlHelper hql)
            throws DAOException;

    /**
     * 
     * <B>方法名称：根据代办授权事项id删除代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午9:44:44
     * @param id
     * @throws DAOException void
     */
    public void delAuth(String[] id) throws DAOException;

    /**
     * 
     * <B>方法名称：获取待办授权列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 上午9:44:48
     * @param hql
     * @return
     * @throws DAOException List<FlowSccredit>
     */
    public List<FlowSccredit> findAll(HqlHelper hql) throws DAOException;
}
