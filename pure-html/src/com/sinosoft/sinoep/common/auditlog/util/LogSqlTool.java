package com.sinosoft.sinoep.common.auditlog.util;

import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.support.json.JSONWriter;
import com.sinosoft.sinoep.common.jedis.services.RedisClusterService;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.util.*;

/**
 * TODO
 * @author 李利广
 * @Date 2019年04月13日 17:20:37
 */
public class LogSqlTool {

    private static Logger log = LoggerFactory.getLogger(LogSqlTool.class);

    /**
     * 用于存放同一次操作中关联唯一标识的ThreadLocal
     */
    private static final ThreadLocal<String> uniqueId = new ThreadLocal();

    /**
     * 用于存放本次请求信息的ThreadLocal
     * 包含：requestPara：请求参数
     *      moduleId：资源ID
     *      operationType：操作类别（新增 CREATE ，修改 UPDATE ，删除 DELETE ，查询 QUERY）
     *      moduleState：操作说明
     */
    private static final ThreadLocal<JSONObject> requestInfo = new ThreadLocal<>();

    /**
     * 用户存放sql分析处理后的数据的ThreadLocal
     */
    private static final ThreadLocal<JSONArray> sqlInfo = new ThreadLocal<>();

    public static String getUniqueId() {
        return uniqueId.get();
    }

    public static void setUniqueId(String id) {
        uniqueId.set(id);
    }

    public static void setRequestInfo(JSONObject json){
        requestInfo.set(json);
    }

    public static JSONObject getRequestInfo() {
        return requestInfo.get();
    }

    public static void setSqlInfo(JSONArray array){
        sqlInfo.set(array);
    }

    public static JSONArray getSqlInfo(){
        return sqlInfo.get();
    }

    /**
     * TODO 检测sql语句类别（insert/update/delete）
     * @author 李利广
     * @Date 2019年04月12日 09:46:03
     * @param sql
     * @return java.lang.String
     */
    public static String checkSqlType(String sql) {
        String name = "";
        String[] begin = { "select", "insert", "update", "delete" };
        int i = -1;
        int i_old = 0;
        for (String type : begin) {
            i_old = sql.indexOf(type);
            if (i_old >= 0) {
                if (i == -1) {
                    name = type;
                    i = i_old;
                }else if (i_old < i) {
                    name = type;
                }
            }
        }
        return name;
    }

    /**
     * TODO 判断sql是否是预编译语句
     * @author 李利广
     * @Date 2019年04月12日 16:46:15
     * @param sql
     * @return java.lang.Boolean
     */
    public static Boolean checkSqlPrecompile(String sql){
        if(sql.indexOf("?") >= 0){
            return true;
        }
        return false;
    }

    /**
     * TODO 获取表名
     * @author 李利广
     * @Date 2019年04月12日 14:22:31
     * @param sql
     * @param sqlType
     * @return java.lang.String
     */
    public static String getTableName(String sql,String sqlType){
        String tableName = "";
        if(StringUtils.isNotBlank(sql)){
            sql = sql.toLowerCase();
            if("insert".equals(sqlType)){
                tableName = sql.substring(sql.indexOf("into") + 4,sql.indexOf("(")).trim();
            }else if("delete".equals(sqlType)){
                int iEndPos = sql.indexOf("where");
                if (iEndPos == -1) {
                    iEndPos = sql.length();
                }
                if (sql.indexOf("from") != -1) {
                    tableName = sql.substring(sql.indexOf("from") + 4, iEndPos).trim();
                }else{
                    tableName = sql.substring(6, iEndPos).trim();
                }
            }else if("update".equals(sqlType)){
                int iEndPos = sql.indexOf("set");
                tableName = sql.substring(sql.indexOf("update") + 6, iEndPos).trim();
            }
            int isHaveSchema = tableName.indexOf(".");
            if (isHaveSchema > -1) {
                tableName = tableName.substring(isHaveSchema + 1,tableName.length());
            }
            int isHaveSpace = tableName.indexOf(" ");
            if(isHaveSpace > -1){
                tableName = tableName.substring(0,isHaveSpace);
            }
        }
        return tableName;
    }

