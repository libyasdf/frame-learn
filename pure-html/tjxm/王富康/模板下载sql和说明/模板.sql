-- Create table
create table SYS_TEMPLATE
(
  ID          VARCHAR2(50) not null,
  TITLE       VARCHAR2(200),
  FILE_TYPE   VARCHAR2(10),
  CRE_TIME    VARCHAR2(30),
  CONTENT     BLOB,
  FILE_SIZE   VARCHAR2(50)
)
tablespace ZHGL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 768K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns
comment on column SYS_TEMPLATE.TITLE
  is '模板名称';
comment on column SYS_TEMPLATE.FILE_TYPE
  is '模板的类型';
comment on column SYS_TEMPLATE.CONTENT
  is '模板的内容';
  comment on column SYS_TEMPLATE.FILE_SIZE
  is '模板的大小';
  comment on column SYS_TEMPLATE.CRE_TIME
  is '模板的创建时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table SYS_TEMPLATE
  add constraint TEMPLATE_ID primary key (ID)
  using index
  tablespace PLATFORM
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
