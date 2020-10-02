package com.sinosoft.sinoep.modules.video.background.common.util;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.info.xxfb.common.InfoConstants;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;

/**
 * 
 * TODO 
 * @author 李利广
 * @Date 2018年9月21日 上午10:17:32
 */
public class InfoUtils {
	
	private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");
	
	/**
	 * 获取栏目是否有发布范围
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月21日 上午10:20:50
	 * @return
	 * @throws Exception
	 */
	public static Boolean getAuthList(String columnCode) throws Exception{
		boolean isFbfw = false;
		StringBuilder sBuilder = new StringBuilder("");
		sBuilder.append("select t.id id,t.is_fbfw isFbfw from info_column t where t.column_code = '"+columnCode+"'");
		List<InfoColumn> columnList = jdbcTemplate.query(sBuilder.toString(), new BeanPropertyRowMapper<InfoColumn>(InfoColumn.class));
		if(!columnList.isEmpty()){
			if (InfoConstants.IS_FBFW[1].equals(columnList.get(0).getIsFbfw())) {
				isFbfw = true;
			}
		}
		return isFbfw;
	}

}
