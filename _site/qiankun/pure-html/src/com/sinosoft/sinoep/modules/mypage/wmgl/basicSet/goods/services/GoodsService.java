package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.entity.Goods;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.entity.GoodsTreeEntity;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.entity.OverTime;

/**
 * 商品类
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:12:50
 */
public interface GoodsService {
	
	/**
	 * 获取某门类下的所有的商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 下午5:12:37
	 * @param categoryId
	 * @return
	 */
	List<Goods> getList(String GoodsName,String isUse,String categoryId);
	
	/**
	 * 保存商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 下午8:58:59
	 * @param entity
	 * @return
	 */
	Goods saveFrom(Goods entity,String flg);
	
	/**
	 * 删除商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月8日 上午9:42:09
	 * @param ids
	 * @return
	 */
	int delete(String ids);
	
	/**
	 * 获取商品的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月8日 下午12:02:17
	 * @param pageable
	 * @param pageImpl
	 * @param goodsName
	 * @param isUse
	 * @param categoryId
	 * @return
	 */
	PageImpl getPageList1(Pageable pageable, PageImpl pageImpl, String goodsName, String isUse, String categoryId) throws Exception;
	
	/**
	 * 根据id查询商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月8日 下午1:41:10
	 * @param id
	 * @return
	 */
	Goods getById(String id);
	
	/**
	 * 获取商品树
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月10日 上午11:49:19
	 * @return
	 */
	List<GoodsTreeEntity> treeList();
	
	

}
