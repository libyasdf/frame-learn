--删除基础配置 zbgl_duty_config
delete from zbgl_duty_config t;

--删除值班表 zbgl_duty_detail
delete from zbgl_duty_detail t;

--删除值班表上报表 zbgl_duty_info
delete from zbgl_duty_info t;

--删除值班工作通知接收 zbgl_duty_notice_incept
delete from zbgl_duty_notice_incept t;

--删除值班通知表 zbgl_duty_notice
delete from zbgl_duty_notice t;

--删除催办记录
delete from sys_urge t where t.module_type='zbgl_shangbao';

--tjxm用户 删除值班管理不走流程待办
delete from sys_waitdo_noflow t where t.op_name='安保值班工作通知';

--uias用户 删除值班管理消息
delete from message t where t.subject like '%值班%';


