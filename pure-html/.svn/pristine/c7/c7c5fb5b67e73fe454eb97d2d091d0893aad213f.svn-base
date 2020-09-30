package com.sinosoft.sinoep.user.dao;

import com.sinosoft.sinoep.user.entity.SysFlowDept;

import java.util.List;

/**
 * 部门人员树扩展查询筛选
 *
 * @author 张卜亢
 * @date 2018 -03-19 21:26:23
 */
public interface TreeDao {

    /**
     * 根据部门ID查询部门人员
     * 如果ID为null，则将自己父部门的ID作为根，
     * 查询父部门的人员、本部门的人和子部门以及子部门的人和部门
     *
     * @param deptid
     * @return
     * @author 张卜亢
     * @date 2018 -03-19 21:26:23
     */
    List<SysFlowDept> getTreeByDeptid(Long deptid);

}
