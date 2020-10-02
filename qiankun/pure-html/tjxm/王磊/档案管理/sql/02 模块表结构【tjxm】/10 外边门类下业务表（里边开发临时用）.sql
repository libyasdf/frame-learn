-- Create table
create table E_TJDAHZ_DAK
(
  ad_folderno           VARCHAR2(10 CHAR),
  archive_ctg_no        VARCHAR2(16 CHAR),
  archive_entity_status VARCHAR2(6 CHAR),
  barcode               VARCHAR2(30 CHAR),
  basefolder_unit       VARCHAR2(50 CHAR),
  content_no            VARCHAR2(20 CHAR),
  doc_end_time          VARCHAR2(50 CHAR),
  doc_start_time        VARCHAR2(50 CHAR),
  filing_year           NUMBER,
  folder_location       VARCHAR2(100 CHAR),
  folder_no             VARCHAR2(50 CHAR),
  fonds_no              VARCHAR2(20 CHAR),
  leibiehao             VARCHAR2(20 CHAR),
  maintitle             VARCHAR2(255 CHAR),
  note                  VARCHAR2(255 CHAR),
  object_quantity       NUMBER,
  page_num              NUMBER,
  piece_num             NUMBER,
  pigeonhole_date       VARCHAR2(50 CHAR),
  quantity              NUMBER,
  recid                 VARCHAR2(50 CHAR),
  retention             VARCHAR2(10 CHAR),
  sbt_word              VARCHAR2(100 CHAR),
  security_class        VARCHAR2(4 CHAR),
  swlx                  VARCHAR2(10 CHAR),
  temp_no               NUMBER,
  year_folder_no        VARCHAR2(5 CHAR),
  pid                   VARCHAR2(50 CHAR),
  classification        VARCHAR2(10 CHAR),
  cre_user_id           VARCHAR2(50 CHAR),
  cre_user_name         VARCHAR2(50 CHAR),
  cre_dept_id           VARCHAR2(50 CHAR),
  cre_dept_name         VARCHAR2(50 CHAR),
  cre_chushi_id         VARCHAR2(50 CHAR),
  cre_chushi_name       VARCHAR2(50 CHAR),
  cre_ju_id             VARCHAR2(50 CHAR),
  cre_ju_name           VARCHAR2(50 CHAR),
  visible               VARCHAR2(1 CHAR),
  cre_time              VARCHAR2(30 CHAR)
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
-- Add comments to the columns 
comment on column E_TJDAHZ_DAK.ad_folderno
  is '预立卷号';
comment on column E_TJDAHZ_DAK.archive_ctg_no
  is '实体分类号';
comment on column E_TJDAHZ_DAK.archive_entity_status
  is '档案实体状态';
comment on column E_TJDAHZ_DAK.barcode
  is '条码';
comment on column E_TJDAHZ_DAK.basefolder_unit
  is '立卷单位';
comment on column E_TJDAHZ_DAK.content_no
  is '目录号';
comment on column E_TJDAHZ_DAK.doc_end_time
  is '文件结束时间';
comment on column E_TJDAHZ_DAK.doc_start_time
  is '文件开始时间';
comment on column E_TJDAHZ_DAK.filing_year
  is '归档年度';
comment on column E_TJDAHZ_DAK.folder_location
  is '存放位置';
comment on column E_TJDAHZ_DAK.folder_no
  is '案卷级档号';
comment on column E_TJDAHZ_DAK.fonds_no
  is '全宗号';
comment on column E_TJDAHZ_DAK.leibiehao
  is '类别号';
comment on column E_TJDAHZ_DAK.maintitle
  is '案卷题名';
comment on column E_TJDAHZ_DAK.note
  is '备注';
comment on column E_TJDAHZ_DAK.object_quantity
  is '实物份数';
comment on column E_TJDAHZ_DAK.page_num
  is '卷内页数';
comment on column E_TJDAHZ_DAK.piece_num
  is '件数';
comment on column E_TJDAHZ_DAK.pigeonhole_date
  is '归档日期';
comment on column E_TJDAHZ_DAK.quantity
  is '数量';
comment on column E_TJDAHZ_DAK.recid
  is '系统唯一关键字';
comment on column E_TJDAHZ_DAK.retention
  is '保管期限';
comment on column E_TJDAHZ_DAK.sbt_word
  is '主题词';
comment on column E_TJDAHZ_DAK.security_class
  is '密级';
comment on column E_TJDAHZ_DAK.swlx
  is '实物类型';
comment on column E_TJDAHZ_DAK.temp_no
  is '临时号';
comment on column E_TJDAHZ_DAK.year_folder_no
  is '案卷号';
comment on column E_TJDAHZ_DAK.pid
  is '父id';


-- Create table
create table E_TJDAHZ
(
  ad_folderno           VARCHAR2(10 CHAR),
  archive_ctg_no        VARCHAR2(16 CHAR),
  archive_entity_status VARCHAR2(6 CHAR),
  barcode               VARCHAR2(30 CHAR),
  basefolder_unit       VARCHAR2(50 CHAR),
  content_no            VARCHAR2(20 CHAR),
  doc_end_time          VARCHAR2(50 CHAR),
  doc_start_time        VARCHAR2(50 CHAR),
  filing_year           NUMBER,
  folder_location       VARCHAR2(100 CHAR),
  folder_no             VARCHAR2(50 CHAR),
  fonds_no              VARCHAR2(20 CHAR),
  leibiehao             VARCHAR2(20 CHAR),
  maintitle             VARCHAR2(255 CHAR),
  note                  VARCHAR2(255 CHAR),
  object_quantity       NUMBER,
  page_num              NUMBER,
  piece_num             NUMBER,
  pigeonhole_date       VARCHAR2(50 CHAR),
  quantity              NUMBER,
  recid                 VARCHAR2(50 CHAR),
  retention             VARCHAR2(10 CHAR),
  sbt_word              VARCHAR2(100 CHAR),
  security_class        VARCHAR2(4 CHAR),
  swlx                  VARCHAR2(10 CHAR),
  temp_no               NUMBER,
  year_folder_no        VARCHAR2(5 CHAR),
  pid                   VARCHAR2(50 CHAR),
  classification        VARCHAR2(10 CHAR),
  cre_user_id           VARCHAR2(50 CHAR),
  cre_user_name         VARCHAR2(50 CHAR),
  cre_dept_id           VARCHAR2(50 CHAR),
  cre_dept_name         VARCHAR2(50 CHAR),
  cre_chushi_id         VARCHAR2(50 CHAR),
  cre_chushi_name       VARCHAR2(50 CHAR),
  cre_ju_id             VARCHAR2(50 CHAR),
  cre_ju_name           VARCHAR2(50 CHAR),
  visible               VARCHAR2(1 CHAR),
  cre_time              VARCHAR2(30 CHAR)
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
-- Add comments to the columns 
comment on column E_TJDAHZ.ad_folderno
  is '预立卷号';
comment on column E_TJDAHZ.archive_ctg_no
  is '实体分类号';
comment on column E_TJDAHZ.archive_entity_status
  is '档案实体状态';
comment on column E_TJDAHZ.barcode
  is '条码';
comment on column E_TJDAHZ.basefolder_unit
  is '立卷单位';
comment on column E_TJDAHZ.content_no
  is '目录号';
comment on column E_TJDAHZ.doc_end_time
  is '文件结束时间';
comment on column E_TJDAHZ.doc_start_time
  is '文件开始时间';
comment on column E_TJDAHZ.filing_year
  is '归档年度';
comment on column E_TJDAHZ.folder_location
  is '存放位置';
comment on column E_TJDAHZ.folder_no
  is '案卷级档号';
comment on column E_TJDAHZ.fonds_no
  is '全宗号';
comment on column E_TJDAHZ.leibiehao
  is '类别号';
comment on column E_TJDAHZ.maintitle
  is '案卷题名';
comment on column E_TJDAHZ.note
  is '备注';
comment on column E_TJDAHZ.object_quantity
  is '实物份数';
comment on column E_TJDAHZ.page_num
  is '卷内页数';
comment on column E_TJDAHZ.piece_num
  is '件数';
comment on column E_TJDAHZ.pigeonhole_date
  is '归档日期';
comment on column E_TJDAHZ.quantity
  is '数量';
comment on column E_TJDAHZ.recid
  is '系统唯一关键字';
comment on column E_TJDAHZ.retention
  is '保管期限';
comment on column E_TJDAHZ.sbt_word
  is '主题词';
comment on column E_TJDAHZ.security_class
  is '密级';
comment on column E_TJDAHZ.swlx
  is '实物类型';
comment on column E_TJDAHZ.temp_no
  is '临时号';
comment on column E_TJDAHZ.year_folder_no
  is '案卷号';
comment on column E_TJDAHZ.pid
  is '父id';

-- Create table
create table E_TJHZWJN_DAK
(
  recid                 VARCHAR2(50 CHAR),
  jiduwjzhtai           VARCHAR2(5 CHAR),
  leibiehao             VARCHAR2(20 CHAR) not null,
  maintitle             VARCHAR2(255 CHAR) not null,
  note                  VARCHAR2(255 CHAR),
  quantity              NUMBER,
  page_no               NUMBER,
  page_num              NUMBER not null,
  piece_no              NUMBER not null,
  pigeonhole_date       VARCHAR2(50 CHAR),
  quantityaaaa          NUMBER,
  responsibleby         VARCHAR2(200 CHAR),
  retention             VARCHAR2(10 CHAR) not null,
  sbt_word              VARCHAR2(100 CHAR),
  security_class        VARCHAR2(4 CHAR),
  temp_no               NUMBER,
  year_folder_no        VARCHAR2(5 CHAR),
  ad_folderno           NUMBER,
  archive_ctg_no        VARCHAR2(16 CHAR),
  archive_entity_status VARCHAR2(6 CHAR),
  archive_flag          VARCHAR2(6 CHAR),
  archive_no            VARCHAR2(50 CHAR),
  barcode               VARCHAR2(30 CHAR),
  basefolder_unit       VARCHAR2(50 CHAR),
  content_no            VARCHAR2(20 CHAR),
  created_date          VARCHAR2(50 CHAR),
  doc_no                VARCHAR2(100 CHAR),
  filing_year           NUMBER,
  folder_location       VARCHAR2(100 CHAR),
  folder_no             VARCHAR2(50 CHAR),
  fonds_no              VARCHAR2(20 CHAR),
  pid                   VARCHAR2(50 CHAR),
  cre_user_id           VARCHAR2(50 CHAR),
  cre_user_name         VARCHAR2(50 CHAR),
  cre_dept_id           VARCHAR2(50 CHAR),
  cre_dept_name         VARCHAR2(50 CHAR),
  cre_chushi_id         VARCHAR2(50 CHAR),
  cre_chushi_name       VARCHAR2(50 CHAR),
  cre_ju_id             VARCHAR2(50 CHAR),
  cre_ju_name           VARCHAR2(50 CHAR),
  visible               VARCHAR2(1 CHAR),
  cre_time              VARCHAR2(30 CHAR)
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
-- Add comments to the table 
comment on table E_TJHZWJN_DAK
  is '文书档案卷内文件目录';
-- Add comments to the columns 
comment on column E_TJHZWJN_DAK.recid
  is '系统唯一字段';
comment on column E_TJHZWJN_DAK.jiduwjzhtai
  is '机读文件状态';
comment on column E_TJHZWJN_DAK.leibiehao
  is '类别号';
comment on column E_TJHZWJN_DAK.maintitle
  is '文件题名';
comment on column E_TJHZWJN_DAK.note
  is '备注';
comment on column E_TJHZWJN_DAK.quantity
  is '实物份数';
comment on column E_TJHZWJN_DAK.page_no
  is '页次';
comment on column E_TJHZWJN_DAK.page_num
  is '页数';
comment on column E_TJHZWJN_DAK.piece_no
  is '序号';
comment on column E_TJHZWJN_DAK.pigeonhole_date
  is '归档日期';
comment on column E_TJHZWJN_DAK.quantityaaaa
  is '数量';
comment on column E_TJHZWJN_DAK.responsibleby
  is '责任者';
comment on column E_TJHZWJN_DAK.retention
  is '保管期限';
comment on column E_TJHZWJN_DAK.sbt_word
  is '主题词';
comment on column E_TJHZWJN_DAK.security_class
  is '密级';
comment on column E_TJHZWJN_DAK.temp_no
  is '临时号';
comment on column E_TJHZWJN_DAK.year_folder_no
  is '案卷号';
comment on column E_TJHZWJN_DAK.ad_folderno
  is '预立卷号';
comment on column E_TJHZWJN_DAK.archive_ctg_no
  is '实体分类号';
comment on column E_TJHZWJN_DAK.archive_entity_status
  is '档案实体状态';
comment on column E_TJHZWJN_DAK.archive_flag
  is '组卷标识';
comment on column E_TJHZWJN_DAK.archive_no
  is '文件级档号';
comment on column E_TJHZWJN_DAK.barcode
  is '条码';
comment on column E_TJHZWJN_DAK.basefolder_unit
  is '立卷单位';
comment on column E_TJHZWJN_DAK.content_no
  is '目录号';
comment on column E_TJHZWJN_DAK.created_date
  is '文件形成日期';
comment on column E_TJHZWJN_DAK.doc_no
  is '文号';
comment on column E_TJHZWJN_DAK.filing_year
  is '归档年度';
comment on column E_TJHZWJN_DAK.folder_location
  is '存放位置';
comment on column E_TJHZWJN_DAK.folder_no
  is '案卷级档案号';
comment on column E_TJHZWJN_DAK.fonds_no
  is '全宗号';
comment on column E_TJHZWJN_DAK.pid
  is '父id';

-- Create table
create table E_E_YEAR_QGDA_DAK
(
  archive_ctg_no        VARCHAR2(20 CHAR),
  archive_entity_status VARCHAR2(6 CHAR),
  archive_no            VARCHAR2(30 CHAR),
  barcode               VARCHAR2(30 CHAR),
  basefolder_unit       VARCHAR2(50 CHAR),
  content_no            VARCHAR2(20 CHAR),
  doc_end_time          VARCHAR2(50 CHAR),
  doc_start_time        VARCHAR2(50 CHAR),
  filing_year           NUMBER,
  folder_location       VARCHAR2(100 CHAR),
  folder_no             VARCHAR2(50 CHAR),
  fonds_no              VARCHAR2(20 CHAR),
  leibiehao             VARCHAR2(20 CHAR),
  maintitle             VARCHAR2(255 CHAR),
  note                  VARCHAR2(255 CHAR),
  object_quantity       NUMBER,
  page_num              NUMBER,
  piece_no              NUMBER,
  piece_num             NUMBER,
  pigeonhole_date       VARCHAR2(50 CHAR),
  recid                 VARCHAR2(50 CHAR),
  retention             VARCHAR2(10 CHAR),
  sbt_word              VARCHAR2(2000 CHAR),
  security_class        VARCHAR2(4 CHAR),
  temp_no               NUMBER,
  year_folder_no        NUMBER,
  quantity              NUMBER,
  cre_user_id           VARCHAR2(50 CHAR),
  cre_user_name         VARCHAR2(50 CHAR),
  cre_dept_id           VARCHAR2(50 CHAR),
  cre_dept_name         VARCHAR2(50 CHAR),
  cre_chushi_id         VARCHAR2(50 CHAR),
  cre_chushi_name       VARCHAR2(50 CHAR),
  cre_ju_id             VARCHAR2(50 CHAR),
  cre_ju_name           VARCHAR2(50 CHAR),
  visible               VARCHAR2(1 CHAR),
  cre_time              VARCHAR2(30 CHAR)
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
comment on column E_E_YEAR_QGDA_DAK.archive_ctg_no
  is '实体分类号';
comment on column E_E_YEAR_QGDA_DAK.archive_entity_status
  is '档案实体状态
';
comment on column E_E_YEAR_QGDA_DAK.archive_no
  is '文件档号
';
comment on column E_E_YEAR_QGDA_DAK.barcode
  is '条码
';
comment on column E_E_YEAR_QGDA_DAK.basefolder_unit
  is '主管单位
';
comment on column E_E_YEAR_QGDA_DAK.content_no
  is '科别
';
comment on column E_E_YEAR_QGDA_DAK.doc_end_time
  is '文件结束时间
';
comment on column E_E_YEAR_QGDA_DAK.doc_start_time
  is '文件开始时间
';
comment on column E_E_YEAR_QGDA_DAK.filing_year
  is '归档年度
';
comment on column E_E_YEAR_QGDA_DAK.folder_location
  is '存放位置';
comment on column E_E_YEAR_QGDA_DAK.folder_no
  is '案卷级档号
';
comment on column E_E_YEAR_QGDA_DAK.fonds_no
  is '全宗号
';
comment on column E_E_YEAR_QGDA_DAK.leibiehao
  is '类别号
';
comment on column E_E_YEAR_QGDA_DAK.maintitle
  is '主管人
';
comment on column E_E_YEAR_QGDA_DAK.note
  is '变更记录
';
comment on column E_E_YEAR_QGDA_DAK.object_quantity
  is '实物份数
';
comment on column E_E_YEAR_QGDA_DAK.page_num
  is '卷内页数
';
comment on column E_E_YEAR_QGDA_DAK.piece_no
  is '件号
';
comment on column E_E_YEAR_QGDA_DAK.piece_num
  is '件数
';
comment on column E_E_YEAR_QGDA_DAK.pigeonhole_date
  is '归档日期
';
comment on column E_E_YEAR_QGDA_DAK.recid
  is '系统唯一关键字';
comment on column E_E_YEAR_QGDA_DAK.retention
  is '保管期限
';
comment on column E_E_YEAR_QGDA_DAK.sbt_word
  is '备注';
comment on column E_E_YEAR_QGDA_DAK.security_class
  is '密级
';
comment on column E_E_YEAR_QGDA_DAK.temp_no
  is '临时号
';
comment on column E_E_YEAR_QGDA_DAK.year_folder_no
  is '案卷号
';
comment on column E_E_YEAR_QGDA_DAK.quantity
  is '数量
';

-- Create table
create table E_E_YEAR_QGDA
(
  archive_ctg_no        VARCHAR2(20 CHAR),
  archive_entity_status VARCHAR2(6 CHAR),
  archive_no            VARCHAR2(30 CHAR),
  barcode               VARCHAR2(30 CHAR),
  basefolder_unit       VARCHAR2(50 CHAR),
  content_no            VARCHAR2(20 CHAR),
  doc_end_time          VARCHAR2(50 CHAR),
  doc_start_time        VARCHAR2(50 CHAR),
  filing_year           NUMBER,
  folder_location       VARCHAR2(100 CHAR),
  folder_no             VARCHAR2(50 CHAR),
  fonds_no              VARCHAR2(20 CHAR),
  leibiehao             VARCHAR2(20 CHAR),
  maintitle             VARCHAR2(255 CHAR),
  note                  VARCHAR2(255 CHAR),
  object_quantity       NUMBER,
  page_num              NUMBER,
  piece_no              NUMBER,
  piece_num             NUMBER,
  pigeonhole_date       VARCHAR2(50 CHAR),
  recid                 VARCHAR2(50 CHAR),
  retention             VARCHAR2(10 CHAR),
  sbt_word              VARCHAR2(2000 CHAR),
  security_class        VARCHAR2(4 CHAR),
  temp_no               NUMBER,
  year_folder_no        NUMBER,
  quantity              NUMBER,
  cre_user_id           VARCHAR2(50 CHAR),
  cre_user_name         VARCHAR2(50 CHAR),
  cre_dept_id           VARCHAR2(50 CHAR),
  cre_dept_name         VARCHAR2(50 CHAR),
  cre_chushi_id         VARCHAR2(50 CHAR),
  cre_chushi_name       VARCHAR2(50 CHAR),
  cre_ju_id             VARCHAR2(50 CHAR),
  cre_ju_name           VARCHAR2(50 CHAR),
  visible               VARCHAR2(1 CHAR),
  cre_time              VARCHAR2(30 CHAR)
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
comment on column E_E_YEAR_QGDA.archive_ctg_no
  is '实体分类号';
comment on column E_E_YEAR_QGDA.archive_entity_status
  is '档案实体状态
';
comment on column E_E_YEAR_QGDA.archive_no
  is '文件档号
';
comment on column E_E_YEAR_QGDA.barcode
  is '条码
';
comment on column E_E_YEAR_QGDA.basefolder_unit
  is '主管单位
';
comment on column E_E_YEAR_QGDA.content_no
  is '科别
';
comment on column E_E_YEAR_QGDA.doc_end_time
  is '文件结束时间
';
comment on column E_E_YEAR_QGDA.doc_start_time
  is '文件开始时间
';
comment on column E_E_YEAR_QGDA.filing_year
  is '归档年度
';
comment on column E_E_YEAR_QGDA.folder_location
  is '存放位置';
comment on column E_E_YEAR_QGDA.folder_no
  is '案卷级档号
';
comment on column E_E_YEAR_QGDA.fonds_no
  is '全宗号
';
comment on column E_E_YEAR_QGDA.leibiehao
  is '类别号
';
comment on column E_E_YEAR_QGDA.maintitle
  is '主管人
';
comment on column E_E_YEAR_QGDA.note
  is '变更记录
';
comment on column E_E_YEAR_QGDA.object_quantity
  is '实物份数
';
comment on column E_E_YEAR_QGDA.page_num
  is '卷内页数
';
comment on column E_E_YEAR_QGDA.piece_no
  is '件号
';
comment on column E_E_YEAR_QGDA.piece_num
  is '件数
';
comment on column E_E_YEAR_QGDA.pigeonhole_date
  is '归档日期
';
comment on column E_E_YEAR_QGDA.recid
  is '系统唯一关键字';
comment on column E_E_YEAR_QGDA.retention
  is '保管期限
';
comment on column E_E_YEAR_QGDA.sbt_word
  is '备注';
comment on column E_E_YEAR_QGDA.security_class
  is '密级
';
comment on column E_E_YEAR_QGDA.temp_no
  is '临时号
';
comment on column E_E_YEAR_QGDA.year_folder_no
  is '案卷号
';
comment on column E_E_YEAR_QGDA.quantity
  is '数量
';

-- Create table
create table E_TJWSJIAN_DAK
(
  archive_ctg_no        VARCHAR2(16 CHAR),
  archive_entity_status VARCHAR2(6 CHAR),
  archive_flag          VARCHAR2(6 CHAR),
  archive_no            VARCHAR2(50 CHAR),
  barcode               VARCHAR2(10 CHAR),
  basefolder_unit       VARCHAR2(50 CHAR),
  case_no               NUMBER,
  content_no            VARCHAR2(20 CHAR),
  created_date          VARCHAR2(50 CHAR),
  doc_no                VARCHAR2(100 CHAR),
  filing_year           NUMBER,
  folder_location       VARCHAR2(100 CHAR),
  fonds_no              VARCHAR2(10 CHAR),
  leibiehao             VARCHAR2(20 CHAR),
  maintitle             VARCHAR2(255 CHAR),
  note                  VARCHAR2(255 CHAR),
  quantity              NUMBER,
  page_num              NUMBER,
  piece_no              NUMBER,
  piece_num             NUMBER,
  pigeonhole_date       VARCHAR2(50 CHAR),
  recid                 VARCHAR2(50 CHAR) not null,
  responsibleby         VARCHAR2(200 CHAR),
  retention             VARCHAR2(10 CHAR),
  sbt_word              VARCHAR2(100 CHAR),
  security_class        VARCHAR2(4 CHAR),
  temp_no               NUMBER,
  cre_user_id           VARCHAR2(50 CHAR),
  cre_user_name         VARCHAR2(50 CHAR),
  cre_dept_id           VARCHAR2(50 CHAR),
  cre_dept_name         VARCHAR2(50 CHAR),
  cre_chushi_id         VARCHAR2(50 CHAR),
  cre_chushi_name       VARCHAR2(50 CHAR),
  cre_ju_id             VARCHAR2(50 CHAR),
  cre_ju_name           VARCHAR2(50 CHAR),
  visible               VARCHAR2(1 CHAR),
  cre_time              VARCHAR2(30 CHAR)
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
comment on column E_TJWSJIAN_DAK.archive_ctg_no
  is '实体分类号';
comment on column E_TJWSJIAN_DAK.archive_entity_status
  is '档案实体状态
';
comment on column E_TJWSJIAN_DAK.archive_flag
  is '组卷标识';
comment on column E_TJWSJIAN_DAK.archive_no
  is '文件级档号';
comment on column E_TJWSJIAN_DAK.barcode
  is '条码';
comment on column E_TJWSJIAN_DAK.basefolder_unit
  is '立卷单位';
comment on column E_TJWSJIAN_DAK.case_no
  is '盒号';
comment on column E_TJWSJIAN_DAK.content_no
  is '目录号';
comment on column E_TJWSJIAN_DAK.created_date
  is '文件形成日期';
comment on column E_TJWSJIAN_DAK.doc_no
  is '文号';
comment on column E_TJWSJIAN_DAK.filing_year
  is '归档年度';
comment on column E_TJWSJIAN_DAK.folder_location
  is '存放位置';
comment on column E_TJWSJIAN_DAK.fonds_no
  is '全宗号';
comment on column E_TJWSJIAN_DAK.leibiehao
  is '类别号';
comment on column E_TJWSJIAN_DAK.maintitle
  is '文件题名';
comment on column E_TJWSJIAN_DAK.note
  is '备注';
comment on column E_TJWSJIAN_DAK.quantity
  is '实物份数';
comment on column E_TJWSJIAN_DAK.page_num
  is '页数';
comment on column E_TJWSJIAN_DAK.piece_no
  is '件号';
comment on column E_TJWSJIAN_DAK.piece_num
  is '件数';
comment on column E_TJWSJIAN_DAK.pigeonhole_date
  is '归档日期';
comment on column E_TJWSJIAN_DAK.recid
  is '系统唯一关键字';
comment on column E_TJWSJIAN_DAK.responsibleby
  is '责任者';
comment on column E_TJWSJIAN_DAK.retention
  is '保管期限';
comment on column E_TJWSJIAN_DAK.sbt_word
  is '主题词';
comment on column E_TJWSJIAN_DAK.security_class
  is '密级';
comment on column E_TJWSJIAN_DAK.temp_no
  is '临时号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table E_TJWSJIAN_DAK
  add constraint RECID primary key (RECID)
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
create table E_TJWSJIAN
(
  archive_ctg_no        VARCHAR2(16 CHAR),
  archive_entity_status VARCHAR2(6 CHAR),
  archive_flag          VARCHAR2(6 CHAR),
  archive_no            VARCHAR2(50 CHAR),
  barcode               VARCHAR2(10 CHAR),
  basefolder_unit       VARCHAR2(50 CHAR),
  case_no               NUMBER,
  content_no            VARCHAR2(20 CHAR),
  created_date          VARCHAR2(50 CHAR),
  doc_no                VARCHAR2(100 CHAR),
  filing_year           NUMBER,
  folder_location       VARCHAR2(100 CHAR),
  fonds_no              VARCHAR2(10 CHAR),
  leibiehao             VARCHAR2(20 CHAR),
  maintitle             VARCHAR2(255 CHAR),
  note                  VARCHAR2(255 CHAR),
  quantity              NUMBER,
  page_num              NUMBER,
  piece_no              NUMBER,
  piece_num             NUMBER,
  pigeonhole_date       VARCHAR2(50 CHAR),
  recid                 VARCHAR2(50 CHAR) not null,
  responsibleby         VARCHAR2(200 CHAR),
  retention             VARCHAR2(10 CHAR),
  sbt_word              VARCHAR2(100 CHAR),
  security_class        VARCHAR2(4 CHAR),
  temp_no               NUMBER,
  cre_user_id           VARCHAR2(50 CHAR),
  cre_user_name         VARCHAR2(50 CHAR),
  cre_dept_id           VARCHAR2(50 CHAR),
  cre_dept_name         VARCHAR2(50 CHAR),
  cre_chushi_id         VARCHAR2(50 CHAR),
  cre_chushi_name       VARCHAR2(50 CHAR),
  cre_ju_id             VARCHAR2(50 CHAR),
  cre_ju_name           VARCHAR2(50 CHAR),
  visible               VARCHAR2(1 CHAR),
  cre_time              VARCHAR2(30 CHAR)
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
comment on column E_TJWSJIAN.archive_ctg_no
  is '实体分类号';
comment on column E_TJWSJIAN.archive_entity_status
  is '档案实体状态
';
comment on column E_TJWSJIAN.archive_flag
  is '组卷标识';
comment on column E_TJWSJIAN.archive_no
  is '文件级档号';
comment on column E_TJWSJIAN.barcode
  is '条码';
comment on column E_TJWSJIAN.basefolder_unit
  is '立卷单位';
comment on column E_TJWSJIAN.case_no
  is '盒号';
comment on column E_TJWSJIAN.content_no
  is '目录号';
comment on column E_TJWSJIAN.created_date
  is '文件形成日期';
comment on column E_TJWSJIAN.doc_no
  is '文号';
comment on column E_TJWSJIAN.filing_year
  is '归档年度';
comment on column E_TJWSJIAN.folder_location
  is '存放位置';
comment on column E_TJWSJIAN.fonds_no
  is '全宗号';
comment on column E_TJWSJIAN.leibiehao
  is '类别号';
comment on column E_TJWSJIAN.maintitle
  is '文件题名';
comment on column E_TJWSJIAN.note
  is '备注';
comment on column E_TJWSJIAN.quantity
  is '实物份数';
comment on column E_TJWSJIAN.page_num
  is '页数';
comment on column E_TJWSJIAN.piece_no
  is '件号';
comment on column E_TJWSJIAN.piece_num
  is '件数';
comment on column E_TJWSJIAN.pigeonhole_date
  is '归档日期';
comment on column E_TJWSJIAN.recid
  is '系统唯一关键字';
comment on column E_TJWSJIAN.responsibleby
  is '责任者';
comment on column E_TJWSJIAN.retention
  is '保管期限';
comment on column E_TJWSJIAN.sbt_word
  is '主题词';
comment on column E_TJWSJIAN.security_class
  is '密级';
comment on column E_TJWSJIAN.temp_no
  is '临时号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table E_TJWSJIAN
  add constraint RECID primary key (RECID)
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
