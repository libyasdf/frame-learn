package com.sinosoft.sinoep.workflow.util;

import java.io.Serializable;
import workflow.servlet.ParameterBean;

public class ParaBeanForXml  implements Serializable {
	private static final long serialVersionUID = 1531851825550927243L;
	  private String sysid = "";
	  private String flag = "selectoption";
	  private String workitemid = "";
	  private String userid = "";
	  private String subflag = "";
	  private String deptid = "";
	  private String filetypeid = "";
	  private String workflowid = "";
	  private String recordid = "";
	  private String relatype = "0";
	  private String docid = "";
	  private String title = "";
	  private String userflag = "1";
	  private String sql = "";
	  private String sqlone = "";
	  private String sqltwo = "";
	  private String sqlthree = "";
	  private String sqlfour = "";
	  private String sqlfive = "";
	  private String sqlsix = "";
	  private String sqlseven = "";
	  private String sqleight = "";
	  private String sqlnine = "";
	  private String sqlten = "";
	  private String idea = "";
	  private String sign = "";
	  private String sqlobj = "";
	  private String wfleveid = "";
	  private String wfoperateid = "";
	  private String settime = "";
	  private String flowtime = "";
	  private String remind = "1";
	  private String para = "1";
	  private String tableandidname = "";
	  private String disflag = "0";
	  private String remindtype = "";
	  private String startflag = "0";
	  private String transferflag = "0";
	  private String attr = "";
	  private String attr1 = "";
	  private String attr2 = "";
	  private String deptvar = "";
	  private String sendsms = "1";
	  private String deptorder = "";
	  private String selectuserlist = "";
	  private String selectdeptlist = "";
	  private String subflowlist = "";
	  private String subflowdeptlist = "";
	  private String userlist = "";
	  private String freeflag = "0";
	  private String breakflag = "";
	  private String ishadata = "true";
	  private String exception = "";
	  private String wfoperatename = "";
	  private String username = "";
	  private String examinetag = "";
	  private String extendattr = "";
	  private String superfiletype = "";
	  private String subend = "0";
	  private String iskeepwrite = "0";
	  private String forcestart = "0";
	  private String startwfleveId = "";
	  private String predeptid = "";
	  private String preuserid = "";
	  private String sourceuser = "";
	  private String touser = "";
	  private String appointuser = "";
	  private String isread = "1";
	  private String isforcepop = "0";
	  private String isforceselectop = "0";

	  public ParameterBean fetchData()
	  {
	    ParameterBean bean = new ParameterBean();
	    bean.setSysid(this.sysid);
	    bean.setAttr(this.attr);
	    bean.setAttr1(this.attr1);
	    bean.setAttr2(this.attr2);
	    bean.setBreakflag(this.breakflag);
	    bean.setDeptid(this.deptid);
	    bean.setDeptorder(this.deptorder);
	    bean.setDeptvar(this.deptvar);
	    bean.setDisflag(this.disflag);
	    bean.setDocid(this.docid);
	    bean.setExaminetag(this.examinetag);
	    bean.setException(this.exception);
	    bean.setExtendattr(this.extendattr);
	    bean.setFiletypeid(this.filetypeid);
	    bean.setFlag(this.flag);
	    bean.setFlowtime(this.flowtime);
	    bean.setForcestart(this.forcestart);
	    bean.setFreeflag(this.freeflag);
	    bean.setIdea(this.idea);
	    bean.setIshadata(this.ishadata);
	    bean.setIskeepwrite(this.iskeepwrite);
	    bean.setPara(this.para);
	    bean.setRecordid(this.recordid);
	    bean.setRelatype(this.relatype);
	    bean.setRemind(this.remind);
	    bean.setRemindtype(this.remindtype);
	    bean.setSelectdeptlist(this.selectdeptlist);
	    bean.setSelectuserlist(this.selectuserlist);
	    bean.setSendsms(this.sendsms);
	    bean.setSettime(this.settime);
	    bean.setSign(this.sign);
	    bean.setSql(this.sql);
	    bean.setSqlone(this.sqlone);
	    bean.setSqltwo(this.sqltwo);
	    bean.setSqlthree(this.sqlthree);
	    bean.setSqlfour(this.sqlfour);
	    bean.setSqlfive(this.sqlfive);
	    bean.setSqlsix(this.sqlsix);
	    bean.setSqlseven(this.sqlseven);
	    bean.setSqleight(this.sqleight);
	    bean.setSqlnine(this.sqlnine);
	    bean.setSqlten(this.sqlten);
	    bean.setSubflowdeptlist(this.subflowdeptlist);
	    bean.setSubflowlist(this.subflowlist);
	    bean.setSuperfiletype(this.superfiletype);
	    bean.setStartflag(this.startflag);
	    bean.setSubend(this.subend);
	    bean.setSubflag(this.subflag);
	    bean.setTableandidname(this.tableandidname);
	    bean.setTitle(this.title);
	    bean.setTransferflag(this.transferflag);
	    bean.setUserflag(this.userflag);
	    bean.setUserid(this.userid);
	    bean.setUsername(this.username);
	    bean.setWfleveid(this.wfleveid);
	    bean.setWfoperateid(this.wfoperateid);
	    bean.setWfoperatename(this.wfoperatename);
	    bean.setWorkflowid(this.workflowid);
	    bean.setWorkitemid(this.workitemid);
	    bean.setIsforcepop(this.isforcepop);
	    bean.setIsforceselectop(this.isforceselectop);
	    return bean;
	  }

