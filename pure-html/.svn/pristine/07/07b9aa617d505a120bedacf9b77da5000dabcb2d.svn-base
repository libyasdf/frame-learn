package com.sinosoft.sinoep.common.jpa.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * {@link BaseRepository}的简单实现类
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
public class SimpleJdbcRepository<T, ID extends Serializable> extends
		SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
	private static final Pattern ORDERBY_PARRERN = Pattern
			.compile(".*\\s+order\\s+by\\s+.*");
	private static final Log log = LogFactory
			.getLog(SimpleJdbcRepository.class);
	private static final boolean LOG_IS_DEBUG = log.isDebugEnabled();
	private EntityManager entityManager;
	private Class<T> entityClass;

	public SimpleJdbcRepository(JpaEntityInformation<T, ?> entityInformation,
			EntityManager em) {
		super(entityInformation, em);
		entityClass = entityInformation.getJavaType();
		this.entityManager = em;
	}

	@Override
	public <D> Page<D> query(String jpql, Pageable pageable, Object... params) {
		jpql = orderBy(jpql, pageable.getSort());
		Query query = entityManager.createQuery(jpql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		Long total = queryCount(jpql, params);
		@SuppressWarnings("unchecked")
		List<D> content = total > pageable.getOffset() ? query.getResultList()
				: Collections.<D> emptyList();
		return new PageImpl<D>(content, pageable, total);
	}

	private long queryCount(String jpql, Object... params) {
		String countQl = QueryUtils.createCountQueryFor(jpql);
		Query query = entityManager.createQuery(countQl);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return Long.parseLong(query.getSingleResult().toString());
	}
	
	private long queryNativeCount(String sql, Object... params){
		sql = String.format("select count(1) from ( %s )", sql);
		Query query = entityManager.createNativeQuery(sql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return Long.parseLong(query.getSingleResult().toString());
	}

	private String orderBy(String jpql, Sort sort) {
		if (sort != null && !hasOrderBy(jpql)) {
			List<String> orderBys = new ArrayList<String>();
			for (Order order : sort) {
				orderBys.add(String.format("%s %s", order.getProperty(), order
						.getDirection().toString()));
			}
			if (orderBys.size() > 0) {
				jpql = String.format("%s ORDER BY %s", jpql,
						StringUtils.join(orderBys.iterator(), ","));
			}
		}
		return jpql;
	}

	private static boolean hasOrderBy(String jpql) {
		return ORDERBY_PARRERN.matcher(jpql.toLowerCase()).matches();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.common.jpa.repository.JpqlAndSqlRepository#update(java.lang
	 * .String)
	 */
	@Override
	public int update(String jpql, Object... params) {
		if (LOG_IS_DEBUG) {
			log.debug(new StringBuilder("UPDATE BY JPQL:").append(jpql)
					.append(";PARAMS:").append(StringUtils.join(params, ",")));
		}
		Query query = entityManager.createQuery(jpql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.common.jpa.repository.JpqlAndSqlRepository#nativeUpdate(
	 * java.lang.String)
	 */
	@Override
	public int nativeUpdate(String sql, Object... params) {
		if (LOG_IS_DEBUG) {
			log.debug(new StringBuilder("UPDATE BY SQL:").append(sql)
					.append(";PARAMS:").append(StringUtils.join(params, ",")));
		}
		Query query = entityManager.createNativeQuery(sql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.executeUpdate();
	}

	@Override
	public <D> Page<D> nativeQuery(String sql, Pageable pageable, 
							Object... params) {
		if (LOG_IS_DEBUG) {
			StringBuilder message = new StringBuilder("QUERY BY SQL:")
					.append(sql).append(";PARAMS:")
					.append(StringUtils.join(params, ","));
			log.debug(message);
		}

		Query query = entityManager.createNativeQuery(sql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		Long total = queryNativeCount(sql, params);
		@SuppressWarnings("unchecked")
		List<D> content = total > pageable.getOffset() ? query.getResultList()
				: Collections.<D> emptyList();
		return new PageImpl<D>(content, pageable, total);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.spring.jpa.repository.BaseRepository#delete(ID[])
	 */
	@Override
	public void delete(ID[] ids) {
		if (ids != null) {
			for (ID id : ids) {
				this.delete(id);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.spring.jpa.repository.BaseRepository#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Class<T> getEntityClass() {
		return entityClass;
	}

	@Override
	public Page<T> getAll(int pageNo, int pageSize, Sort sort) {
		pageNo = pageNo < 0 ? 0 : pageNo;
		Pageable pageable = null;
		if (sort != null) {
			pageable = new PageRequest(pageNo, pageSize, sort);
		} else {
			pageable = new PageRequest(pageNo, pageSize);
		}
		return findAll(pageable);
	}

}