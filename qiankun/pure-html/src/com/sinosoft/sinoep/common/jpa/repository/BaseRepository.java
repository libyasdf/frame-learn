package com.sinosoft.sinoep.common.jpa.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * Repository(dao)层的父接口，实现实体的CRUD、执行JPQL和本地SQL等功能。
 * 
 * @author <a href="http://weibo.com/weibowarning">王宁</a><br/>
 * @date 2013-6-24
 * @param <T>
 *            实体类型
 * @param <ID>
 *            实体主键类型
 */
public interface BaseRepository<T, ID extends Serializable> extends
		JpqlAndSqlRepository<T, ID> {
	/**
	 * 删除多个实体
	 * 
	 * @param ids
	 *            要删除的实体主键数组
	 */
	void delete(ID[] ids);

	/**
	 * 获取实体类型
	 * 
	 * @return the entity class
	 */
	Class<T> getEntityClass();

	/**
	 * 获取JPA持久化上下文。
	 * <p>
	 * {@link BaseRepository}提供的其他方法能够满足大部分的数据库操作，因此无需手动调用JPA上下文
	 * {@link EntityManager}进行操作，除非是对JPA进行特别精细粒度的高级操作。
	 * 
	 * @return
	 */
	EntityManager getEntityManager();
	
	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示数量
	 * @param sort
	 *            排序
	 * @return
	 */
	Page<T> getAll(int pageNo, int pageSize, Sort sort);
}
