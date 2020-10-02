package com.sinosoft.sinoep.modules.mypage.oftenModel.services;

import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.modules.mypage.oftenModel.entity.OftenModule;
import com.sinosoft.sinoep.uias.model.SysRecourse;

public interface OftenModelService {

	/**
	 * 保存
	 * TODO
	 *
	 * @param model
	 * @return
	 * @author 马秋霞
	 * @Date 2018年5月18日 上午9:44:42
	 */
	public void save(String model);

	/**
	 * 根据用户id查询该用户创建的常用模块
	 * TODO
	 *
	 * @param userId
	 * @return
	 * @author 马秋霞
	 * @Date 2018年5月23日 上午8:51:52
	 */
	List<OftenModule> findByUserId() throws Exception;

	void delete(String id);

	void sort(String[] ids);

}
