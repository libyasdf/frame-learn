package com.sinosoft.sinoep.modules.system.component.tree.controller;

import com.sinosoft.sinoep.api.dept.vo.MessageDeptUserTree;
import com.sinosoft.sinoep.modules.system.component.tree.services.TreeService;
import com.sinosoft.sinoep.user.service.UserInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/system/component/tree")
public class TreeAction {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TreeService service;

    @Resource
    UserInfoService userInfoService;

    /**
     * 根据部门id查询部门人员树，无序
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/deptAndUserTree", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getDeptAndUserTree(String deptId) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(deptId)) {
            tree = service.getDeptAndUserTreeByDeptId(deptId);
        }
        return tree;
    }

    /**
     * 根据deptId和级别获取部门树，有序
     * @param deptId 部门id
     * @param level 级别，从1开始，即查询deptId下几级的部门
     * @return
     */
    @RequestMapping(value = "/deptTree", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getDeptTree(String deptId, Integer level) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(deptId)) {
            if (level == null) {
                tree = service.getDeptTreeByDeptId(deptId);
            } else {
                tree = service.getDeptTreeByDeptId(deptId, level);
            }
        }
        return tree;
    }

    /**
     * 根据deptId和级别获取部门树，有序
     * @param deptId 部门id
     * @param level 级别，从1开始，即查询deptId下几级的部门
     * @return
     */
    @RequestMapping(value = "/deptUserTree", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getDeptUserTree(String deptId, Integer level) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(deptId)) {
            tree = service.getDeptUserTreeByDeptId(deptId, level - 1);
        }
        return tree;
    }

    /**
     *  异步加载部门人员树
     * @param id 点击的部门id
     * @param orgId 初始化树时的顶级节点的部门id
     * @param type dept 部门树，all 部门人员树
     * @return
     */
    @RequestMapping(value = "getDeptOrUserTree", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String selectFlowDept(String id, String orgId, String type) {
        String result = "";
        try {
            if (StringUtils.isBlank(id) && StringUtils.isNotBlank(orgId)) {
                result = service.getDeptOrUserTree(type,null,orgId,null);
            } else if (StringUtils.isNotBlank(id)) {
                result = service.getDeptOrUserTree(type,id,null,"1");
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return result;
    }

    /**
     * 根据deptId查询部门及部门下所有人员（包括顶级节点直到该部门，只显示最底级的部门人员信息）
     * @param deptId
     * @return
     */
    @RequestMapping(value = "getDeptAndUserById", produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getDeptAndUserById(String deptId) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(deptId)) {
            tree = service.getDeptAndUserById(deptId);
        }
        return tree;
    }

    /**
     * 根据deptid查询部门及部门下所有子孙部门及人员（包括顶级节点直到该部门，但上层部门节点没有人员）
     * @param deptId
     * @return
     */
    @RequestMapping(value = "getDeptAndUserBydeptId", produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getDeptAndUserBydeptId(String deptId) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(deptId)) {
            tree = service.getDeptAndUserBydeptId(deptId);
        }
        return tree;
    }

    /**
     * 根据部门级别获取部门人员树，从顶级开始，所有的人员都展示
     * @param level 从0开始，0 查询顶级下面的人员，1 查询顶级下子部门及人员
     * @return
     */
    @RequestMapping(value = "getDeptAndUserTreeBylevel", produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getDeptAndUserTreeBylevel(String level) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(level)) {
            tree = service.getDeptAndUserTreeBylevel(level);
        }
        return tree;
    }

    /**
     * 根据部门级别获取部门人员树，从顶级开始，只展示最底级的人员
     * @param level 从0开始，0 查询顶级下面的人员，1 查询顶级下子部门及人员
     * @return
     */
    @RequestMapping(value = "getDeptAndUserTreeBylevel2", produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getDeptAndUserTreeBylevel2(String level) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(level)) {
            tree = service.getDeptAndUserTreeBylevel2(level);
        }
        return tree;
    }

    /**
     * 根据deptid查询部门及部门下所有直属子部门（包括顶级节点直到该部门，但上层部门节点不包括子部门）
     * @param deptId
     * @return
     */
    @RequestMapping(value = "getDeptListBydeptId", produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getDeptListBydeptId(String deptId) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(deptId)) {
            tree = service.getDeptListBydeptId(deptId);
        }
        return tree;
    }

    /**
     * 根据deptid查询部门及部门下所有子孙部门（包括顶级节点直到该部门，但上层部门节点没有其他子部门）
     * @param deptId
     * @return
     */
    @RequestMapping(value = "getAllDeptList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getAllDeptList(String deptId) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(deptId)) {
            tree = service.getAllDeptList(deptId);
        }
        return tree;
    }

    /**
     * 根据deptId只查找该部门及该部门下的所有人员（不包括子部门、父部门）
     * @param deptId
     * @return
     */
    @RequestMapping(value = "getUserByDeptId", produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getUserByDeptId(String deptId) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(deptId)) {
            tree = service.getUserByDeptId(deptId);
        }
        return tree;
    }

    /**
     * 根据人员职务获取部门人员树
     * @param positionName 职务(例'1','2','3',...)
     * @return
     */
    @RequestMapping(value = "getAllTreeBylevel", produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONArray getAllTreeBylevel(String positionName) {
        JSONArray tree = new JSONArray();
        if (StringUtils.isNotBlank(positionName)) {
            tree = service.getAllTreeBylevel(positionName);
        }
        return tree;
    }

    /**
     * 根据部门id和业务角色编号获取对应树
     * @param deptId 部门id
     * @param rolesNo 业务角色编号（如：C001,C002）
     * @return {'id':'id1','name':'name1','type':'1','asyn':false,'level':3,'deptId':441,'rolesNo':'C001,C002','url':'/system/component/tree/getDeptUserByDeptIdAndRolesNo'}
     */
    @RequestMapping(value = "getDeptUserByDeptIdAndRolesNo")
    @ResponseBody
    public JSONArray getDeptUserByDeptIdAndRolesNo(String deptId,String rolesNo){
        JSONObject json = service.getDeptUserByDeptIdAndRolesNo(deptId,rolesNo);
        JSONArray array = json.getJSONArray("data");
        for(int n = 0;n<array.size();n++){
            JSONObject object = array.getJSONObject(n);
            if("dept".equals(object.getString("type"))){
                object.put("uuid", object.get("deptid"));
                object.put("uupid", object.get("super_id"));
                object.put("name", object.get("deptname"));
            }else{
                object.put("uuid", object.get("userid"));
                object.put("uupid", object.get("deptid"));
                object.put("name", object.get("username"));
            }
        }
        return array;
    }
}
