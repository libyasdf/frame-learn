-- Create table
create table HYGL_MEETING_SIGN
(
  ID               VARCHAR2(50 CHAR) not null,
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  VISIBLE          VARCHAR2(1 CHAR),
  MEETING_NAME     VARCHAR2(50 CHAR),
  MEETINGROOM      VARCHAR2(50 CHAR),
  CARD_ID          VARCHAR2(50 CHAR),
  SIGN_MACHINE_ID  VARCHAR2(50 CHAR),
  SIGN_IN_TIME     VARCHAR2(50 CHAR),
  REMARK           VARCHAR2(200 CHAR),
  STATE            VARCHAR2(1 CHAR),
  MEETING_APPLY_ID VARCHAR2(50 CHAR),
  CARD_HOLDER      VARCHAR2(50 CHAR)
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
comment on table HYGL_MEETING_SIGN
  is '会议管理模块：会议签到表
HYGL：会议管理表名前缀';
-- Add comments to the columns 
comment on column HYGL_MEETING_SIGN.ID
  is '主键ID';
comment on column HYGL_MEETING_SIGN.CRE_USER_ID
  is '创建人ID';
comment on column HYGL_MEETING_SIGN.CRE_USER_NAME
  is '创建人NAME';
comment on column HYGL_MEETING_SIGN.CRE_DEPT_ID
  is '创建人部门ID';
comment on column HYGL_MEETING_SIGN.CRE_DEPT_NAME
  is '创建人部门NAME';
comment on column HYGL_MEETING_SIGN.CRE_TIME
  is '创建时间';
comment on column HYGL_MEETING_SIGN.UPDATE_USER_NAME
  is '最后更新人';
comment on column HYGL_MEETING_SIGN.UPDATE_USER_ID
  is '最后更新人ID';
comment on column HYGL_MEETING_SIGN.UPDATE_TIME
  is '最后更新时间';
comment on column HYGL_MEETING_SIGN.VISIBLE
  is '逻辑删除';
comment on column HYGL_MEETING_SIGN.MEETING_NAME
  is '会议名称';
comment on column HYGL_MEETING_SIGN.MEETINGROOM
  is '会议室';
comment on column HYGL_MEETING_SIGN.CARD_ID
  is '参会人员CardID';
comment on column HYGL_MEETING_SIGN.SIGN_MACHINE_ID
  is '签到机ID';
comment on column HYGL_MEETING_SIGN.SIGN_IN_TIME
  is '签到时间';
comment on column HYGL_MEETING_SIGN.REMARK
  is '备注';
comment on column HYGL_MEETING_SIGN.STATE
  is '签到状态';
comment on column HYGL_MEETING_SIGN.MEETING_APPLY_ID
  is '会议申请ID';
comment on column HYGL_MEETING_SIGN.CARD_HOLDER
  is '持卡人Id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table HYGL_MEETING_SIGN
  add constraint PK_HYGL_MEETING_SIGN primary key (ID)
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
