package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.services;


import org.springframework.data.domain.Page;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity.DataTable;

public interface DataTableService {
	/**
	 * 
	 * @param dt
	 * @param pageImpl
	 * @return
	 * @throws Exception
	 * @author 颜振兴
	 * Page<DataTable>
	 * TODO 获取资料列表
	 */
	Page<DataTable> list(DataTable dt,PageImpl pageImpl) throws Exception;
	
	/**
	 * 
	 * @param dt
	 * @return
	 * @author 颜振兴
	 * DataTable
	 * TODO 添加一条资料
	 */
	DataTable save(DataTable dt);
	
	/**
	 * 
	 * @param dt
	 * @return
	 * @author 颜振兴
	 * DataTable
	 * TODO 修改一条资料
	 */
	DataTable upDataTable(DataTable dt);
	
	/**
	 * 
	 * @param id
	 * @return
	 * @author 颜振兴
	 * int
	 * TODO 删除一条资料
	 */
	int  delete(String id);
	
	/**
	 * 
	 * @param id
	 * @return
	 * @author 颜振兴
	 * DataTable
	 * TODO 获取一条资料的详细信息
	 */
	DataTable getOne(String id);
	
}
