--V1.0版本插入字段isChange,与实体字段不符合，删除原来的字段，改为is_change
ALTER TABLE SYS_WORK_PLAN DROP COLUMN isChange; 

alter table SYS_WORK_PLAN add is_change VARCHAR2(1 CHAR);

comment on column SYS_WORK_PLAN.is_change  is '是否由计划转为日志（为空表示未转化，1表示是由计划转成日志）';