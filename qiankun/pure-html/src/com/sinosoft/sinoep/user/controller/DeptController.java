package com.sinosoft.sinoep.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.uias.util.RecourseUtils;
import com.sinosoft.sinoep.user.entity.DeptAllInfo;
import com.sinosoft.sinoep.user.entity.SysFlowDept;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.api.dept.DeptInfoService;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.exception.SysException;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.util.tool.ParamUtils;

import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * 调用统一用户类
 * 
 * @author dzh
 *
 */
@Controller
@RequestMapping("dept")
public class DeptController {

    @Resource
    UserInfoService userInfoService;
    @Autowired
    DeptInfoService deptInfoService;

    @Autowired
    private com.sinosoft.sinoep.user.service.DeptInfoService service;

    /**
     * 获取部门树
     * 
     * @param request
     * @param response
     * @return
     * @throws SysException
     */
    @RequestMapping(value = "getDeptOrUserTree", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String selectFlowDept(HttpServletRequest request, HttpServletResponse response) throws SysException {
        String type = ParamUtils.getParameter(request, "type", "2");
        String orgId = ParamUtils.getParameter(request, "orgId", "");
        String nodeType = ParamUtils.getParameter(request, "nodeType", "user");
        String status = ParamUtils.getParameter(request, "status", "1");
        String deptId = ParamUtils.getParameter(request, "id", "0");
        String superId = ParamUtils.getParameter(request, "superId", "");
        String result = "";
        try {
            result = userInfoService.getDeptOrUserTree(type, nodeType, orgId, deptId, superId, status);
        }
        catch (Exception e) {
            throw new SysException(ConfigConsts.ERROR_STATUS, "获取部门树异常", e);
        }
        return result;
    }

    /**
     * 根据名字查询部门和用户
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getDepartmentStaffByName", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getDepartmentStaffByName(HttpServletRequest request, HttpServletResponse response) {
        String type = ParamUtils.getParameter(request, "type", "");//type 获取用户：user 获取部门 ：dept 获取全部 ：""
        String qName = ParamUtils.getParameter(request, "qName", "");//要查询的关键字
        String deptId = ParamUtils.getParameter(request, "deptId", "");//当前树的部门id，顶层节点id
        String userid = "1"; //全部查询参数
        String result = null;
        result = userInfoService.getDepartmentStaffByName(type, qName, userid,deptId);
        return result == null ? "" : result;
    }

    /**
     * 
     * <B>方法名称：获取部门或用户或部门和用户列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午10:10:02
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "getDepartmentStaffTree", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getDepartmentStaffTree(HttpServletRequest request, HttpServletResponse response) {
        String type = ParamUtils.getParameter(request, "type", "");//type 获取用户：user 获取部门 ：dept 获取全部 ：""
        String result = "";
        result = userInfoService.getDepartmentStaffTree(type);
        return result;
    }

    /**
     * 
     * <B>方法名称：根据部门ID获取用户</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param deptid
     */
    @ResponseBody
    @RequestMapping("/getUserByDeptid")
    public JSONArray getUserByDeptid(String deptid, String status, HttpServletResponse response) {
        JSONArray arr = deptInfoService.getUserByDeptid(deptid, status);
        return arr;
    }

    @ResponseBody
    @RequestMapping("/getDeptAllInfoByDeptId")
    public void getDeptAllInfoByDeptId(String deptId){
        DeptAllInfo entity = userInfoService.getDeptInfoByDeptId(deptId);
    }

    /**
     * TODO 根据部门ID获取部门信息
     * @param deptId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDeptInfoByDeptId")
    public JSONObject getDeptInfoByDeptId(String deptId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        Dept dept = new Dept();
        try{
            dept = userInfoService.getDeptById(deptId);
            json.put("flag","1");
            json.put("data",dept);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 根据部门编码获取部门信息（sys_flow_dept）
     * @param deptNumbers 格式：1,2,3
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDeptInfoByDeptNumber")
    public List<Map<String, Object>> getDeptInfoByDeptNumber(String deptNumbers){
        return this.service.getDeptInfoByDeptNumber(deptNumbers);
    }

    /**
     * 根据部门id和单位属性获取部门树
     * @param deptId 部门id 默认为441
     * @param unitType 多个用逗号分隔（sys_dictionary 表 remarks='4'）
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDeptInfoByDeptIdAndUnitType")
    public JSONObject getDeptInfoByDeptIdAndUnitType(String deptId,String unitType){
        JSONObject json = this.service.getDeptInfoByDeptIdAndUnitType(deptId,unitType);
        return json;
    }

    @RequestMapping("/getSuperIdByDeptId")
    @ResponseBody
    public JSONObject getSuperIdByDeptId(String deptId){
        JSONObject json = this.service.getSuperIdByDeptId(deptId);
        return json;
    }
}