    /**
     * TODO 判断tableName是否需要审核
     * @author 李利广
     * @Date 2019年04月12日 15:13:35
     * @param tableArray
     * @param tableName
     * @return net.sf.json.JSONObject
     */
    public static JSONObject checkAuditTable(JSONArray tableArray, String tableName){
        JSONObject json = new JSONObject();
        if(tableArray.size() > 0){
            for(int i = 0;i < tableArray.size(); i++){
                JSONObject jsonObject = tableArray.getJSONObject(i);
                if("tjxm".equals(jsonObject.getString("sourceName").toLowerCase())){
                    JSONArray tableList = jsonObject.getJSONArray("tables");
                    if(tableList.size() > 0){
                        for(int j = 0;j < tableList.size(); j++){
                            JSONObject table = tableList.getJSONObject(j);
                            if(tableName.equals(table.getString("tableCode").toLowerCase())){
                                json = table;
                                break;
                            }
                        }
                    }
                }else{
                    continue;
                }
            }
        }
        return json;
    }

    /**
     * TODO 获取表的主键字段名
     * @author 李利广
     * @Date 2019年04月21日 13:45:23
     * @param redisService
     * @param redisKey
     * @param tableInfo
     * @return java.lang.String
     */
    public static String getIdName(RedisClusterService redisService,String redisKey, JSONObject tableInfo){
        String tableId = tableInfo.getString("tableId");
        String key = redisKey + ":${" + tableId + "}";
        Object obj = redisService.get(key);
        JSONObject tableColInfo = JSONObject.fromObject(obj);
        String columns = tableColInfo.getString("columns");
        JSONArray array = JSONArray.fromObject(columns);
        for(int i = 0;i < array.size(); i++){
            JSONObject cols = array.getJSONObject(i);
            if("1".equals(cols.getString("isKey"))){
                return cols.getString("columnCode");
            }
        }
        return "";
    }

    /**
     * TODO sql分析处理方法，获取sql参数
     *  只能获取到预编译sql的参数，如果要获取拼接sql中的参数，需另作处理
     * @author 李利广
     * @Date 2019年04月10日 21:39:11
     * @param statement
     * @return java.lang.String
     */
    public static String buildSlowParameters(StatementProxy statement) {
        JSONWriter out = new JSONWriter();
        out.writeArrayStart();
        for (int i = 0, parametersSize = statement.getParametersSize(); i < parametersSize; ++i) {
            JdbcParameter parameter = statement.getParameter(i);
            if (i != 0) {
                out.writeComma();
            }
            if (parameter == null) {
                continue;
            }

            Object value = parameter.getValue();
            if (value == null) {
                out.writeNull();
            }else if (value instanceof String) {
                String text = (String) value;
                if (text.length() > 100) {
                    out.writeString(text.substring(0, 97) + "...");
                }else {
                    out.writeString(text);
                }
            }else if (value instanceof Number) {
                out.writeObject(value);
            }else if (value instanceof java.util.Date) {
                out.writeObject(value);
            }else if (value instanceof Boolean) {
                out.writeObject(value);
            }else if (value instanceof InputStream) {
                out.writeString("<InputStream>");
            }else if (value instanceof NClob) {
                out.writeString("<NClob>");
            }else if (value instanceof Clob) {
                out.writeString("<Clob>");
            }else if (value instanceof Blob) {
                out.writeString("<Blob>");
            }else {
                out.writeString('<' + value.getClass().getName() + '>');
            }
        }
        out.writeArrayEnd();
        return out.toString();
    }

