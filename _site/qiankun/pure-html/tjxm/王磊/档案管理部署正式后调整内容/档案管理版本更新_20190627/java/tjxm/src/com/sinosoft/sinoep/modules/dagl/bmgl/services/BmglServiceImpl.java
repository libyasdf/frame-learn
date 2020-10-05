package com.sinosoft.sinoep.modules.dagl.bmgl.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fr.web.core.A.DA;
import com.ibm.icu.text.NumberFormat;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.ZeBraClient;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.DaglGeneralArchive;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.DangAnEntity;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.services.DaglBorrowService;
import com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.services.ShoppingTrolleyService;
import com.sinosoft.sinoep.modules.dagl.wdgl.services.DaglReceiveFileService;
import com.sinosoft.sinoep.modules.dagl.wdgl.services.DaglSendFileService;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.PartyNumRule;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.services.PreferencesService;
import com.sinosoft.sinoep.modules.system.config.dictionary.dao.DataDictionaryDao;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fr.report.core.A.n;
import com.ibm.icu.text.SimpleDateFormat;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.bmgl.dao.VirtualClassDao;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.MultiLevelQuery;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.VirtualClass;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.dao.DaglCategoryTableDao;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DaglCategoryTable;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Service
public class BmglServiceImpl implements BmglService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private VirtualClassDao dao;
	@Autowired
	private DaglCategoryTableDao daglCategoryTableDao;
	
	@Autowired
	private PreferencesService preferencesService;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private DataDictionaryService dataDictionaryService;

	@Autowired
	private ShoppingTrolleyService shoppingTrolleyService;

	@Autowired
	private DaglBorrowService daglBorrowService;
	
	@Autowired
	private DataDictionaryDao dataDictionaryDao;
	
	/**
	 * 收文的service
	 */
	@Autowired
	private DaglReceiveFileService receiveService;
	
	/**
	 * 发文的service
	 */
	@Autowired
	private DaglSendFileService sendService;

	private static Connection con = null;


    /**
     *
     * @param tName
     * @return
     * @author 颜振兴
     * List<Map<String,Object>>
     * TODO 查询新增动态列表
     */
	@Override
	public List<Map<String, Object>> dynamicFind(String tName) {
		List<Object> para = new ArrayList<>();
		String table_name = "";
		if (StringUtils.isNotEmpty(tName)) {
			table_name = " and t.table_name = ? ";
			para.add(tName);
		}
		String sql = "select t.* from table_struct_description t where 1=1 " + table_name;
		List<Map<String, Object>> query = jdbcTemplate.queryForList(sql,para.toArray());
		return query;
	}

    /**
     *
     * @param tName
     * @return
     * @author 颜振兴
     * List<Map<String,Object>>
     * TODO 动态查询标签
     */
	@Override
	public List<Map<String, Object>> findLabel(String tName) {

		//String sql = "select d.* from ( select * from tables_relation t   where t.s_table_name not like '%_dak'  start with t.m_table_name ='"+tName+"' connect by prior t.s_table_name = t.m_table_name) s,table_description d where d.table_name=s.s_table_name";
		String sql = "select t.s_table_name table_name, " + 
				"       (select d.table_chn_name " + 
				"          from table_description d " + 
				"         where t.s_table_name = d.table_name) table_chn_name " + 
				"  from tables_relation t " + 
				" where t.s_table_name not like '%_dak' " + 
				" start with t.m_table_name = ? " +
				" connect by prior t.s_table_name = t.m_table_name " + 
				" ";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql,tName);
		return queryForList;
	}

    /**
     *
     * @param jsonStr
     * @param tName
     * @return
     * @author 颜振兴
     * int
     * TODO 动态新增
     */
	@Override
	public JSONObject dynamicAdd(String jsonStr, String tName, String fids, String all, String ranking) {
		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		//这个数是表单中数量字段的数据。
		String pageNumData = "0";
		//这里是新增，上级的piece加1
		String pieceNumData = "1";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}
		String[] fid = fids.split(",");
		JSONObject object = new JSONObject();
		String uuid = UUID.randomUUID().toString();
		object.put("uuid", uuid);
		jsonStr = jsonStr.replaceAll("“","\"");
		jsonStr = jsonStr.replaceAll("”","\"");
		Map<String, String> parseObject = com.alibaba.fastjson.JSONObject.parseObject(jsonStr,
				new TypeReference<Map<String, String>>() {
				});
		StringBuffer key = new StringBuffer();
		StringBuffer value = new StringBuffer();
		List<Object> para = new ArrayList<>();

		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);
		//立卷单位的code存到了cre_chushi_id中 ，但是表描述中是没有这一项的，所以特殊处理
		colName.add("cre_chushi_id");
		for (Entry<String, String> entry : parseObject.entrySet()) {
			for(int i=0;i<colName.size();i++){
				if(colName.get(i).equals(entry.getKey())){
					key.append(""+colName.get(i)+",");
					value.append("? ,");
					para.add(entry.getValue());
				}
				//获取数量
				if("page_num".equals(entry.getKey())){
					pageNumData = "".equals(entry.getValue())?"0":entry.getValue();
				}
			}

		}
		//添加创建人 部门、处室、姓名信息 王磊 2019-03-14
		key.append("cre_user_id,cre_time,CRE_USER_NAME,CRE_DEPT_ID,CRE_DEPT_NAME,CRE_JU_ID,CRE_JU_NAME,cre_chushi_name,visible,");
		value.append("'"+UserUtil.getCruUserId()+"',"+"'"+DateUtil.COMMON_FULL.getDateText(new Date())+"',"+"'"+UserUtil.getCruUserName()+"',"+"'"+UserUtil.getCruDeptId()+"',"+"'"+UserUtil.getCruDeptName()+"',"+"'"+UserUtil.getCruJuId()+"',"+"'"+UserUtil.getCruJuName()+"',"+"'"+UserUtil.getCruChushiName()+"','"+DAGLCommonConstants.VISIBLE[1]+"',");
		key.append("recid");
		value.append("'"+uuid+"'");
		//判断表中是否含有组卷标识的字段
        String queryArchiveFlagColSql ="SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = 'ARCHIVE_FLAG'";
		int count = jdbcTemplate.queryForObject(queryArchiveFlagColSql,Integer.class,tableName.toUpperCase());

        //组卷标识不是表单项,并且表中不含有组卷标识字段
		if(key.indexOf("archive_flag")==-1 && count > 0){
			if(fid.length == 1 && !"".equals(fids)){
			    //含有父id，并且只有一个父id时，为关联新增，设置为已组卷
				key.append(",archive_flag");
				value.append(",'"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'");
			}else if("1".equals(ranking)){
				//第一层，都设置为已组卷
				key.append(",archive_flag");
				value.append(",'"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'");
			}else{
				key.append(",archive_flag");
				value.append(",'"+DAGLCommonConstants.ARCHIVE_FLAG[1]+"'");
			}
		}

        //档案实体状态不是表单项
        if(key.indexOf("archive_entity_status")==-1){
            if(tableName.indexOf("_dak") == -1){
                //不是归档库新增的数据为待提交
                key.append(",archive_entity_status");
                value.append(",'"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[0]+"'");
            }else{
                //归档库新增的数据为待提交
                key.append(",archive_entity_status");
                value.append(",'"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"'");
            }
        }

		String keys =key.toString();
		String values = value.toString();
		//String keys = key.substring(0,key.length() - 1);
		//String values = value.substring(0,value.length() - 1);
		String sql = "insert into "+tableName+" ( " + keys + " )" + "values (" + values + ")";
		log.info(sql);
		int update = jdbcTemplate.update(sql,para.toArray());
		object.put("flag", update);
		//修改父级的数据
		if(fid.length == 1 && !"".equals(fids)){
			//勾选，新增
			int num = updatePNum(fids,uuid,tableName,all, ranking,pieceNumData,pageNumData,"add","");
		}
		return object;
	}

    /**
     *
     * @param jsonStr
     * @param tName
     * @return
     * @author 颜振兴
     * int
     * TODO  动态修改
     */
	@Override
	public int dynamicUpdate(String jsonStr, String tName,String key,String value, String all, String ranking) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		String oldPageNumAddOrDel = "0";
		String newPageNumAddOrDel = "0";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//将当前表为父级查询和子级的关联字段
		Map<String, Object> findDAGLByTableNameF = findDAGLByTableNameF(tableName);
		String col_nameValue="";
		String col_name = "";
		//将当前表为父级查是否存在子级
		if (findDAGLByTableNameF!=null&&!findDAGLByTableNameF.isEmpty()) {
			//存在子级 将关联字段付给col_name
			col_name = (String) findDAGLByTableNameF.get("S_COL_NAME");
		}
		//根据传来的id和表名查一条数据
		Map<String, Object> map = findById(tableName, value).get(0);
		if(all.equals(ranking) && Integer.parseInt(all)>1){
			oldPageNumAddOrDel = map.get("page_num")==null?"0":map.get("page_num").toString();
		}
		//获取更新之前的页数
        jsonStr = jsonStr.replaceAll("“","\"");
        jsonStr = jsonStr.replaceAll("”","\"");
		Map<String, String> parseObject = com.alibaba.fastjson.JSONObject.parseObject(jsonStr,
				new TypeReference<Map<String, String>>() {
				});
		StringBuffer set = new StringBuffer();
		List<Object> para = new ArrayList<>();
		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);
		//便利修改的数据统一进行修改
		for (Entry<String, String> entry : parseObject.entrySet()) {

			for(int i=0;i<colName.size();i++){
				if(colName.get(i).equals(entry.getKey())){
					set.append(colName.get(i) + "= ?," );
					para.add(entry.getValue());
					if(col_name.equalsIgnoreCase(colName.get(i))) {
						col_nameValue=entry.getValue();
					}
					if("page_num".equals(colName.get(i))&& all.equals(ranking) && Integer.parseInt(all)>1){
						newPageNumAddOrDel = "".equals(entry.getValue())?"0":entry.getValue();
					}
				}
			}
		}
		String sets = set.substring(0, set.length()-1);
		para.add(value);
		String sql = "update "+tableName+" set "+sets+" where "+key+" = ?";
		int update = jdbcTemplate.update(sql,para.toArray());
		//计算出页数是新增还是删除
		String pageNumAddOrDel = (Integer.parseInt(newPageNumAddOrDel)-Integer.parseInt(oldPageNumAddOrDel))+"";
		if(!("0".equals(pageNumAddOrDel)) && all.equals(ranking) && Integer.parseInt(all)>1){
			int num = updatePNum("",value,tableName,all, ranking,"","","update",pageNumAddOrDel);
		}
		//判断是否有子级，如果没有退出
		if (col_name.equals("")) {
			return update;
		}
		//判断传来的和查出来的一样不一样 防止出现错误
		if (!map.get(col_name).equals(col_nameValue)) {
			//查询是否有子级
			Map<String, Object> TableName = findDAGLByTableNameF(tableName);
			if (!TableName.isEmpty()) {
				//以当前查出的子级作为父级再次查询看是否有子级
				Map<String, Object> findDAGLByTableNameF2 = findDAGLByTableNameF((String)TableName.get("S_TABLE_NAME"));//以查询出来的表为父级查
				if (findDAGLByTableNameF2==null||findDAGLByTableNameF2.isEmpty()) {//判断是否有子级
					//查询修改之前父级的档号项与子级档号项相同的数据
					List<Map<String,Object>> findChildren = findChildren((String)TableName.get("S_TABLE_NAME"), (String)TableName.get("S_COL_NAME"), (String)map.get(TableName.get("S_COL_NAME")),"",String.valueOf(map.get("CRE_USER_ID")));//查没改档案号之前的子级
					//如果查到，进行修改
					if (findChildren!=null&&!findChildren.isEmpty()) {
						for(Map<String, Object> maps:findChildren) {
							String newValue=col_nameValue+maps.get("ARCHIVE_NO").toString().substring(col_nameValue.length());//新的文件档案号
							String updateSql = "update "+(String)TableName.get("S_TABLE_NAME")+" set "+(String)TableName.get("S_COL_NAME")+" = '" +col_nameValue + "',archive_no ='"+newValue+"' where recid = '"+(String)maps.get("recid")+"'";
							int update2 = jdbcTemplate.update(updateSql);
							log.info("修改完成1是0否："+update2);
						}
					}
				}else {
					//此时标识还有子级
					List<Map<String,Object>> findChildren = findChildren((String)TableName.get("S_TABLE_NAME"), (String)TableName.get("S_COL_NAME"), (String)map.get(TableName.get("S_COL_NAME")),"",String.valueOf(map.get("CRE_USER_ID")));
					if (findChildren!=null&&!findChildren.isEmpty()) {
						for(Map<String, Object> maps:findChildren) {
							String newValue=col_nameValue+maps.get(findDAGLByTableNameF2.get("S_COL_NAME")).toString().substring(col_nameValue.length());//新的文件档案号
							String updateSql = "update "+(String)TableName.get("S_TABLE_NAME")+" set "+(String)TableName.get("S_COL_NAME")+" = '" +col_nameValue + "',"+findDAGLByTableNameF2.get("S_COL_NAME")+" ='"+newValue+"' where recid = '"+(String)maps.get("recid")+"'";
							int update2 = jdbcTemplate.update(updateSql);
							log.info("修改完成1是0否："+update2);
							//查询子表数据时添加与父记录立卷单位相同的条件，防止找到其他数据的子表数据 wl 2019-01-11
							List<Map<String,Object>> findChildren2 = findChildren((String)findDAGLByTableNameF2.get("S_TABLE_NAME"), (String)findDAGLByTableNameF2.get("S_COL_NAME"), (String)maps.get(findDAGLByTableNameF2.get("S_COL_NAME")),"",String.valueOf(maps.get("CRE_CHUSHI_ID")));//子表下的子表
							if (findChildren2!=null&&!findChildren2.isEmpty()) {
								for(Map<String, Object> maps2:findChildren2) {
									String newValue2=col_nameValue+maps2.get("ARCHIVE_NO").toString().substring(col_nameValue.length());//新的文件档案号
									String updateSql2 = "update "+(String)findDAGLByTableNameF2.get("S_TABLE_NAME")+" set "+(String)findDAGLByTableNameF2.get("S_COL_NAME")+" = '" +newValue + "',archive_no ='"+newValue2+"' where recid = '"+(String)maps2.get("recid")+"'";
									int update3 = jdbcTemplate.update(updateSql2);
									log.info("修改完成1是0否："+update3);
								}
							}
						}
					}
				}

			}
		}

		return update;

	}

    /**
     *
     * @param tName
     * @return
     * @author 颜振兴
     * List<Map<String,Object>>
     * TODO 动态列表
     * @param ljdanweiAndRenyuan
     */
	@Override
	public List<Map<String, Object>> dynamicList(String tName,PageImpl pageImpl,String jsonStr,String conditions,String fids,String parameters, String complexParam,String danweihao,String danweiku,Integer all,String ljdanweiAndRenyuan) {
		String sortName = "";//排序名字
		String sortOrder = "";//排序方式
		String wherefid = "";// 父级ids
		String whereAnd="";//过滤树的条件
		String easyQuery="";//简单查询的条件
        String complexQuery = "";//组合查询条件
        String danwei="";//单位过滤条件
        String renyuanLjDw = "";//立卷单位人员树过滤条件
		String user=" and cre_user_id='"+UserUtil.getCruUserId()+"'";
		String cruUserRoleNo = UserUtil.getCruUserRoleNo();
		String archiveEntityStatus = "";
		//王富康拼接sql让杜建松导出和报表打印使用
		// 父级ids
		String wherefid_bak = "";
		//过滤树的条件
		String whereAnd_bak="";
		//简单查询的条件
		String easyQuery_bak="";
		//组合查询条件
		String complexQuery_bak = "";
		//单位过滤条件
		String danwei_bak ="";
		//立卷单位人员树过滤条件
		String renyuanLjDw_bak = "";
		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//ARCHIVE_ENTITY_STATUS
		//如果danweiku不为空 则说明为预立库（按门类/按立卷单位）、归档库
		//如果danweiku为dak 则说明为单位归档库
		if (StringUtils.isNotEmpty(danweiku)) {
			user="";
			if("dak".equals(danweiku)) {
				String usersql = "select t.ljdw_mark from dagl_cate_dept_person_relation t where t.ljdw_admin_id like '%"+UserUtil.getCruUserId()+"%'  group by t.ljdw_mark";
				List<String> queryForList = jdbcTemplate.queryForList(usersql, String.class);
				String chushiId = "";
				for(int j =0;j<queryForList.size();j++){
					chushiId += "'"+queryForList.get(j)+"',";
				}
				if(!"".equals(chushiId)){
					chushiId = chushiId.substring(0,chushiId.length()-1);
				}
				user = " and cre_chushi_id in ("+chushiId+")";
			}
		}else{
			//单位预立库不显示已归档的数据 王磊 2019-03-14,调整状态从常量类 王磊20190409
			archiveEntityStatus = " and (ARCHIVE_ENTITY_STATUS !='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"' or ARCHIVE_ENTITY_STATUS is null) ";
		}

		List<Object> para = new ArrayList<>();
		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);

		//简单查询
		if (StringUtils.isNotEmpty(parameters)) {
			Map<String, String> where = com.alibaba.fastjson.JSONObject.parseObject(parameters,
					new TypeReference<Map<String, String>>() {
					});
			for (Entry<String, String> entry : where.entrySet()) {
				for(int i=0;i<colName.size();i++){
					if(colName.get(i).equals(entry.getKey())){
						easyQuery+=" and "+colName.get(i)+" like ? ";
						easyQuery_bak +=" and "+colName.get(i)+" like '%"+entry.getValue()+"%' ";
						para.add("%"+entry.getValue()+"%");
					}
				}
			}
		}
		Map<String, String> parseObject = com.alibaba.fastjson.JSONObject.parseObject(conditions,
				new TypeReference<Map<String, String>>() {
				});
		//树的过滤条件
		for(Entry<String, String> i:parseObject.entrySet()) {
			for(int j=0;j<colName.size();j++){
				if(colName.get(j).equals(i.getKey())){
					whereAnd+=" and "+colName.get(j)+" = ? ";
					whereAnd_bak +=" and "+colName.get(j)+" = '"+i.getValue()+"' ";
					para.add(i.getValue());
				}
			}

		}
		//排序
		if (StringUtils.isNotEmpty(pageImpl.getSortName())) {
			sortName=" order by "+pageImpl.getSortName()+"  nulls first";
		}else {
			Map<String, Object> tNames = findDAGLByTableNameF(tableName);
			String name = "";
			if(all>1) {
				if (tNames==null) {
					name=" archive_no ";
	//				tNames = findDAGLByTableName(tName);
	//				name = String.valueOf(tNames.get("S_COL_NAME"));
				}
				else {
					 name = (String) tNames.get("S_COL_NAME");
				}
			}else {
				tNames = findDAGLByTableName(tableName);
				name = String.valueOf(tNames.get("S_COL_NAME"));
			}
			sortName=" order by ";
			if(tableName.indexOf("_dak")==-1){
				sortName+= "	cre_chushi_id, ";
			}
			sortName += name;
			//客户要求根据档号生序排列 王磊 20190403
			sortOrder=" asc nulls first";
		}
		//原来的立卷单位查询条件  暂时不用
		if (StringUtils.isNotEmpty(danweihao)) {
			String[] split = danweihao.split(",");
			danwei=" and(";
			danwei_bak =" and(";
			int i = 0;
			for(String hao:split) {
				i++;
				if(i==split.length) {
					danwei+=" cre_chushi_id= ? ) ";
					danwei_bak+=" cre_chushi_id= '"+hao+"' ) ";
					para.add(hao);
					break;
				}
				danwei+=" cre_chushi_id= ? or ";
				danwei_bak +=" cre_chushi_id= '"+hao+"' or ";
				para.add(hao);
			}
		}
		//判断是否有父级勾选
		if (StringUtils.isNotEmpty(fids)) {
			Map<String, Object> column = findDAGLByTableName(tableName);
			List<Map<String,Object>> findSublevelDedicated = findSublevelDedicated(fids, column.get("M_TABLE_NAME").toString(), column.get("S_COL_NAME").toString());
			//String[] split = fids.split(",");
			wherefid=" and archive_flag = '"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"' and(";
			wherefid_bak =" and archive_flag = '"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"' and(";
			int i = 0;
			String userFiltration = " ";//根据用户id过滤
			for(Map<String,Object> map:findSublevelDedicated) {
				if(tName.indexOf("_dak") == -1){
					//单位预立库需要加创建人的过滤条件，归档库不需要
					userFiltration= "and cre_user_id='"+map.get("CRE_USER_ID").toString()+"' and cre_chushi_id='"+map.get("CRE_CHUSHI_ID").toString()+"'";
				}
				i++;
				if(i==findSublevelDedicated.size()) {
					wherefid+="("+column.get("S_COL_NAME").toString()+"='"+map.get(column.get("S_COL_NAME").toString())+"' "+userFiltration+") ) ";
					wherefid_bak +="("+column.get("S_COL_NAME").toString()+"='"+map.get(column.get("S_COL_NAME").toString())+"' "+userFiltration+") ) ";
					break;
				}
				wherefid+="("+column.get("S_COL_NAME").toString()+"='"+map.get(column.get("S_COL_NAME").toString())+"' "+userFiltration+") or ";
				wherefid_bak+="("+column.get("S_COL_NAME").toString()+"='"+map.get(column.get("S_COL_NAME").toString())+"' "+userFiltration+") or ";
			}
			/*int i = 0;
			for(String fid:split) {
				Map<String, Object> TableName = findDAGLByTableName(tName);
				List<Map<String,Object>> findById = findById((String)TableName.get("M_TABLE_NAME"), fid);
				String pid = (String) findById.get(0).get(column.get("S_COL_NAME"));
				i++;
				if(split.length==i) {
					wherefid=wherefid+" "+column.get("S_COL_NAME")+" = '"+pid +"') ";
					break;
				}
				wherefid=wherefid+" "+column.get("S_COL_NAME")+" = '"+pid +"' or ";
			}*/
		}
		//组合查询
        if (StringUtils.isNotEmpty(complexParam)) {
            complexQuery = complexSelect(complexParam);
        }
        //预立库（按门类/按立卷单位）另外两个数的条件
        if (StringUtils.isNotEmpty(ljdanweiAndRenyuan)) {
            Map<String, String> ljdanwei = JSONObject.parseObject(ljdanweiAndRenyuan, new TypeReference<Map<String, String>>() {});
            for(Entry<String, String> e:ljdanwei.entrySet()) {
            	if(e.getKey().equals("LJDW_MARK")) {
            		renyuanLjDw+=" and  CRE_CHUSHI_ID = ? ";
					renyuanLjDw_bak +=" and  CRE_CHUSHI_ID = '"+e.getValue()+"' ";
            		para.add(e.getValue());
            	}else {
            		renyuanLjDw+=" and  CRE_USER_ID = ? ";
					renyuanLjDw_bak +=" and  CRE_CHUSHI_ID = '"+e.getValue()+"' ";
            		para.add(e.getValue());
            	}
            }
        }
        //如果是单位预立库档案实体状态添加非移交和空的条件
		String sql = "select * from " +tableName+ " where 1=1 "+wherefid+whereAnd+danwei+user+easyQuery + complexQuery +renyuanLjDw+archiveEntityStatus+sortName+ sortOrder;
		String sqlStr="select * from " +tableName+ " where 1=1 "+wherefid_bak+whereAnd_bak+danwei_bak+user+easyQuery_bak + complexQuery +renyuanLjDw_bak+archiveEntityStatus+sortName+ sortOrder;
		sql="select * from ( select t.*,rownum rn from ("+sql+") t where rownum <="+pageImpl.getPageNumber()*pageImpl.getPageSize()+") where rn >= "+((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1)+" ";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql,para.toArray());
		if(queryForList!=null&&!queryForList.isEmpty()) {
			String totalsql = "select count(1) from " +tableName+ " where 1=1 "+wherefid+whereAnd+user+easyQuery +danwei+complexQuery+renyuanLjDw+archiveEntityStatus;
			Integer queryForObject = jdbcTemplate.queryForObject(totalsql, Integer.class,para.toArray());
			Map<String, Object> map = new HashMap<>();
			map.put("total", queryForObject);
			map.put("sqlStr", sqlStr);
			queryForList.add(map);
		}else {
			Map<String, Object> map = new HashMap<>();
			map.put("sqlStr", sqlStr);
			queryForList.add(map);
		}


		return queryForList;
	}

    /**
     *
     * @return
     * @author 颜振兴
     * List<VirtualClass>
     * TODO 获取部门树（整理编目、档案检索树查询）
     */
	@Override
	public List<VirtualClass> findTree(boolean isQ2,String isAdmin,String dwylk) {
		//List<DaglCategoryTable> doorList = daglCategoryTableDao.DoorList();
		//全宗
		List<VirtualClass> generalArchiveList = new ArrayList<>();
		String generalArchiveSql = "select t.* from dagl_general_archive t order by t.general_archive_code asc";

		List<DaglGeneralArchive> daglGeneralArchiveList = jdbcTemplate.query(generalArchiveSql, new BeanPropertyRowMapper<>(DaglGeneralArchive.class));

		for (DaglGeneralArchive daglGeneralArchive : daglGeneralArchiveList) {
			VirtualClass class1 = new VirtualClass();
			class1.setName(daglGeneralArchive.getGeneralArchiveName());
			class1.setpId("");
			class1.setId(daglGeneralArchive.getGeneralArchiveCode());
			generalArchiveList.add(class1);
		}

		String rrl = "";
		if(StringUtils.isNotEmpty(dwylk)) {
			rrl="  where t.category_code in (select cate_id from  dagl_cate_dept_person_relation where lrr_id='"+UserUtil.getCruUserId()+"' group by cate_id) ";
			if("danweiguidang".equals(dwylk)) {
				rrl="  where t.category_code in (select cate_id from  dagl_cate_dept_person_relation where ljdw_admin_id like '%"+UserUtil.getCruUserId()+"%' group by cate_id) ";
			}
		}
		String sql = "select * from Dagl_Category_Table t "+rrl+" order by t.cre_Time asc";
		List<DaglCategoryTable> doorList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DaglCategoryTable.class));
		String[] codes = new String[doorList.size()];
		for(int i=0;i<doorList.size();i++) {
			codes[i]="'"+doorList.get(i).getCategoryCode()+"'";
		}
		List<VirtualClass> findTree = dao.findTree(isAdmin,dwylk,codes);
		List<VirtualClass> list = new ArrayList<>();
		
		for (DaglCategoryTable categoryTable : doorList) {
			if (categoryTable.getCategoryCode().equals("q2")) {
				if (!isQ2) {
					continue;
				}
				if (UserUtil.getCruUserRoleNo().indexOf(DAGLCommonConstants.ADMIN)<0) {
					continue;
				}
			}
			VirtualClass class1 = new VirtualClass();
			class1.setName(categoryTable.getCategoryName());
			class1.setpId(categoryTable.getGeneralArchiveCode());
			class1.setId(categoryTable.getCategoryCode());
			class1.setCategoryCode(categoryTable.getCategoryCode());
			list.add(class1);
		}
		list.addAll(findTree);
		generalArchiveList.addAll(list);

		return generalArchiveList;
	}

    /**
     *
     * @param tName
     * @param fids
     * @param column
     * @param value1
     * @param value2
     * @return
     * @author 颜振兴
     * int
     * TODO 替换
     */
	@Override
	public int replace(String tName, String fids, String column, String value1, String value2) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		List<Object> para = new ArrayList<>();
		String wherefid="";
		if (StringUtils.isNotEmpty(fids)) {
			String[] split = fids.split(",");
			wherefid=" and(";
			int i = 0;
			for(String fid:split) {
				i++;
				if(split.length==i) {
					wherefid=wherefid+"recid = ? ) ";
					para.add(fid);
					break;
				}
				wherefid=wherefid+"recid = ? or ";
				para.add(fid);
			}
		}
		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);
		String col = "";
		for(int i=0;i<colName.size();i++){
			if(colName.get(i).equals(column.toLowerCase())){
				col =colName.get(i);
			}
		}
		//para.add(column);

		String value11="";
		if(StringUtils.isNotEmpty(value1)) {
			value11 = " like ? ";
			para.add("%"+value1+"%");
		}else {
			value11 = " is null";
		}
		
		String sql="select * from "+tableName+" where 1=1" + wherefid + "and " + col +value11;
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql,para.toArray());
		int i=0;
		for(Map<String,Object> map:queryForList) {
			String replaceAll="";
			if(StringUtils.isBlank(value1)) {
				replaceAll = value2;
			}else {
				String string1 =  map.get(col).toString();
				replaceAll = string1.replaceAll(value1, value2);
			}
			String esql = " update "+tableName+ " set "+col+"= ? where recid='"+map.get("recid")+"'";
		int update = jdbcTemplate.update(esql,replaceAll);
		i+=update;
		}
		return i;
	}

    /**
     *
     * @param tName
     * @param id
     * @return
     * @author 颜振兴
     * List<Map<String,Object>>
     * TODO 根据recid获取一条数据
     */
	@Override
	public List<Map<String, Object>> findById(String tName, String id) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		String sql = "select * from " + tableName+" where recid = ? ";
		return jdbcTemplate.queryForList(sql,id);
	}
	public List<Map<String, Object>> findByIdrchiventityStatus(String tName, String id) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//String sql = "select * from " + tName+" where recid='"+id+"' and (archive_entity_status!='已移交' and  archive_entity_status!='已移交待审核')";
		// 调整档案试题状态从常量类读取 王磊 20190409
		String sql = "select * from " + tableName+" where recid= ? and (archive_entity_status!='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"' and  archive_entity_status!='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[1]+"')";
		return jdbcTemplate.queryForList(sql,id);
	}

    /**
     *
     * @param virtualClass
     * @return
     * @author 颜振兴
     * VirtualClass
     * TODO 添加树节点
     */
	@Override
	public List<VirtualClass> addTree(VirtualClass virtualClass) {

		List<VirtualClass> virtualClassList = new ArrayList<>();
		String[] columnValues = virtualClass.getColumnValue().split(",");
		String[] names = virtualClass.getName().split(",");


		virtualClass.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		virtualClass.setCreUserId(UserUtil.getCruUserId());
		virtualClass.setCreUserName(UserUtil.getCruUserName());
		virtualClass.setCreJuId(UserUtil.getCruJuId());
		virtualClass.setCreJuName(UserUtil.getCruJuName());
		for(int i=0;i<columnValues.length;i++){
			/*virtualClass.setId("");*/
			virtualClass.setColumnValue(columnValues[i].trim());
			virtualClass.setName(names[i].trim());
			List<VirtualClass> virtualClasses = findVirtualClass(virtualClass);
			if(virtualClasses.size()>0){
				//进行修改
				virtualClass.setId(virtualClasses.get(0).getId());
			}else{
				//进行新增，并返回前台，更新树节点
				virtualClass.setId("");
				virtualClassList.add(dao.saveAndFlush(virtualClass));
			}

		}
		return virtualClassList;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 判断该门类下是否已经含有该分类/根据门类code，pid，字段英文名，是否admin，创建人查询已有的选项回填下拉框
	 * @Date 2019/5/28 11:54
	 * @Param [virtualClass]
	 * @return com.sinosoft.sinoep.modules.dagl.bmgl.entity.VirtualClass
	 **/
	@Override
	public List<VirtualClass> findVirtualClass(VirtualClass virtualClass){

		List<Object> para = new ArrayList<>();
		String sql ="select t.*\n" +
						"  from dagl_virtual_class t\n" +
						" where t.column_name = ? \n" +
						"   and t.category_code = ? \n" +
						"   and t.pid = ? \n" +
						"	and t.cre_user_id = ? ";
		para.add(virtualClass.getColumnName());
		para.add(virtualClass.getCategoryCode());
		para.add(virtualClass.getpId());
		para.add(UserUtil.getCruUserId());

		if(StringUtils.isNotEmpty(virtualClass.getColumnValue())){
			//值不为空的时候，根据值查询单独的值
			sql += "   and t.column_value = ? \n" +
					"  and t.name = ? \n";
			para.add(virtualClass.getColumnValue());
			para.add(virtualClass.getName());
		}

		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(VirtualClass.class),para.toArray());
	}

    /**
     *
     * @param id
     * @return
     * @author 颜振兴
     * int
     * TODO 删除树的节点 包括子节点
     */
	@Override
	public int deleteTree(String id) {
		String sql = "select * from  dagl_virtual_class  start with id =? connect by prior id=pid";
		List<VirtualClass> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(VirtualClass.class),id);
		for(VirtualClass virtualClass:query) {
			dao.delete(virtualClass.getId());
		}
		return 1;
	}

    /**
     *
     * @param tName
     * @param id
     * @return
     * @author 颜振兴
     * int
     * TODO 动态删除一条数据
     */
	@Override
	public int dynamicDelete(String tName, String id,int all,int ranking) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		//删除的话总件数，或者上层的件数-1
		String pieceNumData = "-1";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}
		List<Object> para = new ArrayList<>();

		String sql ="";
		List<Map<String,Object>> findById=null;
		if(UserUtil.getCruUserRoleNo().indexOf(DAGLCommonConstants.ADMIN)>-1) {
			sql = "delete from "+tableName+" where recid=? ";
			para.add(id);
			findById=findById(tableName, id);//根据表名和id获取该条数据
		}else {
			//个人删除档案时，考虑档案实体状态为空的情况 王磊 2019-03-14
			//sql = "delete from "+tName+" where recid='"+id+"' and ((archive_entity_status!='已移交' and  archive_entity_status!='已移交待审核') or archive_entity_status is null) ";
			//调整档案实体状态从常量类获取 王磊 20190409
			sql = "delete from "+tableName+" where recid = ? and ((archive_entity_status!='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"' and  archive_entity_status!='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[1]+"') or archive_entity_status is null) ";
			para.add(id);
			findById=findByIdrchiventityStatus(tableName, id);//w为了查关联字段数据
		}

		if(findById!=null&&!findById.isEmpty()) {
			Map<String, Object> NameF = findDAGLByTableNameF(tableName);//获取关联关系（第一层和第二层的关系）
			if(NameF!=null&&!NameF.isEmpty()) {
				Map<String, Object> sNameF = findDAGLByTableNameF(NameF.get("S_TABLE_NAME").toString());//获取关联关系（第二层和第三层的关联关系）
				if(sNameF!=null&&!sNameF.isEmpty()) {
				    //根据关联字段，关联值，创建人，创建人处室查询子表数据（第二层）
					List<Map<String,Object>> findListByguanlian = findListByguanlian(NameF.get("S_COL_NAME")==null?"":NameF.get("S_COL_NAME").toString(),
																					findById.get(0).get(NameF.get("S_COL_NAME")==null?"":NameF.get("S_COL_NAME").toString())==null?"":findById.get(0).get(NameF.get("S_COL_NAME")==null?"":NameF.get("S_COL_NAME").toString()).toString(),
																					NameF.get("S_TABLE_NAME")==null?"":NameF.get("S_TABLE_NAME").toString(),
																					findById.get(0).get("CRE_USER_ID")==null?"":findById.get(0).get("CRE_USER_ID").toString(),
																					findById.get(0).get("CRE_CHUSHI_ID")==null?"":findById.get(0).get("CRE_CHUSHI_ID").toString());
					for(Map<String,Object> map:findListByguanlian) {
                        //根据关联字段，关联值，创建人，创建人处室查询子表数据（第三层）
						List<Map<String,Object>> findListByguanlian3 = findListByguanlian(sNameF.get("S_COL_NAME").toString(), map.get(sNameF.get("S_COL_NAME").toString()).toString(), sNameF.get("S_TABLE_NAME").toString(), map.get("CRE_USER_ID").toString(), findById.get(0).get("CRE_CHUSHI_ID").toString());
						for(Map<String,Object> map2:findListByguanlian3) {

							//归档库
							if(sNameF.get("S_TABLE_NAME").toString().indexOf("_dak") > -1){
								try{
									//查询第三层的recid
									String findRecidsSql = "select recid from "+sNameF.get("S_TABLE_NAME").toString()+" where cre_user_id ='"+findById.get(0).get("CRE_USER_ID").toString()+"' "+"and "+sNameF.get("S_COL_NAME").toString()+"='"+map2.get(sNameF.get("S_COL_NAME").toString()).toString()+"' and archive_flag ='"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
									List<String> queryForObject = jdbcTemplate.queryForList(findRecidsSql, String.class);
									if(queryForObject.size()>0){
										String recids = "";
										//拼接recids
										for(int m = 0;m<queryForObject.size();m++){
											recids += queryForObject.get(m)+",";
										}
										if(!"".equals(recids)) {
											recids = CommonUtils.commomSplit(recids);
											//删除档案时同时删除收集列表，借阅子表的数据
											shoppingTrolleyService.deleteShoppingTrolleyByRecidAndTableName(recids,sNameF.get("S_TABLE_NAME").toString());
											daglBorrowService.deleteDaglFileByTNameAndRecid(recids,sNameF.get("S_TABLE_NAME").toString());
										}

									}
								}catch (Exception e){
									//啥都不干
								}

							}

						    //删除第三层数据
							int deleteSall3 = deleteSall(sNameF.get("S_TABLE_NAME").toString(), findById.get(0).get("CRE_USER_ID").toString(), sNameF.get("S_COL_NAME").toString(), map2.get(sNameF.get("S_COL_NAME").toString()).toString());
							log.info("sdgd" +deleteSall3);
							//调整文电数据的文件状态 王磊 20190409
							receiveService.updateStateByIdAndState(sNameF.get("S_TABLE_NAME").toString(), findById.get(0).get("CRE_USER_ID").toString(), sNameF.get("S_COL_NAME").toString(), map2.get(sNameF.get("S_COL_NAME").toString()).toString());
							sendService.updateStateByIdAndState(sNameF.get("S_TABLE_NAME").toString(), findById.get(0).get("CRE_USER_ID").toString(), sNameF.get("S_COL_NAME").toString(), map2.get(sNameF.get("S_COL_NAME").toString()).toString());
						}
					}
				}

				//归档库的数据删除时，才会去删除收集列表和借阅的信息
				if(NameF.get("S_TABLE_NAME").toString().indexOf("_dak")>-1){
					try{
						//查询第二层所有的recid
						String findRecidsSql = "select recid from "+NameF.get("S_TABLE_NAME").toString()+" where cre_user_id ='"+findById.get(0).get("CRE_USER_ID").toString()+"' "+"and "+NameF.get("S_COL_NAME").toString()+"='"+findById.get(0).get(NameF.get("S_COL_NAME").toString()).toString()+"' and archive_flag ='"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
						List<String> queryForObject = jdbcTemplate.queryForList(findRecidsSql, String.class);
						if(queryForObject.size()>0){
							String recids = "";
							//拼接recids
							for(int m = 0;m<queryForObject.size();m++){
								recids += queryForObject.get(m)+",";
							}

							if(!"".equals(recids)){
								recids = CommonUtils.commomSplit(recids);
								//删除档案时同时删除收集列表，借阅子表的数据
								shoppingTrolleyService.deleteShoppingTrolleyByRecidAndTableName(recids,NameF.get("S_TABLE_NAME").toString());
								daglBorrowService.deleteDaglFileByTNameAndRecid(recids,NameF.get("S_TABLE_NAME").toString());
							}
						}
					}catch (Exception e){
						//啥都不干
					}
				}

				//删除第二层数据
				int deleteSall2 = deleteSall(NameF.get("S_TABLE_NAME").toString(), findById.get(0).get("CRE_USER_ID").toString(), NameF.get("S_COL_NAME").toString(), findById.get(0).get(NameF.get("S_COL_NAME").toString()).toString());
				log.debug("删除了"+deleteSall2);
				//调整文电数据的文件状态 王磊 20190409
				receiveService.updateStateByIdAndState(NameF.get("S_TABLE_NAME").toString(), findById.get(0).get("CRE_USER_ID").toString(), NameF.get("S_COL_NAME").toString(), findById.get(0).get(NameF.get("S_COL_NAME").toString()).toString());
				sendService.updateStateByIdAndState(NameF.get("S_TABLE_NAME").toString(), findById.get(0).get("CRE_USER_ID").toString(), NameF.get("S_COL_NAME").toString(), findById.get(0).get(NameF.get("S_COL_NAME").toString()).toString());
			}
			if(ranking>1 && all == ranking){
				//多层的最后一层
				//删除之前去修改父级节点对应的总件数和案卷数
				int num = updatePNum("",id,tableName,all+"", ranking+"",pieceNumData,findById.get(0).get("PAGE_NUM")==null?"0":"-"+findById.get(0).get("PAGE_NUM").toString(),"delete","");
			}else if(ranking==2 && all == 3){
				//三层的第二层
				//删除之前去修改父级节点对应的总件数和案卷数
				int num = updatePNum("",id,tableName,all+"", ranking+"",pieceNumData,findById.get(0).get("PIECE_NUM")==null?"0":"-"+findById.get(0).get("PIECE_NUM").toString(),"delete","");
			}
		}

		int update = jdbcTemplate.update(sql,para.toArray());
		//归档库
		if(tableName.indexOf("_dak") !=-1){
			//删除档案时同时删除收集列表，借阅子表的数据
			id="'"+id+"'";
			shoppingTrolleyService.deleteShoppingTrolleyByRecidAndTableName(id,tableName);
			daglBorrowService.deleteDaglFileByTNameAndRecid(id,tableName);
		}
		return update;

	}

    /**
     *
     * @param category_code
     * @return
     * @author 颜振兴
     * List<Map<String,Object>>
     * TODO	根据门类代号查门类id
     */
	@Override
	public String findMenById(String category_code) {
		String sql ="select id from dagl_category_table where category_code= ? ";
		String queryForObject = jdbcTemplate.queryForObject(sql, String.class,category_code);
		return queryForObject;
	}

    /**
     *
     * @param category_code_id
     * @return
     * @author 颜振兴
     * List<Map<String,Object>>
     * TODO 根据门类查档号规则
     */
	@Override
	public List<Map<String, Object>> findDHGZbyMCode(String category_code_id) {
		String sql = "select * from dagl_party_num_rule where category_id = ? order by order_num asc";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql,category_code_id);
		return queryForList;
	}

    /**
     *
     * @param tName
     * @return
     * @author 颜振兴
     * List<Map<String,Object>>
     * TODO 根据z表名查询档号关联字段
     */
	@Override
	public Map<String, Object> findDAGLByTableName(String tName) {
		String sql ="select * from tables_relation where s_table_name = ? ";
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql,tName);
		return queryForList.get(0);
	}

    /**
     *
     * @param tName
     * @return
     * @author 颜振兴
     * List<Map<String,Object>>
     * TODO 根据f表名查询档号关联字段
     */
	@Override
	public Map<String, Object> findDAGLByTableNameF(String tName) {
		String sql ="select * from tables_relation where m_table_name = ? ";
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql,tName);
		if (queryForList!=null&&!queryForList.isEmpty()) {
			return queryForList.get(0);

		}else {
			return null;
		}
	}

	public List<Map<String, Object>> findChildren(String tName,String colnum,String value,String userid,String chushiid){
		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}
		List<Object> para = new ArrayList<>();
		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);
		String col = "";
		for(int i=0;i<colName.size();i++){
			if(colName.get(i).equals(colnum)){
				col =colName.get(i);
			}
		}
		para.add(value);

		String user = "";
		String chushi = "";
		if(!StringUtils.isBlank(userid)) {
			user=" and  CRE_USER_ID = ? ";
			para.add(userid);
		}
		if(!StringUtils.isBlank(chushiid)) {
			chushi=" and  CRE_CHUSHI_ID =? ";
			para.add(chushiid);
		}

		String sql = "select  * from "+tableName+" where "+col+" = ? "+user+chushi+"";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql,para.toArray());
		return queryForList;
	}

    /**
     *
     * @param tName
     * @param colnum
     * @return
     * @author 颜振兴
     * String
     * TODO 使用英文名查中文名
     */
	@Override
	public String findZHbycolnum(String tName, String colnum) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		String sql = "select COLUMN_CHN_NAME from table_struct_description where table_name='"+tableName+"' and COLUMN_NAME = ? ";
		return jdbcTemplate.queryForObject(sql, String.class,colnum);
	}

    /**
     *
     * @param tName
     * @return
     * @author 颜振兴
     * List<Map<String,Object>>
     * TODO 获取档案库标签
     */
	@Override
	public List<Map<String, Object>> findLabelDAK(String tName) {

		//String sql = "select d.* from ( select * from tables_relation t   where t.s_table_name  like '%_dak'  start with t.m_table_name ='"+tName+"' connect by prior t.s_table_name = t.m_table_name) s,table_description d where d.table_name=s.s_table_name";
		String sql = "select t.s_table_name table_name, " + 
				"       (select d.table_chn_name " + 
				"          from table_description d " + 
				"         where t.s_table_name = d.table_name) table_chn_name " + 
				"  from tables_relation t " + 
				" where t.s_table_name  like '%_dak' " + 
				" start with t.m_table_name = ? " +
				" connect by prior t.s_table_name = t.m_table_name " + 
				" ";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql,tName);
		return queryForList;
	}

	/**
	 * TODO 查询勾选的多行数据列表
	 * @author 李颖洁
	 * @date 2018年11月22日上午11:28:10
	 * @param tName 当前页标签页的表名
	 * @param fids  勾选的ids
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getSelectedData(String tName, String fids) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		List<Object> para = new ArrayList<>();
		Map<String,Object> recodeNoMap1 = findDAGLByTableNameF(tableName);
		String recodeNo;
		if(recodeNoMap1==null){
			recodeNoMap1 = findDAGLByTableName(tableName);
			recodeNo = String.valueOf(recodeNoMap1.get("S_COL_NAME"));
		}else{
			recodeNo = (String) recodeNoMap1.get("M_COL_NAME");//当前表档号字段
		}
		String wherefid="";
		if (StringUtils.isNotEmpty(fids)) {
			String[] split = fids.split(",");
			wherefid=" and(";
			int i = 0;
			for(String fid:split) {
				i++;
				if(split.length==i) {
					wherefid=wherefid+"recid = ? ) ";
					para.add(fid);
					break;
				}
				wherefid=wherefid+"recid = ? or ";
				para.add(fid);
			}
		}
		String sql="select * from "+tableName+" where 1=1" + wherefid+" order by "+recodeNo+" asc" ;
		log.info(sql);
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql,para.toArray());
		return queryForList;
	}

	/**
	 * TODO 获取未组卷的文件
	 * @author 李颖洁
	 * @date 2018年11月22日下午1:55:45
	 * @param tName 案卷表名
	 * @param basefolderUnit 立卷单位在数据字典中的code
	 * @param creUserId 创建者id
	 * @return List<Map<String, Object>>
	 */
	@Override
	public List<Map<String, Object>> getNotSetVolumeList(String tName,String basefolderUnit,String creUserId) {

		List<Object> para = new ArrayList<>();

		//获取子表表名（卷内表名）
		List<Map<String,Object>> list; 
		if(tName.indexOf("dak")==-1){
			list = findLabel(tName);
		}else{
			list = findLabelDAK(tName);
		}
		Map<String, Object> map = findDAGLByTableNameF(tName);
		String sRelationColumn = String.valueOf(map.get("S_COL_NAME"));//子表的关联字段
		String pRelationColumn = String.valueOf(map.get("M_COL_NAME"));//父表的关联字段
		String conCol = "";
		String orderCol = "";
		if (map!=null&&!map.isEmpty()) {
			conCol = (String) map.get("S_COL_NAME");
			if("item_folder_no".equals(conCol)){
				orderCol = "folder_no";
			}else if("folder_no".equals(conCol)){
				orderCol = "archive_no";
			}
		}

		if(list.size()>0){
			String name = (String) list.get(0).get("TABLE_NAME");//子表表名
			//数据为游离的文件信息
			String sql = "select t.* from "+name+" t where t.archive_flag ='"+DAGLCommonConstants.ARCHIVE_FLAG[1]+"' " ;
			//获取角色
			//String role = UserUtil.getCruUserRoleNo();
			if(name.indexOf("_dak") == -1){
				//单位预立库才需要根据创建人，立卷单位去过滤数据
				//添加创建人一致的条件 王磊 2019-03-12
				String condition = " and t.cre_chushi_id = ? and t.cre_user_id = ? ";
				para.add(basefolderUnit);
				para.add(creUserId);
				sql += condition;
			}
			sql += "order by "+orderCol+" asc" ;
			log.info(sql);
			list = jdbcTemplate.queryForList(sql,para.toArray());
		}
		return list;
	}

	/**
	 * TODO  根据父表id 查询字表的关联信息
	 * @author 李颖洁
	 * @date 2018年11月23日上午11:53:31
	 * @param tName 表名
	 * @param fid 案卷号
	 * @param basefolderUnit 立卷单位在数据字典中的code
	 * @param creUserId 案卷记录创建者id
	 * @return List<Map<String,Object>>
	 */
	@Override
	public List<Map<String, Object>> getChildDataByFartherId(String tName, String fid,String basefolderUnit,String creUserId) {

		List<Object> para = new ArrayList<>();
		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//获取子表表名
		List<Map<String,Object>> list; 
		if(tableName.indexOf("dak")==-1){
			list = findLabel(tableName);
		}else{
			list = findLabelDAK(tableName);
		}
		//获取子表关联字段
		Map<String,Object> conColMap = findDAGLByTableNameF(tableName);
		String conCol = "";
		String orderCol = "";
		if (conColMap!=null&&!conColMap.isEmpty()) {
			conCol = (String) conColMap.get("S_COL_NAME");
			if("item_folder_no".equals(conCol)){
				orderCol = "folder_no";
			}else if("folder_no".equals(conCol)){
				orderCol = "archive_no";
			}
		}
		if(list.size()>0){
			String name = (String) list.get(0).get("TABLE_NAME");
			String sql = "select * from "+name+" where "+conCol+"= ? and archive_flag='"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"' " ;

			para.add(fid);
			if(name.indexOf("_dak") == -1){
				//单位预立库加创建人，立卷单位过滤条件
				sql += " and cre_chushi_id = ? and cre_user_id= ? ";
				para.add(basefolderUnit);
				para.add(creUserId);
			}
			sql +="  order by "+orderCol+" asc";
			list = jdbcTemplate.queryForList(sql,para.toArray());
		}
		return list;
	}

	/**
	 * TODO 拆卷
	 * @author 李颖洁
	 * @date 2018年11月23日下午4:15:04
	 * @param tName
	 * @param ids
	 * @param archiveFlag
	 * @param conCol
	 * @return
	 */
	@Override
	public int openVolume(String tName, String ids, String archiveFlag, String conCol,String conVal, String fid, String all, String ranking) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);

		String col = "";
		for(int i=0;i<colName.size();i++){
			if(colName.get(i).equals(conCol)){
				col =colName.get(i);
			}
		}
		List<Object> para = new ArrayList<>();
		if(!(" ".equals(conVal))){
			para.add(conVal);
		}
		para.add(archiveFlag);
		String inString = CommonUtils.solveSqlInjectionOfIn(ids,para);
		String sql = "";
		int num =0;
		if(" ".equals(conVal)){
			//调出不修改项目级档号，只修改组卷标识为未组卷，要先去修改数量，因为要查询父级
			String[] id = ids.split(",");
			for(int i=0;i<id.length;i++){
				//根据id先查询当前数据
				String thisDataSql = "select * from "+tableName+" t where t.recid= ? ";
				List<Map<String,Object>> thisData = jdbcTemplate.queryForList(thisDataSql,id[i]);
				String pageNumData = thisData.get(0).get("PAGE_NUM")==null?"0":thisData.get(0).get("PAGE_NUM").toString();
				String spieceNumData = thisData.get(0).get("PIECE_NUM")==null?"0":thisData.get(0).get("PIECE_NUM").toString();
				String pieceNumData = "-1";
				int updatePNum = updatePNum(fid,id[i],tableName,all, (Integer.parseInt(ranking)+1)+"",pieceNumData,"-"+pageNumData,"diaochu","-"+spieceNumData);

			}
			sql = "UPDATE "+tableName+" SET ARCHIVE_FLAG = ? WHERE RECID IN ("+inString+")";
			log.info(sql);
			num = jdbcTemplate.update(sql,para.toArray());
		}else{
			//调入，在执行修改之后，，要先去修改数量，因为要查询父级
			sql = "UPDATE "+tableName+" SET "+col+" = ? ,ARCHIVE_FLAG = ? WHERE RECID IN ("+inString+")";
			log.info(sql);
			num = jdbcTemplate.update(sql,para.toArray());
			String[] id = ids.split(",");
			for(int i=0;i<id.length;i++){
				//根据id先查询当前数据
				String thisDataSql = "select * from "+tableName+" t where t.recid= ? ";
				List<Map<String,Object>> thisData = jdbcTemplate.queryForList(thisDataSql,id[i]);
				String pageNumData = thisData.get(0).get("PAGE_NUM")==null?"0":thisData.get(0).get("PAGE_NUM").toString();
				String spieceNumData = thisData.get(0).get("PIECE_NUM")==null?"0":thisData.get(0).get("PIECE_NUM").toString();
				String pieceNumData = "1";
				int updatePNum = updatePNum(fid,id[i],tableName,all, (Integer.parseInt(ranking)+1)+"",pieceNumData,pageNumData,"diaoru",spieceNumData);

			}
		}


		//调入调出,修改父级的相关数量
		return num;
	}

	/**
	 * TODO 更改卷内文件关联（档案确认调整）
	 * @author 李颖洁
	 * @date 2018年11月24日下午6:08:39
	 * @param tName 卷内表名
	 * @param conCol 关联字段
	 * @param conVal 关联字段值
	 * @param data 更改数据项
	 * @return int
	 */
	@Override
	public int updateFileRelation(String tName, String conCol, String conVal, String data,String categoryCode,String pName,String pId, String ranking, String all) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String ptableName = "";
		List<String> ptableNames = findAllTables();
		for(int i=0;i<ptableNames.size();i++){
			if(pName.equals(ptableNames.get(i))){
				ptableName = ptableNames.get(i);
			}
		}


		int num;
		String categoryId = findMenById(categoryCode);
		List<Map<String,Object>> list = findDHGZbyMCode(categoryId);
		int len = list.size();
		//少了几层
		int jianlen = Integer.parseInt(all)-Integer.parseInt(ranking);
		String numbLength = (String)list.get(len-jianlen).get("NUMB_LENGTH");
		int columLen = 0;
		if(!StringUtils.isBlank(numbLength)){
			columLen = Integer.parseInt(numbLength);
		}
		String connector = (String) list.get(len-jianlen-1).get("CONNECTOR");
		String pieceNo;
		String archiveNo;
		String pTableSql = "select * from "+ptableName+" where recid = ? ";//根据id查询案卷的信息
		List<Map<String,Object>> pData = jdbcTemplate.queryForList(pTableSql,pId);
		String columnSql = "";//拼接档号项字段和值的sql（不拼接最后一个档号字段）
		int size = 1;
		if("1".equals(ranking) && "3".equals(all)){
			size = 2;//第一层和第二层调整的时候，不拼接后两个字段
		}
		for (int i=0;i<list.size()-size;i++) {
			String column = String.valueOf(list.get(i).get("RULE_FIELD")).toUpperCase();//注意转为大写字母
            //如果获取的是null则存空字符串
            String columnValue = null == pData.get(0).get(column)?"":pData.get(0).get(column).toString();
			columnSql += column+"='"+columnValue+"',";
		}
		try {
			JSONArray arr = JSON.parseArray(data);
			//原数据的id和档号
			String[] sql = new String[arr.size()];
			for (int i=0;i<arr.size();i++) {
				archiveNo = "";
				Map<String, String> parseObject = com.alibaba.fastjson.JSONObject.parseObject(JSON.toJSONString(arr.get(i)),
						new TypeReference<Map<String, String>>() {
				});
				pieceNo = parseObject.get("PIECE_NO");
				if(columLen>0){
					if(pieceNo.length()>columLen){
						pieceNo = pieceNo.substring(0,columLen);
					}else if(pieceNo.length()<columLen){
						int pieceLen = pieceNo.length();
						for(int j=0;j<columLen-pieceLen;j++){
							pieceNo = "0"+pieceNo;
						}
					}
				}
				if(!StringUtils.isBlank(connector)){
					archiveNo = conVal+connector+pieceNo;
				}else{
					archiveNo = conVal+pieceNo;
				}
				String folderNo = "";
				String lastColumn = "";
				if("1".equals(ranking) && "3".equals(all)){//三层的字段调整
					folderNo = "FOLDER_NO";
					lastColumn = "YEAR_FOLDER_NO";
				}else{
					folderNo = "ARCHIVE_NO";
					lastColumn = "PIECE_NO";
				}
				sql[i] = "UPDATE "+tableName+" SET "+columnSql+""+lastColumn+" = '"+pieceNo+"',"+folderNo+" = '"+archiveNo+"',"
						+conCol+"='"+conVal+"',archive_flag = '"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"' WHERE RECID='"+parseObject.get("RECID")+"'";
				log.info(sql[i]);
				//从这里开始去更新子表的数据
				//当业务表为三层及以上，并且选中的是小于总层数两层的；因为两层的调整，第二层没有子表，多层的调整的子表为最后一层的时候，子表也没有子表
				if(Integer.parseInt(all)>2 && Integer.parseInt(ranking)<= Integer.parseInt(all)-2){
					//如果有子表数据，同时更新所有子表
					String sTableSql =  "	select s.*\n" +
										"  		from tables_relation s\n" +
										" 		start with s.m_table_name = ? \n" +
										"		connect by prior s.s_table_name = s.m_table_name\n" +
										" 	order by rownum asc";

					//得到子表及关联的信息
					List<Map<String,Object>> sTableList = jdbcTemplate.queryForList(sTableSql,tName);
					//循环更新
					for(int j = 0;j<sTableList.size();j++){
						Map<String,Object> sTableRelationMap = sTableList.get(j);
						//更新所有子表的状态
						//子表表名
						String sTableName = String.valueOf(sTableRelationMap.get("S_TABLE_NAME"));
						//父表关联字段
						String PColnumName = String.valueOf(sTableRelationMap.get("M_COL_NAME"));
						//子表关联字段
						String sColnumName = String.valueOf(sTableRelationMap.get("S_COL_NAME"));

						//获取到变化的数据，
						List<Map<String, Object>> thisData = findById(tableName,parseObject.get("RECID"));
						//获取子表的数据
						//此时操作的是子表状态
						//查询子表数据时添加与父记录立卷单位相同的条件，防止找到其他数据的子表数据 wl 2019-01-11
						//查子表数据时，添加创建人相同条件 王磊2019-03-02
						List<Map<String, Object>> sData = new ArrayList<>();
						//去变化的id，没有就证明是原本的就在卷内的信息，直接根据去当前数据的id
						String value = thisData.get(0).get(PColnumName)==null?"":thisData.get(0).get(PColnumName).toString();
						String userid = thisData.get(0).get("CRE_USER_ID")==null?"":thisData.get(0).get("CRE_USER_ID").toString();
						String chushiid = thisData.get(0).get("CRE_CHUSHI_ID")==null?"":thisData.get(0).get("CRE_CHUSHI_ID").toString();
						if(tName.indexOf("_dak")>1){
							//归档库，不需要考虑录入人和立卷单位
							sData = findChildren(sTableName,sColnumName,value,"","");
						}else{

							sData = findChildren(sTableName,sColnumName,value,userid,chushiid);
						}

						String sfolderNo = "";
						String slastColumn = "";
						if("1".equals(ranking) && "3".equals(all)){
							//三层的，最后一层的档号规则字段，和"序号字段"，当前最多三层。
							sfolderNo = "ARCHIVE_NO";
							slastColumn = "PIECE_NO";
						}else{
						}

						//拼接档号项字段和值的sql（不拼接最后一个档号字段）

						for(int m = 0;m<sData.size();m++){
							Map<String,Object> sTableDataMap = sData.get(m);
							//更新子表的数据
							//子表id
							String sTablerelationData = String.valueOf(sTableDataMap.get("RECID"));
							//第三层的“序号”，拼接新的档号
							String slastColumnValue = String.valueOf(sTableDataMap.get(slastColumn));

							int lastNumbLength = Integer.parseInt((String)list.get(len-jianlen+1).get("NUMB_LENGTH"));
							//上一层的连接符
							String lastConnector = (String) list.get(len-jianlen).get("CONNECTOR");
							if(lastNumbLength>0){
								if(slastColumnValue.length()>lastNumbLength){
									slastColumnValue = slastColumnValue.substring(slastColumnValue.length()-1-lastNumbLength,slastColumnValue.length()-1);
								}else if(slastColumnValue.length()<lastNumbLength){
									int slastColumnValueLen = slastColumnValue.length();
									for(int n=0;n<lastNumbLength-slastColumnValueLen;n++){
										slastColumnValue = "0"+slastColumnValue;
									}
								}
							}
							String updateSTableStatusSql = "update "+sTableName+
														//档号规则的一些字段加上本层的最后一位,"序号"不变
														" set "+columnSql+""+lastColumn+" = '"+pieceNo+"'," +
														//关联字段
														sColnumName+" = '"+archiveNo+"',"+
														//自己的档号
														sfolderNo+"='"+archiveNo+lastConnector+slastColumnValue+"'," +
														//项目级档号
														conCol+"='"+conVal+"'"+
														" where recid='"+sTablerelationData+"'";
							int updateSTableSum =  jdbcTemplate.update(updateSTableStatusSql);
						}
					}
				}
			}
			int[] batchUpdate = jdbcTemplate.batchUpdate(sql);
			if(batchUpdate.length==arr.size()){
				return num = 1;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return num=0;
		}

		return num = 0;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 档案移交
	 * @Date 2018/11/22 19:36
	 * @Param [tName, fids]
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 **/
	@Override
	public List<Map<String, Object>> recordSubmit(String tName, String fids) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}


		List<Map<String,Object>> queryForList = getSelectedData(tableName,fids);//查询勾选的多行数据列表
		List<Map<String,Object>> successList =new ArrayList<>();//成功的数据
		List<Map<String,Object>> alreadySubmmitList = new ArrayList<>();//已经提交待审核的数据
		List<Map<String,Object>> P_NotSubmmitList = new ArrayList<>();//父表为移交的数据
		List<Map<String,Object>> NotAssociatedList = new ArrayList<>();//有父级，尚未关联的

		for(int i = 0;i < queryForList.size();i++){
			//遍历勾选的数据
			//首先查看勾选的数据是否是已移交待审核状态
			Map<String,Object> map = queryForList.get(i);
			String archive_entity_status = String.valueOf(map.get("ARCHIVE_ENTITY_STATUS"));//防止空指针，如果为空，返回null
			if(DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[1].equals(archive_entity_status)||DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2].equals(archive_entity_status)){
				//如果数据是已移交待审核状态或者已移交，放到错误List中，不可提交
				alreadySubmmitList.add(map);
				continue;
			}else{//01或者null
				String pTableSql =
						"SELECT t.*\n" +
								"  FROM tables_relation t, table_description s\n" +
								" WHERE t.m_table_name = s.table_name\n" +
								"   and t.s_table_name = ? ";//排除父节点为门类的数据

				List<Map<String,Object>> pTableList = jdbcTemplate.queryForList(pTableSql,tName);
				if(pTableList.size()>0){
					//有父表，查询出父表的状态
					Map<String,Object> relationMap = pTableList.get(0);
					String pTableName = String.valueOf(relationMap.get("M_TABLE_NAME"));//父表id
					String mColName = String.valueOf(relationMap.get("M_COL_NAME"));//父表字段
					String sColName = String.valueOf(relationMap.get("S_COL_NAME"));//子表字段
					String sColData = String.valueOf(map.get(sColName));
					/*String archiveFlag = String.valueOf(map.get("ARCHIVE_FLAG"));
					if(DAGLCommonConstants.ARCHIVE_FLAG[0].equals(archiveFlag)){*/
						//已组卷
						//与父表做了关联,查询出父表的那条数据//查询父表数据时添加同一个创建人、立卷单位的过滤条件 王磊 2019-03-02
						String sql = "SELECT t.* FROM "+pTableName+" t WHERE t."+mColName+" = '"+sColData+"' and t.CRE_USER_ID='"+map.get("CRE_USER_ID").toString()+"' and t.CRE_CHUSHI_ID='"+map.get("CRE_CHUSHI_ID").toString()+"'";
						List<Map<String,Object>> pTableDataList = jdbcTemplate.queryForList(sql);
						if(pTableDataList.size()>0){//常理来说是不应该为空的，这里做判断是为了防止垃圾数据导致报错

							Map<String,Object> pTableDataMap = pTableDataList.get(0);
							//父表的档案实体状态
							String P_archive_entity_status = String.valueOf(pTableDataMap.get("ARCHIVE_ENTITY_STATUS"));
							if(DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[1].equals(P_archive_entity_status)||DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2].equals(P_archive_entity_status)){
								//父表状态为已移交待归档，或者以移交，则证明为补录，更新其状态为已移交待审
								//更新选中的档案的移交状态
								updateArchiveEntityStatus(tableName,map);

								successList.add(map);

							}else{//01或者null
								//证明父节点未移交，状态为已归档，放到错误的List中返回前台
								P_NotSubmmitList.add(map);
								continue;
							}
						}else{
                            //未组卷
                            NotAssociatedList.add(map);
                            continue;
                        }

					/*}else{//未关联状态一律视为为组卷
						//未组卷
						NotAssociatedList.add(map);
						continue;
					}*/
				}else{
					//没有父表，更新状态
					//父表状态为已移交待归档，或者以移交，则证明为补录，更新其状态为已移交待审
					//更新选中的档案的移交状态
					updateArchiveEntityStatus(tableName,map);
					successList.add(map);
				}
			}
		}
		Map<String,Object> zongMap = new HashMap<String,Object>();
		zongMap.put("successList",successList);
		zongMap.put("alreadySubmmitList",alreadySubmmitList);
		zongMap.put("P_NotSubmmitList",P_NotSubmmitList);//NotAssociatedList
		zongMap.put("NotAssociatedList",NotAssociatedList);
		List<Map<String,Object>> zongList = new ArrayList();
		zongList.add(zongMap);
		return zongList;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 跟新档案及其子表移交状态。
	 * @Date 2018/11/24 9:40
	 * @Param []
	 * @return void
	 **/
	public void updateArchiveEntityStatus(String tName,Map<String,Object> map){

		String updateStatusSql = "update "+tName+" t set t.archive_entity_status='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[1]+"' where t.recid ='"+String.valueOf(map.get("RECID"))+"'";//更新状态的sql

		int updateSum =  jdbcTemplate.update(updateStatusSql);
		//随之解封该该数据
		String fhr_id = String.valueOf(map.get("FHR_ID"));//防止空指针，如果为空，返回null
		if(!("null".equals(fhr_id))){
			//如果封号人id不为null，则认为是封号状态，进行解封操作
			sealUpOrRemoveSeal(tName,String.valueOf(map.get("RECID")),"removeSeal");
		}
		//如果有子表数据，同时更新所有子表表名
		String sTableSql =  "	select s.*\n" +
				"  		from tables_relation s\n" +
				" 		start with s.m_table_name = ? \n" +
				"		connect by prior s.s_table_name = s.m_table_name\n" +
				" 	order by rownum asc";

		List<Map<String,Object>> sTableList = jdbcTemplate.queryForList(sTableSql,tName);//得到子表及关联的信息

		if(sTableList.size()>0){
			for(int j= 0;j<sTableList.size();j++){//目前只更新前两层的数据
				Map<String,Object> sTableRelationMap = sTableList.get(j);
				//更新所有子表的状态
				String sTableName = String.valueOf(sTableRelationMap.get("S_TABLE_NAME"));//子表id
				String PColnumName = String.valueOf(sTableRelationMap.get("M_COL_NAME"));//父表关联字段
				String sColnumName = String.valueOf(sTableRelationMap.get("S_COL_NAME"));//子表关联字段

				String PColnumValue = String.valueOf(map.get(PColnumName));//父表的值字段
				//查询子表的数据
				if(j==0){
					//此时操作的是子表状态
					//查询子表数据时添加与父记录立卷单位相同的条件，防止找到其他数据的子表数据 wl 2019-01-11
					//查子表数据时，添加创建人相同条件 王磊2019-03-02
					List<Map<String, Object>> sData = findChildren(sTableName,sColnumName,PColnumValue,String.valueOf(map.get("CRE_USER_ID")),String.valueOf(map.get("CRE_CHUSHI_ID")));
					for(int m = 0;m<sData.size();m++){
						Map<String,Object> sTableDataMap = sData.get(m);
						//更新子表的数据
						String sTablerelationData = String.valueOf(sTableDataMap.get("RECID"));//子表id
						String updateSTableStatusSql = "update "+sTableName+" t set t.archive_entity_status='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[1]+"' where t.recid='"+sTablerelationData+"'";//更新子表状态的sql
						int updateSTableSum =  jdbcTemplate.update(updateSTableStatusSql);
						//随之解封该该数据
						String sTable_fhr_id = String.valueOf(sTableDataMap.get("FHR_ID"));//防止空指针，如果为空，返回null
						if(!("null".equals(sTable_fhr_id))){
							//如果封号人id不为null，则认为是封号状态，进行解封操作
							sealUpOrRemoveSeal(tName,String.valueOf(sTableDataMap.get("RECID")),"removeSeal");
						}
					}
				}else if(j == 1){
					//得到子表的数据
                    Map<String,Object> ssTableRelationMap = sTableList.get(0);
                    //更新所有子表的状态
                    String ssTableName = String.valueOf(ssTableRelationMap.get("S_TABLE_NAME"));//子表id
                    String sPColnumName = String.valueOf(ssTableRelationMap.get("M_COL_NAME"));//父表关联字段
                    String ssColnumName = String.valueOf(ssTableRelationMap.get("S_COL_NAME"));//子表关联字段

                    String sPColnumValue = String.valueOf(map.get(sPColnumName));//父表的值字段
                    //查询子表数据时添加与父记录立卷单位相同的条件，防止找到其他数据的子表数据 wl 2019-01-11
                    //查子表数据时，添加创建人相同条件 王磊2019-03-05
					List<Map<String, Object>> sData = findChildren(ssTableName,ssColnumName,sPColnumValue,String.valueOf(map.get("CRE_USER_ID")),String.valueOf(map.get("CRE_CHUSHI_ID")));
					for(int m = 0;m<sData.size();m++){
						Map<String,Object> sTableDataMap = sData.get(m);
						String ssPColnumValue = String.valueOf(sTableDataMap.get(PColnumName));//父表的值字段
						//孙子级数据
						//查询子表数据时添加与父记录立卷单位相同的条件，防止找到其他数据的子表数据 wl 2019-01-11
						//查子表数据时，添加创建人相同条件 王磊2019-03-05
						List<Map<String, Object>> ssData = findChildren(sTableName,sColnumName,ssPColnumValue,String.valueOf(map.get("CRE_USER_ID")),String.valueOf(sTableDataMap.get("CRE_CHUSHI_ID")));
						for(int n = 0;n<ssData.size();n++){
                            Map<String,Object> ssTableDataMap = ssData.get(n);
							//更新孙子表的数据
							String sTablerelationData = String.valueOf(ssTableDataMap.get("RECID"));//子表id
							String updateSTableStatusSql = "update "+sTableName+" t set t.archive_entity_status='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[1]+"' where t.recid='"+sTablerelationData+"'";//更新子表状态的sql
							int updateSTableSum =  jdbcTemplate.update(updateSTableStatusSql);

							//随之解封该该数据
							String sTable_fhr_id = String.valueOf(sTableDataMap.get("FHR_ID"));//防止空指针，如果为空，返回null
							if(!("null".equals(sTable_fhr_id))){
								//如果封号人id不为null，则认为是封号状态，进行解封操作
								sealUpOrRemoveSeal(tName,String.valueOf(sTableDataMap.get("RECID")),"removeSeal");
							}
						}
					}
				}

			}
		}
	}

    /**
     * @Auther:邴秀慧
     * @Description:组合查询
     * @Date:2018/11/26 9:49
     */
    private String complexSelect(String complexParam) {
        Map<String, String> ConnectMap = new HashMap<String, String>() {{
            put("01", " and ");
            put("02", " or ");
        }};
        Map<String, String> ConditionMap = new HashMap<String, String>() {{
            put("01", " like ");
            put("02", " > ");
            put("03", " < ");
            put("04", " = ");
        }};
        String complexSelect = "";
        JSONObject jsStr = JSONObject.parseObject(complexParam);
        JSONArray selectConnectArray = (JSONArray) jsStr.get("selectConnectArray");
        JSONArray selectNameArray = (JSONArray) jsStr.get("selectNameArray");
        JSONArray selectConditionArray = (JSONArray) jsStr.get("selectConditionArray");
        JSONArray inputValueArray = (JSONArray) jsStr.get("inputValueArray");
        for (int i = 0; i < selectNameArray.size(); i++) {
            if (selectNameArray.get(i) != null && !selectNameArray.get(i).toString().equals("")) {
                if(inputValueArray.get(i) != null && !inputValueArray.get(i).toString().equals("")){
                    complexSelect += ConnectMap.get(selectConnectArray.get(i).toString());
                    complexSelect += selectNameArray.get(i);
                    complexSelect += ConditionMap.get(selectConditionArray.get(i).toString());
                    if (ConditionMap.get(selectConditionArray.get(i).toString()).equals(" like ")) {
                        complexSelect += " '%" + inputValueArray.get(i).toString() + "%'";
                    } else {
                        complexSelect += " '" + inputValueArray.get(i).toString() + "'";
                    }
                }else{
                    if (selectNameArray.get(i).toString().equals("barcode") && inputValueArray.get(i).toString().equals("")) {//条形码
                        complexSelect += ConnectMap.get(selectConnectArray.get(i).toString());
                        complexSelect += selectNameArray.get(i);
                        complexSelect += " is null";
                    }
                }
            }
        }
        if(complexSelect.length() > 4 ){
			String startString = complexSelect.substring(0,4);
			if(startString.indexOf("or") >= 0){
				complexSelect = "";
			}
			if(startString.indexOf("and")>=0){
				complexSelect = "and (" + complexSelect.substring(4,complexSelect.length())+ ")";
			}
		}
        return complexSelect;
    }

	/**
	 *
	 * @param column
	 * @param relevancyId
	 * @param tName
	 * @param chushiId
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 判断关联字段是否存在 返回1可用不存在 返回0 数据库存在
	 */
	@Override
	public int isRepetition(String column,String relevancyId, String tName, String chushiId, String recId, String ranking) {

		List<Object> para = new ArrayList<>();

		para.add(relevancyId);
		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);
		String col = "";
		for(int i=0;i<colName.size();i++){
			if(colName.get(i).equals(column)){
				col =colName.get(i);
			}
		}

		String chushi="";
		if(StringUtils.isNotEmpty(chushiId) && tableName.indexOf("_dak")==-1) {
			//归档库不用区分录入人 王富康：20190417
			chushi=" and cre_chushi_id = ? and cre_user_id = '"+UserUtil.getCruUserId()+"'";
			para.add(chushiId);
		}

		String id = "";
		if(StringUtils.isNotEmpty(recId)) {
			String inString = CommonUtils.solveSqlInjectionOfIn(recId,para);
			id=" and recid not in("+inString+")";
		}

		//添加组卷标识状态，只要已组卷的
		String sql = "select count(1) from "+tableName+" where "+col+" = ? "+chushi+id;
		/*if(Integer.parseInt(ranking)>1){
			//一层以上，只需要判断档号是否跟已组卷的数据是否相同，游离状态的，档案调整会自动调整档号，不会重复
			sql += " and archive_flag = '"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
		}*/
		Integer queryForObject = jdbcTemplate.queryForObject(sql, Integer.class,para.toArray());
		return queryForObject;
	}

	/**
	 *
	 * @param fids
	 * @return
	 * @author 颜振兴
	 * list<Map<String,Object>>
	 * TODO 列表查询子级专用 别人用不上也不要用
	 */
	public List<Map<String,Object>> findSublevelDedicated(String fids,String tName,String column) {
		String[] split = fids.split(",");
		StringBuffer buffer = new StringBuffer("(");
		int i = 0;
		for(String id:split) {
			i++;
			if(i==split.length) {
				buffer.append("'"+id+"')");
				break;
			}
			buffer.append("'"+id+"',");
		}
		String sql = "select "+column+",cre_user_id,cre_chushi_id  from "+tName+" where recid in "+buffer.toString()+"";
		List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(sql);
		return queryForList;
	}

    /**
     * 生成条码号
     * @param ids
     * @param tableName
     * @return
     */
    @Override
    public boolean saveCode(String ids,String tableName) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tableName.equals(tableNames.get(i))){
				tName = tableNames.get(i);
			}
		}

        boolean flag = false;
        if(StringUtils.isNotEmpty(ids)){
            String[] idsArray = ids.split(",");
            for(int n = 0;n<idsArray.length;n++){
                String sql = "select t.RECID from "+tName +" t where t.RECID = ? and t.BARCODE is null ";
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,idsArray[n]);
                if(list.size()>0){
                    String barCode = ZeBraClient.getOrderIdByUUId();
                    String updateSql ="update "+tName+" t set t.BARCODE ='"+barCode+"' where t.RECID = ?";
                    int update = jdbcTemplate.update(updateSql,idsArray[n]);
                    if(update>0){
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     *
     * @param tName
     * @param fids
     * @return
     * @author 颜振兴
     * Map<String,Object>
     * TODO 根据id查数据
     */
	@Override
	public Map<String, Object> findParentData(String tName, String fids) {
		String tableName="M_TABLE_NAME";
		if(StringUtils.isBlank(fids)) {
			 tableName="S_TABLE_NAME";
		}
		String id = fids.split(",")[0];
		Map<String, Object> findDAGLByTableName = findDAGLByTableName(tName);
		List<Map<String,Object>> findById = findById(findDAGLByTableName.get(tableName).toString(), id);
		if (findById!=null&&!findById.isEmpty()) {
			return findById.get(0);
		}else {
			return null;
		}
	}

    /**
     *
     * @param tName
     * @param ids
     * @return
     * @author 颜振兴
     * int
     * TODO 批量删除
     */
	@Override
	public int dynamicDeletes(String tName, String ids,int all,int ranking) {
		String[] split = ids.split(",");
		int i = 0;
		for(String id : split) {
			int dynamicDelete = dynamicDelete(tName, id,all,ranking);
			i+=dynamicDelete;
		}
		return i;
	}

	/**
	 *  TODO 档案汇总
	 * @author 李颖洁  
	 * @date 2018年11月29日上午11:01:55
	 * @param tName 当前标签页表名
	 * @param fids 选择数据ids
	 * @return
	 */
	@Override
	public net.sf.json.JSONObject recodeGather(String tName, String fids,String categoryCode,String total,String ranking) {
		  // 定义事务隔离级别，传播行为
	        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	        //事务状态类，通过PlatformTransactionManager的getTransaction方法根据事务定义获取；获取事务状态后，Spring根据传播行为来决定如何开启事务
	        TransactionStatus transactionStatus = transactionManager.getTransaction(def);

		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		json.put("flag", "0");
		List<Map<String,Object>> errorStatusList = new ArrayList<>();//已归档和已移交
		List<Map<String,Object>> P_NotSubmmitList = new ArrayList<>();//父表为待审核的数据
		try {
			con = jdbcTemplate.getDataSource().getConnection();

			//判断是否为已移交待审核
			List<Map<String,Object>> queryForList = getSelectedData(tName,fids);//查询勾选的多行数据列表
			for(int i = 0;i < queryForList.size();i++){
				Map<String,Object> map = queryForList.get(i);
				String archive_entity_status = String.valueOf(map.get("ARCHIVE_ENTITY_STATUS"));//防止空指针，如果为空，返回null
				if(DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[0].equals(archive_entity_status)||DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2].equals(archive_entity_status)){
					//如果数据是已归档或者待提交，放到错误List中，不可汇总
					errorStatusList.add(map);
					break;
				}else{//02或者null
					String pTableSql =
							"SELECT t.*\n" +
									"  FROM tables_relation t, table_description s\n" +
									" WHERE t.m_table_name = s.table_name\n" +
									"   and t.s_table_name = '"+tName+"'";//排除父节点为门类的数据
					
					List<Map<String,Object>> pTableList = jdbcTemplate.queryForList(pTableSql);
					//-------------------------有父表
					if(pTableList.size()>0){//有父表
						//查询父表数据
						Map<String,Object> relationMap = pTableList.get(0);
						String pTableName = String.valueOf(relationMap.get("M_TABLE_NAME"));//父表表名
						String mColName = String.valueOf(relationMap.get("M_COL_NAME"));//父表字段
						String sColName = String.valueOf(relationMap.get("S_COL_NAME"));//子表字段
						String sColData = String.valueOf(map.get(sColName));//子表关联字段的值（父表的档号）
						String chushiId = String.valueOf(map.get("CRE_CHUSHI_ID"));//处室id
						String creUserId = String.valueOf(map.get("CRE_USER_ID"));//创建人id
						//查询父表记录（单位库）//查父表记录条件:关联字段、立卷单位（上边的处室id）、创建人id一致 王磊 2019-03-12
						String sql = "SELECT t.* FROM "+pTableName+" t WHERE 1=1 AND t."+mColName+" = '"+sColData+"' AND t.CRE_CHUSHI_ID = '"+chushiId+"' and t.CRE_USER_ID='"+creUserId+"' ";
						List<Map<String,Object>> pTableDataList = jdbcTemplate.queryForList(sql);
						Map<String,Object> pTableDataMap = pTableDataList.get(0);
						//父表的档案实体状态(单位库)
						String P_archive_entity_status = String.valueOf(pTableDataMap.get("ARCHIVE_ENTITY_STATUS"));
						if(DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[1].equals(P_archive_entity_status)){
							//父表状态为已移交待审核
							P_NotSubmmitList.add(map);
							break;
						}else if(DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2].equals(P_archive_entity_status)){
							//父表状态为已归档
							//查询最大值
							String idSql = "SELECT t."+mColName+" FROM "+pTableName+"_dak t WHERE t.RECID='"+pTableDataMap.get("RECID")+"'";
							List<Map<String,Object>> pTableDAKList = jdbcTemplate.queryForList(idSql);
							String newRecodeDAK = String.valueOf(pTableDAKList.get(0).get(mColName));
							String maxSql = "SELECT COUNT(1) FROM "+tName+"_dak t WHERE "+sColName+"='"+newRecodeDAK+"'";
							log.info(maxSql);
							int max = jdbcTemplate.queryForObject(maxSql, Integer.class);
							max = max+1;
							String no = String.valueOf(max);
							//查询本表的更改字段(档号字段，关联字段，序号字段，连接符，序号长度)
							Map<String,Object> recodeNoMap = findDAGLByTableNameF(tName);
							String recodeNo;
							if(recodeNoMap==null){
								recodeNo = "ARCHIVE_NO";
							}else{
								recodeNo = (String) recodeNoMap.get("M_COL_NAME");//当前表档号字段
							}
							String relationNo = sColName;//当前表关联字段
							String categoryId = findMenById(categoryCode);
							List<Map<String,Object>> dangHaoList = findDHGZbyMCode(categoryId);
							int index = dangHaoList.size()-(Integer.valueOf(total)-Integer.valueOf(ranking))-1;
							String serialNo = (String) dangHaoList.get(index).get("RULE_FIELD");//当前表序号字段
							String con = (String) dangHaoList.get(index-1).get("CONNECTOR");//当前表连接符
							String lenStr = String.valueOf(dangHaoList.get(index-1).get("NUMB_LENGTH"));
							int len ;//当前表序号长度
							if(lenStr!=null){
								len = Integer.valueOf(lenStr);
							}else{
								len = 1;
							}
							int noLen = no.length();
							if(no.length()>=len){
								no = no.substring(0,len);
							}else{
								for(int k=0;k<len-noLen;k++){
									no = "0"+no;
								}
							}
							String recodeVal = newRecodeDAK+con+no;//拼接的当前表的档号字段的值（新）
							map.put("new", recodeVal);//正式库的新档号
							map.put("newCon", newRecodeDAK);
							
							insertDataToDAK(tName,map,recodeNo,serialNo,recodeVal,no,relationNo);//更新当前表状态和正式库数据新增和修改
							
							//父表为已移交------------------判断是否有子表
							insertChildDataToDAK(tName,map,Integer.valueOf(ranking));//更新子表数据和新增正式库数据
							//提交status中绑定的事务
							//transactionManager.commit(transactionStatus);//一次事务，有且只有一次提交，放在最后了。
							json.put("flag","1");
						}
					}else{//没有父表--------------判是否有子表
						//查询本表最大值
						//查询本表的更改字段(档号字段，序号字段，序号长度)
						Map<String,Object> recodeNoMap1 = findDAGLByTableNameF(tName);
						String recodeNo;
						if(recodeNoMap1==null){
							recodeNoMap1 = findDAGLByTableName(tName);
							recodeNo = String.valueOf(recodeNoMap1.get("S_COL_NAME"));
						}else{
							recodeNo = (String) recodeNoMap1.get("M_COL_NAME");//当前表档号字段
						}
						
						String categoryId = findMenById(categoryCode);
						List<Map<String,Object>> dangHaoList = findDHGZbyMCode(categoryId);
						int index = dangHaoList.size()-(Integer.valueOf(total)-Integer.valueOf(ranking))-1;
						String serialNo = (String) dangHaoList.get(index).get("RULE_FIELD");//当前表序号字段
						int len = Integer.valueOf(String.valueOf(dangHaoList.get(index).get("NUMB_LENGTH"))) ;//当前表序号长度
						String oldRecodeVal = (String) map.get(recodeNo);//单位库的档号值
						oldRecodeVal = oldRecodeVal.substring(0, oldRecodeVal.length()-len);//截取前段部分求最大值
						String maxSql2 = "SELECT COUNT(1) FROM "+tName+"_dak WHERE substr("+recodeNo+",0,"+oldRecodeVal.length()+") = '"+oldRecodeVal+"'";
						int max = jdbcTemplate.queryForObject(maxSql2, Integer.class);
						max = max+1;
						String no = String.valueOf(max);
						int noLen = no.length();
						if(no.length()>=len){
							no = no.substring(0,len);
						}else{
							for(int k=0;k<len-noLen;k++){
								no = "0"+no;
							}
						}
						String newRecodeVal = oldRecodeVal+no;//拼接的当前表的档号字段的值（新）
						map.put("new", newRecodeVal);//正式库的新档号
						
						insertDataToDAK(tName,map,recodeNo,serialNo,newRecodeVal,no,null);//更新当前表状态和正式库数据新增和修改
						//-------------------判断是否有子表
						insertChildDataToDAK(tName,map,Integer.valueOf(ranking));//更新子表数据和新增正式库数据
						json.put("flag","1");

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				//回滚事务
				transactionManager.rollback(transactionStatus);
				json.put("flag","0");
				json.put("msg","汇总失败！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				String msg = "";
				if(errorStatusList.size()>0){
					msg += "选择的数据有不是【已提交待归档】的数据，请重新选择！\n";
				}
				if(P_NotSubmmitList.size()>0){
					msg += "上层有未汇总的档案，请从顶层开始汇总！\n";
				}

				if(!"".equals(msg)){
					json.put("flag","0");
					json.put("msg", msg);
					transactionManager.rollback(transactionStatus);
				}

				if(errorStatusList.size() == 0 && P_NotSubmmitList.size() == 0){
					//提交status中绑定的事务//事务提交应该在最后 王磊 2019-03-12 王富康2019-03-16
					transactionManager.commit(transactionStatus);
				}
				 con.close();

			} catch (Exception e) {
				e.printStackTrace();
				json.put("flag","0");
				json.put("msg","汇总失败！");
			}
		}
		
		return json;
	}

	private void insertDataToDAK(String tName,Map<String,Object> map,String recodeNo,String serialNo,String recodeVal,String no,String relationNo)throws Exception {
		try {
			//更新单位库当前数据为已归档（根据id）；设置单位归档库数据的归档日期为当前日期 王磊20190403
			String nowDay = new SimpleDateFormat("yyyyMMdd").format(new Date());
			//更新状态的sql;
			String updateSql = "update "+tName+" t set t.archive_entity_status='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"' ,t.pigeonhole_date='"+nowDay+"' where t.recid ='"+String.valueOf(map.get("RECID"))+"'";
			log.info(updateSql);
			int num = jdbcTemplate.update(updateSql);
			//更新当前数据对应文电中数据的文件状态为04：已归档
			receiveService.updateState(String.valueOf(map.get("RECID")), DAGLCommonConstants.WEN_DIAN_STATUS[3]);
			sendService.updateState(String.valueOf(map.get("RECID")), DAGLCommonConstants.WEN_DIAN_STATUS[3]);
			if(num>0){
				//插入数据到正式库（根据id）
				String insertSql = "INSERT INTO "+tName+"_dak"+" SELECT * FROM "+tName+" WHERE RECID='"+String.valueOf(map.get("RECID"))+"'";
				log.info(insertSql);
				int insertNums = jdbcTemplate.update(insertSql);
				//振兴添加：汇总时调整归档日期
				//String pigeonhole_datesql = "select count(1) from table_struct_description where table_name='"+tName+"_dak' and column_name ='pigeonhole_date'";
				//int isPigeonholeDate = jdbcTemplate.queryForObject(pigeonhole_datesql, Integer.class);
				String riqikey="";
				String riqival="";
				//if(isPigeonholeDate>0) {
					riqikey=",pigeonhole_date=";
					riqival="'"+nowDay+"' ";
				//}
				//更新正式库数据（根据id）
				String updateColSql;
				if(relationNo!=null){
					updateColSql = "UPDATE "+tName+"_dak"+" SET "+recodeNo+"='"+recodeVal+"',"+serialNo+"='"+no+"',"+relationNo+"='"+map.get("newCon")+"',ARCHIVE_ENTITY_STATUS = '"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"'"+riqikey+riqival+"  WHERE RECID='"+String.valueOf(map.get("RECID"))+"'";
				}else{
					updateColSql = "UPDATE "+tName+"_dak"+" SET "+recodeNo+"='"+recodeVal+"',"+serialNo+"='"+no+"',ARCHIVE_ENTITY_STATUS = '"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"'"+riqikey+riqival+"  WHERE RECID='"+String.valueOf(map.get("RECID"))+"'";
				}
				log.info(updateColSql);
				int updateNums = jdbcTemplate.update(updateColSql);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private void insertChildDataToDAK(String tName,Map<String,Object> map,int ranking)throws Exception{
		try {
			String sTableSql =  "	select s.*\n" +
					"  		from tables_relation s\n" +
					" 		start with s.m_table_name = '"+tName+"'\n" +
					"		connect by prior s.s_table_name = s.m_table_name\n" +
					" 	order by rownum asc";
			List<Map<String,Object>> sTableList = jdbcTemplate.queryForList(sTableSql);//得到子表及关联的信息(所有子表表名)
			if(sTableList.size()>0){
				
				List<Map<String,Object>> ids = new ArrayList<>();//保存父表更改数据的id
				ids.add(map);
				String chushiId = (String) map.get("CRE_CHUSHI_ID");//父表的处室id
				String creUserId = (String) map.get("CRE_USER_ID");//父表的创建人id
				List<Map<String,Object>> ids1 = new ArrayList<>();//保存查询到的子表的数据id
				List<Map<String,Object>> ids2 = new ArrayList<>();//保存查询到的所有子表的数据id
										
				int j = 0;
				//获取当前日期
				String nowDay = new SimpleDateFormat("yyyyMMdd").format(new Date());
				do{//目前只更新前两层的数据
					//获取子表信息
					ranking += 1;
					Map<String,Object> sTableRelationMap = sTableList.get(j);
					j++;	
					String sTableName = String.valueOf(sTableRelationMap.get("S_TABLE_NAME"));//子表表名
					String PColnumName = String.valueOf(sTableRelationMap.get("M_COL_NAME"));//父表关联字段
					String sColnumName = String.valueOf(sTableRelationMap.get("S_COL_NAME"));//子表关联字段
					String pTableName = String.valueOf(sTableRelationMap.get("M_TABLE_NAME"));//父表表名
					String pConColumn = "";
					if(ranking>2){
						Map<String,Object> pcolumnList = findDAGLByTableName(pTableName);
						//pTableName在表关联里作为子表的那条数据的子表字段
						pConColumn = String.valueOf(pcolumnList.get("S_COL_NAME"));
						
					}
					//查询子表的更改字段(档号字段)
					Map<String,Object> columnList1 = findDAGLByTableNameF(sTableName);
					String recodeNo1;
					if(columnList1==null){
						recodeNo1 = "ARCHIVE_NO";
					}else{
						recodeNo1 = String.valueOf(columnList1.get("M_COL_NAME"));//1子表档号字段
					}
					if(ids.size()>0){
						for (Map<String, Object> map2 : ids) {
							
							String PColnumValue = "";
							PColnumValue = String.valueOf(map2.get(PColnumName));//子表关联字段的值（旧）
							String newVal = String.valueOf(map2.get("new"));//子表关联字段的值（新）
							
							//查询子表的数据(处室id和关联字段，查找)
							String getSTableIds = "select t.recid,t."+recodeNo1+",t."+sColnumName+" from "+sTableName+" t where 1=1 and "+sColnumName+"='"+PColnumValue+"' AND CRE_CHUSHI_ID = '"+chushiId+"' and CRE_USER_ID='"+creUserId+"' ";
							log.info(getSTableIds);
							ids1 = jdbcTemplate.queryForList(getSTableIds);
							if(ids1.size()>0){
								
								//更新单位库子表数据为已移交
								String updateDanWeiData ="";
								//汇总时调整单位预立库中数据的归档日期为当前日期 王磊 20190403
								updateDanWeiData = "update "+sTableName+" t set t.archive_entity_status='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"' ,t.pigeonhole_date='"+nowDay+"' where 1=1 and (";
								
								//插入子表数据到正式库
								String insertSDataSql = "INSERT INTO "+sTableName+"_dak"+" SELECT * FROM "+sTableName+" WHERE 1=1 AND "+sColnumName+"='"+PColnumValue+"' AND CRE_CHUSHI_ID = '"+chushiId+"' and CRE_USER_ID='"+creUserId+"' ";
								log.info(insertSDataSql);
								int insertSNums = jdbcTemplate.update(insertSDataSql);
								
								//更新正式库子表数据
								String[] sqls = new String[ids1.size()];
								for (int m=0;m<ids1.size();m++) {
									String recodeVal1 = (String) ids1.get(m).get(recodeNo1);//子表档号值
									String conVal1 = (String) ids1.get(m).get(sColnumName);//子表关联字段值
									recodeVal1 = newVal+recodeVal1.substring(conVal1.length(),recodeVal1.length());//拼接新的档号
									//归档日期
									//String pigeonhole_datesql = "select count(1) from table_struct_description where table_name='"+tName+"_dak' and column_name ='pigeonhole_date'";
									//int isPigeonholeDate = jdbcTemplate.queryForObject(pigeonhole_datesql, Integer.class);
									String riqikey="";
									String riqival="";
									//if(isPigeonholeDate>0) {
										riqikey=",pigeonhole_date=";
										riqival="'"+nowDay+"' ";
									//}
									//
									sqls[m] = "UPDATE "+sTableName+"_dak"+" SET "+recodeNo1+"='"+recodeVal1+"',"+sColnumName+"='"+newVal+"',ARCHIVE_ENTITY_STATUS = '"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"'"+riqikey+riqival; 
									if(!StringUtils.isBlank(pConColumn)){
										sqls[m] += ","+pConColumn+" = '"+map2.get("newCon")+"'";
									}
									sqls[m] += "  WHERE RECID='"+ids1.get(m).get("RECID")+"'";
									updateDanWeiData += "t.recid = '"+ids1.get(m).get("RECID")+"' or ";
									log.info(sqls[m]);
									ids1.get(m).put("new", recodeVal1);
									ids1.get(m).put("newCon", newVal);
									ids2.add(ids1.get(m));
									//更新子表数据对应文电中数据的文件状态为：04:已归档
									receiveService.updateState(ids1.get(m).get("RECID").toString(), DAGLCommonConstants.WEN_DIAN_STATUS[3]);
									sendService.updateState(ids1.get(m).get("RECID").toString(), DAGLCommonConstants.WEN_DIAN_STATUS[3]);
								}
								updateDanWeiData = updateDanWeiData.trim().substring(0, updateDanWeiData.length()-3)+")";
								log.info(updateDanWeiData);
								int updateDanweiNums = jdbcTemplate.update(updateDanWeiData);
								
								int[] updateSNums = jdbcTemplate.batchUpdate(sqls);
								ids1.clear();
							}
						}
						//清空ids ，把ids1赋值为ids,最后清空ids1
						ids.clear();
						ids.addAll(ids2);
						ids2.clear();
						
					}
					
				}while(j<sTableList.size());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}


    /**
     *
     * @param tName
     * @param chushiid
     * @return
     * @author 颜振兴
     * int
     * TODO 获取当前处室档案数量
     * @param hao
     * @param columnValue
     * @param column
     */
	@Override
	public int findThisTotal(String tName, String chushiid, String column, String columnValue, String hao) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);
		String col = "";
		String haoCol = "";
		for(int i=0;i<colName.size();i++){
			if(colName.get(i).equals(column)){
				col =colName.get(i);
			}
			if(colName.get(i).equals(hao)){
				haoCol = colName.get(i);
			}
		}


		String sql = "select max("+haoCol+") from "+tableName+" where cre_chushi_id = ? and "+col+" = ? ";
		Integer integer =jdbcTemplate.queryForObject(sql, Integer.class,chushiid,columnValue);
		if(integer==null) {
			integer=0;
		}
		return integer;
	}

	/**
	 * 
	 * @param tName
	 * @param chushiid
	 * @param column
	 * @param columnValue
	 * @return
	 * @author 颜振兴
	 * int 删除子表数据
	 * TODO
	 */
	public int deleteSall(String tName,String chushiid,String column, String columnValue) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);
		String col = "";
		for(int i=0;i<colName.size();i++){
			if(colName.get(i).equals(column)){
				col =colName.get(i);
			}
		}

		String sql = "delete from "+tableName+" where cre_user_id = ? "+"and "+col+" = ? and archive_flag ='"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
		int update = jdbcTemplate.update(sql,chushiid,columnValue);
		return update;
	}
	
	/**
	 * 
	 * @param guanlian
	 * @param guanlianzhi
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 更加关联字段查数据
	 */
	public List<Map<String,Object>> findListByguanlian(String guanlian,String guanlianzhi,String tName, String userId, String chushiid){

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);
		String col = "";
		for(int i=0;i<colName.size();i++){
			if(colName.get(i).equals(guanlian)){
				col =colName.get(i);
			}
		}

		String sql = " select * from "+tableName+" where "+col+" = ? and cre_user_id = ? and cre_chushi_id = ? ";
		return jdbcTemplate.queryForList(sql,guanlianzhi,userId,chushiid);
	}

	public Map<String,String> findAllConnectedColumns(){
		Map<String,String> map = new HashMap<String,String>();
		return map;
		
	}


	/**
	 * 根据表名，查询表字段及其在页面所占宽度
	 * @author 王磊
	 * @Date 2018年12月24日 下午4:13:49
	 * @param tName
	 * @return
	 */
	@Override
	public List<Map<String, String>> findColumnWidth(String tName) {
		List<Object> para = new ArrayList<>();
		String table_name = "";
		if (StringUtils.isNotEmpty(tName)) {
			table_name = " and t.column_visible='T' and t.table_name = ? order by t.column_order asc ";
			para.add(tName);
		}
		String sql = "select t.column_style from table_struct_description t where 1=1 " + table_name;
		//List<String> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(String.class));
		List<String> query = jdbcTemplate.queryForList(sql, String.class,para.toArray());
		List<Map<String, String>> list = new ArrayList<>();
		for(String string:query) {
			Map<String, String> parseObject = com.alibaba.fastjson.JSONObject.parseObject(string,
					new TypeReference<Map<String, String>>() {
					});
			list.add(parseObject);
		}
		return list; 
	}

    /**
     *
     *  开发人：颜振兴
     *  时间 2018年12月24日
     *  TODO 修改新增页面的样式
     *  @param tName
     *  @param list
     *  @return
     *  editStyle
     */
	@Override
	public int editStyle(String tName, String list) {
		List<HashMap> parseArray = JSONObject.parseArray(list, HashMap.class);
		int i = 0,j=0;
		int orderNum=2;

		for(Map<String, String> map :parseArray) {
			List<Object> para = new ArrayList<>();
			para.add("{'key':'"+ map.get("key")+"','value':'"+map.get("value")+"'}");
			para.add(orderNum);
			para.add(tName);
			para.add(map.get("key"));
			List<Object> paraDak = new ArrayList<>();
			paraDak.add("{'key':'"+ map.get("key")+"','value':'"+map.get("value")+"'}");
			paraDak.add(orderNum);
			paraDak.add(tName+"_dak");
			paraDak.add(map.get("key"));
			String sql = "update table_struct_description set  COLUMN_STYLE = ?,COLUMN_ORDER = ?  where TABLE_NAME=? and COLUMN_NAME = ? ";
			String sqldak = "update table_struct_description set  COLUMN_STYLE = ?,COLUMN_ORDER = ?  where TABLE_NAME=? and COLUMN_NAME = ? ";
			int update = jdbcTemplate.update(sql,para.toArray());
			int updatedak = jdbcTemplate.update(sqldak,paraDak.toArray());
			i+=update;
			j+=updatedak;
			orderNum++;
		}
		return i+j;
	}


	/**
	 * 多及查询：根据门类代号、题名和立卷单位名称查询各级档案数据
	 * @author 王磊
	 * @Date 2019年2月13日 下午4:56:22
	 * @param cateNo
	 * @param mainTitle
	 * @param ljdw 立卷单位code
	 * @param isDak 是否归档库：0不是，1是
	 * @param personType 人员类型：0录入人，查询自己创建的；1立卷单位管理员，查询本立卷单位的；2档案管理员，查询所有
	 * @return
	 */
	@Override
	public List<MultiLevelQuery> multiQuery(String cateNo, String mainTitle, String ljdw,String isDak,String personType) {
		List<MultiLevelQuery> list = new ArrayList<MultiLevelQuery>();
		List<Object> para = new ArrayList<>();

		//当前如果是立卷单位管理员，需要获取其所在的立卷单位名称和code值
		String nowLjdwCode = "";
		if(null !=personType && "1".equals(personType)){
			nowLjdwCode="ljdwCode from config";
		}
		//根据门类代号和是否档案库，查询门类下的表名及标签页名字
		String selTableName = "";
		if("0".equals(isDak)){//不是归档库，查询不带_dak的业务表
			selTableName = "select t.s_table_name,td.table_chn_name from tables_relation t left join table_description td on t.s_table_name=td.table_name where t.s_table_name not like '%dak'" 
				      +" start with t.m_table_name=? connect by prior t.s_table_name=t.m_table_name";
			para.add(cateNo);
		}else{
			selTableName = "select t.s_table_name,td.table_chn_name from tables_relation t left join table_description td on t.s_table_name=td.table_name where t.s_table_name  like '%dak'" 
				      +" start with t.m_table_name=? connect by prior t.s_table_name=t.m_table_name";
			para.add(cateNo);
		}
		List<Map<String, Object>> tableList = jdbcTemplate.queryForList(selTableName,para.toArray());
		//根据表名查询档号字段
		for(Map<String, Object> map : tableList){
			List<Object> paras = new ArrayList<>();
			StringBuffer selLists = new StringBuffer();
			String selDh = "select tr.m_col_name from tables_relation tr where tr.m_table_name='"+map.get("s_table_name").toString()+"'";
			String Dh="";
			try {
				Dh = jdbcTemplate.queryForObject(selDh, String.class);
			} catch (Exception e) {
				log.info("当前表在表关联里不存在档号，默认取archive_no作为档号，执行错误信息："+e.getMessage());
				if(null == Dh || "".equals(Dh)){//最后一层，默认档号为archive_no
					Dh = "archive_no";
				}
			}
			map.put("Dh", Dh);
			//依次从各级档案里查询出 公共字段（主键recid、题名maintitle、立卷单位basefolder_unit）+档号字段
			if(null !=personType && "0".equals(personType)){//录入人自己查自己维护的档案
				selLists.append("select nvl(maintitle,'null') as maintitle,nvl(basefolder_unit,'null') as basefolder_unit,"+"nvl("+Dh+",'null') as "+Dh+",recid,nvl(borrow_status,'0') as borrow_status from "+map.get("s_table_name").toString()+" where CRE_USER_ID='"+UserUtil.getCruUserId()+"'");
			}else if(null !=personType && "1".equals(personType)){//立卷单位管理员查询本立卷单位数据
				selLists.append("select nvl(maintitle,'null') as maintitle,nvl(basefolder_unit,'null') as basefolder_unit,,"+"nvl("+Dh+",'null') as "+Dh+",recid,nvl(borrow_status,'0') as borrow_status from "+map.get("s_table_name").toString()+" where CRE_CHUSHI_ID='"+nowLjdwCode+"'");
			}else{//档案管理员查询所有
				selLists.append("select nvl(maintitle,'null') as maintitle,nvl(basefolder_unit,'null') as basefolder_unit,"+"nvl("+Dh+",'null') as "+Dh+",recid,nvl(borrow_status,'0') as borrow_status from "+map.get("s_table_name").toString()+" where 1=1 ");
			}
			
			//添加查询条件
			if(StringUtils.isNotEmpty(mainTitle)){
				selLists.append(" and maintitle like ? ");
				paras.add("%"+mainTitle+"%");
			}
			if(StringUtils.isNotEmpty(ljdw)){
				selLists.append(" and CRE_CHUSHI_ID =?");
				paras.add(ljdw);
			}
			//本层数据按照档号降序排列
			selLists.append(" order by "+Dh+" desc");
			List<Map<String,Object>> alist = jdbcTemplate.queryForList(selLists.toString(),paras.toArray());
			MultiLevelQuery aMultiLevelQuery = new MultiLevelQuery();
			//依次封装列表数据、表中文名、表英文名
			aMultiLevelQuery.setDataList(alist);
			aMultiLevelQuery.setLevelName(map.get("table_chn_name").toString());
			aMultiLevelQuery.setTableName(map.get("s_table_name").toString());
			list.add(aMultiLevelQuery);
		}
		
		return list;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 批量添加表数据
	 * @Date 2019/2/15 15:51
	 * @Param [jsonStr, tName, addCount]
	 * @return int
	 **/
	@Override
	public int dynamicPlAdd(String jsonStr,String tName,String addCount, String categoryCode, String fids, String all, String ranking){

		int countofadd = 0;
		int addcount = Integer.parseInt(addCount);
		List<PartyNumRule> list = null;
		List<DaglCategoryTable> daglCategoryTables  = preferencesService.getCategoryListData(categoryCode);

		if(daglCategoryTables.size()>0){
			//获取档号规则
			list = preferencesService.getRule(daglCategoryTables.get(0).getId(),"");
		}



		for(int i=0;i<addcount;i++){
			jsonStr = jsonStr.replaceAll("“","\"");
			jsonStr = jsonStr.replaceAll("”","\"");
			Map<String, String> parseObject = com.alibaba.fastjson.JSONObject.parseObject(jsonStr,
					new TypeReference<Map<String, String>>() {
					});

            //新的案卷级档号
            String newFolderNo = "";
            //新的文件级档号
			String newArchiveNo = "";
            //新的案卷号
			String newYearFolderNo = "";
            //新的字符串
			String newJsonStr = "";

			for(int j=0;j<list.size();j++){

			    //档号项的字段名
				String RuleField = list.get(j).getRuleField();
				String Connector = "";
				String NumbLength = list.get(j).getNumbLength();
				if(!StringUtils.isBlank(list.get(j).getConnector())){//防止连接符为空
					Connector = list.get(j).getConnector();
				}

				if("year_folder_no".equals(list.get(j).getRuleField())){
					//取到案卷号
					String year_folder_no = parseObject.get("year_folder_no");
					int yearFolderNo = Integer.parseInt(year_folder_no)+i;
					newYearFolderNo = String.format("%0"+NumbLength+"d", yearFolderNo);
					newArchiveNo += newYearFolderNo+Connector;
				}else if("fonds_no".equals(list.get(j).getRuleField())){
					//全宗号存的code，直接取值就好了
					newArchiveNo += parseObject.get(RuleField)+Connector;
                    newFolderNo += parseObject.get(RuleField)+Connector;
				}else if("leibiehao".equals(list.get(j).getRuleField())){
					//类别号从门类表中取值

					String categoryName = parseObject.get(RuleField);
					String generalArchiveCode = parseObject.get("fonds_no");
					String leibiehao = "";
					try{
						String leibiehaoSql = "SELECT t.category_code FROM dagl_category_table t WHERE t.category_name = ? and t.general_archive_code = ? ";
						leibiehao = jdbcTemplate.queryForObject(leibiehaoSql,String.class,categoryName,generalArchiveCode);
					}catch (Exception e){
						//防止查不到数据，或者查出多行数据报错，导致异常
					}

					newArchiveNo += leibiehao+Connector;
                    newFolderNo += leibiehao+Connector;
				}else{
					try {
						//查询字段对应的字典值
						String select = "select t.COLUMN_SELECT_NO from  table_struct_description t where table_name = ? and column_name = ? ";
						String queryForObject = jdbcTemplate.queryForObject(select, String.class,tName,RuleField);
						if(StringUtils.isNotEmpty(queryForObject)){
							//查询字典值
							List<DataDictionary> dicts = dataDictionaryService.getListByMark(queryForObject,"1");
							if(dicts.size()>0){//存在取code
								for(int m=0;m<dicts.size();m++){
									if( parseObject.get(RuleField).equals(dicts.get(m).getName())){
										newArchiveNo += dicts.get(m).getCode()+Connector;
                                        newFolderNo += dicts.get(m).getCode()+Connector;
									}
								}
							}else{//不存在正常存放
								newArchiveNo += parseObject.get(RuleField)+Connector;
                                newFolderNo += parseObject.get(RuleField)+Connector;
							}
						}else{
							newArchiveNo += parseObject.get(RuleField)+Connector;
                            newFolderNo += parseObject.get(RuleField)+Connector;
						}


					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
            if(!"".equals(newFolderNo)){
                newFolderNo = newFolderNo.substring(0,newFolderNo.length()-1);
            }
			parseObject.put("archive_no",newArchiveNo);
			parseObject.put("year_folder_no",newYearFolderNo);
            parseObject.put("folder_no",newFolderNo);

			ObjectMapper json = new ObjectMapper();

			try {
					 //把map对象转成json格式的String字符串
				newJsonStr = json.writeValueAsString(parseObject);
				 } catch (JsonGenerationException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 } catch (JsonMappingException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			countofadd += (int)dynamicAdd(newJsonStr, tName,fids,all, ranking).get("flag");
		}
		return countofadd;
	}

	
	@Override
	public int tabUser(String tName,String id, String userId) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		String sql = "update "+tableName+" set cre_user_id = ? where recid = ? ";
		int update = jdbcTemplate.update(sql,userId,id);
		return update;
	}
	
	/**
	 * 档案借阅流程审批通过后调整对应档案的借阅状态
	 * @author 王磊
	 * @Date 2019年2月16日 下午3:25:31
	 * @param recid
	 * @param tableName
	 * @return {"anjuan":案卷数,"fenjuan":分卷数,"juannei":卷内数,"flag":true或false，true表示成功}，门类层级不同，这三个可能有多种组合
	 */
	@Override
	public net.sf.json.JSONObject borrowDangAnToChangeStatus(String recid, String tableName) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tableName.equals(tableNames.get(i))){
				tName = tableNames.get(i);
			}
		}

		//json内容格式：{"anjuan":案卷数,"fenjuan":分卷数,"juannei":卷内数}，门类层级不同，这三个可能有多种组合
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		try {
			con = jdbcTemplate.getDataSource().getConnection();
        	con.setAutoCommit(false);
        	//1.1根据表名获取跟子表的关联数据
    		String selTableRelation = "select t.* from tables_relation t "
    		+"start with t.m_table_name = ? connect by prior t.s_table_name=t.m_table_name";
    		List<Map<String,Object>> tableRelationList = jdbcTemplate.queryForList(selTableRelation,tableName);
    		//1.2调整当前档案及子表对应的借阅状态
    		String updateSelf = "update "+tName+" t set t.borrow_status='2' where t.recid=?";
    		//jdbcTemplate.update(updateSelf);
    		PreparedStatement updateSales =  con.prepareStatement(updateSelf);
			updateSales.setString(1,recid);
			updateSales.executeUpdate();

    		//从表描述中查询当前表的类别，是案卷、分卷还是卷内
    		String selType = "select note from table_description t where t.table_name = ?";
    		//当前只有一条数据
    		json.put(jdbcTemplate.queryForObject(selType, String.class,tableName), "1");
    		String selectAll="";
    		for(int i=0;i<tableRelationList.size();i++){
    			Map<String,Object> map = tableRelationList.get(i);
    			//更新子表
    			String pTable = map.get("M_TABLE_NAME").toString();
    			String pColumn = map.get("M_COL_NAME").toString();
    			String sTable = map.get("S_TABLE_NAME").toString();
    			String sColumn = map.get("S_COL_NAME").toString();
    			String selectRecid="";
    			String updateSql = "";
    			if(selectAll.length()==0){
    				selectRecid = " select "+sTable+".recid from "+sTable+" "+sTable+","+pTable+" "+pTable+" where "+sTable+"."+sColumn+"="+pTable+"."+pColumn+" and "+sTable+".CRE_USER_ID="+pTable+".CRE_USER_ID and "+sTable+".CRE_CHUSHI_ID="+pTable+".CRE_CHUSHI_ID and "+pTable+".recid='"+recid+"' and "+sTable+".archive_flag= '"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
    			}else{
    				selectRecid=" select "+sTable+".recid from "+sTable+" "+sTable+",("+selectAll+") "+pTable+" where "+sTable+"."+sColumn+"="+pTable+"."+pColumn+" and "+sTable+".CRE_USER_ID="+pTable+".CRE_USER_ID and "+sTable+".CRE_CHUSHI_ID="+pTable+".CRE_CHUSHI_ID and "+sTable+".archive_flag= '"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
    			}
    			selectAll = selectRecid.replaceFirst(".recid", ".*");
    			updateSql= "update "+sTable+" set borrow_status='2' where recid in "+" ("+selectRecid+")";
    			log.info("更新子表"+sTable+"："+updateSql);
    			//查询子表的类别，是案卷、分卷还是卷内
    			String selSubType = "select note from table_description t where t.table_name='"+sTable+"'";
    			String selSubNum = "select count(recid) from "+sTable+" where recid in "+" ("+selectRecid+")";
    			json.put(jdbcTemplate.queryForObject(selSubType, String.class), jdbcTemplate.queryForObject(selSubNum, String.class));
    			//jdbcTemplate.execute(updateSql);
    			con.prepareStatement(updateSql).execute();
    		}
    		
    		//2.1根据表名获取父表的关联数据
    		String selTableRelationP = "select t.* from tables_relation t,table_description td where t.m_table_name=td.table_name start with t.s_table_name = ? connect by prior t.m_table_name=t.s_table_name";
    		List<Map<String,Object>> tableRelationPList = jdbcTemplate.queryForList(selTableRelationP,tableName);
    		//2.2调整父表对应的借阅状态
    		String tempRecid = recid;//设置默认值为参数传过来当前表的recid
    		//设置当前数据状态为借出
    		String nowBorrow = "2";
    		for(Map<String,Object> map : tableRelationPList){
    			//查询父表数据
    			String pTable = map.get("M_TABLE_NAME").toString();
    			String pColumn = map.get("M_COL_NAME").toString();
    			String sTable = map.get("S_TABLE_NAME").toString();
    			String sColumn = map.get("S_COL_NAME").toString();
    			String selP = "select "+pTable+".* from "+pTable+" "+pTable+","+sTable+" "+sTable+" where "+pTable+"."+pColumn+"="+sTable+"."+sColumn+" and "+sTable+".CRE_USER_ID="+pTable+".CRE_USER_ID and "+sTable+".CRE_CHUSHI_ID="+pTable+".CRE_CHUSHI_ID and "+sTable+".recid = ? and "+sTable+".archive_flag= '"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
    			log.info("查询父表数据sql:"+selP);
    			Map<String,Object> pData = jdbcTemplate.queryForMap(selP,tempRecid);
    			//记录当前记录
    			String subRecid = tempRecid;
    			//更新当前recid为父表记录recid，为下次查找父表的父表记录做准备
    			tempRecid=pData.get("RECID").toString();
    			String updatePStatus = "";
    			if("2".equals(nowBorrow)){
    				//查询当前父表数据的子表数据（不包含当前子表数据），根据子表借阅状态设置父表借阅状态:如果子表数据存在未借出或者部分借出，那么设置父表借阅状态为部分借出；
        			String selSData = " select count("+sTable+".recid) as countNum from "+sTable+" "+sTable+","+pTable+" "+pTable+" where "+sTable+"."+sColumn+"="+pTable+"."+pColumn+" and "+sTable+".CRE_USER_ID="+pTable+".CRE_USER_ID and "+sTable+".CRE_CHUSHI_ID="+pTable+".CRE_CHUSHI_ID and "+pTable+".recid=? and ("+sTable+".BORROW_STATUS in('0','1') or "+sTable+".BORROW_STATUS is null) ";
        			selSData = selSData+" and "+sTable+".recid <> ? ";
        			log.info("查询父表所有子表数据的未借出或者部分借出数量sql:"+selSData);
        			Map<String,Object> wjcOrBfjc = jdbcTemplate.queryForMap(selSData,tempRecid,subRecid);
        			int wjcOrBfjcNum = 0;
        			if(null != wjcOrBfjc.get("countNum")){
        				wjcOrBfjcNum = Integer.parseInt(wjcOrBfjc.get("countNum").toString());
        			}
        			if(0==wjcOrBfjcNum){
        				//子表数据不存在未借出或者部分借出，设置父表数据借阅状态为借出
        				updatePStatus = " update "+pTable+" "+pTable+" set "+pTable+".BORROW_STATUS='2' where "+pTable+".recid=?";
        				//更新当前父表数据的状态，为下次循环做准备
        				nowBorrow = "2";
        			}else{
        				//子表数据存在未借出或者部分借出，设置父表数据借阅状态为部分借出
        				updatePStatus = "update "+pTable+" "+pTable+" set "+pTable+".BORROW_STATUS='1' where "+pTable+".recid=?";
        				//更新当前父表数据的状态，为下次循环做准备
        				nowBorrow = "1";
        			}
    			}else if("1".equals(nowBorrow)){
    				//子表数据存在未借出或者部分借出，设置父表数据借阅状态为部分借出
    				updatePStatus = "update "+pTable+" "+pTable+" set "+pTable+".BORROW_STATUS='1' where "+pTable+".recid=?";
    				//更新当前父表数据的状态，为下次循环做准备
    				nowBorrow = "1";
    			}
    			//jdbcTemplate.execute(updatePStatus);
    			//con.prepareStatement(updatePStatus).execute();
				PreparedStatement updateSaless =  con.prepareStatement(updatePStatus);
				updateSaless.setString(1,tempRecid);
				updateSaless.executeUpdate();
    		}
    		//int nn = 1/0;测试事务
    		con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
				//异常之后设置标识
				json.put("flag", "false");
				return json;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		//正常执行设置标识
		json.put("flag", "true");
		return json;
	}

	/**
	 * 
	 * TODO 归还档案时，调整当前档案及子父表数据的借阅状态
	 * @author 王磊
	 * @Date 2019年2月18日 下午6:01:58
	 * @param recid
	 * @param tableName
	 * @return {"flag":true或者false，true表示成功}
	 */
	@Override
	public net.sf.json.JSONObject returnDangAnToChangeStatus(String recid, String tableName) {
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tableName.equals(tableNames.get(i))){
				tName = tableNames.get(i);
			}
		}
		try {
			con = jdbcTemplate.getDataSource().getConnection();
        	con.setAutoCommit(false);
        	//1.1根据表名获取跟子表的关联数据
    		String selTableRelation = "select t.* from tables_relation t "
    		+"start with t.m_table_name=? connect by prior t.s_table_name=t.m_table_name";
    		List<Map<String,Object>> tableRelationList = jdbcTemplate.queryForList(selTableRelation,tableName);
    		//1.2调整当前档案及子表对应的借阅状态
    		String updateSelf = "update "+tName+" t set t.borrow_status='0' where t.recid = ? ";
    		//jdbcTemplate.update(updateSelf);
    		//con.prepareStatement(updateSelf).execute();
			PreparedStatement updateSales =  con.prepareStatement(updateSelf);
			updateSales.setString(1,recid);
			updateSales.executeUpdate();
    		String selectAll="";
    		for(int i=0;i<tableRelationList.size();i++){

    			List<Object> para = new ArrayList<>();

    			Map<String,Object> map = tableRelationList.get(i);
    			//更新子表
    			String pTable = map.get("M_TABLE_NAME").toString();
    			String pColumn = map.get("M_COL_NAME").toString();
    			String sTable = map.get("S_TABLE_NAME").toString();
    			String sColumn = map.get("S_COL_NAME").toString();
    			String selectRecid="";
    			String updateSql = "";
    			if(selectAll.length()==0){
    				selectRecid = " select "+sTable+".recid from "+sTable+" "+sTable+","+pTable+" "+pTable+" where "+sTable+"."+sColumn+"="+pTable+"."+pColumn+" and "+sTable+".CRE_USER_ID="+pTable+".CRE_USER_ID and "+sTable+".CRE_CHUSHI_ID="+pTable+".CRE_CHUSHI_ID and "+pTable+".recid = ? ";
					para.add(recid);
    			}else{
    				selectRecid=" select "+sTable+".recid from "+sTable+" "+sTable+",("+selectAll+") "+pTable+" where "+sTable+"."+sColumn+"="+pTable+"."+pColumn+" and "+sTable+".CRE_USER_ID="+pTable+".CRE_USER_ID and "+sTable+".CRE_CHUSHI_ID="+pTable+".CRE_CHUSHI_ID ";
    			}
    			selectAll = selectRecid.replaceFirst(".recid", ".*");
    			updateSql= "update "+sTable+" set borrow_status='0' where recid in "+" ("+selectRecid+")";
    			log.info("更新子表"+sTable+"："+updateSql);
    			//jdbcTemplate.execute(updateSql);
    			//con.prepareStatement(updateSql).execute();
				PreparedStatement updateSaless =  con.prepareStatement(updateSql);
				updateSaless.setString(1,recid);
				updateSaless.executeUpdate();

    		}
    		//2.1根据表名获取父表的关联数据
    		String selTableRelationP = "select t.* from tables_relation t,table_description td where t.m_table_name=td.table_name start with t.s_table_name=? connect by prior t.m_table_name=t.s_table_name";
    		List<Map<String,Object>> tableRelationPList = jdbcTemplate.queryForList(selTableRelationP,tableName);
    		//2.2调整父表对应的借阅状态
    		String tempRecid = recid;//设置默认值为参数传过来当前表的recid
    		//设置当前数据状态为未借出
    		String nowBorrow = "0";
    		for(Map<String,Object> map : tableRelationPList){
    			//查询父表数据
    			String pTable = map.get("M_TABLE_NAME").toString();
    			String pColumn = map.get("M_COL_NAME").toString();
    			String sTable = map.get("S_TABLE_NAME").toString();
    			String sColumn = map.get("S_COL_NAME").toString();
    			String selP = "select "+pTable+".* from "+pTable+" "+pTable+","+sTable+" "+sTable+" where "+pTable+"."+pColumn+"="+sTable+"."+sColumn+" and "+sTable+".CRE_USER_ID="+pTable+".CRE_USER_ID and "+sTable+".CRE_CHUSHI_ID="+pTable+".CRE_CHUSHI_ID and "+sTable+".recid = ? ";
    			log.info("查询父表数据sql:"+selP);
    			Map<String,Object> pData = jdbcTemplate.queryForMap(selP,tempRecid);
    			//记录当前子表记录
    			String subRecid = tempRecid;
    			//更新当前recid为父表记录recid，为下次查找父表的父表记录做准备
    			tempRecid=pData.get("RECID").toString();
    			String updatePStatus = "";
    			if("0".equals(nowBorrow)){
    				//查询当前父表数据的子表数据，根据子表借阅状态设置父表借阅状态:如果子表数据存在借出或者部分借出，那么设置父表借阅状态为部分借出；
        			String selSData = " select count("+sTable+".recid) as countNum from "+sTable+" "+sTable+","+pTable+" "+pTable+" where "+sTable+"."+sColumn+"="+pTable+"."+pColumn+" and "+sTable+".CRE_USER_ID="+pTable+".CRE_USER_ID and "+sTable+".CRE_CHUSHI_ID="+pTable+".CRE_CHUSHI_ID and "+pTable+".recid = ? and ("+sTable+".BORROW_STATUS in('1','2')) ";
        			selSData = selSData+" and "+sTable+".recid <> ?";
        			log.info("查询父表所有子表数据的借出或者部分借出数量sql:"+selSData);
        			Map<String,Object> wjcOrBfjc = jdbcTemplate.queryForMap(selSData,tempRecid,subRecid);
        			int wjcOrBfjcNum = 0;
        			if(null != wjcOrBfjc.get("countNum")){
        				wjcOrBfjcNum = Integer.parseInt(wjcOrBfjc.get("countNum").toString());
        			}
        			if(0==wjcOrBfjcNum){
        				//子表数据不存在借出或者部分借出，设置父表数据借阅状态为未借出
        				updatePStatus = " update "+pTable+" "+pTable+" set "+pTable+".BORROW_STATUS='0' where "+pTable+".recid=?";
        				//更新当前父表数据的状态，为下次循环做准备
        				nowBorrow = "0";
        			}else{
        				//子表数据存在借出或者部分借出，设置父表数据借阅状态为部分借出
        				updatePStatus = "update "+pTable+" "+pTable+" set "+pTable+".BORROW_STATUS='1' where "+pTable+".recid=?";
        				//更新当前父表数据的状态，为下次循环做准备
        				nowBorrow = "1";
        			}
    			}else if("1".equals(nowBorrow)){
    				//子表数据存在借出或者部分借出，设置父表数据借阅状态为部分借出
    				updatePStatus = "update "+pTable+" "+pTable+" set "+pTable+".BORROW_STATUS='1' where "+pTable+".recid=?";
    				//更新当前父表数据的状态，为下次循环做准备
    				nowBorrow = "1";
    			}
    			//jdbcTemplate.execute(updatePStatus);
    			//con.prepareStatement(updatePStatus).execute();
				PreparedStatement updateSaless =  con.prepareStatement(updatePStatus);
				updateSaless.setString(1,tempRecid);
				updateSaless.executeUpdate();
    		}
    		//int nn = 1/0;测试事务
    		con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
				//异常之后设置标识
				json.put("flag", "false");
				return json;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//正常执行之后设置标识
		json.put("flag", "true");
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 封号
	 * @Date 2019/2/21 14:34
	 * @Param [tName, fids]
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 **/
	@Override
	public List<Map<String, Object>> sealUpOrRemoveSeal(String tName, String fids, String type) {

		List<Map<String,Object>> queryForList = getSelectedData(tName,fids);//查询勾选的多行数据列表
		List<Map<String,Object>> successList =new ArrayList<>();//成功的数据
		List<Map<String,Object>> alreadySubmmitList = new ArrayList<>();//已经提交待审核的数据

		String fhrId = "";//封号人id
		String fhrName = "";//封号人name
		String fhTime = "";//封号时间

		if("sealUp".equals(type)){
			fhrId = UserUtil.getCruUserId();//封号人id
			fhrName = UserUtil.getCruUserName();//封号人name
			fhTime = DateUtil.COMMON_FULL.getDateText(new Date());//封号时间
		}

		for(int i = 0;i < queryForList.size();i++){
			//遍历勾选的数据
			//首先查看勾选的数据是否是已移交待审核状态
			Map<String,Object> map = queryForList.get(i);
			String fhr_id = String.valueOf(map.get("FHR_ID"));//防止空指针，如果为空，返回null
			if(!("null".equals(fhr_id)) && "sealUp".equals(type)){
				//如果数据已经封号
				alreadySubmmitList.add(map);
				continue;
			}else if("null".equals(fhr_id) && "removeSeal".equals(type)){
				//如果数据已经封号
				alreadySubmmitList.add(map);
				continue;
			}else{//01或者null

				String sql = "update "+tName+" t set t.fhr_id = '"+fhrId+"'," +
						"							 t.fhr_name = '"+fhrName+"', " +
						"							 t.fh_time = '"+fhTime+"' " +
						"						where t.recid ='"+String.valueOf(map.get("RECID"))+"'";//更封号信息的sql

				int updateSum =  jdbcTemplate.update(sql);
				successList.add(map);
			}
		}
		Map<String,Object> zongMap = new HashMap<String,Object>();
		zongMap.put("successList",successList);
		zongMap.put("alreadySubmmitList",alreadySubmmitList);
		List<Map<String,Object>> zongList = new ArrayList();
		zongList.add(zongMap);

		return zongList;
	}

	/**
	 * 档案查询新增数据
	 * 颜振兴
	 * @param id recid
	 * @param tName 表名
	 * @param areYouOk 是否最后一层，true是，false不是
	 * @param all 当前门类共有几层
	 * @return
	 */
	@Override
	public Map<String, Object> adddd(String id, String tName,boolean areYouOk,String all) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

	String areYouOks = "ARCHIVE_NO";
	//根据recid和表名查出数据
	String sql = "select * from "+tableName+" where recid = ?";
	List<Map<String, Object>> queryForMap = jdbcTemplate.queryForList(sql,id);
	//判断是否最后一层，如果不是最后一层，则根据表名查询关联字段
	if(!areYouOk) {
		Map<String, Object> findDAGLByTableNameF = findDAGLByTableNameF(tName);
		areYouOks=(String) findDAGLByTableNameF.get("M_COL_NAME");
	}else if(!StringUtils.isBlank(all) && "1".equals(all)){
		//如果当前总层数为1，那用表名去查询门类和表名关联的那条数据
		Map<String, Object> findDAGLByTableNameF = findDAGLByTableName(tName);
		areYouOks=(String) findDAGLByTableNameF.get("S_COL_NAME");
	}
	Map<String, Object> map = new HashMap<>();
	map.put("recid", queryForMap.get(0).get("RECID"));
	map.put("main_title", queryForMap.get(0).get("MAINTITLE"));
	map.put("basefolder_unit", queryForMap.get(0).get("BASEFOLDER_UNIT"));
	map.put("ljdw_mark", queryForMap.get(0).get("CRE_CHUSHI_ID"));
	map.put("archive_no", queryForMap.get(0).get(areYouOks.toUpperCase()));
	return map;
	}
	
	/**
	 * 
	 * TODO 分页查询第一层表数据
	 * @author 王磊
	 * @Date 2019年4月16日 上午10:06:18
	 * @param pageImpl
	 * @param pageable
	 * @param tableName 表名
	 * @param selectIds 勾选的一条数据的id，根绝这个id查询跟这条数据的档号，截取掉最后一位，根据档号查询相同类型的数据
	 * @param liJuanDanWei 立卷单位名称
	 * @param luRuRen 录入人
	 * @param mainTitle 题名
	 * @param dangHao 档号
	 * @return
	 */
	@Override
	public PageImpl getFirstLevel(PageImpl pageImpl,Pageable pageable,String tableName,String selectIds, String all, String liJuanDanWei,String luRuRen,String mainTitle,String dangHao){

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tableName.equals(tableNames.get(i))){
				tName = tableNames.get(i);
			}
		}


		List<Object> para = new ArrayList<>();
		//1.根据表名查询档号字段(当前表作为父表的记录)
		Map<String, Object> dangHaoColMap = findDAGLByTableNameF(tName);
		String dangHaoCol = "";
		if(null != dangHaoColMap){
			//当前门类是多层结构，更新档号字段
			dangHaoCol = dangHaoColMap.get("M_COL_NAME").toString();
		}else{
			//单层结构的档案获取方式
			dangHaoColMap = findDAGLByTableName(tName);
			dangHaoCol = dangHaoColMap.get("S_COL_NAME").toString();
		}

		//根据表名和id得到勾选的数据
		List<Map<String,Object>> findById = findById(tName, selectIds);
		//得到档号
		String danghao = findById.get(0).get(dangHaoCol).toString();
		//得到该数据的立卷单位，如果是单位预立库的话，需要根据该条件进行过滤
		String ljdw = findById.get(0).get("cre_chushi_id").toString();

		//1判断newDangHao长度是否正确
		//1.2根据层数获取当前档号应该的长度
		String menLeiCode = findDAGLByTableName(tName).get("M_TABLE_NAME").toString();
		String selDhgz = "select * from dagl_party_num_rule where category_id in (select dct.id from dagl_category_table dct where dct.category_code='"+menLeiCode+"') order by order_num asc";
		List<Map<String, Object>> dhgz = jdbcTemplate.queryForList(selDhgz);
		//最后一个字段的长度
		Object lastRule = dhgz.get(dhgz.size()-Integer.parseInt(all)).get("NUMB_LENGTH");
		//获取第一层最后一位的长度
		int lastRuleLength = Integer.parseInt(String.valueOf(lastRule));
		//截取勾选的档号规则
		String likeDanghao = danghao.substring(0,danghao.length()-lastRuleLength);



		//2.根据条件查询表中数据
		StringBuilder selData = new StringBuilder("select t.recid,t.maintitle,t.basefolder_unit,t.CRE_USER_ID,t.CRE_USER_NAME,"+dangHaoColMap.get("s_col_name").toString()+" from "+tName+" t where 1=1 ");

		selData.append(" and t."+dangHaoCol+" like ? ");
		para.add(likeDanghao+"%");

		//立卷单位名称条件
		if (!StringUtils.isBlank(liJuanDanWei)) {
			selData.append(" and t.basefolder_unit like ? ");
			para.add("%"+liJuanDanWei+"%");
		}
		//录入人姓名条件
		if (!StringUtils.isBlank(luRuRen)) {
			selData.append(" and t.CRE_USER_NAME like ? ");
			para.add("%"+luRuRen+"%");
		}
		//题名条件
		if (!StringUtils.isBlank(mainTitle)) {
			selData.append(" and t.maintitle like ? ");
			para.add("%"+mainTitle+"%");
		}
		//档号条件
		if (!StringUtils.isBlank(dangHao)) {
			selData.append(" and t."+dangHaoCol+" like ? ");
			para.add("%"+dangHao+"%");
		}
		//根据当前登录人业务角色和表名是否包含_dak来添加查询条件
		if(tableName.contains("_dak")){
			//当前登录人是档案管理员且在归档库进行操作,就什么都不做，查询全部
			selData.append(" ");
		}else if(!tableName.contains("_dak")){
			//当前登录人是录入人且在单位预立库操作，就显示当前录入人录入的数据
			//这里之所以没有加立卷单位过滤条件，是因为目前用户方一个录入人只会在一个立卷单位，这样可以兼容后续在多个立卷单位的情况
			//录入人需要加上立卷单位的过滤条件20190508
			selData.append(" and t.CRE_USER_ID='"+UserUtil.getCruUserId()+"' and t.ARCHIVE_ENTITY_STATUS <> '"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"' and t.CRE_CHUSHI_ID = '"+ljdw+"' ");
		}else{
			//目前就以上两种情况，其他情况直接不显示数据即可
			selData.append(" and 0=1 ");
		}
		//根据档号升序
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			selData.append(" order by "+dangHaoCol+" asc ");
		} else {
			if(pageImpl.getSortName().equals("dangHao")){
				selData.append(" order by " + dangHaoCol + " " + pageImpl.getSortOrder());
			}else if(pageImpl.getSortName().equals("liJuanDanWei")){
				selData.append(" order by " + "basefolder_unit" + " " + pageImpl.getSortOrder());
			}else if(pageImpl.getSortName().equals("creUserName")){
				selData.append(" order by " + "cre_user_Name" + " " + pageImpl.getSortOrder());
			}
			
		}
		
		int after = pageImpl.getPageNumber()*pageImpl.getPageSize();
		int pre = (pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1;
		String pageData="select * from (select n.*,rownum RN from ("+selData.toString()+") n) where RN between "+pre+" and "+after+"";
		
		//查询总条数
		String selTotalCount = "select count(recid) from ("+selData.toString()+") ";
		int totalCount = jdbcTemplate.queryForObject(selTotalCount, Integer.class,para.toArray());
		List<Map<String,Object>> dataMapList = jdbcTemplate.queryForList(pageData,para.toArray());
		List<DangAnEntity> list = new ArrayList<DangAnEntity>();
		//遍历dataMapList封装成对象的list
		//t.recid,t.maintitle,t.basefolder_unit,t.CRE_USER_ID,t.CRE_USER_NAME,"+dangHaoColMap.get("s_col_name").toString()
		if(null != dataMapList && dataMapList.size() > 0){
			for(Map<String,Object> map : dataMapList){
				DangAnEntity aDangAn = new DangAnEntity();
				aDangAn.setRecid(String.valueOf(map.get("recid")));
				aDangAn.setMaintitle(String.valueOf(map.get("maintitle")));
				aDangAn.setLiJuanDanWei(String.valueOf(map.get("basefolder_unit")));
				aDangAn.setCreUserId(String.valueOf(map.get("CRE_USER_ID")));
				aDangAn.setCreUserName(String.valueOf(map.get("CRE_USER_NAME")));
				aDangAn.setDangHao(String.valueOf(map.get(dangHaoColMap.get("s_col_name").toString())));
				aDangAn.setTableName(tName);
				aDangAn.setCz("update");
				list.add(aDangAn);
			}
		}
		//设置分页数据
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(list);
		pageImpl.getData().setTotal(totalCount);
		return pageImpl;
	}

	/**
	 * 
	 * TODO 门类第一层调整顺序
	 * @author 王磊
	 * @Date 2019年4月16日 下午6:18:35
	 * @param tableName 业务表表名
	 * @param recid 档案在业务表中的主键
	 * @param newXuHao 新的序号，例如项目号：00010,案卷号：0001
	 * @return
	 */
	@Override
	public net.sf.json.JSONObject adjustDangHao(String tableName, String recid, String newXuHao) {

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tableName.equals(tableNames.get(i))){
				tName = tableNames.get(i);
			}
		}

		net.sf.json.JSONObject jsonb = new net.sf.json.JSONObject();
		jsonb.put("flag", "0");
		jsonb.put("msg", "操作失败，请稍后再试！");
		//1判断newDangHao长度是否正确
		//1.1判断当前门类总共有几层
		String selTableRelation = "select t.* from tables_relation t "
	    		+"start with t.m_table_name = ? connect by prior t.s_table_name=t.m_table_name";
	    List<Map<String,Object>> tablesRelation = jdbcTemplate.queryForList(selTableRelation,tableName);
	    //默认只有一层
	    int levelNum = 1;
	    if(null != tablesRelation && tablesRelation.size() != 0){
	    	//门类层数 = 关联数+1
	    	levelNum = tablesRelation.size() + 1;
	    }
		//1.2根据层数获取当前档号应该的长度
	    String menLeiCode = findDAGLByTableName(tName).get("M_TABLE_NAME").toString();
		String selDhgz = "select * from dagl_party_num_rule where category_id in (select dct.id from dagl_category_table dct where dct.category_code='"+menLeiCode+"') order by order_num asc";
		List<Map<String, Object>> dhgz = jdbcTemplate.queryForList(selDhgz);
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			log.info("获取数据库连接失败！");
			jsonb.put("msg", "获取数据库连接异常！");
			jsonb.put("flag", "0");
			e.printStackTrace();
			return jsonb;
		}
		if(dhgz.size() >= levelNum){
			//获得当前表对应的档号规则
			Map<String, Object> nowPos = dhgz.get(dhgz.size()-levelNum);
			//判断用户填写的长度是否跟档号规则配置一致
			if(newXuHao.length() != Integer.parseInt(String.valueOf(nowPos.get("NUMB_LENGTH")))){
				jsonb.put("msg", "操作失败，序号长度跟档号规则里配置的该项长度不一致！");
				jsonb.put("flag", "0");
				return jsonb;
			}
			//2判断是否是调整的当前文件
			String selNowData = "select * from "+tName+" t where t.recid = ?";
			List<Map<String, Object>> nowData = jdbcTemplate.queryForList(selNowData,recid);
			//获取当前数据的序号并跟新的作对比
			if(Integer.parseInt(nowData.get(0).get(dhgz.get(dhgz.size()-levelNum).get("RULE_FIELD")).toString()) == Integer.parseInt(newXuHao)){
				//如果两者一致，假装操作成功
				jsonb.put("msg", "操作成功！");
				jsonb.put("flag", "1");
				return jsonb;
			}
			//3不是调整的当前文件
			if(Integer.parseInt(nowData.get(0).get(dhgz.get(dhgz.size()-levelNum).get("RULE_FIELD").toString().toUpperCase()).toString()) < Integer.parseInt(newXuHao)){
				//当前是往大的方向调整，例如1 2 3 4 5，其中3调整到5
				/**
				 * 判断当前表中是否存在用户输入的新序号:
				 * 1.当前用户是档案管理员且表名包含_dak，那么当前操作发生在归档库，查询是否存在新序号不用考虑当前登录人过滤条件;
				 * 2.当前用户不是录入人且表名不包含_dak,那么当前操作发生在单位预立库，查询是否存在新序号需要考虑当前登录人过滤条件;
				 */
				if(tName.contains("_dak")){
					//档案馆管理员
					//注：这里新的序号默认在数据库以字符串形式存储，没发现有存数字的。如果有，后续这里需结合实际情况做调整。
					//==王富康20190507 这里不能只查看序号，应当拿整个新的档号规则去查找。
					//档号字段
					String danghaoColumn = findDAGLByTableName(tName).get("S_COL_NAME").toString();
					//当前的档号
					String nowDanghao = nowData.get(0).get(danghaoColumn).toString();
					//想要变成的档号
					String changeDanghao = nowDanghao.substring(0,nowDanghao.length()-newXuHao.length())+newXuHao;
					//根据想要变成的档号去查询是否存在
					String selNewDataCount = "select count(recid) from "+tName+" t where t."+danghaoColumn+"= ? ";
					int newDataCount = jdbcTemplate.queryForObject(selNewDataCount, Integer.class,changeDanghao);
					if(newDataCount == 0){
						//不存在，提示用户应该输入一个存在的序号
						jsonb.put("msg", "操作失败，归档库不存在该序号对应的数据！");
						jsonb.put("flag", "0");
						return jsonb;
					}else{
						/**存在，重头戏开始了，往大的方向进行调整：
						 * 1.先调整当前选中的数据到指定的档号；
						 * 2.依次查询当前数据下一条数据，直到新的序号位置
						 */
						jsonb = doAdjust(nowData,dhgz,tablesRelation,con,"asc",newXuHao,levelNum,tName,menLeiCode);
						
					}
				}else if(UserUtil.getCruUserRoleNo().contains(DAGLCommonConstants.DALRR) && !tName.contains("_dak")){
					//录入人
					//==王富康20190507 这里不能只查看序号，应当拿整个新的档号规则去查找。
					//档号字段
					String danghaoColumn = findDAGLByTableName(tName).get("S_COL_NAME").toString();
					//当前的档号
					String nowDanghao = nowData.get(0).get(danghaoColumn).toString();
					//想要变成的档号
					String changeDanghao = nowDanghao.substring(0,nowDanghao.length()-newXuHao.length())+newXuHao;
					//根据想要变成的档号去查询是否存在

					String selNewDataCount = "select count(recid) from "+tName+" t where t."+danghaoColumn+"=? "
							+" and t.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and t.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' and t.ARCHIVE_ENTITY_STATUS !='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"'";
					int newDataCount = jdbcTemplate.queryForObject(selNewDataCount, Integer.class,changeDanghao);
					if(newDataCount == 0){
						//不存在，提示用户应该输入一个存在的序号
						jsonb.put("msg", "操作失败，单位预立库不存在该序号对应的数据！");
						jsonb.put("flag", "0");
						return jsonb;
					}else{
						/**存在，重头戏开始了，往大的方向进行调整：
						 * 1.先调整当前选中的数据到指定的档号；
						 * 2.依次查询当前数据下一条数据，直到新的序号位置
						 */
						jsonb = doAdjust(nowData,dhgz,tablesRelation,con,"asc",newXuHao,levelNum,tName,menLeiCode);
					}
				}else{
					//录入人会在单位预立库使用该功能，档案管理员会在归档库使用该功能，很明显目前用户没有在合理的位置使用当前操作。
					jsonb.put("msg", "操作失败，当前用户没有权限做此操作！");
					jsonb.put("flag", "0");
					return jsonb;
				}
			}else{
				//当前是往小的方向调整，例如1 2 3 4 5，其中3调整到2
				/**
				 * 判断当前表中是否存在用户输入的新序号:
				 * 1.当前用户是档案管理员且表名包含_dak，那么当前操作发生在归档库，查询是否存在新序号不用考虑当前登录人过滤条件;
				 * 2.当前用户不是录入人且表名不包含_dak,那么当前操作发生在单位预立库，查询是否存在新序号需要考虑当前登录人过滤条件;
				 */
				if(tName.contains("_dak")){
					//档案馆管理员
					//注：这里新的序号默认在数据库以字符串形式存储，没发现有存数字的。如果有，后续这里需结合实际情况做调整。
					//==王富康20190507 这里不能只查看序号，应当拿整个新的档号规则去查找。
					//档号字段
					String danghaoColumn = findDAGLByTableName(tName).get("S_COL_NAME").toString();
					//当前的档号
					String nowDanghao = nowData.get(0).get(danghaoColumn).toString();
					//想要变成的档号
					String changeDanghao = nowDanghao.substring(0,nowDanghao.length()-newXuHao.length())+newXuHao;
					//根据想要变成的档号去查询是否存在
					String selNewDataCount = "select count(recid) from "+tName+" t where t."+danghaoColumn+"= ? ";
					int newDataCount = jdbcTemplate.queryForObject(selNewDataCount, Integer.class,changeDanghao);
					if(newDataCount == 0){
						//不存在，提示用户应该输入一个存在的序号
						jsonb.put("msg", "操作失败，归档库不存在该序号对应的数据！");
						jsonb.put("flag", "0");
						return jsonb;
					}else{
						/**存在，重头戏开始了，往大的方向进行调整：
						 * 1.先调整当前选中的数据到指定的档号；
						 * 2.依次查询当前数据下一条数据，直到新的序号位置
						 */
						jsonb = doAdjust(nowData,dhgz,tablesRelation,con,"desc",newXuHao,levelNum,tName,menLeiCode);
						
					}
				}else if(UserUtil.getCruUserRoleNo().contains(DAGLCommonConstants.DALRR) && !tName.contains("_dak")){
					//录入人
					//档号字段
					String danghaoColumn = findDAGLByTableName(tName).get("S_COL_NAME").toString();
					//当前的档号
					String nowDanghao = nowData.get(0).get(danghaoColumn).toString();
					//想要变成的档号
					String changeDanghao = nowDanghao.substring(0,nowDanghao.length()-newXuHao.length())+newXuHao;
					//根据想要变成的档号去查询是否存在
					String selNewDataCount = "select count(recid) from "+tName+" t where t."+danghaoColumn+"= ? "
							+" and t.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and t.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' and t.ARCHIVE_ENTITY_STATUS !='"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2]+"'";

					int newDataCount = jdbcTemplate.queryForObject(selNewDataCount, Integer.class,changeDanghao);
					if(newDataCount == 0){
						//不存在，提示用户应该输入一个存在的序号
						jsonb.put("msg", "操作失败，单位预立库不存在该序号对应的数据！");
						jsonb.put("flag", "0");
						return jsonb;
					}else{
						/**存在，重头戏开始了，往大的方向进行调整：
						 * 1.先调整当前选中的数据到指定的档号；
						 * 2.依次查询当前数据下一条数据，直到新的序号位置
						 */
						jsonb = doAdjust(nowData,dhgz,tablesRelation,con,"desc",newXuHao,levelNum,tName,menLeiCode);
					}
					
				}else{
					//录入人会在单位预立库使用该功能，档案管理员会在归档库使用该功能，很明显目前用户没有在合理的位置使用当前操作。
					jsonb.put("msg", "操作失败，当前用户没有权限做此操作！");
					jsonb.put("flag", "0");
					return jsonb;
				}
			}
		}else{
			jsonb.put("msg", "操作失败，档号规则配置有问题，请确认后再试！");
			jsonb.put("flag", "0");
			return jsonb;
		}
		return jsonb;
	}
	
	/**
	 * 
	 * TODO 抽取出来的做调整序号的方法
	 * @author 王磊
	 * @Date 2019年4月19日 下午5:29:35
	 * @param nowData 当前需要首先变动的数据
	 * @param dhgz 当前门类的档号规则
	 * @param tablesRelation 当前门类下表表关联（不包含门类和）
	 * @param con 数据库连接，事务用
	 * @param order 往大(asc)的方向还是小(desc)的方向
	 * @param newXuHao 用户输入的新的序号
	 * @param levelNum 当前门类层数
	 * @param tableName 第一层表表名，可以根据表名是否包含"_dak"来区分是管理员还是录入人
	 * @return
	 */
	public net.sf.json.JSONObject doAdjust(List<Map<String, Object>> nowData,List<Map<String, Object>> dhgz,
			List<Map<String, Object>> tablesRelation,Connection con,String order,String newXuHao,int levelNum,String tableName,String menLeiCode){
		net.sf.json.JSONObject jsonb = new net.sf.json.JSONObject();
		jsonb.put("flag", "0");
		jsonb.put("msg", "操作失败，请稍后再试！");
		//获取保管期限的数据字典
		List<DataDictionary> listByMark = dataDictionaryDao.getListByMark("05");
		try {
			if(levelNum == 1){
				//当前门类只有一层，从配置里取出档号字段。
				String dangHaoCol = findDAGLByTableName(tableName).get("S_COL_NAME").toString();
				//1.先更新当前数据为新的档号
				//1.1拼接新的档号
				StringBuilder newDangHaoStr = new StringBuilder();
				//临时存储不包含最后一位档号规则的档号
				StringBuilder newDangHaoPartStr = new StringBuilder();
				//根据中文查询或者code查询保管期限数据字典
				//通过循环，获取序号前边那些档号规则项和连接符拼成的串
				for(int i=0;i<dhgz.size()-levelNum;i++){
					if(dhgz.get(i).get("RULE_FIELD").toString().toLowerCase().equals("retention")){
						//特殊处理保管期限retention
						for(DataDictionary d : listByMark){
							//如果数据字典的name和档案的保管期限存的值一样，则返回数据字典的code，来拼接完整的档号
							if(d.getName().equals(nowData.get(0).get(dhgz.get(i).get("RULE_FIELD").toString().toUpperCase()))){
								newDangHaoPartStr.append(d.getCode()+dhgz.get(i).get("CONNECTOR").toString());
								break;
							}
						}
					}else if(dhgz.get(i).get("RULE_FIELD").toString().toLowerCase().equals("leibiehao")){
						//特殊处理类别号leibiehao
						newDangHaoPartStr.append(menLeiCode+dhgz.get(i).get("CONNECTOR").toString());
					}else{
						newDangHaoPartStr.append((nowData.get(0).get(dhgz.get(i).get("RULE_FIELD").toString().toUpperCase())==null?"":nowData.get(0).get(dhgz.get(i).get("RULE_FIELD").toString().toUpperCase()))+dhgz.get(i).get("CONNECTOR").toString());
					}
				}
				newDangHaoStr.append(newDangHaoPartStr);
				//得到新的档号
				newDangHaoStr.append(newXuHao);
				//重新定义一个变量存放最后执行，也就是当前选中的数据的档号信息
				String changeNewDangHaoStr = newDangHaoStr.toString();
				String updateNowData = "update "+tableName+" t set t."+dangHaoCol+"= ? ,t."+dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()+"= ? where t.recid='"+nowData.get(0).get("RECID")+"'";
				//order 往大(asc)的方向还是小(desc)的方向
				if("asc".equals(order)){
					//当前数据下一条
					int start = Integer.parseInt(nowData.get(0).get(dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()).toString())+1;
					//最后一条数据
					int end = Integer.parseInt(newXuHao);
					for(int xh=start;xh<=end;xh++){
						//结合档号规则配置计算新的序号(带0)
						String xuHao = Integer.toString(xh-1);
						String oldXuHao = Integer.toString(xh);
						do{
							xuHao = "0"+xuHao;
						}while(xuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
                        do{
                            oldXuHao = "0"+oldXuHao;
                        }while(oldXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
						String upNextData = "";
						if(tableName.contains("_dak")){
							//管理员归档库操作
							upNextData = "update "+tableName+" t set t."+dangHaoCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+dangHaoCol+" = '"+newDangHaoPartStr.toString()+oldXuHao+"'";
						}else{
							//录入人单位预立库操作
							upNextData = "update "+tableName+" t set t."+dangHaoCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+dangHaoCol+" = '"+newDangHaoPartStr.toString()+oldXuHao
									+"' and t.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and t.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
						}
						con.prepareStatement(upNextData).execute();
						log.info("一层表的情况更新当前表数据的同级数据："+upNextData);
						//清空档号，为下一次做准备
						newDangHaoStr.setLength(0);
					}
				}else{
					//当前数据上一条
					int start = Integer.parseInt(nowData.get(0).get(dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()).toString())-1;
					//最后一条数据
					int end = Integer.parseInt(newXuHao);
					for(int xh=start;xh>=end;xh--){
						//结合档号规则配置计算新的序号(带0)
						String xuHao = Integer.toString(xh+1);
						String oldXuHao = Integer.toString(xh);
						do{
							xuHao = "0"+xuHao;
						}while(xuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
                        do{
                            oldXuHao = "0"+oldXuHao;
                        }while(oldXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
						String upNextData = "";
						if(tableName.contains("_dak")){
							//管理员归档库操作
							upNextData = "update "+tableName+" t set t."+dangHaoCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+dangHaoCol+" ='"+newDangHaoPartStr.toString()+oldXuHao+"'";
						}else{
							//录入人单位预立库操作
							upNextData = "update "+tableName+" t set t."+dangHaoCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+dangHaoCol+" ='"+newDangHaoPartStr.toString()+oldXuHao
									+"' and t.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and t.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
						}
						con.prepareStatement(upNextData).execute();
						log.info("一层表的情况更新当前表数据的同级数据："+upNextData);
						//清空档号，为下一次做准备
						newDangHaoStr.setLength(0);
					}
				}
				//con.prepareStatement(updateNowData).execute();

				PreparedStatement updateSales =  con.prepareStatement(updateNowData);
				updateSales.setString(1,changeNewDangHaoStr);
				updateSales.setString(2,newXuHao);
				updateSales.executeUpdate();

				log.info("最后根据recid进行更新，一层表的情况更新当前表数据："+updateNowData);
			}else if(levelNum == 2){
				//当前表和子表的关联属性
				String sTable = tablesRelation.get(0).get("S_TABLE_NAME").toString();
				String sCol = tablesRelation.get(0).get("S_COL_NAME").toString();
				String pCol = tablesRelation.get(0).get("M_COL_NAME").toString();
				//1.更新当前表数据：档号和序号
				//拼接新的档号
				StringBuilder newDangHaoStr = new StringBuilder();
				//临时存储不包含最后一位档号规则的档号
				StringBuilder newDangHaoPartStr = new StringBuilder();
				//通过循环，获取序号前边那些档号规则项和连接符拼成的串
				for(int i=0;i<dhgz.size()-levelNum;i++){
					if(dhgz.get(i).get("RULE_FIELD").toString().toLowerCase().equals("retention")){
						//特殊处理保管期限retention
						for(DataDictionary d : listByMark){
							//如果数据字典的name和档案的保管期限存的值一样，则返回数据字典的code，来拼接完整的档号
							if(d.getName().equals(nowData.get(0).get(dhgz.get(i).get("RULE_FIELD").toString().toUpperCase()))){
								newDangHaoPartStr.append(d.getCode()+dhgz.get(i).get("CONNECTOR").toString());
								break;
							}
						}
					}else if(dhgz.get(i).get("RULE_FIELD").toString().toLowerCase().equals("leibiehao")){
						//特殊处理类别号leibiehao
						newDangHaoPartStr.append(menLeiCode+dhgz.get(i).get("CONNECTOR").toString());
					}else{
						newDangHaoPartStr.append((nowData.get(0).get(dhgz.get(i).get("RULE_FIELD").toString().toUpperCase())==null?"":nowData.get(0).get(dhgz.get(i).get("RULE_FIELD").toString().toUpperCase()))+dhgz.get(i).get("CONNECTOR").toString());
					}
				}
				newDangHaoStr.append(newDangHaoPartStr);
				//得到新的档号
				newDangHaoStr.append(newXuHao);
				//重新定义一个变量存放最后执行，也就是当前选中的数据的档号信息
				String changeNewDangHaoStr = newDangHaoStr.toString();

				String updateNowData = "update "+tableName+" t set t."+pCol+"= ? ,t."+dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()+" = ? where t.recid='"+nowData.get(0).get("RECID").toString()+"'";
				//2.更新当前表子表：档号和与父表关联字段，默认管理员归档库操作
				String selNowSubData = "select s.* from "+sTable+" s where s."+sCol+"='"+nowData.get(0).get(pCol).toString()+"'";
				if(!tableName.contains("_dak")){
					//录入人单位预立库操作
					selNowSubData = selNowSubData + " and s.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and s.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
				}
				//添加子表排序规则：档号、升序
				selNowSubData = selNowSubData + " order by s."+sCol+" asc ";
				log.info("两层表查询当前表子表："+selNowSubData);
				List<Map<String,Object>> nowSubData = jdbcTemplate.queryForList(selNowSubData);
				//为了防止子表序号在数据库业务表已正整数存储，还是先查出来在做处理（先转整数，然后重新拼接）
				for(Map<String,Object> map : nowSubData){
					//更新子表：新关联字段,新档号项,新案卷号
					String subXuHao = map.get(dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()).toString();
					String lastLianJieFu = dhgz.get(dhgz.size()-2).get("CONNECTOR").toString();
					do{
						subXuHao = "0"+subXuHao;
					}while(subXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
					String updateSub = "update "+sTable+" t set t."+sCol+" = ?,t.archive_no= ? ,t."+dhgz.get(dhgz.size()-2).get("RULE_FIELD")+"= ? where t.recid='"+map.get("RECID").toString()+"'";
					//con.prepareStatement(updateSub).execute();

					PreparedStatement updateSales =  con.prepareStatement(updateSub);
					updateSales.setString(1,newDangHaoPartStr.toString()+newXuHao);
					updateSales.setString(2,newDangHaoPartStr.toString()+newXuHao+lastLianJieFu+subXuHao);
					updateSales.setString(3,newXuHao);
					updateSales.executeUpdate();

					log.info("两层的情况更新当前表子表："+updateSub);
				}
				if("asc".equals(order)){
					//当前数据的序号调大了，例如1 2 3 4 5，3调到5的位置
					//当前数据下一条
					int start = Integer.parseInt(nowData.get(0).get(dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()).toString())+1;
					//最后一条数据
					int end = Integer.parseInt(newXuHao);
					for(int xh=start;xh<=end;xh++){
						//获得新的序号
						String xuHao = Integer.toString(xh-1);
						String oldXuHao = Integer.toString(xh);
						do{
							xuHao = "0"+xuHao;
						}while(xuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-2).get("NUMB_LENGTH").toString()));
                        do{
                            oldXuHao = "0"+oldXuHao;
                        }while(oldXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-2).get("NUMB_LENGTH").toString()));
						//1.定义更新同级数据sql：更新序号和档号项
						String upNextData = "";
						if(tableName.contains("_dak")){
							//管理员归档库操作
							upNextData = "update "+tableName+" t set t."+pCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+pCol+" ='"+newDangHaoPartStr.toString()+oldXuHao+"'";
						}else{
							//录入人单位预立库操作
							upNextData = "update "+tableName+" t set t."+pCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+pCol+" ='"+newDangHaoPartStr.toString()+oldXuHao
									+"' and t.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and t.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"'";
						}
						con.prepareStatement(upNextData).execute();
						log.info("两层的情况更新当前表同级数据："+upNextData);
						//查询当前数据的子表数据
						String pColValOld = Integer.toString(xh);
						do{
							pColValOld = "0"+pColValOld;
						}while(pColValOld.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
						//默认管理员归档库操作
						String selSubData = "select s.* from "+sTable+" s where s."+sCol+"='"+newDangHaoPartStr.toString()+pColValOld+"'";
						if(!tableName.contains("_dak")){
							//录入人单位预立库操作
							selSubData = selSubData + " and s.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and s.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
						}
						//添加子表排序规则：档号、升序
						selSubData = selSubData + " order by s."+sCol+" asc ";
						List<Map<String,Object>> subData = jdbcTemplate.queryForList(selSubData);
						//为了防止子表序号在数据库业务表已正整数存储，还是先查出来在做处理（先转整数，然后重新拼接）
						for(Map<String,Object> map : subData){
							//更新子表：新关联字段,新档号项，新案卷号
							String subXuHao = map.get(dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()).toString();
							String lastLianJieFu = dhgz.get(dhgz.size()-2).get("CONNECTOR").toString();
							do{
								subXuHao = "0"+subXuHao;
							}while(subXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-2).get("NUMB_LENGTH").toString()));
							String updateSub = "update "+sTable+" t set t."+sCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t.archive_no='"+newDangHaoPartStr.toString()+xuHao+lastLianJieFu+subXuHao+"',t."+dhgz.get(dhgz.size()-2).get("RULE_FIELD")+"='"+xuHao+"' where t.recid='"+map.get("RECID").toString()+"'";
							con.prepareStatement(updateSub).execute();
							log.info("两层的情况更新当前表同级数据的子表数据："+updateSub);
						}
					}
				}else{
					//当前数据的序号调小了，例如1 2 3 4 5，5调到3的位置
					//当前数据上一条
					int start = Integer.parseInt(nowData.get(0).get(dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()).toString())-1;
					//最后一条数据
					int end = Integer.parseInt(newXuHao);
					for(int xh=start;xh>=end;xh--){
						//获得新的序号
						String xuHao = Integer.toString(xh+1);
						String oldXuHao = Integer.toString(xh);
						do{
							xuHao = "0"+xuHao;
						}while(xuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-2).get("NUMB_LENGTH").toString()));
                        do{
                            oldXuHao = "0"+oldXuHao;
                        }while(oldXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-2).get("NUMB_LENGTH").toString()));
						//1.定义更新同级数据sql：更新序号和档号项
						String upNextData = "";
						if(tableName.contains("_dak")){
							//管理员归档库操作
							upNextData = "update "+tableName+" t set t."+pCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+pCol+" ='"+newDangHaoPartStr.toString()+oldXuHao+"'";
						}else{
							//录入人单位预立库操作
							upNextData = "update "+tableName+" t set t."+pCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+pCol+" ='"+newDangHaoPartStr.toString()+oldXuHao
									+"' and t.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and t.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
						}
						con.prepareStatement(upNextData).execute();
						log.info("两层的情况更新当前表同级数据："+upNextData);
						//查询当前数据的子表数据
						String pColValOld = Integer.toString(xh);
						do{
							pColValOld = "0"+pColValOld;
						}while(pColValOld.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
						//默认管理员归档库操作
						String selSubData = "select s.* from "+sTable+" s where s."+sCol+"='"+newDangHaoPartStr.toString()+pColValOld+"' ";
						if(!tableName.contains("_dak")){
							//录入人单位预立库操作
							selSubData = selSubData + " and s.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and s.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
						}
						//增加子表升序排：档号、升序
						selSubData = selSubData + " order by s."+sCol+" asc ";
						log.info("两层的情况查询当前表同级数据的子表："+selSubData);
						List<Map<String,Object>> subData = jdbcTemplate.queryForList(selSubData);
						//为了防止子表序号在数据库业务表已正整数存储，还是先查出来再做处理（先转整数，然后重新拼接）
						for(Map<String,Object> map : subData){
							//更新子表：新关联字段,新档号项，新案卷号
							String subXuHao = map.get(dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()).toString();
							String lastLianJieFu = dhgz.get(dhgz.size()-2).get("CONNECTOR").toString();
							do{
								subXuHao = "0"+subXuHao;
							}while(subXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
							String updateSub = "update "+sTable+" t set t."+sCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t.archive_no='"+newDangHaoPartStr.toString()+xuHao+lastLianJieFu+subXuHao+"',t."+dhgz.get(dhgz.size()-2).get("RULE_FIELD")+"='"+xuHao+"' where t.recid='"+map.get("RECID").toString()+"'";
							con.prepareStatement(updateSub).execute();
							log.info("两层的情况更新当前表同级数据的子表数据："+updateSub);
						}
					}
				}
				//con.prepareStatement(updateNowData).execute();

				PreparedStatement updateSales =  con.prepareStatement(updateNowData);
				updateSales.setString(1,changeNewDangHaoStr);
				updateSales.setString(2,newXuHao);
				updateSales.executeUpdate();

				log.info("最后根据recid调整，两层的情况更新当前表："+updateNowData);
			}else if(levelNum == 3){
				//当前表和子表的关联属性
				//第二层表
				String sTable = tablesRelation.get(0).get("S_TABLE_NAME").toString();
				//第二层和第一层关联字段
				String sCol = tablesRelation.get(0).get("S_COL_NAME").toString();
				//第二层表的档号，应该是取第二层表跟第三层表关联数据的M_TABLE_NAME
				String sDangHao = tablesRelation.get(1).get("M_COL_NAME").toString();
				//第一层和第二层关联字段
				String pCol = tablesRelation.get(0).get("M_COL_NAME").toString();
				//第三层表
				String thirdTable = tablesRelation.get(1).get("S_TABLE_NAME").toString();
				//第三层表跟第二层表关联字段
				String thirdCol = tablesRelation.get(1).get("S_COL_NAME").toString();
				//第二层表
				String secdTable = tablesRelation.get(1).get("M_TABLE_NAME").toString();
				//第二层表跟第三层表关联字段
				String secdCol = tablesRelation.get(1).get("M_COL_NAME").toString();
				//预立项目号字段
				String itemIdCol = dhgz.get(dhgz.size()-3).get("RULE_FIELD").toString();
				//1.更新当前表数据：档号和序号
				//拼接新的档号
				StringBuilder newDangHaoStr = new StringBuilder();
				//临时存储不包含最后一位档号规则的档号
				StringBuilder newDangHaoPartStr = new StringBuilder();
				//初始化一层，二层、三层个数，方便从日志里分析执行sql
				int yi = 1,er = 1,san = 1;
				//通过循环，获取序号前边那些档号规则项和连接符拼成的串
				for(int i=0;i<dhgz.size()-levelNum;i++){
					if(dhgz.get(i).get("RULE_FIELD").toString().toLowerCase().equals("retention")){
						//特殊处理保管期限retention
						for(DataDictionary d : listByMark){
							//如果数据字典的name和档案的保管期限存的值一样，则返回数据字典的code，来拼接完整的档号
							if(d.getName().equals(nowData.get(0).get(dhgz.get(i).get("RULE_FIELD").toString().toUpperCase()))){
								newDangHaoPartStr.append(d.getCode()+dhgz.get(i).get("CONNECTOR").toString());
								break;
							}
						}
					}else if(dhgz.get(i).get("RULE_FIELD").toString().toLowerCase().equals("leibiehao")){
						//特殊处理类别号leibiehao
						newDangHaoPartStr.append(menLeiCode+dhgz.get(i).get("CONNECTOR").toString());
					}else{
						newDangHaoPartStr.append((nowData.get(0).get(dhgz.get(i).get("RULE_FIELD").toString().toUpperCase())==null?"":nowData.get(0).get(dhgz.get(i).get("RULE_FIELD").toString().toUpperCase()))+dhgz.get(i).get("CONNECTOR").toString());
					}
				}
				newDangHaoStr.append(newDangHaoPartStr);
				//得到新的档号
				newDangHaoStr.append(newXuHao);
				//重新定义一个变量存放最后执行，也就是当前选中的数据的档号信息
				String changeNewDangHaoStr = newDangHaoStr.toString();
				String updateNowData = "update "+tableName+" t set t."+pCol+"= ? ,t."+dhgz.get(dhgz.size()-3).get("RULE_FIELD").toString()+" = ? where t.recid='"+nowData.get(0).get("RECID").toString()+"'";
				//2.更新当前表子表：档号和与父表关联字段，默认管理员归档库操作
				String selNowSubData = "select s.* from "+sTable+" s where s."+sCol+"='"+nowData.get(0).get(pCol).toString()+"'";
				if(!tableName.contains("_dak")){
					//录入人单位预立库操作
					selNowSubData = selNowSubData + " and s.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and s.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
				}
				//添加排序规则：档号、升序
				selNowSubData = selNowSubData + " order by s."+sDangHao+" asc ";
				log.info("三层的情况查询当前数据第二层数据："+selNowSubData);
				List<Map<String,Object>> nowSubData = jdbcTemplate.queryForList(selNowSubData);
				//为了防止子表序号在数据库业务表已正整数存储，还是先查出来在做处理（先转整数，然后重新拼接）
				for(Map<String,Object> map : nowSubData){
					//更新第二层：新关联字段=第一层和第二层新的关联字段值,新档号项=新关联字段+第一层和第二层的连接符+第二层的序号
					String subXuHao = map.get(dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()).toString();
					String secTableLianJieFu = dhgz.get(dhgz.size()-3).get("CONNECTOR").toString();
					do{
						subXuHao = "0"+subXuHao;
					}while(subXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-2).get("NUMB_LENGTH").toString()));
					String updateSub = "update "+sTable+" t set t."+sCol+" = ? ,t."+secdCol+"= ? ,t."+itemIdCol+"= ?  where t.recid='"+map.get("RECID").toString()+"'";
					//con.prepareStatement(updateSub).execute();

					PreparedStatement updateSales =  con.prepareStatement(updateSub);
					updateSales.setString(1,newDangHaoPartStr.toString()+newXuHao);
					updateSales.setString(2,newDangHaoPartStr.toString()+newXuHao+secTableLianJieFu+subXuHao);
					updateSales.setString(3,newXuHao);
					updateSales.executeUpdate();

					log.info("三层的情况更新当前数据第二层："+er+"-----"+updateSub);
					//查询第三层数据，默认管理员
					String selThirdData = "select s.* from "+thirdTable+" s where s."+thirdCol+"='"+map.get(secdCol)+"' ";
					if(!tableName.contains("_dak")){
						//录入人在单位预立库操作
						selThirdData = selThirdData + " and s.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and s.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
					}
					//查询第三层添加排序：档号、升序
					selThirdData = selThirdData + " order by s."+thirdCol+" asc";
					log.info("三层的情况查询当前数据第三层："+selThirdData);
					List<Map<String,Object>> thirdTableData = jdbcTemplate.queryForList(selThirdData);
					for(Map<String,Object> sMap : thirdTableData){
						//更新第三层：第三层档号、第三层和第二层的关联字段值
						String thirdTableXuHao = sMap.get(dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()).toString();
						String thirdTableLianJieFu = dhgz.get(dhgz.size()-2).get("CONNECTOR").toString();
						do{
							thirdTableXuHao = "0"+thirdTableXuHao;
						}while(thirdTableXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
						//第三层档号=第二层新档号+第二层和第三层的连接符+第三层序号
						String thirdDangHaoVal = newDangHaoPartStr.toString()+newXuHao+secTableLianJieFu+subXuHao+thirdTableLianJieFu+thirdTableXuHao;
						//第三层和第二层关联字段值=第二层新档号
						String thirdRelationColVal = newDangHaoPartStr.toString()+newXuHao+secTableLianJieFu+subXuHao;
						String upThirdTable = "update "+thirdTable+" t set t."+thirdCol+"='"+thirdRelationColVal+"',t.archive_no='"+thirdDangHaoVal+"',t."+sCol+"= ? ,t."+itemIdCol+"= ?  where t.recid='"+sMap.get("RECID")+"' ";
						//con.prepareStatement(upThirdTable).execute();
						PreparedStatement updateSaless =  con.prepareStatement(upThirdTable);
						updateSaless.setString(1,newDangHaoPartStr.toString()+newXuHao);
						updateSaless.setString(2,newXuHao);
						updateSaless.executeUpdate();
						log.info("三层的情况更新当前数据的第三层："+er+"."+san+"-----"+upThirdTable);
						san = san + 1;
					}
					er = er + 1;
				}
				//更新同级数据前重新编号
				er = 1;
				san = 1;
				if("asc".equals(order)){
					//当前数据的序号调大了，例如1 2 3 4 5，3调到5的位置
					//当前数据下一条
					int start = Integer.parseInt(nowData.get(0).get(dhgz.get(dhgz.size()-3).get("RULE_FIELD").toString()).toString())+1;
					//最后一条数据
					int end = Integer.parseInt(newXuHao);
					for(int xh=start;xh<=end;xh++){
						//获得新的序号
						String xuHao = Integer.toString(xh-1);
						String oldXuHao = Integer.toString(xh);
						do{
							xuHao = "0"+xuHao;
						}while(xuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-3).get("NUMB_LENGTH").toString()));

                        do{
                            oldXuHao = "0"+oldXuHao;
                        }while(oldXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-3).get("NUMB_LENGTH").toString()));

						//1.定义更新同级数据sql：更新序号和档号项
						String upNextData = "";
						if(tableName.contains("_dak")){
							//管理员归档库操作
							upNextData = "update "+tableName+" t set t."+pCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-3).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+pCol+" ='"+newDangHaoPartStr.toString()+oldXuHao+"'";
						}else{
							//录入人单位预立库操作
							upNextData = "update "+tableName+" t set t."+pCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-3).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+pCol+" ='"+newDangHaoPartStr.toString()+oldXuHao
									+"' and t.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and t.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
						}
						con.prepareStatement(upNextData).execute();
						log.info("三层的情况更新当前表同级数据："+yi+"-----"+upNextData);
						//查询当前数据的子表数据
						String pColValOld = Integer.toString(xh);
						do{
							pColValOld = "0"+pColValOld;
						}while(pColValOld.length() < Integer.parseInt(dhgz.get(dhgz.size()-3).get("NUMB_LENGTH").toString()));
						//默认管理员归档库操作
						String selSubData = "select s.* from "+sTable+" s where s."+sCol+"='"+newDangHaoPartStr.toString()+pColValOld+"'";
						if(!tableName.contains("_dak")){
							//录入人单位预立库操作
							selSubData = selSubData + " and s.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and s.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
						}
						//添加排序规则：档号、升序
						selSubData = selSubData + " order by s."+sDangHao+" asc ";
						log.info("三层的情况查询同级数据第二层数据："+selSubData);
						List<Map<String,Object>> subData = jdbcTemplate.queryForList(selSubData);
						//为了防止子表序号在数据库业务表以正整数存储，还是先查出来在做处理（先转整数，然后重新拼接）
						for(Map<String,Object> map : subData){
							//更新子表：新关联字段,新档号项
							String subXuHao = map.get(dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()).toString();
							String secTableLianJieFu = dhgz.get(dhgz.size()-3).get("CONNECTOR").toString();
							do{
								subXuHao = "0"+subXuHao;
							}while(subXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-2).get("NUMB_LENGTH").toString()));
							String updateSub = "update "+sTable+" t set t."+sCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+secdCol+"='"+newDangHaoPartStr.toString()+xuHao+secTableLianJieFu+subXuHao+"',t."+itemIdCol+"='"+xuHao+"' where t.recid='"+map.get("RECID")+"'";
							con.prepareStatement(updateSub).execute();
							log.info("三层的情况更新同级数据的二级数据："+yi+"."+er+"-----"+updateSub);
							
							//查询第三层数据，默认管理员
							String selThirdData = "select s.* from "+thirdTable+" s where s."+thirdCol+"='"+map.get(secdCol)+"' ";
							if(!tableName.contains("_dak")){
								//录入人在单位预立库操作
								selThirdData = selThirdData + " and s.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and s.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
							}
							//查询第三层添加排序：档号、升序
							selThirdData = selThirdData + " order by s."+thirdCol+" asc";
							log.info("三层的情况查询同级数据第三层："+selThirdData);
							List<Map<String,Object>> thirdTableData = jdbcTemplate.queryForList(selThirdData);
							for(Map<String,Object> sMap : thirdTableData){
								//更新第三层：第三层档号、第三层和第二层的关联字段值
								String thirdTableXuHao = sMap.get(dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()).toString();
								String thirdTableLianJieFu = dhgz.get(dhgz.size()-2).get("CONNECTOR").toString();
								do{
									thirdTableXuHao = "0"+thirdTableXuHao;
								}while(thirdTableXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
								//第三层档号=第二层新档号+第二层和第三层的连接符+第三层序号
								String thirdDangHaoVal = newDangHaoPartStr.toString()+xuHao+secTableLianJieFu+subXuHao+thirdTableLianJieFu+thirdTableXuHao;
								//第三层和第二层关联字段值=第二层新档号
								String thirdRelationColVal = newDangHaoPartStr.toString()+xuHao+secTableLianJieFu+subXuHao;
								String upThirdTable = "update "+thirdTable+" t set t."+thirdCol+"='"+thirdRelationColVal+"',t.archive_no='"+thirdDangHaoVal+"',t."+sCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+itemIdCol+"='"+xuHao+"' where t.recid='"+sMap.get("RECID")+"' ";
								con.prepareStatement(upThirdTable).execute();
								log.info("三层的情况更新同级数据的第三层："+yi+"."+er+"."+san+"-----"+upThirdTable);
								san = san + 1;
							}
							er = er + 1;
							san = 1;
						}
						yi = yi + 1;
						er = 1;
					}
				}else{
					//当前数据的序号调小了，例如1 2 3 4 5，5调到3的位置
					//当前数据上一条
					int start = Integer.parseInt(nowData.get(0).get(dhgz.get(dhgz.size()-3).get("RULE_FIELD").toString()).toString())-1;
					//最后一条数据
					int end = Integer.parseInt(newXuHao);
					for(int xh=start;xh>=end;xh--){
						//获得新的序号
						String xuHao = Integer.toString(xh+1);
						String oldXuHao = Integer.toString(xh);
						do{
							xuHao = "0"+xuHao;
						}while(xuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-3).get("NUMB_LENGTH").toString()));
                        do{
                            oldXuHao = "0"+oldXuHao;
                        }while(oldXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-3).get("NUMB_LENGTH").toString()));

						//1.定义更新同级数据sql：更新序号和档号项
						String upNextData = "";
						if(tableName.contains("_dak")){
							//管理员归档库操作
							upNextData = "update "+tableName+" t set t."+pCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-3).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+pCol+" ='"+newDangHaoPartStr.toString()+oldXuHao+"'";
						}else{
							//录入人单位预立库操作
							upNextData = "update "+tableName+" t set t."+pCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+dhgz.get(dhgz.size()-3).get("RULE_FIELD").toString()+"='"+xuHao+"' where "+pCol+" ='"+newDangHaoPartStr.toString()+oldXuHao
									+"' and t.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and t.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
						}
						con.prepareStatement(upNextData).execute();
						log.info("三层的情况更新当前表同级数据："+yi+"-----"+upNextData);
						//查询当前数据的子表数据
						String pColValOld = Integer.toString(xh);
						do{
							pColValOld = "0"+pColValOld;
						}while(pColValOld.length() < Integer.parseInt(dhgz.get(dhgz.size()-3).get("NUMB_LENGTH").toString()));
						//默认管理员归档库操作
						String selSubData = "select s.* from "+sTable+" s where s."+sCol+"='"+newDangHaoPartStr.toString()+pColValOld+"'";
						if(!tableName.contains("_dak")){
							//录入人单位预立库操作
							selSubData = selSubData + " and s.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and s.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
						}
						//添加排序规则：档号、升序
						selSubData = selSubData + " order by s."+sDangHao+" asc ";
						log.info("三层的情况查询同级数据第二层数据："+selSubData);
						List<Map<String,Object>> subData = jdbcTemplate.queryForList(selSubData);
						//为了防止子表序号在数据库业务表以正整数存储，还是先查出来在做处理（先转整数，然后重新拼接）
						for(Map<String,Object> map : subData){
							//更新子表：新关联字段,新档号项
							String subXuHao = map.get(dhgz.get(dhgz.size()-2).get("RULE_FIELD").toString()).toString();
							String secTableLianJieFu = dhgz.get(dhgz.size()-3).get("CONNECTOR").toString();
							do{
								subXuHao = "0"+subXuHao;
							}while(subXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-2).get("NUMB_LENGTH").toString()));
							String updateSub = "update "+sTable+" t set t."+sCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+secdCol+"='"+newDangHaoPartStr.toString()+xuHao+secTableLianJieFu+subXuHao+"',t."+itemIdCol+"='"+xuHao+"' where t.recid='"+map.get("RECID")+"'";
							con.prepareStatement(updateSub).execute();
							log.info("三层的情况更新同级数据的二级数据："+yi+"."+er+"-----"+updateSub);
							
							//查询第三层数据，默认管理员
							String selThirdData = "select s.* from "+thirdTable+" s where s."+thirdCol+"='"+map.get(secdCol)+"' ";
							if(!tableName.contains("_dak")){
								//录入人在单位预立库操作
								selThirdData = selThirdData + " and s.CRE_USER_ID='"+nowData.get(0).get("CRE_USER_ID").toString()+"' and s.CRE_CHUSHI_ID='"+nowData.get(0).get("CRE_CHUSHI_ID").toString()+"' ";
							}
							//查询第三层添加排序：档号、升序
							selThirdData = selThirdData + " order by s."+thirdCol+" asc";
							log.info("三层的情况查询同级数据第三层："+selThirdData);
							List<Map<String,Object>> thirdTableData = jdbcTemplate.queryForList(selThirdData);
							for(Map<String,Object> sMap : thirdTableData){
								//更新第三层：第三层档号、第三层和第二层的关联字段值
								String thirdTableXuHao = sMap.get(dhgz.get(dhgz.size()-1).get("RULE_FIELD").toString()).toString();
								String thirdTableLianJieFu = dhgz.get(dhgz.size()-2).get("CONNECTOR").toString();
								do{
									thirdTableXuHao = "0"+thirdTableXuHao;
								}while(thirdTableXuHao.length() < Integer.parseInt(dhgz.get(dhgz.size()-1).get("NUMB_LENGTH").toString()));
								//第三层档号=第二层新档号+第二层和第三层的连接符+第三层序号
								String thirdDangHaoVal = newDangHaoPartStr.toString()+xuHao+secTableLianJieFu+subXuHao+thirdTableLianJieFu+thirdTableXuHao;
								//第三层和第二层关联字段值=第二层新档号
								String thirdRelationColVal = newDangHaoPartStr.toString()+xuHao+secTableLianJieFu+subXuHao;
								String upThirdTable = "update "+thirdTable+" t set t."+thirdCol+"='"+thirdRelationColVal+"',t.archive_no='"+thirdDangHaoVal+"',t."+sCol+"='"+newDangHaoPartStr.toString()+xuHao+"',t."+itemIdCol+"='"+xuHao+"' where t.recid='"+sMap.get("RECID")+"' ";
								con.prepareStatement(upThirdTable).execute();
								log.info("三层的情况更新同级数据的第三层："+yi+"."+er+"."+san+"-----"+upThirdTable);
								san = san + 1;
							}
							er = er + 1;
							san = 1;
						}
						yi = yi + 1;
						er = 1;
					}
				}
				//con.prepareStatement(updateNowData).execute();
				PreparedStatement updateSaless =  con.prepareStatement(updateNowData);
				updateSaless.setString(1,changeNewDangHaoStr);
				updateSaless.setString(2,newXuHao);
				updateSaless.executeUpdate();
				log.info("根据recid，三层的情况更新当前表："+updateNowData);
			}else{
				//目前最多三层结构，如果出现其他则不处理
				jsonb.put("flag", "0");
				jsonb.put("msg", "程序目前只支持最多三层结构的档案，请联系管理员！");
				return jsonb;
			}
			con.commit();
			jsonb.put("flag", "1");
			jsonb.put("msg", "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
				jsonb.put("flag", "0");
				jsonb.put("msg", "操作失败，请联系管理员或稍后再试！");
				return jsonb;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return jsonb;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 查询数量
	 * @Date 2019/4/23 11:45
	 * @Param [danghao, columnName, tName, chushiid]
	 * @return net.sf.json.JSONObject
	 **/
	@Override
	public int queryCountAdd(String danghao,String columnName, String tName, String chushiid,String lastColumnName, String ranking){

		//防止SQL注入，先去查询表名集合，判断是否含有传过来的表，含有，则取查询出来的表名，不用前台传过来的表名
		String tableName = "";
		List<String> tableNames = findAllTables();
		for(int i=0;i<tableNames.size();i++){
			if(tName.equals(tableNames.get(i))){
				tableName = tableNames.get(i);
			}
		}

		//防止SQL注入，先去查询表字段集合，判断是否含有传过来的字段，含有，则取查询出来的字段名，不用前台传过来的字段名
		List<String> colName = findColByTableName(tableName);
		String col = "";
		for(int i=0;i<colName.size();i++){
			if(colName.get(i).equals(lastColumnName)){
				col =colName.get(i);
			}
		}

		List<Object> para = new ArrayList<>();
		String sql = "select max("+col+") from "+tableName+" where "+columnName+" like ?  ";
		para.add(danghao+"%");
		if(tName.indexOf("_dak") == -1){
			sql += " and cre_chushi_id = ? and cre_user_id = '"+UserUtil.getCruUserId()+"'";
			para.add(chushiid);
		}

		if(Integer.parseInt(ranking)>1){
			//一层以上，存在游离的文件，需要把游离的文件排除掉，以防，档号能够匹配上，导致自动加一功能错误,也就是已组卷的文件
			sql+="and archive_flag = ?";
			para.add(DAGLCommonConstants.ARCHIVE_FLAG[0]);
		}
		Integer integer =jdbcTemplate.queryForObject(sql, Integer.class,para.toArray());
		if(integer==null) {
			integer=0;
		}
		return integer;
	}

	/**
	 * 
	 * TODO 获取所有档案业务表表名
	 * @author 王磊
	 * @Date 2019年4月26日 下午5:27:59
	 * @return
	 */
	@Override
	public List<String> findAllTables() {
		String selAllTables = "select distinct t.table_name from table_struct_description t";
		return jdbcTemplate.queryForList(selAllTables,String.class);
	}

	/**
	 * 
	 * TODO 根据字段名和表名判断某个字段是否是属于这张表
	 * @author 王磊
	 * @Date 2019年4月26日 下午5:37:29
	 * @param tableName
	 * @return 如果能找到则返回，找不到就返回null
	 */
	@Override
	public List<String> findColByTableName(String tableName) {
		List<String> colName = null;
		String sel = "select distinct t.column_name from table_struct_description t where t.table_name= ?";
		colName = jdbcTemplate.queryForList(sel,String.class, tableName);
		return colName;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 更新父表的数量相关信息,目前只考虑三层的
	 * @Date 2019/6/3 10:56
	 * @Param fids  父级数据的id
	 * @Param tableName  子表的表名
	 * @Param all  当前门类层数
	 * @Param ranking  当前修改的是第几层的数据
	 * @Param pieceNumData  子表数据新增删除时，父表加一减一的数据（该参数为1或者-1）
	 * @Param pageNumData  子表数据新增删除，修改时，父表同时进行修改的数据（该参数为正数或者负数）
	 * @return int
	 **/
	public int updatePNum(String fids, String id,String tableName,String all, String ranking,String pieceNumData,String pageNumData, String type, String pageNumAddOrDel){

		String[] fid = fids.split(",");
		//子表数据新增删除时，父表加一减一的字段
		String pieceNumCol = "";
		//子表数据新增删除，修改时，父表同时进行修改的字段
		String pageNumCol = "";
		if(3 ==Integer.parseInt(all)){
			if(2 == Integer.parseInt(ranking)){
				pieceNumCol = "folder_num";
				pageNumCol = "item_piece_num";
			}else if(3==Integer.parseInt(ranking)){
				pieceNumCol = "piece_num";
				pageNumCol = "page_num";
			}
		}else if(2 ==Integer.parseInt(all)){
			pieceNumCol = "piece_num";
			pageNumCol = "page_num";
		}
		//找到父表,可以得到表名
		Map<String, Object> pRelationData = findDAGLByTableName(tableName);
		//自动加一减一的sql,只有数据新增和删除时会影响
		//父表表名
		String pTableName = pRelationData.get("M_TABLE_NAME")==null?"":pRelationData.get("M_TABLE_NAME").toString();
		//父表字段
		String pmColName = String.valueOf(pRelationData.get("M_COL_NAME"));
		//子表字段
		String psColName = String.valueOf(pRelationData.get("S_COL_NAME"));
		if("add".equals(type)){
			//新增
			//含有父id，并且只有一个父id时，为关联新增，关联新增考虑数量上的变化。
			//有父级，新增之后首先修改父级的item_piece_num和piece_num
			String updatePieceNumSql = "update "+pTableName+" t set t."+pieceNumCol+" = t."+pieceNumCol+"+("+Integer.parseInt(pieceNumData)+") where t.recid = '"+fid[0]+"'";
			int updatePieceNum = jdbcTemplate.update(updatePieceNumSql);
			//件数的增加
			String updatePageNumSql = "";
			if(all.equals(ranking)){
				//首先更新上层的件数
				//同时进行修改的sql,只要子表的该字段有所修改，父表字段相对应的进行修改。
				updatePageNumSql="update "+pTableName+" t set t."+pageNumCol+" = t."+pageNumCol+"+("+Integer.parseInt(pageNumData)+") where t.recid = '"+fid[0]+"'";
				int updatePageNum = jdbcTemplate.update(updatePageNumSql);
				if(Integer.parseInt(ranking)==3){
					//三层的修改上层，也需要修改第一层的总件数
					//找到父表,可以得到表名
					Map<String, Object> ppRelationData = findDAGLByTableName(pTableName);
					//查询父表数据
					//父表表名
					String ppTableName = String.valueOf(ppRelationData.get("M_TABLE_NAME"));
					//父表字段
					String ppmColName = String.valueOf(ppRelationData.get("M_COL_NAME"));
					//子表字段
					String ppsColName = String.valueOf(ppRelationData.get("S_COL_NAME"));
					//根据id查询案卷的信息
					String pTableSql = "select * from "+pTableName+" where recid = ? ";
					List<Map<String,Object>> pData = jdbcTemplate.queryForList(pTableSql,fid[0]);
					//子表关联字段的值（父表的档号）
					String sColData = String.valueOf(pData.get(0).get(ppsColName));
					//处室id
					String chushiId = String.valueOf(pData.get(0).get("CRE_CHUSHI_ID"));
					//创建人id
					String creUserId = String.valueOf(pData.get(0).get("CRE_USER_ID"));
					//查看当前数据的组卷状态，未组卷的话，就不往上级找了
					String archiveFlag = String.valueOf(pData.get(0).get("ARCHIVE_FLAG"));
					if(DAGLCommonConstants.ARCHIVE_FLAG[0].equals(archiveFlag)){
						//首先判断是否含有组卷标识这个字段
						//判断表中是否含有组卷标识的字段
						String queryArchiveFlagColSql ="SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = 'ARCHIVE_FLAG'";
						int count = jdbcTemplate.queryForObject(queryArchiveFlagColSql,Integer.class,ppTableName.toUpperCase());

						//查询父表记录（单位库）
						String sql = "select t.* from "+ppTableName+" t where 1=1 and t."+ppmColName+" = '"+sColData+"' ";
						if(count > 0){
							//含有组卷标识这个字段，加上组卷标识判断
							sql += "	and t.archive_flag='"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
						}
						if(ppTableName.indexOf("_dak")==-1){
							//查父表记录条件:关联字段、立卷单位（上边的处室id）、创建人id一致
							sql +="	and t.cre_chushi_id = '"+chushiId+"' and t.cre_user_id='"+creUserId+"' ";
						}
						List<Map<String,Object>> pTableDataList = jdbcTemplate.queryForList(sql);
						if(pTableDataList.size()>0){
							Map<String,Object> pTableDataMap = pTableDataList.get(0);
							//总件数加1
							String updatePPageNumSql="update "+ppTableName+" t set t.item_piece_num = t.item_piece_num+("+Integer.parseInt(pieceNumData)+") where t.recid = '"+pTableDataMap.get("RECID")+"'";
							int updatePPageNum = jdbcTemplate.update(updatePPageNumSql);
						}
					}

				}
			}
		}else if("delete".equals(type)){
			//删除
			//根据id查询当前数据
			String tableSql = "select * from "+tableName+" where recid = ? ";
			List<Map<String,Object>> data = jdbcTemplate.queryForList(tableSql,id);
			//子表关联字段的值（父表的档号）
			String sColData = String.valueOf(data.get(0).get(pmColName));
			//处室id
			String chushiId = String.valueOf(data.get(0).get("CRE_CHUSHI_ID"));
			//创建人id
			String creUserId = String.valueOf(data.get(0).get("CRE_USER_ID"));
			//查看当前数据的组卷状态，未组卷的话，就不往上级找了
			String archiveFlag = String.valueOf(data.get(0).get("ARCHIVE_FLAG"));
			if(DAGLCommonConstants.ARCHIVE_FLAG[0].equals(archiveFlag)){
				//判断表中是否含有组卷标识的字段
				String queryArchiveFlagColSql ="SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = 'ARCHIVE_FLAG'";
				int count = jdbcTemplate.queryForObject(queryArchiveFlagColSql,Integer.class,pTableName.toUpperCase());
				//查询父表记录（单位库）
				String sql = "select t.* from "+pTableName+" t where 1=1 and t."+pmColName+" = '"+sColData+"' ";
				if(count>0){
					sql+="	and t.archive_flag='"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
				}
				if(pTableName.indexOf("_dak")==-1){
					//查父表记录条件:关联字段、立卷单位（上边的处室id）、创建人id一致
					sql +="	and t.cre_chushi_id = '"+chushiId+"' and t.cre_user_id='"+creUserId+"' ";
				}
				List<Map<String,Object>> pTableDataList = jdbcTemplate.queryForList(sql);
				if(pTableDataList.size()>0){
					Map<String,Object> pTableDataMap = pTableDataList.get(0);
					String updatePageNumSql="update "+pTableName+" t set t."+pieceNumCol+" = t."+pieceNumCol+"+("+Integer.parseInt(pieceNumData)+"),t."+pageNumCol+"=t."+pageNumCol+"+("+Integer.parseInt(pageNumData)+") where t.recid = '"+pTableDataMap.get("RECID")+"'";
					int updatePageNum = jdbcTemplate.update(updatePageNumSql);
					if(Integer.parseInt(ranking)==3){
						//需要更新第一层的数据
						//找到父表,可以得到表名
						Map<String, Object> ppRelationData = findDAGLByTableName(pTableName);
						//查询父表数据
						//父表表名
						String ppTableName = String.valueOf(ppRelationData.get("M_TABLE_NAME"));
						//父表字段
						String ppmColName = String.valueOf(ppRelationData.get("M_COL_NAME"));
						//子表字段
						String ppsColName = String.valueOf(ppRelationData.get("S_COL_NAME"));
						//根据id查询案卷的信息
						String ppTableSql = "select * from "+pTableName+" where recid = ? ";
						List<Map<String,Object>> ppData = jdbcTemplate.queryForList(ppTableSql,pTableDataMap.get("RECID"));
						//子表关联字段的值（父表的档号）
						String ppsColData = String.valueOf(ppData.get(0).get(ppsColName));
						//处室id
						String ppchushiId = String.valueOf(ppData.get(0).get("CRE_CHUSHI_ID"));
						//创建人id
						String ppcreUserId = String.valueOf(ppData.get(0).get("CRE_USER_ID"));
						//查看当前数据的组卷状态，未组卷的话，就不往上级找了
						String ppArchiveFlag = String.valueOf(ppData.get(0).get("ARCHIVE_FLAG"));
						if(DAGLCommonConstants.ARCHIVE_FLAG[0].equals(ppArchiveFlag)){
							//查询父表记录（单位库）
							//判断表中是否含有组卷标识的字段
							String ppQueryArchiveFlagColSql ="SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = 'ARCHIVE_FLAG'";
							int ppCount = jdbcTemplate.queryForObject(ppQueryArchiveFlagColSql,Integer.class,ppTableName.toUpperCase());

							String ppsql = "select t.* from "+ppTableName+" t where 1=1 and t."+ppmColName+" = '"+ppsColData+"' ";
							if(ppCount > 0){
								//含有组卷标识这个字段，加上组卷标识判断
								ppsql += "and t.archive_flag='"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
							}

							if(ppTableName.indexOf("_dak")==-1){
								//查父表记录条件:关联字段、立卷单位（上边的处室id）、创建人id一致
								ppsql +="	and t.cre_chushi_id = '"+ppchushiId+"' and t.cre_user_id='"+ppcreUserId+"' ";
							}
							List<Map<String,Object>> ppTableDataList = jdbcTemplate.queryForList(ppsql);
							Map<String,Object> ppTableDataMap = ppTableDataList.get(0);
							//总件数加1
							String updatePPageNumSql="update "+ppTableName+" t set t.item_piece_num = t.item_piece_num+("+Integer.parseInt(pieceNumData)+") where t.recid = '"+ppTableDataMap.get("RECID")+"'";
							int updatePPageNum = jdbcTemplate.update(updatePPageNumSql);
						}
					}
				}
			}
		}else if("update".equals(type)){
			//修改，只有修改第三层的页数才会影响，影响的只有第二层的page_num
			if(all.equals(ranking) && Integer.parseInt(all)>1 && !("".equals(pageNumAddOrDel))){
				//根据id查询当前数据
				String tableSql = "select * from "+tableName+" where recid = ? ";
				List<Map<String,Object>> data = jdbcTemplate.queryForList(tableSql,id);
				//子表关联字段的值（父表的档号）
				String sColData = String.valueOf(data.get(0).get(pmColName));
				//处室id
				String chushiId = String.valueOf(data.get(0).get("CRE_CHUSHI_ID"));
				//创建人id
				String creUserId = String.valueOf(data.get(0).get("CRE_USER_ID"));
				//查看当前数据的组卷状态，未组卷的话，就不往上级找了
				String archiveFlag = String.valueOf(data.get(0).get("ARCHIVE_FLAG"));
				if(DAGLCommonConstants.ARCHIVE_FLAG[0].equals(archiveFlag)){
					//判断表中是否含有组卷标识的字段
					String PQueryArchiveFlagColSql ="SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = 'ARCHIVE_FLAG'";
					int pCount = jdbcTemplate.queryForObject(PQueryArchiveFlagColSql,Integer.class,pTableName.toUpperCase());
					//查询父表记录（单位库）
					String sql = "select t.* from "+pTableName+" t where 1=1 and t."+pmColName+" = '"+sColData+"' ";
					if(pCount>0){
						sql += " and t.archive_flag='"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
					}
					if(pTableName.indexOf("_dak")==-1){
						//查父表记录条件:关联字段、立卷单位（上边的处室id）、创建人id一致
						sql +="	and t.cre_chushi_id = '"+chushiId+"' and t.cre_user_id='"+creUserId+"' ";
					}
					List<Map<String,Object>> pTableDataList = jdbcTemplate.queryForList(sql);
					if(pTableDataList.size()>0){
						Map<String,Object> pTableDataMap = pTableDataList.get(0);
						String updatePageNumSql="update "+pTableName+" t set t."+pageNumCol+" = t."+pageNumCol+"+("+Integer.parseInt(pageNumAddOrDel)+") where t.recid = '"+pTableDataMap.get("RECID")+"'";
						int updatePageNum = jdbcTemplate.update(updatePageNumSql);
					}
				}
			}
		}else{
			//调入,调出
				//二层，调整父表
				//调入新增
				//含有父id，并且只有一个父id时，为关联新增，关联新增考虑数量上的变化。
				//有父级，新增之后首先修改父级的item_piece_num和piece_num（案卷数）
				String updatePieceNumSql = "update "+pTableName+" t set t."+pieceNumCol+" = t."+pieceNumCol+"+("+Integer.parseInt(pieceNumData)+") where t.recid = '"+fid[0]+"'";
				int updatePieceNum = jdbcTemplate.update(updatePieceNumSql);
				//件数的增加
				String updatePageNumSql = "";
				if(all.equals(ranking)){
					//首先更新上层的件数
					//同时进行修改的sql,只要子表的该字段有所修改，父表字段相对应的进行修改。
					updatePageNumSql="update "+pTableName+" t set t."+pageNumCol+" = t."+pageNumCol+"+("+Integer.parseInt(pageNumData)+") where t.recid = '"+fid[0]+"'";
					int updatePageNum = jdbcTemplate.update(updatePageNumSql);
					if(Integer.parseInt(ranking)==3){
						//三层的修改上层，也需要修改第一层的总件数
						//找到父表,可以得到表名
						Map<String, Object> ppRelationData = findDAGLByTableName(pTableName);
						//查询父表数据
						//父表表名
						String ppTableName = String.valueOf(ppRelationData.get("M_TABLE_NAME"));
						//父表字段
						String ppmColName = String.valueOf(ppRelationData.get("M_COL_NAME"));
						//子表字段
						String ppsColName = String.valueOf(ppRelationData.get("S_COL_NAME"));
						//根据id查询案卷的信息
						String pTableSql = "select * from "+pTableName+" where recid = ? ";
						List<Map<String,Object>> pData = jdbcTemplate.queryForList(pTableSql,fid[0]);
						//子表关联字段的值（父表的档号）
						String sColData = String.valueOf(pData.get(0).get(ppsColName));
						//处室id
						String chushiId = String.valueOf(pData.get(0).get("CRE_CHUSHI_ID"));
						//创建人id
						String creUserId = String.valueOf(pData.get(0).get("CRE_USER_ID"));
						//查看当前数据的组卷状态，未组卷的话，就不往上级找了
						String archiveFlag = String.valueOf(pData.get(0).get("ARCHIVE_FLAG"));
						if(DAGLCommonConstants.ARCHIVE_FLAG[0].equals(archiveFlag)){
							//判断表中是否含有组卷标识的字段
							String queryArchiveFlagColSql ="SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = 'ARCHIVE_FLAG'";
							int count = jdbcTemplate.queryForObject(queryArchiveFlagColSql,Integer.class,ppTableName.toUpperCase());
							//查询父表记录（单位库）
							String sql = "select t.* from "+ppTableName+" t where 1=1 and t."+ppmColName+" = '"+sColData+"'";
							if(count >0){
								sql += "and t.archive_flag='"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'";
							}
							if(ppTableName.indexOf("_dak")==-1){
								//查父表记录条件:关联字段、立卷单位（上边的处室id）、创建人id一致
								sql +="	and t.cre_chushi_id = '"+chushiId+"' and t.cre_user_id='"+creUserId+"' ";
							}
							List<Map<String,Object>> pTableDataList = jdbcTemplate.queryForList(sql);
							if(pTableDataList.size()>0){
								Map<String,Object> pTableDataMap = pTableDataList.get(0);
								//总件数加1
								String updatePPageNumSql="update "+ppTableName+" t set t.item_piece_num = t.item_piece_num+("+Integer.parseInt(pieceNumData)+") where t.recid = '"+pTableDataMap.get("RECID")+"'";
								int updatePPageNum = jdbcTemplate.update(updatePPageNumSql);
							}
						}
					}
				}else if("2".equals(ranking) && "3".equals(all)){
					//三层的第二层
					//修改了上层的案卷数之后，取本层的分卷数，加到第一层的总件数上边
					//首先更新上层的件数
					//同时进行修改的sql,只要子表的该字段有所修改，父表字段相对应的进行修改。
					updatePageNumSql="update "+pTableName+" t set t.item_piece_num = t.item_piece_num+("+Integer.parseInt(pageNumAddOrDel)+") where t.recid = '"+fid[0]+"'";
					int updatePageNum = jdbcTemplate.update(updatePageNumSql);
				}
		}
		return 0;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 是否能调入，防止确认调整之后导致档号跟游离的档案重复
	 * @Date 2019/6/12 9:43
	 * @Param []
	 * @return int
	 **/
	@Override
	public int canDiaoru(String count, String tName, String selectFRecid, String categoryCode, String recid, String all,String ranking){

		int repetitionData =0;
		//调整的档号字段
		String danghaoCol = "";
		if(Integer.parseInt(ranking) ==1){
			//判断第二层的档号
			//获取档号字段
			if(Integer.parseInt(ranking)+1 == Integer.parseInt(all)){
				//两层的第二层
				danghaoCol = "archive_no";
			}else if(Integer.parseInt(all)==3){
				//三层的第二层
				danghaoCol = "folder_no";
			}
		}else if(Integer.parseInt(ranking) ==2){
			//判断第二层的档号
			//获取档号字段
			if(Integer.parseInt(ranking)+1 == Integer.parseInt(all)){
				//三层层的第三层
				danghaoCol = "archive_no";
			}
		}
		//根据id获取当前数据
		List<Map<String,Object>> findById = findById(tName, selectFRecid);
		//
		String queryCategoryIdSql = "select dct.id from dagl_category_table dct where dct.category_code= ? ";
		List<String> categoryId = jdbcTemplate.queryForList(queryCategoryIdSql,String.class, categoryCode);
		//获取档号规则
		List<Map<String,Object>> ruleList = findDHGZbyMCode(categoryId.get(0));
		//查找
		String pTableSql =
				"SELECT t.*\n" +
						"  FROM tables_relation t, table_description s\n" +
						" WHERE t.s_table_name = s.table_name\n" +
						"   and t.m_table_name = '"+tName+"'";//排除父节点为门类的数据

		List<Map<String,Object>> pTableList = jdbcTemplate.queryForList(pTableSql);
		if(pTableList.size()>0){
			Map<String,Object> relevancyData = pTableList.get(0);
			//子表表名
			String sTableName = relevancyData.get("S_TABLE_NAME")==null?"":relevancyData.get("S_TABLE_NAME").toString();
			//父表关联字段
			String mColName = relevancyData.get("M_COL_NAME")==null?"":relevancyData.get("M_COL_NAME").toString();
			//子表关联字段
			String sColName = relevancyData.get("S_COL_NAME")==null?"":relevancyData.get("S_COL_NAME").toString();
			//关联数据
			String relevancyColData = findById.get(0).get(mColName)==null?"":findById.get(0).get(mColName).toString();
			//关联的数据的创建人
			String relevancyUserData = findById.get(0).get("CRE_USER_ID")==null?"":findById.get(0).get("CRE_USER_ID").toString();
			//关联的数据的创建处室
			String relevancyChushiData = findById.get(0).get("CRE_CHUSHI_ID")==null?"":findById.get(0).get("CRE_CHUSHI_ID").toString();
			//最后一个档号规则字段之前的连接符
			String lastRuleConnector = ruleList.get(ruleList.size()-1-(Integer.parseInt(all)-Integer.parseInt(ranking))).get("CONNECTOR")==null?"":ruleList.get(ruleList.size()-1-(Integer.parseInt(all)-Integer.parseInt(ranking))).get("CONNECTOR").toString();
			//最后一个档号规则字段
			String lastRuleCol = ruleList.get(ruleList.size()-(Integer.parseInt(all)-Integer.parseInt(ranking))).get("RULE_FIELD")==null?"":ruleList.get(ruleList.size()-(Integer.parseInt(all)-Integer.parseInt(ranking))).get("RULE_FIELD").toString();
			//最后一个档号规则长度
			String lastRuleLength = ruleList.get(ruleList.size()-(Integer.parseInt(all)-Integer.parseInt(ranking))).get("NUMB_LENGTH")==null?"":ruleList.get(ruleList.size()-(Integer.parseInt(all)-Integer.parseInt(ranking))).get("NUMB_LENGTH").toString();
			//查询子表数据
			String sTableSql =	"select *\n" +
					"  from "+sTableName+"\n" +
					" where 1 = 1\n" +
					"   and archive_flag = '"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'\n" +
					"   and "+sColName+" = '"+relevancyColData+"'\n" +
					"   and (ARCHIVE_ENTITY_STATUS != '"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2] +"' or ARCHIVE_ENTITY_STATUS is null)\n";
				if(tName.indexOf("_dak")==-1){
					//单位预立库根据立卷单位和创建人查询
					sTableSql += "   and cre_user_id = '"+relevancyUserData+"'\n" +
							"   and cre_chushi_id = '"+relevancyChushiData+"'\n" ;
				}
			sTableSql += " order by "+danghaoCol+" desc";
			List<Map<String,Object>> sTableList = jdbcTemplate.queryForList(sTableSql);
			int maxNum = 0;
			if(sTableList.size()>0){
				//最大一条数据
				Map<String,Object> maxSData = sTableList.get(0);
				String lastNum = maxSData.get(lastRuleCol)==null?"":maxSData.get(lastRuleCol).toString();
				maxNum = Integer.parseInt(lastNum)+1;
			}else{
				//无数据，根据档号规则模拟第一条数据。
				maxNum = 1;
			}

			for(int i=0;i<Integer.parseInt(count);i++){
				//得到一个NumberFormat的实例
				NumberFormat nf = NumberFormat.getInstance();
				//设置是否使用分组
				nf.setGroupingUsed(false);
				//设置最大整数位数
				nf.setMaximumIntegerDigits(Integer.parseInt(lastRuleLength));
				//设置最小整数位数
				nf.setMinimumIntegerDigits(Integer.parseInt(lastRuleLength));
				String newLastData = nf.format(maxNum);
				//最大的档号规则
				String newDanghao = relevancyColData+lastRuleConnector+newLastData;
				//判重
				int repetition = isRepetition(danghaoCol,newDanghao, sTableName, relevancyChushiData, recid, ranking);
				if(repetition==0) {
					//不重复，什么都不做
				}else {
					//重复
					repetitionData++;
				}
				maxNum++;
			}

		}
		//是否可以调入,0可以调入，大于0不可调入
		return repetitionData;
	}
}
