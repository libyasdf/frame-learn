package com.sinosoft.sinoep.modules.dagl.daly.change.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.dagl.daly.change.entity.QTwoChange;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;

/**
 * 
 * Q2变更dao
 * @author 王磊
 * @Date 2018年11月24日 上午10:38:52
 */
public interface QTwoChangeDao extends BaseRepository<QTwoChange,String>{
	
	/**
	 * 根据pid查找本次变更记录
	 * @author 王磊
	 * @Date 2018年11月24日 上午11:40:01
	 * @param id
	 * @return
	 */
	@Query(" from QTwoChange t where t.pid = ?1")
	List<QTwoChange> getByPid(String pid);
}
