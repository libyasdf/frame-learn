package com.sinosoft.sinoep.modules.system.config.bbkzversion.service.impl;


import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.Help;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.ReturnClass;
import com.sinosoft.sinoep.modules.system.config.bbkzversion.service.BbkzVersionServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 子火
 * @Date 2019年4月10日09:38:20
 */

@Service("bbkzVersionService")
@Transactional(rollbackFor = Exception.class)
public class BbkzVersionServiceImpl implements BbkzVersionServiceI {
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public ReturnClass getVersion()throws Exception{
		String sql="SELECT * FROM BBKZ_VERSION";
		List<Map<String, Object>> list=jdbc.queryForList(sql);
		if(list.size()>0){
			return Help.returnClassT(200,"查询成功",list.get(0).get("VERSION"));
		}else{
			return Help.returnClassT(300,"无查询结果","无查询结果");
		}
	}

 
	
}