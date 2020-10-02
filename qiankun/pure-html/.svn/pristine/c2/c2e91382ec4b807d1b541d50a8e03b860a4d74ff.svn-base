package com.sinosoft.sinoep.common.jpa.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 支持使用JPQL语句和本地SQL语句进行查询更新操作的
 * {@link org.springframework.data.repository.Repository Repository}.
 * 
 * @param <T>
 *            实体类型
 * @param <ID>
 *            实体主键类型
 * @author <a href="http://weibo.com/weibowarning">王宁</a><br/>
 *         email: <a
 *         href="mailto:childe.wangning@gmail.com">childe.wangning@gmail.com</a>
 * @date 2013-4-7
 */
public interface JpqlAndSqlRepository<T, ID extends Serializable> extends
		JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	/**
	 * 使用JPQL语句进行分页查询
	 * 
	 * @param jpql
	 *            JPQL语句
	 * @param pageable
	 *            分页信息
	 * @param params
	 *            查询参数
	 * @see Pageable
	 */
	<D> Page<D> query(String jpql, Pageable pageable, Object... params);

	/**
	 * 使用SQL语句进行分页查询
	 * 
	 * @param 
	 *            SQL语句
	 * @param pageable
	 *            分页信息
	 * @param params
	 *            查询参数
	 * @see Pageable
	 */
	<D> Page<D> nativeQuery(String sql, Pageable pageable, Object... params);

	/**
	 * 使用JPQL语句进行更新(删除)
	 * 
	 * @param jpql
	 * @return
	 */
	int update(String jpql, Object... params);

	/**
	 * 使用SQL语句进行更新(删除)
	 * 
	 * @param sql
	 * @return
	 */
	int nativeUpdate(String sql, Object... params);

}
