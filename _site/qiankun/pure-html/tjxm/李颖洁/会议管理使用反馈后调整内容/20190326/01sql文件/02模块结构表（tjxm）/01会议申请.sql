alter table hygl_meeting_apply modify (APPLY_TITLE VARCHAR2(200 CHAR));
alter table hygl_meeting_apply rename column MEETING_START_DATE to MEETING_TIME;
alter table hygl_meeting_apply drop column meeting_end_date;
alter table hygl_meeting_apply drop column MEETING_START_TIME;
alter table hygl_meeting_apply drop column MEETING_END_TIME;
alter table hygl_meeting_apply drop column meetingroom_id;
alter table hygl_meeting_apply drop column meetingroom_name;

comment on column HYGL_MEETING_APPLY.meeting_time is '会议时间';


-- Create table
-- Create table
create table HYGL_MEETINGROOM_USEINFO
(
  id               VARCHAR2(50 CHAR) not null,
  cre_user_id      VARCHAR2(50 CHAR),
  cre_user_name    VARCHAR2(30 CHAR),
  cre_dept_id      VARCHAR2(50 CHAR),
  cre_dept_name    VARCHAR2(30 CHAR),
  cre_time         VARCHAR2(30 CHAR),
  update_user_name VARCHAR2(30 CHAR),
  update_user_id   VARCHAR2(50 CHAR),
  update_time      VARCHAR2(30 CHAR),
  visible          VARCHAR2(1 CHAR),
  meetingroom_id   VARCHAR2(50 CHAR),
  apply_id         VARCHAR2(50 CHAR),
  use_date         VARCHAR2(50 CHAR),
  use_time         VARCHAR2(50 CHAR),
  week_date        VARCHAR2(50 CHAR),
  cre_ju_name      VARCHAR2(50 CHAR),
  cre_ju_id        VARCHAR2(50 CHAR),
  cre_chushi_id    VARCHAR2(50 CHAR),
  cre_chushi_name  VARCHAR2(50 CHAR)
)
tablespace ZHGL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table HYGL_MEETINGROOM_USEINFO
  is '会议管理模块：会议室使用情况表
hygl：会议管理表名前缀';
-- Add comments to the columns 
comment on column HYGL_MEETINGROOM_USEINFO.id
  is 'id';
comment on column HYGL_MEETINGROOM_USEINFO.cre_user_id
  is '创建人id';
comment on column HYGL_MEETINGROOM_USEINFO.cre_user_name
  is '创建人name';
comment on column HYGL_MEETINGROOM_USEINFO.cre_dept_id
  is '创建人部门id';
comment on column HYGL_MEETINGROOM_USEINFO.cre_dept_name
  is '创建人部门name';
comment on column HYGL_MEETINGROOM_USEINFO.cre_time
  is '创建时间';
comment on column HYGL_MEETINGROOM_USEINFO.update_user_name
  is '最后更新人';
comment on column HYGL_MEETINGROOM_USEINFO.update_user_id
  is '最后更新人id';
comment on column HYGL_MEETINGROOM_USEINFO.update_time
  is '最后更新时间';
comment on column HYGL_MEETINGROOM_USEINFO.visible
  is '逻辑删除';
comment on column HYGL_MEETINGROOM_USEINFO.meetingroom_id
  is '会议室id';
comment on column HYGL_MEETINGROOM_USEINFO.apply_id
  is '会议室申请id';
comment on column HYGL_MEETINGROOM_USEINFO.use_date
  is '占用日期';
comment on column HYGL_MEETINGROOM_USEINFO.use_time
  is '占用时间';
comment on column HYGL_MEETINGROOM_USEINFO.week_date
  is '占用日期属于周几';
comment on column HYGL_MEETINGROOM_USEINFO.cre_ju_name
  is '创建人二级局名';
comment on column HYGL_MEETINGROOM_USEINFO.cre_ju_id
  is '创建人二级局ID';
comment on column HYGL_MEETINGROOM_USEINFO.cre_chushi_id
  is '创建人处室ID';
comment on column HYGL_MEETINGROOM_USEINFO.cre_chushi_name
  is '创建人处室名';