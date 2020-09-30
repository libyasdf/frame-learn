package com.sinosoft.sinoep.modules.dagl.daly.change.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.daly.change.entity.QTwoChange;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Question;

/**
 * 
 * Q2变更service
 * @author 王磊
 * @Date 2018年11月24日 上午10:42:02
 */
public interface QTwoChangeService {
	
	/**
	 * 保存变更记录
	 * @author 王磊
	 * @Date 2018年11月24日 上午10:44:15
	 * @param qTwoChangeList
	 * @return
	 */
	void saveForm(QTwoChange qTwoChange);
	
	/**
	 * 
	 * @param list
	 * @author 颜振兴
	 * void 修改列表数据
	 * TODO
	 */
	int updateForm(QTwoChange twoChange);

	/**
	 * 查询变更列表（带分页）
	 * @author 王磊
	 * @Date 2018年11月24日 上午10:55:36
	 * @param pageable
	 * @param pageImpl
	 * @param qTwoChange
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl,String timeRange,String changeNo,String folderNo);
	
	/**
	 * 根据pid查询本次变更记录
	 * @author 王磊
	 * @Date 2018年11月24日 上午10:56:49
	 * @param List<QTwoChange>
	 * @return
	 */
	QTwoChange findByPid(String pid);
	
	/**
	 * 
	 * @param ids
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 通过表明和id查数据
	 */
	List<Map<String, Object>> findListByIds(String ids,String tName);

	/**
	 * @Author 王富康
	 * @Description //TODO 查询变更数量生成流水号
	 * @Date 2019/1/31 17:06
	 * @Param []
	 * @return java.lang.String
	 **/
	String findChangeConut();
}
