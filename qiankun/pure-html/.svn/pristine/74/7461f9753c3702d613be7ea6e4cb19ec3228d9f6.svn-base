package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtRevoke;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtUnit;

import java.util.List;
import java.util.Map;


public interface DwxtOrgService {

	/**
	 * 查询编码下所有子孙节点
	 * @param dwxtOrg
	 * @return
	 * @throws Exception
	 */
	List<DwxtOrg> findChildVal(DwxtOrg dwxtOrg,PageImpl pageImpl);
	/**
	 * 查询编码下所有子孙节点
	 * @param dwxtOrg
	 * @return
	 * @throws Exception
	 */
	List<DwxtOrg> findChild(DwxtOrg dwxtOrg);
	/**
	 * 获取党组织TREE
	 * @param dwxtOrg
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getTree(DwxtOrg dwxtOrg);

	/**
	 * 获取党支部下的党员
	 * @param partyBranchId
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getOrgUserTree(String partyBranchId,String id);

	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	DwxtOrg findOne(String id);

	/**
	 * 保存党组织
	 * @param dwxtOrg
	 * @return
	 */
	DwxtOrg save(DwxtOrg dwxtOrg,String jcOrgId);

	/**
	 * 保存党小组
	 * @param dwxtOrg
	 * @return
	 */
	DwxtOrg saveDxz(DwxtOrg dwxtOrg,String selectIds);

	/**
	 * 修改党组织
	 * @param dwxtOrg
	 * @return
	 */
	DwxtOrg update(DwxtOrg dwxtOrg,String jcOrgId);

	/**
	 * 修改党小组
	 * @param dwxtOrg
	 * @return
	 */
	public DwxtOrg updateDxz(DwxtOrg dwxtOrg,String selectIds);

	/**
	 * 检查重复党组织信息
	 * @param orgId
	 * @return
	 */
	Boolean check(String orgId);

	/**
	 * 更新下级党组织编号
	 * @param orgId
	 */
	void recursiveDwxtOrg(String orgId);

	/**
	 * 删除党组织
	 * @param dwxtOrg
	 * @return
	 */
	int deleteOrg(DwxtOrg dwxtOrg,String revokeId);

	/**
	 * 查询单位列表
	 * @param dwxtOrg
	 * @return
	 */
	List<DwxtUnit> unitList(DwxtOrg dwxtOrg);

	/**
	 * 保存单位
	 * @param dwxtUnit
	 * @return
	 */
	DwxtUnit saveUnit(DwxtUnit dwxtUnit);

	/**
	 * 更新单位
	 * @param dwxtUnit
	 * @return
	 */
	DwxtUnit updateUnit(DwxtUnit dwxtUnit);

	/**
	 * 单位信息详情
	 * @param id
	 * @return
	 */
	DwxtUnit findOneUnit(String id);

	/**
	 * 删除单位信息
	 * @param id
	 * @return
	 */
	int deleteUnit(String id);

	/**
	 * 修改组织编码 组织变更用
	 * @param
	 * @return
	 */
	DwxtOrg changeDzzOrg(String id,String targetId,String code);

	/**
	 * 保存撤销信息
	 * @param dwxtRevoke
	 * @return
	 */
	DwxtRevoke saveRevoke(DwxtRevoke dwxtRevoke,String dwxtOrgId);

	/**
	 * 查询撤销的党组织
	 * @param revokeId
	 * @return
	 */
	List<DwxtOrg> findOrgListByDwxtRevoke(String revokeId,String orgId);

	/**
	 * 党组织基本情况
	 */
	Map orgBasicSituation(String id);

	/**
	 * 单位基本情况
	 */
	Map unitBasicSituation(String id);

	/**
	 * 党组织表彰数
	 */
	Map<String, Object> orgCommendation(String id);

	/**
	 * 党组织增加减少情况
	 */
	Map<String, Object> orgChangeSituation(String id);

	/**
	 * 获取登陆人的顶级节点
	 */
	String getOrgId();

//	/**
//	 * 获取登陆人的顶级节点
//	 */
//	String findByOrgId(String OrgId);

	/**
	 * 查询历史党组织
	 * @param dwxtOrg
	 * @return
	 * @throws Exception
	 */
	List<DwxtOrg> lsList(DwxtOrg dwxtOrg);
}