    /**
     * TODO sql分析
     * @author 李利广
     * @Date 2019年04月12日 15:29:46
     * @param sql   sql语句
     * @param idName 表的主键字段名
     * @param statement 表名
     * @param sqlType   sql类型（insert/update/delete）
     * @param isCompile 是否是预编译sql
     * @return List<Map<String ,String>>
     */
    public static synchronized List<Map<String ,Object>> analyseSql(String sql,String idName, StatementProxy statement, String sqlType, Boolean isCompile) throws Exception{
        List<Map<String ,Object>> list = new ArrayList<>();
        if("insert".equals(sqlType)){
            //旧数据全部为空
            //获取新数据
            List<Map<String ,Object>> list1 = new ArrayList<>();
            Map<String ,Object> map1 = new HashMap<>();
            Map<String ,Object> map = analyseInsertSql(sql,isCompile,statement);
            Set<String> colName = map.keySet();
            for(String col:colName){
                Map<String,Object> infoMap = new HashMap<>();
                infoMap.put("columnCode",col.toUpperCase());
                infoMap.put("oldValue","");
                infoMap.put("newValue",map.get(col));
                //如果是主键，则放在第一位
                if(col.equalsIgnoreCase(idName)){
                    list1.add(0,infoMap);
                }else{
                    list1.add(infoMap);
                }
            }
            map1.put("columns",list1);
            list.add(map1);
        }else if("delete".equals(sqlType)){
            //新数据全部为空
            //获取旧数据
            List<Map<String,Object>> rows = analyseDeleteSql(sql,isCompile,statement);
            List<Map<String ,Object>> list1 = new ArrayList<>();
            Map<String ,Object> map1 = new HashMap<>();
            if(rows.size() > 0){
                for(Map<String,Object> row:rows){
                    Set<String> keys = row.keySet();
                    for(String key:keys){
                        Object oldValue = row.get(key);
                        Map<String,Object> map = new HashMap<>();
                        map.put("columnCode",key.toUpperCase());
                        map.put("oldValue",oldValue);
                        map.put("newValue","");
                        //如果是主键，则放在第一位
                        if(key.equalsIgnoreCase(idName)){
                            list1.add(0,map);
                        }else{
                            list1.add(map);
                        }
                    }
                }
            }
            map1.put("columns",list1);
            list.add(map1);
        }else if("update".equals(sqlType)){
            //获取新数据
            Map<String,Object> newMap = analyseUpdateSql(sql,isCompile,statement);
            //根据新数据获取对应字段的旧数据(包括主键)
            List<Map<String,Object>> rowsOld = analyseUpdateSqlOld(sql,idName,isCompile,statement);
            //遍历旧数据，比对新旧数据，去除没有变化的数据
            //注意：旧数据中包括主键，肯定比新数据多一个字段
            Map<String ,Object> map1 = new HashMap<>();
            List<Map<String ,Object>> list1 = new ArrayList<>();
            for(Map<String,Object> oldRow:rowsOld){
                Set<String> keys = oldRow.keySet();
                for(String key:keys){
                    Object oldValue = oldRow.get(key);
                    Object newValue = null;
                    //如果是主键，则放在第一位
                    if(key.equalsIgnoreCase(idName)){
                        Map<String ,Object> map2 = new HashMap<>();
                        map2.put("columnCode",key.toUpperCase());
                        map2.put("oldValue",oldValue);
                        map2.put("newValue",oldValue);
                        list1.add(0,map2);
                    }else{
                        //如果不是主键，再去比对新旧数据
                        if(newMap.containsKey(key.toLowerCase())){
                            newValue = newMap.get(key.toLowerCase());
                        }else if(newMap.containsKey(key.toUpperCase())){
                            newValue = newMap.get(key.toUpperCase());
                        }
                        //比对新旧数据
                        Boolean isComp =  compValue(oldValue ,newValue);
                        if(!isComp){
                            Map<String ,Object> map2 = new HashMap<>();
                            map2.put("columnCode",key.toUpperCase());
                            map2.put("oldValue",oldValue != null?oldValue:"");
                            map2.put("newValue",newValue != null?newValue:"");
                            list1.add(map2);
                        }
                    }
                }
            }
            map1.put("columns",list1);
            list.add(map1);
        }
        return list;
    }

    public static void main(String[] args){
        String sql = "update task_plan t set t.title = '123',t.note = '123' where t.id = '123'";
        String aa = getUpdateCol(sql);
        System.out.println(aa);
    }

