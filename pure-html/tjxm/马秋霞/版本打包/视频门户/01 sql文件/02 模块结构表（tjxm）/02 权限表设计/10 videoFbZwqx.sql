-- Create table
create table VIDEO_FB_ZWQX
(
  id            VARCHAR2(50) not null,
  content_id    VARCHAR2(200),
  position_id   VARCHAR2(50),
  position_name VARCHAR2(50),
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
comment on table VIDEO_FB_ZWQX
  is '信息发布范围-职务权限';
-- Add comments to the columns 
comment on column VIDEO_FB_ZWQX.id
  is '主键id';
comment on column VIDEO_FB_ZWQX.content_id
  is '关联内容id';
comment on column VIDEO_FB_ZWQX.position_id
  is '部门id';
comment on column VIDEO_FB_ZWQX.position_name
  is '部门name';
comment on column VIDEO_FB_ZWQX.cre_user_id
  is '创建人id';
comment on column VIDEO_FB_ZWQX.cre_user_name
  is '创建人name';
comment on column VIDEO_FB_ZWQX.cre_time
  is '创建时间';
comment on column VIDEO_FB_ZWQX.visible
  is '逻辑删除';
-- Create/Recreate primary, unique and foreign key constraints 
alter table VIDEO_FB_ZWQX
  add constraint PK_CONTENT_FB_ZWQX primary key (ID)
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
