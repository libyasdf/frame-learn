--会务服务增加负责人父部门id字段
alter table HYGL_MEETING_SERVICE add RESPONSIBLE_DEPT_ID varchar2(50 char);
comment on column HYGL_MEETING_SERVICE.RESPONSIBLE_DEPT_ID is '负责人父部门id';