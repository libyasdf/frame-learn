package com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.entity.OrderDetil;

/**
 * 订单详情service
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:12:50
 */
public interface OrderDetilService {
	
	/**
	 * 获取某次外卖订单的门类
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月15日 下午4:09:24
	 * @param infoId
	 * @return
	 */
	List<Category> getOrderCategory(String takeOutId);
	
	/**
	 * 查询此次外卖的汇总信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月15日 下午5:26:06
	 * @param pageImpl
	 * @param takeOutId
	 * @return
	 */
	PageImpl getCollectInfo(PageImpl pageImpl, String takeOutId,String categoryId);
	
	/**
	 * 商品汇总的导出
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月17日 上午11:36:52
	 * @param takeOutId
	 * @param categoryId
	 * @return
	 */
	List<OrderDetil> getList(String takeOutId, String categoryId);
	
	/**
	 * 根据订单id获取订单详情
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月21日 下午4:10:12
	 * @param pageable
	 * @param pageImpl
	 * @param orderId
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String orderId);

	/** 
	 * TODO 修改商品数量
	 * @author 李颖洁  
	 * @date 2019年5月23日下午2:14:10
	 * @param orderDetil
	 * @return OrderDetil
	 */
	OrderDetil updateOrderDetil(OrderDetil orderDetil);

	/** 
	 * TODO 删除商品
	 * @author 李颖洁  
	 * @date 2019年5月23日下午4:10:23
	 * @param id
	 * @return int
	 */
	int deleteById(String id);
	

}
