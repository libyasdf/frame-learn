package com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.dao.ShoppingCartDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.entity.ShoppingCart;
import com.sinosoft.sinoep.user.util.UserUtil;


/**
 * 外卖serviceImp
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月9日 上午11:53:03
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	ShoppingCartDao dao;
	
	/**
	 * 清空购物车
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午5:40:19
	 * @return
	 */
	@Override
	public void clearCart(String takeOutId) {
			StringBuilder sb = new StringBuilder(" delete from wmgl_SHOPPING_CART where take_out_id= ? ");
			jdbcTemplate.update(sb.toString(), takeOutId);
		
	}
	
	/**
	 * 添加购物车
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午6:16:40
	 * @param cart
	 */
	@Override
	public void addCart(ShoppingCart cart) {
		List<String> param = new ArrayList<String>();
		param.add(cart.getTakeOutId());
		param.add(cart.getGoodsId());
		StringBuilder querySql = new StringBuilder(" select count(1) cnt from wmgl_SHOPPING_CART t where t.take_out_id=? and t.goods_id=? and t.cre_user_id='" + UserUtil.getCruUserId() + "'");

		Integer total = jdbcTemplate.queryForObject(querySql.toString(), Integer.class,param.toArray());

		if(total<=0) {
			//购物车没有该商品
			cart.setCreChushiId(UserUtil.getCruChushiId());
			cart.setCreChushiName(UserUtil.getCruChushiName());
			cart.setCreJuId(UserUtil.getCruJuId());
			cart.setCreJuName(UserUtil.getCruJuName());
			cart.setCreDeptId(UserUtil.getCruDeptId());
			cart.setCreDeptName(UserUtil.getCruDeptName());
			cart.setTotalPrice(cart.getPrice());
			cart.setCreUserId(UserUtil.getCruUserId());
			cart.setCreUserName(UserUtil.getCruUserName());
			dao.save(cart);
		}else {
			//购物车有该商品
			StringBuilder querySql1 = new StringBuilder(" select t.goods_num NUM,t.price from wmgl_SHOPPING_CART t where t.take_out_id=? and t.goods_id=? and t.cre_user_id='" + UserUtil.getCruUserId() + "'");
			Map<String, Object> map = jdbcTemplate.queryForMap(querySql1.toString(),param.toArray());
			Integer num = Integer.valueOf(map.get("NUM").toString())+cart.getGoodsNum();
			if(num<=0) {
				//在购物车中删除商品
				String sql = " delete from wmgl_SHOPPING_CART t where t.take_out_id=? and t.goods_id=? and t.cre_user_id='" + UserUtil.getCruUserId() + "'";
				jdbcTemplate.update(sql, cart.getTakeOutId(),cart.getGoodsId());
			}else {
				//修改购物车的数量
				Double totalPrice = Double.valueOf(map.get("PRICE").toString())*num;
				StringBuilder updateSql = new StringBuilder(" update wmgl_SHOPPING_CART set goods_num= ?,TOTAL_PRICE = ? where goods_id= ? and take_out_id= ? and cre_user_id='" + UserUtil.getCruUserId() +"'");
				jdbcTemplate.update(updateSql.toString(), num,totalPrice,cart.getGoodsId(),cart.getTakeOutId());
			}
			
		}
		
	}
	
	/**
	 * 获取购物车数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午6:43:47
	 * @param takeOutId
	 * @return
	 */
	@Override
	public List<ShoppingCart> getCartData(String takeOutId) {
		StringBuilder sql = new StringBuilder(" select t.goods_id goodsId,t.goods_name goodsName,t.goods_num goodsNum,t.valuation_unit valuationUnit,t.buy_unit buyUnit,t.price,"
				+ "t.category_id categoryId,t.category_name categoryName,t1.id mealId,t1.optnum mealOptNum,t2.buy_limit buyLimit,t2.buy_num buyNum from wmgl_SHOPPING_CART t ");
		
		sql.append(" left outer join ( select distinct * from ( select regexp_substr(q.goods_ids, '[^,]+', 1, Level,'i') goods_id,q.goods_ids,q.id,q.opt_num optNum  from ( ");
		sql.append(" select * from wmgl_set_meal where take_out_id = ?  ) q ");
		sql.append(" connect by Level <= LENGTH(q.goods_ids) - LENGTH(REGEXP_REPLACE(q.goods_ids, ',', '')) + 1) t order by t.id ");
		sql.append(" )t1 on t.goods_id=t1.goods_id ");
		sql.append(" left outer join (select t.goods_id,t.buy_limit,t.buy_num from wmgl_take_out_detail t where t.take_out_id=?) t2 on t.goods_id=t2.goods_id ");
		sql.append(" where t.cre_user_id='" + UserUtil.getCruUserId() + "' and t.take_out_id= ?  ");
		List<ShoppingCart> list = jdbcTemplate.query(sql.toString(), new RowMapper<ShoppingCart>() {
			@Override
			public ShoppingCart mapRow(ResultSet result, int arg1) throws SQLException {
				ShoppingCart cart = new ShoppingCart();
				cart.setMealId(result.getString("mealId"));
				cart.setMealOptNum(result.getInt("mealOptNum"));
				cart.setPrice(result.getDouble("price"));
				cart.setCategoryId(result.getString("categoryId"));
				cart.setBelongId(result.getString("categoryId"));
				cart.setCategoryName(result.getString("categoryName"));
				cart.setId(result.getString("goodsId"));
				cart.setGoodsName(result.getString("goodsName"));
				cart.setGoodsNum(result.getInt("goodsNum"));
				cart.setBuyUnit(result.getString("buyUnit"));
				cart.setValuationUnit(result.getString("valuationUnit"));
				cart.setBuyLimit(result.getString("buyLimit"));
				cart.setBuyNum(result.getInt("buyNum"));
				return cart;
			}
		},takeOutId,takeOutId,takeOutId);
		return list;
	}
	

}
