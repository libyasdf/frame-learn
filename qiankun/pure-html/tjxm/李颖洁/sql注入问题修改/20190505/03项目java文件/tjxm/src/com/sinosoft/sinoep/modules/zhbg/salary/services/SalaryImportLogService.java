package com.sinosoft.sinoep.modules.zhbg.salary.services;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.salary.entity.SalaryImportLog;

public interface SalaryImportLogService {
	
	/**
	 * 分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月30日 上午10:34:02
	 * @param pageable
	 * @param pageImpl
	 * @param name
	 * @param yearMonth
	 * @return
	 */
	PageImpl getlistBootHql(Pageable pageable, PageImpl pageImpl, String name, String yearMonth,String personId);
	
	/**
	 * 
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 上午11:34:31
	 * @param id
	 * @return
	 */
	SalaryImportLog getById(String id);

}
