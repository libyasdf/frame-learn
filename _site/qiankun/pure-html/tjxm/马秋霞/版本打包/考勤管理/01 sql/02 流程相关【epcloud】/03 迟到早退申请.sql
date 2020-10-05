insert into FLOW_WORKFLOW (WORKFLOWID,WORKFLOWNAME,CREATETIME,FLOW_ADMIN,DEPT_ID,FLOWTYPE_ID,Z_WHO,Z_STATUS,Z_WHEN,WORKFLOW_EXPAND,ISNOTIFY,LIMITED,NOTIFYTYPE,ISMODIFIED,WAITDOCOMBINE,DESCRIPTION,OWNERS,VEISION,STARTUPTIME,SORTID,WAITDOURL,HANDDONEURL,FORM,DATAFROM,SYS_ID,ORGID,UPDATE_TIME,SUBFILETYPE) values('fd8130ce3f604957b3eda7115e104384','迟到早退申请','2018-08-21 14:27:25','1','','fd8130ce3f604957b3eda7115e104384','','1','','fd8130ce3f604957b3eda7115e104384','0','','','','','','','1','2018-06-26','e1f222bb6de246f1a30f661796560974','/modules/zhbg/kqgl/comeLateLeaveEarly/comeLateLeaveEarlyForm.html','/modules/zhbg/kqgl/comeLateLeaveEarly/comeLateLeaveEarlyReadOnlyForm.html','','','97206','18043','2018-08-21 14:27:25','');
insert into FLOW_FILETYPE (FILETYPEID,FILETYPENAME,Z_STATUS,FILEADMIN,FLOWSORT,Z_WHO,SENDCLASS,FLOWOP,DEPTID,FLOWURL,FLOWURL2,FLOWORDER,LISTNAME,PAGENAME,FLOWSORT2,ADMIN_USER,SYS_ID,ORGID,UPDATE_TIME) values ('fd8130ce3f604957b3eda7115e104384','迟到早退申请','1','','e1f222bb6de246f1a30f661796560974','','','','','','','','','考勤管理','','','97206','18043','2018-09-13 09:41:28');
delete from flow_flowtype where WORKFLOWID ='fd8130ce3f604957b3eda7115e104384';
delete from FLOW_FLOWTYPE where FILETYPEID='fd8130ce3f604957b3eda7115e104384';
insert into FLOW_FLOWTYPE (FILETYPEID,WORKFLOWID,UPDATE_TIME) values ('fd8130ce3f604957b3eda7115e104384','fd8130ce3f604957b3eda7115e104384','');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('2e9ef9009e3843c0b578aceb935b7644','fd8130ce3f604957b3eda7115e104384','副科长','1','0','','','1','100986','','1','1','','','1','','','0','','','ghgl14,ghgl37','216.67999267578125','137.050048828125','','','|KQGL_OVER_TIME_INFO|id|','title','','bf306a0a61c1477da00af6f91975f624|4a89352f380a40df9d6c381353faee02','','','','0','','','0','','0','','0','','','','','1806261422598080192168901340016|1','','','null|','0');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('10e1c051cc18465db65a86ed61c55d24','fd8130ce3f604957b3eda7115e104384','正科长','3','0','','','1','100985','','1','0','','','1','','','0','','','ghgl06,ghgl07,ghgl14,ghgl37','394.28997802734375','118.6099853515625','','','|KQGL_COMELATE_LEAVEEARLY_INFO|ID|','cdztUserName','','9b859530761f499ab4f58340d9ece180|1535268411323','','','','0','','','0','','0','','0','','','','','1806261422598080192168901340016|1','','','null|','0');
insert into FLOW_SUBIDEA (ID,NAME,SEQNUMBER,NODEID,ISVISIBLE,COVERMODE,FILLMODE,SPAREFIELD,ISSHOW,ISHQ,UPDATE_TIME,TYPEID,WORKFLOWID) values ('d12fd117622046e1a07e4db7c2e4b097','keshi_leader_idea','0','10e1c051cc18465db65a86ed61c55d24','1','1','1','','1','','','aec813cfa14a4d12973b363753280540','fd8130ce3f604957b3eda7115e104384');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('baed11d6cff64db994392cf48de932d3','fd8130ce3f604957b3eda7115e104384','处领导','3','0','','','1','100629,100644','','0','0','','','1','','','0','','','ghgl06,ghgl07,ghgl14,ghgl37','394.10003662109375','234.6000518798828','','','|KQGL_COMELATE_LEAVEEARLY_INFO|ID|','cdztUserName','','bed59c3e0a454cca93175b796a629634|e6f24b960b1b40cdb1dc7b1307313af5|c4ca56effe6c4dcd94e0484778af0be9|1535244068266|1535262089360','','','','0','','','0','','0','','0','','','','','1806261422598080192168901340016|1','','','null|','0');
insert into FLOW_SUBIDEA (ID,NAME,SEQNUMBER,NODEID,ISVISIBLE,COVERMODE,FILLMODE,SPAREFIELD,ISSHOW,ISHQ,UPDATE_TIME,TYPEID,WORKFLOWID) values ('3158ed51b381459a8f7ded40f18d0e7c','chushi_leader_idea','0','baed11d6cff64db994392cf48de932d3','1','1','1','','1','','','4adc9405d80b432e9dd95e6b4b494d93','fd8130ce3f604957b3eda7115e104384');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('cffe75f704c641348cdbcd4c3088d318','fd8130ce3f604957b3eda7115e104384','一般涉密人员（科）','1','0','','','1','100631','','1','1','','','1','','','0','','','ghgl14,ghgl37','215.75999450683594','52.22998046875','','','|KQGL_OVER_TIME_INFO|id|','title','','b5ed65169f2f44d39273182e98df37d5','','','','0','','','0','','0','','0','','','','','','','','null|','0');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('216a2c52065340bab2d6488cfcaee2d2','fd8130ce3f604957b3eda7115e104384','科领导','3','0','','','1','100985,100986','','0','1','','','1','','','0','','','ghgl06,ghgl07,ghgl14,ghgl37','393.7099914550781','38.20000457763672','','','|KQGL_COMELATE_LEAVEEARLY_INFO|ID|','cdztUserName','','a4aca5eae03e430c8c6d4694d1f89bcc','','','','0','','','0','','0','','0','','','','','1806261422438080192168901340015|1','','','null|','0');
insert into FLOW_SUBIDEA (ID,NAME,SEQNUMBER,NODEID,ISVISIBLE,COVERMODE,FILLMODE,SPAREFIELD,ISSHOW,ISHQ,UPDATE_TIME,TYPEID,WORKFLOWID) values ('c2433d1070df4f1f840da44d877283b4','keshi_leader_idea','0','216a2c52065340bab2d6488cfcaee2d2','1','1','1','','1','','','aec813cfa14a4d12973b363753280540','fd8130ce3f604957b3eda7115e104384');
insert into FLOW_WFLEVE (WFLEVEID,WORKFLOWID,WFLEVENAME,WFLEVE_TYPE,WFLEVE_VALUE,SUBFILETYPE,DEPTID,DEPT_LEVEL,ROLEID,DB_SOURCE,IS_START,ISEDIT,IDEAFIELD,SIGNFIELD,ISWRITE,ACCRCOLM,WFLEVECOND,ISMUCHECK,SENDMETHOD,MEMO,BUTTONS,VBNODEX,VBNODEY,TARGETPEOPLE,TARGETDEPT,TABLE_ID,SUBFLAG,FLOW_IDLIST,LINESQUENCE,PREHANDLE,AFTERHANDLE,VARUSER,ISNOTIFY,LIMITED,OVERNOTIFYTYPE,OVERMODIFIED,WAITDOCOMBINE,ISSENDWAITNOTIFY,WAITNOTIFYTYPE,WAITMODIFIED,APPLICATION,AUTONODETYPE,INVOKECONTENT,FORMURL,NODEPURIEWROLEID,WFLEVEID_EXPAND,FLOWCOURSE,CANDO,Z_WHO) values ('758074e7dacc463e936b7828b1ac8856','fd8130ce3f604957b3eda7115e104384','一般涉密人员（处）','1','0','','','1','100630','','1','0','','','1','','','0','','','','608.2799682617188','249.5600128173828','','','','','','79651568ef3740498ce5a4ae26417fe0','','','','0','','','0','','0','','0','','','','','','','','null|','0');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('b5ed65169f2f44d39273182e98df37d5','fd8130ce3f604957b3eda7115e104384','cffe75f704c641348cdbcd4c3088d318','科领导审批','216a2c52065340bab2d6488cfcaee2d2','','7','',0,'3','0','4','','','','301.7433268229167','347.7266591389974','76.70000457763672','76.70000457763672','','1806251720578080192168901340002','76.70000457763672','76.70000457763672','straightLine','255.75999450683594','393.7099914550781','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('bf306a0a61c1477da00af6f91975f624','fd8130ce3f604957b3eda7115e104384','2e9ef9009e3843c0b578aceb935b7644','正科长审批','10e1c051cc18465db65a86ed61c55d24','','5','1',0,'3','0','4','','','','302.54998779296875','348.41998291015625','160.550048828125','160.550048828125','','1806251720578080192168901340002','160.550048828125','160.550048828125','straightLine','256.67999267578125','394.28997802734375','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('9b859530761f499ab4f58340d9ece180','fd8130ce3f604957b3eda7115e104384','10e1c051cc18465db65a86ed61c55d24','返回申请人','2e9ef9009e3843c0b578aceb935b7644','NEEDREMOVE,DOMAN','8','',0,'','0','4','','','','348.41998291015625','302.54998779296875','153.550048828125','153.550048828125','','','153.550048828125','153.550048828125','straightLine','394.28997802734375','256.67999267578125','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('79651568ef3740498ce5a4ae26417fe0','fd8130ce3f604957b3eda7115e104384','758074e7dacc463e936b7828b1ac8856','正副处长审批','baed11d6cff64db994392cf48de932d3','','7','',0,'3','0','4','','','','571.8866577148438','535.4933471679688','266.0600128173828','266.0600128173828','','','266.0600128173828','266.0600128173828','straightLine','608.2799682617188','499.10003662109375','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('e6f24b960b1b40cdb1dc7b1307313af5','fd8130ce3f604957b3eda7115e104384','baed11d6cff64db994392cf48de932d3','返回给申请人','758074e7dacc463e936b7828b1ac8856','NEEDREMOVE','8','',0,'','0','4','','','','535.4933471679688','571.8866577148438','273.0600128173828','273.0600128173828','','','273.0600128173828','273.0600128173828','straightLine','499.10003662109375','608.2799682617188','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('a4aca5eae03e430c8c6d4694d1f89bcc','fd8130ce3f604957b3eda7115e104384','216a2c52065340bab2d6488cfcaee2d2','返回申请人','cffe75f704c641348cdbcd4c3088d318','','8','',0,'','0','4','','','','347.7266591389974','301.7433268229167','69.70000457763672','69.70000457763672','','','69.70000457763672','69.70000457763672','straightLine','393.7099914550781','255.75999450683594','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('bed59c3e0a454cca93175b796a629634','fd8130ce3f604957b3eda7115e104384','baed11d6cff64db994392cf48de932d3','返回给申请人','2e9ef9009e3843c0b578aceb935b7644','ISFIRSTWFLEVE,NEEDREMOVE,DOMAN','8','',1,'','0','4','','','','348.29335530598956','302.48667399088544','232.08338419596353','201.56671651204428','','','262.6000518798828','171.050048828125','straightLine','394.10003662109375','256.67999267578125','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('4a89352f380a40df9d6c381353faee02','fd8130ce3f604957b3eda7115e104384','2e9ef9009e3843c0b578aceb935b7644','正副处长审批','baed11d6cff64db994392cf48de932d3','','1','1',1,'3','0','4','','','','302.48667399088544','348.29335530598956','194.56671651204428','225.08338419596353','','','164.050048828125','255.6000518798828','straightLine','256.67999267578125','394.10003662109375','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('1535268411323','fd8130ce3f604957b3eda7115e104384','10e1c051cc18465db65a86ed61c55d24','上报处领导','baed11d6cff64db994392cf48de932d3','','1','1',1,'3','0','4','','','','450.22666422526044','450.16335042317706','203.94000752766928','219.27002970377603','zkzCre=1','','188.6099853515625','234.6000518798828','straightLine','450.28997802734375','450.10003662109375','','','','','');
insert into FLOW_WFOPERATE (WFOPERATEID,WORKFLOWID,WFLEVEID,WFOPERATENAME,NEXTWFLEVEID,CONTROLCOND,BOUND,LEVELBOUND,LEVELNUM,TARGET,ENDSIGN,ISMUCHECK,SENDMETHOD,FLAG,MEMO,VBLINEX1,VBLINEX2,VBLINEY1,VBLINEY2,EXPRESSION,DEPTLEVEL,VBNODESTARTY,VBNODEENDY,LINETYPE,VBNODESTART,VBNODEEND,VBLINESTRAIGHT,VBLINEID,Z_WHO,Z_STATUS,Z_WHEN) values('1535244068266','fd8130ce3f604957b3eda7115e104384','baed11d6cff64db994392cf48de932d3','返回发送人','10e1c051cc18465db65a86ed61c55d24','ISFIRSTWFLEVE,NEEDREMOVE,DOMAN','3','1',1,'3','0','4','','','','443.16335042317706','443.22666422526044','219.27002970377603','203.94000752766928','','','234.6000518798828','188.6099853515625','straightLine','443.10003662109375','443.28997802734375','','','','','');
insert into FLOW_PROCESSVARIABLE (VARIABLEID,WORKFLOWID,VARNAME,VARDESC,DATATYPE,VARSOURCE,DEFAULTVALUE,TABLENAME,FIELDNAME,UPDATE_TIME,KEYFIELDNAME) values ('709df83d40af4fd2920d25b55615261d','fd8130ce3f604957b3eda7115e104384','zkzCre','','String','request','0','','','','');
insert into FLOW_SUBIDEA_RELATION (ID,IDEA_TYPE_ID,NOTE,STATE,TYPE_ID,TYPE_ORDER,NAME,UPDATE_TIME) values ('5f81700fb10f4786aa1e9a81885d786d','0f2a45ff212c46d4b020405ecf40842e','部门领导意见','1','fd8130ce3f604957b3eda7115e104384','09','ejj_leader_idea','2018-09-13 09:41:28');
insert into FLOW_SUBIDEA_RELATION (ID,IDEA_TYPE_ID,NOTE,STATE,TYPE_ID,TYPE_ORDER,NAME,UPDATE_TIME) values ('773ae79ac93f4a3ea55a8767284ca6a4','4adc9405d80b432e9dd95e6b4b494d93','处室领导意见','1','fd8130ce3f604957b3eda7115e104384','05','chushi_leader_idea','2018-09-13 09:41:28');
insert into FLOW_SUBIDEA_RELATION (ID,IDEA_TYPE_ID,NOTE,STATE,TYPE_ID,TYPE_ORDER,NAME,UPDATE_TIME) values ('ef7321b00dcb4407996082eacd457acf','aec813cfa14a4d12973b363753280540','科室领导意见','1','fd8130ce3f604957b3eda7115e104384','14','keshi_leader_idea','2018-09-13 09:41:28');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('7ae7617ec2b640009dde0795a7414dd4','ghgl14','','1','','fd8130ce3f604957b3eda7115e104384');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('6cf3500e61404531bbf13db5b66b63d4','ghgl06','','1','','fd8130ce3f604957b3eda7115e104384');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('68f6144dfd3d405e956413334bc4c52a','ghgl07','','1','','fd8130ce3f604957b3eda7115e104384');
insert into FLOW_BUTTON_RELATION (ID,BUTTON_ID,NOTE,STATE,UPDATE_TIME,TYPE_ID) values ('727dc99696a14564ae68156aa25be918','ghgl37','','1','','fd8130ce3f604957b3eda7115e104384');
