package com.sinosoft.sinoep.modules.system.config.orgdepmapping.services;

import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.modules.system.config.orgdepmapping.entity.OtherFlowDep;


public interface OtherFlowDepService {
	
	/**
	 * 获取该组织体系下的所有部门
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月6日 下午5:50:18
	 * @param orgId
	 * @return
	 */
	List<OtherFlowDep> getList(String orgId,String departmentName,String sortName,String sortOrder);
	
	/**
	 * 保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午2:04:51
	 * @param info
	 * @return
	 */
	OtherFlowDep saveForm(OtherFlowDep info);

	OtherFlowDep getById(String id);
	
	/**
	 * 根据id删除一条记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午4:46:33
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 排序
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午5:21:54
	 * @param id
	 */
	void sort(String[] ids);
	
	/**
	 * 绑定其他部门
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月11日 上午10:11:31
	 * @param id
	 * @param glDeptId
	 */
	int binding(String id, String glDeptId,String glDeptName);
	
	/**
	 * 解除绑定
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月11日 上午11:44:38
	 * @param id
	 * @return
	 */
	int unbinding(String id);

}
