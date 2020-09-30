package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.dao.CategoryDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.dao.GoodsDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.entity.Goods;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.entity.GoodsTreeEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;

@Service
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	GoodsDao dao;
	@Autowired
	CategoryDao categoryDao;
	
	/**
	 * 获取某门类下的所有商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 下午5:13:15
	 * @param categoryId
	 * @return
	 */
	@Override
	public List<Goods> getList(String GoodsName,String isUse,String categoryId) {
		List<String> params = new ArrayList<String>();
		StringBuilder sql = new StringBuilder("select t.id,t.goods_name goodsName,t.price,t.VALUATION_UNIT valuUnit,t.is_use isUse,t1.table_id imageId,t.STATUS status ");
		sql.append(" from WMGL_Goods t left outer join sys_affix t1 on t.id=t1.table_id where t.visible=? and t.belong_id= ?  ");
		params.add("1");
		params.add(categoryId);
		if(StringUtils.isNotBlank(GoodsName)) {
			sql.append(" and t.goods_name like ? ");
			params.add("%" + GoodsName + "%");
		}
		if(StringUtils.isNotBlank(isUse)) {
			sql.append(" and t.is_use = ? ");
			params.add(isUse);
		}
		sql.append(" order by t.CRE_TIME ");
		List<Goods>	list = jdbcTemplate.query(sql.toString(), new RowMapper<Goods>() {
				@Override
				public Goods mapRow(ResultSet result, int arg1) throws SQLException {
					Goods goods = new Goods();
					goods.setGoodsName(result.getString("goodsName"));
					goods.setPrice(result.getDouble("price"));
					goods.setId(result.getString("id"));
					//goods.setValuUnit(result.getString("valuUnit"));
					goods.setValuationUnit(result.getString("valuUnit"));
					goods.setIsUse(result.getString("isUse"));
					goods.setImageId(result.getString("imageId"));
					goods.setStatus(result.getString("status"));
					return goods;
				}
				
			},params.toArray());
		return list;
	}

	/**
	 * 保存商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 下午8:59:27
	 * @param entity
	 * @return
	 */
	@Override
	public Goods saveFrom(Goods entity,String flg) {
		Goods goods = new Goods();
        if(StringUtils.isBlank(entity.getId())){
            entity.setVisible("1");
            entity.setCreUserId(UserUtil.getCruUserId());
            entity.setCreUserName(UserUtil.getCruUserName());
            entity.setCreChushiId(UserUtil.getCruChushiId());
            entity.setCreChushiId(UserUtil.getCruChushiId());
            entity.setCreChushiName(UserUtil.getCruChushiName());
            entity.setCreJuId(UserUtil.getCruJuId());
            entity.setCreJuName(UserUtil.getCruJuName());
            entity.setCreTime(JDateToolkit.getNowDate1());
            entity = dao.save(entity);
        }else {
        	goods = dao.findOne(entity.getId());
        	goods.setUpdateUserId(UserUtil.getCruUserId());
        	goods.setUpdateUserName(UserUtil.getCruUserName());
        	goods.setUpdateTime(JDateToolkit.getNowDate1());
        	goods.setIsUse(entity.getIsUse());
        	goods.setDescribe(entity.getDescribe());
        	goods.setGoodsName(entity.getGoodsName());
        	goods.setValuationUnit(entity.getValuationUnit());
        	goods.setStatus(entity.getStatus());
        	goods.setBuyUnit(entity.getBuyUnit());
        	goods.setPrice(entity.getPrice());
        	goods.setMark(entity.getMark());
        	goods.setAmountLimit(entity.getAmountLimit());
        	goods.setAmountNum(entity.getAmountNum());
        	goods.setBuyLimit(entity.getBuyLimit());
        	goods.setBuyNum(entity.getBuyNum());
            goods = dao.save(goods);
        }
  
        return entity;
	}

	@Override
	public int delete(String id) {
		int result = 0;
		String jpql = "update Goods t set t.visible = ? where t.id = ?";
		try {
			result = dao.update(jpql, CommonConstants.VISIBLE[0], id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

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
	@Override
	public PageImpl getPageList1(Pageable pageable, PageImpl pageImpl, String goodsName, String isUse,String categoryId) throws Exception {
		List<Object> para = new ArrayList<>();
		//总个数
    	StringBuilder sb = new StringBuilder("select count(1) from (");
    	querySql(sb,goodsName,isUse,categoryId,para);
    	sb.append(")");
    	Integer total=jdbcTemplate.queryForObject(sb.toString(), Integer.class,para.toArray());
    	//查询分页数据
    	para.clear();
		String pageSql = pageSql(pageable,goodsName,isUse,categoryId,para);
		List<Goods> listData =jdbcTemplate.query(pageSql.toString(), new RowMapper<Goods>(){
			@Override
			public Goods mapRow(ResultSet result, int arg1) throws SQLException {
				
				Goods goods = new Goods();
				goods.setId(result.getString("id"));
				goods.setGoodsName(result.getString("goodsName"));
				goods.setPrice(result.getDouble("price"));
				goods.setAmountLimit(result.getString("amountLimit"));
				goods.setId(result.getString("id"));
				//goods.setValuUnit(result.getString("valuUnit"));
				goods.setValuationUnit(result.getString("valuUnit"));
				goods.setIsUse(result.getString("isUse"));
				goods.setImageId(result.getString("imageId"));
				goods.setStatus(result.getString("status"));
				return goods;
			}
			
		},para.toArray());
		return new PageImpl(total,listData);
	}
	//获取分页sql
	private String pageSql(Pageable pageable, String goodsName, String isUse, String categoryId, List<Object> para) {
		StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
		querySql(sb,goodsName,isUse,categoryId,para);
		//sb.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
		sb.append("  ) a1 where ROWNUM <= ? ) where RN >=  ?");
		para.add(pageable.getOffset()+pageable.getPageSize());
		para.add(pageable.getOffset()+1);
		return sb.toString();
	}

	//拼sql语句
	private void querySql(StringBuilder sb, String goodsName, String isUse, String categoryId, List<Object> para) {
		sb.append("select t.id,t.goods_name goodsName,t.price,t.VALUATION_UNIT valuUnit,t.is_use isUse,t1.table_id imageId,t.STATUS status,t.amount_limit amountLimit ");
		sb.append(" from WMGL_Goods t left outer join sys_affix t1 on t.id=t1.table_id where t.visible=? and t.belong_id= ?  ");
		para.add("1");
		para.add(categoryId);
		if(StringUtils.isNotBlank(goodsName)) {
			sb.append(" and t.goods_name like ? ");
			para.add("%" + goodsName + "%");
		}
		if(StringUtils.isNotBlank(isUse)) {
			sb.append(" and t.is_use = ? ");
			para.add(isUse);
		}
		sb.append(" order by t.CRE_TIME ");
		
	}

	/**
	 * 根据id查询商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月8日 下午1:41:36
	 * @param id
	 * @return
	 */
	@Override
	public Goods getById(String id) {
		StringBuilder sql = new StringBuilder("select t.id,t.is_use isUse,t.goods_name goodsName,t.price,t.valuation_unit VUnit,t.status,t.buy_limit buyLimit,t.buy_num buyNum, "
				+ "t.buy_unit buyUnit,t.describe,t.mark,t1.type_name categoryName,t2.id imageId,t.amount_limit amountLimit,t.amount_num  amountNum");
		sql.append(" from wmgl_goods t left outer join wmgl_category t1 on t.belong_id=t1.id ");
		sql.append("  left outer join sys_affix t2 on t.id=t2.save_path ");
		sql.append(" where t.id = ? ");
		Map<String,Object> map = jdbcTemplate.queryForMap(sql.toString(),id);
		Goods goods = new Goods();
		goods.setId(map.get("ID").toString());
		goods.setIsUse(map.get("ISUSE").toString());
		goods.setGoodsName(map.get("GOODSNAME").toString());
		goods.setPrice(Double.valueOf(map.get("PRICE").toString()));
		goods.setValuationUnit(map.get("VUNIT").toString());
		goods.setBuyUnit(map.get("BUYUNIT").toString());
		goods.setStatus(map.get("STATUS").toString());
		goods.setAmountLimit(map.get("AMOUNTLIMIT").toString());
		if(map.get("BUYLIMIT")!=null) {
			goods.setBuyLimit(map.get("BUYLIMIT").toString());
		}else {
			goods.setBuyLimit("0");
		}
		
		if(map.get("AMOUNTNUM")!=null) {
			goods.setAmountNum(map.get("AMOUNTNUM").toString());
		}
		if(map.get("BUYNUM")!=null) {
			goods.setBuyNum(Integer.valueOf(map.get("BUYNUM").toString()));
		}
		if(map.get("DESCRIBE")!=null) {
			goods.setDescribe(map.get("DESCRIBE").toString());
		}
		if(map.get("MARK")!=null) {
			goods.setMark(map.get("MARK").toString());
		}
		goods.setCategoryName(map.get("CATEGORYNAME").toString());
		if(map.get("IMAGEID")!=null) {
			goods.setImageId(map.get("IMAGEID").toString());
		}
		return goods;
	}
	
	/**
	 * 获取商品树
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月10日 上午11:49:56
	 * @return
	 */
	Integer index = 0;
	@Override
	public List<GoodsTreeEntity> treeList() {
		StringBuilder sql = new StringBuilder(" select * from ( ");
		sql.append(" select t.id,t.goods_name name,t.belong_id pid,'1' as flg,t.price goodsPrice,t.valuation_unit valuationUnit,t.buy_unit buyUnit,t.CRE_TIME,t.Amount_Limit amountLimit,t.amount_num goodsNum,t.buy_limit buyLimit,t.buy_num buyNum from wmgl_goods t where t.visible='1' and t.status='1' and t.IS_USE='1' ");
		sql.append(" union  ");
		sql.append(" select t.id,t.type_name name,t.SUPER_ID pid,'0' as flg,0 as goodsPrice,'0' as valuationUnit,'0' as buyUnit,t.CRE_TIME,'0' amountLimit,0 goodsNum,'0' buyLimit,0 buyNum  from wmgl_category t where t.visible='1' ");
		sql.append(" ) a order by a.cre_time ");
		index = 0;
		List<GoodsTreeEntity> list = jdbcTemplate.query(sql.toString(), new RowMapper<GoodsTreeEntity>() {
			@Override
			public GoodsTreeEntity mapRow(ResultSet result, int arg1) throws SQLException {
				GoodsTreeEntity entity = new GoodsTreeEntity();
				if("0".equals(result.getString("flg"))) {
					//查询的是门类
					entity.setCategorySort(index);
					index = index+1;
				}
				entity.setId(result.getString("id"));
				entity.setAmountLimit(result.getString("amountLimit"));
				entity.setBuyLimit(result.getString("buyLimit"));
				entity.setBuyNum(result.getInt("buyNum"));
				entity.setGoodsNum(result.getInt("goodsNum"));
				entity.setName(result.getString("name"));
				entity.setpId(result.getString("pid"));
				entity.setGoodsPrice(result.getDouble("goodsPrice"));
				entity.setValuationUnit(result.getString("valuationUnit"));
				entity.setBuyUnit(result.getString("buyUnit"));
				entity.setFlg(result.getString("flg"));
				return entity;
			}
			
		});
		return list;
	}
	

}
