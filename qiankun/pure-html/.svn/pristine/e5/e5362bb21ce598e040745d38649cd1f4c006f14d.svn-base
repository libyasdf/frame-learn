package com.sinosoft.sinoep.modules.dynamicregulation.service.impl;

import com.sinosoft.sinoep.modules.dynamicregulation.dao.DynamicRegulationDao;
import com.sinosoft.sinoep.modules.dynamicregulation.service.DynamicRegulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class DynamicRegulationServiceImpl implements DynamicRegulationService {
  private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DynamicRegulationDao dynamicRegulationDao;
    @Autowired
    private JdbcTemplate jdbc;
    @Override
    public String tableLine(String tablename) throws Exception{
        return "[1,2]";
    }

    //表的结构
    @Override
    public List<Map<String, Object>> tableLineD(String tablename) throws Exception{

        String getNewEntitySql ="\tselect d.*,a.COLUMN_NAME as COLNAME\n" +
                "\tfrom  (SELECT COLUMN_NAME from sys.all_tab_cols where Table_Name=? ) a\n" +
                "\tLEFT JOIN tjxm.DYNAMIC_REGULATION d\n" +
                "\tON a.COLUMN_NAME=d.COLUMN_NAME AND d.TABLE_NAME=?\n" +
                "\tWHERE d.COLUMN_VISIBLE=1\tOR d.COLUMN_VISIBLE is null\t\n" +
                "\tORDER BY d.SEQUENCE_NUM";
        List<Map<String, Object>> list =jdbc.queryForList(getNewEntitySql,tablename,tablename);

        return list;
    }


}