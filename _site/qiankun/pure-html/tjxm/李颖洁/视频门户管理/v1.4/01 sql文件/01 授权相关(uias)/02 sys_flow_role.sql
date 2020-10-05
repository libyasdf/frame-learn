update sys_flow_role set rolename='一级视频栏目管理员（处）' where ROLEID='4028da0667743e33016778afb667011c';
update sys_flow_role set rolename='一级视频栏目管理员（科）' where ROLEID='4028da0667743e33016778b0afe30121';
insert into sys_flow_role (ROLEID, DPLV_ID, DEPTID, ROLENAME, ORDER_NO, ISVISIBLE, NOTE, AUTHER, STATUS, TIME, SYS_DEF, SUB_ID, ROLE_TYPE, ROLE_NO)
values ('4028da0668a305e60168e4d9aae00a53', '93248', '441', '二级视频栏目管理员（处）', 0, '1', '1.负责视频门户的栏目维护', '1', '1', '2019-02-18 10:16:49', '0', '97206', null, 'C106');

insert into sys_flow_role (ROLEID, DPLV_ID, DEPTID, ROLENAME, ORDER_NO, ISVISIBLE, NOTE, AUTHER, STATUS, TIME, SYS_DEF, SUB_ID, ROLE_TYPE, ROLE_NO)
values ('4028da0668a305e60168e4da6aa40a58', '93248', '441', '视频发布审核人（处）', 0, '1', '1.负责视频内容的审核', '1', '1', '2019-02-13 11:15:45', '0', '97206', null, 'C107');

insert into sys_flow_role (ROLEID, DPLV_ID, DEPTID, ROLENAME, ORDER_NO, ISVISIBLE, NOTE, AUTHER, STATUS, TIME, SYS_DEF, SUB_ID, ROLE_TYPE, ROLE_NO)
values ('4028da0668a305e60168e4db77d60a63', '93258', '441', '视频发布审核人（科）', 0, '1', '1.负责视频内容的审核', '1', '1', '2019-02-13 11:16:54', '0', '97206', null, 'D109');

insert into sys_flow_role (ROLEID, DPLV_ID, DEPTID, ROLENAME, ORDER_NO, ISVISIBLE, NOTE, AUTHER, STATUS, TIME, SYS_DEF, SUB_ID, ROLE_TYPE, ROLE_NO)
values ('4028da0668a305e60168e4dafdde0a5e', '93258', '441', '二级视频栏目管理员（科）', 0, '1', '1.负责视频门户的栏目维护', '1', '1', '2019-02-18 10:03:09', '0', '97206', null, 'D108');

