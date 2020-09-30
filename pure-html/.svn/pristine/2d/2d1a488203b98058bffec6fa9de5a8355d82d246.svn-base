package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtHjxj;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.entity.DwxtLdcy;

import java.util.List;


public interface DwxtHjxjService {

	/**
	 * 查询换届选举列表
	 * @param pageImpl
	 * @return
	 * @throws Exception
	 */
	PageImpl getPageList(PageImpl pageImpl,DwxtHjxj dwxtHjxj,String timeRange);

	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	DwxtHjxj findOne(String id);

	/**
	 * 保存换届信息
	 * @param dwxtHjxj
	 * @return
	 */
	DwxtHjxj save(DwxtHjxj dwxtHjxj);

	/**
	 * 修改换届信息
	 * @param dwxtHjxj
	 * @return
	 */
	DwxtHjxj update(DwxtHjxj dwxtHjxj);

	/**
	 * 删除党组织
	 * @param id
	 * @return
	 */
	int delete(String id);

	/**
	 * 删除党组织
	 * @param
	 * @return
	 */
	PageImpl ldcyList(DwxtLdcy dwxtLdcy,PageImpl pageImpl);

	/**
	 * 保存换届信息
	 * @param dwxtLdcy
	 * @return
	 */
	DwxtLdcy saveLdcy(DwxtLdcy dwxtLdcy);

	/**
	 * 修改换届信息
	 * @param dwxtLdcy
	 * @return
	 */
	DwxtLdcy updateLdcy(DwxtLdcy dwxtLdcy);

	/**
	 * 查询领导成员
	 * @param id
	 * @return
	 */
	DwxtLdcy findOneLdcy(String id);

	/**
	 * 删除领导成员
	 * @param id
	 * @return
	 */
	int deleteLdcy(String id);

	/**
	 * 离任
	 * @param id
	 * @return
	 */
	int outTenure(String id);

	/**
	 * 查询人员党内职务
	 * @param userId
	 * @return
	 */
	List<DwxtLdcy> findByUserId(String userId);
	/**
	 * 新增换届选举时判断该人员是否已经为领导人
	 * TODO
	 * @author 李帅
	 * @Date 2018年11月16日
	 * @param
	 * @param
	 * @return
	 */
	public DwxtLdcy  queryLdcy(String hjxjId, String leaderId);
	/**
	 * 对领导成员进行离任
	 * TODO
	 * @author 李帅
	 * @Date 2018年11月16日
	 * @param
	 * @param
	 * @return
	 */
	public DwxtLdcy updateLeave(DwxtLdcy dwxtLdcy);
	/**
	 * 查询本届是否有领导成员
	 * TODO
	 * @author 李帅
	 * @Date 2018年11月21日
	 * @param
	 * @param
	 * @return
	 */
	public  DwxtLdcy queryLeave(String hjxjId);

}
