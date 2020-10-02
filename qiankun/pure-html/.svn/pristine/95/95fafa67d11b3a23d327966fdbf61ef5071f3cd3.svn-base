package com.sinosoft.sinoep.yycx.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.user.entity.DeptAllInfo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.workflow.service.WorkFlowService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@RequestMapping("yycx")
@Controller
public class YingYongChengXu {

    public Logger log = LoggerFactory.getLogger(YingYongChengXu.class);

    @Autowired
    public UserInfoService userInfoService;

    @Autowired
    public WorkFlowService workFlowService;

    /**
     * 应用程序处室
     * 根据发送人的所在处室过滤接收节点的业务角色
     * @param request
     * @return
     */
    @RequestMapping("getRectiveDeptUser/execution")
    @ResponseBody
    public String getReceiveDeptUser(HttpServletRequest request){
        log.info("应用程序：getRectiveDeptUser");
        String deptUsers = "";
        JSONObject contextJson = JSONObject.fromObject(request.getParameter("context"));
        if(contextJson.size()>0){
            String wfoperateId = contextJson.getString("wfoperateid");
            String rolesNo = this.getRoleNoByWfoperateid(wfoperateId);
            String deptId = contextJson.getString("deptid");
            DeptAllInfo deptAllInfo = userInfoService.getDeptInfoByDeptId(deptId);
            String chushiId = deptAllInfo.getChushiId().toString();
            log.info("处室ID："+chushiId);
            JSONObject deptJSON = userInfoService.getAllDeptBySuperId(chushiId);
            log.info("处室下所有部门：",deptJSON.toString());
            deptUsers = this.getDeptUsersByRolesNoAndDeptJSON(rolesNo,deptJSON);
            log.info("返回结果：",deptUsers);
        }
        if(StringUtils.isBlank(deptUsers)){
            return " ";
        }else {
            return deptUsers;
        }
    }

    /**
     * 应用程序部门
     * 根据发送人的所在部门过滤接收节点的业务角色
     * @param request
     * @return
     */
    @RequestMapping("getRectiveDeptUserBm/execution")
    @ResponseBody
    public String getReceiveDeptUserBm(HttpServletRequest request){
        log.info("应用程序：getRectiveDeptUserBm");
        String deptUsers = "";
        JSONObject contextJson = JSONObject.fromObject(request.getParameter("context"));
        if(contextJson.size()>0) {
            String wfoperateId = contextJson.getString("wfoperateid");
            String rolesNo = this.getRoleNoByWfoperateid(wfoperateId);
            String deptId = contextJson.getString("deptid");
            DeptAllInfo deptAllInfo = userInfoService.getDeptInfoByDeptId(deptId);
            String juId = deptAllInfo.getJuId().toString();
            log.info("部门ID："+juId);
            JSONObject deptJSON = userInfoService.getAllDeptBySuperId(juId);
            deptUsers = this.getDeptUsersByRolesNoAndDeptJSON(rolesNo,deptJSON);
            log.info("返回结果："+deptUsers);
        }
        if(StringUtils.isBlank(deptUsers)){
            return " ";
        }else {
            return deptUsers;
        }
    }

