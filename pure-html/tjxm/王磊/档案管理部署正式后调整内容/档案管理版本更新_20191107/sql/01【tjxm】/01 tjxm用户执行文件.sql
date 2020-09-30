-- 收集表添加页数和密集

--档案借阅，添加密级和页数两个字段
alter table dagl_shopping_trolley add security_class varchar2(10 char);
alter table dagl_shopping_trolley add page_num varchar2(10 char);
comment  on  column  dagl_shopping_trolley.security_class   is  '密级';
comment  on  column  dagl_shopping_trolley.page_num   is  '页数';

