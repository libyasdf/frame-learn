package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.services;

import java.util.List;

import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.entity.TakeOutDetail;

import net.sf.json.JSONObject;

/**
 * 外卖详情service
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:12:50
 */
public interface TakeOutDetilService {
	
	/**
	 * 根据外卖和门类id获取所有的商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月10日 上午9:12:48
	 * @param infoId
	 * @param classId
	 * @return
	 */
	List<TakeOutDetail> getDetilByInfoId(String infoId, String classId);
	
	 /**
	  * 获取外卖所有的门类
	  * TODO 
	  * @author 马秋霞
	  * @Date 2019年5月10日 上午11:00:54
	  * @param infoId
	  * @return
	  */
	List<Category> getCategoryByInfoId(String infoId);
	
	/**
	 * 设置外卖的商品列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月10日 下午3:06:30
	 * @param takeOutId
	 * @param detil
	 */
	void setDetilInfo(String takeOutId, String detil);
	
	/**
	 * 删除外卖的商品信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 上午9:14:14
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 根据外卖id获取外卖详情
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 上午10:07:19
	 * @param infoId
	 * @return
	 */
	List<TakeOutDetail> getDetil(String infoId);
	
	/**
	 * 排序
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月14日 下午4:51:48
	 * @param id
	 */
	void sort(String[] id);
	
	/**
	 * 修改外卖的详情
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月23日 上午10:53:11
	 * @param detil
	 */
	void updateDetil(TakeOutDetail detil,String flg);
	
	/**
	 * 是否可以修改库存数量
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年6月12日 上午10:58:46
	 * @param goodsNum
	 */
	JSONObject judgeUpdateGoodsNum( String takeOutId,String goodsId,Integer goodsNum);
	
	
	

}
