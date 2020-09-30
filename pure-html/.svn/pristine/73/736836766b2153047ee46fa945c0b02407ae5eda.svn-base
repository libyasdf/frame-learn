package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.order.entity.OrderInfo;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.entity.ShoppingCart;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.dao.SetMealDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.entity.SetMeal;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.dao.TakeOutDetilDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.entity.TakeOutDetail;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.dao.TakeOutInfoDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.entity.TakeOutInfo;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 外卖serviceImp
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月9日 上午11:53:03
 */
@Service
public class TakeOutInfoServiceImpl implements TakeOutInfoService{
	@Autowired
	JdbcTemplate jdbcTemlate;
	@Autowired
	TakeOutInfoDao dao;
	@Autowired
	TakeOutDetilDao detilDao;
	@Autowired
	SetMealDao setMealDao;
	
	/**
	 * 获取外卖标题
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午2:16:17
	 * @return
	 */
	@Override
	public String getTitle() {
		StringBuilder sb = new StringBuilder(" select max(t.title) title from wmgl_take_out_info t where t.visible = '1' order by t.title desc ");
		String title = jdbcTemlate.queryForObject(sb.toString(), String.class);
		String cruYear = new SimpleDateFormat("yyyy").format(new Date());
		if(StringUtils.isNotBlank(title)) {
			String tempYear = title.substring(0, 4);
			if(cruYear.equals(tempYear)) {
				Integer cnt = Integer.valueOf(title.substring(4,title.length()))+1;
				if(cnt<10) {
					title = cruYear + "0" + cnt;
				}else {
					title = cruYear + cnt;
				}
			}else {
				title = cruYear + "01";
			}
		}else {
			title = cruYear + "01";
		}
		return title;
	}
	
	/**
	 * 保存外卖信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午4:00:46
	 * @param entity
	 * @return
	 */
	@Override
	public TakeOutInfo saveFrom(TakeOutInfo entity) {
		String cruTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		TakeOutInfo info = new TakeOutInfo();
        if(StringUtils.isBlank(entity.getId())){
            entity.setVisible("1");
            entity.setTongji("0");
            entity.setCreUserId(UserUtil.getCruUserId());
            entity.setCreDeptId(UserUtil.getCruDeptId());
            entity.setCreDeptName(UserUtil.getCruDeptName());
            entity.setCreUserName(UserUtil.getCruUserName());
            entity.setCreChushiId(UserUtil.getCruChushiId());
            entity.setCreChushiId(UserUtil.getCruChushiId());
            entity.setCreChushiName(UserUtil.getCruChushiName());
            entity.setCreJuId(UserUtil.getCruJuId());
            entity.setCreJuName(UserUtil.getCruJuName());
            entity.setCreTime(cruTime);
            entity.setCopy("0");
            entity = dao.save(entity);
        }else {
        	info = dao.findOne(entity.getId());
        	info.setUpdateUserId(UserUtil.getCruUserId());
        	info.setUpdateUserName(UserUtil.getCruUserName());
        	info.setUpdateTime(cruTime);
        	info.setAttentionItem(entity.getAttentionItem());
        	info.setMark(entity.getMark());
        	info.setTakeFoodTime(entity.getTakeFoodTime());
        	info.setDeadlineTime(entity.getDeadlineTime());
        	info.setStatus(entity.getStatus());
        	info = dao.save(info);
        }
        return entity;
	}
	
	/**
	 * 删除外卖信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午4:07:18
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		int result = 0;
		String jpql = "update TakeOutInfo t set t.visible = ? where t.id = ?";
		try {
			result = dao.update(jpql, CommonConstants.VISIBLE[0], id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	  /**
	   * 外卖管理打开只读、修改页面时，查询数据进行渲染 TODO
	   * TODO 
	   * @author 马秋霞
	   * @Date 2019年5月8日 下午1:40:13
	   * @param id
	   * @return
	   */
	@Override
	public TakeOutInfo getById(String id) {
		TakeOutInfo info = dao.findOne(id);
		return info;
	}
	
