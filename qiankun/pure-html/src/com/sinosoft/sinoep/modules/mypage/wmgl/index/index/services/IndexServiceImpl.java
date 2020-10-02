package com.sinosoft.sinoep.modules.mypage.wmgl.index.index.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.entity.Goods;
import com.sinosoft.sinoep.user.util.UserUtil;



/**
 * 首页serviceImp
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月9日 上午11:53:03
 */
@Service
public class IndexServiceImpl implements IndexService{
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	/**
	 * 获取首页商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月14日 上午9:55:15
	 * @return
	 */
	@Override
	public List<Category> getWmGoods(String takeOutId) {
		Map<String,String> mealMap = new HashMap<String,String>();//存放套餐组合的map,key为套餐id,value为套餐名称
		Map<String,Category> categoryMap = new HashMap<String,Category>();//存放门类的key-value,key为门类id,value为门类对象
		List<Category> result = new ArrayList<Category>();//用于最终封装后的返回结果
		StringBuilder sql = new StringBuilder(" select t.class_id classId,t.class_name typeName,t.goods_id goodsId,t.AMOUNT_LIMIT Limit,t.buy_limit buyLimit,t.buy_num buyNum,t.goods_name goodsName,t.goods_price price,(t.goods_num-nvl(t4.goodsNum,0)) goodsNum, ");
		sql.append(" t.valuation_unit valuationUnit,t.buy_unit buyUnit,t1.describe,t2.save_path imgPath, t5.id mealId,t5.optNum mealOptNum,t5.goods_ids mealGoodsIds,case when t3.goods_num is null then 0 else t3.goods_num end totalNumber ");
		sql.append(" from wmgl_take_out_detail t ");
		sql.append(" left outer join wmgl_goods t1 on t.goods_id=t1.id left outer join sys_affix t2 on t1.id=t2.table_id ");
		sql.append(" left outer join (select * from wmgl_shopping_cart where cre_user_id='" + UserUtil.getCruUserId() + "' and take_out_id= ?) t3 on t3.goods_id=t.goods_id ");
		sql.append(" left outer join (select t1.goods_id,sum(t1.GOODS_NUM) goodsNum from wmgl_order_info t left outer join wmgl_order_detil t1 on t.id=t1.order_id ");
		sql.append(" where t.visible='1' and t.status !='2' and t.take_out_id= ? group by t1.goods_id)t4 on t.goods_id=t4.goods_id ");
		
		sql.append(" left outer join ( select distinct * from ( select regexp_substr(q.goods_ids, '[^,]+', 1, Level,'i') goods_id,q.goods_ids,q.id,q.opt_num optNum  from ( ");
		sql.append(" select * from wmgl_set_meal where take_out_id = ?  ) q ");
		sql.append(" connect by Level <= LENGTH(q.goods_ids) - LENGTH(REGEXP_REPLACE(q.goods_ids, ',', '')) + 1) t order by t.id )t5 on t.goods_id=t5.goods_id ");
		
		sql.append(" where t.visible='1' and t.take_out_id= ?  order by t5.id,t.category_sort ,t.sort  ");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString(), takeOutId,takeOutId,takeOutId,takeOutId);
		int index = 1;//第几个套餐
		for (Map<String, Object> map : list) {
			Category category = categoryMap.get(map.get("CLASSID"));
			if(category==null) {
				Category temCategory = new Category();
				categoryMap.put(map.get("CLASSID").toString(), temCategory);
				result.add(temCategory);
				temCategory.setId(map.get("CLASSID").toString());
				temCategory.setTypeName(map.get("TYPENAME").toString());
				List<Goods> goodsList = temCategory.getGoodList();
				Goods goods = new Goods();
				goodsList.add(goods);
				goods.setId(map.get("GOODSID").toString());
				goods.setBelongId(map.get("CLASSID").toString());
				goods.setCategoryName(map.get("TYPENAME").toString());
				goods.setGoodsName(map.get("GOODSNAME").toString());
				goods.setPrice(Double.valueOf(map.get("PRICE").toString()));
				goods.setValuationUnit(map.get("VALUATIONUNIT").toString());
				goods.setBuyUnit(map.get("BUYUNIT").toString());
				if(map.get("BUYLIMIT")!=null) {
					goods.setBuyLimit(map.get("BUYLIMIT").toString());
				}else {
					goods.setBuyLimit("0");
				}
				if(map.get("BUYNUM")!=null) {
					String buyNum = map.get("BUYNUM").toString();
					goods.setBuyNum(Integer.valueOf(buyNum));
				}
				if(map.get("DESCRIBE")!=null) {
					goods.setDescribe(map.get("DESCRIBE").toString());
				}
				if(map.get("LIMIT")!=null) {
					goods.setAmountLimit(map.get("LIMIT").toString());
				}
				if(map.get("MEALID")!=null) {
					goods.setMealId(map.get("MEALID").toString());
					if(StringUtils.isNotBlank(mealMap.get(map.get("MEALID").toString()))) {
						goods.setMealName(mealMap.get(map.get("MEALID").toString()));
					}else {
						goods.setMealName("套餐"+index);
						mealMap.put(map.get("MEALID").toString(), "套餐"+index);
						index++;
					}
				}
				if(map.get("MEALOPTNUM")!=null) {
					goods.setMealOptNum(Integer.valueOf(map.get("MEALOPTNUM").toString()));
				}
				if(map.get("MEALGOODSIDS")!=null) {
					String mealGoodsIds = map.get("MEALGOODSIDS").toString();
					String[] mealGoods = mealGoodsIds.split(",");
					goods.setMealGoodsIds(mealGoodsIds);
					goods.setMealGoodsNum(mealGoods.length);
				}
				if(map.get("IMGPATH")!=null) {
					String imagePath = map.get("IMGPATH").toString();
					imagePath = imagePath.substring(imagePath.indexOf("upload")-1);
					goods.setImgPath(imagePath);
				}
				if(map.get("GOODSNUM")!=null) {
					String goodsNum = map.get("GOODSNUM").toString();
					goods.setRepertory(Integer.valueOf(goodsNum));
				}
				goods.setGoodsNum(Integer.valueOf(map.get("TOTALNUMBER").toString()));
				
			}else {
				List<Goods> goodsList = category.getGoodList();
				Goods goods = new Goods();
				goodsList.add(goods);
				goods.setBelongId(map.get("CLASSID").toString());
				goods.setId(map.get("GOODSID").toString());
				goods.setGoodsName(map.get("GOODSNAME").toString());
				goods.setPrice(Double.valueOf(map.get("PRICE").toString()));
				goods.setValuationUnit(map.get("VALUATIONUNIT").toString());
				goods.setCategoryName(map.get("TYPENAME").toString());
				goods.setBuyUnit(map.get("BUYUNIT").toString());
				if(map.get("BUYLIMIT")!=null) {
					goods.setBuyLimit(map.get("BUYLIMIT").toString());
				}
				if(map.get("BUYNUM")!=null) {
					String buyNum = map.get("BUYNUM").toString();
					goods.setBuyNum(Integer.valueOf(buyNum));
				}
				if(map.get("DESCRIBE")!=null) {
					goods.setDescribe(map.get("DESCRIBE").toString());
				}
				if(map.get("IMGPATH")!=null) {
					String imagePath = map.get("IMGPATH").toString();
					imagePath = imagePath.substring(imagePath.indexOf("upload")-1);
					goods.setImgPath(imagePath);
				}
				if(map.get("LIMIT")!=null) {
					goods.setAmountLimit(map.get("LIMIT").toString());
				}
				if(map.get("MEALID")!=null) {
					goods.setMealId(map.get("MEALID").toString());
					if(StringUtils.isNotBlank(mealMap.get(map.get("MEALID").toString()))) {
						goods.setMealName(mealMap.get(map.get("MEALID").toString()));
					}else {
						goods.setMealName("套餐"+index);
						mealMap.put(map.get("MEALID").toString(), "套餐"+index);
						index++;
					}
				}
				if(map.get("MEALOPTNUM")!=null) {
					goods.setMealOptNum(Integer.valueOf(map.get("MEALOPTNUM").toString()));
				}
				if(map.get("MEALGOODSIDS")!=null) {
					String mealGoodsIds = map.get("MEALGOODSIDS").toString();
					String[] mealGoods = mealGoodsIds.split(",");
					goods.setMealGoodsIds(mealGoodsIds);
					goods.setMealGoodsNum(mealGoods.length);
				}
				if(map.get("GOODSNUM")!=null) {
					String goodsNum = map.get("GOODSNUM").toString();
					goods.setRepertory(Integer.valueOf(goodsNum));
				}
				goods.setGoodsNum(Integer.valueOf(map.get("TOTALNUMBER").toString()));
				
			}
		}
	
		return result;
	}
	
	
	

}
