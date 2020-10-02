-- Create table
create table VIDEO_COLUMN
(
  id                   VARCHAR2(50) not null,
  cre_user_id          VARCHAR2(50),
  cre_user_name        VARCHAR2(50),
  cre_dept_id          VARCHAR2(50),
  cre_dept_name        VARCHAR2(50),
  cre_chushi_id        VARCHAR2(50),
  cre_chushi_name      VARCHAR2(50),
  cre_ju_id            VARCHAR2(50),
  cre_ju_name          VARCHAR2(50),
  visible              VARCHAR2(1),
  cre_time             VARCHAR2(30),
  update_user_id       VARCHAR2(50),
  update_user_name     VARCHAR2(50),
  update_time          VARCHAR2(30),
  super_id             CHAR(50),
  node_level           CHAR(50),
  column_name          VARCHAR2(200),
  column_code          VARCHAR2(50),
  column_icon          VARCHAR2(200),
  column_remark        VARCHAR2(500),
  is_show              VARCHAR2(1),
  is_sp                VARCHAR2(1),
  is_fbfw              VARCHAR2(1),
  column_gl_user_ids   VARCHAR2(200),
  column_gl_user_names VARCHAR2(200),
  order_no             VARCHAR2(10),
  code                 VARCHAR2(50),
  is_first             VARCHAR2(1)
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
comment on table VIDEO_COLUMN
  is '信息发布栏目表';
-- Add comments to the columns 
comment on column VIDEO_COLUMN.id
  is '主键id';
comment on column VIDEO_COLUMN.cre_user_id
  is '创建人id';
comment on column VIDEO_COLUMN.cre_user_name
  is '创建人name';
comment on column VIDEO_COLUMN.cre_dept_id
  is '创建人部门id';
comment on column VIDEO_COLUMN.cre_dept_name
  is '创建人部门名';
comment on column VIDEO_COLUMN.cre_chushi_id
  is '创建人处室id';
comment on column VIDEO_COLUMN.cre_chushi_name
  is '创建人处室名';
comment on column VIDEO_COLUMN.cre_ju_id
  is '创建人二级局id';
comment on column VIDEO_COLUMN.cre_ju_name
  is '创建人二级局名';
comment on column VIDEO_COLUMN.visible
  is '逻辑删除';
comment on column VIDEO_COLUMN.cre_time
  is '创建时间';
comment on column VIDEO_COLUMN.update_user_id
  is '最后更新人id';
comment on column VIDEO_COLUMN.update_user_name
  is '最后更新人名';
comment on column VIDEO_COLUMN.update_time
  is '最后更新时间';
comment on column VIDEO_COLUMN.super_id
  is '父节点id';
comment on column VIDEO_COLUMN.node_level
  is '节点级别';
comment on column VIDEO_COLUMN.column_name
  is '栏目名称';
comment on column VIDEO_COLUMN.column_code
  is '栏目编号';
comment on column VIDEO_COLUMN.column_icon
  is '栏目图标';
comment on column VIDEO_COLUMN.column_remark
  is '栏目说明';
comment on column VIDEO_COLUMN.is_show
  is '是否在门户显示';
comment on column VIDEO_COLUMN.is_sp
  is '是否审批';
comment on column VIDEO_COLUMN.is_fbfw
  is '是否有发布范围';
comment on column VIDEO_COLUMN.column_gl_user_ids
  is '栏目管理人员ids';
comment on column VIDEO_COLUMN.column_gl_user_names
  is '栏目管理人员names';
comment on column VIDEO_COLUMN.order_no
  is '排序';
comment on column VIDEO_COLUMN.code
  is '选中类别在音视频系统中的Id';
comment on column VIDEO_COLUMN.is_first
  is '是否是根目录';
-- Create/Recreate primary, unique and foreign key constraints 
alter table VIDEO_COLUMN
  add constraint PK_VIDEO_COLUMN primary key (ID)
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
  
  
insert into video_column (ID, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, CRE_CHUSHI_ID, CRE_CHUSHI_NAME, CRE_JU_ID, CRE_JU_NAME, VISIBLE, CRE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME, UPDATE_TIME, SUPER_ID, NODE_LEVEL, COLUMN_NAME, COLUMN_CODE, COLUMN_ICON, COLUMN_REMARK, IS_SHOW, IS_SP, IS_FBFW, COLUMN_GL_USER_IDS, COLUMN_GL_USER_NAMES, ORDER_NO, CODE, IS_FIRST)
values ('4028d090674da7fa01674dafd5a90006', '1003472', '郭涛', null, null, '111220', '处级部门', '111097', '测试部（二级局）', '1', '2018-12-21 16:35', '1003472', '郭涛', '2018-12-21 16:35', '4028d090674da7fa01674dad033d0000                  ', '2                                                 ', '推荐', 'tj', null, '华东师大是否费上岛咖啡第三方第三方HD声卡费皇室典范三俯拾地芥废话第三方皇室典范很大声开发华东师范的数据开发很大声富华大厦费SD卡减肥很SD卡减肥很大声减肥号地块是富华大厦费上岛咖啡很第三款放到水井坊HD声卡减肥号打卡费花的嫁纱废话第三节课返回第三方HD声卡减肥很大声看见富华大厦会计法哈萨克废话第三方海底世界开发', '0', '1', '0', '507568', '炎琥宁', '1', '323', '0');

insert into video_column (ID, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, CRE_CHUSHI_ID, CRE_CHUSHI_NAME, CRE_JU_ID, CRE_JU_NAME, VISIBLE, CRE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME, UPDATE_TIME, SUPER_ID, NODE_LEVEL, COLUMN_NAME, COLUMN_CODE, COLUMN_ICON, COLUMN_REMARK, IS_SHOW, IS_SP, IS_FBFW, COLUMN_GL_USER_IDS, COLUMN_GL_USER_NAMES, ORDER_NO, CODE, IS_FIRST)
values ('4028d090674da7fa01674db03e700008', '1003472', '郭涛', null, null, '111220', '处级部门', '111097', '测试部（二级局）', '1', '2018-12-21 14:23', '1003472', '郭涛', '2018-12-21 14:23', '4028d090674da7fa01674dad033d0000                  ', '2                                                 ', '新闻宣传', 'xwxc', null, null, '1', '1', '1', null, null, '2', '324', '0');

insert into video_column (ID, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, CRE_CHUSHI_ID, CRE_CHUSHI_NAME, CRE_JU_ID, CRE_JU_NAME, VISIBLE, CRE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME, UPDATE_TIME, SUPER_ID, NODE_LEVEL, COLUMN_NAME, COLUMN_CODE, COLUMN_ICON, COLUMN_REMARK, IS_SHOW, IS_SP, IS_FBFW, COLUMN_GL_USER_IDS, COLUMN_GL_USER_NAMES, ORDER_NO, CODE, IS_FIRST)
values ('4028d090674da7fa01674db0d350000a', '1001012', '张三', null, null, '93255', '资金交易处', '93245', '资金局', '1', '2018-12-21 11:42', '1001012', '张三', '2018-12-21 11:42', '4028d090674da7fa01674dad033d0000                  ', '2                                                 ', '学习教育', 'xxjy', null, null, '1', '0', '0', null, null, '3', '325', '0');

insert into video_column (ID, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, CRE_CHUSHI_ID, CRE_CHUSHI_NAME, CRE_JU_ID, CRE_JU_NAME, VISIBLE, CRE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME, UPDATE_TIME, SUPER_ID, NODE_LEVEL, COLUMN_NAME, COLUMN_CODE, COLUMN_ICON, COLUMN_REMARK, IS_SHOW, IS_SP, IS_FBFW, COLUMN_GL_USER_IDS, COLUMN_GL_USER_NAMES, ORDER_NO, CODE, IS_FIRST)
values ('4028d090674da7fa01674db148d3000c', '1001012', '张三', null, null, '93255', '资金交易处', '93245', '资金局', '1', '2018-12-20 14:44', '1001012', '张三', '2018-12-20 14:44', '4028d090674da7fa01674dad033d0000                  ', '2                                                 ', '战争题材', 'zztc', null, null, '1', '0', '0', null, null, '4', '326', '0');

insert into video_column (ID, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, CRE_CHUSHI_ID, CRE_CHUSHI_NAME, CRE_JU_ID, CRE_JU_NAME, VISIBLE, CRE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME, UPDATE_TIME, SUPER_ID, NODE_LEVEL, COLUMN_NAME, COLUMN_CODE, COLUMN_ICON, COLUMN_REMARK, IS_SHOW, IS_SP, IS_FBFW, COLUMN_GL_USER_IDS, COLUMN_GL_USER_NAMES, ORDER_NO, CODE, IS_FIRST)
values ('4028d090674da7fa01674db1bd76000e', '1001012', '张三', null, null, '93255', '资金交易处', '93245', '资金局', '1', '2018-12-20 14:45', '1001012', '张三', '2018-12-20 14:45', '4028d090674da7fa01674dad033d0000                  ', '2                                                 ', '闭路频道', 'blpd', null, null, '1', '0', '0', null, null, '6', '327', '0');

insert into video_column (ID, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, CRE_CHUSHI_ID, CRE_CHUSHI_NAME, CRE_JU_ID, CRE_JU_NAME, VISIBLE, CRE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME, UPDATE_TIME, SUPER_ID, NODE_LEVEL, COLUMN_NAME, COLUMN_CODE, COLUMN_ICON, COLUMN_REMARK, IS_SHOW, IS_SP, IS_FBFW, COLUMN_GL_USER_IDS, COLUMN_GL_USER_NAMES, ORDER_NO, CODE, IS_FIRST)
values ('4028d090674da7fa01674db3e3270010', '1001012', '张三', null, null, '93255', '资金交易处', '93245', '资金局', '1', '2018-12-20 14:44', '1001012', '张三', '2018-12-20 14:44', '4028d090674da7fa01674dad033d0000                  ', '2                                                 ', '电影电视', 'dyds', null, null, '1', '0', '0', null, null, '5', '328', '0');

insert into video_column (ID, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, CRE_CHUSHI_ID, CRE_CHUSHI_NAME, CRE_JU_ID, CRE_JU_NAME, VISIBLE, CRE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME, UPDATE_TIME, SUPER_ID, NODE_LEVEL, COLUMN_NAME, COLUMN_CODE, COLUMN_ICON, COLUMN_REMARK, IS_SHOW, IS_SP, IS_FBFW, COLUMN_GL_USER_IDS, COLUMN_GL_USER_NAMES, ORDER_NO, CODE, IS_FIRST)
values ('4028d090674da7fa01674dad033d0000', '1003472', '郭涛', null, null, '111220', '处级部门', '111097', '测试部（二级局）', '1', '2018-12-21 16:25', '1003472', '郭涛', '2018-12-21 16:25', '0                                                 ', '1                                                 ', '视频门户', 'spmh', null, '1.会计法电竞盛典
2.卡的减肥的减肥卡的减肥第三方了肯定就是', '0', '0', '0', '93277', '资金交易处处长', null, '320', '1');