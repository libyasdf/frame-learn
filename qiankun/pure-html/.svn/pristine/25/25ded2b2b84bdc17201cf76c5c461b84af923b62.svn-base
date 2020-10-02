--删除原来的表
drop table SYS_HONOR;
-- Create table
create table SYS_HONOR
(
  id               VARCHAR2(50 CHAR) not null,
  cre_user_id      VARCHAR2(50 CHAR),
  cre_user_name    VARCHAR2(50 CHAR),
  cre_dept_id      VARCHAR2(50 CHAR),
  cre_dept_name    VARCHAR2(50 CHAR),
  cre_chushi_id    VARCHAR2(50 CHAR),
  cre_chushi_name  VARCHAR2(50 CHAR),
  cre_ju_id        VARCHAR2(50 CHAR),
  cre_ju_name      VARCHAR2(50 CHAR),
  visible          VARCHAR2(1 CHAR),
  cre_time         VARCHAR2(30 CHAR),
  year             VARCHAR2(30 CHAR),
  update_user_id   VARCHAR2(50 CHAR),
  update_user_name VARCHAR2(50 CHAR),
  update_time      VARCHAR2(50 CHAR),
  is_publish       VARCHAR2(1 CHAR)
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
-- Add comments to the columns
comment on column SYS_HONOR.id
  is '主键ID';
comment on column SYS_HONOR.cre_user_id
  is '创建人ID';
comment on column SYS_HONOR.cre_user_name
  is '创建人姓名';
comment on column SYS_HONOR.cre_dept_id
  is '创建人部门ID';
comment on column SYS_HONOR.cre_dept_name
  is '创建人部门名称';
comment on column SYS_HONOR.cre_chushi_id
  is '创建人处室ID';
comment on column SYS_HONOR.cre_chushi_name
  is '创建人处室名';
comment on column SYS_HONOR.cre_ju_id
  is '创建人二级局ID';
comment on column SYS_HONOR.cre_ju_name
  is '创建人二级局名';
comment on column SYS_HONOR.visible
  is '逻辑删除';
comment on column SYS_HONOR.cre_time
  is '创建时间';
comment on column SYS_HONOR.year
  is '年度';
comment on column SYS_HONOR.update_user_id
  is '最后更新人ID';
comment on column SYS_HONOR.update_user_name
  is '最后更新人NAME';
comment on column SYS_HONOR.update_time
  is '最后更新时间';
comment on column SYS_HONOR.is_publish
  is '是否发布，0：未发布，1已发布';
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_HONOR
  add constraint PK_SYS_HONOR primary key (ID)
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
create table SYS_HONOR_DETAILS
(
  id               VARCHAR2(50 CHAR) not null,
  honor_id         VARCHAR2(50 CHAR),
  person_name      VARCHAR2(100 CHAR),
  feats_type       VARCHAR2(1 CHAR),
  cre_user_id      VARCHAR2(50 CHAR),
  cre_user_name    VARCHAR2(50 CHAR),
  cre_dept_id      VARCHAR2(50 CHAR),
  cre_dept_name    VARCHAR2(50 CHAR),
  cre_chushi_id    VARCHAR2(50 CHAR),
  cre_chushi_name  VARCHAR2(50 CHAR),
  cre_ju_id        VARCHAR2(50 CHAR),
  cre_ju_name      VARCHAR2(50 CHAR),
  visible          VARCHAR2(1 CHAR),
  cre_time         VARCHAR2(30 CHAR),
  update_user_id   VARCHAR2(50 CHAR),
  update_user_name VARCHAR2(50 CHAR),
  update_time      VARCHAR2(50 CHAR),
  order_number     NUMBER,
  honor_story      VARCHAR2(2000 CHAR)
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
-- Add comments to the columns
comment on column SYS_HONOR_DETAILS.person_name
  is '人或团体';
comment on column SYS_HONOR_DETAILS.feats_type
  is '1：一级英模，2：二级英模，3：个人一等功，4：个人二等功，5：个人三等功，6：集体一等功，7：集体二等功，8：集体三等功';
comment on column SYS_HONOR_DETAILS.honor_story
  is '英雄事迹';
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_HONOR_DETAILS
  add constraint PK_SYS_HONOR_DETAILS primary key (ID)
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