	/**
	 * 外卖分页列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午4:47:35
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param takeFoodTime
	 * @param status
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String title, String  startDate,String endDate,String startDate1,String endDate1,String status,String name) {
		List<Object> para = new ArrayList<>();
		//总个数
    	StringBuilder sb = new StringBuilder("select count(1) from (");
    	querySql(sb,title,startDate,endDate,startDate1,endDate1,status,name,para);
    	sb.append(")");
    	Integer total=jdbcTemlate.queryForObject(sb.toString(), Integer.class,para.toArray());
    	//查询分页数据
    	para.clear();
    	final String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String pageSql = pageSql(pageable,title,startDate,endDate,startDate1,endDate1,status,name,para);
		List<TakeOutInfo> listData =jdbcTemlate.query(pageSql.toString(), new RowMapper<TakeOutInfo>(){
			@Override
			public TakeOutInfo mapRow(ResultSet result, int arg1) throws SQLException {
				TakeOutInfo info = new TakeOutInfo();
				info.setName(result.getString("name"));
				info.setTitle(result.getString("title"));
				info.setId(result.getString("id"));
				info.setTakeFoodTime(result.getString("takeFoodTime"));
				String deadlineTime = result.getString("deadlineTime");
				if(cruDate.compareTo(deadlineTime)>0) {
					info.setStop("0");
				}
				info.setDeadlineTime(deadlineTime);
				info.setStatus(result.getString("status"));
				String orderNo = result.getString("orderNo");
				if(StringUtils.isNotBlank(orderNo)) {
					info.setOrderNo(orderNo);
				}
				
				return info;
			}
			
		},para.toArray());
		return new PageImpl(total,listData);
	}
	private String pageSql(Pageable pageable, String title, String startDate, String endDate, String startDate1,String endDate1,String status,String name,
			List<Object> para) {
		StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
		querySql(sb,title,startDate,endDate,startDate1,endDate1,status,name,para);
		//sb.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
		sb.append("  ) a1 where ROWNUM <= ? ) where RN >=  ?");
		para.add(pageable.getOffset()+pageable.getPageSize());
		para.add(pageable.getOffset()+1);
		return sb.toString();
	}

	//拼接列表的sql语句
	private void querySql(StringBuilder sb, String title,String  startDate,String endDate, String startDate1,String endDate1,String status,String name, List<Object> para) {
		sb.append("select t.id,t.name,t.title title,t.takefood_time takeFoodTime,t.deadline_time deadlineTime,t.status,t1.orderNo from wmgl_take_out_info t left outer join ");
		sb.append("  (select take_out_id,count(id) orderNo from WMGL_ORDER_INFO where visible='1' group by take_out_id) t1  on t.id=t1.take_out_id where t.visible=? ");
		para.add("1");
		if(StringUtils.isNotBlank(title)) {
			sb.append(" and t.title like ? ");
			para.add("%" + title + "%");
		}
		if(StringUtils.isNotBlank(name)) {
			sb.append(" and t.name like ? ");
			para.add("%" + name + "%");
		}
		if (StringUtils.isNotBlank(startDate)) {
			sb.append(" and SUBSTR(t.takefood_time,23,10)>= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			sb.append(" and SUBSTR(t.takefood_time,0,10)<= ?");
			para.add(endDate);
		}
		if (StringUtils.isNotBlank(startDate1)) {
			sb.append("   and SUBSTR(t.deadline_time,0,10) >= ?");
			para.add(startDate1);
		}
		if (StringUtils.isNotBlank(endDate1)) {
			sb.append("   and SUBSTR(t.deadline_time,0,10) <= ?");
			para.add(endDate1);
		}
		if(StringUtils.isNotBlank(status)) {
			sb.append(" and t.status = ? ");
			para.add(status);
		}
		
		sb.append(" order by to_number(t.title) desc ");
		
	}
	
	/**
	 * 获取可预定的外卖列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午7:10:34
	 * @return
	 */
	@Override
	public List<TakeOutInfo> getList() {
		String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		StringBuilder sql = new StringBuilder(" select t.id,t.name,t.title,t.deadline_time deadlineTime,t.takefood_time takeFoodTime,t.attention_item attentionItem  from wmgl_take_out_info t where t.visible='1' and t.status='1' and t.deadline_time> ?  order by t.deadline_time,to_number(t.title) ");
		List<TakeOutInfo> list = jdbcTemlate.query(sql.toString(), new RowMapper<TakeOutInfo>() {
			@Override
			public TakeOutInfo mapRow(ResultSet result, int arg1) throws SQLException {
				TakeOutInfo info = new TakeOutInfo();
				info.setId(result.getString("id"));
				info.setName(result.getString("name"));
				info.setTitle(result.getString("name"));
				info.setDeadlineTime(result.getString("deadlineTime"));
				info.setTakeFoodTime(result.getString("takeFoodTime"));
				info.setAttentionItem(result.getString("attentionItem"));
				return info;
			}
			
		},cruDate);
		return list;
	}
	
