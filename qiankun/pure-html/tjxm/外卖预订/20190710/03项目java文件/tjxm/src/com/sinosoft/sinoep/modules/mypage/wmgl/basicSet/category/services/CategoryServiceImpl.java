package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.dao.CategoryDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.CategoryTreeEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;

import net.sf.json.JSONObject;

/**
 * 门类的实现类
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 上午11:10:54
 */
@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
    private CategoryDao dao;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月10日 上午10:42:11
	 * @param id
	 * @return
	 */
	@Override
	public Category getCategoryCategById(String id) {
		Category entity = new Category();
        if(StringUtils.isNotBlank(id)){
            try{
                entity = dao.findOne(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return entity;
	}
	
	/**
	 * 保存门类信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 上午11:11:39
	 * @param entity
	 * @param isFirst
	 * @return
	 */
	@Override
	public Category saveFroms(Category entity) {
		Category entityDB = new Category();
		String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	        try {
	            if(StringUtils.isBlank(entity.getId())){
	                entity.setVisible("1");
	                entity.setCreUserId(UserUtil.getCruUserId());
	                entity.setCreUserName(UserUtil.getCruUserName());
	                entity.setCreDeptId(UserUtil.getCruDeptId());
	                entity.setCreDeptName(UserUtil.getCruDeptName());
	                entity.setCreUserId(UserUtil.getCruUserId());
	                entity.setCreUserName(UserUtil.getCruUserName());
	                entity.setCreChushiId(UserUtil.getCruChushiId());
	                entity.setCreChushiName(UserUtil.getCruChushiName());
	                entity.setCreJuId(UserUtil.getCruJuId());
	                entity.setCreJuName(UserUtil.getCruJuName());
	                entity.setCreTime(cruDate);
	                entityDB = dao.save(entity);
	            }else{
	                entity.setVisible("1");
	                entity.setUpdateUserId(UserUtil.getCruUserId());
	                entity.setUpdateUserName(UserUtil.getCruUserName());
	                entity.setUpdateTime(JDateToolkit.getNowDate1());
	                entityDB = dao.save(entity);
	            }
	            
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        return entityDB;
	}
	
	/**
	 * 获取门类
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 上午11:31:08
	 * @return
	 */
	@Override
	public JSONObject getCategoryTree() {
		JSONObject json = new JSONObject();
        json.put("flag","0");
		try {
			StringBuilder sql = new StringBuilder(
					" select t.id,t.type_name typeName,t.super_id superId,t.order_no orderNo,t.is_first isFirst,t.NODE_LEVEL nodeLevel ");
			sql.append(" from wmgl_category t where t.visible='1' order by t.cre_time  ");
			List<CategoryTreeEntity> treeList = jdbcTemplate.query(sql.toString(), new RowMapper<CategoryTreeEntity>() {
				@Override
				public CategoryTreeEntity mapRow(ResultSet result, int arg1) throws SQLException {
					CategoryTreeEntity categoryTree = new CategoryTreeEntity();
					categoryTree.setId(result.getString("id"));
					categoryTree.setName(result.getString("typeName"));
					categoryTree.setpId(result.getString("superId"));
					categoryTree.setOrderNo(result.getString("orderNo"));
					categoryTree.setIsFirst(result.getString("isFirst"));
					categoryTree.setNodeLevel(result.getString("nodeLevel"));
					return categoryTree;
				}
			});
			json.put("flag", "1");
			json.put("data", treeList);
		} catch (Exception e) {
			 e.printStackTrace();
	         json.put("flag","-1");
	         json.put("msg","获取失败!");
		}
		return json;
	}
	
	/**
	 * 根据门类id删除门类
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 下午4:07:42
	 * @param ids
	 * @return
	 */
	@Override
	public int delete(String ids) {
		int n = 0;
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idsArry = ids.split(",");
				for (String id : idsArry) {
					// 删除门类
					n = dao.delVisible(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}
	

}
