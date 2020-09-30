--【考勤管理员】系统角色新增加资源模块（【查询统计】资源模块）
insert into sys_ctrl_record (ID, RLSY_ID, RESOURCE_ID, RELEMENT_STR, IT_PURVIEW, IT_CONTROL, SUB_ID, AUTHOR, TIME)
values ('4028da0669471a740169bd1d041e17cb', '1000459', '1000454', null, null, '0', '97206', '1', '2019-03-27 11:06:29');

--取消【会议管理员】与会议室申请下的【草稿列表】的关联关系
delete from sys_ctrl_record t where t.rlsy_id = '1000421' and t.resource_id = '1000045';