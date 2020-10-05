--查询基础配置 zbgl_duty_config
select t.*,t.rowid from zbgl_duty_config t;

--查询值班表 zbgl_duty_detail
select t.*,t.rowid from zbgl_duty_detail t;

--查询值班表上报表 zbgl_duty_info
select t.*,t.rowid from zbgl_duty_info t;

--查询值班工作通知接收 zbgl_duty_notice_incept
select t.*,t.rowid from zbgl_duty_notice_incept t;

--查询值班通知表 zbgl_duty_notice
select t.*,t.rowid from zbgl_duty_notice t;

--查询催办记录
select t.*,t.rowid from sys_urge t where t.module_type='zbgl_shangbao';

--tjxm用户 查询值班管理不走流程待办
select t.*,t.rowid from sys_waitdo_noflow t where t.op_name='安保值班工作通知';

--uias用户 查询值班管理消息
select t.*,t.rowid from message t where t.subject like '%值班%';


