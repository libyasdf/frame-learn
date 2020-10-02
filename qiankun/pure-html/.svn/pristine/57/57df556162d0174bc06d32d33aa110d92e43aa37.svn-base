-- 改变档案借阅的审批人，审批单位相关字段长度
alter table dagl_borrow modify(approve_user_id VARCHAR2(500 CHAR));
alter table dagl_borrow modify(approve_user_name VARCHAR2(500 CHAR));
alter table dagl_borrow modify(approve_unit_id VARCHAR2(500 CHAR));
alter table dagl_borrow modify(approve_unit_name VARCHAR2(500 CHAR));

alter table dagl_file modify(approve_user_id VARCHAR2(500 CHAR));
alter table dagl_file modify(approve_user_name VARCHAR2(500 CHAR));
alter table dagl_file modify(approve_unit_id VARCHAR2(500 CHAR));
alter table dagl_file modify(approve_unit_name VARCHAR2(500 CHAR));

--档案借阅，添加密级和页数两个字段
alter table dagl_file add security_class varchar2(10 char);
alter table dagl_file add page_num varchar2(10 char);
comment  on  column  dagl_file.security_class   is  '密级';
comment  on  column  dagl_file.page_num   is  '页数';

--把值继承全部都去掉 ******后续，档号项不要设置值继承*******
update table_struct_description t set t.column_inherit='F'

