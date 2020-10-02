package com.sinosoft.sinoep.workflow.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sinosoft.sinoep.common.base.BaseDaoImpl;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：操作数据库字段值</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Repository
public class ModifyFieldDaoImpl extends BaseDaoImpl<Object> implements ModifyFieldDao {

    /**
     * 
     * <B>方法名称：更新字段值</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.dao.ModifyFieldDao#updateOneFieldString(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public int updateOneFieldString(String tableName, String filedName, String value, String where) {
        Session session = getCurrentSession();
        // 更新状态
        String updateSql = "update " + tableName + " set " + filedName + " = ? where " + where;
        Query query = session.createQuery(updateSql);
        query.setString(0, value);
        int res = query.executeUpdate();
        return res;
    }

}