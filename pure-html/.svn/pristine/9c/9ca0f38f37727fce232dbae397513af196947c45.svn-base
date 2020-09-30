package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.dao.TakeOutDetilDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.entity.TakeOutDetail;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 外卖详情serviceImp
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月9日 上午11:53:03
 */
@Service
public class TakeOutDetilServiceImpl implements TakeOutDetilService{
	@Autowired
	TakeOutDetilDao dao;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 根据外卖和门类id获取所有的商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月10日 上午9:12:48
	 * @param infoId
	 * @param classId
	 * @return
	 */
	@Override
	public List<TakeOutDetail> getDetilByInfoId(String infoId, String classId) {
		List<String> param = new ArrayList<String>();
		StringBuilder sql = new StringBuilder("  select t.*,case when t1.goods_id is null then '0' else '1' end combination  from ( ");
		sql.append(" select t.id,t.class_id classId,t.goods_id goodsId,t.goods_name goodsName,t.goods_price goodsPrice,t.valuation_unit valuationUnit,t.BUY_UNIT buyUnit,t.goods_num goodsNum,t.SORT,t.AMOUNT_LIMIT amountLimit,t.buy_limit buyLimit,t.buy_num buyNum   ");
		sql.append(" from wmgl_take_out_detail t where t.visible=? and t.take_out_id= ? and t.class_id = ? ");
		param.add("1");
		param.add(infoId);
		param.add(classId);
		sql.append(" ) t left outer join ( ");
		
		sql.append("  select distinct * from (select regexp_substr(q.goods_ids, '[^,]+', 1, Level,'i') goods_id ");
		sql.append(" from (select * from wmgl_set_meal where take_out_id = ? and CATEGORY_ID= ? ) q ");
		sql.append(" connect by Level <= LENGTH(q.goods_ids) - LENGTH(REGEXP_REPLACE(q.goods_ids, ',', '')) + 1) ");
		param.add(infoId);
		param.add(classId);
				
		sql.append(" )t1 on t.goodsId=t1.goods_id ");
		sql.append(" order by t.SORT ");
		List<TakeOutDetail> detailList = jdbcTemplate.query(sql.toString(), new RowMapper<TakeOutDetail>() {
			@Override
			public TakeOutDetail mapRow(ResultSet result, int arg1) throws SQLException {
				TakeOutDetail detail = new TakeOutDetail();
				detail.setId(result.getString("id"));
				detail.setGoodsId(result.getString("goodsId"));
				detail.setGoodsName(result.getString("goodsName"));
				detail.setGoodsPrice(result.getDouble("goodsPrice"));
				detail.setCombination(result.getString("combination"));
				detail.setValuationUnit(result.getString("valuationUnit"));
				detail.setBuyLimit(result.getString("buyLimit"));
				detail.setBuyNum(result.getInt("buyNum"));
				detail.setBuyUnit(result.getString("buyUnit"));
					detail.setGoodsNum(result.getInt("goodsNum"));
				detail.setAmountLimit(result.getString("amountLimit"));
				return detail;
			}
		},param.toArray());
		return detailList;
	}
	
	 /**
	  * 获取外卖所有的门类
	  * TODO 
	  * @author 马秋霞
	  * @Date 2019年5月10日 上午11:00:54
	  * @param infoId
	  * @return
	  */
	@Override
	public List<Category> getCategoryByInfoId(String infoId) {
		StringBuilder sql = new StringBuilder(" select * from( ");
		sql.append(" select t.class_id classId,t.class_name className,max(t.category_sort) sort from wmgl_take_out_detail t where t.visible='1' and t.take_out_id=?   ");
		sql.append(" group by t.class_id,t.class_name ");
		sql.append("  ) t order by t.sort asc");
		List<Category> list = jdbcTemplate.query(sql.toString(), new RowMapper<Category>() {
			@Override
			public Category mapRow(ResultSet result, int arg1) throws SQLException {
				Category category =new Category();
				category.setId(result.getString("classId"));
				category.setTypeName(result.getString("className"));
				return category;
			}
			
		},infoId);
		return list;
	}
	