	  public String getSysid() {
	    return this.sysid;
	  }

	  public void setSysid(String sysid) {
	    this.sysid = sysid;
	  }

	  public String getFlag() {
	    return this.flag;
	  }

	  public void setFlag(String flag) {
	    this.flag = flag;
	  }

	  public String getWorkitemid() {
	    return this.workitemid;
	  }

	  public void setWorkitemid(String workitemid) {
	    this.workitemid = workitemid;
	  }

	  public String getUserid() {
	    return this.userid;
	  }

	  public void setUserid(String userid) {
	    this.userid = userid;
	  }

	  public String getSubflag() {
	    return this.subflag;
	  }

	  public void setSubflag(String subflag) {
	    this.subflag = subflag;
	  }

	  public String getDeptid() {
	    return this.deptid;
	  }

	  public void setDeptid(String deptid) {
	    this.deptid = deptid;
	  }

	  public String getFiletypeid() {
	    return this.filetypeid;
	  }

	  public void setFiletypeid(String filetypeid) {
	    this.filetypeid = filetypeid;
	  }

	  public String getWorkflowid() {
	    return this.workflowid;
	  }

	  public void setWorkflowid(String workflowid) {
	    this.workflowid = workflowid;
	  }

	  public String getRecordid() {
	    return this.recordid;
	  }

	  public void setRecordid(String recordid) {
	    this.recordid = recordid;
	  }

	  public String getRelatype() {
	    return this.relatype;
	  }

	  public void setRelatype(String relatype) {
	    this.relatype = relatype;
	  }

	  public String getDocid() {
	    return this.docid;
	  }

	  public void setDocid(String docid) {
	    this.docid = docid;
	  }

	  public String getTitle() {
	    return this.title;
	  }

	  public void setTitle(String title) {
	    this.title = title;
	  }

	  public String getUserflag() {
	    return this.userflag;
	  }

	  public void setUserflag(String userflag) {
	    this.userflag = userflag;
	  }

	  public String getSql() {
	    return this.sql;
	  }

	  public void setSql(String sql) {
	    this.sql = sql;
	  }

	  public String getSqlone() {
	    return this.sqlone;
	  }

	  public void setSqlone(String sqlone) {
	    this.sqlone = sqlone;
	  }

	  public String getSqltwo() {
	    return this.sqltwo;
	  }

	  public void setSqltwo(String sqltwo) {
	    this.sqltwo = sqltwo;
	  }

	  public String getSqlthree() {
	    return this.sqlthree;
	  }

	  public void setSqlthree(String sqlthree) {
	    this.sqlthree = sqlthree;
	  }

	  public String getSqlfour() {
	    return this.sqlfour;
	  }

	  public void setSqlfour(String sqlfour) {
	    this.sqlfour = sqlfour;
	  }

	  public String getSqlfive() {
	    return this.sqlfive;
	  }

	  public void setSqlfive(String sqlfive) {
	    this.sqlfive = sqlfive;
	  }

