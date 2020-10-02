/**
 * 
 */
package com.sinosoft.sinoep.workflow.dao;

import java.util.List;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sinosoft.ep.webform.tool.Utiles;
import com.sinosoft.log.LogHelper;
import com.sinosoft.sinoep.common.base.BaseDaoImpl;
import com.sinosoft.sinoep.common.util.HqlHelper;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.workflow.model.FlowSccredit;
import com.sinosoft.util.exception.DAOException;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：待办授权数据库操作DAO实现类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Repository
public class FlowSccreditDAOImpl extends BaseDaoImpl<FlowSccredit> implements FlowSccreditDAO {
    
	@SuppressWarnings("unused")
	private static LogHelper log = new LogHelper(FlowSccreditDAOImpl.class);

    /**
     * 
     * <B>方法名称：添加待办授权</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.dao.IFlowSccreditDAO#addBatch(java.util.Vector)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public void addBatch(Vector vec) throws DAOException {
        Session session = getCurrentSession();
        if (vec != null && vec.size() > 0) {
            FlowSccredit flowSccredit = null; // 创建代办授权对象
            // 循环获取代办授权对象
            for (int i = 0; i < vec.size(); i++) {
                flowSccredit = (FlowSccredit) vec.get(i); // 获取代办授权对象
                session.save(flowSccredit); // 保存代办授权对象
                // 批插入的对象立即写入数据库并释放内存
                if (i % 10 == 0) {
                    session.flush();
                    session.clear();
                }
            }
        }
    }

    /**
     * 
     * <B>方法名称：判断待办授权是否存在</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.dao.IFlowSccreditDAO#existSccrRecord(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public boolean existSccrRecord(String fileType, String userID, String startDate, String endDate, String deptId)
            throws DAOException {
        Session session = getCurrentSession();
        String sql = " from FlowSccredit  where filetype='" + fileType + "' and deptid='" + deptId + "' and userid='"
                + userID + "'" + " and (" +
                // 时间段内部
                "(sccbegindate >= '" + startDate + "' and sccenddate <= '" + endDate + "')" +
                // 跨时间段；
                "or (sccbegindate <= '" + startDate + "' and sccenddate >= '" + endDate + "')" +
                // 左边界；
                "or (sccbegindate <= '" + startDate + "' and sccenddate <= '" + endDate + "' and  sccenddate >= '"
                + startDate + "')" +
                // 右边界；
                "or (sccbegindate >= '" + startDate + "' and sccbegindate <= '" + endDate + "' and  sccenddate >= '"
                + endDate + "')" +
                ")";
        List list = session.createQuery(sql).list();
        if (null != list && list.size() > 0) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 
     * <B>方法名称：根据授权事项id，用户id查询代办授权</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.dao.IFlowSccreditDAO#checkAuto(java.lang.String, java.lang.String)
     */
    @Override
    public boolean checkAuto(String fileTypes, String userID) throws DAOException {
        String issccredit = (String) getCurrentSession()
                .createQuery("select f.issccredit from FlowSccredit f where f.sccredituserid = '" + userID
                        + "' and f.filetype = '" + fileTypes + "' and f.z_status ='1'")
                .uniqueResult();
        if (!Utiles.isNullStr(issccredit)) {
            if ("1".equals(issccredit)) {
                return true;
            }
            else if ("0".equals(issccredit)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * <B>方法名称：获取代办授权列表</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.dao.IFlowSccreditDAO#queryFlowSccredit(int, int,
     *      com.sinosoft.sinoep.workflow.model.FlowSccredit, com.sinosoft.sinoep.common.util.HqlHelper)
     */
    @Override
    public Page queryFlowSccredit(int pageNum, int pageSize, FlowSccredit flowSccredit, HqlHelper hql)
            throws DAOException {
        return getPageBean(pageNum, pageSize, hql);
    }

    /**
     * 
     * <B>方法名称：根据代办授权事项id删除代办授权</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.dao.IFlowSccreditDAO#delAuth(java.lang.String[])
     */
    @Override
    public void delAuth(String[] id) throws DAOException {
        Query query = getCurrentSession().createQuery(
                "delete from FlowSccredit where sccreditid in(:sccreditids) and z_status='1'");
        query.setParameterList("sccreditids", id);
        query.executeUpdate();
    }

    /**
     * 
     * <B>方法名称：获取待办授权列表</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.dao.IFlowSccreditDAO#findAll(com.sinosoft.sinoep.common.util.HqlHelper)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<FlowSccredit> findAll(HqlHelper hql) throws DAOException {
        return (List<FlowSccredit>) queryList(hql);
    }
}
