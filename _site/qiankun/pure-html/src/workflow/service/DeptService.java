/**
 * <p> Copyright: Copyright (c) 2011 </p>
 * <p> Company: 中科软科技股份有限公司 </p>
 */
package workflow.service;

public interface DeptService {
	/**
	 * 得到直属上级的参与者
	 * @param deptId 当前部门ID
	 * @param num    跨跃的级别数
	 * @param limit  范围限制(1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param roldId 角色ID
	 * @param deptCon部门查询条件(有可能为空)
	 * @return Node 部门用户封装树
	 */
	public String getDirectSuperUser(String deptId,String num,String limit,String roldId,String deptCon);
	
	/**
	 * 得到直属上级部门串,以逗号分隔
	 * @param deptId 当前部门ID
	 * @param num    跨跃的级别数
	 * @param limit  对象范围限制(1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param deptCon部门查询条件(有可能为空)
	 * @return String 部门ID串
	 */
	public String getDirectSuperDept(String deptId,String num,String limit,String deptCon);
	
	/**
	 * 得到直属同级的参与者
	 * @param deptId 当前部门ID
	 * @param limit  对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param roldId 角色ID
	 * @param deptCon查询条件(有可能为空)
	 * @return String 部门用户封装树
	 */
	public String getDirectSameUser(String deptId,String limit,String roldId,String deptCon);
	
	/**
	 * 得到直属同级部门串,以逗号分隔
	 * @param deptId 当前部门ID
	 * @param limit  对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门ID串
	 */
	public String getDirectSameDept(String deptId,String limit,String deptCon);
	
	/**
	 * 得到直属下级的参与者
	 * @param deptId 当前部门ID
	 * @param num 跨跃级别数
	 * @param limit 对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param roldId 角色ID
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门用户封装树
	 */
	public String getDirectSubUser(String deptId,String num,String limit,String roldId,String deptCon);
	
	/**
	 * 得到直属下级部门串,以逗号分隔
	 * @param deptId 当前部门ID
	 * @param num 跨跃级别数
	 * @param limit 对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门ID串
	 */
	public String getDirectSubDept(String deptId,String num,String limit,String deptCon);
	
	/**
	 * 得到跨部门上级的参与者
	 * @param deptId 当前部门ID
	 * @param num 跨跃的级别数
	 * @param limit 对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param roldId  角色ID
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门用户封装树
	 */
	public String getSpanSuperUser(String sysId,String deptId,String num,String limit,String roldId,String deptCon);
	
	/**
	 * 得到直属跨部门上级部门串,以逗号分隔
	 * @param deptId 当前部门ID
	 * @param num 跨跃的级别数
	 * @param limit  对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门ID串
	 */
	public String getSpanSuperDept(String sysId,String deptId,String num,String limit,String deptCon);
	/**
	 * 得到跨部门同级的参与者
	 * @param deptId 当前部门ID
	 * @param limit 对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param roldId 角色ID
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门用户封装树
	 */
	public String getSpanSameUser(String sysId, String deptId, String num,String limit,String roldId,String deptCon);
	
	/**
	 * 得到跨部门同级的部门串,以逗号分隔
	 * @param deptId 当前部门ID
	 * @param limit 对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门ID串
	 * 
	 */
	public String getSpanSameDept(String sysId, String deptId,String num,String limit,String deptCon);
	/**
	 * 得到跨部门下级的参与者
	 * @param deptId 当前部门ID
	 * @param num 跨跃的级别数
	 * @param limit 对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param roldId 角色ID
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门用户封装树
	 */
	public String getSpanSubUser(String sysId,String deptId,String num,String limit,String roldId,String deptCon);
	
