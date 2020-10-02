--增加提醒时间字段
alter table hygl_meeting_notice add remind_time varchar2(50 char);
comment on column hygl_meeting_notice.remind_time
  is '提醒反馈参会人员的时间';