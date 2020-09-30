-- Create table
create table PROGRAMA_FB_GROUP
(
  id            VARCHAR2(50) not null,
  column_id     VARCHAR2(200),
  group_id      VARCHAR2(50),
  group_name    VARCHAR2(50),
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
-- Create/Recreate primary, unique and foreign key constraints 
alter table PROGRAMA_FB_GROUP
  add constraint PK_PROGRAMA_FB_GROUP primary key (ID)
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
