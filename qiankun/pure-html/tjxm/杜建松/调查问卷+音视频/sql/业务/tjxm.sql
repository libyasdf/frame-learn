--调查问卷业务表
-- Create table
create table SURVEY_ENTIY
(
  id               VARCHAR2(50 CHAR) not null,
  cre_user_id      VARCHAR2(50 CHAR),
  cre_user_name    VARCHAR2(50 CHAR),
  cre_dept_id      VARCHAR2(50 CHAR),
  cre_dept_name    VARCHAR2(50 CHAR),
  cre_chushi_id    VARCHAR2(50 CHAR),
  cre_chushi_name  VARCHAR2(50 CHAR),
  cre_ju_id        VARCHAR2(50 CHAR),
  cre_ju_name      VARCHAR2(50 CHAR),
  cre_time         VARCHAR2(50 CHAR),
  update_user_id   VARCHAR2(50 CHAR),
  update_user_name VARCHAR2(50 CHAR),
  update_time      VARCHAR2(30 CHAR),
  status           VARCHAR2(1 CHAR),
  title            VARCHAR2(100 CHAR),
  content          VARCHAR2(2000 CHAR),
  subflag          VARCHAR2(1 CHAR),
  visible          VARCHAR2(1 CHAR),
  publish_time     VARCHAR2(30 CHAR),
  wenjuan_deptid   VARCHAR2(30 CHAR),
  wenjuantitle     VARCHAR2(100 CHAR),
  surveyid         VARCHAR2(50 CHAR)
)
tablespace PLATFORM
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
comment on table SURVEY_ENTIY
  is '问卷调查发布实体表';
-- Add comments to the columns
comment on column SURVEY_ENTIY.id
  is '主键ID';
comment on column SURVEY_ENTIY.cre_user_id
  is '创建人ID';
comment on column SURVEY_ENTIY.cre_user_name
  is '创建人name';
comment on column SURVEY_ENTIY.cre_dept_id
  is '创建人部门ID';
comment on column SURVEY_ENTIY.cre_dept_name
  is '创建人部门name';
comment on column SURVEY_ENTIY.cre_chushi_id
  is '创建人处室ID';
comment on column SURVEY_ENTIY.cre_chushi_name
  is '创建人处室name';
comment on column SURVEY_ENTIY.cre_ju_id
  is '创建人局ID';
comment on column SURVEY_ENTIY.cre_ju_name
  is '创建人局name';
comment on column SURVEY_ENTIY.cre_time
  is '创建时间';
comment on column SURVEY_ENTIY.update_user_id
  is '最近修改人ID';
comment on column SURVEY_ENTIY.update_user_name
  is '最近修改人name';
comment on column SURVEY_ENTIY.update_time
  is '最近修改时间';
comment on column SURVEY_ENTIY.status
  is '是否已选择发布范围（0未选，1已选）';
comment on column SURVEY_ENTIY.title
  is '标题';
comment on column SURVEY_ENTIY.content
  is '内容';
comment on column SURVEY_ENTIY.subflag
  is '状态（0草稿；1流程中；2撤销；5发布；6未通过）';
comment on column SURVEY_ENTIY.visible
  is '删除状态';
comment on column SURVEY_ENTIY.publish_time
  is '发布时间';
comment on column SURVEY_ENTIY.wenjuan_deptid
  is '二级局或局级id';
comment on column SURVEY_ENTIY.wenjuantitle
  is '工作流标题';
comment on column SURVEY_ENTIY.surveyid
  is '调查问卷id';
-- Create/Recreate primary, unique and foreign key constraints
alter table SURVEY_ENTIY
  add constraint PK_SURVEY_ENTIY primary key (ID)
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


--调查问卷设计表
alter table t_survey_directory add DEPT_STATE VARCHAR2(50);
alter table t_survey_directory add SUBFLAG VARCHAR2(1);