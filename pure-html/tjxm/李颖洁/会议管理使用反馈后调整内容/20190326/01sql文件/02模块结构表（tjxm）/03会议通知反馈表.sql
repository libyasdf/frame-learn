alter table hygl_notice_back add (SHOULD_ATTEND_NUM varchar2(10 char));
alter table hygl_notice_back add (REAL_ATTEND_NUM varchar2(10 char));
alter table hygl_notice_back add (ATTEND_PERSON_LEAVE_ID varchar2(4000 char));
alter table hygl_notice_back add (ATTEND_PERSON_LEAVE_NAME varchar2(4000 char));
comment on column hygl_notice_back.SHOULD_ATTEND_NUM is '应参会人数';
comment on column hygl_notice_back.REAL_ATTEND_NUM is '实参会人数';
comment on column hygl_notice_back.ATTEND_PERSON_CHU_ID is '主要领导人id';
comment on column hygl_notice_back.ATTEND_PERSON_CHU_NAME is '主要领导人姓名';
comment on column hygl_notice_back.ATTEND_PERSON_LEAVE_ID is '分管领导人id';
comment on column hygl_notice_back.ATTEND_PERSON_LEAVE_NAME is '分管领导人姓名';
alter table hygl_notice_back add (NOT_ATTEND_PERSON_ID varchar2(4000 char));
comment on column hygl_notice_back.NOT_ATTEND_PERSON_ID is '不能参会人员id';
alter table hygl_notice_back add (NOT_ATTEND_PERSON_NAME varchar2(4000 char));
comment on column hygl_notice_back.NOT_ATTEND_PERSON_NAME is '不能参会人员姓名';
alter table hygl_notice_back add (NOT_ATTEND_PERSON_CHU_ID varchar2(4000 char));
comment on column hygl_notice_back.NOT_ATTEND_PERSON_CHU_ID is '不能参会的主要领导人id';
alter table hygl_notice_back add (NOT_ATTEND_PERSON_CHU_NAME varchar2(4000 char));
comment on column hygl_notice_back.NOT_ATTEND_PERSON_CHU_NAME is '不能参会的主要领导人姓名';
alter table hygl_notice_back add (NOT_ATTEND_PERSON_LEAVE_ID varchar2(4000 char));
comment on column hygl_notice_back.NOT_ATTEND_PERSON_LEAVE_ID is '不能参会的分管领导人id';
alter table hygl_notice_back add (NOT_ATTEND_PERSON_LEAVE_NAME varchar2(4000 char));
comment on column hygl_notice_back.NOT_ATTEND_PERSON_LEAVE_NAME is '不能参会的分管领导人姓名';