	/**
	 * 置顶排序
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月14日 下午4:40:29
	 * @param ids
	 * @return
	 */
	@Override
	public int orderZd(String ids) {
		 int n = 0;
	        try {
	            if (StringUtils.isNotBlank(ids)) {
	                String[] idsArray = ids.split(",");
	                for(int m = 0;idsArray.length > m; m++){
	                    String iszd = String.valueOf(m+1);
	                    //dao.updateZdById(iszd,idsArray[m]);
	                }
	                n = 1;
	            }
	        }catch (Exception e){
	            e.printStackTrace();
	            n = -1;
	        }
	        return n;
	}
	
	/**
	 * 查询该订单是否已超过最晚下单时间，是否已下单
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月17日 下午7:08:33
	 * @param takeOutId
	 * @return
	 */
	@Override
	public List<Map<String,Object>>  findById(String takeOutId) {
		StringBuilder sql = new StringBuilder("select t1.deadline_time deadlineTime,t.id orderId from wmgl_order_info t right outer join wmgl_take_out_info t1 on t.take_out_id=t1.id ");
		sql.append(" where t.visible='1' and t.cre_user_id ='" + UserUtil.getCruUserId() + "' and t.take_out_id =? ");
		List<Map<String,Object>> list = jdbcTemlate.queryForList(sql.toString(),takeOutId);
		return list;
		
	}
	
	/**
	 * 外卖的复用
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月22日 上午11:32:33
	 * @param id
	 */
	@Override
	public void reuse(String id) {
		String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		//获取要复用的外卖信息
		TakeOutInfo info = dao.findOne(id);
		//保存复制的外卖信息
		TakeOutInfo newInfo = new TakeOutInfo();
		BeanUtils.copyProperties(info, newInfo);
		newInfo.setId("");
		String title = getTitle();
		newInfo.setTongji("0");
		newInfo.setStatus("0");
		newInfo.setTitle(title);
		newInfo.setCopy("1");
		newInfo.setCreTime(cruDate);
		newInfo = dao.save(newInfo);
		//保存复制的外卖的详情信息
		StringBuilder sql = new StringBuilder("select t.* from wmgl_take_out_detail t where t.take_out_id=? ");
		 List<TakeOutDetail> detailList = jdbcTemlate.query(sql.toString() ,new BeanPropertyRowMapper<>(TakeOutDetail.class),id);
		 List<TakeOutDetail> list = new ArrayList<TakeOutDetail>();
		 for (TakeOutDetail takeOutDetail : detailList) {
			 TakeOutDetail detil = new TakeOutDetail();
			 BeanUtils.copyProperties(takeOutDetail, detil);
			 detil.setId("");
			 detil.setTakeOutId(newInfo.getId());
			 detil.setCreTime(cruDate);
			 list.add(detil);
		}
		 detilDao.save(list);
		 //复制外卖的套餐信息
		 List<SetMeal> mealsResult = new ArrayList<SetMeal>();
		 StringBuilder setMealSql = new StringBuilder("select t.* from WMGL_SET_MEAL t where t.take_out_id=? ");
		 List<SetMeal> mealsList = jdbcTemlate.query(setMealSql.toString() ,new BeanPropertyRowMapper<>(SetMeal.class),id);
		 for (SetMeal setMeal : mealsList) {
			 SetMeal newMeal = new SetMeal();
			 BeanUtils.copyProperties(setMeal, newMeal);
			 newMeal.setId("");
			 newMeal.setTakeOutId(newInfo.getId());
			 newMeal.setCreTime(cruDate);
			 mealsResult.add(newMeal);
		}
		 setMealDao.save(mealsResult);
	}
	
	/**
	 * 有哪些商品超过了库存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月24日 上午9:27:28
	 * @param goodsId
	 * @return
	 */
	@Override
	public JSONObject surpassRepertory(String takeOutId,String cartgoodsInfo,String orderId) {
		JSONObject result = new JSONObject();//最终的返回结果
		Map<String,Integer> meatGoodMap = new HashMap<String,Integer>();//存放套餐的map,key为套餐中包含的商品id，value为套餐规则
		String flag = "1";//购物车中的商品都正常
		String msg="";//返回的消息
		//把购物车中，商品对应的数量放到map中
		List<Object> params = new ArrayList<Object>();
		StringBuilder goodsId = new StringBuilder();
		Map<String,Integer> cartMap = new HashMap<String,Integer>();//存放购物车中，商品对应的数量,key为商品id,value为商品数量
		Map<String,String> cartGoodsNameMap = new HashMap<String,String>();//购物车的商品id-商品名称映射，key为商品id,value为商品名称
		Map<String,String> cartGoodsMap = new HashMap<String,String>();//购物车的商品id-商品名称映射，key为商品id,value为商品名称

		JSONArray jsonArry = JSONArray.fromObject(cartgoodsInfo);
		for (Object object : jsonArry) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			goodsId.append(jsonObj.getString("goodsId") + ",");
			cartMap.put(jsonObj.getString("goodsId"), jsonObj.getInt("goodsNum"));
			cartGoodsNameMap.put(jsonObj.getString("goodsId"), jsonObj.getString("goodsName"));
			cartGoodsMap.put(jsonObj.getString("goodsId"), jsonObj.getString("goodsName"));
		}
		
