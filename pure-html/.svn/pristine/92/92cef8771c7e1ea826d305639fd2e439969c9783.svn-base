package com.sinosoft.sinoep.user.service;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DeptInfoServiceImpl implements DeptInfoService {

    private static Log log = LogFactory.getLog(DeptInfoServiceImpl.class);
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public List<Map<String, Object>> getDeptInfoByDeptNumber(String deptNumbers) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(deptNumbers)){
            String[] deptNumbersArry = deptNumbers.split(",");
            String deptNumber = "";
            for (String s :deptNumbersArry){
                deptNumber+="'"+s+"',";
            }
            deptNumber = deptNumber.substring(0,deptNumber.length()-1);
            String sql = " select * from sys_flow_dept where deptnumber in("+deptNumber+") ";
            JSONObject json = userInfoService.getDateBySql(sql);
            if(ConfigConsts.SUCESS_STARUS.equals(json.get("flag"))){
                list = json.getJSONArray("data");
            }else{
                log.info("根据部门编号获取部门信息异常");
            }
        }
        return list;
    }

    /**
     * 根据部门id和单位属性获取部门树
     * @param deptId 部门id 默认为441
     * @param unitType 多个用逗号分隔（sys_dictionary 表 remarks='4'）
     * @return
     */
    @Override
    public JSONObject getDeptInfoByDeptIdAndUnitType(String deptId, String unitType) {
        JSONObject json = new JSONObject();
        if(StringUtils.isNotEmpty(unitType)){
            String unitTypes = "";
            if(unitType.contains(",")){
                String[] unitTypeArray = unitType.split(",");
                for(String s:unitTypeArray){
                    unitTypes +="'"+s+"',";
                }
                unitTypes = unitTypes.substring(0,unitTypes.length()-1);
            }else{
                unitTypes = unitType;
            }
            StringBuilder sql = new StringBuilder("select * from (select rownum as row_num,  nvl(t.unit_type, ' ') unitType, t.deptid,t.tree_id,t.deptlevel,t.deptname,t.super_id,nvl(t.note, ' ') note" +
                    " from sys_flow_dept t where t.status = '1' ");
            String sql1 = "";
            if(StringUtils.isNotEmpty(deptId)){
                sql1 = "select deptId from sys_flow_dept where status = '1' and unit_type in('"+unitTypes+"') and super_Id ='"+deptId+"'";
            }else{
                sql1 = "select deptId from sys_flow_dept where status = '1' and unit_type in('"+unitTypes+"') and super_Id ='441'";
            }
            sql.append(" start with t.deptid in (").append(sql1).append(") ");
            sql.append(" connect by prior t.deptid = t.super_id");
            sql.append(" order siblings by t.order_no) ");
            sql.append("union");
            sql.append("(select rownum as row_num, nvl(t.unit_type, ' ') unitType, t.deptid, t.tree_id, t.deptlevel, t.deptname, t.super_id, nvl(t.note, ' ') note from sys_flow_dept t " +
                    " where t.status = '1' and t.deptid = '").append(deptId).append("')");
            json = userInfoService.getDateBySql(sql.toString());
        }
        return json;
    }

    @Override
    public JSONObject getSuperIdByDeptId(String deptId) {
        JSONObject json = new JSONObject();
        if(StringUtils.isNotBlank(deptId)){
           String sql = "select super_id from sys_flow_dept where deptId='"+deptId+"'";
           json = userInfoService.getDateBySql(sql);
        }else {
            json.put("flag",false);
        }
        return json;
    }
}
