-- 创建门类表
create table WMGL_CATEGORY
(
  ID               VARCHAR2(50 CHAR),
  TYPE_NAME        VARCHAR2(50 CHAR),
  MARK             VARCHAR2(200 CHAR),
  VISIBLE          VARCHAR2(50 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  ORDER_NO         VARCHAR2(50 CHAR),
  IS_FIRST         VARCHAR2(1),
  NODE_LEVEL       VARCHAR2(50 CHAR),
  SUPER_ID         VARCHAR2(50 CHAR)
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
comment on table WMGL_CATEGORY
  is '外卖中的门类表';
-- Add comments to the columns 
comment on column WMGL_CATEGORY.ID
  is '主键';
comment on column WMGL_CATEGORY.TYPE_NAME
  is '类型名称';
comment on column WMGL_CATEGORY.MARK
  is '备注';
comment on column WMGL_CATEGORY.VISIBLE
  is '删除';
comment on column WMGL_CATEGORY.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_CATEGORY.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_CATEGORY.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_CATEGORY.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_CATEGORY.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_CATEGORY.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_CATEGORY.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_CATEGORY.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_CATEGORY.CRE_TIME
  is '创建时间';
comment on column WMGL_CATEGORY.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_CATEGORY.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_CATEGORY.UPDATE_TIME
  is '更新时间';
comment on column WMGL_CATEGORY.ORDER_NO
  is '排序';
comment on column WMGL_CATEGORY.IS_FIRST
  is '是否是根目录';
comment on column WMGL_CATEGORY.NODE_LEVEL
  is '节点级别';
comment on column WMGL_CATEGORY.SUPER_ID
  is '父节点id';
  
  
--创建基础配置表
create table WMGL_CONFIG
(
  ID               VARCHAR2(50 CHAR) not null,
  ISVALID          VARCHAR2(50 CHAR),
  PERIOD           VARCHAR2(4 CHAR),
  LOST_CREDIT_LIMT NUMBER(2),
  LOCK_TIME        NUMBER,
  ATTENTION_ITME   VARCHAR2(200 CHAR),
  VISIBLE          VARCHAR2(50 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR)
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
comment on table WMGL_CONFIG
  is '外卖中的基础配置表';
-- Add comments to the columns 
comment on column WMGL_CONFIG.ID
  is '主键';
comment on column WMGL_CONFIG.ISVALID
  is '是否有效，0：无效，1：有效';
comment on column WMGL_CONFIG.PERIOD
  is '年度';
comment on column WMGL_CONFIG.LOST_CREDIT_LIMT
  is '一个人在指定周期内，最多可失信多少次';
comment on column WMGL_CONFIG.LOCK_TIME
  is '失信多少次后，用户被锁定多长时间，单位为月';
comment on column WMGL_CONFIG.ATTENTION_ITME
  is '外卖的注意事项';
comment on column WMGL_CONFIG.VISIBLE
  is '删除';
comment on column WMGL_CONFIG.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_CONFIG.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_CONFIG.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_CONFIG.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_CONFIG.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_CONFIG.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_CONFIG.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_CONFIG.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_CONFIG.CRE_TIME
  is '创建时间';
comment on column WMGL_CONFIG.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_CONFIG.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_CONFIG.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table WMGL_CONFIG
  add constraint PK_WMGL_CONFIG primary key (ID)
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

-- 创建商品表
create table WMGL_GOODS
(
  ID               VARCHAR2(50 CHAR) not null,
  BELONG_ID        VARCHAR2(50 CHAR),
  GOODS_NAME       VARCHAR2(50 CHAR),
  PRICE            NUMBER,
  VALUATION_UNIT   VARCHAR2(50 CHAR),
  BUY_UNIT         VARCHAR2(50 CHAR),
  IS_USE           VARCHAR2(1 CHAR),
  IMAGE_ID         VARCHAR2(50 CHAR),
  VISIBLE          VARCHAR2(50 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  STATUS           VARCHAR2(50 CHAR),
  MARK             VARCHAR2(400 CHAR),
  ORDER_NO         NUMBER,
  DESCRIBE         VARCHAR2(400 CHAR),
  AMOUNT_LIMIT     VARCHAR2(1 CHAR),
  AMOUNT_NUM       NUMBER,
  BUY_LIMIT        VARCHAR2(1 CHAR),
  BUY_NUM          NUMBER
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
comment on table WMGL_GOODS
  is '外卖中的商品表';
-- Add comments to the columns 
comment on column WMGL_GOODS.ID
  is '主键';
comment on column WMGL_GOODS.BELONG_ID
  is '所属门类';
comment on column WMGL_GOODS.GOODS_NAME
  is '商品名称';
comment on column WMGL_GOODS.PRICE
  is '单价';
comment on column WMGL_GOODS.VALUATION_UNIT
  is '计价单位';
comment on column WMGL_GOODS.BUY_UNIT
  is '购买单位';
comment on column WMGL_GOODS.IS_USE
  is '是否可用(0表示不可用，1表示可用）';
comment on column WMGL_GOODS.IMAGE_ID
  is '图片id';
comment on column WMGL_GOODS.VISIBLE
  is '删除';
comment on column WMGL_GOODS.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_GOODS.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_GOODS.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_GOODS.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_GOODS.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_GOODS.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_GOODS.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_GOODS.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_GOODS.CRE_TIME
  is '创建时间';
comment on column WMGL_GOODS.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_GOODS.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_GOODS.UPDATE_TIME
  is '更新时间';
comment on column WMGL_GOODS.STATUS
  is '状态；0表示未发布；1表示已发布';
comment on column WMGL_GOODS.MARK
  is '备注';
comment on column WMGL_GOODS.ORDER_NO
  is '排序';
comment on column WMGL_GOODS.DESCRIBE
  is '描述';
comment on column WMGL_GOODS.AMOUNT_LIMIT
  is '是否有数量限制(0表示没有，1表示有）';
comment on column WMGL_GOODS.AMOUNT_NUM
  is '商品库存的数量';
comment on column WMGL_GOODS.BUY_LIMIT
  is ' 是否有购买限制，0：没有，1：有 ';
comment on column WMGL_GOODS.BUY_NUM
  is '最多购买数量';
-- Create/Recreate primary, unique and foreign key constraints 
alter table WMGL_GOODS
  add constraint PK_WMGL_GOODS primary key (ID)
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
  
-- 创建失信人员名单表
create table WMGL_LOSECREDIT
(
  ID                  VARCHAR2(50 CHAR),
  LOSE_USER_ID        VARCHAR2(50 CHAR),
  LOSE_USER_NAME      VARCHAR2(50 CHAR),
  LOSE_USER_DEPT_ID   VARCHAR2(50 CHAR),
  LOSE_USER_DEPT_NAME VARCHAR2(50 CHAR),
  LOSE_CHUSHI_ID      VARCHAR2(50 CHAR),
  LOSE_CHUSHI_NAME    VARCHAR2(50 CHAR),
  LOSE_JU_ID          VARCHAR2(50 CHAR),
  LOSE_JU_NAME        VARCHAR2(50 CHAR),
  LOSE_TIME           VARCHAR2(50 CHAR),
  RELIEVE_TIME        VARCHAR2(50 CHAR),
  RELIEVE_TYPE        VARCHAR2(1 CHAR),
  VISIBLE             VARCHAR2(1 CHAR),
  CRE_USER_ID         VARCHAR2(50 CHAR),
  CRE_USER_NAME       VARCHAR2(50 CHAR),
  CRE_DEPT_ID         VARCHAR2(50 CHAR),
  CRE_DEPT_NAME       VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID       VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME     VARCHAR2(50 CHAR),
  CRE_JU_ID           VARCHAR2(50 CHAR),
  CRE_JU_NAME         VARCHAR2(50 CHAR),
  CRE_TIME            VARCHAR2(50 CHAR),
  UPDATE_USER_ID      VARCHAR2(50 CHAR),
  UPDATE_USER_NAME    VARCHAR2(50 CHAR),
  UPDATE_TIME         VARCHAR2(50 CHAR),
  CARD_NUM            VARCHAR2(50 CHAR),
  TAKE_OUT_IDS        VARCHAR2(50 CHAR)
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
comment on table WMGL_LOSECREDIT
  is '外卖中的失信名单表';
-- Add comments to the columns 
comment on column WMGL_LOSECREDIT.ID
  is '主键';
comment on column WMGL_LOSECREDIT.LOSE_USER_ID
  is '失信人id';
comment on column WMGL_LOSECREDIT.LOSE_USER_NAME
  is '失信人姓名';
comment on column WMGL_LOSECREDIT.LOSE_USER_DEPT_ID
  is '锁定人的所属部门id';
comment on column WMGL_LOSECREDIT.LOSE_USER_DEPT_NAME
  is '锁定人的所属部门名称';
comment on column WMGL_LOSECREDIT.LOSE_CHUSHI_ID
  is '锁定人的处室id';
comment on column WMGL_LOSECREDIT.LOSE_CHUSHI_NAME
  is '锁定人的处室名称';
comment on column WMGL_LOSECREDIT.LOSE_JU_ID
  is '锁定人的局id';
comment on column WMGL_LOSECREDIT.LOSE_JU_NAME
  is '锁定人的局名称';
comment on column WMGL_LOSECREDIT.LOSE_TIME
  is '被锁定时间';
comment on column WMGL_LOSECREDIT.RELIEVE_TIME
  is '解除时间';
comment on column WMGL_LOSECREDIT.RELIEVE_TYPE
  is '0表示自动解除；1表示是手动解除';
comment on column WMGL_LOSECREDIT.VISIBLE
  is '删除';
comment on column WMGL_LOSECREDIT.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_LOSECREDIT.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_LOSECREDIT.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_LOSECREDIT.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_LOSECREDIT.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_LOSECREDIT.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_LOSECREDIT.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_LOSECREDIT.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_LOSECREDIT.CRE_TIME
  is '创建时间';
comment on column WMGL_LOSECREDIT.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_LOSECREDIT.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_LOSECREDIT.UPDATE_TIME
  is '更新时间';
comment on column WMGL_LOSECREDIT.CARD_NUM
  is '卡号';
comment on column WMGL_LOSECREDIT.TAKE_OUT_IDS
  is '外卖id,是因为哪几次外卖的订单，而被锁定';
 
 -- 创建通知公告表
create table WMGL_NOTICE
(
  ID               VARCHAR2(50 CHAR) not null,
  IS_VALID         VARCHAR2(1 CHAR),
  CONTENT          VARCHAR2(1000 CHAR),
  STATUS           VARCHAR2(1 CHAR),
  VISIBLE          VARCHAR2(1 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR)
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
comment on table WMGL_NOTICE
  is '外卖中的通知公告表';
-- Add comments to the columns 
comment on column WMGL_NOTICE.ID
  is '主键';
comment on column WMGL_NOTICE.IS_VALID
  is '是否有效，0：无效1：有效';
comment on column WMGL_NOTICE.CONTENT
  is '通知内容';
comment on column WMGL_NOTICE.STATUS
  is '状态，0：未发布；1：已发布';
comment on column WMGL_NOTICE.VISIBLE
  is '删除，0：删除；1：可用';
comment on column WMGL_NOTICE.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_NOTICE.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_NOTICE.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_NOTICE.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_NOTICE.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_NOTICE.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_NOTICE.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_NOTICE.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_NOTICE.CRE_TIME
  is '创建时间';
comment on column WMGL_NOTICE.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_NOTICE.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_NOTICE.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table WMGL_NOTICE
  add constraint PK_WMGL_NOTICE primary key (ID)
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

-- 创建商品订单详情表
create table WMGL_ORDER_DETIL
(
  ID               VARCHAR2(50 CHAR),
  ORDER_ID         VARCHAR2(50 CHAR),
  CATEGORY_ID      VARCHAR2(50 CHAR),
  CATEGORY_NAME    VARCHAR2(50 CHAR),
  GOODS_ID         VARCHAR2(50 CHAR),
  GOODS_NAME       VARCHAR2(50 CHAR),
  VALUATION_UNIT   VARCHAR2(50 CHAR),
  BUY_UNIT         VARCHAR2(50 CHAR),
  GOODS_PRICE      NUMBER,
  GOODS_NUM        NUMBER,
  TOTAL_PRICE      VARCHAR2(50 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  CATEGORY_SORT    VARCHAR2(50 CHAR)
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
comment on table WMGL_ORDER_DETIL
  is '外中的订单详情表';
-- Add comments to the columns 
comment on column WMGL_ORDER_DETIL.ID
  is '主键';
comment on column WMGL_ORDER_DETIL.ORDER_ID
  is '订单id';
comment on column WMGL_ORDER_DETIL.CATEGORY_ID
  is '门类id';
comment on column WMGL_ORDER_DETIL.CATEGORY_NAME
  is '门类名称';
comment on column WMGL_ORDER_DETIL.GOODS_ID
  is '商品id';
comment on column WMGL_ORDER_DETIL.GOODS_NAME
  is '商品名称';
comment on column WMGL_ORDER_DETIL.VALUATION_UNIT
  is '计价单位';
comment on column WMGL_ORDER_DETIL.BUY_UNIT
  is '购买单位';
comment on column WMGL_ORDER_DETIL.GOODS_PRICE
  is '单价';
comment on column WMGL_ORDER_DETIL.GOODS_NUM
  is '数量';
comment on column WMGL_ORDER_DETIL.TOTAL_PRICE
  is '总价';
comment on column WMGL_ORDER_DETIL.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_ORDER_DETIL.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_ORDER_DETIL.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_ORDER_DETIL.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_ORDER_DETIL.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_ORDER_DETIL.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_ORDER_DETIL.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_ORDER_DETIL.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_ORDER_DETIL.CRE_TIME
  is '创建时间';
comment on column WMGL_ORDER_DETIL.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_ORDER_DETIL.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_ORDER_DETIL.UPDATE_TIME
  is '更新时间';
comment on column WMGL_ORDER_DETIL.CATEGORY_SORT
  is '门类的排序号';
 
-- 创建订单表
create table WMGL_ORDER_INFO
(
  ID               VARCHAR2(50 CHAR),
  TAKE_OUT_ID      VARCHAR2(50 CHAR),
  ORDER_NUM        VARCHAR2(50 CHAR),
  TOTAL_PRICE      VARCHAR2(50 CHAR),
  LOSE_CREDIT      VARCHAR2(1 CHAR),
  STATUS           VARCHAR2(1 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  CARD_NO          VARCHAR2(50 CHAR),
  PHONE            VARCHAR2(50),
  ORDER_NO         NUMBER,
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
-- Add comments to the table 
comment on table WMGL_ORDER_INFO
  is '外卖中的订单表';
-- Add comments to the columns 
comment on column WMGL_ORDER_INFO.ID
  is '主键';
comment on column WMGL_ORDER_INFO.TAKE_OUT_ID
  is '外卖id';
comment on column WMGL_ORDER_INFO.ORDER_NUM
  is '订单号(自动生成)';
comment on column WMGL_ORDER_INFO.TOTAL_PRICE
  is '商品总价钱';
comment on column WMGL_ORDER_INFO.LOSE_CREDIT
  is '是否被统计过失信(0表示未被统计过，1表示被统计过）';
comment on column WMGL_ORDER_INFO.STATUS
  is '状态(1:已下单；2：已取消；3：已领取；4：未领取)';
comment on column WMGL_ORDER_INFO.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_ORDER_INFO.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_ORDER_INFO.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_ORDER_INFO.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_ORDER_INFO.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_ORDER_INFO.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_ORDER_INFO.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_ORDER_INFO.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_ORDER_INFO.CRE_TIME
  is '创建时间';
comment on column WMGL_ORDER_INFO.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_ORDER_INFO.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_ORDER_INFO.UPDATE_TIME
  is '更新时间';
comment on column WMGL_ORDER_INFO.CARD_NO
  is '创建人的门禁卡号';
comment on column WMGL_ORDER_INFO.PHONE
  is '下单人联系电话';
comment on column WMGL_ORDER_INFO.ORDER_NO
  is '订单序号';
  
-- 创建套餐表
create table WMGL_SET_MEAL
(
  ID               VARCHAR2(50 CHAR) not null,
  CATEGORY_ID      VARCHAR2(50 CHAR),
  TAKE_OUT_ID      VARCHAR2(50 CHAR),
  GOODS_IDS        VARCHAR2(4000 CHAR),
  OPT_NUM          NUMBER(4),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  GOODS_NAMES      VARCHAR2(4000 CHAR)
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
comment on table WMGL_SET_MEAL
  is '套餐表';
-- Add comments to the columns 
comment on column WMGL_SET_MEAL.ID
  is '主键';
comment on column WMGL_SET_MEAL.CATEGORY_ID
  is '门类id';
comment on column WMGL_SET_MEAL.TAKE_OUT_ID
  is '外卖id';
comment on column WMGL_SET_MEAL.GOODS_IDS
  is '商品id';
comment on column WMGL_SET_MEAL.OPT_NUM
  is '规则（可选数目）';
comment on column WMGL_SET_MEAL.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_SET_MEAL.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_SET_MEAL.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_SET_MEAL.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_SET_MEAL.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_SET_MEAL.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_SET_MEAL.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_SET_MEAL.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_SET_MEAL.CRE_TIME
  is '创建时间';
comment on column WMGL_SET_MEAL.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_SET_MEAL.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_SET_MEAL.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table WMGL_SET_MEAL
  add constraint PK_WMGL_SET_MEAL primary key (ID)
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

-- 创建购物车表
create table WMGL_SHOPPING_CART
(
  ID               VARCHAR2(50 CHAR),
  TAKE_OUT_ID      VARCHAR2(50 CHAR),
  GOODS_ID         VARCHAR2(50 CHAR),
  GOODS_NAME       VARCHAR2(50 CHAR),
  GOODS_NUM        NUMBER(4),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  VALUATION_UNIT   VARCHAR2(50 CHAR),
  BUY_UNIT         VARCHAR2(50 CHAR),
  PRICE            NUMBER,
  TOTAL_PRICE      NUMBER,
  CATEGORY_ID      VARCHAR2(50 CHAR),
  CATEGORY_NAME    VARCHAR2(50 CHAR)
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
comment on table WMGL_SHOPPING_CART
  is '外卖中的购物车详情表';
-- Add comments to the columns 
comment on column WMGL_SHOPPING_CART.ID
  is '主键';
comment on column WMGL_SHOPPING_CART.TAKE_OUT_ID
  is '外卖id';
comment on column WMGL_SHOPPING_CART.GOODS_ID
  is '商品id';
comment on column WMGL_SHOPPING_CART.GOODS_NAME
  is '商品名称';
comment on column WMGL_SHOPPING_CART.GOODS_NUM
  is '商品数数量';
comment on column WMGL_SHOPPING_CART.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_SHOPPING_CART.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_SHOPPING_CART.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_SHOPPING_CART.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_SHOPPING_CART.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_SHOPPING_CART.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_SHOPPING_CART.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_SHOPPING_CART.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_SHOPPING_CART.CRE_TIME
  is '创建时间';
comment on column WMGL_SHOPPING_CART.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_SHOPPING_CART.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_SHOPPING_CART.UPDATE_TIME
  is '更新时间';
comment on column WMGL_SHOPPING_CART.VALUATION_UNIT
  is '计价单位';
comment on column WMGL_SHOPPING_CART.BUY_UNIT
  is '购买单位';
comment on column WMGL_SHOPPING_CART.PRICE
  is '购买价格';
comment on column WMGL_SHOPPING_CART.TOTAL_PRICE
  is '总价';
comment on column WMGL_SHOPPING_CART.CATEGORY_ID
  is '门类id';
comment on column WMGL_SHOPPING_CART.CATEGORY_NAME
  is '门类名称';
  
-- 创建外卖详情表
create table WMGL_TAKE_OUT_DETAIL
(
  ID               VARCHAR2(50 CHAR) not null,
  CLASS_ID         VARCHAR2(50 CHAR),
  TAKE_OUT_ID      VARCHAR2(50 CHAR),
  GOODS_ID         VARCHAR2(50 CHAR),
  GOODS_NAME       VARCHAR2(50 CHAR),
  GOODS_PRICE      VARCHAR2(50 CHAR),
  BUY_UNIT         VARCHAR2(50 CHAR),
  VALUATION_UNIT   VARCHAR2(50 CHAR),
  VISIBLE          VARCHAR2(200 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  CLASS_NAME       VARCHAR2(50 CHAR),
  SORT             NUMBER,
  CATEGORY_SORT    NUMBER,
  GOODS_NUM        NUMBER,
  AMOUNT_LIMIT     VARCHAR2(50 CHAR),
  BUY_LIMIT        VARCHAR2(50 CHAR),
  BUY_NUM          VARCHAR2(50 CHAR)
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
comment on table WMGL_TAKE_OUT_DETAIL
  is '外卖详情表';
-- Add comments to the columns 
comment on column WMGL_TAKE_OUT_DETAIL.ID
  is '主键';
comment on column WMGL_TAKE_OUT_DETAIL.CLASS_ID
  is '门类id';
comment on column WMGL_TAKE_OUT_DETAIL.TAKE_OUT_ID
  is '外卖id';
comment on column WMGL_TAKE_OUT_DETAIL.GOODS_ID
  is '商品id';
comment on column WMGL_TAKE_OUT_DETAIL.GOODS_NAME
  is '商品名称';
comment on column WMGL_TAKE_OUT_DETAIL.GOODS_PRICE
  is '商品单价';
comment on column WMGL_TAKE_OUT_DETAIL.BUY_UNIT
  is '购买单位';
comment on column WMGL_TAKE_OUT_DETAIL.VALUATION_UNIT
  is '计价单位';
comment on column WMGL_TAKE_OUT_DETAIL.VISIBLE
  is '删除';
comment on column WMGL_TAKE_OUT_DETAIL.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_TAKE_OUT_DETAIL.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_TAKE_OUT_DETAIL.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_TAKE_OUT_DETAIL.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_TAKE_OUT_DETAIL.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_TAKE_OUT_DETAIL.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_TAKE_OUT_DETAIL.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_TAKE_OUT_DETAIL.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_TAKE_OUT_DETAIL.CRE_TIME
  is '创建时间';
comment on column WMGL_TAKE_OUT_DETAIL.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_TAKE_OUT_DETAIL.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_TAKE_OUT_DETAIL.UPDATE_TIME
  is '更新时间';
comment on column WMGL_TAKE_OUT_DETAIL.CLASS_NAME
  is '门类名称';
comment on column WMGL_TAKE_OUT_DETAIL.SORT
  is '商品排序号';
comment on column WMGL_TAKE_OUT_DETAIL.CATEGORY_SORT
  is '门类的排序号';
comment on column WMGL_TAKE_OUT_DETAIL.GOODS_NUM
  is '商品数量';
comment on column WMGL_TAKE_OUT_DETAIL.AMOUNT_LIMIT
  is '是否有数量限制，0：没有，1：有 ';
comment on column WMGL_TAKE_OUT_DETAIL.BUY_LIMIT
  is '是否有购买限制，0：没有，1：有 ';
comment on column WMGL_TAKE_OUT_DETAIL.BUY_NUM
  is '最多购买数量';
-- Create/Recreate primary, unique and foreign key constraints 
alter table WMGL_TAKE_OUT_DETAIL
  add constraint PK_WMGL_TAKE_OUT_DETAIL primary key (ID)
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

  
-- 创建外卖信息表
create table WMGL_TAKE_OUT_INFO
(
  ID               VARCHAR2(50 CHAR) not null,
  TITLE            VARCHAR2(50 CHAR),
  TAKEFOOD_TIME    VARCHAR2(50 CHAR),
  DEADLINE_TIME    VARCHAR2(50 CHAR),
  MARK             VARCHAR2(1000 CHAR),
  ATTENTION_ITEM   VARCHAR2(1000 CHAR),
  VISIBLE          VARCHAR2(1 CHAR),
  STATUS           VARCHAR2(50 CHAR),
  CRE_USER_ID      VARCHAR2(50 CHAR),
  CRE_USER_NAME    VARCHAR2(50 CHAR),
  CRE_DEPT_ID      VARCHAR2(50 CHAR),
  CRE_DEPT_NAME    VARCHAR2(50 CHAR),
  CRE_CHUSHI_ID    VARCHAR2(50 CHAR),
  CRE_CHUSHI_NAME  VARCHAR2(50 CHAR),
  CRE_JU_ID        VARCHAR2(50 CHAR),
  CRE_JU_NAME      VARCHAR2(50 CHAR),
  CRE_TIME         VARCHAR2(50 CHAR),
  UPDATE_USER_ID   VARCHAR2(50 CHAR),
  UPDATE_USER_NAME VARCHAR2(50 CHAR),
  UPDATE_TIME      VARCHAR2(50 CHAR),
  COPY             VARCHAR2(50 CHAR),
  TONGJI           VARCHAR2(50 CHAR)
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
comment on table WMGL_TAKE_OUT_INFO
  is '外卖信息表';
-- Add comments to the columns 
comment on column WMGL_TAKE_OUT_INFO.ID
  is '主键';
comment on column WMGL_TAKE_OUT_INFO.TITLE
  is '外卖标题（例如：201901次）';
comment on column WMGL_TAKE_OUT_INFO.TAKEFOOD_TIME
  is '取餐时间';
comment on column WMGL_TAKE_OUT_INFO.DEADLINE_TIME
  is '最晚预定时间';
comment on column WMGL_TAKE_OUT_INFO.MARK
  is '备注';
comment on column WMGL_TAKE_OUT_INFO.ATTENTION_ITEM
  is '注意事项';
comment on column WMGL_TAKE_OUT_INFO.VISIBLE
  is '删除';
comment on column WMGL_TAKE_OUT_INFO.STATUS
  is '状态;0表示未发布；1表示已发布';
comment on column WMGL_TAKE_OUT_INFO.CRE_USER_ID
  is '创建人ID';
comment on column WMGL_TAKE_OUT_INFO.CRE_USER_NAME
  is '创建人名称';
comment on column WMGL_TAKE_OUT_INFO.CRE_DEPT_ID
  is '创建人部门ID';
comment on column WMGL_TAKE_OUT_INFO.CRE_DEPT_NAME
  is '创建人部门名称';
comment on column WMGL_TAKE_OUT_INFO.CRE_CHUSHI_ID
  is '创建人处室ID';
comment on column WMGL_TAKE_OUT_INFO.CRE_CHUSHI_NAME
  is '创建人处室名称';
comment on column WMGL_TAKE_OUT_INFO.CRE_JU_ID
  is '创建人局ID';
comment on column WMGL_TAKE_OUT_INFO.CRE_JU_NAME
  is '创建人局名称';
comment on column WMGL_TAKE_OUT_INFO.CRE_TIME
  is '创建时间';
comment on column WMGL_TAKE_OUT_INFO.UPDATE_USER_ID
  is '更新人ID';
comment on column WMGL_TAKE_OUT_INFO.UPDATE_USER_NAME
  is '更新人名称';
comment on column WMGL_TAKE_OUT_INFO.UPDATE_TIME
  is '更新时间';
comment on column WMGL_TAKE_OUT_INFO.COPY
  is '是否复用记录，1表示是，0表示否';
comment on column WMGL_TAKE_OUT_INFO.TONGJI
  is '定时器是否改过订单状态，1表示是，0表示';
-- Create/Recreate primary, unique and foreign key constraints 
alter table WMGL_TAKE_OUT_INFO
  add constraint PK_WMGL_TAKE_OUT_INFO primary key (ID)
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
 
  