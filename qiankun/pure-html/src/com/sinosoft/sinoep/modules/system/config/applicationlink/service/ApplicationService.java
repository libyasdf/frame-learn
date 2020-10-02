package com.sinosoft.sinoep.modules.system.config.applicationlink.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.config.applicationlink.entity.Application;

/**
 * 
 * TODO 导航
 * @author 冯超
 * @Date 2018年5月7日 上午11:58:09
 */
public interface ApplicationService {
	
	/**
	 * 
	 * TODO 获取导航信息
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:58:41
	 * @param id
	 * @return
	 */
	Application getById(String id);
	
	/**
	 * 
	 * TODO 保存导航信息
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:02:27
	 * @param application
	 * @return
	 */
	Application saveForm(Application application);
	
	/**
	 * 
	 * TODO 查询导航列表
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:28:14
	 * @param pageable
	 * @param pageImpl
	 * @param name
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String name);
	
	/**
	 * 
	 * TODO 查询导航列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:11:36
	 * @param application
	 * @return
	 */
	List<Application> getList(Application application);
	
	/**
	 * 
	 * TODO 删除导航
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:55:27
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 
	 * TODO 导航排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:17:28
	 * @param ids
	 */
	void sort(String[] ids);

}
