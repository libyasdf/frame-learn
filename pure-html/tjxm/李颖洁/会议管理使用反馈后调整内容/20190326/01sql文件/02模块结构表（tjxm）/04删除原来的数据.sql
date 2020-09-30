--删除原来的数据，因为会议申请的会议室，新增加了表保存，原来数据会不显示申请的会议室
delete from hygl_meeting_apply;
delete from hygl_meeting_notice;
delete from hygl_notice_back;
delete from hygl_meeting_service_task;
delete from hygl_seat;