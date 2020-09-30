package com.sinosoft.sinoep.modules.dagl.xtpz.categorymanage.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.sinosoft.sinoep.modules.dagl.bmgl.entity.DaglGeneralArchive;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.VirtualClass;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.services.CateDeptPersonRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.modules.dagl.bmgl.dao.VirtualClassDao;
import com.sinosoft.sinoep.modules.dagl.xtpz.categorymanage.entity.MenTree;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.dao.DaglCategoryTableDao;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DaglCategoryTable;

/**
 * 门类管理service实现类
 * @author 王磊
 * @Date 2018年12月28日 上午10:37:52
 */
@Service
public class CategoryManageServiceImpl implements CategoryManageService{
	private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
	private VirtualClassDao dao;
	@Autowired
	private DaglCategoryTableDao daglCategoryTableDao;
//	@Autowired
//	@Resource
//    private PlatformTransactionManager transactionManager;

	@Autowired
	private CateDeptPersonRelationService cateDeptPersonRelationService;
	
	private Connection connection;

	/**
	 *
	 *  开发人：颜振兴
	 *  时间 2018年12月28日
	 *  TODO 查门类树
	 *  @return
	 *  findMenLeiTree
	 */
	@Override
	public List<MenTree> findMenLeiTree() {

		List<MenTree> list = new ArrayList<MenTree>();
		String generalArchiveSql = "select t.* from dagl_general_archive t where t.VISIBLE ='"+DAGLCommonConstants.VISIBLE[1] +"'order by t.general_archive_code asc";

		List<DaglGeneralArchive> daglGeneralArchiveList = jdbcTemplate.query(generalArchiveSql, new BeanPropertyRowMapper<>(DaglGeneralArchive.class));

		for (DaglGeneralArchive daglGeneralArchive : daglGeneralArchiveList) {
			MenTree menTree = new MenTree();
			menTree.setName(daglGeneralArchive.getGeneralArchiveName());
			menTree.setpId("");
			menTree.setId(daglGeneralArchive.getGeneralArchiveCode());
			list.add(menTree);
		}

		List<DaglCategoryTable> doorList = daglCategoryTableDao.DoorList();

		for(DaglCategoryTable class1 : doorList) {
			MenTree menTree = new MenTree();
			menTree.setId(class1.getCategoryCode());
			menTree.setpId(class1.getGeneralArchiveCode());
			menTree.setName(class1.getCategoryName());
			menTree.setColumnName("menlei");
			list.add(menTree);
		}
		String sql ="select a.*,b.table_chn_name from (select * " + 
				"  from tables_relation t " + 
				" where t.s_table_name not like '%_dak' " + 
				" start with t.m_table_name in " + 
				"            (select c.category_code from dagl_category_table c) " + 
				"connect by prior t.s_table_name = t.m_table_name) a,table_description b where a.s_table_name = b.table_name";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql);
		for(Map<String,Object> map :queryForList) {
			MenTree menTree = new MenTree();
			menTree.setId(map.get("S_TABLE_NAME").toString());
			menTree.setName(map.get("TABLE_CHN_NAME").toString());
			menTree.setpId(map.get("M_TABLE_NAME").toString());
			menTree.setColumnName("menleicolumn");
			list.add(menTree);
		}
		return list;
	}

    /**
	 * 门类新增时，判断门类名称和门类代号是否可用
	 * @author 王磊
	 * @Date 2018年12月28日 下午4:09:55
	 * @param columnName
	 * @param columnValue
	 * @return 个数，0就是不存在
	 */
	@Override
	public int isCategoryExist(String columnName, String columnValue) {
		//防止sql注入 王磊20190426
		String countSql = "select count("+columnName+") from dagl_category_table where "+columnName+"=?";
		return jdbcTemplate.queryForObject(countSql, Integer.class,columnValue);
	}

	/**
	 *
	 *  开发人：颜振兴
	 *  时间 2018年12月28日
	 *  TODO 修改树的节点名称
	 *  @param menTree
	 *  @return
	 *  editTreeName
	 */
	@Override
	public int editTreeName(MenTree menTree) {
		//防止sql注入 王磊 20190426
		if(menTree!=null) {
			if (menTree.getpId()==null||menTree.getpId()==""||menTree.getpId().equals("null")) {
				String sql = "update dagl_category_table set category_name = ? where category_code = ?";
				int update = jdbcTemplate.update(sql,menTree.getName(),menTree.getId());
				return update;
			}else {
				String sql = " update table_description set table_chn_name = ? where table_name like ?";
				int update = jdbcTemplate.update(sql,menTree.getName(),menTree.getId()+"%");
				return update;
			}
		}else {
			return 0;
		}
	}

	/**
	 * 查询所有门类名称及门类代码
	 * @author 王磊
	 * @Date 2018年12月28日 下午5:00:32
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getCategoryOption() {
		String sql = "select category_name,category_code from dagl_category_table t where t.visible='"+DAGLCommonConstants.VISIBLE[1]+"' order by t.cre_time asc";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 根据门类模板创建新的门类
	 * @author 王磊
	 * @Date 2018年12月28日 下午5:03:54
	 * @param newCategoryName
	 * @param newCategoryCode
	 * @param templateCategoryCode
	 * @return
	 */
	@Override
	@Transactional
	public boolean addCategory(String newCategoryName, String newCategoryCode, String templateCategoryCode, String generalArchiveCode) {

		boolean isSuccess = false;
		//查询模板门类下有哪些表，根据这些表创建新门类的两套业务表
		//1.查询模板门类下的表
		String selTables = "select t.s_table_name from tables_relation t where t.s_table_name not like'%_dak' start with t.m_table_name='"+templateCategoryCode+"' connect by PRIOR t.s_table_name=t.m_table_name";
		List<String> tablesName = jdbcTemplate.queryForList(selTables, String.class);
		//2.根据这些表创建新的业务表
		String tableNames = "";
		for(int i=0;i<tablesName.size();i++){
			//逗号分隔业务表表名
			tableNames = tableNames+","+tablesName.get(i);
			//新增的两套表表名
			String newTableName = "dagl_"+newCategoryCode+"_0"+(i+1);
			String newTableName_dak = "dagl_"+newCategoryCode+"_0"+(i+1)+"_dak";
			//2.1先删除表
			try {
				String dropTable = "BEGIN EXECUTE IMMEDIATE 'DROP TABLE "+newTableName+"';EXCEPTION WHEN OTHERS THEN NULL;END;";
				String dropTable_dak = "BEGIN EXECUTE IMMEDIATE 'DROP TABLE "+newTableName_dak+"';EXCEPTION WHEN OTHERS THEN NULL;END;";
				jdbcTemplate.execute(dropTable);
				jdbcTemplate.execute(dropTable_dak);
			} catch (Exception e) {
				log.info("新建门类，删除的业务表不存在，删除异常！这里获取下异常，下边继续执行创建表。");
			}
			
			//2.2再创建表
			String createTable = "create table "+newTableName+" as select * from "+tablesName.get(i)+" where 1<>1";
			String createTable_dak = "create table "+newTableName_dak+" as select * from "+tablesName.get(i)+" where 1<>1";
			jdbcTemplate.execute(createTable);
			jdbcTemplate.execute(createTable_dak);
		}
		
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
        	connection.setAutoCommit(false);
			//3.新建门类表数据
			String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
			String insertCategory = "insert into dagl_category_table(id,category_name,category_code,cre_time,cre_user_name,visible,general_archive_code)"
					+"values('"+uuid+"','"+newCategoryName+"','"+newCategoryCode+"','"+DateUtil.COMMON_FULL.getDateText(new Date())+"','"+UserUtil.getCruUserName()+"','1','"+generalArchiveCode+"')";
			connection.prepareStatement(insertCategory).execute();
			log.info("新增门类表数据："+insertCategory);
			//4.新增两套表描述
			//4.1查询模板表描述
			tableNames = tableNames.substring(1, tableNames.length());
			String selTableDescription = "select * from table_description where table_name in("+CommonUtils.commomSplit(tableNames)+") order by DECODE(table_name,"+CommonUtils.commomSplit(tableNames)+")";
			log.info("查询表描述（有排序）"+selTableDescription);
			List<Map<String,Object>> tableDescriptionList = jdbcTemplate.queryForList(selTableDescription);
			StringBuffer keys = new StringBuffer();
			StringBuffer values = new StringBuffer();
			StringBuffer values_dak = new StringBuffer();
			//4.2拼接插入语句
			for(int i=0;i<tableDescriptionList.size();i++){
				Map<String,Object> map = tableDescriptionList.get(i);
				for (String key : map.keySet()) {
					keys.append(key+",");
					if(key.toLowerCase().equals("table_name")){
						values.append("'dagl_"+newCategoryCode+"_0"+(i+1)+ "',");
						values_dak.append("'dagl_"+newCategoryCode+"_0"+(i+1)+ "_dak',");
					}else{
						values.append("'"+map.get(key)+"',");
						values_dak.append("'"+map.get(key)+"',");
					}
				}
				String insertsql = "insert into table_description ( " + keys.deleteCharAt(keys.length()-1) + " )" + "values (" + values.deleteCharAt(values.length()-1) + ")";
				String insertsql_dak = "insert into table_description ( " + keys + " )" + "values (" + values_dak.deleteCharAt(values_dak.length()-1) + ")";
				connection.prepareStatement(insertsql).execute();
				connection.prepareStatement(insertsql_dak).execute();
				//清空字符串
				keys.delete(0, keys.length());
				values.delete(0, values.length());
				values_dak.delete(0, values_dak.length());
				log.info("表描述："+insertsql);
				log.info("表描述——dak："+insertsql_dak);
			}
			//5.新增两套表关联
			//5.1查询模板表关联数据
			String selRelation="select t.* from tables_relation t where t.s_table_name not like '%_dak'"
					+"start with t.m_table_name='"+templateCategoryCode+"' connect by prior t.s_table_name=t.m_table_name";
			List<Map<String,Object>> tableRelationList = jdbcTemplate.queryForList(selRelation);
			//5.2拼接插入语句
			StringBuffer relationKeys = new StringBuffer();
			StringBuffer relationValues = new StringBuffer();
			StringBuffer relationValues_dak = new StringBuffer();
			for(int i=0;i<tableRelationList.size();i++){
				Map<String,Object> map = tableRelationList.get(i);
				for (String key : map.keySet()) {
					relationKeys.append(key+",");
					//第一条数据为门类代码跟第一张表的关系
					if(i==0 && key.toLowerCase().equals("m_table_name")){
						relationValues.append("'"+newCategoryCode+"',");
						relationValues_dak.append("'"+newCategoryCode+"',");
					}else if(i!=0 && key.toLowerCase().equals("m_table_name")){
						relationValues.append("'dagl_"+newCategoryCode+"_0"+(i)+ "',");
						relationValues_dak.append("'dagl_"+newCategoryCode+"_0"+(i)+ "_dak',");
					}else if(key.toLowerCase().equals("s_table_name")){
						relationValues.append("'dagl_"+newCategoryCode+"_0"+(i+1)+ "',");
						relationValues_dak.append("'dagl_"+newCategoryCode+"_0"+(i+1)+ "_dak',");
					}else{
						String akey = map.get(key)==null?"":map.get(key).toString();
						relationValues.append("'"+akey+"',");
						relationValues_dak.append("'"+akey+"',");
					}
				}
				String insertsql = "insert into tables_relation ( " + relationKeys.deleteCharAt(relationKeys.length()-1) + " )" + "values (" + relationValues.deleteCharAt(relationValues.length()-1) + ")";
				String insertsql_dak = "insert into tables_relation ( " + relationKeys + " )" + "values (" + relationValues_dak.deleteCharAt(relationValues_dak.length()-1) + ")";
				connection.prepareStatement(insertsql).execute();
				connection.prepareStatement(insertsql_dak).execute();
				//清空字符串
				relationKeys.delete(0, relationKeys.length());
				relationValues.delete(0, relationValues.length());
				relationValues_dak.delete(0, relationValues_dak.length());
				log.info("表关联："+insertsql);
				log.info("表关联_dak："+insertsql_dak);
			}
			//6.新增两套表字段
			//6.1查询模板
			String selTableName = "select t.s_table_name from tables_relation t where t.s_table_name not like '%_dak'"
					+"start with t.m_table_name='"+templateCategoryCode+"' connect by prior t.s_table_name=t.m_table_name";
			List<String> tableNameList = jdbcTemplate.queryForList(selTableName, String.class);
			//6.2拼接插入语句
			StringBuffer structKeys = new StringBuffer();
			StringBuffer structValues = new StringBuffer();
			StringBuffer structValues_dak = new StringBuffer();
			//遍历模板下业务表
			for(int i=0;i<tableNameList.size();i++){
				String selTableStruct = "select * from table_struct_description t where t.table_name='"+tableNameList.get(i)+"'";
				List<Map<String,Object>> tableStructList = jdbcTemplate.queryForList(selTableStruct);
				//获取业务表字段属性并拼接插入语句
				for(Map<String,Object> map : tableStructList){
					for(String key : map.keySet()){
						structKeys.append(key+",");
						if(key.toLowerCase().equals("table_name")){
							structValues.append("'dagl_"+newCategoryCode+"_0"+(i+1)+ "',");
							structValues_dak.append("'dagl_"+newCategoryCode+"_0"+(i+1)+ "_dak',");
						}else{
							String akey = map.get(key)==null?"":map.get(key).toString();
							structValues.append("'"+akey+"',");
							structValues_dak.append("'"+akey+"',");
						}
					}
					String insertsql = "insert into table_struct_description ( " + structKeys.deleteCharAt(structKeys.length()-1) + " )" + "values (" + structValues.deleteCharAt(structValues.length()-1) + ")";
					String insertsql_dak = "insert into table_struct_description ( " + structKeys + " )" + "values (" + structValues_dak.deleteCharAt(structValues_dak.length()-1) + ")";
					connection.prepareStatement(insertsql).execute();
					connection.prepareStatement(insertsql_dak).execute();
					//清空字符串
					structKeys.delete(0, structKeys.length());
					structValues.delete(0, structValues.length());
					structValues_dak.delete(0, structValues_dak.length());
					log.info("表字段："+insertsql);
					log.info("表字段："+insertsql_dak);
				}
			}
			connection.commit();
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return isSuccess;
	}

	/**
	 *
	 *  开发人：颜振兴
	 *  时间 2018年12月28日
	 *  TODO 删除树的父节点
	 *  @param name
	 *  @return
	 *  deleteTreeName
	 */
	@Override
	public int deleteTreeName(MenTree menTree) {
		int result=0;
		if(menTree!=null) {
			if (menTree.getpId()!=null || !"".equals(menTree.getpId())|| "null".equals(menTree.getpId()) || !"menleicolumn".equals(menTree.getColumnName())) {
//		        定义事务隔离级别，传播行为
//		        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
//		        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
//		        //事务状态类，通过PlatformTransactionManager的getTransaction方法根据事务定义获取；获取事务状态后，Spring根据传播行为来决定如何开启事务
//		        TransactionStatus transactionStatus = transactionManager.getTransaction(def);
//		        transactionManager.setTransactionSynchronization(DataSourceTransactionManager.SYNCHRONIZATION_ALWAYS);
		        Connection connectiondelete = null;
		        try {
		        	connectiondelete = jdbcTemplate.getDataSource().getConnection();
		        	connectiondelete.setAutoCommit(false);
		        	//删除立卷单位，录入人，立卷单位管理员的对应关系
					cateDeptPersonRelationService.deleteAllRelation(menTree.getId());
		        	//删除父表的数据
					String sql = "delete from  dagl_category_table where category_code = '"+menTree.getId()+"'";
					int executeUpdate = connectiondelete.prepareStatement(sql).executeUpdate();
//					int[] fubiao = jdbcTemplate.batchUpdate(sql);
					if (executeUpdate>0) {
						log.info("门类表删除"+executeUpdate+"条数据");
					}
					String xunifenleisql="delete from dagl_virtual_class where category_code = '"+menTree.getId()+"'";
					int xunifenlei = connectiondelete.prepareStatement(xunifenleisql).executeUpdate();
					log.info("虚拟分类删除成功"+xunifenlei+"条数据");
					//int o = 1/0;
					//递归查询子表数据
					String sql3="select s_table_name from   tables_relation t  start with  t.m_table_name ='"+menTree.getId()+"' connect by prior t.s_table_name = t.m_table_name" ;
					List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql3);
					for(Map<String, Object> map : queryForList) {
						String deleteMiaoshu = "delete from table_description where table_name like '"+map.get("S_TABLE_NAME")+"%'";
						int[] deleteMiaoshu2 = jdbcTemplate.batchUpdate(deleteMiaoshu);
						if (deleteMiaoshu2.length>0) {
							log.info("表字段已删除："+deleteMiaoshu2[0]);
						}
						String deleteZiduan= "delete from table_struct_description where table_name = '"+map.get("S_TABLE_NAME")+"'";
						int[] deleteziduan = jdbcTemplate.batchUpdate(deleteZiduan);
						if (deleteziduan.length>0) {
							log.info("表字段已删除："+deleteziduan[0]);
						}
						String deletesql =" drop TABLE "+map.get("S_TABLE_NAME")+" ";
						int[] delete;
						try {
							 jdbcTemplate.execute(deletesql);
						} catch (DataAccessException e) {
							log.info("没事 这个不算错");
							e.printStackTrace();
						}
					}
					//
					//删除子表的数据
					if(queryForList!=null&&!queryForList.isEmpty()) {
						String  selectsql = "select s_table_name from   tables_relation t  start with  t.m_table_name ='"+menTree.getId()+"' connect by prior t.s_table_name = t.m_table_name";
						String sql2="delete from tables_relation where s_table_name in ("+selectsql+")";
						
						//int[] zibiao = jdbcTemplate.batchUpdate(sql2);
						int executeUpdate2 = connectiondelete.prepareStatement(sql2).executeUpdate();
						if (executeUpdate2>0) {
							log.info("子表删除"+executeUpdate2+"条数据");
						}else {
							log.info("子表删除错误");
					}
					}
					result=1;
					//int p = 1/0;
					//transactionManager.commit(transactionStatus);
					connectiondelete.commit();
		        } catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
					//transactionManager.rollback(transactionStatus);
					try {
						connectiondelete.rollback();
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}finally {
					try {
						connectiondelete.setAutoCommit(true);
						connectiondelete.close();
					} catch (SQLException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				
				return result;
			}else {
				result=2;
				return result;
			}
		}else {
			return result;
		}
	}

	/**
	 *
	 *  开发人：颜振兴
	 *  时间 2019年1月3日
	 *  TODO 查找门类下的所有字段
	 *  @param tName
	 *  @param pid
	 *  @return
	 *  findZiDuan
	 */
	@Override
	public List<Map<String, Object>> findZiDuan(String tName, String pid) {
		String s_name="";
		if(pid==null||pid.equals("")||pid.equals("null")) {
			String sql="select s_table_name from   tables_relation t  start with  t.m_table_name ='"+tName+"' connect by prior t.s_table_name = t.m_table_name" ;
			List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql);
			String name="";
			for(Map<String,Object> map:queryForList) {
				name+="'"+map.get("S_TABLE_NAME")+"',";
			}
			name=name.substring(0, name.length()-1);
			s_name=" and  table_name in ("+name+")";
		}else {
			s_name="  and  table_name ='"+tName+"'";
		}
		String sqlziduan="select * from table_struct_description where 1=1 "+s_name +" order by column_order asc";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sqlziduan);
		
		String zidiansql ="select t.MARK,t.name from sys_data_dictionary t where t.visible='"+DAGLCommonConstants.VISIBLE[1]+"' start with t.code='dagl' connect by prior t.id=t.pid";
		List<Map<String,Object>> zidianxiang = jdbcTemplate.queryForList(zidiansql);
		Map<String, String> map = new HashMap<>();
		for(Map<String,Object> map2:zidianxiang) {
			map.put(map2.get("MARK").toString(), map2.get("NAME").toString());
		}
		for(Map<String,Object> map3:queryForList) {
			map3.put("COLUMN_SELECT_NO", map.get(map3.get("COLUMN_SELECT_NO")));
		}
		return queryForList;
	}

	/**
	 *
	 *  开发人：颜振兴
	 *  时间 2019年1月7日
	 *  TODO 删除门类下的字段
	 *  @param tName
	 *  @param columnName
	 *  @return
	 *  deleteZiDuan
	 */
	@Override
	public int deleteZiDuan(String tName, String columnName) {
		int result = 0;
		Connection connectiondelete = null;
		try {
			connectiondelete = jdbcTemplate.getDataSource().getConnection();
			connectiondelete.setAutoCommit(false);
			String select = "select column_class from  TABLE_STRUCT_DESCRIPTION where table_name = '"+tName+"' and column_name = '"+columnName+"'";
			String queryForObject = jdbcTemplate.queryForObject(select, String.class);
			if(queryForObject.equals("2")) {
				return 2;
			}
			String sqldw = "alter table "+tName+" drop ("+columnName+")";
			connectiondelete.prepareStatement(sqldw).executeUpdate();
			String sqldak = "alter table "+tName+"_dak drop ("+columnName+")";
			connectiondelete.prepareStatement(sqldak).executeUpdate();
			String sql = "delete from TABLE_STRUCT_DESCRIPTION where table_name = '"+tName+"' and column_name = '"+columnName+"'";
			int delete = connectiondelete.prepareStatement(sql).executeUpdate();
			String sql2 = "delete from TABLE_STRUCT_DESCRIPTION where table_name = '"+tName+"_dak' and column_name = '"+columnName+"'";
			int delete2 = connectiondelete.prepareStatement(sql2).executeUpdate();
			result=1;
				connectiondelete.commit();
		} catch (Exception e) {
			try {
				connectiondelete.rollback();
				log.error(e.getMessage());
			} catch (SQLException e1) {
				log.error(e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				connectiondelete.setAutoCommit(true);
				connectiondelete.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 *
	 *  开发人：颜振兴
	 *  时间 2019年1月7日
	 *  TODO  修改门类下的字段
	 *  @param tName
	 *  @param columnName
	 *  @param column
	 *  @return
	 *  updateZiDuan
	 */
	@Override
	public int updateZiDuan(String tName, String updateColumnName, String columnName, String column,String code) {
		if ("COLUMN_CHN_NAME".equals(column)) {
			String danghaoxiangsql = "select count(1) from dagl_party_num_rule t right join  dagl_category_table c on t.category_id = c.id where c.category_code ='"+code+"' and t.rule_field = '"+columnName+"'";
			Integer count = jdbcTemplate.queryForObject(danghaoxiangsql, Integer.class);
			if(count!=null&&count>0) {
				String updateName = "update  dagl_party_num_rule set rule_name = '"+updateColumnName+"' where category_id = (select id from dagl_category_table where category_code = '"+code+"') and rule_field = '"+columnName+"'";
				int update = jdbcTemplate.update(updateName);
				log.info(update+"~~~~~~~~~~~~~~~~~~~~~~~档号规则修改");
			}
		}
		//如果当前调整列表项且将字段设置为：不是列表项，那么需要设置列表顺序为空
		String exTiaoJian = "";
		if("COLUMN_LIST_ISSHOW".equals(column)){
			exTiaoJian=",COLUMN_LIST_ORDER='' ";
		}
		String sql = "update  TABLE_STRUCT_DESCRIPTION set "+column+" = '"+updateColumnName+"'"+exTiaoJian+" where  column_name = '"+columnName+"' and  table_name like '%"+tName+"%'";
		int update = jdbcTemplate.update(sql);
		return update;
	}

	/**
	 *
	 *  开发人：颜振兴
	 *  时间 2019年1月7日
	 *  TODO  添加门类下的字段
	 *  @param tName
	 *  @param StrJson
	 *  @return
	 *  addZiDuan
	 */
	@Override
	@Transactional
	public int addZiDuan(String chnName,String tName, String StrJson) {
		int result= 0;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			Map<String, String> parseObject = JSONObject.parseObject(StrJson, new TypeReference<Map<String, String>>() {});
			StringBuilder keys = new StringBuilder("(");
			StringBuilder values = new StringBuilder("(");
			String column="";//字段名
			String width="";//字段长度
			String isNull="";//是否为空
			String dataType="";//字段类型
			String dakBiaoName = tName+"_dak";//档案库名
			for(Entry<String, String> entry : parseObject.entrySet()) {
//				if(entry.getKey().equals("TABLE_NAME")) {
//					dakBiaoName=entry.getValue();
//				}
				if(entry.getKey().equals("COLUMN_NAME")) {
					column=entry.getValue();
				}
				if(entry.getKey().equals("COLUMN_WIDTH")) {
					width=entry.getValue();
				}
				if(entry.getKey().equals("COLUMN_CAN_NULL")) {
					isNull=entry.getValue();
				}
				if(entry.getKey().equals("COLUMN_TYPE")) {
					if(entry.getValue().equals("2")) {
						dataType="number";
					}else {
						dataType="varchar2";
					}
					
					
				}
				keys.append(""+entry.getKey()+",");
				values.append("'"+entry.getValue()+"',");
			}
			Integer count = jdbcTemplate.queryForObject("select max(column_order) from TABLE_STRUCT_DESCRIPTION where table_name='"+tName+"' ", Integer.class);
			String style = "{\"key\":\""+column+"\",\"value\":\"col-xs-6\"}";
			String keyss = keys.substring(0, keys.length()-1)+",TABLE_NAME,TABLE_CHN_NAME,COLUMN_CLASS,COLUMN_ORDER,COLUMN_STYLE)";
			String valuess = values.substring(0, values.length()-1)+",'"+tName+"','"+chnName+"','4','"+(count+1)+"','"+style+"')";
			String valuesdak = values.substring(0, values.length()-1)+",'"+dakBiaoName+"','"+chnName+"','4','"+(count+1)+"','"+style+"')";
			String sqldw = "alter table "+tName+" add ("+column+" "+(dataType.equals("number")?"NUMBER":"VARCHAR2("+width+" CHAR)")+")"; //"+(isNull.equals("T")?"":"not null ")+" 是否为空
			connection.prepareStatement(sqldw).execute();
			String sqldak = "alter table "+tName+"_dak add ("+column+" "+(dataType.equals("number")?"NUMBER":"VARCHAR2("+width+" CHAR)")+")"; //"+(isNull.equals("T")?"":"not null ")+" 是否为空
			connection.prepareStatement(sqldak).execute();
			String sql  = "INSERT INTO TABLE_STRUCT_DESCRIPTION "+keyss+" VALUES "+valuess+"";
			int add = connection.prepareStatement(sql).executeUpdate();
			String sqldatadak  = "INSERT INTO TABLE_STRUCT_DESCRIPTION "+keyss+" VALUES "+valuesdak+"";
			int add1 = connection.prepareStatement(sqldatadak).executeUpdate();
			//int a=1/0;
			result=1;
			connection.commit();
		} catch (Exception e) {
			log.error(e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * 创建人员树
	 *
	 * @param codeId
	 * @return
	 */
	@Override
	public JSONObject personnelTree(String codeId) {
		String code_Id = "";
		if(!StringUtils.isEmpty(codeId)) {
			code_Id = " and cate_id = '"+codeId+"' ";
		}
		//立卷单位下人员去重 王磊 20190417
		String sql = "select distinct lrr_name,lrr_id,ljdw_mark from dagl_cate_dept_person_relation where 1=1 "+code_Id;
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql);
		String sqlDW = "select t.ljdw_mark,t.ljdw_name from dagl_cate_dept_person_relation t where 1 = 1 "+code_Id+" group by t.LJDW_MARK,t.LJDW_Name";
		List<Map<String,Object>> queryForList1 = jdbcTemplate.queryForList(sqlDW);
		
		JSONObject object = new JSONObject();
		object.put("flag", "1");
		//所有人员
		List<JSONObject> list = new ArrayList<>();
		for(Map<String,Object> map:queryForList) {
			JSONObject data = new JSONObject();
			data.put("id", map.get("LRR_ID"));
			data.put("pId", map.get("LJDW_MARK"));
			data.put("name", map.get("LRR_NAME"));
			data.put("columnName", "LRR_ID");
			data.put("columnValue", map.get("LRR_ID"));
			list.add(data);
		}
		//父节点 立卷单位
		for(Map<String,Object> map:queryForList1) {
			JSONObject data = new JSONObject();
			data.put("id", map.get("LJDW_MARK"));
			data.put("pId", map.get(""));
			data.put("name", map.get("LJDW_NAME"));
			data.put("columnName", "LJDW_MARK");
			data.put("columnValue",  map.get("LJDW_MARK"));
			list.add(data);
		}
		JSONObject jsonObject = new JSONObject();
		object.put("flag", "1");
		object.put("data", jsonObject);
		jsonObject.put("rows", list);
		jsonObject.put("total", list.size());
		
		return object;
	}

	/**
	 * 通过人员树查询门类树
	 * @param codeId
	 * @return
	 */
	@Override
	public JSONObject personnelTreeToMenTree(MenTree menTree) {
		String sql  = "select t.cate_id from dagl_cate_dept_person_relation t where t."+menTree.getColumnName()+" = '"+menTree.getId()+"' group by  t.cate_id";
		String sql2 = "select * from dagl_category_table c,("+sql+") s where c.CATEGORY_CODE=s.cate_id order by c.CRE_TIME asc";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql2);
		List<JSONObject> findTreeByMenCode = findTreeByMenCode(queryForList);
		JSONObject object = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("rows", findTreeByMenCode);
		data.put("total", findTreeByMenCode.size());
		object.put("flag", "1");
		object.put("data", data);
		return object;
	}

	/**
	 * 根据门类代号查虚拟分类
	 * @param code
	 * @return
	 */
	public List<JSONObject> findTreeByMenCode(List<Map<String,Object>> maps) {
		List<JSONObject> list = new ArrayList<>();
		String categoryCode = " and(";
		for(int i =0;i<maps.size();i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", maps.get(i).get("CATEGORY_CODE"));
			jsonObject.put("pId",maps.get(i).get("GENERAL_ARCHIVE_CODE"));
			jsonObject.put("name", maps.get(i).get("CATEGORY_NAME"));
			jsonObject.put("categoryCode", maps.get(i).get("CATEGORY_CODE"));
			jsonObject.put("columnName", "");
			list.add(jsonObject);
			if(i==maps.size()-1) {
				categoryCode+="category_code ='"+maps.get(i).get("CATEGORY_CODE")+"')";
				break;
			}else {
				categoryCode+="category_code ='"+maps.get(i).get("CATEGORY_CODE")+"' or ";
			}
		}
		String sql = "select * from dagl_virtual_class where 1=1 "+categoryCode+" and isadmin = '1'";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql);
		JSONObject object = new JSONObject();
		
		for(Map<String,Object> map:queryForList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", map.get("ID"));
			jsonObject.put("pId", map.get("PID"));
			jsonObject.put("name", map.get("NAME"));
			jsonObject.put("columnName", map.get("COLUMN_NAME"));
			jsonObject.put("columnValue", map.get("COLUMN_VALUE"));
			jsonObject.put("categoryCode", map.get("CATEGORY_CODE"));
			list.add(jsonObject);
		}

		String generalArchiveSql = "select t.* from dagl_general_archive t order by t.general_archive_code asc";
		List<Map<String,Object>> queryForGeneralArchiveList = jdbcTemplate.queryForList(generalArchiveSql);

		for(Map<String,Object> map:queryForGeneralArchiveList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", map.get("GENERAL_ARCHIVE_CODE"));
			jsonObject.put("pId", map.get(""));
			jsonObject.put("name", map.get("GENERAL_ARCHIVE_NAME"));
			list.add(jsonObject);
		}

		return list;
		
	}

	/**
	 * 判断新增字段是否可以删除
	 * @param tName
	 * @param columnName
	 * @param code
	 * @return
	 */
	@Override
	public Map<String, String> isKeYiDelete(String tName, String columnName, String code) {
		Map<String, String> map = new HashMap<>();
		String duizhao = "DZOK";
		String danghaoxiang = "DHOK";
		String duizhaosql ="select dcr.contrast_name from dagl_contrasting_relations dcr,dagl_data_contrast ddc where ddc.contrasting_id=dcr.id " + 
				"and dcr.target_name='"+tName+"' and ddc.target_column='"+columnName+"'  and dcr.visible='"+DAGLCommonConstants.VISIBLE[1]+"'";
		 List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(duizhaosql);
		if(!queryForList.isEmpty()&&queryForList.size()>0) {
			duizhao="DZNO";
		}
		String danghaoxiangsql = "select t.rule_name from dagl_party_num_rule t right join  dagl_category_table c on t.category_id = c.id where c.category_code ='"+code+"' and t.rule_field = '"+columnName+"'";
		List<Map<String, Object>> queryForList1 = jdbcTemplate.queryForList(danghaoxiangsql);
		if(!queryForList1.isEmpty()&&queryForList1.size()>=1) {
			danghaoxiang="DZNO";
		}
		if(duizhao.equals("DZOK")&&danghaoxiang.equals("DHOK")) {
			map.put("result", "0");
			return map;
		}
		if(duizhao.equals("DZOK")) {
			map.put("HH","不能删除");
			map.put("SS",queryForList1.get(0).get("RULE_NAME").toString());
			map.put("result", "1");
			return map;
		}
		if(danghaoxiang.equals("DHOK")) {
			map.put("HH", queryForList.get(0).get("CONTRAST_NAME").toString());
			map.put("SS", "");
			map.put("result", "2");
			return map;
		}
		if(duizhao.equals("DZNO")&&danghaoxiang.equals("DHNO")) {
			map.put("result", "3");
			map.put("SS", queryForList1.get(0).get("RULE_NAME").toString());
			map.put("HH", queryForList.get(0).get("CONTRAST_NAME").toString());
			return map;
		}
		return map;
	}

	/**
	 * 是否为档号规则
	 * 颜振兴
	 * @param tName
	 * @param columnNames
	 * @return
	 */
	@Override
	public JSONObject isDanghaoGuizhe(String code, String[] columnNames) {
		String columnName = "(";
		for(String column:columnNames) {
			columnName+="'"+column+"',";
		}
		columnName=columnName.substring(0, columnName.length()-1);
		columnName+=") ";
		String danghaoxiangsql = "select t.rule_name from dagl_party_num_rule t right join  dagl_category_table c on t.category_id = c.id where c.category_code ='"+code+"' and t.rule_field in "+columnName+"";
		List<String> queryForList = jdbcTemplate.queryForList(danghaoxiangsql, String.class);
		JSONObject object = new JSONObject();
		if(queryForList.isEmpty()||queryForList.size()<=0) {
			object.put("flag", "0");
			return object;
		}
		String msgs = "";
		for(String msg:queryForList) {
			msgs+="【"+msg+"】";
		}
		msgs = "所选字段中存在字段"+msgs+"为档号规则，无法删除，请取消勾选该字段重新删除！";
		object.put("msg", msgs);
		object.put("flag", "1");
		return object;
	}

	/**
	 * 根据门类查询立卷单位
	 * @param code
	 * @return
	 */
	@Override
	public Map<String,String> LJDWmark(String code,String userId) {
		String lrrId="";
		if (!StringUtils.isEmpty(userId)) {
			lrrId = " and lrr_id ='"+userId+"'";
		}
		String sql = "select ljdw_name,ljdw_mark from  dagl_cate_dept_person_relation where cate_id = '"+code+"' "+lrrId+" group by ljdw_name,ljdw_mark";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql);
		Map<String, String> map = new HashMap<>();
		for(Map<String,Object> map2 :queryForList) {
			map.put(map2.get("LJDW_MARK").toString(), map2.get("LJDW_NAME").toString());
		}
		
		return map;
	}

	/**
	 * 根据门类和立卷单位查找立卷单位下的所有人员
	 * @param code
	 * @param chushi
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryLjUser(String code, String chushi) {
		String sql = "select LRR_NAME,LRR_ID from  dagl_cate_dept_person_relation where cate_id = '"+code+"' and ljdw_mark ='"+chushi+"' group by LRR_NAME,LRR_ID";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql);
		return queryForList;
	}

	/**
	 * 批量删除
	 * @param jsonStr
	 * @return
	 */
	@Override
	public JSONObject deleteAllZiDuan(String jsonStr) {
		JSONObject jsonObject = new JSONObject();
		List<Map<String,String>> parseArray = (List<Map<String,String>>)JSONObject.parse(jsonStr);
		int j =0;
		for(Map<String, String> map:parseArray) {
			int deleteZiDuan = deleteZiDuan(map.get("tableName"),map.get("columnName"));
			j+=deleteZiDuan;
		}
		jsonObject.put("flag", j);
		return jsonObject;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 删除门类时，判断业务表中是否有数据
	 * @Date 2019/4/8 14:03
	 * @Param [tableNames]
	 * @return com.alibaba.fastjson.JSONObject
	 **/
	@Override
	public JSONObject canDelete(String[] tableCodes){
		int tableNames = 0;
		for(int i= 0;i<tableCodes.length;i++){
			String sql = "select * from "+tableCodes[i];
			List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql);
			tableNames += queryForList.size();

			String sql_dak = "select * from "+tableCodes[i]+"_dak";
			List<Map<String,Object>> queryForList_dak = jdbcTemplate.queryForList(sql_dak);
			tableNames += queryForList_dak.size();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("num", tableNames);
		return jsonObject;
	}

	/**
	 * 
	 * TODO 获得Q2的授权立卷单位，Q2变更用，正式环境中Q2的门类code为：q2
	 * @author 王磊
	 * @Date 2019年4月23日 下午8:21:30
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getLiJuanDanWeiForQtow() {
		List<Map<String, Object>> shouQuanUnit = null;
		String sel = "select distinct t.ljdw_name,t.ljdw_mark from dagl_cate_dept_person_relation t where t.cate_id='q2' order by t.ljdw_name asc";
		shouQuanUnit = jdbcTemplate.queryForList(sel);
		//如果管理员没有把Q2授权给任何单位，那么就取所有的立卷单位
		if(null == shouQuanUnit || shouQuanUnit.size() == 0){
			sel = "select distinct t.ljdw_name,t.ljdw_mark from dagl_cate_dept_person_relation t  by t.ljdw_name asc";
			shouQuanUnit = jdbcTemplate.queryForList(sel);
		}
		return shouQuanUnit;
	}

}
