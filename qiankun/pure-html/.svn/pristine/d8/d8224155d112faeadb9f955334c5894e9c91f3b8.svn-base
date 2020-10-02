package com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.dao.OrderDetilDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.entity.OrderDetil;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 订单详情serviceImp
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月9日 上午11:53:03
 */
@Service
public class OrderDetilServiceImpl implements OrderDetilService{
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	OrderDetilDao dao;
	
	/**
	 * 获取某次外卖订单的门类
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月15日 下午4:15:21
	 * @param takeOutId
	 * @return
	 */
	@Override
	public List<Category> getOrderCategory(String takeOutId) {
		StringBuilder sql = new StringBuilder("select t.category_id id,t.category_name typeName from wmgl_order_detil t ");
		sql.append("  left outer join wmgl_order_info t1 on t.order_id=t1.id ");
		sql.append(" where t1.take_out_id=? and t1.visible='1' and t.category_id is not null group by t.category_id,t.category_name ");
		List<Category> list = jdbcTemplate.query(sql.toString(), new RowMapper<Category>() {
			@Override
			public Category mapRow(ResultSet result, int arg1) throws SQLException {
				Category category = new Category();
				category.setId(result.getString("id"));
				category.setTypeName(result.getString("typeName"));
				return category;
			}
		},takeOutId);
		return list;
	}
	
	/**
	 * 查询此次外卖的汇总信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月15日 下午5:26:42
	 * @param pageImpl
	 * @param takeOutId
	 * @return
	 */
	@Override
	public PageImpl getCollectInfo(PageImpl pageImpl, String takeOutId,String categoryId) {
		List<OrderDetil> list = new ArrayList<OrderDetil>();
		if(StringUtils.isBlank(categoryId)) {
			 return new PageImpl("1",0,list);
		}
		StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        List<Object> totalPara = new ArrayList<>();
        querySql.append(" select t.goods_id,t.goods_name,t.buy_unit bugUnit,sum(GOODS_NUM) goodsNum from wmgl_order_detil t  "
        		+ "left outer join wmgl_order_info t1  on t.order_id = t1.id "
        		+ "where t1.take_out_id = ?   and t.category_id = ?  and t1.visible='1' and t1.status!='2' "
        		+ "group by t.goods_id,t.goods_name,t.buy_unit ");
        para.add(takeOutId);
        para.add(categoryId);
        totalPara.add(takeOutId);
        totalPara.add(categoryId);
        
		String sql = "select s.* from (select s.*,rownum rn from ("+querySql.toString()+") s  where rownum<= ?) s where rn>= ?";
		para.add(pageImpl.getPageNumber()*pageImpl.getPageSize());
		para.add((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
         list = jdbcTemplate.query(sql, para.toArray(),new BeanPropertyRowMapper<>(OrderDetil.class));
        Map<String,Object> map = new HashMap<>(1);
        Integer total=0;
        if(list!=null&&!list.isEmpty()) {
        	
			StringBuilder totalSql = new StringBuilder();
			totalSql.append("select count(1) total from ("+querySql+")");
			total = jdbcTemplate.queryForObject(totalSql.toString(), Integer.class,totalPara.toArray());
			
		}
        return new PageImpl("1",total,list);
	}
	
	/**
	 * 查询某次外卖的商品汇总的列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月17日 上午11:37:17
	 * @param takeOutId
	 * @param categoryId
	 * @return
	 */
	@Override
	public List<OrderDetil> getList(String takeOutId, String categoryId) {
		 StringBuilder querySql = new StringBuilder();
         List<Object> para = new ArrayList<>();
		 querySql.append(" select t.goods_id,t.goods_name,t.buy_unit bugUnit,sum(GOODS_NUM) goodsNum from wmgl_order_detil t  "
	        		+ "left outer join wmgl_order_info t1  on t.order_id = t1.id "
	        		+ "where t1.take_out_id = ?   and t1.visible='1' and t1.status!='2' " );
		 para.add(takeOutId);
		 if(StringUtils.isNotBlank(categoryId)) {
			 querySql.append("  and t.category_id = ? ");
			 para.add(categoryId);
		 }
	     querySql.append(" group by t.goods_id,t.goods_name,t.buy_unit ");
	     List<OrderDetil> list = jdbcTemplate.query(querySql.toString(), para.toArray(),new BeanPropertyRowMapper<>(OrderDetil.class));
		return list;
	}
	
	/**
	 * 根据订单id获取订单详情
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月21日 下午4:11:07
	 * @param pageable
	 * @param pageImpl
	 * @param orderId
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String orderId) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from OrderDetil t where  t.orderId = ? ");
		// 拼接条件
		para.add(orderId);
		
		// 拼接排序语句
		Page<OrderDetil> page = dao.query(querySql.toString(), pageable, para.toArray());
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/** 
	 * TODO 修改订单商品中的数量
	 * @author 李颖洁  
	 * @date 2019年5月23日下午2:14:19
	 * @param orderDetil
	 * @return OrderDetil
	 */
	@Override
	public OrderDetil updateOrderDetil(OrderDetil orderDetil) {
		OrderDetil detil = dao.findOne(orderDetil.getId());
		detil.setGoodsNum(orderDetil.getGoodsNum());
		detil.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		detil.setUpdateUserId(UserUtil.getCruUserId());
		detil.setUpdateUserName(UserUtil.getCruUserName());
		return dao.save(detil);
	}

	/** 
	 * TODO 删除订单中的商品
	 * @author 李颖洁  
	 * @date 2019年5月23日下午4:10:42
	 * @param id 订单中商品详情id
	 * @return int
	 */
	@Override
	public int deleteById(String id) {
		String jpql = "delete from OrderDetil t where t.id = ?";
		return dao.update(jpql, id);
	}
	
	

}
