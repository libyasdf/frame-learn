
create table TJXM.ZBGL_DUTY_CONFIG
(
  ID                     VARCHAR2(50 CHAR) not null,
  CRE_USER_ID            VARCHAR2(50 CHAR),
  CRE_USER_NAME          VARCHAR2(50 CHAR),
  CRE_DEPT_ID            VARCHAR2(50 CHAR),
  CRE_DEPT_NAME          VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID          VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME        VARCHAR2(50 CHAR),
  CRE_JU_ID              VARCHAR2(50 CHAR),
  CRE_JU_NAME            VARCHAR2(50 CHAR),
  VISIBLE                VARCHAR2(1 CHAR),
  CRE_TIME               VARCHAR2(30 CHAR),
  REPORT_OVER_DATE       VARCHAR2(30 CHAR),
  PROMPT_MESSAGE_CONTENT VARCHAR2(500 CHAR),
  UNIT_ID                VARCHAR2(500 CHAR),
  UNIT_NAME              VARCHAR2(500 CHAR),
  UPDATE_USER_ID         VARCHAR2(50 CHAR),
  UPDATE_USER_NAME       VARCHAR2(50 CHAR),
  UPDATE_TIME            VARCHAR2(50 CHAR),
  DUTY_MONTH             VARCHAR2(30 CHAR)
);
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
comment on table TJXM.ZBGL_DUTY_CONFIG
  is '值班管理：基础配置表
zbgl：值班管理数据库表前缀';
comment on column TJXM.ZBGL_DUTY_CONFIG.ID
  is '主键';
comment on column TJXM.ZBGL_DUTY_CONFIG.CRE_USER_ID
  is '创建人id';
comment on column TJXM.ZBGL_DUTY_CONFIG.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.ZBGL_DUTY_CONFIG.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.ZBGL_DUTY_CONFIG.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.ZBGL_DUTY_CONFIG.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.ZBGL_DUTY_CONFIG.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.ZBGL_DUTY_CONFIG.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.ZBGL_DUTY_CONFIG.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.ZBGL_DUTY_CONFIG.VISIBLE
  is '逻辑删除';
comment on column TJXM.ZBGL_DUTY_CONFIG.CRE_TIME
  is '创建时间';
comment on column TJXM.ZBGL_DUTY_CONFIG.REPORT_OVER_DATE
  is '上报截止日期';
comment on column TJXM.ZBGL_DUTY_CONFIG.PROMPT_MESSAGE_CONTENT
  is '催办提示内容';
comment on column TJXM.ZBGL_DUTY_CONFIG.UNIT_ID
  is '值班单位id';
comment on column TJXM.ZBGL_DUTY_CONFIG.UNIT_NAME
  is '值班单位name';
comment on column TJXM.ZBGL_DUTY_CONFIG.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.ZBGL_DUTY_CONFIG.UPDATE_USER_NAME
  is '最后更新人姓名';
comment on column TJXM.ZBGL_DUTY_CONFIG.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.ZBGL_DUTY_CONFIG.DUTY_MONTH
  is '值班的月份';
alter table TJXM.ZBGL_DUTY_CONFIG
  add constraint PK_ZBGL_DUTY_CONFIG primary key (ID)
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

create table TJXM.ZBGL_DUTY_INFO
(
  ID            VARCHAR2(50 CHAR) not null,
  UNIT_ID       VARCHAR2(50 CHAR),
  UNIT_NAME     VARCHAR2(50 CHAR),
  MONTH         VARCHAR2(30 CHAR),
  IS_REPORT     VARCHAR2(1 CHAR),
  REPORTER_ID   VARCHAR2(50 CHAR),
  REPORTER_NAME VARCHAR2(50 CHAR),
  REPORTER_TIME VARCHAR2(50 CHAR),
  IS_ON_TIME    VARCHAR2(1 CHAR)
);
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
comment on table TJXM.ZBGL_DUTY_INFO
  is '值班管理：上报表（每月每个单位有一条数据）
zbgl：值班管理数据库表前缀';
comment on column TJXM.ZBGL_DUTY_INFO.ID
  is '主键id';
