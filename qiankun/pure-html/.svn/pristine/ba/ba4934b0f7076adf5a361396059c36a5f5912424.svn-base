alter table hygl_meeting_apply modify (APPLY_TITLE VARCHAR2(200 CHAR));
alter table hygl_meeting_apply rename column MEETING_START_DATE to MEETING_TIME;
alter table hygl_meeting_apply drop column meeting_end_date;
alter table hygl_meeting_apply drop column MEETING_START_TIME;
alter table hygl_meeting_apply drop column MEETING_END_TIME;
 alter table hygl_meeting_apply drop column meetingroom_id;
alter table hygl_meeting_apply drop column meetingroom_name;

comment on column HYGL_MEETING_APPLY.meeting_time is '会议时间';