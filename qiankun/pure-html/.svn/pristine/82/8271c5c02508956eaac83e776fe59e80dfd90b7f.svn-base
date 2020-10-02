package com.sinosoft.sinoep.modules.system.config.toggle.dao;


import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.system.config.toggle.entity.SysToggle;


/**
 * 
 * TODO DAO层
 * @author 王富康
 * @Date 2018年5月11日 上午11:21:30
 */
public interface SysToggleDao extends BaseRepository<SysToggle, String>{
	
	@Query(" from SysToggle t where t.key = ?1 and t.state != 3 ")
	public List<SysToggle> getToggleByKey(String key);

	public List<SysToggle> findByKey(String key);
	
}
