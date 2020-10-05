package com.sinosoft.sinoep.modules.mypage.wmgl.index.index.services;

import java.util.List;

import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;


/**
 * 首页service
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:12:50
 */
public interface IndexService {
	
	/**
	 * 获取首页外卖的所有商品
	 * TODO 
	 * @author 马秋霞
	 * @param <Category>
	 * @Date 2019年5月14日 上午9:46:05
	 * @return
	 */
	List<Category> getWmGoods(String takeOutId);
	

	
	
	

}
