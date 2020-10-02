--添加原因是否是下拉选字段，申请人处室id和处室名称
alter table KQGL_COMELATE_LEAVEEARLY_INFO add is_choose varchar2(1 char);
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.is_choose is '原因是否是下拉选（1：是）';
alter table KQGL_COMELATE_LEAVEEARLY_INFO add APPLICANT_CHUSHI_ID varchar2(50 char);
alter table KQGL_COMELATE_LEAVEEARLY_INFO add APPLICANT_CHUSHI_NAME varchar2(50 char);
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.APPLICANT_CHUSHI_ID is '申请人处室ID';
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.APPLICANT_CHUSHI_NAME is '申请人处室名称';