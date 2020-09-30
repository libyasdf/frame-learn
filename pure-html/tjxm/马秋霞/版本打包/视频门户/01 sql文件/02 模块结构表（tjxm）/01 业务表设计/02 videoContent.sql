-- Create table
create table VIDEO_CONTENT
(
  id               VARCHAR2(50) not null,
  cre_user_id      VARCHAR2(50),
  cre_user_name    VARCHAR2(50),
  cre_dept_id      VARCHAR2(50),
  cre_dept_name    VARCHAR2(50),
  cre_chushi_id    VARCHAR2(50),
  cre_chushi_name  VARCHAR2(50),
  cre_ju_id        VARCHAR2(50),
  cre_ju_name      VARCHAR2(50),
  visible          VARCHAR2(1),
  cre_time         VARCHAR2(30),
  update_user_id   VARCHAR2(50),
  update_user_name VARCHAR2(50),
  update_time      VARCHAR2(50),
  title            VARCHAR2(200),
  subtitle         VARCHAR2(200),
  author           VARCHAR2(50),
  show_start_time  VARCHAR2(30),
  show_end_time    VARCHAR2(30),
  content          CLOB,
  source           VARCHAR2(50),
  video_id         VARCHAR2(50),
  image_id         VARCHAR2(50),
  subflag          VARCHAR2(2),
  is_zd            VARCHAR2(50),
  column_id        VARCHAR2(50),
  fbfw             VARCHAR2(2000),
  fb_time          VARCHAR2(30),
  is_fb_content    VARCHAR2(1)
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
comment on table VIDEO_CONTENT
  is '信息发布内容表';
-- Add comments to the columns 
comment on column VIDEO_CONTENT.id
  is '主键id';
comment on column VIDEO_CONTENT.cre_user_id
  is '创建人id';
comment on column VIDEO_CONTENT.cre_user_name
  is '创建人name';
comment on column VIDEO_CONTENT.cre_dept_name
  is '创建人部门名';
comment on column VIDEO_CONTENT.cre_chushi_id
  is '创建人处室id';
comment on column VIDEO_CONTENT.cre_chushi_name
  is '创建人处室名';
comment on column VIDEO_CONTENT.cre_ju_id
  is '创建人二级局id';
comment on column VIDEO_CONTENT.cre_ju_name
  is '创建人二级局名';
comment on column VIDEO_CONTENT.visible
  is '逻辑删除';
comment on column VIDEO_CONTENT.cre_time
  is '创建时间';
comment on column VIDEO_CONTENT.update_user_id
  is '最后更新人id';
comment on column VIDEO_CONTENT.update_user_name
  is '最后更新人名';
comment on column VIDEO_CONTENT.update_time
  is '最后更新时间';
comment on column VIDEO_CONTENT.title
  is '视频标题';
comment on column VIDEO_CONTENT.subtitle
  is '视频副标题';
comment on column VIDEO_CONTENT.author
  is '视频作者';
comment on column VIDEO_CONTENT.show_start_time
  is '显示开始日期';
comment on column VIDEO_CONTENT.show_end_time
  is '显示结束日记';
comment on column VIDEO_CONTENT.content
  is '视频';
comment on column VIDEO_CONTENT.source
  is '视频来源';
comment on column VIDEO_CONTENT.video_id
  is '视频id';
comment on column VIDEO_CONTENT.image_id
  is '缩略图id';
comment on column VIDEO_CONTENT.subflag
  is '流程状态';
comment on column VIDEO_CONTENT.is_zd
  is '是否置顶';
comment on column VIDEO_CONTENT.column_id
  is '栏目id';
comment on column VIDEO_CONTENT.fbfw
  is '发布范围';
comment on column VIDEO_CONTENT.fb_time
  is '发布时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table VIDEO_CONTENT
  add constraint PK_VIDEO_CONTENT primary key (ID)
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
