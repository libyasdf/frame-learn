--添加原因类型字段
alter table KQGL_COMELATE_LEAVEEARLY_INFO add reason_type varchar2(1 char);
comment on column KQGL_COMELATE_LEAVEEARLY_INFO.reason_type is '原因类型（0：因私；1：因公）';