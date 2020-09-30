package com.sinosoft.sinoep.common.base;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Component;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.GenericsUtils;
import com.sinosoft.sinoep.common.util.HqlHelper;
import com.sinosoft.sinoep.common.util.Page;

/**
 * 
 * @author kjx123 Dao基础类实现
 * @param <T>
 */
@Component
public class BaseDaoImpl<T> implements BaseDao<T> {

    @Resource
    private HibernateTemplate hibernateTemplate;

    protected Class<T> clazz; // 这是一个问题！

    @SuppressWarnings({ "unchecked" })
    public BaseDaoImpl() {
        // 通过反射得到T的真实类型
        this.clazz = GenericsUtils.getSuperClassGenricType(this.getClass());

    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    /**
     * 
     * <B>方法名称：获取数据连接</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @return
     * @throws Exception
     */
    @Override
    public Connection getConnection() throws Exception {
        Connection connection = SessionFactoryUtils.getDataSource(hibernateTemplate.getSessionFactory())
                .getConnection();
        return connection;
    }

    @Override
    public Session getCurrentSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

    // 添加
    @Override
    public void add(Object o) {
        hibernateTemplate.save(o);
    }

    // 修改
    @Override
    public void update(Object o) {
        hibernateTemplate.update(o);
    }

    // 修改(在session中已存在这个对象的修改)
    @Override
    public void merge(Object o) {
        hibernateTemplate.merge(o);
    }

    // 根据ID获取对象
    @Override
    public Object getById(Class<?> c, Serializable id) {
        return hibernateTemplate.get(c, id);
    }

    // 删除对象
    @Override
    public void delete(Object o) {
        hibernateTemplate.delete(o);
    }

    // 根据ID删除对象
    @Override
    public void deleteById(Class<?> c, Serializable id) {
        delete(getById(c, id));
    }

    // 获取所有的记录
    @Override
    public List<?> getAll(Class<?> c) {
        return hibernateTemplate.find("from " + c.getName());
    }

    // 批量修改
    @Override
    public void bulkUpdate(String hql, Object... objects) {
        hibernateTemplate.bulkUpdate(hql, objects);
    }

    // 得到唯一值
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object getUnique(final String hql, final Object... objects) {
        return hibernateTemplate.execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                if (objects != null)
                    for (int i = 0; i < objects.length; i++)
                        query.setParameter(i, objects[i]);
                return query.uniqueResult();
            }
        });
    }

    // 分页查询
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<?> pageQuery(final String hql, final Integer page, final Integer size, final Object... objects) {
        return (List<?>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                if (objects != null)
                    for (int i = 0; i < objects.length; i++) {
                        query.setParameter(i, objects[i]);
                    }
                if (page != null && size != null)
                    query.setFirstResult((page - 1) * size).setMaxResults(size);
                return query.list();
            }
        });
    }

    // 不分页查询
    @Override
    public List<?> pageQuery(String hql, Object... objects) {
        return pageQuery(hql, null, null, objects);
    }

    // 不分页查询,参数传递List
    @Override
    public List<?> pageQuery(String hql, List<Object> objList) {
        return pageQuery(hql, null, null, objList);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<?> pageQuery(final String hql, final Integer page, final Integer size, final List<Object> objList) {
        return (List<?>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                if (objList != null) {
                    for (int i = 0; i < objList.size(); i++) {
                        query.setParameter(i, objList.get(i));
                    }
                }
                if (page != null && size != null) {
                    query.setFirstResult((page - 1) * size).setMaxResults(size);
                }
                return query.list();
            }
        });
    }

    @Override
    public void save(Object o) {
        hibernateTemplate.save(o);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Integer update(final String hql, final Object... objects) {
        return (Integer) hibernateTemplate.execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                if (objects != null)
                    for (int i = 0; i < objects.length; i++)
                        query.setParameter(i, objects[i]);
                return query.executeUpdate();
            }
        });
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Integer update(final String hql, final List<Object> objList) {
        return (Integer) hibernateTemplate.execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                if (objList != null) {
                    for (int i = 0; i < objList.size(); i++) {
                        query.setParameter(i, objList.get(i));
                    }
                }
                return query.executeUpdate();
            }
        });
    }

    /**
     * whereParameter where 语句拼接 parameters 参数
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<?> queryList(String[] whereParameter, final Object[] parameters) {
        final StringBuffer hql = new StringBuffer();
        hql.append("FROM " + clazz.getSimpleName() + " o ");
        if (whereParameter.length > 0) {
            hql.append(" where 1 = 1");
        }
        for (String str : whereParameter) {
            hql.append(" and o." + str + " = ? ");
        }

        // hibernateTemplate.executeWithNativeSession(action)
        return (List<?>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query listQuery = session.createQuery(hql.toString());
                for (int i = 0; i < parameters.length; i++) {
                    listQuery.setParameter(i, parameters[i]);
                }
                return listQuery.list();

            }
        });
    }

    /**
     * 查询记录条数
     * 
     * @param pageNum
     *            当前页
     * @param queryListHQL
     *            查询HQL
     * @param parameters
     *            传递的参数
     * @return
     */
    @Override
    public Long getCount(String queryListHQL, List<Object> parameters) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        // 查询总记录数
        Query countQuery = session.createQuery("SELECT COUNT(*) " + queryListHQL);
        if (parameters != null) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                countQuery.setParameter(i, parameters.get(i));
            }
        }
        return (Long) countQuery.uniqueResult(); // 执行查询
    }

    /**
     * 获取匹配记录数
     * 
     * @param hqlHelper
     *            hql生成工具
     * @return
     */
    @Override
    public List<?> queryList(HqlHelper hqlHelper) {
        List<Object> parameters = hqlHelper.getParameters();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        // 查询本页的数据列表
        Query listQuery = session.createQuery(hqlHelper.getQueryListHql());
        if (parameters != null && parameters.size() > 0) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                listQuery.setParameter(i, parameters.get(i));
            }
        }
        return listQuery.list(); // 返回查询结果
    }

    /**
     * 查询记录条数
     * 
     * @param hqlHelper
     *            hql生成工具
     * @return
     */
    @Override
    public Long getCount(HqlHelper hqlHelper) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        // 查询总记录数
        List<Object> parameters = hqlHelper.getParameters();
        Query countQuery = session.createQuery("SELECT COUNT(*) " + hqlHelper.getQueryCountHql());
        if (parameters != null) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                countQuery.setParameter(i, parameters.get(i));
            }
        }
        return (Long) countQuery.uniqueResult(); // 执行查询
    }

    /**
     * 公共的查询分页信息的方法
     * 
     * @param pageNum
     *            当前页
     * @param queryListHQL
     *            查询HQL
     * @param parameters
     *            传递的参数
     * @return
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Page getPageBean(int pageNum, String queryListHQL, List<Object> parameters) {
        Integer pageSize = ConfigConsts.PAGE_SIZE;
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        // 查询本页的数据列表
        Query listQuery = session.createQuery(queryListHQL);
        if (parameters != null) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                listQuery.setParameter(i, parameters.get(i));
            }
        }
        listQuery.setFirstResult((pageNum - 1) * pageSize);
        listQuery.setMaxResults(pageSize);
        List list = listQuery.list(); // 执行查询
        // 查询总记录数
        Query countQuery = session.createQuery("SELECT COUNT(*) " + queryListHQL);
        if (parameters != null) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                countQuery.setParameter(i, parameters.get(i));
            }
        }
        Long count = (Long) countQuery.uniqueResult(); // 执行查询
        return new Page(pageNum, pageSize, count.intValue(), list);
    }

    /**
     * 分页查询
     * 
     * @param pageNum
     *            当前页
     * @param pageSize
     *            每页个数
     * @param hqlHelper
     *            hql工具
     * @return
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Page getPageBean(Integer pageNum, Integer pageSize, HqlHelper hqlHelper) {
        if (pageSize == null) {
            pageSize = ConfigConsts.PAGE_SIZE;
        }
        List<Object> parameters = hqlHelper.getParameters();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        // 查询本页的数据列表
        String hql = hqlHelper.getQueryListHql();
        Query listQuery = session.createQuery(hql);
        if (parameters != null && parameters.size() > 0) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                listQuery.setParameter(i, parameters.get(i));
            }
        }
        listQuery.setFirstResult((pageNum - 1) * pageSize);
        listQuery.setMaxResults(pageSize);
        List list = listQuery.list(); // 执行查询
        // 查询总记录数
        Query countQuery = session.createQuery(hqlHelper.getQueryCountHql());
        if (parameters != null && parameters.size() > 0) { // 设置参数
            for (int i = 0; i < parameters.size(); i++) {
                countQuery.setParameter(i, parameters.get(i));
            }
        }
        Long count = (Long) countQuery.uniqueResult(); // 执行查询
        return new Page(pageNum, pageSize, count.intValue(), list);
    }

    /**
     * 获取ids 的业务数据
     * 
     * @param ids
     * @return
     */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> getByIds(final Long[] ids) {
        if (ids == null || ids.length == 0) {
            return Collections.EMPTY_LIST;
        }
        return (List<T>) hibernateTemplate.execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                return session.createQuery("FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)")
                        .setParameterList("ids", ids)//
                        .list();
            }
        });
    }

    @Override
    public Object getById(Serializable id) {
        return getById(clazz, id);
    }

    @Override
    public void deleteById(Serializable id) {
        deleteById(clazz, id);
    }

    @Override
    public List<?> getAll() {
        return hibernateTemplate.find("from " + clazz.getSimpleName());
    }

    @Override
    public void saveOrUpdate(T entity) {
        hibernateTemplate.saveOrUpdate(entity);
    }

    /**
     * 批量删除文件 <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see cn.com.sinosoft.nfwjs.common.dao.BaseDao#delBatch(java.lang.String[],
     *      java.lang.String)
     */
    @Override
    public int delBatch(String selectFlag, String table) {
        // 数组中封装的是ID的集合;
        String hql = "";
        hql = "id=" + Integer.parseInt(selectFlag);
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Query q = session.createQuery("delete from " + table + " where " + hql);
        return q.executeUpdate();
    }

    /**
     * 
     * <B>方法名称：修改对象字段属性</B><BR>
     * <B>概要说明：</B><BR>
     * clazz 对象class id 主键值 cloumParameter 对象列字段 parameters 对应列对应值
     */
    @Override
    public void updateCloum(Serializable id, String[] cloumParameter, Object[] parameters) throws Exception {
        Object obj = getById(clazz, id);
        for (int i = 0; i < cloumParameter.length; i++) {
            Field f = clazz.getDeclaredField(cloumParameter[i]);
            f.setAccessible(true);
            f.set(obj, parameters[i]);
        }
        update(obj);
    }

    /**
     * 
     * <B>方法名称：查询model指定字段属性</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param clazz
     *            model class
     * @param id
     *            主键 id
     * @param idCloumName
     *            主键字段
     * @param cloum
     *            查询的字段
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Object queryCloums(Serializable id, String idCloumName, String cloum) throws Exception {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        if (StringUtils.isBlank(idCloumName)) {
            Object obj = getById(clazz, id);
            PropertyDescriptor pd = new PropertyDescriptor(cloum, clazz);
            Method getMethod = pd.getReadMethod();// 获得get方法
            Object objCloum = getMethod.invoke(obj);// 执行get方法返回一个Object
            return objCloum;
        }
        else {
            String hql = "select " + cloum + " from " + clazz.getSimpleName() + " where  " + idCloumName + " = ? ";
            Query query = session.createQuery(hql);
            query.setParameter(0, id);
            List list = query.list();
            if (list == null || list.size() == 0) {
                return null;
            }
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                return iterator.next();
            }
            return null;
        }
    }

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：通过jdbc获取结果集</B><BR>
     * 
     * @param sql
     *            查询SQL
     * @param cloumParameter
     *            查询字段名称
     * @param parameters
     *            查询字段值
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> getJdbcData(final String sql, final Object[] parameters) throws Exception {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        ResultSet resultSet = session.doReturningWork(new ReturningWork<ResultSet>() {
            @Override
            public ResultSet execute(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql);
                for (int i = 0; i < parameters.length; i++) {
                    ps.setObject(i + 1, parameters[i]);
                }
                ResultSet resultSet = ps.executeQuery();
                return resultSet;
            }
        });
        return resultSetToList(resultSet);
    }

    /**
     * 
     * <B>方法名称：修改Blob数据</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param tableName
     *            表名
     * @param blobCloumName
     *            blob字段名
     * @param whereCloumNames
     *            匹配字段
     * @param whereParameters
     *            匹配字段值
     * @param data
     *            data 数据
     * @throws Exception
     */
    @Override
    public void updateBlobData(final String tableName, final String blobCloumName, final String[] whereCloumNames,
            final String[] whereParameters, final byte[] data) throws Exception {
        updateLobData("1", tableName, blobCloumName, whereCloumNames, whereParameters, data);
    }

    /**
     * 
     * <B>方法名称：修改Clob数据</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param tableName
     *            表名
     * @param clobCloumName
     *            clob字段名
     * @param whereCloumNames
     *            匹配字段
     * @param whereParameters
     *            匹配字段值
     * @param data
     *            data 数据
     * @throws Exception
     */
    @Override
    public void updateClobData(final String tableName, final String clobCloumName, final String[] whereCloumNames,
            final String[] whereParameters, final byte[] data) throws Exception {
        updateLobData("2", tableName, clobCloumName, whereCloumNames, whereParameters, data);
    }

    /**
     * 
     * <B>方法名称：修改流文件</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param type
     *            1 blob 2 clob
     * @param tableName
     * @param id
     * @param idCloumName
     * @param cloum
     * @param data
     * @throws Exception
     */
    private void updateLobData(final String type, final String tableName, final String lobCloumName,
            final String[] whereCloumNames, final String[] whereParameters, final byte[] data) throws Exception {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        session.doWork(new Work() {
            @SuppressWarnings("deprecation")
			@Override
            public void execute(Connection connection) throws SQLException {// 注意不要close了这个connection。
                String func = "";
                if ("2".equals(type)) {
                    func = "EMPTY_CLOB()";
                }
                else {
                    func = "EMPTY_BLOB()";
                }
                StringBuffer updateSql = new StringBuffer();
                StringBuffer whereSql = new StringBuffer();
                updateSql.append("UPDATE " + tableName + " SET " + lobCloumName + " = " + func + " WHERE 1=1 ");
                for (int i = 0; i < whereCloumNames.length; i++) {
                    whereSql.append(" and  " + whereCloumNames[i] + " = ?");
                }
                updateSql.append(whereSql);
                PreparedStatement ps = connection.prepareStatement(updateSql.toString());
                for (int i = 0; i < whereParameters.length; i++) {
                    ps.setObject(i + 1, whereParameters[i]);
                }
                ps.execute();

                PreparedStatement ps2 = connection.prepareStatement("SELECT " + lobCloumName + " FROM " + tableName
                        + " WHERE 1=1 " + whereSql.toString() + " FOR UPDATE NOWAIT");
                for (int i = 0; i < whereParameters.length; i++) {
                    ps2.setObject(i + 1, whereParameters[i]);
                }
                ResultSet rs = ps2.executeQuery();
                OutputStream blobOutputStream = null;
                Writer outStream = null;
                if (rs != null && rs.next()) {
                    try {
                        if ("2".equals(type)) {
                            oracle.sql.CLOB mapClob = (oracle.sql.CLOB) rs.getClob(1);
                            outStream = mapClob.getCharacterOutputStream();
                            // data是传入的字符串，定义：String data
                            String c = data.toString();
                            outStream.write(c);
                            outStream.close();
                        }
                        else {
                            Blob mapBlob = rs.getBlob(1);
                            blobOutputStream = ((oracle.sql.BLOB) mapBlob).getBinaryOutputStream();
                            blobOutputStream.write(data);
                            blobOutputStream.close();
                        }
                        connection.commit();
                    }
                    catch (IOException e) {
                        throw new SQLException("流转换异常", e);
                    }
                    finally {
                        try {
                            if (blobOutputStream != null) {
                                blobOutputStream.close();
                            }
                            if (outStream != null) {
                                outStream.close();
                            }

                        }
                        catch (IOException e) {
                            throw new SQLException("流转换异常", e);
                        }
                    }
                }
            }
        });
    }

    /**
     * 
     * <B>方法名称：将ResultSet结果集转化为List<Map<String, Object>> 格式</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param rs
     *            数据库查询结果集
     * @return
     * @throws SQLException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        List<String> colNameList = new ArrayList<String>();
        for (int i = 0; i < colCount; i++) {
            colNameList.add(rsmd.getColumnName(i + 1));
        }
        while (rs.next()) {
            Map map = new HashMap<String, Object>();
            for (int i = 0; i < colCount; i++) {
                String key = colNameList.get(i);
                Object value = rs.getObject(key);
                map.put(key, value);
            }
            results.add(map);
        }
        return results;
    }

    /**
     * 根据sql查询实体
     */
    @Override
    public Object findList(CharSequence queryString, Class<?> c, Object... params) {
        SQLQuery query = getCurrentSession().createSQLQuery(queryString.toString()).addEntity(c);
        for (int i = 0; i < params.length; ++i) {
            query.setParameter(i, params[i]);
        }
        return query.uniqueResult();
    }
}
