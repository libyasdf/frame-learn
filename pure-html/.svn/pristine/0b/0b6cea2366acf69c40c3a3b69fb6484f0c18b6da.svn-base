package com.sinosoft.sinoep.modules.system.config.dictionary.util;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 
 * @author 周俊林
 * @Date 2018年3月15日 下午8:43:28
 */
public class DictionaryUtil {

//	private static Logger log = LoggerFactory.getLogger(AffixUtil.class);
	private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");

	/**
	 * 生成排序号
	 * @param dic
	 * @return
	 */
	public static Integer getSort(DataDictionary dic){
		StringBuilder sb = new StringBuilder();
		List listzwf=new ArrayList();
		sb.append("select max(t.sort) from sys_data_dictionary t where ");
		if("1".equals(dic.getType())){
			sb.append(" t.mark = ? and t.type = ? and t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
			listzwf.add(dic.getMark());
			listzwf.add(dic.getType());
		}else if("0".equals(dic.getType())){
			sb.append(" t.pid = ? and t.type =? and t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
			listzwf.add(dic.getpId());
			listzwf.add(dic.getType());
		}
		Integer sort = jdbcTemplate.queryForObject(sb.toString(),Integer.class,listzwf.toArray());
		return (sort == null) ? 0 : (sort+1);
	}
}