		//查询购物车中商品对应的库存数量,
		StringBuilder sql = new StringBuilder(" select t.goodsId,t.goodsName,t.buyUnit,t.amountLimit,t.buyLimit,t3.goods_id meatGoodId,t3.goods_ids meatGoodIds,t3.optNum,case when t.buyNum is null then 0 else t.buyNum end buyNum,(t.goods_num-nvl(t1.goodsNum,0)) goodsNum  ");
		if(StringUtils.isNotBlank(orderId)) {
			sql.append(" ,case when t2.goodsNum is null then 0 else t2.goodsNum  end hasBuyNum ");
		}
		sql.append(" from ( select t.goods_id goodsId,t.buy_unit  buyUnit,sum(t.buy_limit) buyLimit,sum(t.buy_num) buyNum,t.goods_name goodsName,t.amount_limit amountLimit,sum(t.goods_num) goods_num from wmgl_take_out_detail t where t.take_out_id= ? and t.visible='1' ");
		params.add(takeOutId);
		sql.append("   and t.goods_id in (" + CommonUtils.solveSqlInjectionOfIn(goodsId.toString(),params) + ") group by t.goods_id,t.goods_name,t.buy_unit,t.amount_limit ");
		sql.append(" ) t left outer join ( ");
		sql.append(" select t1.goods_id goodsId, sum(t1.goods_num) goodsNum from wmgl_order_info t left outer join wmgl_order_detil t1 on t.id=t1.order_id where t.visible='1' and t.status!='2' and t.take_out_id= ? ");
		params.add(takeOutId);
		sql.append("   and t1.goods_id in ("+  CommonUtils.solveSqlInjectionOfIn(goodsId.toString(),params) +")group by t1.goods_id ");
		sql.append(" )t1 on t.goodsId=t1.goodsId ");
		if(StringUtils.isNotBlank(orderId)) {//已下过订单，需要关联某些商品已购买的数量，
			sql.append("  left outer join ( ");
			sql.append(" select t1.goods_id,sum(t1.goods_num) goodsNum   from wmgl_order_info t left outer join wmgl_order_detil t1  on t.id = t1.order_id  "
					+ "where t.take_out_id = ?  and t.id = ?  and t.cre_user_id = '" + UserUtil.getCruUserId() + "' and t.visible = '1' group by t1.goods_id ");
			sql.append("  )t2 on t.goodsId=t2.goods_id ");
			params.add(takeOutId);
			params.add(orderId);
		}
		//关联套餐表
		sql.append("   left outer join ( select distinct * from ( select regexp_substr(q.goods_ids, '[^,]+', 1, Level,'i') goods_id,q.goods_ids,q.id,q.opt_num optNum  from (  ");
		sql.append(" select * from wmgl_set_meal where take_out_id = ?  ) q  ");
		sql.append(" connect by Level <= LENGTH(q.goods_ids) - LENGTH(REGEXP_REPLACE(q.goods_ids, ',', '')) + 1) t order by t.id  ");
		sql.append("  )t3 on t.goodsId=t3.goods_id  ");
		params.add(takeOutId);
		
