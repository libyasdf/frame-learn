package com.sinosoft.sinoep.workflow.model;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：工作流参数数据包装类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月5日
 */
public class FlowClientData {

    /**
     * 表单操作类型：NEW-新建 EDIT-编辑 VIEW-查看 SAVE-保存 DELETE-删除
     */
    private String oper = "";
    /**
     * 提交工作流时使用的flag
     */
    private String flag = "";
    /**
     * 工作项ID VIEW时为已办工作项ID EDIT时为待办工作项ID
     */
    private String workitemid = "";
    /**
     * 工作流参数：文件类型ID(流程id)
     */
    private String filetypeid = "";
    /**
     * 工作流参数：流程唯一ID
     */
    private String workflowid = "";
    /**
     * 工作流参数：用户所在部门ID
     */
    private String deptid = "";
    /**
     * 工作流参数：用户ID
     */
    private String userid = "";
    /**
     * 业务主键ID(如果工作流挂接的是表单或复杂表单，该id为表单id；否则为案例系统对应业务表id)
     */
    private String recordid = "";
    /**
     * 工作流参数：填写的意见内容
     */
    private String idea = "";// 取意见域内容
    /**
     * 工作流参数：标题
     */
    private String title = "";
    /**
     * 状态标识(业务表状态)
     */
    private String subflag = "";
    /**
     * 弹出选择启动节点页面，选择结果赋给该属性
     */
    private String wfleveid = "";
    /**
     * 弹出选择路由页面，选择结果赋给该属性
     */
    private String wfoperateid = "";
    /**
     * 弹出选择人员的页面，选择的结果赋给该属性。子流程的参与者为人时赋给该属性
     */
    private String subflowlist;
    /**
     * 弹出选择人员的页面，选择的结果赋给该属性。子流程的参与者为部门时赋给该属性
     */
    private String subflowdeptlist;

    /**
     * 弹出选择人员的页面，选择的结果赋给该属性。非子流程的参与者为人时赋给该属性
     */
    private String selectuserlist;
    /**
     * 弹出选择人员的页面，选择的结果赋给该属性。非子流程的参与者为部门时赋给该属性
     */
    private String selectdeptlist;
    /**
     * 子系统id
     */
    private String sysid;
    /**
     * 待办中的属性字段（这个存的是分类）
     */
    private String attr;
    /**
     * 待办中的属性字段（这个存的是具体的分类）
     */
    private String attr1;

    public FlowClientData(HttpServletRequest request) {
        this.flag = request.getParameter("flag");
        if (flag == null || "".equals(flag)) {
            flag = "selectoption";
        }
        this.oper = request.getParameter("oper");
        this.workitemid = request.getParameter("workitemid");
        this.filetypeid = request.getParameter("filetypeid");
        this.workflowid = request.getParameter("workflowid");
        this.deptid = request.getParameter("deptid");
        this.userid = request.getParameter("userid");
        this.idea = request.getParameter("idea");// 取意见域内容
        this.recordid = request.getParameter("pkValue");
        this.title = request.getParameter("title");
        this.subflag = request.getParameter("subflag");

        this.wfleveid = request.getParameter("wfleveid");
        this.wfoperateid = request.getParameter("wfoperateid");

        this.subflowlist = request.getParameter("subflowlist");
        this.subflowdeptlist = request.getParameter("subflowdeptlist");
        this.selectuserlist = request.getParameter("selectuserlist");
        this.selectdeptlist = request.getParameter("selectdeptlist");
        this.attr = request.getParameter("attr");
        this.attr1 = request.getParameter("attr1");
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getWorkitemid() {
        return workitemid;
    }

    public void setWorkitemid(String workitemid) {
        this.workitemid = workitemid;
    }

    public String getFiletypeid() {
        return filetypeid;
    }

    public void setFiletypeid(String filetypeid) {
        this.filetypeid = filetypeid;
    }

    public String getWorkflowid() {
        return workflowid;
    }

    public void setWorkflowid(String workflowid) {
        this.workflowid = workflowid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWfleveid() {
        return wfleveid;
    }

    public void setWfleveid(String wfleveid) {
        this.wfleveid = wfleveid;
    }

    public String getWfoperateid() {
        return wfoperateid;
    }

    public void setWfoperateid(String wfoperateid) {
        this.wfoperateid = wfoperateid;
    }

    public String getSubflowlist() {
        return subflowlist;
    }

    public void setSubflowlist(String subflowlist) {
        this.subflowlist = subflowlist;
    }

    public String getSubflowdeptlist() {
        return subflowdeptlist;
    }

    public void setSubflowdeptlist(String subflowdeptlist) {
        this.subflowdeptlist = subflowdeptlist;
    }

    public String getSelectuserlist() {
        return selectuserlist;
    }

    public void setSelectuserlist(String selectuserlist) {
        this.selectuserlist = selectuserlist;
    }

    public String getSelectdeptlist() {
        return selectdeptlist;
    }

    public void setSelectdeptlist(String selectdeptlist) {
        this.selectdeptlist = selectdeptlist;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getSubflag() {
        return subflag;
    }

    public void setSubflag(String subflag) {
        this.subflag = subflag;
    }

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }
}
