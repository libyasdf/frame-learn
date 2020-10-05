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
public class BbglFirstTable extends DefinedSubmitJob {

	private JobValue ID;
    private JobValue CRE_TIME;
	private JobValue BEFORE_YEAR_TOTAL;
	private JobValue THIS_YEAR_ADD;
	private JobValue DEVELOPMENT_MEMBER;
    private JobValue REJOIN_HE_PARTY;
    private JobValue RECOVERY_MEMBERSHIP;
    private JobValue STOPAFTER_REC_MEMSHIP;
    private JobValue TRANSFER_INTO_RELATIONS;
    private JobValue TRANSFER_SYSTEM;
    private JobValue THIS_YEAR_REMOVE;
    private JobValue OUT_PARTY;
    private JobValue STOP_MEMBERSHIP;
    private JobValue DEATH;
    private JobValue TURN_OUT_RELATIONS;
    private JobValue TURN_OUT_TRANSFER_SYSTEM;
    private JobValue END_YEAR_DUE_NUMBER;
    private JobValue END_YEAR_REAL_NUMBER;
    private JobValue DUE_AND_REAL_DIFFERENCE;
    private JobValue LETTER_OF_INTRODUCTION_NUMBER;
    private JobValue STATUS;

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
            String filedName = "ID,CRE_USER_ID,CRE_USER_NAME,CRE_DEPT_ID,CRE_DEPT_NAME,CRE_CHUSHI_ID,CRE_CHUSHI_NAME,CRE_JU_ID,CRE_JU_NAME," +
                    "cre_time,before_year_total,this_year_add,development_member,Rejoin_he_party," +
                    "recovery_membership,stopafter_rec_memship,Transfer_into_relations,Transfer_system," +
                    "this_year_remove,Out_party,stop_membership,death,Turn_out_relations,turn_out_transfer_system," +
                    "end_year_Due_number,end_year_real_number,Due_and_real_difference,Letter_of_introduction_number,status";
            if(StringUtils.isNotBlank(idParam) &&  !"1".equals(STATUS.getValue().toString())){
                id = idParam;
                StringBuilder sql = new StringBuilder(" update DDJS_BBGL_FIRST_TABLE set ");
                sql.append(" BEFORE_YEAR_TOTAL ='").append(BEFORE_YEAR_TOTAL.getValue().toString()).append("',");
                sql.append(" THIS_YEAR_ADD ='").append(THIS_YEAR_ADD.getValue().toString()).append("',");
                sql.append(" DEVELOPMENT_MEMBER ='").append(DEVELOPMENT_MEMBER.getValue().toString()).append("',");
                sql.append(" REJOIN_HE_PARTY ='").append(REJOIN_HE_PARTY.getValue().toString()).append("',");
                sql.append(" RECOVERY_MEMBERSHIP ='").append(RECOVERY_MEMBERSHIP.getValue().toString()).append("',");
                sql.append(" STOPAFTER_REC_MEMSHIP ='").append(STOPAFTER_REC_MEMSHIP.getValue().toString()).append("',");
                sql.append(" TRANSFER_INTO_RELATIONS ='").append(TRANSFER_INTO_RELATIONS.getValue().toString()).append("',");
                sql.append(" TRANSFER_SYSTEM ='").append(TRANSFER_SYSTEM.getValue().toString()).append("',");
                sql.append(" THIS_YEAR_REMOVE ='").append(THIS_YEAR_REMOVE.getValue().toString()).append("',");
                sql.append(" OUT_PARTY ='").append(OUT_PARTY.getValue().toString()).append("',");
                sql.append(" STOP_MEMBERSHIP ='").append(STOP_MEMBERSHIP.getValue().toString()).append("',");
                sql.append(" DEATH ='").append(DEATH.getValue().toString()).append("',");
                sql.append(" TURN_OUT_RELATIONS ='").append(TURN_OUT_RELATIONS.getValue().toString()).append("',");
                sql.append(" TURN_OUT_TRANSFER_SYSTEM ='").append(TURN_OUT_TRANSFER_SYSTEM.getValue().toString()).append("',");
                sql.append(" END_YEAR_DUE_NUMBER ='").append(END_YEAR_DUE_NUMBER.getValue().toString()).append("',");
                sql.append(" END_YEAR_REAL_NUMBER ='").append(END_YEAR_REAL_NUMBER.getValue().toString()).append("',");
                sql.append(" DUE_AND_REAL_DIFFERENCE ='").append(DUE_AND_REAL_DIFFERENCE.getValue().toString()).append("',");
                sql.append(" LETTER_OF_INTRODUCTION_NUMBER ='").append(LETTER_OF_INTRODUCTION_NUMBER.getValue().toString()).append("'");
                sql.append(" where id='").append(id).append("' ");
                pstmt = coon.prepareStatement(sql.toString());
            }else{
                id = UUID.randomUUID().toString();
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
                values.append("'").append(BEFORE_YEAR_TOTAL.getValue().toString()).append("',");
                values.append("'").append(THIS_YEAR_ADD.getValue().toString()).append("',");
                values.append("'").append(DEVELOPMENT_MEMBER.getValue().toString()).append("',");
                values.append("'").append(REJOIN_HE_PARTY.getValue().toString()).append("',");
                values.append("'").append(RECOVERY_MEMBERSHIP.getValue().toString()).append("',");
                values.append("'").append(STOPAFTER_REC_MEMSHIP.getValue().toString()).append("',");
                values.append("'").append(TRANSFER_INTO_RELATIONS.getValue().toString()).append("',");
                values.append("'").append(TRANSFER_SYSTEM.getValue().toString()).append("',");
                values.append("'").append(THIS_YEAR_REMOVE.getValue().toString()).append("',");
                values.append("'").append(OUT_PARTY.getValue().toString()).append("',");
                values.append("'").append(STOP_MEMBERSHIP.getValue().toString()).append("',");
                values.append("'").append(DEATH.getValue().toString()).append("',");
                values.append("'").append(TURN_OUT_RELATIONS.getValue().toString()).append("',");
                values.append("'").append(TURN_OUT_TRANSFER_SYSTEM.getValue().toString()).append("',");
                values.append("'").append(END_YEAR_DUE_NUMBER.getValue().toString()).append("',");
                values.append("'").append(END_YEAR_REAL_NUMBER.getValue().toString()).append("',");
                values.append("'").append(DUE_AND_REAL_DIFFERENCE.getValue().toString()).append("',");
                values.append("'").append(LETTER_OF_INTRODUCTION_NUMBER.getValue().toString()).append("',");
                values.append("'2'");
                StringBuilder sql = new StringBuilder(" insert into DDJS_BBGL_FIRST_TABLE (");
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