	/**
	 * 得到跨部门下级的部门串,以逗号分隔
	 * @param deptId 当前部门ID
	 * @param num 跨跃的级别数
	 * @param limit 对象范围限制 (1- 整个组织体系 2-独立实体内部 3-独立实体外部)
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门ID串
	 */ 
	public String getSpanSubDept(String sysId,String deptId,String num,String limit,String deptCon);
	/**
	 * 得到子部门ID串，以逗号分隔
	 * @param deptId String 当前部门ID
	 * @return 
	 */
	public String getSubdept(String deptId);
	/**
	 * 得到父部门
	 * @param deptId String 当前部门ID
	 * @return String 父部门ID
	 */
	public String getSuperdept(String deptId);
	/**
	 * 得到该部门下具有该角色的参与者
	 * @param deptId 当前部门ID
	 * @param roleId 角色ID
	 * @return String 部门用户封装树
	 */
	public String getDeptRoleUser(String deptId,String roleId);
	/**
	 * 得到多个部门下具有该角色的人
	 * @param deptIdStr 部门ID串,以逗号分隔 deptId,deptId
	 * @param roleId 角色ID
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门用户封装树
	 */
	public String getMuchDeptRoleUser(String deptIdStr,String roleId,String deptCon);
	
	/**
	 * 得到部门和用户名称
	 * @param deptUserIdStr 格式 deptId*userId,deptId*userId
	 * @return String 部门用户封装树
	 */
	public String getUser(String deptUserIdStr);
	/**
	 * 得到某一用户的名称及所在部门的名称
	 * @param deptId
	 * @param userId
	 * @return String 部门用户封装树
	 */
	public String getUser(String deptId,String userId);
	/**
	 * 得到具有该级别和该角色的参与者
	 * @param deptLevel 当前级别ID
	 * @param roleId 角色ID
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门用户封装树
	 */
	public String getDeptLevelUser(String appId,String deptLevel,String roleId,String deptCon);
	/**
	 * 根据角色得到部门级别
	 * @param roleId 角色ID
	 * @return String 部门级别ID
	 */
	public String getDeptlevel(String roleId);
	/**
	 * 根据角色ID得到参与者
	 * @param roleId 角色ID
	 * @return String 部门用户封装树
	 */
	public String getRoleUser(String roleId);
	/**
	 * 得到部门名称
	 * @param deptId 当前部门ID
	 * @return 部门名称(根据业务需要,是否得到部门全称,默认是全称)
	 */
	public String getDeptName(String deptId);
	
	/**
	 * 得到部门名称
	 * @param deptId 当前部门ID
	 * @return 部门名称(根据业务需要,是否得到部门全称,默认是全称)
	 */
	public String getDeptFullName(String deptId);
	
	/**
	 * 得到用户名称
	 * @param userId 当前用户ID
	 * @return String 用户名称
	 */
	public String getUserName(String userId);
	
	/**
	 * 得到部门名称
	 * @param deptId 当前部门ID
	 * @return 部门名称(根据业务需要,是否得到部门全称,默认是全称)
	 */
	public String getUserFullName(String deptId);
	/**
	 * 根据部门ID和用户ID得到角色ＩＤ
	 * @param deptId　当前部门ＩＤ
	 * @param userId　当前用户ＩＤ
	 * @return String 角色ID
	 */
	public String getRole(String deptId,String userId);
	/**
	 * 根据用户ID得到ID
	 * @param userId
	 * @return String 部门ID
	 */
	public String getDept(String userId);
	/**
	 * 得到群组ID
	 * @param userId
	 * @return String 群组ID
	 */
	public String getGroup(String userId);
	/**
	 * 得到该部门下的参与者(适用于系统管理是部门的)
	 * @param deptId 当前部门ID
	 * @param deptCon 查询条件(有可能为空)
	 * @return String 部门用户封装树
	 */
	public String getDeptUser(String deptId,String deptCon);
	/**
	 * 得到该群组下的参与者(适用于系统管理是群组的)
	 * @param groupId 群组ID
	 * @param GroupCon 查询条件(有可能为空)
	 * @return String 部门用户封装树
	 */
	public String getGroupUser(String groupId,String groupCon);
	/**
	 * 得到参与者
	 * @return String 部门用户封装树
	 */
	public String getUser();
	/**
	 * 得到组织体系ID
	 * @param deptId
	 * @return String 组织体系ID
	 */
	public String getOrgId(String deptId);
	/**
	 * 得到部门信息
	 * @param whereCon where 后面的条件
	 * @param order　排序 asc或desc或空串
	 * @return Vector<FlowDeptVO>
	 */
	public String getDept(String deptIdStr,String order);
	/**
	 * 取部门所在的独立实体ID
	 * @param deptId 部门ID
	 * @return 独立实体ID
	 */
	public String getUnipDetpId(String deptId);
}
