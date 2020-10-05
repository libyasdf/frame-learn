package com.sinosoft.sinoep.modules.system.component.tree.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.uias.constant.UiasConfigConsts;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.api.dept.service.DeptInfoService;
import com.sinosoft.sinoep.api.dept.vo.DeptUserTreeVo;
import com.sinosoft.sinoep.api.dept.vo.MessageDeptUserTree;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.workflow.external.IClientWorkflowOrg;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class TreeServiceImpl implements TreeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IClientWorkflowOrg clientWorkflowOrg;

    @Autowired
    UserInfoService userInfoService;

    @Override
    public JSONArray getDeptAndUserTreeByDeptId(String deptId) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        MessageDeptUserTree result = deptInfoService.getDeptAndUserTreeBydeptId(deptId);
        if (ConfigConsts.SUCESS_STARUS.equals(result.getStatus())) {
            List<DeptUserTreeVo> list = result.getDeptUserTree();
            List<DeptUserTreeVo> user = new ArrayList<>();
            List<DeptUserTreeVo> dept = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if ("user".equals(list.get(i).getType())) {
                    user.add(list.get(i));
                } else {
                    dept.add(list.get(i));
                }
            }
            user.addAll(dept);
            JSONArray array =  JSONArray.fromObject(user);
            for (int i = 0; i < array.size(); i++) {
                if (deptId.equals(array.getJSONObject(i).getString("uuid"))) {
                    array.getJSONObject(i).put("open",true);
                }
            }
            return array;
        }
        return new JSONArray();
    }

    @Override
    public JSONArray getDeptTreeByDeptId(String deptId, int level) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        JSONArray array = new JSONArray();// 最终结果集
        List<DeptUserTreeVo> top = getTopTree(deptId);// 查询根节点
        //JSONArray nodes = JSONArray.fromObject(top);// 保存临时结果
        List<DeptUserTreeVo> nodes = top;
        Stack<Map<String, Object>> stack = new Stack<>();
        boolean flag = false;
        for (int j = 0; j <= level; ) {
            flag = false;
            for (int i = nodes.size() - 1; i >= 0; i--) {
                Map<String, Object> map = new HashMap<>();
                map.put("deptId", nodes.get(i).getUuid());
                map.put("level", j);
                map.put("node", nodes.get(i));
                stack.push(map);// 入栈
            }
            while (!stack.isEmpty()) {
                Map<String, Object> map = stack.pop();// 出栈
                array.add(map.get("node"));// 放入结果集
                if (Integer.valueOf(map.get("level").toString()) < level) {
                    String sql = "and super_id = '" + map.get("deptId").toString() + "'";
                    nodes = deptInfoService.queryDeptUsersByCondition("dept",sql, null).getDeptUserTree();
                    j = Integer.valueOf(map.get("level").toString()) + 1;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                break;
            }
        }
        return array;
    }

    @Override
    public JSONArray getDeptTreeByDeptId(String deptId) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        JSONArray array = new JSONArray();// 最终结果集
        List<DeptUserTreeVo> top = getTopTree(deptId);// 查询根节点
        List<DeptUserTreeVo> nodes = top;
        Stack<Map<String, Object>> stack = new Stack<>();
            for (int i = nodes.size() - 1; i >= 0; i--) {
                Map<String, Object> map = new HashMap<>();
                map.put("deptId", nodes.get(i).getUuid());
                map.put("node", nodes.get(i));
                stack.push(map);// 入栈
            }
            while (!stack.isEmpty()) {
                Map<String, Object> map = stack.pop();// 出栈
                array.add(map.get("node"));// 放入结果集
                String sql = "and super_id = '" + map.get("deptId").toString() + "'";
                nodes = deptInfoService.queryDeptUsersByCondition("dept",sql, null).getDeptUserTree();
                for (int i = nodes.size() - 1; i >= 0; i--) {
                    map = new HashMap<>();
                    map.put("deptId", nodes.get(i).getUuid());
                    map.put("node", nodes.get(i));
                    stack.push(map);// 入栈
                }
            }
        return array;
    }

    @Override
    public JSONArray getDeptUserTreeByDeptId(String deptId, Integer level) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        JSONArray array = new JSONArray();// 最终结果集
        List<DeptUserTreeVo> top = getTopTree(deptId);// 查询根节点
        //JSONArray nodes = JSONArray.fromObject(top);// 保存临时结果
        List<DeptUserTreeVo> nodes = top;
        Stack<Map<String, Object>> stack = new Stack<>();
        boolean flag = false;
        for (int j = 0; j <= level; ) {
            flag = false;
            for (int i = nodes.size() - 1; i >= 0; i--) {
                Map<String, Object> map = new HashMap<>();
                map.put("deptId", nodes.get(i).getUuid());
                map.put("level", j);
                map.put("node", nodes.get(i));
                stack.push(map);// 入栈
            }
            while (!stack.isEmpty()) {
                Map<String, Object> map = stack.pop();// 出栈
                array.add(map.get("node"));// 放入结果集
                // 查询部门下的人员，并放入结果集
                array.addAll(queryUserCondition(map.get("deptId").toString()));
                if (Integer.valueOf(map.get("level").toString()) < level) {
                    String sql = "and super_id = '" + map.get("deptId").toString() + "'";
                    nodes = deptInfoService.queryDeptUsersByCondition("dept",sql, null).getDeptUserTree();
                    j = Integer.valueOf(map.get("level").toString()) + 1;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                break;
            }
        }
        return array;
    }

    /**
     * 查询某部门下的所有人员，有序
     * @param deptId
     * @return
     */
    private List<DeptUserTreeVo> queryUserCondition(String deptId) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        String sql = "and d.deptid = '" + deptId + "'";
        List<DeptUserTreeVo> nodes = deptInfoService.queryDeptUsersByCondition("all",sql, sql).getDeptUserTree();
        List<DeptUserTreeVo> result = new ArrayList<>();
        for (int i = 0, length = nodes.size(); i < length; i++) {
            DeptUserTreeVo node = nodes.get(i);
            if ("user".equals(node.getType())) {
                result.add(node);
            }
        }
        return result;
    }

    private List<DeptUserTreeVo> getTopTree(String deptId) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        String deptCondition = "and d.deptid = '" + deptId + "'";
        return deptInfoService.queryDeptUsersByCondition("dept",deptCondition, null).getDeptUserTree();
    }


    /**
     *
     * <B>方法名称：getDeptOrUserTree</B><BR>
     * <B>概要说明：获得部门或人员树</B><BR>
     *
     * @see com.sinosoft.sinoep.user.service.UserInfoService#getDeptOrUserTree(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String getDeptOrUserTree(String type, String superId, String orgId, String status) {
        String result = "";
        JSONObject json = new JSONObject();
        json.put("orgId", orgId);
        json.put("type", type);
        json.put("superId", superId);
        json.put("status", status);
        try {
            result = clientWorkflowOrg.getOrgTree(json.toString());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @Override
    public JSONArray getDeptAndUserById(String deptId) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        MessageDeptUserTree result = deptInfoService.getDeptAndUserById(deptId);
        if (ConfigConsts.SUCESS_STARUS.equals(result.getStatus())) {
            JSONArray array = JSONArray.fromObject(result.getDeptUserTree());
            for (int i = 0; i < array.size(); i++) {
                if ("dept".equals(array.getJSONObject(i).getString("type"))) {
                    array.getJSONObject(i).put("open",true);
                }
            }
            return array;
        }
        return  new JSONArray();
    }

    @Override
    public JSONArray getDeptAndUserBydeptId(String deptId) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        MessageDeptUserTree result = deptInfoService.getDeptAndUserBydeptId(deptId);
        if (ConfigConsts.SUCESS_STARUS.equals(result.getStatus())) {
            List<DeptUserTreeVo> list = result.getDeptUserTree();
            List<DeptUserTreeVo> user = new ArrayList<>();
            List<DeptUserTreeVo> dept = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if ("user".equals(list.get(i).getType())) {
                    user.add(list.get(i));
                } else {
                    dept.add(list.get(i));
                }
            }
            user.addAll(dept);
            JSONArray array = JSONArray.fromObject(user);
            int length = 0;
            for (int i = 0; i < array.size(); i++) {
                if (deptId.equals(array.getJSONObject(i).getString("uuid"))) {
                    length = array.getJSONObject(i).getString("treeId").length();
                    break;
                }
            }
            for (int i = 0; i < array.size(); i++) {
                if ("dept".equals(array.getJSONObject(i).getString("type"))) {
                    if (length >= array.getJSONObject(i).getString("treeId").length()) {
                        array.getJSONObject(i).put("open",true);
                    }
                }
            }
            return array;
        }
        return  new JSONArray();
    }

    @Override
    public JSONArray getDeptAndUserTreeBylevel(String deptlevel) {
        String resJson = HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/dept/getDeptAndUserTreeBylevel",
                "deptlevel=" + deptlevel + "&pkdeptId=" + UserUtil.getCruOrgId());
        JSONObject json = JSONObject.fromObject(resJson);
        if (ConfigConsts.SUCESS_STARUS.equals(json.getString(UiasConfigConsts.STATUS))) {
            JSONArray array = json.getJSONArray(UiasConfigConsts.DEPT_USER_TREE);
            JSONArray user = new JSONArray();
            JSONArray dept = new JSONArray();
            for (int i = 0; i < array.size(); i++) {
                if (array.getJSONObject(i).getString("type").equals("user")) {
                    user.add(array.getJSONObject(i));
                } else {
                    if (array.getJSONObject(i).getString("uuid").equals(UserUtil.getCruOrgId())) {
                        array.getJSONObject(i).put("open",true);
                    }
                    dept.add(array.getJSONObject(i));

                }
            }
            user.addAll(dept);
            return user;
        }
        return  new JSONArray();
    }

    @Override
    public JSONArray getDeptListBydeptId(String deptId) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        MessageDeptUserTree result = deptInfoService.getDeptListBydeptId(deptId);
        if (ConfigConsts.SUCESS_STARUS.equals(result.getStatus())) {
            JSONArray array = JSONArray.fromObject(result.getDeptUserTree());
            for (int i = 0; i < array.size(); i++) {
                if ("dept".equals(array.getJSONObject(i).getString("type"))) {
                    array.getJSONObject(i).put("open",true);
                }
            }
            return array;
        }
        return  new JSONArray();
    }

    @Override
    public JSONArray getAllDeptList(String deptId) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        MessageDeptUserTree result = deptInfoService.getAllDeptList(deptId);
        if (ConfigConsts.SUCESS_STARUS.equals(result.getStatus())) {
            JSONArray array = JSONArray.fromObject(result.getDeptUserTree());
            int length = 0;
            for (int i = 0; i < array.size(); i++) {
                if (deptId.equals(array.getJSONObject(i).getString("uuid"))) {
                    length = array.getJSONObject(i).getString("treeId").length();
                    break;
                }
            }
            for (int i = 0; i < array.size(); i++) {
                if ("dept".equals(array.getJSONObject(i).getString("type"))) {
                    if (length >= array.getJSONObject(i).getString("treeId").length()) {
                        array.getJSONObject(i).put("open",true);
                    }
                }
            }
            return array;
        }
        return  new JSONArray();
    }

    @Override
    public JSONArray getUserByDeptId(String deptId) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        MessageDeptUserTree result = deptInfoService.getAllDeptById(deptId);
        if (ConfigConsts.SUCESS_STARUS.equals(result.getStatus())) {
            return JSONArray.fromObject(result.getDeptUserTree());
        }
        return  new JSONArray();
    }

    @Override
    public JSONArray getAllTreeBylevel(String positionName) {
        DeptInfoService deptInfoService = (DeptInfoService) SpringBeanUtils.getBean("sDeptInfoService");
        MessageDeptUserTree result = deptInfoService.getAllTreeBylevel(positionName);
        if (ConfigConsts.SUCESS_STARUS.equals(result.getStatus())) {
            JSONArray array = JSONArray.fromObject(result.getDeptUserTree());
            JSONArray user = new JSONArray();
            JSONArray dept = new JSONArray();
            for (int i = 0; i < array.size(); i++) {
                if ("dept".equals(array.getJSONObject(i).getString("type"))) {
                    array.getJSONObject(i).put("open",true);
                    dept.add(array.getJSONObject(i));
                } else {
                    user.add(array.getJSONObject(i));
                }
            }
            user.addAll(dept);
            return user;
        }
        return  new JSONArray();
    }

    @Override
    public JSONArray getDeptAndUserTreeBylevel2(String level) {
        String resJson = HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/dept/getDeptAndUserTreeBylevel",
                "deptlevel=" + level + "&pkdeptId=" + UserUtil.getCruOrgId());
        JSONObject json = JSONObject.fromObject(resJson);
        if (ConfigConsts.SUCESS_STARUS.equals(json.getString(UiasConfigConsts.STATUS))) {
            JSONArray array = json.getJSONArray(UiasConfigConsts.DEPT_USER_TREE);
            JSONArray result = new JSONArray();
            // 获取可以显示人员的部门id
            int size = (Integer.parseInt(level) + 1) * 4;
            List<String> deptidList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                if ("dept".equals(array.getJSONObject(i).getString("type")) && array.getJSONObject(i).getString("treeId").length() == size) {
                    deptidList.add(array.getJSONObject(i).getString("uuid"));
                }
            }
            for (int i = 0; i < array.size(); i++) {
                if ("user".equals(array.getJSONObject(i).getString("type")) && deptidList.contains(array.getJSONObject(i).getString("uupid"))) {
                    result.add(array.getJSONObject(i));
                } else if ("dept".equals(array.getJSONObject(i).getString("type"))) {
                    result.add(array.getJSONObject(i));
                }
            }
            return result;
        }
        return  new JSONArray();
    }

    @Override
    public JSONObject getDeptUserByDeptIdAndRolesNo(String deptId, String rolesNo) {
        JSONObject json = new JSONObject();
        if(StringUtils.isNotBlank(deptId)){
            String[] deptIdArray = deptId.split(",");
            String deptIds = "";
            for(String s:deptIdArray){
                deptIds += "'"+s+"',";
            }
            deptId = deptIds.substring(0,deptIds.length()-1);
        }
        if(StringUtils.isNotBlank(rolesNo)){
            String[] rolesNoArray = rolesNo.split(",");
            String rolesNos = "";
            for(String m:rolesNoArray){
                rolesNos += "'"+m+"',";
            }
            rolesNo = rolesNos.substring(0,rolesNos.length()-1);
        }
        StringBuilder userSql = new StringBuilder();
        StringBuilder deptSql = new StringBuilder();
        userSql.append("select distinct(u.userId), u.username,d.deptid ,'user' type" +
                "  from sys_flow_role r, sys_user_frole f, sys_flow_user u, sys_user_dprb d" +
                " where r.roleid = f.roleid" +
                "   and f.u_dept_id = d.id" +
                "   and u.userid = d.userid" +
                "   and u.status = '1'" );
        deptSql.append("select distinct(t.deptId),t.super_id,t.tree_id,t.deptname, t.order_no, 'dept' type" +
                "  from sys_flow_dept t" +
                " where t.status = '1'" +
                " start with t.deptid in( select distinct(d.deptid)  from sys_flow_role r, sys_user_frole f, sys_flow_user u, sys_user_dprb d" +
                " where r.roleid = f.roleid" +
                "   and f.u_dept_id = d.id" +
                "   and u.userid = d.userid" +
                "   and u.status = '1'");
        if(StringUtils.isNotEmpty(rolesNo)){
            userSql.append(" and r.role_no in (").append(rolesNo).append(")");
            deptSql.append(" and r.role_no in (").append(rolesNo).append(")");
        }
        if(StringUtils.isNotEmpty(deptId)){
            userSql.append("and d.deptid in (select t.deptid from sys_flow_dept t where t.status = '1' " +
                    "start with t.deptid in (").append(deptId).append(")");
            userSql.append("connect by prior t.deptid = t.super_id )");
            deptSql.append("and d.deptid in (select t.deptid from sys_flow_dept t where t.status = '1' " +
                    "start with t.deptid in (").append(deptId).append(")");
            deptSql.append("connect by prior t.deptid = t.super_id )");
        }
        deptSql.append(" ) connect by prior t.super_id = t.deptid order by order_no" );
        JSONObject userJson = userInfoService.getDateBySql(userSql.toString());
        JSONArray jsonArray = userJson.getJSONArray("data");
        JSONObject deptJson = userInfoService.getDateBySql(deptSql.toString());
        //王富康-展开顶层树节点
        for (int i = 0; i < deptJson.getJSONArray("data").size(); i++) {
            if ("0".equals(deptJson.getJSONArray("data").getJSONObject(i).getString("super_id"))) {
                deptJson.getJSONArray("data").getJSONObject(i).put("open",true);
            }
        }
        jsonArray.addAll(deptJson.getJSONArray("data"));
        json.put("data",jsonArray);
        return json;
    }
}
