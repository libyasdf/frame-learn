--发布人父部门id
alter table VIDEO_COLUMN_FB_USER add FB_DEPT_ID varchar2(50 CHAR);
--审核人父部门id 
alter table VIDEO_COLUMN_FB_USER add SH_DEPT_ID varchar2(50 CHAR);
comment on column VIDEO_COLUMN_FB_USER.FB_DEPT_ID
  is '发布人父部门id';
comment on column VIDEO_COLUMN_FB_USER.SH_DEPT_ID
  is '审核人父部门id';