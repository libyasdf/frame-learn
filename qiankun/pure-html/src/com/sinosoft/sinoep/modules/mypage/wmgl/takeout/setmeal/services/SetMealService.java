package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.entity.SetMeal;

/**
 * 组合service
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:12:50
 */
public interface SetMealService {
	
	/**
	 * TODO 根据外卖和门类id获取所有的套餐详情
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:14:50
	 * @param infoId 外卖id
	 * @param classId 门类id
	 * @return List<SetMeal>
	 */
	List<SetMeal> getDetilByInfoId(String infoId, String classId);
	
	/**
	 * TODO 根据外卖id获取套餐中所有的门类
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:15:28
	 * @param infoId 外卖id
	 * @return List<Category>
	 */
	List<Category> getCategoryByInfoId(String infoId);
	
	/**
	 * TODO 根据套餐组合id删除数据
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:27:45
	 * @param id
	 * @return int
	 */
	int delete(String id);

	/** 
	 * TODO 保存套餐组合
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:35:18
	 * @param meal
	 * @return SetMeal
	 */
	int saveForm(SetMeal meal);
	
	/**
	 * TODO 套餐组合
	 * @author 马秋霞  
	 * @date 2019年5月28日下午1:50:00
	 * @param takeOutId
	 * @param categoryId
	 * @param goodsIds
	 * @param goodsNames
	 * @return void
	 */
	void CombineGoods(String takeOutId,String categoryId, String goodsIds,String goodsNames);

	/** 
	 * TODO 
	 * @author 李颖洁  
	 * @date 2019年5月27日下午5:12:12
	 * @param pageable
	 * @param pageImpl
	 * @param infoId
	 * @param classId
	 * @return PageImpl
	 */
	PageImpl getListByClassId(Pageable pageable, PageImpl pageImpl, String infoId, String classId);
	
	

}
