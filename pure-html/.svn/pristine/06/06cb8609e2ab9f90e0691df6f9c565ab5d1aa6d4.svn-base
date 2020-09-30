package com.sinosoft.sinoep.modules.mypage.wmgl.index.order.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.dao.WmglConfigDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.dao.WmgLosecreditDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.entity.WmgLosecredit;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.order.dao.OrderInfoDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.order.entity.OrderInfo;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.dao.OrderDetilDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.entity.OrderDetil;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.entity.ShoppingCart;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 订单serviceImpl
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月15日 上午9:52:31
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService{
	@Autowired
	JdbcTemplate jdbcTemlate;
	@Autowired
	OrderInfoDao dao;
	@Autowired
	OrderDetilDao detilDao;
	@Autowired
	WmgLosecreditDao loseCreditDao;
	@Autowired
	WmglConfigDao configDao;
	@Autowired
	WmgLosecreditDao creditDao;
	  @Resource
	    UserInfoService userInfoService;
	
	/**
	 * 下单操作
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月15日 上午9:57:08
	 * @param takeOutId
	 */
	@Override
	public   String placeOrder(String takeOutId,String title,String phone,String cartgoodsInfo,String orderId) {
		String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 查询此次外卖的购物车数据
		OrderInfo newOrder = new OrderInfo();
		 
		List<ShoppingCart> cartList = new ArrayList<ShoppingCart>();
		/*StringBuilder sql = new StringBuilder(" select t.goods_id goodsId,t.goods_name goodsName,t.goods_num goodsNum,t.TOTAL_PRICE totalPrice,t.price, ");
		sql.append(" t.VALUATION_UNIT valuationUnit,t.BUY_UNIT buyUnit,t.GOODS_NUM goodsNum,t.category_id categoryId,t.category_name categoryName from wmgl_SHOPPING_CART t where t.take_out_id= ?  and  t.cre_user_id='" + UserUtil.getCruUserId() + "' ");
		
		List<ShoppingCart> cartList =jdbcTemlate.query(sql.toString(), new RowMapper<ShoppingCart>() {
											@Override
											public ShoppingCart mapRow(ResultSet result, int arg1) throws SQLException {
												ShoppingCart cart = new ShoppingCart();
												cart.setCategoryId(result.getString("categoryId"));
												cart.setCategoryName(result.getString("categoryName"));
												cart.setGoodsId(result.getString("goodsId"));
												cart.setGoodsName(result.getString("goodsName"));
												cart.setGoodsNum(result.getInt("goodsNum"));
												cart.setPrice(result.getDouble("price"));
												cart.setTotalPrice(result.getDouble("totalPrice"));
												cart.setValuationUnit(result.getString("valuationUnit"));
												cart.setBuyUnit(result.getString("buyUnit"));
												cart.setGoodsNum(result.getInt("goodsNum"));
												order.setTotalPrice(order.getTotalPrice()+result.getDouble("totalPrice"));
												return cart;
											}
									},takeOutId);*/
		JSONArray jsonArry = JSONArray.fromObject(cartgoodsInfo);
		for (Object object : jsonArry) {
			JSONObject jsonObj = JSONObject.fromObject(object);
			ShoppingCart cart = new ShoppingCart();
			cart.setCategoryId(jsonObj.getString("categoryId"));
			cart.setCategoryName(jsonObj.getString("categoryName"));
			cart.setGoodsId(jsonObj.getString("goodsId"));
			cart.setGoodsName( jsonObj.getString("goodsName"));
			cart.setGoodsNum(jsonObj.getInt("goodsNum"));
			cart.setPrice(jsonObj.getDouble("price"));
			cart.setValuationUnit(jsonObj.getString("valuationUnit"));
			cart.setBuyUnit(jsonObj.getString("buyUnit"));
			cartList.add(cart);
		}
	
		String cruUserId = UserUtil.getCruUserId();
		Map<String,OrderDetil> detilMap = new HashMap<String,OrderDetil>();
		//设置订单参数
		if(StringUtils.isBlank(orderId)) {
			OrderInfo order = new OrderInfo();
			order.setTakeOutId(takeOutId);
			order.setStatus("1");
			order.setLoseCredit("0");
			SysFlowUserVo cruUser = UserUtil.getUserVo(cruUserId).get(cruUserId);
			String deptNm = userInfoService.getDeptFullName(UserUtil.getCruDeptId());
			String[] deptNames = deptNm.split("/");
			String deptStr = "";
			for (int i=deptNames.length-1;i>=0;i--) {
				deptStr += deptNames[i]+"/";
			}
			if(deptStr.length()>0) {
				deptStr=deptStr.substring(0, deptStr.length()-1);
			}
			order.setFullDeptName(deptStr);
			order.setCreUserId(cruUserId);
			order.setPhone(phone);
			order.setCreUserName(UserUtil.getCruUserName());
			order.setCreDeptId(UserUtil.getCruDeptId());
			order.setCreDeptName(UserUtil.getCruDeptName());
			order.setCreChushiId(UserUtil.getCruChushiId());
			order.setCreChushiName(UserUtil.getCruChushiName());
			order.setCreJuId(UserUtil.getCruJuId());
			order.setCreJuName(UserUtil.getCruJuName());
			order.setCardNo(cruUser.getCardNumber());
			order.setVisible("1");
			order.setCreTime(cruDate);
			//查询该外卖最大的一个订单序号号，加+1，设置到订单中
			StringBuilder orderNumSql = new StringBuilder(" select max(t.ORDER_NO) orderNo  from wmgl_order_info t  where t.take_out_id = ? ");

			Integer orderNo = 1;
			try {
				orderNo = jdbcTemlate.queryForObject(orderNumSql.toString(), Integer.class, takeOutId) + 1;
			} catch (Exception e) {
				// 该年度还没有订单
			}
			String orderNum = "E" + title + "00" + orderNo;
			order.setOrderNo(orderNo);
			order.setOrderNum(orderNum);
			//保存订单
			newOrder = dao.save(order);
			
		}else {
			StringBuilder orderSql = new StringBuilder("select t.id,t.goods_id goodId, t.goods_num goodNum from wmgl_order_detil t  ");
			orderSql.append(" left outer join wmgl_order_info t1  on t.order_id = t1.id  where t1.take_out_id=? and t1.cre_user_id='"+UserUtil.getCruUserId()+"' and t1.visible='1' and t1.status='1' ");
			
			List<OrderDetil> orderDetil =jdbcTemlate.query(orderSql.toString(), new RowMapper<OrderDetil>() {
				@Override
				public OrderDetil mapRow(ResultSet result, int arg1) throws SQLException {
					OrderDetil detil = new OrderDetil();
					String id = result.getString("id");
					String goodId = result.getString("goodId");
					detil.setId(id);
					detil.setGoodsNum(result.getInt("goodNum"));
					detilMap.put(goodId, detil);
					return detil;
				}
				
			},takeOutId);
			
			newOrder.setId(orderId);
		}
		
		//保存订单详情
		List<OrderDetil> detilList = new ArrayList<OrderDetil>();
		for (ShoppingCart shoppingCart : cartList) {
			OrderDetil detil = new OrderDetil();
			detil.setOrderId(newOrder.getId());
			detil.setCategoryId(shoppingCart.getCategoryId());
			detil.setCategoryName(shoppingCart.getCategoryName());
			detil.setGoodsId(shoppingCart.getGoodsId().toString());
			OrderDetil orderDetil = detilMap.get(shoppingCart.getGoodsId().toString());
			if(orderDetil!=null) {//订单中已有该商品，只需要修改数量即可
				detil.setId(orderDetil.getId());
				detil.setGoodsNum(shoppingCart.getGoodsNum()+orderDetil.getGoodsNum());
			}else {
				detil.setGoodsNum(shoppingCart.getGoodsNum());
			}
			
			detil.setGoodsName(shoppingCart.getGoodsName().toString());
			detil.setGoodsPrice(shoppingCart.getPrice());
			//detil.setTotalPrice(shoppingCart.getTotalPrice());
			detil.setValuationUnit(shoppingCart.getValuationUnit());
			detil.setBugUnit(shoppingCart.getBuyUnit());
			detil.setCreUserId(cruUserId);
			detil.setCreUserName(UserUtil.getCruUserName());
			detil.setCreDeptId(UserUtil.getCruDeptId());
			detil.setCreDeptName(UserUtil.getCruDeptName());
			detil.setCreChushiId(UserUtil.getCruChushiId());
			detil.setCreChushiName(UserUtil.getCruChushiName());
			detil.setCreJuId(UserUtil.getCruJuId());
			detil.setCreJuName(UserUtil.getCruJuName());
			detil.setCreTime(cruDate);
			detilList.add(detil);
		}
		detilDao.save(detilList);
		return newOrder.getId();
		//清空购物车
		//StringBuilder deleSql = new StringBuilder(" delete from wmgl_SHOPPING_CART where take_out_id = ? ");
		//jdbcTemlate.update(deleSql.toString(), takeOutId);
	}

	/** 
	 * TODO 查询订单的分页数据
	 * @author 李颖洁  
	 * @date 2019年5月15日上午11:31:43
	 * @param pageable
	 * @param pageImpl
	 * @param status
	 * @param cardNo
	 * @param orderNum
	 * @return PageImpl
	 */
	@Override
	public PageImpl getOrderList(PageImpl pageImpl, String status, String cardNo, String orderNum,String takeOutId) {
		StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        List<Object> totalPara = new ArrayList<>();
        querySql.append("select t.order_num,t.card_no,t.status,t.id,t1.goodsName,t.cre_user_id,t.phone	"
        		+ "from wmgl_order_info t  "
        		+ "left join (select to_char(wm_concat(distinct t.goods_name)) goodsName,t.order_id from wmgl_order_detil t group by t.order_id) t1 "
        		+ "on t1.order_id = t.id  "
        		+ "	where t.take_out_id = ? and t.visible=?");
        para.add(takeOutId);
        para.add(CommonConstants.VISIBLE[1]);
        totalPara.add(takeOutId);
        totalPara.add(CommonConstants.VISIBLE[1]);
        //拼接条件
        if (StringUtils.isNotBlank(status)) {
            querySql.append("   and t.status =?");
            para.add(status);
            totalPara.add(status);
        }
        if (StringUtils.isNotBlank(cardNo)) {
            querySql.append("   and t.card_no like ?");
            para.add("%"+cardNo+"%");
            totalPara.add("%"+cardNo+"%");
        }
        if (StringUtils.isNotBlank(orderNum)) {
        	querySql.append("   and t.order_num like ?");
        	para.add("%"+orderNum+"%");
        	totalPara.add("%"+orderNum+"%");
        }
        
     // 拼接排序语句
     	if (StringUtils.isBlank(pageImpl.getSortName())) {
     			querySql.append("  order by t.card_no,t.order_no  ");
     	} else {
     		String sortName = pageImpl.getSortName();
			if("orderNum".equals(sortName)) {
				sortName = "t.order_no";
			}else {
				sortName = "t.card_no";
			}
			querySql.append("  order by " + sortName + " " + pageImpl.getSortOrder() + " ");
     	}
        
        
		String sql = "select s.* from (select s.*,rownum rn from ("+querySql.toString()+") s  where rownum<= ?) s where rn>= ?";
		para.add(pageImpl.getPageNumber()*pageImpl.getPageSize());
		para.add((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
        List<OrderInfo> list = jdbcTemlate.query(sql, para.toArray(),new BeanPropertyRowMapper<>(OrderInfo.class));
        Integer total = 0;
        if(list!=null&&!list.isEmpty()) {
			StringBuilder totalSql = new StringBuilder();
			totalSql.append("select count(1) total from ("+querySql+")");
			total = jdbcTemlate.queryForObject(totalSql.toString(), Integer.class, totalPara.toArray());
		}
        return new PageImpl("1",total,list);
	}

	/** 
	 * TODO 根据外卖id查询订单数据
	 * @author 李颖洁  
	 * @date 2019年5月16日下午2:50:09
	 * @param status
	 * @param cardNo
	 * @param orderNum
	 * @param takeOutId
	 * @return
	 */
	@Override
	public List<OrderInfo> getOrderDate(String status, String cardNo, String orderNum, String takeOutId) {
		StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        querySql.append("select t.order_num,t.card_no,t.status,t.id,t1.goodsName,t.phone	"
        		+ "from wmgl_order_info t  "
        		//+ "left join (select to_char(wm_concat(distinct t.goods_name)) goodsName,t.order_id from wmgl_order_detil t group by t.order_id) t1 "
        		+ "left join (select to_char(replace(wm_concat(nvl(goods_name,'')||':'||' '||goods_num||buy_unit ),',','\r\n')) as goodsName,order_id from wmgl_order_detil group by order_id) t1 "
        		+ "on t1.order_id = t.id  "
        		+ "	where t.take_out_id = ? and t.visible = ? ");
        para.add(takeOutId);
        para.add(CommonConstants.VISIBLE[1]);
        //拼接条件
        if (StringUtils.isNotBlank(status)) {
            querySql.append("   and t.status =?");
            para.add(status);
        }
        if (StringUtils.isNotBlank(cardNo)) {
            querySql.append("   and t.card_no like ?");
            para.add("%"+cardNo+"%");
        }
        if (StringUtils.isNotBlank(orderNum)) {
        	querySql.append("   and t.order_num like ?");
        	para.add("%"+orderNum+"%");
        }
        //拼接排序语句
        querySql.append("  order by t.card_no asc  ");
        
        List<OrderInfo> list = jdbcTemlate.query(querySql.toString(), para.toArray(),new BeanPropertyRowMapper<>(OrderInfo.class));
        
		return list;
	}
	
	/**
	 * 修改订单状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月16日 上午10:20:59
	 * @param id
	 * @param status
	 */
	@Override
	public void updateState(String ids,String orderUserId, String status) {
		//OrderInfo orderInfo = dao.findOne(id);
		Date cruDate = new Date();
		String cruDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cruDate);
		String cruYear = cruDateStr.substring(0, 4);
		List<Object> param = new ArrayList<>();
		param.add(status);
		String idStr = CommonUtils.solveSqlInjectionOfIn(ids, param);
		StringBuilder updateSql = new StringBuilder("update WMGL_ORDER_INFO set LOSE_CREDIT='0',status=? where id in ( " + idStr + ")" );
		if(!"4".equals(status)) {
			//已取消或已领取
			jdbcTemlate.update(updateSql.toString(), param.toArray());
		}else {
			//更新本条订单状态和未被统计过字段
			jdbcTemlate.update(updateSql.toString(), param.toArray());
			//查询当前年的配置信息
			StringBuilder configSql = new StringBuilder(" select t.lost_credit_limt CreditLimt,t.lock_time lockTime from WMGL_CONFIG t where period  = ? ");
			Map<String,Object> configMap = jdbcTemlate.queryForMap(configSql.toString(), cruYear);
			//先查询某些用户当前年没有被统计过的失信订单数
			param.clear();
			String orderUserIdStr = CommonUtils.solveSqlInjectionOfIn(orderUserId, param);
			StringBuilder userCntSql = new StringBuilder(" select t.cre_user_id userId, count(1) cnt   from wmgl_order_info t   ");
			userCntSql.append(" where  LOSE_CREDIT='0' and t.visible='1' and t.STATUS='4' and t.cre_user_id in (" + orderUserIdStr + ")  group by t.cre_user_id ");
			List<Map<String,Object>> userInfoMap = jdbcTemlate.queryForList(userCntSql.toString(),param.toArray());
			Integer LOCKTIME=3;//默认的解锁时间是3个月后
			if(configMap!=null && configMap.get("LOCKTIME")!=null) {
				 LOCKTIME=Integer.valueOf(configMap.get("LOCKTIME").toString());
			}
			Integer CREDITLIMT=3;//默认订单超过3次被锁定
			if(configMap!=null && configMap.get("CREDITLIMT")!=null) {
				CREDITLIMT=Integer.valueOf(configMap.get("CREDITLIMT").toString());
			}
			List<WmgLosecredit> loseResult = new ArrayList<WmgLosecredit>();//存放失信人记录
			for (Map<String, Object> map : userInfoMap) {
				 SysFlowUserVo userVo = UserUtil.getUserVo(map.get("USERID").toString()).get(map.get("USERID").toString());
				 Integer cnt = Integer.valueOf(map.get("CNT").toString());
				 if(cnt>=CREDITLIMT) {
					 StringBuilder sql = new StringBuilder("update WMGL_ORDER_INFO set LOSE_CREDIT='1' where CRE_USER_ID='" + map.get("USERID").toString() + "' ");
						jdbcTemlate.update(sql.toString());
					 //失信的次数大于最大的失信限制
					 WmgLosecredit loseCredit = new WmgLosecredit();
					 loseCredit.setLoseTime(cruDateStr);//锁定时间
					 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				     Calendar calendar = Calendar.getInstance();
				     calendar.setTime(cruDate);
				      calendar.add(Calendar.MONTH, LOCKTIME);
				      String relieveTime = sdf.format(calendar.getTime());
					 loseCredit.setRelieveTime(relieveTime);
					 loseCredit.setRelieveType("0");
					
					 loseCredit.setVisible("1");
					 loseCredit.setCardNum(userVo.getCardNumber());
					 loseCredit.setLoseChushiId(userVo.getUserChushiId());
					 loseCredit.setLoseChushiName(userVo.getUserChushiName());
					 loseCredit.setLoseJuId(userVo.getUserJuId());
					 loseCredit.setLoseJuName(userVo.getUserJuName());
					 loseCredit.setLoseUserDeptId(userVo.getUserDeptId());
					 loseCredit.setLoseUserDeptName(userVo.getUserDeptName());
					 loseCredit.setLoseUserId(userVo.getUserId());
					 loseCredit.setLoseUserName(userVo.getUserName());
					 loseCredit.setCreTime(cruDateStr);
					 //设置失信人的创建信息
					 loseCredit.setCreChushiId(UserUtil.getCruChushiId());
					 loseCredit.setCreChushiName(UserUtil.getCruChushiName());
					 loseCredit.setCreDeptId(UserUtil.getCruDeptId());
					 loseCredit.setCreDeptName(UserUtil.getCruDeptName());
					 loseCredit.setCreJuId(UserUtil.getCruJuId());
					 loseCredit.setCreJuName(UserUtil.getCruJuId());
					 loseCredit.setCreUserId(userVo.getUserId());
					 loseResult.add(loseCredit);
				 }
			}
			creditDao.save(loseResult);
			
			
			 
		}
		
	}
	
	public void updateState1(String id, String status) {
		List<Object> para = new ArrayList<>();
		OrderInfo orderInfo = dao.findOne(id);
		Date cruDate = new Date();
		String cruDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cruDate);
		String cruYear = cruDateStr.substring(0, 4);
		List<Object> param = new ArrayList<>();
		StringBuilder updateSql = new StringBuilder("update WMGL_ORDER_INFO set LOSE_CREDIT='0',status=? where id= ? ");
		param.add(status);
		param.add(id);
		if(!"4".equals(status)) {
			//已取消或已领取
			jdbcTemlate.update(updateSql.toString(), param.toArray());
		}else {
			//更新本条订单状态和未被统计过字段
			jdbcTemlate.update(updateSql.toString(), param.toArray());
			//查询当前年的配置信息
			StringBuilder configSql = new StringBuilder(" select t.lost_credit_limt CreditLimt,t.lock_time lockTime from WMGL_CONFIG t where period  = ? ");
			Map<String,Object> configMap = jdbcTemlate.queryForMap(configSql.toString(), cruYear);
			//先查询当前年该用户没有被统计过的失信外卖id的订单
			StringBuilder orderSql = new StringBuilder(" select t.take_out_id takeOutId,to_char(wm_concat(id))  orderId from WMGL_ORDER_INFO t where SUBSTR(t.cre_time, 0, 4) =" + cruYear + " and t.cre_user_id='" + orderInfo + "' and t.lose_credit='0' group by t.take_out_id ");
			List<Map<String,Object>> orders = jdbcTemlate.queryForList(orderSql.toString());
			Integer CREDITLIMT=3;//默认订单超过3次被锁定
			if(configMap!=null && configMap.get("CREDITLIMT")!=null) {
				CREDITLIMT=Integer.valueOf(configMap.get("CREDITLIMT").toString());
			}
			if(orders.size() >= CREDITLIMT) {
				param.clear();
				//加上本条订单正好n条(通过基础配置中获得)没被统计过失信外卖的信息，,需要把查出来的这几条外卖的订单改被统计过
				StringBuilder takeOutIds = new StringBuilder(); //外卖的id
				StringBuilder orderIds = new StringBuilder();
				for (Map<String,Object> map : orders) {
					takeOutIds.append(map.get("TAKEOUTID")).append(",");
					orderIds.append(map.get("ORDERID")).append(",");
				}
				StringBuilder sql = new StringBuilder("update WMGL_ORDER_INFO set LOSE_CREDIT='1' where id in (" + CommonUtils.solveSqlInjectionOfIn(orderIds.toString(),param) + ") ");
				jdbcTemlate.update(sql.toString(), param.toArray());
				takeOutIds.deleteCharAt(takeOutIds.length()-1);
				
				//插入一条失信记录
				WmgLosecredit loseCredit = new WmgLosecredit();
				 SysFlowUserVo userVo = UserUtil.getUserVo(orderInfo.getCreUserId()).get(orderInfo.getCreUserId());
				 //设置失信人信息
				 loseCredit.setLoseTime(cruDateStr);//锁定时间
				 Integer LOCKTIME=3;//默认的解锁时间是3个月后
				 if(configMap!=null && configMap.get("LOCKTIME")!=null) {
					 LOCKTIME=Integer.valueOf(configMap.get("LOCKTIME").toString());
				 }
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			     Calendar calendar = Calendar.getInstance();
			     calendar.setTime(cruDate);
			      calendar.add(Calendar.MONTH, LOCKTIME);
			      String relieveTime = sdf.format(calendar.getTime());
				 loseCredit.setRelieveTime(relieveTime);
				 loseCredit.setRelieveType("0");
				 loseCredit.setTakeOutIds(takeOutIds.toString());
				 loseCredit.setVisible("1");
				 loseCredit.setCardNum(userVo.getCardNumber());
				 loseCredit.setLoseChushiId(userVo.getUserChushiId());
				 loseCredit.setLoseChushiName(userVo.getUserChushiName());
				 loseCredit.setLoseJuId(userVo.getUserJuId());
				 loseCredit.setLoseJuName(userVo.getUserJuName());
				 loseCredit.setLoseUserDeptId(userVo.getUserDeptId());
				 loseCredit.setLoseUserDeptName(userVo.getUserDeptId());
				 loseCredit.setLoseUserId(userVo.getUserId());
				 loseCredit.setLoseUserName(userVo.getUserName());
				 //设置失信人的创建信息
				 loseCredit.setCreChushiId(UserUtil.getCruChushiId());
				 loseCredit.setCreChushiName(UserUtil.getCruChushiName());
				 loseCredit.setCreDeptId(UserUtil.getCruDeptId());
				 loseCredit.setCreDeptName(UserUtil.getCruDeptName());
				 loseCredit.setCreJuId(UserUtil.getCruJuId());
				 loseCredit.setCreJuName(UserUtil.getCruJuId());
				 loseCredit.setCreUserId(userVo.getUserId());
				 loseCreditDao.save(loseCredit);
			}
			
		}
		
	}
	
	/**
	 * 根据订单id查询订单详细信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月20日 上午9:23:23
	 * @param id
	 * @return
	 */
	@Override
	public OrderInfo getById(String id) {
		return dao.findOne(id);
	}

	/** TODO 获取当前用户订单列表
	 * @author 李颖洁  
	 * @date 2019年5月22日下午5:17:19
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public PageImpl getMyOrderList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate) {
		StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        querySql.append("select t.order_num,t.card_no,t.status,t.id,t.cre_time,t.phone,t1.goodsName,f.deadline_time deadlineTime,f.title	"
        		+ "from wmgl_order_info t  "
        		+ "left join (select to_char(wm_concat(distinct t.goods_name)) goodsName,t.order_id from wmgl_order_detil t group by t.order_id) t1 "
        		//+ "left join (select to_char(replace(wm_concat(nvl(goods_name,'')||':'||' '||goods_num||buy_unit ),',','\r\n')) as goodsName,order_id from wmgl_order_detil group by order_id) t1 "
        		+ "on t1.order_id = t.id  "
        		+ "left join wmgl_take_out_info f on f.id = t.take_out_id  "
        		+ "	where t.cre_user_id = ? and t.visible= ?");
        para.add(UserUtil.getCruUserId());
        para.add(CommonConstants.VISIBLE[1]);
        //拼接条件
        if (StringUtils.isNotBlank(startDate)) {
            querySql.append("   and substr(t.cre_time,0,10) >= ?");
            para.add(startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            querySql.append("   and substr(t.cre_time,0,10) <= ?");
            para.add(endDate);
        }
        //拼接排序语句
        querySql.append("  order by t.cre_time desc  ");
        //求总数
        StringBuilder totalSql = new StringBuilder("select count(1) total from ("+querySql.toString()+")");
		Integer total = jdbcTemlate.queryForObject(totalSql.toString(), Integer.class, para.toArray());
		//列表数据
		String sql = "select s.* from (select s.*,rownum rn from ("+querySql.toString()+") s  where rownum<= ?) s where rn>= ?";
		para.add(pageImpl.getPageNumber()*pageImpl.getPageSize());
		para.add((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
        List<OrderInfo> list = jdbcTemlate.query(sql.toString(), para.toArray(),new BeanPropertyRowMapper<>(OrderInfo.class));
		return new PageImpl(total,list);
	}
	
	/**
	 * 获取商品总个数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年6月5日 下午3:27:27
	 * @param takeOutId
	 * @return
	 */
	@Override
	public Integer getGoodsNum(String takeOutId,String categoryId) {
		StringBuilder sql = new StringBuilder(" select sum(t1.GOODS_NUM) goodsNum from wmgl_order_info t  left outer join wmgl_order_detil t1 on t.id=t1.order_id ");
		sql.append(" where t.take_out_id= ? and t.visible='1' and t.status!='2' and t1.category_id = ? ");	
		Integer goodsNum = jdbcTemlate.queryForObject(sql.toString(), Integer.class,takeOutId,categoryId);
		return goodsNum;
	}

	/** 
	 * TODO 删除订单
	 * @author 李颖洁  
	 * @date 2019年6月12日上午11:04:22
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		List<Object> params = new ArrayList<>();
		params.add(CommonConstants.VISIBLE[0]);
		String inString = CommonUtils.solveSqlInjectionOfIn(id,params);
		return dao.update("update OrderInfo t set t.visible = ? where t.id in (" + inString + ")", params.toArray());
	}
	
	 /**
	    * 获取当前用户某次外卖的订单id和手机号
	    * TODO 
	    * @author 马秋霞
	    * @Date 2019年6月13日 下午5:00:24
	    * @param takeOutId
	    * @return
	    */
	@Override
	public JSONObject getOrderIdAndPhone(String takeOutId) {
		JSONObject jsonObj = new JSONObject();
		StringBuilder sql = new StringBuilder("select ");
		sql.append(" (select t.id from wmgl_order_info t where t.TAKE_OUT_ID=? and t.status='1' and t.cre_user_id='" + UserUtil.getCruUserId() + "' and t.visible='1') orderId, ");
		sql.append(" (select * from (select t.phone from wmgl_order_info t where visible='1' and cre_user_id = '" + UserUtil.getCruUserId() + "' and t.phone is not null order by t.cre_time desc) t1 where rownum = 1) phone  ");
		sql.append(" ,0 flg ");
		sql.append(" from dual ");
		Map<String,Object> map = jdbcTemlate.queryForMap(sql.toString(),takeOutId);
		if(map.get("ORDERID")!=null) {
			jsonObj.put("orderId", map.get("ORDERID").toString());
			StringBuilder orderSql = new StringBuilder(" select t.category_id categoryId,t.category_name categoryName,t.goods_id goodsId,t.goods_name goodsName,"
					+ "t.goods_num goodsNum,t.buy_unit buyUnit,t.valuation_unit valuationUnit,t3.buy_limit buyLimit,t3.buy_num buyNum,t4.id mealId,t4.optNum mealOptNum ");
			orderSql.append("  from wmgl_order_detil t left outer join wmgl_order_info t1  on t.order_id = t1.id ");
			orderSql.append(" left outer join (select t.goods_id,t.buy_limit,t.buy_num from wmgl_take_out_detail t where t.take_out_id=?) t3 on t.goods_id=t3.goods_id");
			
			orderSql.append("  left outer join ( select distinct * from ( select regexp_substr(q.goods_ids, '[^,]+', 1, Level,'i') goods_id,q.goods_ids,q.id,q.opt_num optNum  from ( ");
			orderSql.append(" select * from wmgl_set_meal where take_out_id = ?  ) q  ");
			orderSql.append(" connect by Level <= LENGTH(q.goods_ids) - LENGTH(REGEXP_REPLACE(q.goods_ids, ',', '')) + 1) t order by t.id ");
			orderSql.append(" )t4 on t.goods_id=t4.goods_id ");
			
			
			orderSql.append(" where t1.id=? and t1.visible = '1'  and t1.status = '1' and t1.take_out_id = ? and t.cre_user_id='" + UserUtil.getCruUserId()+ "' ");
			List<ShoppingCart> orderList = jdbcTemlate.query(orderSql.toString(), new RowMapper<ShoppingCart>() {
				@Override
				public ShoppingCart mapRow(ResultSet result, int arg1) throws SQLException {
					ShoppingCart cart = new ShoppingCart();
					cart.setMealId(result.getString("mealId"));
					cart.setMealOptNum(result.getInt("mealOptNum"));
					
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
			},takeOutId,takeOutId,map.get("ORDERID").toString(),takeOutId);
			jsonObj.put("orderList", orderList);
		}
		if(map.get("PHONE")!=null) {
			jsonObj.put("phone", map.get("PHONE").toString());
		}
		return jsonObj;
	}
}
