-- Create table
create table KQGL_GO_OUT_INFO
(
  id                  VARCHAR2(50 CHAR) not null,
  cre_user_id         VARCHAR2(50 CHAR),
  cre_user_name       VARCHAR2(50 CHAR),
  cre_dept_id         VARCHAR2(50 CHAR),
  cre_dept_name       VARCHAR2(50 CHAR),
  cre_chushi_id       VARCHAR2(50 CHAR),
  cre_chushi_name     VARCHAR2(50 CHAR),
  cre_ju_id           VARCHAR2(50 CHAR),
  cre_ju_name         VARCHAR2(50 CHAR),
  visible             VARCHAR2(1 CHAR),
  cre_time            VARCHAR2(30 CHAR),
  subflag             VARCHAR2(2 CHAR),
  update_user_id      VARCHAR2(50 CHAR),
  update_user_name    VARCHAR2(50 CHAR),
  update_time         VARCHAR2(30 CHAR),
  go_out_title        VARCHAR2(100 CHAR),
  applicant_unit_id   VARCHAR2(50 CHAR),
  applicant_unit_name VARCHAR2(50 CHAR),
  application_time    VARCHAR2(30 CHAR),
  destination         VARCHAR2(30 CHAR),
  go_out_long_time    VARCHAR2(10 CHAR),
  go_out_reason       VARCHAR2(500 CHAR),
  go_out_date         VARCHAR2(30 CHAR),
  start_stop_time     VARCHAR2(30 CHAR)
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
comment on table KQGL_GO_OUT_INFO
  is '考勤管理模块：外出信息表
KQGL:考勤管理模块表名前缀';
-- Add comments to the columns 
comment on column KQGL_GO_OUT_INFO.id
  is '主键';
comment on column KQGL_GO_OUT_INFO.cre_user_id
  is '创建人ID（也是申请人ID字段）';
comment on column KQGL_GO_OUT_INFO.cre_user_name
  is '创建人名称（也是申请人名称字段）';
comment on column KQGL_GO_OUT_INFO.cre_dept_id
  is '创建人部门ID';
comment on column KQGL_GO_OUT_INFO.cre_dept_name
  is '创建人部门名';
comment on column KQGL_GO_OUT_INFO.cre_chushi_id
  is '创建人处室ID';
comment on column KQGL_GO_OUT_INFO.cre_chushi_name
  is '创建人处室名';
comment on column KQGL_GO_OUT_INFO.cre_ju_id
  is '创建人二级局ID';
comment on column KQGL_GO_OUT_INFO.cre_ju_name
  is '创建人二级局名';
comment on column KQGL_GO_OUT_INFO.visible
  is '逻辑删除';
comment on column KQGL_GO_OUT_INFO.cre_time
  is '创建时间';
comment on column KQGL_GO_OUT_INFO.subflag
  is '流程状态';
comment on column KQGL_GO_OUT_INFO.update_user_id
  is '最后更新人ID';
comment on column KQGL_GO_OUT_INFO.update_user_name
  is '最后更新人名';
comment on column KQGL_GO_OUT_INFO.update_time
  is '最后更新时间';
comment on column KQGL_GO_OUT_INFO.go_out_title
  is '标题';
comment on column KQGL_GO_OUT_INFO.applicant_unit_id
  is '申请人单位ID';
comment on column KQGL_GO_OUT_INFO.applicant_unit_name
  is '申请人单位名称';
comment on column KQGL_GO_OUT_INFO.application_time
  is '申请时间';
comment on column KQGL_GO_OUT_INFO.destination
  is '目的地';
comment on column KQGL_GO_OUT_INFO.go_out_long_time
  is '外出时长';
comment on column KQGL_GO_OUT_INFO.go_out_reason
  is '外出事由';
comment on column KQGL_GO_OUT_INFO.go_out_date
  is '外出日期';
comment on column KQGL_GO_OUT_INFO.start_stop_time
  is '外出起止时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_GO_OUT_INFO
  add constraint PK_KQGL_GO_OUT_INFO primary key (ID)
  using index 
  tablespace ZHGL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create table
create table KQGL_OVER_TIME_INFO
(
  id                   VARCHAR2(50 CHAR) not null,
  cre_user_id          VARCHAR2(50 CHAR),
  cre_user_name        VARCHAR2(50 CHAR),
  cre_dept_id          VARCHAR2(50 CHAR),
  cre_dept_name        VARCHAR2(50 CHAR),
  cre_chushi_id        VARCHAR2(50 CHAR),
  cre_chushi_name      VARCHAR2(50 CHAR),
  cre_ju_id            VARCHAR2(50 CHAR),
  cre_ju_name          VARCHAR2(50 CHAR),
  visible              VARCHAR2(1 CHAR),
  cre_time             VARCHAR2(30 CHAR),
  subflag              VARCHAR2(2 CHAR),
  title                VARCHAR2(200 CHAR),
  update_user_id       VARCHAR2(50 CHAR),
  update_user_name     VARCHAR2(50 CHAR),
  update_time          VARCHAR2(30 CHAR),
  over_time_title      VARCHAR2(100 CHAR),
  applicant_unit_id    VARCHAR2(50 CHAR),
  applicant_unit_name  VARCHAR2(50 CHAR),
  application_time     VARCHAR2(30 CHAR),
  over_time_type       VARCHAR2(50 CHAR),
  over_time_date       VARCHAR2(30 CHAR),
  start_stop_time      VARCHAR2(30 CHAR),
  over_time_long_timeh VARCHAR2(10 CHAR),
  over_time_reason     VARCHAR2(500 CHAR),
  filetype             VARCHAR2(50 CHAR),
  idea                 VARCHAR2(500 CHAR),
  over_time_long_timed VARCHAR2(10 CHAR)
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
comment on table KQGL_OVER_TIME_INFO
  is '考勤管理模块：加班信息表
KQGL:考勤管理模块表名前缀';
-- Add comments to the columns 
comment on column KQGL_OVER_TIME_INFO.id
  is '主键';
comment on column KQGL_OVER_TIME_INFO.cre_user_id
  is '创建人ID（也是申请人ID字段）';
comment on column KQGL_OVER_TIME_INFO.cre_user_name
  is '创建人名称（也是申请人名称字段）';
comment on column KQGL_OVER_TIME_INFO.cre_dept_id
  is '创建人部门ID';
comment on column KQGL_OVER_TIME_INFO.cre_dept_name
  is '创建人部门名';
comment on column KQGL_OVER_TIME_INFO.cre_chushi_id
  is '创建人处室ID';
comment on column KQGL_OVER_TIME_INFO.cre_chushi_name
  is '创建人处室名';
comment on column KQGL_OVER_TIME_INFO.cre_ju_id
  is '创建人二级局ID';
comment on column KQGL_OVER_TIME_INFO.cre_ju_name
  is '创建人二级局名';
comment on column KQGL_OVER_TIME_INFO.visible
  is '逻辑删除';
comment on column KQGL_OVER_TIME_INFO.cre_time
  is '创建时间';
comment on column KQGL_OVER_TIME_INFO.subflag
  is '流程状态';
comment on column KQGL_OVER_TIME_INFO.title
  is '带班标题';
comment on column KQGL_OVER_TIME_INFO.update_user_id
  is '最后更新人ID';
comment on column KQGL_OVER_TIME_INFO.update_user_name
  is '最后更新人名';
comment on column KQGL_OVER_TIME_INFO.update_time
  is '最后更新时间';
comment on column KQGL_OVER_TIME_INFO.over_time_title
  is '标题';
comment on column KQGL_OVER_TIME_INFO.applicant_unit_id
  is '申请人单位ID';
comment on column KQGL_OVER_TIME_INFO.applicant_unit_name
  is '申请人单位名称';
comment on column KQGL_OVER_TIME_INFO.application_time
  is '申请时间';
comment on column KQGL_OVER_TIME_INFO.over_time_type
  is '加班类型';
comment on column KQGL_OVER_TIME_INFO.over_time_date
  is '加班日期';
comment on column KQGL_OVER_TIME_INFO.start_stop_time
  is '加班的起止时间';
comment on column KQGL_OVER_TIME_INFO.over_time_long_timeh
  is '加班时长(工作日加班时长）';
comment on column KQGL_OVER_TIME_INFO.over_time_reason
  is '加班事由';
comment on column KQGL_OVER_TIME_INFO.filetype
  is '流程类型';
comment on column KQGL_OVER_TIME_INFO.idea
  is '意见';
comment on column KQGL_OVER_TIME_INFO.over_time_long_timed
  is '加班时长(节假日加班时长）';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_OVER_TIME_INFO
  add constraint PK_KQGL_OVER_TIME_INFO primary key (ID)
  using index 
  tablespace ZHGL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

-- Create table
create table KQGL_LEAVE_INFO
(
  id                  VARCHAR2(50 CHAR) not null,
  cre_user_id         VARCHAR2(50 CHAR),
  cre_user_name       VARCHAR2(50 CHAR),
  cre_dept_id         VARCHAR2(50 CHAR),
  cre_dept_name       VARCHAR2(50 CHAR),
  cre_chushi_id       VARCHAR2(50 CHAR),
  cre_chushi_name     VARCHAR2(50 CHAR),
  cre_ju_id           VARCHAR2(50 CHAR),
  cre_ju_name         VARCHAR2(50 CHAR),
  visible             VARCHAR2(1 CHAR),
  cre_time            VARCHAR2(30 CHAR),
  subflag             VARCHAR2(2 CHAR),
  title               VARCHAR2(200 CHAR),
  update_user_id      VARCHAR2(50 CHAR),
  update_user_name    VARCHAR2(50 CHAR),
  update_time         VARCHAR2(30 CHAR),
  applicant_unit_id   VARCHAR2(50 CHAR),
  applicant_unit_name VARCHAR2(50 CHAR),
  application_time    VARCHAR2(30 CHAR),
  leave_start_date    VARCHAR2(30 CHAR),
  start_am_or_pm      VARCHAR2(50 CHAR),
  leave_end_date      VARCHAR2(30 CHAR),
  end_am_or_pm        VARCHAR2(50 CHAR),
  leave_type          VARCHAR2(50 CHAR),
  leave_long_time     VARCHAR2(10 CHAR),
  leave_reason        VARCHAR2(500 CHAR),
  leave_title         VARCHAR2(50 CHAR)
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
comment on table KQGL_LEAVE_INFO
  is '考勤管理模块：请假信息表
KQGL:考勤管理模块表名前缀';
-- Add comments to the columns 
comment on column KQGL_LEAVE_INFO.id
  is '主键';
comment on column KQGL_LEAVE_INFO.cre_user_id
  is '创建人ID（也是申请人ID字段）';
comment on column KQGL_LEAVE_INFO.cre_user_name
  is '创建人名称（也是申请人名称字段）';
comment on column KQGL_LEAVE_INFO.cre_dept_id
  is '创建部门ID';
comment on column KQGL_LEAVE_INFO.cre_dept_name
  is '创建部门名';
comment on column KQGL_LEAVE_INFO.cre_chushi_id
  is '创建人处室ID';
comment on column KQGL_LEAVE_INFO.cre_chushi_name
  is '创建人处室名';
comment on column KQGL_LEAVE_INFO.cre_ju_id
  is '创建人二级局ID';
comment on column KQGL_LEAVE_INFO.cre_ju_name
  is '创建人二级局名';
comment on column KQGL_LEAVE_INFO.visible
  is '逻辑删除';
comment on column KQGL_LEAVE_INFO.cre_time
  is '创建时间';
comment on column KQGL_LEAVE_INFO.subflag
  is '流程状态';
comment on column KQGL_LEAVE_INFO.title
  is '待办标题';
comment on column KQGL_LEAVE_INFO.update_user_id
  is '最后更新人ID';
comment on column KQGL_LEAVE_INFO.update_user_name
  is '最后更新人名';
comment on column KQGL_LEAVE_INFO.update_time
  is '最后更新时间';
comment on column KQGL_LEAVE_INFO.applicant_unit_id
  is '申请人单位ID';
comment on column KQGL_LEAVE_INFO.applicant_unit_name
  is '申请人单位名称';
comment on column KQGL_LEAVE_INFO.application_time
  is '申请时间';
comment on column KQGL_LEAVE_INFO.leave_start_date
  is '请假开始日期';
comment on column KQGL_LEAVE_INFO.start_am_or_pm
  is '请假开始上午';
comment on column KQGL_LEAVE_INFO.leave_end_date
  is '请假结束日期';
comment on column KQGL_LEAVE_INFO.end_am_or_pm
  is '请假结束上下午';
comment on column KQGL_LEAVE_INFO.leave_type
  is '请假类型包括：调休、事假、病假、年休假、探亲假、婚假、丧假、哺乳假、产假、陪产假';
comment on column KQGL_LEAVE_INFO.leave_long_time
  is '请假时长';
comment on column KQGL_LEAVE_INFO.leave_reason
  is '请假事由';
comment on column KQGL_LEAVE_INFO.leave_title
  is '请假标题';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_LEAVE_INFO
  add constraint PK_KQGL_LEAVE_INFO primary key (ID)
  using index 
  tablespace ZHGL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

-- Create table
create table KQGL_BUSINESS_TRIP_INFO
(
  id                     VARCHAR2(50 CHAR) not null,
  cre_user_id            VARCHAR2(50 CHAR),
  cre_user_name          VARCHAR2(50 CHAR),
  cre_dept_id            VARCHAR2(50 CHAR),
  cre_dept_name          VARCHAR2(50 CHAR),
  cre_chushi_id          VARCHAR2(50 CHAR),
  cre_chushi_name        VARCHAR2(50 CHAR),
  cre_ju_id              VARCHAR2(50 CHAR),
  cre_ju_name            VARCHAR2(50 CHAR),
  visible                VARCHAR2(1 CHAR),
  cre_time               VARCHAR2(30 CHAR),
  subflag                VARCHAR2(2 CHAR),
  title                  VARCHAR2(200 CHAR),
  update_user_id         VARCHAR2(50 CHAR),
  update_user_name       VARCHAR2(50 CHAR),
  update_time            VARCHAR2(30 CHAR),
  business_trip_title    VARCHAR2(100 CHAR),
  application_unit_id    VARCHAR2(50 CHAR),
  application_unit_name  VARCHAR2(50 CHAR),
  application_time       VARCHAR2(30 CHAR),
  start_time             VARCHAR2(30 CHAR),
  end_time               VARCHAR2(30 CHAR),
  destination            VARCHAR2(30 CHAR),
  vehicle                VARCHAR2(30 CHAR),
  is_have_reception_fees VARCHAR2(1 CHAR),
  reception_fees         VARCHAR2(30 CHAR),
  long_time              VARCHAR2(10 CHAR),
  business_trip_reason   VARCHAR2(500 CHAR),
  filetype               VARCHAR2(50 CHAR),
  idea                   VARCHAR2(500 CHAR),
  busi_trip_type         VARCHAR2(1 CHAR),
  start_am_or_pm         VARCHAR2(1 CHAR),
  end_am_or_pm           VARCHAR2(1 CHAR),
  trip_colleague         VARCHAR2(500 CHAR),
  trip_colleague_ids     VARCHAR2(100 CHAR)
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
comment on table KQGL_BUSINESS_TRIP_INFO
  is '考勤管理模块：出差信息表
KQGL:考勤管理模块表名前缀';
-- Add comments to the columns 
comment on column KQGL_BUSINESS_TRIP_INFO.id
  is '主键';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_user_id
  is '创建人ID（也是申请人ID字段）';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_user_name
  is '创建人名称（也是申请人名称字段）';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_dept_id
  is '创建部门ID';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_dept_name
  is '创建部门名';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_chushi_id
  is '创建人处室ID';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_chushi_name
  is '创建人处室名';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_ju_id
  is '创建人二级局ID';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_ju_name
  is '创建人二级局名';
comment on column KQGL_BUSINESS_TRIP_INFO.visible
  is '逻辑删除';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_time
  is '创建时间';
comment on column KQGL_BUSINESS_TRIP_INFO.subflag
  is '流程状态';
comment on column KQGL_BUSINESS_TRIP_INFO.title
  is '带班标题';
comment on column KQGL_BUSINESS_TRIP_INFO.update_user_id
  is '最后更新人ID';
comment on column KQGL_BUSINESS_TRIP_INFO.update_user_name
  is '最后更新人名';
comment on column KQGL_BUSINESS_TRIP_INFO.update_time
  is '最后更新时间';
comment on column KQGL_BUSINESS_TRIP_INFO.business_trip_title
  is '标题';
comment on column KQGL_BUSINESS_TRIP_INFO.application_unit_id
  is '申请人单位ID';
comment on column KQGL_BUSINESS_TRIP_INFO.application_unit_name
  is '申请人单位名称';
comment on column KQGL_BUSINESS_TRIP_INFO.application_time
  is '申请时间';
comment on column KQGL_BUSINESS_TRIP_INFO.start_time
  is '出差开始日期';
comment on column KQGL_BUSINESS_TRIP_INFO.end_time
  is '出差结束日期';
comment on column KQGL_BUSINESS_TRIP_INFO.destination
  is '目的地';
comment on column KQGL_BUSINESS_TRIP_INFO.vehicle
  is '乘坐交通工具';
comment on column KQGL_BUSINESS_TRIP_INFO.is_have_reception_fees
  is '有无接待费用';
comment on column KQGL_BUSINESS_TRIP_INFO.reception_fees
  is '接待费用';
comment on column KQGL_BUSINESS_TRIP_INFO.long_time
  is '出差时长';
comment on column KQGL_BUSINESS_TRIP_INFO.business_trip_reason
  is '出差事由';
comment on column KQGL_BUSINESS_TRIP_INFO.filetype
  is '流程类型';
comment on column KQGL_BUSINESS_TRIP_INFO.idea
  is '意见';
comment on column KQGL_BUSINESS_TRIP_INFO.busi_trip_type
  is '出差类型（1行政出差，0业务出差）';
comment on column KQGL_BUSINESS_TRIP_INFO.start_am_or_pm
  is '出差开始上下午 1：上午  0：下午';
comment on column KQGL_BUSINESS_TRIP_INFO.end_am_or_pm
  is '出差结束上下午 1：上午 0：下午';
comment on column KQGL_BUSINESS_TRIP_INFO.trip_colleague
  is '同行人 （新增）';
comment on column KQGL_BUSINESS_TRIP_INFO.trip_colleague_ids
  is '同行人ids';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_BUSINESS_TRIP_INFO
  add constraint PK_KQGL_BUSINESS_TRIP_INFO primary key (ID)
  using index 
  tablespace ZHGL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

-- Create table
create table KQGL_ABSENTEEISM_INFO
(
  id                      VARCHAR2(50 CHAR) not null,
  cre_user_id             VARCHAR2(50 CHAR),
  cre_user_name           VARCHAR2(50 CHAR),
  cre_dept_id             VARCHAR2(50 CHAR),
  cre_dept_name           VARCHAR2(50 CHAR),
  cre_chushi_id           VARCHAR2(50 CHAR),
  cre_chushi_name         VARCHAR2(50 CHAR),
  cre_ju_id               VARCHAR2(50 CHAR),
  cre_ju_name             VARCHAR2(50 CHAR),
  visible                 VARCHAR2(1 CHAR),
  cre_time                VARCHAR2(30 CHAR),
  update_user_id          VARCHAR2(50 CHAR),
  update_user_name        VARCHAR2(50 CHAR),
  update_time             VARCHAR2(30 CHAR),
  applicant_unit_id       VARCHAR2(50 CHAR),
  applicant_unit_name     VARCHAR2(50 CHAR),
  application_time        VARCHAR2(30 CHAR),
  absenteeism_user_id     VARCHAR2(50 CHAR),
  absenteeism_user_name   VARCHAR2(50 CHAR),
  absenteeism_date        VARCHAR2(30 CHAR),
  absenteeism_reason      VARCHAR2(200 CHAR),
  abs_applicant_unit_id   VARCHAR2(50 CHAR),
  abs_applicant_unit_name VARCHAR2(50 CHAR)
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
comment on table KQGL_ABSENTEEISM_INFO
  is '考勤管理模块：旷工信息表 KQGL:考勤管理模块表名前缀';
-- Add comments to the columns 
comment on column KQGL_ABSENTEEISM_INFO.id
  is '主键';
comment on column KQGL_ABSENTEEISM_INFO.cre_user_id
  is '创建人id（也是录入人id字段）';
comment on column KQGL_ABSENTEEISM_INFO.cre_user_name
  is '创建人名称（也是录入人名称字段）';
comment on column KQGL_ABSENTEEISM_INFO.cre_dept_id
  is '创建部门id';
comment on column KQGL_ABSENTEEISM_INFO.cre_dept_name
  is '创建部门name';
comment on column KQGL_ABSENTEEISM_INFO.cre_chushi_id
  is '创建人处室id';
comment on column KQGL_ABSENTEEISM_INFO.cre_chushi_name
  is '创建人处室名';
comment on column KQGL_ABSENTEEISM_INFO.cre_ju_id
  is '创建人二级局id';
comment on column KQGL_ABSENTEEISM_INFO.cre_ju_name
  is '创建人二级局名';
comment on column KQGL_ABSENTEEISM_INFO.visible
  is '逻辑删除';
comment on column KQGL_ABSENTEEISM_INFO.cre_time
  is '创建时间';
comment on column KQGL_ABSENTEEISM_INFO.update_user_id
  is '最后更新人id';
comment on column KQGL_ABSENTEEISM_INFO.update_user_name
  is '最后更新人name';
comment on column KQGL_ABSENTEEISM_INFO.update_time
  is '最后更新时间';
comment on column KQGL_ABSENTEEISM_INFO.applicant_unit_id
  is '录入人单位id';
comment on column KQGL_ABSENTEEISM_INFO.applicant_unit_name
  is '录入人单位名称';
comment on column KQGL_ABSENTEEISM_INFO.application_time
  is '录入时间';
comment on column KQGL_ABSENTEEISM_INFO.absenteeism_user_id
  is '旷工人id';
comment on column KQGL_ABSENTEEISM_INFO.absenteeism_user_name
  is '旷工人姓名';
comment on column KQGL_ABSENTEEISM_INFO.absenteeism_date
  is '旷工日期';
comment on column KQGL_ABSENTEEISM_INFO.absenteeism_reason
  is '旷工原因';
comment on column KQGL_ABSENTEEISM_INFO.abs_applicant_unit_id
  is '旷工人单位id';
comment on column KQGL_ABSENTEEISM_INFO.abs_applicant_unit_name
  is '旷工人单位名称';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_ABSENTEEISM_INFO
  add constraint PK_KQGL_ABSENTEEISM_INFO primary key (ID)
  using index 
  tablespace ZHGL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

-- Create table
create table KQGL_COMELATE_LEAVEEARLY_INFO
(
  id                  VARCHAR2(50 CHAR) not null,
  cre_user_id         VARCHAR2(50 CHAR),
  cre_user_name       VARCHAR2(50 CHAR),
  cre_dept_id         VARCHAR2(50 CHAR),
  cre_dept_name       VARCHAR2(50 CHAR),
  cre_chushi_id       VARCHAR2(50 CHAR),
  cre_chushi_name     VARCHAR2(50 CHAR),
  cre_ju_id           VARCHAR2(50 CHAR),
  cre_ju_name         VARCHAR2(50 CHAR),
  visible             VARCHAR2(1 CHAR),
  cre_time            VARCHAR2(30 CHAR),
  update_user_id      VARCHAR2(50 CHAR),
  update_user_name    VARCHAR2(30 CHAR),
  update_time         VARCHAR2(30 CHAR),
  applicant_unit_id   VARCHAR2(50 CHAR),
  applicant_unit_name VARCHAR2(50 CHAR),
  application_time    VARCHAR2(30 CHAR),
  cdzt_user_name      VARCHAR2(50 CHAR),
  cdzt_user_id        VARCHAR2(30 CHAR),
  state               VARCHAR2(2 CHAR),
  cdzt_date           VARCHAR2(30 CHAR),
  cdzt_reason         VARCHAR2(200 CHAR),
  subflag             VARCHAR2(1 CHAR),
  cdzt_title          VARCHAR2(50 CHAR)
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
comment on table KQGL_COMELATE_LEAVEEARLY_INFO
  is '考勤管理模块：迟到早退信息表
kqgl:考勤管理模块表名前缀';
-- Add comments to the columns 
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.id
  is '主键';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_user_id
  is '创建人id（也是申请人id字段）';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_user_name
  is '创建人名称（也是申请人名称字段）';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_dept_id
  is '创建部门id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_dept_name
  is '创建部门name';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_chushi_id
  is '创建人处室id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_chushi_name
  is '创建人处室name';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_ju_id
  is '创建人二级局id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_ju_name
  is '创建人二级局name';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.visible
  is '逻辑删除';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_time
  is '创建时间';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.update_user_id
  is '最后更新人id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.update_user_name
  is '最后更新人name';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.update_time
  is '最后更新时间';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.applicant_unit_id
  is '申请人单位id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.applicant_unit_name
  is '申请人单位名称';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.application_time
  is '申请时间';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_user_name
  is '迟到早退人姓名';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_user_id
  is '姓名id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.state
  is '状态 0:迟到 1：早退';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_date
  is '迟到早退日期';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_reason
  is '迟到早退原因';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.subflag
  is '流程状态';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_title
  is '迟到早退标题';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_COMELATE_LEAVEEARLY_INFO
  add constraint PK_KQGL_COLATE_LEARLY_INFO primary key (ID)
  using index 
  tablespace ZHGL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

-- Create table
create table KQGL_SUPPLEMENT_SIGN_INFO
(
  id                         VARCHAR2(50 CHAR) not null,
  cre_user_id                VARCHAR2(50 CHAR),
  cre_user_name              VARCHAR2(50 CHAR),
  cre_dept_id                VARCHAR2(50 CHAR),
  cre_dept_name              VARCHAR2(50 CHAR),
  cre_chushi_id              VARCHAR2(50 CHAR),
  cre_chushi_name            VARCHAR2(50 CHAR),
  cre_ju_id                  VARCHAR2(50 CHAR),
  cre_ju_name                VARCHAR2(50 CHAR),
  visible                    VARCHAR2(1 CHAR),
  cre_time                   VARCHAR2(30 CHAR),
  subflag                    VARCHAR2(2 CHAR),
  supplement_sign_title      VARCHAR2(200 CHAR),
  update_user_id             VARCHAR2(50 CHAR),
  update_user_name           VARCHAR2(50 CHAR),
  update_time                VARCHAR2(30 CHAR),
  leave_title                VARCHAR2(100 CHAR),
  applicant_unit_id          VARCHAR2(50 CHAR),
  applicant_unit_name        VARCHAR2(50 CHAR),
  application_time           VARCHAR2(30 CHAR),
  supplement_sign_type       VARCHAR2(50 CHAR),
  supplement_sign_date       VARCHAR2(30 CHAR),
  supplement_sign_start_time VARCHAR2(30 CHAR),
  supplement_sign_end_time   VARCHAR2(30 CHAR),
  supplement_sign_reason     VARCHAR2(500 CHAR)
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
comment on table KQGL_SUPPLEMENT_SIGN_INFO
  is '考勤管理模块：补签信息表
KQGL:考勤管理模块表名前缀';
-- Add comments to the columns 
comment on column KQGL_SUPPLEMENT_SIGN_INFO.id
  is '主键';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_user_id
  is '创建人ID（也是申请人ID字段）';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_user_name
  is '创建人名称（也是申请人名称字段）';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_dept_id
  is '创建人部门ID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_dept_name
  is '创建人部门名';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_chushi_id
  is '创建人处室ID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_chushi_name
  is '创建人处室名';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_ju_id
  is '创建人二级局ID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_ju_name
  is '创建人二级局名';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.visible
  is '逻辑删除';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_time
  is '创建时间';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.subflag
  is '流程状态';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_title
  is '带班标题';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.update_user_id
  is '最后更新人ID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.update_user_name
  is '最后更新人名';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.update_time
  is '最后更新时间';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.leave_title
  is '标题';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.applicant_unit_id
  is '申请人单位ID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.applicant_unit_name
  is '申请人单位名称';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.application_time
  is '申请时间';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_type
  is '补签类型 0：补签入 1：补签出 2：补全天';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_date
  is '补签日期';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_start_time
  is '补签开始时间';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_end_time
  is '补签结束时间';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_reason
  is '补签事由';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_SUPPLEMENT_SIGN_INFO
  add constraint PK_KQGL_SUPPLEMENT_SIGN_INFO primary key (ID)
  using index 
  tablespace ZHGL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
