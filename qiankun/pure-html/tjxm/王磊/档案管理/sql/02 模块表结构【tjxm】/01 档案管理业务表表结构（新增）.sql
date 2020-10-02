
create table TJXM.DAGL_BORROW
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
  VISIBLE          VARCHAR2(1 CHAR),
  CRE_TIME         VARCHAR2(30 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(30 CHAR),
  UPDATE_TIME      VARCHAR2(30 CHAR),
  TITLE            VARCHAR2(100 CHAR),
  BORROW_USER_NAME VARCHAR2(50 CHAR),
  BORROW_USER_ID   VARCHAR2(50 CHAR),
  UNIT_ID          VARCHAR2(50 CHAR),
  UNIT_NAME        VARCHAR2(50 CHAR),
  USE_PURPOSE      VARCHAR2(50 CHAR),
  EMAIL            VARCHAR2(50 CHAR),
  PHONE            VARCHAR2(50 CHAR),
  REMARK           VARCHAR2(2000 CHAR),
  SUBFLAG          VARCHAR2(1 CHAR),
  FLOW_TYPE        VARCHAR2(1 CHAR),
  YEAR             VARCHAR2(50 CHAR),
  FILE_UNIT_ID     VARCHAR2(50 CHAR),
  FILE_UNIT_NAME   VARCHAR2(50 CHAR),
  IS_RENEW         VARCHAR2(50 CHAR),
  BORROW_DEPT_ID   VARCHAR2(50 CHAR),
  BORROW_DEPT_NAME VARCHAR2(50 CHAR)
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
comment on table TJXM.DAGL_BORROW
  is '档案管理模块：借阅审批表
dagl:档案管理模块表名前缀';
comment on column TJXM.DAGL_BORROW.ID
  is '主键';
comment on column TJXM.DAGL_BORROW.CRE_USER_ID
  is '创建人id（也是申请人id字段）';
comment on column TJXM.DAGL_BORROW.CRE_USER_NAME
  is '创建人名称（也是申请人名称字段）';
comment on column TJXM.DAGL_BORROW.CRE_DEPT_ID
  is '创建部门id';
comment on column TJXM.DAGL_BORROW.CRE_DEPT_NAME
  is '创建部门name';
comment on column TJXM.DAGL_BORROW.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.DAGL_BORROW.CRE_CHUSHI_NAME
  is '创建人处室name';
comment on column TJXM.DAGL_BORROW.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.DAGL_BORROW.CRE_JU_NAME
  is '创建人二级局name';
comment on column TJXM.DAGL_BORROW.VISIBLE
  is '逻辑删除';
comment on column TJXM.DAGL_BORROW.CRE_TIME
  is '创建时间';
comment on column TJXM.DAGL_BORROW.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.DAGL_BORROW.UPDATE_USER_NAME
  is '最后更新人name';
comment on column TJXM.DAGL_BORROW.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.DAGL_BORROW.TITLE
  is '待办标题';
comment on column TJXM.DAGL_BORROW.BORROW_USER_NAME
  is '借阅姓名';
comment on column TJXM.DAGL_BORROW.BORROW_USER_ID
  is '借阅人id';
comment on column TJXM.DAGL_BORROW.UNIT_ID
  is '所在单位id';
comment on column TJXM.DAGL_BORROW.UNIT_NAME
  is '所在单位名称';
comment on column TJXM.DAGL_BORROW.USE_PURPOSE
  is '利用目的';
comment on column TJXM.DAGL_BORROW.EMAIL
  is '邮箱';
comment on column TJXM.DAGL_BORROW.PHONE
  is '电话';
comment on column TJXM.DAGL_BORROW.REMARK
  is '备份';
comment on column TJXM.DAGL_BORROW.SUBFLAG
  is '流程状态';
comment on column TJXM.DAGL_BORROW.FLOW_TYPE
  is '1：借阅本单位，2：借阅非本单位';
comment on column TJXM.DAGL_BORROW.YEAR
  is '年份';
comment on column TJXM.DAGL_BORROW.FILE_UNIT_ID
  is '归档单位id';
comment on column TJXM.DAGL_BORROW.FILE_UNIT_NAME
  is '归档单位name';
comment on column TJXM.DAGL_BORROW.IS_RENEW
  is '是否续借 0：否；1：是';
comment on column TJXM.DAGL_BORROW.BORROW_DEPT_ID
  is '借阅人所在部门id';
comment on column TJXM.DAGL_BORROW.BORROW_DEPT_NAME
  is '借阅人所在部门名称';
alter table TJXM.DAGL_BORROW
  add primary key (ID)
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


create table TJXM.DAGL_CATEGORY_TABLE
(
  ID               VARCHAR2(36 CHAR) not null,
  CATEGORY_NAME    VARCHAR2(50 CHAR),
  CATEGORY_CODE    VARCHAR2(50 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(30 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  VISIBLE          VARCHAR2(1 CHAR)
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
comment on column TJXM.DAGL_CATEGORY_TABLE.ID
  is '唯一标识';
comment on column TJXM.DAGL_CATEGORY_TABLE.CATEGORY_NAME
  is '门类的中文名';
comment on column TJXM.DAGL_CATEGORY_TABLE.CATEGORY_CODE
  is '门类的代号';
comment on column TJXM.DAGL_CATEGORY_TABLE.CRE_USER_ID
  is '创建人id';
comment on column TJXM.DAGL_CATEGORY_TABLE.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.DAGL_CATEGORY_TABLE.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.DAGL_CATEGORY_TABLE.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.DAGL_CATEGORY_TABLE.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.DAGL_CATEGORY_TABLE.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.DAGL_CATEGORY_TABLE.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.DAGL_CATEGORY_TABLE.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.DAGL_CATEGORY_TABLE.CRE_TIME
  is '创建时间';
comment on column TJXM.DAGL_CATEGORY_TABLE.UPDATE_USER_NAME
  is '最后更新人姓名';
comment on column TJXM.DAGL_CATEGORY_TABLE.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.DAGL_CATEGORY_TABLE.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.DAGL_CATEGORY_TABLE.VISIBLE
  is '0：已“删除；1：未删除';
alter table TJXM.DAGL_CATEGORY_TABLE
  add constraint PK_DAGL_CATEGORY_TABLE primary key (ID)
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


create table TJXM.DAGL_CONFIG
(
  ID     VARCHAR2(50 CHAR) not null,
  COM_ID VARCHAR2(50 CHAR)
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
comment on table TJXM.DAGL_CONFIG
  is '档案管理模块：扫码枪配置表
dagl:档案管理模块表名前缀';
comment on column TJXM.DAGL_CONFIG.ID
  is '主键';
comment on column TJXM.DAGL_CONFIG.COM_ID
  is '串口号';
alter table TJXM.DAGL_CONFIG
  add primary key (ID)
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


create table TJXM.DAGL_CONTRASTING_RELATIONS
(
  ID               VARCHAR2(36 CHAR) not null,
  CONTRAST_NAME    VARCHAR2(50 CHAR),
  SOURCE_NAME      VARCHAR2(36 CHAR),
  TARGET_NAME      VARCHAR2(36 CHAR),
  SOURCE_CHN_NAME  VARCHAR2(50 CHAR),
  TARGET_CHN_NAME  VARCHAR2(50 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(30 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  VISIBLE          VARCHAR2(1 CHAR)
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
comment on table TJXM.DAGL_CONTRASTING_RELATIONS
  is '这里是对数据关系对照关系的汇总';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.ID
  is '唯一标识';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CONTRAST_NAME
  is '对照关系名称';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.SOURCE_NAME
  is '原表name(相当于id，唯一标识)';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.TARGET_NAME
  is '目标表name(相当于id，唯一标识)';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.SOURCE_CHN_NAME
  is '源表中文名';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.TARGET_CHN_NAME
  is '目标表中文名';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CRE_USER_ID
  is '创建人id';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.CRE_TIME
  is '创建时间';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.UPDATE_USER_NAME
  is '最后更新人姓名';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.DAGL_CONTRASTING_RELATIONS.VISIBLE
  is '0：已“删除；1：未删除';
alter table TJXM.DAGL_CONTRASTING_RELATIONS
  add constraint PK_DAGL_CONTRASTING_RELATIONS primary key (ID)
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


create table TJXM.DAGL_DATA_CONTRAST
(
  ID                     VARCHAR2(36 CHAR) not null,
  CONTRASTING_ID         VARCHAR2(36 CHAR),
  SOURCE_COLUMN          VARCHAR2(50 CHAR),
  TARGET_COLUMN          VARCHAR2(50 CHAR),
  SOURCE_TYPE            VARCHAR2(10 CHAR),
  TARGET_TYPE            VARCHAR2(10 CHAR),
  SOURCE_LENGTH          VARCHAR2(10 CHAR),
  TARGET_LENGTH          VARCHAR2(10 CHAR),
  SOURCE_COLUMN_CHN_NAME VARCHAR2(50 CHAR),
  TARGET_COLUMN_CHN_NAME VARCHAR2(50 CHAR)
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
comment on table TJXM.DAGL_DATA_CONTRAST
  is '这里描述了表跟表之间的字段对应关系。';
comment on column TJXM.DAGL_DATA_CONTRAST.ID
  is '唯一标识';
comment on column TJXM.DAGL_DATA_CONTRAST.CONTRASTING_ID
  is '关联关系id';
comment on column TJXM.DAGL_DATA_CONTRAST.SOURCE_COLUMN
  is '源表字段';
comment on column TJXM.DAGL_DATA_CONTRAST.TARGET_COLUMN
  is '目标表字段';
comment on column TJXM.DAGL_DATA_CONTRAST.SOURCE_TYPE
  is '源表字段类型';
comment on column TJXM.DAGL_DATA_CONTRAST.TARGET_TYPE
  is '目标表字段类型';
comment on column TJXM.DAGL_DATA_CONTRAST.SOURCE_LENGTH
  is '源表字段长度';
comment on column TJXM.DAGL_DATA_CONTRAST.TARGET_LENGTH
  is '目标表字段长度';
comment on column TJXM.DAGL_DATA_CONTRAST.SOURCE_COLUMN_CHN_NAME
  is '源表字段中文名';
comment on column TJXM.DAGL_DATA_CONTRAST.TARGET_COLUMN_CHN_NAME
  is '目标表字段中文名';
alter table TJXM.DAGL_DATA_CONTRAST
  add constraint PK_DAGL_DATA_CONTRAST primary key (ID)
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
alter table TJXM.DAGL_DATA_CONTRAST
  add constraint FK_DAGL_DAT_REFERENCE_DAGL_CON foreign key (CONTRASTING_ID)
  references TJXM.DAGL_CONTRASTING_RELATIONS (ID);


create table TJXM.DAGL_FILE
(
  ID                      VARCHAR2(50 CHAR) not null,
  CRE_USER_ID             VARCHAR2(50 CHAR),
  CRE_USER_NAME           VARCHAR2(50 CHAR),
  CRE_DEPT_ID             VARCHAR2(50 CHAR),
  CRE_DEPT_NAME           VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID           VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME         VARCHAR2(50 CHAR),
  CRE_JU_ID               VARCHAR2(50 CHAR),
  CRE_JU_NAME             VARCHAR2(50 CHAR),
  VISIBLE                 VARCHAR2(1 CHAR),
  CRE_TIME                VARCHAR2(30 CHAR),
  UPDATE_USER_ID          VARCHAR2(50 CHAR),
  UPDATE_USER_NAME        VARCHAR2(30 CHAR),
  UPDATE_TIME             VARCHAR2(30 CHAR),
  FILE_CATEGORY           VARCHAR2(50 CHAR),
  FILE_NO                 VARCHAR2(100 CHAR),
  FILE_NAME               VARCHAR2(2000 CHAR),
  FILE_NUM                VARCHAR2(30 CHAR),
  CLASSIFICATION          VARCHAR2(50 CHAR),
  USE_WAY                 VARCHAR2(50 CHAR),
  BELONG_UNIT_ID          VARCHAR2(50 CHAR),
  BELONG_UNIT_NAME        VARCHAR2(50 CHAR),
  BORROW_STATUS           VARCHAR2(50 CHAR),
  BORROW_DATE             VARCHAR2(50 CHAR),
  RETURN_DATE             VARCHAR2(50 CHAR),
  SHOULD_RETURN_DATE      VARCHAR2(50 CHAR),
  BORROW_USER_ID          VARCHAR2(50 CHAR),
  BORROW_USER_NAME        VARCHAR2(50 CHAR),
  APPROVE_USER_ID         VARCHAR2(50 CHAR),
  APPROVE_USER_NAME       VARCHAR2(50 CHAR),
  HANDLE_USER_ID          VARCHAR2(50 CHAR),
  HANDLE_USER_NAME        VARCHAR2(50 CHAR),
  REASON                  VARCHAR2(2000 CHAR),
  USE_RESULT              VARCHAR2(2000 CHAR),
  REMARK                  VARCHAR2(2000 CHAR),
  IS_BORROW               VARCHAR2(50 CHAR),
  FILE_STATUS             VARCHAR2(50 CHAR),
  BORROW_ID               VARCHAR2(4000 CHAR),
  RETURN_TIME             VARCHAR2(50 CHAR),
  IN_RENEW                VARCHAR2(50 CHAR),
  BORROW_DEPT_ID          VARCHAR2(50 CHAR),
  BORROW_DEPT_NAME        VARCHAR2(50 CHAR),
  UNIT_ID                 VARCHAR2(50 CHAR),
  UNIT_NAME               VARCHAR2(50 CHAR),
  ORDER_NUM               VARCHAR2(10 CHAR),
  SHOULD_RETURN_DATE_FLAG VARCHAR2(10 CHAR)
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
comment on table TJXM.DAGL_FILE
  is '档案管理模块：被借阅档案信息表
dagl:档案管理模块表名前缀';
comment on column TJXM.DAGL_FILE.ID
  is '主键';
comment on column TJXM.DAGL_FILE.CRE_USER_ID
  is '创建人id（也是申请人id字段）';
comment on column TJXM.DAGL_FILE.CRE_USER_NAME
  is '创建人名称（也是申请人名称字段）';
comment on column TJXM.DAGL_FILE.CRE_DEPT_ID
  is '创建部门id';
comment on column TJXM.DAGL_FILE.CRE_DEPT_NAME
  is '创建部门name';
comment on column TJXM.DAGL_FILE.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.DAGL_FILE.CRE_CHUSHI_NAME
  is '创建人处室name';
comment on column TJXM.DAGL_FILE.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.DAGL_FILE.CRE_JU_NAME
  is '创建人二级局name';
comment on column TJXM.DAGL_FILE.VISIBLE
  is '逻辑删除';
comment on column TJXM.DAGL_FILE.CRE_TIME
  is '创建时间';
comment on column TJXM.DAGL_FILE.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.DAGL_FILE.UPDATE_USER_NAME
  is '最后更新人name';
comment on column TJXM.DAGL_FILE.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.DAGL_FILE.FILE_CATEGORY
  is '档案类别';
comment on column TJXM.DAGL_FILE.FILE_NO
  is '档号';
comment on column TJXM.DAGL_FILE.FILE_NAME
  is '档案题名';
comment on column TJXM.DAGL_FILE.FILE_NUM
  is '份数';
comment on column TJXM.DAGL_FILE.CLASSIFICATION
  is '密级';
comment on column TJXM.DAGL_FILE.USE_WAY
  is '利用方式';
comment on column TJXM.DAGL_FILE.BELONG_UNIT_ID
  is '归档单位id';
comment on column TJXM.DAGL_FILE.BELONG_UNIT_NAME
  is '归档单位名称';
comment on column TJXM.DAGL_FILE.BORROW_STATUS
  is '状态';
comment on column TJXM.DAGL_FILE.BORROW_DATE
  is '借阅日期';
comment on column TJXM.DAGL_FILE.RETURN_DATE
  is '归还日期';
comment on column TJXM.DAGL_FILE.SHOULD_RETURN_DATE
  is '应还日期';
comment on column TJXM.DAGL_FILE.BORROW_USER_ID
  is '借阅人id';
comment on column TJXM.DAGL_FILE.BORROW_USER_NAME
  is '借阅人名称';
comment on column TJXM.DAGL_FILE.APPROVE_USER_ID
  is '批准人id';
comment on column TJXM.DAGL_FILE.APPROVE_USER_NAME
  is '批准人名称';
comment on column TJXM.DAGL_FILE.HANDLE_USER_ID
  is '经办人id';
comment on column TJXM.DAGL_FILE.HANDLE_USER_NAME
  is '经办人name';
comment on column TJXM.DAGL_FILE.REASON
  is '阅档事由';
comment on column TJXM.DAGL_FILE.USE_RESULT
  is '利用效果';
comment on column TJXM.DAGL_FILE.REMARK
  is '备注';
comment on column TJXM.DAGL_FILE.IS_BORROW
  is '是否借出';
comment on column TJXM.DAGL_FILE.FILE_STATUS
  is '借阅状态：0：流程中1：流程结束';
comment on column TJXM.DAGL_FILE.BORROW_ID
  is '借阅id';
comment on column TJXM.DAGL_FILE.RETURN_TIME
  is '归还的具体时间';
comment on column TJXM.DAGL_FILE.IN_RENEW
  is '是否续借';
comment on column TJXM.DAGL_FILE.BORROW_DEPT_ID
  is '借阅人所在部门id';
comment on column TJXM.DAGL_FILE.BORROW_DEPT_NAME
  is '借阅人所在部门name';
comment on column TJXM.DAGL_FILE.UNIT_ID
  is '借阅人所在单位id';
comment on column TJXM.DAGL_FILE.UNIT_NAME
  is '借阅人所在单位name';
comment on column TJXM.DAGL_FILE.ORDER_NUM
  is '排序字段';
comment on column TJXM.DAGL_FILE.SHOULD_RETURN_DATE_FLAG
  is '设置应还日期按钮的状态：0按钮不出现1按钮出现';
alter table TJXM.DAGL_FILE
  add primary key (ID)
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


create table TJXM.DAGL_PARTY_NUM_RULE
(
  ID               VARCHAR2(36 CHAR) not null,
  CATEGORY_ID      VARCHAR2(36 CHAR),
  RULE_FIELD       VARCHAR2(50 CHAR),
  RULE_NAME        VARCHAR2(50 CHAR),
  CONNECTOR        VARCHAR2(50 CHAR),
  ORDER_NUM        VARCHAR2(5 CHAR),
  NUMB_LENGTH      VARCHAR2(5 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(30 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  VISIBLE          VARCHAR2(1 CHAR)
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
comment on column TJXM.DAGL_PARTY_NUM_RULE.ID
  is '唯一标识';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CATEGORY_ID
  is '门类的id（外键）';
comment on column TJXM.DAGL_PARTY_NUM_RULE.RULE_FIELD
  is '字段列，英文名';
comment on column TJXM.DAGL_PARTY_NUM_RULE.RULE_NAME
  is '字段列，中文名';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CONNECTOR
  is '连接符';
comment on column TJXM.DAGL_PARTY_NUM_RULE.ORDER_NUM
  is '字段位于第几位';
comment on column TJXM.DAGL_PARTY_NUM_RULE.NUMB_LENGTH
  is '档号规则某个字段的长度，长度不够时，0自动补全';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CRE_USER_ID
  is '创建人id';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.DAGL_PARTY_NUM_RULE.CRE_TIME
  is '创建时间';
comment on column TJXM.DAGL_PARTY_NUM_RULE.UPDATE_USER_NAME
  is '最后更新人姓名';
comment on column TJXM.DAGL_PARTY_NUM_RULE.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.DAGL_PARTY_NUM_RULE.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.DAGL_PARTY_NUM_RULE.VISIBLE
  is '0：已“删除；1：未删除';
alter table TJXM.DAGL_PARTY_NUM_RULE
  add constraint PK_DAGL_PARTY_NUM_RULE primary key (ID)
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
alter table TJXM.DAGL_PARTY_NUM_RULE
  add constraint FK_DAGL_PAR_REFERENCE_DAGL_CAT foreign key (CATEGORY_ID)
  references TJXM.DAGL_CATEGORY_TABLE (ID);


create table TJXM.DAGL_Q2_CHANGE
(
  ID                       VARCHAR2(50 CHAR) not null,
  CRE_USER_ID              VARCHAR2(50 CHAR),
  CRE_USER_NAME            VARCHAR2(50 CHAR),
  CRE_DEPT_ID              VARCHAR2(50 CHAR),
  CRE_DEPT_NAME            VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID            VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME          VARCHAR2(50 CHAR),
  CRE_JU_ID                VARCHAR2(50 CHAR),
  CRE_JU_NAME              VARCHAR2(50 CHAR),
  VISIBLE                  VARCHAR2(1 CHAR),
  CRE_TIME                 VARCHAR2(30 CHAR),
  PID                      VARCHAR2(50 CHAR),
  RECID                    VARCHAR2(50 CHAR),
  FOLDER_NO                VARCHAR2(50 CHAR),
  OLD_MAINTITLE            VARCHAR2(50 CHAR),
  OLD_BASEFOLDER_UNIT_ID   VARCHAR2(50 CHAR),
  OLD_BASEFOLDER_UNIT_NAME VARCHAR2(50 CHAR),
  NEW_MAINTITLE            VARCHAR2(50 CHAR),
  NEW_BASEFOLDER_UNIT_ID   VARCHAR2(50 CHAR),
  NEW_BASEFOLDER_UNIT_NAME VARCHAR2(50 CHAR),
  CHANGE_NO                VARCHAR2(50 CHAR),
  CHANGE_DATE              VARCHAR2(30 CHAR),
  REGISTER_DATE            VARCHAR2(30 CHAR),
  REMARK                   VARCHAR2(2000 CHAR)
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
comment on table TJXM.DAGL_Q2_CHANGE
  is '档案管理模块：Q2变更审批表
dagl:档案管理模块表名前缀';
comment on column TJXM.DAGL_Q2_CHANGE.ID
  is '主键';
comment on column TJXM.DAGL_Q2_CHANGE.CRE_USER_ID
  is '创建人id';
comment on column TJXM.DAGL_Q2_CHANGE.CRE_USER_NAME
  is '创建人名称（登记人）';
comment on column TJXM.DAGL_Q2_CHANGE.CRE_DEPT_ID
  is '创建部门id';
comment on column TJXM.DAGL_Q2_CHANGE.CRE_DEPT_NAME
  is '创建部门name';
comment on column TJXM.DAGL_Q2_CHANGE.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.DAGL_Q2_CHANGE.CRE_CHUSHI_NAME
  is '创建人处室name';
comment on column TJXM.DAGL_Q2_CHANGE.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.DAGL_Q2_CHANGE.CRE_JU_NAME
  is '创建人二级局name';
comment on column TJXM.DAGL_Q2_CHANGE.VISIBLE
  is '逻辑删除';
comment on column TJXM.DAGL_Q2_CHANGE.CRE_TIME
  is '创建时间';
comment on column TJXM.DAGL_Q2_CHANGE.PID
  is '批量处理唯一编号（uuid），用于找到某一次所有变更的记录';
comment on column TJXM.DAGL_Q2_CHANGE.RECID
  is '原系统档案唯一值';
comment on column TJXM.DAGL_Q2_CHANGE.FOLDER_NO
  is '案卷级档号';
comment on column TJXM.DAGL_Q2_CHANGE.OLD_MAINTITLE
  is '原主管人名称';
comment on column TJXM.DAGL_Q2_CHANGE.OLD_BASEFOLDER_UNIT_ID
  is '原主管单位id';
comment on column TJXM.DAGL_Q2_CHANGE.OLD_BASEFOLDER_UNIT_NAME
  is '原主管单位name';
comment on column TJXM.DAGL_Q2_CHANGE.NEW_MAINTITLE
  is '新主管人名称';
comment on column TJXM.DAGL_Q2_CHANGE.NEW_BASEFOLDER_UNIT_ID
  is '新主管单位id';
comment on column TJXM.DAGL_Q2_CHANGE.NEW_BASEFOLDER_UNIT_NAME
  is '新主管单位name';
comment on column TJXM.DAGL_Q2_CHANGE.CHANGE_NO
  is '变更单号';
comment on column TJXM.DAGL_Q2_CHANGE.CHANGE_DATE
  is '变更日期';
comment on column TJXM.DAGL_Q2_CHANGE.REGISTER_DATE
  is '登记日期';
comment on column TJXM.DAGL_Q2_CHANGE.REMARK
  is '变更事由';
alter table TJXM.DAGL_Q2_CHANGE
  add constraint PRI primary key (ID)
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


create table TJXM.DAGL_RECEIVE_FILE
(
  ID              VARCHAR2(50 CHAR) not null,
  CRE_USER_ID     VARCHAR2(50 CHAR),
  CRE_USER_NAME   VARCHAR2(50 CHAR),
  CRE_DEPT_ID     VARCHAR2(50 CHAR),
  CRE_DEPT_NAME   VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID   VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME VARCHAR2(50 CHAR),
  CRE_JU_ID       VARCHAR2(50 CHAR),
  CRE_JU_NAME     VARCHAR2(50 CHAR),
  VISIBLE         VARCHAR2(1 CHAR),
  CRE_TIME        VARCHAR2(30 CHAR),
  TITLE           VARCHAR2(255 CHAR) not null,
  FILE_NUM        VARCHAR2(100 CHAR),
  PAGE_NUM        NUMBER,
  FILE_DEPT       VARCHAR2(200 CHAR),
  SECURITY_CLASS  VARCHAR2(4 CHAR),
  CREATED_DATE    VARCHAR2(50 CHAR),
  STATE           VARCHAR2(4 CHAR),
  NOTE            VARCHAR2(500 CHAR),
  TYPE            VARCHAR2(4 CHAR),
  SERIAL_NUM      VARCHAR2(50 CHAR),
  FILE_TIME       VARCHAR2(30 CHAR),
  QUANTITY        NUMBER,
  BARCODE         VARCHAR2(30),
  FILE_TYPE       VARCHAR2(10),
  ZHUS_UNIT       VARCHAR2(100),
  URGENCY_DEGREE  VARCHAR2(10),
  HANDLE_UNIT     VARCHAR2(50),
  RECEIPIENT      VARCHAR2(50)
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
comment on table TJXM.DAGL_RECEIVE_FILE
  is '文电管理中的收文表';
comment on column TJXM.DAGL_RECEIVE_FILE.ID
  is '主键id';
comment on column TJXM.DAGL_RECEIVE_FILE.CRE_USER_ID
  is '创建人id';
comment on column TJXM.DAGL_RECEIVE_FILE.CRE_USER_NAME
  is '创建人name';
comment on column TJXM.DAGL_RECEIVE_FILE.CRE_DEPT_ID
  is '创建人部门id';
comment on column TJXM.DAGL_RECEIVE_FILE.CRE_DEPT_NAME
  is '创建人部门name';
comment on column TJXM.DAGL_RECEIVE_FILE.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.DAGL_RECEIVE_FILE.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.DAGL_RECEIVE_FILE.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.DAGL_RECEIVE_FILE.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.DAGL_RECEIVE_FILE.VISIBLE
  is '逻辑删除，0：删除，1：可见';
comment on column TJXM.DAGL_RECEIVE_FILE.CRE_TIME
  is '文件的创建时间';
comment on column TJXM.DAGL_RECEIVE_FILE.TITLE
  is '文件标题';
comment on column TJXM.DAGL_RECEIVE_FILE.FILE_NUM
  is '原文编号';
comment on column TJXM.DAGL_RECEIVE_FILE.PAGE_NUM
  is '页数';
comment on column TJXM.DAGL_RECEIVE_FILE.FILE_DEPT
  is '来文单位';
comment on column TJXM.DAGL_RECEIVE_FILE.SECURITY_CLASS
  is '密级';
comment on column TJXM.DAGL_RECEIVE_FILE.CREATED_DATE
  is '文件形成日期';
comment on column TJXM.DAGL_RECEIVE_FILE.STATE
  is '01：待归档，02：已归档';
comment on column TJXM.DAGL_RECEIVE_FILE.NOTE
  is '备注';
comment on column TJXM.DAGL_RECEIVE_FILE.TYPE
  is '文件类型：01：需归档；02：留存；03：销毁';
comment on column TJXM.DAGL_RECEIVE_FILE.SERIAL_NUM
  is '文件编号';
comment on column TJXM.DAGL_RECEIVE_FILE.FILE_TIME
  is '收文日期';
comment on column TJXM.DAGL_RECEIVE_FILE.QUANTITY
  is '份数';


create table TJXM.DAGL_SEND_FILE
(
  ID              VARCHAR2(50 CHAR) not null,
  CRE_USER_ID     VARCHAR2(50 CHAR),
  CRE_USER_NAME   VARCHAR2(50 CHAR),
  CRE_DEPT_ID     VARCHAR2(50 CHAR),
  CRE_DEPT_NAME   VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID   VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME VARCHAR2(50 CHAR),
  CRE_JU_ID       VARCHAR2(50 CHAR),
  CRE_JU_NAME     VARCHAR2(50 CHAR),
  VISIBLE         VARCHAR2(1 CHAR),
  CRE_TIME        VARCHAR2(30 CHAR),
  TITLE           VARCHAR2(255 CHAR) not null,
  FILE_NUM        VARCHAR2(100 CHAR),
  PAGE_NUM        NUMBER,
  FILE_DEPT       VARCHAR2(200 CHAR),
  SECURITY_CLASS  VARCHAR2(4 CHAR),
  CREATED_DATE    VARCHAR2(50 CHAR),
  STATE           VARCHAR2(4 CHAR),
  NOTE            VARCHAR2(500 CHAR),
  TYPE            VARCHAR2(4 CHAR),
  SERIAL_NUM      VARCHAR2(50 CHAR),
  FILE_TIME       VARCHAR2(30 CHAR),
  QUANTITY        NUMBER,
  BARCODE         VARCHAR2(30),
  FILE_TYPE       VARCHAR2(10),
  ZHUS_UNIT       VARCHAR2(100),
  URGENCY_DEGREE  VARCHAR2(10),
  HANDLE_UNIT     VARCHAR2(50),
  RECEIPIENT      VARCHAR2(50)
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


create table TJXM.DAGL_VIRTUAL_CLASS
(
  ID              VARCHAR2(50 CHAR) not null,
  CRE_USER_ID     VARCHAR2(50 CHAR),
  CRE_USER_NAME   VARCHAR2(50 CHAR),
  CRE_DEPT_ID     VARCHAR2(50 CHAR),
  CRE_DEPT_NAME   VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID   VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME VARCHAR2(50 CHAR),
  CRE_JU_ID       VARCHAR2(50 CHAR),
  CRE_JU_NAME     VARCHAR2(50 CHAR),
  CRE_TIME        VARCHAR2(30 CHAR),
  COLUMN_NAME     VARCHAR2(30 CHAR),
  COLUMN_VALUE    VARCHAR2(30 CHAR),
  CATEGORY_CODE   VARCHAR2(50 CHAR),
  PID             VARCHAR2(50 CHAR),
  NAME            VARCHAR2(50 CHAR)
)
tablespace ZHGL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table TJXM.DAGL_VIRTUAL_CLASS
  is '档案管理虚拟分类表
dagl：档案管理表前缀';
comment on column TJXM.DAGL_VIRTUAL_CLASS.ID
  is '主键id';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CRE_USER_ID
  is '创建人id';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CRE_TIME
  is '创建时间';
comment on column TJXM.DAGL_VIRTUAL_CLASS.COLUMN_NAME
  is '字段英文名';
comment on column TJXM.DAGL_VIRTUAL_CLASS.COLUMN_VALUE
  is '字段分类值';
comment on column TJXM.DAGL_VIRTUAL_CLASS.CATEGORY_CODE
  is '门类代号';
comment on column TJXM.DAGL_VIRTUAL_CLASS.PID
  is '父虚拟分类id';
comment on column TJXM.DAGL_VIRTUAL_CLASS.NAME
  is '虚拟分类名称';