	  public String getSqlsix() {
	    return this.sqlsix;
	  }

	  public void setSqlsix(String sqlsix) {
	    this.sqlsix = sqlsix;
	  }

	  public String getSqlseven() {
	    return this.sqlseven;
	  }

	  public void setSqlseven(String sqlseven) {
	    this.sqlseven = sqlseven;
	  }

	  public String getSqleight() {
	    return this.sqleight;
	  }

	  public void setSqleight(String sqleight) {
	    this.sqleight = sqleight;
	  }

	  public String getSqlnine() {
	    return this.sqlnine;
	  }

	  public void setSqlnine(String sqlnine) {
	    this.sqlnine = sqlnine;
	  }

	  public String getSqlten() {
	    return this.sqlten;
	  }

	  public void setSqlten(String sqlten) {
	    this.sqlten = sqlten;
	  }

	  public String getIdea() {
	    return this.idea;
	  }

	  public void setIdea(String idea) {
	    this.idea = idea;
	  }

	  public String getSign() {
	    return this.sign;
	  }

	  public void setSign(String sign) {
	    this.sign = sign;
	  }

	  public String getSqlobj() {
	    return this.sqlobj;
	  }

	  public void setSqlobj(String sqlobj) {
	    this.sqlobj = sqlobj;
	  }

	  public String getWfleveid() {
	    return this.wfleveid;
	  }

	  public void setWfleveid(String wfleveid) {
	    this.wfleveid = wfleveid;
	  }

	  public String getWfoperateid() {
	    return this.wfoperateid;
	  }

	  public void setWfoperateid(String wfoperateid) {
	    this.wfoperateid = wfoperateid;
	  }

	  public String getSettime() {
	    return this.settime;
	  }

	  public void setSettime(String settime) {
	    this.settime = settime;
	  }

	  public String getFlowtime() {
	    return this.flowtime;
	  }

	  public void setFlowtime(String flowtime) {
	    this.flowtime = flowtime;
	  }

	  public String getRemind() {
	    return this.remind;
	  }

	  public void setRemind(String remind) {
	    this.remind = remind;
	  }

	  public String getPara() {
	    return this.para;
	  }

	  public void setPara(String para) {
	    this.para = para;
	  }

	  public String getTableandidname() {
	    return this.tableandidname;
	  }

	  public void setTableandidname(String tableandidname) {
	    this.tableandidname = tableandidname;
	  }

	  public String getDisflag() {
	    return this.disflag;
	  }

	  public void setDisflag(String disflag) {
	    this.disflag = disflag;
	  }

	  public String getRemindtype() {
	    return this.remindtype;
	  }

	  public void setRemindtype(String remindtype) {
	    this.remindtype = remindtype;
	  }

	  public String getStartflag() {
	    return this.startflag;
	  }

	  public void setStartflag(String startflag) {
	    this.startflag = startflag;
	  }

	  public String getTransferflag() {
	    return this.transferflag;
	  }

	  public void setTransferflag(String transferflag) {
	    this.transferflag = transferflag;
	  }

	  public String getAttr() {
	    return this.attr;
	  }

	  public void setAttr(String attr) {
	    this.attr = attr;
	  }

	  public String getAttr1() {
	    return this.attr1;
	  }

	  public void setAttr1(String attr1) {
	    this.attr1 = attr1;
	  }

	  public String getAttr2() {
	    return this.attr2;
	  }

	  public void setAttr2(String attr2) {
	    this.attr2 = attr2;
	  }

	  public String getDeptvar() {
	    return this.deptvar;
	  }

	  public void setDeptvar(String deptvar) {
	    this.deptvar = deptvar;
	  }

	  public String getSendsms() {
	    return this.sendsms;
	  }

	  public void setSendsms(String sendsms) {
	    this.sendsms = sendsms;
	  }

	  public String getDeptorder() {
	    return this.deptorder;
	  }

	  public void setDeptorder(String deptorder) {
	    this.deptorder = deptorder;
	  }

	  public String getSelectuserlist() {
	    return this.selectuserlist;
	  }

	  public void setSelectuserlist(String selectuserlist) {
	    this.selectuserlist = selectuserlist;
	  }

