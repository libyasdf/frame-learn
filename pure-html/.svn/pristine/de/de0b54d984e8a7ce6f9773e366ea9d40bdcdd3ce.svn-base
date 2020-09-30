package com.sinosoft.sinoep.user.service;

import com.sinosoft.sinoep.user.entity.SysFlowDept;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface DeptInfoService {
    public List<Map<String, Object>> getDeptInfoByDeptNumber(String deptNumbers);

    /**
     * 根据部门id和单位属性获取部门树
     * @param deptId 部门id 默认为441
     * @param unitType 多个用逗号分隔（sys_dictionary 表 remarks='4'）
     * @return
     */
    public JSONObject getDeptInfoByDeptIdAndUnitType(String deptId,String unitType);


    public JSONObject getSuperIdByDeptId(String deptId);
}