    /**
     * TODO 解析insert语句，获取新数据
     * @author 李利广
     * @Date 2019年04月12日 16:18:49
     * @param sql
     * @param isCompile 是否是预编译sql
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private static synchronized Map<String ,Object> analyseInsertSql(String sql,Boolean isCompile, StatementProxy statement){
        Map<String ,Object> map = new HashMap<>();
        //获取字段名
        String tableName = getTableName(sql,"insert");
        String colNameStr = sql.substring(sql.indexOf(tableName)+tableName.length(),sql.indexOf("values"));
        colNameStr = colNameStr.substring(colNameStr.indexOf("(")+1,colNameStr.indexOf(")"));
        String[] cols = colNameStr.split(",");
        if(isCompile){
            //如果是预编译，从statement中获取数据
            for (int i = 0, parametersSize = statement.getParametersSize(); i < parametersSize; ++i) {
                JdbcParameter parameter = statement.getParameter(i);
                String colName = cols[i].trim();
                if (parameter == null) {
                    continue;
                }
                Object value = parameter.getValue();
                if (value == null) {
                    map.put(colName,"");
                }else if (value instanceof String) {
                    String text = (String) value;
                    if (text.length() > 100) {
                        map.put(colName,text.substring(0, 97) + "...");
                    }else {
                        map.put(colName,text);
                    }
                }else if (value instanceof Number) {
                    map.put(colName,value);
                }else if (value instanceof java.util.Date) {
                    map.put(colName,value);
                }else if (value instanceof Boolean) {
                    map.put(colName,value);
                }else if (value instanceof InputStream) {
                    map.put(colName,"<InputStream>");
                }else if (value instanceof NClob) {
                    map.put(colName,"<NClob>");
                }else if (value instanceof Clob) {
                    map.put(colName,"<Clob>");
                }else if (value instanceof Blob) {
                    map.put(colName,"<Blob>");
                }else {
                    map.put(colName,'<' + value.getClass().getName() + '>');
                }
            }
        }else{
            //如果不是，截取sql
            String paramStr = sql.substring(sql.indexOf("values") + 6,sql.length());
            String params = paramStr.substring(paramStr.indexOf("(") + 1,paramStr.indexOf(")"));
            if(params.indexOf("'") >= 0){
                params = params.replaceAll("'","");
            }
            String[] paramArray = params.split(",");
            for(int i = 0; i < paramArray.length; i++){
                map.put(cols[i].trim(),paramArray[i].trim());
            }
        }
        return map;
    }

    /**
     * TODO 解析delete语句，获取旧数据
     * @author 李利广
     * @Date 2019年04月12日 16:18:49
     * @param sql
     * @param isCompile 是否是预编译sql
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private static synchronized List<Map<String,Object>> analyseDeleteSql(String sql,Boolean isCompile, StatementProxy statement){
        List<Map<String,Object>> rows = new ArrayList<>();
        Map<String ,Object> map = new HashMap<>();
        int whereIndex = sql.indexOf("where");
        if(whereIndex >= 0){
            JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");
            //判断sql语句中是否有别名
            String bie = "";
            String tableName = getTableName(sql,"delete");
            bie = sql.substring(sql.indexOf(tableName) + tableName.length(),sql.indexOf("where"));
            if(bie != null && StringUtils.isNotBlank(bie.trim())){
                bie = bie.trim();
            }
            //获取类似“id='123' and title='234' or name='343'”的条件
            String paramStr = sql.substring(whereIndex + 5,sql.length());
            String querySql = "select * from " + tableName + " " + bie + " where " + paramStr;
            if(isCompile){
                //如果是预编译语句，从statement中获取条件
                String params = buildSlowParameters(statement);
                if(params.length() > 2){
                    params = params.substring(1, params.length()-1);
                    params = params.replaceAll("\"","");
                    String[] args = params.split(",");
                    rows = jdbcTemplate.queryForList(querySql,args);
                }
            }else{
                //如果是拼接sql则直接查询
                rows = jdbcTemplate.queryForList(querySql);
            }
        }
        return rows;
    }

    /**
     * TODO 解析update语句，获取旧数据
     * @author 李利广
     * @Date 2019年04月12日 16:18:49
     * @param sql
     * @param idName
     * @param isCompile 是否是预编译sql
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private static synchronized List<Map<String,Object>> analyseUpdateSqlOld(String sql,String idName,Boolean isCompile, StatementProxy statement){
        List<Map<String,Object>> rows = new ArrayList<>();
        //获取更新的字段("col1,col2,col3")带别名
        String cols = getUpdateCol(sql);
        //判断sql语句中是否有别名
        String bie = "";
        String tableName = getTableName(sql,"update");
        bie = sql.substring(sql.indexOf(tableName) + tableName.length(),sql.indexOf("set"));
        if(bie != null && StringUtils.isNotBlank(bie.trim())){
            bie = bie.trim();
        }
        //获取条件
        int whereIndex = sql.indexOf("where");
        if(whereIndex >= 0){
            JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");
            String paramStr = sql.substring(whereIndex + 5,sql.length());
            String querySql = "select "+ idName + "," + cols +" from " + tableName + " " + bie + " where " + paramStr;
            if(!isCompile){
                //如果是拼接sql则直接查询
                rows = jdbcTemplate.queryForList(querySql);
            }else{
                //如果是预编译语句，从statement中获取条件
                String params = buildSlowParameters(statement);
                int paraCount = 0;
                while (paramStr.indexOf("?") >= 0){
                    paraCount ++;
                    paramStr = paramStr.substring(paramStr.indexOf("?") + 1);
                }
                if(params.length() > 2){
                    params = params.substring(1, params.length()-1).replaceAll("\"","");
                    String[] args = params.split(",");
                    List<String> argList = Arrays.asList(args);
                    argList = argList.subList(argList.size() - paraCount,argList.size());
                    rows = jdbcTemplate.queryForList(querySql,argList.toArray());
                }
            }
        }
        return rows;
    }

    /**
     * TODO 解析update语句，获取新数据
     * @author 李利广
     * @Date 2019年04月12日 16:18:49
     * @param sql
     * @param isCompile 是否是预编译sql
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private static synchronized Map<String,Object> analyseUpdateSql(String sql,Boolean isCompile, StatementProxy statement){
        //update task_plan t set t.title = '123',t.note = '123' where t.id = '123'
        Map<String,Object> map = new HashMap<>();
        int startPos = sql.indexOf("set");
        int wherePos = sql.indexOf("where");
        String col,value = "";
        if(wherePos >= 0 && startPos >= 0){
            //t.title = '123',t.note = '123'
            String strUpdCon = sql.substring(startPos + 4,wherePos);
            String[] conArray = strUpdCon.split(",");
            for(int i = 0;i < conArray.length; i++){
                if(conArray[i].indexOf("=") >= 0){
                    if(isCompile){
                        //如果是预编译
                        //t.title = ?
                        String[] con = conArray[i].split("=");
                        //获取字段，处理别名
                        col = con[0].trim();
                        if(col.indexOf(".") > -1){
                            col = col.substring(col.indexOf(".") + 1,col.length());
                        }
                        //获取值
                        String params = buildSlowParameters(statement);
                        if(params.length() > 2){
                            params = params.substring(1, params.length()-1).replaceAll("\"","");
                            String[] args = params.split(",");
                            value = args[i];
                        }
                    }else{
                        //如果不是预编译
                        //t.title = '123'
                        String[] con = conArray[i].split("=");
                        //获取字段，处理别名
                        col = con[0].trim();
                        if(col.indexOf(".") > -1){
                            col = col.substring(col.indexOf(".") + 1,col.length());
                        }
                        //获取值,处理单引号
                        value = con[1].trim();
                        if(value.indexOf("'") >= 0){
                            value = value.replaceAll("'","");
                        }
                    }
                    map.put(col,value);
                }
            }
        }
        return map;
    }

    /**
     * TODO 分析update的sql语句，获取更新的字段(带别名的)，用于查询
     * @author 李利广
     * @Date 2019年04月13日 16:07:26
     * @param sql
     * @return java.lang.String 返回“col1,col2,col3”
     */
    private static synchronized String getUpdateCol(String sql){
        //update task_plan t set t.title = '123',t.note = '123' where t.id = '123'
        StringBuilder cols = new StringBuilder();
        String colStr = "";
        int startPos = sql.indexOf("set");
        int wherePos = sql.indexOf("where");
        if(wherePos >= 0 && startPos >= 0){
            //t.title = '123',t.note = '123'
            String strUpdCon = sql.substring(startPos + 4,wherePos);
            String[] conArray = strUpdCon.split(",");
            for(int i = 0;i < conArray.length; i++){
                //t.title = '123'
                String[] con = conArray[i].split("=");
                String col = con[0].trim();
                cols.append(col).append(",");
            }
        }
        if(cols.length() > 0){
            colStr = cols.substring(0,cols.length() - 1);
        }
        return colStr;
    }

