package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.entity.TakeOutInfo;

import net.sf.json.JSONObject;

/**
 * 外卖service
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:12:50
 */
public interface TakeOutInfoService {
	
	/**
	 * 获取外卖标题
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午2:15:53
	 * @return
	 */
	String getTitle();
	
	/**
	 * 保存外卖信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午4:00:21
	 * @param entity
	 * @return
	 */
	TakeOutInfo saveFrom(TakeOutInfo entity);
	
	/**
	 * 删除外卖信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午4:06:56
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	  /**
	   * 外卖管理打开只读、修改页面时，查询数据进行渲染 TODO
	   * TODO 
	   * @author 马秋霞
	   * @Date 2019年5月8日 下午1:40:13
	   * @param id
	   * @return
	   */
	TakeOutInfo getById(String id);
	
	/**
	 * 外卖分页列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午4:47:03
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param takeFoodTime
	 * @param status
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String title, String startDate,String endDate,String startDate1,String endDate1, String status);
	
	/**
	 * 获取可订餐的外卖列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午7:10:04
	 * @return
	 */
	List<TakeOutInfo> getList();
	
	/**
	 * 置顶排序
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月14日 下午4:40:07
	 * @param ids
	 * @return
	 */
	int orderZd(String ids);
	
	/**
	 * 查询该订单是否已超过最晚下单时间，是否已下单
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月17日 下午7:07:58
	 * @param takeOutId
	 * @return
	 */
	List<Map<String,Object>>  findById(String takeOutId);
	
	/**
	 * 外卖的复用
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月22日 上午11:32:00
	 * @param id
	 */
	void reuse(String id);
	
	/**
	 * 有哪些商品超过了库存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月24日 上午9:26:39
	 * @param goodsId
	 * @return
	 */
	JSONObject surpassRepertory(String takeOutId,String cartgoodsInfo,String orderId);
	
	
	

}