comment on column TJXM.ZBGL_DUTY_INFO.UNIT_ID
  is '单位的id';
comment on column TJXM.ZBGL_DUTY_INFO.UNIT_NAME
  is '单位的名称';
comment on column TJXM.ZBGL_DUTY_INFO.MONTH
  is '考勤表的月份';
comment on column TJXM.ZBGL_DUTY_INFO.IS_REPORT
  is '是否上报：0,未上报;1.已上报';
comment on column TJXM.ZBGL_DUTY_INFO.REPORTER_ID
  is '上报人id';
comment on column TJXM.ZBGL_DUTY_INFO.REPORTER_NAME
  is '上报人name';
comment on column TJXM.ZBGL_DUTY_INFO.REPORTER_TIME
  is '上报时间';
comment on column TJXM.ZBGL_DUTY_INFO.IS_ON_TIME
  is '是否按时：0，未按时；1，按时';
alter table TJXM.ZBGL_DUTY_INFO
  add constraint PK_ZBGL_DUTY_INFO primary key (ID)
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

create table TJXM.ZBGL_DUTY_NOTICE
(
  ID               VARCHAR2(50 CHAR) not null,
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  VISIBLE          VARCHAR2(1 CHAR) not null,
  CRE_TIME         VARCHAR2(30 CHAR),
  TITLE            VARCHAR2(200 CHAR),
  START_TIME       VARCHAR2(30 CHAR),
  END_TIME         VARCHAR2(30 CHAR),
  NOTICE_TEXT      VARCHAR2(2000 CHAR),
  REMARK           VARCHAR2(2000 CHAR),
  IS_SECURITY      VARCHAR2(1 CHAR) not null,
  STATE            VARCHAR2(1 CHAR) not null,
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(30 CHAR)
);
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
comment on table TJXM.ZBGL_DUTY_NOTICE
  is '值班管理：值班通知
zbgl:值班管理模块表前缀';
comment on column TJXM.ZBGL_DUTY_NOTICE.ID
  is '主键id';
comment on column TJXM.ZBGL_DUTY_NOTICE.CRE_USER_ID
  is '创建人id';
comment on column TJXM.ZBGL_DUTY_NOTICE.CRE_USER_NAME
  is '创建人name';
comment on column TJXM.ZBGL_DUTY_NOTICE.CRE_DEPT_ID
  is '创建人部门id';
comment on column TJXM.ZBGL_DUTY_NOTICE.CRE_DEPT_NAME
  is '创建人部门name';
comment on column TJXM.ZBGL_DUTY_NOTICE.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.ZBGL_DUTY_NOTICE.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.ZBGL_DUTY_NOTICE.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.ZBGL_DUTY_NOTICE.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.ZBGL_DUTY_NOTICE.VISIBLE
  is '逻辑删除，0：删除，1：可见';
comment on column TJXM.ZBGL_DUTY_NOTICE.CRE_TIME
  is '值班通知的创建时间';
comment on column TJXM.ZBGL_DUTY_NOTICE.TITLE
  is '值班工作标题';
comment on column TJXM.ZBGL_DUTY_NOTICE.START_TIME
  is '值班工作有效期开始日期';
comment on column TJXM.ZBGL_DUTY_NOTICE.END_TIME
  is '值班工作有效期结束日期';
comment on column TJXM.ZBGL_DUTY_NOTICE.NOTICE_TEXT
  is '值班工作通知的具体内容';
comment on column TJXM.ZBGL_DUTY_NOTICE.REMARK
  is '备注';
comment on column TJXM.ZBGL_DUTY_NOTICE.IS_SECURITY
  is '是否是安保值班通知 0：否，1：是';
comment on column TJXM.ZBGL_DUTY_NOTICE.STATE
  is '值班通知是否已经发布 0：保存草稿，1：已发布';
comment on column TJXM.ZBGL_DUTY_NOTICE.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.ZBGL_DUTY_NOTICE.UPDATE_USER_NAME
  is '最后更新人name';
