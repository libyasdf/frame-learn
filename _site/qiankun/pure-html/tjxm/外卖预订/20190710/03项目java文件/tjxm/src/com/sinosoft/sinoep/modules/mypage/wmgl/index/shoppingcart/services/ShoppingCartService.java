package com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.services;

import java.util.List;

import com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.entity.ShoppingCart;

/**
 * 购物车service
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:12:50
 */
public interface ShoppingCartService {
	
	/**
	 * 清空购物车
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午5:39:50
	 * @return
	 */
	void clearCart(String takeOutId) ;
	
	/**
	 * 添加购物车
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午6:15:46
	 * @param cart
	 */
	void addCart(ShoppingCart cart);
	
	/**
	 * 获取购物车数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午6:42:05
	 * @param takeOutId
	 * @return
	 */
	List<ShoppingCart> getCartData(String takeOutId);
	

}
