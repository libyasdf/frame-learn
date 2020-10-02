-- Create table
create table VIDEO_COLUMN_FB_USER
(
  id            VARCHAR2(50) not null,
  cre_user_id   VARCHAR2(50),
  cre_user_name VARCHAR2(50),
  visible       VARCHAR2(1),
  cre_tiem      VARCHAR2(30),
  fb_user_id    VARCHAR2(50),
  fb_user_name  VARCHAR2(50),
  sh_user_id    VARCHAR2(50),
  sh_user_name  VARCHAR2(50),
  column_id     VARCHAR2(50)
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
comment on table VIDEO_COLUMN_FB_USER
  is '信息发布栏目发布人，审核人表';
-- Add comments to the columns 
comment on column VIDEO_COLUMN_FB_USER.id
  is '主键id';
comment on column VIDEO_COLUMN_FB_USER.cre_user_id
  is '创建人id';
comment on column VIDEO_COLUMN_FB_USER.cre_user_name
  is '创建人name';
comment on column VIDEO_COLUMN_FB_USER.visible
  is '逻辑删除';
comment on column VIDEO_COLUMN_FB_USER.cre_tiem
  is '创建时间';
comment on column VIDEO_COLUMN_FB_USER.fb_user_id
  is '栏目发布人id';
comment on column VIDEO_COLUMN_FB_USER.fb_user_name
  is '栏目发布人name';
comment on column VIDEO_COLUMN_FB_USER.sh_user_id
  is '栏目审核人id';
comment on column VIDEO_COLUMN_FB_USER.sh_user_name
  is '栏目审核人name';
comment on column VIDEO_COLUMN_FB_USER.column_id
  is '关联栏目id ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table VIDEO_COLUMN_FB_USER
  add constraint PK_VIDEO_COLUMN_FB_USER primary key (ID)
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
