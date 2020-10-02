package com.sinosoft.sinoep.modules.system.config.dictionary.dao;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataDictionaryDaoImpl implements DataDictionaryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询该类型下的所有子孙类型
     * @param id
     * @return
     */
    @Override
    public List<DataDictionary> getAllChildTypes(String id){
        List<String> params = new ArrayList<>();
        String sql = "select * from sys_data_dictionary t where t.visible = ? and t.type = '0'"
               + " start with t.id = ? connect by prior id = pid order by t.sort";
        params.add(CommonConstants.VISIBLE[1]);
        params.add(id);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataDictionary.class),params.toArray());
    }

    /**
     * 逻辑删除该类型下的所有子孙类型
     * @param id
     * @return
     */
    @Override
    public int delTypeVisible(String id){
        String sql = "update sys_data_dictionary t set t.visible = '"+ CommonConstants.VISIBLE[0]+"' where t.id in ("
                + "select d.id from sys_data_dictionary d start with d.id = ? connect by prior d.id = d.pid )";
        return jdbcTemplate.update(sql,id);
    }

}