	/**
	 * 设置外卖的商品列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月10日 下午3:07:00
	 * @param takeOutId
	 * @param detil
	 */
	@Override
	public void setDetilInfo(String takeOutId, String detil) {
		//先把此次外卖的商品信息查询出来
		List<String> goodsIdList = new ArrayList<String>();//存放外卖中设置未删除的商品id
		List<String> delgoodsIdList = new ArrayList<String>();//存放外卖中设置删除的商品id
		List<String> updategoodsId = new ArrayList<String>();//存放需要把删除状态的商品id改为未删除状态
		Map<String,String> goodsMap = new HashMap<String,String>();//存放商品id及状态，key为商品id,value为状态
		StringBuilder sql = new StringBuilder("select t.goods_id goodsId,t.visible from wmgl_take_out_detail t where t.take_out_id= ?  ");
		List<TakeOutDetail> selectlist =jdbcTemplate.query(sql.toString(), new RowMapper<TakeOutDetail>() {
			@Override
			public TakeOutDetail mapRow(ResultSet result, int arg1) throws SQLException {
				if("1".equals(result.getString("visible"))) {
					//存放未删除的商品id
					goodsIdList.add(result.getString("goodsId"));
				}else {
					delgoodsIdList.add(result.getString("goodsId"));
				}
				TakeOutDetail detil = new TakeOutDetail();
				return detil;
			}
		},takeOutId);
		//List<String> goodsIdList = jdbcTemplate.queryForList(sql.toString(), String.class,takeOutId);
		JSONArray jsonArray = JSONArray.fromObject(detil);
		int index=0;
		List<TakeOutDetail> list = new ArrayList<TakeOutDetail>();
		for (Object object : jsonArray) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			String goodsId = jsonObj.getString("goodsId");
			if(goodsIdList.contains(goodsId)) {//已保存在数据库的商品
				goodsIdList.remove(goodsId);
			}else if(delgoodsIdList.contains(goodsId)){
				updategoodsId.add(goodsId);
			}else{
				TakeOutDetail detail = new TakeOutDetail();
				detail.setTakeOutId(takeOutId);
				detail.setSort(index);
				detail.setCategorySort(jsonObj.getInt("categorySort"));
				detail.setGoodsId(jsonObj.getString("goodsId"));
				detail.setClassId(jsonObj.getString("classId"));
				detail.setClassName(jsonObj.getString("className"));
				detail.setGoodsName(jsonObj.getString("goodsName"));
				detail.setGoodsPrice(jsonObj.getDouble("goodsPrice"));
				detail.setValuationUnit(jsonObj.getString("valuationUnit"));
				detail.setBuyUnit(jsonObj.getString("buyUnit"));
				detail.setGoodsNum(jsonObj.getInt("goodsNum"));
				detail.setAmountLimit(jsonObj.getString("amountLimit"));
				detail.setBuyLimit(jsonObj.getString("buyLimit"));
				detail.setBuyNum(jsonObj.getInt("buyNum"));
				detail.setVisible("1");
				list.add(detail);
			}
			index++;
		}
		//保存未在数据库出现的商品信息
		if(list.size()>0) {
			dao.save(list);
		}
		//删除取消勾选的商品
		List<Object> param = new ArrayList<Object>();
		StringBuilder delGoodsIds = new StringBuilder();
		for(String goodsId:goodsIdList) {
			delGoodsIds.append(goodsId+",");
		}
		if(delGoodsIds.length()>0) {
			param.add(takeOutId);
			StringBuilder deleSql = new StringBuilder("update wmgl_take_out_detail t set t.visible='0' "
					+ "where t.take_out_id= ? and t.goods_id in (" + CommonUtils.solveSqlInjectionOfIn(delGoodsIds.toString(),param) + ")");
			jdbcTemplate.update(deleSql.toString(), param.toArray());
		}
		//把删除状态的商品改为未删除
		param.clear();
		StringBuilder updateGoodsIds = new StringBuilder();
		for(String goodsId:updategoodsId) {
			updateGoodsIds.append(goodsId+",");
		}
		if(updateGoodsIds.length()>0) {
			param.add(takeOutId);
			StringBuilder deleSql = new StringBuilder("update wmgl_take_out_detail t set t.visible='1' "
					+ "where t.take_out_id= ? and t.goods_id in (" + CommonUtils.solveSqlInjectionOfIn(updateGoodsIds.toString(),param) + ")");
			jdbcTemplate.update(deleSql.toString(), param.toArray());
		}
	}
	
	
	/**
	 * 设置外卖的商品列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月10日 下午3:07:00
	 * @param takeOutId
	 * @param detil
	 */
	public void setDetilInfo1(String takeOutId, String detil) {
		String cruDate = new SimpleDateFormat().format(new Date());
		StringBuilder deleteSql = new StringBuilder(" delete from wmgl_take_out_detail where take_out_id= ? ");
		jdbcTemplate.update(deleteSql.toString(), takeOutId);
		JSONArray jsonArray = JSONArray.fromObject(detil);
		List<TakeOutDetail> list = new ArrayList<TakeOutDetail>();
		int index=0;
		for (Object object : jsonArray) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			TakeOutDetail detail = new TakeOutDetail();
			detail.setTakeOutId(takeOutId);
			detail.setSort(index);
			index++;
			detail.setCategorySort(jsonObj.getInt("categorySort"));
			detail.setGoodsId(jsonObj.getString("goodsId"));
			detail.setClassId(jsonObj.getString("classId"));
			detail.setClassName(jsonObj.getString("className"));
			detail.setGoodsName(jsonObj.getString("goodsName"));
			detail.setGoodsPrice(jsonObj.getDouble("goodsPrice"));
			detail.setValuationUnit(jsonObj.getString("valuationUnit"));
			detail.setBuyUnit(jsonObj.getString("buyUnit"));
			detail.setGoodsNum(jsonObj.getInt("goodsNum"));
			detail.setAmountLimit(jsonObj.getString("amountLimit"));
			detail.setBuyLimit(jsonObj.getString("buyLimit"));
			detail.setBuyNum(jsonObj.getInt("buyNum"));
			detail.setVisible("1");
			if(StringUtils.isNotBlank(detail.getId())) {//修改
				detail.setUpdateTime(cruDate);
				detail.setUpdateUserId(UserUtil.getCruUserId());
				detail.setUpdateUserName(UserUtil.getCruUserName());
				
			}else {//创建
				detail.setCreUserId(UserUtil.getCruUserId());
				detail.setCreTime(cruDate);
				detail.setCreUserName(UserUtil.getCruUserName());
				detail.setCreDeptId(UserUtil.getCruDeptId());
				detail.setCreDeptName(UserUtil.getCruDeptName());
				
				detail.setCreChushiId(UserUtil.getCruChushiId());
				detail.setCreChushiName(UserUtil.getCruChushiName());
				detail.setCreJuId(UserUtil.getCruJuId());
				detail.setCreJuName(UserUtil.getCruJuName());
			}
			
			list.add(detail);
		}
		dao.save(list);
		
	}
	/**
	 * 删除外卖的商品信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 上午9:14:51
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		int result = 0;
		String jpql = "update TakeOutDetail t set t.visible = ? where t.id = ?";
		try {
			result = dao.update(jpql, CommonConstants.VISIBLE[0], id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据外卖id获取外卖详情
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 上午10:07:55
	 * @param infoId
	 * @return
	 */
	@Override
	public List<TakeOutDetail> getDetil(String infoId) {
		StringBuilder sql = new StringBuilder(" select t.id,t.take_out_id takeOutId,t.class_id classId,t.class_name className,t.goods_id goodsId,t.goods_num goodsNum, "
				+ "t.goods_name goodsName,t.goods_price goodsPrice,t.valuation_unit valuationUnit,t.buy_unit buyUnit,t.category_sort categorySort,case when t1.goodsIds is null then '0' else '1' end combination ");
		sql.append(" from wmgl_take_out_detail t  left outer join ( ");
		sql.append(" select distinct * from ( select regexp_substr(q.goods_ids, '[^,]+', 1, Level,'i') goodsIds from ( "
				+ "select * from wmgl_set_meal where take_out_id = ?  ) q ");
		sql.append(" connect by Level <= LENGTH(q.goods_ids) - LENGTH(REGEXP_REPLACE(q.goods_ids, ',', '')) + 1) )t1 on t.goods_id=t1.goodsIds ");
		sql.append(" where t.visible='1' and t.take_out_id= ? ");
		List<TakeOutDetail> List = jdbcTemplate.query(sql.toString(), new RowMapper<TakeOutDetail>() {
			@Override
			public TakeOutDetail mapRow(ResultSet result, int arg1) throws SQLException {
				TakeOutDetail detil = new TakeOutDetail();
				detil.setCombination(result.getString("combination"));
				detil.setId(result.getString("id"));
				detil.setTakeOutId(result.getString("takeOutId"));
				detil.setClassId(result.getString("classId"));
				detil.setClassName(result.getString("className"));
				detil.setGoodsId(result.getString("goodsId"));
				detil.setGoodsName(result.getString("goodsName"));
				detil.setGoodsNum(result.getInt("goodsNum"));
				detil.setGoodsPrice(result.getDouble("goodsPrice"));
				detil.setValuationUnit(result.getString("valuationUnit"));
				detil.setBuyUnit(result.getString("buyUnit"));
				detil.setCategorySort(result.getInt("categorySort"));
				return detil;
			}
		},infoId,infoId);
		return List;
	}
	
	/**
	 * 排序
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月14日 下午4:52:16
	 * @param id
	 */
	@Override
	public void sort(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			TakeOutDetail detil = dao.findOne(ids[i]);
			detil.setSort(i);
			dao.save(detil);
		}
	}
	
	/**
	 * 修改外卖的详情
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月23日 上午10:54:07
	 * @param detil
	 */
	@Override
	public void updateDetil(TakeOutDetail detil,String flg) {
		TakeOutDetail oldEntry = dao.findOne(detil.getId());
		if("0".equals(flg)) {//修改单价
			if(detil.getGoodsPrice()!=null) {
				oldEntry.setGoodsPrice(detil.getGoodsPrice());
				
			}
		}else if("1".equals(flg)) {//修改的是库存量
			oldEntry.setGoodsNum(detil.getGoodsNum());
			if(detil.getGoodsNum()!=null) {
				oldEntry.setAmountLimit("1");
			}else {
				oldEntry.setAmountLimit("0");
			}
			//oldEntry.setAmountLimit(detil.getAmountLimit());
			
		}else {
			oldEntry.setBuyNum(detil.getBuyNum());
			if(detil.getBuyNum()!=null) {
				oldEntry.setBuyLimit("1");
			}else {
				oldEntry.setBuyLimit("0");
			}
		}
		
		
		dao.save(oldEntry);
	}
	
	/**
	 * 是否可以修改库存数量
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年6月12日 上午10:59:36
	 * @param goodsNum
	 */
	@Override
	public JSONObject judgeUpdateGoodsNum(String takeOutId,String goodsId,Integer goodsNum) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flg", "1");
		jsonObj.put("msg", "");
		StringBuilder sql = new StringBuilder("select sum(t.GOODS_NUM) goodsNum from wmgl_order_detil t left outer join wmgl_order_info t1 ");
		sql.append(" on t.ORDER_ID = t1.id where t1.take_out_id = ? and t.GOODS_ID= ? and t1.visible='1' and t1.status!='2' group by t.GOODS_ID ");
		Integer goodsNum1;
		try {
			goodsNum1 = jdbcTemplate.queryForObject(sql.toString(), Integer.class,takeOutId,goodsId);
		} catch (Exception e) {
			goodsNum1=0;
		}
		if(goodsNum!=null) {
			if(goodsNum1>goodsNum) {
				jsonObj.put("flg", "0");
				jsonObj.put("orderNum", goodsNum1);
				//jsonObj.put("msg", "目前此商品已订购"+goodsNum1+",最小数量不能低于"+goodsNum+"!");
			}
		}
		
		return jsonObj;
		
	}
	
	
	

}
