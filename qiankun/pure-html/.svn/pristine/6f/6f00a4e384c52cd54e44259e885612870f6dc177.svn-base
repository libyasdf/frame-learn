package com.sinosoft.sinoep.common.auditlog.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.common.auditlog.util.LogSqlTool;
import com.sinosoft.sinoep.common.auditlog.util.OperType;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.jedis.services.RedisClusterService;
import com.sinosoft.sinoep.common.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO 拦截器，用来拦截sql
 * @author 李利广
 * @Date 2019年04月10日 14:48:58
 */
public class DBLogFilter extends FilterEventAdapter {

    private final static Log auditLog = LogFactory.getLog("auditLog");

    /**
     * redis缓存中存放“是否需要审核表”配置数据的KEY
     */
    private final String AUDIT_TABLE_KEY = "SYS_AUDIT_LOG_SETTING";

    @Autowired
    private RedisClusterService redisService;

    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql) {
        auditLog.info("==========Before  Execute==========");
        String uniqueId = LogSqlTool.getUniqueId();
        if(StringUtils.isNotBlank(uniqueId)){
            internalBeforeStatementExecute(statement,sql);
        }
    }

    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        auditLog.info("==========Before  Query==========");
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        auditLog.info("==========Before  ExecuteUpdate==========");
        String uniqueId = LogSqlTool.getUniqueId();
        if(StringUtils.isNotBlank(uniqueId)){
            internalBeforeStatementExecute(statement,sql);
        }
    }

    @Override
    protected void statementExecuteBatchBefore(StatementProxy statement) {
        auditLog.info("==========Before  Batch==========");
        String uniqueId = LogSqlTool.getUniqueId();
        if(StringUtils.isNotBlank(uniqueId)){
            internalBeforeStatementExecute(statement,"");
        }
    }

    /**
     * TODO sql拦截后，记录审计日志的方法
     * @author 李利广
     * @Date 2019年04月10日 21:38:24
     * @param statement
     * @return void
     */
    private final void internalBeforeStatementExecute(StatementProxy statement, String sql) {
        List<String> sqlList = new ArrayList<>();
        try{
            //判断sql是否为空，为空表示是批处理sql
            if(StringUtils.isBlank(sql)){
                sqlList = statement.getBatchSqlList();
            }else{
                sqlList.add(sql);
            }
            if(sqlList.size() == 0){
                auditLog.info("未获拦截到sql的请求:" + sqlList.size());
            }
            //获取缓存中需要审核的库表
            Object auditTab = null;
            auditTab = redisService.get(AUDIT_TABLE_KEY + ":${" + ConfigConsts.SYSTEM_ID + "}");
            if(auditTab != null){
                String auditTable = auditTab.toString();
                auditLog.info("需要审核的表信息，auditTable:" + auditTable);
                JSONArray tableArray = JSONArray.fromObject(auditTable);
                for(String strSql:sqlList){
                    strSql = strSql.toLowerCase();
                    auditLog.info("sql:" + strSql);
                    //根据sql判断是哪种语句（select/insert/update/delete）
                    String sqlType = LogSqlTool.checkSqlType(strSql);
                    auditLog.info("sql类型:" + sqlType);
                    //获取表名
                    String tableName = LogSqlTool.getTableName(strSql,sqlType);
                    auditLog.info("sql表名:" + tableName);
                    //判断tableName是否需要审核,需要审核则返回表数据
                    JSONObject tableInfo = LogSqlTool.checkAuditTable(tableArray,tableName);
                    auditLog.info("tableInfo:" + tableInfo);
                    if(!tableInfo.isEmpty()){
                        //获取表的主键字段名
                        String idName = LogSqlTool.getIdName(redisService,AUDIT_TABLE_KEY,tableInfo);
                        auditLog.info("表主键字段idName:" + idName);
                        if(StringUtils.isNotBlank(idName)){
                            //判断sql是否是预编译sql，如果是，则从statement里面获取数据，如果不是则通过分析sql获取数据
                            Boolean isCompile = LogSqlTool.checkSqlPrecompile(strSql);
                            auditLog.info("sql是:" + (isCompile?"预编译":"非预编译"));
                            //需要审核，sql分析
                            List<Map<String ,Object>> array = LogSqlTool.analyseSql(strSql,idName,statement,sqlType,isCompile);
                            //组装executeInfo，放到ThreadLocal中
                            buildExecuteInfo(tableInfo,array,sqlList,sqlType);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            auditLog.error(e.getMessage(),e);
        }
    }

    /**
     * TODO 组装executeInfo，放到ThreadLocal中
     * @author 李利广
     * @Date 2019年04月15日 10:18:48
     * @param tableInfo 需要审核的表数据
     * @param array sql分析结果
     * @param sqlList
     * @return void
     */
    private void buildExecuteInfo(JSONObject tableInfo,List<Map<String ,Object>> array,List<String> sqlList,String sqlType){
        JSONObject executeJson = new JSONObject();
        executeJson.put("tableId",tableInfo.getString("tableId"));
        executeJson.put("executeDate",DateUtil.COMMON_FULL.getDateText(new Date()));
        if(sqlType.equals("insert")){
            executeJson.put("operType", OperType.CREATE.getOperType());
        }else if(sqlType.equals("update")){
            executeJson.put("operType", OperType.UPDATE.getOperType());
        }else if(sqlType.equals("delete")){
            executeJson.put("operType", OperType.DELETE.getOperType());
        }
        executeJson.put("executeSql",StringUtils.join(sqlList,";"));
        executeJson.put("rows",array);
        //将每次拦截到sql分析处理结果，放到ThreadLocal中
        LogSqlTool.getSqlInfo().add(executeJson);
    }

}
