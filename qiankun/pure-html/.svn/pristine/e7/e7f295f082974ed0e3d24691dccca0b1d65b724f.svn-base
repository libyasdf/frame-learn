UPDATE flow_wfoperate t SET t.controlcond ='TARGET' WHERE t.wfleveid in (select fw.wfleveid from flow_wfleve fw where fw.workflowid in(
 select fwf.workflowid from flow_workflow fwf where fwf.workflow_expand in (SELECT t.filetypeid FROM flow_filetype t WHERE t.pagename ='¿¼ÇÚ¹ÜÀí')
) and fw.is_start='1') and t.controlcond is null
