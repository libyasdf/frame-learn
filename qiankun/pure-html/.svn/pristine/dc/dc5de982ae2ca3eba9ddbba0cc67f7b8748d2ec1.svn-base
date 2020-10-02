-- Create table
create table TABLE_DESCRIPTION
(
  table_name      VARCHAR2(50 CHAR) not null,
  table_auxi_name VARCHAR2(50 CHAR) not null,
  table_chn_name  VARCHAR2(50 CHAR),
  table_data_type VARCHAR2(25 CHAR),
  table_sys_type  VARCHAR2(20 CHAR),
  table_aggregate VARCHAR2(20 CHAR),
  note            VARCHAR2(255 CHAR)
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
comment on table TABLE_DESCRIPTION
  is '这里存放了门类-案卷-卷内-文件等表的数据。';
-- Add comments to the columns 
comment on column TABLE_DESCRIPTION.table_name
  is '表的英文名（主键）';
comment on column TABLE_DESCRIPTION.table_auxi_name
  is '表助记名（中文名）';
comment on column TABLE_DESCRIPTION.table_chn_name
  is '新中文名:中文简称';
comment on column TABLE_DESCRIPTION.table_data_type
  is '对应模版：指的是生成表的时候使用的哪个表作为模版生成的此表';
comment on column TABLE_DESCRIPTION.table_sys_type
  is '表类型：可分为D或者S；D代表档案（常用）';
comment on column TABLE_DESCRIPTION.note
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TABLE_DESCRIPTION
  add constraint PK_TABLE_DESCRIPTION primary key (TABLE_NAME)
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

-- Create table
create table TABLES_RELATION
(
  m_table_name   VARCHAR2(50 CHAR),
  s_table_name   VARCHAR2(50 CHAR) not null,
  relation_no    NUMBER,
  relation_type  VARCHAR2(1 CHAR),
  m_col_name     VARCHAR2(50 CHAR),
  s_col_name     VARCHAR2(50 CHAR),
  rel_can_change VARCHAR2(1 CHAR),
  note           VARCHAR2(255 CHAR)
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
comment on table TABLES_RELATION
  is '这里描述的是表根表之间的关系';
-- Add comments to the columns 
comment on column TABLES_RELATION.m_table_name
  is '父表表名';
comment on column TABLES_RELATION.s_table_name
  is '子表表名';
comment on column TABLES_RELATION.relation_no
  is '关联关系数,父表、子表有几个字段有关联';
comment on column TABLES_RELATION.relation_type
  is '关联类型,全关联，不能修改；猜测：A是all的简写；';
comment on column TABLES_RELATION.m_col_name
  is '父表字段';
comment on column TABLES_RELATION.s_col_name
  is '子表字段';
comment on column TABLES_RELATION.rel_can_change
  is '关联关系是否可修改；F：不可修改；T：可修改';
comment on column TABLES_RELATION.note
  is '备注';

-- Create table
create table TABLE_STRUCT_DESCRIPTION
(
  table_name           VARCHAR2(50 CHAR) not null,
  table_chn_name       VARCHAR2(50 CHAR),
  column_name          VARCHAR2(50 CHAR) not null,
  column_order         NUMBER,
  column_chn_name      VARCHAR2(50 CHAR),
  column_class         NUMBER,
  column_type          VARCHAR2(10 CHAR),
  column_width         NUMBER,
  column_point         NUMBER,
  column_is_key        VARCHAR2(1 CHAR),
  column_index_name    VARCHAR2(50 CHAR),
  column_can_null      VARCHAR2(1 CHAR),
  column_can_repeat    VARCHAR2(1 CHAR),
  column_visible       VARCHAR2(1 CHAR),
  column_format        VARCHAR2(50 CHAR),
  column_value_scale   VARCHAR2(255 CHAR),
  column_value_default VARCHAR2(255 CHAR),
  column_as_secret     VARCHAR2(1 CHAR),
  column_inherit       VARCHAR2(1 CHAR),
  column_auto_add      VARCHAR2(1 CHAR),
  column_input_type    VARCHAR2(1 CHAR),
  column_select_no     VARCHAR2(25 CHAR),
  note                 VARCHAR2(255 CHAR),
  column_as_ctg_query  VARCHAR2(1 CHAR),
  column_as_simquery   VARCHAR2(1 CHAR)
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
comment on table TABLE_STRUCT_DESCRIPTION
  is '这里存的是表字段的一些信息';
-- Add comments to the columns 
comment on column TABLE_STRUCT_DESCRIPTION.table_name
  is '表名,与table_description表的table_name为主外建关系';
comment on column TABLE_STRUCT_DESCRIPTION.table_chn_name
  is '中文名';
comment on column TABLE_STRUCT_DESCRIPTION.column_name
  is '字段名称';
comment on column TABLE_STRUCT_DESCRIPTION.column_order
  is '字段顺序 number';
comment on column TABLE_STRUCT_DESCRIPTION.column_chn_name
  is '字段中文名';
comment on column TABLE_STRUCT_DESCRIPTION.column_class
  is '1/2/3/5：number系统级字段，用户不可删除，1用来标记主键，其余什么区别未知；
4：用户新增字段，可以进行修改删除操作；';
comment on column TABLE_STRUCT_DESCRIPTION.column_type
  is '字段类型:1:字符型；2：整数型；3：数字型；4：时间型；5：日期；6.树';
comment on column TABLE_STRUCT_DESCRIPTION.column_width
  is '字段长度number 4';
comment on column TABLE_STRUCT_DESCRIPTION.column_point
  is '只有当column_type为数字型（3）的时候，此项才可设置 number 4';
comment on column TABLE_STRUCT_DESCRIPTION.column_is_key
  is '是否为主键;T：代表true；F：代表false；';
comment on column TABLE_STRUCT_DESCRIPTION.column_index_name
  is '索引列名称';
comment on column TABLE_STRUCT_DESCRIPTION.column_can_null
  is '字段是否可为空;T：代表true；F：代表false；
';
comment on column TABLE_STRUCT_DESCRIPTION.column_can_repeat
  is 'T：代表true；F：代表false；
该项字段是否允许有重复的数值';
comment on column TABLE_STRUCT_DESCRIPTION.column_visible
  is 'T：代表true；F：代表false；
理论上，只有主键字段值为F不可见；';
comment on column TABLE_STRUCT_DESCRIPTION.column_value_default
  is '默认值';
comment on column TABLE_STRUCT_DESCRIPTION.column_as_secret
  is '暂不需要';
comment on column TABLE_STRUCT_DESCRIPTION.column_inherit
  is '在添加数据时该字段自动继承上一条记录的数值';
comment on column TABLE_STRUCT_DESCRIPTION.column_auto_add
  is 'T：代表true；F：代表false；';
comment on column TABLE_STRUCT_DESCRIPTION.column_input_type
  is 'T：文本输入；S：选择输入';
comment on column TABLE_STRUCT_DESCRIPTION.column_select_no
  is '文本输入为S时指定所依赖的字典';
comment on column TABLE_STRUCT_DESCRIPTION.note
  is '备注';
comment on column TABLE_STRUCT_DESCRIPTION.column_as_ctg_query
  is '是否为分类查询字段';
comment on column TABLE_STRUCT_DESCRIPTION.column_as_simquery
  is '是否为简单查询字段';
