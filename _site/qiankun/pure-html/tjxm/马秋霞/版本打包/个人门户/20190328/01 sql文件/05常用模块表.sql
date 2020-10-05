--正式环境中先备份常用模块的表,然后再执行下面的删除和新建表的sql
create table often_model_20190515 as select * from often_model;
-- 删除常用模块表
 drop table OFTEN_MODEL;

-- Create table
 create table OFTEN_MODEL
 (
   id            VARCHAR2(50 CHAR) not null,
   cre_user_id   VARCHAR2(50 CHAR),
   cre_user_name VARCHAR2(50 CHAR),
   cre_dept_id   VARCHAR2(50 CHAR),
   cre_dept_name VARCHAR2(50 CHAR),
   cre_time      VARCHAR2(30 CHAR),
   resource_id   VARCHAR2(50 CHAR),
   sort          NUMBER(10),
   resource_name VARCHAR2(50 CHAR),
   ppid          VARCHAR2(50 CHAR)
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
 -- Add comments to the columns
 comment on column OFTEN_MODEL.id
   is '主键id';
 comment on column OFTEN_MODEL.cre_user_id
   is '创建人用户id';
 comment on column OFTEN_MODEL.cre_user_name
   is '创建人用户名';
 comment on column OFTEN_MODEL.cre_dept_id
   is '创建人部门id';
 comment on column OFTEN_MODEL.cre_dept_name
   is '创建人部门名';
 comment on column OFTEN_MODEL.cre_time
   is '创建时间';
 comment on column OFTEN_MODEL.resource_id
   is '资源id';
 comment on column OFTEN_MODEL.sort
   is '排序';
 comment on column OFTEN_MODEL.resource_name
   is '常用模块名称';
 -- Create/Recreate primary, unique and foreign key constraints
 alter table OFTEN_MODEL
   add constraint PK_OFTEN_MODULE primary key (ID)
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