--增加是否手工录入字段
alter table hygl_meeting_apply add IF_INPUT_BYHAND varchar2(1 CHAR);
--是否被管理员撤办 0：否  1：是 
alter table hygl_meeting_apply add IF_REMOVED_BYADMIN varchar2(1 CHAR);
comment on column HYGL_MEETING_APPLY.IF_INPUT_BYHAND
  is '是否手工录入 0：否  1：是';
comment on column HYGL_MEETING_APPLY.IF_REMOVED_BYADMIN
  is '是否被管理员撤办 0：否  1：是';