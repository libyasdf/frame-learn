package com.sinosoft.sinoep.modules.mypage.wmgl.index.order.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.order.entity.OrderInfo;

import net.sf.json.JSONObject;

/**
 * 订单service
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月15日 上午9:51:17
 */
public interface OrderInfoService {
	
	/**
	 * 下单操作
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月15日 上午9:56:49
	 * @param takeOutId
	 */
	String placeOrder(String takeOutId,String title,String phone,String cartgoodsInfo,String orderId);

	/**
	 * TODO  查询订单分页数据
	 * @author 李颖洁  
	 * @date 2019年5月15日下午1:33:49
	 * @param pageImpl
	 * @param status
	 * @param cardNo
	 * @param orderNum
	 * @param takeOutId
	 * @return PageImpl
	 */
	PageImpl getOrderList(PageImpl pageImpl, String status, String cardNo, String orderNum,String takeOutId);
	
	/**
	 * 修改订单状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月16日 上午10:19:51
	 * @param id
	 * @param status
	 */
	void updateState(String ids, String orderUserId,String status);

	/**
	 *  TODO 根据外卖id查询订单数据
	 * @author 李颖洁  
	 * @date 2019年5月16日下午2:49:59
	 * @param status
	 * @param cardNo
	 * @param orderNum
	 * @param takeOutId
	 * @return List<OrderInfo>
	 */
	List<OrderInfo> getOrderDate(String status, String cardNo, String orderNum, String takeOutId);
	
	 /**
	    * 根据订单id查询订单详情信息
	    * TODO 
	    * @author 马秋霞
	    * @Date 2019年5月20日 上午9:21:53
	    * @param id
	    * @return
	    */
	OrderInfo getById(String id);

	/** TODO 获取当前用户订单列表
	 * @author 李颖洁  
	 * @param endDate 
	 * @param startDate 
	 * @param pageImpl 
	 * @param pageable 
	 * @date 2019年5月22日下午5:12:44
	 * @return OrderInfo
	 */
	PageImpl getMyOrderList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate);
	
	/**
	 * 获取商品总个数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年6月5日 下午3:27:27
	 * @param takeOutId
	 * @return
	 */
	Integer getGoodsNum(String takeOutId,String categoryId);

	/**
	 *  TODO 删除订单
	 * @author 李颖洁  
	 * @date 2019年6月12日上午11:04:09
	 * @param id
	 * @return int
	 */
	int delete(String id);
	
	 /**
	    * 获取当前用户某次外卖的订单id和手机号
	    * TODO 
	    * @author 马秋霞
	    * @Date 2019年6月13日 下午5:00:24
	    * @param takeOutId
	    * @return
	    */
	JSONObject getOrderIdAndPhone(String takeOutId);


   
	
}
