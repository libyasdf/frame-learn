--修改参会人员的字段长度
alter table hygl_notice_back modify attend_person_id varchar2(4000 char);
alter table hygl_notice_back modify attend_dept_id varchar2(4000 char);
alter table hygl_notice_back modify attend_person_name varchar2(4000 char);
alter table hygl_notice_back modify attend_dept varchar2(4000 char);
alter table hygl_notice_back modify attend_person_chu_id varchar2(4000 char);
alter table hygl_notice_back modify attend_person_chu_name varchar2(4000 char);
alter table hygl_notice_back modify attend_person_leave_id varchar2(4000 char);
alter table hygl_notice_back modify attend_person_leave_name varchar2(4000 char);
alter table hygl_notice_back modify not_attend_person_id varchar2(4000 char);
alter table hygl_notice_back modify not_attend_person_name varchar2(4000 char);
alter table hygl_notice_back modify not_attend_person_chu_id varchar2(4000 char);
alter table hygl_notice_back modify not_attend_person_chu_name varchar2(4000 char);
alter table hygl_notice_back modify not_attend_person_leave_id varchar2(4000 char);
alter table hygl_notice_back modify not_attend_person_leave_name varchar2(4000 char);
--以下三个字段，后期没用，本地没删除，不用执行，2019.5.31修改打包文件
--alter table hygl_notice_back modify attend_person_deptpid varchar2(4000 char);
--alter table hygl_notice_back modify attend_main_person_deptpid varchar2(4000 char);
--alter table hygl_notice_back modify attend_leave_person_deptpid varchar2(4000 char);