comment on column TJXM.ZBGL_DUTY_NOTICE.UPDATE_TIME
  is '最后更新时间';
alter table TJXM.ZBGL_DUTY_NOTICE
  add constraint PK_ZBGL_DUTY_NOTICE primary key (ID)
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

create table TJXM.ZBGL_DUTY_NOTICE_INCEPT
(
  ID              VARCHAR2(50 CHAR) not null,
  ZB_NOTICE_ID    VARCHAR2(50 CHAR),
  CRE_USER_ID     VARCHAR2(50 CHAR),
  CRE_USER_NAME   VARCHAR2(50 CHAR),
  CRE_DEPT_ID     VARCHAR2(50 CHAR),
  CRE_DEPT_NAME   VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID   VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME VARCHAR2(50 CHAR),
  CRE_JU_ID       VARCHAR2(50 CHAR),
  CRE_JU_NAME     VARCHAR2(50 CHAR),
  VISIBLE         VARCHAR2(1 CHAR) not null,
  CRE_TIME        VARCHAR2(30 CHAR),
  ZB_DEPT_ID      VARCHAR2(50 CHAR),
  ZB_DEPT_NAME    VARCHAR2(50 CHAR),
  IS_READ         VARCHAR2(1 CHAR) not null,
  READ_USER_ID    VARCHAR2(50 CHAR),
  READ_USER_NAME  VARCHAR2(50 CHAR),
  READ_TIME       VARCHAR2(50 CHAR),
  IS_REPORT       VARCHAR2(1 CHAR) not null
);
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
comment on table TJXM.ZBGL_DUTY_NOTICE_INCEPT
  is '值班管理：值班通知接收（发布时，选择几个单位，就有几条数据）
zbgl：值班管理数据库表前缀
';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.ID
  is '主键id';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.ZB_NOTICE_ID
  is '值班通知主键id';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.CRE_USER_ID
  is '创建人id';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.CRE_USER_NAME
  is '创建人name';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.CRE_DEPT_ID
  is '创建人部门id';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.CRE_DEPT_NAME
  is '创建人部门name';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.VISIBLE
  is '逻辑删除，0：删除，1：可见';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.CRE_TIME
  is '创建时间';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.ZB_DEPT_ID
  is '值班部门id';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.ZB_DEPT_NAME
  is '值班部门name';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.IS_READ
  is '本部门是否已读此通知，0：未读，1：已读';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.READ_USER_ID
  is '读取此通知的人员id';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.READ_USER_NAME
  is '读取此通知的人员名称';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.READ_TIME
  is '读取此通知的时间';
comment on column TJXM.ZBGL_DUTY_NOTICE_INCEPT.IS_REPORT
  is '部门根据此通知是否上报值班表，0：未上报，1：已上报';
alter table TJXM.ZBGL_DUTY_NOTICE_INCEPT
  add constraint PK_ZBGL_DUTY_NOTICE_INCEPT primary key (ID)
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
alter table TJXM.ZBGL_DUTY_NOTICE_INCEPT
  add constraint FK_ZBGL_DUT_REFERENCE_ZBGL_INT foreign key (ZB_NOTICE_ID)
  references TJXM.ZBGL_DUTY_NOTICE (ID)
  disable;

