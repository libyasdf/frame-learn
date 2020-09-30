package com.sinosoft.sinoep.common.base;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.sinosoft.sinoep.common.util.HqlHelper;
import com.sinosoft.sinoep.common.util.Page;

/**
 * @author kjx123 基础操作Dao接口
 */
public interface BaseDao<T> {

	public Session getCurrentSession();

	/**
	 * 
	 * <B>方法名称：获取数据连接</B><BR>
	 * <B>概要说明：</B><BR>
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception;

	// 添加
	public void add(T entity);

	// 修改
	public void update(T entity);

	// 修改(在session中已存在这个对象的修改)
	public void merge(T entity);

	// 更新或修改
	public void saveOrUpdate(T entity);

	// 根据ID获取对象，实体为传递的泛型
	public Object getById(Serializable id);

	// 根据ID获取对象
	public Object getById(Class<?> c, Serializable id);

	// 删除对象
	public void delete(T entity);

	// 根据ID删除对象
	public void deleteById(Serializable id);

	// 根据ID删除对象
	public void deleteById(Class<?> c, Serializable id);

	// 获取所有的记录，实体为传递的泛型
	public List<?> getAll();

	// 获取所有的记录
	public List<?> getAll(Class<?> c);

	// 批量修改
	public void bulkUpdate(String hql, Object... objects);

	// 得到唯一值
	public Object getUnique(final String hql, final Object... objects);

	// 分页查询
	public List<?> pageQuery(final String hql, final Integer page, final Integer size, final Object... objects);

	// 不分页查询
	public List<?> pageQuery(String hql, Object... objects);

	public void save(T entity);

	public Integer update(final String hql, final Object... objects);

	public Integer update(final String hql, final List<Object> objList);

	// 不分页查询,参数传递List
	public List<?> pageQuery(String hql, List<Object> objList);

	/**
	 * 获取匹配记录数
	 * 
	 * @param hqlHelper
	 *            hql生成工具
	 * @return
	 */
	public List<?> queryList(HqlHelper hqlHelper);

	// 分页查询,参数传递List
	public List<?> pageQuery(final String hql, final Integer page, final Integer size, final List<Object> objList);

	/**
	 * 查询记录条数
	 * 
	 * @param pageNum
	 *            当前页
	 * @param queryListHQL
	 *            查询HQL前面加上“select count(*) ”变成查询总数量的HQL语句
	 * @param parameters
	 *            传递的参数 参数列表，顺序与HQL中的'?'的顺序一一对应。
	 * @return
	 */
	public Long getCount(String queryListHQL, List<Object> parameters);

	/**
	 * 查询记录条数
	 * 
	 * @param hqlHelper
	 *            hql生成工具
	 * @return
	 */
	public Long getCount(HqlHelper hqlHelper);

	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param pageNum
	 *            当前页
	 * @param queryListHQL
	 *            查询HQL 查询数据列表的HQL语句，如果在前面加上“select count(*) ”就变成了查询总数量的HQL语句了
	 * @param parameters
	 *            传递的参数 参数列表，顺序与HQL中的'?'的顺序一一对应。
	 * @return
	 */
	public Page getPageBean(int pageNum, String queryListHQL, List<Object> parameters);

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
	public Page getPageBean(Integer pageNum, Integer pageSize, HqlHelper hqlHelper);

	/**
	 * 获取ids 的业务数据
	 * 
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(final Long[] ids);

	/**
	 * 批量删除
	 * 
	 * @param selectFlag
	 * @param table
	 */
	public int delBatch(String selectFlag, String table);

	/**
	 * 
	 * <B>方法名称：修改对象字段属性</B><BR>
	 * <B>概要说明：</B><BR>
	 * clazz 对象class id 主键值 cloumParameter 对象列字段 parameters 对应列对应值
	 */
	public void updateCloum(Serializable id, String[] cloumParameter, Object[] parameters) throws Exception;

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
	public Object queryCloums(Serializable id, String idCloumName, String cloum) throws Exception;

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
	public List<Map<String, Object>> getJdbcData(final String sql, final Object[] parameters) throws Exception;

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
	public void updateBlobData(final String tableName, final String blobCloumName, final String[] whereCloumNames,
			final String[] whereParameters, final byte[] data) throws Exception;

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
	public void updateClobData(final String tableName, final String clobCloumName, final String[] whereCloumNames,
			final String[] whereParameters, final byte[] data) throws Exception;

	/**
	 * 
	 * @param queryString
	 *            查询语句
	 * @param params
	 * @return
	 */
	public Object findList(CharSequence queryString, Class<?> c, Object... params);

}
