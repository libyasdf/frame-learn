package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.util;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;

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
	public static Integer getSort(DataDictionarys dic){
		StringBuilder sb = new StringBuilder();
		sb.append("select max(t.sort) from xxkh_tree t where ");
		if("1".equals(dic.getType())){
			sb.append(" ' and t.type = '"+dic.getType()+"' and t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
		}else if("0".equals(dic.getType())){
			sb.append(" t.pid = '"+dic.getpId()+"' and t.type = '"+dic.getType()+"' and t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
		}
		Integer sort = jdbcTemplate.queryForObject(sb.toString(),Integer.class);
		return (sort == null) ? 0 : (sort+1);
	}
}
