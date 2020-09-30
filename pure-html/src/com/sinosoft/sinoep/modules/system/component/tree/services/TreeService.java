package com.sinosoft.sinoep.modules.system.component.tree.services;

import com.sinosoft.sinoep.api.dept.vo.MessageDeptUserTree;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface TreeService {

    /**
     * 根据deptId查询部门人员树
     * @param deptId 部门id
     * @return
     */
    public JSONArray getDeptAndUserTreeByDeptId(String deptId);

    /**
     * 根据deptId和级别查询部门树
     * @param deptId deptId
     * @param level 级别
     */
    public JSONArray getDeptTreeByDeptId(String deptId, int level);

    /**
     * 根据deptId查询部门树
     * @param deptId deptId
     */
    public JSONArray getDeptTreeByDeptId(String deptId);

    /**
     * 根据部门id获取部门人员树
     * @param deptId 部门id
     * @param level 级别
     * @return
     */
    public JSONArray getDeptUserTreeByDeptId(String deptId, Integer level);

    /**
     * 异步加载部门人员树
     * @param type 2 部门树，3 人员树
     * @param superId 异步加载时用到，即所点击的部门id
     * @param orgId 初始化时用到，即顶级节点的部门id
     * @param status 状态，默认1
     * @return
     */
    public String getDeptOrUserTree(String type, String superId, String orgId, String status);

    /**
     * 根据deptId查询部门及部门下所有人员（包括顶级节点直到该部门，只显示最底级的部门人员信息）
     * @param deptId
     * @return
     */
    public JSONArray getDeptAndUserById(String deptId);

    /**
     * 根据deptid查询部门及部门下所有子孙部门及人员（包括顶级节点直到该部门，但上层部门节点没有人员）
     * @param deptId
     * @return
     */
    JSONArray getDeptAndUserBydeptId(String deptId);

    /**
     * 根据部门级别获取部门人员树，从顶级开始
     * @param deptlevel 从0开始，0 查询顶级下面的人员，1 查询顶级下子部门及人员
     * @return
     */
    JSONArray getDeptAndUserTreeBylevel(String deptlevel);

    /**
     * 根据deptid查询部门及部门下所有直属子部门（包括顶级节点直到该部门，但上层部门节点不包括子部门）
     * @param deptId
     * @return
     */
    JSONArray getDeptListBydeptId(String deptId);

    /**
     * 根据deptid查询部门及部门下所有子孙部门（包括顶级节点直到该部门，但上层部门节点没有其他子部门）
     * @param deptId
     * @return
     */
    JSONArray getAllDeptList(String deptId);

    /**
     * 根据deptId只查找该部门及该部门下的所有人员（不包括子部门、父部门）
     * @param deptId
     * @return
     */
    JSONArray getUserByDeptId(String deptId);

    /**
     * 根据人员职务获取部门人员树
     * @param positionName 职务(例'1','2','3',...)
     * @return
     */
    JSONArray getAllTreeBylevel(String positionName);

    /**
     * 根据部门级别获取部门人员树，从顶级开始，只展示最底级的人员
     * @param level 从0开始，0 查询顶级下面的人员，1 查询顶级下子部门及人员
     * @return
     */
    JSONArray getDeptAndUserTreeBylevel2(String level);

    /**
     * 根据部门和业务角色编号获取部门人员树
     * @param deptId
     * @param RolesNo
     * @return
     */
    public JSONObject getDeptUserByDeptIdAndRolesNo(String deptId,String rolesNo);
}
