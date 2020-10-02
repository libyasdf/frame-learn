package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.util;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;

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
	public static Integer getSort(DataDictionarys dic){
		StringBuilder sb = new StringBuilder();
		List<Object> para = new ArrayList<>();
		sb.append("select max(t.sort) from xxkh_tree t where ");
		if("1".equals(dic.getType())){
			sb.append(" ' and t.type = ? and t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
			para.add(dic.getType());
		}else if("0".equals(dic.getType())){
			sb.append(" t.pid = ? and t.type = ? and t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
			para.add(dic.getpId());
			para.add(dic.getType());
		}
		Integer sort = jdbcTemplate.queryForObject(sb.toString(),Integer.class,para.toArray());
		return (sort == null) ? 0 : (sort+1);
	}
}
