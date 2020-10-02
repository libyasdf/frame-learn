package com.sinosoft.sinoep.common.jpa.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * JpaRepositoryFactory的自定义工厂类，用于创建自定义的JpaRepositoryFactory实现。
 * 
 * @author <a href="http://weibo.com/weibowarning">王宁</a><br/>
 * @date 2013-6-24
 * @param <R>
 * @param <T>
 * @param <I>
 */
public class RepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean
	 * #afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean
	 * #createRepositoryFactory(javax.persistence.EntityManager)
	 */
	protected RepositoryFactorySupport createRepositoryFactory(
			EntityManager entityManager) {
		return new MyRepositoryFactory<T, I>(entityManager);
	}

	/**
	 * JPA specific generic repository 的工厂类。
	 * 
	 * @author <a href="http://weibo.com/weibowarning">王宁</a><br/>
	 * @date 2013-6-24
	 * @param <T>
	 *            实体类型
	 * @param <I>
	 *            实体主键类型
	 */
	private static class MyRepositoryFactory<T, I extends Serializable> extends
			JpaRepositoryFactory {
		private EntityManager entityManager;

		public MyRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
			this.entityManager = entityManager;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.data.jpa.repository.support.JpaRepositoryFactory
		 * #getTargetRepository
		 * (org.springframework.data.repository.core.RepositoryMetadata)
		 */
		@SuppressWarnings("unchecked")
		protected Object getTargetRepository(RepositoryMetadata metadata) {
			JpaEntityInformation<T, I> entityInformation = (JpaEntityInformation<T, I>) JpaEntityInformationSupport
					.getMetadata(metadata.getDomainType(), entityManager);
			SimpleJdbcRepository<T, I> repo = new SimpleJdbcRepository<T, I>(
					entityInformation, entityManager);
			return repo;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.data.jpa.repository.support.JpaRepositoryFactory
		 * #getRepositoryBaseClass
		 * (org.springframework.data.repository.core.RepositoryMetadata)
		 */
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return SimpleJdbcRepository.class;
		}

	}
}