	  public String getSelectdeptlist() {
	    return this.selectdeptlist;
	  }

	  public void setSelectdeptlist(String selectdeptlist) {
	    this.selectdeptlist = selectdeptlist;
	  }

	  public String getSubflowlist() {
	    return this.subflowlist;
	  }

	  public void setSubflowlist(String subflowlist) {
	    this.subflowlist = subflowlist;
	  }

	  public String getSubflowdeptlist() {
	    return this.subflowdeptlist;
	  }

	  public void setSubflowdeptlist(String subflowdeptlist) {
	    this.subflowdeptlist = subflowdeptlist;
	  }

	  public String getUserlist() {
	    return this.userlist;
	  }

	  public void setUserlist(String userlist) {
	    this.userlist = userlist;
	  }

	  public String getFreeflag() {
	    return this.freeflag;
	  }

	  public void setFreeflag(String freeflag) {
	    this.freeflag = freeflag;
	  }

	  public String getBreakflag() {
	    return this.breakflag;
	  }

	  public void setBreakflag(String breakflag) {
	    this.breakflag = breakflag;
	  }

	  public String getIshadata() {
	    return this.ishadata;
	  }

	  public void setIshadata(String ishadata) {
	    this.ishadata = ishadata;
	  }

	  public String getException() {
	    return this.exception;
	  }

	  public void setException(String exception) {
	    this.exception = exception;
	  }

	  public String getWfoperatename() {
	    return this.wfoperatename;
	  }

	  public void setWfoperatename(String wfoperatename) {
	    this.wfoperatename = wfoperatename;
	  }

	  public String getUsername() {
	    return this.username;
	  }

	  public void setUsername(String username) {
	    this.username = username;
	  }

	  public String getExaminetag() {
	    return this.examinetag;
	  }

	  public void setExaminetag(String examinetag) {
	    this.examinetag = examinetag;
	  }

	  public String getExtendattr() {
	    return this.extendattr;
	  }

	  public void setExtendattr(String extendattr) {
	    this.extendattr = extendattr;
	  }

	  public String getSuperfiletype() {
	    return this.superfiletype;
	  }

	  public void setSuperfiletype(String superfiletype) {
	    this.superfiletype = superfiletype;
	  }

	  public String getSubend() {
	    return this.subend;
	  }

	  public void setSubend(String subend) {
	    this.subend = subend;
	  }

	  public String getIskeepwrite() {
	    return this.iskeepwrite;
	  }

	  public void setIskeepwrite(String iskeepwrite) {
	    this.iskeepwrite = iskeepwrite;
	  }

	  public String getForcestart() {
	    return this.forcestart;
	  }

	  public void setForcestart(String forcestart) {
	    this.forcestart = forcestart;
	  }

	  public String getPredeptid() {
	    return this.predeptid;
	  }

	  public void setPredeptid(String predeptid) {
	    this.predeptid = predeptid;
	  }

	  public String getPreuserid() {
	    return this.preuserid;
	  }

	  public void setPreuserid(String preuserid) {
	    this.preuserid = preuserid;
	  }

	  public String getStartwfleveId() {
	    return this.startwfleveId;
	  }

	  public void setStartwfleveId(String startWfleveId) {
	    this.startwfleveId = startWfleveId;
	  }

	  public String getSourceuser()
	  {
	    return this.sourceuser;
	  }

	  public void setSourceuser(String sourceuser) {
	    this.sourceuser = sourceuser;
	  }

	  public String getTouser() {
	    return this.touser;
	  }

	  public void setTouser(String touser) {
	    this.touser = touser;
	  }

	  public String getAppointuser() {
	    return this.appointuser;
	  }

	  public void setAppointuser(String appointuser) {
	    this.appointuser = appointuser;
	  }

	  public String getIsread() {
	    return this.isread;
	  }

	  public void setIsread(String isread) {
	    this.isread = isread;
	  }

	  public String getIsforcepop() {
	    return this.isforcepop;
	  }

	  public void setIsforcepop(String isforcepop) {
	    this.isforcepop = isforcepop;
	  }

	  public String getIsforceselectop() {
	    return this.isforceselectop;
	  }

	  public void setIsforceselectop(String isforceselectop) {
	    this.isforceselectop = isforceselectop;
	  }
}
