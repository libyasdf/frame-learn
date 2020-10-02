package com.sinosoft.sinoep.modules.system.config.honor.services;

import com.sinosoft.sinoep.modules.system.config.honor.entity.Honor;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.config.honor.entity.HonorDetails;

import java.util.List;

public interface HonorService {

	/**
	 * 查询列表
	 * @param pageImpl
	 * @param honor
	 * @return
	 */
	PageImpl getPageList(PageImpl pageImpl, Honor honor);

	Honor findByVisibleAndYear(String year);

	Honor saveHonor(String year);

	List<HonorDetails> findByHonor_Id(String honorId);

	HonorDetails editHonorDetails(HonorDetails honorDetails);

	void changeNumber(String id, Integer number, String exchangeId, Integer exchangeNumber);

	List<Honor> getList(Honor honor);

	/**
	 * 根据id删除一条记录
	 * TODO
	 * @author 马秋霞
	 * @Date 2018年5月31日 下午1:31:46
	 * @param id
	 * @return
	 */
	int delete(String id);


	int deleteDetails(String id);

	int updatePublish(String publish, String id);

}
