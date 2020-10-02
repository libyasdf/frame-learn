-- Create table
create table HISTORY_RECORD
(
  id             VARCHAR2(50) not null,
  user_id        VARCHAR2(50),
  user_name      VARCHAR2(50),
  dept_id        VARCHAR2(50),
  dept_name      VARCHAR2(50),
  chushi_id      VARCHAR2(50),
  chushi_name    VARCHAR2(50),
  ju_id          VARCHAR2(50),
  ju_name        VARCHAR2(50),
  visible        VARCHAR2(1),
  cre_time       VARCHAR2(30),
  content_id     VARCHAR2(50),
  surplus_time   LONG,
  surplus_minute VARCHAR2(200),
  video_id       VARCHAR2(50),
  state          VARCHAR2(1)
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
comment on table HISTORY_RECORD
  is '历史记录表';
-- Add comments to the columns 
comment on column HISTORY_RECORD.id
  is '主键id';
comment on column HISTORY_RECORD.user_id
  is '用户id';
comment on column HISTORY_RECORD.user_name
  is '用户名';
comment on column HISTORY_RECORD.dept_id
  is '部门id';
comment on column HISTORY_RECORD.dept_name
  is '部门名';
comment on column HISTORY_RECORD.chushi_id
  is '处室id';
comment on column HISTORY_RECORD.chushi_name
  is '处室名';
comment on column HISTORY_RECORD.ju_id
  is '二级局id';
comment on column HISTORY_RECORD.ju_name
  is '二级局名';
comment on column HISTORY_RECORD.visible
  is '逻辑删除';
comment on column HISTORY_RECORD.cre_time
  is '创建时间';
comment on column HISTORY_RECORD.content_id
  is '内容id';
comment on column HISTORY_RECORD.surplus_time
  is '剩余多少秒未看';
comment on column HISTORY_RECORD.surplus_minute
  is '剩余分钟数';
comment on column HISTORY_RECORD.video_id
  is '视频id';
comment on column HISTORY_RECORD.state
  is '是否播放完，0表示未播放完，1表示已播放完';
-- Create/Recreate primary, unique and foreign key constraints 
alter table HISTORY_RECORD
  add constraint PK_HISTORY_RECORD primary key (ID)
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
