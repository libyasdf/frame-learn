-- Create table
create table PROGRAMA_FB_DEPT_ZWQX_LIST
(
  id            VARCHAR2(50) not null,
  column_id     VARCHAR2(200),
  cre_user_id   VARCHAR2(50),
  cre_user_name VARCHAR2(50),
  cre_time      VARCHAR2(30),
  visible       VARCHAR2(1)
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
comment on table PROGRAMA_FB_DEPT_ZWQX_LIST
  is '信息发布范围——部门职务权限列表';
-- Add comments to the columns 
comment on column PROGRAMA_FB_DEPT_ZWQX_LIST.id
  is '主键id';
comment on column PROGRAMA_FB_DEPT_ZWQX_LIST.column_id
  is '关联栏目id';
comment on column PROGRAMA_FB_DEPT_ZWQX_LIST.cre_user_id
  is '创建人id';
comment on column PROGRAMA_FB_DEPT_ZWQX_LIST.cre_user_name
  is '创建人name';
comment on column PROGRAMA_FB_DEPT_ZWQX_LIST.cre_time
  is '创建时间';
comment on column PROGRAMA_FB_DEPT_ZWQX_LIST.visible
  is '逻辑删除';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PROGRAMA_FB_DEPT_ZWQX_LIST
  add constraint PK_VI_COLUMN_FB_DEPT_ZWQX_LIST primary key (ID)
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
