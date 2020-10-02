


--查询流程所有启动节点
select fw.* from flow_wfleve fw where fw.workflowid in(
 select fwf.workflowid from flow_workflow fwf where fwf.workflow_expand in (SELECT t.filetypeid FROM flow_filetype t WHERE t.pagename ='考勤管理')
) and fw.is_start='1'
--查询启动节点发出去的线
select t.*,t.rowid from flow_wfoperate t where t.wfleveid in (select fw.wfleveid from flow_wfleve fw where fw.workflowid in(
 select fwf.workflowid from flow_workflow fwf where fwf.workflow_expand in (SELECT t.filetypeid FROM flow_filetype t WHERE t.pagename ='考勤管理')
) and fw.is_start='1') and t.controlcond ='TARGET'
