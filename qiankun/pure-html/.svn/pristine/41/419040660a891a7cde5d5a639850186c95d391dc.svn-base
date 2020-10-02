-- Create table
create table SYS_USER_DEFINE_SETTING
(
  ID                      VARCHAR2(50) not null,
  USER_ID                 VARCHAR2(50),
  PERSONAL_PORTAL_ADDRESS VARCHAR2(1),
  THEME_SETTING           VARCHAR2(1)
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
comment on table SYS_USER_DEFINE_SETTING
  is '用户自定义设置表：设置主题色';
-- Add comments to the columns 
comment on column SYS_USER_DEFINE_SETTING.ID
  is '主键';
comment on column SYS_USER_DEFINE_SETTING.USER_ID
  is '用户ID';
comment on column SYS_USER_DEFINE_SETTING.PERSONAL_PORTAL_ADDRESS
  is '个人门户地址 （1:旧门户，2:新门户）';
comment on column SYS_USER_DEFINE_SETTING.THEME_SETTING
  is '主题设置（1:蓝色主题，2:红色主题）';