create table TJXM.ZBGL_DUTY_DETAIL
(
  ID                 VARCHAR2(50 CHAR) not null,
  CRE_USER_ID        VARCHAR2(50 CHAR),
  CRE_USER_NAME      VARCHAR2(50 CHAR),
  CRE_DEPT_ID        VARCHAR2(50 CHAR),
  CRE_DEPT_NAME      VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID      VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME    VARCHAR2(50 CHAR),
  CRE_JU_ID          VARCHAR2(50 CHAR),
  CRE_JU_NAME        VARCHAR2(50 CHAR),
  VISIBLE            VARCHAR2(1 CHAR),
  CRE_TIME           VARCHAR2(30 CHAR),
  REPORT_ID          VARCHAR2(50 CHAR),
  WATCH_DATE         VARCHAR2(30 CHAR),
  WATCH_ID           VARCHAR2(50 CHAR),
  WATCH_NAME         VARCHAR2(50 CHAR),
  CLASSES            VARCHAR2(1 CHAR),
  PHONE              VARCHAR2(20 CHAR),
  COMMON_PHONE       VARCHAR2(20 CHAR),
  PRIVATE_PHONE      VARCHAR2(20 CHAR),
  SHIFT_LEADER_ID    VARCHAR2(50 CHAR),
  SHIFT_LEADER_NAME  VARCHAR2(50 CHAR),
  SHIFT_LEADER_PHONE VARCHAR2(20 CHAR),
  IS_SECURITY        VARCHAR2(1 CHAR),
  ZB_NOTICE_ID       VARCHAR2(50 CHAR),
  WEEKDAY            VARCHAR2(20 CHAR)
);
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
comment on table TJXM.ZBGL_DUTY_DETAIL
  is '值班管理：值班表（每天都是谁值班）
zbgl：值班管理数据库表前缀';
comment on column TJXM.ZBGL_DUTY_DETAIL.ID
  is '主键';
comment on column TJXM.ZBGL_DUTY_DETAIL.CRE_USER_ID
  is '创建人id';
comment on column TJXM.ZBGL_DUTY_DETAIL.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.ZBGL_DUTY_DETAIL.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.ZBGL_DUTY_DETAIL.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.ZBGL_DUTY_DETAIL.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.ZBGL_DUTY_DETAIL.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.ZBGL_DUTY_DETAIL.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.ZBGL_DUTY_DETAIL.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.ZBGL_DUTY_DETAIL.VISIBLE
  is '逻辑删除';
comment on column TJXM.ZBGL_DUTY_DETAIL.CRE_TIME
  is '创建时间';
comment on column TJXM.ZBGL_DUTY_DETAIL.REPORT_ID
  is '上报表的id:是否上报';
comment on column TJXM.ZBGL_DUTY_DETAIL.WATCH_DATE
  is '值班的日期';
comment on column TJXM.ZBGL_DUTY_DETAIL.WATCH_ID
  is '值班人id';
comment on column TJXM.ZBGL_DUTY_DETAIL.WATCH_NAME
  is '值班人name';
comment on column TJXM.ZBGL_DUTY_DETAIL.CLASSES
  is '值班班次：0.昼;1.夜;2.早;3.中;4.晚';
comment on column TJXM.ZBGL_DUTY_DETAIL.PHONE
  is '值班人联系电话';
comment on column TJXM.ZBGL_DUTY_DETAIL.COMMON_PHONE
  is '值班人普网电话';
comment on column TJXM.ZBGL_DUTY_DETAIL.PRIVATE_PHONE
  is '值班人专网电话';
comment on column TJXM.ZBGL_DUTY_DETAIL.SHIFT_LEADER_ID
  is '带班领导ID';
comment on column TJXM.ZBGL_DUTY_DETAIL.SHIFT_LEADER_NAME
  is '带班领导姓名';
comment on column TJXM.ZBGL_DUTY_DETAIL.SHIFT_LEADER_PHONE
  is '带班领导电话';
comment on column TJXM.ZBGL_DUTY_DETAIL.IS_SECURITY
  is '0：不是；1：是';
comment on column TJXM.ZBGL_DUTY_DETAIL.ZB_NOTICE_ID
  is '值班通知接收id';
comment on column TJXM.ZBGL_DUTY_DETAIL.WEEKDAY
  is '周几';
alter table TJXM.ZBGL_DUTY_DETAIL
  add constraint PK_ZBGL_DUTY_DETAIL primary key (ID)
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
alter table TJXM.ZBGL_DUTY_DETAIL
  add constraint FK_REFERENCE_DUTY_INFO_ID foreign key (REPORT_ID)
  references TJXM.ZBGL_DUTY_INFO (ID);
alter table TJXM.ZBGL_DUTY_DETAIL
  add constraint FK_REFERENCE_NOTICE_INCEPT_ID foreign key (ZB_NOTICE_ID)
  references TJXM.ZBGL_DUTY_NOTICE_INCEPT (ID);

