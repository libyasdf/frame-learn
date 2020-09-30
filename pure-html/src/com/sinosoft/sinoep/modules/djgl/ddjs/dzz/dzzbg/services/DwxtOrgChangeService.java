package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzbg.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzbg.entity.DwxtOrgChange;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtHjxj;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtLdcy;

import java.util.List;


public interface DwxtOrgChangeService {

	/**
	 * 查询变更信息列表
	 * @param pageImpl
	 * @return
	 */
	PageImpl getPageList(PageImpl pageImpl, DwxtOrgChange dwxtOrgChange, String timeRange);

	/**
	 * 保存变更信息
	 * @param dwxtOrgChange
	 * @return
	 */
	DwxtOrgChange save(DwxtOrgChange dwxtOrgChange,String code);

	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	DwxtOrgChange findOne(String id);
}