		List<Map<String, Object>> goodsNumMap = jdbcTemlate.queryForList(sql.toString(), params.toArray());
		Map<String,Integer> repertoryMap = new HashMap<String,Integer>();
		String isStop = "1";
		for (Map<String, Object> map : goodsNumMap) {
			String tempGoodsId = map.get("GOODSID").toString();
			String amountLimit = map.get("AMOUNTLIMIT").toString();
			String tempGoodsName = map.get("GOODSNAME").toString();
			String buyLimit = "0";
			
			if(map.get("MEATGOODID")!=null) {//此商品在套餐中，需要把套餐信息存放起来
				Integer optNum = meatGoodMap.get(map.get("MEATGOODIDS").toString());
				if(optNum==null) {
					meatGoodMap.put(map.get("MEATGOODIDS").toString(), Integer.valueOf(map.get("OPTNUM").toString()));
				}
			}
			if(map.get("BUYLIMIT")!=null) {
				buyLimit = map.get("BUYLIMIT").toString();
			}
			Integer buyNum=0;
			if(map.get("BUYNUM")!=null) {
				 buyNum = Integer.valueOf(map.get("BUYNUM").toString());
			}
			String buyUnit = map.get("BUYUNIT").toString();
			Integer tempGoodsNum = Integer.valueOf(map.get("GOODSNUM").toString());
			
			Integer hasBuyNum=0;
			if(map.get("HASBUYNUM")!=null) {
				 hasBuyNum = Integer.valueOf(map.get("HASBUYNUM").toString());
			}
			Integer cartGoodsNum = cartMap.get(tempGoodsId);
			if(StringUtils.isNotBlank(orderId)) {
				if("1".equals(buyLimit) && (cartGoodsNum+hasBuyNum)>buyNum) {
					 flag = "4";
					 isStop = "0";
					 msg = tempGoodsName + "每人限购" + buyNum + buyUnit +"!";
					 break;
				}
			}else {
				if("1".equals(buyLimit) && cartGoodsNum>buyNum) {
					 flag = "4";
					 isStop = "0";
					 msg = tempGoodsName + "每人限购" + buyNum + buyUnit +"!";
					 break;
				}
			}
			
			
			//判断商品已售罄，还是库存不足，还是已删除
			if("1".equals(amountLimit) && tempGoodsNum<=0) {
				 flag = "4";
				 isStop = "0";
				 msg = tempGoodsName + "已售罄!";
				 break;
			}else if("1".equals(amountLimit) && cartGoodsNum>tempGoodsNum) {//购物车数据大于库存数据
				 flag = "4";
				 isStop = "0";
				 msg = tempGoodsName + "目前在库存中还有" + tempGoodsNum + buyUnit+"!";
				 break;
			}else {
				//库存数据大于购物车数据
				cartMap.remove(tempGoodsId);
				cartGoodsNameMap.remove(tempGoodsId);
			}
		}
		if("1".equals(isStop)) {
			//库存中的商品遍历完，不存在购物车的数据大于库存数量，检查购物车的商品是否有在库存中删除
			Set<Entry<String, Integer>> entrySet = cartMap.entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				String goodsId1 = entry.getKey();
				String goodsName = cartGoodsNameMap.get(goodsId1);
				msg = msg + goodsName+",";
			}
			if(entrySet.size()>0) {
				 flag = "4";
				 isStop = "0";
				msg =msg.substring(0, msg.length()-1) + "已删除!";
			}
						
		}
		Set<String> meatGoodIds = meatGoodMap.keySet();
		String meatGoodIdsTemp = "";
		String meatGoodNameTemp = "";
		Integer optNum =0;
		if("1".equals(isStop)) {//遍历完后，判断是否符合套餐规则
			if(StringUtils.isNotBlank(orderId)) {
				StringBuilder orderSql = new StringBuilder("select t.goods_id goodsId,t.goods_name goodName from wmgl_order_detil t "
						+ "left outer join wmgl_order_info t1 on t.order_id=t1.id where t1.visible='1' and t1.status='1' and t.order_id=? and t.cre_user_id='"+UserUtil.getCruUserId()+"'");
				List<ShoppingCart> cartList = jdbcTemlate.query(orderSql.toString(), new RowMapper<ShoppingCart>() {
					@Override
					public ShoppingCart mapRow(ResultSet result, int arg1) throws SQLException {
						ShoppingCart cart = new ShoppingCart();
						cart.setGoodsId(result.getString("goodsId"));
						cart.setGoodsName(result.getString("goodName"));
						
						return cart;
					}
					
				},orderId);
				for (ShoppingCart cart : cartList) {
					cartGoodsMap.put(cart.getGoodsId(), cart.getGoodsName());
				}
				
			}
			Integer meatNum = 0;//下单商品包含在套餐中的几个商品
			Set<String> goodIdsSet = cartGoodsMap.keySet();
			for (String meatGoodId : meatGoodIds) {
				meatGoodNameTemp="";
				meatNum=0;
				for (String goodId : goodIdsSet) {
					if(meatGoodId.contains(goodId)) {
						meatNum++;
						optNum=meatGoodMap.get(meatGoodId);
						meatGoodNameTemp+=cartGoodsMap.get(goodId)+",";
					}
				}
				if(meatNum>0 && meatNum>optNum) {//购物车中有商品属于套餐，并且超出了套餐规则
					meatGoodNameTemp=meatGoodNameTemp.substring(0, meatGoodNameTemp.length()-1);
					 flag = "4";
					 msg=meatGoodNameTemp+"只能"+meatGoodNameTemp.split(",").length+"选"+optNum;
					break;
				}
			}
		
		}
		
		result.put("flag", flag);
		result.put("msg", msg);
		return result;
	}
	
	
	

}
