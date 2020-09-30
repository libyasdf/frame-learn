-- Create table
create table VIDEO_VIDEO_AND_PDF
(
  id              VARCHAR2(50 CHAR) not null,
  info_id         VARCHAR2(50 CHAR),
  cre_user_name   VARCHAR2(50 CHAR),
  cre_time        VARCHAR2(30 CHAR),
  cre_dept_id     VARCHAR2(50 CHAR),
  cre_dept_name   VARCHAR2(50 CHAR),
  cre_chushi_id   VARCHAR2(50 CHAR),
  cre_chushi_name VARCHAR2(50 CHAR),
  cre_ju_id       VARCHAR2(50 CHAR),
  cre_ju_name     VARCHAR2(50 CHAR),
  file_name       VARCHAR2(500 CHAR),
  file_id         VARCHAR2(100 CHAR),
  file_type       VARCHAR2(1 CHAR),
  visible         VARCHAR2(1 CHAR),
  cre_user_id     VARCHAR2(50 CHAR),
  file_id_num     VARCHAR2(50 CHAR)
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
alter table VIDEO_VIDEO_AND_PDF
  add constraint PK_VIDEO_VIDEO_AND_PDF primary key (ID)
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
