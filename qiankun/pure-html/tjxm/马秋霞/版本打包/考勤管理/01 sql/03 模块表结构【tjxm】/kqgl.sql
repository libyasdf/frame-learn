create table KQGL_GO_OUT_INFO
(
  id                  VARCHAR2(50 CHAR) not null,
  cre_user_id         VARCHAR2(50 CHAR),
  cre_user_name       VARCHAR2(50 CHAR),
  cre_dept_id         VARCHAR2(50 CHAR),
  cre_dept_name       VARCHAR2(50 CHAR),
  cre_chushi_id       VARCHAR2(50 CHAR),
  cre_chushi_name     VARCHAR2(50 CHAR),
  cre_ju_id           VARCHAR2(50 CHAR),
  cre_ju_name         VARCHAR2(50 CHAR),
  visible             VARCHAR2(1 CHAR),
  cre_time            VARCHAR2(30 CHAR),
  subflag             VARCHAR2(2 CHAR),
  update_user_id      VARCHAR2(50 CHAR),
  update_user_name    VARCHAR2(50 CHAR),
  update_time         VARCHAR2(30 CHAR),
  go_out_title        VARCHAR2(100 CHAR),
  applicant_unit_id   VARCHAR2(50 CHAR),
  applicant_unit_name VARCHAR2(50 CHAR),
  application_time    VARCHAR2(30 CHAR),
  destination         VARCHAR2(30 CHAR),
  go_out_long_time    VARCHAR2(10 CHAR),
  go_out_reason       VARCHAR2(500 CHAR),
  go_out_date         VARCHAR2(30 CHAR),
  start_stop_time     VARCHAR2(30 CHAR)
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
comment on table KQGL_GO_OUT_INFO
  is '���ڹ���ģ�飺�����Ϣ��
KQGL:���ڹ���ģ�����ǰ׺';
comment on column KQGL_GO_OUT_INFO.id
  is '����';
comment on column KQGL_GO_OUT_INFO.cre_user_id
  is '������ID��Ҳ��������ID�ֶΣ�';
comment on column KQGL_GO_OUT_INFO.cre_user_name
  is '��������ƣ�Ҳ������������ֶΣ�';
comment on column KQGL_GO_OUT_INFO.cre_dept_id
  is '�����˲���ID';
comment on column KQGL_GO_OUT_INFO.cre_dept_name
  is '�����˲�����';
comment on column KQGL_GO_OUT_INFO.cre_chushi_id
  is '�����˴���ID';
comment on column KQGL_GO_OUT_INFO.cre_chushi_name
  is '�����˴�����';
comment on column KQGL_GO_OUT_INFO.cre_ju_id
  is '�����˶�����ID';
comment on column KQGL_GO_OUT_INFO.cre_ju_name
  is '�����˶�������';
comment on column KQGL_GO_OUT_INFO.visible
  is '�߼�ɾ��';
comment on column KQGL_GO_OUT_INFO.cre_time
  is '����ʱ��';
comment on column KQGL_GO_OUT_INFO.subflag
  is '����״̬';
comment on column KQGL_GO_OUT_INFO.update_user_id
  is '��������ID';
comment on column KQGL_GO_OUT_INFO.update_user_name
  is '����������';
comment on column KQGL_GO_OUT_INFO.update_time
  is '������ʱ��';
comment on column KQGL_GO_OUT_INFO.go_out_title
  is '����';
comment on column KQGL_GO_OUT_INFO.applicant_unit_id
  is '�����˵�λID';
comment on column KQGL_GO_OUT_INFO.applicant_unit_name
  is '�����˵�λ���';
comment on column KQGL_GO_OUT_INFO.application_time
  is '����ʱ��';
comment on column KQGL_GO_OUT_INFO.destination
  is 'Ŀ�ĵ�';
comment on column KQGL_GO_OUT_INFO.go_out_long_time
  is '���ʱ��';
comment on column KQGL_GO_OUT_INFO.go_out_reason
  is '�������';
comment on column KQGL_GO_OUT_INFO.go_out_date
  is '�������';
comment on column KQGL_GO_OUT_INFO.start_stop_time
  is '�����ֹʱ��';
alter table KQGL_GO_OUT_INFO
  add constraint PK_KQGL_GO_OUT_INFO primary key (ID)
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
  
  
  
  
  
  create table KQGL_OVER_TIME_INFO
(
  id                   VARCHAR2(50 CHAR) not null,
  cre_user_id          VARCHAR2(50 CHAR),
  cre_user_name        VARCHAR2(50 CHAR),
  cre_dept_id          VARCHAR2(50 CHAR),
  cre_dept_name        VARCHAR2(50 CHAR),
  cre_chushi_id        VARCHAR2(50 CHAR),
  cre_chushi_name      VARCHAR2(50 CHAR),
  cre_ju_id            VARCHAR2(50 CHAR),
  cre_ju_name          VARCHAR2(50 CHAR),
  visible              VARCHAR2(1 CHAR),
  cre_time             VARCHAR2(30 CHAR),
  subflag              VARCHAR2(2 CHAR),
  title                VARCHAR2(200 CHAR),
  update_user_id       VARCHAR2(50 CHAR),
  update_user_name     VARCHAR2(50 CHAR),
  update_time          VARCHAR2(30 CHAR),
  over_time_title      VARCHAR2(100 CHAR),
  applicant_unit_id    VARCHAR2(50 CHAR),
  applicant_unit_name  VARCHAR2(50 CHAR),
  application_time     VARCHAR2(30 CHAR),
  over_time_type       VARCHAR2(50 CHAR),
  over_time_date       VARCHAR2(30 CHAR),
  start_stop_time      VARCHAR2(30 CHAR),
  over_time_long_timeh VARCHAR2(10 CHAR),
  over_time_reason     VARCHAR2(500 CHAR),
  filetype             VARCHAR2(50 CHAR),
  idea                 VARCHAR2(500 CHAR),
  over_time_long_timed VARCHAR2(10 CHAR)
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
comment on table KQGL_OVER_TIME_INFO
  is '���ڹ���ģ�飺�Ӱ���Ϣ��
KQGL:���ڹ���ģ�����ǰ׺';
comment on column KQGL_OVER_TIME_INFO.id
  is '����';
comment on column KQGL_OVER_TIME_INFO.cre_user_id
  is '������ID��Ҳ��������ID�ֶΣ�';
comment on column KQGL_OVER_TIME_INFO.cre_user_name
  is '��������ƣ�Ҳ������������ֶΣ�';
comment on column KQGL_OVER_TIME_INFO.cre_dept_id
  is '�����˲���ID';
comment on column KQGL_OVER_TIME_INFO.cre_dept_name
  is '�����˲�����';
comment on column KQGL_OVER_TIME_INFO.cre_chushi_id
  is '�����˴���ID';
comment on column KQGL_OVER_TIME_INFO.cre_chushi_name
  is '�����˴�����';
comment on column KQGL_OVER_TIME_INFO.cre_ju_id
  is '�����˶�����ID';
comment on column KQGL_OVER_TIME_INFO.cre_ju_name
  is '�����˶�������';
comment on column KQGL_OVER_TIME_INFO.visible
  is '�߼�ɾ��';
comment on column KQGL_OVER_TIME_INFO.cre_time
  is '����ʱ��';
comment on column KQGL_OVER_TIME_INFO.subflag
  is '����״̬';
comment on column KQGL_OVER_TIME_INFO.title
  is '������';
comment on column KQGL_OVER_TIME_INFO.update_user_id
  is '��������ID';
comment on column KQGL_OVER_TIME_INFO.update_user_name
  is '����������';
comment on column KQGL_OVER_TIME_INFO.update_time
  is '������ʱ��';
comment on column KQGL_OVER_TIME_INFO.over_time_title
  is '����';
comment on column KQGL_OVER_TIME_INFO.applicant_unit_id
  is '�����˵�λID';
comment on column KQGL_OVER_TIME_INFO.applicant_unit_name
  is '�����˵�λ���';
comment on column KQGL_OVER_TIME_INFO.application_time
  is '����ʱ��';
comment on column KQGL_OVER_TIME_INFO.over_time_type
  is '�Ӱ�����';
comment on column KQGL_OVER_TIME_INFO.over_time_date
  is '�Ӱ�����';
comment on column KQGL_OVER_TIME_INFO.start_stop_time
  is '�Ӱ����ֹʱ��';
comment on column KQGL_OVER_TIME_INFO.over_time_long_timeh
  is '�Ӱ�ʱ��(�����ռӰ�ʱ����';
comment on column KQGL_OVER_TIME_INFO.over_time_reason
  is '�Ӱ�����';
comment on column KQGL_OVER_TIME_INFO.filetype
  is '��������';
comment on column KQGL_OVER_TIME_INFO.idea
  is '���';
comment on column KQGL_OVER_TIME_INFO.over_time_long_timed
  is '�Ӱ�ʱ��(�ڼ��ռӰ�ʱ����';
alter table KQGL_OVER_TIME_INFO
  add constraint PK_KQGL_OVER_TIME_INFO primary key (ID)
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
  
  
  
  
 create table KQGL_LEAVE_INFO
(
  id                  VARCHAR2(50 CHAR) not null,
  cre_user_id         VARCHAR2(50 CHAR),
  cre_user_name       VARCHAR2(50 CHAR),
  cre_dept_id         VARCHAR2(50 CHAR),
  cre_dept_name       VARCHAR2(50 CHAR),
  cre_chushi_id       VARCHAR2(50 CHAR),
  cre_chushi_name     VARCHAR2(50 CHAR),
  cre_ju_id           VARCHAR2(50 CHAR),
  cre_ju_name         VARCHAR2(50 CHAR),
  visible             VARCHAR2(1 CHAR),
  cre_time            VARCHAR2(30 CHAR),
  subflag             VARCHAR2(2 CHAR),
  title               VARCHAR2(200 CHAR),
  update_user_id      VARCHAR2(50 CHAR),
  update_user_name    VARCHAR2(50 CHAR),
  update_time         VARCHAR2(30 CHAR),
  applicant_unit_id   VARCHAR2(50 CHAR),
  applicant_unit_name VARCHAR2(50 CHAR),
  application_time    VARCHAR2(30 CHAR),
  leave_start_date    VARCHAR2(30 CHAR),
  start_am_or_pm      VARCHAR2(50 CHAR),
  leave_end_date      VARCHAR2(30 CHAR),
  end_am_or_pm        VARCHAR2(50 CHAR),
  leave_type          VARCHAR2(50 CHAR),
  leave_long_time     VARCHAR2(10 CHAR),
  leave_reason        VARCHAR2(500 CHAR),
  leave_title         VARCHAR2(50 CHAR)
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
comment on table KQGL_LEAVE_INFO
  is '���ڹ���ģ�飺�����Ϣ��
KQGL:���ڹ���ģ�����ǰ׺';
-- Add comments to the columns 
comment on column KQGL_LEAVE_INFO.id
  is '����';
comment on column KQGL_LEAVE_INFO.cre_user_id
  is '������ID��Ҳ��������ID�ֶΣ�';
comment on column KQGL_LEAVE_INFO.cre_user_name
  is '��������ƣ�Ҳ������������ֶΣ�';
comment on column KQGL_LEAVE_INFO.cre_dept_id
  is '��������ID';
comment on column KQGL_LEAVE_INFO.cre_dept_name
  is '����������';
comment on column KQGL_LEAVE_INFO.cre_chushi_id
  is '�����˴���ID';
comment on column KQGL_LEAVE_INFO.cre_chushi_name
  is '�����˴�����';
comment on column KQGL_LEAVE_INFO.cre_ju_id
  is '�����˶�����ID';
comment on column KQGL_LEAVE_INFO.cre_ju_name
  is '�����˶�������';
comment on column KQGL_LEAVE_INFO.visible
  is '�߼�ɾ��';
comment on column KQGL_LEAVE_INFO.cre_time
  is '����ʱ��';
comment on column KQGL_LEAVE_INFO.subflag
  is '����״̬';
comment on column KQGL_LEAVE_INFO.title
  is '������';
comment on column KQGL_LEAVE_INFO.update_user_id
  is '��������ID';
comment on column KQGL_LEAVE_INFO.update_user_name
  is '����������';
comment on column KQGL_LEAVE_INFO.update_time
  is '������ʱ��';
comment on column KQGL_LEAVE_INFO.applicant_unit_id
  is '�����˵�λID';
comment on column KQGL_LEAVE_INFO.applicant_unit_name
  is '�����˵�λ���';
comment on column KQGL_LEAVE_INFO.application_time
  is '����ʱ��';
comment on column KQGL_LEAVE_INFO.leave_start_date
  is '��ٿ�ʼ����';
comment on column KQGL_LEAVE_INFO.start_am_or_pm
  is '��ٿ�ʼ����';
comment on column KQGL_LEAVE_INFO.leave_end_date
  is '��ٽ�������';
comment on column KQGL_LEAVE_INFO.end_am_or_pm
  is '��ٽ���������';
comment on column KQGL_LEAVE_INFO.leave_type
  is '������Ͱ��������ݡ��¼١����١����ݼ١�̽�׼١���١�ɥ�١�����١���١�����';
comment on column KQGL_LEAVE_INFO.leave_long_time
  is '���ʱ��';
comment on column KQGL_LEAVE_INFO.leave_reason
  is '�������';
comment on column KQGL_LEAVE_INFO.leave_title
  is '��ٱ���';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_LEAVE_INFO
  add constraint PK_KQGL_LEAVE_INFO primary key (ID)
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
  
  
  
  
  
  
  create table KQGL_BUSINESS_TRIP_INFO
(
  id                     VARCHAR2(50 CHAR) not null,
  cre_user_id            VARCHAR2(50 CHAR),
  cre_user_name          VARCHAR2(50 CHAR),
  cre_dept_id            VARCHAR2(50 CHAR),
  cre_dept_name          VARCHAR2(50 CHAR),
  cre_chushi_id          VARCHAR2(50 CHAR),
  cre_chushi_name        VARCHAR2(50 CHAR),
  cre_ju_id              VARCHAR2(50 CHAR),
  cre_ju_name            VARCHAR2(50 CHAR),
  visible                VARCHAR2(1 CHAR),
  cre_time               VARCHAR2(30 CHAR),
  subflag                VARCHAR2(2 CHAR),
  title                  VARCHAR2(200 CHAR),
  update_user_id         VARCHAR2(50 CHAR),
  update_user_name       VARCHAR2(50 CHAR),
  update_time            VARCHAR2(30 CHAR),
  business_trip_title    VARCHAR2(100 CHAR),
  application_unit_id    VARCHAR2(50 CHAR),
  application_unit_name  VARCHAR2(50 CHAR),
  application_time       VARCHAR2(30 CHAR),
  start_time             VARCHAR2(30 CHAR),
  end_time               VARCHAR2(30 CHAR),
  destination            VARCHAR2(30 CHAR),
  vehicle                VARCHAR2(30 CHAR),
  is_have_reception_fees VARCHAR2(1 CHAR),
  reception_fees         VARCHAR2(30 CHAR),
  long_time              VARCHAR2(10 CHAR),
  business_trip_reason   VARCHAR2(500 CHAR),
  filetype               VARCHAR2(50 CHAR),
  idea                   VARCHAR2(500 CHAR),
  busi_trip_type         VARCHAR2(1 CHAR),
  start_am_or_pm         VARCHAR2(1 CHAR),
  end_am_or_pm           VARCHAR2(1 CHAR),
  trip_colleague         VARCHAR2(500 CHAR),
  trip_colleague_ids     VARCHAR2(100 CHAR)
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
comment on table KQGL_BUSINESS_TRIP_INFO
  is '���ڹ���ģ�飺������Ϣ��
KQGL:���ڹ���ģ�����ǰ׺';
-- Add comments to the columns 
comment on column KQGL_BUSINESS_TRIP_INFO.id
  is '����';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_user_id
  is '������ID��Ҳ��������ID�ֶΣ�';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_user_name
  is '��������ƣ�Ҳ������������ֶΣ�';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_dept_id
  is '��������ID';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_dept_name
  is '����������';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_chushi_id
  is '�����˴���ID';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_chushi_name
  is '�����˴�����';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_ju_id
  is '�����˶�����ID';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_ju_name
  is '�����˶�������';
comment on column KQGL_BUSINESS_TRIP_INFO.visible
  is '�߼�ɾ��';
comment on column KQGL_BUSINESS_TRIP_INFO.cre_time
  is '����ʱ��';
comment on column KQGL_BUSINESS_TRIP_INFO.subflag
  is '����״̬';
comment on column KQGL_BUSINESS_TRIP_INFO.title
  is '������';
comment on column KQGL_BUSINESS_TRIP_INFO.update_user_id
  is '��������ID';
comment on column KQGL_BUSINESS_TRIP_INFO.update_user_name
  is '����������';
comment on column KQGL_BUSINESS_TRIP_INFO.update_time
  is '������ʱ��';
comment on column KQGL_BUSINESS_TRIP_INFO.business_trip_title
  is '����';
comment on column KQGL_BUSINESS_TRIP_INFO.application_unit_id
  is '�����˵�λID';
comment on column KQGL_BUSINESS_TRIP_INFO.application_unit_name
  is '�����˵�λ���';
comment on column KQGL_BUSINESS_TRIP_INFO.application_time
  is '����ʱ��';
comment on column KQGL_BUSINESS_TRIP_INFO.start_time
  is '���ʼ����';
comment on column KQGL_BUSINESS_TRIP_INFO.end_time
  is '�����������';
comment on column KQGL_BUSINESS_TRIP_INFO.destination
  is 'Ŀ�ĵ�';
comment on column KQGL_BUSINESS_TRIP_INFO.vehicle
  is '����ͨ����';
comment on column KQGL_BUSINESS_TRIP_INFO.is_have_reception_fees
  is '���޽Ӵ����';
comment on column KQGL_BUSINESS_TRIP_INFO.reception_fees
  is '�Ӵ����';
comment on column KQGL_BUSINESS_TRIP_INFO.long_time
  is '����ʱ��';
comment on column KQGL_BUSINESS_TRIP_INFO.business_trip_reason
  is '��������';
comment on column KQGL_BUSINESS_TRIP_INFO.filetype
  is '��������';
comment on column KQGL_BUSINESS_TRIP_INFO.idea
  is '���';
comment on column KQGL_BUSINESS_TRIP_INFO.busi_trip_type
  is '�������ͣ�1�������0ҵ����';
comment on column KQGL_BUSINESS_TRIP_INFO.start_am_or_pm
  is '���ʼ������ 1������  0������';
comment on column KQGL_BUSINESS_TRIP_INFO.end_am_or_pm
  is '������������� 1������ 0������';
comment on column KQGL_BUSINESS_TRIP_INFO.trip_colleague
  is 'ͬ���� ��������';
comment on column KQGL_BUSINESS_TRIP_INFO.trip_colleague_ids
  is 'ͬ����ids';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_BUSINESS_TRIP_INFO
  add constraint PK_KQGL_BUSINESS_TRIP_INFO primary key (ID)
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
  
  
  
  
  create table KQGL_ABSENTEEISM_INFO
(
  id                      VARCHAR2(50 CHAR) not null,
  cre_user_id             VARCHAR2(50 CHAR),
  cre_user_name           VARCHAR2(50 CHAR),
  cre_dept_id             VARCHAR2(50 CHAR),
  cre_dept_name           VARCHAR2(50 CHAR),
  cre_chushi_id           VARCHAR2(50 CHAR),
  cre_chushi_name         VARCHAR2(50 CHAR),
  cre_ju_id               VARCHAR2(50 CHAR),
  cre_ju_name             VARCHAR2(50 CHAR),
  visible                 VARCHAR2(1 CHAR),
  cre_time                VARCHAR2(30 CHAR),
  update_user_id          VARCHAR2(50 CHAR),
  update_user_name        VARCHAR2(50 CHAR),
  update_time             VARCHAR2(30 CHAR),
  applicant_unit_id       VARCHAR2(50 CHAR),
  applicant_unit_name     VARCHAR2(50 CHAR),
  application_time        VARCHAR2(30 CHAR),
  absenteeism_user_id     VARCHAR2(50 CHAR),
  absenteeism_user_name   VARCHAR2(50 CHAR),
  absenteeism_date        VARCHAR2(30 CHAR),
  absenteeism_reason      VARCHAR2(200 CHAR),
  abs_applicant_unit_id   VARCHAR2(50 CHAR),
  abs_applicant_unit_name VARCHAR2(50 CHAR)
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
comment on table KQGL_ABSENTEEISM_INFO
  is '���ڹ���ģ�飺������Ϣ�� KQGL:���ڹ���ģ�����ǰ׺';
-- Add comments to the columns 
comment on column KQGL_ABSENTEEISM_INFO.id
  is '����';
comment on column KQGL_ABSENTEEISM_INFO.cre_user_id
  is '������id��Ҳ��¼����id�ֶΣ�';
comment on column KQGL_ABSENTEEISM_INFO.cre_user_name
  is '��������ƣ�Ҳ��¼��������ֶΣ�';
comment on column KQGL_ABSENTEEISM_INFO.cre_dept_id
  is '��������id';
comment on column KQGL_ABSENTEEISM_INFO.cre_dept_name
  is '��������name';
comment on column KQGL_ABSENTEEISM_INFO.cre_chushi_id
  is '�����˴���id';
comment on column KQGL_ABSENTEEISM_INFO.cre_chushi_name
  is '�����˴�����';
comment on column KQGL_ABSENTEEISM_INFO.cre_ju_id
  is '�����˶�����id';
comment on column KQGL_ABSENTEEISM_INFO.cre_ju_name
  is '�����˶�������';
comment on column KQGL_ABSENTEEISM_INFO.visible
  is '�߼�ɾ��';
comment on column KQGL_ABSENTEEISM_INFO.cre_time
  is '����ʱ��';
comment on column KQGL_ABSENTEEISM_INFO.update_user_id
  is '��������id';
comment on column KQGL_ABSENTEEISM_INFO.update_user_name
  is '��������name';
comment on column KQGL_ABSENTEEISM_INFO.update_time
  is '������ʱ��';
comment on column KQGL_ABSENTEEISM_INFO.applicant_unit_id
  is '¼���˵�λid';
comment on column KQGL_ABSENTEEISM_INFO.applicant_unit_name
  is '¼���˵�λ���';
comment on column KQGL_ABSENTEEISM_INFO.application_time
  is '¼��ʱ��';
comment on column KQGL_ABSENTEEISM_INFO.absenteeism_user_id
  is '������id';
comment on column KQGL_ABSENTEEISM_INFO.absenteeism_user_name
  is '����������';
comment on column KQGL_ABSENTEEISM_INFO.absenteeism_date
  is '��������';
comment on column KQGL_ABSENTEEISM_INFO.absenteeism_reason
  is '����ԭ��';
comment on column KQGL_ABSENTEEISM_INFO.abs_applicant_unit_id
  is '�����˵�λid';
comment on column KQGL_ABSENTEEISM_INFO.abs_applicant_unit_name
  is '�����˵�λ���';
  
  
  
  create table KQGL_COMELATE_LEAVEEARLY_INFO
(
  id                  VARCHAR2(50 CHAR) not null,
  cre_user_id         VARCHAR2(50 CHAR),
  cre_user_name       VARCHAR2(50 CHAR),
  cre_dept_id         VARCHAR2(50 CHAR),
  cre_dept_name       VARCHAR2(50 CHAR),
  cre_chushi_id       VARCHAR2(50 CHAR),
  cre_chushi_name     VARCHAR2(50 CHAR),
  cre_ju_id           VARCHAR2(50 CHAR),
  cre_ju_name         VARCHAR2(50 CHAR),
  visible             VARCHAR2(1 CHAR),
  cre_time            VARCHAR2(30 CHAR),
  update_user_id      VARCHAR2(50 CHAR),
  update_user_name    VARCHAR2(30 CHAR),
  update_time         VARCHAR2(30 CHAR),
  applicant_unit_id   VARCHAR2(50 CHAR),
  applicant_unit_name VARCHAR2(50 CHAR),
  application_time    VARCHAR2(30 CHAR),
  cdzt_user_name      VARCHAR2(50 CHAR),
  cdzt_user_id        VARCHAR2(30 CHAR),
  state               VARCHAR2(2 CHAR),
  cdzt_date           VARCHAR2(30 CHAR),
  cdzt_reason         VARCHAR2(200 CHAR),
  subflag             VARCHAR2(1 CHAR),
  cdzt_title          VARCHAR2(50 CHAR)
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
comment on table KQGL_COMELATE_LEAVEEARLY_INFO
  is '���ڹ���ģ�飺�ٵ�������Ϣ��
kqgl:���ڹ���ģ�����ǰ׺';
-- Add comments to the columns 
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.id
  is '����';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_user_id
  is '������id��Ҳ��������id�ֶΣ�';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_user_name
  is '��������ƣ�Ҳ������������ֶΣ�';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_dept_id
  is '��������id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_dept_name
  is '��������name';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_chushi_id
  is '�����˴���id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_chushi_name
  is '�����˴���name';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_ju_id
  is '�����˶�����id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_ju_name
  is '�����˶�����name';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.visible
  is '�߼�ɾ��';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cre_time
  is '����ʱ��';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.update_user_id
  is '��������id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.update_user_name
  is '��������name';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.update_time
  is '������ʱ��';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.applicant_unit_id
  is '�����˵�λid';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.applicant_unit_name
  is '�����˵�λ���';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.application_time
  is '����ʱ��';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_user_name
  is '�ٵ�����������';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_user_id
  is '����id';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.state
  is '״̬ 0:�ٵ� 1������';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_date
  is '�ٵ���������';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_reason
  is '�ٵ�����ԭ��';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.subflag
  is '����״̬';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.cdzt_title
  is '�ٵ����˱���';

  
  
-- Create table
create table KQGL_SUPPLEMENT_SIGN_INFO
(
  id                         VARCHAR2(50 CHAR) not null,
  cre_user_id                VARCHAR2(50 CHAR),
  cre_user_name              VARCHAR2(50 CHAR),
  cre_dept_id                VARCHAR2(50 CHAR),
  cre_dept_name              VARCHAR2(50 CHAR),
  cre_chushi_id              VARCHAR2(50 CHAR),
  cre_chushi_name            VARCHAR2(50 CHAR),
  cre_ju_id                  VARCHAR2(50 CHAR),
  cre_ju_name                VARCHAR2(50 CHAR),
  visible                    VARCHAR2(1 CHAR),
  cre_time                   VARCHAR2(30 CHAR),
  subflag                    VARCHAR2(2 CHAR),
  supplement_sign_title      VARCHAR2(200 CHAR),
  update_user_id             VARCHAR2(50 CHAR),
  update_user_name           VARCHAR2(50 CHAR),
  update_time                VARCHAR2(30 CHAR),
  leave_title                VARCHAR2(100 CHAR),
  applicant_unit_id          VARCHAR2(50 CHAR),
  applicant_unit_name        VARCHAR2(50 CHAR),
  application_time           VARCHAR2(30 CHAR),
  supplement_sign_type       VARCHAR2(50 CHAR),
  supplement_sign_date       VARCHAR2(30 CHAR),
  supplement_sign_start_time VARCHAR2(30 CHAR),
  supplement_sign_end_time   VARCHAR2(30 CHAR),
  supplement_sign_reason     VARCHAR2(500 CHAR)
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
comment on table KQGL_SUPPLEMENT_SIGN_INFO
  is '���ڹ���ģ�飺��ǩ��Ϣ��
KQGL:���ڹ���ģ�����ǰ׺';
-- Add comments to the columns 
comment on column KQGL_SUPPLEMENT_SIGN_INFO.id
  is '����';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_user_id
  is '������ID��Ҳ��������ID�ֶΣ�';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_user_name
  is '��������ƣ�Ҳ������������ֶΣ�';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_dept_id
  is '�����˲���ID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_dept_name
  is '�����˲�����';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_chushi_id
  is '�����˴���ID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_chushi_name
  is '�����˴�����';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_ju_id
  is '�����˶�����ID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_ju_name
  is '�����˶�������';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.visible
  is '�߼�ɾ��';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.cre_time
  is '����ʱ��';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.subflag
  is '����״̬';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_title
  is '������';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.update_user_id
  is '��������ID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.update_user_name
  is '����������';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.update_time
  is '������ʱ��';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.leave_title
  is '����';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.applicant_unit_id
  is '�����˵�λID';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.applicant_unit_name
  is '�����˵�λ���';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.application_time
  is '����ʱ��';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_type
  is '��ǩ���� 0����ǩ�� 1����ǩ�� 2����ȫ��';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_date
  is '��ǩ����';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_start_time
  is '��ǩ��ʼʱ��';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_end_time
  is '��ǩ����ʱ��';
comment on column KQGL_SUPPLEMENT_SIGN_INFO.supplement_sign_reason
  is '��ǩ����';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KQGL_SUPPLEMENT_SIGN_INFO
  add constraint PK_KQGL_SUPPLEMENT_SIGN_INFO primary key (ID)
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
