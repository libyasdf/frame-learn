package com.sinosoft.sinoep.modules.system.config.utillink.service;

import java.util.List;

import com.sinosoft.sinoep.modules.system.config.utillink.entity.UtilLink;

/**
 * 
 * TODO 常用工具service
 * @author 冯超
 * @Date 2018年5月10日 下午7:03:28
 */
public interface UtilLinkService {
	
	/**
	 * 
	 * TODO 获取常用工具信息
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:58:41
	 * @param id
	 * @return
	 */
	UtilLink getById(String id);
	
	/**
	 * 
	 * TODO 保存常用工具信息
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:02:27
	 * @param utilLink
	 * @return
	 */
	UtilLink saveForm(UtilLink utilLink);
	
	/**
	 * 
	 * TODO 查询常用工具列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:11:36
	 * @param utilLink
	 * @return
	 */
	List<UtilLink> getList(UtilLink utilLink);
	
	/**
	 * 
	 * TODO 查询不包含本条数据的列表
	 * @author 冯超
	 * @Date 2018年5月11日 上午11:41:29
	 * @param utilLink
	 * @return
	 */
	List<UtilLink> getListExcludeId(UtilLink utilLink);
	
	/**
	 * 
	 * TODO 删除常用工具
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:55:27
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 
	 * TODO 常用工具排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:17:28
	 * @param ids
	 */
	void sort(String[] ids);

}