    /**
     * 根据下个节点编号获取对应业务角色编号
     * @param wfoperateId
     * @return
     */
    private String getRoleNoByWfoperateid(String wfoperateId){
        String getRolesIdSql = "";
        String rolesId = "";
        String rolesNo = "";
        if(StringUtils.isNotBlank(wfoperateId)) {
            try {
                getRolesIdSql = "select l.roleid from flow_wfoperate o ,flow_wfleve l where o.nextwfleveid = l.wfleveid and o.wfoperateid = '" + wfoperateId + "'";
                List<Map<String, Object>> rolesList = workFlowService.getDataBySql(getRolesIdSql);
                for (Map<String, Object> map : rolesList) {
                    String roleid = (String) map.get("roleid");
                    if (roleid.contains(",")) {
                        String[] roleIdArray = roleid.split(",");
                        for (String s : roleIdArray) {
                            rolesId += "'" + s + "',";
                        }
                    } else {
                        rolesId += "'" + roleid + "',";
                    }
                }
                rolesId = rolesId.substring(0, rolesId.length() - 1);
                String getRolesNoSql = "select role_no from sys_flow_role where roleid in (" + rolesId + ")";
                JSONObject rolesJson = userInfoService.getDateBySql(getRolesNoSql);
                List<Map<String, Object>> rolesNoList = rolesJson.getJSONArray("data");
                for (Map<String, Object> map : rolesNoList) {
                    rolesNo += "'" + (String) map.get("role_no") + "',";
                }
                rolesNo = rolesNo.substring(0, rolesNo.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                log.error("自定义sql查询错误！");
            }
        }
        return rolesNo;
    }

    /**
     * 根据业务指定部门 选择指定部门下业务角色的人
     * 根据页面选择的指定部门过滤接收节点的业务角色
     * @param request
     * @return
     */
    @RequestMapping("getSelectDept/execution")
    @ResponseBody
    public String getSelectDept(HttpServletRequest request){
        log.info("应用程序：getSelectDept");
        String deptUsers = "";
        JSONObject contextJson = JSONObject.fromObject(request.getParameter("context"));
        if(contextJson.size()>0){
            String wfoperateId = contextJson.getString("wfoperateid");
            String rolesNo = this.getRoleNoByWfoperateid(wfoperateId);
            String deptIds = contextJson.getString("attr");
            log.info("传递的部门id="+deptIds);
            if(StringUtils.isNotBlank(deptIds)){
                String[] deptIdArr = deptIds.split(",");
                for (String deptId:deptIdArr) {
                    JSONObject deptJson = userInfoService.getAllDeptBySuperId(deptId);
                    deptUsers += this.getDeptUsersByRolesNoAndDeptJSON(rolesNo,deptJson) + ",";
                }
            }
            if (deptUsers.length() > 0){
                deptUsers = deptUsers.substring(0,deptUsers.length() - 1);
            }
            log.info("返回结果："+deptUsers);
        }
        if (StringUtils.isBlank(deptUsers)){
            return " ";
        }
        return deptUsers;
    }

    /**
     * 指定人
     * 发送给页面选择的指定的人
     * @param request
     * @return
     */
    @RequestMapping("getSelectUser/execution")
    @ResponseBody
    public String  getSelectUser(HttpServletRequest request){
        String deptUsers = "";
        JSONObject contextJson = JSONObject.fromObject(request.getParameter("context"));
        if(contextJson.size()>0){
            String userIds = contextJson.getString("attr");
            String deptIds = contextJson.getString("attr1");
            if(StringUtils.isNotBlank(userIds) && StringUtils.isNotBlank(deptIds)){
                log.info( "传递的部门id="+deptIds+"用户id="+userIds);
                String[] userIdArr = userIds.split(",");
                String[] deptIdArr = deptIds.split(",");
                if (userIdArr.length == deptIdArr.length){
                    for(int i = 0;i < userIdArr.length;i++){
                        deptUsers += deptIdArr[i] + "*" + userIdArr[i] + ",";
                    }
                }
            }
        }
        if (deptUsers.length() > 0 ){
            deptUsers = deptUsers.substring(0,deptUsers.length() - 1);
        }
        if(StringUtils.isBlank(deptUsers)){
            return "";
        }
        return deptUsers;
    }

    /**
     *根据业务角色编号和部门json获取显示人员 deptid*userid,deptid*userid,...
     * @param rolesNo
     * @param deptJSON
     * @return
     */
    private String getDeptUsersByRolesNoAndDeptJSON(String rolesNo,JSONObject deptJSON){
        String deptUsers ="";
        String deptIds = "";
        JSONArray jsonArray = deptJSON.getJSONArray("data");
        List<Dept> list = jsonArray.toList(jsonArray,Dept.class);
        for(Dept item :list){
            deptIds +=item.getDeptid()+",";
        }
        System.out.println("获取到所有符合的部门ids="+deptIds);
        List<Map<String,Object>> users = this.yycxgetUserByDeptIdAndRolesNo(deptIds,rolesNo);
        for (Map<String,Object> map : users){
            String userDeptid = String.valueOf(map.get("deptid"));
            System.out.println("根据用户id获取部门id="+userDeptid);
            if(userDeptid.contains(",")){
                String[] userDeptIdArry = userDeptid.split(",");
                for (String s:userDeptIdArry){
                    if(deptIds.contains(s)){
                        userDeptid = s;
                    }
                }
            }
            deptUsers+=userDeptid+"*"+String.valueOf(map.get("uid"))+",";
        }
        if(StringUtils.isNotBlank(deptUsers)){
            deptUsers = deptUsers.substring(0,deptUsers.length()-1);
        }
        log.info("工作流调用OA时间方法放回结果："+deptUsers);
        return deptUsers;
    }

    /**
     * 根据部门id和业务角色编号获取人员
     * @param deptId
     * @param rolesNo
     * @return
     */
    private List<Map<String,Object>> yycxgetUserByDeptIdAndRolesNo(String deptId,String rolesNo){
        List<Map<String ,Object>> userInfoList = new ArrayList<Map<String ,Object>>();
        try{
            if(StringUtils.isNotBlank(rolesNo) && StringUtils.isNotBlank(deptId)){
                if(!rolesNo.contains("'")){
                    String roles = "";
                    String[] rolesNoArray = rolesNo.split(",");
                    for(String s:rolesNoArray){
                        roles += "'"+s+"',";
                    }
                    rolesNo = roles.substring(0,roles.length()-1);
                }
                String deptIds = "";
                String[] deptIdArray = deptId.split(",");
                for(String s:deptIdArray){
                    deptIds+="'"+s+"',";
                }
                deptId = deptIds .substring(0,deptIds.length()-1);
                String sql = " select u.userid,u.username,u.deptid,u.deptname " +
                        " from (select c.userid,c.username,a.deptid,a.deptname" +
                        " from (select rownum as row_num,t.deptname,t.deptid,t.order_no" +
                        " from sys_flow_dept t" +
                        " where t.status = '1' " +
                        " start with t.deptid = '441'" +
                        " connect by prior t.deptid = t.super_id" +
                        " order  siblings by t.order_no )a," +
                        " (select * from sys_user_dprb t where t.status ='1')b," +
                        " (select * from sys_flow_user t where t.status ='1')c " +
                        " where a.deptid = b.deptid " +
                        " and b.userid(+) = c.userid " +
                        " and c.userid in(select u.userid from sys_user_dprb ud," +
                        " sys_flow_user u, " +
                        " sys_user_frole s," +
                        " sys_flow_role r" +
                        " where u.status= '1' " +
                        " and ud.status = '1'" +
                        " and s.status = '1' " +
                        " and r.status = '1'" +
                        " and u.userid = ud.userid" +
                        " and ud.id = s.u_dept_id " +
                        " and r.roleid = s.roleid" +
                        " and r.role_no in("+rolesNo+") " +
                        " and ud.deptid in("+deptId+"))" +
                        " and b.id in(select ud.id from sys_user_dprb ud," +
                        " sys_flow_user u, " +
                        " sys_user_frole s," +
                        " sys_flow_role r" +
                        " where u.status= '1' " +
                        " and ud.status = '1'" +
                        " and s.status = '1' " +
                        " and r.status = '1'" +
                        " and u.userid = ud.userid" +
                        " and ud.id = s.u_dept_id " +
                        " and r.roleid = s.roleid" +
                        " and r.role_no in("+rolesNo+") " +
                        " and ud.deptid in("+deptId+"))" +
                        " order by a.row_num, b.order_no) u ";
                JSONObject json = userInfoService.getDateBySql(sql);
                List<JSONObject> list = json.getJSONArray("data");
                for(JSONObject obj :list){
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("uid",obj.getString("userid"));
                    map.put("userName",obj.getString("username"));
                    map.put("deptid",obj.getString("deptid"));
                    map.put("deptname",obj.getString("deptname"));
                    userInfoList.add(map);
                }
            }
        }catch (Exception e){
            log.error("获取部门下业务角色的人员信息异常",e);
            e.printStackTrace();
        }
        return userInfoList;
    }


    /**
     * 应用程序——上一节点发送人作为下一节点接收人
     * 根据当前待办ID，查询前一节点deptid*userid
     * @param request
     * @return
     */
    @RequestMapping("getPreUserToNextLevel/execution")
    @ResponseBody
    public String getPreUserToNextLevel(HttpServletRequest request){
        String deptUsers = "";
        try{
            JSONObject contextJson = JSONObject.fromObject(request.getParameter("context"));
            if(contextJson.size()>0){
                String workItemId = contextJson.getString("workitemid");
                log.info("应用程序：上一节点发送人作为下一节点接收人。workItemId：",workItemId);
                if(StringUtils.isNotBlank(workItemId)){
                    StringBuilder sql = new StringBuilder("select * from flow_write t where t.id = '");
                    sql.append(workItemId).append("'");
                    log.info("查询待办sql:",sql.toString());
                    List<Map<String, Object>> waitDoList = workFlowService.getDataBySql(sql.toString());
                    if (!waitDoList.isEmpty()) {
                        for (Map<String, Object> waitDo:waitDoList) {
                            String preUserId = waitDo.get("preuserid").toString();
                            String preDeptId = waitDo.get("predeptid").toString();
                            deptUsers += preDeptId + "*" + preUserId + ",";
                        }
                    }
                }
                if(StringUtils.isNotBlank(deptUsers)){
                    deptUsers = deptUsers.substring(0,deptUsers.length()-1);
                }
                log.info("上一节点发送人：",deptUsers);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        if(StringUtils.isBlank(deptUsers)){
            return " ";
        }else {
            return deptUsers;
        }
    }

    /**
     * 应用程序——起草人作为下一节点接收人
     * 根据当前待办ID，查询起草人deptid*userid
     * @param request
     * @return
     */
    @RequestMapping("getDraftUserToNextLevel/execution")
    @ResponseBody
    public String getDraftUserToNextLevel(HttpServletRequest request){
        String deptUsers = "";
        try{
            JSONObject contextJson = JSONObject.fromObject(request.getParameter("context"));
            if(contextJson.size()>0){
                String workItemId = contextJson.getString("workitemid");
                log.info("应用程序：起草人作为下一节点接收人。workItemId：",workItemId);
                if(StringUtils.isNotBlank(workItemId)){
                    StringBuilder sql = new StringBuilder("select * from flow_write t where t.id = '");
                    sql.append(workItemId).append("'");
                    log.info("查询待办sql:",sql.toString());
                    List<Map<String, Object>> waitDoList = workFlowService.getDataBySql(sql.toString());
                    if (!waitDoList.isEmpty()) {
                        for (Map<String, Object> waitDo:waitDoList) {
                            String draftUserId = waitDo.get("draftuser").toString();
                            String draftDeptId = waitDo.get("draftdept").toString();
                            deptUsers += draftDeptId + "*" + draftUserId + ",";
                        }
                    }
                }
                if(StringUtils.isNotBlank(deptUsers)){
                    deptUsers = deptUsers.substring(0,deptUsers.length()-1);
                }
                log.info("拟稿人：",deptUsers);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        if(StringUtils.isBlank(deptUsers)){
            return " ";
        }else {
            return deptUsers;
        }
    }

}


