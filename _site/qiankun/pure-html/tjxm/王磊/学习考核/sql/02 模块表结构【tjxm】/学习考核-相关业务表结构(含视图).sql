
create table TJXM.XXKH_ANSWER
(
  ID              VARCHAR2(50 CHAR) not null,
  TEST_ID         VARCHAR2(50 CHAR),
  PAPER_ID        VARCHAR2(50 CHAR),
  QUESTION_ID     VARCHAR2(50 CHAR),
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
  OPTION_CONTENT  VARCHAR2(4000 CHAR),
  OPTION_ID       VARCHAR2(500 CHAR),
  SCORE           VARCHAR2(5 CHAR),
  QUESTION_TYPE   VARCHAR2(1 CHAR)
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
comment on table TJXM.XXKH_ANSWER
  is '学习考核模块：答题管理表
xxkh：学习考核模块表名前缀';
comment on column TJXM.XXKH_ANSWER.ID
  is '主键id';
comment on column TJXM.XXKH_ANSWER.TEST_ID
  is '考试id';
comment on column TJXM.XXKH_ANSWER.PAPER_ID
  is '试卷id';
comment on column TJXM.XXKH_ANSWER.QUESTION_ID
  is '试题id';
comment on column TJXM.XXKH_ANSWER.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_ANSWER.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_ANSWER.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_ANSWER.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.XXKH_ANSWER.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.XXKH_ANSWER.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_ANSWER.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.XXKH_ANSWER.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_ANSWER.VISIBLE
  is '逻辑删除';
comment on column TJXM.XXKH_ANSWER.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_ANSWER.OPTION_CONTENT
  is '答题者填写/选择的答案的串，以$分割';
comment on column TJXM.XXKH_ANSWER.OPTION_ID
  is '答题者填写的答案id的串，以$分割';
comment on column TJXM.XXKH_ANSWER.SCORE
  is '分数';
comment on column TJXM.XXKH_ANSWER.QUESTION_TYPE
  is '1：单选；2：多选；3：判断；4：填空；5：问答';
alter table TJXM.XXKH_ANSWER
  add constraint PK_XXKH_ANSWER primary key (ID)
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


create table TJXM.XXKH_LEARN_TIME
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
  CRE_TIME        VARCHAR2(50 CHAR),
  LEARN_DATE      VARCHAR2(50 CHAR),
  START_TIME      VARCHAR2(50 CHAR),
  OVER_TIME       VARCHAR2(50 CHAR),
  LEARN_TIME_H    NUMBER,
  INFO_ID         VARCHAR2(50 CHAR),
  INFO_NAME       VARCHAR2(500 CHAR),
  VISIBLE         VARCHAR2(1 CHAR)
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
comment on table TJXM.XXKH_LEARN_TIME
  is '学习考核：学习记录表
xxkh：学习考核模块数据库表前缀';
comment on column TJXM.XXKH_LEARN_TIME.ID
  is '主键id';
comment on column TJXM.XXKH_LEARN_TIME.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_LEARN_TIME.CRE_USER_NAME
  is '创建人name';
comment on column TJXM.XXKH_LEARN_TIME.CRE_DEPT_ID
  is '创建人部门id';
comment on column TJXM.XXKH_LEARN_TIME.CRE_DEPT_NAME
  is '创建人部门name';
comment on column TJXM.XXKH_LEARN_TIME.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.XXKH_LEARN_TIME.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_LEARN_TIME.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.XXKH_LEARN_TIME.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_LEARN_TIME.CRE_TIME
  is '创建时间yyyy-mm-dd hh:mm:ss';
comment on column TJXM.XXKH_LEARN_TIME.LEARN_DATE
  is '本次学习日期yyyy-mm-dd';
comment on column TJXM.XXKH_LEARN_TIME.START_TIME
  is '本次学习开始时间yyyy-mm-dd hh:mm:ss';
comment on column TJXM.XXKH_LEARN_TIME.OVER_TIME
  is 'yyyy-mm-dd hh:mm:ss';
comment on column TJXM.XXKH_LEARN_TIME.LEARN_TIME_H
  is '本次学习时长（小时）';
comment on column TJXM.XXKH_LEARN_TIME.INFO_ID
  is '资料id';
comment on column TJXM.XXKH_LEARN_TIME.INFO_NAME
  is '资料名称';
comment on column TJXM.XXKH_LEARN_TIME.VISIBLE
  is '是否可用，0：不可用，1：可用';
alter table TJXM.XXKH_LEARN_TIME
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


create table TJXM.XXKH_OPTION
(
  ID               VARCHAR2(50 CHAR) not null,
  QUESTION_ID      VARCHAR2(50 CHAR) not null,
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
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  IS_RIGHT         VARCHAR2(1 CHAR),
  CONTENT          VARCHAR2(1000 CHAR),
  SEQUENCE         VARCHAR2(2 CHAR)
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
comment on table TJXM.XXKH_OPTION
  is '学习考核模块：答案表
xxkh：学习考核模块表名前缀';
comment on column TJXM.XXKH_OPTION.ID
  is '主键id';
comment on column TJXM.XXKH_OPTION.QUESTION_ID
  is '试题id';
comment on column TJXM.XXKH_OPTION.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_OPTION.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_OPTION.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_OPTION.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.XXKH_OPTION.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.XXKH_OPTION.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_OPTION.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.XXKH_OPTION.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_OPTION.VISIBLE
  is '逻辑删除';
comment on column TJXM.XXKH_OPTION.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_OPTION.UPDATE_USER_NAME
  is '最后更新人姓名';
comment on column TJXM.XXKH_OPTION.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.XXKH_OPTION.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.XXKH_OPTION.IS_RIGHT
  is '1：是；0：否';
comment on column TJXM.XXKH_OPTION.CONTENT
  is '选项内容';
comment on column TJXM.XXKH_OPTION.SEQUENCE
  is '在试题中的顺序1、2、3、4，用于试卷试题选项展示';
alter table TJXM.XXKH_OPTION
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


create table TJXM.XXKH_PAPER_INFO
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
  CRE_TIME         VARCHAR2(30 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  VISIBLE          VARCHAR2(1 CHAR),
  NAME             VARCHAR2(50 CHAR),
  TYPE             VARCHAR2(50 CHAR),
  NODE_ID          VARCHAR2(50 CHAR),
  DIFFICULTY_LEVEL VARCHAR2(10 CHAR),
  REMARK           VARCHAR2(500 CHAR),
  IS_SHARE         VARCHAR2(2 CHAR),
  CREATE_TYPE      VARCHAR2(2 CHAR),
  FULL_SCORE       VARCHAR2(5 CHAR),
  STATE            VARCHAR2(2 CHAR),
  DEPT_AUTO_TYPE   VARCHAR2(50 CHAR)
);
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
comment on table TJXM.XXKH_PAPER_INFO
  is '学习考核模块：试卷管理信息表
xxkh：学习考核模块表名前缀';
comment on column TJXM.XXKH_PAPER_INFO.ID
  is '主键id';
comment on column TJXM.XXKH_PAPER_INFO.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_PAPER_INFO.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_PAPER_INFO.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_PAPER_INFO.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.XXKH_PAPER_INFO.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.XXKH_PAPER_INFO.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_PAPER_INFO.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.XXKH_PAPER_INFO.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_PAPER_INFO.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_PAPER_INFO.UPDATE_USER_NAME
  is '最后更新人姓名';
comment on column TJXM.XXKH_PAPER_INFO.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.XXKH_PAPER_INFO.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.XXKH_PAPER_INFO.VISIBLE
  is '0：已“删除；1：未删除';
comment on column TJXM.XXKH_PAPER_INFO.NAME
  is '试卷名称';
comment on column TJXM.XXKH_PAPER_INFO.TYPE
  is '试题所属大类:法制、保密等';
comment on column TJXM.XXKH_PAPER_INFO.NODE_ID
  is '试题所属具体类别：法制-物权法、法制-劳动法等，对应树的唯一标识';
comment on column TJXM.XXKH_PAPER_INFO.DIFFICULTY_LEVEL
  is '1：简单；2：一般；3：困难';
comment on column TJXM.XXKH_PAPER_INFO.REMARK
  is '备注';
comment on column TJXM.XXKH_PAPER_INFO.IS_SHARE
  is '是否共享试卷，0：不共享，1共享';
comment on column TJXM.XXKH_PAPER_INFO.CREATE_TYPE
  is '组卷方式，0：人工组卷，1：自动组卷';
comment on column TJXM.XXKH_PAPER_INFO.FULL_SCORE
  is '试卷总分';
comment on column TJXM.XXKH_PAPER_INFO.STATE
  is '0：草稿；1：已发布';
comment on column TJXM.XXKH_PAPER_INFO.DEPT_AUTO_TYPE
  is '部门自动组卷类型';
alter table TJXM.XXKH_PAPER_INFO
  add constraint XXHK_PARPER_INFO_PK primary key (ID)
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


create table TJXM.XXKH_QUESTION_GROUP
(
  ID              VARCHAR2(50 CHAR) not null,
  PAPER_ID        VARCHAR2(50 CHAR),
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
  CREATE_TYPE     VARCHAR2(1 CHAR),
  SEQUENCE        VARCHAR2(1 CHAR),
  QUESTION_COUNT  VARCHAR2(5 CHAR),
  EVERY_SCORE     VARCHAR2(5 CHAR),
  FULL_SCORE      VARCHAR2(5 CHAR),
  SIMPLE_COUNT    VARCHAR2(5 CHAR),
  NORMAL_COUNT    VARCHAR2(5 CHAR),
  HARD_COUNT      VARCHAR2(5 CHAR),
  ZUJUAN_STATUS   VARCHAR2(1 CHAR),
  QUESTION_TYPE   VARCHAR2(1 CHAR)
);
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
comment on table TJXM.XXKH_QUESTION_GROUP
  is '学习考核：试题组：即组卷时，单选、多选等类型称之为试题组，引入这个概念，主要是方便修改试卷时展示各类试题用。
xxkh：学习考核数据库表前缀。';
comment on column TJXM.XXKH_QUESTION_GROUP.ID
  is '主键id';
comment on column TJXM.XXKH_QUESTION_GROUP.PAPER_ID
  is '试卷id';
comment on column TJXM.XXKH_QUESTION_GROUP.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_QUESTION_GROUP.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_QUESTION_GROUP.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_QUESTION_GROUP.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.XXKH_QUESTION_GROUP.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.XXKH_QUESTION_GROUP.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_QUESTION_GROUP.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.XXKH_QUESTION_GROUP.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_QUESTION_GROUP.VISIBLE
  is '逻辑删除';
comment on column TJXM.XXKH_QUESTION_GROUP.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_QUESTION_GROUP.CREATE_TYPE
  is '0:人工组卷，1：自动组卷';
comment on column TJXM.XXKH_QUESTION_GROUP.SEQUENCE
  is '试题组在试卷中顺序';
comment on column TJXM.XXKH_QUESTION_GROUP.QUESTION_COUNT
  is '人工组卷时该题型数量';
comment on column TJXM.XXKH_QUESTION_GROUP.EVERY_SCORE
  is '该题型每题分数';
comment on column TJXM.XXKH_QUESTION_GROUP.FULL_SCORE
  is '该题型总分';
comment on column TJXM.XXKH_QUESTION_GROUP.SIMPLE_COUNT
  is '自动组卷时，该试题组中简单题多少个';
comment on column TJXM.XXKH_QUESTION_GROUP.NORMAL_COUNT
  is '自动组卷时，该试题组中一般题多少个';
comment on column TJXM.XXKH_QUESTION_GROUP.HARD_COUNT
  is '自动组卷时，该试题组中困难题数量';
comment on column TJXM.XXKH_QUESTION_GROUP.ZUJUAN_STATUS
  is '自动组卷时，改试题组的组卷状态；0：未组卷；1：已组卷';
comment on column TJXM.XXKH_QUESTION_GROUP.QUESTION_TYPE
  is '试题类型：1：单选；2：多选；3：判断；4：填空；5：简答';
alter table TJXM.XXKH_QUESTION_GROUP
  add constraint XXKH_QUESTION_GROUP primary key (ID)
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


create table TJXM.XXKH_QUESTION_INFO
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
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  NAME             VARCHAR2(50 CHAR),
  TYPE             VARCHAR2(50 CHAR),
  NODE_ID          VARCHAR2(50 CHAR),
  DIFFICULTY_LEVEL VARCHAR2(1 CHAR),
  QUESTION_TYPE    VARCHAR2(1 CHAR),
  DESCRIBE         VARCHAR2(1000 CHAR),
  ANALYSIS         VARCHAR2(1000 CHAR),
  STATE            VARCHAR2(5 CHAR)
);
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
comment on table TJXM.XXKH_QUESTION_INFO
  is '学习考核模块：试题管理信息表
xxkh：学习考核模块表名前缀';
comment on column TJXM.XXKH_QUESTION_INFO.ID
  is '主键id';
comment on column TJXM.XXKH_QUESTION_INFO.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_QUESTION_INFO.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_QUESTION_INFO.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_QUESTION_INFO.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.XXKH_QUESTION_INFO.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.XXKH_QUESTION_INFO.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_QUESTION_INFO.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.XXKH_QUESTION_INFO.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_QUESTION_INFO.VISIBLE
  is '逻辑删除';
comment on column TJXM.XXKH_QUESTION_INFO.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_QUESTION_INFO.UPDATE_USER_NAME
  is '最后更新人姓名';
comment on column TJXM.XXKH_QUESTION_INFO.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.XXKH_QUESTION_INFO.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.XXKH_QUESTION_INFO.NAME
  is '试题名称';
comment on column TJXM.XXKH_QUESTION_INFO.TYPE
  is '试题所属大类:法制、保密等';
comment on column TJXM.XXKH_QUESTION_INFO.NODE_ID
  is '试题所属具体类别：法制-物权法、法制-劳动法等，对应树的唯一标识';
comment on column TJXM.XXKH_QUESTION_INFO.DIFFICULTY_LEVEL
  is '1：简单；2：一般；3：困难';
comment on column TJXM.XXKH_QUESTION_INFO.QUESTION_TYPE
  is '1：单选；2：多选；3：判断；4：填空；5：问答';
comment on column TJXM.XXKH_QUESTION_INFO.DESCRIBE
  is '试题描述';
comment on column TJXM.XXKH_QUESTION_INFO.ANALYSIS
  is '答案解析';
comment on column TJXM.XXKH_QUESTION_INFO.STATE
  is '0：草稿；1：发布';
alter table TJXM.XXKH_QUESTION_INFO
  add constraint PK_XXKH_QUESTION_INFO primary key (ID)
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


create table TJXM.XXKH_QUESTION_QGROUP
(
  ID                VARCHAR2(50 CHAR) not null,
  QUESTION_ID       VARCHAR2(50 CHAR),
  QUESTION_GROUP_ID VARCHAR2(50 CHAR),
  CRE_USER_ID       VARCHAR2(50 CHAR),
  CRE_USER_NAME     VARCHAR2(50 CHAR),
  CRE_DEPT_ID       VARCHAR2(50 CHAR),
  CRE_DEPT_NAME     VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID     VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME   VARCHAR2(50 CHAR),
  CRE_JU_ID         VARCHAR2(50 CHAR),
  CRE_JU_NAME       VARCHAR2(50 CHAR),
  VISIBLE           VARCHAR2(1 CHAR),
  CRE_TIME          VARCHAR2(30 CHAR)
);
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
comment on table TJXM.XXKH_QUESTION_QGROUP
  is '学习考核模块：试题与试题组关联表
xxkh：学习考核模块表名前缀';
comment on column TJXM.XXKH_QUESTION_QGROUP.ID
  is '主键id';
comment on column TJXM.XXKH_QUESTION_QGROUP.QUESTION_ID
  is '试题id';
comment on column TJXM.XXKH_QUESTION_QGROUP.QUESTION_GROUP_ID
  is '试题组id';
comment on column TJXM.XXKH_QUESTION_QGROUP.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_QUESTION_QGROUP.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_QUESTION_QGROUP.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_QUESTION_QGROUP.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.XXKH_QUESTION_QGROUP.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.XXKH_QUESTION_QGROUP.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_QUESTION_QGROUP.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.XXKH_QUESTION_QGROUP.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_QUESTION_QGROUP.VISIBLE
  is '逻辑删除';
comment on column TJXM.XXKH_QUESTION_QGROUP.CRE_TIME
  is '创建时间';
alter table TJXM.XXKH_QUESTION_QGROUP
  add constraint XXKH_QUESTION_QGROUP primary key (ID)
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


create table TJXM.XXKH_TEST_INFO
(
  ID                 VARCHAR2(50 CHAR) not null,
  CRE_USER_NAME      VARCHAR2(50 CHAR),
  CRE_USER_ID        VARCHAR2(50 CHAR),
  CRE_DEPT_NAME      VARCHAR2(50 CHAR),
  CRE_DEPT_ID        VARCHAR2(50 CHAR),
  CRE_JU_ID          VARCHAR2(50 CHAR),
  CRE_JU_NAME        VARCHAR2(50 CHAR),
  CRE_TIME           VARCHAR2(30 CHAR),
  UPDATE_USER_NAME   VARCHAR2(50 CHAR),
  UPDATE_USER_ID     VARCHAR2(50 CHAR),
  UPDATE_TIME        VARCHAR2(50 CHAR),
  VISIBLE            VARCHAR2(1 CHAR),
  NAME               VARCHAR2(500 CHAR),
  TYPE               VARCHAR2(50 CHAR),
  NODE_ID            VARCHAR2(50 CHAR),
  ANSWER_TIME        VARCHAR2(50 CHAR),
  DIFFICULTY_LEVEL   VARCHAR2(1 CHAR),
  FULL_SCORE         VARCHAR2(50 CHAR),
  PASS_SCORE         VARCHAR2(50 CHAR),
  START_TIME         VARCHAR2(30 CHAR),
  END_TIME           VARCHAR2(30 CHAR),
  TEST_TO_DEPARTMENT VARCHAR2(1 CHAR),
  IS_MORE_CHANCE     VARCHAR2(1 CHAR),
  IS_SHOW_ANSWER     VARCHAR2(1 CHAR),
  ARTIFICIAL_MARKING VARCHAR2(1 CHAR),
  ANSWER_RANDOM      VARCHAR2(1 CHAR),
  TEST_NOTICE        VARCHAR2(1000 CHAR),
  TEST_CHU_SHI_NAMES VARCHAR2(2000 CHAR),
  TEST_CHU_SHI_IDS   VARCHAR2(4000 CHAR),
  DUTY_IDS           VARCHAR2(100 CHAR),
  LEVEL_IDS          VARCHAR2(100 CHAR),
  REQUIREMENT        VARCHAR2(1000 CHAR),
  MARK_STATUS        VARCHAR2(1 CHAR),
  STATE              VARCHAR2(1 CHAR)
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
comment on table TJXM.XXKH_TEST_INFO
  is '学习考核模块：考试管理信息表
xxkh：学习考核模块表名前缀';
comment on column TJXM.XXKH_TEST_INFO.ID
  is '主键id';
comment on column TJXM.XXKH_TEST_INFO.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_TEST_INFO.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_TEST_INFO.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column TJXM.XXKH_TEST_INFO.CRE_DEPT_ID
  is '创建人部门id';
comment on column TJXM.XXKH_TEST_INFO.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.XXKH_TEST_INFO.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_TEST_INFO.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_TEST_INFO.UPDATE_USER_NAME
  is '最后更新人姓名';
comment on column TJXM.XXKH_TEST_INFO.UPDATE_USER_ID
  is '最后更新人id';
comment on column TJXM.XXKH_TEST_INFO.UPDATE_TIME
  is '最后更新时间';
comment on column TJXM.XXKH_TEST_INFO.VISIBLE
  is '0：已删除；1：未删除';
comment on column TJXM.XXKH_TEST_INFO.NAME
  is '考试名称';
comment on column TJXM.XXKH_TEST_INFO.TYPE
  is '考试大类：法制、保密、政治理论、部门';
comment on column TJXM.XXKH_TEST_INFO.NODE_ID
  is '所属小类的唯一标识';
comment on column TJXM.XXKH_TEST_INFO.ANSWER_TIME
  is '当前试卷要求的答题时长(分钟)';
comment on column TJXM.XXKH_TEST_INFO.DIFFICULTY_LEVEL
  is '1：简单；2：一般；3：困难';
comment on column TJXM.XXKH_TEST_INFO.FULL_SCORE
  is '满分分数';
comment on column TJXM.XXKH_TEST_INFO.PASS_SCORE
  is '及格分数';
comment on column TJXM.XXKH_TEST_INFO.START_TIME
  is '考试开始时间(YYYY-MM-DD)';
comment on column TJXM.XXKH_TEST_INFO.END_TIME
  is '考试结束时间(YYYY-MM-DD)';
comment on column TJXM.XXKH_TEST_INFO.TEST_TO_DEPARTMENT
  is '0：否；1：是';
comment on column TJXM.XXKH_TEST_INFO.IS_MORE_CHANCE
  is '0：否；1；是';
comment on column TJXM.XXKH_TEST_INFO.IS_SHOW_ANSWER
  is '0：否；1：是';
comment on column TJXM.XXKH_TEST_INFO.ARTIFICIAL_MARKING
  is '0：否；1：是';
comment on column TJXM.XXKH_TEST_INFO.ANSWER_RANDOM
  is '0：否；1：是';
comment on column TJXM.XXKH_TEST_INFO.TEST_NOTICE
  is '考试须知';
comment on column TJXM.XXKH_TEST_INFO.TEST_CHU_SHI_NAMES
  is '参加考试处室，逗号分隔';
comment on column TJXM.XXKH_TEST_INFO.TEST_CHU_SHI_IDS
  is '参加考试处室，逗号分隔';
comment on column TJXM.XXKH_TEST_INFO.DUTY_IDS
  is '不需要上报考试人员时，需要选择处室，并勾选人员职务';
comment on column TJXM.XXKH_TEST_INFO.LEVEL_IDS
  is '不需要上报考试人员时，需要选择人员职级';
comment on column TJXM.XXKH_TEST_INFO.REQUIREMENT
  is '人员要求';
comment on column TJXM.XXKH_TEST_INFO.MARK_STATUS
  is '0：未评卷；1：评卷中；2：已评卷';
comment on column TJXM.XXKH_TEST_INFO.STATE
  is '0：草稿；1：已提交；2：已发布';
alter table TJXM.XXKH_TEST_INFO
  add constraint PK_XXKH_TEST_INFO primary key (ID)
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


create table TJXM.XXKH_TEST_PAPER
(
  ID              VARCHAR2(50 CHAR),
  TEST_ID         VARCHAR2(50 CHAR),
  PAPER_ID        VARCHAR2(50 CHAR),
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
  SORT            VARCHAR2(2 CHAR)
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
comment on table TJXM.XXKH_TEST_PAPER
  is '学习考核：考试与试卷关联表
xxkh:学习考核模块数据库表前缀';
comment on column TJXM.XXKH_TEST_PAPER.ID
  is '主键id';
comment on column TJXM.XXKH_TEST_PAPER.TEST_ID
  is '考试id';
comment on column TJXM.XXKH_TEST_PAPER.PAPER_ID
  is '试卷id';
comment on column TJXM.XXKH_TEST_PAPER.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_TEST_PAPER.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_TEST_PAPER.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_TEST_PAPER.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.XXKH_TEST_PAPER.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.XXKH_TEST_PAPER.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_TEST_PAPER.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.XXKH_TEST_PAPER.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_TEST_PAPER.VISIBLE
  is '逻辑删除';
comment on column TJXM.XXKH_TEST_PAPER.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_TEST_PAPER.SORT
  is '试卷顺序：1、2、3、4...';


create table TJXM.XXKH_TREE
(
  ID               VARCHAR2(50 CHAR) not null,
  PID              VARCHAR2(50 CHAR),
  NAME             VARCHAR2(50 CHAR),
  CODE             VARCHAR2(50 CHAR),
  FLAG             VARCHAR2(2 CHAR),
  VISIBLE          VARCHAR2(1 CHAR),
  SORT             NUMBER,
  TYPE             VARCHAR2(1 CHAR),
  CRE_TIME         VARCHAR2(30 CHAR),
  UPDATE_TIME      VARCHAR2(30 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  TREE_TYPE        VARCHAR2(50 CHAR),
  REMARK           VARCHAR2(2000 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR)
);
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
comment on column TJXM.XXKH_TREE.ID
  is 'ID';
comment on column TJXM.XXKH_TREE.PID
  is '父ID';
comment on column TJXM.XXKH_TREE.NAME
  is '字典名称';
comment on column TJXM.XXKH_TREE.CODE
  is '在音视频系统中的分类id';
comment on column TJXM.XXKH_TREE.FLAG
  is '在音视频系统中的ClassLayer，即层级（1、2、3...）';
comment on column TJXM.XXKH_TREE.VISIBLE
  is '删除标识（1可用；0删除）';
comment on column TJXM.XXKH_TREE.SORT
  is '排序号';
comment on column TJXM.XXKH_TREE.TYPE
  is '类型（0字典类型；1字典项）';
comment on column TJXM.XXKH_TREE.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_TREE.UPDATE_TIME
  is '最近更新时间';
comment on column TJXM.XXKH_TREE.UPDATE_USER_ID
  is '最近更新人ID';
comment on column TJXM.XXKH_TREE.UPDATE_USER_NAME
  is '最近更新人姓名';
comment on column TJXM.XXKH_TREE.TREE_TYPE
  is '树类别';
comment on column TJXM.XXKH_TREE.REMARK
  is '备注';
comment on column TJXM.XXKH_TREE.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_TREE.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_TREE.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_TREE.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column TJXM.XXKH_TREE.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_TREE.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.XXKH_TREE.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.XXKH_TREE.CRE_JU_NAME
  is '创建人二级局名';
alter table TJXM.XXKH_TREE
  add constraint ID primary key (ID)
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


create table TJXM.XXKH_USER_PAPER
(
  ID                       VARCHAR2(50 CHAR) not null,
  TEST_ID                  VARCHAR2(50 CHAR),
  PAPER_ID                 VARCHAR2(50 CHAR),
  TEST_NAME                VARCHAR2(500 CHAR),
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
  USER_ID                  VARCHAR2(50 CHAR),
  USER_NAME                VARCHAR2(50 CHAR),
  PAPER_ORDER              VARCHAR2(2 CHAR),
  IS_SUBMIT                VARCHAR2(1 CHAR),
  SUBMIT_TIME              VARCHAR2(50 CHAR),
  BEGIN_TEST_TIME          VARCHAR2(50 CHAR),
  ANSWER_TIME              VARCHAR2(50 CHAR),
  CANDIDATE_DEPT_ID        VARCHAR2(50 CHAR),
  CANDIDATE_DEPT_NAME      VARCHAR2(50 CHAR),
  CANDIDATE_CHUSHI_ID      VARCHAR2(50 CHAR),
  CANDIDATE_CHUSHI_NAME    VARCHAR2(50 CHAR),
  CANDIDATE_JU_ID          VARCHAR2(50 CHAR),
  CANDIDATE_JU_NAME        VARCHAR2(50 CHAR),
  CANDIDATE_DUTY           VARCHAR2(50 CHAR),
  CANDIDATE_LEVEL          VARCHAR2(50 CHAR),
  USER_SEX                 VARCHAR2(10),
  PHONE                    VARCHAR2(50),
  CANDIDATE_DUTY_NAME      VARCHAR2(50),
  CANDIDATE_LEVEL_NAME     VARCHAR2(50),
  PAPER_SUBJECTIVE_SCORE   VARCHAR2(50 CHAR),
  PAPER_OBJECTIVE_SCORE    VARCHAR2(50 CHAR),
  ARTIFICIAL_MARKING_STATE VARCHAR2(1 CHAR)
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
comment on table TJXM.XXKH_USER_PAPER
  is '学习考核：人员-试卷关联表
xxkh:学习考核数据库表名前缀';
comment on column TJXM.XXKH_USER_PAPER.ID
  is '主键id';
comment on column TJXM.XXKH_USER_PAPER.TEST_ID
  is '考试id';
comment on column TJXM.XXKH_USER_PAPER.PAPER_ID
  is '试卷id';
comment on column TJXM.XXKH_USER_PAPER.TEST_NAME
  is '考试名称';
comment on column TJXM.XXKH_USER_PAPER.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_USER_PAPER.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_USER_PAPER.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_USER_PAPER.CRE_DEPT_NAME
  is '创建人部门名';
comment on column TJXM.XXKH_USER_PAPER.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column TJXM.XXKH_USER_PAPER.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_USER_PAPER.CRE_JU_ID
  is '创建人二级局ID';
comment on column TJXM.XXKH_USER_PAPER.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_USER_PAPER.VISIBLE
  is '0：已删除；1：未删除';
comment on column TJXM.XXKH_USER_PAPER.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_USER_PAPER.USER_ID
  is '用户id';
comment on column TJXM.XXKH_USER_PAPER.USER_NAME
  is '用户姓名';
comment on column TJXM.XXKH_USER_PAPER.PAPER_ORDER
  is '试卷顺序：1、2、3...';
comment on column TJXM.XXKH_USER_PAPER.IS_SUBMIT
  is '是否已交卷，0：未交卷，1：已交卷';
comment on column TJXM.XXKH_USER_PAPER.SUBMIT_TIME
  is '交卷时间';
comment on column TJXM.XXKH_USER_PAPER.BEGIN_TEST_TIME
  is '首次进入答题时间，用于判断';
comment on column TJXM.XXKH_USER_PAPER.ANSWER_TIME
  is '当前试卷要求的答题时长，用于判断是否还能再次答题';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_DEPT_ID
  is '考生所在部门id';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_DEPT_NAME
  is '考生所在部门name';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_CHUSHI_ID
  is '考生所在处室id';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_CHUSHI_NAME
  is '考生所在处室name';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_JU_ID
  is '考生所在二级局id';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_JU_NAME
  is '考生所在二级局name';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_DUTY
  is '考生职务';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_LEVEL
  is '考生职级';
comment on column TJXM.XXKH_USER_PAPER.USER_SEX
  is '性别：0男1女';
comment on column TJXM.XXKH_USER_PAPER.PHONE
  is '手机号';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_DUTY_NAME
  is '职务全名称';
comment on column TJXM.XXKH_USER_PAPER.CANDIDATE_LEVEL_NAME
  is '职级全名称';
comment on column TJXM.XXKH_USER_PAPER.PAPER_SUBJECTIVE_SCORE
  is '试卷主观题得分';
comment on column TJXM.XXKH_USER_PAPER.PAPER_OBJECTIVE_SCORE
  is '试卷客观题得分';
comment on column TJXM.XXKH_USER_PAPER.ARTIFICIAL_MARKING_STATE
  is '人工评卷状态，0：暂存或未评卷，1：已人工评卷完毕';


create table TJXM.XXKH_VIDEO_AND_PDF
(
  ID              VARCHAR2(50 CHAR) not null,
  INFO_ID         VARCHAR2(50 CHAR),
  CRE_USER_NAME   VARCHAR2(50 CHAR),
  CRE_TIME        VARCHAR2(30 CHAR),
  CRE_DEPT_ID     VARCHAR2(50 CHAR),
  CRE_DEPT_NAME   VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID   VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME VARCHAR2(50 CHAR),
  CRE_JU_ID       VARCHAR2(50 CHAR),
  CRE_JU_NAME     VARCHAR2(50 CHAR),
  FILE_NAME       VARCHAR2(500 CHAR),
  FILE_ID         VARCHAR2(100 CHAR),
  FILE_TYPE       VARCHAR2(1 CHAR),
  VISIBLE         VARCHAR2(1 CHAR),
  CRE_USER_ID     VARCHAR2(50 CHAR),
  FILE_ID_NUM     VARCHAR2(50 CHAR)
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
comment on column TJXM.XXKH_VIDEO_AND_PDF.ID
  is '主键id';
comment on column TJXM.XXKH_VIDEO_AND_PDF.INFO_ID
  is '资料id';
comment on column TJXM.XXKH_VIDEO_AND_PDF.CRE_USER_NAME
  is '创建人姓名';
comment on column TJXM.XXKH_VIDEO_AND_PDF.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_VIDEO_AND_PDF.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_VIDEO_AND_PDF.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column TJXM.XXKH_VIDEO_AND_PDF.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.XXKH_VIDEO_AND_PDF.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_VIDEO_AND_PDF.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.XXKH_VIDEO_AND_PDF.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_VIDEO_AND_PDF.FILE_NAME
  is '文件名称（带后缀）';
comment on column TJXM.XXKH_VIDEO_AND_PDF.FILE_ID
  is '文件在音视频中uuid（目前是获得播放地址用）';
comment on column TJXM.XXKH_VIDEO_AND_PDF.FILE_TYPE
  is '文件类型，0：视频，1：文档';
comment on column TJXM.XXKH_VIDEO_AND_PDF.VISIBLE
  is '删除标识（1可用；0删除）';
comment on column TJXM.XXKH_VIDEO_AND_PDF.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_VIDEO_AND_PDF.FILE_ID_NUM
  is '文件在音视频中id（目前是删除用）';
alter table TJXM.XXKH_VIDEO_AND_PDF
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


create table TJXM.XXKH_ZI_LIAO
(
  ID                    VARCHAR2(50 CHAR) not null,
  NODE_ID               VARCHAR2(50 CHAR),
  DATA_NAME             VARCHAR2(50 CHAR),
  VIDEO                 VARCHAR2(50 CHAR),
  TYPE                  VARCHAR2(50 CHAR),
  CRE_DEPT_ID           VARCHAR2(50 CHAR),
  CRE_DEPT_NAME         VARCHAR2(50 CHAR),
  UPDATE_TIME           VARCHAR2(30 CHAR),
  UPDATE_USER_ID        VARCHAR2(50 CHAR),
  UPDATE_USER_NAME      VARCHAR2(50 CHAR),
  CONTEXT               VARCHAR2(2000 CHAR),
  REMARK                VARCHAR2(2000 CHAR),
  CRE_CHUSHI_NAME       VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID         VARCHAR2(50 CHAR),
  CRE_JU_ID             VARCHAR2(50 CHAR),
  CRE_JU_NAME           VARCHAR2(50 CHAR),
  CRE_USER_NAME         VARCHAR2(50 CHAR),
  CRE_TIME              VARCHAR2(50 CHAR),
  CRE_USER_ID           VARCHAR2(50 CHAR),
  VISIBLE               VARCHAR2(30 CHAR),
  SORT                  NUMBER,
  SHARE_TIME            VARCHAR2(50 CHAR),
  SHARE_DEPT            VARCHAR2(200 CHAR),
  SUBFLAG               VARCHAR2(50 CHAR),
  IS_WILL_LEARN         VARCHAR2(2 CHAR),
  MINIMUM_LEARNING_TIME VARCHAR2(50 CHAR),
  LATEST_STUDY_DATE     VARCHAR2(30 CHAR)
);
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
comment on column TJXM.XXKH_ZI_LIAO.ID
  is 'id';
comment on column TJXM.XXKH_ZI_LIAO.NODE_ID
  is '唯一标示';
comment on column TJXM.XXKH_ZI_LIAO.DATA_NAME
  is '资料名称';
comment on column TJXM.XXKH_ZI_LIAO.VIDEO
  is '视频';
comment on column TJXM.XXKH_ZI_LIAO.TYPE
  is '归属类';
comment on column TJXM.XXKH_ZI_LIAO.CRE_DEPT_ID
  is '创建人部门ID';
comment on column TJXM.XXKH_ZI_LIAO.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column TJXM.XXKH_ZI_LIAO.UPDATE_TIME
  is '最近更新时间';
comment on column TJXM.XXKH_ZI_LIAO.UPDATE_USER_ID
  is '最近更新人ID';
comment on column TJXM.XXKH_ZI_LIAO.UPDATE_USER_NAME
  is '最近更新人姓名';
comment on column TJXM.XXKH_ZI_LIAO.CONTEXT
  is '内容';
comment on column TJXM.XXKH_ZI_LIAO.REMARK
  is '备注';
comment on column TJXM.XXKH_ZI_LIAO.CRE_CHUSHI_NAME
  is '创建人处室名';
comment on column TJXM.XXKH_ZI_LIAO.CRE_CHUSHI_ID
  is '创建人处室id';
comment on column TJXM.XXKH_ZI_LIAO.CRE_JU_ID
  is '创建人二级局id';
comment on column TJXM.XXKH_ZI_LIAO.CRE_JU_NAME
  is '创建人二级局名';
comment on column TJXM.XXKH_ZI_LIAO.CRE_USER_NAME
  is '创建人';
comment on column TJXM.XXKH_ZI_LIAO.CRE_TIME
  is '创建时间';
comment on column TJXM.XXKH_ZI_LIAO.CRE_USER_ID
  is '创建人id';
comment on column TJXM.XXKH_ZI_LIAO.VISIBLE
  is '删除标识（1可用；0删除）';
comment on column TJXM.XXKH_ZI_LIAO.SORT
  is '排序号';
comment on column TJXM.XXKH_ZI_LIAO.SHARE_TIME
  is '共享时间';
comment on column TJXM.XXKH_ZI_LIAO.SHARE_DEPT
  is '共享部门';
comment on column TJXM.XXKH_ZI_LIAO.SUBFLAG
  is '是否可用（0不可用，1可用）';
comment on column TJXM.XXKH_ZI_LIAO.IS_WILL_LEARN
  is '是否必学（0不必学1必学）';
comment on column TJXM.XXKH_ZI_LIAO.MINIMUM_LEARNING_TIME
  is '最少学习时长';
comment on column TJXM.XXKH_ZI_LIAO.LATEST_STUDY_DATE
  is '最晚学习日期';

create or replace view xxkh_type_info_view as
select dd.id,dd.pid,dd.name,dd.type,dd.tree_type,dd.cre_time from xxkh_tree dd where dd.visible='1' union
select dt.id,dt.node_id as pid,dt.data_name as name,'1' as type,dt.type as tree_type,dt.cre_time from xxkh_zi_liao dt where dt.visible='1' and dt.subflag='1';



