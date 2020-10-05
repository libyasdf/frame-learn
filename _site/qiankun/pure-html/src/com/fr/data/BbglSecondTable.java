package com.fr.data;

import com.fr.script.Calculator;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings({ "serial","unused" })
public class BbglSecondTable extends DefinedSubmitJob {

	private JobValue ID;
    private JobValue CRE_TIME;
	private JobValue ORDER_NUMBER;
	private JobValue FULL_NAME;
	private JobValue SEX;
    private JobValue NATION;
    private JobValue BIRTHDAY;
    private JobValue WORKUNIT;
    private JobValue TIME_JOIN_PARTY;
    private JobValue RECOVERY_MEMBERSHIP;

    /**
     * 保存/修改报表数据
     * @param calculator
     * @throws Exception
     */
    public void doJob(Calculator calculator) throws Exception {
        Connection coon = null;
        PreparedStatement pstmt = null;
    	try {
            coon = getConnection();
            String creTime = DateUtil.COMMON_FULL.getDateText(new Date());
            String id = "";
            String idParam = ID.getValue().toString();
            if(StringUtils.isNotBlank(idParam)){
                id = (String)idParam;
                StringBuilder sql = new StringBuilder(" update DDJS_BBGL_SECOND_TABLE set ");
                sql.append(" ORDER_NUMBER ='").append((String)ORDER_NUMBER.getValue()).append("',");
                sql.append(" FULL_NAME ='").append((String)FULL_NAME.getValue()).append("',");
                sql.append(" SEX ='").append((String)SEX.getValue()).append("',");
                sql.append(" NATION ='").append((String)NATION.getValue()).append("',");
                sql.append(" RECOVERY_MEMBERSHIP ='").append((String)RECOVERY_MEMBERSHIP.getValue()).append("',");
                sql.append(" BIRTHDAY ='").append((String)BIRTHDAY.getValue()).append("',");
                sql.append(" WORKUNIT ='").append((String)WORKUNIT.getValue()).append("',");
                sql.append(" TIME_JOIN_PARTY ='").append((String)TIME_JOIN_PARTY.getValue()).append("'");
                sql.append(" where id='").append(id).append("' ");
                pstmt = coon.prepareStatement(sql.toString());
            }else{
                id = UUID.randomUUID().toString();
                String filedName = "ID,CRE_USER_ID,CRE_USER_NAME,CRE_DEPT_ID,CRE_DEPT_NAME,CRE_CHUSHI_ID,CRE_CHUSHI_NAME,CRE_JU_ID,CRE_JU_NAME," +
                        "CRE_TIME,ORDER_NUMBER,FULL_NAME,SEX,NATION," +
                        "BIRTHDAY,WORKUNIT,TIME_JOIN_PARTY,RECOVERY_MEMBERSHIP";
                StringBuilder values = new StringBuilder("");
                values.append("'").append(id).append("',");
                values.append("'").append(UserUtil.getCruUserId()).append("',");
                values.append("'").append(UserUtil.getCruUserName()).append("',");
                values.append("'").append(UserUtil.getCruDeptId()).append("',");
                values.append("'").append(UserUtil.getCruDeptName()).append("',");
                values.append("'").append(UserUtil.getCruChushiId()).append("',");
                values.append("'").append(UserUtil.getCruChushiName()).append("',");
                values.append("'").append(UserUtil.getCruJuId()).append("',");
                values.append("'").append(UserUtil.getCruJuName()).append("',");
                values.append("'").append(creTime).append("',");
                values.append("'").append((String)ORDER_NUMBER.getValue()).append("',");
                values.append("'").append((String)FULL_NAME.getValue()).append("',");
                values.append("'").append((String)SEX.getValue()).append("',");
                values.append("'").append(NATION.getValue().toString()).append("',");
                values.append("'").append((String)BIRTHDAY.getValue()).append("',");
                values.append("'").append((String)WORKUNIT.getValue()).append("',");
                values.append("'").append((String)TIME_JOIN_PARTY.getValue()).append("',");
                values.append("'").append((String)RECOVERY_MEMBERSHIP.getValue()).append("'");
                StringBuilder sql = new StringBuilder(" insert into DDJS_BBGL_SECOND_TABLE (");
                sql.append(filedName).append(") values (").append(values).append(") ");
                pstmt = coon.prepareStatement(sql.toString());
            }
           pstmt.execute();
        } catch (Exception e) {  
            e.printStackTrace();  
            System.out.println("自定义报表提交失败========");
        }finally{
            this.free(pstmt,coon);
        	System.out.println("自定义报表提交结束========");
        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@192.168.90.137:1521:sinoepuias";
        String user = "tjxm";
        String pass = "tjxm";
        Connection conn = null;
        try{
             Class.forName(driver);
             conn = DriverManager.getConnection(url, user, pass);
             System.out.println("数据库连接成功========");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库连接失败========");
        }
        return conn;
    }

    /**
     * 释放资源
     */
    public static void free(PreparedStatement stat,Connection conn){
        // 释放资源，关闭连接
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
