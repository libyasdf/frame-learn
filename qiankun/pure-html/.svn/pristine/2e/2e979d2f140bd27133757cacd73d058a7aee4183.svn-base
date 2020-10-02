package com.sinosoft.sinoep.modules.djgl.ddjs.bbgl.service;

import com.sinosoft.sinoep.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 报表管理Service
 */
@Service
public class BbglServiceImpl implements BbglService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String save(String tableName) {
        String  status = null;
        if("one".equals(tableName)){
            status = this.saveOneTable();
        }
        return status;
    }

    /**
     * 保存历史统计数据
     */
    private String  saveOneTable() {
        Calendar date = Calendar.getInstance();
        int yearInt = date.get(Calendar.YEAR);
        String status = "1";
        String sql2 = "select count(*) from DDJS_BBGL_FIRST_TABLE s where s.cre_time like '"+ String.valueOf(yearInt) + "%' and s.status ='1' ";
        int count = this.jdbcTemplate.queryForObject(sql2,Integer.class);
        if(count == 0){

            String THIS_YEAR_ADD="", DEVELOPMENT_MEMBER="",REJOIN_HE_PARTY="",RECOVERY_MEMBERSHIP="",STOPAFTER_REC_MEMSHIP="",TRANSFER_INTO_RELATIONS = "";
            String TRANSFER_SYSTEM="",THIS_YEAR_REMOVE="",OUT_PARTY="",STOP_MEMBERSHIP="",DEATH="",TURN_OUT_RELATIONS="";
            String TURN_OUT_TRANSFER_SYSTEM="",END_YEAR_DUE_NUMBER="",END_YEAR_REAL_NUMBER="",DUE_AND_REAL_DIFFERENCE="",LETTER_OF_INTRODUCTION_NUMBER="";
            String filedName = "ID,cre_time,before_year_total,this_year_add,development_member,Rejoin_he_party," +
                    "recovery_membership,stopafter_rec_memship,Transfer_into_relations,Transfer_system," +
                    "this_year_remove,Out_party,stop_membership,death,Turn_out_relations,turn_out_transfer_system," +
                    "end_year_Due_number,end_year_real_number,Due_and_real_difference,Letter_of_introduction_number,status";
            String id = UUID.randomUUID().toString();
            String creTime = DateUtil.COMMON_FULL.getDateText(new Date());
            //上年底总数
            String sql1 = "select count(*) from ddjs_dygl_userbasicinfo s, ddjs_dygl_partybasicinfo t where s.id = t.partybasicinfo_super_id and t.join_party_time like '"+String.valueOf(yearInt-1) + "%'";
            int beoreYear =  this.jdbcTemplate.queryForObject(sql1,Integer.class);
            //发展党员
            String sql3 = "select count(*) from ddjs_dygl_userbasicinfo s, ddjs_dygl_partybasicinfo t where s.id = t.partybasicinfo_super_id and  s.type_of_personnel like '04%' and t.join_party_time like '"+String.valueOf(yearInt) + "%'";
            int deveInt = this.jdbcTemplate.queryForObject(sql3,Integer.class);
            //转入组织关系
            String sql4 = "select count(*)from ddjs_dygl_around s where s.turn_around_time like '"+String.valueOf(yearInt) + "%'";
            int arountInt = this.jdbcTemplate.queryForObject(sql4,Integer.class);
            THIS_YEAR_ADD = String.valueOf(arountInt);
            DEVELOPMENT_MEMBER = String.valueOf(deveInt);
            TRANSFER_INTO_RELATIONS = String.valueOf(arountInt);

            //转出组织关系
            String sql5 = "select count(*)from ddjs_dygl_turnout s where s.turn_out_time like '"+String.valueOf(yearInt) + "%'";
            int outInt = this.jdbcTemplate.queryForObject(sql5,Integer.class);
            TURN_OUT_RELATIONS = String.valueOf(outInt);
            //本年增加
            int thisYearAddInt = deveInt + arountInt;

            //本年减少
            THIS_YEAR_REMOVE =TURN_OUT_RELATIONS;
            //本年底应有数
            int DueInt = beoreYear + thisYearAddInt - outInt;
            END_YEAR_DUE_NUMBER = String.valueOf(DueInt);

            String BEFORE_YEAR_TOTAL = String.valueOf(beoreYear);
            StringBuilder values = new StringBuilder("");
            values.append("'").append(id).append("',");
            values.append("'").append(creTime).append("',");
            values.append("'").append(BEFORE_YEAR_TOTAL).append("',");
            values.append("'").append(THIS_YEAR_ADD).append("',");
            values.append("'").append(DEVELOPMENT_MEMBER).append("',");
            values.append("'").append(REJOIN_HE_PARTY).append("',");
            values.append("'").append(RECOVERY_MEMBERSHIP).append("',");
            values.append("'").append(STOPAFTER_REC_MEMSHIP).append("',");
            values.append("'").append(TRANSFER_INTO_RELATIONS).append("',");
            values.append("'").append(TRANSFER_SYSTEM).append("',");
            values.append("'").append(THIS_YEAR_REMOVE).append("',");
            values.append("'").append(OUT_PARTY).append("',");
            values.append("'").append(STOP_MEMBERSHIP).append("',");
            values.append("'").append(DEATH).append("',");
            values.append("'").append(TURN_OUT_RELATIONS).append("',");
            values.append("'").append(TURN_OUT_TRANSFER_SYSTEM).append("',");
            values.append("'").append(END_YEAR_DUE_NUMBER).append("',");
            values.append("'").append(END_YEAR_REAL_NUMBER).append("',");
            values.append("'").append(DUE_AND_REAL_DIFFERENCE).append("',");
            values.append("'").append(LETTER_OF_INTRODUCTION_NUMBER).append("',");
            values.append("'1'");
            StringBuilder sql = new StringBuilder(" insert into DDJS_BBGL_FIRST_TABLE ( ");
            sql.append(filedName).append(") values (").append(values).append(") ");
            this.jdbcTemplate.execute(sql.toString());
        }else{
            status = "2";
        }
        return status;
    }
}
