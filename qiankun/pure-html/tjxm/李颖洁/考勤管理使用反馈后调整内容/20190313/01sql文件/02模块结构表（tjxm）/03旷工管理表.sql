--添加旷工类型字段
alter table KQGL_ABSENTEEISM_INFO add ABSENTEEISM_TYPE varchar2(1 char);
comment on column KQGL_ABSENTEEISM_INFO.ABSENTEEISM_TYPE is '旷工类型（0：旷工；1：无故迟到；2：无故早退）';