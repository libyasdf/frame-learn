package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.services;

import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;

import net.sf.json.JSONObject;

public interface CategoryService {
	
	/**
	 * 根据id获取门类
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 上午9:11:13
	 * @param id
	 * @return
	 */
	Category getCategoryCategById(String id);
	
	/**
	 * 保存门类
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 上午11:10:29
	 * @param entity
	 * @param isFirst
	 * @return
	 */
	Category saveFroms(Category entity);
	
	/**
	 * 获取门类树
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 上午11:21:21
	 * @param columnName
	 * @param userId
	 * @return
	 */
	JSONObject getCategoryTree();
	
	/**
	 * 根据门类id删除门类
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 下午4:07:12
	 * @param ids
	 * @return
	 */
	int delete(String ids);
	
	

}
