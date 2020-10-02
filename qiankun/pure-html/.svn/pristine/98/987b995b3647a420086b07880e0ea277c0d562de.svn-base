package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.dao;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DataDictionarysDaoImpl implements DataDictionarysRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 查询该类型下的所有子孙类型
     * @param id
     * @return
     */
    @Override
    public List<DataDictionarys> getAllChildTypes(String id){
        String sql = "select * from xxkh_tree t where t.visible = '"+ CommonConstants.VISIBLE[1]+"' and t.type = '0'"
               + " start with t.id = ? connect by prior id = pid order by t.cre_time";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataDictionarys.class),id);
    }
    
    /**
     * 查询学习时长树下指定类型下面的所有的子孙类型
     * TODO 
     * @author 郝灵涛
     * @Date 2018年9月4日 上午10:55:01
     * @param id
     * @return
     */
     @Override
     public List<DataDictionarys> getLearningTimeTreeAllChildTypes(String id){
    	 //查询视图时，现根据类别（节点0，资料1）降序排，然后同类的根据创建时间升序排
         String sql = "select * from xxkh_type_info_view t "
                + " start with t.id = ? connect by prior id = pid order siblings by type desc,cre_time asc";
         return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataDictionarys.class),id);
     }
   

    /**
     * 逻辑删除该类型下的所有子孙类型
     * @param id
     * @return
     */
    @Override
    public int delTypeVisible(String id){
        String sql = "update xxkh_tree t set t.visible = '"+ CommonConstants.VISIBLE[0]+"' where t.id in ("
                + "select d.id from xxkh_tree d start with d.id = ? connect by prior d.id = d.pid )";
        return jdbcTemplate.update(sql,id);
    }

	@Override
	public int findPid(String id) {
		String sql = "select count(1) from xxkh_tree d where pid=?";
		return  jdbcTemplate.queryForObject(sql, Integer.class,id);
	}

	@Override
	public List<DataDictionarys> getDicByType(String treeType, String type) {
		String sql="select t.* from xxkh_tree t where t.tree_type like ? and t.type = ? and  t.pId is null  and t.visible = '1'   order by t.sort";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataDictionarys.class),"%"+treeType+"%",type);
	}

	@Override
	public List<DataDictionarys> getAllpTypes(String id) {
        String sql = "select * from xxkh_tree t where t.visible = '"+ CommonConstants.VISIBLE[1]+"' and t.type = '0'"
                + " start with t.id = ? connect by prior pid = id order by t.cre_time asc";
         return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataDictionarys.class),id);
	}

	@Override
	public List<DataDictionarys> getBuMenTree(String cruJuId, String treeType) {
		String sql="select t.* from xxkh_tree t where t.tree_type like ? and t.type = '0' and  t.pId is null  and t.visible = '1' and  t.tree_type like ? order by t.sort";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DataDictionarys.class),"%"+treeType+"%","%"+cruJuId+"%");
	}

	/**
	 * 根据id获得当前类别类型
	 * @author 王磊
	 * @Date 2018年9月5日 上午11:42:41
	 * @param id
	 * @return
	 */
	@Override
	public String getTreeTypeById(String id) {
		String treeType = "";
		String sql = "select t.tree_type from xxkh_tree t where t.id=?";
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = jdbcTemplate.queryForList(sql,id);
		if(list.size() > 0){
			Map<String,Object> map = list.get(0);
			if(map.get("tree_type") != null){
				treeType = map.get("tree_type").toString();
			}
		}
		return treeType;
	}

	/**
	 * 根据id和tableName查询该类型在相关的业务表中是否有数据
	 * @author 王磊
	 * @Date 2018年9月5日 下午12:15:36
	 * @param id
	 * @param tableName
	 * @return
	 */
	@Override
	public int getCountByIdAndTreeType(String id, String tableName) {
		int dataNum = 0;
		String sql = "select count(t.id) as dataNum from "+tableName+" t where t.node_id=? and t.visible='1'";
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = jdbcTemplate.queryForList(sql,id);
		if(list.size() > 0){
			Map<String,Object> map = list.get(0);
			if(map.get("dataNum") != null){
				dataNum = Integer.parseInt(map.get("dataNum").toString());
			}
		}
		return dataNum;
	}
	

}
