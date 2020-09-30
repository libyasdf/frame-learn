--添加旷工人处室id和处室名字段
alter table KQGL_ABSENTEEISM_INFO add ABS_APPLICANT_CHUSHI_ID varchar2(50 char);
alter table KQGL_ABSENTEEISM_INFO add ABS_APPLICANT_CHUSHI_NAME varchar2(50 char);
comment on column KQGL_ABSENTEEISM_INFO.ABS_APPLICANT_CHUSHI_ID is '旷工人处室ID';
comment on column KQGL_ABSENTEEISM_INFO.ABS_APPLICANT_CHUSHI_NAME is '旷工人处室名称';