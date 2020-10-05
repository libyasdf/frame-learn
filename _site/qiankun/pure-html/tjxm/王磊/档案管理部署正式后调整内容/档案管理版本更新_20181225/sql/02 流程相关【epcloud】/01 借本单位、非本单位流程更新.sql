--url配置删除
delete from FLOW_WAITURL t where t.id = '7a35e6071d424bc0a8a58fcce9abb3e5';
delete from FLOW_WAITURL t where t.id = '701b36ca17f54a23a2f0d8ecd1a6da1c';

--url配置新增
insert into FLOW_WAITURL (ID,NAME,WAIT_URL,MEMO,EXTEND,STATE,URLTYPE,SYS_ID,ORGID,UPDATE_TIME) values ('7a35e6071d424bc0a8a58fcce9abb3e5','借阅档案','/modules/dagl/daly/borrow/apply/borrowApplyEditForm.html','aa','','1','0','97206','18043','2019-03-18 13:51:48');
insert into FLOW_WAITURL (ID,NAME,WAIT_URL,MEMO,EXTEND,STATE,URLTYPE,SYS_ID,ORGID,UPDATE_TIME) values ('701b36ca17f54a23a2f0d8ecd1a6da1c','借阅档案','/modules/dagl/daly/borrow/apply/borrowApplyReadOnlyForm.html','','','1','1','97206','18043','2019-03-18 13:51:48');
--流程删除
--删除流程节点意见
delete from flow_subidea t where t.workflowid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程节点签字
delete from flow_subsign t where t.workflowid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程操作
delete from flow_wfoperate t where t.workflowid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程节点
delete from flow_wfleve t where t.workflowid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程变量
delete from flow_processvariable t where t.workflowid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程与权限的关联
delete from flow_nodepuriewele t where t.roleid in (select nr.roleid from flow_nodepuriewrole nr where nr.workflowid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099'));
delete from flow_nodepuriewrole t where t.workflowid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程类型与签字类型的关联
delete from flow_subsign_relation t where t.type_id in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程类型与意见类型的关联
delete from flow_subidea_relation t where t.type_id in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程类型和业务表关联
delete from flow_define_relation t where t.type_id in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程类型和按钮关联
delete from flow_button_relation t where t.type_id in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程与流程类型关联
delete from flow_flowtype t where t.filetypeid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
delete from flow_flowtype t where t.workflowid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程
delete from flow_workflow t where t.workflowid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');
--删除流程类型
delete from flow_filetype t where t.filetypeid in ('734f44278c21455aba920ab4d3452a95','c333a7a057d142308a42f38e8ef74099');

--流程 借阅本单位档案
insert into FLOW_WORKFLOW (WORKFLOWID,WORKFLOWNAME,CREATETIME,FLOW_ADMIN,DEPT_ID,FLOWTYPE_ID,Z_WHO,Z_STATUS,Z_WHEN,WORKFLOW_EXPAND,ISNOTIFY,LIMITED,NOTIFYTYPE,ISMODIFIED,WAITDOCOMBINE,DESCRIPTION,OWNERS,VEISION,STARTUPTIME,SORTID,WAITDOURL,HANDDONEURL,FORM,DATAFROM,SYS_ID,ORGID,UPDATE_TIME,SUBFILETYPE) values('734f44278c21455aba920ab4d3452a95','借阅本单位档案','2018-11-20 09:16:23','1','','734f44278c21455aba920ab4d3452a95','','1','','734f44278c21455aba920ab4d3452a95','0','','','','','','','1','2018-11-20','b00f274b44384ce2877c76a2b71870b2','/modules/dagl/daly/borrow/apply/borrowApplyEditForm.html','/modules/dagl/daly/borrow/apply/borrowApplyReadOnlyForm.html','','1','97206','18043','2018-11-20 09:16:23','');
insert into FLOW_FILETYPE (FILETYPEID,FILETYPENAME,Z_STATUS,FILEADMIN,FLOWSORT,Z_WHO,SENDCLASS,FLOWOP,DEPTID,FLOWURL,FLOWURL2,FLOWORDER,LISTNAME,PAGENAME,FLOWSORT2,ADMIN_USER,SYS_ID,ORGID,UPDATE_TIME) values ('734f44278c21455aba920ab4d3452a95','借阅本单位档案','1','1','b00f274b44384ce2877c76a2b71870b2','','','','','','','','','档案借阅','','1','97206','18043','2019-03-18 13:44:55');
delete from flow_flowtype where WORKFLOWID ='734f44278c21455aba920ab4d3452a95';
delete from FLOW_FLOWTYPE where FILETYPEID='734f44278c21455aba920ab4d3452a95';
insert into FLOW_FLOWTYPE (FILETYPEID,WORKFLOWID,UPDATE_TIME) values ('734f44278c21455aba920ab4d3452a95','734f44278c21455aba920ab4d3452a95','');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('1542711815643','734f44278c21455aba920ab4d3452a95','借阅人','1','0','','','1','100630,100985,100986,100631','','1','0','','','1','','','0','','借阅人申请借阅信息','ghgl14,ghgl34,ghgl35,ghgl37,ghgl76','321.0','275.0','','','|dagl_borrow|id|','','','1542704381277','','','','1','','1','0','','0','1','1','','','','','','','','null|','0');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('1542703756993','734f44278c21455aba920ab4d3452a95','借阅单位处室领导','3','0','','','1','100644,100629','','0','0','','','1','','','0','','借阅单位处室领导审批','ghgl06,ghgl07,ghgl14,ghgl37,ghgl76','618.0','261.0','','','|dagl_borrow|id|','','','1542765117894|1542766219803','','','','1','','1','0','','0','1','1','','','','','','','','null|','0');
insert into FLOW_SUBIDEA (ID,NAME,SEQNUMBER,NODEID,ISVISIBLE,COVERMODE,FILLMODE,SPAREFIELD,ISSHOW,ISHQ,UPDATE_TIME,TYPEID,WORKFLOWID) values ('a8f0e29442a24e0cb7cc7f255109dc7b','chushi_leader_idea','0','1542703756993','1','1','1','','1','','','4adc9405d80b432e9dd95e6b4b494d93','734f44278c21455aba920ab4d3452a95');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('1542704381277','734f44278c21455aba920ab4d3452a95','1542711815643','借阅单位处室领导','1542703756993','','11','1',0,'3','0','4','','','','446.6666666666667','532.3333333333334','299.5','299.5','','18062521173280841921689040005','299.5','299.5','straightLine','361','618','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('1542766219803','734f44278c21455aba920ab4d3452a95','1542703756993','借阅人','1542711815643','','8','',1,'','0','4','','','','532.3333333333334','446.66666666666663','292.5','292.5','','','292.5','292.5','straightLine','618','361','','','','','');
insert into FLOW_DEFINE_RELATION (ID,BUSINESS_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('bc270a6b4961418dab268921ba9b490a','a82f795aae8a4718940174222e56b202','','1','','734f44278c21455aba920ab4d3452a95');
insert into FLOW_SUBIDEA_RELATION (ID,IDEA_TYPE_ID,NOTE,STATE,TYPE_ID,TYPE_ORDER,NAME,UPDATE_TIME) values ('d296bb7e039a4495bba8609c72ace149','ac15d5ee25624b28b5f15871cb769eed','局档案馆意见','1','734f44278c21455aba920ab4d3452a95','31','dagl_jdabm_idea','2019-03-18 13:44:55');
insert into FLOW_SUBIDEA_RELATION (ID,IDEA_TYPE_ID,NOTE,STATE,TYPE_ID,TYPE_ORDER,NAME,UPDATE_TIME) values ('915a21f4bde84f4c88eb0c0518db6cb1','3dafd03f181541569b7821204be6da7f','立卷单位领导意见','1','734f44278c21455aba920ab4d3452a95','32','dagl_file_unit_leader_idea','2019-03-18 13:44:55');
insert into FLOW_SUBIDEA_RELATION (ID,IDEA_TYPE_ID,NOTE,STATE,TYPE_ID,TYPE_ORDER,NAME,UPDATE_TIME) values ('122dfa1d1d7f403cb52373e072d55898','4adc9405d80b432e9dd95e6b4b494d93','处室领导意见','1','734f44278c21455aba920ab4d3452a95','05','chushi_leader_idea','2019-03-18 13:44:55');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('f6d4951edb5f464d9331c862f9168a5d','ghgl07','','1','','734f44278c21455aba920ab4d3452a95');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('bee46b087d4241e09dd5ebc38de02c63','ghgl37','','1','','734f44278c21455aba920ab4d3452a95');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('b0f8ebec1bc64828a2833357e0dedb64','ghgl06','','1','','734f44278c21455aba920ab4d3452a95');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('70ad1bca97e8408282e5a62d69c6c362','ghgl35','','1','','734f44278c21455aba920ab4d3452a95');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('61d36c71c28b43bd8ca0e8e86cba7b2c','ghgl34','','1','','734f44278c21455aba920ab4d3452a95');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('b21656f86a4444c89d90e1d7f3de4c52','ghgl14','','1','','734f44278c21455aba920ab4d3452a95');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('28a13f606945454cbd06047151baf3e1','ghgl76','','1','','734f44278c21455aba920ab4d3452a95');
--借阅非本单位档案
insert into FLOW_WORKFLOW (WORKFLOWID,WORKFLOWNAME,CREATETIME,FLOW_ADMIN,DEPT_ID,FLOWTYPE_ID,Z_WHO,Z_STATUS,Z_WHEN,WORKFLOW_EXPAND,ISNOTIFY,LIMITED,NOTIFYTYPE,ISMODIFIED,WAITDOCOMBINE,DESCRIPTION,OWNERS,VEISION,STARTUPTIME,SORTID,WAITDOURL,HANDDONEURL,FORM,DATAFROM,SYS_ID,ORGID,UPDATE_TIME,SUBFILETYPE) values('c333a7a057d142308a42f38e8ef74099','借阅非本单位档案','2018-11-13 15:50:52','1','','c333a7a057d142308a42f38e8ef74099','','1','','c333a7a057d142308a42f38e8ef74099','0','','','','','','','1','2018-11-13','b00f274b44384ce2877c76a2b71870b2','/modules/dagl/daly/borrow/apply/borrowApplyEditForm.html','/modules/dagl/daly/borrow/apply/borrowApplyReadOnlyForm.html','','1','97206','18043','2018-11-13 15:50:52','');
insert into FLOW_FILETYPE (FILETYPEID,FILETYPENAME,Z_STATUS,FILEADMIN,FLOWSORT,Z_WHO,SENDCLASS,FLOWOP,DEPTID,FLOWURL,FLOWURL2,FLOWORDER,LISTNAME,PAGENAME,FLOWSORT2,ADMIN_USER,SYS_ID,ORGID,UPDATE_TIME) values ('c333a7a057d142308a42f38e8ef74099','借阅非本单位档案','1','1','b00f274b44384ce2877c76a2b71870b2','','','','','','','','','档案借阅','','1','97206','18043','2019-03-18 13:45:23');
delete from flow_flowtype where WORKFLOWID ='c333a7a057d142308a42f38e8ef74099';
delete from FLOW_FLOWTYPE where FILETYPEID='c333a7a057d142308a42f38e8ef74099';
insert into FLOW_FLOWTYPE (FILETYPEID,WORKFLOWID,UPDATE_TIME) values ('c333a7a057d142308a42f38e8ef74099','c333a7a057d142308a42f38e8ef74099','');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('1542168786645','c333a7a057d142308a42f38e8ef74099','借阅人','1','0','','','1','100630,100631,100985,100986','','1','0','','','1','','','0','','借阅人编辑档案信息','ghgl14,ghgl34,ghgl35,ghgl37,ghgl76','351.0','290.0','','','|dagl_borrow|id|','','','1542183318514','','','','1','','1','0','','0','1','1','','','','','','','','||','0');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('1542128965225','c333a7a057d142308a42f38e8ef74099','借阅单位处室领导','3','0','','','1','100629,100644','','0','0','','','1','','','0','','借阅单位处室领导审批','ghgl14,ghgl37,ghgl76','563.0','279.0','','','|dagl_borrow|id|','','','1551277429603|1542166784104','','','','1','','1','0','','0','1','1','','','','','','','','||','0');
insert into FLOW_SUBIDEA (ID,NAME,SEQNUMBER,NODEID,ISVISIBLE,COVERMODE,FILLMODE,SPAREFIELD,ISSHOW,ISHQ,UPDATE_TIME,TYPEID,WORKFLOWID) values ('eaf47d584c58438a8bf0aec4bd9df139','chushi_leader_idea','0','1542128965225','1','1','1','','1','','','4adc9405d80b432e9dd95e6b4b494d93','c333a7a057d142308a42f38e8ef74099');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('1551313007601','c333a7a057d142308a42f38e8ef74099','立卷单位处室领导','3','0','','','1','100629,100644','','0','0','','','1','','','0','','立卷单位处室领导审批','ghgl06,ghgl07,ghgl14,ghgl37,ghgl76','785.0','277.0','','','|dagl_borrow|id|','','','1551296614797','','','','1','','1','0','','0','1','1','','','','','','','','null|','0');
insert into FLOW_SUBIDEA (ID,NAME,SEQNUMBER,NODEID,ISVISIBLE,COVERMODE,FILLMODE,SPAREFIELD,ISSHOW,ISHQ,UPDATE_TIME,TYPEID,WORKFLOWID) values ('a8d983904682416f8b332f5c329467cf','dagl_file_unit_leader_idea','0','1551313007601','1','1','1','','1','','','3dafd03f181541569b7821204be6da7f','c333a7a057d142308a42f38e8ef74099');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('1542183318514','c333a7a057d142308a42f38e8ef74099','1542168786645','借阅单位处室领导','1542128965225','','11','1',0,'3','0','4','','','','448.3333333333333','505.6666666666667','317.5','317.5','','18062521173280841921689040005','317.5','317.5','straightLine','391','563','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('1542166784104','c333a7a057d142308a42f38e8ef74099','1542128965225','借阅人','1542168786645','','8','',0,'','0','4','','','','505.6666666666667','448.3333333333333','310.5','310.5','','','310.5','310.5','straightLine','563','391','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('1551277429603','c333a7a057d142308a42f38e8ef74099','1542128965225','立卷单位处室领导','1551313007601','','5','1',0,'3','0','4','','','','707','746','315.5','315.5','','18062521173280841921689040007','315.5','315.5','straightLine','668','785','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('1551296614797','c333a7a057d142308a42f38e8ef74099','1551313007601','借阅单位处室领导','1542128965225','DOMAN','5','1',0,'3','0','4','','','','746','707','308.5','308.5','','','308.5','308.5','straightLine','785','668','','','','','');
insert into FLOW_DEFINE_RELATION (ID,BUSINESS_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('0b30954e78d94dd781f69ec9958b95b1','a82f795aae8a4718940174222e56b202','','1','','c333a7a057d142308a42f38e8ef74099');
insert into FLOW_SUBIDEA_RELATION (ID,IDEA_TYPE_ID,NOTE,STATE,TYPE_ID,TYPE_ORDER,NAME,UPDATE_TIME) values ('0529f0a62ed349af830ba15145c6d34e','3dafd03f181541569b7821204be6da7f','立卷单位领导意见','1','c333a7a057d142308a42f38e8ef74099','32','dagl_file_unit_leader_idea','2019-03-18 13:45:23');
insert into FLOW_SUBIDEA_RELATION (ID,IDEA_TYPE_ID,NOTE,STATE,TYPE_ID,TYPE_ORDER,NAME,UPDATE_TIME) values ('82fb1508108e41c5b807150188d47b6e','ac15d5ee25624b28b5f15871cb769eed','局档案馆意见','1','c333a7a057d142308a42f38e8ef74099','31','dagl_jdabm_idea','2019-03-18 13:45:23');
insert into FLOW_SUBIDEA_RELATION (ID,IDEA_TYPE_ID,NOTE,STATE,TYPE_ID,TYPE_ORDER,NAME,UPDATE_TIME) values ('fa45eae4193c45a48661adb8225bdca9','4adc9405d80b432e9dd95e6b4b494d93','处室领导意见','1','c333a7a057d142308a42f38e8ef74099','05','chushi_leader_idea','2019-03-18 13:45:23');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('da22cdebc4914c44bab18ed54d0dce93','ghgl76','','1','','c333a7a057d142308a42f38e8ef74099');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('0653b83695e840e6a1ea5ab0b3f67076','ghgl06','','1','','c333a7a057d142308a42f38e8ef74099');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('f9dac519ecc34aca89fb4526dd816e75','ghgl35','','1','','c333a7a057d142308a42f38e8ef74099');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('5164371211f64a189b5ba56740a61172','ghgl37','','1','','c333a7a057d142308a42f38e8ef74099');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('55ca1d003f2d46b7afcca3819a49a7de','ghgl07','','1','','c333a7a057d142308a42f38e8ef74099');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('d60eb036b91d4c9a989c67b0074262b2','ghgl34','','1','','c333a7a057d142308a42f38e8ef74099');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('1a7b8e7ac8cf4947a18d5d3dac476c39','ghgl14','','1','','c333a7a057d142308a42f38e8ef74099');

