package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.dao.SetMealDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.entity.SetMeal;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 套餐组合serviceImp
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月9日 上午11:53:03
 */
@Service
public class SetMealServiceImpl implements SetMealService{
	
	@Autowired
	SetMealDao dao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * TODO 根据外卖和门类id获取所有的套餐详情
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:24:54
	 * @param infoId 外卖id
	 * @param classId 门类id
	 * @return List<SetMeal>
	 */
	@Override
	public List<SetMeal> getDetilByInfoId(String infoId, String classId) {
		List<String> param = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		sql.append("select  m.id,m.opt_num optNum,m.goods_ids goodsIds,t1.goodsNames	from wmgl_set_meal m from wmgl_set_meal m left join (");
		sql.append("select to_char(wm_concat(d.goods_name)) goodsNames,count(t.goods_id) sum,t.opt_num,t.id from ("
				+ "SELECT id,take_out_id,category_id,REGEXP_SUBSTR (goods_ids, '[^,]+', 1,rownum) goods_id,opt_num	"
				+ "from wmgl_set_meal where take_out_id = ? "
				);
		param.add(infoId);
		if(StringUtils.isNotBlank(classId)) {
			sql.append(" and class_id = ? ");
			param.add(classId);
		}
		sql.append("connect by rownum<=LENGTH (goods_ids) - LENGTH (regexp_replace(goods_ids, ',', ''))+1"
				+ ")t left join wmgl_take_out_detail d on t.take_out_id = d.take_out_id and t.category_id = d.class_id and t.goods_id = d.goods_id	"
				+ "group by t.id,t.opt_num");
		sql.append(" ) t1 on m.id = t1.id order by m.cre_time desc ");
		List<SetMeal> detailList = jdbcTemplate.query(sql.toString(), new RowMapper<SetMeal>() {
			@Override
			public SetMeal mapRow(ResultSet result, int arg1) throws SQLException {
				SetMeal setMeal = new SetMeal();
				setMeal.setId(result.getString("id"));
				setMeal.setOptNum(result.getInt("optNum"));
				setMeal.setGoodsIds(result.getString("goodsIds"));
				setMeal.setGoodsNames(result.getString("goodsNames"));
				return setMeal;
			}
		},param.toArray());
		return detailList;
	}
	
	 /**
	  * TODO 根据外卖id获取外卖中套餐组合的门类
	  * @author 李颖洁  
	  * @date 2019年5月27日下午3:25:20
	  * @param infoId 外卖ID
	  * @return List<Category>
	  */
	@Override
	public List<Category> getCategoryByInfoId(String infoId) {
		StringBuilder sql = new StringBuilder(" select * from( ");
		sql.append("select distinct d.class_id classId,d.class_name className,max(d.category_sort) sort from (select * from wmgl_set_meal t where t.take_out_id = ?)m,wmgl_take_out_detail d "
				+ "	where m.take_out_id = d.take_out_id and m.category_id = d.class_id");
		sql.append(" group by d.class_id,d.class_name ");
		sql.append(" ) t order by t.sort asc ");
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
	 * TODO 根据套餐组合id删除数据
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:27:01
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		String jpql = "delete from SetMeal t  where t.id = ?";
		return dao.update(jpql, id);
	}

	/** 
	 * TODO 
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:36:10
	 * @param meal
	 * @return
	 */
	@Override
	public int saveForm(SetMeal meal) {
		return dao.update("update SetMeal t set t.optNum = ? where t.id = ?", meal.getOptNum(),meal.getId());
	}
	
	/**
	 * 套餐组合
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月27日 下午2:28:46
	 * @param categoryId
	 * @param goodsIds
	 */
	@Override
	public void CombineGoods(String takeOutId,String categoryId, String goodsIds,String goodsNames) {
		String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		SetMeal setMeal = new SetMeal();
		setMeal.setTakeOutId(takeOutId);
		setMeal.setCategoryId(categoryId);
		setMeal.setGoodsIds(goodsIds);
		setMeal.setGoodsNames(goodsNames);
		setMeal.setOptNum(1);
	
		setMeal.setCreUserId(UserUtil.getCruUserId());
		setMeal.setCreUserName(UserUtil.getCruUserName());
		setMeal.setCreDeptId(UserUtil.getCruDeptId());
		setMeal.setCreDeptName(UserUtil.getCruDeptName());
		setMeal.setCreChushiId(UserUtil.getCruChushiId());
		setMeal.setCreChushiName(UserUtil.getCruChushiName());
		setMeal.setCreJuId(UserUtil.getCruJuId());
		setMeal.setCreJuName(UserUtil.getCruJuName());
		setMeal.setCreTime(cruDate);
		
		dao.save(setMeal);
	}

	/** 
	 * TODO 根据外卖id和门类id查询分页套餐数据
	 * @author 李颖洁  
	 * @date 2019年5月27日下午5:12:25
	 * @param pageable
	 * @param pageImpl
	 * @param infoId 外卖id
	 * @param classId 门类id
	 * @return
	 */
	@Override
	public PageImpl getListByClassId(Pageable pageable, PageImpl pageImpl, String infoId, String classId) {
		StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();

        querySql.append("	from SetMeal t   ");
        querySql.append("   where t.takeOutId = ?");
        para.add(infoId);
        if (StringUtils.isNotBlank(classId)) {
        	querySql.append("   and t.categoryId = ?");
        	para.add(classId);
        }else{
        	return new PageImpl(0,new ArrayList<SetMeal>());
        }
        //拼接排序语句
        querySql.append("  order by t.creTime desc  ");

        Page<SetMeal> page = dao.query(querySql.toString(), pageable,para.toArray());
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	

	
	
	

}
