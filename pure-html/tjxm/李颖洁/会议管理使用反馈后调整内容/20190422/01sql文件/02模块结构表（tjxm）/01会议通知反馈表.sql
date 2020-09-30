--修改参会人员字段的长度为4000
alter table hygl_notice_back modify ATTEND_PERSON_CHU_ID varchar2(4000 char);
alter table hygl_notice_back modify ATTEND_PERSON_CHU_NAME varchar2(4000 char);
alter table hygl_notice_back modify ATTEND_PERSON_ID varchar2(4000 char);
alter table hygl_notice_back modify ATTEND_DEPT_ID varchar2(4000 char);
alter table hygl_notice_back modify ATTEND_PERSON_NAME varchar2(4000 char);
alter table hygl_notice_back modify ATTEND_DEPT varchar2(4000 char);
alter table hygl_notice_back modify ATTEND_PERSON_LEAVE_ID varchar2(4000 char);
alter table hygl_notice_back modify ATTEND_PERSON_LEAVE_NAME varchar2(4000 char);