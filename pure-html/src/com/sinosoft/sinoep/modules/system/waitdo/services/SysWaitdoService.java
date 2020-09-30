package com.sinosoft.sinoep.modules.system.waitdo.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.waitdo.entity.SysWaitdo;

/**
 * TODO 系统待办service业务层接口
 * @author 李利广
 * @Date 2018年5月25日 上午10:14:52
 */
public interface SysWaitdoService {
	
	/**
	 * TODO 查询系统待办列表
	 * @author 李利广
	 * @Date 2018年5月25日 下午2:56:33
	 * @param pageImpl
	 * @param workFlowId
	 * @param fileTypeId
	 * @param startDate
	 * @param endDate
	 * @param title
	 * @return
	 */
	public PageImpl getWaitdoList(PageImpl pageImpl,String workFlowId,String fileTypeId,String startDate,String endDate,String title) throws Exception;

	/**
	 * 根据ID查询一条待办
	 * @param id
	 * @return
	 */
	SysWaitdo getById(String id);

}