    /**
     * TODO 比对新旧数据是否一样，一样返回true
     * @author 李利广
     * @Date 2019年04月15日 08:45:06
     * @param oldValue
     * @param newValue
     * @return java.lang.Boolean
     */
    private static Boolean compValue(Object oldValue ,Object newValue) throws Exception{
        Boolean isComp = false;
        if(oldValue == null && newValue == null){
            //两个都为null
            isComp = true;
        }else if(oldValue == null || newValue == null) {
            isComp = false;
            //只有一个为null
            if(oldValue == null){
                if("null".equalsIgnoreCase(newValue.toString()) || "".equals(newValue.toString())){
                    isComp = true;
                }
            }
            if(newValue == null){
                if("null".equalsIgnoreCase(oldValue.toString()) || "".equals(oldValue.toString())){
                    isComp = true;
                }
            }
        }else{
            //两个都不为null
            if (oldValue instanceof String) {
                isComp = oldValue.toString().equalsIgnoreCase(newValue.toString());
                if("".equals(oldValue.toString()) && "".equals(newValue.toString())){
                    isComp = true;
                }
                if("".equals(oldValue.toString()) && "null".equals(newValue.toString())){
                    isComp = true;
                }
                if("null".equals(oldValue.toString()) && "null".equals(newValue.toString())){
                    isComp = true;
                }
                if("null".equals(oldValue.toString()) && "".equals(newValue.toString())){
                    isComp = true;
                }
            }else if (oldValue instanceof Number) {
                isComp = ((Number) oldValue).floatValue() == ((Number) newValue).floatValue();
            }else if (oldValue instanceof java.util.Date) {
                isComp = ((Date) oldValue).compareTo((Date) newValue) == 0?true:false;
            }else if (oldValue instanceof Boolean) {
                isComp = ((Boolean) oldValue).compareTo((Boolean) newValue) == 0?true:false;
            }else if (oldValue instanceof InputStream) {
                byte[] b1=new byte[((InputStream) oldValue).available()];
                ((InputStream) oldValue).read(b1);
                String oldStr = new String(b1);
                byte[] b2=new byte[((InputStream) newValue).available()];
                ((InputStream) newValue).read(b2);
                String newStr = new String(b2);
                isComp = oldStr.equals(newStr);
            }else if (oldValue instanceof NClob) {
                InputStream oldStream = ((NClob) oldValue).getAsciiStream();
                byte[] b1=new byte[oldStream.available()];
                oldStream.read(b1);
                String oldStr = new String(b1);
                InputStream newStream = ((NClob) newValue).getAsciiStream();
                byte[] b2=new byte[newStream.available()];
                newStream.read(b2);
                String newStr = new String(b2);
                isComp = oldStr.equals(newStr);
            }else if (oldValue instanceof Clob) {
                InputStream newStream = ((Clob) newValue).getAsciiStream();
                byte[] b2=new byte[newStream.available()];
                newStream.read(b2);
                String newStr = new String(b2);
                InputStream oldStream = ((Clob) oldValue).getAsciiStream();
                byte[] b1=new byte[oldStream.available()];
                oldStream.read(b1);
                String oldStr = new String(b1);
                isComp = oldStr.equals(newStr);
            }else if (oldValue instanceof Blob) {
                InputStream newStream = ((Blob) newValue).getBinaryStream();
                byte[] b2=new byte[newStream.available()];
                newStream.read(b2);
                String newStr = new String(b2);
                InputStream oldStream = ((Blob) oldValue).getBinaryStream();
                byte[] b1=new byte[oldStream.available()];
                oldStream.read(b1);
                String oldStr = new String(b1);
                isComp = oldStr.equals(newStr);
            }else {
                isComp = false;
            }
        }
        return isComp;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * 用户真实IP为： 192.168.1.110
     * @param request HttpContext.getRequest();
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}