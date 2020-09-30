package com.sinosoft.sinoep.modules.system.config.honor.util;

import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class HonorUtil {

    private static JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringBeanUtils.getBean("jdbcTemplate");


    public static Integer getMaxNumber(String honorId,String featsType){
        StringBuffer sb = new StringBuffer();
        sb.append("select max(t.order_number) from sys_honor_details t where honor_id = '"+ honorId +"' and feats_type='"+ featsType +"'");
        Integer number = jdbcTemplate.queryForObject(sb.toString(),Integer.class);
        return (number == null) ? 1 : (number+1);
    }

}
