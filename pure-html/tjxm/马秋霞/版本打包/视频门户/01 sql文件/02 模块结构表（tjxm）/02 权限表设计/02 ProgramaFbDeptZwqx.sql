-- Create table
create table PROGRAMA_FB_DEPT_ZWQX
(
  id                VARCHAR2(50) not null,
  column_id         VARCHAR2(200),
  dept_id           VARCHAR2(50),
  dept_name         VARCHAR2(50),
  position_id       VARCHAR2(50),
  position_name     VARCHAR2(50),
  cre_user_id       VARCHAR2(50),
  cre_user_name     VARCHAR2(50),
  cre_time          VARCHAR2(30),
  visible           VARCHAR2(1),
  dept_zwqx_list_id VARCHAR2(50)
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
comment on column PROGRAMA_FB_DEPT_ZWQX.id
  is '主键id';
comment on column PROGRAMA_FB_DEPT_ZWQX.column_id
  is '关联栏目id';
comment on column PROGRAMA_FB_DEPT_ZWQX.dept_id
  is '部门id';
comment on column PROGRAMA_FB_DEPT_ZWQX.dept_name
  is '部门name';
comment on column PROGRAMA_FB_DEPT_ZWQX.position_id
  is '职务权限ID';
comment on column PROGRAMA_FB_DEPT_ZWQX.position_name
  is '职务权限name';
comment on column PROGRAMA_FB_DEPT_ZWQX.cre_user_id
  is '创建人id';
comment on column PROGRAMA_FB_DEPT_ZWQX.cre_user_name
  is '创建人name';
comment on column PROGRAMA_FB_DEPT_ZWQX.cre_time
  is '创建时间';
comment on column PROGRAMA_FB_DEPT_ZWQX.visible
  is '逻辑删除';
comment on column PROGRAMA_FB_DEPT_ZWQX.dept_zwqx_list_id
  is '部门职务权限列表id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PROGRAMA_FB_DEPT_ZWQX
  add constraint PK_VIDEO_COLUMN_FB_DEPT_ZWQX primary key (ID)
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
